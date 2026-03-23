'use client';

import { useEffect, useState } from 'react';
import { useRouter, useSearchParams, usePathname } from 'next/navigation';
import {
  isDemoAllowlistMode,
  isEmailAllowedForDemos,
  isUnlockQueryAllowed,
  getDemoUnlockToken,
} from '@/lib/demo-access-policy';
import {
  isPortalApiConfigured,
  isPortalAuthRequired,
} from '@/lib/portal-auth-policy';
import { fetchPortalMe } from '@/lib/portal-api';
import { clearPortalJwt, getPortalJwt } from '@/lib/portal-session';
import {
  DEMO_ACCESS_KEY,
  DEMO_SCOPE_KEY,
  DEMO_LOGIN_EMAIL_KEY,
  DEMO_BYPASS_KEY,
} from '@/lib/demo-session-keys';
import { hrefLoginNext } from '@/lib/routes';

export {
  DEMO_ACCESS_KEY,
  DEMO_SCOPE_KEY,
  DEMO_LOGIN_EMAIL_KEY,
  DEMO_BYPASS_KEY,
};

function sessionAllowsDemo(): boolean {
  const stored =
    typeof window !== 'undefined' && sessionStorage.getItem(DEMO_ACCESS_KEY);
  if (!stored) return false;
  if (!isDemoAllowlistMode()) return true;
  if (sessionStorage.getItem(DEMO_BYPASS_KEY) === '1') return true;
  const email = sessionStorage.getItem(DEMO_LOGIN_EMAIL_KEY) ?? '';
  return email.length > 0 && isEmailAllowedForDemos(email);
}

function applyUnlockFromQuery(searchParams: ReturnType<typeof useSearchParams>) {
  const unlockParam = searchParams.get('unlock');
  const unlockOk = isUnlockQueryAllowed(unlockParam);
  if (!unlockOk) return false;
  if (typeof window !== 'undefined') {
    sessionStorage.setItem(DEMO_ACCESS_KEY, '1');
    sessionStorage.setItem(DEMO_SCOPE_KEY, 'full');
    if (isDemoAllowlistMode() && getDemoUnlockToken()) {
      sessionStorage.setItem(DEMO_BYPASS_KEY, '1');
    } else {
      sessionStorage.removeItem(DEMO_BYPASS_KEY);
    }
  }
  return true;
}

/**
 * Gate para demos internas. Orden:
 * 1) `?unlock=` válido (preview interno).
 * 2) Si hay API + JWT válido → acceso (modo enterprise).
 * 3) Si portal no obligatorio y sesión legacy (correo) → acceso.
 * 4) Si portal obligatorio o hay API pero no JWT → /login.
 */
export function DemoGate({ children }: { children: React.ReactNode }) {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const [allowed, setAllowed] = useState(false);
  const [checking, setChecking] = useState(true);

  useEffect(() => {
    let cancelled = false;

    async function run() {
      if (applyUnlockFromQuery(searchParams)) {
        if (typeof window !== 'undefined') {
          window.history.replaceState({}, '', pathname);
        }
        if (!cancelled) {
          setAllowed(true);
          setChecking(false);
        }
        return;
      }

      const api = isPortalApiConfigured();
      const portalRequired = isPortalAuthRequired();
      const jwt = getPortalJwt();

      if (api && jwt) {
        const me = await fetchPortalMe(jwt);
        if (cancelled) return;
        if (me.ok) {
          try {
            sessionStorage.setItem(DEMO_ACCESS_KEY, '1');
            sessionStorage.setItem(DEMO_LOGIN_EMAIL_KEY, me.user.email);
            sessionStorage.removeItem(DEMO_BYPASS_KEY);
          } catch {
            /* ignore */
          }
          setAllowed(true);
          setChecking(false);
          return;
        }
        clearPortalJwt();
      }

      if (!portalRequired && sessionAllowsDemo()) {
        if (!cancelled) {
          setAllowed(true);
          setChecking(false);
        }
        return;
      }

      if (portalRequired || api) {
        const next = `${pathname}${typeof window !== 'undefined' ? window.location.search : ''}`;
        if (!cancelled) {
          router.replace(hrefLoginNext(next));
          setChecking(false);
        }
        return;
      }

      if (sessionAllowsDemo()) {
        if (!cancelled) {
          setAllowed(true);
          setChecking(false);
        }
        return;
      }

      if (!cancelled) {
        router.replace(hrefLoginNext(pathname));
        setChecking(false);
      }
    }

    void run();
    return () => {
      cancelled = true;
    };
  }, [router, searchParams, pathname]);

  if (checking || !allowed) {
    return (
      <div className="flex min-h-[50vh] items-center justify-center">
        <p className="text-muted-foreground">Comprobando acceso…</p>
      </div>
    );
  }

  return <>{children}</>;
}

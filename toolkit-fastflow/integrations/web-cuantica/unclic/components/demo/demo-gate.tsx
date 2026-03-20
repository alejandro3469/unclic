'use client';

import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import {
  isDemoAllowlistMode,
  isEmailAllowedForDemos,
  isUnlockQueryAllowed,
  getDemoUnlockToken,
} from '@/lib/demo-access-policy';
import {
  DEMO_ACCESS_KEY,
  DEMO_SCOPE_KEY,
  DEMO_LOGIN_EMAIL_KEY,
  DEMO_BYPASS_KEY,
} from '@/lib/demo-session-keys';

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

/** Comprueba gate (sessionStorage o ?unlock=…). Sin sesión válida → /login (correo). */
export function DemoGate({ children }: { children: React.ReactNode }) {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [allowed, setAllowed] = useState(false);

  useEffect(() => {
    const unlockParam = searchParams.get('unlock');
    const unlockOk = isUnlockQueryAllowed(unlockParam);

    if (unlockOk) {
      if (typeof window !== 'undefined') {
        sessionStorage.setItem(DEMO_ACCESS_KEY, '1');
        sessionStorage.setItem(DEMO_SCOPE_KEY, 'full');
        if (isDemoAllowlistMode() && getDemoUnlockToken()) {
          sessionStorage.setItem(DEMO_BYPASS_KEY, '1');
        } else {
          sessionStorage.removeItem(DEMO_BYPASS_KEY);
        }
        window.history.replaceState({}, '', '/demo');
      }
      setAllowed(true);
      return;
    }

    if (sessionAllowsDemo()) {
      setAllowed(true);
      return;
    }

    router.replace('/login?next=/demo');
  }, [router, searchParams]);

  if (!allowed) {
    return (
      <div className="flex min-h-[50vh] items-center justify-center">
        <p className="text-muted-foreground">Comprobando acceso…</p>
      </div>
    );
  }

  return <>{children}</>;
}

'use client';

import { useEffect, useState } from 'react';
import {
  isDemoAllowlistMode,
  isEmailAllowedForDemos,
} from '@/lib/demo-access-policy';
import {
  DEMO_ACCESS_KEY,
  DEMO_LOGIN_EMAIL_KEY,
  DEMO_BYPASS_KEY,
} from '@/lib/demo-session-keys';

/**
 * Tras montar: si no hay allowlist, siempre puede ver enlaces de infra.
 * Con allowlist: hace falta sesión demo + correo permitido.
 */
export function useDemoInfraAccess(): {
  ready: boolean;
  canAccessInfra: boolean;
} {
  const [ready, setReady] = useState(false);
  const [canAccessInfra, setCanAccessInfra] = useState(false);

  useEffect(() => {
    if (!isDemoAllowlistMode()) {
      setCanAccessInfra(true);
      setReady(true);
      return;
    }
    try {
      const hasFlag = sessionStorage.getItem(DEMO_ACCESS_KEY) === '1';
      const bypass = sessionStorage.getItem(DEMO_BYPASS_KEY) === '1';
      const email = sessionStorage.getItem(DEMO_LOGIN_EMAIL_KEY) ?? '';
      const ok =
        hasFlag &&
        (bypass || (email.length > 0 && isEmailAllowedForDemos(email)));
      setCanAccessInfra(ok);
    } catch {
      setCanAccessInfra(false);
    }
    setReady(true);
  }, []);

  return { ready, canAccessInfra };
}

/** JWT del portal (demos protegidas). sessionStorage = se pierde al cerrar pestaña. */
export const PORTAL_JWT_KEY = 'unclic_portal_jwt';

export function getPortalJwt(): string | null {
  if (typeof window === 'undefined') return null;
  try {
    return sessionStorage.getItem(PORTAL_JWT_KEY);
  } catch {
    return null;
  }
}

export function setPortalJwt(token: string): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.setItem(PORTAL_JWT_KEY, token);
  } catch {
    /* ignore */
  }
}

export function clearPortalJwt(): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.removeItem(PORTAL_JWT_KEY);
  } catch {
    /* ignore */
  }
}

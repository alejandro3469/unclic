import { getUnclicApiBase } from '@/lib/api-base';

/**
 * Con `NEXT_PUBLIC_PORTAL_AUTH_REQUIRED=true` las rutas gated solo aceptan sesión JWT
 * válida (además del `?unlock=` interno). Sin API URL configurada, el front mantiene
 * el modo legacy solo-correo si esta variable es false.
 */
export function isPortalAuthRequired(): boolean {
  const v = process.env.NEXT_PUBLIC_PORTAL_AUTH_REQUIRED?.trim().toLowerCase();
  return v === '1' || v === 'true' || v === 'yes';
}

export function isPortalApiConfigured(): boolean {
  return getUnclicApiBase().length > 0;
}

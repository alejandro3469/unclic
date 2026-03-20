/**
 * Política de acceso a demos / enlaces de infra (Jenkins, Gitea, POS, etc.).
 * Con `output: 'export'` todo corre en el cliente: la lista de correos va en el bundle
 * (NEXT_PUBLIC_*). Para seguridad real, protege también Nginx/Jenkins/Gitea con Basic Auth o VPN.
 */

function normalizeEmail(email: string): string {
  return email.trim().toLowerCase();
}

/** Modo `allowlist`: solo correos en NEXT_PUBLIC_DEMO_ALLOWED_EMAILS entran. */
export function isDemoAllowlistMode(): boolean {
  return process.env.NEXT_PUBLIC_DEMO_ACCESS_MODE === 'allowlist';
}

function parseAllowedEmails(): string[] {
  const raw = process.env.NEXT_PUBLIC_DEMO_ALLOWED_EMAILS ?? '';
  return raw
    .split(/[,;\s]+/)
    .map((s) => normalizeEmail(s))
    .filter(Boolean);
}

export function isEmailAllowedForDemos(email: string): boolean {
  if (!isDemoAllowlistMode()) return true;
  const allowed = parseAllowedEmails();
  if (allowed.length === 0) {
    // Allowlist activo pero vacío → nadie (evita abrir por error).
    return false;
  }
  return allowed.includes(normalizeEmail(email));
}

/**
 * Token opcional para ?unlock=TOKEN cuando allowlist está activo (p. ej. preview interno).
 * Si allowlist y esta variable está vacía, ?unlock=1 queda desactivado.
 */
export function getDemoUnlockToken(): string {
  return (process.env.NEXT_PUBLIC_DEMO_UNLOCK_TOKEN ?? '').trim();
}

export function isUnlockQueryAllowed(unlockParam: string | null): boolean {
  if (!unlockParam) return false;
  if (!isDemoAllowlistMode()) {
    return unlockParam === '1';
  }
  const token = getDemoUnlockToken();
  if (!token) return false;
  return unlockParam === token;
}

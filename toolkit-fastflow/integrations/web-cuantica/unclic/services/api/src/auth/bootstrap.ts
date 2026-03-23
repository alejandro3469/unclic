import { getAuthStore } from './store.js';
import { hashPassword } from './password.js';

function parseList(raw: string | undefined): string[] {
  if (!raw?.trim()) return [];
  return raw
    .split(/[,;\s]+/)
    .map((s) => s.trim().toLowerCase())
    .filter(Boolean);
}

/**
 * Crea admin(es) y usuario de prueba si no existen (contraseñas desde env).
 * Llamar una vez al arrancar el servidor.
 */
export function runAuthBootstrap(): void {
  const store = getAuthStore();
  const adminEmails = parseList(process.env.UNCLIC_ADMIN_EMAILS);
  const adminPassword = process.env.UNCLIC_ADMIN_PASSWORD?.trim();
  const seedEmail = process.env.UNCLIC_SEED_USER_EMAIL?.trim()?.toLowerCase();
  const seedPassword = process.env.UNCLIC_SEED_USER_PASSWORD?.trim();

  for (const email of adminEmails) {
    if (!adminPassword) {
      console.warn('[auth/bootstrap] UNCLIC_ADMIN_PASSWORD no definida; no se crea admin', email);
      continue;
    }
    if (store.findByEmail(email)) continue;
    try {
      store.createUser({
        email,
        passwordHash: hashPassword(adminPassword),
        emailVerified: true,
        role: 'admin',
      });
      console.log('[auth/bootstrap] admin creado:', email);
    } catch (e) {
      console.error('[auth/bootstrap] admin', email, e);
    }
  }

  if (seedEmail && seedPassword) {
    if (store.findByEmail(seedEmail)) return;
    try {
      store.createUser({
        email: seedEmail,
        passwordHash: hashPassword(seedPassword),
        emailVerified: true,
        role: 'user',
      });
      console.log('[auth/bootstrap] usuario demo creado:', seedEmail);
    } catch (e) {
      console.error('[auth/bootstrap] seed user', e);
    }
  }
}

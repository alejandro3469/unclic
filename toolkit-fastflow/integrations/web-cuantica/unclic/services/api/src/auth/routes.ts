import type { Hono } from 'hono';
import { z } from 'zod';
import { getAuthStore, newVerifyToken } from './store.js';
import { hashPassword, verifyPassword } from './password.js';
import { signPortalToken, verifyPortalToken } from './jwt.js';
import { sendVerificationEmail } from './mail-verify.js';
const registerSchema = z.object({
  email: z.string().email().max(320),
  password: z.string().min(8).max(200),
});

const loginSchema = z.object({
  email: z.string().email().max(320),
  password: z.string().min(1).max(200),
});

function portalPublicUrl(): string {
  const u = process.env.PORTAL_PUBLIC_URL?.trim();
  return u ? u.replace(/\/$/, '') : '';
}

async function bearerUser(c: { req: { header: (n: string) => string | undefined } }) {
  const h = c.req.header('Authorization') ?? '';
  const m = /^Bearer\s+(.+)$/i.exec(h);
  if (!m) return null;
  try {
    const payload = await verifyPortalToken(m[1].trim());
    const store = getAuthStore();
    const user = store.findById(payload.sub);
    if (!user || user.email !== payload.email) return null;
    return user;
  } catch {
    return null;
  }
}

export function registerAuthRoutes(app: Hono) {
  app.post('/v1/auth/register', async (c) => {
    let body: unknown;
    try {
      body = await c.req.json();
    } catch {
      return c.json({ ok: false, error: 'invalid_json' }, 400);
    }
    const parsed = registerSchema.safeParse(body);
    if (!parsed.success) {
      return c.json({ ok: false, error: 'validation', details: parsed.error.flatten() }, 400);
    }
    const email = parsed.data.email.trim().toLowerCase();
    const store = getAuthStore();
    if (store.findByEmail(email)) {
      return c.json({ ok: false, error: 'email_taken' }, 409);
    }

    const token = newVerifyToken();
    const expires = Date.now() + 48 * 60 * 60 * 1000;
    try {
      store.createUser({
        email,
        passwordHash: hashPassword(parsed.data.password),
        emailVerified: false,
        role: 'user',
        verifyToken: token,
        verifyExpires: expires,
      });
    } catch (e) {
      if (e instanceof Error && e.message === 'email_taken') {
        return c.json({ ok: false, error: 'email_taken' }, 409);
      }
      throw e;
    }

    const base = portalPublicUrl();
    const verifyPath = `/portal/verify-email?token=${encodeURIComponent(token)}`;
    const verifyUrl = base ? `${base}${verifyPath}` : verifyPath;

    const mailed = await sendVerificationEmail({ to: email, verifyUrl });
    const devLog = process.env.AUTH_LOG_VERIFICATION_LINK === '1' || process.env.NODE_ENV !== 'production';

    if (!mailed.ok && mailed.error === 'smtp_not_configured') {
      if (devLog) {
        console.warn('[v1/auth/register] SMTP no configurado; enlace de verificación (solo dev):', verifyUrl);
      }
      return c.json({
        ok: true,
        pendingVerification: true,
        message:
          'Cuenta creada. Configura Gmail SMTP en la API para enviar el correo; mientras tanto revisa la consola del servidor si AUTH_LOG_VERIFICATION_LINK=1.',
        devVerificationUrl: devLog ? verifyUrl : undefined,
      });
    }
    if (!mailed.ok) {
      return c.json({ ok: false, error: mailed.error }, 502);
    }

    return c.json({
      ok: true,
      pendingVerification: true,
      message: 'Revisa tu correo para verificar la cuenta antes de iniciar sesión.',
    });
  });

  app.get('/v1/auth/verify-email', async (c) => {
    const token = c.req.query('token')?.trim();
    if (!token) {
      return c.json({ ok: false, error: 'missing_token' }, 400);
    }
    const store = getAuthStore();
    const user = store.findByVerifyToken(token);
    if (!user) {
      return c.json({ ok: false, error: 'invalid_or_expired_token' }, 400);
    }
    if (user.verifyExpires != null && Date.now() > user.verifyExpires) {
      return c.json({ ok: false, error: 'token_expired' }, 400);
    }
    store.setVerification(user.id, true);
    return c.json({ ok: true, email: user.email });
  });

  app.post('/v1/auth/login', async (c) => {
    let body: unknown;
    try {
      body = await c.req.json();
    } catch {
      return c.json({ ok: false, error: 'invalid_json' }, 400);
    }
    const parsed = loginSchema.safeParse(body);
    if (!parsed.success) {
      return c.json({ ok: false, error: 'validation', details: parsed.error.flatten() }, 400);
    }
    const email = parsed.data.email.trim().toLowerCase();
    const store = getAuthStore();
    const user = store.findByEmail(email);
    if (!user || !verifyPassword(parsed.data.password, user.passwordHash)) {
      return c.json({ ok: false, error: 'invalid_credentials' }, 401);
    }
    if (!user.emailVerified) {
      return c.json({ ok: false, error: 'email_not_verified' }, 403);
    }

    let jwt: string;
    try {
      jwt = await signPortalToken({
        sub: user.id,
        email: user.email,
        role: user.role,
      });
    } catch (e) {
      console.error('[v1/auth/login] JWT', e);
      return c.json({ ok: false, error: 'jwt_misconfigured' }, 503);
    }

    return c.json({
      ok: true,
      token: jwt,
      user: { email: user.email, role: user.role, emailVerified: user.emailVerified },
    });
  });

  app.get('/v1/auth/me', async (c) => {
    const user = await bearerUser(c);
    if (!user) {
      return c.json({ ok: false, error: 'unauthorized' }, 401);
    }
    return c.json({
      ok: true,
      user: { email: user.email, role: user.role, emailVerified: user.emailVerified },
    });
  });
}

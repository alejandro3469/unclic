import { Hono } from 'hono';
import { cors } from 'hono/cors';
import { z } from 'zod';
import { createTransport, getSmtpConfigFromEnv } from './smtp.js';
import { sendLeadNotifications } from './mail.js';
import { fetchPingHealth } from './adapters/ping-adapter.js';
import { registerOssAssessmentRoutes } from './oss-assessment/register.js';
import { registerAuthRoutes } from './auth/routes.js';
import { runAuthBootstrap } from './auth/bootstrap.js';

const leadBodySchema = z.object({
  email: z.string().email().max(320),
  website: z.string().max(200).optional(),
  source: z.string().max(500).optional(),
});

function parseCorsOrigins(): string[] | string {
  const raw = process.env.CORS_ORIGINS?.trim();
  if (!raw || raw === '*') return '*';
  return raw.split(',').map((s) => s.trim()).filter(Boolean);
}

export function createApp() {
  const app = new Hono();

  app.use(
    '/*',
    cors({
      origin: parseCorsOrigins(),
      allowMethods: ['GET', 'POST', 'OPTIONS'],
      allowHeaders: ['Content-Type'],
      maxAge: 86400,
    })
  );

  app.get('/health', (c) =>
    c.json({
      ok: true,
      service: '@unclic/api',
      ts: new Date().toISOString(),
    })
  );

  app.get('/v1/info', (c) =>
    c.json({
      name: 'unclic-api',
      version: '0.1.0',
      hints: [
        'GET /health',
        'GET /v1/orchestration/health',
        'POST /v1/leads/email',
        'GET /v1/oss-assessment/schema',
        'POST /v1/oss-assessment/estimate',
        'POST /v1/auth/register',
        'POST /v1/auth/login',
        'GET /v1/auth/verify-email?token=',
        'GET /v1/auth/me',
      ],
    })
  );

  registerOssAssessmentRoutes(app);
  registerAuthRoutes(app);
  runAuthBootstrap();

  /** Agregación de salud: API + microservicio ping (si `PING_SERVICE_URL` está definida). */
  app.get('/v1/orchestration/health', async (c) => {
    const pingBase = process.env.PING_SERVICE_URL?.trim();
    const self = {
      ok: true as const,
      service: '@unclic/api',
      ts: new Date().toISOString(),
    };

    if (!pingBase) {
      return c.json({
        self,
        ping: { skipped: true, reason: 'PING_SERVICE_URL not configured' },
      });
    }

    const ping = await fetchPingHealth(pingBase);
    return c.json({
      self,
      ping: ping.ok
        ? { ok: true, upstream: ping.body }
        : { ok: false, error: ping.error, status: ping.status },
    });
  });

  app.post('/v1/leads/email', async (c) => {
    let body: unknown;
    try {
      body = await c.req.json();
    } catch {
      return c.json({ ok: false, error: 'invalid_json' }, 400);
    }

    const parsed = leadBodySchema.safeParse(body);
    if (!parsed.success) {
      return c.json({ ok: false, error: 'validation', details: parsed.error.flatten() }, 400);
    }

    const { email, website, source } = parsed.data;
    if (website && website.trim().length > 0) {
      return c.json({ ok: true });
    }

    const smtp = getSmtpConfigFromEnv();
    if (!smtp) {
      return c.json({ ok: false, error: 'smtp_not_configured' }, 503);
    }

    const notifyTo = process.env.LEAD_NOTIFY_TO?.trim() || smtp.user;
    const fromName = process.env.LEAD_FROM_NAME?.trim() || 'UnClic';

    try {
      const transport = createTransport(smtp);
      await sendLeadNotifications(transport, {
        fromAddress: smtp.user,
        fromName,
        notifyTo,
        lead: { email: email.trim().toLowerCase(), source: source?.trim() },
      });
    } catch (e) {
      console.error('[v1/leads/email]', e);
      return c.json({ ok: false, error: 'send_failed' }, 502);
    }

    return c.json({ ok: true });
  });

  return app;
}

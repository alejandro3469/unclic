import { describe, it, expect, vi, afterEach } from 'vitest';
import { createApp } from './app.js';

describe('createApp', () => {
  afterEach(() => {
    vi.unstubAllGlobals();
    vi.restoreAllMocks();
    delete process.env.PING_SERVICE_URL;
  });
  it('GET /health returns ok', async () => {
    const app = createApp();
    const res = await app.request('http://localhost/health');
    expect(res.status).toBe(200);
    const j = (await res.json()) as { ok: boolean; service: string };
    expect(j.ok).toBe(true);
    expect(j.service).toBe('@unclic/api');
  });

  it('POST /v1/leads/email rejects invalid email', async () => {
    const app = createApp();
    const res = await app.request('http://localhost/v1/leads/email', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: 'not-an-email' }),
    });
    expect(res.status).toBe(400);
  });

  it('POST /v1/leads/email honeypot returns 200 ok without sending', async () => {
    const app = createApp();
    const res = await app.request('http://localhost/v1/leads/email', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: 'ok@test.local', website: 'http://spam' }),
    });
    expect(res.status).toBe(200);
    const j = (await res.json()) as { ok: boolean };
    expect(j.ok).toBe(true);
  });

  it('POST /v1/leads/email returns 503 when SMTP not configured', async () => {
    const prevUser = process.env.GMAIL_SMTP_USER;
    const prevPass = process.env.GMAIL_SMTP_APP_PASSWORD;
    delete process.env.GMAIL_SMTP_USER;
    delete process.env.GMAIL_SMTP_APP_PASSWORD;
    try {
      const app = createApp();
      const res = await app.request('http://localhost/v1/leads/email', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: 'lead@test.local' }),
      });
      expect(res.status).toBe(503);
    } finally {
      if (prevUser !== undefined) process.env.GMAIL_SMTP_USER = prevUser;
      if (prevPass !== undefined) process.env.GMAIL_SMTP_APP_PASSWORD = prevPass;
    }
  });

  it('GET /v1/orchestration/health skips ping when PING_SERVICE_URL unset', async () => {
    delete process.env.PING_SERVICE_URL;
    const app = createApp();
    const res = await app.request('http://localhost/v1/orchestration/health');
    expect(res.status).toBe(200);
    const j = (await res.json()) as { ping: { skipped?: boolean } };
    expect(j.ping.skipped).toBe(true);
  });

  it('GET /v1/orchestration/health aggregates ping via HTTP adapter', async () => {
    process.env.PING_SERVICE_URL = 'http://ping:3010';
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        status: 200,
        json: async () => ({ ok: true, service: '@unclic/ping' }),
      })
    );
    const app = createApp();
    const res = await app.request('http://localhost/v1/orchestration/health');
    expect(res.status).toBe(200);
    const j = (await res.json()) as {
      ping: { ok: boolean; upstream?: { service: string } };
    };
    expect(j.ping.ok).toBe(true);
    expect(j.ping.upstream?.service).toBe('@unclic/ping');
  });
});

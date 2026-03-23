import { describe, it, expect, beforeEach, afterEach } from 'vitest';
import { mkdirSync, unlinkSync, existsSync } from 'node:fs';
import { join } from 'node:path';
import { tmpdir } from 'node:os';
import { randomUUID } from 'node:crypto';
import { createApp } from '../app.js';
import { resetAuthStoreForTests } from './store.js';

describe('auth routes', () => {
  let storePath: string;

  beforeEach(() => {
    resetAuthStoreForTests();
    storePath = join(tmpdir(), `unclic-auth-${randomUUID()}.json`);
    process.env.AUTH_STORE_PATH = storePath;
    process.env.AUTH_JWT_SECRET = 'test-jwt-secret-minimum-32-characters-long';
    delete process.env.UNCLIC_ADMIN_EMAILS;
    delete process.env.UNCLIC_ADMIN_PASSWORD;
    delete process.env.UNCLIC_SEED_USER_EMAIL;
    delete process.env.GMAIL_SMTP_USER;
    delete process.env.GMAIL_SMTP_APP_PASSWORD;
    const dir = join(tmpdir(), 'unclic-api-test-data');
    try {
      mkdirSync(dir, { recursive: true });
    } catch {
      /* ignore */
    }
  });

  afterEach(() => {
    resetAuthStoreForTests();
    if (storePath && existsSync(storePath)) {
      try {
        unlinkSync(storePath);
      } catch {
        /* ignore */
      }
    }
    delete process.env.AUTH_STORE_PATH;
  });

  it('register → verify-email → login → me', async () => {
    process.env.PORTAL_PUBLIC_URL = 'http://localhost:3002';
    const app = createApp();

    const reg = await app.request('http://localhost/v1/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: 'newuser@test.local', password: 'password123' }),
    });
    expect(reg.status).toBe(200);
    const regJson = (await reg.json()) as { ok?: boolean; devVerificationUrl?: string };
    expect(regJson.ok).toBe(true);
    expect(regJson.devVerificationUrl).toBeDefined();
    const u = new URL(regJson.devVerificationUrl!);
    const token = u.searchParams.get('token');
    expect(token).toBeTruthy();

    const ver = await app.request(
      `http://localhost/v1/auth/verify-email?token=${encodeURIComponent(token!)}`
    );
    expect(ver.status).toBe(200);

    const login = await app.request('http://localhost/v1/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: 'newuser@test.local', password: 'password123' }),
    });
    expect(login.status).toBe(200);
    const loginJson = (await login.json()) as { ok?: boolean; token?: string };
    expect(loginJson.token).toBeDefined();

    const me = await app.request('http://localhost/v1/auth/me', {
      headers: { Authorization: `Bearer ${loginJson.token}` },
    });
    expect(me.status).toBe(200);
    const meJson = (await me.json()) as { user?: { email: string } };
    expect(meJson.user?.email).toBe('newuser@test.local');
  });

  it('login fails before verify', async () => {
    const app = createApp();
    await app.request('http://localhost/v1/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: 'u2@test.local', password: 'password123' }),
    });

    const login = await app.request('http://localhost/v1/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: 'u2@test.local', password: 'password123' }),
    });
    expect(login.status).toBe(403);
  });
});

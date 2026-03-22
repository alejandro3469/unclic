import { describe, it, expect } from 'vitest';
import { createApp } from './app.js';

describe('@unclic/ping', () => {
  it('GET /health', async () => {
    const app = createApp();
    const res = await app.request('http://localhost/health');
    expect(res.status).toBe(200);
    const j = (await res.json()) as { ok: boolean; service: string };
    expect(j.ok).toBe(true);
    expect(j.service).toBe('@unclic/ping');
  });
});

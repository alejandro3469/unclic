import { Hono } from 'hono';

export function createApp() {
  const app = new Hono();

  app.get('/health', (c) =>
    c.json({
      ok: true,
      service: '@unclic/ping',
      ts: new Date().toISOString(),
    })
  );

  return app;
}

import { serve } from '@hono/node-server';
import { createApp } from './app.js';

const port = Number(process.env.PORT || '3001');
const host = process.env.HOST || '0.0.0.0';

const app = createApp();

serve({ fetch: app.fetch, port, hostname: host }, (info) => {
  console.log(`@unclic/api listening on http://${info.address}:${info.port}`);
});

#!/usr/bin/env node
/**
 * Sirve deploy/ y config/ para ver la UI de configuraciones y el JSON en vivo.
 * Uso: node scripts/serve-config-ui.js
 * Abre http://localhost:9090/config-ui.html y http://localhost:9090/config/fastflow-config.json
 */
import { createServer } from 'http';
import { readFileSync, existsSync, statSync } from 'fs';
import { join, extname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = fileURLToPath(new URL('.', import.meta.url));
const root = join(__dirname, '..');
const deployDir = join(root, 'deploy');
const configPath = join(root, 'config', 'fastflow-config.json');
const port = Number(process.env.CONFIG_UI_PORT) || 9090;

const mime = {
  '.html': 'text/html',
  '.json': 'application/json',
  '.js': 'application/javascript',
  '.css': 'text/css',
};

const server = createServer((req, res) => {
  let pathname = req.url?.split('?')[0] || '/';
  if (pathname === '/') pathname = '/config-ui.html';
  if (pathname === '/config/fastflow-config.json' || pathname === '/config') {
    try {
      const data = readFileSync(configPath, 'utf8');
      res.writeHead(200, { 'Content-Type': 'application/json' });
      res.end(data);
      return;
    } catch (e) {
      res.writeHead(404);
      res.end('Config not found');
      return;
    }
  }
  const file = pathname.startsWith('/config/') ? join(root, pathname) : join(deployDir, pathname);
  if (!file.startsWith(deployDir) && !file.startsWith(join(root, 'config'))) {
    res.writeHead(403);
    res.end();
    return;
  }
  if (!existsSync(file) || !statSync(file).isFile()) {
    res.writeHead(404);
    res.end('Not found');
    return;
  }
  const ext = extname(file);
  res.writeHead(200, { 'Content-Type': mime[ext] || 'application/octet-stream' });
  res.end(readFileSync(file));
});

server.listen(port, () => {
  console.log(`Config UI: http://localhost:${port}/config-ui.html`);
  console.log(`Config JSON: http://localhost:${port}/config/fastflow-config.json`);
});

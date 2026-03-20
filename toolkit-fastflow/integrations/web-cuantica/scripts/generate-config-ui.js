#!/usr/bin/env node
/**
 * Genera deploy/config-ui.html con la configuración embebida para ver y administrar
 * todas las configuraciones desde un solo lugar. Ejecutar: node scripts/generate-config-ui.js
 */
import { readFileSync, writeFileSync } from 'fs';
import { fileURLToPath } from 'url';
import path from 'path';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const root = path.join(__dirname, '..');
const configPath = path.join(root, 'config', 'fastflow-config.json');
const outPath = path.join(root, 'deploy', 'config-ui.html');

let config = {};
try {
  config = JSON.parse(readFileSync(configPath, 'utf8'));
} catch (e) {
  console.error('No se pudo leer config/fastflow-config.json:', e.message);
  process.exit(1);
}

const sections = [
  { id: 'jenkins', title: 'Jenkins', keys: ['url', 'port', 'jobName', 'credentialsIdRegistry'] },
  { id: 'registry', title: 'Registry', keys: ['url', 'port', 'catalogPath', 'imageName', 'defaultTag', 'retentionDays'] },
  { id: 'pipeline', title: 'Pipeline', keys: ['imageName', 'imageTag', 'registry', 'mavenGoals'] },
  { id: 'terraform', title: 'Terraform', keys: ['namespace', 'image', 'baseUrl'] },
  { id: 'k8s', title: 'Kubernetes', keys: ['namespace', 'image', 'baseUrl', 'servicePort'] },
  { id: 'dashboard', title: 'Dashboard', keys: ['jenkinsUrl', 'registryUrl', 'appUrl'] },
];

function tableRows(section) {
  const data = config[section.id] || {};
  return section.keys.map(k => {
    const v = data[k];
    const val = v === undefined || v === null ? '' : String(v);
    return `        <tr><td>${k}</td><td><code>${escapeHtml(val)}</code></td></tr>`;
  }).join('\n');
}

function escapeHtml(s) {
  return s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

const html = `<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Configuraciones — FastFlow pos-online</title>
  <style>
    :root { --bg: #0f1115; --card: #181b22; --text: #e6e8ec; --accent: #4a9eff; --muted: #8b8f99; }
    * { box-sizing: border-box; }
    body { font-family: system-ui, sans-serif; background: var(--bg); color: var(--text); margin: 0; padding: 1.5rem; }
    h1 { font-size: 1.35rem; margin-bottom: 0.5rem; }
    .sub { color: var(--muted); font-size: 0.9rem; margin-bottom: 1.5rem; }
    .grid { display: grid; gap: 1rem; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); }
    .card { background: var(--card); border-radius: 8px; padding: 1rem; border: 1px solid rgba(255,255,255,.06); }
    .card h2 { font-size: 0.95rem; margin: 0 0 0.75rem; color: var(--accent); }
    table { width: 100%; border-collapse: collapse; font-size: 0.85rem; }
    th, td { text-align: left; padding: 0.35rem 0.5rem; vertical-align: top; }
    td:first-child { color: var(--muted); width: 40%; }
    code { background: rgba(255,255,255,.06); padding: 0.15rem 0.4rem; border-radius: 4px; font-size: 0.8rem; }
    .actions { margin-top: 1.5rem; display: flex; flex-wrap: wrap; gap: 0.75rem; }
    .actions a { color: var(--accent); text-decoration: none; }
    .actions a:hover { text-decoration: underline; }
    .file-path { font-size: 0.8rem; color: var(--muted); margin-top: 1rem; }
  </style>
</head>
<body>
  <h1>Configuraciones — un solo lugar</h1>
  <p class="sub">Implementación FastFlow para pos-online. Todas las tecnologías (Jenkins, Registry, Pipeline, Terraform, K8s, Dashboard) se administran desde el archivo de configuración central.</p>

  <div class="grid">
${sections.map(s => `    <div class="card">
      <h2>${s.title}</h2>
      <table>
${tableRows(s)}
      </table>
    </div>`).join('\n')}
  </div>

  <div class="actions">
    <a href="dashboard-demo-jenkins-registry.html">← Dashboard Jenkins y Registry</a>
    <a href="${config.dashboard?.jenkinsUrl || 'http://localhost:8080'}" target="_blank" rel="noopener">Abrir Jenkins</a>
    <a href="${config.dashboard?.registryUrl || 'http://localhost:5000/v2/_catalog'}" target="_blank" rel="noopener">Abrir Registry</a>
  </div>

  <p class="file-path">Para cambiar valores: editar <strong>config/fastflow-config.json</strong> y volver a ejecutar <code>node scripts/generate-config-ui.js</code> para regenerar esta página (o recargar si usas serve-config-ui).</p>
</body>
</html>
`;

writeFileSync(outPath, html);
console.log('Generado:', outPath);

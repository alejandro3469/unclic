#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

const ROOT = path.resolve(__dirname, '..');
const DOCS_DIR = path.join(ROOT, 'docs');
const OUTPUT = path.join(ROOT, 'integrations', 'web-cuantica', 'js', 'docs-catalog-data.js');

function normalize(value) {
  return String(value || '')
    .toLowerCase()
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .trim();
}

function walkMarkdown(dir, out) {
  const entries = fs.readdirSync(dir, { withFileTypes: true });
  for (const entry of entries) {
    const full = path.join(dir, entry.name);
    if (entry.isDirectory()) {
      walkMarkdown(full, out);
      continue;
    }
    if (entry.isFile() && entry.name.toLowerCase().endsWith('.md')) {
      out.push(full);
    }
  }
}

function titleFromFilename(relPath) {
  const base = path.basename(relPath, '.md');
  return base
    .replace(/[-_]+/g, ' ')
    .trim()
    .split(/\s+/)
    .map((part) => part.charAt(0).toUpperCase() + part.slice(1).toLowerCase())
    .join(' ');
}

function extractTitle(markdown, relPath) {
  const lines = markdown.split(/\r?\n/);
  for (const line of lines) {
    const trimmed = line.trim();
    if (trimmed.startsWith('# ')) {
      return trimmed.replace(/^#\s+/, '').trim();
    }
  }
  return titleFromFilename(relPath);
}

function extractSummary(markdown) {
  const lines = markdown.split(/\r?\n/);
  const paragraph = [];
  let inCode = false;

  for (const raw of lines) {
    const line = raw.trim();

    if (line.startsWith('```')) {
      inCode = !inCode;
      continue;
    }

    if (inCode) continue;

    if (!line) {
      if (paragraph.length) break;
      continue;
    }

    if (
      line.startsWith('#') ||
      line.startsWith('|') ||
      line.startsWith('>') ||
      line === '---' ||
      /^fecha\b/i.test(line) ||
      /^objetivo\b/i.test(line) ||
      /^principios\b/i.test(line) ||
      /^[-*]\s+/.test(line) ||
      /^\d+\.\s+/.test(line)
    ) {
      continue;
    }

    if (!paragraph.length && /:$/.test(line)) continue;

    paragraph.push(line);
    if (paragraph.join(' ').length > 220) break;
  }

  const joined = paragraph.join(' ').replace(/\s+/g, ' ').trim();
  if (!joined) return 'Documento operativo para estandarizar ejecucion, reducir riesgo y acelerar entrega.';
  if (joined.length <= 220) return joined;
  return `${joined.slice(0, 217).trim()}...`;
}

function classifyTopic(relPath, title, summary) {
  const blob = normalize(`${relPath} ${title} ${summary}`);

  if (/(kubernetes|\bk8s\b|helm|kubectl|pod|cluster|orquest)/.test(blob)) return 'Kubernetes';
  if (/(finops|cost|costo|ahorro|presupuesto|cloud spend)/.test(blob)) return 'FinOps';
  if (/(seguridad|security|hardening|rbac|oauth|audit|auditoria|cve|vulnerab|sast)/.test(blob)) return 'Seguridad';
  if (/(terraform|packer|\biac\b|arquitect|microserv|multi cloud|multicloud|diseno modular|dependenc)/.test(blob)) return 'Arquitectura';
  if (/(calidad|testing|test|cobertura|sonarqube|duplicidad|estandar|clean code|quality)/.test(blob)) return 'Calidad';
  if (/(jenkins|pipeline|jenkinsfile|git|webhook|shared librar|ci\/cd|\bcicd\b|multibranch|deploy)/.test(blob)) return 'Pipeline';
  if (/(citas|referenc|fuentes|glosario|webinar|landing|growth|newsletter|copywriting|curado|oficial)/.test(blob)) return 'Growth';
  if (/(incidente|postmortem|troubleshooting|logs|monitoreo|metric|operacion|sre|rollback|instalacion|entorno)/.test(blob)) return 'Operacion';

  return 'Operacion';
}

function classifyType(relPath, title) {
  const rel = normalize(relPath.replace(/\\/g, '/'));
  const text = normalize(`${relPath} ${title}`);

  if (rel.startsWith('manuals/')) return 'Manual';
  if (rel.startsWith('how-tos/')) return 'How-to';
  if (rel.startsWith('templates/')) return 'Plantilla';
  if (/(citas|referenc|fuentes|glosario|catalogo|curado|home-)/.test(text)) return 'Referencia';
  if (/(manual)/.test(text)) return 'Manual';
  if (/(how to|how-to)/.test(text)) return 'How-to';
  if (/(guia)/.test(text)) return 'Guia';

  return 'Guia';
}

function classifyAudience(topic, type, relPath, title) {
  const text = normalize(`${relPath} ${title}`);

  if (type === 'Plantilla') return 'Ejecutivo';
  if (topic === 'Growth' || topic === 'FinOps') return 'Ejecutivo';
  if (type === 'How-to') return 'Desarrollador';
  if (type === 'Manual' && /(usuario final|desarrollador)/.test(text)) return 'Desarrollador';
  if (topic === 'Pipeline' || topic === 'Calidad') return 'Desarrollador';
  if (topic === 'Arquitectura' || topic === 'Seguridad' || topic === 'Kubernetes' || topic === 'Operacion') return 'Arquitecto/SRE';
  if (type === 'Manual') return 'Arquitecto/SRE';

  return 'Arquitecto/SRE';
}

function classifySource(topic, type, relPath, title) {
  const text = normalize(`${relPath} ${title}`);

  if (type === 'Plantilla') return 'Plantilla homologada';
  if (/(oficial|referenc|citas|fuentes|curado)/.test(text)) return 'Fuentes verificadas';
  if (topic === 'Growth') return 'Libros y criterios curados';
  if (type === 'How-to') return 'Runbook operativo';
  if (type === 'Manual') return 'Manual interno homologado';
  if (/(jenkins|kubernetes|terraform|docker|helm|hashicorp)/.test(text)) return 'Documentacion oficial + FastFlow';

  return 'Playbooks FastFlow';
}

function buildKeywords(title, summary, relPath) {
  const stopwords = new Set([
    'para', 'como', 'desde', 'sobre', 'este', 'esta', 'estos', 'estas', 'fastflow', 'guia', 'manual', 'toolkit',
    'with', 'from', 'that', 'this', 'your', 'del', 'las', 'los', 'una', 'uno', 'the', 'and', 'por', 'con'
  ]);

  const text = normalize(`${title} ${summary} ${relPath}`)
    .replace(/[^a-z0-9\s\-]/g, ' ')
    .replace(/\s+/g, ' ')
    .trim();

  const seen = new Set();
  const result = [];

  for (const token of text.split(' ')) {
    if (!token || token.length < 4 || stopwords.has(token)) continue;
    if (seen.has(token)) continue;
    seen.add(token);
    result.push(token);
    if (result.length >= 18) break;
  }

  return result.join(' ');
}

function toWebHref(relPath) {
  const normalizedRel = relPath.replace(/\\/g, '/');
  return `../../docs/${normalizedRel}`;
}

function buildItem(filePath) {
  const rel = path.relative(DOCS_DIR, filePath);
  const markdown = fs.readFileSync(filePath, 'utf8');
  const stat = fs.statSync(filePath);

  const title = extractTitle(markdown, rel);
  const summary = extractSummary(markdown);
  const topic = classifyTopic(rel, title, summary);
  const type = classifyType(rel, title);
  const audience = classifyAudience(topic, type, rel, title);
  const source = classifySource(topic, type, rel, title);
  const keywords = buildKeywords(title, summary, rel);
  const updated = new Date(stat.mtimeMs).toISOString().slice(0, 10);

  return {
    title,
    summary,
    topic,
    type,
    audience,
    updated,
    href: toWebHref(rel),
    source,
    keywords
  };
}

function main() {
  if (!fs.existsSync(DOCS_DIR)) {
    throw new Error(`No existe el directorio de docs: ${DOCS_DIR}`);
  }

  const files = [];
  walkMarkdown(DOCS_DIR, files);

  const items = files.map(buildItem);

  items.sort((a, b) => {
    const byDate = String(b.updated).localeCompare(String(a.updated));
    if (byDate !== 0) return byDate;
    return String(a.title).localeCompare(String(b.title), 'es');
  });

  const payload = {
    generatedAt: new Date().toISOString(),
    count: items.length,
    items
  };

  const output = [
    '/* AUTO-GENERATED FILE. DO NOT EDIT MANUALLY. */',
    '/* Generated by toolkit-fastflow/scripts/build-docs-catalog.js */',
    '(function (global) {',
    `  global.WCXDocsCatalogMeta = ${JSON.stringify({ generatedAt: payload.generatedAt, count: payload.count }, null, 2)};`,
    `  global.WCXDocsCatalogData = ${JSON.stringify(payload.items, null, 2)};`,
    '})(window);',
    ''
  ].join('\n');

  fs.writeFileSync(OUTPUT, output, 'utf8');
  console.log(`Docs catalog generated: ${OUTPUT}`);
  console.log(`Items: ${payload.count}`);
}

main();

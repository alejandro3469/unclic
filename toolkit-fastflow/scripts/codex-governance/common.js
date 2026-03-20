#!/usr/bin/env node
'use strict';

const fs = require('fs');
const path = require('path');

function parseArgs(argv) {
  const args = {
    positional: []
  };

  for (let i = 2; i < argv.length; i += 1) {
    const token = argv[i];
    if (!token.startsWith('--')) {
      args.positional.push(token);
      continue;
    }

    const [rawKey, inlineValue] = token.split('=', 2);
    const key = rawKey.slice(2);
    if (!key) continue;

    if (inlineValue !== undefined) {
      args[key] = inlineValue;
      continue;
    }

    const next = argv[i + 1];
    if (next && !next.startsWith('--')) {
      args[key] = next;
      i += 1;
      continue;
    }

    args[key] = true;
  }

  return args;
}

function loadEnvFile(filePath) {
  if (!filePath || !fs.existsSync(filePath)) {
    return;
  }

  const content = fs.readFileSync(filePath, 'utf8');
  const lines = content.split(/\r?\n/);

  for (const rawLine of lines) {
    const line = rawLine.trim();
    if (!line || line.startsWith('#')) continue;

    const idx = line.indexOf('=');
    if (idx <= 0) continue;

    const key = line.slice(0, idx).trim();
    if (!key) continue;

    let value = line.slice(idx + 1).trim();

    if (
      (value.startsWith('"') && value.endsWith('"')) ||
      (value.startsWith("'") && value.endsWith("'"))
    ) {
      value = value.slice(1, -1);
    }

    if (process.env[key] === undefined) {
      process.env[key] = value;
    }
  }
}

function requireFetch() {
  if (typeof fetch !== 'function') {
    throw new Error('This script requires Node.js 18+ (global fetch is missing).');
  }
}

function formatDateUTC(date) {
  return date.toISOString().slice(0, 10);
}

function daysAgoUTC(days) {
  const d = new Date();
  d.setUTCHours(0, 0, 0, 0);
  d.setUTCDate(d.getUTCDate() - days);
  return formatDateUTC(d);
}

function resolveAbsolutePath(p) {
  return path.isAbsolute(p) ? p : path.resolve(process.cwd(), p);
}

function ensureDir(dirPath) {
  fs.mkdirSync(dirPath, { recursive: true });
}

function writeJson(filePath, data) {
  fs.writeFileSync(filePath, `${JSON.stringify(data, null, 2)}\n`, 'utf8');
}

function nonEmpty(value) {
  if (value === undefined || value === null) return undefined;
  const trimmed = String(value).trim();
  return trimmed ? trimmed : undefined;
}

function buildHeaders(config) {
  const apiKey = nonEmpty(config.apiKey);
  if (!apiKey) {
    throw new Error('Missing API key. Set OPENAI_API_KEY or CODEX_API_KEY.');
  }

  const headers = {
    Authorization: `Bearer ${apiKey}`,
    'Content-Type': 'application/json'
  };

  if (nonEmpty(config.organizationId)) {
    headers['OpenAI-Organization'] = config.organizationId;
  }

  if (nonEmpty(config.projectId)) {
    headers['OpenAI-Project'] = config.projectId;
  }

  return headers;
}

function getPath(obj, rawPath) {
  if (!obj || !rawPath) return undefined;
  const parts = String(rawPath).split('.').filter(Boolean);
  let current = obj;

  for (const part of parts) {
    if (typeof current !== 'object' || current === null) {
      return undefined;
    }
    current = current[part];
  }

  return current;
}

async function fetchJson(url, headers) {
  const response = await fetch(url, {
    method: 'GET',
    headers
  });

  const text = await response.text();
  let data;

  try {
    data = text ? JSON.parse(text) : {};
  } catch (err) {
    throw new Error(`Non-JSON response from ${url}: ${text.slice(0, 300)}`);
  }

  if (!response.ok) {
    const message = data.error?.message || data.message || JSON.stringify(data).slice(0, 300);
    throw new Error(`Request failed (${response.status}) for ${url}: ${message}`);
  }

  return data;
}

function parseIntSafe(value, fallback) {
  const n = Number.parseInt(String(value), 10);
  return Number.isFinite(n) ? n : fallback;
}

function boolEnv(value, fallback = false) {
  if (value === undefined || value === null || value === '') return fallback;
  const normalized = String(value).trim().toLowerCase();
  if (['1', 'true', 'yes', 'on'].includes(normalized)) return true;
  if (['0', 'false', 'no', 'off'].includes(normalized)) return false;
  return fallback;
}

module.exports = {
  parseArgs,
  loadEnvFile,
  requireFetch,
  formatDateUTC,
  daysAgoUTC,
  resolveAbsolutePath,
  ensureDir,
  writeJson,
  nonEmpty,
  buildHeaders,
  getPath,
  fetchJson,
  parseIntSafe,
  boolEnv
};

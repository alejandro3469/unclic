#!/usr/bin/env node
'use strict';

const path = require('path');
const {
  parseArgs,
  loadEnvFile,
  requireFetch,
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
} = require('./common');

function resolveConfig(args) {
  const defaultEnvPath = path.resolve(__dirname, '../../integrations/codex-governance/.env');
  const envPath = resolveAbsolutePath(args.env || defaultEnvPath);
  loadEnvFile(envPath);

  const endpoint = nonEmpty(args.endpoint || process.env.CODEX_ANALYTICS_ENDPOINT);
  const workspaceId = nonEmpty(args.workspace || process.env.CODEX_WORKSPACE_ID);
  const workspaceParam = nonEmpty(process.env.CODEX_ANALYTICS_WORKSPACE_PARAM || 'workspace_id');

  const fromDate = nonEmpty(args.from || process.env.CODEX_ANALYTICS_FROM) || daysAgoUTC(1);
  const toDate = nonEmpty(args.to || process.env.CODEX_ANALYTICS_TO) || fromDate;

  const groupBy = nonEmpty(args.groupBy || process.env.CODEX_ANALYTICS_GROUP_BY);
  const groupByParam = nonEmpty(process.env.CODEX_ANALYTICS_GROUP_BY_PARAM || 'group_by');

  const fromParam = nonEmpty(process.env.CODEX_ANALYTICS_FROM_PARAM || 'start_date');
  const toParam = nonEmpty(process.env.CODEX_ANALYTICS_TO_PARAM || 'end_date');
  const limitParam = nonEmpty(process.env.CODEX_ANALYTICS_LIMIT_PARAM || 'limit');
  const cursorParam = nonEmpty(process.env.CODEX_ANALYTICS_CURSOR_PARAM || 'cursor');
  const nextCursorField = nonEmpty(process.env.CODEX_ANALYTICS_NEXT_CURSOR_FIELD || 'next_cursor');

  const limit = parseIntSafe(args.limit || process.env.CODEX_ANALYTICS_LIMIT || '200', 200);
  const maxPages = parseIntSafe(args.maxPages || process.env.CODEX_ANALYTICS_MAX_PAGES || '500', 500);

  const outRoot = resolveAbsolutePath(
    args.out || process.env.CODEX_ANALYTICS_OUTPUT_DIR || 'toolkit-fastflow/integrations/codex-governance/exports/analytics'
  );

  const stopOnEmpty = boolEnv(process.env.CODEX_ANALYTICS_STOP_ON_EMPTY, true);

  return {
    envPath,
    endpoint,
    workspaceId,
    workspaceParam,
    fromDate,
    toDate,
    groupBy,
    groupByParam,
    fromParam,
    toParam,
    limit,
    limitParam,
    cursorParam,
    nextCursorField,
    maxPages,
    outRoot,
    stopOnEmpty,
    dryRun: Boolean(args['dry-run']),
    headers: buildHeaders({
      apiKey: process.env.OPENAI_API_KEY || process.env.CODEX_API_KEY,
      organizationId: process.env.OPENAI_ORGANIZATION_ID,
      projectId: process.env.OPENAI_PROJECT_ID
    })
  };
}

function validateConfig(config) {
  if (!config.endpoint) {
    throw new Error('Missing CODEX_ANALYTICS_ENDPOINT. Set it in .env or --endpoint.');
  }

  if (!config.workspaceId) {
    throw new Error('Missing CODEX_WORKSPACE_ID. Set it in .env or --workspace.');
  }
}

function buildUrl(config, cursor) {
  const url = new URL(config.endpoint);
  url.searchParams.set(config.fromParam, config.fromDate);
  url.searchParams.set(config.toParam, config.toDate);
  url.searchParams.set(config.limitParam, String(config.limit));
  url.searchParams.set(config.workspaceParam, config.workspaceId);

  if (config.groupBy) {
    url.searchParams.set(config.groupByParam, config.groupBy);
  }

  if (cursor) {
    url.searchParams.set(config.cursorParam, cursor);
  }

  return url.toString();
}

function normalizeCount(payload) {
  if (Array.isArray(payload?.data)) return payload.data.length;
  if (Array.isArray(payload?.results)) return payload.results.length;
  if (Array.isArray(payload?.items)) return payload.items.length;
  return undefined;
}

async function run() {
  requireFetch();

  const args = parseArgs(process.argv);
  const config = resolveConfig(args);
  validateConfig(config);

  const runStamp = new Date().toISOString().replace(/[.:]/g, '-');
  const outDir = path.join(config.outRoot, `${config.fromDate}_to_${config.toDate}`, runStamp);
  const pagesDir = path.join(outDir, 'pages');

  ensureDir(pagesDir);

  const manifest = {
    stream: 'analytics',
    created_at: new Date().toISOString(),
    env_file: config.envPath,
    endpoint: config.endpoint,
    workspace_id: config.workspaceId,
    from_date: config.fromDate,
    to_date: config.toDate,
    group_by: config.groupBy || null,
    limit: config.limit,
    pages: 0,
    records: 0,
    output_dir: outDir
  };

  if (config.dryRun) {
    const previewUrl = buildUrl(config);
    manifest.preview_url = previewUrl;
    writeJson(path.join(outDir, 'manifest.json'), manifest);
    console.log(`Dry run ready: ${previewUrl}`);
    return;
  }

  let cursor;
  for (let page = 1; page <= config.maxPages; page += 1) {
    const url = buildUrl(config, cursor);
    const payload = await fetchJson(url, config.headers);
    const pageFile = path.join(pagesDir, `page-${String(page).padStart(4, '0')}.json`);

    writeJson(pageFile, payload);

    const count = normalizeCount(payload);
    if (typeof count === 'number') {
      manifest.records += count;
      if (config.stopOnEmpty && count === 0) {
        manifest.pages = page;
        break;
      }
    }

    manifest.pages = page;

    const nextCursor = getPath(payload, config.nextCursorField);
    if (!nextCursor) {
      break;
    }

    cursor = String(nextCursor);
  }

  writeJson(path.join(outDir, 'manifest.json'), manifest);
  console.log(`Analytics export complete: ${manifest.pages} page(s), ${manifest.records} record(s)`);
  console.log(`Saved under: ${outDir}`);
}

run().catch((err) => {
  console.error(`ERROR: ${err.message}`);
  process.exitCode = 1;
});

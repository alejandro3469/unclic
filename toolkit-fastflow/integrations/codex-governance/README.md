# Codex Governance Starter

This folder implements the recommended enterprise governance pattern for Codex:

1. Analytics Dashboard for quick daily visibility.
2. Analytics API for structured metric exports.
3. Compliance API for auditable activity logs.

## Files

- `toolkit-fastflow/scripts/codex-governance/fetch-analytics.js`
- `toolkit-fastflow/scripts/codex-governance/fetch-compliance.js`
- `toolkit-fastflow/integrations/codex-governance/.env.example`
- `toolkit-fastflow/integrations/codex-governance/exports/` (generated)

## Setup

1. Copy environment template:

```bash
cp toolkit-fastflow/integrations/codex-governance/.env.example toolkit-fastflow/integrations/codex-governance/.env
```

2. Fill values in `.env`:
- `OPENAI_API_KEY`
- `CODEX_WORKSPACE_ID`
- `CODEX_ANALYTICS_ENDPOINT`
- `CODEX_COMPLIANCE_ENDPOINT`

3. Optional: tune query parameter names if your endpoint uses different names.

## Run Exports

### Analytics API

```bash
node toolkit-fastflow/scripts/codex-governance/fetch-analytics.js --from 2026-03-09 --to 2026-03-09
```

### Compliance API

```bash
node toolkit-fastflow/scripts/codex-governance/fetch-compliance.js --from 2026-03-09 --to 2026-03-09
```

### Dry run (URL/params check only)

```bash
node toolkit-fastflow/scripts/codex-governance/fetch-analytics.js --dry-run
node toolkit-fastflow/scripts/codex-governance/fetch-compliance.js --dry-run
```

## Output Layout

Exports are saved as dated snapshots:

- `toolkit-fastflow/integrations/codex-governance/exports/analytics/<from>_to_<to>/<run-timestamp>/pages/page-0001.json`
- `toolkit-fastflow/integrations/codex-governance/exports/compliance/<from>_to_<to>/<run-timestamp>/pages/page-0001.json`
- Each run includes `manifest.json` with endpoint, date range, page count, and record count.

## Schedule (Daily)

Run at 06:10 local time for previous-day data:

```cron
10 6 * * * cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master && node toolkit-fastflow/scripts/codex-governance/fetch-analytics.js >> /tmp/codex-analytics.log 2>&1
20 6 * * * cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master && node toolkit-fastflow/scripts/codex-governance/fetch-compliance.js >> /tmp/codex-compliance.log 2>&1
```

## Operating Model

- Dashboard: admins review daily usage/adoption and code-review trends.
- Analytics exports: feed BI warehouse dashboards (adoption, power users, cost tracking).
- Compliance exports: route to SIEM/eDiscovery pipelines for audit and investigations.

## Notes

- Scripts require Node.js 18+ (global `fetch`).
- Date defaults to previous UTC day when `--from/--to` are omitted.
- Endpoint path and query parameter names are configurable to support workspace-specific API contracts.

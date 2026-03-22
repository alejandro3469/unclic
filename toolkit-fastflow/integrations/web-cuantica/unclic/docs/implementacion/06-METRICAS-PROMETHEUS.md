# Métricas mínimas Prometheus (`/metrics`)

## Objetivo

- Scraping desde Prometheus cada 15–30 s.
- Alertas básicas: API caída, error rate alto.

## En Node (API)

1. Dependencia:

   ```bash
   cd services/api && npm install prom-client
   ```

2. Exponer `GET /metrics` **solo red interna** o detrás de firewall; no hace falta CORS público.

3. Métricas útiles de primer nivel:

   - `http_requests_total` con label `method`, `route`, `status`.
   - `process_cpu_user_seconds_total` (viene del cliente default).
   - Histograma de latencia si quieres SLO.

4. Ejemplo de registro (pseudo; ver [prom-client README](https://github.com/siimon/prom-client)):

   ```ts
   import client from 'prom-client';
   client.collectDefaultMetrics();
   const httpCounter = new client.Counter({
     name: 'http_requests_total',
     help: 'HTTP requests',
     labelNames: ['method', 'route', 'status'],
   });
   // middleware Hono: incrementar por request
   ```

## Prometheus scrape

```yaml
scrape_configs:
  - job_name: unclic-api
    static_configs:
      - targets: ['api.internal:3001']
    metrics_path: /metrics
```

## Grafana

Datasource Prometheus → dashboard import ID comunitario o uno mínimo con `up` y `rate(http_requests_total[5m])`.

## Sin guesswork

No inventes puertos: el tuyo es el de `PORT` en `services/api` (3001 por defecto).

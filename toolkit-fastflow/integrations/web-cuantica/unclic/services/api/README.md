# @unclic/api

Microservicio HTTP (Hono) para UnClic:

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/health` | Liveness (JSON). |
| GET | `/v1/orchestration/health` | Agrega salud de esta API + microservicio **ping** si `PING_SERVICE_URL` está definida. |
| GET | `/v1/info` | Metadatos mínimos. |
| POST | `/v1/leads/email` | Mismo contrato que `app/api/lead-email` en Next (correo + honeypot `website`). |

Contrato OpenAPI (plantilla): [openapi/openapi.yaml](openapi/openapi.yaml).

## Variables de entorno

- `PORT` (default `3001`), `HOST` (default `0.0.0.0`)
- `CORS_ORIGINS` — lista separada por comas o `*`. En desarrollo: `http://localhost:3002`
- `GMAIL_SMTP_USER`, `GMAIL_SMTP_APP_PASSWORD` — ver `.env.example` en la raíz del proyecto UnClic
- `LEAD_NOTIFY_TO`, `LEAD_FROM_NAME`

## Comandos

```bash
npm ci
npm run dev    # tsx watch
npm test
npm run build && npm start
```

## Docker

Desde la raíz de `unclic`:

```bash
docker compose up -d --build api
```

## Documentación

- [docs/MICROSERVICIOS-Y-DOCKER.md](../../docs/MICROSERVICIOS-Y-DOCKER.md)
- [docs/EXTRACT-REPO-UNClic-AISLADO.md](../../docs/EXTRACT-REPO-UNClic-AISLADO.md)

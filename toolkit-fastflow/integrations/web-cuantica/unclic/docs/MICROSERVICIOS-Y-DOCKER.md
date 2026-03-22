# Microservicios, Docker y sitio estático UnClic

## Por qué existe `services/api`

El sitio Next usa **`output: 'export'`** (HTML estático en `out/`). En ese modo **no hay Route Handlers** en producción: `app/api/lead-email` solo sirve en `next dev`.

**Solución:** el formulario de leads usa:

- **`NEXT_PUBLIC_UNCLIC_API_URL` vacío** → en desarrollo, `POST /api/lead-email` (Next).
- **`NEXT_PUBLIC_UNCLIC_API_URL=https://api.tu-dominio.com`** → el navegador llama al microservicio **`POST .../v1/leads/email`** (CORS habilitado).

## Desarrollo local

Terminal 1 — web:

```bash
npm run dev
```

Terminal 2 — API:

```bash
npm run dev:api
```

`.env.local` en la raíz de UnClic:

```env
NEXT_PUBLIC_UNCLIC_API_URL=http://localhost:3001
```

La API debe permitir el origen del front:

```env
# al ejecutar la API (export en la misma shell o .env cargado por tu herramienta)
CORS_ORIGINS=http://localhost:3002,http://127.0.0.1:3002
```

(Gmail SMTP igual que en [LEADS-CORREO-GMAIL-SMTP.md](LEADS-CORREO-GMAIL-SMTP.md).)

## Pruebas

```bash
npm run test:api
```

## Orquestación (API + ping)

Con **dos** servicios en Compose, la API llama al ping vía `PING_SERVICE_URL` (por defecto `http://ping:3010` en la red Docker).

```bash
docker compose up -d --build
curl -s http://localhost:3001/v1/orchestration/health
```

Desarrollo local sin Docker: `npm run dev:ping` (3010) + `PING_SERVICE_URL=http://localhost:3010 npm run dev:api`.

Documentación de patrones: [ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md](ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md).

## Docker

```bash
# Variables: exporta GMAIL_* o usa archivo:
docker compose --env-file .env.local up -d --build
```

## Nginx (ejemplo)

Tras `next build`, sirves `out/` en un server block y proxéas la API en otro `location` o subdominio `api.`.

## Voz OSS (`services/oss-voice`)

Stub Python (FastAPI) para **STT/TTS/WebSocket** con CORS hacia el front. Puerto **3005**. Ver `npm run dev:oss-voice` y [UI-OSS-MEDIA-ELEVENLABS-STYLE.md](UI-OSS-MEDIA-ELEVENLABS-STYLE.md).

## Siguientes microservicios

Añade carpetas bajo `services/` (cada una con su `package.json` o `requirements.txt` y tests). Referencia de capas: [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md).

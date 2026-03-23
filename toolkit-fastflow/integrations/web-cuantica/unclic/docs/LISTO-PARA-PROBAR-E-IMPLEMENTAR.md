# Listo para probar e implementar — UnClic

**Objetivo:** un solo lugar con **cómo validar todo el proyecto** y **qué te falta por implementar** (con enlace al plan detallado).

---

## 1. Verificación automática (desde la raíz `unclic/`)

| Comando | Qué hace |
|--------|-----------|
| `npm run verify` | **Lint** + **TypeScript** + **build Next** (`out/`) + **tests API + ping** |
| `npm run lint` | ESLint (Next + TypeScript); requiere `eslint.config.mjs` en la raíz |
| `npm run typecheck` | `tsc --noEmit` del front |
| `npm run build` | Export estático → carpeta `out/` |
| `npm run test:services` | Vitest en `services/api` y `services/ping` |

**CI (GitHub):** `.github/workflows/ci.yml` — job **frontend** (lint, typecheck, build) + jobs **api** y **ping**.

---

## 2. Prueba manual rápida (E0)

1. `npm install` y `npm run dev` → [http://localhost:3002](http://localhost:3002) (home y rutas principales).
2. En otra terminal: `npm run dev:api` → API en el puerto configurado en `services/api` (típico `3001`).
3. En `.env.local` (raíz UnClic): `NEXT_PUBLIC_UNCLIC_API_URL=http://localhost:3001` si quieres leads/registro contra API local.
4. En la shell del API: `CORS_ORIGINS=http://localhost:3002` (o según `.env.example`).
5. **Health:** `curl -s http://localhost:3001/health` → JSON con `ok: true`.
6. **Lead / SMTP:** formulario de contacto → éxito o **503** claro si faltan variables Gmail (ver [LEADS-CORREO-GMAIL-SMTP.md](LEADS-CORREO-GMAIL-SMTP.md)).
7. **Portal:** flujo login/registro según [PORTAL-AUTH-JWT-EMAIL.md](PORTAL-AUTH-JWT-EMAIL.md) y variables en `.env.example`.

---

## 3. Qué implementar después (resumen)

La **matriz completa** (herramienta → estado → siguiente paso atómico) está en:

**[PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md](PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md)**

### Prioridades típicas “siguiente sprint”

| Área | Estado doc | Acción corta |
|------|------------|----------------|
| Gmail SMTP + leads en prod | En curso | Variables en servidor; probar POST real |
| `CORS_ORIGINS` producción | En curso | Dominio final `https://unclic.consulting` (sin barra final) |
| Stripe webhook | Pendiente | `POST /v1/webhooks/stripe` + firma + tests |
| Facturapi | Pendiente | `FACTURAPI_API_KEY` + borrador desde API |
| Despliegue API + `out/` | E1 en plan | Nginx/Caddy + TLS + rebuild front con `NEXT_PUBLIC_*` |

### Billing / OSS alternativos (no bloquean la web)

Polar, Lago, Hyperswitch, etc. → ver misma matriz en el plan granular; son **PoC por necesidad de producto**, no requisito del landing.

### Infra y tooling opcional

Coolify, ERPNext, Keycloak, NATS, Prometheus/Grafana, capa L19 (Ollama, n8n…) → filas **Pendiente** en la matriz; seguir **un solo “próximo paso atómico”** por ítem.

---

## 4. Lint: avisos conocidos (no bloquean `verify`)

- Varios `<img>` en demos legacy (`hero125`, `ecommerce-navbar1`, `hero154`): mejora futura → `next/image` + `remotePatterns` en `next.config.js`.
- `ollama-chat-panel` — dependencia de `useCallback`: revisar si incorporas `input` en deps o estabilizas con ref.
- `carousel.tsx` — `canScrollPrev` / `canScrollNext` sin usar (shadcn default).

---

## 5. Relacionado

- [MICROSERVICIOS-Y-DOCKER.md](MICROSERVICIOS-Y-DOCKER.md) — Compose y puertos.
- [UI-SHADCN-CONSISTENCIA-UNClic.md](UI-SHADCN-CONSISTENCIA-UNClic.md) — UI solo shadcn + bloques propios.

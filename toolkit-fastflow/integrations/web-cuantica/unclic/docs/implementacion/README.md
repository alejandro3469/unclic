# Implementación UnClic — guías y ejemplos (producción)

**Propósito:** lo que **no está cableado aún** en el repo tiene aquí **patrones reales** (sin adivinar): pasos, variables, fragmentos de infra y **archivos `.example` copiables**. Lo que **sí** está en código hoy se enlaza a rutas concretas.

**Estado del código actual (referencia rápida)**

| Pieza | Ubicación en repo |
|-------|-------------------|
| API Hono | `services/api/src/app.ts`, `services/api/src/server.ts` |
| OpenAPI | `services/api/openapi/openapi.yaml` |
| Compose base | `docker-compose.yml` (api + ping; Postgres comentado) |
| Leads + CORS | [LEADS-CORREO-GMAIL-SMTP.md](../LEADS-CORREO-GMAIL-SMTP.md), [MICROSERVICIOS-Y-DOCKER.md](../MICROSERVICIOS-Y-DOCKER.md) |
| Plan y matriz | [PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md](../PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md) |

---

## Índice de guías (orden sugerido para producción)

| # | Documento | Cuándo leerlo |
|---|-------------|----------------|
| 0 | [OPERACION-PRODUCCION-CHECKLIST.md](OPERACION-PRODUCCION-CHECKLIST.md) | Antes de declarar “en prod”: TLS, secretos, backups, observabilidad mínima. |
| 1 | [01-CORS-STATIC-API-TLS.md](01-CORS-STATIC-API-TLS.md) | Sitio `out/` + API en subdominio; CORS exacto; proxy (Caddy/Nginx). |
| 2 | [02-STRIPE-WEBHOOK.md](02-STRIPE-WEBHOOK.md) | Webhook firmado, idempotencia, tabla o cola; **no** en Next estático. |
| 3 | [03-FACTURAPI-CFDI.md](03-FACTURAPI-CFDI.md) | Tras pago confirmado; claves solo en servidor; manejo de errores PAC. |
| 4 | [04-DOCKER-COMPOSE-STACK-OPCIONAL.example.yml](04-DOCKER-COMPOSE-STACK-OPCIONAL.example.yml) | Postgres, LavinMQ, (opcional) servicios L19 — **no** fusionar ciegamente; copiar servicios que uses. |
| 5 | [05-COLA-AMQP-WORKER.md](05-COLA-AMQP-WORKER.md) | Desacople webhook → worker AMQP (RabbitMQ o LavinMQ). |
| 6 | [06-METRICAS-PROMETHEUS.md](06-METRICAS-PROMETHEUS.md) | `/metrics` + scrape + alertas mínimas. |
| 7 | [07-PROXY-LLM-OLLAMA-DMR.md](07-PROXY-LLM-OLLAMA-DMR.md) | L19 detrás de red privada; timeouts; sin PII sin política. |
| 8 | [08-KEYCLOAK-OIDC-FRENTE.md](08-KEYCLOAK-OIDC-FRENTE.md) | OIDC delante de rutas sensibles; sin inventar realm — seguir doc Keycloak. |
| 9 | [09-ERPNEXT-API-SYNC.md](09-ERPNEXT-API-SYNC.md) | Patrón Frappe REST; límites de lo que **no** automatizamos sin tu empresa en ERP. |
| 10 | [10-DEVTOOLS-ALTERNATIVAS-2026.md](10-DEVTOOLS-ALTERNATIVAS-2026.md) | Biome, Bun, Deno, Turso, Astro, Ruff, Zed, Continue — curación dev.to; PoC vs stack actual Node+Next. |
| 11 | [11-EMPRESA-OSS-VATES-REFERENCIA.md](11-EMPRESA-OSS-VATES-REFERENCIA.md) | Vates 2025: política 3 niveles, escala ~100 pers./infra 2019, sustituciones (Plane, Metabase, BBB, Docusaurus), riesgos (Formbricks SSO), radar GPU/Open WebUI, Zammad/Marp observación, stack estable. |
| 12 | [12-37signals-REWORK-LEAVING-CLOUD-DESARROLLADOR.md](12-37signals-REWORK-LEAVING-CLOUD-DESARROLLADOR.md) | **Referencia dev (si o si):** REWORK *Leaving the Cloud* + post DHH; cuándo nube vs owned, mito “menos ops”, lock-in, TCO, transición lenta; contraste con doc 11. |
| 13 | [13-GRIST-HOJA-RELACIONAL-SELF-HOST.md](13-GRIST-HOJA-RELACIONAL-SELF-HOST.md) | Grist: doc Docker oficial + tutorial Lawrence Systems (auth, permisos, API, widgets); checklist prod; enlace Vates 2025. |
| 14 | [14-NETALERTX-RED-DESCOBERTA-MONITOR.md](14-NETALERTX-RED-DESCOBERTA-MONITOR.md) | NetAlertX: descubrimiento LAN, Docker, plugins (UniFi, Nmap), alertas; vídeo + foro Lawrence; encaje planes 1–4. |
| 15 | [15-RUSTDESK-REMOTO-SELF-HOST.md](15-RUSTDESK-REMOTO-SELF-HOST.md) | RustDesk: remoto OSS self-host vs TeamViewer/AnyDesk; Docker, seguridad, clientes; Lawrence Systems + doc oficial; planes 1–4. |
| 16 | [16-L19-OLLAMA-DOCKER-Y-PROMPTFOO.md](16-L19-OLLAMA-DOCKER-Y-PROMPTFOO.md) | **L19 laboratorio:** Ollama vía [16-DOCKER-COMPOSE-L19-OLLAMA.example.yml](16-DOCKER-COMPOSE-L19-OLLAMA.example.yml); eval **PromptFoo** (contenedor perfil `eval` o `npx` en host); OpenViking+LiteLLM→Ollama; fusión con compose principal. |

---

## Carpeta `ejemplos/` (copiar al API cuando toque)

| Archivo | Uso |
|---------|-----|
| [ejemplos/stripe-webhook.app-snippet.example.ts](ejemplos/stripe-webhook.app-snippet.example.ts) | Pegar rutas en `createApp()` o módulo aparte; requiere `stripe` en `package.json`. |
| [ejemplos/amqp-publish-snippet.example.ts](ejemplos/amqp-publish-snippet.example.ts) | Publicar evento tras webhook; requiere `amqplib`. |
| [ejemplos/llm-proxy-snippet.example.ts](ejemplos/llm-proxy-snippet.example.ts) | Proxy mínimo a Ollama/DMR con allowlist de modelo. |
| [ejemplos/promptfooconfig.l19-ollama.example.yaml](ejemplos/promptfooconfig.l19-ollama.example.yaml) | **PromptFoo** contra Ollama (`ollama:chat:llama3.2:1b`); usar con [16-L19-OLLAMA-DOCKER-Y-PROMPTFOO.md](16-L19-OLLAMA-DOCKER-Y-PROMPTFOO.md). |

**Regla:** los `.example.ts` **no** se importan desde el build; son plantilla. Tras copiar, añade dependencias, tests y OpenAPI.

---

## Lo que **no** está en este repo (y por qué)

- **Credenciales** (Stripe, Facturapi, Gmail, ERPNext): solo en **servidor** o secret manager; nunca en Git ni `NEXT_PUBLIC_*`.
- **Dominio y TLS**: dependen de tu DNS y CA; aquí solo **patrones** de proxy.
- **ERPNext completo**: es otro stack (Frappe); esta documentación cubre **contrato HTTP** hacia tu API UnClic, no instalar ERP por ti.

Tras cada entrega real, actualiza [PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md](../PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md) (matriz §1).

---

## Estrategia (fuera de snippets)

- [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](../CURACION-DUPLICADOS-Y-ELECCION-UNClic.md) — qué OSS elegir cuando varias hacen lo mismo; default UnClic.  
- [PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md](../PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md) — cuatro formas de integrar el catálogo en servidor propio (UnClic + suite trabajo + plataforma dev + negocio on-prem).

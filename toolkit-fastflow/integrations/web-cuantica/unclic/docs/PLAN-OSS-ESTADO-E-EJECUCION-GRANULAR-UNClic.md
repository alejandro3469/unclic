# Estado OSS + ejecución granular — UnClic Consulting

**Propósito:** una sola fuente para **qué ya está integrado en el repo**, **qué está en curso** y **pasos sin ambigüedad** por etapa (E0–E5). Así reduces *guesswork* al implementar y sabes **qué puedes mencionar en CV/portafolio** solo cuando el ítem pase a **Hecho**.

**Documentos relacionados**

- **Mapa único** (capas + micros + módulos + adaptadores): [MAPA-STACK-OSS-UNIFICADO-CAPAS-MICROS-ADAPTADORES.md](MAPA-STACK-OSS-UNIFICADO-CAPAS-MICROS-ADAPTADORES.md)  
- **Comunicaciones + arquitectura alto/medio/bajo por componente:** [COMUNICACIONES-Y-ARQUITECTURA-POR-COMPONENTE-NIVELES.md](COMUNICACIONES-Y-ARQUITECTURA-POR-COMPONENTE-NIVELES.md)  
- **Guías de implementación + ejemplos copiables (prod):** [implementacion/README.md](implementacion/README.md)  
- Visión por capas y catálogo OSS: [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md)  
- **Curación duplicados (elección UnClic):** [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](CURACION-DUPLICADOS-Y-ELECCION-UNClic.md)  
- **4 planes integrados servidor propio:** [PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md](PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md)  
- Microservicios actuales: [MICROSERVICIOS-Y-DOCKER.md](MICROSERVICIOS-Y-DOCKER.md) · [ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md](ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md)  
- Leads / Gmail: [LEADS-CORREO-GMAIL-SMTP.md](LEADS-CORREO-GMAIL-SMTP.md)  
- Extraer repo: [EXTRACT-REPO-UNClic-AISLADO.md](EXTRACT-REPO-UNClic-AISLADO.md)  
- IaC ancla: [infra/tofu/README.md](../infra/tofu/README.md)

### Atlas ilustrativo en el sitio UnClic (base de cartera)

- **Ruta pública:** `/integraciones` (página estática generada por Next).
- **Qué es:** mapa **educativo e ilustrativo**: escenarios de cliente (entrega, runtime, datos, seguridad, observabilidad, ERP, pagos/fiscal) y tarjetas con enlaces a **documentación oficial** de cada herramienta (OSS + SaaS donde aplica).
- **Qué no es:** no ejecuta Gitea, ERPNext, etc. en el navegador; es la **vitrina** que alinea discursos con el plan técnico del repo.
- **Código:** copy en `lib/copy.ts` (`ossAtlas`, `ossAtlasScenarios`, `ossAtlasTools`); UI en `components/sections/oss-atlas-*.tsx`.
- **Mantenimiento:** al adoptar una herramienta nueva en el plan, añade una entrada en `ossAtlasTools` y, si hace falta, un escenario en `ossAtlasScenarios` (incl. **IA / ML / agentes** · capa **L19** y filtro homónimo en la página).

**Leyenda de estado**

| Estado | Significado |
|--------|-------------|
| **Hecho** | Código + doc + criterio de aceptación cumplido en este repo o flujo documentado reproducible. |
| **En curso** | Decisión tomada; falta cerrar pasos finales o producción. |
| **Pendiente** | No iniciado en UnClic; solo planificado. |

---

## 1. Matriz de integración (herramienta → estado → evidencia)

| Ítem | Capa | Estado | Evidencia / ubicación | Próximo paso atómico (1 sola acción) |
|------|------|--------|------------------------|--------------------------------------|
| Next.js sitio + export | L17 | **Hecho** | Raíz `unclic/`, `next.config.js` (`output: 'export'`) | — |
| Leads correo (dev Next) | L13 | **Hecho** | `app/api/lead-email/route.ts` | — |
| Leads correo (prod estática) | L13 | **Hecho** | `services/api` + `NEXT_PUBLIC_UNCLIC_API_URL` + [LEADS-CORREO-GMAIL-SMTP.md](LEADS-CORREO-GMAIL-SMTP.md) | Desplegar API en VPS/Docker con env en prod |
| Microservicio API | L5 | **Hecho** | `services/api/`, `openapi/openapi.yaml` | Añadir endpoint Stripe cuando exista spec |
| Microservicio ping + orquestación | L5 | **Hecho** | `services/ping/`, `GET /v1/orchestration/health` | — |
| Docker Compose local | L5 | **Hecho** | `docker-compose.yml` | Ejecutar `docker compose up` en máquina con Docker |
| Pruebas automatizadas servicios | L2 | **Hecho** | `npm run test:services`, Jenkins `Test API` + ping | — |
| CI GitHub (repo aislado) | L2 | **Hecho** | `.github/workflows/ci.yml` | Activar al extraer repo raíz |
| Jenkins pipeline landing | L2 | **Hecho** | `Jenkinsfile` (monorepo nucleic) | — |
| Gmail SMTP variables | L13 | **En curso** | `.env.example` | Copiar a `.env.local` / servidor y probar POST lead |
| CORS producción | L9 | **En curso** | `CORS_ORIGINS` en API | Fijar dominio real `https://unclic.consulting` en env del API |
| Stripe webhook → API | L14 | **Pendiente** | — | Crear `POST /v1/webhooks/stripe` + firma + test key |
| Facturapi client test | L15 | **Pendiente** | — | Variable `FACTURAPI_API_KEY` + llamada desde API a borrador |
| Coolify deploy UnClic | L5 | **Pendiente** | Doc [coolify.io/docs](https://coolify.io/docs) | Crear servidor Coolify + app Git + env + dominio |
| Gitea + Jenkins (toolkit) | L1–L2 | **Hecho fuera** | Toolkit FastFlow / demos | Enlazar en CV solo si URL pública estable |
| Registry Docker | L4 | **Hecho fuera** | Infra documentada DEMOS / Jenkins | Misma regla: evidencia URL o no reclamar en CV |
| ERPNext self-host | L16 | **Pendiente** | [docs.erpnext.com](https://docs.erpnext.com/) | Elegir: Frappe Cloud trial vs Docker en VPS; cerrar empresa en ERP |
| Mexico Einvoice (ERPNext) | L15–L16 | **Pendiente** | App Frappe según doc | Tras ERP base: configurar según guía oficial del módulo |
| Mattermost | Colaboración | **Pendiente** | [docs.mattermost.com](https://docs.mattermost.com/) | Instalar en VPS o usar cloud trial; no bloquea UnClic web |
| Keycloak / Authelia | L10 | **Pendiente** | Doc oficial | Poner **delante** de URLs sensibles de demo; definir realm/client |
| NATS / RabbitMQ en UnClic | L8 | **Pendiente** | — | Añadir servicio en compose + adaptador en API |
| LavinMQ (alternativa AMQP liviana) | L8 | **Pendiente** | [docs.lavinmq.com](https://docs.lavinmq.com/) | Evaluar vs RabbitMQ (mismo patrón AMQP en API); buen fit si buscas broker ligero + streams |
| **DX / toolchain 2026** (Biome, Bun, Deno, Zed, Turso, Ruff, Astro, Continue) | L1–L17 | **Alternativa** | [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) §curación 2026 | PoC por cliente; **UnClic web** sigue Node + Next salvo decisión explícita |
| **Operación empresa OSS** (Vates: XCP-ng, XO, Nextcloud, CRM, BI, PM…) | HV + L10–L17 | **Referencia** | [Vates 2025](https://vates.tech/blog/the-open-source-we-use-at-vates-2025-edition/) · [implementacion/11-EMPRESA-OSS-VATES-REFERENCIA.md](implementacion/11-EMPRESA-OSS-VATES-REFERENCIA.md) | Política 3 niveles + revisión anual; escala org sin rearmar DC; sustituciones documentadas (Plane, Metabase, BBB…); radar vs prod; tabla “bajo observación”; atlas **Operación empresa OSS** |
| **Criterio dev: nube vs owned** (37signals) | Infra / ops | **Referencia personal** | [REWORK — Leaving the Cloud](https://37signals.com/podcast/leaving-the-cloud) · [post DHH](https://world.hey.com/dhh/why-we-re-leaving-the-cloud-654b47e0) · [implementacion/12-37signals-REWORK-LEAVING-CLOUD-DESARROLLADOR.md](implementacion/12-37signals-REWORK-LEAVING-CLOUD-DESARROLLADOR.md) | No sustituye decisión por cliente; brújula: mito ops, lock-in, TCO, cuándo sí nube; contrastar con doc 11 |
| **Grist** (hoja relacional self-host) | L6 / datos internos | **Pendiente** | [support.getgrist.com/install/grist-on-docker](https://support.getgrist.com/install/grist-on-docker/) · [implementacion/13-GRIST-HOJA-RELACIONAL-SELF-HOST.md](implementacion/13-GRIST-HOJA-RELACIONAL-SELF-HOST.md) | PoC cliente que necesite CSV→app sin ERP; TLS + auth + backup; tutorial [lawrence.video/grist](https://lawrence.video/grist) |
| **Curación duplicados + 4 planes integración** | Estrategia | **Referencia** | [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](CURACION-DUPLICADOS-Y-ELECCION-UNClic.md) · [PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md](PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md) | Elegir OSS cuando hay solape; arquetipos: UnClic, suite trabajo, plataforma dev, negocio on-prem |
| **NetAlertX** (descubrimiento LAN) | L12 | **Pendiente** | [docs.netalertx.com](https://docs.netalertx.com/) · [implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md](implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md) | Planes 1–4 (opc. en 1); vídeo [Lawrence](https://www.youtube.com/watch?v=R3b5cxLZMpo); solo redes propias; no exponer UI a Internet |
| **RustDesk** (acceso remoto self-host) | L10 (nota) | **Pendiente** | [rustdesk.com/docs](https://rustdesk.com/docs/en/) · [implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md](implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md) | Planes 1–4; vídeo [Lawrence](https://www.youtube.com/watch?v=FIEcTNjFZNA); firewall/VPN; claves servidor; no confundir con IdP |
| Dapr | Orquestación | **Pendiente** | [docs.dapr.io](https://docs.dapr.io/) | Opcional E4+; no requerido para Stripe simple |
| Google Cloud OAuth | L10 | **Pendiente** | Consola GCP proyecto `unclic` | Pantalla consentimiento + OAuth client → login Next opcional |
| OpenTofu / Terraform recurso real | L18 | **Pendiente** | [infra/tofu/README.md](../infra/tofu/README.md) | Añadir `main.tf` mínimo EC2 + outputs (no commitear secrets) |
| Prometheus + Grafana | L12 | **Pendiente** | Doc oficial | Compose sidecar o stack monitoring en VPS |
| **Capa L19 — IA / ML / agentes (ver PLAN-STACK §L19)** | L19 | **Pendiente** | [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) | Elegir 1–2 piezas por cliente; no bloquea retail/POS |
| Ollama (inferencia local / VPS) | L19 | **Pendiente** | [ollama.com](https://ollama.com/) | `docker run` o binario; API interna; **no** exponer sin auth |
| Docker Model Runner (DMR) | L19 | **Pendiente** | [docs.docker.com/ai/model-runner](https://docs.docker.com/ai/model-runner/) | Habilitar en Docker Desktop o `docker-model-plugin` en Linux; `docker model pull`; API solo red privada |
| MLflow (tracking experimentos) | L19 | **Pendiente** | [mlflow.org/docs](https://mlflow.org/docs/latest/index.html) | Servidor + artefactos; separar de DB transaccional L6 |
| n8n (workflows / agentes low-code) | L19 | **Pendiente** | [docs.n8n.io](https://docs.n8n.io/) | Self-host; webhooks hacia tu API u inferencia local (Ollama o DMR) |
| LangGraph (agentes en código) | L19 | **Pendiente** | [LangGraph docs](https://langchain-ai.github.io/langgraph/) | Worker Python aparte o servicio interno |
| PyTorch / TensorFlow / Keras (entrenamiento) | L19 | **Pendiente** | Doc oficial cada uno | Solo si el cliente tiene caso de ML propio; no default UnClic web |
| Hexabot / Stable Studio / GPT4All | L19 | **Pendiente** | Ver enlaces en PLAN-STACK L19 | Evaluar licencia y mantenimiento antes de comprometer a cliente |

**Regla CV:** en portafolio/CV solo **Hecho** o **En curso** con **URL o repo público verificable**. Lo **Pendiente** no se vende como entregado.

---

## 2. Ejecución granular por etapa (sin ambigüedad)

Cada etapa incluye: **objetivo**, **prerrequisitos**, **pasos numerados**, **comandos** (desde la carpeta `unclic/` salvo que se diga lo contrario), **definición de hecho (DoD)**.

---

### E0 — Manual / local

**Objetivo:** reproducir el sitio y los servicios en tu máquina sin Docker.

**Prerrequisitos:** Node 20+, npm, (opcional) Docker Desktop.

**Pasos**

1. Clonar/acceder al repo y entrar a `toolkit-fastflow/integrations/web-cuantica/unclic` (o raíz del repo aislado).
2. `npm install` en la raíz de UnClic.
3. `npm run dev` → comprobar `http://localhost:3002`.
4. `cd services/api && npm ci && npm run dev` en otra terminal → `http://localhost:3001/health`.
5. En `.env.local` (raíz): `NEXT_PUBLIC_UNCLIC_API_URL=http://localhost:3001` y variables Gmail si pruebas leads reales.
6. En la shell del API: `export CORS_ORIGINS=http://localhost:3002` (macOS/Linux).
7. `npm run test:services` desde raíz UnClic.

**DoD**

- [ ] Home carga sin error.  
- [ ] `curl -s http://localhost:3001/health` devuelve JSON `ok: true`.  
- [ ] Formulario lead devuelve éxito o 503 explícito si falta SMTP.  
- [ ] `npm run test:services` en verde.

**Si falla:** revisar puerto 3002 ocupado; firewall local; `GMAIL_SMTP_*` mal copiados (sin espacios en app password).

---

### E1 — Monolito desplegable (sitio estático + API en servidor)

**Objetivo:** `out/` en Nginx (o similar) + API en proceso o contenedor accesible por HTTPS.

**Prerrequisitos:** E0 OK; servidor con IP/DNS; certificado TLS (Let’s Encrypt o existente).

**Pasos**

1. `npm run build` en raíz → genera `out/`.
2. Subir `out/` al servidor (rsync/Jenkins existente) → comprobar URL pública.
3. En servidor: levantar API con `docker compose up -d --build` **o** `node services/api/dist/server.js` tras `npm run build:api`.
4. DNS: `A` o `CNAME` para `api.tu-dominio.com` → IP del servidor.
5. Reverse proxy (Nginx/Caddy): `proxy_pass` a `127.0.0.1:3001`; TLS.
6. En build del front (o env en host estático): `NEXT_PUBLIC_UNCLIC_API_URL=https://api.tu-dominio.com` y **volver a** `npm run build` + desplegar `out/`.
7. En API producción: `CORS_ORIGINS=https://tu-dominio.com` (sin barra final).

**DoD**

- [ ] Sitio carga por HTTPS.  
- [ ] `curl https://api.../health` OK.  
- [ ] Lead desde el sitio real no falla por CORS.  
- [ ] No hay secretos en `out/` (solo `NEXT_PUBLIC_*` permitidas).

**Rollback:** conservar carpeta `out/` anterior y contenedor/imagen API anterior con tag.

---

### E2 — Monolito + plataforma (registry, CI, Coolify o equivalente)

**Objetivo:** mismo producto con **pipeline repetible** y opción **PaaS self-hosted**.

**Prerrequisitos:** E1 OK; acceso a Jenkins o Coolify; registry accesible.

**Pasos (elige A o B)**

**A — Solo Jenkins + registry (ya en toolkit)**

1. Confirmar webhook Gitea → Jenkins según doc del toolkit.  
2. Pipeline construye artefacto y despliega según `Jenkinsfile`.  
3. Tag de imagen API si empaquetas API como imagen (opcional).

**B — Coolify**

1. Instalar Coolify en VPS ([coolify.io/docs](https://coolify.io/docs)).  
2. Conectar repo (GitHub/Gitea).  
3. Definir **Build pack** o Dockerfile path `services/api/Dockerfile`.  
4. Variables de entorno en UI Coolify (mismas que `.env.example`).  
5. Dominio y TLS automáticos en Coolify.

**DoD**

- [ ] Push a rama acordada dispara build.  
- [ ] Deploy no es solo manual scp.  
- [ ] Documentado en `docs/` el **flujo exacto** (3–10 líneas) con URL del panel.

---

### E3 — Servicios acoplados (API + workers + cola opcional)

**Objetivo:** Stripe u otro webhook no bloquea request; trabajo async.

**Prerrequisitos:** E1; cuenta Stripe test.

**Pasos**

1. Añadir ruta `POST /v1/webhooks/stripe` en `services/api` con verificación de firma ([stripe.com/docs/webhooks](https://stripe.com/docs/webhooks)).  
2. Idempotencia: guardar `event.id` en SQLite/Postgres o rechazar duplicado.  
3. (Opcional) Publicar evento interno a NATS/RabbitMQ; consumer segundo proceso.  
4. Tests con `stripe listen` + fixture.

**DoD**

- [ ] Webhook test devuelve 200 y no dobla efectos secundarios.  
- [ ] Documentado en `docs/` variable `STRIPE_WEBHOOK_SECRET`.

---

### E4 — Microservicios / eventos (ampliar)

**Objetivo:** varios despliegues, observabilidad mínima.

**Pasos (orden estricto)**

1. Métricas HTTP en API (Prometheus client o middleware).  
2. Logs JSON a stdout; rotación en host.  
3. Decidir si NATS o Kafka; un solo topic de prueba.  
4. Dapr solo si hay dolor real de muchos SDKs (evaluación coste/beneficio).

**DoD**

- [ ] Dashboard o query Prometheus con al menos `http_requests_total`.  
- [ ] Runbook 1 página: “cómo ver logs en prod”.

---

### E5 — Cloud-native avanzado

**Objetivo:** K8s, múltiples entornos, DR — **solo después** de E3 estable.

**Pasos:** definir cluster (EKS, k3s, etc.) según doc oficial; Helm charts por servicio; **no** mezclar con UnClic landing hasta tener manifiestos en repo `deploy/k8s/` con revisión.

**DoD:** fuera de alcance hasta cierre de E3; actualizar este doc cuando arranques.

---

## 3. Backlog ordenado (una fila = siguiente decisión)

| Orden | Tarea | Bloquea |
|-------|--------|---------|
| 1 | Fijar `CORS_ORIGINS` y `NEXT_PUBLIC_UNCLIC_API_URL` en prod | Leads reales |
| 2 | Webhook Stripe test en API | Monetización demo |
| 3 | Facturapi test desde API (presupuesto) | CFDI automático |
| 4 | Coolify o documentar solo Jenkins (elegir) | Menos fricción deploy |
| 5 | ERPNext VPS + empresa + plan de cuentas | Back office |
| 6 | Keycloak delante de demos sensibles | Seguridad perimetral |
| 7 | Mattermost (opcional) | Comunicación equipo |
| 8 | **L19 IA:** Ollama **o** Docker Model Runner + n8n **o** LangGraph (elegir según cliente) | Demos “agentes” sin bloquear core retail |
| 9 | UI audio/vídeo tipo agente + panel STT/TTS/WS + stub `services/oss-voice` (sustituir por Whisper/Piper) | [UI-OSS-MEDIA-ELEVENLABS-STYLE.md](UI-OSS-MEDIA-ELEVENLABS-STYLE.md) · `npm run dev:oss-voice` |

---

## 4. Nota explícita para CV / LinkedIn

Mientras un ítem esté **Pendiente** o **En curso** sin URL pública, en el CV usa formulaciones del tipo: **“En implementación en portafolio (unclic.consulting)”** o **no lo menciones**. Cuando pase a **Hecho**, añade la **URL** o el **comportamiento demostrable** en una línea.

---

## 5. Mantenimiento

Al cerrar una fila de la matriz §1: cambiar estado a **Hecho**, añadir enlace a PR/commit o doc, y actualizar fecha al pie.

---

*Última revisión: marzo 2026 — alinear con el estado real del repo tras cada entrega.*

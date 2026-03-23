# Landing UnClic

Next.js (export estático): **sitio y producto UnClic** (landing, demos, hub de capacidades). El código fuente de referencia está **en esta carpeta del monorepo**; el deploy de producción suele ir al repo Gitea **`alejandro-perez/nucleic`** (raíz Next + Jenkins; ver [docs/JENKINS-NUCLEIC-DEPLOY.md](docs/JENKINS-NUCLEIC-DEPLOY.md)). **Ámbito:** UnClic / tu sitio — **no** un proyecto genérico “para George”; ver [docs/ALCANCE-REPO-UNClic-Y-NUCLEIC.md](docs/ALCANCE-REPO-UNClic-Y-NUCLEIC.md).

**Toolkit FastFlow / POS** (contexto monorepo): índice en [../docs/00-inicio/](../docs/00-inicio/) (documentación compartida; la landing UnClic es independiente en propósito).

**Docs útiles:** [docs/README.md](docs/README.md) · **implementación prod (Stripe, colas, métricas, LLM, etc.):** [docs/implementacion/README.md](docs/implementacion/README.md) · **flujo bloque + copy:** [docs/FLUJO-NORMAL-BLOQUE-Y-COPY.md](docs/FLUJO-NORMAL-BLOQUE-Y-COPY.md) · posicionamiento/copy/UI: [docs/POSICIONAMIENTO-ENTERPRISE-UNClic.md](docs/POSICIONAMIENTO-ENTERPRISE-UNClic.md), [SHADCN-BLOCKS-MAP.md](docs/SHADCN-BLOCKS-MAP.md), [CONSISTENCIA-UI.md](docs/CONSISTENCIA-UI.md), [DOCKER-Y-REGISTRY-UNClic.md](docs/DOCKER-Y-REGISTRY-UNClic.md).

`toolkit-fastflow/integrations/web-cuantica/unclic`

---

## Stack

- Next.js 15, React 19, TypeScript, Tailwind, Radix, lucide-react
- Sección audio con `<audio>` (sin WebGL / Three en runtime)
- UI: Shadcn + tema; referencia visual agente/audio: [ElevenLabs UI](https://ui.elevenlabs.io/) (componentes OSS; **sin** API de pago en este repo — ver [docs/UI-OSS-MEDIA-ELEVENLABS-STYLE.md](docs/UI-OSS-MEDIA-ELEVENLABS-STYLE.md))
- **Microservicios `services/api`** y **`services/ping`** (Hono): leads, orquestación de salud, demo multi-contenedor. Ver [docs/MICROSERVICIOS-Y-DOCKER.md](docs/MICROSERVICIOS-Y-DOCKER.md), [docs/ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md](docs/ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md). Pruebas: `npm run test:services`. **Todo el front + servicios:** `npm run verify` (ver [docs/LISTO-PARA-PROBAR-E-IMPLEMENTAR.md](docs/LISTO-PARA-PROBAR-E-IMPLEMENTAR.md)). Aislar repo: [docs/EXTRACT-REPO-UNClic-AISLADO.md](docs/EXTRACT-REPO-UNClic-AISLADO.md).

---

## Desarrollo

```bash
npm install
npm run dev
```

Abre http://localhost:3002

**API en paralelo** (leads + auth portal + presupuesto OSS): otra terminal → `npm run dev:api` (puerto **3001**). En `.env.local` define `NEXT_PUBLIC_UNCLIC_API_URL=http://localhost:3001` y en la shell de la API `CORS_ORIGINS=http://localhost:3002`. **Portal demos** (JWT + email verificado): [docs/PORTAL-AUTH-JWT-EMAIL.md](docs/PORTAL-AUTH-JWT-EMAIL.md); activa `NEXT_PUBLIC_PORTAL_AUTH_REQUIRED=true` para exigir login en `/demo` y `/flow-demo`.

**Voz OSS (stub STT/TTS/WebSocket):** `npm run dev:oss-voice` (puerto **3005**). En `.env.local`: `NEXT_PUBLIC_OSS_STT_URL`, `NEXT_PUBLIC_OSS_TTS_URL`, `NEXT_PUBLIC_OSS_VOICE_WS_URL` (ver [docs/UI-OSS-MEDIA-ELEVENLABS-STYLE.md](docs/UI-OSS-MEDIA-ELEVENLABS-STYLE.md)).

**Ollama (LLM local):** instala desde [ollama.com](https://ollama.com/download) o `docker compose up -d ollama`. `.env.local`: `NEXT_PUBLIC_OLLAMA_URL=http://127.0.0.1:11434`, `NEXT_PUBLIC_OLLAMA_MODEL=llama3.2`. CORS: [docs/OLLAMA-UNClic-LLM-E-IMAGEN.md](docs/OLLAMA-UNClic-LLM-E-IMAGEN.md).

---

## Build

```bash
npm run build
```

Genera `out/`. Probar: `npm run serve` (sirve `out/` en 3002).

**CI local (lint + types + build + tests API/ping):**

```bash
npm run verify
```

Si en dev ves errores tipo `Expected '</'` en JSX, **`ssr: false` en `app/layout.tsx`** (usa solo `ChatterAssistLoader`, no `dynamic` en el layout), `ENOENT`/`middleware-manifest.json` en `.next/` (caché corrupta tras un fallo), o **`ENOSPC`**: libera espacio en disco, luego `rm -rf .next` y vuelve a arrancar. Con disco muy justo en dev: `NEXT_DISABLE_WEBPACK_CACHE=1 npm run dev` (más lento, menos escrituras en `.next/cache`).

**Desplegar el sitio:** [docs/DEPLOY-SITIO-REMOTO.md](docs/DEPLOY-SITIO-REMOTO.md) — GitHub Pages, Gitea desde local, rsync/SSH a servidor, Vercel/Netlify.

**Jenkins (repo nucleic) + deploy automático:** [docs/JENKINS-NUCLEIC-DEPLOY.md](docs/JENKINS-NUCLEIC-DEPLOY.md) · sincronizar monorepo → Gitea: `bash scripts/update-nucleic-from-monorepo.sh`

**Demos solo para correos autorizados (Jenkins, Gitea, hub, Cloudcraft en la web):** [docs/DEMO-ACCESO-ALLOWLIST-INFRA.md](docs/DEMO-ACCESO-ALLOWLIST-INFRA.md) — combinar con Basic Auth/VPN en servidores reales. **Checklist producción HTTPS + flujo commit→POS:** [docs/DEMOS-PRODUCCION-END-TO-END.md](docs/DEMOS-PRODUCCION-END-TO-END.md).

**LocalStack + Terraform / Pulumi / SST + Jenkins + registry + POS:** [docs/LOCALSTACK-IAC-JENKINS-POS.md](docs/LOCALSTACK-IAC-JENKINS-POS.md) — qué simula LocalStack, qué no, y cómo encajar con tu `deploy/terraform` (Kubernetes).

---

## Estructura

- **app/** — layout, globals.css, page (una página con todas las secciones)
- **components/layout/** — Header, Footer
- **components/sections/** — **HubDemosSection** (stack completo), Hero, **DemosSection**, **ArchitectureLiveSection** (Cloudcraft), AudioSection, CtaSection, etc.
- **components/ui/** — Button, Card, Separator, Orb (usado en AudioSection)
- **lib/** — utils, audio-samples (URLs de audios genéricos)
- **docs/** — PLAN-GRANULAR-SITIO-WEB-CUANTICA.md, REQUISITOS-SITIO-WEB-CUANTICA.md (referencias internas al nombre de archivo)

---

## Audio en la landing

Rutas en `lib/audio-samples.ts` (p. ej. `/audio/sample-1.mp3`). Coloca los ficheros en `public/audio/` o apunta a URLs finales. Reproductor avanzado: extender con componente dedicado según diseño.

---

## Orb 3D

Esfera Three.js en `components/ui/orb.tsx`; ajustable a la identidad visual del sitio.

---

## Arquitectura en tiempo real (Cloudcraft)

La sección **Arquitectura en tiempo real** (`#architecture-live`) muestra el diagrama de infra AWS (Live scanning) o un enlace a Cloudcraft. Se configura con variables de entorno (inyectadas en **build time**):

- **`NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL`** — URL para abrir el diagrama en Cloudcraft (nueva pestaña). Si está definida, se muestra un botón "Ver diagrama en Cloudcraft".
- **`NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL`** — (opcional) URL para embeber en iframe. Si está definida, se muestra el diagrama embebido en la página.

Crear o editar **`.env.local`** en la raíz del proyecto (no commitear). Ejemplo:

```env
NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL=https://app.cloudcraft.co/view/XXXXXXXX?key=YYYY
NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL=https://app.cloudcraft.co/view/XXXXXXXX?key=YYYY&embed=true
```

Tras cambiar variables, reiniciar `npm run dev` o volver a ejecutar `npm run build`. En producción, definir las mismas variables en el entorno de build (Vercel, Docker, CI). Documentación completa: **toolkit-fastflow/docs/GUIA-CLOUDCRAFT-COMPLETA.md** y **PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md**.

**Diagrama con acceso restringido:** usa una **captura** estática y **`NEXT_PUBLIC_CLOUDCRAFT_STATIC_IMAGE`**. Pasos: **[docs/VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md](docs/VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md)**.

---

## Documentación

- **docs/PLAN-GRANULAR-SITIO-WEB-CUANTICA.md** — Plan por fases y checklist
- **docs/REQUISITOS-SITIO-WEB-CUANTICA.md** — Requisitos técnicos y de contenido

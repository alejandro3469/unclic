# Landing UnClic

Next.js (export estático): landing, demos y hub de capacidades. **Integración con el toolkit** (POS, Jenkins, docs): [../docs/00-inicio/EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md](../docs/00-inicio/EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md).

**Docs útiles:** [docs/README.md](docs/README.md) · posicionamiento/copy/UI: [docs/POSICIONAMIENTO-ENTERPRISE-UNClic.md](docs/POSICIONAMIENTO-ENTERPRISE-UNClic.md), [SHADCN-BLOCKS-MAP.md](docs/SHADCN-BLOCKS-MAP.md), [CONSISTENCIA-UI.md](docs/CONSISTENCIA-UI.md), [DOCKER-Y-REGISTRY-UNClic.md](docs/DOCKER-Y-REGISTRY-UNClic.md).

`toolkit-fastflow/integrations/web-cuantica/unclic`

---

## Stack

- Next.js 15, React 19, TypeScript, Tailwind, Radix, motion, lucide-react
- Three.js / R3F / drei (Orb); sección audio con `<audio>`
- UI: Shadcn + tema; referencias: [Shadcn Blocks](https://www.shadcnblocks.com/), [ElevenLabs UI](https://ui.elevenlabs.io/)

---

## Desarrollo

```bash
npm install
npm run dev
```

Abre http://localhost:3002

---

## Build

```bash
npm run build
```

Genera `out/`. Probar: `npm run serve` (sirve `out/` en 3002).

Si en dev ves errores tipo `Expected '</'` en JSX o `ENOENT` en `.next/`, borra la caché y vuelve a compilar: `rm -rf .next && npm run build` (y luego `npm run dev` si quieres).

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

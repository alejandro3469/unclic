# Landing UnClic

Proyecto Next.js (export estático) con **estructura de sitio completo** y componentes **dummy**: misma UI que las muestras, tema neutro (out of the box), listo para ordenar y rellenar con contenido.

**Propósito:** **Hub central** (`#hub-demos`) con todas las demos y referencias (Jenkins, Gitea, POS, registry, Cloudcraft, Gmail/Workspace, audio/vídeo, roadmap pagos/SAT). Un solo enlace para **CV, LinkedIn y GitHub**. Ver [docs/POSICIONAMIENTO-ENTERPRISE-UNClic.md](docs/POSICIONAMIENTO-ENTERPRISE-UNClic.md) y [docs/CV-LINKEDIN-INSTAGRAM-UNClic.md](docs/CV-LINKEDIN-INSTAGRAM-UNClic.md).  
**Stack contenedores:** [DOCKER-Y-REGISTRY-UNClic.md](docs/DOCKER-Y-REGISTRY-UNClic.md) — alineación con [registry oficial Docker Hub](https://hub.docker.com/_/registry), [Docker Docs](https://docs.docker.com/).  
**Logo y prompts (Gemini):** [docs/PROMPTS-GANADORES-LOGO-GEMINI-UNClic.md](docs/PROMPTS-GANADORES-LOGO-GEMINI-UNClic.md) · asset `public/images/brand/unclic-logo.svg`.  
**Estructura (referencia Sequoia):** [REFERENCIA-DISENO-SEQUOIA.md](docs/REFERENCIA-DISENO-SEQUOIA.md). **Shadcn Blocks:** [SHADCN-BLOCKS-MAP.md](docs/SHADCN-BLOCKS-MAP.md) — mapeo categorías → componentes y copy. [INSTALAR-SHADCN-BLOCKS.md](docs/INSTALAR-SHADCN-BLOCKS.md) — instalar bloques (alineado a [docs oficiales](https://www.shadcnblocks.com/docs/getting-started)). [CONSISTENCIA-UI.md](docs/CONSISTENCIA-UI.md) — una sola fuente para texto (lib/copy) y componentes UI (Shadcn/tema). · [PATRON-PAGINA-SOLUCION-SEQUOIA.md](docs/PATRON-PAGINA-SOLUCION-SEQUOIA.md) · [PATRON-HUB-CAPACIDADES-SEQUOIA.md](docs/PATRON-HUB-CAPACIDADES-SEQUOIA.md) · [PATRON-ARTICULO-INSIGHTS-SEQUOIA.md](docs/PATRON-ARTICULO-INSIGHTS-SEQUOIA.md) · [IA-SITIO-ESTILO-ENTERPRISE.md](docs/IA-SITIO-ESTILO-ENTERPRISE.md).

**Ubicación:** `toolkit-fastflow/integrations/web-cuantica/unclic`

---

## Stack

- **Next.js** 15, **React** 19, **TypeScript**
- **Tailwind CSS** (tema neutro slate/zinc)
- **Radix UI** (Button, Card, Separator, etc.)
- **motion** (animaciones)
- **lucide-react** (iconos genéricos)
- **Three.js** + **@react-three/fiber** + **@react-three/drei** (Orb)
- **Audio:** sección dummy con lista de pistas y `<audio>`; para reproductor completo tipo ElevenLabs copiar desde Vantive
- **Referencias UI:** [ElevenLabs UI](https://ui.elevenlabs.io/) (componentes agente/audio open source sobre shadcn) y **[Shadcn Blocks](https://www.shadcnblocks.com/)** (bloques Shadcn UI). Componentes vitales (login, formularios, cards) usan **Shadcn UI** (Card, Button, Input, Label, Textarea) y tokens del tema para consistencia en todo el sitio.

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

## Audios genéricos

Los archivos referenciados en `lib/audio-samples.ts` son `/audio/sample-1.mp3`, etc. Añadir en `public/audio/` o sustituir por URLs definitivas. Para reproductor completo (controles ElevenLabs-style) copiar desde Vantive: `components/ui/audio-player.tsx`, provider y drawer de pistas.

---

## Orb completo

El Orb actual es un placeholder (esfera Three.js). Para el Orb igual que en Vantive, copiar `components/ui/orb.tsx` desde el proyecto Vantive.

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

**Blueprint privado (“not public” / Login):** sube una **captura** del diagrama y configura **`NEXT_PUBLIC_CLOUDCRAFT_STATIC_IMAGE`**. Pasos: **[docs/VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md](docs/VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md)**.

---

## Documentación

- **docs/PLAN-GRANULAR-SITIO-WEB-CUANTICA.md** — Plan por fases y checklist
- **docs/REQUISITOS-SITIO-WEB-CUANTICA.md** — Requisitos técnicos y de contenido (basado en Vantive)

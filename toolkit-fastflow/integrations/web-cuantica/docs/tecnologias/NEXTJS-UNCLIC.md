# Next.js (UnClic) — Por qué, open source y cómo replicar

## Por qué lo usamos

- **Landing y UI de demos:** Sitio UnClic con enlaces a Jenkins, Gitea, POS, diagrama de arquitectura (Cloudcraft). Todo en un solo front moderno.
- **Objetivo de automatización:** El contenido (URLs de demo, textos) se puede configurar por variables de entorno; el build es reproducible (npm run build).

## Open source

- **Next.js:** [nextjs.org](https://nextjs.org/), licencia MIT.

## Cómo replicar

| Consola | Comando |
|---------|---------|
| Terminal (local) | `cd toolkit-fastflow/integrations/web-cuantica/unclic` |
| Terminal (local) | `npm install` |
| Terminal (local) | `npm run dev` → abrir http://localhost:3002 |
| Opcional | `.env.local` con `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL`, `NEXT_PUBLIC_DEMO_JENKINS_URL`, etc. |

Ver [unclic/README.md](../../unclic/README.md).

---

## ElevenLabs UI (componentes agente / audio)

**ElevenLabs UI** es una biblioteca de componentes **open source** y un **registry** personalizado sobre **shadcn/ui** para construir experiencias **multimodales y agenticas** (voz, transcripción, orbes de agente, conversación). *"Powered by ElevenLabs Scribe"* en demos (Voice Fill, Listening, etc.).

- **Web / docs:** Docs | Components | Examples | búsqueda en documentación | **Toggle theme**. Banner *"Introducing · Scribe v2 Realtime"* / *"Access code ELEVENUI · Try it now"*.
- **Qué ofrece:** Agent Orbs (estados Idle / Listening / Talking), Waveform, Voice Chat (*"Customer Support — Tap to start voice chat"*), Transcriber, lista de pistas, conversación por texto o voz (*"Start a conversation"*, *"Send message"*, *"Start voice call"*). *"Engineered by ElevenLabs. The source code is available on GitHub."*
- **Definición (Introduction):** *"A collection of Open Source agent and audio components that you can customize and extend."* Componentes vía CLI **`@elevenlabs/cli`**:  
  `pnpm dlx @elevenlabs/cli@latest components add <component>`  
  Ejemplo Orb: `pnpm dlx @elevenlabs/cli@latest components add orb`
- **Getting Started — estructura:** Introduction | Components | **Setup** | **Usage** | **Troubleshooting**. Lista de componentes: **Audio Player**, Bar Visualizer, **Conversation**, Conversation Bar, **Live Waveform**, **Matrix**, **Message**, Mic Selector, **Orb**, **Response**, Scrub Bar, Shimmering Text, Speech Input, **Transcript Viewer**, **Voice Button**, **Voice Picker**, **Waveform**.
- **Prerrequisitos (Setup):** **Node.js 18+**, proyecto **Next.js**, **shadcn/ui** (el CLI puede configurarlo si falta).
- **Usage:** Los componentes se copian al código del proyecto (no solo como dependencia opaca); puedes editarlos. Ejemplo:

```tsx
"use client"
import { Card } from "@/components/ui/card"
import { Orb } from "@/components/ui/orb"
export default function Page() {
  return (
    <Card className="flex items-center justify-center p-8">
      <Orb />
    </Card>
  )
}
```

- **Troubleshooting (resumen):**
  - **Sin estilos:** Revisar **Tailwind 4** + `globals.css` con estilos base de shadcn/ui.
  - **CLI no añade archivos:** Directorio raíz del proyecto (`package.json`), `components.json` correcto, usar **última** CLI: `pnpm dlx @elevenlabs/cli@latest components add orb`.
  - **Tema oscuro no cambia:** Mismo sistema **data-theme** en `<html>` que esperan shadcn/AI Elements; revisar `tailwind.config` (class vs data).
  - **Module not found:** Comprobar que exista el archivo y que **tsconfig** tenga alias `@/*` → `./*` (o equivalente).
- **Infra comercial:** *"Deploy and Scale Agents with ElevenLabs"* — Deploy Now, Talk to an expert (producto ElevenLabs aparte de la UI open source).

Enlaces: documentación oficial ElevenLabs UI, repositorio GitHub de los componentes (indicado en el pie de la doc).

---

## Shadcnblocks.com (bloques de pago para shadcn/ui)

**Shadcnblocks.com** es un **marketplace de bloques y plantillas** para **shadcn/ui**, Tailwind y React (Next.js, Astro). **No está afiliado oficialmente** a shadcn/ui ni a Tailwind CSS (lo indica el propio sitio).

- **Oferta:** Miles de **blocks** por categoría (Hero, About, Footer, Feature, Pricing, Gallery, Dashboard, Navbar, Ecommerce, Shader, Social Media Trending, etc.), **componentes** y **templates** Pro. Ejemplos de planes: **Pro** mensual (~$19/mes), acceso lifetime, plantillas premium, Figma Kit, etc. (ver pricing actual en la web).
- **Uso:** Copiar/pegar o instalar vía **Shadcn CLI** / registry. Útil para acelerar landings con secciones ya diseñadas.
- **UnClic / FastFlow:** Si contratas un plan Pro, guarda facturación y soporte en **support@shadcnblocks.com** según el sitio; no commitear claves ni datos de pago en el repo.

---

## Relación con UnClic

UnClic puede combinar **Next.js**, **shadcn/ui**, patrones de **audio/orb** (inspiración o componentes tipo ElevenLabs) y, opcionalmente, bloques de **Shadcnblocks** para secciones de marketing. La documentación de **Cloudcraft** y variables `NEXT_PUBLIC_*` sigue en [unclic/README.md](../../unclic/README.md) y [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

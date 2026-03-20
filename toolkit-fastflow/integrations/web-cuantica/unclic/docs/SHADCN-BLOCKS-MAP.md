# Mapeo Shadcn Blocks → UnClic

Este documento relaciona las **categorías de Shadcn Blocks** (shadcnblocks.com) con los componentes y el contenido actual del sitio UnClic.

**Base de diseño:** [sequoia.com](https://www.sequoia.com) — jerarquía (eyebrow → H1 → lead → métricas → CTA), nav (Soluciones / Capacidades / Insights / Empresa / Comenzar / Iniciar sesión) y footer en cuatro columnas. Ver [REFERENCIA-DISENO-SEQUOIA.md](./REFERENCIA-DISENO-SEQUOIA.md).

**Para instalar bloques desde el registro privado (con tu API key):** [INSTALAR-SHADCN-BLOCKS.md](./INSTALAR-SHADCN-BLOCKS.md). Todo el contenido proviene de `lib/copy.ts`, `lib/copy-pricing.ts` y datos locales; la estructura sigue los patrones de bloques Shadcn UI.

Referencia: [Shadcn Blocks](https://www.shadcnblocks.com/) — 1398+ bloques (React, Shadcn, Tailwind).

---

## Bloques usados en el sitio

| Categoría Shadcn Blocks | Nuestro componente / página | Contenido (copy) |
|-------------------------|-----------------------------|-------------------|
| **Banner**              | `components/layout/top-banner.tsx` | `banner` (hub, CTA) |
| **Navbar**              | `components/layout/header.tsx` | `nav`, `navDropdowns`, `site` |
| **Hero**                | `components/sections/hero.tsx` (home) | `hero` (headline, subtitle, CTAs), `trustStrip` |
| **Hero2**               | `components/sections/hero2-section.tsx` → bloque `hero2.tsx` | `hero` (badge, heading, description, buttons, image). Usado en `/empresa`. |
| **Trust Strip**         | Dentro de Hero (métricas) | `trustStrip` (métricas, título, subtítulo) |
| **Feature**             | `components/sections/features-section.tsx` | `features` (cards con título/descripción) |
| **Cta**                 | `components/sections/cta-section.tsx` → bloque `cta1.tsx` con props `content` desde `cta` | `cta` (título, descripción, botones, imagen) |
| **Book a Demo**         | Card en CTA: `LoginCardWithBorderBeam` | `loginCard` (español: iniciar sesión, correo, contraseña) |
| **Footer**              | `components/layout/footer.tsx` | `footerCopy`, `footerNav`, `site` |
| **Footer1**             | `components/sections/footer1-section.tsx` → bloque `footer1.tsx` con `content` desde `footer` + `footerNav`. Usado en `/signup`. Opcional: builtWith, hideAppStore. |
| **Login**               | `app/login/page.tsx` | `loginPage` (tabs POS/Full, email, continuar) |
| **Signup / Solicitar acceso** | `app/demo/access/page.tsx` | `demoAccess` (email, opcionales, checkboxes, enviar) |
| **Signup10**            | `app/signup/page.tsx` → bloque `signup10.tsx` con props `content` | `signupPage` (título, Google, correo, términos, ya usuario). |
| **Faq**                 | `components/sections/faq-section.tsx` → bloque `faq1.tsx` | `faq.sectionTitle`, `pricingFaq` (accordion). En home no; en `/soluciones` y `/empresa` sí. |
| **Pricing**             | `components/sections/pricing-section.tsx` + `components/ui/pricing-41.tsx` | `copy-pricing` (planes, categorías) |
| **Gallery**             | `components/sections/gallery-section.tsx` | `gallery` (placeholders / grid) |
| **Contact**             | `app/contacto/page.tsx` + CtaSection | `cta` (formulario / CTA) |

---

## Otras secciones (contenido existente)

| Sección              | Componente | Copy / datos |
|----------------------|------------|--------------|
| Hub / Demos          | `hub-demos-section.tsx` | `hubDemos`, `getHubCategories()` |
| Demos detalladas     | `demos-section.tsx` | `demos`, `DEMO_LINKS` |
| Stack (Icon Cloud)   | `icon-cloud-section.tsx` | `iconCloud`, `iconCloudImageUrls` |
| Flujo (Animated Beam)| `animated-beam-section.tsx` | `animatedBeam` |
| Flow Diagrams        | `flow-diagrams-section.tsx` | `flowDiagrams` |
| Arquitectura         | `architecture-live-section.tsx` | `architectureLive` |
| Cómo empezar         | `how-it-works-section.tsx` | `howItWorks` |
| Globe                | `globe-section.tsx` | `globeSection` |
| Por qué UnClic       | `why-section.tsx` | `why` |
| Cliente ideal        | `cliente-ideal-section.tsx` | `clienteIdeal` |
| Vídeo / Audio        | `video-section.tsx`, `audio-section.tsx` | placeholders, `audioSamples` |
| Social trending      | `social-trending-carousel-section.tsx` | `socialTrendingCarousel` |

---

## Convenciones

- **UI:** Solo componentes Shadcn UI (`Card`, `Button`, `Input`, `Label`, `Accordion`, `Sheet`, `DropdownMenu`, etc.) y tokens del tema (`--background`, `--foreground`, `--primary`, `border-border`, etc.).
- **Estructura:** Cada bloque sigue el patrón de Shadcn Blocks (p. ej. Hero = eyebrow + headline + subheadline + CTAs + trust; FAQ = accordion; Footer = columnas de enlaces + redes + copyright).
- **Contenido:** Todo el texto y datos vienen de `lib/copy.ts`, `lib/copy-pricing.ts` y `lib/*`; no se hardcodea contenido en los componentes.

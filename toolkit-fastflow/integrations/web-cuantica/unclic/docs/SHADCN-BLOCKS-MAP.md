# Mapeo diseño → UnClic (shadcn/ui oficial)

Este documento relaciona las **plantillas visuales** (referencia Sequoia / agencia) con los componentes **reales** del repo: solo primitivas de [`@/components/ui/*`](https://ui.shadcn.com/docs/components) + `BlockContainer` / `BlockSection`.

**Referencias oficiales shadcn/ui**

- [Componentes](https://ui.shadcn.com/docs/components) · [Blocks](https://ui.shadcn.com/blocks) · [Charts](https://ui.shadcn.com/charts/area) · [Registry Directory](https://ui.shadcn.com/docs/directory) · [Create / init](https://ui.shadcn.com/create) · [Installation](https://ui.shadcn.com/docs/installation)

**Inspiración de layout (no dependencia de runtime):** [sequoia.com](https://www.sequoia.com) — jerarquía, nav y footer en columnas. Ver [REFERENCIA-DISENO-SEQUOIA.md](./REFERENCIA-DISENO-SEQUOIA.md).

**Instalación opcional de bloques de terceros (API key):** [INSTALAR-SHADCN-BLOCKS.md](./INSTALAR-SHADCN-BLOCKS.md). El contenido del sitio sigue viniendo de `lib/copy.ts`, `lib/copy-pricing.ts` y datos locales.

---

## Secciones y páginas

| Patrón / página | Implementación UnClic | Copy / datos |
|-------------------|------------------------|--------------|
| **Banner** | `components/layout/top-banner.tsx` | `banner` |
| **Navbar** | `components/layout/header.tsx` | `nav`, `navDropdowns`, `site` |
| **Hero (home)** | `components/sections/hero.tsx` | `hero`, `trustStrip` |
| **Hero marketing 2 col (`/empresa`)** | `components/sections/hero2-section.tsx` — Badge, Button, `next/image`, `BlockContainer` | `hero`, `HERO_IMAGE_PLACEHOLDER` |
| **Trust strip** | Dentro de Hero | `trustStrip` |
| **Features** | `components/sections/features-section.tsx` | `features` |
| **CTA** | `components/sections/cta-section.tsx` — Card + un botón principal → `/portal/registro` | `cta` |
| **Footer** | `components/layout/footer.tsx` | `footerCopy`, `footerNav`, `site` |
| **Login** | `app/login/page.tsx` | `loginPage` |
| **Signup marketing** | `components/sections/signup-marketing-section.tsx` — Card, Input, Separator, Button | `signupPage` |
| **Demo access** | `app/demo/access/page.tsx` — redirección cliente → `/portal/registro` | `demoAccess` (copy legacy) |
| **FAQ** | `components/sections/faq-section.tsx` — Accordion | `faq.sectionTitle`, `pricingFaq` |
| **Pricing** | `components/sections/pricing-section.tsx` | `copy-pricing` |
| **Gallery** | `components/sections/gallery-section.tsx` | `gallery` |

---

## Otras secciones

| Sección | Componente | Copy / datos |
|---------|------------|--------------|
| Hub / Demos | `hub-demos-section.tsx` | `hubDemos` |
| Demos | `demos-section.tsx` | `demos` |
| Integraciones / iconos | `icon-cloud-section.tsx` | `SLUG_LABELS`, etc. |
| Flujo pipeline | `animated-beam-section.tsx` + `flow-diagram-visuals.tsx` | `animatedBeam`, diagrama |
| … | (resto en `components/sections/`) | `lib/copy.ts` |

---

## Convenciones

- **UI:** Componentes instalados con la CLI desde la [documentación oficial](https://ui.shadcn.com/docs/components) (`components/ui/*`) y tokens del tema.
- **Contenido:** Textos y datos desde `lib/copy.ts` y módulos `lib/*`; no hardcodear mensajes de producto en JSX salvo labels de UI genéricos.
- **Charts:** Si se añaden gráficos, seguir [Charts](https://ui.shadcn.com/charts/area) (Recharts + componentes chart del registro shadcn).

# Plan: Sitio solo con Shadcn UI + Shadcnblocks (layout leads)

Objetivo: que el sitio use **únicamente** componentes de la librería Shadcn UI (y bloques de Shadcnblocks.com), con los bloques más nuevos posibles, mismos estilos (tokens de `globals.css`), ordenado como sitio de **leads** al estilo Apple / Google / Datadog.

---

## 1. Orden final del sitio (lead-gen)

Flujo recomendado, de arriba a abajo:

| Orden | Sección        | Categoría Shadcnblocks | Notas |
|-------|----------------|------------------------|--------|
| 0     | **Navbar**     | [Navbar](https://www.shadcnblocks.com/blocks/navbar) (19) | Sticky, logo + links + CTA. Usar bloque newest. |
| 1     | **Hero**       | [Hero](https://www.shadcnblocks.com/blocks/hero) (177) | Headline + subtítulo + 2 CTAs. Newest. |
| 2     | **Logos / Trust** | [Logos](https://www.shadcnblocks.com/blocks/logos) (14) o [Trust Strip](https://www.shadcnblocks.com/blocks/trust-strip) (4) | “Confían en nosotros”. |
| 3     | **Features**   | [Feature](https://www.shadcnblocks.com/blocks/feature) (274) | 3–6 features con iconos. Newest. |
| 4     | **Stats**      | [Stats](https://www.shadcnblocks.com/blocks/stats) (18) o [Stats Card](https://www.shadcnblocks.com/blocks/stats-card) (10) | Números clave. |
| 5     | **Demos / Integración** | [Integration](https://www.shadcnblocks.com/blocks/integration) (16) o Feature | Sustituir “Demos” por bloque Integration o Feature. |
| 6     | **Pricing**    | [Pricing](https://www.shadcnblocks.com/blocks/pricing) (37) | Planes + FAQ con **Accordion** shadcn (no `<details>`). Newest. |
| 7     | **Testimonial**| [Testimonial](https://www.shadcnblocks.com/blocks/testimonial) (29) | Social proof. |
| 8     | **FAQ**        | [Faq](https://www.shadcnblocks.com/blocks/faq) (17) | Si no va dentro de Pricing. Usar **Accordion** shadcn. |
| 9     | **CTA**        | [Cta](https://www.shadcnblocks.com/blocks/cta) (26) | Última llamada a la acción. Newest. |
| 10    | **Footer**     | [Footer](https://www.shadcnblocks.com/blocks/footer) (26) | Links, redes, legal. Newest. |

Opcionales (según contenido): [Banner](https://www.shadcnblocks.com/blocks/banner), [Background Pattern](https://www.shadcnblocks.com/blocks/background-pattern), [Contact](https://www.shadcnblocks.com/blocks/contact) (30, +13 New).

---

## 2. Mapeo link a link (archivo actual → Shadcnblocks)

Cada fila es un paso: **archivo o sección actual** → **reemplazo** (solo componentes shadcn + bloque Shadcnblocks).

| # | Archivo / Sección actual | Categoría Shadcnblocks | Acción |
|---|---------------------------|------------------------|--------|
| 1 | `components/layout/header.tsx` | **Navbar** | Reemplazar por un bloque de [Navbar](https://www.shadcnblocks.com/blocks/navbar). Filtrar por **Newest**. Usar solo `Button`, `Sheet` (móvil) si aplica. Mantener `globals.css` (site-container, tokens). |
| 2 | `components/sections/hero.tsx` | **Hero** | Reemplazar por un bloque de [Hero](https://www.shadcnblocks.com/blocks/hero) (newest). Solo `Button` shadcn, mismo copy/CTAs. |
| 3 | *(nuevo)* | **Logos** o **Trust Strip** | Añadir sección desde [Logos](https://www.shadcnblocks.com/blocks/logos) o [Trust Strip](https://www.shadcnblocks.com/blocks/trust-strip). Solo componentes shadcn. |
| 4 | `app/page.tsx` (SectionBlock “Features” + Cards) | **Feature** | Sustituir por un bloque de [Feature](https://www.shadcnblocks.com/blocks/feature) (newest). Mantener `Card`, `Button`; sin divs genéricos como “card”. |
| 5 | *(nuevo)* | **Stats** | Añadir sección desde [Stats](https://www.shadcnblocks.com/blocks/stats) o [Stats Card](https://www.shadcnblocks.com/blocks/stats-card). |
| 6 | `components/sections/demos-section.tsx` | **Integration** o **Feature** | Reemplazar por bloque [Integration](https://www.shadcnblocks.com/blocks/integration) o variante Feature. Solo `Card`, `Button` shadcn. |
| 7 | `components/sections/pricing-section.tsx` | **Pricing** | Reemplazar por bloque [Pricing](https://www.shadcnblocks.com/blocks/pricing) (newest). **Importante:** sustituir `<details>`/`<summary>` por **Accordion** shadcn (añadir `components/ui/accordion.tsx`). |
| 8 | *(nuevo)* | **Testimonial** | Añadir sección desde [Testimonial](https://www.shadcnblocks.com/blocks/testimonial). |
| 9 | `components/sections/cta-section.tsx` | **Cta** | Reemplazar por bloque de [Cta](https://www.shadcnblocks.com/blocks/cta) (newest). Solo `Button`, `Card` si aplica. |
| 10 | `components/layout/footer.tsx` | **Footer** | Reemplazar por bloque de [Footer](https://www.shadcnblocks.com/blocks/footer) (newest). Misma estructura de links/redes con componentes shadcn. |

**Secciones especiales (mantener o encapsular en shadcn):**

| # | Sección actual | Decisión |
|---|----------------|----------|
| - | `components/sections/audio-section.tsx` | Sustituir `<button>` nativo por `Button` shadcn. Contenedor: `Card` shadcn. Si se convierte en “bloque”, usar estilo [Content](https://www.shadcnblocks.com/blocks/content) o Feature. |
| - | `components/sections/orb-section.tsx` | Mantener Orb (Three.js) dentro de un **Card** o bloque tipo [Gallery](https://www.shadcnblocks.com/blocks/gallery) / [Shader](https://www.shadcnblocks.com/blocks/shader) (14 blocks). No usar divs genéricos para el contenedor; usar Card. |
| - | `components/sections/section-block.tsx` | Dejar de usar como genérico; cada sección será un bloque concreto (Feature, Stats, etc.). Eliminar o reemplazar por bloques nombrados arriba. |

---

## 3. Componentes UI permitidos (solo Shadcn UI)

- **Ya en proyecto:** `Button`, `Card`, `Separator` (Radix + CVA + `cn`).
- **A añadir según bloques:** `Accordion` (FAQ/Pricing), `Sheet` (navbar móvil), `NavigationMenu` si el bloque lo usa.
- **No usar:** componentes genéricos que no sean shadcn: por ejemplo `<button>` → `Button`, `<details>` → `Accordion` o `Collapsible`.

Referencia de componentes: [Shadcnblocks Components](https://www.shadcnblocks.com/components).

---

## 4. Estilos (un solo sistema)

- **Tokens:** usar solo variables de `app/globals.css` (`--background`, `--foreground`, `--primary`, `--card`, `--muted`, `--border`, `--radius`, etc.).
- **Contenedor:** mantener clase `.site-container` (max-width + padding).
- Al copiar un bloque de Shadcnblocks, adaptar clases a estos tokens; no introducir colores o espaciados a mano que rompan el tema.

---

## 5. Checklist de implementación (link a link)

- [ ] **Navbar:** Reemplazar `header.tsx` por bloque Navbar (newest); solo Button/Sheet shadcn.
- [ ] **Hero:** Reemplazar `hero.tsx` por bloque Hero (newest); solo Button.
- [ ] **Logos/Trust:** Añadir sección desde Logos o Trust Strip.
- [ ] **Features:** Reemplazar SectionBlock + Cards por bloque Feature (newest).
- [ ] **Stats:** Añadir sección Stats o Stats Card.
- [ ] **Demos:** Reemplazar `demos-section.tsx` por bloque Integration/Feature.
- [x] **Pricing (FAQ):** Añadido `accordion.tsx`; `<details>` reemplazado por Accordion shadcn. Pendiente: reemplazar toda la sección por bloque Pricing (newest).
- [ ] **Testimonial:** Añadir sección Testimonial.
- [ ] **CTA:** Reemplazar `cta-section.tsx` por bloque Cta (newest).
- [ ] **Footer:** Reemplazar `footer.tsx` por bloque Footer (newest).
- [x] **Audio:** `<button>` reemplazado por `Button` shadcn; contenedor con Card.
- [ ] **Orb:** Envolver en Card; opcional Gallery/Shader block.
- [ ] **page.tsx:** Ordenar secciones en el orden de la tabla del §1 y quitar `SectionBlock` genérico.

---

## 6. Resumen

- **Solo** componentes Shadcn UI y bloques de Shadcnblocks; **ningún** componente genérico (button → Button, details → Accordion).
- **Newest** donde aplique (Navbar, Hero, Feature, Pricing, CTA, Footer).
- **Orden** tipo leads: Navbar → Hero → Logos → Features → Stats → Integration/Demos → Pricing → Testimonial → FAQ (si aplica) → CTA → Footer.
- **Un solo** sistema de estilos (`globals.css` + `.site-container`).

Cuando tengas acceso a los bloques (Free/Basic/Pro), copiar cada bloque desde Shadcnblocks, pegar en el archivo correspondiente de la tabla §2 y ajustar copy + tokens. Para FAQ/Pricing, instalar primero el componente [Accordion](https://ui.shadcn.com/docs/components/accordion) vía CLI shadcn y luego reemplazar `<details>`.

---

## 7. Referencia Shadcnblocks (categorías y conteos)

Fuente: [Shadcnblocks.com](https://www.shadcnblocks.com). 1398 bloques, 1189 componentes, 13 templates. Solo usar bloques/componentes de esta librería; filtrar por **Newest** cuando aplique.

### Bloques por categoría

| Categoría | Bloques | Categoría | Bloques |
|-----------|---------|-----------|---------|
| About | 19 | Accept Invite | 2 |
| Application Shell | 14 | Address Book | 2 |
| Awards | 5 | Background Pattern | 46 |
| Banner | 7 | Bento | 8 |
| Blog | 22 | Blog Post | 7 |
| Book A Demo | 3 | Careers | 9 |
| Case Studies | 6 | Case Study | 3 |
| Changelog | 7 | Chart Card | 27 |
| Chart Group | 15 | Checkout | 6 |
| Code Example | 5 | Community | 7 |
| Compare | 10 | Compare Products | 3 |
| Compliance | 4 | **Contact** | **30** |
| Content | 4 | Crud Companies | 9 |
| **Cta** | **26** | Dashboard | 5 |
| Data Table | 32 | Download | 13 |
| Ecommerce Footer | 6 | Ecommerce Hero | 3 |
| Ecommerce Navbar | 2 | Experience | 4 |
| **Faq** | **17** | **Feature** | **274** |
| Field Mapping | 2 | **Footer** | **26** |
| Gallery | 48 | Help | 4 |
| **Hero** | **177** | Hero Feature Icons | 6 |
| Incentives | 4 | Industries | 4 |
| **Integration** | **16** | Invite User | 4 |
| Leaderboard | 3 | List | 3 |
| Live Purchase | 3 | Login | 8 |
| **Logos** | **14** | **Navbar** | **19** |
| Offer Modal | 3 | Onboarding | 2 |
| Order History | 5 | Order Summary | 6 |
| Payment Methods | 4 | **Pricing** | **37** |
| Process | 4 | Product Card | 14 |
| Product Categories | 5 | Product Detail | 10 |
| Product Gallery | 5 | Product List | 10 |
| Product Quick View | 5 | Product Specs | 2 |
| Project | 33 | Projects | 25 |
| Promo Banner | 7 | Rate Card | 2 |
| Resource | 3 | Resources | 5 |
| Reviews | 10 | Service | 7 |
| Services | 19 | Settings Integrations | 10 |
| Settings Members | 5 | Settings Notifications | 4 |
| Settings Profile | 7 | Shader | 14 |
| Shopping Cart | 11 | Sidebar | 21 |
| Signup | 10 | **Stats** | **18** |
| Stats Card | 10 | Team | 15 |
| **Testimonial** | **29** | Timeline | 15 |
| Todo List | 10 | Trust Strip | 4 |
| User Profile | 12 | Waitlist | 4 |
| Wishlist | 2 | | |

### Componentes Shadcn (referencia rápida)

Para los bloques solo usar variantes de: Accordion, Alert, Alert Dialog, Aspect Ratio, Avatar, Badge, Breadcrumb, **Button**, Calendar, **Card**, Carousel, Checkbox, Collapsible, Combobox, Command, Context Menu, Data Table, Date Picker, Dialog, Drawer, Dropdown Menu, Form, Hover Card, Input, Input OTP, **Label**, Menubar, **Navigation Menu**, Pagination, Popover, Progress, Radio Group, Resizable, Scroll Area, Select, **Separator**, **Sheet**, Skeleton, Slider, Sonner, Switch, Table, Tabs, Textarea, Toggle. Documentación: [Shadcnblocks Components](https://www.shadcnblocks.com/components).

### Planes Shadcnblocks

- **Free/Basic:** filtrar en la librería por “free” o “basic” (login sin pago para basic).
- **Pro:** $149 one-time — 1248+ Pro Blocks, 1189+ Component Variants, lifetime + updates.
- **Premium:** $299 — + Figma Kit, Admin Kit, 13 Templates.
- **Premium Team:** $599 — 10 seats, Organization Dashboard.

*Shadcnblocks.com no está afiliado oficialmente a shadcn/ui ni a Tailwind CSS. Proyecto por @ausrobdev.*

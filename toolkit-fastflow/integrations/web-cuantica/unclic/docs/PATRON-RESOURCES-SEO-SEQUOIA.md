# Patrón SEO — página hub Resources (sequoia.com)

**URL:** [https://www.sequoia.com/resources/](https://www.sequoia.com/resources/)

UnClic **no** copia contenido de Sequoia. Este documento resume **SEO y estructura on-page** del hub **Resources** (WordPress página `page-id-713`), a partir del HTML servido. Complementa el patrón de bloques en [PATRON-RESOURCES-HUB-SEQUOIA.md](PATRON-RESOURCES-HUB-SEQUOIA.md).

**Stack detectado:** Yoast SEO v27, Divi + child theme, Supreme Mega Menu / DSM, AddToAny, Bloomreach (`be_ixf`), GTM, Speculation Rules (prefetch).

---

## 1. Title, canonical y robots

| Campo | Valor (Resources) |
|-------|-------------------|
| **`<title>`** | `Resources – Sequoia` (entidad `&#8211;` = en dash) |
| **`link rel="canonical"`** | `https://www.sequoia.com/resources/` |
| **`meta robots`** | `index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1` |

**UnClic:** título tipo *«Recursos | UnClic»* o *«Guías, casos y blog — UnClic»*; canónica absoluta en `/recursos` (o slug definitivo).

---

## 2. Open Graph y Twitter

| Meta | Resources |
|------|-----------|
| **`og:locale`** | `en_US` |
| **`og:type`** | **`article`** (página WP tratada como artículo, igual que otras landings internas) |
| **`og:title`** | Alineado al `<title>` |
| **`og:url`** | `https://www.sequoia.com/resources/` |
| **`og:site_name`** | Sequoia |
| **`article:publisher`** | Facebook de la marca |
| **`article:modified_time`** | Ej. `2025-09-03T17:36:46+00:00` |

**Twitter**

- `twitter:card` = `summary_large_image`
- `twitter:site` = `@thesequoiateam`
- Yoast añade **tiempo de lectura:** `twitter:label1` / `twitter:data1` → *Est. reading time* / *4 minutes*

**Nota:** En el `<head>` analizado, **`og:image`** puede depender de imagen destacada de la página; si no hay, la red social puede elegir otra captura. **UnClic:** definir siempre `openGraph.images` para el hub (hero o asset 1200×630).

---

## 3. JSON-LD (`@graph` Yoast)

Nodos relevantes:

| Tipo | Uso en Resources |
|------|-------------------|
| **WebPage** | `@id` = URL del hub; **name** = título SEO; **isPartOf** → WebSite; **datePublished** / **dateModified**; **breadcrumb** → id del BreadcrumbList; **potentialAction** → **ReadAction** sobre la misma URL |
| **BreadcrumbList** | Posición 1: *Home* → `/`; posición 2: *Resources* (a veces **sin** `item` en el último nivel, solo `name`) |
| **WebSite** / **Organization** | Igual que en home; **SearchAction** en WebSite |

**UnClic:** `BreadcrumbList`: Inicio → Recursos (y si aplica “Insights” como padre intermedio).

---

## 4. Cuerpo visible — jerarquía y anclas (SEO + UX)

Del HTML del artículo `#post-713`:

1. **Un solo H1:** *Resources* (en hero, tema oscuro).
2. **Subtítulo** en párrafo (claim del hub).
3. **Navegación por anclas** (botones tipo pill): `#reports`, `#client-voices`, `#client-stories`, `#blog`, `#videos`; **Webinars** enlaza a **`/events/`** (página aparte, no ancla).
4. **Secciones** con **`id`** en el contenedor: `reports`, `client-voices`, `client-stories`, `blog`, `videos`.
5. **H2 de bloque** para títulos de sección (*Reports & Benchmarks*, *Client Voices*, etc.) + CTA *View All* a listados.
6. **Riesgo SEO — múltiples H2 en tarjetas:** En el grid de informes, cada tarjeta usa **`h2.card__text__title`** con el enlace al informe. Eso **multiplica H2** en la misma página. En **UnClic** conviene **un H2 por sección** y **H3** (o `div` con clase de título) para cada tarjeta, para una jerarquía más limpia.
7. **Casos cliente:** títulos en **`h3.client-success-title`** (mejor encaje semántico que los H2 de las tarjetas de informes).
8. **Blog:** carrusel con **`h2.post-excerpt__title`** por ítem — mismo tema: muchos H2 en una sola página.
9. **Prefooter:** bloque CTA con **H2** en componente gradiente + botón *Get Started*.

**Principio UnClic:** H1 = nombre del hub; H2 = solo nombres de sección; títulos de cards = H3 o elementos no-heading con `aria`-label razonable.

---

## 5. Otros elementos técnicos (referencia)

| Elemento | Efecto |
|----------|--------|
| **`speculationrules` prefetch** | Prefetch conservador de enlaces internos (excluye uploads, nofollow, etc.) |
| **AddToAny** | Compartir en redes en flotante y en tarjetas de vídeo |
| **Bloomreach `be-ix-link-block`** | Footer “Also of Interest” — enlaces relacionados automatizados |
| **JSON API** | `link rel="alternate" type="application/json"` → `wp-json/wp/v2/pages/713` |

---

## 6. Checklist — traducir a UnClic (`/recursos`)

| Sequoia Resources | UnClic |
|-------------------|--------|
| Title = *Hub + marca* | *Recursos* o *Centro de contenido \| UnClic* |
| `og:type` article + URL canónica | Igual para página hub estática o CMS |
| Breadcrumbs JSON-LD | Inicio → Recursos |
| H1 = nombre del hub | Un solo H1 |
| Anclas `#seccion` | Informes, voces, casos, blog, vídeos; webinars si es ruta propia |
| Evitar 10+ H2 en cards | H2 solo por sección; cards con H3 |
| OG image explícita | Imagen hero o asset dedicado |
| Tiempo de lectura (opcional) | Next.js: metadata o campo CMS |

---

## 7. Referencias cruzadas

- Bloques y mapeo contenido: [PATRON-RESOURCES-HUB-SEQUOIA.md](PATRON-RESOURCES-HUB-SEQUOIA.md)
- Home SEO: [PATRON-HOME-SEO-SEQUOIA.md](PATRON-HOME-SEO-SEQUOIA.md)
- Página solución (OG article): [PATRON-SOLUTIONS-ADVISORY-SEO-SEQUOIA.md](PATRON-SOLUTIONS-ADVISORY-SEO-SEQUOIA.md)
- IA general: [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md)

---

*Documento basado en HTML público de `/resources/` (Yoast, fechas y estructura visibles). Meta description puede existir fuera del fragmento analizado; conviene comprobar en el CMS.*

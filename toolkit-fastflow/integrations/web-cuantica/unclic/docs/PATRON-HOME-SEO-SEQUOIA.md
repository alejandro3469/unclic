# Patrón home + SEO — sequoia.com (referencia)

**URL:** [https://www.sequoia.com/](https://www.sequoia.com/)

UnClic **no** copia contenido de Sequoia. Este documento resume **cómo ordenan meta, datos estructurados y cabecera** en la home para SEO y redes, a partir del HTML real (WordPress + **Yoast SEO** + tema Divi).

---

## 1. Stack técnico relevante

| Elemento | Uso |
|----------|-----|
| `lang="en-US"` en `<html>` | Idioma explícito para buscadores y lectores. |
| **Yoast SEO** | Genera `title`, `description`, `canonical`, Open Graph, Twitter Card y **JSON-LD** en bloque coherente. |
| **robots** | `index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1` — favorece rich results de imagen y control de snippet. |
| Divi + mega menú | Marca la navegación; el logo lleva `alt` descriptivo. |

*(Otros scripts en head — Bloomreach `be:*`, reCAPTCHA, fuentes — son rendimiento/marketing; no son el núcleo del mensaje SEO on-page.)*

---

## 2. Title y meta description (orden lógico)

| Campo | Ejemplo home Sequoia | Patrón |
|-------|----------------------|--------|
| **`<title>`** | `Smarter people spend starts with Sequoia` | **Claim de valor + marca** (no solo “Inicio” o solo el nombre). |
| **`meta description`** | Una frase que nombra **quién son**, **para quién** (investor-backed companies) y **qué dominan** (compensation, benefits, risk). | **Una oración** con palabras clave principales; encaja con el H1/hero. |

**UnClic (equivalente):** título tipo *«Pipeline as Code y despliegue en un clic — UnClic»*; descripción que una Jenkins, Gitea, Docker/registry, retail/FastFlow en una línea.

---

## 3. Canonical y Open Graph

- **`link rel="canonical"`** → URL absoluta de la home (`https://www.sequoia.com/`).
- **OG:** `og:locale`, `og:type` = `website`, `og:title`, `og:description` (alineados a title/description), `og:url`, `og:site_name`, `og:image` + **`og:image:width`**, **`og:image:height`**, **`og:image:type`**.
- **`article:modified_time`** y **`article:publisher`** (Facebook) — refuerzan frescura y marca.

**UnClic:** en Next.js, `metadata.openGraph` y `metadata.alternates.canonical` con la URL pública real (HTTPS).

---

## 4. Twitter Card

- `twitter:card` = `summary_large_image`
- `twitter:site` = handle de la marca

Misma imagen grande que OG → previsualización consistente al compartir.

---

## 5. JSON-LD (`@graph`) — piezas que encajan

Yoast emite un **grafo** con varios nodos enlazados por `@id`:

| Tipo | Función SEO |
|------|-------------|
| **WebPage** | URL, nombre (= título SEO), descripción, `datePublished` / `dateModified`, imagen principal, idioma, `potentialAction` ReadAction. |
| **ImageObject** | URL de la imagen hero, dimensiones, **`caption`** (texto alternativo semántico para la imagen destacada). |
| **BreadcrumbList** | En home: un solo ítem “Home” — estructura mínima; en interiores sería la jerarquía real. |
| **WebSite** | Nombre del sitio, **descripción corta de marca** (“The Total People Investment Company”), **`SearchAction`** (`?s={search_term_string}`), publisher → Organization. |
| **Organization** | Nombre, URL, **logo** (ImageObject), **`sameAs`** (Facebook, X, Instagram, LinkedIn, YouTube). |

**UnClic:** equivalente razonable — `WebSite` + `Organization` (logo, `sameAs` a LinkedIn/GitHub si aplica) + `WebPage` en home; en páginas hijas, `BreadcrumbList` con Soluciones / Insights / Empresa, etc.

---

## 6. Cuerpo visible (orden para crawlers y accesibilidad)

Del HTML de la home:

1. **Cabecera** con logo enlazando a `/`, **`alt`** en la imagen del logo.
2. **`<nav itemscope itemtype="http://schema.org/SiteNavigationElement">`** — ítems con **`itemprop="name"`** (y `url` donde aplica): refuerzo semántico de la IA del sitio (Solutions, Capabilities, etc.).
3. Secciones posteriores en filas Divi (`et_pb_section`, `et_pb_row`): hero, bloques de valor, CTAs (el detalle de copy está en otros patrones UnClic).

**Principio:** un solo **H1** claro en hero (coherente con title/description); secciones con **H2** por bloque.

---

## 7. Checklist rápido — traducir a UnClic

| Sequoia | UnClic |
|---------|--------|
| Title = promesa + marca | Igual: valor + “UnClic”. |
| Description = 1 frase con ICP + oferta | Pipeline, CI/CD, retail, demos. |
| Canonical absoluto | `metadataBase` + path. |
| OG/Twitter + imagen con dimensiones | `openGraph.images` con URL estable (1200×630 si es posible). |
| JSON-LD WebSite + Organization | Logo, redes, nombre legal o comercial. |
| WebPage + fechas si el CMS lo permite | `dateModified` en artículos/landings que cambian. |
| Nav semántica o estructura clara | Header con enlaces crawlables (no solo `#` vacíos sin destino). |
| Alt en logo e imágenes hero | Texto útil, no keyword stuffing. |

---

## 8. Referencias cruzadas

- IA y navegación: [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md), [IA-SITIO-ESTILO-ENTERPRISE.md](IA-SITIO-ESTILO-ENTERPRISE.md).
- Metadata en código UnClic: `app/layout.tsx` + `lib/copy.ts`.

---

*Basado en HTML de home sequoia.com (Yoast SEO v27, snapshot con fechas de modificación y grafo Schema.org como arriba). Las URLs y textos de ejemplo son de Sequoia solo como referencia de patrón.*

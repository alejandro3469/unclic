# Patrón SEO — página About / Empresa (sequoia.com)

**URL:** [https://www.sequoia.com/company/about/](https://www.sequoia.com/company/about/)

UnClic **no** copia contenido de Sequoia. Este documento resume **SEO y estructura on-page** de la página **Company → About** (WordPress página `post-768`), a partir del HTML servido. Complementa el patrón de bloques en [PATRON-PAGINA-EMPRESA-SEQUOIA.md](PATRON-PAGINA-EMPRESA-SEQUOIA.md).

**Stack detectado:** Yoast SEO, Divi + child theme (DSM gradient text), AddToAny, Bloomreach (`be_ixf`), GTM, Speculation Rules (prefetch).

---

## 1. Title, meta description, canonical y robots

| Campo | Valor (About) |
|-------|----------------|
| **`<title>`** | `About Sequoia: The Total Comp & Benefits Company` (entidad `&amp;` = ampersand) |
| **`meta name="description"`** | `Sequoia comes through for investor-backed companies by helping them get the most out of their investment in people.` |
| **`link rel="canonical"`** | `https://www.sequoia.com/company/about/` |
| **`meta robots`** | `index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1` |

**UnClic:** título tipo *«Empresa \| UnClic»* o *«Sobre nosotros — UnClic»*; descripción única que resuma misión/posicionamiento en una línea; canónica absoluta en `/empresa` o `/nosotros`.

---

## 2. Open Graph y Twitter

| Meta | About |
|------|--------|
| **`og:locale`** | `en_US` |
| **`og:type`** | **`article`** (página tratada como artículo) |
| **`og:title`** | Alineado al `<title>` |
| **`og:description`** | Igual que `meta name="description"` |
| **`og:url`** | `https://www.sequoia.com/company/about/` |
| **`og:site_name`** | Sequoia |
| **`article:modified_time`** | Ej. `2026-03-02T17:59:13+00:00` |
| **`og:image`** | `https://www.sequoia.com/wp-content/uploads/2025/09/why-sequoia-stats.png` (imagen “by the numbers”) |

**Twitter**

- `twitter:card` = `summary_large_image`
- `twitter:site` = `@thesequoiateam`
- **Tiempo de lectura:** `twitter:label1` / `twitter:data1` → *Est. reading time* / *12 minutes*

**UnClic:** definir siempre `openGraph.images` para la página empresa (stats, hero o asset 1200×630).

---

## 3. JSON-LD (`@graph` Yoast)

Nodos relevantes:

| Tipo | Uso en About |
|------|----------------|
| **WebPage** | `@id` = URL de la página; **name** = título SEO; **isPartOf** → WebSite; **primaryImageOfPage** / **image** → ImageObject; **thumbnailUrl** = misma imagen stats; **datePublished** / **dateModified**; **breadcrumb** → id del BreadcrumbList; **potentialAction** → **ReadAction** sobre la misma URL |
| **ImageObject** | `@id` página + `#primaryimage`; **url** / **contentUrl** = why-sequoia-stats.png; **width** 942, **height** 797; **caption** = "Sequoia by the numbers" |
| **BreadcrumbList** | Posición 1: *Home* → `/`; posición 2: *Company* → `/company/`; posición 3: *Our Story* (último ítem, nombre de la página en nav) |
| **WebSite** / **Organization** | Igual que en home; **SearchAction** en WebSite; **sameAs** redes sociales |

**UnClic:** `BreadcrumbList`: Inicio → Empresa (o “Sobre nosotros”). Incluir **ImageObject** si la página tiene imagen destacada para OG/schema.

---

## 4. Cuerpo visible — jerarquía (SEO + UX)

Del HTML del artículo `#post-768`:

1. **Un solo H1:** *About Sequoia* (clase `et_pb_module_heading`, en hero).
2. **Primer H2** (subtítulo/claim): *Your trusted partner for total comp and benefits* (clase `dsm-gradient-text et_pb_module_header`).
3. **H2 de sección** (orden lógico): *Our legacy of innovation*, *Our client community*, *Careers*, *Culture*, *Commitment to service*, *Clients*, *Giving Back*, *Leadership*, *Sequoia Through the Years*, *Let's get more out of your investment in people* (CTA final).
4. **Línea de tiempo:** bloques por año con **H2** para el año y **H3** (o equivalente) para el hito/descripción — buena práctica: un H2 por “año” o grupo, H3 por evento.
5. **Prefooter:** bloque “Also of Interest” (`be-ix-link-block`, Bloomreach) — enlaces relacionados; no afecta jerarquía de headings.

**Riesgo SEO:** La página tiene **muchos H2** (más de 10) porque cada tarjeta de la rejilla (Careers, Culture, etc.) y cada sección usan H2. En **UnClic** se puede mantener un H2 por sección principal y usar **H3** para las tarjetas “explorar más” (Carreras, Cultura, Servicio, Clientes, Impacto, Liderazgo), de modo que la jerarquía quede: H1 → H2 por bloque narrativo / línea de tiempo / CTA → H3 en tarjetas.

**Principio UnClic:** H1 = nombre de la página (Empresa / Sobre nosotros); H2 = solo títulos de sección principal; tarjetas de la rejilla = H3.

---

## 5. Otros elementos técnicos (referencia)

| Elemento | Efecto |
|----------|--------|
| **Speculation Rules prefetch** | Prefetch de enlaces internos (comportamiento similar a Resources) |
| **AddToAny** | Compartir en redes en flotante o en bloques |
| **Bloomreach `be-ix-link-block`** | Footer “Also of Interest” — enlaces relacionados automatizados |
| **JSON API** | `link rel="alternate" type="application/json"` → endpoint WP de la página |

---

## 6. Checklist — traducir a UnClic (`/empresa` o `/nosotros`)

| Sequoia About | UnClic |
|---------------|--------|
| Title = *About + tagline/marca* | *Empresa \| UnClic* o *Sobre nosotros — UnClic* |
| Meta description única | Una línea: misión/posicionamiento (pipeline, entrega, confianza) |
| `og:type` article + URL canónica | Igual para página estática o CMS |
| Breadcrumbs JSON-LD | Inicio → Empresa |
| Imagen OG explícita | Stats, hero o asset 1200×630 |
| H1 = nombre de página | Un solo H1 (Empresa / Sobre nosotros) |
| H2 por sección principal | Narrativa, comunidad, rejilla, timeline, CTA |
| Tarjetas “explorar más” | H3 para cada tarjeta (Careers, Culture, etc.) |
| Timeline | H2 por año o grupo, H3 por hito |
| Tiempo de lectura (opcional) | Next.js: metadata o campo CMS (ej. 12 min) |

---

## 7. Referencias cruzadas

- Bloques y mapeo contenido: [PATRON-PAGINA-EMPRESA-SEQUOIA.md](PATRON-PAGINA-EMPRESA-SEQUOIA.md)
- Home SEO: [PATRON-HOME-SEO-SEQUOIA.md](PATRON-HOME-SEO-SEQUOIA.md)
- Hub Resources SEO: [PATRON-RESOURCES-SEO-SEQUOIA.md](PATRON-RESOURCES-SEO-SEQUOIA.md)
- Índice general: [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md)

---

*Documento basado en HTML público de `/company/about/` (Yoast, fechas y estructura visibles).*

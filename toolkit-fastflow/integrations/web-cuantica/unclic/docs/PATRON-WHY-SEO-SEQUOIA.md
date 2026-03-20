# Patrón SEO — página Why Sequoia / Por qué (sequoia.com)

**URL:** [https://www.sequoia.com/why-sequoia/](https://www.sequoia.com/why-sequoia/)

UnClic **no** copia contenido de Sequoia. Este documento resume **SEO y estructura on-page** de la página **Why Sequoia** (argumentario central de diferenciación), a partir del HTML servido. Complementa el patrón de bloques y mega menú en [PATRON-PAGINA-POR-QUE-SEQUOIA.md](PATRON-PAGINA-POR-QUE-SEQUOIA.md).

**Stack detectado:** Yoast SEO, Divi + child theme (DSM gradient text), AddToAny, Bloomreach (`be_ixf`), GTM, Speculation Rules (prefetch), Swiper.js (carruseles).

---

## 1. Title, meta description, canonical y robots

| Campo | Valor (Why Sequoia) |
|-------|----------------------|
| **`<title>`** | `Sequoia stands apart as a people partner` (claim diferenciador, no literal “Why Sequoia”) |
| **`meta name="description"`** | `Balancing what's precious to your people — like pay and healthcare — while fulfilling your promise to investors is what Sequoia does best.` |
| **`link rel="canonical"`** | `https://www.sequoia.com/why-sequoia/` |
| **`meta robots`** | `index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1` |

**UnClic:** título tipo *«Por qué UnClic — no cualquier socio de pipeline sirve»* o *«Por qué UnClic \| UnClic»*; descripción única que resuma promesa (velocidad, estabilidad, stack integrado) en una línea; canónica absoluta en `/por-que-unclic`.

---

## 2. Open Graph y Twitter

| Meta | Why Sequoia |
|------|--------------|
| **`og:type`** | **`article`** (página tratada como artículo) |
| **`og:title`** | Alineado al `<title>` — *Sequoia stands apart as a people partner* |
| **`og:description`** | Igual que `meta name="description"` |
| **`og:url`** | `https://www.sequoia.com/why-sequoia/` |
| **`og:site_name`** | Sequoia |
| **`article:publisher`** | `https://www.facebook.com/TheSequoiaTeam` |
| **`og:image`** | `https://www.sequoia.com/wp-content/uploads/2025/08/Coming-through-for-clients-two-decades.png` (imagen “coming through for clients” / dos décadas) |

**Twitter**

- `twitter:card` = `summary_large_image`
- `twitter:site` = `@thesequoiateam`
- **Tiempo de lectura:** `twitter:label1` / `twitter:data1` → *Est. reading time* / *8 minutes*

**UnClic:** definir siempre `openGraph.images` para la página por-qué (hero, cifras o asset 1200×630).

---

## 3. JSON-LD (`@graph` Yoast)

Nodos relevantes (estructura típica Yoast para esta página):

| Tipo | Uso en Why Sequoia |
|------|---------------------|
| **WebPage** | `@id` = URL de la página; **name** = título SEO; **isPartOf** → WebSite; **primaryImageOfPage** / **image** → ImageObject; **datePublished** / **dateModified**; **breadcrumb** → id del BreadcrumbList; **potentialAction** → **ReadAction** sobre la misma URL |
| **ImageObject** | Imagen OG (Coming-through-for-clients-two-decades.png); **width** / **height**; **caption** si aplica |
| **BreadcrumbList** | Posición 1: *Home* → `/`; posición 2: *Why Sequoia* (o “Our Approach”) — último ítem = nombre de la página en nav |
| **WebSite** / **Organization** | Igual que en home; **SearchAction** en WebSite; **sameAs** redes sociales |

**UnClic:** `BreadcrumbList`: Inicio → Por qué UnClic. Incluir **ImageObject** si la página tiene imagen destacada para OG/schema.

---

## 4. Cuerpo visible — jerarquía (SEO + UX)

Del HTML de la página Why Sequoia:

1. **Eyebrow (H6):** *Why Sequoia* (clase `et_pb_module_heading`).
2. **Un solo H1:** *Not just any people partner will do* (clase `dsm-gradient-text et_pb_module_header`).
3. **Bloque “One Team, One Integrated Platform”:** eyebrow **H6** + **H2** *Expertise backed by platform power* (y subtítulos equivalentes).
4. **Secciones posteriores:** **H2** por bloque principal (Advisory + Platform, Legacy, Any Size, Employee Experience, Impact, etc.) — alineado con las anclas del mega menú Our Approach.
5. **Tarjetas (soluciones, segmentos, blog destacado):** en la referencia aparecen **H4** para títulos de tarjeta en algunos bloques — mejor que abusar de H2; mantener en UnClic **H2** solo para títulos de sección y **H3**/H4 para tarjetas.
6. **Carruseles:** Swiper.js para bloques tipo “featured from blog” o listados; no cambian la jerarquía de headings.
7. **Prefooter / Bloomreach:** bloques `be-ix-link-block` para enlaces relacionados; no afectan jerarquía.

**Riesgo SEO:** Si en algún bloque se usan muchos H2 (p. ej. una tarjeta por H2), conviene en **UnClic** un **H2 por sección** y **H3** (o H4) para cada tarjeta, de modo que la jerarquía quede: H1 → H2 por bloque narrativo / ancla → H3/H4 en tarjetas.

**Principio UnClic:** H1 = claim principal (Por qué UnClic); H2 = solo títulos de sección con `id` para anclas (`#stack-integrado`, `#trayectoria`, `#escala`, etc.); tarjetas = H3.

---

## 5. Otros elementos técnicos (referencia)

| Elemento | Efecto |
|----------|--------|
| **Speculation Rules prefetch** | Prefetch de enlaces internos (comportamiento similar a Resources / About) |
| **AddToAny** | Compartir en redes en flotante o en bloques |
| **Bloomreach `be-ix-link-block`** | Enlaces relacionados / “Also of Interest” |
| **Swiper** | Carruseles (blog destacado, listados) — accesibilidad y lazy-load de imágenes |
| **JSON API** | `link rel="alternate" type="application/json"` → endpoint WP de la página |

---

## 6. Checklist — traducir a UnClic (`/por-que-unclic`)

| Sequoia Why | UnClic |
|-------------|--------|
| Title = claim diferenciador (no literal “Why”) | *Por qué UnClic \| UnClic* o *No cualquier socio de pipeline sirve — UnClic* |
| Meta description única | Una línea: promesa (stack integrado, velocidad, estabilidad, confianza) |
| `og:type` article + URL canónica | Igual para página estática o CMS |
| Breadcrumbs JSON-LD | Inicio → Por qué UnClic |
| Imagen OG explícita | Hero, “cifras” o asset 1200×630 |
| H1 = claim principal | Un solo H1 (Por qué UnClic / claim) |
| H2 por sección + `id` ancla | Enfoque, stack integrado, trayectoria, escala, experiencia equipo, impacto |
| Tarjetas (soluciones, blog destacado) | H3 (o H4) para cada tarjeta |
| Mega menú “Why” → anclas | Deep links a `#stack-integrado`, `#trayectoria`, etc. |
| Tiempo de lectura (opcional) | Next.js: metadata o campo CMS (ej. 8 min) |

---

## 7. Referencias cruzadas

- Bloques y mega menú Our Approach: [PATRON-PAGINA-POR-QUE-SEQUOIA.md](PATRON-PAGINA-POR-QUE-SEQUOIA.md)
- About SEO: [PATRON-ABOUT-SEO-SEQUOIA.md](PATRON-ABOUT-SEO-SEQUOIA.md)
- Hub Resources SEO: [PATRON-RESOURCES-SEO-SEQUOIA.md](PATRON-RESOURCES-SEO-SEQUOIA.md)
- Índice general: [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md)

---

*Documento basado en HTML público de `/why-sequoia/` (Yoast, estructura y meta visibles).*

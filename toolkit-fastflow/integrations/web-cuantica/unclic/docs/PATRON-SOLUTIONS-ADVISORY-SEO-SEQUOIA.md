# Patrón página Solución (Advisory) + SEO — sequoia.com

**URL:** [https://www.sequoia.com/solutions/advisory/](https://www.sequoia.com/solutions/advisory/)

UnClic **no** copia contenido de Sequoia. Este documento resume **SEO y estructura on-page** de una **página hija de Solutions** (Advisory & Brokerage), en línea con la home ([PATRON-HOME-SEO-SEQUOIA.md](PATRON-HOME-SEO-SEQUOIA.md)) y el orden de bloques ([PATRON-PAGINA-SOLUCION-SEQUOIA.md](PATRON-PAGINA-SOLUCION-SEQUOIA.md)).

**Nota:** el sitio suele servirse tras Cloudflare; el **`<title>`** visible en resultados de búsqueda es coherente con Yoast (*oferta + “from Sequoia”*). El resto se infiere del **mismo stack** (WordPress + Yoast + Divi) que la home.

---

## 1. Title y meta description (página de solución)

| Campo | Patrón Advisory (Sequoia) | Lógica |
|-------|---------------------------|--------|
| **`<title>`** | Tipo *«Comp & Benefits Advisory & Brokerage from Sequoia»* | **Nombre de la oferta + marca** (más literal que la home, que usa solo claim + marca). |
| **`meta description`** | Resume **asesoría + plataforma + ICP** (empresas con inversión, comp, benefits, riesgo). | **Una oración** alineada al H1 y al eyebrow; keywords de la vertical. |

**UnClic (`/soluciones/asesoria` o similar):** título *«Asesoría en pipeline y CI/CD — UnClic»* o *«Pipeline as Code y arquitectura de entrega | UnClic»*; descripción que una Jenkins, Gitea, Docker/registry, retail/FastFlow y **tipo de solución** (asesoría, no solo producto).

---

## 2. Diferencias respecto a la home (Open Graph)

En páginas **singulares** de WordPress, Yoast suele usar:

| Meta | Home | Página Advisory (típico) |
|------|------|---------------------------|
| **`og:type`** | `website` | **`article`** (página/post) |
| **`og:url`** | `/` | URL absoluta **`/solutions/advisory/`** |
| **`article:modified_time`** | Sí | Sí — **fecha de última edición** de la página |
| **`article:publisher`** | Facebook de la marca | Igual |

Imagen OG: hero o imagen destacada de la página, con **`og:image:width`** / **`height`** / **`type`** cuando la imagen está definida en el CMS.

**UnClic:** en Next.js, rutas `/soluciones/*` pueden usar `openGraph.type: 'article'` y `publishedTime` / `modifiedTime` si el contenido es estático versionado o CMS.

---

## 3. Canonical y robots

- **`link rel="canonical"`** → URL absoluta de la página de advisory (sin duplicar con parámetros UTM).
- **`robots`:** mismo patrón que home (`index, follow, max-image-preview:large, …`) salvo que la plantilla marque `noindex` (no es el caso en páginas públicas de solución).

---

## 4. Twitter Card

- `twitter:card` = `summary_large_image`
- `twitter:site` = handle de la marca  
Misma imagen que OG para compartir enlace a **una solución concreta**.

---

## 5. JSON-LD (`@graph`) — interior vs home

Yoast mantiene el **grafo** con nodos reutilizables (**Organization**, **WebSite**) y añade/ajusta:

| Tipo | Función en Advisory |
|------|---------------------|
| **WebPage** | `@id` de la URL advisory; **nombre** = título SEO; descripción; fechas; imagen principal; idioma; a veces **ReadAction**. |
| **ImageObject** | Imagen destacada con **`caption`**. |
| **BreadcrumbList** | **Jerarquía real:** p. ej. *Home* → *Solutions* (si existe página padre) → *Advisory & Brokerage* — no un solo ítem como en home. |
| **WebSite** / **Organization** | Referenciados desde WebPage; **SearchAction** sigue en WebSite. |

**UnClic:** `BreadcrumbList`: Inicio → Soluciones → Asesoría & pipeline (o el slug final).

---

## 6. Cuerpo visible (SEO + accesibilidad)

Del contenido público de la página Advisory:

1. **Eyebrow** de categoría (`COMP + BENEFITS + RISK` en Sequoia) — refuerzo semántico antes del H1.
2. **H1** orientado a **prueba social + promesa** (*“There's a reason 2,500+ companies…”*) — puede ser **más largo** que el `<title>`; el title SEO sigue siendo la etiqueta principal en SERP.
3. **Métricas** (clientes, países, NPS, años) — datos estructurados visualmente; opcionalmente **FAQ** o **ItemList** si el CMS lo añade.
4. **H2** por bloque: plataforma integrada, pilares (“Improve executive decision making”, etc.), tarjetas hijas, testimonios, CTA.
5. **Nav** con `SiteNavigationElement` / enlaces crawlables a otras soluciones y al hub Resources.

**Principio:** un **solo H1**; **H2** por sección; CTAs con texto accionable (“Why Sequoia”, “Talk to an Advisor”).

---

## 7. Checklist — traducir a UnClic (`/soluciones/...`)

| Sequoia Advisory | UnClic |
|------------------|--------|
| Title = oferta + marca | Slug claro + “UnClic” o marca. |
| Description = ICP + asesoría + stack | Retail, pipeline, demos, Docker/registry si aplica. |
| `og:type` article + URL canónica | Igual para landings de solución. |
| Breadcrumbs en JSON-LD | Inicio → Soluciones → [Oferta]. |
| H1 emocional/prueba; title más “SEO” | Misma separación opcional. |
| Eyebrow de línea de negocio | p. ej. **ASESORÍA & PIPELINE**. |

---

## 8. Referencias cruzadas

- Home SEO: [PATRON-HOME-SEO-SEQUOIA.md](PATRON-HOME-SEO-SEQUOIA.md)
- Bloques de página: [PATRON-PAGINA-SOLUCION-SEQUOIA.md](PATRON-PAGINA-SOLUCION-SEQUOIA.md)
- IA general: [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md)

---

*Referencia de patrón; textos y cifras de ejemplo son de Sequoia solo como ilustración. Title de advisory alineado a resultados indexados públicos; meta internas pueden variar ligeramente según versión del CMS.*

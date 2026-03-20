# Patrón hub “Resources” (tipo Sequoia)

**Referencia:** [sequoia.com/resources](https://www.sequoia.com/resources/) — página **índice** de todo el contenido “Learn / Insights”: informes, voces de cliente, historias, blog, vídeos, webinars.

**SEO (title, OG article, breadcrumbs, H1 vs H2 en cards):** [PATRON-RESOURCES-SEO-SEQUOIA.md](PATRON-RESOURCES-SEO-SEQUOIA.md).

Sirve para una ruta UnClic tipo **`/recursos`** o **`/insights`** que agrupe guías, casos, blog y multimedia. **No copiar copy ni sector HR**; solo **estructura de secciones y ritmo visual**.

---

## 1. Hero del hub

| Sequoia | UnClic |
|---------|--------|
| **H1** “Resources” | **Recursos** o **Centro de contenido** |
| Subtítulo: hub para guidance, insights, tools, inspiration | **Tu hub de guías, tendencias de pipeline, plantillas y material para despliegues más sólidos** |
| **Anclas horizontales** (sticky o bajo hero): Reports & Benchmarks · Client Voices · Client Stories · Blog · Videos · Webinars | **Informes y guías** · **Voces (vídeo)** · **Casos de cliente** · **Blog** · **Vídeos** · **Webinars** — cada una hace scroll a `#informes`, `#voces`, etc. |

---

## 2. Patrón de cada sección (se repite)

Cada bloque sigue el mismo molde:

1. **Título de sección** (H2) — ej. *Reports & Benchmarks*
2. Enlace **Ver todo** → lista filtrada o categoría (`/recursos/informes`, `/blog`, etc.)
3. **Grid 3 columnas** (desktop) de tarjetas destacadas:
   - **Imagen o portada**
   - **Título** del recurso
   - **2–3 líneas** de descripción
   - **CTA** de acción única: *Get the Report* → en UnClic *Descargar guía* / *Leer informe* / *Ver PDF*

Opcional: en móvil, carrusel con **prev / next** y contador (*slide 1 de 5*).

---

## 3. Mapeo sección → contenido UnClic

| Sección Sequoia | Rol | Equivalente UnClic |
|-----------------|-----|-------------------|
| **Reports & Benchmarks** | PDFs / estudios / playbooks | **Guías y checklists** — Jenkins desde cero, pipeline + registry, checklist transferencia repo, plan demo FastFlow (enlaces a `docs/` o PDFs empaquetados) |
| **Client Voices** | Clips cortos, testimonio en vídeo | **Voces en vídeo** — demo Jenkins en 2 min, “por qué Git + Jenkins”, clip de retail |
| **Client Stories** | Casos largos escritos | **Casos de cliente** — retail/cadena, migración a pipeline as code, de PEO/manual a claridad (adaptar a historias reales cuando existan) |
| **Blog** | Artículos largos | Mismo patrón que [PATRON-ARTICULO-INSIGHTS-SEQUOIA.md](PATRON-ARTICULO-INSIGHTS-SEQUOIA.md) — carrusel de últimos posts |
| **Videos** | Tutoriales, eventos, producto | **Vídeos** — walkthrough hub, open enrollment paralelo = *cómo registrar job en Jenkins*, recap de meetup |
| **Webinars** | Eventos en vivo o grabados | **Webinars / sesiones** — “Pipeline seguro en AWS”, calendario o grabaciones en YouTube/Vimeo |

---

## 4. Página intermedia “Insights” (sub-índice)

En algunos flujos, **Insights** abre un **sub-hub** con **tarjetas grandes** (una por canal):

| Tarjeta Sequoia | UnClic |
|-----------------|--------|
| Blog | → `/blog` o ancla a sección blog en `/recursos` |
| Resources | → `/recursos` (esta página) |
| Client Success | → `#casos` o `/recursos/casos` |
| Reports & Benchmarking | → `#informes` |
| Webinars | → `#webinars` |
| Videos | → `#videos` |

Útil si el mega menú **Insights** tiene muchas entradas y quieres **una sola pantalla de elección** antes del listado largo.

---

## 5. CTA final del hub

| Sequoia | UnClic |
|---------|--------|
| “Personalized comp and benefits guidance? … Get Started” | **¿Quieres alinear pipeline y negocio?** — **Habla con un asesor** / **Comenzar** → `#contacto` o `#hub-demos` |

---

## 6. Orden sugerido en `/recursos` (scroll único)

1. Hero + anclas  
2. **Informes y guías** (3 destacados + Ver todo)  
3. **Voces en vídeo** (3)  
4. **Casos de cliente** (3)  
5. **Blog** (carrusel 5+ entradas)  
6. **Vídeos** (3–6 thumbnails)  
7. **Webinars** (próximos + grabados, si aplica)  
8. CTA asesor / demo  

---

## Enlaces Sequoia (solo formato)

- [Resources hub](https://www.sequoia.com/resources/)  
- [Client success](https://www.sequoia.com/resources/client-success/) (ejemplo de sub-ruta)  
- [Blog](https://www.sequoia.com/blog/)

Relacionados: [PATRON-ARTICULO-INSIGHTS-SEQUOIA.md](PATRON-ARTICULO-INSIGHTS-SEQUOIA.md) · [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md) · footer columna **Recursos** en [IA-SITIO-ESTILO-ENTERPRISE.md](IA-SITIO-ESTILO-ENTERPRISE.md).

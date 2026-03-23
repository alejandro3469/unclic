# Referencia de estructura: Sequoia.com

**URL oficial:** [https://www.sequoia.com](https://www.sequoia.com)

UnClic **no** está asociado a Sequoia. Tomamos **sequoia.com como base** de jerarquía, navegación y patrón de páginas.

---

## Base de diseño (Sequoia)

- **Jerarquía de contenido:** Eyebrow (categoría) → H1 → subtítulo/lead → métricas o trust strip → bloques narrativos → tarjetas → CTA final.
- **Navegación:** Solutions / Capabilities / Insights = Soluciones / Capacidades / Insights (dropdowns); Company = Empresa; CTA principal = Comenzar; Log In = Iniciar sesión.
- **Footer:** Cuatro columnas: SOLUTIONS, CAPABILITIES, LEARN, CONNECT (mapeado en `footerNav`).
- **Tono:** Enterprise, limpio, un CTA principal por sección. Bloques de Shadcn Blocks se rellenan con nuestro copy siguiendo esta jerarquía.
- **Tema visual:** UnClic usa tokens tipo shadcn (Nova/Inter + zinc) y Nord opcional; la *estructura* de secciones y el orden nav/footer siguen a Sequoia.

---

## Navegación principal (home)

| Sequoia | UnClic |
|---------|--------|
| Solutions | **Soluciones** (dropdown) |
| Capabilities | **Capacidades** (dropdown) |
| Insights | **Insights** (dropdown) |
| Company | **Empresa** → `#contacto` o **`/empresa`** (página About cuando exista) |
| Why Sequoia | **Por qué UnClic** → `#why` o **`/por-que-unclic`** (página larga) |
| Get Started | **Comenzar** → `#hub-demos` |
| **Log In** | Dropdown multi-portal → ver [PATRON-LOGIN-Y-PORTALES-SEQUOIA.md](PATRON-LOGIN-Y-PORTALES-SEQUOIA.md); UnClic: hoy suele bastar un acceso; con Jenkins+Gitea, dropdown por audiencia |

---

## Solutions: mega menú (ejemplo Advisory)

Página interna tipo: [sequoia.com/solutions/advisory](https://www.sequoia.com/solutions/advisory/)

- Eyebrow categoría → H1 → métricas → bloque narrativo → tarjetas hijas → testimonios → CTA.

Plantillas UnClic: [PATRON-PAGINA-SOLUCION-SEQUOIA.md](PATRON-PAGINA-SOLUCION-SEQUOIA.md) — las **cuatro Solutions**: Advisory, Investor, Outsourcing & PEO, **Platform** ([/solutions/platform](https://www.sequoia.com/solutions/platform/)).

---

## Footer (Explore)

| Columna Sequoia | UnClic |
|-----------------|--------|
| **SOLUTIONS** (4 ofertas) | Asesoría & pipeline, Retail, Despliegue, Hub |
| **CAPABILITIES** (5 áreas) | Pipeline as Code, Jenkins & Gitea, Docker, AWS, Arquitectura viva |
| **LEARN** | Galería, Vídeo, Audio, Presencia, Para quién |
| **CONNECT** | Empresa/contacto, Por qué UnClic, Acceso demo |

---

## Otros enlaces útiles

| Recurso | URL |
|---------|-----|
| Why Sequoia | https://www.sequoia.com/why-sequoia/ |
| Client success | https://www.sequoia.com/resources/client-success/ |
| Blog | https://www.sequoia.com/blog/ |
| Resources | https://www.sequoia.com/resources/ |
| About / Company | https://www.sequoia.com/company/about/ |
| Formulario asesor | https://www.sequoia.com/form/consultation/ |

Mapeo home completo: [IA-SITIO-ESTILO-ENTERPRISE.md](IA-SITIO-ESTILO-ENTERPRISE.md).

**Home — SEO (title, OG, Twitter, JSON-LD, nav semántica):** [PATRON-HOME-SEO-SEQUOIA.md](PATRON-HOME-SEO-SEQUOIA.md).

**Página Solución Advisory — SEO (og:article, breadcrumbs, title vs H1):** [PATRON-SOLUTIONS-ADVISORY-SEO-SEQUOIA.md](PATRON-SOLUTIONS-ADVISORY-SEO-SEQUOIA.md) — [solutions/advisory](https://www.sequoia.com/solutions/advisory/).

**Artículos (Insights / blog):** [PATRON-ARTICULO-INSIGHTS-SEQUOIA.md](PATRON-ARTICULO-INSIGHTS-SEQUOIA.md).

**Hub Resources (informes, voces, casos, blog, vídeo, webinars):** [PATRON-RESOURCES-HUB-SEQUOIA.md](PATRON-RESOURCES-HUB-SEQUOIA.md) — [sequoia.com/resources](https://www.sequoia.com/resources/).

**Hub Resources — SEO (og:article, breadcrumbs, anclas, jerarquía H2/H3 en cards):** [PATRON-RESOURCES-SEO-SEQUOIA.md](PATRON-RESOURCES-SEO-SEQUOIA.md).

**Página Empresa / About:** [PATRON-PAGINA-EMPRESA-SEQUOIA.md](PATRON-PAGINA-EMPRESA-SEQUOIA.md) — [company/about](https://www.sequoia.com/company/about/). **About — SEO (title, OG, breadcrumbs, jerarquía H2/H3):** [PATRON-ABOUT-SEO-SEQUOIA.md](PATRON-ABOUT-SEO-SEQUOIA.md).

**Página Por qué (Why):** [PATRON-PAGINA-POR-QUE-SEQUOIA.md](PATRON-PAGINA-POR-QUE-SEQUOIA.md) — [why-sequoia](https://www.sequoia.com/why-sequoia/) (incluye **mega menú Our Approach** + anclas + blog destacado). **Why — SEO (title, OG, breadcrumbs, jerarquía H2/H3):** [PATRON-WHY-SEO-SEQUOIA.md](PATRON-WHY-SEO-SEQUOIA.md).

**Hub Capabilities (mega menú + pillar + página hija):** [PATRON-HUB-CAPACIDADES-SEQUOIA.md](PATRON-HUB-CAPACIDADES-SEQUOIA.md) — [total-comp-benefits](https://www.sequoia.com/total-comp-benefits/) · [analytics (hija)](https://www.sequoia.com/total-comp-benefits/analytics/).

**Log In multi-portal (cliente / comunidad / partners / empleado):** [PATRON-LOGIN-Y-PORTALES-SEQUOIA.md](PATRON-LOGIN-Y-PORTALES-SEQUOIA.md).

**Docker + registry (soluciones / stack):** [DOCKER-Y-REGISTRY-UNClic.md](DOCKER-Y-REGISTRY-UNClic.md) — [Hub registry oficial](https://hub.docker.com/_/registry), [Docker Docs](https://docs.docker.com/).

**Formulario «Habla con asesor»:** [PATRON-FORMULARIO-ASESOR-SEQUOIA.md](PATRON-FORMULARIO-ASESOR-SEQUOIA.md) — [form/consultation](https://www.sequoia.com/form/consultation/).

**LinkedIn página empresa:** [PATRON-LINKEDIN-PAGINA-EMPRESA-SEQUOIA.md](PATRON-LINKEDIN-PAGINA-EMPRESA-SEQUOIA.md) — [sequoia-tpi](https://www.linkedin.com/company/sequoia-tpi/).  
**LinkedIn perfil personal** (misma lógica de secciones): [GUIA-LINKEDIN-PERSONAL-ESTRUCTURA-SEQUOIA.md](GUIA-LINKEDIN-PERSONAL-ESTRUCTURA-SEQUOIA.md).

**Logo UnClic (prompts Gemini + asset):** [PROMPTS-GANADORES-LOGO-GEMINI-UNClic.md](PROMPTS-GANADORES-LOGO-GEMINI-UNClic.md).

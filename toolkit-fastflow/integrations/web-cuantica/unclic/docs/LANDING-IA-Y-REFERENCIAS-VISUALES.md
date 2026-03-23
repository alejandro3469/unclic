# Landing UnClic — IA, navegación y referencias visuales

## Qué cambió (resumen)

- **Jerarquía tipo agencia** ([Phenomenon Studio](https://phenomenonstudio.com), [Sequoia](https://www.sequoia.com)): mega-menú en tres columnas — **Servicios** (oferta), **Stack OSS** (técnico), **Explorar** (marca + contenido + contacto/login).
- **Anclas footer/nav/chips**: hashes alineados a `id` en DOM; **`lib/routes.ts`** define rutas base y ancladas (`routes.solucionesHub`, `routes.integracionesAtlas`, `routes.contactForm`, `routes.legalDemosTerminos`, etc.) — úsalo en copy y componentes. Helper **`hrefLoginNext(path)`** para redirigir a login con `next` codificado (p. ej. `DemoGate`). `sectionIds` en `lib/copy.ts` documenta ids usados en UI.
- **Páginas profundas** con intro + chips: `/soluciones`, `/capacidades`, `/empresa` (**`subnav`** + `Hero2` `#empresa-hero`), `/integraciones` (`ossAtlas` + `#oss-atlas`), **`/contacto`**, **`/insights`**, **`/presupuesto-oss`** (`#presupuesto-wizard`, `#presupuesto-api`), **`/demo/access`** (`#demo-access-form` + cliente en `demo-access-client.tsx`), **`/legal/acceso-demos`** (`legalAccesoDemosLead` + `#terminos` / `#datos`), **`/signup`** (`#signup-form` + `signup-client.tsx`). Metadatos unificados en **`pagesMeta`**; textos de intro en `*PageIntro` en `lib/copy.ts` (presupuesto: título/lead siguen en **`copy-presupuesto-oss.ts`**).
- **Home en capas**: Hero (CTA múltiples + gradiente) → **tres pilares** (dolor → solución) → **banda manifesto OSS** (filosofía) → **fases 01–03** (validar / construir / operar) → logos → teaser atlas → CTA final.
- **Tono** alineado a [Vates](https://vates.tech/en/) y a su artículo [“The Open Source we use at Vates: 2025 edition”](https://vates.tech/blog/the-open-source-we-use-at-vates-2025-edition/): prioridad open source / autoalojado, soberanía, coherencia entre lo que enseñas y lo que operas — **sin copiar marca ni textos ajenos**.

## Dónde editar

| Qué | Dónde |
|-----|--------|
| Navegación y pie | `lib/copy.ts` → `nav`, `navDropdowns`, `footerNav`, `footer` |
| Hero, SEO, banner | `lib/copy.ts` → `hero`, `seo`, `banner`, `site`, `trustStrip` |
| Secciones nuevas home | `lib/copy.ts` → `homePillars`, `homeOpenSource`, `homeJourney` |
| Acentos visuales (gradientes) | `app/globals.css` → `.text-gradient-impact`, `.btn-impact`, `.bg-section-manifesto` |
| Orden de bloques en home | `app/page.tsx` |
| Intro + anclas | `page-intro-with-anchors.tsx` + `*PageIntro` (`contacto`, `insights`, `presupuestoOssPageIntro` solo chips, etc.) |
| Títulos SEO profundos | `pagesMeta` en `lib/copy.ts` |

## Nota

Los sitios de referencia son **inspiración de estructura y ritmo visual**; UnClic mantiene **FastFlow, Jenkins, Gitea, portal, presupuesto OSS** y el mensaje propio.

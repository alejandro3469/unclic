# Arquitectura del sitio al estilo enterprise (UnClic)

Cómo suelen **organizar el contenido** las webs B2B / SaaS de nivel enterprise, y cómo **alinear UnClic** con ese patrón usando **nuestro contenido** (Pipeline as Code, demos, FastFlow, hub técnico).

**Referencia elegida:** [Sequoia](https://www.sequoia.com/) (estructura y tono, no sector HR). La home y el nav del sitio UnClic están alineados a ese patrón; detalle en la tabla siguiente.

---

## Sequoia → UnClic (qué reflejamos)

| Bloque Sequoia | Equivalente UnClic (copy + UI) |
|----------------|--------------------------------|
| **COMP + BENEFITS + RISK** (eyebrow) | **PIPELINE + REGISTRY + DEPLOY** |
| Lead “There’s a reason…” | “Hay una razón por la que equipos con operación crítica…” |
| Párrafo “two decades…” | Enfoque en código, retail/cadena, Jenkins/Gitea/hub |
| Métricas (clientes, países, NPS, años) | Hub 1 · 12+ enlaces · 4 regiones demo · pipeline 100% Git |
| “One team… integrated platform” | “Un solo equipo, un stack integrado” + partnerTitle |
| **Why Sequoia** (4 pilares) | **Por qué UnClic** — decisiones, costos, experiencia equipo, protección negocio |
| “People solutions for any size…” | **Soluciones de pipeline para cualquier equipo…** |
| Tarjetas *Because a partner should…* | 4 tarjetas con **Porque…** (asesoría, registry, demos, plataforma documentada) |
| Client stories | Galería / flujos / casos de uso (misma posición aprox.) |
| **Latest Insights & Resources** | **Ideas y contenido reciente** (carrusel / social) |
| “Let’s get more out of your investment in people” | “Saquemos más partido a tu inversión en entrega de software” |
| Nav: Solutions, Capabilities, Insights… | **Soluciones** · **Capacidades** · **Recursos** · **Por qué UnClic** · Contacto · **Comenzar** |
| Footer: Explore / Learn / Connect | Columnas **Soluciones** · **Capacidades** · **Recursos** · **Conectar** |

---

## 1. Patrones comunes en sitios enterprise

### Cabecera (header)

| Patrón | Qué suele haber |
|--------|------------------|
| **Producto** | Desplegable: capacidades, plataforma, integraciones, seguridad |
| **Soluciones** | Por industria (retail, finanzas) o por caso de uso |
| **Recursos** | Blog, documentación, webinars, ebooks, estado del servicio |
| **Precios** | Planes o “Contactar ventas” |
| **Empresa** | Nosotros, carreras, prensa, partners |
| **CTAs** | Iniciar sesión / Probar / Solicitar demo / Contacto |

Muchas marcas unifican **Product + Platform** en un mega-menú con columnas (títulos + enlaces cortos).

### Página de inicio (home)

Orden típico de bloques (de arriba abajo):

1. **Hero** — promesa + 2 CTAs (primario secundario) + prueba social o métrica.
2. **Logos** — “Confían en nosotros” (si aplica).
3. **Propuesta de valor** — 3–4 pilares con icono + título + una línea.
4. **Producto en profundidad** — una fila por capacidad o screenshot.
5. **Soluciones / para quién** — segmentos o casos de uso.
6. **Cómo funciona** — 3 pasos o diagrama.
7. **Prueba / demo** — enlace claro a ver el producto.
8. **Testimonios o casos** (opcional).
9. **Precios teaser** o “Hablemos”.
10. **FAQ** + **CTA final** + **footer denso**.

### Páginas internas (no todo en una sola scroll)

| Tipo de página | Contenido típico |
|----------------|------------------|
| **/product** o **/platform** | Visión del producto, subpáginas por módulo |
| **/solutions** | Una URL por industria o por problema |
| **/pricing** | Tabla, comparativa, FAQ de facturación |
| **/resources** o **/blog** | Listado + categorías |
| **/docs** | Enlace externo o subdominio |
| **/company** o **/about** | Misión, equipo, contacto |
| **/contact** o **/demo** | Formulario o Calendly |

### Pie (footer)

Columnas frecuentes:

- **Producto** (enlaces a mismas rutas que el mega-menú)
- **Recursos** (docs, blog, estado, API)
- **Empresa** (legal, privacidad, careers)
- **Redes** + selector de idioma/región

---

## 2. Mapeo: qué tenemos hoy (UnClic) → equivalente enterprise

| Contenido actual (home) | Equivalente “enterprise” |
|-------------------------|---------------------------|
| Hero + banner | Hero + barra superior (avisos / CTA) |
| Hub de demos | Bloque “Platform overview” o página **/demos** |
| Icon cloud (stack) | Subsección dentro de **Producto** o **Plataforma** |
| Por qué / Features | **/product** — “Why” + “Capabilities” |
| Flujos / diagramas | **/platform** o **/how-it-works** |
| Arquitectura Cloudcraft | **/architecture** o ancla dentro de Producto |
| Cómo empezar | **/get-started** o sección estándar |
| Precios | Ya encaja con **/pricing** (ya existe ruta posible vía nav) |
| Galería / vídeo / audio | **/resources** o “Media” |
| Cliente ideal | **/solutions** — “Para retail / alto volumen” |
| FAQ + CTA | Pie de home + **/pricing#faq** o **/contact** |

Nada de esto obliga a copiar textos ajenos: solo **orden y jerarquía** parecidos; **copy y demos** siguen siendo UnClic.

---

## 3. Estructura objetivo recomendada (fases)

### Fase A — Sin romper la home actual

- Renombrar agrupaciones en **header** al estilo enterprise: **Producto** (anclas), **Demos** (#hub-demos), **Precios**, **Recursos** (blog/docs externos), **Contacto**.
- Footer en **4 columnas** explícitas (Producto | Demos | Recursos | Legal/Redes).

### Fase B — Multipágina (Next.js)

Rutas sugeridas alineadas al patrón enterprise:

| Ruta | Contenido |
|------|-----------|
| `/` | Home recortada: hero, logos (opcional), 3 pilares, teaser demos, CTA |
| `/product` | Por qué + features + flujos resumidos |
| `/demos` | Hub completo + credenciales + enlaces |
| `/solutions` | Retail, alto volumen, enterprise (cliente ideal expandido) |
| `/pricing` | Ya puede existir como página dedicada |
| `/company` o `/about` | Quiénes somos, enlace CV/marca |
| `/contact` | Formulario o mailto + enlaces |

La home actual puede **redirigir secciones largas** a estas URLs para que no quede una página interminable.

### Fase C — Contenido propio por página

Cada URL tiene **un H1 claro** y meta description propia (SEO). Reutilizar `lib/copy.ts` por namespace (`copyProduct`, `copySolutions`, …) o ficheros por ruta.

---

## 4. Checklist de coherencia con “look enterprise”

- [ ] Un **mensaje principal** por página (no diez mensajes iguales).
- [ ] **CTA primario** repetido pero consistente (ej. “Ver demos” / “Contactar ventas”).
- [ ] **Demos** fáciles de encontrar en ≤2 clics desde cualquier página.
- [ ] Footer con **misma navegación** que el header (o subconjunto).
- [ ] **Documentación técnica** enlazada como “Docs” (toolkit / GitHub) sin mezclar con marketing salvo en Recursos.

---

## Referencias que compartirás

| Empresa / sitio | URL | Notas |
|-----------------|-----|--------|
| **Sequoia** | https://www.sequoia.com | Referencia principal — ya mapeada arriba. |
| *(otras)* | | Añade si quieres combinar otro estilo. |

---

## Siguiente paso

1. Rellena la tabla con **2–5 URLs** enterprise que te gusten.  
2. Indica si prefieres **solo reordenar la home** o **crear rutas `/product`, `/demos`, etc.**  
3. Con eso se puede implementar en código (nav, footer, páginas nuevas) sin cambiar vuestro mensaje.

*Documento vivo — actualizar cuando defináis referencias concretas.*

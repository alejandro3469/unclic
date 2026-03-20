# Sistema de copia para sitio web (SEO y organización)

Objetivo: que **quien nos busque nos encuentre**. La copia del sitio se resuelve de forma **orientada a SEO**, con un sistema reutilizable basado en ejemplos expertos (Laravel Cloud, Vercel, etc.) y en cómo organizan páginas y enlaces.

---

## 1. Principios del sistema

| Principio | Aplicación en UnClic |
|-----------|----------------------|
| **Palabra clave principal en H1 y título** | "Pipeline as Code" y variantes (CI/CD, Jenkins, despliegue automático) en el headline y en `<title>`. |
| **Long-tail en H2 y descripciones** | Frases de búsqueda real: "desplegar con Jenkins sin gestionar servidores", "prueba Jenkins y Gitea gratis". |
| **Meta description con CTA y keyword** | 150–160 caracteres, incluye beneficio + keyword + llamada (ej. "Despliega con Pipeline as Code. Jenkins, Gitea, registry. Prueba gratis, sin tarjeta."). |
| **Una idea por sección, un H2 por sección** | Cada bloque tiene un único H2 que resume la idea y contiene keyword secundaria. |
| **Enlaces internos con texto descriptivo** | No "Más información" sino "Ver demos de Jenkins y Gitea", "Cómo empezar con Pipeline as Code". |
| **URLs y anclas semánticas** | `#demos`, `#precios`, `#como-empezar`; si hay más páginas: `/precios`, `/demo`. |

---

## 2. Organización de páginas y enlaces (patrón experto)

### 2.1 Estructura típica de sitios que convierten

- **Una landing principal** con: Hero (H1) → Problema/Solución → Producto/Características → Cómo empezar → Social proof / Demos → Precios → FAQ → CTA.
- **Páginas secundarias** (si aplica): Precios, Demo, Blog, Contacto. Cada una con su propio `<title>` y meta description.
- **Navegación**: pocos ítems (Producto, Precios, Demo, Contacto); enlaces a secciones de la home o a páginas.
- **Footer**: enlaces a secciones clave + legales + redes.

### 2.2 Aplicado a UnClic (una página por ahora)

| Área | Contenido | Enlace / ancla |
|------|-----------|-----------------|
| Hero | H1 + valor + CTAs | — |
| Por qué | Problema/solución Pipeline as Code | `#why` |
| Qué ofrecemos | Features (Pipeline, Registry, Demos) | `#features` |
| Flujo | Integración Git → Pipeline → Deploy | `#flow` |
| Cómo empezar | 3 pasos | `#how-it-works` |
| Presencia global | Globe | `#globe` |
| Demos | Jenkins, Gitea, POS, Registry | `#demos` |
| Precios | Planes y FAQ precios | `#pricing` |
| Galería / Más | Galería, vídeo, audio | `#gallery`, `#video`, `#audio` |
| Para quién | Cliente ideal | `#cliente-ideal` |
| FAQ | Preguntas frecuentes | `#faq` |
| CTA final | Contacto | `#contacto` |

Los **textos de los enlaces** (nav y footer) deben ser descriptivos para SEO: "Demos en vivo", "Precios", "Cómo empezar", "Contacto".

### 2.3 Si añadimos más páginas

- **/precios** → título "Precios | Pipeline as Code y CI/CD — UnClic", meta "Precios de Pipeline as Code. Suscripción o por sprint. Cotización sin compromiso."
- **/demo** → título "Probar demo Jenkins y Gitea — UnClic", meta "Accede a la demo de Jenkins, Gitea y POS. Sin tarjeta. Usuario invitado."
- Enlaces desde la home: "Ver precios" → `/precios`, "Abrir demo" → `/demo` o `#demos`.

---

## 3. Keywords y dónde colocarlas

| Tipo | Ejemplos | Dónde |
|------|----------|--------|
| **Principal** | Pipeline as Code, CI/CD, Jenkins, Gitea | Título, H1, meta description, primera línea del hero |
| **Secundarias** | despliegue automático, Jenkinsfile, registry Docker, integración continua | H2 de secciones, títulos de features, FAQ |
| **Long-tail** | "desplegar con Jenkins sin gestionar servidores", "prueba Jenkins y Gitea gratis", "pipeline as code en español" | Descripciones de secciones, párrafos, alt de imágenes |
| **Marca** | UnClic | Título (suffix), footer, CTA |

---

## 4. Reglas de redacción por tipo de bloque

- **Hero (H1)**: Frase corta con beneficio + keyword principal. Ej. "La forma más rápida de desplegar y escalar con Pipeline as Code."
- **Subtítulo hero**: Una o dos frases con keyword secundaria o long-tail. Ej. "Despliega sin gestionar servidores. Jenkins, Gitea, registry y despliegue automático en un clic."
- **H2 de sección**: Incluir keyword cuando suene natural. Ej. "Por qué Pipeline as Code", "Demos en vivo de Jenkins y Gitea".
- **Descripción de sección**: 1–2 frases con long-tail o variante. Ej. "Prueba Jenkins, Gitea y una app de ejemplo sin registrarte."
- **CTAs**: Acción clara + valor. "Empezar gratis", "Ver demo", "Contactar ventas".
- **Meta description**: 150–160 caracteres, keyword principal, beneficio y CTA. Sin rellenar de palabras clave.

---

## 5. Ejemplos expertos (pegar aquí y extraer patrones)

Cuando pegues ejemplos de sitios que te gustan (Laravel Cloud, Vercel, etc.), anótalos aquí y extraemos:

- **Título y H1** que usan.
- **Estructura de navegación** (qué enlaces, a qué páginas/secciones).
- **Orden de secciones** en la home.
- **Formulación de CTAs** y de la meta description.
- **Uso de keywords** en títulos y párrafos.

---

### Ejemplo 1: Sequoia

**Nav:** Solutions | Capabilities | Insights | Company | Why Sequoia | Log In | **Get Started**

**Hero:**
- H1: **COMP + BENEFITS + RISK** (tres pilares del producto en mayúsculas)
- Sub: *"There's a reason 2,500+ companies trust Sequoia with their people strategy"*
- Párrafo: *"For two decades and counting, our advisory-first approach has helped investor-backed clients manage spend and hit their goals by mastering comp, benefits, and beyond."*

**Trust bar:** "Sequoia is proud to partner with world-class employers" + **2,500+ CLIENTS** | **140+ COUNTRIES** | **75 NPS** | **24+ YEARS IN BUSINESS**

**Sección diferenciadora:** "One team leveraging one integrated platform" — un equipo, una plataforma. Luego **4 pilares de beneficio** (Improve executive decision making, Control costs, Elevate the employee experience, Protect the business).

**Soluciones:** "People solutions for any size company, in any situation" — 4 bloques con:
- Título (Advisory & Brokerage, Investor Solutions, etc.)
- *"Because [razón emocional/racional]."*
- Descripción corta + icono/$

**Social proof:** "Impacting client success, from emerging startup to household name" + **View all client stories**

**Recursos:** "Latest Insights & Resources" — Blog, Reports, Webinars, Client Voices, Events (enlaces con $)

**CTA final:** "Let's get more out of your investment in people" + *"Sequoia is the one partner to minimize all your moving parts."* + **Get Started**

**Footer:** Explore (Solutions, Capabilities) | Learn (Blog, Webinars, Client Stories, Reports, Events) | Connect (About Us, Why Sequoia, Contact, Login). Legal: Trust Center, Privacy, Terms, etc.

**Patrones extraídos (Sequoia):**
- **H1 tipo pilares:** 3 conceptos clave en mayúsculas (COMP + BENEFITS + RISK) que definen el producto.
- **Sub con prueba social:** "There's a reason [X]+ companies trust [Marca] with [valor]." — número + confianza.
- **Párrafo hero:** "For [X] years, our [enfoque] has helped [cliente] [objetivo] by [cómo]."
- **Barra de métricas:** 4 números (clientes, países, NPS, años) justo después del hero.
- **Una sección "one team / one platform":** diferenciador en una frase, luego 4 beneficios con título outcome (verbos: Improve, Control, Elevate, Protect).
- **Soluciones en cards:** cada una con título + *"Because [razón]."* + descripción breve.
- **Recursos/Insights:** "Latest Insights & Resources" con Blog, Reports, Webinars, Client Stories, Events.
- **CTA final:** "Let's get more out of [X]" + frase "one partner to [beneficio]" + botón Get Started.
- **Nav:** Soluciones | Capacidades | Insights | Empresa | Why [Marca] | Log In | **Get Started** (CTA destacado).
- **Footer en 3 columnas:** Explore (producto) | Learn (contenido) | Connect (empresa + contacto).

---

### Patrones Sequoia aplicados a UnClic

| Patrón Sequoia | Aplicación UnClic |
|----------------|-------------------|
| H1 pilares (COMP + BENEFITS + RISK) | H1 alternativo: **PIPELINE + REGISTRY + DEPLOY** o mantener headline actual con keyword. |
| "There's a reason X+ trust..." | "Hay una razón por la que equipos confían en UnClic para su estrategia de CI/CD" (con número si hay datos). |
| Barra de métricas | Añadir bloque opcional: proyectos, años, clientes o NPS si hay cifras; si no, mantener trust strip cualitativo. |
| "One team, one platform" | Sección: "Un solo equipo, un solo flujo: Pipeline as Code con Jenkins, Gitea y registry." |
| 4 pilares de beneficio | Ya tenemos "Por qué" (problemas/soluciones); se pueden reformular como 4 outcomes (Despliega más rápido, Controla costes, Trazabilidad, Menos riesgo). |
| Soluciones "Because..." | Features: cada ítem con título + *"Porque [razón]."* + descripción. |
| "Latest Insights & Resources" | Si hay blog/recursos: sección "Recursos" con enlaces a docs, demo, precios. |
| CTA final "Let's get more..." | "Aprovecha al máximo tu inversión en CI/CD. UnClic es el partner para simplificar tu pipeline." + Empezar gratis. |
| Nav tipo Sequoia | Soluciones (o Qué ofrecemos) | Cómo empezar | Demos | Precios | Por qué UnClic | Contacto / Empezar gratis. |
| Footer 3 columnas | Explorar (Qué ofrecemos, Demos, Precios) | Aprender (Docs, Blog si hay) | Conectar (Por qué, Contacto). |

---

### Ejemplo 2: ElevenLabs

**Nav:** ElevenCreative | ElevenAgents | ElevenAPI | Resources | Enterprise | Pricing | Contact sales | **Go to app** | Contact sales | **Sign up**

**Hero:**
- H1: **"Bringing technology to life"**
- Sub: *"Powering the best enterprises, creators, and developers. From ElevenAgents for customer experience, ElevenCreative for content creation, to the leading AI voice generator."*
- CTAs: **Sign up** | Contact sales

**Trust strip:** "Trusted by leading developers and enterprises" + **Read all stories** + logos (Twilio, Disney, KPN, TVS, Telus, Cisco, Epic Games, Nvidia, Revolut, Meta, Bertelsmann, Ukraine, deliveroo, Chess.com, Deutsche Telekom, meesho, Harvey, Salesforce)

**Sección diferenciadora:** "Two platforms built on the same research foundation" — dos productos (ElevenCreative / ElevenAgents) con título, descripción corta y CTA "Learn more" cada uno.

**Producto 1 (ElevenCreative):** "Generate ultra-realistic speech, videos, music, and sound effects."  
- Bloque "Create, edit and localize in one AI platform" + párrafo + grid de capacidades (All-in-one AI editor, Ultra-realistic speech, Music, SFX, Voices, Image & Video).  
- Caso: "NVIDIA — Using synthetic voice technology to power multilingual marketing content" + **Get started**

**Producto 2 (ElevenAgents):** "Deploy agents that talk, type, and take action."  
- Bloque "Configure, deploy and monitor..." + grid (Omnichannel agents, Analytics, Testing, Guardrails, Workflows).  
- Caso: "Deliveroo — Using voice agents to enhance rider and restaurant experience" + **Get started**

**API / desarrolladores:** "Or build anything with a powerful host of APIs" — **Explore docs**. Tres bloques: Text to Speech API, Speech to Text API, Music API, cada uno con título, métrica (ej. "75ms latency", "98% accuracy"), snippet de código y CTA.

**Sección impacto:** "Showcasing the global impact of AI audio research" + carrusel de noticias/casos (NVIDIA ACE, Matthew McConaughey, Impact Program).

**Investigación:** "Research that redefines human technology interaction" — visión + "We build our own foundational models" + timeline de releases (Scribe v2, Eleven Music, etc.) con "Learn more".

**Seguridad / confianza:** "Safety, built in" — 3 bloques: Moderation, Accountability, Provenance + "Learn more". Luego "Latest updates" (blog: Introducing Flows, Government, Expressive Mode).

**CTA final:** "The most realistic voice AI platform" + **Talk to sales** | **Create an AI agent**

**Footer (multi-columna):**  
- ElevenCreative (Text to Speech, Speech to Text, Voice Changer, …)  
- ElevenAgents (Voice Agents, Integrations, Telecommunications, …)  
- ElevenAPI (API Reference, Agents API, Dubbing API, …)  
- Resources (Blog, Iconic Marketplace, Impact Program, Help Center, Webinars, Docs)  
- Enterprise, Trust Center  
- Company (About, Careers, Safety, Brand & Press Kit, …)  
- Legal: Terms, Privacy, Modern Slavery, CCPA, EU-US DPF.  
- Socials: X, LinkedIn, GitHub, YouTube, Discord, TikTok, Instagram, Facebook, Reddit.

**Patrones extraídos (ElevenLabs):**
- **H1 aspiracional corto:** "Bringing technology to life" — frase de impacto, no lista de features; el detalle va en el sub.
- **Sub hero con audiencias y productos:** "Powering [audiencias]. From [Producto A] for [uso], [Producto B] for [uso], to [claim]." — enumera productos y posicionamiento en una frase.
- **Trust strip con logos de marcas:** "Trusted by leading [audiencia]" + "Read all stories" + grid de logos (enterprises conocidas).
- **Dos productos, misma base:** "Two platforms built on the same [foundation]" — titulo de sección que une Pipeline + Registry + Deploy (o Creative + Agents).
- **Bloques producto:** Cada producto = título + descripción una línea + "Learn more" + grid de capacidades (título + descripción corta) + caso de uso con marca + "Get started".
- **Sección API / developers:** "Or build anything with [APIs]" — CTA "Explore docs" + bloques por API con métrica (latency, accuracy) + código de ejemplo.
- **Impacto / noticias:** "Showcasing the global impact of [X]" — carrusel o grid de noticias/casos con marcas.
- **Research / roadmap:** "Research that redefines [X]" — visión + "We build our own [models]" + timeline o releases con "Learn more".
- **Safety / Trust:** Bloque "Safety, built in" con 3 pilares (Moderation, Accountability, Provenance). Luego "Latest updates" (blog).
- **CTA final corto:** Claim del producto ("The most realistic voice AI platform") + 2 CTAs (ventas + acción principal).
- **Footer por producto y rol:** Columnas por producto (Creative, Agents, API), Resources, Enterprise, Company, Legal, Socials — muy escaneable.

---

### Patrones ElevenLabs aplicados a UnClic

| Patrón ElevenLabs | Aplicación UnClic |
|-------------------|-------------------|
| H1 aspiracional corto | Alternativa a pilares: "Llevar el pipeline a la vida" o "De código a producción en un clic" (mantener keyword en sub). |
| Sub con audiencias y productos | "Para equipos que quieren entregar más. Pipeline as Code, Registry y despliegue automático con Jenkins y Gitea." |
| Trust strip con logos | Si hay clientes/logos: "Confían en UnClic" + grid de logos; si no, mantener métricas o frases cualitativas (ya aplicado). |
| "Two platforms, same foundation" | "Un flujo, una base: Pipeline, Registry y Deploy sobre el mismo código y la misma plataforma." (refuerza one team / one platform). |
| Bloques producto con caso de uso | Features: cada uno con "Learn more" o "Ver demo" + opcional caso (ej. "Equipo X — usando Jenkins y Gitea para POS"). |
| Sección API / developers | Si hay API o Jenkinsfile público: "O integra con tu stack" + snippet + "Ver documentación". |
| Impacto / noticias | Si hay casos o noticias: "Impacto de Pipeline as Code" con 2–3 referencias o enlaces a docs. |
| Safety / Trust | Opcional: "Seguridad y trazabilidad" — Git, rollback, alcance por escrito (breve). |
| CTA final con claim + 2 botones | "La forma más directa de desplegar con Pipeline as Code" + Empezar gratis | Contactar ventas (ya aplicado). |
| Footer por producto/área | Explorar | Aprender | Conectar ya aplicado; si crece el producto, añadir columna "Por producto" (Pipeline, Registry, Demos). |

---

### Ejemplo 3: Kiro

**Nav (banner):** "New: Specs for bugfixes and design-first dev"  
**Nav:** Kiro | CLI | POWERS | AUTONOMOUS AGENT | ENTERPRISE | PRICING | DOCS | RESOURCES | SIGN IN | **DOWNLOADS**

**Hero:**
- H1 (repetido): **"Agentic AI development from prototype to production"**
- Sub: *"Kiro helps you do your best work by bringing structure to AI coding with spec-driven development."*
- CTAs: **Download for macOS** | Watch Demo

**Bloque refuerzo:** "Tame complexity with spec-driven development, advanced steering, and custom agents" + párrafo (10x output, specs, agents, codebases).

**Flujo en pasos (3 bloques numerados):**
1. **Natural prompt to structured requirements** — EARS, requirements y acceptance criteria.
2. **Architectural designs backed by best practices** — analiza codebase, arquitectura y tech stack.
3. **Discrete tasks that map to requirements** — plan de implementación, tareas, tests; "ask Kiro to implement".

**CLI / terminal:** "Conversation to code to deployment, directly from the terminal" — Install (curl), Learn more about CLI.

**Agent hooks:** "Automate tasks with agent hooks" — triggers on file save, background agents (docs, tests, performance). Learn more about hooks.

**Sección "EVERYTHING YOU NEED":** H2 **"Go from vibe coding to viable code"** — 3 cards:
- Built for working with agents (multimodal, spec-driven, hooks).
- Advanced context management (specs, steering, smart context, fewer shots).
- Native MCP support (docs, databases, APIs, remote).

**"Your code, your rules"** — steering files: contexto, estándares, workflows por proyecto o global.

**"Powered by the state of the art"** — modelos (Claude Sonnet 4.5, Auto, frontier models). Compatible con VS Code (Open VSX, themes, settings).

**Feature grid (iconos + título + descripción):** Autopilot mode, Per prompt credit usage, Show don't tell (image/UI), Generate Git commit messages, Intelligent error diagnostics.

**"Witness the magic with code diffs"** — approve, step through, edit with one click.

**Confianza:** "With enterprise-grade security and privacy, you can ship your best work with confidence." + Learn more.

**Testimonios:** "Trusted by engineers worldwide" — carrusel/grid de citas: nombre, rol (CTO, Principal Architect, Founder, etc.), quote corta, avatar.

**Quick guides:** "Get started fast with quick guides" — 1 destacado (Learn by playing: create a video game) + BLOG | GUIDE (Your first project) | Common questions.

**FAQ (acordeón):** What is Kiro? | What is spec-driven development? | How can I get started? | What programming languages? | What languages can I ask questions in? | Can I import settings from VS Code? — **Browse all FAQs**.

**CTA final:** "Build something real in minutes" — **Get started for free** | **Download for macOS**.

**Footer:** Kiro (logo) | **PRODUCT** (About Kiro, CLI, Powers, Autonomous agent, Pricing, Downloads) | **FOR** (Enterprise, Startups) | **RESOURCES** (Documentation, Blog, Changelog, FAQs, Report a bug, Suggest an idea, Billing support) | **SOCIAL** (Site Terms, License, Responsible AI Policy, Legal, Privacy Policy, Cookie Preferences).

**Patrones extraídos (Kiro):**
- **H1 aspiracional + repetición:** Una frase de impacto ("from prototype to production"); se repite como ancla. El "cómo" va en el sub (spec-driven development).
- **Banner de novedad en nav:** "New: [feature o mensaje]" — genera urgencia sin quitar protagonismo al hero.
- **CTAs hero:** Descarga principal (Download for macOS) + secundario (Watch Demo) — acción concreta y demo.
- **Bloque refuerzo bajo hero:** Subhead + párrafo que amplía el valor (tame complexity, 10x, structure, intent).
- **Flujo en 3 pasos:** Prompt → Requirements; Requirements → Architecture; Architecture → Tasks + implement. Secuencia lógica que explica el producto.
- **Sección "todo en uno" con H2 memorable:** "Go from [antes] to [después]" (vibe coding → viable code) + 3 pilares con icono/título/descripción.
- **"Your X, your rules":** Frase corta de diferenciación (config, control) + una línea de explicación.
- **"Powered by" / stack:** Transparencia sobre modelos o tecnología (builds trust).
- **Feature grid escaneable:** Varios ítems con icono + título + una línea (autopilot, credits, image input, commits, errors).
- **Social proof con rol:** Testimonios con nombre + rol (CTO, Architect, Founder) + quote — no solo logo.
- **Quick guides + FAQ:** "Get started fast" con 1 guía destacada + enlaces (Blog, Guide, FAQ). FAQ en acordeón con "Browse all FAQs".
- **CTA final corto:** "Build something real in minutes" + Get started for free + Download (repite CTA principal).
- **Footer por rol/uso:** PRODUCT | FOR (Enterprise, Startups) | RESOURCES | SOCIAL — separación por tipo de usuario o necesidad.

---

### Patrones Kiro aplicados a UnClic

| Patrón Kiro | Aplicación UnClic |
|-------------|-------------------|
| H1 aspiracional + repetición | Mantener H1 tipo pilares (PIPELINE + REGISTRY + DEPLOY) o alternar con "De prototipo a producción con Pipeline as Code"; repetir en bloque refuerzo si hay espacio. |
| Banner de novedad en nav | Opcional: "Nuevo: demos con Jenkins y Gitea" o "Nuevo: guía HTTPS" cuando haya lanzamiento o doc destacada. |
| CTAs hero: Descarga + Demo | Si hay descarga (CLI, script): "Descargar" + "Ver demo"; si no, "Empezar gratis" + "Ver demo" (ya alineado). |
| Bloque refuerzo bajo hero | Ya tenemos subtítulo y trust strip; se puede añadir una línea tipo "Domina la complejidad con pipeline versionado, registry y despliegue automático." |
| Flujo en 3 pasos | Refuerza "Cómo funciona": Commit → Pipeline (build + test) → Registry → Deploy; ya existe #how-it-works, se puede numerar explícitamente (1. Push, 2. Build, 3. Deploy). |
| "Go from X to Y" | H2 alternativo en Por qué o Features: "De manual a automatizado" o "De código a producción en un flujo." |
| "Your X, your rules" | "Tu código, tu pipeline" — configuración por repo (Jenkinsfile, Gitea). Opcional en copy. |
| "Powered by" | Sección Stack: "Con lo que ya usas: Jenkins, Gitea, Docker, Kubernetes" (ya existe icon cloud / stack). |
| Feature grid escaneable | Features actuales en cards; se puede añadir ítems cortos con icono + título + una línea (ej. Rollback, Trazabilidad, Sin tarjeta). |
| Testimonios con rol | Si hay casos: nombre + rol (DevOps, CTO) + quote; si no, mantener métricas o "equipos que confían". |
| Quick guides + FAQ | "Empieza rápido" con enlace a docs (Gitea/Jenkins HTTPS, demo) + FAQ en acordeón y "Ver todas las FAQ". |
| CTA final "Build something real" | "Lleva tu pipeline a producción en minutos" + Empezar gratis (ya aplicado con variante Sequoia). |
| Footer PRODUCT | FOR | RESOURCES | Opcional: columna "Para" (Equipos, Startups, Enterprise) además de Explorar | Aprender | Conectar. |

**Detalles de implementación (Kiro, desde HTML):**
- **Meta y SEO:** `<title>` = "[Marca]: [H1 o claim]" (ej. "Kiro: Agentic AI development from prototype to production"). Meta description = sub del hero (una frase con valor + método). Keywords en meta (marca, producto, términos búsqueda). Canonical, OG y Twitter con misma title/description e imagen 1200×630.
- **JSON-LD:** WebSite (name, url, description, sameAs, potentialAction tipo DownloadAction) y SoftwareApplication (applicationCategory, operatingSystem, downloadUrl, offers, featureList) para rich results.
- **Banner (eyebrow):** Enlace con borde y fondo suave, texto "New: [mensaje]", icono flecha; colocado sobre el hero dentro del grid. En UnClic: opcional "Nuevo: [guía o demo]" con href a doc o #demos.
- **Nav:** Sticky, `aria-label="Main"`. Enlaces en mayúsculas; CTA principal (DOWNLOADS) destacado (fondo sólido); secundario (SIGN IN) outline. Opcional: búsqueda (DocSearch) antes de los botones.
- **Títulos de sección:** font-mono, H2, centrado, `text-pretty`/`text-balance`, max-width (ej. 900px) para líneas cortas. Refuerza sensación "producto técnico".
- **Bloque de código:** Label ("Install on macOS and Linux") + `<code>` con comando (curl \| bash), botón copiar, contenedor con borde y fondo tipo terminal. En UnClic: "Probar local" o "Un solo comando" con snippet si hay script.
- **Testimonios:** H2 "Trusted by [audiencia]". Carrusel con cards: cita (borde, padding), avatar (iniciales o imagen), nombre, rol (subtítulo más suave). Algunos ítems con enlace externo (blog, GitHub).
- **Quick guides:** Contenedor con `rounded-[48px]`, fondo oscuro. H2 "Get started fast with quick guides". Una guía destacada (card grande, imagen, H3, descripción) + 2 cards menores con etiqueta tipo `{ BLOG }` / `{ GUIDE }` y título. En UnClic: "Empieza rápido" con enlace a docs HTTPS/demo + 1–2 entradas de blog o guía.
- **FAQ:** H2 "Common questions". Acordeón: cada ítem con `border-b`, botón (pregunta + icono plus), contenido colapsable; `data-state`, `aria-controls`/`aria-expanded` para accesibilidad. CTA bajo la lista: "Browse all FAQs" → /faq.
- **CTA final:** Bloque con fondo sólido (purple-500), `rounded-[48px]`, padding vertical grande. H1 corto ("Build something real in minutes"), subtítulo ("Get started for free"), un botón principal (Downloads). Opcional: mascota o ilustración decorativa en la parte inferior. En UnClic: mismo patrón con "Lleva tu pipeline a producción en minutos" y Empezar gratis.
- **Footer:** Logo + 4 columnas (Product, For, Resources, Social). Títulos de columna en mayúsculas, lista de enlaces debajo. Social = solo iconos (Discord, LinkedIn, X, etc.). Barra inferior: logo partner (ej. AWS) + enlaces legales (Site Terms, License, Privacy, Cookie Preferences).

---

### Ejemplo 4: Laravel Cloud

**Banner superior:** "Laravel Cloud now offers a free trial with $5 of credit, no credit card required. Try for free →"

**Nav:** Laravel Cloud | Pricing | Platform | Networking | Compute | Changelog | Docs | Private Cloud | Sign in | **Sign up**

**Hero:**
- H1: **"The fastest way to deploy and scale Laravel applications"**
- Sub: *"Deploy your Laravel applications without managing servers. One-click autoscaling, databases, caching, storage, and security."*
- CTAs: **Get started for free** | Contact sales
- Línea de confianza: "No credit card required."
- Enlace secundario: Watch demo

**Trust strip:** "Trusted by teams who want to ship fast" + logo (Statamic).

**Bloque valor principal:** "Speed, simplicity, and scalability without the headaches" — Built specifically for Laravel, Cloud eliminates configuration hassles and deployment complexity. So you can focus on building, not configuring. CTA: **Get started under 60 seconds**. Refuerzo: "No code changes, extra packages, or CLI tools needed - just connect your git provider account, select a repo, and you're ready to go."

**Features (secciones con H2 + descripción + sub-ítems):**
- **Multiple environments** — staging, production, development, each with its own configuration.
- **Queue workers** — background tasks (emails, images, provisioning).
- **For individuals and teams** — invite team, manage permissions.
- **Everything you need, included** — Logs, Commands, Metrics, Command palette (cada uno con una línea).
- **Push to deploy** — manual, on git push, or via API; Deploy hooks, Task scheduler, Octane.
- **Security and speed, automatically configured** — Edge network, Custom domains, DDoS protection, Content caching, SSL/TLS, Load balancing.
- **Scale up and down in your sleep** — Autoscaling, Hibernation, Cost optimized / Performance optimized, Multiple regions.
- **Connect it all with one-click resources** — Fully managed; Injected env vars; Databases (MySQL/Postgres), Cache (Redis), Object storage (S3), WebSockets.

**Testimonios:** "Trusted by developers, startups, and enterprises" — "Join thousands of developers and companies around the world." — Cards: cita, nombre, rol, empresa (Peter Steenbergen/Elastic, Sam Pizzo/CMS Max, Rafael Lunardelli/Devsquad, Bradley Bernard/Snap, Duncan McClean/Statamic, Silvan Hagen, Tim Geisendoerfer/Innoge). Algunos con logo (Statamic).

**FAQ:** "Frequently asked questions" — lista larga (databases, regions, try for free, pricing, spending caps, serverless, VPC, autoscaling, AI agents, Cloud vs Forge, Cloud vs Vapor, migrate from Vapor/Heroku).

**CTA final:** "Ready to ship?" — *"Let's build the incredible together, with Laravel. Start for free with $5 of credit. No credit card required."* — **Get started for free** | Contact sales

**Footer:** "Optimized and crafted for Laravel, by Laravel. So you can focus on building, not configuring." + Stay updated (GitHub, X, YouTube, Discord). Columnas: **Cloud** (Pricing, Pricing calculator, Networking, Documentation, Contact sales) | **Explore** (Cloud vs Forge, Cloud vs Vapor, Cloud vs Cloudways, Cloud vs VPS, Migrate from Vapor/Heroku) | **Discover Laravel** (Documentation, Release notes, Blog, Community, Careers). © 2026 Laravel | Legal | Trust | Status.

**Patrones extraídos (Laravel Cloud):**
- **Banner trial con cifra y sin tarjeta:** "Now offers a free trial with $X of credit, no credit card required. Try for free →" — cifra concreta + eliminación de fricción.
- **H1 con beneficio + stack:** "The fastest way to [verbo] and [verbo] [tipo de] applications" — velocidad + acción + audiencia.
- **Sub hero con lista de capacidades:** "Without managing servers. One-click [lista: autoscaling, databases, caching, storage, security]." — dolor (servers) + solución en una línea.
- **Doble CTA + línea de confianza:** Get started for free | Contact sales; debajo "No credit card required." + Watch demo.
- **Trust strip minimalista:** "Trusted by [audiencia]" + un logo o pocos logos (Statamic).
- **Bloque "sin fricción":** "No code changes, extra packages, or CLI tools needed - just [acción simple], and you're ready to go." + CTA "Get started under 60 seconds".
- **Features por sección:** Cada H2 = beneficio o claim (Scale up and down in your sleep, Connect it all with one-click resources). Descripción corta + sub-ítems con título y una línea (Edge network, Custom domains, etc.).
- **Comparativas en footer:** "Cloud vs Forge", "Cloud vs Vapor", "Migrate from X" — ayuda a búsquedas de comparación y migración.
- **Testimonios con empresa:** Cita + nombre + rol + empresa (Search & GenAI Specialist, Elastic). "Join thousands of developers and companies around the world."
- **FAQ orientada a precios y comparación:** Try for free?, base plan price?, spending caps?, How is Cloud different from Forge/Vapor?, Migrate from Vapor/Heroku.
- **CTA final con cifra y sin tarjeta:** "Ready to ship?" + "Start for free with $5 of credit. No credit card required." + Get started for free | Contact sales.
- **Footer tagline repetida:** "Optimized and crafted for [marca], by [marca]. So you can focus on building, not configuring." — refuerza posicionamiento.

---

### Patrones Laravel Cloud aplicados a UnClic

| Patrón Laravel Cloud | Aplicación UnClic |
|----------------------|-------------------|
| Banner trial con cifra + no credit card | "Prueba UnClic: [X] de crédito o demo gratis, sin tarjeta. Probar ahora →" (si hay trial o demo gratuita). |
| H1 "fastest way to deploy and scale" | "La forma más rápida de desplegar y escalar con Pipeline as Code" (ya cercano en copy). |
| Sub con "without managing servers" | "Despliega sin gestionar servidores. Jenkins, Gitea, registry y despliegue automático en un clic." (ya aplicado). |
| No credit card + Watch demo | Mantener "Sin tarjeta" en CTAs y enlace "Ver demo" junto a Empezar gratis / Contactar ventas. |
| Trust strip con un logo | Si hay partner (ej. Statamic): "Equipos que confían en UnClic" + logo; si no, mantener métricas o frase cualitativa. |
| "No code changes... just connect git" | "Sin cambios en tu código: conecta Gitea o Git, elige el repo y el pipeline hace el resto." (bloque Por qué o Features). |
| Get started under 60 seconds | Opcional: "Listo en menos de un minuto" o "Deploy en 60 segundos" en CTA o sub. |
| Features con H2 memorable + sub-ítems | Mantener H2 por sección; sub-ítems con título + una línea (Logs, Commands, Metrics → adaptar a Pipeline, Registry, Deploy). |
| Explore: Cloud vs X, Migrate from X | Si aplica: "Pipeline as Code vs [alternativa]" o "Migrar desde [Heroku/Jenkins manual]" en docs o footer. |
| Testimonios nombre + rol + empresa | Si hay casos: "Nombre — Rol, Empresa" bajo cada cita; si no, mantener rol solo (CTO, DevOps). |
| FAQ try for free, pricing, caps | Incluir en FAQ: "¿Puedo probar gratis?", "¿Hay límite de gasto?", "¿Cómo se compara con X?". |
| CTA final "Ready to ship?" + cifra | "¿Listo para desplegar?" + "Empieza gratis, sin tarjeta." + Empezar gratis y Contactar ventas. |
| Footer tagline "focus on building" | "Hecho para que te enfoques en entregar, no en configurar." (variante de one team / one platform). |

---

### Ejemplo 5: Datadog (Workflow / product page)

**Header:** Logo Datadog, enlace home.

**Hero:**
- H1: **"Tired of Manually Responding to Alerts?"**
- Sub: *"Automate and orchestrate processes across your entire tech stack with over 1,000 out-of-the-box actions."*
- CTA: formulario (User email) + botón de signup
- Línea legal: "No credit card required. By signing up, you agree to the Subscription Agreement and Privacy Policy."

**Trust strip (integraciones):** "1,000+ TURN-KEY INTEGRATIONS, INCLUDING" + logos (Kubernetes, Azure, etc.).

**Sección PRODUCT BENEFITS** — bloques con H2 + viñetas + imagen:
- **Build End-To-End Workflows** — point-and-click builder, 1,000+ actions (AWS, Cloudflare, GitLab, Slack), multi-step con lógica y condiciones.
- **Automatically Trigger Remediation Actions** — disparar workflows desde monitors y señales de seguridad, datos en tiempo real, un clic desde dashboards/alertas.
- **Get Started Quickly with Pre-Built Blueprints** — 40+ blueprints (DevOps, security), personalizables, hub centralizado.
- **Automate Complex Processes While Maintaining Human Control** — pasos con decisión humana, RBAC, visibilidad y debugging.
- **Enhance Productivity with Bits AI, Your Generative AI Copilot** — consultas en lenguaje natural (móvil, web, Slack), resúmenes de incidentes, post mortems, sugerencias de código y tests.

**Sección plataforma:** "Optimize your Systems with end-to-end cloud monitoring" — "See inside any stack, any app, at any scale, anywhere in one cloud observability platform."

**Grid de 3 features (imagen + título + una línea):**
- Host and Container Maps — Visualize servers or containers in a single view.
- Synchronized Dashboards — Track incidents across metrics with a common tagging structure.
- Watchdog — Detect performance issues using machine learning.

**Social proof:** "LOVED & TRUSTED BY THOUSANDS" + logos (Washington Post, 21st Century Fox, Peloton, Samsung, Comcast, Nginx).

**Footer:** © Datadog 2026 | Terms | Privacy | Your Privacy Choices.

**Variante: Datadog (homepage principal)**  
- **Banner evento:** "Join Datadog at DASH—coming to NYC June 9-10. Register now for Super Early Bird savings of $700 until March 31" — evento + lugar + fecha + beneficio concreto (ahorro) + fecha límite.  
- **Nav:** PRODUCT | CUSTOMERS | PRICING | SOLUTIONS | DOCS | logo | ABOUT | BLOG | LOGIN — muchas categorías + CTA Login.  
- **Hero:** H1 **"AI-Powered Observability and Security"** + claim **"See inside any stack, any app, at any scale, anywhere."** — sin formulario, solo mensaje.  
- **Trust:** "THOUSANDS OF CUSTOMERS LOVE & TRUST DATADOG".  
- **Bloques promocionales (cards):** DASH 2026 (conferencia, REGISTER NOW), Gartner Magic Quadrant (READ THE REPORT), State of DevSecOps (LEARN MORE), Careers (Join Our Pack, LEARN MORE), Events & Webinars (LEARN MORE), Download mobile app. Cada uno con título, descripción corta y CTA.  
- **Footer mega:** PRODUCT (lista larga por categoría: Infrastructure, APM, Log Management, Security, etc.), RESOURCES (Pricing, Documentation, Support, …), ABOUT, BLOG, selector de idioma. Abajo: © 2026, Terms, Privacy, Your Privacy Choices.

**Patrones extraídos (Datadog):**
- **H1 como pregunta dolor:** "Tired of [pain point]?" — conecta con el problema antes de ofrecer la solución.
- **Sub con cifra concreta:** "over 1,000 out-of-the-box actions" — número que da escala y credibilidad.
- **Hero con email only + no credit card:** Formulario mínimo (email), CTA de signup, línea "No credit card required" + legal (Subscription Agreement, Privacy Policy).
- **Trust: integraciones en mayúsculas:** "X+ TURN-KEY INTEGRATIONS, INCLUDING" + logos de tecnologías (Kubernetes, Azure) — refuerza compatibilidad con el stack.
- **Product benefits con H2 + viñetas + imagen:** Cada beneficio = H2 impactante + 2–3 viñetas + imagen a un lado (workflow, blueprints, human control, AI copilot).
- **Sección “one platform”:** "See inside any stack, any app, at any scale, anywhere in one [product] platform" — claim de cobertura total.
- **Grid 3 columnas (imagen + título + descripción):** Tres capacidades con icono/ilustración, título corto y una línea de descripción.
- **Social proof con marcas:** "LOVED & TRUSTED BY THOUSANDS" + logos de empresas (media, tech, retail) — sin citas, solo marcas.
- **Footer mínimo:** Año, Terms, Privacy, Your Privacy Choices — sin columnas de enlaces.
- **Banner evento (homepage):** "Join [Marca] at [Evento]—[lugar] [fechas]. Register now for [beneficio] until [fecha límite]" — ahorro o incentivo concreto.
- **Hero solo claim (homepage):** H1 (tema + producto) + una línea "See inside any stack, any app, at any scale, anywhere" — sin formulario, mensaje de cobertura total.
- **Bloques promocionales:** Cards con imagen/fondo (evento, informe, careers, webinars, app): título + descripción + CTA (REGISTER NOW, READ THE REPORT, LEARN MORE).
- **Footer mega:** Columnas PRODUCT (lista larga por categoría), RESOURCES, ABOUT, BLOG — para sitios con muchos productos o secciones.

---

### Patrones Datadog aplicados a UnClic

| Patrón Datadog | Aplicación UnClic |
|----------------|-------------------|
| H1 pregunta dolor | "¿Cansado de desplegar a mano?" o "¿Quieres dejar de gestionar pipelines a mano?" (alternativa al H1 por pilares). |
| Sub con cifra | "Más de [X] integraciones" o "Pipeline, registry y deploy en un flujo" — usar número si hay datos (repos, pipelines). |
| Hero email + no credit card | Si hay signup: solo email + "Sin tarjeta. Al registrarte aceptas Términos y Privacidad."; si no, mantener CTAs actuales. |
| Trust "X+ INTEGRATIONS" | "Integraciones listas: Jenkins, Gitea, Docker, Kubernetes" (icon cloud o logos) con label tipo "STACK SOPORTADO". |
| Product benefits H2 + viñetas + imagen | Features: cada uno con H2 ("Automatiza de punta a punta", "Un solo flujo") + 2–3 viñetas + imagen o ilustración opcional. |
| "One platform" claim | "Un solo flujo para tu stack: commit, build, registry y deploy en una plataforma." (alineado con one team / one platform). |
| Grid 3 columnas imagen + título + línea | Sección Stack o Features: 3 cards con icono/imagen, título (Pipeline, Registry, Deploy) y una línea cada uno. |
| LOVED & TRUSTED BY THOUSANDS | "Equipos que confían en UnClic" o "Usado por equipos que quieren entregar más" + logos si hay; si no, métricas (50+ Equipos, etc.). |
| Footer mínimo | Si la página es de producto: © UnClic, Legal, Privacidad, Estado (sin columnas); si es sitio completo, mantener footer actual. |
| Banner evento | Si hay evento o webinar: "UnClic en [evento] — [fecha]. Inscríbete antes de [fecha] y ahorra [X]." con CTA Register/Inscribirse. |
| Hero solo claim | Alternativa hero sin formulario: H1 + "Un solo flujo para cualquier stack, cualquier repo, a cualquier escala." (claim de cobertura). |
| Bloques promocionales | Cards: "Guía HTTPS y Gitea" (LEER MÁS), "Demo Jenkins y POS" (VER DEMO), "Carreras" o "Eventos" si aplican. |
| Footer mega | Si el sitio crece con muchas secciones: columnas Producto (Pipeline, Registry, Demos, …), Recursos (Docs, Precios, Soporte), Empresa, Blog. |

---

### Ejemplo 6: AWS (página de producto — DevOps Guru)

**Contexto:** Página de producto dentro de un portal (AWS). Skip to Main Content, región (United States Ohio), usuario/cuenta en header.

**Breadcrumb / categoría:** Machine Learning

**Hero producto:**
- Nombre: **Amazon DevOps Guru**
- Tagline: *"ML-powered cloud operations service to improve application availability"*
- Párrafo: Improve application availability with ML-powered insights and recommendations to facilitate faster remediation and reduce expensive downtime.
- CTAs: **Configure** | **Get started** | How it works

**Sección "Features and benefits":** 4 bloques, cada uno con:
- H2 (beneficio): "Automatically detect operational issues", "Resolve issues quickly with ML-powered insights", "Easily scale and maintain availability", "Reduce noise and alarm fatigue"
- Párrafo descriptivo (2–4 líneas) explicando el beneficio y el cómo

**Sección "Pricing (US)":**
- Frase de valor: "You only pay for what you use. No up-front commitment or minimum fee."
- Qué se paga: resources analyzed + API calls; enlace "See the pricing page for complete details"
- **Free tier:** "Free tier for 3 months" + tabla/cards: AWS resource analysis (7,200 hours/month), DevOps Guru API (10,000 calls/month)
- CTA: **Cost estimator** — "Try the cost estimator to get a free estimate of your monthly resource analysis costs."

**Related Services:** Lista de enlaces (Amazon CodeGuru, CloudFormation, CloudTrail, Systems Manager, X-Ray).

**More resources:** Documentation | API Reference | FAQs

**Footer:** CloudShell, Feedback, Console Mobile App | © 2026 AWS | Privacy, Terms, Cookie preferences.

**Variante: AWS CodeGuru Profiler**  
- **Banner free tier:** "90 days free of CodeGuru Profiler with the AWS Free Tier" — duración concreta + nombre del producto.  
- **Breadcrumb:** Machine Learning › AWS CodeGuru › AWS CodeGuru Profiler — categoría y producto.  
- **Hero:** Nombre producto + tagline "Optimize application performance and reduce operational costs" + un CTA "Get started with CodeGuru Profiler".  
- **Benefits:** Grid de 3 ítems; cada uno: icono "Feature" + título (beneficio) + una línea ("Find your most expensive lines of code to help reduce operational costs by up to 50%", etc.).  
- **How it works:** Párrafo intro + elemento visual (p. ej. 100%) + párrafo largo explicando el flujo (monitors, identifies, shows, estimates) + CTA "Explore Amazon CodeGuru".  
- **Use cases:** 3 bloques con título ("Improve runtime code quality", "Visualize your application heap", "Improve performance with flame graphs"), descripción corta y "Learn More".  
- **How to get started:** 4 enlaces en bloque — producto relacionado (Reviewer), mismo producto (Profiler), "Start building in the console", "Create an AWS account".  
- **Footer grande:** Learn (What Is AWS?, Cloud Computing, Agentic AI, …), Resources (Getting Started, Training, Trust Center, …), Help (Contact, Support, re:Post, …), Legal (Privacy, Site terms, Cookie preferences). Incluso "Ask a Question", "Back to top", Equal Opportunity Employer.

**Patrones extraídos (AWS producto):**
- **Nombre producto + tagline una línea:** "[Product name]" + "[tipo de servicio] to [beneficio principal]" — definición clara en una frase.
- **Párrafo hero + 3 CTAs:** Descripción corta (2 líneas) + Configure/Get started + How it works (primario, primario, secundario).
- **Features and benefits:** H2 por beneficio (verbos: Automatically detect, Resolve quickly, Easily scale, Reduce noise), sin viñetas; un párrafo por beneficio que explica el qué y el cómo.
- **Pricing con free tier y cifras:** "You only pay for what you use", enlace a pricing page, bloque Free tier con duración (3 months) y límites (hours/month, calls/month), CTA "Cost estimator" o similar.
- **Related Services / More resources:** Lista de servicios relacionados (cross-sell) + Documentation, API Reference, FAQs — ayuda a SEO y descubrimiento.
- **Footer de producto:** Herramientas (CloudShell, Feedback, App), legal, sin mega columnas.
- **Banner free tier con duración:** "[X] days free of [Product] with the [Free Tier]" — refuerza trial sin tarjeta.
- **Benefits como grid de 3:** Icono + título de beneficio + una línea con cifra o resultado ("reduce costs by up to 50%") — escaneable.
- **How it works con visual + CTA:** Párrafo + elemento visual (barra, %) + párrafo largo del flujo + "Explore [product]" o "Get started".
- **Use cases con Learn More:** 3–4 bloques (título + descripción corta + "Learn More") — para profundizar en docs o subpáginas.
- **How to get started (bloque):** Enlaces: producto relacionado, mismo producto, Console/Start building, Create account — guía clara de siguiente paso.
- **Footer Learn / Resources / Help:** Tres columnas: Learn (What is X, concepts), Resources (Getting Started, Training, FAQs), Help (Contact, Support) — típico de portales enterprise.

---

### Patrones AWS (producto) aplicados a UnClic

| Patrón AWS producto | Aplicación UnClic |
|---------------------|-------------------|
| Nombre + tagline una línea | En página de producto o sección: "Pipeline as Code — Deploy automático con Jenkins y Gitea sin gestionar servidores." |
| Párrafo hero + 3 CTAs | Descripción corta + "Configurar" / "Empezar gratis" / "Cómo funciona" (o Ver demo). |
| Features and benefits (H2 + párrafo) | Por qué / Features: cada H2 con verbo ("Despliega automáticamente", "Reduce tiempo de entrega") + un párrafo, sin listas de viñetas si se busca tono más narrativo. |
| Pricing con free tier y cifras | Si hay precios: "Solo pagas por lo que usas", enlace a precios, bloque "Prueba gratis" con duración o límites, CTA "Calcular coste" si aplica. |
| Related / More resources | "Servicios relacionados" (Jenkins, Gitea, Registry, POS) y "Más recursos" (Documentación, FAQ) en página de producto o docs. |
| Footer de producto | En páginas de producto o doc: Feedback, enlace a docs, © UnClic, Legal, Privacidad — sin repetir nav completa. |

---

## 6. Checklist de aplicación en el código

- [ ] `lib/copy.ts`: existe objeto `seo` con metaTitle, metaDescription, keywords por sección (o se usa `site` para meta global).
- [ ] `app/layout.tsx`: `<title>` y meta description vienen de la copia (site o seo).
- [ ] Hero: H1 = copia con keyword principal.
- [ ] Cada sección: H2 único, descriptivo, con keyword cuando aplique.
- [ ] Enlaces internos (nav, footer, CTAs): texto descriptivo (no "Click aquí").
- [ ] Imágenes: `alt` con descripción que incluya keyword cuando sea relevante.
- [ ] Si hay varias páginas: cada una con su propio `metadata` (title, description).

---

## 7. Referencias en el repo

- Copia global: `unclic/lib/copy.ts`
- Meta y título: `unclic/app/layout.tsx`
- Secciones y H2: `unclic/components/sections/*.tsx`
- Navegación y footer: `unclic/components/layout/header.tsx`, `footer.tsx`

Cuando compartas los ejemplos expertos, los incorporamos en la sección 5 y actualizamos la copia en `copy.ts` y, si hace falta, la estructura de páginas y enlaces.

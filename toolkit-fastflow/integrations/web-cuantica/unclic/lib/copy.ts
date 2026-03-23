/**
 * Copy UnClic — narrativa simple en todo el sitio:
 * - **Punto A:** dónde está el equipo hoy (fricción, incertidumbre, coste oculto).
 * - **Punto B:** a dónde quiere llegar (deploy trazable, menos sorpresas, más calidad/velocidad).
 * - **Brecha:** hay muchos caminos; UnClic presenta **uno** que la cierra con stack abierto y práctica de campo.
 * - **Herramientas:** una pista + enlace a doc oficial; sin ensayar en la landing.
 * Detalle ampliado: docs del repo (`docs/`). SEO: `docs/SISTEMA-COPIA-SEO-SITIO-WEB.md`.
 */

import { routes } from '@/lib/routes';

/** SEO: meta, títulos y keywords para que quien busque nos encuentre. */
export const seo = {
  /** Título principal (home). Incluye keyword + marca. */
  title: 'UnClic — Pipeline as Code, stack abierto y demos | Jenkins, Gitea, FastFlow',
  /** Meta description 150–160 caracteres: beneficio + keyword + CTA. */
  metaDescription:
    'De un despliegue opaco a un pipeline en Git que puedes auditar: Jenkins, Gitea, registry. Demos y estimador OSS — retail y operación crítica.',
  /** Keyword principal para H1 y títulos. */
  primaryKeyword: 'Pipeline as Code',
  /** Keywords secundarias para H2 y cuerpo. */
  secondaryKeywords: [
    'CI/CD',
    'Jenkins',
    'Gitea',
    'despliegue automático',
    'Jenkinsfile',
    'registry Docker',
    'Docker',
    'contenedores OCI',
    'integración continua',
  ] as const,
  /** Long-tail para descripciones y alt. */
  longTail: [
    'desplegar con Jenkins sin gestionar servidores',
    'prueba Jenkins y Gitea gratis',
    'pipeline as code en español',
    'despliegue continuo con Jenkins y Gitea',
  ] as const,
  /** Título corto para Open Graph / redes (opcional). */
  ogTitle: 'UnClic — De A a B: pipeline en Git, demos bajo control.',
} as const;

/** Banner superior — mensaje corto tipo producto (impacto + acción). */
export const banner = {
  message: 'Punto B cercano: portal verificado · mismas demos (Jenkins, Gitea, app) en un hub.',
  cta: 'Hub',
  dismissLabel: 'Cerrar',
} as const;

/** Hub central — primera parada: demos y tecnologías para enterprise. */
export const hubDemos = {
  kicker: 'Punto B',
  sectionTitle: 'Demos y stack',
  sectionLead: 'A: enlaces dispersos. B: un hub con lo mismo que defendemos en proyecto.',
  sectionSub: 'Git + Jenkins + registry — pista visual, no sustituye la doc.',
  ctaExternal: 'Abrir',
  ctaInternal: 'Ir',
  ctaRoadmapContact: 'Cuenta',
  ctaConfigureEnv: '.env',
  badgeRoadmap: 'Roadmap',
  footerNote: 'SAT / pagos en roadmap · Workspace típico en clientes.',
  /** Enlaces externos al stack (Jenkins, Gitea…) ocultos hasta login autorizado. */
  lockedExternalCta: 'Acceder',
  lockedExternalHint: 'Inicia sesión con un correo autorizado para abrir este enlace.',
} as const;

export const site = {
  name: 'UnClic',
  tagline: 'A → B: del commit al deploy con trazabilidad (stack abierto).',
  description:
    'Cerramos la brecha con pipelines en Git, demos acotadas y números antes de comprometer — documentación y práctica alineadas.',
  keywords: [
    'pipeline as code',
    'CI/CD',
    'Jenkins',
    'Gitea',
    'despliegue automático',
    'integración continua',
    'despliegue continuo',
    'Docker registry',
    'infraestructura como código',
    'DevOps',
    'FastFlow',
    'Jenkinsfile',
    'managed CI/CD',
  ],
} as const;

/**
 * Hero — estilo Laravel Cloud + Sequoia (pilares en H1, prueba social en sub).
 * headline = frase beneficio; heroPillars = alternativa tipo "PIPELINE + REGISTRY + DEPLOY".
 */
export const hero = {
  title: 'UnClic',
  heroEyebrow: 'PUNTO A → PUNTO B',
  /** Parte 1 del H1 — situación inicial (A). */
  headline: 'Hoy: despliegues poco auditables y muchas horas en “¿qué pasó?”.',
  /** Punto B — situación deseada (gradiente visual). */
  headlineAccent: 'Mañana: un pipeline en Git, artefacto trazable, rollback con nombre.',
  subtitle:
    'Hay muchos caminos del A al B; nosotros mostramos uno que ya ahorró tiempo y nervios en operación crítica: Jenkins, Gitea, registry — con demos y docs oficiales como referencia.',
  heroPillars: 'MENOS RUIDO · MÁS TRAZA · MISMO GIT',
  trustLead: 'Menos coste oculto; más evidencia por commit.',
  ctaPrimary: 'Crear cuenta',
  /** Enlaces de texto bajo el botón principal (sin segundo botón). */
  ctaProduct: 'Ver servicios',
  ctaSecondary: 'Hablar con el equipo',
  ctaDemo: 'Iniciar sesión',
  noCreditCard: '',
  imageAlt: 'Flujo commit → deploy — UnClic',
  /** Placeholder del email en Hero154 (home con formulario). */
  leadEmailPlaceholder: 'Tu correo de trabajo',
  heroFeatures: [
    { title: 'A: código suelto', description: 'B: Jenkinsfile en repo' },
    { title: 'A: “última imagen”', description: 'B: tag = rollback' },
    { title: 'A: acceso anónimo', description: 'B: portal con correo' },
    { title: 'A: herramientas sueltas', description: 'B: atlas con doc oficial' },
  ] as ReadonlyArray<{ title: string; description: string }>,
} as const;

/** Trust strip — métricas tipo agencia (número + etiqueta corta). */
export const trustStrip = {
  title: 'Misma historia en web, repo y demo',
  subtitle: 'Pista rápida: si no cuadra aquí, no lo vendemos allí.',
  partnerTitle: 'Señales (demo)',
  metrics: [
    { label: 'Portal & demos', value: '1' },
    { label: 'Herramientas en atlas', value: '40+' },
    { label: 'API presupuesto OSS', value: 'v1' },
    { label: 'Pipeline en Git', value: '100%' },
  ] as ReadonlyArray<{ label: string; value: string }>,
} as const;

/** Home — tres pilares (A → brecha → B). */
export const homePillars = {
  eyebrow: 'Tres brechas típicas',
  title: 'Del dolor de hoy al control de mañana',
  lead: 'Sin humo: cada pilar es un salto A→B que ya hemos repetido con stack documentado (oficial + experiencia).',
  items: [
    {
      title: 'A: deploys opacos · B: pipeline en Git',
      body: 'Pista: Jenkinsfile versionado, Gitea, contenedores — alineado a retail / JDE / Oracle cuando aplica.',
      cta: 'Ver soluciones',
      href: routes.solucionesHub,
    },
    {
      title: 'A: discurso ≠ código · B: una sola historia',
      body: 'Pista: portal, login y atlas con enlaces oficiales — auditores y partners leen lo mismo que el equipo.',
      cta: 'Explorar capacidades',
      href: routes.capacidadesWhy,
    },
    {
      title: 'A: “¿cuánto cuesta?” · B: orden de magnitud',
      body: 'Pista: estimador OSS + API — no reemplaza propuesta, evita sorpresas en la primera reunión.',
      cta: 'Presupuesto OSS',
      href: routes.presupuestoWizard,
    },
  ] as const,
} as const;

/** Banda oscura — open source como medio para B (soberanía operativa). */
export const homeOpenSource = {
  id: 'filosofia-oss',
  eyebrow: 'Por qué abierto',
  title: 'Menos caja negra, más B alcanzable',
  lead: 'Código y licencias que puedes revisar = menos tiempo en vendor lock y más en entregar valor.',
  bullets: [
    'OSS primero si cubre el caso (doc oficial).',
    'SaaS solo acotado; contrato y datos claros.',
    'Web = misma postura que el repo.',
  ] as const,
  closing: 'Lo que mostramos en demo es defendible en sala de juntas.',
  citeNote: 'Referencia de industria: equipos que escalan con OSS (p. ej. prácticas públicas tipo Vates).',
} as const;

/** Fases del recorrido — A → validar → B por fases. */
export const homeJourney = {
  eyebrow: 'Camino A → B',
  title: 'Validar · Construir · Operar',
  lead: 'Tres pasos. Cada uno cierra incertidumbre antes de invertir el siguiente.',
  phases: [
    {
      step: '01',
      title: 'Validar',
      description: 'Números y riesgos en orden de magnitud (estimador OSS). Pista, no contrato.',
      linkLabel: 'Abrir calculadora',
      href: routes.presupuestoWizard,
    },
    {
      step: '02',
      title: 'Construir',
      description: 'Pipeline + registry + demo alineados a tu operación (FastFlow / tu contexto).',
      linkLabel: 'Mapa de soluciones',
      href: routes.solucionesHub,
    },
    {
      step: '03',
      title: 'Operar',
      description: 'Diagramas, integraciones y portal: B con gobernanza.',
      linkLabel: 'Stack y arquitectura',
      href: routes.capacidadesWhy,
    },
  ] as const,
} as const;

/** Meta y H1 de `/soluciones` — alineado al mega-menú «Servicios». */
export const pagesMeta = {
  soluciones: {
    title: 'Soluciones — del A al B en un hub',
    description:
      'Demos, plataforma, precios y FAQ: un solo recorrido para ver cómo cerramos la brecha (Git → pipeline → deploy).',
  },
  capacidades: {
    title: 'Capacidades — evidencia técnica',
    description:
      'Stack, flujos y diagramas: pistas mínimas; detalle en docs oficiales y en el repo.',
  },
  empresa: {
    title: 'Empresa — quiénes somos',
    description:
      'Por qué UnClic: A→B con pipeline abierto, demos y contacto.',
  },
  integraciones: {
    title: 'Atlas de integraciones open source y escenarios | UnClic',
    description:
      'Mapa breve por escenario; cada herramienta enlaza a su documentación. Ilustrativo, no sustituye el diseño por proyecto.',
  },
  contacto: {
    title: 'Contacto — mismo registro que el resto del sitio',
    description:
      'Un solo CTA: crear cuenta (correo + verificación + contraseña). El resto de acciones, tras entrar al portal.',
  },
  insights: {
    title: 'Insights — referencias',
    description:
      'Galería, medios y perfil de encaje — mismo tono A→B que el resto del sitio.',
  },
  presupuestoOss: {
    title: 'Presupuesto open source — calculadora y API',
    description:
      'Orden de magnitud antes de comprometer capital: cuestionario + API con el mismo esquema.',
  },
  demoAccess: {
    title: 'Acceso a demos — UnClic',
    description:
      'Redirige al registro unificado (/portal/registro). Tras verificar correo e iniciar sesión, el hub y demos están en el portal.',
  },
  legalAccesoDemos: {
    title: 'Términos y política de datos — acceso a demos',
    description:
      'Marco legal mínimo para el acceso a demos.',
  },
  signup: {
    title: 'Crear cuenta — UnClic',
    description:
      'UI de registro; el flujo productivo es portal con verificación por correo.',
  },
} as const;

/** Intro + chips de salto — `/soluciones` (misma jerarquía que footer/nav Servicios). */
export const solucionesPageIntro = {
  eyebrow: 'Servicios',
  title: 'Un hub: de la duda al plan',
  lead: 'A: información dispersa. B: demos, precios orientativos y portal en una sola página — el estimador OSS si necesitas orden de magnitud antes de la llamada.',
  navLabel: 'En esta página',
  links: [
    { label: 'Hub & demos', href: '#hub-demos' },
    { label: 'Plataforma', href: '#features' },
    { label: 'Demos enlaces', href: '#demos' },
    { label: 'Precios', href: '#pricing' },
    { label: 'FAQ', href: '#faq' },
    { label: 'Portal', href: routes.portal },
    { label: 'Presupuesto OSS', href: routes.presupuestoWizard },
    { label: 'Capacidades técnicas', href: routes.capacidadesWhy },
  ] as const,
} as const;

/** Intro + chips — `/capacidades` (mega-menú «Stack OSS» + enlace al atlas). */
export const capacidadesPageIntro = {
  eyebrow: 'Stack OSS',
  title: 'Cómo llevamos el A al B',
  lead: 'Pistas técnicas escaneables; la fuente de verdad sigue siendo la documentación oficial de cada pieza y tu contexto.',
  navLabel: 'En esta página',
  links: [
    { label: 'Por qué UnClic', href: '#why' },
    { label: 'Stack técnico', href: '#stack' },
    { label: 'Flujo integración', href: '#flow' },
    { label: 'Diagramas', href: '#flow-diagrams' },
    { label: 'Arquitectura en vivo', href: '#architecture-live' },
    { label: 'Cómo empezar', href: '#how-it-works' },
    { label: 'Ecosistema & módulos', href: '#matrix-display' },
    { label: 'Atlas integraciones', href: routes.integracionesAtlas },
    { label: 'Soluciones & precios', href: routes.solucionesHub },
  ] as const,
} as const;

export const empresaPageIntro = {
  variant: 'subnav' as const,
  eyebrow: 'Empresa',
  navLabel: 'Saltar a',
  links: [
    { label: 'Resumen', href: '#empresa-hero' },
    { label: 'Por qué UnClic', href: '#why' },
    { label: 'FAQ', href: '#faq' },
    { label: 'Contacto', href: routes.contactForm },
    { label: 'Soluciones', href: routes.solucionesHub },
    { label: 'Capacidades', href: routes.capacidadesWhy },
    { label: 'Atlas integraciones', href: routes.integracionesAtlas },
    { label: 'Presupuesto OSS', href: routes.presupuestoWizard },
  ] as const,
} as const;

export const integracionesPageIntro = {
  navLabel: 'Explorar el atlas',
  links: [
    { label: 'Catálogo & filtros', href: '#oss-atlas' },
    { label: 'Capacidades técnicas', href: routes.capacidadesWhy },
    { label: 'Soluciones', href: routes.solucionesHub },
    { label: 'Presupuesto OSS', href: routes.presupuestoWizard },
    { label: 'Portal demos', href: routes.portal },
    { label: 'Empresa', href: routes.empresa },
    { label: 'Contacto', href: routes.contactForm },
  ] as const,
} as const;

export const contactoPageIntro = {
  eyebrow: 'Conectar',
  title: 'Del A al B con contexto',
  lead: 'Misma acción que en el inicio: crear cuenta. Luego, desde el portal, demos y módulos.',
  navLabel: 'Enlaces útiles',
  links: [
    { label: 'Crear cuenta', href: routes.publicSignup },
    { label: 'Iniciar sesión', href: routes.login },
    { label: 'Portal', href: routes.portal },
    { label: 'Soluciones', href: routes.solucionesHub },
    { label: 'Capacidades', href: routes.capacidadesWhy },
    { label: 'Presupuesto OSS', href: routes.presupuestoWizard },
    { label: 'Atlas integraciones', href: routes.integracionesAtlas },
  ] as const,
} as const;

export const insightsPageIntro = {
  eyebrow: 'Explorar',
  title: 'Referencias sin perder el hilo A→B',
  lead: 'Galería y medios: contexto visual; el argumento de negocio sigue en Soluciones y Empresa.',
  navLabel: 'En esta página',
  links: [
    { label: 'Galería', href: '#gallery' },
    { label: 'Vídeo', href: '#video' },
    { label: 'Audio', href: '#audio' },
    { label: 'Presencia global', href: '#globe' },
    { label: 'Destacados', href: '#social-trending-carousel' },
    { label: 'Cliente ideal', href: '#cliente-ideal' },
    { label: 'Crear cuenta', href: routes.publicSignup },
    { label: 'Soluciones', href: routes.solucionesHub },
    { label: 'Empresa', href: routes.empresaHero },
  ] as const,
} as const;

/** Chips extra — título/lead de `/presupuesto-oss` viven en `lib/copy-presupuesto-oss.ts`. */
export const presupuestoOssPageIntro = {
  navLabel: 'Más en UnClic',
  links: [
    { label: 'Calculadora', href: '#presupuesto-wizard' },
    { label: 'API & endpoints', href: '#presupuesto-api' },
    { label: 'Soluciones', href: routes.solucionesHub },
    { label: 'Capacidades', href: routes.capacidadesWhy },
    { label: 'Atlas', href: routes.integracionesAtlas },
    { label: 'Crear cuenta', href: routes.publicSignup },
  ] as const,
} as const;

/** Chips — `/demo/access` redirige a `/portal/registro` (compatibilidad). */
export const demoAccessPageIntro = {
  variant: 'subnav' as const,
  eyebrow: 'Demos',
  navLabel: 'Enlaces de acceso',
  links: [
    { label: 'Crear cuenta', href: routes.publicSignup },
    { label: 'Términos', href: routes.legalDemosTerminos },
    { label: 'Datos personales', href: routes.legalDemosDatos },
    { label: 'Login', href: routes.login },
    { label: 'Portal', href: routes.portal },
    { label: 'Hub demos', href: routes.demo },
    { label: 'Inicio', href: routes.home },
  ] as const,
} as const;

/** Chips — `/legal/acceso-demos` (un solo H1 en el cuerpo legal). */
export const legalAccesoDemosPageIntro = {
  variant: 'subnav' as const,
  eyebrow: 'Legal',
  navLabel: 'En este documento',
  links: [
    { label: 'Términos', href: '#terminos' },
    { label: 'Datos personales', href: '#datos' },
    { label: 'Crear cuenta', href: routes.publicSignup },
    { label: 'Portal', href: routes.portal },
    { label: 'Crear cuenta', href: routes.publicSignup },
  ] as const,
} as const;

/** Párrafo bajo chips — `/legal/acceso-demos`. */
export const legalAccesoDemosLead =
  'Documento de referencia para quien solicita acceso. Versión provisional: revísalo con asesoría legal antes de producción.';

/** Chips — `/signup` redirige a `/portal/registro`. */
export const signupPageIntro = {
  variant: 'subnav' as const,
  eyebrow: 'Cuenta',
  navLabel: 'Enlaces útiles',
  links: [
    { label: 'Registro', href: routes.publicSignup },
    { label: 'Iniciar sesión', href: routes.login },
    { label: 'Portal', href: routes.portal },
    { label: 'Soluciones', href: routes.solucionesHub },
  ] as const,
} as const;

/**
 * Ejemplo de flujo bloque + copy — ruta `/flow-demo`.
 * Ver docs/EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md (comando: npx shadcn add progress -y).
 */
export const flowDemo = {
  kicker: 'Ejemplo A→B',
  title: 'Progreso visible = menos incertidumbre',
  lead: 'A: etapas solo en la cabeza. B: ver el avance como en un pipeline (demo UI).',
  progressLabel: 'Avance del pipeline (demo visual)',
  /** 0–100 para el componente Progress */
  progressValue: 66,
  badges: ['Validar', 'Construir', 'Operar'] as const,
  footnote: 'Pista técnica: el texto vive en `lib/copy.ts` — un solo sitio para editar.',
} as const;

/** Carrusel de logos (Shadcn Blocks: Logos12) — stack o partners. image = URL (ej. Simple Icons) o path en public. */
export const logosCarousel = {
  sectionTitle: 'Stack que aparece en demo (pista)',
  items: [
    { id: 'jenkins', description: 'Jenkins', image: 'https://cdn.simpleicons.org/jenkins/D24939' },
    { id: 'gitea', description: 'Gitea', image: 'https://cdn.simpleicons.org/gitea/609926' },
    { id: 'docker', description: 'Docker', image: 'https://cdn.simpleicons.org/docker/2496ED' },
    { id: 'terraform', description: 'Terraform', image: 'https://cdn.simpleicons.org/terraform/7B42BC' },
    { id: 'kubernetes', description: 'Kubernetes', image: 'https://cdn.simpleicons.org/kubernetes/326CE5' },
    { id: 'maven', description: 'Maven', image: 'https://cdn.simpleicons.org/apachemaven/C71A36' },
    { id: 'aws', description: 'AWS', image: 'https://cdn.simpleicons.org/amazonaws/FF9900' },
    { id: 'nextjs', description: 'Next.js', image: 'https://cdn.simpleicons.org/nextdotjs/000000' },
  ] as ReadonlyArray<{ id: string; description: string; image: string }>,
} as const;

/**
 * Atlas ilustrativo OSS + integraciones SaaS — ruta `/integraciones`.
 * Base visual para cartera amplia: cada herramienta enlaza a documentación oficial.
 * No ejecuta los servicios en el navegador; es mapa vivo para clientes y el plan técnico.
 */
export const ossAtlas = {
  path: routes.integraciones,
  seoTitle: 'Atlas de integraciones open source y escenarios | UnClic',
  seoDescription:
    'Atlas breve: escenarios (A→B) y herramientas con enlace a documentación oficial — mapa, no sustituto del diseño.',
  pageEyebrow: 'Cartera técnica',
  pageTitle: 'Atlas de integraciones',
  pageLead:
    'A: demasiadas opciones sueltas. B: ver en qué capa encaja cada pieza. Cada tarjeta = una pista + doc oficial del proyecto.',
  pageSub:
    'Filtra por escenario. Profundidad: carpetas `docs/` del repo (plan OSS, curación, arquetipos).',
  filterAll: 'Todos los escenarios',
  filterLabel: 'Escenario',
  cardLayer: 'Capa',
  cardScenarios: 'Casos',
  docCta: 'Documentación oficial',
  kindOss: 'Open source',
  kindSaaS: 'Integración SaaS',
  teaserKicker: 'Mismo marco, distintos perfiles',
  teaserTitle: 'Herramientas y escenarios',
  teaserLead:
    'Un vistazo: qué capa toca cada herramienta. Detalle en el doc oficial de cada ítem.',
  teaserCta: 'Abrir atlas completo',
  footnote:
    'Ilustrativo: la elección final depende de tu A, tu B y tu presupuesto. Implementación por fases — ver plan OSS en el repo.',
} as const;

/** Escenarios del atlas — ids usados en `ossAtlasTools`. */
export const ossAtlasScenarios = [
  {
    id: 'entrega',
    title: 'Entrega continua',
    blurb: 'Código versionado, builds reproducibles, imágenes en registry.',
  },
  {
    id: 'runtime',
    title: 'Runtime y borde',
    blurb: 'Dónde corre la app, TLS, enrutamiento y PaaS self-hosted.',
  },
  {
    id: 'datos',
    title: 'Datos y mensajería',
    blurb: 'Persistencia, cache, colas y eventos para picos y desacople.',
  },
  {
    id: 'seguridad',
    title: 'Identidad y equipo',
    blurb: 'Login centralizado y colaboración operativa.',
  },
  {
    id: 'observabilidad',
    title: 'Observabilidad',
    blurb: 'Métricas, tableros y trazas para operar con evidencia.',
  },
  {
    id: 'negocio',
    title: 'ERP y back office',
    blurb: 'Inventario, ventas, contabilidad y maestros corporativos.',
  },
  {
    id: 'comercial',
    title: 'Pagos y fiscal',
    blurb: 'Cobro al cliente y comprobantes; SaaS con adaptadores en tu API.',
  },
  {
    id: 'ia',
    title: 'IA / ML / agentes',
    blurb: 'A: hype. B: modelo con trazabilidad — Ollama, MLflow, n8n, LangGraph (docs oficiales).',
  },
  {
    id: 'devtools',
    title: 'DX / toolchain',
    blurb: 'A: herramientas nuevas cada mes. B: curar 2–3 que sostengan el pipeline (referencias técnicas públicas).',
  },
  {
    id: 'operacion',
    title: 'Operación empresa OSS',
    blurb: 'A: silos de proveedor. B: stack OSS con política de sourcing explícita (referencias sector públicas).',
  },
] as const;

/** Herramientas del atlas — `icon` = slug_hex para cdn.simpleicons.org (sin #). */
export const ossAtlasTools = [
  {
    id: 'gitea',
    name: 'Gitea',
    layer: 'L1',
    kind: 'oss' as const,
    scenarioIds: ['entrega'] as const,
    tagline: 'Git self-hosted ligero; webhooks hacia CI.',
    docUrl: 'https://docs.gitea.com/',
    icon: 'gitea/609926',
  },
  {
    id: 'jenkins',
    name: 'Jenkins',
    layer: 'L2',
    kind: 'oss' as const,
    scenarioIds: ['entrega'] as const,
    tagline: 'Pipeline as Code; build, test, empaquetado.',
    docUrl: 'https://www.jenkins.io/doc/',
    icon: 'jenkins/D24939',
  },
  {
    id: 'forgejo',
    name: 'Forgejo',
    layer: 'L1',
    kind: 'oss' as const,
    scenarioIds: ['entrega'] as const,
    tagline: 'Alternativa OSS a Gitea; mismo patrón Git + Actions.',
    docUrl: 'https://forgejo.org/docs/latest/',
    icon: null,
  },
  {
    id: 'docker',
    name: 'Docker',
    layer: 'L4–L5',
    kind: 'oss' as const,
    scenarioIds: ['entrega', 'runtime'] as const,
    tagline: 'Imágenes OCI; mismo artefacto en dev y prod.',
    docUrl: 'https://docs.docker.com/',
    icon: 'docker/2496ED',
  },
  {
    id: 'harbor',
    name: 'Harbor',
    layer: 'L4',
    kind: 'oss' as const,
    scenarioIds: ['entrega', 'runtime'] as const,
    tagline: 'Registry con políticas, escaneo y proyectos.',
    docUrl: 'https://goharbor.io/docs/',
    icon: 'harbor/60B932',
  },
  {
    id: 'opentofu',
    name: 'OpenTofu',
    layer: 'L18',
    kind: 'oss' as const,
    scenarioIds: ['entrega', 'runtime'] as const,
    tagline: 'Infra declarativa; entornos repetibles.',
    docUrl: 'https://opentofu.org/docs/',
    icon: 'opentofu/FFDA18',
  },
  {
    id: 'coolify',
    name: 'Coolify',
    layer: 'L5',
    kind: 'oss' as const,
    scenarioIds: ['runtime'] as const,
    tagline: 'PaaS self-hosted sobre tus VPS; deploy desde Git.',
    docUrl: 'https://coolify.io/docs',
    icon: null,
  },
  {
    id: 'traefik',
    name: 'Traefik',
    layer: 'L9',
    kind: 'oss' as const,
    scenarioIds: ['runtime'] as const,
    tagline: 'Proxy dinámico, TLS y descubrimiento de servicios.',
    docUrl: 'https://doc.traefik.io/',
    icon: 'traefik/24A1C7',
  },
  {
    id: 'caddy',
    name: 'Caddy',
    layer: 'L9',
    kind: 'oss' as const,
    scenarioIds: ['runtime'] as const,
    tagline: 'HTTPS automático; reverse proxy sencillo.',
    docUrl: 'https://caddyserver.com/docs/',
    icon: 'caddy/1F8DCF',
  },
  {
    id: 'nginx',
    name: 'NGINX',
    layer: 'L9',
    kind: 'oss' as const,
    scenarioIds: ['runtime'] as const,
    tagline: 'Alto rendimiento; terminación TLS y balanceo.',
    docUrl: 'https://nginx.org/en/docs/',
    icon: 'nginx/009639',
  },
  {
    id: 'nextjs',
    name: 'Next.js',
    layer: 'L17',
    kind: 'oss' as const,
    scenarioIds: ['runtime', 'comercial'] as const,
    tagline: 'UI y sitios; en UnClic como base del hub público.',
    docUrl: 'https://nextjs.org/docs',
    icon: 'nextdotjs/000000',
  },
  {
    id: 'postgresql',
    name: 'PostgreSQL',
    layer: 'L6',
    kind: 'oss' as const,
    scenarioIds: ['datos'] as const,
    tagline: 'OLTP robusto; transacciones y JSON cuando hace falta.',
    docUrl: 'https://www.postgresql.org/docs/',
    icon: 'postgresql/4169E1',
  },
  {
    id: 'redis',
    name: 'Redis',
    layer: 'L7',
    kind: 'oss' as const,
    scenarioIds: ['datos'] as const,
    tagline: 'Cache, rate limit, sesiones de corta duración.',
    docUrl: 'https://redis.io/docs/',
    icon: 'redis/FF4438',
  },
  {
    id: 'rabbitmq',
    name: 'RabbitMQ',
    layer: 'L8',
    kind: 'oss' as const,
    scenarioIds: ['datos'] as const,
    tagline: 'Colas AMQP; desacople entre API y workers.',
    docUrl: 'https://www.rabbitmq.com/documentation.html',
    icon: 'rabbitmq/FF6600',
  },
  {
    id: 'nats',
    name: 'NATS',
    layer: 'L8',
    kind: 'oss' as const,
    scenarioIds: ['datos'] as const,
    tagline: 'Mensajería ligera; pub/sub y streams.',
    docUrl: 'https://docs.nats.io/',
    icon: 'nats/34A574',
  },
  {
    id: 'kafka',
    name: 'Apache Kafka',
    layer: 'L8',
    kind: 'oss' as const,
    scenarioIds: ['datos'] as const,
    tagline: 'Log distribuido; muchos consumidores y auditoría.',
    docUrl: 'https://kafka.apache.org/documentation/',
    icon: 'apachekafka/231F20',
  },
  {
    id: 'lavinmq',
    name: 'LavinMQ',
    layer: 'L8',
    kind: 'oss' as const,
    scenarioIds: ['datos'] as const,
    tagline:
      'Broker AMQP liviano con streams y métricas Prometheus; alternativa a RabbitMQ en equipos que valoran footprint bajo.',
    docUrl: 'https://docs.lavinmq.com/',
    icon: null,
  },
  {
    id: 'keycloak',
    name: 'Keycloak',
    layer: 'L10',
    kind: 'oss' as const,
    scenarioIds: ['seguridad'] as const,
    tagline: 'OIDC/OAuth2; SSO para paneles y APIs internas.',
    docUrl: 'https://www.keycloak.org/documentation',
    icon: 'keycloak/4D4D4D',
  },
  {
    id: 'workos',
    name: 'WorkOS',
    layer: 'L10',
    kind: 'saas' as const,
    scenarioIds: ['seguridad'] as const,
    tagline:
      'Pago: SSO enterprise (SAML/OIDC), Directory Sync/SCIM, Admin Portal; pricing en workos.com/pricing. Criterios en docs/WORKOS-ENTERPRISE-SSO-REFERENCIA-UNClic.md.',
    docUrl: 'https://workos.com/docs',
    icon: null,
  },
  {
    id: 'mattermost',
    name: 'Mattermost',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['seguridad'] as const,
    tagline: 'Chat de equipo self-hosted; alertas desde CI/observabilidad.',
    docUrl: 'https://docs.mattermost.com/',
    icon: 'mattermost/0058CC',
  },
  {
    id: 'prometheus',
    name: 'Prometheus',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['observabilidad'] as const,
    tagline: 'Métricas pull; reglas y alertmanager.',
    docUrl: 'https://prometheus.io/docs/',
    icon: 'prometheus/E6522C',
  },
  {
    id: 'grafana',
    name: 'Grafana',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['observabilidad'] as const,
    tagline: 'Dashboards; une métricas, logs y trazas.',
    docUrl: 'https://grafana.com/docs/',
    icon: 'grafana/F46800',
  },
  {
    id: 'dapr',
    name: 'Dapr',
    layer: 'L5–L8',
    kind: 'oss' as const,
    scenarioIds: ['datos', 'runtime'] as const,
    tagline: 'Abstracción sidecar: pub/sub, estado, secretos.',
    docUrl: 'https://docs.dapr.io/',
    icon: 'dapr/0D2192',
  },
  {
    id: 'temporal',
    name: 'Temporal',
    layer: 'L8',
    kind: 'oss' as const,
    scenarioIds: ['datos', 'comercial'] as const,
    tagline: 'Workflows duraderos; sagas de pago + ERP + correo.',
    docUrl: 'https://docs.temporal.io/',
    icon: 'temporal/444CE7',
  },
  {
    id: 'erpnext',
    name: 'ERPNext',
    layer: 'L16',
    kind: 'oss' as const,
    scenarioIds: ['negocio'] as const,
    tagline: 'ERP sobre Frappe; ventas, stock, contabilidad.',
    docUrl: 'https://docs.erpnext.com/',
    icon: null,
  },
  {
    id: 'stripe',
    name: 'Stripe',
    layer: 'L14',
    kind: 'saas' as const,
    scenarioIds: ['comercial'] as const,
    tagline: 'Pagos globales; webhooks hacia tu API (adaptador).',
    docUrl: 'https://stripe.com/docs',
    icon: 'stripe/635BFF',
  },
  {
    id: 'facturapi',
    name: 'Facturapi',
    layer: 'L15',
    kind: 'saas' as const,
    scenarioIds: ['comercial'] as const,
    tagline: 'Facturación electrónica México; timbrado vía API.',
    docUrl: 'https://docs.facturapi.io/',
    icon: null,
  },
  {
    id: 'polar',
    name: 'Polar',
    layer: 'L14+L19',
    kind: 'saas' as const,
    scenarioIds: ['comercial', 'ia'] as const,
    tagline:
      'Billing SaaS: checkout, usage (tokens IA), MoR/taxes; adaptador Next. Criterios vs Stripe en docs/PAGOS-BILLING-*.md.',
    docUrl: 'https://docs.polar.sh',
    icon: null,
  },
  {
    id: 'lago',
    name: 'Lago',
    layer: 'L14',
    kind: 'oss' as const,
    scenarioIds: ['comercial'] as const,
    tagline:
      'Motor billing OSS/cloud: metering, planes híbridos; conecta Stripe/Adyen… Self-host opcional.',
    docUrl: 'https://getlago.com/docs',
    icon: null,
  },
  {
    id: 'hyperswitch',
    name: 'Hyperswitch',
    layer: 'L14',
    kind: 'oss' as const,
    scenarioIds: ['comercial'] as const,
    tagline:
      'Orquestación pagos OSS: multi-PSP, vault, routing, APMs; hosted Juspay opcional.',
    docUrl: 'https://docs.hyperswitch.io',
    icon: null,
  },
  {
    id: 'pismo',
    name: 'Pismo',
    layer: 'L14',
    kind: 'saas' as const,
    scenarioIds: ['comercial'] as const,
    tagline:
      'BaaS / card issuing / wallets — nicho fintech; no sustituye checkout típico UnClic.',
    docUrl: 'https://pismo.io',
    icon: null,
  },
  {
    id: 'ollama',
    name: 'Ollama',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia', 'devtools'] as const,
    tagline: 'Ejecutar LLMs abiertos en local o servidor; privacidad y coste predecible.',
    docUrl: 'https://github.com/ollama/ollama',
    icon: 'ollama/000000',
  },
  {
    id: 'docker-model-runner',
    name: 'Docker Model Runner',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia', 'entrega', 'runtime'] as const,
    tagline:
      'LLMs 100% locales vía Docker: pull desde Hub, APIs OpenAI/Ollama-compat, plugin en Linux o Desktop con AI habilitado.',
    docUrl: 'https://docs.docker.com/ai/model-runner/get-started/',
    icon: 'docker/2496ED',
  },
  {
    id: 'mlflow',
    name: 'MLflow',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'Tracking de experimentos, modelos y despliegue ML.',
    docUrl: 'https://mlflow.org/docs/latest/index.html',
    icon: null,
  },
  {
    id: 'pytorch',
    name: 'PyTorch',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'Entrenamiento e investigación deep learning.',
    docUrl: 'https://pytorch.org/docs/stable/index.html',
    icon: 'pytorch/EE4C2C',
  },
  {
    id: 'tensorflow',
    name: 'TensorFlow',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'ML end-to-end; ecosistema amplio en industria.',
    docUrl: 'https://www.tensorflow.org/learn',
    icon: 'tensorflow/FF6F00',
  },
  {
    id: 'keras',
    name: 'Keras',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'API de alto nivel para redes neuronales (típico sobre TensorFlow).',
    docUrl: 'https://keras.io/',
    icon: null,
  },
  {
    id: 'n8n',
    name: 'n8n',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia', 'datos'] as const,
    tagline: 'Workflows y automatización; patrones “agente + herramientas” low-code.',
    docUrl: 'https://docs.n8n.io/',
    icon: 'n8n/EA4B71',
  },
  {
    id: 'langgraph',
    name: 'LangGraph',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'Grafos de agentes con estado; multi-paso y bucles controlados.',
    docUrl: 'https://langchain-ai.github.io/langgraph/',
    icon: 'langchain/121317',
  },
  {
    id: 'hexabot',
    name: 'Hexabot',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'Plataforma conversacional OSS; evaluar licencia y roadmap por cliente.',
    docUrl: 'https://www.hexabot.io/',
    icon: null,
  },
  {
    id: 'stablestudio',
    name: 'Stable Studio',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'UI para flujos generativos (ecosistema Stability).',
    docUrl: 'https://github.com/Stability-AI/stable-studio',
    icon: null,
  },
  {
    id: 'gpt4all',
    name: 'GPT4All',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['ia'] as const,
    tagline: 'Ejecución local de modelos; buen fit privacidad en equipos cliente.',
    docUrl: 'https://gpt4all.io/',
    icon: null,
  },
  {
    id: 'openai-agents-sdk',
    name: 'OpenAI Agents SDK',
    layer: 'L19',
    kind: 'saas' as const,
    scenarioIds: ['ia'] as const,
    tagline:
      'SDK para agentes (vendor); combinable con modelos abiertos vía Ollama en tutoriales híbridos.',
    docUrl: 'https://openai.github.io/openai-agents-python/',
    icon: 'openai/412991',
  },
  {
    id: 'biome',
    name: 'Biome',
    layer: 'L2',
    kind: 'oss' as const,
    scenarioIds: ['entrega', 'devtools'] as const,
    tagline:
      'Toolchain JS/TS (lint + format) en Rust; alternativa agresiva a ESLint+Prettier en repos nuevos.',
    docUrl: 'https://biomejs.dev/guides/getting-started/',
    icon: null,
  },
  {
    id: 'bun',
    name: 'Bun',
    layer: 'L5',
    kind: 'oss' as const,
    scenarioIds: ['runtime', 'devtools'] as const,
    tagline: 'Runtime + bundler + test runner JS/TS; alternativa a Node donde el equipo acepte el ecosistema.',
    docUrl: 'https://bun.sh/docs',
    icon: null,
  },
  {
    id: 'deno',
    name: 'Deno',
    layer: 'L5',
    kind: 'oss' as const,
    scenarioIds: ['runtime', 'devtools'] as const,
    tagline: 'Runtime TypeScript-first, permisos explícitos; microservicios o edge según caso.',
    docUrl: 'https://docs.deno.com/',
    icon: 'deno/000000',
  },
  {
    id: 'zed',
    name: 'Zed',
    layer: 'L1',
    kind: 'oss' as const,
    scenarioIds: ['entrega', 'devtools'] as const,
    tagline:
      'Editor OSS con enfoque rendimiento e IA integrada; vive en el laptop del equipo, no en el servidor.',
    docUrl: 'https://zed.dev/docs/',
    icon: null,
  },
  {
    id: 'turso',
    name: 'Turso (libSQL)',
    layer: 'L6',
    kind: 'oss' as const,
    scenarioIds: ['datos', 'devtools'] as const,
    tagline:
      'SQLite replicado / edge; útil para apps multi-región o datos locales sincronizados — evaluar vs Postgres monolito.',
    docUrl: 'https://docs.turso.tech/',
    icon: null,
  },
  {
    id: 'ruff',
    name: 'Ruff',
    layer: 'L2',
    kind: 'oss' as const,
    scenarioIds: ['entrega', 'devtools'] as const,
    tagline: 'Linter Python ultrarrápido (Rust); pipelines que mezclan Node + Python (workers IA, LangGraph).',
    docUrl: 'https://docs.astral.sh/ruff/',
    icon: null,
  },
  {
    id: 'astro',
    name: 'Astro',
    layer: 'L17',
    kind: 'oss' as const,
    scenarioIds: ['runtime', 'devtools'] as const,
    tagline:
      'Front orientado a contenido; alternativa creativa a Next para landings/documentación — UnClic sigue en Next por decisión actual.',
    docUrl: 'https://docs.astro.build/',
    icon: 'astro/BC52EE',
  },
  {
    id: 'continue',
    name: 'Continue',
    layer: 'L1',
    kind: 'oss' as const,
    scenarioIds: ['ia', 'devtools'] as const,
    tagline:
      'Asistente código OSS en el IDE (modelos locales o cloud); acelera plantillas y adaptadores, no sustituye revisión humana.',
    docUrl: 'https://docs.continue.dev/',
    icon: null,
  },
  {
    id: 'xcp-ng',
    name: 'XCP-ng',
    layer: 'Virtualización',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'entrega', 'runtime'] as const,
    tagline:
      'Hipervisor OSS (fork Citrix Xen); datacenter propio; producto Vates.',
    docUrl: 'https://docs.xcp-ng.org/',
    icon: null,
  },
  {
    id: 'xen-orchestra',
    name: 'Xen Orchestra',
    layer: 'Virtualización',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'runtime'] as const,
    tagline: 'Consola backup/VM para XCP-ng; línea Vates.',
    docUrl: 'https://xen-orchestra.com/docs/',
    icon: null,
  },
  {
    id: 'nextcloud',
    name: 'Nextcloud',
    layer: 'L16',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'negocio'] as const,
    tagline: 'Archivos y colaboración self-host; patrón “empresa sin suite cerrada”.',
    docUrl: 'https://docs.nextcloud.com/',
    icon: 'nextcloud/0082C9',
  },
  {
    id: 'metabase',
    name: 'Metabase',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'observabilidad'] as const,
    tagline: 'BI y dashboards; ejemplo real migración desde stack ELK (Vates).',
    docUrl: 'https://www.metabase.com/docs/latest/',
    icon: null,
  },
  {
    id: 'plane',
    name: 'Plane',
    layer: 'L16',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'negocio'] as const,
    tagline: 'PM open core; issues, sprints, roadmaps (sustituto Wekan en caso Vates).',
    docUrl: 'https://docs.plane.so/',
    icon: null,
  },
  {
    id: 'grist',
    name: 'Grist',
    layer: 'L6',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'datos'] as const,
    tagline:
      'Hoja relacional (CSV → app interna); Docker, permisos, widgets y API. Tutorial práctico: Lawrence Systems.',
    docUrl: 'https://support.getgrist.com/',
    icon: null,
    extraLinks: [
      {
        label: 'Tutorial Docker (Lawrence Systems)',
        href: 'https://lawrence.video/grist',
      },
      {
        label: 'Grist en Docker (oficial)',
        href: 'https://support.getgrist.com/install/grist-on-docker/',
      },
      {
        label: 'Grist Con 2025',
        href: 'https://lawrence.video/gristcon',
      },
    ] as const,
  },
  {
    id: 'formbricks',
    name: 'Formbricks',
    layer: 'L17',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'comercial'] as const,
    tagline: 'Formularios y experiencia self-host.',
    docUrl: 'https://formbricks.com/docs',
    icon: null,
  },
  {
    id: 'vaultwarden',
    name: 'Vaultwarden',
    layer: 'L11',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'seguridad'] as const,
    tagline: 'Servidor compatible Bitwarden (Rust); secretos de equipo.',
    docUrl: 'https://github.com/dani-garcia/vaultwarden',
    icon: null,
  },
  {
    id: 'bigbluebutton',
    name: 'BigBlueButton',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'seguridad'] as const,
    tagline: 'Videoconferencia y grabación; reuniones grandes.',
    docUrl: 'https://docs.bigbluebutton.org/',
    icon: null,
  },
  {
    id: 'espocrm',
    name: 'EspoCRM',
    layer: 'L16',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'negocio', 'comercial'] as const,
    tagline: 'CRM self-host; automatización ventas con packs según doc.',
    docUrl: 'https://docs.espocrm.com/',
    icon: null,
  },
  {
    id: 'netdata',
    name: 'Netdata',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'observabilidad'] as const,
    tagline: 'Métricas por host en tiempo real.',
    docUrl: 'https://learn.netdata.cloud/docs/',
    icon: null,
  },
  {
    id: 'uptime-kuma',
    name: 'Uptime Kuma',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'observabilidad'] as const,
    tagline: 'Monitoreo uptime y páginas de estado.',
    docUrl: 'https://github.com/louislam/uptime-kuma',
    icon: null,
  },
  {
    id: 'netalertx',
    name: 'NetAlertX',
    layer: 'L12',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'observabilidad', 'runtime'] as const,
    tagline:
      'Descubrimiento de dispositivos en LAN, plugins (UniFi, Nmap), alertas ante cambios; complementa Kuma/Netdata.',
    docUrl: 'https://docs.netalertx.com/',
    icon: null,
    extraLinks: [
      {
        label: 'Tutorial Docker (Lawrence Systems)',
        href: 'https://www.youtube.com/watch?v=R3b5cxLZMpo',
      },
      {
        label: 'Instalación Docker (oficial)',
        href: 'https://docs.netalertx.com/DOCKER_INSTALLATION/',
      },
      {
        label: 'Hilo foro Lawrence + contexto',
        href: 'https://forums.lawrencesystems.com/t/discover-monitor-your-network-with-this-self-hosted-open-source-tool-youtube-release/25181',
      },
    ] as const,
  },
  {
    id: 'rustdesk',
    name: 'RustDesk',
    layer: 'L10',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'seguridad', 'runtime'] as const,
    tagline:
      'Escritorio remoto self-host (hbbs/hbbr); alternativa a TeamViewer/AnyDesk con control de datos.',
    docUrl: 'https://rustdesk.com/docs/en/',
    icon: null,
    extraLinks: [
      {
        label: 'Tutorial (Lawrence Systems)',
        href: 'https://www.youtube.com/watch?v=FIEcTNjFZNA',
      },
      {
        label: 'Servidor Docker (oficial)',
        href: 'https://rustdesk.com/docs/en/self-host/rustdesk-server-oss/docker/',
      },
      {
        label: 'Hilo foro Lawrence',
        href: 'https://forums.lawrencesystems.com/t/open-source-self-hosted-teamviewer-replacement-youtube-release/24183',
      },
    ] as const,
  },
  {
    id: 'openwebui',
    name: 'Open WebUI',
    layer: 'L19',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'ia', 'devtools'] as const,
    tagline: 'UI tipo ChatGPT self-host; Ollama y APIs compatibles OpenAI.',
    docUrl: 'https://docs.openwebui.com/',
    icon: null,
  },
  {
    id: 'docusaurus',
    name: 'Docusaurus',
    layer: 'L17',
    kind: 'oss' as const,
    scenarioIds: ['operacion', 'entrega', 'devtools'] as const,
    tagline: 'Docs y sitios estáticos; Vates para documentación XCP-ng / XO.',
    docUrl: 'https://docusaurus.io/docs',
    icon: null,
  },
] as const;

/** Icon Cloud — stack / tecnologías (Magic UI). */
export const iconCloud = {
  sectionTitle: 'Stack',
  sectionDescription: 'A: “¿qué usáis?”. B: misma foto en demo y doc.',
} as const;

/** Sección globo 3D — México, SAT / factura digital, alcance internacional. */
export const globeSection = {
  title: 'Presencia global',
  description:
    'A: regulación local (p. ej. México / SAT). B: mismo pipeline de confianza para equipos en otras regiones cuando el reto lo pide.',
} as const;

/** Animated Beam — cadena fiel a la matriz de conexiones (sin aristas falsas). */
export const animatedBeam = {
  sectionTitle: 'Flujo de integración',
  sectionDescription: 'A: push manual. B: cadena repetible.',
  /** Texto bajo el diagrama (sección sin título). */
  tagline: 'Build · test · deploy — mismo orden, cada vez.',
  pipelineSteps: 'COMMIT · BUILD · DEPLOY',
  integrationEdgeLabels: [
    'Push',
    'Clone + Jenkinsfile',
    'Maven (etapa pipeline)',
    'Local 8111 o SSH/scp',
  ] as const,
  integrationConvergeNote:
    'Aristas unidireccionales; Jenkins despliega tras el build.',
  integrationInputsNote: 'Código y Jenkinsfile en Gitea; el pipeline clona.',
  integrationOneLiner: 'Push → Gitea → Jenkins → Maven → deploy.',
} as const;

/** Dominio genérico para URLs de arquitectura enterprise (control de accesos bajo un solo dominio). */
const DOMINIO_EMPRESA = 'empresa.internal';

/** Diagramas de flujo: integración, demo y producción (basados en REPLICAR-FLUJO-COMPLETO). */
export const flowDiagrams = {
  sectionTitle: 'Demo vs producción',
  sectionDescription: 'Misma idea, dos entornos: una EC2 o app dedicada.',
  /** Dominio ejemplo para URLs de arquitectura enterprise (subdominios por servicio). */
  dominioEmpresa: DOMINIO_EMPRESA,
  legend: {
    unidirectional: 'Unidireccional (origen → destino)',
    bidirectional: 'Bidireccional (comunicación en ambos sentidos)',
  },
  integration: {
    title: 'Integración',
    description: 'Git → Gitea → Jenkins → build → deploy. Dominio propio.',
    nodes: [
      { title: 'Tu repo', url: null },
      { title: 'Git', url: null },
      { title: 'Gitea', url: `https://gitea.${DOMINIO_EMPRESA}/repos` },
      { title: 'Código', url: `https://gitea.${DOMINIO_EMPRESA}/repo/pos-online` },
      { title: 'Jenkinsfile', url: `https://gitea.${DOMINIO_EMPRESA}/repo/pos-online/Jenkinsfile` },
      { title: 'Build', url: `https://jenkins.${DOMINIO_EMPRESA}/job/pos-online` },
      { title: 'Pipeline', url: `https://jenkins.${DOMINIO_EMPRESA}/pipeline` },
      { title: 'Deploy', url: `https://pos.${DOMINIO_EMPRESA}` },
    ] as const,
  },
  /** URLs por nombre de nodo (dominio/ruta/servicio) para demo y flujo real. */
  nodeUrls: {
    Desarrollador: null,
    Gitea: `https://gitea.${DOMINIO_EMPRESA}`,
    Jenkins: `https://jenkins.${DOMINIO_EMPRESA}`,
    'App :8111': `http://jenkins.${DOMINIO_EMPRESA}:8111`,
    Usuario: null,
    Build: `https://jenkins.${DOMINIO_EMPRESA}/job/pos-online/build`,
    App: `https://pos.${DOMINIO_EMPRESA}`,
  } as const,
  demo: {
    title: 'Demo (una EC2)',
    description: 'Jenkins + Gitea en el mismo host · app :8111.',
    nodes: ['Desarrollador', 'Gitea', 'Jenkins', 'App :8111', 'Usuario'] as const,
    steps: [
      { from: 'Desarrollador', to: 'Gitea', step: 1, label: 'push', direction: 'unidireccional' as const },
      { from: 'Gitea', to: 'Jenkins', step: 2, label: 'clone / SCM', direction: 'unidireccional' as const },
      { from: 'Jenkins', to: 'App :8111', step: 3, label: 'build + deploy local', direction: 'unidireccional' as const },
      { from: 'Usuario', to: 'App :8111', step: 4, label: 'acceso HTTP', direction: 'bidireccional' as const },
    ],
  },
  real: {
    title: 'Producción (app dedicada)',
    description: 'Deploy SSH/scp · HTTPS en tu dominio.',
    nodes: ['Desarrollador', 'Gitea', 'Jenkins', 'Build', 'App', 'Usuario'] as const,
    steps: [
      { from: 'Desarrollador', to: 'Gitea', step: 1, label: 'git push gitea main', direction: 'unidireccional' as const },
      { from: 'Gitea', to: 'Jenkins', step: 2, label: 'Pipeline from SCM, clone', direction: 'unidireccional' as const },
      { from: 'Jenkins', to: 'Build', step: 3, label: 'Maven package', direction: 'unidireccional' as const },
      { from: 'Jenkins', to: 'App', step: 4, label: 'scp JAR + ssh java -jar', direction: 'unidireccional' as const },
      { from: 'Jenkins', to: 'App', step: 5, label: 'Verify (curl)', direction: 'unidireccional' as const },
      { from: 'Usuario', to: 'App', step: 6, label: 'HTTPS app', direction: 'bidireccional' as const },
    ],
  },
  topology: {
    title: 'Topología',
    description: 'Local → Gitea → Jenkins → app → usuario.',
    nodes: [
      { label: 'Local', path: 'repo local', url: null },
      { label: 'Gitea', path: `gitea.${DOMINIO_EMPRESA}/repos`, url: `https://gitea.${DOMINIO_EMPRESA}` },
      { label: 'Jenkins', path: `jenkins.${DOMINIO_EMPRESA}/job`, url: `https://jenkins.${DOMINIO_EMPRESA}` },
      { label: 'Build', path: `jenkins.${DOMINIO_EMPRESA}/build`, url: null },
      { label: 'App', path: `pos.${DOMINIO_EMPRESA}`, url: `https://pos.${DOMINIO_EMPRESA}` },
      { label: 'Usuario', path: 'acceso HTTPS', url: null },
    ] as const,
  },
  connectionMatrix: {
    title: 'Matriz de conexiones',
    rows: [
      { from: 'Código local', to: 'Gitea', step: 'Push', direction: 'unidireccional' as const },
      { from: 'Gitea', to: 'Jenkins', step: 'Clone + Jenkinsfile', direction: 'unidireccional' as const },
      { from: 'Jenkins', to: 'Build', step: 'Maven', direction: 'unidireccional' as const },
      { from: 'Jenkins', to: 'Deploy', step: 'Local 8111 o SSH/scp', direction: 'unidireccional' as const },
      { from: 'Usuario', to: 'App', step: 'Acceso app', direction: 'bidireccional' as const },
    ],
  },
} as const;

/** ID del blueprint de Cloudcraft con la arquitectura de la demo (FastFlow, Jenkins, Gitea, POS). Editor: https://app.cloudcraft.co/blueprint/504a8a6e-fca3-404d-a280-63ab8fe9d0c1 */
export const CLOUDCRAFT_DEMO_BLUEPRINT_ID = '504a8a6e-fca3-404d-a280-63ab8fe9d0c1';
/** URL de vista del diagrama de la demo (sin key). Con key = enlace compartible en Share & Export. */
export const CLOUDCRAFT_DEMO_VIEW_BASE = `https://app.cloudcraft.co/view/${CLOUDCRAFT_DEMO_BLUEPRINT_ID}`;

/** Arquitectura — mínimo: título + diagrama exportado / enlace Cloudcraft. */
export const architectureLive = {
  sectionTitle: 'Arquitectura AWS',
  sectionDescription: 'A: caja negra. B: diagrama compartible (Cloudcraft) para auditoría.',
  ctaOpen: 'Abrir en Cloudcraft',
  ctaOpenDemo: 'Abrir diagrama',
  iframeTitle: 'Arquitectura AWS (Cloudcraft)',
  imageAlt: 'Diagrama arquitectura AWS (Cloudcraft)',
  lockedLead:
    'El diagrama en vivo en Cloudcraft está disponible tras iniciar sesión con un correo autorizado.',
  lockedCta: 'Iniciar sesión',
} as const;

/**
 * Por qué Pipeline as Code — problema/solución, lenguaje claro.
 */
export const why = {
  sectionTitle: 'Por qué UnClic',
  oneTeamOnePlatform: '',
  sectionDescription: 'A: silos de herramientas. B: un hilo Git → pipeline → deploy.',
  problems: {
    title: 'Fricción habitual',
    items: [] as const,
  },
  benefitPillars: [
    {
      title: 'Decisiones claras',
      description: 'A: reuniones sin artefacto. B: commit con evidencia.',
    },
    {
      title: 'Menos retrabajo',
      description: 'A: deploys “a mano”. B: mismo pipeline cada vez.',
    },
    {
      title: 'Equipo alineado',
      description: 'A: doc desactualizada. B: repo como fuente de verdad.',
    },
    {
      title: 'Negocio cubierto',
      description: 'A: miedo al pico. B: rollback con tag conocido.',
    },
  ] as ReadonlyArray<{ title: string; description: string }>,
  solutions: { title: '', items: [] as const },
} as const;

/**
 * Qué ofrecemos — beneficio + una línea; opcional "Porque..." por ítem (estilo Sequoia).
 */
export const features = {
  sectionTitle: 'Cómo trabajamos',
  sectionDescription: 'Cuatro palancas del A al B. Una línea cada una.',
  solutionsLead: '',
  items: [
    {
      title: 'Asesoría & pipeline',
      because: '',
      description: 'B: Jenkinsfile en Git; webhooks; menos sorpresas en build.',
    },
    {
      title: 'Registry & deploy',
      because: '',
      description: 'B: imagen versionada; rollback con tag; infra acordada.',
    },
    {
      title: 'Demos',
      because: '',
      description: 'B: ver el hub antes de firmar largo plazo.',
    },
    {
      title: 'Arquitectura viva',
      because: '',
      description: 'B: diagrama compartible (auditoría) sin PowerPoint eterno.',
    },
  ],
} as const;

/** Demos — acceso por correo; sin exponer credenciales en la landing. */
export const demos = {
  sectionTitle: 'Demos',
  sectionDescription: 'A: promesas en PDF. B: ver Jenkins, registry y app con correo verificado.',
  cardCta: 'Abrir demo',
  cardLockedCta: 'Iniciar sesión',
  cardSoon: 'Próximamente',
  userCardTitle: 'Acceso a demos',
  userCardDescription:
    'Solo necesitas dejar tu correo. Si quieres, añade datos opcionales: con eso priorizamos el seguimiento.',
  userCardCta: 'Dejar mi correo',
  userCardCtaDisabled: 'Configurar URLs de demo',
  guestCredentialsTitle: 'Usuario invitado (solo app)',
  guestCredentialsDescription: 'Solo app de recursos y finanzas.',
  guestUserLabel: 'Usuario',
  guestPasswordLabel: 'Contraseña',
  copyUser: 'Copiar usuario',
  copyPassword: 'Copiar contraseña',
  passwordRequestContact: 'Solicitar en contacto',
  openJenkins: 'Abrir Jenkins',
  openGitea: 'Abrir Gitea',
  openPos: 'Abrir app',
  /** Texto en /demo tras el gate (sin bloque de usuario/contraseña). */
  demoPageCredentialsNote:
    'Las credenciales para Jenkins y Gitea las enviamos por correo. Revisa tu bandeja (y spam) tras solicitar acceso.',
} as const;

/** Gate /demo/access — estilo mínimo: correo obligatorio; resto opcional; políticas obligatorias. */
export const demoAccess = {
  eyebrow: '· demos',
  title: 'Solicitar acceso',
  lead:
    'Paso mínimo: correo → enlace. Opcional: contexto para afinar el B que buscas.',
  optionalHint: 'Opcional — nos ayuda a priorizar',
  emailLabel: 'Correo electrónico',
  emailPlaceholder: 'tu@empresa.com',
  nameLabel: 'Nombre',
  namePlaceholder: 'Nombre',
  companyLabel: 'Empresa',
  companyPlaceholder: 'Empresa u organización',
  messageLabel: '¿En qué podemos ayudarte?',
  messagePlaceholder: 'Proyecto, stack, plazo…',
  policyTermsLabel: 'He leído y acepto los términos y condiciones del acceso a demos.',
  policyTermsLink: 'Leer términos',
  policyDataLabel: 'He leído y acepto la política de datos personales.',
  policyDataLink: 'Leer política de datos',
  submitLabel: 'Enviar y continuar',
  submittingLabel: 'Enviando…',
  successMessage: 'Listo. Entrando a la demo…',
  errorMessage: 'No se pudo enviar. Intenta de nuevo o escríbenos por contacto.',
  recaptchaNote:
    'Este sitio puede estar protegido por reCAPTCHA; aplican la Política de privacidad y los Términos de Google.',
  privacyCommitment: 'Nos comprometemos con tu privacidad. Usamos tu correo para el acceso y el seguimiento que autorices.',
  allowlistDenied:
    'Este correo no tiene acceso a las demos en este entorno. Si necesitas acceso, escríbenos por contacto.',
} as const;

/** Página /login — estilo mínimo tipo Sequoia (correo → demo restringida o completa). */
export const loginPage = {
  title: 'Login',
  /** Con API + contraseña (portal enterprise). */
  titlePortal: 'Portal UnClic',
  subtitlePortal: 'Correo verificado y contraseña (JWT de sesión).',
  tabPos: 'Invitado',
  tabPosHint: 'Solo app',
  tabFull: 'Hub demo',
  tabFullHint: 'App + Jenkins + Gitea',
  emailLabel: 'Correo',
  emailPlaceholder: 'correo@empresa.com',
  passwordLabel: 'Contraseña',
  passwordPlaceholder: 'Tu contraseña',
  signIn: 'Iniciar sesión',
  noAccount: '¿Sin cuenta?',
  createAccount: 'Crear cuenta',
  portalHubLink: 'Ir al hub del portal',
  continue: 'Continuar',
  termsLine: 'Al continuar aceptas nuestros',
  termsLink: 'Términos y condiciones',
  firstTime: '¿Primera vez?',
  firstTimeLink: 'Crear cuenta',
  footerAbout: 'Por qué UnClic',
  footerContact: 'Contacto',
  footerPrivacy: 'Privacidad',
  footerTerms: 'Términos',
  scopePosNote:
    'Invitado: app recursos/finanzas. Hub Jenkins/Gitea: elige «Hub demo» al entrar o crea cuenta en /portal/registro.',
  allowlistDenied:
    'Este correo no está autorizado para demos en este sitio. Usa el correo que te compartimos o escríbenos.',
} as const;

/** Precios — claro y escalable; keyword para SEO. */
export const pricing = {
  sectionTitle: 'Precios',
  sectionDescription: 'A: incertidumbre de coste. B: suscripción o sprint con alcance en papel.',
} as const;

/** Galería — una línea; keyword para SEO. */
export const gallery = {
  sectionTitle: 'Galería',
  sectionDescription: 'A: promesa abstracta. B: equipos con el mismo hilo Git → deploy.',
  imageAlts: [
    'Pipeline en pantalla: código y CI',
    'Equipo alineado en integración',
    'Plan de despliegue concreto',
    'Repo + flujo de trabajo',
    'Equipo pequeño, mismo pipeline',
    'Jenkins, Gitea, registry en contexto',
    'DevOps y negocio en la misma mesa',
    'Deploy con rollback conocido',
  ] as const,
} as const;

/** Vídeo. */
export const video = {
  sectionTitle: 'Vídeo',
  sectionDescription:
    'A: leer el PDF. B: ver commit → build → deploy. (MP4 de ejemplo; sustituye por tu render.)',
  cardLabel: 'Vídeo de demostración',
  videoLabel: 'UnClic: commit, build, registry y deploy',
  fallbackText: 'Tu navegador no soporta la reproducción de vídeo.',
  /** Pie opcional bajo el reproductor (OSS). */
  ossMediaFootnote:
    'Ejemplo de archivo abierto (Big Buck Bunny). Producción propia: ffmpeg + tu hosting.',
} as const;

/** Audio. */
export const audio = {
  sectionTitle: 'Audio',
  sectionDescription:
    'A: solo texto. B: escuchar el “hilo” del pipeline (demo; sin API de pago en la demo).',
  tracksLabel: 'Pistas disponibles',
  controlsDescription: 'Reproducción simple: velocidad y volumen.',
  /** Pie del Orb: sin cuota ElevenLabs; medios reproducibles y stack OSS. */
  ossMediaCaption:
    'Sin ElevenLabs: audio local (p. ej. SoundHelix). STT/TTS: ver docs oficiales de Whisper, Piper, Coqui.',
  /** Detalle para pie o documentación. */
  ossMediaStackLine:
    'Pista: STT/TTS self-hosted ahorra coste por minuto; calidad depende del modelo y del micrófono.',
  trackTitles: [
    'Introducción a Pipeline as Code',
    'Pipeline as Code y beneficios',
    'Despliegue, registry y rollback',
    'Cierre y próximos pasos',
  ] as const,
} as const;

/** Panel STT/TTS/WebSocket — backend OSS (Whisper, Piper, etc.). */
export const ossVoice = {
  panelTitle: 'Voz OSS: STT, TTS y WebSocket',
  panelDescription:
    'A: SaaS por minuto. B: Whisper/Piper en tu red (variables en .env — ver docs de cada proyecto).',
  needEnvHint:
    'Activa las URLs en .env.local: NEXT_PUBLIC_OSS_STT_URL, NEXT_PUBLIC_OSS_TTS_URL (pueden ser el mismo origen) y opcional NEXT_PUBLIC_OSS_VOICE_WS_URL.',
  record: 'Grabar',
  stopAndTranscribe: 'Parar y transcribir',
  transcribing: 'Transcribiendo…',
  transcriptLabel: 'Transcripción',
  clearTranscript: 'Limpiar',
  ttsLabel: 'Texto a voz (TTS)',
  ttsPlaceholder: 'Texto para sintetizar…',
  synthesize: 'Sintetizar y reproducir',
  synthesizing: 'Generando audio…',
  ttsAudioLabel: 'Última salida TTS',
  wsTitle: 'Canal WebSocket',
  wsEnable: 'Conectar canal',
  wsDisable: 'Desconectar',
  wsPing: 'Ping',
  wsPushTranscript: 'Enviar texto al servidor',
  wsLog: 'Registro',
  missingStt: 'Sin STT: define NEXT_PUBLIC_OSS_STT_URL',
  missingTts: 'Sin TTS: define NEXT_PUBLIC_OSS_TTS_URL',
  missingWs: 'Sin WebSocket: define NEXT_PUBLIC_OSS_VOICE_WS_URL',
  errorMic: 'No se pudo acceder al micrófono.',
  errorRecorder: 'MediaRecorder no está soportado aquí.',
} as const;

/** LLM local con Ollama (texto). Imagen tipo Stable Diffusion: ver doc Ollama vs CompVis/diffusers. */
export const ollamaLocal = {
  panelTitle: 'Ollama — chat local (LLM)',
  panelDescription:
    'A: API cloud por token. B: modelo local (ollama pull) — datos en tu perímetro; revisa CORS en docs Ollama.',
  needEnvHint:
    'Define NEXT_PUBLIC_OLLAMA_URL (ej. http://127.0.0.1:11434) y opcional NEXT_PUBLIC_OLLAMA_MODEL. En el host Ollama configura OLLAMA_ORIGINS para http://localhost:3002.',
  modelLinePrefix: 'Modelo:',
  welcomeMessage:
    'Hola. Soy un asistente local vía Ollama. Pregunta por pipelines, integración o buenas prácticas — las respuestas dependen del modelo que tengas cargado.',
  systemPrompt:
    'Eres un asistente técnico breve y claro. Contexto: consultoría UnClic, integración, POS, Jenkins, Docker, CI/CD, open source. Responde en español salvo que pidan otro idioma.',
  inputPlaceholder: 'Escribe tu mensaje…',
  inputDisabledPlaceholder: 'Configura NEXT_PUBLIC_OLLAMA_URL para habilitar el chat.',
  thinking: 'Generando…',
  errorPrefix: 'No se pudo completar la petición:',
  footnoteSd:
    'Stable Diffusion «clásico» (CompVis, diffusers, checkpoints .ckpt) suele ir aparte (GPU, ComfyUI, etc.). Ollama también publica modelos de imagen en su biblioteca cuando aplique; no es el mismo binario que el repo CompVis/stable-diffusion.',
} as const;

/** Matrix display — animación tipo display retro (Pipeline as Code / CI/CD). */
export const matrixDisplay = {
  sectionTitle: 'Pipeline as Code',
  sectionDescription: 'A: pasos opacos. B: mismo orden cada vez.',
  /** Línea bajo el display (ej. "Commit → Build → Deploy"). */
  tagline: 'Commit · Build · Deploy',
} as const;

/** Orb. */
export const orb = {
  sectionTitle: 'Flujo 3D',
  sectionDescription: 'Metáfora visual: del commit al usuario.',
  loadingLabel: 'Cargando…',
} as const;

/** CTA final — "Ready to ship?" + opcional estilo Sequoia "Let's get more out of...". */
export const cta = {
  headlineAlt: '¿Siguiente paso hacia el B?',
  sectionTitle: 'Cierra la brecha con contexto',
  sectionDescription: 'Un solo registro: correo, verificación y contraseña. Demos y siguientes pasos dentro del portal.',
  onePartner: 'Mismo criterio en demo, repo y reunión.',
  ctaPrimary: 'Crear cuenta',
  ctaSecondary: 'Ya tengo cuenta',
  buttonLabel: 'Crear cuenta',
} as const;

/** Página /signup — copy para `SignupMarketingSection` (Card + Input + Button, shadcn/ui). */
export const signupPage = {
  title: 'Crear cuenta',
  signUpWithGoogle: 'Continuar con Google',
  or: 'o',
  emailPlaceholder: 'Tu correo',
  continue: 'Continuar',
  termsPrefix: 'Al continuar aceptas nuestros',
  termsAnd: ' y ',
  termsLink: 'Términos',
  privacyLink: 'Privacidad',
  alreadyUser: '¿Ya tienes cuenta?',
  logIn: 'Iniciar sesión',
  logoAlt: 'UnClic',
} as const;

/** Card de login (demo UI shadcn) — CTA y home. */
export const loginCard = {
  title: 'Iniciar sesión',
  description: 'A: visitante. B: correo verificado → demos y portal.',
  emailLabel: 'Correo',
  emailPlaceholder: 'correo@empresa.com',
  passwordLabel: 'Contraseña',
  passwordPlaceholder: 'Tu contraseña',
  register: 'Registrarse',
  submit: 'Entrar',
} as const;

/** Navegación — etiquetas al estilo Sequoia (Solutions / Capabilities / Insights). */
export const nav = {
  hubDemos: 'Centro de demos',
  features: 'Soluciones & plataforma',
  why: 'Por qué UnClic',
  whyShort: 'Por qué UnClic',
  stack: 'Stack técnico',
  ossAtlas: 'Atlas integraciones',
  flow: 'Flujo integración',
  flowDiagrams: 'Flujos demo y producción',
  architectureLive: 'Arquitectura en vivo',
  howItWorks: 'Cómo empezar',
  globe: 'Presencia global',
  demos: 'Demos detalladas',
  pricing: 'Precios',
  gallery: 'Galería',
  video: 'Vídeo',
  audio: 'Audio',
  clienteIdeal: 'Para quién es',
  budgetOss: 'Presupuesto OSS',
  contact: 'Cuenta',
  login: 'Iniciar sesión',
  portalDemos: 'Portal demos',
  openMenu: 'Abrir menú',
  sheetTitle: 'Navegación',
  closeMenu: 'Cerrar menú',
  skipToContent: 'Ir al contenido',
  home: 'UnClic — Ir al inicio',
  /** Texto corto para botón/enlace «volver al inicio» (demo/access, demo, login). */
  backToHome: 'Inicio',
  groupSolutions: 'Soluciones',
  groupCapabilities: 'Capacidades',
  /** Mismo rol que “Insights” en Sequoia. */
  groupResources: 'Insights',
  groupConnect: 'Conectar',
  company: 'Empresa',
  /** CTA principal del header (alineado a hero.ctaPrimary). */
  ctaPrimary: 'Crear cuenta',
  /** Compatibilidad footer antiguo */
  groupExplore: 'Soluciones',
  groupLearn: 'Capacidades',
} as const;

/** FAQ — título de sección (Shadcn Blocks: Faq). */
export const faq = {
  sectionTitle: 'Preguntas frecuentes',
  sectionDescription: 'A: dudas sueltas. B: respuestas cortas antes de escribirnos.',
} as const;

/**
 * Mega-menú — jerarquía tipo agencia (servicios / stack / explorar).
 * Cada ítem enlaza a página ancla o hub existente.
 */
export const navDropdowns = [
  {
    id: 'solutions',
    labelKey: 'groupSolutions' as const,
    items: [
      { key: 'budgetOss' as const, href: routes.presupuestoOss },
      { key: 'hubDemos' as const, href: routes.solucionesHub },
      { key: 'features' as const, href: routes.solucionesFeatures },
      { key: 'demos' as const, href: routes.solucionesDemos },
      { key: 'portalDemos' as const, href: routes.portal },
      { key: 'pricing' as const, href: routes.solucionesPricing },
    ],
  },
  {
    id: 'capabilities',
    labelKey: 'groupCapabilities' as const,
    items: [
      { key: 'ossAtlas' as const, href: routes.integracionesAtlas },
      { key: 'stack' as const, href: routes.capacidadesStack },
      { key: 'flow' as const, href: routes.capacidadesFlow },
      { key: 'flowDiagrams' as const, href: routes.capacidadesFlowDiagrams },
      { key: 'architectureLive' as const, href: routes.capacidadesArchitectureLive },
      { key: 'howItWorks' as const, href: routes.capacidadesHowItWorks },
      { key: 'why' as const, href: routes.capacidadesWhy },
    ],
  },
  {
    id: 'resources',
    labelKey: 'groupResources' as const,
    items: [
      { key: 'company' as const, href: routes.empresa },
      { key: 'gallery' as const, href: routes.insightsGallery },
      { key: 'video' as const, href: routes.insightsVideo },
      { key: 'audio' as const, href: routes.insightsAudio },
      { key: 'globe' as const, href: routes.insightsGlobe },
      { key: 'clienteIdeal' as const, href: routes.insightsClienteIdeal },
      { key: 'contact' as const, href: routes.publicSignup },
      { key: 'login' as const, href: routes.login },
    ],
  },
] as const;

/**
 * Footer al estilo Sequoia: SOLUTIONS (ofertas) ≠ CAPABILITIES (pilares técnicos).
 * Explora: https://www.sequoia.com/solutions/advisory/
 */
export const footerNav = {
  solutions: {
    title: 'Servicios',
    items: [
      { label: 'Presupuesto OSS (API)', href: routes.presupuestoWizard },
      { label: 'Hub soluciones & precios', href: routes.solucionesHub },
      { label: 'Plataforma & pipeline', href: routes.solucionesFeatures },
      { label: 'Demos detalladas', href: routes.solucionesDemos },
      { label: 'Portal (acceso)', href: routes.portal },
      { label: 'Cómo empezamos', href: routes.capacidadesHowItWorks },
    ] as const,
  },
  capabilities: {
    title: 'Stack OSS',
    items: [
      { label: 'Atlas integraciones', href: routes.integracionesAtlas },
      { label: 'Stack técnico', href: routes.capacidadesStack },
      { label: 'Flujo integración', href: routes.capacidadesFlow },
      { label: 'Diagramas demo / prod', href: routes.capacidadesFlowDiagrams },
      { label: 'Arquitectura en vivo', href: routes.capacidadesArchitectureLive },
      { label: 'Por qué UnClic', href: routes.capacidadesWhy },
    ] as const,
  },
  learn: {
    title: 'Explorar',
    items: [
      { label: 'Empresa', href: routes.empresa },
      { label: 'Galería', href: routes.insightsGallery },
      { label: 'Vídeo', href: routes.insightsVideo },
      { label: 'Audio', href: routes.insightsAudio },
      { label: 'Presencia global', href: routes.insightsGlobe },
      { label: 'Cliente ideal', href: routes.insightsClienteIdeal },
      { label: 'Iniciar sesión', href: routes.login },
    ] as const,
  },
  connect: {
    title: 'Conectar',
    items: [
      { label: 'Crear cuenta', href: routes.publicSignup },
      { label: 'Iniciar sesión', href: routes.login },
      { label: 'Portal', href: routes.portal },
      { label: 'Presupuesto OSS', href: routes.presupuestoWizard },
    ] as const,
  },
} as const;

/** @deprecated usar navDropdowns + footerNav */
export const navGroups = {
  explore: navDropdowns[0].items,
  learn: [...navDropdowns[1].items, ...navDropdowns[2].items],
  connect: [{ key: 'contact' as const, href: routes.contactForm }],
} as const;

/** Carrusel "Lo que suena" / Social trending 1 (tarjetas con vídeo + enlace). */
export const socialTrendingCarousel = {
  kicker: 'Destacado',
  sectionTitle: 'Recientes',
  sectionDescription: 'A: solo claims. B: clip + enlace al hub cuando aplique.',
  prevLabel: 'Anterior',
  nextLabel: 'Siguiente',
} as const;

/** Footer — "Optimized and crafted for Laravel, by Laravel" style. */
export const footer = {
  tagline: 'A→B con stack abierto: menos sorpresas en deploy, más evidencia en Git.',
  brandDescription: 'Jenkins · Gitea · Docker · AWS · FastFlow. Hablamos español; documentación alineada al repo.',
  builtWith: 'Pista de stack: Jenkins, Docker, K8s, Terraform.',
  linksTitle: 'Enlaces',
  productTitle: 'Producto',
  exploreTitle: 'Explorar',
  socialTitle: 'Redes',
  socialEnvHint:
    'LinkedIn y GitHub: define NEXT_PUBLIC_LINKEDIN_URL y NEXT_PUBLIC_GITHUB_URL en .env.local y vuelve a hacer build.',
  copyright: 'UnClic. Imágenes: Unsplash. Todos los derechos reservados.',
  /** Atribución Icons8 (iconos Liquid Glass en secciones de marketing). */
  icons8AttributionLabel: 'Iconos Liquid Glass:',
  icons8AttributionHref: 'https://icons8.com/liquid-glass',
  icons8AttributionVendor: 'Icons8',
} as const;

/** Cliente ideal — positivo y escaneable. */
export const clienteIdeal = {
  title: 'Encaja si',
  items: [
    'A: picos de ventas · B: deploy sin apagar tienda',
    'Retail / cadena / ecommerce multicanal',
    'Un URL para demos + doc (auditores, partners)',
    'Quieres alcance por escrito y sprints',
    'Git + pipeline; Workspace; SAT en roadmap',
  ] as const,
} as const;

/** Cómo funciona — 3 pasos A→B. */
export const howItWorks = {
  sectionTitle: 'Cómo empezar',
  sectionDescription: 'Tres pasos. Sin perder el foco.',
  steps: [
    { step: 1, title: 'Hablamos', description: 'A y B en una frase. Sin compromiso.' },
    { step: 2, title: 'Demo', description: 'Ver el B antes de firmar largo.' },
    { step: 3, title: 'Propuesta', description: 'Alcance · plazos · precio — por escrito.' },
  ] as const,
} as const;

/**
 * Resumen de secciones del sitio (orden y títulos para UI y navegación).
 * Cada sección usa la copia correspondiente en lib/copy.ts o lib/copy-pricing.ts.
 */
export const sectionIds = [
  'hero',
  'hub-demos',
  'stack',
  'why',
  'features',
  'flow',
  'flow-diagrams',
  'architecture-live',
  'how-it-works',
  'matrix-display',
  'globe',
  'demos',
  'pricing',
  'gallery',
  'video',
  'audio',
  'social-trending-carousel',
  'cliente-ideal',
  'faq',
  'contacto',
  'oss-atlas',
  'presupuesto-wizard',
  'presupuesto-api',
  'empresa-hero',
  'demo-access-form',
  'signup-form',
  'terminos',
  'datos',
] as const;

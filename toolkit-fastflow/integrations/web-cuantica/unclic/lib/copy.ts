/**
 * Copy UnClic — adaptado a tu caso (docs locales: PLAN-COPIA, COPY-PRODUCTOS-SERVICIOS-PRICING,
 * COPY-LANDING-ESTILO-CLERK, COPY-DEVOPS-AS-A-SERVICE-DAAS, COPY-FRAGMENTOS).
 * Sistema de copia orientado a SEO: ver unclic/docs/SISTEMA-COPIA-SEO-SITIO-WEB.md.
 */

/** SEO: meta, títulos y keywords para que quien busque nos encuentre. */
export const seo = {
  /** Título principal (home). Incluye keyword + marca. */
  title: 'UnClic — Pipeline as Code, app de recursos y finanzas y demos técnicas | Jenkins, Gitea, AWS',
  /** Meta description 150–160 caracteres: beneficio + keyword + CTA. */
  metaDescription:
    'Hub de demos: Jenkins, Gitea, app operativa, registry, Cloudcraft. Retail y alto volumen. Workspace, roadmap pagos y SAT.',
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
  ogTitle: 'UnClic — Pipeline as Code. Despliega sin gestionar servidores.',
} as const;

/** Banner superior — hub de demos + CV/LinkedIn (enlace único para portfolio). */
export const banner = {
  message: 'Un enlace: demos + stack (Jenkins, Gitea, app).',
  cta: 'Hub',
  dismissLabel: 'Cerrar',
} as const;

/** Hub central — primera parada: demos y tecnologías para enterprise. */
export const hubDemos = {
  kicker: 'Portfolio',
  sectionTitle: 'Demos y stack',
  sectionLead: 'Un solo sitio para compartir: Jenkins, Gitea, registry y app de recursos y finanzas.',
  sectionSub: 'Pipeline versionado · despliegue controlado.',
  ctaExternal: 'Abrir',
  ctaInternal: 'Ir',
  ctaRoadmapContact: 'Roadmap',
  ctaConfigureEnv: '.env',
  badgeRoadmap: 'Roadmap',
  footerNote: 'SAT / pagos en roadmap · Workspace típico en clientes.',
  /** Enlaces externos al stack (Jenkins, Gitea…) ocultos hasta login autorizado. */
  lockedExternalCta: 'Acceder',
  lockedExternalHint: 'Inicia sesión con un correo autorizado para abrir este enlace.',
} as const;

export const site = {
  name: 'UnClic',
  tagline: 'Pipeline as Code. Demos vivas para retail y alto volumen.',
  description:
    'Hub técnico: Jenkins, Gitea, app operativa, registry, Cloudcraft. Operación crítica.',
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
  heroEyebrow: 'PIPELINE · REGISTRY · DEPLOY',
  headline: 'Pipeline as Code para operación crítica',
  subtitle: 'Retail y alto volumen: Jenkins, Gitea y registry en un hub. Despliega sin parar la venta.',
  heroPillars: 'PIPELINE + REGISTRY + DEPLOY',
  trustLead: 'CI/CD con trazabilidad real.',
  ctaPrimary: 'Comenzar',
  ctaSecondary: 'Contactar',
  ctaDemo: 'Demo',
  noCreditCard: '',
  imageAlt: 'Flujo commit → deploy — UnClic',
  heroFeatures: [
    { title: 'Pipeline en Git', description: 'Jenkinsfile · multibranch' },
    { title: 'Registry OCI', description: 'Tags · rollback' },
    { title: 'Un flujo', description: 'Commit → deploy' },
    { title: 'Trazable', description: 'Por commit' },
  ] as ReadonlyArray<{ title: string; description: string }>,
} as const;

/** Trust strip — estilo Sequoia (clientes / países / NPS / años). Adaptado a demos públicas. */
export const trustStrip = {
  title: 'Un stack, un equipo',
  subtitle: 'Jenkins · Gitea · registry · diagramas vivos.',
  partnerTitle: 'Todo en un hub',
  metrics: [
    { label: 'Hub de demos', value: '1' },
    { label: 'Enlaces técnicos', value: '12+' },
    { label: 'Regiones AWS (demo)', value: '4' },
    { label: 'Pipeline en Git', value: '100%' },
  ] as ReadonlyArray<{ label: string; value: string }>,
} as const;

/** Carrusel de logos (Shadcn Blocks: Logos12) — stack o partners. image = URL (ej. Simple Icons) o path en public. */
export const logosCarousel = {
  sectionTitle: 'Stack',
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

/** Icon Cloud — stack / tecnologías (Magic UI). */
export const iconCloud = {
  sectionTitle: 'Stack',
  sectionDescription: 'Arrastra para explorar.',
} as const;

/** Sección globo 3D — México, SAT / factura digital, alcance internacional. */
export const globeSection = {
  title: 'Presencia global',
  description:
    'Con base en México conocemos a fondo las regulaciones del SAT y la factura digital (CFDI y entorno fiscal). No nos cerramos al país: conectamos equipos y proyectos en otras regiones cuando el reto lo pide.',
} as const;

/** Animated Beam — cadena fiel a la matriz de conexiones (sin aristas falsas). */
export const animatedBeam = {
  sectionTitle: 'Flujo de integración',
  sectionDescription: 'De tu repo al despliegue, en una cadena.',
  /** Texto bajo el diagrama (sección sin título). */
  tagline: 'Build, test, deploy. Un flujo versionado y trazable.',
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
  sectionDescription: '',
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
  sectionDescription: 'Código, deploy y arquitectura en un solo lugar.',
  problems: {
    title: 'Fricción habitual',
    items: [] as const,
  },
  benefitPillars: [
    {
      title: 'Decisiones claras',
      description: 'Mismo pipeline versionado para técnico y producto.',
    },
    {
      title: 'Menos retrabajo',
      description: 'Deploy predecible = menos horas perdidas.',
    },
    {
      title: 'Equipo alineado',
      description: 'Dev y ops comparten el mismo flujo.',
    },
    {
      title: 'Negocio cubierto',
      description: 'Rollback y trazabilidad cuando escala el tráfico.',
    },
  ] as ReadonlyArray<{ title: string; description: string }>,
  solutions: { title: '', items: [] as const },
} as const;

/**
 * Qué ofrecemos — beneficio + una línea; opcional "Porque..." por ítem (estilo Sequoia).
 */
export const features = {
  sectionTitle: 'Cómo trabajamos',
  sectionDescription: 'Cuatro entradas. Misma filosofía enterprise.',
  solutionsLead: '',
  items: [
    {
      title: 'Asesoría & pipeline',
      because: '',
      description: 'Jenkinsfile en Git · Jenkins · Gitea · webhooks.',
    },
    {
      title: 'Registry & deploy',
      because: '',
      description: 'Docker/OCI · EC2/Nginx · rollback por tag.',
    },
    {
      title: 'Demos',
      because: '',
      description: 'Hub público: Jenkins, registry, app operativa.',
    },
    {
      title: 'Arquitectura viva',
      because: '',
      description: 'Cloudcraft · multi-región · una fuente de verdad.',
    },
  ],
} as const;

/** Demos — acceso por correo; sin exponer credenciales en la landing. */
export const demos = {
  sectionTitle: 'Demos',
  sectionDescription: 'Solicita acceso con tu correo; te enviamos el enlace y lo que necesites para entrar.',
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
    'Déjanos tu correo: te compartimos el acceso para que entres enseguida. Lo demás es opcional; entre más contexto nos des, mejor podemos acompañarte.',
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
  tabPos: 'Invitado',
  tabPosHint: 'Solo app',
  tabFull: 'Hub demo',
  tabFullHint: 'App + Jenkins + Gitea',
  emailLabel: 'Correo',
  emailPlaceholder: 'correo@empresa.com',
  continue: 'Continuar',
  termsLine: 'Al continuar aceptas nuestros',
  termsLink: 'Términos y condiciones',
  firstTime: '¿Primera vez?',
  firstTimeLink: 'Solicitar acceso',
  footerAbout: 'Por qué UnClic',
  footerContact: 'Contacto',
  footerPrivacy: 'Privacidad',
  footerTerms: 'Términos',
  scopePosNote:
    'Estás en modo invitado: acceso a la app de recursos y finanzas. Jenkins y Gitea requieren el hub completo (elige «Hub demo» al iniciar sesión o solicita acceso).',
  allowlistDenied:
    'Este correo no está autorizado para demos en este sitio. Usa el correo que te compartimos o escríbenos.',
} as const;

/** Precios — claro y escalable; keyword para SEO. */
export const pricing = {
  sectionTitle: 'Precios',
  sectionDescription: 'Suscripción o por sprint. Descuentos por volumen.',
} as const;

/** Galería — una línea; keyword para SEO. */
export const gallery = {
  sectionTitle: 'Galería',
  sectionDescription: 'Entornos reales.',
  imageAlts: [
    'Entorno de trabajo con pipeline as code y CI/CD',
    'Equipo colaborando en integración y despliegue continuo',
    'Reunión de planificación de despliegues',
    'Oficina y flujo de desarrollo con pipelines versionados',
    'Startup con pipeline as code',
    'Workspace con Jenkins, Gitea y registro de imágenes',
    'Colaboración en equipo DevOps',
    'Desarrollo y despliegue automatizado con rollback seguro',
  ] as const,
} as const;

/** Vídeo. */
export const video = {
  sectionTitle: 'Vídeo',
  sectionDescription: 'Commit → deploy.',
  cardLabel: 'Vídeo de demostración',
  videoLabel: 'UnClic: commit, build, registry y deploy',
  fallbackText: 'Tu navegador no soporta la reproducción de vídeo.',
} as const;

/** Audio. */
export const audio = {
  sectionTitle: 'Audio',
  sectionDescription: 'Intro Pipeline as Code.',
  tracksLabel: 'Pistas disponibles',
  controlsDescription: 'Reproductor con progreso, velocidad y volumen.',
  /** Pie del reproductor: crédito ElevenLabs (muestras gratis). */
  elevenLabsCredit: 'Muestras de audio por ElevenLabs',
  trackTitles: [
    'Introducción a Pipeline as Code',
    'Pipeline as Code y beneficios',
    'Despliegue, registry y rollback',
    'Cierre y próximos pasos',
  ] as const,
} as const;

/** Matrix display — animación tipo display retro (Pipeline as Code / CI/CD). */
export const matrixDisplay = {
  sectionTitle: 'Pipeline as Code',
  sectionDescription: 'Build · test · deploy.',
  /** Línea bajo el display (ej. "Commit → Build → Deploy"). */
  tagline: 'Commit · Build · Deploy',
} as const;

/** Orb. */
export const orb = {
  sectionTitle: 'Flujo 3D',
  sectionDescription: 'Datos en movimiento.',
  loadingLabel: 'Cargando…',
} as const;

/** CTA final — "Ready to ship?" + opcional estilo Sequoia "Let's get more out of...". */
export const cta = {
  headlineAlt: 'Más valor en cada release',
  sectionTitle: 'Un partner, un pipeline',
  sectionDescription: 'Propuesta clara si encaja.',
  onePartner: 'Jenkins · Gitea · arquitectura visible.',
  ctaPrimary: 'Comenzar',
  ctaSecondary: 'Contactar',
  buttonLabel: 'Comenzar',
} as const;

/** Página /signup o bloque Signup (Shadcn Blocks: Signup10, etc.). */
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

/** Card de login (Border Beam) — estilo Magic UI demo; usado en CTA y home. */
export const loginCard = {
  title: 'Iniciar sesión',
  description: 'Accede a demos y recursos con tu correo.',
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
  contact: 'Contacto',
  login: 'Iniciar sesión',
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
  /** CTA principal del header (mismo que hero.ctaPrimary para consistencia). */
  ctaPrimary: 'Comenzar',
  /** Compatibilidad footer antiguo */
  groupExplore: 'Soluciones',
  groupLearn: 'Capacidades',
} as const;

/** FAQ — título de sección (Shadcn Blocks: Faq). */
export const faq = {
  sectionTitle: 'Preguntas frecuentes',
} as const;

/** Desplegables header — rutas tipo Sequoia (Solutions / Capabilities / Insights). */
export const navDropdowns = [
  {
    id: 'solutions',
    labelKey: 'groupSolutions' as const,
    items: [
      { key: 'hubDemos' as const, href: '/soluciones' },
      { key: 'features' as const, href: '/soluciones#features' },
      { key: 'demos' as const, href: '/soluciones#demos' },
      { key: 'pricing' as const, href: '/soluciones#pricing' },
    ],
  },
  {
    id: 'capabilities',
    labelKey: 'groupCapabilities' as const,
    items: [
      { key: 'stack' as const, href: '/capacidades#stack' },
      { key: 'flow' as const, href: '/capacidades#flow' },
      { key: 'flowDiagrams' as const, href: '/capacidades#flow-diagrams' },
      { key: 'architectureLive' as const, href: '/capacidades#architecture-live' },
      { key: 'howItWorks' as const, href: '/capacidades#how-it-works' },
    ],
  },
  {
    id: 'resources',
    labelKey: 'groupResources' as const,
    items: [
      { key: 'gallery' as const, href: '/insights#gallery' },
      { key: 'video' as const, href: '/insights#video' },
      { key: 'audio' as const, href: '/insights#audio' },
      { key: 'globe' as const, href: '/insights#globe' },
      { key: 'clienteIdeal' as const, href: '/insights#cliente-ideal' },
    ],
  },
] as const;

/**
 * Footer al estilo Sequoia: SOLUTIONS (ofertas) ≠ CAPABILITIES (pilares técnicos).
 * Explora: https://www.sequoia.com/solutions/advisory/
 */
export const footerNav = {
  solutions: {
    title: 'Soluciones',
    items: [
      { label: 'Asesoría & pipeline', href: '/soluciones#features' },
      { label: 'Retail & operación crítica', href: '/insights#cliente-ideal' },
      { label: 'Despliegue & acompañamiento', href: '/capacidades#how-it-works' },
      { label: 'Hub & plataforma documentada', href: '/soluciones' },
    ] as const,
  },
  capabilities: {
    title: 'Capacidades',
    items: [
      { label: 'Pipeline as Code', href: '/capacidades#why' },
      { label: 'Jenkins & Gitea', href: '/soluciones#demos' },
      { label: 'Docker & registry', href: '/soluciones' },
      { label: 'Infra AWS & flujos', href: '/capacidades#flow-diagrams' },
      { label: 'Arquitectura en vivo', href: '/capacidades#architecture-live' },
    ] as const,
  },
  learn: {
    title: 'Aprender',
    items: [
      { label: 'Galería', href: '/insights#gallery' },
      { label: 'Vídeo', href: '/insights#video' },
      { label: 'Audio', href: '/insights#audio' },
      { label: 'Presencia global', href: '/insights#globe' },
      { label: 'Para quién es', href: '/insights#cliente-ideal' },
    ] as const,
  },
  connect: {
    title: 'Conectar',
    items: [
      { label: 'Empresa', href: '/empresa' },
      { label: 'Contacto', href: '/contacto' },
      { label: 'Acceso demo', href: '/demo' },
    ] as const,
  },
} as const;

/** @deprecated usar navDropdowns + footerNav */
export const navGroups = {
  explore: navDropdowns[0].items,
  learn: [...navDropdowns[1].items, ...navDropdowns[2].items],
  connect: [{ key: 'contact' as const, href: '#contacto' }],
} as const;

/** Carrusel "Lo que suena" / Social trending 1 (tarjetas con vídeo + enlace). */
export const socialTrendingCarousel = {
  kicker: 'Destacado',
  sectionTitle: 'Recientes',
  prevLabel: 'Anterior',
  nextLabel: 'Siguiente',
} as const;

/** Footer — "Optimized and crafted for Laravel, by Laravel" style. */
export const footer = {
  tagline: 'Pipeline as Code · demos vivas · arquitectura clara.',
  brandDescription: 'Jenkins · Gitea · registry · FastFlow. Español.',
  builtWith: 'Jenkins, Docker, Kubernetes y Terraform.',
  linksTitle: 'Enlaces',
  productTitle: 'Producto',
  exploreTitle: 'Explorar',
  socialTitle: 'Redes',
  socialEnvHint:
    'LinkedIn y GitHub: define NEXT_PUBLIC_LINKEDIN_URL y NEXT_PUBLIC_GITHUB_URL en .env.local y vuelve a hacer build.',
  copyright: 'UnClic. Imágenes: Unsplash. Todos los derechos reservados.',
} as const;

/** Cliente ideal — positivo y escaneable. */
export const clienteIdeal = {
  title: 'Ideal si',
  items: [
    'Alto volumen de ventas · deploy sin parar operación',
    'Retail / cadena / ecommerce multicanal',
    'Un URL para demos + doc (auditores, partners)',
    'Alcance por escrito · sprints',
    'Git + pipeline; Workspace; SAT en roadmap',
  ] as const,
} as const;

/** Cómo funciona — "Get started under 60 seconds" style, 3 pasos; long-tail SEO. */
export const howItWorks = {
  sectionTitle: 'Cómo empezar',
  sectionDescription: 'Tres pasos.',
  steps: [
    { step: 1, title: 'Hablamos', description: 'Stack y objetivo. Sin compromiso.' },
    { step: 2, title: 'Demo', description: 'Jenkins + registry + app.' },
    { step: 3, title: 'Propuesta', description: 'Alcance · plazos · precio.' },
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
  'globe',
  'demos',
  'pricing',
  'gallery',
  'video',
  'audio',
  'cliente-ideal',
  'faq',
  'contacto',
] as const;

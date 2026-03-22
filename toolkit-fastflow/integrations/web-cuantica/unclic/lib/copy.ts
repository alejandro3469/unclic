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
  /** Placeholder del email en Hero154 (home con formulario). */
  leadEmailPlaceholder: 'Tu correo de trabajo',
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

/**
 * Ejemplo de flujo bloque + copy — ruta `/flow-demo`.
 * Ver docs/EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md (comando: npx shadcn add progress -y).
 */
export const flowDemo = {
  kicker: 'Ejemplo de flujo',
  title: 'Componente UI + copy en un solo lugar',
  lead: 'Pieza shadcn (Progress), textos aquí; la sección vive en components/sections/flow-demo-section.tsx.',
  progressLabel: 'Avance del pipeline (demo visual)',
  /** 0–100 para el componente Progress */
  progressValue: 66,
  badges: ['CLI shadcn', 'lib/copy.ts', 'components/sections'] as const,
  footnote: 'Pulí solo este objeto flowDemo para cambiar toda la página de ejemplo.',
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

/**
 * Atlas ilustrativo OSS + integraciones SaaS — ruta `/integraciones`.
 * Base visual para cartera amplia: cada herramienta enlaza a documentación oficial.
 * No ejecuta los servicios en el navegador; es mapa vivo para clientes y el plan técnico.
 */
export const ossAtlas = {
  path: '/integraciones',
  seoTitle: 'Atlas de integraciones open source y escenarios | UnClic',
  seoDescription:
    'Mapa ilustrativo: Gitea, Jenkins, ERPNext, colas, observabilidad, identidad y enlaces a docs oficiales. UnClic como base para distintos perfiles de cliente.',
  pageEyebrow: 'Cartera técnica',
  pageTitle: 'Atlas de integraciones',
  pageLead:
    'Un mismo sitio como lienzo: aquí ves cómo encajan herramientas open source (y algunas integraciones SaaS inevitables) según el escenario del cliente — sin sustituir la documentación oficial de cada proyecto.',
  pageSub:
    'Filtra por escenario. Cada tarjeta abre la doc del fabricante. En el repo: capas y ejecución (`docs/MAPA-STACK-OSS-…`, `docs/PLAN-OSS-…`), duplicados y elección UnClic (`docs/CURACION-DUPLICADOS-Y-ELECCION-UNClic.md`), cuatro planes de integración en servidor propio (`docs/PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md`).',
  filterAll: 'Todos los escenarios',
  filterLabel: 'Escenario',
  cardLayer: 'Capa',
  cardScenarios: 'Casos',
  docCta: 'Documentación oficial',
  kindOss: 'Open source',
  kindSaaS: 'Integración SaaS',
  teaserKicker: 'Base para muchos clientes',
  teaserTitle: 'Atlas de herramientas y escenarios',
  teaserLead:
    'Retail, fintech, manufactura o servicios: el mismo marco de capas — distintas piezas. Explora el mapa ilustrativo con enlaces a documentación oficial.',
  teaserCta: 'Abrir atlas completo',
  footnote:
    'Ilustrativo y educativo: la selección real por proyecto depende de restricciones, presupuesto y equipo. UnClic implementa por fases (ver plan OSS en el repo).',
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
    blurb:
      'LLMs locales (Ollama), experimentos (MLflow), orquestación (n8n) y grafos de agentes (LangGraph); según curación dev.to / daily.dev y docs oficiales.',
  },
  {
    id: 'devtools',
    title: 'DX / toolchain 2026',
    blurb:
      'Curaciones tipo “dominarán 2026” (dev.to): runtimes, lint, edge DB, front contenido, IDE y asistente código — alternativas a UnClic actual, no reemplazo automático.',
  },
  {
    id: 'operacion',
    title: 'Operación empresa OSS',
    blurb:
      'Caso Vates (XCP-ng, Xen Orchestra): virtualización, CRM, BI, PM, SSO y política de sourcing; divulgación Lawrence Systems + lista oficial Vates 2025.',
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
  sectionDescription:
    'Commit → deploy. Muestra de archivo MP4 pública; sustituye por tu render OSS o hosting propio.',
  cardLabel: 'Vídeo de demostración',
  videoLabel: 'UnClic: commit, build, registry y deploy',
  fallbackText: 'Tu navegador no soporta la reproducción de vídeo.',
  /** Pie opcional bajo el reproductor (OSS). */
  ossMediaFootnote:
    'Vídeo de ejemplo (open movie Big Buck Bunny). Generación propia: modelos abiertos + ffmpeg en tu infra.',
} as const;

/** Audio. */
export const audio = {
  sectionTitle: 'Audio',
  sectionDescription:
    'Intro Pipeline as Code. Interfaz tipo agente (Orb + ondas + reproductor), alineada con patrones tipo ElevenLabs UI OSS — motor de audio/TTS open source cuando conectes tu API.',
  tracksLabel: 'Pistas disponibles',
  controlsDescription: 'Reproductor con progreso, velocidad y volumen.',
  /** Pie del Orb: sin cuota ElevenLabs; medios reproducibles y stack OSS. */
  ossMediaCaption:
    'Sin API de ElevenLabs: demos reproducibles (SoundHelix / ficheros locales) y stack sugerido Whisper · Piper · Coqui · ffmpeg.',
  /** Detalle para pie o documentación. */
  ossMediaStackLine:
    'Para voz en tiempo real: faster-whisper o whisper.cpp (STT) + Piper o Coqui (TTS). Vídeo: pipeline self-hosted + modelos abiertos (p. ej. SVD/AnimateDiff) según tu GPU.',
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
    'Grabas en el navegador → tu servicio STT (p. ej. Whisper). Escribes texto → tu TTS (p. ej. Piper). WebSocket opcional para eventos en tiempo real.',
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
      { key: 'ossAtlas' as const, href: '/integraciones' },
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
      { label: 'Atlas integraciones OSS', href: '/integraciones' },
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

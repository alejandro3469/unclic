/**
 * Copia para la sección / página de Pricing.
 * Fuente: docs/copia/COPY-PRICING-PAGE.md
 */

export const pricingHero = {
  title: 'Precios',
  subtitle: 'A: “¿cuánto cuesta el B?”. B: modelo claro (suscripción o sprint).',
  currencyNote: 'USD.',
  note: 'Volumen: descuento acordado.',
  ctaDemo: 'Probar la demo',
  ctaContact: 'Contactar',
  ctaPlans: 'Ver planes',
} as const;

export const pricingModels = [
  {
    name: 'Suscripción',
    description: 'B: coste recurrente por pipeline/entorno — menos sorpresas que horas sueltas.',
  },
  {
    name: 'Pago por sprint',
    description: 'B: alcance por ciclo (p. ej. 2 semanas) con entrega revisable.',
  },
] as const;

export const pricingCategories = [
  {
    id: 'pipeline',
    title: 'Pipeline y CI/CD',
    products: [
      {
        name: 'Pipeline as Code',
        description: 'B: commit → build → test → deploy (Jenkinsfile en Git; webhooks).',
        price: 'Cotización según alcance',
      },
      {
        name: 'Registry de imágenes',
        description: 'B: imagen con tag = rollback (OCI; patrón Distribution).',
        price: 'Incluido con pipeline o por entorno',
      },
    ],
    startingAt: 'Un pipeline, un repo, un entorno — cotización según alcance.',
    features: [
      'Jenkinsfile en Git (multibranch)',
      'Webhook: build al push',
      'Maven, tests, artefacto',
      'Registry opcional: versionado',
    ],
  },
  {
    id: 'deploy',
    title: 'Despliegue e infraestructura',
    products: [
      {
        name: 'Despliegue',
        description: 'B: mismo EC2/Nginx o K8s cada vez (Terraform opcional).',
        price: 'Por entorno / por sprint',
      },
      {
        name: 'Infraestructura como código',
        description: 'B: red e instancias en repo (HashiCorp Terraform — doc oficial).',
        price: 'Según alcance',
      },
    ],
    startingAt: 'Por entorno o sprint; según alcance.',
    features: [
      'EC2 o K8s según acuerdo',
      'Repo como fuente de verdad',
      'Rollback si hay imagen versionada',
    ],
  },
  {
    id: 'demos-sites',
    title: 'Demos y sitios',
    products: [
      {
        name: 'Demos en vivo',
        description: 'B: ver Jenkins + registry + app antes de compromiso largo.',
        price: 'Gratuito',
      },
      {
        name: 'Sitios y landings',
        description: 'B: Next.js estático — mismo pipeline que el resto.',
        price: 'Por proyecto / por sprint',
      },
    ],
    startingAt: null,
    features: [
      'Demo: flujo real sin contrato largo',
      'Landings: alcance por escrito',
    ],
  },
  {
    id: 'consulting',
    title: 'Consultoría',
    products: [
      {
        name: 'Consultoría y diseño',
        description: 'A: dudas sin plan. B: auditoría + diseño de pipeline acotado.',
        price: 'Por proyecto / por sprint',
      },
    ],
    startingAt: null,
    features: [
      'Alcance por escrito',
      'Salida: doc + diseño de pipeline',
    ],
  },
] as const;

export const pricingCombined = {
  title: 'Combinación de planes',
  items: [
    'B típico: pipeline + registry + deploy → suscripción por entorno.',
    'Varios repos: Team — cotización según alcance.',
    'B por fases: sprint con entrega en cada ciclo.',
  ],
} as const;

export const pricingSupport = {
  title: 'Soporte y servicio',
  included: [
    'Doc en repo (pipeline + deploy)',
    'Alcance y revisiones por escrito',
    'Prueba en entorno acordado antes de cierre',
  ],
  notIncluded: [
    '24/7',
    'Revisiones ilimitadas (extras por anexo)',
    'Fuera de alcance firmado',
  ],
  supportPlans: 'Email/chat laboral; SLA a medida bajo consulta.',
} as const;

export const pricingFaq = [
  {
    q: '¿Facturación?',
    a: 'Por pipeline o entorno. Ciclo mensual/trimestral/anual (descuento).',
  },
  {
    q: '¿Pago por sprint?',
    a: 'Sí. Alcance por ciclo; extra por anexo.',
  },
  {
    q: '¿Varios productos a la vez?',
    a: 'No hace falta: un pipeline primero cierra el B mínimo.',
  },
  {
    q: '¿Alto volumen de ventas?',
    a: 'B: rollback por tag + trazabilidad en Git.',
  },
  {
    q: '¿Workspace / SAT?',
    a: 'Workspace habitual. SAT/pagos: roadmap según cliente.',
  },
] as const;

export const pricingPlanCards = [
  {
    id: 'starter',
    name: 'Starter',
    description: 'A: un repo sin CI. B: un pipeline + un entorno.',
    priceFrom: 'Desde 150 USD/mes',
    priceFromMxn: '2 775 MXN/mes',
    cta: 'Solicitar cotización',
  },
  {
    id: 'team',
    name: 'Team',
    description: 'A: varios repos. B: registry compartido + doc en repo.',
    priceFrom: 'Desde 350 USD/mes',
    priceFromMxn: '6 475 MXN/mes',
    cta: 'Solicitar cotización',
  },
  {
    id: 'demos',
    name: 'Demos',
    description: 'B: ver el hub antes de pagar: Jenkins, registry, app.',
    priceFrom: null,
    priceFromMxn: null,
    cta: 'Probar la demo',
  },
] as const;

/** Precios de referencia por sprint (2 semanas). Fuente: CALCULO-PRECIOS-Y-COMPETENCIA.md */
export const pricingPerSprint = [
  {
    name: 'Setup pipeline + un entorno',
    usd: '1 200 – 2 500 USD',
    mxn: '22 200 – 46 250 MXN',
  },
  {
    name: 'Mantenimiento / ampliación',
    usd: '600 – 1 200 USD',
    mxn: '11 100 – 22 200 MXN',
  },
  {
    name: 'Sitio o landing + deploy',
    usd: '800 – 1 800 USD',
    mxn: '14 800 – 33 300 MXN',
  },
] as const;

/** Argumentario: por qué nuestra oferta es mejor (escaneable). */
export const whyOurPricing = {
  title: 'Por qué nuestra oferta',
  points: [
    'A: proyecto genérico 15k–50k USD. B: plantillas FastFlow + tu repo — pagas adaptación, no inventario desde cero.',
    'A: horas sin techo. B: alcance por escrito; extra = anexo.',
    'A: contrato largo para “ver”. B: demo gratis + sprint cuando encaje.',
    'Stack estándar (Jenkins, Gitea, Docker): documentación pública de cada pieza.',
    'A: FTE DevOps 24/7. B: pipeline + doc + mantenimiento acotado.',
  ],
} as const;

export const pricingDisclaimer =
  'Cotización según alcance e infra. Entrega probada antes de cerrar.';

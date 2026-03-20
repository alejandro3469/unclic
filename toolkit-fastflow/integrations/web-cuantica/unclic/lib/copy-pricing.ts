/**
 * Copia para la sección / página de Pricing.
 * Fuente: docs/copia/COPY-PRICING-PAGE.md
 */

export const pricingHero = {
  title: 'Precios',
  subtitle: 'Flexible · transparente.',
  currencyNote: 'USD.',
  note: 'Descuentos por volumen.',
  ctaDemo: 'Probar la demo',
  ctaContact: 'Contactar',
  ctaPlans: 'Ver planes',
} as const;

export const pricingModels = [
  {
    name: 'Suscripción',
    description: 'Mensual o trimestral por pipeline o entorno. Previsibilidad y mantenimiento incluido.',
  },
  {
    name: 'Pago por sprint',
    description: 'Alcance por sprint (p. ej. 2 semanas). Entregas y feedback en cada ciclo; revisiones base incluidas.',
  },
] as const;

export const pricingCategories = [
  {
    id: 'pipeline',
    title: 'Pipeline y CI/CD',
    products: [
      {
        name: 'Pipeline as Code',
        description: 'Jenkinsfile, Jenkins, Gitea; build, test y deploy automático ante commit.',
        price: 'Cotización según alcance',
      },
      {
        name: 'Registry de imágenes',
        description: 'Docker/OCI versionado; deploy y rollback con tag conocido (estándar Distribution).',
        price: 'Incluido con pipeline o por entorno',
      },
    ],
    startingAt: 'Cotización según alcance (un pipeline, un repo, un entorno).',
    features: [
      'Jenkinsfile en Git, multibranch',
      'Ejecución automática ante commit (webhook)',
      'Build con Maven, tests y empaquetado',
      'Registry opcional para imágenes versionadas',
    ],
  },
  {
    id: 'deploy',
    title: 'Despliegue e infraestructura',
    products: [
      {
        name: 'Despliegue',
        description: 'EC2, Nginx; opcional Kubernetes y Terraform. Entorno acordado y repetible.',
        price: 'Por entorno / por sprint',
      },
      {
        name: 'Infraestructura como código',
        description: 'Terraform para redes, instancias y seguridad; documentación incluida.',
        price: 'Según alcance',
      },
    ],
    startingAt: 'Por entorno o por sprint; cotización según alcance.',
    features: [
      'Despliegue en servidor (EC2) o cluster (K8s)',
      'Configuración y documentación en repo',
      'Rollback con versión conocida cuando hay registry',
    ],
  },
  {
    id: 'demos-sites',
    title: 'Demos y sitios',
    products: [
      {
        name: 'Demos en vivo',
        description: 'Acceso a Jenkins, registry y app de ejemplo. Sin compromiso.',
        price: 'Gratuito',
      },
      {
        name: 'Sitios y landings',
        description: 'Sitios estáticos (Next.js) con deploy repetible por pipeline.',
        price: 'Por proyecto / por sprint',
      },
    ],
    startingAt: null,
    features: [
      'Flujo commit → build → registry → deploy sin compromiso',
      'Sitios con contenido y estructura acordados por escrito',
    ],
  },
  {
    id: 'consulting',
    title: 'Consultoría',
    products: [
      {
        name: 'Consultoría y diseño',
        description: 'Auditoría, diseño de pipeline y estrategia de despliegue.',
        price: 'Por proyecto / por sprint',
      },
    ],
    startingAt: null,
    features: [
      'Alcance pactado por escrito',
      'Entregables: documentación, diseño de pipeline, recomendaciones',
    ],
  },
] as const;

export const pricingCombined = {
  title: 'Combinación de planes',
  items: [
    'Pipeline + Registry + Despliegue: suscripción por entorno (mensual o trimestral).',
    'Varios repos o entornos: plan Team; cotización según alcance.',
    'Por fases: pago por sprint con entregas y feedback en cada ciclo.',
  ],
} as const;

export const pricingSupport = {
  title: 'Soporte y servicio',
  included: [
    'Documentación del pipeline y del despliegue en repo',
    'Alcance y revisiones por escrito (propuesta o anexo)',
    'Entrega probada en el entorno acordado antes del cierre',
  ],
  notIncluded: [
    'Soporte 24/7',
    'Revisiones ilimitadas (base incluida; adicionales por anexo)',
    'Alcance no especificado en propuesta o anexo firmado',
  ],
  supportPlans: 'Soporte por email/chat en horario laboral; acuerdos a medida bajo consulta.',
} as const;

export const pricingFaq = [
  {
    q: '¿Facturación?',
    a: 'Por pipeline o entorno. Mensual / trimestral / anual con descuento.',
  },
  {
    q: '¿Pago por sprint?',
    a: 'Sí. Alcance por ciclo; extra por anexo.',
  },
  {
    q: '¿Varios productos a la vez?',
    a: 'No hace falta. Empieza con un pipeline.',
  },
  {
    q: '¿Alto volumen de ventas?',
    a: 'Sí. Rollback por versión, trazabilidad, arquitectura documentada.',
  },
  {
    q: '¿Workspace / SAT?',
    a: 'Workspace típico en clientes. SAT y pagos en roadmap.',
  },
] as const;

export const pricingPlanCards = [
  {
    id: 'starter',
    name: 'Starter',
    description: 'Un pipeline, un repo, un entorno. Suscripción o por sprint.',
    priceFrom: 'Desde 150 USD/mes',
    priceFromMxn: '2 775 MXN/mes',
    cta: 'Solicitar cotización',
  },
  {
    id: 'team',
    name: 'Team',
    description: 'Múltiples repos o entornos, registry compartido, documentación.',
    priceFrom: 'Desde 350 USD/mes',
    priceFromMxn: '6 475 MXN/mes',
    cta: 'Solicitar cotización',
  },
  {
    id: 'demos',
    name: 'Demos',
    description: 'Acceso gratuito a Jenkins, registry y app de ejemplo.',
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
    'Precios por costes reales; muy por debajo de consultoría tradicional (15k–50k USD por proyecto).',
    'Pipeline, plantillas y documentación ya existen: pagas por aplicarlo a tu repo, no desde cero.',
    'Alcance y revisiones por escrito; lo extra se cotiza por anexo. Sin sorpresas.',
    'Suscripción o por sprint. Sin compromiso largo para empezar.',
    'Tu Jenkins, Gitea y registry a medida, en tu entorno o el nuestro.',
    'Menos coste que DevOps full-time: pipeline, documentación y mantenimiento acordado.',
  ],
} as const;

export const pricingDisclaimer =
  'Precios según expertise, infraestructura y entrega probada. Cotización vigente según alcance y variables del proyecto.';

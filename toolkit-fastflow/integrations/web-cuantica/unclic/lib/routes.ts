/**
 * Rutas canónicas (con hash alineado a `id` en el DOM cuando aplica — scroll con header sticky).
 * Preferir importar desde aquí en lugar de duplicar strings en copy y componentes.
 */
export const routes = {
  home: '/',
  soluciones: '/soluciones',
  capacidades: '/capacidades',
  integraciones: '/integraciones',
  presupuestoOss: '/presupuesto-oss',
  empresa: '/empresa',
  contacto: '/contacto',
  insights: '/insights',
  portal: '/portal',
  portalRegistro: '/portal/registro',
  /** Único registro público: correo + contraseña + verificación → portal/dashboard. */
  publicSignup: '/portal/registro',
  login: '/login',
  /** Login con retorno al hub del portal (sesión JWT). */
  loginNextPortal: '/login?next=/portal',
  demo: '/demo',
  /** Redirige a `publicSignup` (compatibilidad con enlaces antiguos). */
  demoAccess: '/demo/access',
  flowDemo: '/flow-demo',
  /** Página legal base (sin hash). */
  legalAccesoDemos: '/legal/acceso-demos',

  /** Bloque CTA + formulario (`CtaSection`, id="contacto"). */
  contactForm: '/contacto#contacto',

  solucionesHub: '/soluciones#hub-demos',
  solucionesFeatures: '/soluciones#features',
  solucionesDemos: '/soluciones#demos',
  solucionesPricing: '/soluciones#pricing',

  presupuestoWizard: '/presupuesto-oss#presupuesto-wizard',
  presupuestoApi: '/presupuesto-oss#presupuesto-api',

  capacidadesWhy: '/capacidades#why',
  capacidadesStack: '/capacidades#stack',
  capacidadesFlow: '/capacidades#flow',
  capacidadesFlowDiagrams: '/capacidades#flow-diagrams',
  capacidadesArchitectureLive: '/capacidades#architecture-live',
  capacidadesHowItWorks: '/capacidades#how-it-works',

  integracionesAtlas: '/integraciones#oss-atlas',
  empresaHero: '/empresa#empresa-hero',
  empresaWhy: '/empresa#why',

  insightsGallery: '/insights#gallery',
  insightsVideo: '/insights#video',
  insightsAudio: '/insights#audio',
  insightsGlobe: '/insights#globe',
  insightsClienteIdeal: '/insights#cliente-ideal',

  legalDemosTerminos: '/legal/acceso-demos#terminos',
  legalDemosDatos: '/legal/acceso-demos#datos',
} as const;

export type AppRouteHref = (typeof routes)[keyof typeof routes];

/** `/login?next=` con path codificado (p. ej. gate de demos). */
export function hrefLoginNext(path: string): string {
  return `${routes.login}?next=${encodeURIComponent(path)}`;
}

/**
 * URLs de las demos UnClic (Jenkins, Gitea, POS, Registry, K8s, Terraform).
 * El usuario solo visualiza pipelines e interfaces ya creadas; no crea nuevos.
 * Sobrescribir con variables de entorno en build si se usan otros dominios.
 */

export interface DemoLink {
  id: string;
  title: string;
  description: string;
  href: string;
  /** Lucide icon name for mapping in component */
  iconId: string;
  /** Slug en Simple Icons (cdn.simpleicons.org) para logo real gratuito; si no, se usa iconId con Lucide */
  simpleIconsSlug?: string;
}

const baseUrls = {
  jenkins: process.env.NEXT_PUBLIC_DEMO_JENKINS_URL ?? 'https://jenkins.unclic.consulting',
  gitea: process.env.NEXT_PUBLIC_DEMO_GITEA_URL ?? 'https://gitea.unclic.consulting',
  pos: process.env.NEXT_PUBLIC_DEMO_POS_URL ?? 'https://pos.unclic.consulting',
  registry: process.env.NEXT_PUBLIC_DEMO_REGISTRY_URL ?? 'https://registry.unclic.consulting',
  k8s: process.env.NEXT_PUBLIC_DEMO_K8S_URL ?? '',
  terraform: process.env.NEXT_PUBLIC_DEMO_TERRAFORM_URL ?? '',
  pulumi: process.env.NEXT_PUBLIC_DEMO_PULUMI_URL ?? '',
};

export const DEMO_LINKS: DemoLink[] = [
  {
    id: 'jenkins',
    title: 'Demo Jenkins',
    description: 'Ver pipelines ya creados, builds y logs. Solo visualización, no crear nuevos pipelines.',
    href: baseUrls.jenkins,
    iconId: 'workflow',
    simpleIconsSlug: 'jenkins',
  },
  {
    id: 'gitea',
    title: 'Demo Gitea',
    description: 'Repos existentes, commits y ramas. Acceso de solo lectura a la demo.',
    href: baseUrls.gitea,
    iconId: 'git',
    simpleIconsSlug: 'gitea',
  },
  {
    id: 'pos',
    title: 'App de recursos y finanzas',
    description: 'App operativa desplegada con FastFlow. Usar la aplicación, sin crear pipelines.',
    href: baseUrls.pos,
    iconId: 'shopping-cart',
  },
  {
    id: 'registry',
    title: 'Demo Registry',
    description:
      'Registry Docker/OCI: imágenes ya publicadas por el pipeline. Listar y ver tags; mismo estándar que registry oficial.',
    href: baseUrls.registry,
    iconId: 'package',
    simpleIconsSlug: 'docker',
  },
  {
    id: 'k8s',
    title: 'Demo Kubernetes',
    description: 'Dashboard o vistas de solo lectura de recursos ya desplegados.',
    href: baseUrls.k8s || '#',
    iconId: 'container',
    simpleIconsSlug: 'kubernetes',
  },
  {
    id: 'terraform',
    title: 'Demo Terraform',
    description: 'Estados y outputs ya generados. Solo consulta, no ejecutar apply.',
    href: baseUrls.terraform || '#',
    iconId: 'layers',
    simpleIconsSlug: 'terraform',
  },
  {
    id: 'pulumi',
    title: 'Pulumi (stack / CI)',
    description: 'Enlace a Pulumi Cloud u outputs de IaC multi-nube. Configura NEXT_PUBLIC_DEMO_PULUMI_URL.',
    href: baseUrls.pulumi || '#',
    iconId: 'layers',
    simpleIconsSlug: 'pulumi',
  },
];

/** Demos con URL configurada (para enlaces activos) */
export const DEMO_LINKS_ACTIVE = DEMO_LINKS.filter((d) => d.href && d.href !== '#');

/** Usuario invitado (solo POS) para Jenkins y Gitea. Opción A del doc: enlaces + credenciales. */
export const DEMO_GUEST_USER =
  process.env.NEXT_PUBLIC_DEMO_GUEST_USER ?? 'demo-invitado';

/** Contraseña del invitado; si no se define en build, no se muestra (solicitar en contacto). */
export const DEMO_GUEST_PASSWORD =
  process.env.NEXT_PUBLIC_DEMO_GUEST_PASSWORD ?? null;

export const DEMO_GUEST_CREDENTIALS = {
  user: DEMO_GUEST_USER,
  password: DEMO_GUEST_PASSWORD,
} as const;

/** Mensaje para la tarjeta de usuario abierto (credenciales demo) */
export const DEMO_USER_MESSAGE =
  'Credenciales de prueba para acceder a las demos en modo solo lectura. No se pueden crear nuevos pipelines ni modificar configuración.';

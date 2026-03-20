/**
 * Hub central: todas las demos y referencias del stack UnClic / FastFlow.
 * URLs desde env (NEXT_PUBLIC_*) con valores por defecto para demos públicas.
 */

import { CLOUDCRAFT_DEMO_VIEW_BASE } from '@/lib/copy';

export type HubBadge = 'roadmap' | 'interno';

export interface HubItem {
  id: string;
  title: string;
  description: string;
  href: string;
  external: boolean;
  iconId: string;
  simpleIconsSlug?: string;
  badge?: HubBadge;
}

export interface HubCategory {
  id: string;
  title: string;
  enterpriseNote?: string;
  items: HubItem[];
}

function ext(url: string, fallback = '#'): string {
  const u = url?.trim();
  return u && u !== '#' ? u : fallback;
}

export function getHubCategories(): HubCategory[] {
  const jenkins = process.env.NEXT_PUBLIC_DEMO_JENKINS_URL ?? 'https://jenkins.unclic.consulting';
  const gitea = process.env.NEXT_PUBLIC_DEMO_GITEA_URL ?? 'https://gitea.unclic.consulting';
  const pos = process.env.NEXT_PUBLIC_DEMO_POS_URL ?? 'https://pos.unclic.consulting';
  const registry = process.env.NEXT_PUBLIC_DEMO_REGISTRY_URL ?? 'https://registry.unclic.consulting';
  const k8s = process.env.NEXT_PUBLIC_DEMO_K8S_URL?.trim() ?? '';
  const terraform = process.env.NEXT_PUBLIC_DEMO_TERRAFORM_URL?.trim() ?? '';
  const pulumi = process.env.NEXT_PUBLIC_DEMO_PULUMI_URL?.trim() ?? '';
  const cloudcraft =
    process.env.NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL?.trim() || CLOUDCRAFT_DEMO_VIEW_BASE;
  const docsRepo = process.env.NEXT_PUBLIC_DOCS_REPO_URL?.trim() ?? '';
  const mailPortal =
    process.env.NEXT_PUBLIC_MAIL_PORTAL_URL?.trim() || 'https://workspace.google.com/products/gmail/';
  const datadogCloudcraft =
    process.env.NEXT_PUBLIC_DATADOG_CLOUDCRAFT_DOC_URL?.trim() ||
    'https://docs.datadoghq.com/datadog_cloudcraft/';

  return [
    {
      id: 'cicd',
      title: 'CI/CD y código',
      enterpriseNote: 'Pipeline versionado · rollback por tag.',
      items: [
        {
          id: 'jenkins',
          title: 'Jenkins',
          description: 'Pipelines y logs · demo invitado.',
          href: jenkins,
          external: true,
          iconId: 'workflow',
          simpleIconsSlug: 'jenkins',
        },
        {
          id: 'gitea',
          title: 'Gitea',
          description: 'Repos · ramas · Jenkinsfile.',
          href: gitea,
          external: true,
          iconId: 'git',
          simpleIconsSlug: 'gitea',
        },
        {
          id: 'registry',
          title: 'Registry Docker',
          description: 'OCI · tags · rollback.',
          href: registry,
          external: true,
          iconId: 'package',
          simpleIconsSlug: 'docker',
        },
      ],
    },
    {
      id: 'app',
      title: 'App y operación',
      enterpriseNote: 'Alto volumen · deploy controlado.',
      items: [
        {
          id: 'pos',
          title: 'App recursos y finanzas',
          description: 'Mismo pipeline que enterprise.',
          href: pos,
          external: true,
          iconId: 'shopping-cart',
        },
      ],
    },
    {
      id: 'infra',
      title: 'Infra',
      enterpriseNote: 'Multi-región · VPC documentada.',
      items: [
        {
          id: 'cloudcraft',
          title: 'Cloudcraft',
          description: 'AWS en vivo: Jenkins, Gitea, app.',
          href: cloudcraft,
          external: true,
          iconId: 'layers',
          simpleIconsSlug: 'amazonaws',
        },
        {
          id: 'architecture',
          title: 'Arquitectura (esta página)',
          description: 'Embed o enlace al diagrama.',
          href: '/#architecture-live',
          external: false,
          iconId: 'layers',
        },
        {
          id: 'k8s',
          title: 'Kubernetes',
          description: k8s ? 'Demo K8s.' : 'Configura NEXT_PUBLIC_DEMO_K8S_URL.',
          href: ext(k8s, '#'),
          external: true,
          iconId: 'container',
          simpleIconsSlug: 'kubernetes',
          badge: k8s ? undefined : 'roadmap',
        },
        {
          id: 'terraform',
          title: 'Terraform / IaC',
          description: terraform ? 'IaC / outputs.' : 'NEXT_PUBLIC_DEMO_TERRAFORM_URL.',
          href: ext(terraform, '#'),
          external: true,
          iconId: 'layers',
          simpleIconsSlug: 'terraform',
          badge: terraform ? undefined : 'roadmap',
        },
        {
          id: 'pulumi',
          title: 'Pulumi',
          description: pulumi ? 'Stack / historial de despliegues.' : 'NEXT_PUBLIC_DEMO_PULUMI_URL.',
          href: ext(pulumi, '#'),
          external: true,
          iconId: 'layers',
          simpleIconsSlug: 'pulumi',
          badge: pulumi ? undefined : 'roadmap',
        },
        {
          id: 'datadog-cloudcraft',
          title: 'Cloudcraft × Datadog',
          description: 'Docs in-app Datadog.',
          href: datadogCloudcraft,
          external: true,
          iconId: 'workflow',
          simpleIconsSlug: 'datadog',
        },
      ],
    },
    {
      id: 'comms',
      title: 'Comms',
      enterpriseNote: 'Workspace · audio · vídeo.',
      items: [
        {
          id: 'gmail-workspace',
          title: 'Workspace',
          description: 'Correo @dominio.',
          href: mailPortal,
          external: true,
          iconId: 'mail',
          simpleIconsSlug: 'google',
        },
        {
          id: 'audio',
          title: 'Audio',
          description: 'Pipeline as Code en voz.',
          href: '/#audio',
          external: false,
          iconId: 'audio',
        },
        {
          id: 'video',
          title: 'Vídeo',
          description: 'Recorrido commit → build → deploy.',
          href: '/#video',
          external: false,
          iconId: 'video',
        },
      ],
    },
    {
      id: 'roadmap',
      title: 'Roadmap',
      enterpriseNote: 'Pagos y fiscal en camino.',
      items: [
        {
          id: 'pagos',
          title: 'Pagos',
          description: 'En integración · alineado a la app.',
          href: '/contacto',
          external: false,
          iconId: 'card',
          badge: 'roadmap',
        },
        {
          id: 'sat-sello',
          title: 'SAT / CFDI',
          description: 'Planeado · sello digital.',
          href: '/contacto',
          external: false,
          iconId: 'file',
          badge: 'roadmap',
        },
        {
          id: 'docs-repo',
          title: 'Documentación técnica (repo)',
          description: docsRepo ? 'Toolkit FastFlow.' : 'NEXT_PUBLIC_DOCS_REPO_URL.',
          href: ext(docsRepo, '#'),
          external: true,
          iconId: 'git',
          simpleIconsSlug: 'github',
          badge: docsRepo ? undefined : 'roadmap',
        },
      ],
    },
  ];
}

/**
 * Slugs de Simple Icons para la nube de tecnologías (cdn.simpleicons.org).
 * Stack alineado con Pipeline as Code: Jenkins, Gitea, Docker, K8s, etc.
 */
const SLUGS = [
  'jenkins',
  'docker',
  'kubernetes',
  'git',
  'github',
  'gitea',
  'terraform',
  'nginx',
  'amazonaws',
  'nextdotjs',
  'java',
  'typescript',
  'postgresql',
  'mysql',
  'redis',
  'gradle',
  'apachemaven',
  'visualstudiocode',
  'gitlab',
  'linux',
] as const;

/** URLs de iconos (referencia CDN; la sección usa Badge con texto). */
export const iconCloudImageUrls = SLUGS.map(
  (slug) => `https://cdn.simpleicons.org/${slug}/64748b`
);

const LABELS: Record<(typeof SLUGS)[number], string> = {
  jenkins: 'Jenkins',
  docker: 'Docker',
  kubernetes: 'Kubernetes',
  git: 'Git',
  github: 'GitHub',
  gitea: 'Gitea',
  terraform: 'Terraform',
  nginx: 'NGINX',
  amazonaws: 'AWS',
  nextdotjs: 'Next.js',
  java: 'Java',
  typescript: 'TypeScript',
  postgresql: 'PostgreSQL',
  mysql: 'MySQL',
  redis: 'Redis',
  gradle: 'Gradle',
  apachemaven: 'Maven',
  visualstudiocode: 'VS Code',
  gitlab: 'GitLab',
  linux: 'Linux',
};

/** Para lista Badge en `/capacidades` (sin IconCloud). */
export const SLUG_LABELS = SLUGS.map((slug) => ({
  slug,
  label: LABELS[slug],
}));

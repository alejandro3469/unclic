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

/** URLs de iconos para IconCloud (Simple Icons, color slate). */
export const iconCloudImageUrls = SLUGS.map(
  (slug) => `https://cdn.simpleicons.org/${slug}/64748b`
);

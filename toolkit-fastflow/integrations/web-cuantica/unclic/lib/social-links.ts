/**
 * URLs públicas para CV, LinkedIn, GitHub y redes.
 * Configurar en .env.local (NEXT_PUBLIC_*) para build; no commitear secretos.
 */

function trimUrl(key: string): string | null {
  const v = process.env[key]?.trim();
  if (!v || !v.startsWith('http')) return null;
  return v;
}

export function getLinkedInUrl(): string | null {
  return trimUrl('NEXT_PUBLIC_LINKEDIN_URL');
}

export function getGitHubUrl(): string | null {
  return trimUrl('NEXT_PUBLIC_GITHUB_URL');
}

export function getDocsRepoUrl(): string | null {
  return trimUrl('NEXT_PUBLIC_DOCS_REPO_URL');
}

/**
 * URLs de logos gratuitos (Simple Icons CDN).
 * Uso: https://cdn.simpleicons.org/{slug} o con color opcional /{slug}/{hexColor}
 * Licencia: Simple Icons – iconos de marcas con permisos de uso.
 */

const SIMPLE_ICONS_CDN = 'https://cdn.simpleicons.org';

export function simpleIconUrl(slug: string, color?: string): string {
  if (color) return `${SIMPLE_ICONS_CDN}/${slug}/${color.replace(/^#/, '')}`;
  return `${SIMPLE_ICONS_CDN}/${slug}`;
}

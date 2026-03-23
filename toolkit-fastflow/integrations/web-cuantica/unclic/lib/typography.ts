/**
 * Typography — **Inter** + Space Mono + Ubuntu (logo).
 * Alineado a preset shadcn **Nova** (un stack sans para ritmo y jerarquía).
 * Ver docs/TYPOGRAPHY.md.
 */

/** Cuerpo, UI, nav, botones, formularios (default del sitio). */
export const FONT_SANS = 'font-sans' as const;

/**
 * Display / títulos: también **Inter** (misma familia que body, pesos distintos).
 * En Tailwind `font-serif` está mapeado a Inter para no romper clases existentes.
 */
export const FONT_SERIF = 'font-serif' as const;

/** Inter explícito (alias de sans). */
export const FONT_INTER = 'font-inter' as const;

/** Código, datos técnicos, acentos monoespaciados. */
export const FONT_SPACE_MONO = 'font-space-mono' as const;

export const typography = {
  body: FONT_SANS,
  heading: FONT_SERIF,
  hero: FONT_SERIF,
  lead: FONT_SERIF,
  quote: FONT_SERIF,
  ui: FONT_SANS,
  caption: FONT_SANS,
} as const;

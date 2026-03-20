/**
 * Typography system — Roboto + Space Mono (Google Fonts pairing).
 * See docs/TYPOGRAPHY.md for full rules.
 */

/** Use SANS (Roboto): body, UI, nav, buttons, forms, lists, captions. */
export const FONT_SANS = 'font-sans' as const;

/** Use SERIF / display (Space Mono): section headings (h1–h3), hero, lead, code, accents. */
export const FONT_SERIF = 'font-serif' as const;

/** Use when you explicitly want Roboto only (e.g. data, chips). */
export const FONT_ROBOTO = 'font-roboto' as const;

/** Use when you explicitly want Space Mono only (e.g. code, lead paragraph). */
export const FONT_SPACE_MONO = 'font-space-mono' as const;

/**
 * Suggested font class by usage.
 * Use these constants in components to keep typography consistent.
 */
export const typography = {
  /** Default for body, paragraphs, UI. */
  body: FONT_SANS,
  /** Section titles — also applied by default to h1, h2, h3 in globals.css. */
  heading: FONT_SERIF,
  /** Hero/main title. */
  hero: FONT_SERIF,
  /** Lead or eyebrow line above a title. */
  lead: FONT_SERIF,
  /** Blockquotes, testimonials. */
  quote: FONT_SERIF,
  /** Nav, buttons, labels, forms. */
  ui: FONT_SANS,
  /** Captions, metadata, footer. */
  caption: FONT_SANS,
} as const;

/**
 * Iconos raster **Liquid Glass** vía CDN de Icons8.
 * Estilo: https://icons8.com/liquid-glass — uso gratuito seleccionado con atribución (ver pie del sitio).
 *
 * Patrón comprobado: `https://img.icons8.com/liquid-glass/{px}/{slug}.png`
 */
export const ICONS8_LIQUID_GLASS_BASE = 'https://img.icons8.com/liquid-glass';

/** Tamaño PNG solicitado al CDN (mayor = más nítido en retina). */
export const ICONS8_LIQUID_GLASS_FETCH_PX = 128 as const;

export function icons8LiquidGlassUrl(slug: string, px: number = ICONS8_LIQUID_GLASS_FETCH_PX): string {
  const s = slug.replace(/^\//, '').replace(/\.png$/i, '');
  return `${ICONS8_LIQUID_GLASS_BASE}/${px}/${s}.png`;
}

/** Slugs usados en UnClic (semántica alineada al contenido). */
export const LG = {
  speedometer: 'speedometer',
  layers: 'layers',
  wallet: 'wallet',
  lightning: 'lightning-bolt',
  box: 'box',
  dashboard: 'dashboard',
  chat: 'chat-message',
  playCircled: 'play-button-circled',
  approval: 'approval',
  ok: 'ok',
  externalLink: 'external-link',
  sparkling: 'sparkling',
  arrow: 'arrow',
  forward: 'forward',
  clock: 'clock',
  paperPlane: 'paper-plane',
  sourceCode: 'source-code',
  shippingContainer: 'shipping-container',
  password: 'password',
  toolbox: 'toolbox',
  headphones: 'headphones',
  globe: 'globe',
  layersSmall: 'layers',
  checked: 'checked-checkbox',
} as const;

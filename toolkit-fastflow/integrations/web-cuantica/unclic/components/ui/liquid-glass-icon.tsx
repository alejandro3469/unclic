import Image from 'next/image';
import { cn } from '@/lib/utils';
import { ICONS8_LIQUID_GLASS_FETCH_PX, icons8LiquidGlassUrl } from '@/lib/icons8-liquid-glass';

type LiquidGlassIconProps = {
  /** Nombre de archivo sin `.png` (p. ej. `layers`, `speedometer`). */
  slug: string;
  /** Tamaño visual en CSS px. */
  size?: number;
  className?: string;
  alt: string;
};

/**
 * Icono **Icons8 Liquid Glass** (PNG desde CDN).
 * No sustituye iconos de control UI (chevron, close); usar en bloques de marketing / secciones.
 */
export function LiquidGlassIcon({ slug, size = 48, className, alt }: LiquidGlassIconProps) {
  const src = icons8LiquidGlassUrl(slug, ICONS8_LIQUID_GLASS_FETCH_PX);
  return (
    <Image
      src={src}
      alt={alt}
      width={size}
      height={size}
      unoptimized
      role={alt ? undefined : 'presentation'}
      className={cn('object-contain drop-shadow-[0_1px_3px_rgba(0,0,0,0.12)] dark:drop-shadow-[0_1px_4px_rgba(0,0,0,0.35)]', className)}
    />
  );
}

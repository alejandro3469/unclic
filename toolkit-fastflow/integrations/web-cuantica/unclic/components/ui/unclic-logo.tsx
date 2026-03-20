'use client';

import { cn } from '@/lib/utils';

type UnClicLogoProps = {
  className?: string;
  /** Tamaño base (altura aproximada en px). */
  size?: number;
  ariaHidden?: boolean;
  /** legacy: marca U + punto (SVG) por compatibilidad. */
  variant?: 'mark' | 'legacy';
};

/**
 * Logo UnClic: dos letras "U" y "C" en Ubuntu, con estilos del tema Nord.
 */
export function UnClicLogo({
  className,
  size = 32,
  ariaHidden = true,
  variant = 'mark',
}: UnClicLogoProps) {
  if (variant === 'legacy') {
    return (
      <svg
        width={size}
        height={size}
        viewBox="0 0 40 40"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
        className={cn('shrink-0', className)}
        aria-hidden={ariaHidden}
      >
        <path
          d="M10 10v14a8 8 0 0 0 16 0V10"
          stroke="currentColor"
          strokeWidth="2.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <circle cx="28" cy="28" r="4" fill="currentColor" />
      </svg>
    );
  }

  const fontSize = size;
  return (
    <span
      className={cn(
        'inline-flex items-baseline font-ubuntu font-bold tracking-tight text-primary',
        className
      )}
      style={{ fontSize }}
      aria-hidden={ariaHidden}
    >
      <span className="text-foreground">U</span>
      <span>C</span>
    </span>
  );
}

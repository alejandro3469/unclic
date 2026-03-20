import { type ReactNode } from 'react';
import { cn } from '@/lib/utils';

/**
 * Block container — Shadcn Blocks standard, enterprise width.
 * Use for Hero, Feature, CTA, Footer, Navbar content width.
 * max-w-content (72rem) with optional max-w-content-wide (80rem).
 */
export function BlockContainer({
  children,
  className,
  as: Component = 'div',
  wide = false,
}: {
  children: ReactNode;
  className?: string;
  as?: 'div' | 'nav' | 'header' | 'footer';
  /** Use wider content max (80rem) for hero/landing. */
  wide?: boolean;
}) {
  const Comp = Component;
  return (
    <Comp
      className={cn(
        'mx-auto w-full px-4 sm:px-6 lg:px-8',
        wide ? 'max-w-content-wide' : 'max-w-content',
        className
      )}
    >
      {children}
    </Comp>
  );
}

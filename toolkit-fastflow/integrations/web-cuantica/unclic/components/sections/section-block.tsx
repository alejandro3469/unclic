'use client';

import { type ReactNode } from 'react';
import { BlockSection } from '@/components/blocks';
import { cn } from '@/lib/utils';

/** Bandas de color tipo editorial (más aire visual, menos bloque gris). */
export const sectionTones = {
  default: '',
  wine: 'bg-gradient-to-b from-rose-100/50 via-background to-background dark:from-rose-950/30 dark:via-background',
  frost: 'bg-gradient-to-br from-sky-100/40 via-cyan-50/30 to-background dark:from-sky-950/25',
  warm: 'bg-gradient-to-b from-amber-100/40 to-orange-50/20 dark:from-amber-950/20',
  sage: 'bg-gradient-to-b from-emerald-100/35 to-teal-50/25 dark:from-emerald-950/20',
  violet: 'bg-gradient-to-b from-violet-100/40 to-purple-50/20 dark:from-violet-950/20',
} as const;

export type SectionTone = keyof typeof sectionTones;

/**
 * Section wrapper aligned to Shadcn Blocks structure.
 * Uses BlockSection (container + left-aligned header) so all SectionBlock usages get block-style layout.
 */
export function SectionBlock({
  id,
  title,
  description,
  children,
  className,
  tone = 'default',
}: {
  id?: string;
  title: string;
  description?: string;
  children: ReactNode;
  className?: string;
  tone?: SectionTone;
}) {
  return (
    <BlockSection
      id={id}
      title={title}
      description={description}
      headerLeft
      className={cn(sectionTones[tone], className)}
    >
      {children}
    </BlockSection>
  );
}

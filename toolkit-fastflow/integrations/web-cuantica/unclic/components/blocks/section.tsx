'use client';

import { type ReactNode } from 'react';
import { cn } from '@/lib/utils';
import { BlockContainer } from './container';
import { BlockSectionHeader, BlockSectionHeaderLeft } from './section-header';

/**
 * Block section wrapper — Shadcn Blocks standard.
 * section + padding (py-16 md:py-24) + container + optional header + children.
 */
export function BlockSection({
  id,
  children,
  className,
  containerClassName,
  /** Centered block-style header (eyebrow + title + description). */
  eyebrow,
  title,
  description,
  titleId,
  /** If true, header is left-aligned (Feature, About blocks often use this). */
  headerLeft = false,
  /** Extra content after header, inside container (e.g. lead paragraph). */
  headerExtra,
}: {
  id?: string;
  children: ReactNode;
  className?: string;
  containerClassName?: string;
  eyebrow?: string;
  title?: ReactNode;
  description?: ReactNode;
  titleId?: string;
  headerLeft?: boolean;
  headerExtra?: ReactNode;
}) {
  const headingId = titleId ?? (id ? `${id}-heading` : undefined);
  return (
    <section
      id={id}
      className={cn('section-padding', className)}
      aria-labelledby={headingId ?? undefined}
    >
      <BlockContainer className={containerClassName}>
        {(title ?? eyebrow ?? description) ? (
          <div className="space-y-10">
            {headerLeft ? (
              <BlockSectionHeaderLeft
                eyebrow={eyebrow}
                title={title ?? ''}
                description={description}
                titleId={headingId}
              />
            ) : (
              <BlockSectionHeader
                eyebrow={eyebrow}
                title={title ?? ''}
                description={description}
                titleId={headingId}
              />
            )}
            {headerExtra}
            {children}
          </div>
        ) : (
          children
        )}
      </BlockContainer>
    </section>
  );
}

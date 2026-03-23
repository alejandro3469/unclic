import { type ReactNode } from 'react';
import { cn } from '@/lib/utils';

/**
 * Block section header — Shadcn Blocks pattern.
 * Eyebrow (optional) + H2 + description. Used in Feature, Stats, FAQ, CTA blocks.
 */
export function BlockSectionHeader({
  eyebrow,
  title,
  description,
  titleId,
  className,
  titleAs = 'h2',
}: {
  eyebrow?: string;
  title: ReactNode;
  description?: ReactNode;
  titleId?: string;
  className?: string;
  titleAs?: 'h1' | 'h2' | 'h3';
}) {
  const Title = titleAs;
  return (
    <div className={cn('space-y-4 text-center', className)}>
      {eyebrow ? (
        <p className="text-type-eyebrow-accent tracking-[0.2em]">{eyebrow}</p>
      ) : null}
      <Title id={titleId} className="text-type-section-title font-bold">
        {title}
      </Title>
      {description ? (
        <p className="text-type-lead mx-auto max-w-2xl text-center">{description}</p>
      ) : null}
    </div>
  );
}

/** Left-aligned variant for sections that don't use centered block headers. */
export function BlockSectionHeaderLeft({
  eyebrow,
  title,
  description,
  titleId,
  className,
  titleAs = 'h2',
}: {
  eyebrow?: string;
  title: ReactNode;
  description?: ReactNode;
  titleId?: string;
  className?: string;
  titleAs?: 'h1' | 'h2' | 'h3';
}) {
  const Title = titleAs;
  return (
    <div className={cn('space-y-2', className)}>
      {eyebrow ? <p className="text-type-eyebrow tracking-wider">{eyebrow}</p> : null}
      <Title id={titleId} className="text-type-section-title">
        {title}
      </Title>
      {description ? <p className="text-type-lead max-w-3xl">{description}</p> : null}
    </div>
  );
}

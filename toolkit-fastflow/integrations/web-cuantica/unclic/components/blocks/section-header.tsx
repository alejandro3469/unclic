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
        <p className="text-xs font-semibold uppercase tracking-[0.2em] text-primary">
          {eyebrow}
        </p>
      ) : null}
      <Title
        id={titleId}
        className="text-display-sm font-bold tracking-tight text-foreground sm:text-display-md md:text-display-lg"
      >
        {title}
      </Title>
      {description ? (
        <p className="mx-auto max-w-2xl text-body-lg text-muted-foreground">
          {description}
        </p>
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
      {eyebrow ? (
        <p className="text-xs font-semibold uppercase tracking-wider text-muted-foreground">
          {eyebrow}
        </p>
      ) : null}
      <Title
        id={titleId}
        className="text-display-sm font-semibold tracking-tight text-foreground sm:text-display-md md:text-display-lg"
      >
        {title}
      </Title>
      {description ? (
        <p className="max-w-3xl text-body-lg text-muted-foreground">
          {description}
        </p>
      ) : null}
    </div>
  );
}

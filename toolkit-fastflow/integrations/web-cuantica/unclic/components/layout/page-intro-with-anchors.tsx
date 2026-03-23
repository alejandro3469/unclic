import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { cn } from '@/lib/utils';

export type PageIntroAnchor = { readonly label: string; readonly href: string };
export type PageIntroVariant = 'full' | 'subnav';

type PageIntroWithAnchorsProps = {
  variant?: PageIntroVariant;
  eyebrow?: string;
  title?: string;
  lead?: string;
  navLabel: string;
  links: readonly PageIntroAnchor[];
  className?: string;
};

export function PageIntroWithAnchors({
  variant = 'full',
  eyebrow,
  title,
  lead,
  navLabel,
  links,
  className,
}: PageIntroWithAnchorsProps) {
  const isSubnav = variant === 'subnav';

  return (
    <section
      className={cn(
        isSubnav
          ? 'border-b border-border bg-muted/25 pt-[5.25rem] pb-4 md:pt-[5.5rem] md:pb-5'
          : 'border-b border-border bg-gradient-to-b from-muted/30 to-background pb-10 pt-20 md:pb-12 md:pt-24',
        className
      )}
      {...(isSubnav ? { 'aria-label': navLabel } : { 'aria-labelledby': 'page-intro-heading' })}
    >
      <BlockContainer>
        {eyebrow ? (
          <p
            className={cn(
              'text-type-eyebrow tracking-[0.2em]',
              isSubnav ? 'mb-3' : ''
            )}
          >
            {eyebrow}
          </p>
        ) : null}
        {!isSubnav && title ? (
          <h1
            id="page-intro-heading"
            className="text-type-page-title mt-3 max-w-4xl font-light tracking-tight text-foreground"
          >
            {title}
          </h1>
        ) : null}
        {!isSubnav && lead ? (
          <p className="text-type-lead mt-5 max-w-2xl">{lead}</p>
        ) : null}
        <nav className={cn(!isSubnav && (eyebrow || title) ? 'mt-10' : 'mt-0')} aria-label={navLabel}>
          <p className="sr-only">{navLabel}</p>
          <ul className="flex flex-wrap gap-2">
            {links.map((item) => (
              <li key={`${item.href}-${item.label}`}>
                <Link
                  href={item.href}
                  className={cn(
                    'inline-flex rounded-full border border-border bg-card px-4 py-2.5 text-sm font-medium text-foreground shadow-sm transition',
                    'hover:border-primary/35 hover:bg-primary/5 hover:text-foreground',
                    'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2'
                  )}
                >
                  {item.label}
                </Link>
              </li>
            ))}
          </ul>
        </nav>
      </BlockContainer>
    </section>
  );
}

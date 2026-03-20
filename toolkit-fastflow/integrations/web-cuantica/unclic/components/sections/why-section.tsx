'use client';

import { why } from '@/lib/copy';
import { BlockSection } from '@/components/blocks';
import { cn } from '@/lib/utils';

const PILLAR_TINT = [
  'border-l-4 border-l-rose-400 bg-rose-50/60 dark:bg-rose-950/20 dark:border-l-rose-500',
  'border-l-4 border-l-sky-400 bg-sky-50/60 dark:bg-sky-950/20 dark:border-l-sky-500',
  'border-l-4 border-l-amber-400 bg-amber-50/50 dark:bg-amber-950/20 dark:border-l-amber-500',
  'border-l-4 border-l-emerald-400 bg-emerald-50/50 dark:bg-emerald-950/20 dark:border-l-emerald-500',
] as const;

/** Por qué — poco texto, tarjetas con color (estilo Sequoia: titular + una línea). */
export function WhySection() {
  return (
    <BlockSection
      id="why"
      title={why.sectionTitle}
      description={why.sectionDescription}
      headerLeft
      className="bg-gradient-to-b from-violet-50/40 to-background dark:from-violet-950/15"
    >
      <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-4" aria-label="Beneficios clave">
        {why.benefitPillars.map(({ title, description }, i) => (
          <div
            key={title}
            className={cn(
              'rounded-xl border border-border/60 p-5 shadow-sm transition-shadow hover:shadow-md',
              PILLAR_TINT[i % PILLAR_TINT.length]
            )}
          >
            <h3 className="text-base font-semibold tracking-tight text-foreground">{title}</h3>
            <p className="mt-2 text-sm leading-snug text-muted-foreground">{description}</p>
          </div>
        ))}
      </div>
    </BlockSection>
  );
}

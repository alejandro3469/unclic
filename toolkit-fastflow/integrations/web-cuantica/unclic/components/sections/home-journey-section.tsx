import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { homeJourney } from '@/lib/copy';
import { LG } from '@/lib/icons8-liquid-glass';
import { cn } from '@/lib/utils';

/** Fases Validate / Build / Scale — numeración grande, enlaces claros. */
export function HomeJourneySection() {
  return (
    <section className={cn('section-padding bg-background')} aria-labelledby="home-journey-title">
      <BlockContainer>
          <p className="text-type-eyebrow tracking-[0.2em]">{homeJourney.eyebrow}</p>
          <h2 id="home-journey-title" className="text-type-section-title mt-3 max-w-3xl font-bold">
            {homeJourney.title}
          </h2>
          <p className="text-type-lead mt-4 max-w-2xl">{homeJourney.lead}</p>

          <ol className="mt-14 grid gap-10 md:grid-cols-3 md:gap-8">
            {homeJourney.phases.map((phase) => (
              <li key={phase.step} className="relative flex flex-col">
                <span
                  className="font-mono text-4xl font-light tabular-nums text-gradient-impact md:text-5xl"
                  aria-hidden
                >
                  {phase.step}
                </span>
                <h3 className="text-type-card-title mt-4 font-bold">{phase.title}</h3>
                <p className="mt-3 flex-1 text-base leading-relaxed text-muted-foreground">{phase.description}</p>
                <Link
                  href={phase.href}
                  className="mt-6 inline-flex items-center gap-2 text-sm font-semibold text-primary hover:underline"
                >
                  {phase.linkLabel}
                  <LiquidGlassIcon slug={LG.externalLink} size={16} alt="" />
                </Link>
              </li>
            ))}
          </ol>
      </BlockContainer>
    </section>
  );
}

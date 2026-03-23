import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { homePillars } from '@/lib/copy';
import { LG } from '@/lib/icons8-liquid-glass';
import { cn } from '@/lib/utils';

const pillarSlugs = [LG.speedometer, LG.layers, LG.wallet] as const;

/** Tres pilares tipo agencia — problema → oferta UnClic (Phenomenon-style layout). */
export function HomePillarsSection() {
  return (
    <section
      className={cn('section-padding border-t border-border bg-muted/20')}
      aria-labelledby="home-pillars-title"
    >
      <BlockContainer>
          <p className="text-type-eyebrow tracking-[0.2em]">{homePillars.eyebrow}</p>
          <h2 id="home-pillars-title" className="text-type-section-title mt-3 max-w-3xl font-bold">
            {homePillars.title}
          </h2>
          <p className="text-type-lead mt-4 max-w-2xl">{homePillars.lead}</p>

          <div className="mt-14 grid gap-8 md:grid-cols-3 md:gap-6 lg:gap-10">
            {homePillars.items.map((item, i) => {
              const slug = pillarSlugs[i] ?? LG.layers;
              return (
                <article
                  key={item.title}
                  className="group flex flex-col rounded-card border border-border bg-card p-8 shadow-sm transition-shadow hover:shadow-md"
                >
                  <div className="mb-6 flex size-14 items-center justify-center rounded-xl bg-primary/10">
                    <LiquidGlassIcon slug={slug} size={40} alt="" />
                  </div>
                  <h3 className="text-type-card-title font-bold">{item.title}</h3>
                  <p className="mt-3 flex-1 text-base leading-relaxed text-muted-foreground">{item.body}</p>
                  <Link
                    href={item.href}
                    className="mt-6 inline-flex items-center gap-2 text-sm font-semibold text-primary transition-colors group-hover:text-foreground"
                  >
                    {item.cta}
                    <LiquidGlassIcon
                      slug={LG.externalLink}
                      size={16}
                      alt=""
                      className="transition-transform group-hover:translate-x-0.5 group-hover:-translate-y-0.5"
                    />
                  </Link>
                </article>
              );
            })}
          </div>
      </BlockContainer>
    </section>
  );
}

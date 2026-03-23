'use client';

import { BlockContainer } from '@/components/blocks';
import { howItWorks } from '@/lib/copy';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { LG } from '@/lib/icons8-liquid-glass';

const STEP_SLUGS = [LG.chat, LG.playCircled, LG.approval] as const;

export function HowItWorksSection() {
  return (
    <section
      id="how-it-works"
      className="bg-gradient-to-r from-emerald-50/70 via-teal-50/40 to-cyan-50/50 py-16 dark:from-emerald-950/20 md:py-24"
      aria-labelledby="how-it-works-heading"
    >
      <BlockContainer>
        <h2 id="how-it-works-heading" className="text-type-section-title">
          {howItWorks.sectionTitle}
        </h2>
        <p className="text-type-lead mt-2 max-w-2xl">
          {howItWorks.sectionDescription}
        </p>

        <div className="mt-10 grid gap-6 sm:grid-cols-3">
          {howItWorks.steps.map(({ step, title, description }) => {
            const slug = STEP_SLUGS[step - 1] ?? LG.ok;
            return (
              <Card key={step} className="flex flex-col">
                <CardHeader className="pb-2">
                  <div className="flex items-center gap-3">
                    <span
                      className="flex size-10 shrink-0 items-center justify-center rounded-full bg-primary/10 text-lg font-semibold text-primary"
                      aria-hidden
                    >
                      {step}
                    </span>
                    <LiquidGlassIcon slug={slug} size={32} alt="" className="shrink-0 opacity-90" />
                    <h3 className="text-type-card-title">{title}</h3>
                  </div>
                </CardHeader>
                <CardContent>
                  <p className="text-sm text-muted-foreground">{description}</p>
                </CardContent>
              </Card>
            );
          })}
        </div>
      </BlockContainer>
    </section>
  );
}

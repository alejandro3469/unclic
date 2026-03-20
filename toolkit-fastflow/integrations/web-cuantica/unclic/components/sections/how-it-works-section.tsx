'use client';

import { BlockContainer } from '@/components/blocks';
import { howItWorks } from '@/lib/copy';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { MessageCircle, PlayCircle, FileCheck } from 'lucide-react';

const STEP_ICONS = [MessageCircle, PlayCircle, FileCheck] as const;

export function HowItWorksSection() {
  return (
    <section
      id="how-it-works"
      className="bg-gradient-to-r from-emerald-50/70 via-teal-50/40 to-cyan-50/50 py-16 dark:from-emerald-950/20 md:py-24"
      aria-labelledby="how-it-works-heading"
    >
      <BlockContainer>
        <h2
          id="how-it-works-heading"
          className="text-2xl font-semibold tracking-tight sm:text-3xl md:text-4xl"
        >
          {howItWorks.sectionTitle}
        </h2>
        <p className="mt-2 max-w-2xl text-muted-foreground">
          {howItWorks.sectionDescription}
        </p>

        <div className="mt-10 grid gap-6 sm:grid-cols-3">
          {howItWorks.steps.map(({ step, title, description }) => {
            const Icon = STEP_ICONS[step - 1];
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
                    <Icon className="size-6 text-muted-foreground" aria-hidden />
                    <h3 className="text-lg font-semibold tracking-tight">{title}</h3>
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

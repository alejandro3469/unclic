'use client';

import { features } from '@/lib/copy';
import { SectionBlock } from '@/components/sections/section-block';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { Layers, Zap, Box, LayoutDashboard } from 'lucide-react';

const ICONS = [Layers, Zap, Box, LayoutDashboard] as const;

const CARD_TONE = [
  'bg-gradient-to-br from-rose-50/90 to-background dark:from-rose-950/25',
  'bg-gradient-to-br from-cyan-50/80 to-background dark:from-cyan-950/20',
  'bg-gradient-to-br from-amber-50/80 to-background dark:from-amber-950/20',
  'bg-gradient-to-br from-violet-50/80 to-background dark:from-violet-950/20',
] as const;

export function FeaturesSection() {
  return (
    <SectionBlock
      id="features"
      title={features.sectionTitle}
      description={features.sectionDescription}
      tone="warm"
    >
      <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-4">
        {features.items.map(({ title, description }, index) => {
          const Icon = ICONS[index] ?? Box;
          return (
            <Card
              key={title}
              className={`overflow-hidden border-border/50 shadow-sm ${CARD_TONE[index % CARD_TONE.length]}`}
            >
              <CardHeader className="pb-2">
                <Icon className="size-9 text-primary" aria-hidden />
                <h3 className="text-lg font-semibold">{title}</h3>
              </CardHeader>
              <CardContent>
                <p className="text-sm leading-snug text-muted-foreground">{description}</p>
              </CardContent>
            </Card>
          );
        })}
      </div>
    </SectionBlock>
  );
}

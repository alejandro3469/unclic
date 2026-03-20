'use client';

import React from 'react';
import { BlockSection } from '@/components/blocks';
import { animatedBeam as copy } from '@/lib/copy';
import { IntegrationFaithfulFlowDiagram } from '@/components/sections/flow-diagram-visuals';

export function AnimatedBeamSection() {
  return (
    <BlockSection id="flow" className="bg-transparent py-12 md:py-16 lg:py-20">
      <div className="mx-auto flex max-w-3xl flex-col items-center text-center">
        <IntegrationFaithfulFlowDiagram scale="large" className="w-full" />
        <p className="mt-10 max-w-xl text-balance text-base leading-relaxed text-muted-foreground md:text-lg">
          {copy.tagline}
        </p>
        <p className="mt-4 font-mono text-xs font-semibold tracking-[0.35em] text-foreground/80 md:text-sm md:tracking-[0.4em]">
          {copy.pipelineSteps}
        </p>
      </div>
    </BlockSection>
  );
}

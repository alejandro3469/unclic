'use client';

import React from 'react';
import { SectionBlock } from '@/components/sections/section-block';
import { flowDiagrams as copy } from '@/lib/copy';
import {
  IntegrationFaithfulFlowDiagram,
  DemoFlowFaithfulDiagram,
  RealFlowFaithfulDiagram,
  TopologyFlowFaithfulDiagram,
} from '@/components/sections/flow-diagram-visuals';

function DiagramFigure({
  caption,
  children,
}: {
  caption: string;
  children: React.ReactNode;
}) {
  return (
    <figure className="m-0">
      <figcaption className="mb-4 text-center text-[10px] font-semibold uppercase tracking-[0.22em] text-muted-foreground/90">
        {caption}
      </figcaption>
      {children}
    </figure>
  );
}

export function FlowDiagramsSection() {
  return (
    <SectionBlock id="flow-diagrams" title={copy.sectionTitle} tone="frost">
      <div className="mt-6 space-y-14 md:space-y-16">
        <DiagramFigure caption={copy.integration.title}>
          <IntegrationFaithfulFlowDiagram />
        </DiagramFigure>
        <DiagramFigure caption={copy.demo.title}>
          <DemoFlowFaithfulDiagram />
        </DiagramFigure>
        <DiagramFigure caption={copy.real.title}>
          <RealFlowFaithfulDiagram />
        </DiagramFigure>
        <DiagramFigure caption={copy.topology.title}>
          <TopologyFlowFaithfulDiagram />
        </DiagramFigure>
      </div>
    </SectionBlock>
  );
}

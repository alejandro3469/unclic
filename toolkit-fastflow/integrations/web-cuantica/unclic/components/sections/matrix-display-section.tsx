'use client';

import { BlockContainer } from '@/components/blocks';
import { Matrix, wave } from '@/components/ui/matrix';
import { matrixDisplay } from '@/lib/copy';
import { cn } from '@/lib/utils';

/** Tamaño de cada celda del display (px). */
const CELL_SIZE = 12;
const CELL_GAP = 3;

export function MatrixDisplaySection() {
  return (
    <section
      id="matrix-display"
      className="bg-gradient-to-r from-primary/5 via-transparent to-cyan-500/10 py-12 md:py-16"
      aria-labelledby="matrix-display-heading"
    >
      <BlockContainer>
        <h2
          id="matrix-display-heading"
          className="sr-only"
        >
          {matrixDisplay.sectionTitle}
        </h2>
        <p className="text-center text-sm text-muted-foreground mb-2">
          {matrixDisplay.sectionDescription}
        </p>

        <div
          className={cn(
            'flex flex-col items-center justify-center gap-6',
            'rounded-xl border border-border bg-muted/30 px-6 py-8',
            'min-h-[180px]'
          )}
        >
          <div
            className="flex items-center justify-center text-primary"
            aria-hidden
          >
            <Matrix
              rows={7}
              cols={7}
              frames={wave}
              fps={10}
              autoplay
              loop
              size={CELL_SIZE}
              gap={CELL_GAP}
              palette={{
                on: 'hsl(var(--primary))',
                off: 'hsl(var(--muted-foreground) / 0.35)',
              }}
              brightness={1}
            />
          </div>
          <p
            className="font-mono text-xs uppercase tracking-widest text-muted-foreground"
            aria-hidden
          >
            {matrixDisplay.tagline}
          </p>
        </div>
      </BlockContainer>
    </section>
  );
}

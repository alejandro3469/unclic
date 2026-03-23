'use client';

import { BlockContainer } from '@/components/blocks';
import { matrixDisplay } from '@/lib/copy';
import { cn } from '@/lib/utils';

const COLS = 7;
/** Patrón estático tipo “onda” (sin animación por frame). */
const WAVE_PATTERN = [
  [0, 0, 1, 1, 1, 0, 0],
  [0, 1, 1, 1, 1, 1, 0],
  [1, 1, 1, 1, 1, 1, 1],
  [1, 1, 1, 1, 1, 1, 1],
  [1, 1, 1, 1, 1, 1, 1],
  [0, 1, 1, 1, 1, 1, 0],
  [0, 0, 1, 1, 1, 0, 0],
] as const satisfies readonly (readonly (0 | 1)[])[];

const CELL = 12;
const GAP = 3;

export function MatrixDisplaySection() {
  return (
    <section
      id="matrix-display"
      className="bg-gradient-to-r from-primary/5 via-transparent to-cyan-500/10 py-12 md:py-16"
      aria-labelledby="matrix-display-heading"
    >
      <BlockContainer>
        <h2 id="matrix-display-heading" className="sr-only">
          {matrixDisplay.sectionTitle}
        </h2>
        <p className="mb-2 text-center text-sm text-muted-foreground">
          {matrixDisplay.sectionDescription}
        </p>

        <div
          className={cn(
            'flex min-h-[180px] flex-col items-center justify-center gap-6 rounded-xl border border-border bg-muted/30 px-6 py-8'
          )}
        >
          <div
            className="grid w-fit"
            style={{
              gridTemplateColumns: `repeat(${COLS}, ${CELL}px)`,
              gap: GAP,
            }}
            aria-hidden
          >
            {WAVE_PATTERN.flatMap((row, r) =>
              row.map((cell, c) => {
                const on = Boolean(cell);
                return (
                  <div
                    key={`${r}-${c}`}
                    className={cn(
                      'rounded-sm',
                      on
                        ? 'bg-primary shadow-[0_0_6px_hsl(var(--primary)/0.45)]'
                        : 'bg-muted-foreground/25'
                    )}
                    style={{ width: CELL, height: CELL }}
                  />
                );
              })
            )}
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

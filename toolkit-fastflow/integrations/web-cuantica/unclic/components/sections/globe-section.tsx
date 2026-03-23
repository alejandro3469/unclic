'use client';

import Image from 'next/image';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { LG } from '@/lib/icons8-liquid-glass';
import { BlockContainer } from '@/components/blocks';
import { Card, CardContent } from '@/components/ui/card';
import { globeSection as copy } from '@/lib/copy';

const GLOBE_IMAGE =
  'https://unpkg.com/three-globe@2.31.0/example/img/earth-blue-marble.jpg';

/** Presencia global — imagen estática + Card (sin WebGL / globe.gl). */
export function GlobeSection() {
  return (
    <section
      id="globe"
      className="relative overflow-hidden py-16 md:py-24"
      aria-labelledby="globe-heading"
    >
      <div
        className="pointer-events-none absolute inset-0 bg-[radial-gradient(ellipse_80%_60%_at_50%_45%,rgba(56,189,248,0.12),transparent_55%),radial-gradient(ellipse_50%_40%_at_50%_100%,rgba(99,102,241,0.08),transparent_50%)] dark:bg-[radial-gradient(ellipse_80%_60%_at_50%_45%,rgba(56,189,248,0.18),transparent_55%)]"
        aria-hidden
      />
      <BlockContainer className="relative flex flex-col items-center text-center">
        <div className="mb-3 flex justify-center">
          <span className="inline-flex items-center justify-center rounded-2xl border border-sky-500/25 bg-sky-500/10 p-3 text-sky-600 shadow-inner shadow-sky-500/10 dark:border-sky-400/20 dark:bg-sky-950/40 dark:text-sky-400">
            <LiquidGlassIcon slug={LG.globe} size={44} alt="" className="md:h-11 md:w-11" />
          </span>
        </div>
        <h2 id="globe-heading" className="text-type-section-title text-balance">
          {copy.title}
        </h2>
        <p className="text-type-body-sm mx-auto mt-4 max-w-2xl text-pretty md:text-base">
          {copy.description}
        </p>

        <Card className="mt-12 w-full max-w-[min(92vw,580px)] overflow-hidden rounded-full border-sky-400/20 p-0 shadow-lg ring-1 ring-white/10">
          <CardContent className="relative aspect-square p-0">
            <Image
              src={GLOBE_IMAGE}
              alt=""
              fill
              className="object-cover"
              sizes="(max-width: 768px) 92vw, 580px"
              priority={false}
            />
          </CardContent>
        </Card>
      </BlockContainer>
    </section>
  );
}

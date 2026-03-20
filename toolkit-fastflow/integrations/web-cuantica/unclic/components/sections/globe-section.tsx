'use client';

import { useEffect, useRef } from 'react';
import { Globe } from 'lucide-react';
import { BlockContainer } from '@/components/blocks';
import { globeSection as copy } from '@/lib/copy';
import { mexicoHubGlobeArcs, type GlobeArc } from '@/lib/globe-arcs-data';

function asArc(d: object): GlobeArc {
  return d as GlobeArc;
}

/** Textura día — más legible y “bonita” que earth-night. */
const GLOBE_IMAGE =
  'https://unpkg.com/three-globe@2.31.0/example/img/earth-blue-marble.jpg';

export function GlobeSection() {
  const containerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!containerRef.current) return;

    let globeInstance: { controls?: () => { autoRotate?: boolean; autoRotateSpeed?: number } } | null =
      null;

    import('globe.gl').then(({ default: GlobeGL }) => {
      if (!containerRef.current) return;

      const globe = new GlobeGL(containerRef.current);

      globe
        .backgroundColor('rgba(0, 0, 0, 0)')
        .globeImageUrl(GLOBE_IMAGE)
        .bumpImageUrl('https://unpkg.com/three-globe@2.31.0/example/img/earth-topology.png')
        .showAtmosphere(true)
        .atmosphereColor('rgba(56, 189, 248, 0.45)')
        .atmosphereAltitude(0.22)
        .arcsData(mexicoHubGlobeArcs)
        .arcLabel((d: object) => asArc(d).name ?? '')
        .arcStartLat((d: object) => asArc(d).startLat)
        .arcStartLng((d: object) => asArc(d).startLng)
        .arcEndLat((d: object) => asArc(d).endLat)
        .arcEndLng((d: object) => asArc(d).endLng)
        .arcColor((d: object) => {
          const c = asArc(d).color;
          if (typeof c === 'string') return c;
          if (Array.isArray(c)) return c;
          return '#38bdf8';
        })
        .arcStroke(0.55)
        .arcAltitudeAutoScale(0.38)
        .arcCurveResolution(48)
        .arcsTransitionDuration(1400);

      globe.pointOfView({ lat: 22, lng: -100, altitude: 1.55 }, 0);

      const ctrl = globe.controls?.();
      if (ctrl) {
        ctrl.autoRotate = true;
        ctrl.autoRotateSpeed = 0.35;
      }

      globeInstance = globe;
    });

    return () => {
      globeInstance = null;
    };
  }, []);

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
            <Globe className="size-9 md:size-10" strokeWidth={1.35} aria-hidden />
          </span>
        </div>
        <h2
          id="globe-heading"
          className="text-balance text-2xl font-semibold tracking-tight text-foreground sm:text-3xl md:text-4xl"
        >
          {copy.title}
        </h2>
        <p className="mx-auto mt-4 max-w-2xl text-pretty text-sm leading-relaxed text-muted-foreground md:text-base">
          {copy.description}
        </p>

        <div className="relative mt-12 flex w-full justify-center px-2">
          <div
            className="relative aspect-square w-full max-w-[min(92vw,580px)] overflow-hidden rounded-full border border-sky-400/20 bg-gradient-to-b from-sky-950/30 via-indigo-950/20 to-slate-950/40 shadow-[0_0_0_1px_rgba(255,255,255,0.06),0_25px_80px_-20px_rgba(56,189,248,0.35),0_40px_100px_-30px_rgba(99,102,241,0.2)] ring-1 ring-white/10 dark:border-sky-500/15 dark:from-sky-950/50 dark:via-indigo-950/30 dark:to-background"
            style={{ minHeight: 280 }}
          >
            <div ref={containerRef} className="absolute inset-0 h-full w-full [&_canvas]:!block" />
          </div>
        </div>
      </BlockContainer>
    </section>
  );
}

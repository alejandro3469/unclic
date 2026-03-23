'use client';

import Image from 'next/image';
import Link from 'next/link';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { LG } from '@/lib/icons8-liquid-glass';
import { BlockContainer } from '@/components/blocks';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { hero } from '@/lib/copy';
import { routes } from '@/lib/routes';
import { HERO_IMAGE_PLACEHOLDER } from '@/lib/placeholders';

/**
 * Hero marketing dos columnas (imagen + copy) — solo primitivas shadcn/ui + BlockContainer.
 * Doc: https://ui.shadcn.com/docs/components
 */
export function Hero2Section({ id }: { id?: string } = {}) {
  const badge = hero.heroEyebrow || hero.heroPillars;
  const heading = hero.headlineAccent
    ? `${hero.headline} ${hero.headlineAccent}`
    : hero.headline;

  return (
    <section id={id} className="py-24 md:py-32">
      <BlockContainer>
        <div className="grid items-center gap-8 lg:grid-cols-2 lg:gap-12">
          <div className="relative aspect-video w-full overflow-hidden rounded-lg border border-border bg-muted">
            <Image
              src={HERO_IMAGE_PLACEHOLDER}
              alt={hero.imageAlt}
              fill
              className="object-cover"
              sizes="(max-width: 1024px) 100vw, 50vw"
              priority
            />
          </div>
          <div className="flex flex-col items-center gap-5 text-center lg:items-start lg:text-left">
            {badge ? (
              <Badge variant="outline" className="gap-1.5 pr-2">
                {badge}
                <LiquidGlassIcon slug={LG.externalLink} size={14} alt="" className="opacity-80" />
              </Badge>
            ) : null}
            <h1 className="text-type-hero text-balance font-bold tracking-tight">{heading}</h1>
            <p className="text-type-lead max-w-xl">{hero.subtitle}</p>
            <div className="flex w-full flex-col justify-center gap-3 sm:flex-row lg:justify-start lg:items-center">
              <Button asChild className="w-full sm:w-auto">
                <Link href={routes.publicSignup}>{hero.ctaPrimary}</Link>
              </Button>
              <Link
                href={routes.solucionesHub}
                className="text-center text-sm font-medium text-muted-foreground underline-offset-4 hover:text-foreground hover:underline sm:text-left"
              >
                {hero.ctaProduct}
              </Link>
            </div>
          </div>
        </div>
      </BlockContainer>
    </section>
  );
}

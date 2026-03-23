'use client';

import Link from 'next/link';
import Image from 'next/image';
import AutoScroll from 'embla-carousel-auto-scroll';
import Autoplay from 'embla-carousel-autoplay';
import { Button } from '@/components/ui/button';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import {
  Carousel,
  CarouselContent,
  CarouselItem,
} from '@/components/ui/carousel';
import { UnClicLogo } from '@/components/ui/unclic-logo';
import { BlockContainer } from '@/components/blocks';
import { hero, trustStrip } from '@/lib/copy';
import { routes } from '@/lib/routes';
import { GALLERY_PLACEHOLDERS } from '@/lib/placeholders';
import { cn } from '@/lib/utils';
import { LG } from '@/lib/icons8-liquid-glass';

/** Iconos Liquid Glass alineados a los 3 primeros `hero.heroFeatures` (pipeline / imagen / acceso). */
const HERO_FEATURE_SLUGS = [LG.sourceCode, LG.shippingContainer, LG.password] as const;

/** Hero block (Shadcn Blocks: Hero70) — logo, features, headline, carousel, CTAs. Copy from lib/copy.ts. */
export function Hero() {
  const carouselImages = GALLERY_PLACEHOLDERS.slice(0, 8);
  const featureItems = hero.heroFeatures.slice(0, 3);

  return (
    <section
      className={cn('relative bg-background py-14')}
      aria-labelledby="hero-headline"
    >
      <BlockContainer className="relative z-10">
        <div className="py-8">
          <Link href={routes.home} className="inline-block" aria-label="UnClic — inicio">
            <UnClicLogo size={48} ariaHidden />
          </Link>
        </div>
        <div className="flex flex-col gap-10 py-10 lg:py-28">
          <div className="hidden items-center gap-6 lg:flex">
            {featureItems.map((item, i) => (
              <div
                key={item.title}
                className="flex items-center gap-2 text-foreground"
              >
                <LiquidGlassIcon
                  slug={HERO_FEATURE_SLUGS[i] ?? LG.ok}
                  size={28}
                  alt=""
                  className="shrink-0"
                />
                <span>{item.title}</span>
              </div>
            ))}
          </div>
          <div className="flex">
            <div className="flex flex-1 flex-col gap-4">
              {hero.heroEyebrow ? (
                <p className="text-type-eyebrow tracking-[0.2em]">{hero.heroEyebrow}</p>
              ) : null}
              <h1
                id="hero-headline"
                className="text-type-hero max-w-6xl font-light tracking-tighter text-foreground"
              >
                <span className="block">{hero.headline}</span>
                {hero.headlineAccent ? (
                  <span className="mt-2 block text-gradient-impact">{hero.headlineAccent}</span>
                ) : null}
              </h1>
              <p className="text-type-lead max-w-3xl">{hero.subtitle}</p>
            </div>
          </div>
          <div className="flex flex-col gap-4 sm:flex-row sm:flex-wrap sm:items-center sm:gap-6">
            <Button
              asChild
              size="lg"
              className="btn-impact h-auto rounded-full px-8 py-4 text-base font-semibold"
            >
              <Link href={routes.publicSignup} className="gap-2">
                {hero.ctaPrimary}
                <LiquidGlassIcon slug={LG.externalLink} size={22} alt="" className="shrink-0 opacity-90" />
              </Link>
            </Button>
            <p className="text-sm text-muted-foreground">
              <Link href={routes.solucionesHub} className="font-medium underline-offset-4 hover:text-foreground hover:underline">
                {hero.ctaProduct}
              </Link>
              {' · '}
              <Link href={routes.login} className="font-medium underline-offset-4 hover:text-foreground hover:underline">
                {hero.ctaDemo}
              </Link>
            </p>
          </div>
        </div>
      </BlockContainer>

      <div className="relative flex flex-col">
        <Carousel
          opts={{
            loop: true,
            align: 'center',
          }}
          plugins={[
            AutoScroll({
              speed: 1,
            }),
            Autoplay({
              playOnInit: true,
              delay: 3000,
            }),
          ]}
          className={cn(
            'relative mx-auto w-full max-w-full overflow-hidden',
            'before:absolute before:left-0 before:top-0 before:z-10 before:h-full before:w-[20%] before:bg-gradient-to-r before:from-background before:to-transparent before:content-[""]',
            'after:absolute after:right-0 after:top-0 after:z-10 after:h-full after:w-[20%] after:bg-gradient-to-l after:from-transparent after:to-background after:content-[""]'
          )}
        >
          <CarouselContent className="ml-5 flex gap-5 pl-4">
            {carouselImages.map((src, i) => (
              <CarouselItem key={i} className="basis-[496px] bg-background">
                <div className="h-[380px] basis-[480px] overflow-hidden rounded-lg">
                  <Image
                    src={src}
                    alt=""
                    width={496}
                    height={380}
                    className="size-full object-cover"
                    sizes="(max-width: 1024px) 90vw, 496px"
                  />
                </div>
              </CarouselItem>
            ))}
          </CarouselContent>
        </Carousel>
      </div>

      {/* Trust strip */}
      <BlockContainer className="mt-16 border-t border-border pt-10 text-center">
        {trustStrip.partnerTitle ? (
          <p className="text-sm font-medium text-foreground">
            {trustStrip.partnerTitle}
          </p>
        ) : null}
        {trustStrip.metrics.length > 0 ? (
          <div className="mt-4 flex flex-wrap items-center justify-center gap-8 md:gap-12">
            {trustStrip.metrics.map(({ label, value }) => (
              <div key={label} className="text-center">
                <p className="text-type-stat" aria-label={`${value} ${label}`}>
                  {value}
                </p>
                <p className="text-xs font-medium uppercase tracking-wider text-muted-foreground">{label}</p>
              </div>
            ))}
          </div>
        ) : null}
        <p className="mt-4 text-sm font-medium text-foreground">
          {trustStrip.title}
        </p>
        <p className="mt-1 text-sm text-muted-foreground">
          {trustStrip.subtitle}
        </p>
      </BlockContainer>
    </section>
  );
}

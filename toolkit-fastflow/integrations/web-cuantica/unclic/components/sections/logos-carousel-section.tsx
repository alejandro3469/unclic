'use client';

import Image from 'next/image';
import AutoScroll from 'embla-carousel-auto-scroll';
import {
  Carousel,
  CarouselContent,
  CarouselItem,
} from '@/components/ui/carousel';
import { BlockContainer } from '@/components/blocks';
import { logosCarousel } from '@/lib/copy';
import { cn } from '@/lib/utils';

/** Carrusel de logos (Shadcn Blocks: Logos12) — copy desde lib/copy.ts (logosCarousel). Gradientes con bg-gradient-to-* del tema. */
export function LogosCarouselSection({ className }: { className?: string }) {
  return (
    <section className={cn('py-16 md:py-24', className)} aria-labelledby="logos-heading">
      <BlockContainer>
        <h2 id="logos-heading" className="sr-only">
          {logosCarousel.sectionTitle}
        </h2>
        <div className="relative flex items-center justify-center pt-6">
          <Carousel
            opts={{ loop: true }}
            plugins={[AutoScroll({ playOnInit: true, speed: 0.5 })]}
            className="w-full"
          >
            <CarouselContent className="ml-0">
              {logosCarousel.items.map((logo) => (
                <CarouselItem
                  key={logo.id}
                  className="relative mx-4 flex min-h-[6.25rem] basis-1/2 justify-center border-y border-border py-6 pl-0 sm:basis-1/4 md:basis-1/5 lg:basis-1/6"
                >
                  <div className="flex flex-col items-center justify-center lg:mx-10">
                    <Image
                      src={logo.image}
                      alt={logo.description}
                      width={112}
                      height={28}
                      className="h-7 w-auto object-contain dark:invert dark:opacity-90"
                    />
                  </div>
                </CarouselItem>
              ))}
            </CarouselContent>
          </Carousel>
          {/* Gradientes de borde: usar bg-gradient-to-* (no bg-linear-to-*) */}
          <div
            className="pointer-events-none absolute inset-y-0 left-0 w-24 bg-gradient-to-r from-background to-transparent"
            aria-hidden
          />
          <div
            className="pointer-events-none absolute inset-y-0 right-0 w-24 bg-gradient-to-l from-background to-transparent"
            aria-hidden
          />
        </div>
      </BlockContainer>
    </section>
  );
}

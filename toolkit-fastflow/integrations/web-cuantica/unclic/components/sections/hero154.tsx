'use client';

import Link from 'next/link';

import { BlockContainer } from '@/components/blocks';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Button } from '@/components/ui/button';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { hero } from '@/lib/copy';
import { routes } from '@/lib/routes';
import { LG } from '@/lib/icons8-liquid-glass';
import { cn } from '@/lib/utils';

/** Dos líneas tipo “badges” bajo el form: primeros pilares de `hero.heroFeatures`. */
const trustFromHero = () => {
  const a = hero.heroFeatures[0]?.title ?? '';
  const b = hero.heroFeatures[1]?.title ?? '';
  return { line1: a, line2: b };
};

interface Hero154Props {
  className?: string;
}

/**
 * Hero estilo Shadcn Blocks Hero154 — lead email + mockups.
 * Copy principal: `hero` en `lib/copy.ts` (mismo que el hero Hero70). Alternativa: `components/hero.tsx`.
 */
export function Hero154({ className }: Hero154Props) {
  const { line1, line2 } = trustFromHero();

  return (
    <section
      className={cn(
        'border-b border-b-primary/50 bg-background pt-12 md:pt-20',
        className
      )}
    >
      <BlockContainer>
        <div className="flex w-full flex-col items-center justify-center gap-16">
          <div className="flex flex-col justify-center gap-12">
            <div className="flex w-full max-w-[32.5rem] flex-col gap-6">
              {hero.heroEyebrow ? (
                <p className="text-type-eyebrow text-center tracking-[0.2em]">{hero.heroEyebrow}</p>
              ) : null}
              <h1
                id="hero-headline"
                className="text-type-page-title text-center font-medium tracking-tighter text-foreground"
              >
                <span className="block">{hero.headline}</span>
                {hero.headlineAccent ? (
                  <span className="mt-2 block text-gradient-impact">{hero.headlineAccent}</span>
                ) : null}
              </h1>
              <p className="text-type-lead text-center">{hero.subtitle}</p>
              <div className="mx-auto flex w-full max-w-[25.625rem] flex-col items-center gap-3">
                <Button asChild size="lg" className="h-auto rounded-full px-8 py-4 text-base font-semibold">
                  <Link href={routes.publicSignup} className="gap-2">
                    {hero.ctaPrimary}
                    <LiquidGlassIcon slug={LG.externalLink} size={22} alt="" className="shrink-0" />
                  </Link>
                </Button>
                <p className="text-center text-xs text-muted-foreground">
                  Mismo registro en todo el sitio: correo, verificación y contraseña.
                </p>
              </div>
            </div>
            <div className="flex items-center justify-center gap-8">
              <div className="flex items-center gap-2">
                <LiquidGlassIcon slug={LG.clock} size={18} alt="" />
                <div className="text-xs font-medium text-muted-foreground">
                  {line1}
                </div>
              </div>
              <div className="flex items-center gap-2">
                <LiquidGlassIcon slug={LG.paperPlane} size={18} alt="" />
                <div className="text-xs font-medium text-muted-foreground">
                  {line2}
                </div>
              </div>
            </div>
          </div>
          <div className="w-full">
            <div className="relative mx-auto w-full max-w-[62.5rem] overflow-hidden">
              <AspectRatio ratio={2.100840336 / 1}>
                <div className="w-full">
                  <div className="absolute top-0 left-0 w-[94.2%] overflow-hidden">
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/mockups/desktop-1.png"
                      alt=""
                      className="relative z-20 h-full w-full"
                    />
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/placeholder-1.svg"
                      alt=""
                      className="absolute top-[3%] left-[2%] z-10 w-full object-contain"
                    />
                  </div>
                  <div className="absolute right-0 -bottom-[35%] z-20 w-[23%] overflow-hidden">
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/mockups/phone-3.png"
                      alt=""
                      className="relative z-20 h-full w-full"
                    />
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/placeholder-dark-7-tall.svg"
                      alt=""
                      className="absolute top-0 left-1/2 z-10 w-full -translate-x-1/2 rounded-[15px] md:rounded-[30px]"
                    />
                  </div>
                </div>
              </AspectRatio>
            </div>
          </div>
        </div>
      </BlockContainer>
    </section>
  );
}

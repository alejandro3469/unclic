'use client';

import Image from 'next/image';
import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { Button } from '@/components/ui/button';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import {
  Card,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { cta } from '@/lib/copy';
import { routes } from '@/lib/routes';
import { HERO_IMAGE_PLACEHOLDER } from '@/lib/placeholders';
import { cn } from '@/lib/utils';
import { LG } from '@/lib/icons8-liquid-glass';

/**
 * CTA marketing — Card + Button + imagen (patrón tipo bloques shadcn).
 * https://ui.shadcn.com/docs/components/card
 */
export function CtaSection() {
  return (
    <section
      id="contacto"
      className={cn('scroll-mt-24 py-24 md:py-32')}
      aria-labelledby="cta-heading"
    >
      <BlockContainer>
        <Card className="flex flex-col overflow-hidden border-muted pb-0 md:flex-row md:pb-0">
          <div className="flex flex-1 flex-col justify-between p-6 md:max-w-md lg:max-w-lg">
            <CardHeader className="space-y-3 p-0">
              {cta.headlineAlt ? (
                <p className="text-sm font-medium text-muted-foreground">{cta.headlineAlt}</p>
              ) : null}
              <div className="flex items-center gap-2">
                <span className="flex size-9 items-center justify-center rounded-full bg-primary/10">
                  <LiquidGlassIcon slug={LG.sparkling} size={26} alt="" />
                </span>
                <CardTitle id="cta-heading" className="text-type-section-title font-bold">
                  {cta.sectionTitle}
                </CardTitle>
              </div>
              {cta.onePartner ? (
                <p className="text-sm text-muted-foreground">{cta.onePartner}</p>
              ) : null}
              <CardDescription className="text-base">{cta.sectionDescription}</CardDescription>
            </CardHeader>
            <CardFooter className="mt-8 flex flex-wrap gap-4 p-0">
              <Button asChild size="lg" className="gap-2">
                <Link href={routes.publicSignup}>
                  {cta.ctaPrimary}
                  <LiquidGlassIcon slug={LG.forward} size={18} alt="" className="shrink-0" />
                </Link>
              </Button>
              <p className="self-center text-sm text-muted-foreground">
                <Link href={routes.login} className="font-medium underline-offset-4 hover:text-foreground hover:underline">
                  {cta.ctaSecondary}
                </Link>
              </p>
            </CardFooter>
          </div>
          <div className="relative aspect-video w-full shrink-0 md:aspect-auto md:min-h-[220px] md:max-w-sm lg:max-w-md">
            <Image
              src={HERO_IMAGE_PLACEHOLDER}
              alt=""
              fill
              className="object-cover"
              sizes="(max-width: 768px) 100vw, 400px"
            />
          </div>
        </Card>
      </BlockContainer>

    </section>
  );
}

import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { Button } from '@/components/ui/button';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { ossAtlas, ossAtlasScenarios } from '@/lib/copy';
import { LG } from '@/lib/icons8-liquid-glass';

/** CTA hacia `/integraciones` — atlas ilustrativo OSS + SaaS por escenario. */
export function OssAtlasTeaserSection() {
  const preview = ossAtlasScenarios.slice(0, 10);

  return (
    <section
      className="border-b border-border bg-muted/15 py-16 md:py-20"
      aria-labelledby="oss-teaser-title"
    >
      <BlockContainer>
        <div className="flex flex-col gap-10 lg:flex-row lg:items-center lg:justify-between lg:gap-12">
          <div className="max-w-xl shrink-0">
            <p className="text-type-eyebrow-accent mb-2 tracking-wider">{ossAtlas.teaserKicker}</p>
            <h2 id="oss-teaser-title" className="text-type-section-title font-bold">
              {ossAtlas.teaserTitle}
            </h2>
            <p className="text-type-lead mt-3 max-w-xl">{ossAtlas.teaserLead}</p>
            <Button asChild className="mt-6">
              <Link href={ossAtlas.path}>
                {ossAtlas.teaserCta}
                <LiquidGlassIcon slug={LG.forward} size={16} alt="" className="ml-2 shrink-0" />
              </Link>
            </Button>
          </div>
          <div className="grid min-w-0 flex-1 grid-cols-2 gap-2 sm:grid-cols-3 lg:max-w-2xl">
            {preview.map((s) => (
              <div
                key={s.id}
                className="rounded-lg border border-border/80 bg-background/90 p-3 text-sm shadow-sm"
              >
                <div className="mb-2" aria-hidden>
                  <LiquidGlassIcon slug={LG.layers} size={20} alt="" />
                </div>
                <div className="font-medium leading-snug">{s.title}</div>
                <p className="mt-1 line-clamp-2 text-xs text-muted-foreground">
                  {s.blurb}
                </p>
              </div>
            ))}
          </div>
        </div>
      </BlockContainer>
    </section>
  );
}

import Link from 'next/link';
import { ArrowRight, Layers } from 'lucide-react';
import { BlockContainer } from '@/components/blocks';
import { Button } from '@/components/ui/button';
import { ossAtlas, ossAtlasScenarios } from '@/lib/copy';

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
            <p className="mb-2 text-xs font-semibold uppercase tracking-wider text-primary">
              {ossAtlas.teaserKicker}
            </p>
            <h2
              id="oss-teaser-title"
              className="text-2xl font-bold tracking-tight md:text-3xl"
            >
              {ossAtlas.teaserTitle}
            </h2>
            <p className="mt-3 text-muted-foreground">{ossAtlas.teaserLead}</p>
            <Button asChild className="mt-6">
              <Link href={ossAtlas.path}>
                {ossAtlas.teaserCta}
                <ArrowRight className="ml-2 size-4" aria-hidden />
              </Link>
            </Button>
          </div>
          <div className="grid min-w-0 flex-1 grid-cols-2 gap-2 sm:grid-cols-3 lg:max-w-2xl">
            {preview.map((s) => (
              <div
                key={s.id}
                className="rounded-lg border border-border/80 bg-background/90 p-3 text-sm shadow-sm"
              >
                <Layers
                  className="mb-2 size-4 text-primary"
                  aria-hidden
                />
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

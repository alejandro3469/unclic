'use client';

import { useMemo, useState } from 'react';
import Image from 'next/image';
import { ExternalLink, Package } from 'lucide-react';
import { BlockContainer } from '@/components/blocks';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { ossAtlas, ossAtlasScenarios, ossAtlasTools } from '@/lib/copy';
import { cn } from '@/lib/utils';

type ScenarioId = (typeof ossAtlasScenarios)[number]['id'] | 'all';

function scenarioTitle(id: string): string {
  const s = ossAtlasScenarios.find((x) => x.id === id);
  return s?.title ?? id;
}

function matchesScenario(
  scenarioIds: readonly string[],
  filter: ScenarioId
): boolean {
  if (filter === 'all') return true;
  return scenarioIds.includes(filter);
}

export function OssAtlasInteractiveSection() {
  const [filter, setFilter] = useState<ScenarioId>('all');

  const filtered = useMemo(
    () => ossAtlasTools.filter((t) => matchesScenario(t.scenarioIds, filter)),
    [filter]
  );

  return (
    <section
      className="border-b border-border py-16 md:py-24"
      aria-labelledby="oss-atlas-filter-heading"
    >
      <BlockContainer>
        <h2 id="oss-atlas-filter-heading" className="sr-only">
          {ossAtlas.filterLabel}
        </h2>
        <p className="mb-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">
          {ossAtlas.filterLabel}
        </p>
        <div className="mb-12 flex flex-wrap gap-2">
          <Button
            type="button"
            variant={filter === 'all' ? 'default' : 'outline'}
            size="sm"
            className="rounded-full text-xs"
            onClick={() => setFilter('all')}
          >
            {ossAtlas.filterAll}
          </Button>
          {ossAtlasScenarios.map((s) => (
            <Button
              key={s.id}
              type="button"
              variant={filter === s.id ? 'default' : 'outline'}
              size="sm"
              className="rounded-full text-xs"
              onClick={() => setFilter(s.id)}
            >
              {s.title}
            </Button>
          ))}
        </div>

        <ul className="grid gap-4 sm:grid-cols-2 xl:grid-cols-3">
          {filtered.map((tool) => (
            <li key={tool.id}>
              <div
                className={cn(
                  'flex h-full flex-col rounded-xl border border-border bg-card/40 p-4 transition-colors',
                  'hover:border-primary/40 hover:bg-card/80'
                )}
              >
                <div className="flex flex-1 items-start gap-3">
                  <div className="flex size-11 shrink-0 items-center justify-center rounded-lg border border-border bg-background">
                    {tool.icon ? (
                      <Image
                        src={`https://cdn.simpleicons.org/${tool.icon}`}
                        alt=""
                        width={28}
                        height={28}
                        className="dark:invert dark:opacity-90"
                      />
                    ) : (
                      <Package
                        className="size-6 text-muted-foreground"
                        aria-hidden
                      />
                    )}
                  </div>
                  <div className="min-w-0 flex-1">
                    <div className="flex flex-wrap items-center gap-2">
                      <span className="font-semibold leading-tight">
                        {tool.name}
                      </span>
                      <Badge
                        variant="secondary"
                        className="text-[0.65rem] font-normal"
                      >
                        {ossAtlas.cardLayer} {tool.layer}
                      </Badge>
                      <Badge
                        variant={
                          tool.kind === 'oss' ? 'outline' : 'secondary'
                        }
                        className="text-[0.65rem] font-normal"
                      >
                        {tool.kind === 'oss'
                          ? ossAtlas.kindOss
                          : ossAtlas.kindSaaS}
                      </Badge>
                    </div>
                    <p className="mt-2 text-sm text-muted-foreground">
                      {tool.tagline}
                    </p>
                    <div className="mt-3 flex flex-wrap gap-1">
                      {tool.scenarioIds.map((sid) => (
                        <Badge
                          key={sid}
                          variant="outline"
                          className="text-[0.6rem] font-normal opacity-80"
                        >
                          {scenarioTitle(sid)}
                        </Badge>
                      ))}
                    </div>
                  </div>
                </div>
                <div className="mt-4 flex flex-col gap-2 border-t border-border/80 pt-3">
                  <a
                    href={tool.docUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="inline-flex items-center gap-1 text-xs font-medium text-primary hover:underline"
                  >
                    {ossAtlas.docCta}
                    <ExternalLink className="size-3.5 opacity-70" aria-hidden />
                  </a>
                  {'extraLinks' in tool &&
                    tool.extraLinks &&
                    tool.extraLinks.map((link) => (
                      <a
                        key={link.href}
                        href={link.href}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="inline-flex items-center gap-1 text-xs text-muted-foreground transition-colors hover:text-primary hover:underline"
                      >
                        {link.label}
                        <ExternalLink
                          className="size-3 opacity-70"
                          aria-hidden
                        />
                      </a>
                    ))}
                </div>
              </div>
            </li>
          ))}
        </ul>

        <div className="mt-10 rounded-lg border border-dashed border-border/80 bg-muted/20 p-4 md:p-6">
          <p className="text-sm font-medium">Escenarios (referencia)</p>
          <ul className="mt-3 grid gap-3 sm:grid-cols-2">
            {ossAtlasScenarios.map((s) => (
              <li key={s.id} className="text-sm text-muted-foreground">
                <span className="font-medium text-foreground">{s.title}.</span>{' '}
                {s.blurb}
              </li>
            ))}
          </ul>
        </div>

        <p className="mt-10 max-w-3xl text-sm text-muted-foreground">
          {ossAtlas.footnote}
        </p>
      </BlockContainer>
    </section>
  );
}

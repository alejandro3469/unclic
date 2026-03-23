'use client';

import Link from 'next/link';
import Image from 'next/image';
import {
  Workflow,
  Package,
  ShoppingCart,
  Code2,
  Container,
  Layers,
  Mail,
  Volume2,
  Video,
  CreditCard,
  FileText,
  Lock,
} from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { BlockContainer } from '@/components/blocks';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { getHubCategories, type HubItem } from '@/lib/hub-links';
import { hubItemNeedsDemoGate } from '@/lib/hub-item-gate';
import { simpleIconUrl } from '@/lib/logo-urls';
import { hubDemos } from '@/lib/copy';
import { useDemoInfraAccess } from '@/lib/hooks/use-demo-infra-access';
import { routes } from '@/lib/routes';

const ICON_MAP = {
  workflow: Workflow,
  git: Code2,
  'shopping-cart': ShoppingCart,
  package: Package,
  container: Container,
  layers: Layers,
  mail: Mail,
  audio: Volume2,
  video: Video,
  card: CreditCard,
  file: FileText,
} as const;

function HubCard({
  item,
  canAccessInfra,
  ready,
}: {
  item: HubItem;
  canAccessInfra: boolean;
  ready: boolean;
}) {
  const Icon = ICON_MAP[item.iconId as keyof typeof ICON_MAP] ?? Layers;
  const logoUrl = item.simpleIconsSlug ? simpleIconUrl(item.simpleIconsSlug) : null;
  const isHash = !item.href || item.href === '#';
  const isSignup = Boolean(item.href?.startsWith('/portal/registro'));
  const active = !isHash || isSignup;
  const gated = hubItemNeedsDemoGate(item);
  const showLocked = ready && gated && !canAccessInfra;

  return (
    <Card className="flex h-full flex-col border-border/80 transition-shadow hover:shadow-md">
      <CardHeader className="pb-2">
        <div className="flex items-start justify-between gap-2">
          <div className="flex items-center gap-3">
            <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-lg bg-muted p-1.5">
              {logoUrl ? (
                <Image
                  src={logoUrl}
                  alt=""
                  width={28}
                  height={28}
                  className="object-contain"
                  unoptimized
                />
              ) : (
                <Icon className="size-6 text-muted-foreground" aria-hidden />
              )}
            </div>
            <div>
              <h3 className="text-base font-semibold leading-tight">{item.title}</h3>
              {item.badge === 'roadmap' && (
                <Badge variant="secondary" className="mt-1 text-[10px] uppercase">
                  {hubDemos.badgeRoadmap}
                </Badge>
              )}
            </div>
          </div>
        </div>
      </CardHeader>
      <CardContent className="flex flex-1 flex-col pt-0">
        <p className="flex-1 text-sm text-muted-foreground">{item.description}</p>
        {!ready && gated ? (
          <Button variant="outline" size="sm" className="mt-4 w-full cursor-wait sm:w-auto" disabled>
            …
          </Button>
        ) : showLocked ? (
          <div className="mt-4 space-y-2 rounded-lg border border-dashed border-muted-foreground/30 bg-muted/30 p-3">
            <p className="flex items-center gap-2 text-xs text-muted-foreground">
              <Lock className="size-3.5 shrink-0" aria-hidden />
              {hubDemos.lockedExternalHint}
            </p>
            <Button asChild variant="outline" size="sm" className="w-full sm:w-auto">
              <Link href={routes.login}>{hubDemos.lockedExternalCta}</Link>
            </Button>
          </div>
        ) : active && !isHash ? (
          <Button asChild variant="outline" size="sm" className="mt-4 w-full sm:w-auto">
            <Link
              href={item.href}
              target={item.external ? '_blank' : undefined}
              rel={item.external ? 'noopener noreferrer' : undefined}
            >
              {item.external ? hubDemos.ctaExternal : hubDemos.ctaInternal}
            </Link>
          </Button>
        ) : isSignup ? (
          <Button asChild variant="outline" size="sm" className="mt-4 w-full sm:w-auto">
            <Link href={item.href}>{hubDemos.ctaRoadmapContact}</Link>
          </Button>
        ) : (
          <Button variant="outline" size="sm" className="mt-4 w-full cursor-not-allowed opacity-60 sm:w-auto" disabled>
            {hubDemos.ctaConfigureEnv}
          </Button>
        )}
      </CardContent>
    </Card>
  );
}

export function HubDemosSection() {
  const categories = getHubCategories();
  const { ready, canAccessInfra } = useDemoInfraAccess();

  return (
    <section
      id="hub-demos"
      className="scroll-mt-20 border-b border-primary/10 bg-gradient-to-b from-amber-50/50 via-background to-sky-50/30 py-16 dark:from-amber-950/15 dark:to-sky-950/10 md:py-24"
      aria-labelledby="hub-demos-heading"
    >
      <BlockContainer>
        <div className="mx-auto max-w-3xl text-center">
          <Badge variant="outline" className="mb-4">
            {hubDemos.kicker}
          </Badge>
          <h2 id="hub-demos-heading" className="text-type-section-title">
            {hubDemos.sectionTitle}
          </h2>
          <p className="text-type-lead mt-3">{hubDemos.sectionLead}</p>
          <p className="text-type-body-sm mt-2">{hubDemos.sectionSub}</p>
        </div>

        <div className="mt-14 space-y-16">
          {categories.map((cat) => (
            <div key={cat.id}>
              <div className="mb-2 flex flex-col gap-1 border-l-4 border-primary pl-4 md:flex-row md:items-end md:justify-between">
                <h3 className="text-type-card-title">{cat.title}</h3>
                {cat.enterpriseNote && (
                  <p className="max-w-xl text-sm text-muted-foreground md:text-right">{cat.enterpriseNote}</p>
                )}
              </div>
              <div className="mt-6 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                {cat.items.map((item) => (
                  <HubCard
                    key={item.id}
                    item={item}
                    canAccessInfra={canAccessInfra}
                    ready={ready}
                  />
                ))}
              </div>
            </div>
          ))}
        </div>

        <p className="mx-auto mt-12 max-w-2xl text-center text-sm text-muted-foreground">
          {hubDemos.footerNote}
        </p>
      </BlockContainer>
    </section>
  );
}

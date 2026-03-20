'use client';

import Link from 'next/link';
import Image from 'next/image';
import {
  Workflow,
  Package,
  ShoppingCart,
  User,
  Code2,
  Container,
  Layers,
} from 'lucide-react';
import { Button } from '@/components/ui/button';
import { BlockContainer } from '@/components/blocks';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { DEMO_LINKS } from '@/lib/demos';
import { simpleIconUrl } from '@/lib/logo-urls';
import { demos } from '@/lib/copy';
import { useDemoInfraAccess } from '@/lib/hooks/use-demo-infra-access';

const ICON_MAP = {
  workflow: Workflow,
  git: Code2,
  'shopping-cart': ShoppingCart,
  package: Package,
  container: Container,
  layers: Layers,
} as const;

export function DemosSection() {
  const { ready, canAccessInfra } = useDemoInfraAccess();

  return (
    <section id="demos" className="py-16 md:py-24" aria-labelledby="demos-heading">
      <BlockContainer>
        <h2 id="demos-heading" className="text-2xl font-semibold tracking-tight sm:text-3xl md:text-4xl">
          {demos.sectionTitle}
        </h2>
        <p className="mt-2 text-muted-foreground">
          {demos.sectionDescription}
        </p>

        <div className="mt-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {DEMO_LINKS.map(({ id, title, description, href, iconId, simpleIconsSlug }) => {
            const Icon = ICON_MAP[iconId as keyof typeof ICON_MAP] ?? Package;
            const isActive = href && href !== '#';
            const logoUrl = simpleIconsSlug ? simpleIconUrl(simpleIconsSlug) : null;
            const locked = ready && isActive && !canAccessInfra;
            return (
              <Card key={id} className="flex flex-col">
                <CardHeader>
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
                    <h3 className="text-lg font-semibold">{title}</h3>
                  </div>
                </CardHeader>
                <CardContent className="flex-1">
                  <p className="text-sm text-muted-foreground">{description}</p>
                  {!ready && isActive ? (
                    <Button variant="outline" size="sm" className="mt-4 cursor-wait" disabled>
                      …
                    </Button>
                  ) : locked ? (
                    <Button asChild variant="outline" size="sm" className="mt-4">
                      <Link href="/login">{demos.cardLockedCta}</Link>
                    </Button>
                  ) : isActive ? (
                    <Button asChild variant="outline" size="sm" className="mt-4">
                      <Link href={href} target="_blank" rel="noopener noreferrer">
                        {demos.cardCta}
                      </Link>
                    </Button>
                  ) : (
                    <Button variant="outline" size="sm" className="mt-4" disabled>
                      {demos.cardSoon}
                    </Button>
                  )}
                </CardContent>
              </Card>
            );
          })}
        </div>

        <Card className="mt-8 border-2 border-border bg-card shadow-md">
          <CardHeader>
            <div className="flex items-center gap-2">
              <User className="size-6 text-primary" aria-hidden />
              <h3 className="text-lg font-semibold">{demos.userCardTitle}</h3>
            </div>
          </CardHeader>
          <CardContent>
            <p className="text-sm text-muted-foreground">{demos.userCardDescription}</p>
            <Button asChild size="sm" className="mt-4">
              <Link href="/demo/access">{demos.userCardCta}</Link>
            </Button>
          </CardContent>
        </Card>
      </BlockContainer>
    </section>
  );
}

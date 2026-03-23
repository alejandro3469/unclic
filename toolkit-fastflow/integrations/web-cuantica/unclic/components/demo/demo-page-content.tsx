'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { ArrowLeft, ExternalLink, Lock } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { BlockContainer } from '@/components/blocks';
import { DEMO_LINKS } from '@/lib/demos';
import { demos, loginPage, nav } from '@/lib/copy';
import { DEMO_SCOPE_KEY } from '@/components/demo/demo-gate';
import { routes } from '@/lib/routes';

export function DemoPageContent() {
  const jenkins = DEMO_LINKS.find((d) => d.id === 'jenkins');
  const gitea = DEMO_LINKS.find((d) => d.id === 'gitea');
  const pos = DEMO_LINKS.find((d) => d.id === 'pos');
  const [scope, setScope] = useState<'pos' | 'full'>('full');

  useEffect(() => {
    const s = sessionStorage.getItem(DEMO_SCOPE_KEY);
    setScope(s === 'pos' ? 'pos' : 'full');
  }, []);

  const posOnly = scope === 'pos';

  return (
    <div className="flex min-h-screen flex-col">
      <header className="border-b border-border bg-background">
        <BlockContainer className="flex h-14 items-center justify-between">
          <Button variant="ghost" size="sm" asChild>
            <Link href={routes.home} className="gap-2" aria-label={nav.home}>
              <ArrowLeft className="size-4" aria-hidden />
              {nav.backToHome}
            </Link>
          </Button>
          <Button variant="ghost" size="sm" asChild>
            <Link href={routes.login}>Cambiar sesión</Link>
          </Button>
        </BlockContainer>
      </header>

      <main id="main-content" className="flex-1 py-12 md:py-16">
        <BlockContainer className="space-y-10">
          <div>
            <h1 className="text-type-section-title font-semibold">
              Demo Pipeline as Code
            </h1>
            <p className="mt-2 text-muted-foreground">
              Abre la app, Jenkins o Gitea en una nueva pestaña. Pipelines y repos en vivo; entorno acotado a demo.
            </p>
            <p className="mt-3 rounded-lg border border-border bg-muted/50 px-4 py-3 text-sm text-muted-foreground">
              {demos.demoPageCredentialsNote}
            </p>
            {posOnly ? (
              <p className="mt-3 rounded-lg border border-amber-500/25 bg-amber-500/5 px-4 py-3 text-sm text-amber-950 dark:text-amber-100/90">
                {loginPage.scopePosNote}{' '}
                <Link href={routes.login} className="font-semibold text-primary underline-offset-2 hover:underline">
                  Hub demo
                </Link>
                .
              </p>
            ) : null}
          </div>

          <div
            className={
              posOnly ? 'grid gap-4 sm:grid-cols-1 md:max-w-md' : 'grid gap-4 sm:grid-cols-3'
            }
          >
            {pos?.href && pos.href !== '#' && (
              <Button asChild size="lg" className="h-auto flex-col gap-2 py-6">
                <Link href={pos.href} target="_blank" rel="noopener noreferrer">
                  {demos.openPos}
                  <ExternalLink className="size-4" aria-hidden />
                </Link>
              </Button>
            )}
            {jenkins?.href &&
              (posOnly ? (
                <div className="flex flex-col items-center justify-center gap-2 rounded-lg border border-dashed border-muted-foreground/30 bg-muted/30 px-4 py-8 text-center">
                  <Lock className="size-5 text-muted-foreground" aria-hidden />
                  <span className="text-sm text-muted-foreground">Jenkins — modo Hub demo</span>
                  <Button asChild variant="outline" size="sm">
                    <Link href={routes.login}>Iniciar sesión</Link>
                  </Button>
                </div>
              ) : (
                <Button asChild variant="outline" size="lg" className="h-auto flex-col gap-2 py-6">
                  <Link href={jenkins.href} target="_blank" rel="noopener noreferrer">
                    {demos.openJenkins}
                    <ExternalLink className="size-4" aria-hidden />
                  </Link>
                </Button>
              ))}
            {gitea?.href &&
              (posOnly ? (
                <div className="flex flex-col items-center justify-center gap-2 rounded-lg border border-dashed border-muted-foreground/30 bg-muted/30 px-4 py-8 text-center">
                  <Lock className="size-5 text-muted-foreground" aria-hidden />
                  <span className="text-sm text-muted-foreground">Gitea — modo Hub demo</span>
                  <Button asChild variant="outline" size="sm">
                    <Link href={routes.login}>Iniciar sesión</Link>
                  </Button>
                </div>
              ) : (
                <Button asChild variant="outline" size="lg" className="h-auto flex-col gap-2 py-6">
                  <Link href={gitea.href} target="_blank" rel="noopener noreferrer">
                    {demos.openGitea}
                    <ExternalLink className="size-4" aria-hidden />
                  </Link>
                </Button>
              ))}
          </div>
        </BlockContainer>
      </main>
    </div>
  );
}

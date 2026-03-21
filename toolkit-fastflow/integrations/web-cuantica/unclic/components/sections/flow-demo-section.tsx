'use client';

import Link from 'next/link';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Progress } from '@/components/ui/progress';
import { Separator } from '@/components/ui/separator';
import { flowDemo } from '@/lib/copy';

/**
 * Ejemplo mínimo del flujo: pieza shadcn (`Progress`) + copy centralizado (`flowDemo`).
 * Página: `/flow-demo` — ver docs/EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md
 */
export function FlowDemoSection() {
  return (
    <section
      className="container max-w-2xl py-16 md:py-24"
      aria-labelledby="flow-demo-title"
    >
      <Card>
        <CardHeader className="space-y-2">
          <p className="text-xs font-semibold uppercase tracking-widest text-muted-foreground">
            {flowDemo.kicker}
          </p>
          <CardTitle id="flow-demo-title" className="text-2xl tracking-tight">
            {flowDemo.title}
          </CardTitle>
          <CardDescription className="text-base">{flowDemo.lead}</CardDescription>
          <div className="flex flex-wrap gap-2 pt-2">
            {flowDemo.badges.map((b) => (
              <Badge key={b} variant="secondary">
                {b}
              </Badge>
            ))}
          </div>
        </CardHeader>
        <CardContent className="space-y-4">
          <Separator />
          <div className="space-y-2">
            <p className="text-sm font-medium text-foreground">
              {flowDemo.progressLabel}
            </p>
            <Progress value={flowDemo.progressValue} aria-label={flowDemo.progressLabel} />
            <p className="text-xs text-muted-foreground">
              {flowDemo.progressValue}% — valor también en{' '}
              <code className="rounded bg-muted px-1 py-0.5 text-[0.7rem]">
                lib/copy.ts
              </code>
            </p>
          </div>
          <p className="text-sm text-muted-foreground">{flowDemo.footnote}</p>
          <Button asChild variant="outline" size="sm">
            <Link href="/">← Volver al inicio</Link>
          </Button>
        </CardContent>
      </Card>
    </section>
  );
}

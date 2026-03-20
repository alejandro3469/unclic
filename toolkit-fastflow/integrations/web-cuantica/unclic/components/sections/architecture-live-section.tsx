'use client';

import React from 'react';
import Link from 'next/link';
import { SectionBlock } from '@/components/sections/section-block';
import { Button } from '@/components/ui/button';
import { architectureLive as copy, CLOUDCRAFT_DEMO_VIEW_BASE } from '@/lib/copy';
import { ExternalLink } from 'lucide-react';
import { isDemoAllowlistMode } from '@/lib/demo-access-policy';
import { useDemoInfraAccess } from '@/lib/hooks/use-demo-infra-access';

const embedUrl = process.env.NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL ?? '';
const viewUrl = process.env.NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL ?? '';
const staticImageSrc = process.env.NEXT_PUBLIC_CLOUDCRAFT_STATIC_IMAGE?.trim() ?? '';

/** Prioridad: embed → imagen estática → enlace vista Cloudcraft → fallback (enlace base). En modo allowlist sin sesión, no se muestran enlaces/embed a Cloudcraft. */
export function ArchitectureLiveSection() {
  const { ready, canAccessInfra } = useDemoInfraAccess();
  const hasEmbed = Boolean(embedUrl);
  const hasStatic = Boolean(staticImageSrc);
  const hasView = Boolean(viewUrl);
  const openUrl = viewUrl || CLOUDCRAFT_DEMO_VIEW_BASE;
  const gateCloudcraft = isDemoAllowlistMode();
  const locked = gateCloudcraft && ready && !canAccessInfra;

  if (gateCloudcraft && !ready) {
    return (
      <SectionBlock
        id="architecture-live"
        title={copy.sectionTitle}
        description={copy.sectionDescription || undefined}
        tone="violet"
      >
        <div className="flex min-h-[120px] items-center justify-center rounded-xl border border-border/50 bg-muted/20 p-8">
          <p className="text-sm text-muted-foreground">…</p>
        </div>
      </SectionBlock>
    );
  }

  if (locked) {
    return (
      <SectionBlock
        id="architecture-live"
        title={copy.sectionTitle}
        description={copy.sectionDescription || undefined}
        tone="violet"
      >
        <div className="space-y-4 rounded-xl border border-border/50 bg-muted/20 p-8">
          <p className="text-center text-sm text-muted-foreground">{copy.lockedLead}</p>
          <div className="flex justify-center">
            <Button asChild size="lg">
              <Link href="/login">{copy.lockedCta}</Link>
            </Button>
          </div>
        </div>
      </SectionBlock>
    );
  }

  return (
    <SectionBlock
      id="architecture-live"
      title={copy.sectionTitle}
      description={copy.sectionDescription || undefined}
      tone="violet"
    >
      <div className="space-y-4">
        {hasEmbed ? (
          <div className="relative w-full overflow-hidden rounded-xl border border-border/50 bg-muted/20">
            <iframe
              title={copy.iframeTitle}
              src={embedUrl}
              className="h-[480px] w-full min-h-[320px]"
              allowFullScreen
            />
          </div>
        ) : hasStatic ? (
          <div className="space-y-3">
            <div className="relative w-full overflow-auto rounded-xl border border-border/50 bg-muted/10">
              {/* eslint-disable-next-line @next/next/no-img-element */}
              <img
                src={staticImageSrc}
                alt={copy.imageAlt}
                className="mx-auto h-auto w-full max-w-5xl object-contain"
                width={1600}
                height={900}
              />
            </div>
            <Button asChild size="sm">
              <a href={openUrl} target="_blank" rel="noopener noreferrer" className="inline-flex gap-2">
                {copy.ctaOpen}
                <ExternalLink className="size-4" aria-hidden />
              </a>
            </Button>
          </div>
        ) : (
          <div className="flex flex-col items-center gap-4 rounded-xl border border-border/50 bg-muted/20 p-8">
            <Button asChild size="lg">
              <a href={openUrl} target="_blank" rel="noopener noreferrer" className="inline-flex gap-2">
                {hasView ? copy.ctaOpen : copy.ctaOpenDemo}
                <ExternalLink className="size-4" aria-hidden />
              </a>
            </Button>
          </div>
        )}
      </div>
    </SectionBlock>
  );
}

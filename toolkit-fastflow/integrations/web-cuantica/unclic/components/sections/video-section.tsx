'use client';

import { PLACEHOLDER_VIDEO_URL } from '@/lib/placeholders';
import { BlockContainer } from '@/components/blocks';
import { video } from '@/lib/copy';
import { Card, CardContent, CardHeader } from '@/components/ui/card';

export function VideoSection() {
  return (
    <section
      id="video"
      className="bg-gradient-to-b from-slate-100/50 to-background py-16 dark:from-slate-900/30 md:py-24"
      aria-labelledby="video-heading"
    >
      <BlockContainer>
        <h2 id="video-heading" className="text-type-section-title">
          {video.sectionTitle}
        </h2>
        <p className="text-type-lead mt-2 max-w-2xl">
          {video.sectionDescription}
        </p>

        <Card className="mt-8 overflow-hidden">
          <CardHeader>
            <span className="text-sm font-medium text-muted-foreground">
              {video.cardLabel}
            </span>
          </CardHeader>
          <CardContent className="p-0">
            <div className="relative aspect-video w-full bg-muted">
              <video
                src={PLACEHOLDER_VIDEO_URL}
                controls
                className="h-full w-full object-contain"
                poster=""
                aria-label={video.videoLabel}
              >
                {video.fallbackText}
              </video>
            </div>
            <p className="border-t border-border px-4 py-3 text-center text-xs text-muted-foreground">
              {video.ossMediaFootnote}
            </p>
          </CardContent>
        </Card>
      </BlockContainer>
    </section>
  );
}

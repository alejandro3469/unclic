'use client';

import { iconCloud } from '@/lib/copy';
import { SectionBlock } from '@/components/sections/section-block';
import { Badge } from '@/components/ui/badge';
import { SLUG_LABELS } from '@/lib/icon-cloud-slugs';

/** Stack tecnológico — Badge (shadcn) en lugar de canvas / física 3D. */
export function IconCloudSection() {
  return (
    <SectionBlock
      id="stack"
      title={iconCloud.sectionTitle}
      description={iconCloud.sectionDescription}
      tone="wine"
    >
      <div className="flex flex-wrap justify-center gap-2 md:gap-3">
        {SLUG_LABELS.map(({ slug, label }) => (
          <Badge key={slug} variant="secondary" className="px-3 py-1.5 text-sm font-medium">
            {label}
          </Badge>
        ))}
      </div>
    </SectionBlock>
  );
}

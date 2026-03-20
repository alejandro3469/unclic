'use client';

import { iconCloud } from '@/lib/copy';
import { iconCloudImageUrls } from '@/lib/icon-cloud-slugs';
import { SectionBlock } from '@/components/sections/section-block';
import { IconCloud } from '@/components/ui/icon-cloud';

export function IconCloudSection() {
  return (
    <SectionBlock
      id="stack"
      title={iconCloud.sectionTitle}
      description={iconCloud.sectionDescription}
      tone="wine"
    >
      <div className="flex justify-center">
        <IconCloud
          images={iconCloudImageUrls}
          width={480}
          height={480}
          className="min-h-[360px] w-full max-w-[480px]"
        />
      </div>
    </SectionBlock>
  );
}

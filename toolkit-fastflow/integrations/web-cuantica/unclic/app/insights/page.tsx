import type { Metadata } from 'next';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { GallerySection } from '@/components/sections/gallery-section';
import { VideoSection } from '@/components/sections/video-section';
import { AudioSection } from '@/components/sections/audio-section';
import { GlobeSection } from '@/components/sections/globe-section';
import { SocialTrendingCarouselSection } from '@/components/sections/social-trending-carousel-section';
import { ClienteIdealSection } from '@/components/sections/cliente-ideal-section';
import { CtaSection } from '@/components/sections/cta-section';
import { Separator } from '@/components/ui/separator';
import { insightsPageIntro, pagesMeta } from '@/lib/copy';

export const metadata: Metadata = {
  title: pagesMeta.insights.title,
  description: pagesMeta.insights.description,
};

export default function InsightsPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <PageIntroWithAnchors
          eyebrow={insightsPageIntro.eyebrow}
          title={insightsPageIntro.title}
          lead={insightsPageIntro.lead}
          navLabel={insightsPageIntro.navLabel}
          links={insightsPageIntro.links}
        />
        <GallerySection />
        <Separator />
        <VideoSection />
        <AudioSection />
        <Separator />
        <GlobeSection />
        <Separator />
        <SocialTrendingCarouselSection />
        <ClienteIdealSection />
        <Separator />
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { GallerySection } from '@/components/sections/gallery-section';
import { VideoSection } from '@/components/sections/video-section';
import { AudioSection } from '@/components/sections/audio-section';
import { GlobeSection } from '@/components/sections/globe-section';
import { SocialTrendingCarouselSection } from '@/components/sections/social-trending-carousel-section';
import { ClienteIdealSection } from '@/components/sections/cliente-ideal-section';
import { CtaSection } from '@/components/sections/cta-section';
import { Separator } from '@/components/ui/separator';

export const metadata = {
  title: 'Insights',
  description: 'Galería, vídeo, audio, presencia global y para quién es UnClic.',
};

export default function InsightsPage() {
  return (
    <>
      <Header />
      <main id="main-content">
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

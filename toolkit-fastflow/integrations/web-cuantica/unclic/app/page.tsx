import { TopBanner } from '@/components/layout/top-banner';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { Hero } from '@/components/sections/hero';
import { LogosCarouselSection } from '@/components/sections/logos-carousel-section';
import { OssAtlasTeaserSection } from '@/components/sections/oss-atlas-teaser-section';
import { CtaSection } from '@/components/sections/cta-section';

/** Home: hero + carrusel logos + atlas integraciones (teaser) + CTA. Resto en /soluciones, /capacidades, /integraciones, /insights, /empresa, /contacto. */
export default function HomePage() {
  return (
    <>
      <TopBanner />
      <Header />
      <main id="main-content">
        <Hero />
        <LogosCarouselSection />
        <OssAtlasTeaserSection />
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

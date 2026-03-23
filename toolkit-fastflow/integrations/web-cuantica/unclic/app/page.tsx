import { TopBanner } from '@/components/layout/top-banner';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { Hero } from '@/components/sections/hero';
import { HomePillarsSection } from '@/components/sections/home-pillars-section';
import { HomeOpenSourceSection } from '@/components/sections/home-open-source-section';
import { HomeJourneySection } from '@/components/sections/home-journey-section';
import { LogosCarouselSection } from '@/components/sections/logos-carousel-section';
import { OssAtlasTeaserSection } from '@/components/sections/oss-atlas-teaser-section';
import { CtaSection } from '@/components/sections/cta-section';

/**
 * Home — arquitectura tipo agencia (servicios → filosofía OSS → fases → prueba social → atlas → CTA).
 * Páginas profundas: /soluciones, /capacidades, /integraciones, /insights, /empresa, /contacto, /portal.
 */
export default function HomePage() {
  return (
    <>
      <TopBanner />
      <Header />
      <main id="main-content">
        <Hero />
        <HomePillarsSection />
        <HomeOpenSourceSection />
        <HomeJourneySection />
        <LogosCarouselSection />
        <OssAtlasTeaserSection />
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

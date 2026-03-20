import { TopBanner } from '@/components/layout/top-banner';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { Hero } from '@/components/sections/hero';
import { LogosCarouselSection } from '@/components/sections/logos-carousel-section';
import { CtaSection } from '@/components/sections/cta-section';

/** Home: hero + carrusel logos (stack) + CTA. Resto en /soluciones, /capacidades, /insights, /empresa, /contacto. */
export default function HomePage() {
  return (
    <>
      <TopBanner />
      <Header />
      <main id="main-content">
        <Hero />
        <LogosCarouselSection />
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

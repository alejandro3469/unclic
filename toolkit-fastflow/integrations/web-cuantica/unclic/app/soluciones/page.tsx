import type { Metadata } from 'next';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { HubDemosSection } from '@/components/sections/hub-demos-section';
import { FeaturesSection } from '@/components/sections/features-section';
import { DemosSection } from '@/components/sections/demos-section';
import { PricingSection } from '@/components/sections/pricing-section';
import { FaqSection } from '@/components/sections/faq-section';
import { CtaSection } from '@/components/sections/cta-section';
import { Separator } from '@/components/ui/separator';
import { pagesMeta, solucionesPageIntro } from '@/lib/copy';

export const metadata: Metadata = {
  title: pagesMeta.soluciones.title,
  description: pagesMeta.soluciones.description,
};

export default function SolucionesPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <PageIntroWithAnchors
          eyebrow={solucionesPageIntro.eyebrow}
          title={solucionesPageIntro.title}
          lead={solucionesPageIntro.lead}
          navLabel={solucionesPageIntro.navLabel}
          links={solucionesPageIntro.links}
        />
        <HubDemosSection />
        <Separator />
        <FeaturesSection />
        <Separator />
        <DemosSection />
        <Separator />
        <PricingSection />
        <Separator />
        <FaqSection />
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

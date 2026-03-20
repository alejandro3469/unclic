import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { HubDemosSection } from '@/components/sections/hub-demos-section';
import { FeaturesSection } from '@/components/sections/features-section';
import { DemosSection } from '@/components/sections/demos-section';
import { PricingSection } from '@/components/sections/pricing-section';
import { FaqSection } from '@/components/sections/faq-section';
import { CtaSection } from '@/components/sections/cta-section';
import { Separator } from '@/components/ui/separator';

export const metadata = {
  title: 'Soluciones',
  description: 'Hub de demos, pipeline, despliegue y precios. UnClic.',
};

export default function SolucionesPage() {
  return (
    <>
      <Header />
      <main id="main-content">
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

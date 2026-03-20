import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { WhySection } from '@/components/sections/why-section';
import { IconCloudSection } from '@/components/sections/icon-cloud-section';
import { AnimatedBeamSection } from '@/components/sections/animated-beam-section';
import { FlowDiagramsSection } from '@/components/sections/flow-diagrams-section';
import { ArchitectureLiveSection } from '@/components/sections/architecture-live-section';
import { HowItWorksSection } from '@/components/sections/how-it-works-section';
import { MatrixDisplaySection } from '@/components/sections/matrix-display-section';
import { CtaSection } from '@/components/sections/cta-section';
import { Separator } from '@/components/ui/separator';

export const metadata = {
  title: 'Capacidades',
  description: 'Pipeline as Code, Jenkins, Gitea, flujos y arquitectura. UnClic.',
};

export default function CapacidadesPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <WhySection />
        <Separator />
        <IconCloudSection />
        <Separator />
        <AnimatedBeamSection />
        <FlowDiagramsSection />
        <Separator />
        <ArchitectureLiveSection />
        <Separator />
        <HowItWorksSection />
        <MatrixDisplaySection />
        <Separator />
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

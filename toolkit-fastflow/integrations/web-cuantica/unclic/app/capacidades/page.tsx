import type { Metadata } from 'next';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { WhySection } from '@/components/sections/why-section';
import { IconCloudSection } from '@/components/sections/icon-cloud-section';
import { AnimatedBeamSection } from '@/components/sections/animated-beam-section';
import { FlowDiagramsSection } from '@/components/sections/flow-diagrams-section';
import { ArchitectureLiveSection } from '@/components/sections/architecture-live-section';
import { HowItWorksSection } from '@/components/sections/how-it-works-section';
import { MatrixDisplaySection } from '@/components/sections/matrix-display-section';
import { CtaSection } from '@/components/sections/cta-section';
import { Separator } from '@/components/ui/separator';
import { capacidadesPageIntro, pagesMeta } from '@/lib/copy';

export const metadata: Metadata = {
  title: pagesMeta.capacidades.title,
  description: pagesMeta.capacidades.description,
};

export default function CapacidadesPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <PageIntroWithAnchors
          eyebrow={capacidadesPageIntro.eyebrow}
          title={capacidadesPageIntro.title}
          lead={capacidadesPageIntro.lead}
          navLabel={capacidadesPageIntro.navLabel}
          links={capacidadesPageIntro.links}
        />
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

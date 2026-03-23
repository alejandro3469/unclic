import type { Metadata } from 'next';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { Hero2Section } from '@/components/sections/hero2-section';
import { WhySection } from '@/components/sections/why-section';
import { FaqSection } from '@/components/sections/faq-section';
import { Separator } from '@/components/ui/separator';
import { empresaPageIntro, pagesMeta } from '@/lib/copy';

export const metadata: Metadata = {
  title: pagesMeta.empresa.title,
  description: pagesMeta.empresa.description,
};

export default function EmpresaPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <PageIntroWithAnchors
          variant="subnav"
          eyebrow={empresaPageIntro.eyebrow}
          navLabel={empresaPageIntro.navLabel}
          links={empresaPageIntro.links}
        />
        <Hero2Section id="empresa-hero" />
        <Separator />
        <WhySection />
        <Separator />
        <FaqSection />
      </main>
      <Footer />
    </>
  );
}

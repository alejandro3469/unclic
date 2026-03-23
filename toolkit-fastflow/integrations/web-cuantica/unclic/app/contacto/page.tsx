import type { Metadata } from 'next';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { CtaSection } from '@/components/sections/cta-section';
import { contactoPageIntro, pagesMeta } from '@/lib/copy';

export const metadata: Metadata = {
  title: pagesMeta.contacto.title,
  description: pagesMeta.contacto.description,
};

export default function ContactoPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <PageIntroWithAnchors
          eyebrow={contactoPageIntro.eyebrow}
          title={contactoPageIntro.title}
          lead={contactoPageIntro.lead}
          navLabel={contactoPageIntro.navLabel}
          links={contactoPageIntro.links}
        />
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

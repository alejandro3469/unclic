import type { Metadata } from 'next';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { OssAtlasInteractiveSection } from '@/components/sections/oss-atlas-interactive-section';
import { integracionesPageIntro, ossAtlas, pagesMeta } from '@/lib/copy';

export const metadata: Metadata = {
  title: pagesMeta.integraciones.title,
  description: pagesMeta.integraciones.description,
};

export default function IntegracionesPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <PageIntroWithAnchors
          eyebrow={ossAtlas.pageEyebrow}
          title={ossAtlas.pageTitle}
          lead={`${ossAtlas.pageLead} ${ossAtlas.pageSub}`}
          navLabel={integracionesPageIntro.navLabel}
          links={integracionesPageIntro.links}
        />
        <OssAtlasInteractiveSection />
      </main>
      <Footer />
    </>
  );
}

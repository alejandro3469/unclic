import type { Metadata } from 'next';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { BlockContainer } from '@/components/blocks';
import { OssAssessmentWizard } from '@/components/presupuesto-oss/oss-assessment-wizard';
import { pagesMeta, presupuestoOssPageIntro } from '@/lib/copy';
import { presupuestoOssPage } from '@/lib/copy-presupuesto-oss';

export const metadata: Metadata = {
  title: pagesMeta.presupuestoOss.title,
  description: pagesMeta.presupuestoOss.description,
};

export default function PresupuestoOssPage() {
  return (
    <>
      <Header />
      <main id="main-content" className="pb-12 md:pb-16">
        <PageIntroWithAnchors
          eyebrow={presupuestoOssPage.heroKicker}
          title={presupuestoOssPage.heroTitle}
          lead={presupuestoOssPage.heroLead}
          navLabel={presupuestoOssPageIntro.navLabel}
          links={presupuestoOssPageIntro.links}
        />
        <BlockContainer className="max-w-3xl pb-4">
          <div
            id="presupuesto-wizard"
            className="scroll-mt-24 rounded-xl border border-border bg-card p-6 md:p-8"
          >
            <OssAssessmentWizard />
          </div>

          <section
            id="presupuesto-api"
            className="scroll-mt-24 mt-12 border-t border-border pt-10 text-sm text-muted-foreground"
          >
            <h2 className="text-base font-semibold text-foreground">{presupuestoOssPage.apiTitle}</h2>
            <p className="mt-2">{presupuestoOssPage.apiBody}</p>
            <p className="mt-2 font-mono text-xs">{presupuestoOssPage.apiEndpoints}</p>
          </section>
        </BlockContainer>
      </main>
      <Footer />
    </>
  );
}

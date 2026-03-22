import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { BlockContainer } from '@/components/blocks';
import { OssAtlasInteractiveSection } from '@/components/sections/oss-atlas-interactive-section';
import { ossAtlas } from '@/lib/copy';

export const metadata = {
  title: ossAtlas.seoTitle,
  description: ossAtlas.seoDescription,
};

export default function IntegracionesPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <section className="border-b border-border py-14 md:py-20">
          <BlockContainer>
            <p className="mb-2 text-xs font-semibold uppercase tracking-wider text-primary">
              {ossAtlas.pageEyebrow}
            </p>
            <h1 className="text-3xl font-bold tracking-tight md:text-4xl">
              {ossAtlas.pageTitle}
            </h1>
            <p className="mt-4 max-w-3xl text-lg text-muted-foreground">
              {ossAtlas.pageLead}
            </p>
            <p className="mt-3 max-w-3xl text-sm text-muted-foreground">
              {ossAtlas.pageSub}
            </p>
          </BlockContainer>
        </section>
        <OssAtlasInteractiveSection />
      </main>
      <Footer />
    </>
  );
}

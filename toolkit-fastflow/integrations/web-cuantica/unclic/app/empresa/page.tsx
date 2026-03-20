import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { Hero2Section } from '@/components/sections/hero2-section';
import { WhySection } from '@/components/sections/why-section';
import { FaqSection } from '@/components/sections/faq-section';
import { Separator } from '@/components/ui/separator';

export const metadata = {
  title: 'Empresa',
  description: 'Por qué UnClic y preguntas frecuentes.',
};

export default function EmpresaPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <Hero2Section />
        <Separator />
        <WhySection />
        <Separator />
        <FaqSection />
      </main>
      <Footer />
    </>
  );
}

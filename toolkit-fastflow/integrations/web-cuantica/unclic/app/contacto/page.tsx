import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { CtaSection } from '@/components/sections/cta-section';

export const metadata = {
  title: 'Contacto',
  description: 'Conecta con UnClic.',
};

export default function ContactoPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <CtaSection />
      </main>
      <Footer />
    </>
  );
}

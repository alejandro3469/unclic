import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { FlowDemoSection } from '@/components/sections/flow-demo-section';

export const metadata = {
  title: 'Ejemplo flujo bloque + copy — UnClic',
  description:
    'Demo mínima: componente shadcn + textos en lib/copy.ts. Documentación en docs/EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md',
};

/** Ruta de prueba del flujo documentado; no sustituye la home. */
export default function FlowDemoPage() {
  return (
    <>
      <Header />
      <main id="main-content">
        <FlowDemoSection />
      </main>
      <Footer />
    </>
  );
}

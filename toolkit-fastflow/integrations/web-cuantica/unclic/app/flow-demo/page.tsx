import { Suspense } from 'react';
import { Header } from '@/components/layout/header';
import { Footer } from '@/components/layout/footer';
import { FlowDemoSection } from '@/components/sections/flow-demo-section';
import { DemoGate } from '@/components/demo/demo-gate';

export const metadata = {
  title: 'Ejemplo flujo bloque + copy — UnClic',
  description:
    'Demo mínima: componente shadcn + textos en lib/copy.ts. Tras el portal (JWT). Documentación en docs/EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md',
};

/** Ruta de prueba del flujo documentado; protegida igual que /demo cuando el portal está activo. */
export default function FlowDemoPage() {
  return (
    <Suspense
      fallback={
        <div className="flex min-h-screen items-center justify-center">
          <p className="text-muted-foreground">Cargando…</p>
        </div>
      }
    >
      <DemoGate>
        <>
          <Header />
          <main id="main-content">
            <FlowDemoSection />
          </main>
          <Footer />
        </>
      </DemoGate>
    </Suspense>
  );
}

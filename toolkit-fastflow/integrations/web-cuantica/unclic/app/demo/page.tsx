import { Suspense } from 'react';
import { DemoGate } from '@/components/demo/demo-gate';
import { DemoPageContent } from '@/components/demo/demo-page-content';

export const metadata = {
  title: 'Demo Pipeline as Code',
  description:
    'Acceso a la demo: app de recursos y finanzas, Jenkins y Gitea. Deja tu correo en /demo/access o inicia sesión en /login.',
};

export default function DemoPage() {
  return (
    <Suspense
      fallback={
        <div className="flex min-h-screen items-center justify-center">
          <p className="text-muted-foreground">Cargando…</p>
        </div>
      }
    >
      <DemoGate>
        <DemoPageContent />
      </DemoGate>
    </Suspense>
  );
}

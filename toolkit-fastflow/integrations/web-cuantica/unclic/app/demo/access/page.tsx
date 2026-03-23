'use client';

import { Suspense, useEffect } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { routes } from '@/lib/routes';

/**
 * Compatibilidad: `/demo/access` → registro unificado.
 * Preserva `?next=/ruta` para tras el login (p. ej. `?next=/demo`).
 */
function RedirectInner() {
  const router = useRouter();
  const searchParams = useSearchParams();

  useEffect(() => {
    const next = searchParams.get('next')?.trim();
    const qs =
      next && next.startsWith('/') && !next.startsWith('//')
        ? `?next=${encodeURIComponent(next)}`
        : '';
    router.replace(`${routes.publicSignup}${qs}`);
  }, [router, searchParams]);

  return (
    <p className="flex min-h-[40vh] items-center justify-center px-4 text-center text-muted-foreground">
      Redirigiendo al registro…
    </p>
  );
}

export default function DemoAccessRedirectPage() {
  return (
    <Suspense
      fallback={
        <p className="flex min-h-[40vh] items-center justify-center text-muted-foreground">Cargando…</p>
      }
    >
      <RedirectInner />
    </Suspense>
  );
}

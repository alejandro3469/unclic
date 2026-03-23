'use client';

import { Suspense, useEffect, useState } from 'react';
import Link from 'next/link';
import { useSearchParams } from 'next/navigation';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { BlockContainer } from '@/components/blocks';
import { getUnclicApiBase } from '@/lib/api-base';
import { nav } from '@/lib/copy';
import { routes } from '@/lib/routes';

function VerifyInner() {
  const searchParams = useSearchParams();
  const token = searchParams.get('token');
  const api = getUnclicApiBase();
  const [status, setStatus] = useState<'idle' | 'loading' | 'ok' | 'err'>('idle');
  const [detail, setDetail] = useState<string>('');

  useEffect(() => {
    if (!token || !api) {
      setStatus('err');
      setDetail(!api ? 'API no configurada.' : 'Falta el token en el enlace.');
      return;
    }
    let cancelled = false;
    setStatus('loading');
    void (async () => {
      const res = await fetch(`${api}/v1/auth/verify-email?token=${encodeURIComponent(token)}`);
      const data = (await res.json().catch(() => ({}))) as { ok?: boolean; error?: string; email?: string };
      if (cancelled) return;
      if (res.ok && data.ok) {
        setStatus('ok');
        setDetail(data.email ?? '');
        return;
      }
      setStatus('err');
      setDetail(
        data.error === 'token_expired'
          ? 'El enlace ha caducado. Regístrate de nuevo o pide un nuevo enlace (próximamente).'
          : 'Enlace inválido o ya usado.'
      );
    })();
    return () => {
      cancelled = true;
    };
  }, [token, api]);

  return (
    <div className="min-h-screen bg-background py-12 md:py-16">
      <BlockContainer className="mx-auto max-w-md">
        <Card>
          <CardHeader>
            <CardTitle>Verificación de correo</CardTitle>
            <CardDescription>Confirmación de cuenta — portal UnClic.</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            {status === 'loading' ? <p className="text-muted-foreground">Verificando…</p> : null}
            {status === 'ok' ? (
              <>
                <p className="text-green-700 dark:text-green-400">
                  Cuenta verificada{detail ? ` (${detail})` : ''}. Ya puedes iniciar sesión.
                </p>
                <Button asChild className="w-full">
                  <Link href={routes.loginNextPortal}>{nav.login}</Link>
                </Button>
              </>
            ) : null}
            {status === 'err' ? (
              <>
                <p className="text-destructive">{detail}</p>
                <Button asChild variant="outline" className="w-full">
                  <Link href={routes.portalRegistro}>Volver al registro</Link>
                </Button>
              </>
            ) : null}
          </CardContent>
        </Card>
      </BlockContainer>
    </div>
  );
}

export default function VerifyEmailPage() {
  return (
    <Suspense
      fallback={
        <div className="flex min-h-screen items-center justify-center">
          <p className="text-muted-foreground">Cargando…</p>
        </div>
      }
    >
      <VerifyInner />
    </Suspense>
  );
}

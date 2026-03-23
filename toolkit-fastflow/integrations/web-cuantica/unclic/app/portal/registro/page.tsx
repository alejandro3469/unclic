'use client';

import { useState, Suspense } from 'react';
import Link from 'next/link';
import { useSearchParams } from 'next/navigation';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { BlockContainer } from '@/components/blocks';
import { getUnclicApiBase } from '@/lib/api-base';
import { nav } from '@/lib/copy';
import { routes } from '@/lib/routes';

function PortalRegistroForm() {
  const api = getUnclicApiBase();
  const searchParams = useSearchParams();
  const rawNext = searchParams.get('next')?.trim();
  const nextAfterLogin =
    rawNext && rawNext.startsWith('/') && !rawNext.startsWith('//') ? rawNext : '/portal';
  const loginWithNext = `${routes.login}?next=${encodeURIComponent(nextAfterLogin)}`;

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [password2, setPassword2] = useState('');
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState<string | null>(null);
  const [devLink, setDevLink] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setMessage(null);
    setDevLink(null);
    if (password.length < 8) {
      setError('La contraseña debe tener al menos 8 caracteres.');
      return;
    }
    if (password !== password2) {
      setError('Las contraseñas no coinciden.');
      return;
    }
    if (!api) {
      setError('API no configurada (NEXT_PUBLIC_UNCLIC_API_URL).');
      return;
    }
    setLoading(true);
    try {
      const res = await fetch(`${api}/v1/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: email.trim().toLowerCase(), password }),
      });
      const data = (await res.json().catch(() => ({}))) as {
        ok?: boolean;
        error?: string;
        message?: string;
        devVerificationUrl?: string;
      };
      if (!res.ok) {
        if (data.error === 'email_taken') {
          setError('Ese correo ya está registrado. Inicia sesión o usa recuperación si la añadimos más adelante.');
        } else {
          setError('No se pudo completar el registro. Revisa la API y los logs.');
        }
        return;
      }
      setMessage(
        data.message ?? 'Cuenta creada. Revisa tu correo para verificar antes de iniciar sesión.'
      );
      if (data.devVerificationUrl) {
        setDevLink(data.devVerificationUrl);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-background py-12 md:py-16">
      <BlockContainer className="mx-auto max-w-md">
        <Card>
          <CardHeader>
            <CardTitle>Crear cuenta — portal</CardTitle>
            <CardDescription>
              Un solo registro para el sitio: correo, contraseña y verificación por enlace (la contraseña no se envía
              por email).
            </CardDescription>
          </CardHeader>
          <CardContent>
            {!api ? (
              <p className="text-sm text-destructive">
                Configura <code className="text-xs">NEXT_PUBLIC_UNCLIC_API_URL</code> y arranca{' '}
                <code className="text-xs">npm run dev:api</code>.
              </p>
            ) : (
              <form onSubmit={submit} className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="reg-email">Correo</Label>
                  <Input
                    id="reg-email"
                    type="email"
                    required
                    autoComplete="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    disabled={loading}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="reg-pass">Contraseña (mín. 8)</Label>
                  <Input
                    id="reg-pass"
                    type="password"
                    required
                    autoComplete="new-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    disabled={loading}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="reg-pass2">Repetir contraseña</Label>
                  <Input
                    id="reg-pass2"
                    type="password"
                    required
                    autoComplete="new-password"
                    value={password2}
                    onChange={(e) => setPassword2(e.target.value)}
                    disabled={loading}
                  />
                </div>
                {error ? (
                  <p className="text-sm text-destructive" role="alert">
                    {error}
                  </p>
                ) : null}
                {message ? (
                  <p className="text-sm text-green-700 dark:text-green-400" role="status">
                    {message}
                  </p>
                ) : null}
                {devLink ? (
                  <p className="break-all text-xs text-muted-foreground">
                    Dev (sin SMTP):{' '}
                    <a href={devLink} className="text-primary underline">
                      abrir verificación
                    </a>
                  </p>
                ) : null}
                <Button type="submit" className="w-full" disabled={loading}>
                  {loading ? 'Enviando…' : 'Registrarme'}
                </Button>
              </form>
            )}
          </CardContent>
          <CardFooter className="flex flex-col gap-2 border-t border-border pt-6 text-sm text-muted-foreground">
            <Link href={loginWithNext} className="text-foreground underline-offset-2 hover:underline">
              Ya tengo cuenta
            </Link>
            <Link href={routes.portal} className="underline-offset-2 hover:underline">
              Volver al portal
            </Link>
            <Link href={routes.home} className="underline-offset-2 hover:underline">
              {nav.backToHome}
            </Link>
          </CardFooter>
        </Card>
      </BlockContainer>
    </div>
  );
}

export default function PortalRegistroPage() {
  return (
    <Suspense
      fallback={
        <div className="flex min-h-screen items-center justify-center bg-background">
          <p className="text-muted-foreground">Cargando…</p>
        </div>
      }
    >
      <PortalRegistroForm />
    </Suspense>
  );
}

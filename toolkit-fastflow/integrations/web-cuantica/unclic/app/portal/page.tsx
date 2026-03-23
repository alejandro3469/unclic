'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { LogOut, ExternalLink } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { BlockContainer } from '@/components/blocks';
import { UnClicLogo } from '@/components/ui/unclic-logo';
import { nav } from '@/lib/copy';
import { routes } from '@/lib/routes';
import { getUnclicApiBase } from '@/lib/api-base';
import { fetchPortalMe, type PortalUser } from '@/lib/portal-api';
import { clearPortalJwt, getPortalJwt } from '@/lib/portal-session';
import {
  DEMO_ACCESS_KEY,
  DEMO_LOGIN_EMAIL_KEY,
  DEMO_BYPASS_KEY,
} from '@/lib/demo-session-keys';

const PROTECTED_LINKS = [
  {
    href: routes.demo,
    title: 'Hub demo Pipeline',
    description: 'POS, Jenkins, Gitea y enlaces del entorno acotado.',
  },
  {
    href: routes.flowDemo,
    title: 'Ejemplo flujo bloque + copy',
    description: 'Página técnica de referencia (shadcn + copy).',
  },
] as const;

export default function PortalPage() {
  const [user, setUser] = useState<PortalUser | null | undefined>(undefined);
  const api = getUnclicApiBase();

  useEffect(() => {
    const t = getPortalJwt();
    if (!t || !api) {
      setUser(null);
      return;
    }
    let cancelled = false;
    void (async () => {
      const me = await fetchPortalMe(t);
      if (!cancelled) {
        setUser(me.ok ? me.user : null);
      }
    })();
    return () => {
      cancelled = true;
    };
  }, [api]);

  const logout = () => {
    clearPortalJwt();
    try {
      sessionStorage.removeItem(DEMO_ACCESS_KEY);
      sessionStorage.removeItem(DEMO_LOGIN_EMAIL_KEY);
      sessionStorage.removeItem(DEMO_BYPASS_KEY);
    } catch {
      /* ignore */
    }
    setUser(null);
  };

  if (user === undefined) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-background">
        <p className="text-muted-foreground">Cargando portal…</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background">
      <header className="border-b border-border">
        <BlockContainer className="flex h-14 items-center justify-between gap-4">
          <Link href={routes.home} className="flex items-center gap-2" aria-label={nav.home}>
            <UnClicLogo size={32} />
          </Link>
          <div className="flex items-center gap-2">
            {user ? (
              <Button type="button" variant="outline" size="sm" onClick={logout} className="gap-2">
                <LogOut className="size-4" aria-hidden />
                Salir
              </Button>
            ) : (
              <>
                <Button asChild variant="ghost" size="sm">
                  <Link href={routes.loginNextPortal}>{nav.login}</Link>
                </Button>
                <Button asChild size="sm">
                  <Link href={routes.portalRegistro}>Crear cuenta</Link>
                </Button>
              </>
            )}
          </div>
        </BlockContainer>
      </header>

      <main id="main-content" className="py-12 md:py-16">
        <BlockContainer className="mx-auto max-w-2xl space-y-8">
          <div>
            <h1 className="text-type-page-title font-semibold tracking-tight">Portal UnClic</h1>
            <p className="mt-2 text-muted-foreground">
              Demos y entornos de prueba tras verificar correo e iniciar sesión. El sitio público (presupuesto OSS,
              integraciones, contacto) sigue abierto sin cuenta.
            </p>
          </div>

          {!api ? (
            <Card>
              <CardHeader>
                <CardTitle>API no configurada</CardTitle>
                <CardDescription>
                  Define <code className="text-xs">NEXT_PUBLIC_UNCLIC_API_URL</code> y arranca la API (
                  <code className="text-xs">npm run dev:api</code>) para usar el portal.
                </CardDescription>
              </CardHeader>
            </Card>
          ) : null}

          {user ? (
            <>
              <Card>
                <CardHeader>
                  <CardTitle className="text-lg">Sesión activa</CardTitle>
                  <CardDescription>
                    {user.email}
                    {user.role === 'admin' ? (
                      <span className="ml-2 rounded-md bg-primary/15 px-2 py-0.5 text-xs font-medium text-primary">
                        Admin
                      </span>
                    ) : null}
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-3">
                  <p className="text-sm text-muted-foreground">
                    Elige una demo (misma sesión JWT que valida la API).
                  </p>
                  <ul className="space-y-3">
                    {PROTECTED_LINKS.map((item) => (
                      <li key={item.href}>
                        <Link
                          href={item.href}
                          className="flex items-start gap-3 rounded-lg border border-border bg-card p-4 transition-colors hover:bg-muted/40"
                        >
                          <ExternalLink className="mt-0.5 size-4 shrink-0 text-muted-foreground" aria-hidden />
                          <span>
                            <span className="font-medium">{item.title}</span>
                            <span className="mt-1 block text-sm text-muted-foreground">{item.description}</span>
                          </span>
                        </Link>
                      </li>
                    ))}
                  </ul>
                </CardContent>
              </Card>
            </>
          ) : (
            <Card>
              <CardHeader>
                <CardTitle className="text-lg">Accede con tu cuenta</CardTitle>
                <CardDescription>
                  Registro con verificación por correo (enlace de 48 h). Admin y usuario de prueba se pueden crear por
                  variables de entorno en el servidor API.
                </CardDescription>
              </CardHeader>
              <CardContent className="flex flex-wrap gap-3">
                <Button asChild>
                  <Link href={routes.portalRegistro}>Crear cuenta</Link>
                </Button>
                <Button asChild variant="outline">
                  <Link href={routes.loginNextPortal}>{nav.login}</Link>
                </Button>
              </CardContent>
            </Card>
          )}

          <p className="text-center text-sm text-muted-foreground">
            <Link href={routes.home} className="underline-offset-2 hover:underline">
              Volver al inicio
            </Link>
          </p>
        </BlockContainer>
      </main>
    </div>
  );
}

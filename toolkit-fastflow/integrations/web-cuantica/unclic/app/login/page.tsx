'use client';

import { useState, Suspense } from 'react';
import Link from 'next/link';
import { useRouter, useSearchParams } from 'next/navigation';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { UnClicLogo } from '@/components/ui/unclic-logo';
import { loginPage as copy, nav } from '@/lib/copy';
import { routes } from '@/lib/routes';
import {
  DEMO_ACCESS_KEY,
  DEMO_SCOPE_KEY,
  DEMO_BYPASS_KEY,
  DEMO_LOGIN_EMAIL_KEY,
} from '@/components/demo/demo-gate';
import { isDemoAllowlistMode, isEmailAllowedForDemos } from '@/lib/demo-access-policy';
import { getUnclicApiBase } from '@/lib/api-base';
import { isPortalAuthRequired } from '@/lib/portal-auth-policy';
import { setPortalJwt } from '@/lib/portal-session';
import { cn } from '@/lib/utils';

export type DemoScope = 'pos' | 'full';

function safeNext(raw: string | null): string {
  if (!raw || !raw.startsWith('/') || raw.startsWith('//')) return routes.portal;
  return raw;
}

function LoginPageInner() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const next = safeNext(searchParams.get('next'));

  const apiBase = getUnclicApiBase();
  const usePasswordLogin = apiBase.length > 0;
  const portalRequired = isPortalAuthRequired();

  const [tab, setTab] = useState<DemoScope>('pos');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [allowlistError, setAllowlistError] = useState(false);
  const [formError, setFormError] = useState<string | null>(null);

  const handlePasswordLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setFormError(null);
    if (!email.trim() || !password) return;
    const normalized = email.trim().toLowerCase();
    setLoading(true);
    try {
      const res = await fetch(`${apiBase}/v1/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: normalized, password }),
      });
      const data = (await res.json().catch(() => ({}))) as {
        ok?: boolean;
        error?: string;
        token?: string;
      };
      if (!res.ok) {
        if (data.error === 'email_not_verified') {
          setFormError(
            'Revisa tu correo y abre el enlace de verificación antes de entrar. Si no llega, comprueba SMTP en la API.'
          );
        } else {
          setFormError('Correo o contraseña incorrectos, o cuenta no verificada.');
        }
        return;
      }
      if (!data.token) {
        setFormError('Respuesta inválida del servidor.');
        return;
      }
      setPortalJwt(data.token);
      try {
        sessionStorage.setItem(DEMO_ACCESS_KEY, '1');
        sessionStorage.setItem(DEMO_LOGIN_EMAIL_KEY, normalized);
        sessionStorage.setItem(DEMO_SCOPE_KEY, tab);
        sessionStorage.removeItem(DEMO_BYPASS_KEY);
      } catch {
        /* ignore */
      }
      router.push(next);
    } finally {
      setLoading(false);
    }
  };

  const handleLegacyContinue = (e: React.FormEvent) => {
    e.preventDefault();
    if (!email.trim() || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.trim())) return;
    const normalized = email.trim().toLowerCase();
    if (isDemoAllowlistMode() && !isEmailAllowedForDemos(normalized)) {
      setAllowlistError(true);
      return;
    }
    setAllowlistError(false);
    setLoading(true);
    try {
      sessionStorage.setItem(DEMO_ACCESS_KEY, '1');
      sessionStorage.setItem(DEMO_LOGIN_EMAIL_KEY, normalized);
      sessionStorage.setItem(DEMO_SCOPE_KEY, tab);
      sessionStorage.removeItem(DEMO_BYPASS_KEY);
    } catch {
      // sessionStorage blocked
    }
    router.push(next === routes.portal ? routes.demo : next);
  };

  if (portalRequired && !usePasswordLogin) {
    return (
      <div className="flex min-h-screen flex-col items-center justify-center bg-background px-4">
        <Card className="w-full max-w-md">
          <CardHeader>
            <CardTitle>Configuración incompleta</CardTitle>
            <CardDescription>
              El portal requiere API y autenticación. Define{' '}
              <code className="text-xs">NEXT_PUBLIC_UNCLIC_API_URL</code> en{' '}
              <code className="text-xs">.env.local</code> y arranca{' '}
              <code className="text-xs">npm run dev:api</code>.
            </CardDescription>
          </CardHeader>
          <CardFooter>
            <Button asChild variant="outline">
              <Link href={routes.home}>{nav.backToHome}</Link>
            </Button>
          </CardFooter>
        </Card>
      </div>
    );
  }

  return (
    <div className="flex min-h-screen flex-col bg-background">
      <div
        className="pointer-events-none fixed inset-0 bg-[radial-gradient(ellipse_120%_80%_at_50%_-20%,hsl(var(--primary)/0.08),transparent),radial-gradient(ellipse_80%_50%_at_100%_50%,hsl(var(--accent)/0.06),transparent)]"
        aria-hidden
      />

      <div className="relative flex flex-1 flex-col items-center justify-center px-4 py-12 sm:py-16">
        <Link
          href={routes.home}
          className="mb-10 flex flex-col items-center gap-2 text-foreground transition-opacity hover:opacity-80"
          aria-label={nav.home}
        >
          <UnClicLogo size={44} className="drop-shadow-sm" />
        </Link>

        <Card className="w-full max-w-[420px] border-border bg-card shadow-md">
          <CardHeader className="space-y-1 text-center">
            <CardTitle className="text-2xl font-semibold tracking-tight">
              {usePasswordLogin ? copy.titlePortal : copy.title}
            </CardTitle>
            <CardDescription className="text-muted-foreground">
              {usePasswordLogin ? copy.subtitlePortal : copy.emailLabel}
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="flex rounded-lg border border-input bg-muted/50 p-1">
              <button
                type="button"
                onClick={() => setTab('pos')}
                className={cn(
                  'flex-1 rounded-md py-2.5 text-center text-sm font-medium transition-colors',
                  tab === 'pos'
                    ? 'bg-background text-foreground shadow-sm'
                    : 'text-muted-foreground hover:text-foreground'
                )}
              >
                <span className="block">{copy.tabPos}</span>
                <span className="mt-0.5 block text-[10px] font-normal uppercase tracking-wide text-muted-foreground">
                  {copy.tabPosHint}
                </span>
              </button>
              <button
                type="button"
                onClick={() => setTab('full')}
                className={cn(
                  'flex-1 rounded-md py-2.5 text-center text-sm font-medium transition-colors',
                  tab === 'full'
                    ? 'bg-background text-foreground shadow-sm'
                    : 'text-muted-foreground hover:text-foreground'
                )}
              >
                <span className="block">{copy.tabFull}</span>
                <span className="mt-0.5 block text-[10px] font-normal uppercase tracking-wide text-muted-foreground">
                  {copy.tabFullHint}
                </span>
              </button>
            </div>

            <form
              onSubmit={usePasswordLogin ? handlePasswordLogin : handleLegacyContinue}
              className="space-y-4"
            >
              <div className="space-y-2">
                <Label htmlFor="login-email" className="sr-only">
                  {copy.emailLabel}
                </Label>
                <Input
                  id="login-email"
                  type="email"
                  autoComplete="email"
                  required
                  placeholder={copy.emailPlaceholder}
                  value={email}
                  onChange={(e) => {
                    setAllowlistError(false);
                    setFormError(null);
                    setEmail(e.target.value);
                  }}
                  disabled={loading}
                  className="h-11"
                />
              </div>
              {usePasswordLogin ? (
                <div className="space-y-2">
                  <Label htmlFor="login-password" className="sr-only">
                    {copy.passwordLabel}
                  </Label>
                  <Input
                    id="login-password"
                    type="password"
                    autoComplete="current-password"
                    required
                    placeholder={copy.passwordPlaceholder}
                    value={password}
                    onChange={(e) => {
                      setFormError(null);
                      setPassword(e.target.value);
                    }}
                    disabled={loading}
                    className="h-11"
                  />
                </div>
              ) : null}
              {allowlistError ? (
                <p className="text-sm text-destructive" role="alert">
                  {copy.allowlistDenied}
                </p>
              ) : null}
              {formError ? (
                <p className="text-sm text-destructive" role="alert">
                  {formError}
                </p>
              ) : null}
              <Button type="submit" disabled={loading} className="h-11 w-full" size="lg">
                {loading ? '…' : usePasswordLogin ? copy.signIn : copy.continue}
              </Button>
            </form>

            {usePasswordLogin ? (
              <p className="text-center text-sm text-muted-foreground">
                {copy.noAccount}{' '}
                <Link
                  href={routes.portalRegistro}
                  className="font-semibold text-foreground underline-offset-2 hover:underline"
                >
                  {copy.createAccount}
                </Link>
              </p>
            ) : null}
          </CardContent>
          <CardFooter className="flex flex-col items-center gap-1 border-t border-border pt-6">
            <p className="text-center text-xs leading-relaxed text-muted-foreground">
              {copy.termsLine}{' '}
              <Link
                href={routes.legalDemosTerminos}
                className="font-medium text-foreground underline underline-offset-2 hover:no-underline"
              >
                {copy.termsLink}
              </Link>
              .
            </p>
          </CardFooter>
        </Card>

        <p className="mt-8 text-center text-sm text-muted-foreground">
          {copy.firstTime}{' '}
          <Link
            href={routes.publicSignup}
            className="font-semibold text-foreground underline-offset-2 hover:underline"
          >
            {copy.firstTimeLink}
          </Link>
        </p>
        {usePasswordLogin ? (
          <p className="mt-2 text-center text-sm text-muted-foreground">
            <Link href={routes.portal} className="underline-offset-2 hover:underline">
              {copy.portalHubLink}
            </Link>
          </p>
        ) : null}
      </div>

      <footer
        className="relative border-t border-border/80 bg-muted/40 py-6"
        role="contentinfo"
      >
        <nav
          className="flex flex-wrap items-center justify-center gap-x-8 gap-y-2 px-4 text-sm text-muted-foreground"
          aria-label="Pie de login"
        >
          <Link href={routes.empresaWhy} className="transition-colors hover:text-foreground">
            {copy.footerAbout}
          </Link>
          <Link href={routes.contactForm} className="transition-colors hover:text-foreground">
            {copy.footerContact}
          </Link>
          <Link href={routes.legalDemosDatos} className="transition-colors hover:text-foreground">
            {copy.footerPrivacy}
          </Link>
          <Link href={routes.legalDemosTerminos} className="transition-colors hover:text-foreground">
            {copy.footerTerms}
          </Link>
        </nav>
      </footer>
    </div>
  );
}

export default function LoginPage() {
  return (
    <Suspense
      fallback={
        <div className="flex min-h-screen items-center justify-center">
          <p className="text-muted-foreground">Cargando…</p>
        </div>
      }
    >
      <LoginPageInner />
    </Suspense>
  );
}

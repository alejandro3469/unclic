'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
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
import {
  DEMO_ACCESS_KEY,
  DEMO_SCOPE_KEY,
  DEMO_BYPASS_KEY,
  DEMO_LOGIN_EMAIL_KEY,
} from '@/components/demo/demo-gate';
import { isDemoAllowlistMode, isEmailAllowedForDemos } from '@/lib/demo-access-policy';
import { cn } from '@/lib/utils';

export type DemoScope = 'pos' | 'full';

export default function LoginPage() {
  const router = useRouter();
  const [tab, setTab] = useState<DemoScope>('pos');
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [allowlistError, setAllowlistError] = useState(false);

  const handleContinue = (e: React.FormEvent) => {
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
    router.push('/demo');
  };

  return (
    <div className="flex min-h-screen flex-col bg-background">
      {/* Fondo suave alineado al tema (Nord / enterprise) */}
      <div
        className="pointer-events-none fixed inset-0 bg-[radial-gradient(ellipse_120%_80%_at_50%_-20%,hsl(var(--primary)/0.08),transparent),radial-gradient(ellipse_80%_50%_at_100%_50%,hsl(var(--accent)/0.06),transparent)]"
        aria-hidden
      />

      <div className="relative flex flex-1 flex-col items-center justify-center px-4 py-12 sm:py-16">
        <Link
          href="/"
          className="mb-10 flex flex-col items-center gap-2 text-foreground transition-opacity hover:opacity-80"
          aria-label={nav.home}
        >
          <UnClicLogo size={44} className="drop-shadow-sm" />
        </Link>

        <Card className="w-full max-w-[420px] border-border bg-card shadow-md">
          <CardHeader className="space-y-1 text-center">
            <CardTitle className="text-2xl font-semibold tracking-tight">
              {copy.title}
            </CardTitle>
            <CardDescription className="text-muted-foreground">
              {copy.emailLabel}
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-6">
            {/* Toggle tipo Shadcn: dos opciones (POS / Full) */}
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

            <form onSubmit={handleContinue} className="space-y-4">
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
                    setEmail(e.target.value);
                  }}
                  disabled={loading}
                  className="h-11"
                />
              </div>
              {allowlistError ? (
                <p className="text-sm text-destructive" role="alert">
                  {copy.allowlistDenied}
                </p>
              ) : null}
              <Button
                type="submit"
                disabled={loading}
                className="h-11 w-full"
                size="lg"
              >
                {loading ? '…' : copy.continue}
              </Button>
            </form>
          </CardContent>
          <CardFooter className="flex flex-col items-center gap-1 border-t border-border pt-6">
            <p className="text-center text-xs leading-relaxed text-muted-foreground">
              {copy.termsLine}{' '}
              <Link
                href="/legal/acceso-demos#terminos"
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
            href="/demo/access"
            className="font-semibold text-foreground underline-offset-2 hover:underline"
          >
            {copy.firstTimeLink}
          </Link>
        </p>
      </div>

      <footer
        className="relative border-t border-border/80 bg-muted/40 py-6"
        role="contentinfo"
      >
        <nav
          className="flex flex-wrap items-center justify-center gap-x-8 gap-y-2 px-4 text-sm text-muted-foreground"
          aria-label="Pie de login"
        >
          <Link
            href="/empresa#why"
            className="transition-colors hover:text-foreground"
          >
            {copy.footerAbout}
          </Link>
          <Link
            href="/contacto"
            className="transition-colors hover:text-foreground"
          >
            {copy.footerContact}
          </Link>
          <Link
            href="/legal/acceso-demos#datos"
            className="transition-colors hover:text-foreground"
          >
            {copy.footerPrivacy}
          </Link>
          <Link
            href="/legal/acceso-demos#terminos"
            className="transition-colors hover:text-foreground"
          >
            {copy.footerTerms}
          </Link>
        </nav>
      </footer>
    </div>
  );
}

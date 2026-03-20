'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { ArrowLeft } from 'lucide-react';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { UnClicLogo } from '@/components/ui/unclic-logo';
import { BlockContainer } from '@/components/blocks';
import { demoAccess as copy, nav } from '@/lib/copy';
import {
  DEMO_ACCESS_KEY,
  DEMO_SCOPE_KEY,
  DEMO_BYPASS_KEY,
  DEMO_LOGIN_EMAIL_KEY,
} from '@/components/demo/demo-gate';
import { isDemoAllowlistMode, isEmailAllowedForDemos } from '@/lib/demo-access-policy';
import { cn } from '@/lib/utils';

const FORMSPREE_BASE = 'https://formspree.io/f';
const LEGAL = '/legal/acceso-demos';

export default function DemoAccessPage() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [company, setCompany] = useState('');
  const [message, setMessage] = useState('');
  const [acceptTerms, setAcceptTerms] = useState(false);
  const [acceptData, setAcceptData] = useState(false);
  const [status, setStatus] = useState<'idle' | 'sending' | 'ok' | 'error' | 'denied'>('idle');
  const formId = process.env.NEXT_PUBLIC_DEMO_ACCESS_FORM_ID ?? '';

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!email.trim() || !acceptTerms || !acceptData) return;

    const normalized = email.trim().toLowerCase();
    if (isDemoAllowlistMode() && !isEmailAllowedForDemos(normalized)) {
      setStatus('denied');
      return;
    }

    setStatus('sending');

    try {
      if (formId) {
        const res = await fetch(FORMSPREE_BASE + '/' + formId, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            email: email.trim(),
            name: name.trim() || undefined,
            company: company.trim() || undefined,
            message: message.trim() || undefined,
            terms_accepted: true,
            data_policy_accepted: true,
            _subject: 'UnClic — solicitud acceso demos',
          }),
        });
        if (!res.ok) throw new Error('Formspree error');
      }
      sessionStorage.setItem(DEMO_ACCESS_KEY, '1');
      sessionStorage.setItem(DEMO_SCOPE_KEY, 'full');
      sessionStorage.setItem(DEMO_LOGIN_EMAIL_KEY, normalized);
      sessionStorage.removeItem(DEMO_BYPASS_KEY);
      setStatus('ok');
      router.push('/demo');
    } catch {
      setStatus('error');
    }
  };

  const canSubmit = email.trim().length > 0 && acceptTerms && acceptData;

  return (
    <div className="flex min-h-screen flex-col bg-background">
      <header className="border-b border-border/80 bg-muted/30">
        <BlockContainer className="flex h-14 items-center justify-between">
          <Button variant="ghost" size="sm" asChild>
            <Link href="/" className="gap-2" aria-label={nav.home}>
              <ArrowLeft className="size-4" aria-hidden />
              {nav.backToHome}
            </Link>
          </Button>
        </BlockContainer>
      </header>

      <main className="flex-1 px-4 py-10 md:py-16">
        <BlockContainer className="mx-auto w-full max-w-xl">
          <Card className="overflow-hidden border-border shadow-md">
            <CardHeader>
              <p className="flex items-center gap-1.5 text-xs font-semibold uppercase tracking-[0.2em] text-muted-foreground">
                <UnClicLogo size={14} className="shrink-0" />
                <span>· demos</span>
              </p>
              <CardTitle className="mt-3 text-2xl md:text-3xl">
                {copy.title}
              </CardTitle>
              <CardDescription className="text-sm leading-relaxed md:text-[15px]">
                {copy.lead}
              </CardDescription>
            </CardHeader>
            <CardContent>
              {status === 'ok' ? (
                <p className="text-sm font-medium text-primary">
                  {copy.successMessage}
                </p>
              ) : (
                <form onSubmit={handleSubmit} className="space-y-6">
                  <div className="space-y-2">
                    <Label htmlFor="demo-email">
                      {copy.emailLabel}{' '}
                      <span className="font-normal text-destructive">*</span>
                    </Label>
                    <Input
                      id="demo-email"
                      type="email"
                      required
                      autoComplete="email"
                      placeholder={copy.emailPlaceholder}
                      value={email}
                      onChange={(e) => {
                        if (status === 'denied') setStatus('idle');
                        setEmail(e.target.value);
                      }}
                      disabled={status === 'sending'}
                      className="h-11"
                    />
                  </div>

                  <div className="space-y-4 rounded-lg border border-border bg-muted/40 p-4">
                    <p className="text-xs font-medium uppercase tracking-wider text-muted-foreground">
                      {copy.optionalHint}
                    </p>
                    <div className="grid gap-4 sm:grid-cols-2">
                      <div className="space-y-2">
                        <Label htmlFor="demo-name" className="text-muted-foreground">
                          {copy.nameLabel}
                        </Label>
                        <Input
                          id="demo-name"
                          type="text"
                          autoComplete="name"
                          placeholder={copy.namePlaceholder}
                          value={name}
                          onChange={(e) => setName(e.target.value)}
                          disabled={status === 'sending'}
                        />
                      </div>
                      <div className="space-y-2">
                        <Label htmlFor="demo-company" className="text-muted-foreground">
                          {copy.companyLabel}
                        </Label>
                        <Input
                          id="demo-company"
                          type="text"
                          autoComplete="organization"
                          placeholder={copy.companyPlaceholder}
                          value={company}
                          onChange={(e) => setCompany(e.target.value)}
                          disabled={status === 'sending'}
                        />
                      </div>
                    </div>
                    <div className="space-y-2">
                      <Label htmlFor="demo-message" className="text-muted-foreground">
                        {copy.messageLabel}
                      </Label>
                      <Textarea
                        id="demo-message"
                        rows={3}
                        placeholder={copy.messagePlaceholder}
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                        disabled={status === 'sending'}
                        className="resize-y"
                      />
                    </div>
                  </div>

                  <div className="space-y-3 border-t border-border pt-6">
                    <p className="text-xs font-medium text-muted-foreground">
                      Debes leer y aceptar antes de continuar:
                    </p>
                    <label className="flex cursor-pointer gap-3 text-sm leading-snug">
                      <input
                        type="checkbox"
                        checked={acceptTerms}
                        onChange={(e) => setAcceptTerms(e.target.checked)}
                        className={cn(
                          'mt-0.5 size-4 shrink-0 rounded border border-input bg-background accent-primary'
                        )}
                        disabled={status === 'sending'}
                      />
                      <span>
                        {copy.policyTermsLabel}{' '}
                        <Link
                          href={`${LEGAL}#terminos`}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="font-medium text-primary underline-offset-2 hover:underline"
                        >
                          {copy.policyTermsLink}
                        </Link>
                      </span>
                    </label>
                    <label className="flex cursor-pointer gap-3 text-sm leading-snug">
                      <input
                        type="checkbox"
                        checked={acceptData}
                        onChange={(e) => setAcceptData(e.target.checked)}
                        className={cn(
                          'mt-0.5 size-4 shrink-0 rounded border border-input bg-background accent-primary'
                        )}
                        disabled={status === 'sending'}
                      />
                      <span>
                        {copy.policyDataLabel}{' '}
                        <Link
                          href={`${LEGAL}#datos`}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="font-medium text-primary underline-offset-2 hover:underline"
                        >
                          {copy.policyDataLink}
                        </Link>
                      </span>
                    </label>
                  </div>

                  {status === 'error' && (
                    <p className="text-sm text-destructive">{copy.errorMessage}</p>
                  )}
                  {status === 'denied' && (
                    <p className="text-sm text-destructive" role="alert">
                      {copy.allowlistDenied}
                    </p>
                  )}

                  <Button
                    type="submit"
                    className="h-11 w-full"
                    size="lg"
                    disabled={status === 'sending' || !canSubmit}
                  >
                    {status === 'sending' ? copy.submittingLabel : copy.submitLabel}
                  </Button>

                  <p className="text-[11px] leading-relaxed text-muted-foreground">
                    {copy.recaptchaNote}
                  </p>
                  <p className="text-xs font-medium text-foreground/80">
                    {copy.privacyCommitment}
                  </p>
                </form>
              )}
            </CardContent>
          </Card>
        </BlockContainer>
      </main>
    </div>
  );
}

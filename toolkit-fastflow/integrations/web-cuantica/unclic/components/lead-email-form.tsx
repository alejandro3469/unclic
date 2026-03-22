'use client';

import { useCallback, useState } from 'react';
import { leadEmailForm } from '@/lib/copy-lead-email';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { cn } from '@/lib/utils';

type Status = 'idle' | 'loading' | 'success' | 'error' | 'misconfigured';

export function LeadEmailForm({ className, source }: { className?: string; source?: string }) {
  const [email, setEmail] = useState('');
  const [status, setStatus] = useState<Status>('idle');
  const [message, setMessage] = useState<string | null>(null);

  const onSubmit = useCallback(
    async (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      setStatus('loading');
      setMessage(null);

      const form = e.currentTarget;
      const website = (form.elements.namedItem('website') as HTMLInputElement)?.value ?? '';

      const apiBase =
        typeof process.env.NEXT_PUBLIC_UNCLIC_API_URL === 'string'
          ? process.env.NEXT_PUBLIC_UNCLIC_API_URL.replace(/\/$/, '')
          : '';
      // `output: 'export'`: en producción no hay `/api/*`; define NEXT_PUBLIC_UNCLIC_API_URL.
      const endpoint = apiBase ? `${apiBase}/v1/leads/email` : '/api/lead-email';

      try {
        const res = await fetch(endpoint, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            email,
            website,
            source: source ?? (typeof window !== 'undefined' ? window.location.pathname : undefined),
          }),
        });

        if (res.status === 503) {
          setStatus('misconfigured');
          setMessage(leadEmailForm.errorConfig);
          return;
        }

        const data = (await res.json()) as { ok?: boolean };

        if (!res.ok || !data.ok) {
          setStatus('error');
          setMessage(leadEmailForm.errorGeneric);
          return;
        }

        setStatus('success');
        setMessage(leadEmailForm.success);
        setEmail('');
      } catch {
        setStatus('error');
        setMessage(leadEmailForm.errorGeneric);
      }
    },
    [email, source]
  );

  if (status === 'success') {
    return (
      <div
        className={cn(
          'rounded-xl border border-primary/20 bg-primary/5 px-6 py-5 text-center text-sm text-foreground',
          className
        )}
        role="status"
      >
        <p className="font-medium text-primary">¡Recibido!</p>
        <p className="mt-2 text-muted-foreground">{message}</p>
      </div>
    );
  }

  return (
    <form
      onSubmit={onSubmit}
      className={cn(
        'relative mx-auto w-full max-w-md space-y-4 rounded-xl border bg-card p-6 shadow-sm',
        className
      )}
      noValidate
    >
      <div>
        <h3 className="text-lg font-semibold tracking-tight">{leadEmailForm.title}</h3>
        <p className="mt-1 text-sm text-muted-foreground">{leadEmailForm.description}</p>
      </div>

      <div className="space-y-2">
        <Label htmlFor="lead-email">{leadEmailForm.label}</Label>
        <Input
          id="lead-email"
          name="email"
          type="email"
          autoComplete="email"
          inputMode="email"
          required
          placeholder={leadEmailForm.placeholder}
          value={email}
          onChange={(ev) => setEmail(ev.target.value)}
          disabled={status === 'loading'}
          aria-invalid={status === 'error' || status === 'misconfigured'}
        />
      </div>

      {/* Honeypot — no quitar name="website"; oculto a usuarios y lectores de pantalla */}
      <div className="absolute -left-[9999px] h-0 w-0 overflow-hidden" aria-hidden="true">
        <label htmlFor="lead-website">Website</label>
        <input id="lead-website" name="website" type="text" tabIndex={-1} autoComplete="off" />
      </div>

      <p className="text-xs text-muted-foreground">{leadEmailForm.privacyNote}</p>

      {(status === 'error' || status === 'misconfigured') && message ? (
        <p className="text-sm text-destructive" role="alert">
          {message}
        </p>
      ) : null}

      <Button type="submit" className="w-full" disabled={status === 'loading'}>
        {status === 'loading' ? leadEmailForm.submitting : leadEmailForm.submit}
      </Button>
    </form>
  );
}

'use client';

import * as React from 'react';
import { cn } from '@/lib/utils';

/**
 * Primitivos mínimos estilo shadcn/ui Field — usados por bloques tipo Hero154 (formulario email).
 * Si más adelante instalas `npx shadcn add field` y prefieres el oficial, puedes sustituir este archivo.
 */
function Field({
  className,
  ...props
}: React.ComponentProps<'div'> & { 'data-invalid'?: boolean }) {
  return (
    <div
      data-slot="field"
      className={cn('space-y-2', className)}
      {...props}
    />
  );
}

function FieldLabel({
  className,
  ...props
}: React.ComponentProps<'label'>) {
  return (
    <label
      data-slot="field-label"
      className={cn(
        'text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70',
        className
      )}
      {...props}
    />
  );
}

function FieldError({
  className,
  errors,
  ...props
}: React.ComponentProps<'p'> & {
  errors?: Array<{ message?: string } | undefined>;
}) {
  const err = errors?.find((e) => e?.message);
  if (!err?.message) return null;
  return (
    <p
      role="alert"
      data-slot="field-error"
      className={cn('text-sm font-medium text-destructive', className)}
      {...props}
    >
      {err.message}
    </p>
  );
}

export { Field, FieldLabel, FieldError };

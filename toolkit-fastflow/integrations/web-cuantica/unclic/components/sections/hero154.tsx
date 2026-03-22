'use client';

import { zodResolver } from '@hookform/resolvers/zod';
import { Clock, Send } from 'lucide-react';
import { Controller, useForm } from 'react-hook-form';
import { z } from 'zod';

import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Button } from '@/components/ui/button';
import { Field, FieldError, FieldLabel } from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { hero } from '@/lib/copy';
import { cn } from '@/lib/utils';

/** Dos líneas tipo “badges” bajo el form: primeros pilares de `hero.heroFeatures`. */
const trustFromHero = () => {
  const a = hero.heroFeatures[0]?.title ?? '';
  const b = hero.heroFeatures[1]?.title ?? '';
  return { line1: a, line2: b };
};

const formSchema = z.object({
  email: z.string().email('Correo no válido'),
});

type FormValues = z.infer<typeof formSchema>;

function HeroForm() {
  const form = useForm<FormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: { email: '' },
  });

  function onSubmit(values: FormValues) {
    // Conecta aquí a Formspree, API o `/contacto?email=…`
    if (typeof window !== 'undefined') {
      console.log('[hero154 lead]', values);
    }
  }

  return (
    <form onSubmit={form.handleSubmit(onSubmit)} className="w-full">
      <div className="flex w-full flex-col items-start justify-center gap-2 sm:flex-row">
        <Controller
          control={form.control}
          name="email"
          render={({ field, fieldState }) => (
            <Field className="w-full" data-invalid={fieldState.invalid}>
              <FieldLabel htmlFor={field.name} className="sr-only">
                Email
              </FieldLabel>
              <Input
                {...field}
                type="email"
                id={field.name}
                aria-invalid={fieldState.invalid}
                placeholder={hero.leadEmailPlaceholder}
                className="h-12 w-full rounded-lg px-3 py-2 text-center text-sm leading-loose"
              />
              {fieldState.invalid ? (
                <FieldError errors={[fieldState.error]} />
              ) : null}
            </Field>
          )}
        />
        <div className="w-full shrink-0 sm:w-fit">
          <Button
            type="submit"
            className="h-fit w-full rounded-lg px-4 py-2.5 text-sm font-medium leading-loose sm:w-fit"
          >
            {hero.ctaPrimary}
          </Button>
        </div>
      </div>
    </form>
  );
}

interface Hero154Props {
  className?: string;
}

/**
 * Hero estilo Shadcn Blocks Hero154 — lead email + mockups.
 * Copy principal: `hero` en `lib/copy.ts` (mismo que el hero Hero70). Alternativa: `components/hero.tsx`.
 */
export function Hero154({ className }: Hero154Props) {
  const { line1, line2 } = trustFromHero();

  return (
    <section
      className={cn(
        'border-b border-b-primary/50 bg-background pt-12 md:pt-20',
        className
      )}
    >
      <div className="container">
        <div className="flex w-full flex-col items-center justify-center gap-16">
          <div className="flex flex-col justify-center gap-12">
            <div className="flex w-full max-w-[32.5rem] flex-col gap-6">
              {hero.heroEyebrow ? (
                <p className="text-center text-xs font-semibold uppercase tracking-[0.2em] text-muted-foreground">
                  {hero.heroEyebrow}
                </p>
              ) : null}
              <h1
                id="hero-headline"
                className="text-center text-4xl font-medium tracking-tighter text-foreground md:text-5xl"
              >
                {hero.headline}
              </h1>
              <p className="text-center text-base text-muted-foreground">
                {hero.subtitle}
              </p>
              <div className="mx-auto w-full max-w-[25.625rem]">
                <HeroForm />
              </div>
            </div>
            <div className="flex items-center justify-center gap-8">
              <div className="flex items-center gap-2">
                <Clock className="h-4 w-4 stroke-foreground" aria-hidden />
                <div className="text-xs font-medium text-muted-foreground">
                  {line1}
                </div>
              </div>
              <div className="flex items-center gap-2">
                <Send className="h-4 w-4 stroke-foreground" aria-hidden />
                <div className="text-xs font-medium text-muted-foreground">
                  {line2}
                </div>
              </div>
            </div>
          </div>
          <div className="w-full">
            <div className="relative mx-auto w-full max-w-[62.5rem] overflow-hidden">
              <AspectRatio ratio={2.100840336 / 1}>
                <div className="w-full">
                  <div className="absolute top-0 left-0 w-[94.2%] overflow-hidden">
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/mockups/desktop-1.png"
                      alt=""
                      className="relative z-20 h-full w-full"
                    />
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/placeholder-1.svg"
                      alt=""
                      className="absolute top-[3%] left-[2%] z-10 w-full object-contain"
                    />
                  </div>
                  <div className="absolute right-0 -bottom-[35%] z-20 w-[23%] overflow-hidden">
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/mockups/phone-3.png"
                      alt=""
                      className="relative z-20 h-full w-full"
                    />
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/placeholder-dark-7-tall.svg"
                      alt=""
                      className="absolute top-0 left-1/2 z-10 w-full -translate-x-1/2 rounded-[15px] md:rounded-[30px]"
                    />
                  </div>
                </div>
              </AspectRatio>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

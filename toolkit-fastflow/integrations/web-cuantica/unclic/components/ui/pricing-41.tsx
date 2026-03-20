'use client';

import { Check } from 'lucide-react';
import { useState } from 'react';
import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Switch } from '@/components/ui/switch';
import { cn } from '@/lib/utils';

export interface Plan {
  name: string;
  monthlyPrice: string;
  yearlyPrice: string;
  description?: string;
  features: string[];
  highlighted?: boolean;
  ctaText?: string;
  ctaHref?: string;
}

interface Pricing41Props {
  heading?: string;
  description?: string;
  plans?: Plan[];
  className?: string;
  id?: string;
  headingId?: string;
}

const Pricing41 = ({
  heading = 'Pricing',
  description = '',
  plans = [],
  className,
  id,
  headingId = 'pricing-heading',
}: Pricing41Props) => {
  const [isAnnual, setIsAnnual] = useState(true);

  return (
    <section
      id={id}
      className={cn('py-28 lg:py-32', className)}
      aria-labelledby={headingId}
    >
      <BlockContainer className="max-w-5xl">
        <div className="space-y-4 text-center">
          <h2
            id={headingId}
            className="text-2xl tracking-tight md:text-4xl lg:text-5xl"
          >
            {heading}
          </h2>
          {description ? (
            <p className="mx-auto max-w-xl leading-snug text-balance text-muted-foreground">
              {description}
            </p>
          ) : null}
        </div>

        <div className="mt-8 grid items-start gap-5 text-start md:mt-12 md:grid-cols-3 lg:mt-20">
          {plans.map((plan) => (
            <Card
              key={plan.name}
              className={cn(
                plan.highlighted && 'origin-top outline-4 outline-primary'
              )}
            >
              <CardContent className="flex flex-col gap-7 px-6 py-5">
                <div className="space-y-2">
                  <h3 className="font-semibold text-foreground">{plan.name}</h3>
                  <div className="space-y-1">
                    <div className="text-lg font-medium text-muted-foreground">
                      {isAnnual ? plan.yearlyPrice : plan.monthlyPrice}
                      {plan.monthlyPrice !== 'Gratuito' &&
                        plan.monthlyPrice !== '$0' && (
                          <span className="text-muted-foreground">
                            {' '}
                            / {isAnnual ? 'año' : 'mes'}
                          </span>
                        )}
                    </div>
                  </div>
                </div>

                {plan.monthlyPrice !== 'Gratuito' && plan.monthlyPrice !== '$0' ? (
                  <div className="flex items-center gap-2">
                    <Switch
                      checked={isAnnual}
                      onCheckedChange={() => setIsAnnual(!isAnnual)}
                      aria-label="Facturación anual"
                    />
                    <span className="text-sm font-medium">
                      Facturación anual
                    </span>
                  </div>
                ) : plan.description ? (
                  <span className="text-sm text-muted-foreground">
                    {plan.description}
                  </span>
                ) : null}

                <div className="space-y-3">
                  {plan.features.map((feature) => (
                    <div
                      key={feature}
                      className="flex items-center gap-1.5 text-muted-foreground"
                    >
                      <Check className="size-5 shrink-0" aria-hidden />
                      <span className="text-sm">{feature}</span>
                    </div>
                  ))}
                </div>

                <Button
                  className="w-fit"
                  variant={plan.highlighted ? 'default' : 'outline'}
                  asChild
                >
                  <Link href={plan.ctaHref || '/contacto'}>
                    {plan.ctaText || 'Solicitar cotización'}
                  </Link>
                </Button>
              </CardContent>
            </Card>
          ))}
        </div>
      </BlockContainer>
    </section>
  );
};

export { Pricing41 };

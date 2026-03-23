'use client';

import { useState } from 'react';
import Link from 'next/link';
import {
  pricingHero,
  pricingPlanCards,
  pricingCategories,
} from '@/lib/copy-pricing';
import { BlockContainer } from '@/components/blocks';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Switch } from '@/components/ui/switch';
import { cn } from '@/lib/utils';
import { routes } from '@/lib/routes';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { LG } from '@/lib/icons8-liquid-glass';

interface Plan {
  name: string;
  monthlyPrice: string;
  yearlyPrice: string;
  description?: string;
  features: string[];
  highlighted?: boolean;
  ctaText?: string;
  ctaHref?: string;
}

function buildPlans(): Plan[] {
  const [pipeline, deploy] = pricingCategories;
  return [
    {
      name: pricingPlanCards[0].name,
      monthlyPrice: 'Desde 150 USD',
      yearlyPrice: 'Cotización',
      description: pricingPlanCards[0].description,
      features: [...pipeline.features],
      highlighted: false,
      ctaText: pricingPlanCards[0].cta,
      ctaHref: routes.publicSignup,
    },
    {
      name: pricingPlanCards[1].name,
      monthlyPrice: 'Desde 350 USD',
      yearlyPrice: 'Cotización',
      description: pricingPlanCards[1].description,
      features: [...deploy.features],
      highlighted: true,
      ctaText: pricingPlanCards[1].cta,
      ctaHref: routes.publicSignup,
    },
    {
      name: pricingPlanCards[2].name,
      monthlyPrice: 'Gratuito',
      yearlyPrice: 'Gratuito',
      description: pricingPlanCards[2].description,
      features: [
        'Acceso a Jenkins, registry y app de ejemplo',
        'Sin compromiso',
      ],
      highlighted: false,
      ctaText: pricingPlanCards[2].cta,
      ctaHref: routes.solucionesDemos,
    },
  ];
}

/** Precios — Card, Button, Switch (shadcn/ui únicamente). */
export function PricingSection() {
  const [isAnnual, setIsAnnual] = useState(true);
  const plans = buildPlans();

  return (
    <section
      id="pricing"
      className="py-28 lg:py-32"
      aria-labelledby="pricing-heading"
    >
      <BlockContainer className="max-w-5xl">
        <div className="space-y-4 text-center">
          <h2 id="pricing-heading" className="text-type-section-title mx-auto max-w-3xl text-balance">
            {pricingHero.title}
          </h2>
          <p className="text-type-lead mx-auto max-w-xl text-balance">
            {pricingHero.subtitle} {pricingHero.currencyNote}
          </p>
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
                    <span className="text-sm font-medium">Facturación anual</span>
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
                      <LiquidGlassIcon slug={LG.checked} size={20} alt="" className="shrink-0" />
                      <span className="text-sm">{feature}</span>
                    </div>
                  ))}
                </div>

                <Button className="w-fit" variant="outline" asChild>
                  <Link href={plan.ctaHref || routes.publicSignup}>
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
}

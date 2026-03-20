'use client';

import {
  pricingHero,
  pricingPlanCards,
  pricingCategories,
} from '@/lib/copy-pricing';
import { Pricing41, type Plan } from '@/components/ui/pricing-41';

function buildPlans(): Plan[] {
  const [pipeline, deploy, demosSites] = pricingCategories;
  return [
    {
      name: pricingPlanCards[0].name,
      monthlyPrice: 'Desde 150 USD',
      yearlyPrice: 'Cotización',
      description: pricingPlanCards[0].description,
      features: [...pipeline.features],
      highlighted: false,
      ctaText: pricingPlanCards[0].cta,
      ctaHref: '/contacto',
    },
    {
      name: pricingPlanCards[1].name,
      monthlyPrice: 'Desde 350 USD',
      yearlyPrice: 'Cotización',
      description: pricingPlanCards[1].description,
      features: [...deploy.features],
      highlighted: true,
      ctaText: pricingPlanCards[1].cta,
      ctaHref: '/contacto',
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
      ctaHref: '/soluciones#demos',
    },
  ];
}

export function PricingSection() {
  const plans = buildPlans();

  return (
    <Pricing41
      id="pricing"
      headingId="pricing-heading"
      heading={pricingHero.title}
      description={`${pricingHero.subtitle} ${pricingHero.currencyNote}`}
      plans={plans}
    />
  );
}

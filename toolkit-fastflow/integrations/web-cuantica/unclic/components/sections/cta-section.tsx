'use client';

import { BlockContainer } from '@/components/blocks';
import { Cta1 } from '@/components/cta1';
import { LoginCardWithBorderBeam } from '@/components/ui/login-card-with-border-beam';
import { LeadEmailForm } from '@/components/lead-email-form';
import { cta } from '@/lib/copy';
import { HERO_IMAGE_PLACEHOLDER } from '@/lib/placeholders';
import { cn } from '@/lib/utils';

/** CTA usando bloque Shadcn Blocks Cta1 con nuestro copy. Estilo Sequoia: limpio, enterprise. */
export function CtaSection() {
  return (
    <section
      id="contacto"
      className={cn('py-24 md:py-32')}
      aria-labelledby="cta-heading"
    >
      <Cta1
        headingId="cta-heading"
        content={{
          headlineAlt: cta.headlineAlt,
          sectionTitle: cta.sectionTitle,
          onePartner: cta.onePartner,
          sectionDescription: cta.sectionDescription,
          ctaPrimary: cta.ctaPrimary,
          ctaSecondary: cta.ctaSecondary,
          ctaPrimaryHref: '/demo/access',
          ctaSecondaryHref: '/contacto',
          imageSrc: HERO_IMAGE_PLACEHOLDER,
          imageAlt: '',
        }}
      />
      <BlockContainer className="mt-12 flex flex-col items-center gap-12">
        <LeadEmailForm />
        <LoginCardWithBorderBeam />
      </BlockContainer>
    </section>
  );
}

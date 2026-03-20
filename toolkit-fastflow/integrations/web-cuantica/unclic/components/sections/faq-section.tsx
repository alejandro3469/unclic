'use client';

import { Faq1 } from '@/components/faq1';
import { faq } from '@/lib/copy';
import { pricingFaq } from '@/lib/copy-pricing';

/** FAQ con bloque Shadcn Blocks Faq1 y nuestro copy (faq.sectionTitle, pricingFaq). */
export function FaqSection() {
  const items = pricingFaq.map((item, index) => ({
    id: `faq-${index}`,
    question: item.q,
    answer: item.a,
  }));

  return (
    <div id="faq" className="scroll-mt-20" aria-labelledby="faq-heading">
      <Faq1 heading={faq.sectionTitle} items={items} />
    </div>
  );
}

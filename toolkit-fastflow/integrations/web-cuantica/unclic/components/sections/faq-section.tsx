'use client';

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from '@/components/ui/accordion';
import { BlockContainer } from '@/components/blocks';
import { faq } from '@/lib/copy';
import { pricingFaq } from '@/lib/copy-pricing';

/**
 * FAQ — Accordion shadcn/ui + copy centralizado.
 * https://ui.shadcn.com/docs/components/accordion
 */
export function FaqSection() {
  const items = pricingFaq.map((item, index) => ({
    id: `faq-${index}`,
    question: item.q,
    answer: item.a,
  }));

  return (
    <section
      id="faq"
      className="scroll-mt-20 py-24 md:py-32"
      aria-labelledby="faq-heading"
    >
      <BlockContainer className="max-w-3xl">
        <h2 id="faq-heading" className="text-type-section-title mb-3">
          {faq.sectionTitle}
        </h2>
        <p className="mb-8 max-w-2xl text-sm leading-relaxed text-muted-foreground md:mb-11 md:text-base">
          {faq.sectionDescription}
        </p>
        <Accordion type="single" collapsible className="w-full">
          {items.map((item, index) => (
            <AccordionItem key={item.id} value={`item-${index}`}>
              <AccordionTrigger className="text-left font-semibold hover:no-underline">
                {item.question}
              </AccordionTrigger>
              <AccordionContent className="text-muted-foreground">{item.answer}</AccordionContent>
            </AccordionItem>
          ))}
        </Accordion>
      </BlockContainer>
    </section>
  );
}

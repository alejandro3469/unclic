'use client';

import { BlockContainer } from '@/components/blocks';
import { clienteIdeal } from '@/lib/copy';
import { CheckCircle2 } from 'lucide-react';

export function ClienteIdealSection() {
  return (
    <section
      id="cliente-ideal"
      className="py-16 md:py-24"
      aria-labelledby="cliente-ideal-heading"
    >
      <BlockContainer>
        <h2
          id="cliente-ideal-heading"
          className="text-2xl font-semibold tracking-tight sm:text-3xl md:text-4xl"
        >
          {clienteIdeal.title}
        </h2>
        <ul className="mt-6 space-y-4">
          {clienteIdeal.items.map((item) => (
            <li
              key={item}
              className="flex gap-3 text-muted-foreground"
            >
              <CheckCircle2
                className="mt-0.5 size-5 shrink-0 text-green-600 dark:text-green-500"
                aria-hidden
              />
              <span>{item}</span>
            </li>
          ))}
        </ul>
      </BlockContainer>
    </section>
  );
}

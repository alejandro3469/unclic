'use client';

import { BlockContainer } from '@/components/blocks';
import { clienteIdeal } from '@/lib/copy';
import { LiquidGlassIcon } from '@/components/ui/liquid-glass-icon';
import { LG } from '@/lib/icons8-liquid-glass';

export function ClienteIdealSection() {
  return (
    <section
      id="cliente-ideal"
      className="py-16 md:py-24"
      aria-labelledby="cliente-ideal-heading"
    >
      <BlockContainer>
        <h2 id="cliente-ideal-heading" className="text-type-section-title">
          {clienteIdeal.title}
        </h2>
        <ul className="mt-6 space-y-4">
          {clienteIdeal.items.map((item) => (
            <li
              key={item}
              className="flex gap-3 text-muted-foreground"
            >
              <span className="mt-0.5 shrink-0" aria-hidden>
                <LiquidGlassIcon slug={LG.checked} size={22} alt="" />
              </span>
              <span>{item}</span>
            </li>
          ))}
        </ul>
      </BlockContainer>
    </section>
  );
}

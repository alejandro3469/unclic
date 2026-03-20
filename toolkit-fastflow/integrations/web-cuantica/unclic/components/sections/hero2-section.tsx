'use client';

import { Hero2 } from '@/components/hero2';
import { hero } from '@/lib/copy';
import { HERO_IMAGE_PLACEHOLDER } from '@/lib/placeholders';

/** Hero bloque Shadcn Blocks Hero2 con nuestro copy (hero). */
export function Hero2Section() {
  return (
    <Hero2
      badge={hero.heroEyebrow || hero.heroPillars}
      heading={hero.headline}
      description={hero.subtitle}
      buttons={{
        primary: { text: hero.ctaPrimary, url: '/demo/access' },
        secondary: { text: hero.ctaSecondary, url: '/contacto' },
      }}
      image={{
        src: HERO_IMAGE_PLACEHOLDER,
        alt: hero.imageAlt,
      }}
    />
  );
}

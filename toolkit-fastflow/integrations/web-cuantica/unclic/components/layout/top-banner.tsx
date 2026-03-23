'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { X } from 'lucide-react';
import { BlockContainer } from '@/components/blocks';
import { banner } from '@/lib/copy';
import { routes } from '@/lib/routes';

const BANNER_STORAGE_KEY = 'unclic-banner-dismissed';

// Google Merchant Center: los pop-ups/banners no deben cubrir precio, CTA ni datos clave.
// Este banner es una barra superior cerrable y no debe extenderse ni tapar la sección de precios.

export function TopBanner() {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    try {
      const dismissed = sessionStorage.getItem(BANNER_STORAGE_KEY);
      setVisible(dismissed !== 'true');
    } catch {
      setVisible(true);
    }
  }, []);

  const dismiss = () => {
    try {
      sessionStorage.setItem(BANNER_STORAGE_KEY, 'true');
      setVisible(false);
    } catch {
      setVisible(false);
    }
  };

  if (!visible) return null;

  return (
    <div
      role="region"
      aria-label="Aviso de prueba gratuita"
      className="relative z-10 border-b border-primary/40 bg-gradient-to-r from-primary to-primary/90 text-primary-foreground"
    >
      <BlockContainer className="flex items-center justify-between gap-4 py-3">
        <Link
          href={routes.publicSignup}
          className="group flex-1 text-center text-sm font-medium transition-colors hover:underline focus:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
        >
          {banner.message}
          <span className="ml-1 inline-block transition-transform group-hover:translate-x-0.5" aria-hidden>
            →
          </span>
        </Link>
        <button
          type="button"
          className="shrink-0 rounded p-1.5 text-primary-foreground/80 transition-colors hover:bg-primary-foreground/10 hover:text-primary-foreground focus:outline-none focus-visible:ring-2 focus-visible:ring-ring"
          onClick={dismiss}
          aria-label={banner.dismissLabel}
        >
          <X className="size-4" aria-hidden />
        </button>
      </BlockContainer>
    </div>
  );
}

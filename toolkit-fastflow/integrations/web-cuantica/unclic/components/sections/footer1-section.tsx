'use client';

import { Footer1 } from '@/components/footer1';
import { site, footer as footerCopy, footerNav } from '@/lib/copy';

const LOGO_SRC = '/images/brand/unclic-logo.svg';

function mapSections() {
  return [
    {
      title: footerNav.solutions.title,
      links: footerNav.solutions.items.map((item) => ({
        name: item.label,
        href: item.href,
      })),
    },
    {
      title: footerNav.capabilities.title,
      links: footerNav.capabilities.items.map((item) => ({
        name: item.label,
        href: item.href,
      })),
    },
    {
      title: footerNav.learn.title,
      links: footerNav.learn.items.map((item) => ({
        name: item.label,
        href: item.href,
      })),
    },
    {
      title: footerNav.connect.title,
      links: footerNav.connect.items.map((item) => ({
        name: item.label,
        href: item.href,
      })),
    },
  ];
}

/** Footer bloque Shadcn Blocks Footer1 con nuestro copy (footer, footerNav, site). */
export function Footer1Section() {
  const currentYear = new Date().getFullYear();
  const copyright = `© ${currentYear} ${footerCopy.copyright}`;

  return (
    <Footer1
      logo={{
        url: '/',
        src: LOGO_SRC,
        alt: site.name,
        title: site.name,
      }}
      content={{
        tagline: footerCopy.tagline,
        sections: mapSections(),
        builtWith: footerCopy.builtWith,
        copyright,
        hideAppStore: true,
      }}
      className="py-24 pb-16 border-t border-border bg-muted/30"
    />
  );
}

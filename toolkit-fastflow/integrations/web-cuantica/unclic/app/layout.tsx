import type { Metadata } from 'next';
import dynamic from 'next/dynamic';
import { Roboto, Space_Mono, Ubuntu } from 'next/font/google';
import './globals.css';
import { site, nav, seo } from '@/lib/copy';

const ChatterAssist = dynamic(
  () => import('@/components/chat/chatter-assist').then((m) => m.ChatterAssist),
  { ssr: false }
);

const roboto = Roboto({
  weight: ['100', '300', '400', '500', '700', '900'],
  style: ['normal', 'italic'],
  subsets: ['latin'],
  variable: '--font-roboto',
  display: 'swap',
});

const spaceMono = Space_Mono({
  weight: ['400', '700'],
  subsets: ['latin'],
  variable: '--font-space-mono',
  display: 'swap',
});

const ubuntu = Ubuntu({
  weight: ['300', '400', '500', '700'],
  subsets: ['latin'],
  variable: '--font-ubuntu',
  display: 'swap',
});

export const metadata: Metadata = {
  metadataBase: new URL('https://unclic.consulting'),
  title: {
    default: seo.title,
    template: `%s | ${site.name}`,
  },
  description: seo.metaDescription,
  keywords: [...site.keywords],
  authors: [{ name: site.name, url: 'https://unclic.consulting' }],
  creator: site.name,
  publisher: site.name,
  formatDetection: { email: false, address: false, telephone: false },
  openGraph: {
    type: 'website',
    locale: 'es_ES',
    url: 'https://unclic.consulting',
    siteName: site.name,
    title: seo.ogTitle,
    description: seo.metaDescription,
  },
  twitter: {
    card: 'summary_large_image',
    title: seo.ogTitle,
    description: seo.metaDescription,
  },
  robots: {
    index: true,
    follow: true,
    googleBot: { index: true, follow: true },
  },
  alternates: { canonical: 'https://unclic.consulting' },
  icons: {
    icon: [{ url: '/images/brand/favicon.svg', type: 'image/svg+xml' }],
  },
};

const jsonLd = {
  '@context': 'https://schema.org',
  '@graph': [
    {
      '@type': 'Organization',
      '@id': 'https://unclic.consulting/#organization',
      name: site.name,
      url: 'https://unclic.consulting',
      logo: 'https://unclic.consulting/images/brand/favicon.svg',
      description: seo.metaDescription,
    },
    {
      '@type': 'WebSite',
      '@id': 'https://unclic.consulting/#website',
      url: 'https://unclic.consulting',
      name: site.name,
      description: seo.metaDescription,
      publisher: { '@id': 'https://unclic.consulting/#organization' },
      inLanguage: 'es-ES',
    },
    // Precio y moneda en HTML inicial para cumplir con requisitos de landing (Google Merchant Center).
    {
      '@type': 'Service',
      '@id': 'https://unclic.consulting/#service',
      name: 'Pipeline as Code y CI/CD',
      description: seo.metaDescription,
      provider: { '@id': 'https://unclic.consulting/#organization' },
      inLanguage: 'es-ES',
      areaServed: 'ES',
      offers: {
        '@type': 'AggregateOffer',
        priceCurrency: 'USD',
        lowPrice: '0',
        highPrice: '350',
        offerCount: 3,
        availability: 'https://schema.org/InStock',
      },
    },
  ],
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="es" className={`${roboto.variable} ${spaceMono.variable} ${ubuntu.variable}`}>
      <body className="min-h-screen bg-background font-sans text-foreground antialiased">
        <script
          type="application/ld+json"
          dangerouslySetInnerHTML={{ __html: JSON.stringify(jsonLd) }}
        />
        <a
          href="#main-content"
          className="sr-only focus:not-sr-only focus:absolute focus:left-4 focus:top-4 focus:z-50 focus:rounded focus:bg-primary focus:px-4 focus:py-2 focus:text-primary-foreground focus:outline-none"
        >
          {nav.skipToContent}
        </a>
        {children}
        <ChatterAssist />
      </body>
    </html>
  );
}

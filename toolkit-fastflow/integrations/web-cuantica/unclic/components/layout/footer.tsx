'use client';

import Link from 'next/link';
import { Github, Mail, Linkedin } from 'lucide-react';
import { UnClicLogo } from '@/components/ui/unclic-logo';
import { Separator } from '@/components/ui/separator';
import { Button } from '@/components/ui/button';
import { BlockContainer } from '@/components/blocks';
import { site, footer as footerCopy, footerNav } from '@/lib/copy';
import { routes } from '@/lib/routes';
import { cn } from '@/lib/utils';

/** Footer con estructura tipo Shadcn Blocks Footer1 (logo + tagline, separadores, grid por secciones). Nuestro copy desde lib/copy. */
export function Footer() {
  const currentYear = new Date().getFullYear();
  const linkedInUrl = process.env.NEXT_PUBLIC_LINKEDIN_URL?.trim();
  const githubUrl = process.env.NEXT_PUBLIC_GITHUB_URL?.trim();
  const linkedInValid = linkedInUrl?.startsWith('http');
  const githubValid = githubUrl?.startsWith('http');

  const sections = [
    { title: footerNav.solutions.title, items: footerNav.solutions.items },
    { title: footerNav.capabilities.title, items: footerNav.capabilities.items },
    { title: footerNav.learn.title, items: footerNav.learn.items },
  ];

  return (
    <footer
      className={cn('border-t border-border bg-muted/30 py-24 pb-16')}
      role="contentinfo"
    >
      <BlockContainer>
        <div className="flex flex-col justify-between gap-6 md:flex-row md:items-center">
          <Link href={routes.home} className="flex items-center gap-2 font-medium" aria-label={site.name}>
            <UnClicLogo size={28} />
            <span className="text-xl text-foreground">{site.name}</span>
          </Link>
          <p className="text-lg font-medium text-foreground md:max-w-md">
            {footerCopy.tagline}
          </p>
        </div>
        <Separator className="my-10" />
        <div className="grid gap-8 sm:grid-cols-2 lg:grid-cols-4">
          {sections.map((section) => (
            <div key={section.title}>
              <h3 className="mb-4 text-sm font-semibold text-foreground">{section.title}</h3>
              <ul className="space-y-3 text-sm text-muted-foreground">
                {section.items.map(({ label, href }) => (
                  <li key={label}>
                    <Link
                      href={href}
                      className="font-medium transition-colors hover:text-primary"
                    >
                      {label}
                    </Link>
                  </li>
                ))}
              </ul>
            </div>
          ))}
          <div>
            <h3 className="mb-4 text-sm font-semibold text-foreground">
              {footerNav.connect.title}
            </h3>
            <ul className="space-y-3 text-sm text-muted-foreground">
              {footerNav.connect.items.map(({ label, href }) => (
                <li key={label}>
                  <Link
                    href={href}
                    className="font-medium transition-colors hover:text-primary"
                  >
                    {label}
                  </Link>
                </li>
              ))}
            </ul>
            <p className="mt-4 text-xs font-semibold uppercase tracking-wider text-muted-foreground">
              {footerCopy.socialTitle}
            </p>
            <div className="mt-2 flex gap-1">
              {githubValid ? (
                <Button variant="ghost" size="icon" asChild aria-label="GitHub">
                  <a href={githubUrl} target="_blank" rel="noopener noreferrer">
                    <Github className="size-5" />
                  </a>
                </Button>
              ) : null}
              {linkedInValid ? (
                <Button variant="ghost" size="icon" asChild aria-label="LinkedIn">
                  <a href={linkedInUrl} target="_blank" rel="noopener noreferrer">
                    <Linkedin className="size-5" />
                  </a>
                </Button>
              ) : null}
              <Button variant="ghost" size="icon" asChild aria-label="Crear cuenta">
                <Link href={routes.publicSignup}>
                  <Mail className="size-5" />
                </Link>
              </Button>
            </div>
            {!githubValid && !linkedInValid ? (
              <p className="mt-2 text-xs text-muted-foreground">
                {footerCopy.socialEnvHint}
              </p>
            ) : null}
          </div>
        </div>
        <Separator className="my-10" />
        <p className="text-sm text-muted-foreground">
          {footerCopy.builtWith}
        </p>
        <p className="mt-1 text-sm text-muted-foreground">
          © {currentYear} {footerCopy.copyright}
        </p>
        <p className="mt-3 text-xs text-muted-foreground">
          {footerCopy.icons8AttributionLabel}{' '}
          <a
            href={footerCopy.icons8AttributionHref}
            target="_blank"
            rel="noopener noreferrer"
            className="underline-offset-2 hover:text-foreground hover:underline"
          >
            {footerCopy.icons8AttributionVendor}
          </a>
        </p>
      </BlockContainer>
    </footer>
  );
}

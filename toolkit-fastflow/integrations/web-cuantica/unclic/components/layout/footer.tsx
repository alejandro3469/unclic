'use client';

import Link from 'next/link';
import { Github, Mail, Linkedin } from 'lucide-react';
import { UnClicLogo } from '@/components/ui/unclic-logo';
import { Separator } from '@/components/ui/separator';
import { site, footer as footerCopy, footerNav } from '@/lib/copy';
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
      <div className="container">
        <div className="flex flex-col justify-between gap-6 md:flex-row md:items-center">
          <Link href="/" className="flex items-center gap-2 font-medium" aria-label={site.name}>
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
              <h3 className="mb-4 font-bold text-foreground">{section.title}</h3>
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
            <h3 className="mb-4 font-bold text-foreground">
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
            <div className="mt-2 flex gap-2">
              {githubValid ? (
                <a
                  href={githubUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-muted-foreground transition-colors hover:text-foreground"
                  aria-label="GitHub"
                >
                  <Github className="size-5" />
                </a>
              ) : null}
              {linkedInValid ? (
                <a
                  href={linkedInUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-muted-foreground transition-colors hover:text-foreground"
                  aria-label="LinkedIn"
                >
                  <Linkedin className="size-5" />
                </a>
              ) : null}
              <Link
                href="/contacto"
                className="text-muted-foreground transition-colors hover:text-foreground"
                aria-label="Email"
              >
                <Mail className="size-5" />
              </Link>
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
      </div>
    </footer>
  );
}

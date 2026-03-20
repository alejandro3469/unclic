'use client';

import { Fragment, useState } from 'react';
import Link from 'next/link';
import { Menu, Mail, Search, X, Linkedin, Github, ChevronDown } from 'lucide-react';
import { Button } from '@/components/ui/button';
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetHeader,
  SheetTitle,
} from '@/components/ui/sheet';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { BlockContainer } from '@/components/blocks';
import { UnClicLogo } from '@/components/ui/unclic-logo';
import { useIsMobile } from '@/hooks/use-mobile';
import { cn } from '@/lib/utils';
import { nav, navDropdowns, site } from '@/lib/copy';

const menuLinkStyle =
  'uppercase text-xs font-semibold px-3 py-2 text-muted-foreground transition-colors hover:text-foreground';

/** Navbar block (Shadcn Blocks: Navbar) — logo, dropdowns, Sheet (mobile), CTAs. */
export function Header() {
  const isMobile = useIsMobile();
  const [sheetOpen, setSheetOpen] = useState(false);
  const linkedInUrl = process.env.NEXT_PUBLIC_LINKEDIN_URL?.trim();
  const githubUrl = process.env.NEXT_PUBLIC_GITHUB_URL?.trim();
  const showLinkedIn = linkedInUrl?.startsWith('http');
  const showGitHub = githubUrl?.startsWith('http');

  if (isMobile === undefined) {
    return (
      <header className="sticky top-0 z-50 w-full border-b border-border/80 glass-subtle">
        <BlockContainer className="flex min-h-[4.375rem] items-center justify-between">
          <Link href="/" className="flex items-center font-medium" aria-label={nav.home}>
            <UnClicLogo size={32} />
          </Link>
        </BlockContainer>
      </header>
    );
  }

  const renderMobileGroup = (
    label: string,
    items: ReadonlyArray<{ key: keyof typeof nav; href: string }>
  ) => (
    <>
      <span className="mt-4 px-2 pb-2 text-xs font-semibold uppercase tracking-wider text-muted-foreground first:mt-0">
        {label}
      </span>
      {items.map(({ key, href }) => (
        <Link
          key={key}
          href={href}
          onClick={() => setSheetOpen(false)}
          className={cn(
            'flex w-full border-b border-border py-4 px-2 text-sm font-semibold leading-relaxed hover:no-underline',
            menuLinkStyle
          )}
        >
          {nav[key]}
        </Link>
      ))}
    </>
  );

  return (
    <div className="w-full">
      {isMobile ? (
        <Fragment>
          <nav className="sticky top-0 z-50 min-h-14 border-b border-border/80 bg-background/95 backdrop-blur-md">
            <div className="flex h-full items-center gap-2 px-3 sm:px-4">
              <Link href="/" className="flex items-center gap-2" aria-label={nav.home}>
                <UnClicLogo size={32} />
              </Link>
              <div className="ml-auto flex items-center gap-0.5">
                {showGitHub ? (
                  <Button variant="ghost" size="icon" asChild aria-label="GitHub">
                    <a href={githubUrl} target="_blank" rel="noopener noreferrer">
                      <Github className="size-5" />
                    </a>
                  </Button>
                ) : null}
                {showLinkedIn ? (
                  <Button variant="ghost" size="icon" asChild aria-label="LinkedIn">
                    <a href={linkedInUrl} target="_blank" rel="noopener noreferrer">
                      <Linkedin className="size-5" />
                    </a>
                  </Button>
                ) : null}
                <Button variant="ghost" size="icon" aria-label="Buscar">
                  <Search className="size-5" />
                </Button>
                <Button asChild size="sm" variant="ghost" className="hidden sm:inline-flex text-xs">
                  <Link href="/login">{nav.login}</Link>
                </Button>
                <Button asChild size="sm" variant="default" className="hidden sm:inline-flex">
                  <Link href="/demo/access">{nav.ctaPrimary}</Link>
                </Button>
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => setSheetOpen(true)}
                  aria-label={nav.openMenu}
                >
                  <Menu className="size-5" />
                </Button>
              </div>
            </div>
          </nav>
          <Sheet open={sheetOpen} onOpenChange={setSheetOpen}>
            <SheetContent
              side="left"
              showCloseButton={false}
              className="w-full gap-0 overflow-auto sm:max-w-[27.75rem]"
              aria-describedby={undefined}
            >
              <div className="flex h-full flex-col pt-5">
                <SheetHeader className="h-9 px-6">
                  <SheetTitle className="sr-only">{nav.sheetTitle}</SheetTitle>
                  <SheetClose asChild>
                    <Button size="icon" variant="ghost" aria-label={nav.closeMenu}>
                      <X className="size-5" />
                    </Button>
                  </SheetClose>
                </SheetHeader>
                <div className="flex-1 overflow-auto px-6 py-5">
                  {navDropdowns.map((drop) =>
                    renderMobileGroup(nav[drop.labelKey], drop.items)
                  )}
                  <span className="mt-4 px-2 pb-2 text-xs font-semibold uppercase tracking-wider text-muted-foreground">
                    {nav.groupConnect}
                  </span>
                  <Link
                    href="/empresa"
                    onClick={() => setSheetOpen(false)}
                    className={cn(
                      'flex w-full border-b border-border py-4 px-2 text-sm font-semibold',
                      menuLinkStyle
                    )}
                  >
                    {nav.company}
                  </Link>
                  <Link
                    href="/contacto"
                    onClick={() => setSheetOpen(false)}
                    className={cn(
                      'flex w-full border-b border-border py-4 px-2 text-sm font-semibold',
                      menuLinkStyle
                    )}
                  >
                    {nav.contact}
                  </Link>
                  <Link
                    href="/login"
                    onClick={() => setSheetOpen(false)}
                    className="mt-4 flex w-full items-center justify-center rounded-md border border-border py-3 text-sm font-semibold"
                  >
                    {nav.login}
                  </Link>
                  <Link
                    href="/demo/access"
                    onClick={() => setSheetOpen(false)}
                    className="mt-2 flex w-full items-center justify-center rounded-md bg-primary py-3 text-sm font-semibold text-primary-foreground"
                  >
                    {nav.ctaPrimary}
                  </Link>
                </div>
              </div>
            </SheetContent>
          </Sheet>
        </Fragment>
      ) : (
        <header className="sticky top-0 z-50 w-full border-b border-border/80 glass-subtle">
          <nav aria-label="Navegación principal">
            <BlockContainer className="flex min-h-[4.375rem] flex-wrap items-center gap-x-1 gap-y-2 py-2 md:flex-nowrap">
            <Link href="/" className="mr-2 flex shrink-0 items-center font-medium" aria-label={nav.home}>
              <UnClicLogo size={32} className="shrink-0" />
            </Link>
            <div className="flex flex-1 flex-wrap items-center gap-0 lg:gap-1">
              {navDropdowns.map((drop) => (
                <DropdownMenu key={drop.id}>
                  <DropdownMenuTrigger
                    className={cn(
                      menuLinkStyle,
                      'flex items-center gap-0.5 rounded-md outline-none ring-offset-background focus-visible:ring-2 focus-visible:ring-ring'
                    )}
                  >
                    {nav[drop.labelKey]}
                    <ChevronDown className="size-3 opacity-60" aria-hidden />
                  </DropdownMenuTrigger>
                  <DropdownMenuContent align="start" className="min-w-[13rem]">
                    {drop.items.map(({ key, href }) => (
                      <DropdownMenuItem key={key} asChild className="cursor-pointer">
                        <Link href={href}>{nav[key]}</Link>
                      </DropdownMenuItem>
                    ))}
                  </DropdownMenuContent>
                </DropdownMenu>
              ))}
              <Link href="/empresa" className={menuLinkStyle}>
                {nav.company}
              </Link>
              <Link href="/contacto" className={menuLinkStyle}>
                {nav.contact}
              </Link>
            </div>
            <div className="ml-auto flex shrink-0 items-center gap-0.5">
              {showGitHub ? (
                <Button variant="ghost" size="icon" asChild aria-label="GitHub">
                  <a href={githubUrl} target="_blank" rel="noopener noreferrer">
                    <Github className="size-5" />
                  </a>
                </Button>
              ) : null}
              {showLinkedIn ? (
                <Button variant="ghost" size="icon" asChild aria-label="LinkedIn">
                  <a href={linkedInUrl} target="_blank" rel="noopener noreferrer">
                    <Linkedin className="size-5" />
                  </a>
                </Button>
              ) : null}
              <Button variant="ghost" size="icon" aria-label="Buscar">
                <Search className="size-5" />
              </Button>
              <Button asChild variant="ghost" size="sm" className="hidden lg:inline-flex">
                <Link href="/contacto" className="gap-1.5 text-xs uppercase">
                  <Mail className="size-3.5" aria-hidden />
                  {nav.contact}
                </Link>
              </Button>
              <Button asChild size="sm" variant="ghost" className="ml-1 text-xs uppercase">
                <Link href="/login">{nav.login}</Link>
              </Button>
              <Button asChild size="sm" variant="default" className="ml-1">
                <Link href="/demo/access">{nav.ctaPrimary}</Link>
              </Button>
            </div>
            </BlockContainer>
          </nav>
        </header>
      )}
    </div>
  );
}

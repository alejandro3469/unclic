'use client';

import Image from 'next/image';
import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Separator } from '@/components/ui/separator';
import { signupPage } from '@/lib/copy';
import { routes } from '@/lib/routes';
import { cn } from '@/lib/utils';

const LOGO_DEFAULT = '/images/brand/unclic-logo.svg';
const GOOGLE_ICON =
  'https://deifkwefumgah.cloudfront.net/shadcnblocks/block/logos/google-icon.svg';
const SIDE_PLACEHOLDER =
  'https://deifkwefumgah.cloudfront.net/shadcnblocks/block/placeholder-dark-7-tall.svg';

type SignupMarketingSectionProps = {
  className?: string;
  logoSrc?: string;
  sideImageSrc?: string;
};

/**
 * Registro estilo marketing — Card + Input + Button + Separator (shadcn/ui).
 * Flujo real de registro: portal/API; esto es UI + copy.
 */
export function SignupMarketingSection({
  className,
  logoSrc = LOGO_DEFAULT,
  sideImageSrc = SIDE_PLACEHOLDER,
}: SignupMarketingSectionProps) {
  return (
    <section className={cn('bg-background', className)}>
      <BlockContainer className="flex min-h-[min(100dvh,880px)] flex-col items-stretch gap-12 py-16 lg:min-h-screen lg:flex-row lg:gap-10 lg:py-12">
        <Card className="flex flex-1 flex-col justify-center border-0 bg-transparent shadow-none lg:max-w-md">
          <CardHeader className="items-center space-y-2 pb-2 text-center sm:items-start sm:text-left">
            <div className="mb-2 flex justify-center sm:justify-start">
              <Image src={logoSrc} alt={signupPage.logoAlt} width={48} height={48} className="h-12 w-auto object-contain" />
            </div>
            <CardTitle className="text-type-page-title font-medium tracking-tighter">
              {signupPage.title}
            </CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col items-stretch gap-6 pt-2">
            <Button
              type="button"
              variant="outline"
              className="h-12 w-full rounded-full border-muted-foreground/30"
            >
              <Image src={GOOGLE_ICON} alt="" width={20} height={20} className="mr-2 size-5" />
              {signupPage.signUpWithGoogle}
            </Button>
            <div className="flex items-center gap-4">
              <Separator className="flex-1" />
              <span className="text-sm font-medium text-muted-foreground">{signupPage.or}</span>
              <Separator className="flex-1" />
            </div>
            <Input
              type="email"
              className="h-12 rounded-full border-none bg-muted px-5"
              placeholder={signupPage.emailPlaceholder}
              autoComplete="email"
            />
            <Button type="button" className="h-12 rounded-full">
              {signupPage.continue}
            </Button>
          </CardContent>
          <CardFooter className="flex flex-col gap-6 pt-6 text-center text-sm text-muted-foreground sm:text-left">
            <p>
              {signupPage.termsPrefix}{' '}
              <Link href={routes.legalDemosTerminos} className="underline underline-offset-4">
                {signupPage.termsLink}
              </Link>
              {signupPage.termsAnd}
              <Link href={routes.legalDemosDatos} className="underline underline-offset-4">
                {signupPage.privacyLink}
              </Link>
            </p>
            <p className="text-foreground">
              {signupPage.alreadyUser}{' '}
              <Link href={routes.login} className="font-medium underline underline-offset-4">
                {signupPage.logIn}
              </Link>
            </p>
          </CardFooter>
        </Card>

        <div className="relative hidden min-h-[280px] flex-1 overflow-hidden rounded-xl border border-border bg-muted lg:block lg:min-h-[min(100dvh,56rem)]">
          <Image
            src={sideImageSrc}
            alt=""
            fill
            className="object-cover"
            sizes="(max-width: 1024px) 100vw, 45vw"
          />
        </div>
      </BlockContainer>
    </section>
  );
}

"use client";

import Link from "next/link";
import React from "react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Separator } from "@/components/ui/separator";
import { cn } from "@/lib/utils";

export interface Signup10Content {
  title: string;
  signUpWithGoogle: string;
  or: string;
  emailPlaceholder: string;
  continue: string;
  termsPrefix: string;
  termsAnd: string;
  termsLink: string;
  privacyLink: string;
  alreadyUser: string;
  logIn: string;
  logoAlt: string;
}

const DEFAULT_CONTENT: Signup10Content = {
  title: "Create your free account",
  signUpWithGoogle: "Sign up with Google",
  or: "or",
  emailPlaceholder: "Enter Your Email",
  continue: "Continue",
  termsPrefix: "By proceeding, you accept our",
  termsAnd: " and ",
  termsLink: "Terms",
  privacyLink: "Privacy Policy",
  alreadyUser: "Already a user?",
  logIn: "Log in",
  logoAlt: "Logo",
};

interface Signup10Props {
  className?: string;
  content?: Partial<Signup10Content>;
  logoSrc?: string;
  sideImageSrc?: string;
}

const Signup10 = ({
  className,
  content: contentProp,
  logoSrc = "https://deifkwefumgah.cloudfront.net/shadcnblocks/block/block-1.svg",
  sideImageSrc = "https://deifkwefumgah.cloudfront.net/shadcnblocks/block/placeholder-dark-7-tall.svg",
}: Signup10Props) => {
  const content = { ...DEFAULT_CONTENT, ...contentProp };

  return (
    <section className={cn("bg-background", className)}>
      <div className="container flex min-h-screen flex-col items-center justify-between gap-20 py-16 lg:flex-row lg:px-0 lg:py-0">
        <div className="mx-auto flex w-full max-w-xl flex-col items-center gap-6">
          <div className="flex h-14 w-14 items-center justify-center">
            <img
              className="h-14 w-12"
              alt={content.logoAlt}
              src={logoSrc}
            />
          </div>

          <h1 className="mb-8 w-full text-center text-3xl font-medium tracking-tighter text-foreground md:text-4xl">
            {content.title}
          </h1>

          <Button
            variant="outline"
            className="flex h-14 w-full max-w-lg items-center justify-center gap-8 rounded-full border-muted-foreground/30"
          >
            <img
              className="h-5 w-5"
              alt="Google"
              src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/logos/google-icon.svg"
            />
            <span className="font-medium">{content.signUpWithGoogle}</span>
          </Button>

          <div className="flex w-full max-w-lg items-center gap-6">
            <Separator className="flex-1" />
            <span className="font-medium tracking-tight">{content.or}</span>
            <Separator className="flex-1" />
          </div>

          <div className="w-full max-w-lg">
            <Input
              className="h-14 rounded-full border-none bg-muted px-5 py-4 font-medium"
              placeholder={content.emailPlaceholder}
            />
          </div>

          <Button className="h-14 w-full max-w-lg rounded-full bg-foreground text-background hover:bg-foreground/90">
            <span className="font-medium tracking-tight">{content.continue}</span>
          </Button>

          <p className="mb-8 w-full text-center text-sm tracking-tight text-foreground/40">
            <span>{content.termsPrefix} </span>
            <Link href="/legal/acceso-demos#terminos" className="underline">
              {content.termsLink}
            </Link>
            <span>{content.termsAnd}</span>
            <Link href="/legal/acceso-demos#datos" className="underline">
              {content.privacyLink}
            </Link>
          </p>

          <p className="mb-20 w-full text-center text-sm font-medium tracking-tight">
            {content.alreadyUser}{" "}
            <Link href="/login" className="underline">
              {content.logIn}
            </Link>
          </p>
        </div>
        <div className="hidden h-screen w-full bg-muted lg:block">
          <img
            src={sideImageSrc}
            className="size-full object-cover"
            alt=""
          />
        </div>
      </div>
    </section>
  );
};

export { Signup10 };

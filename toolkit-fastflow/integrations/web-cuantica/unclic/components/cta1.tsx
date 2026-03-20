import Link from "next/link";
import Image from "next/image";
import { ArrowRight, Sparkles } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { cn } from "@/lib/utils";

export interface Cta1Content {
  headlineAlt?: string;
  sectionTitle: string;
  onePartner?: string;
  sectionDescription: string;
  ctaPrimary: string;
  ctaSecondary: string;
  ctaPrimaryHref?: string;
  ctaSecondaryHref?: string;
  imageSrc: string;
  imageAlt: string;
}

const DEFAULT_CONTENT: Cta1Content = {
  sectionTitle: "Call to Action",
  sectionDescription:
    "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Architecto illo praesentium nisi, accusantium quae.",
  ctaPrimary: "Get Started",
  ctaSecondary: "Contact",
  ctaPrimaryHref: "#",
  ctaSecondaryHref: "#",
  imageSrc: "https://deifkwefumgah.cloudfront.net/shadcnblocks/block/placeholder-1.svg",
  imageAlt: "placeholder",
};

interface Cta1Props {
  className?: string;
  content?: Partial<Cta1Content>;
  /** Id for the section title (e.g. for aria-labelledby). */
  headingId?: string;
}

const Cta1 = ({ className, content: contentProp, headingId }: Cta1Props) => {
  const content = { ...DEFAULT_CONTENT, ...contentProp };
  const primaryHref = content.ctaPrimaryHref ?? "#";
  const secondaryHref = content.ctaSecondaryHref ?? "#";

  return (
    <section className={cn("py-32", className)}>
      <div className="container">
        <Card className="flex flex-col justify-between border-muted pb-0 md:flex-row md:py-6">
          <div className="p-6 md:max-w-96 lg:max-w-lg">
            {content.headlineAlt ? (
              <p className="mb-2 text-sm font-medium text-muted-foreground">
                {content.headlineAlt}
              </p>
            ) : null}
            <div className="mb-2 flex items-center gap-2">
              <span className="flex size-7 items-center justify-center rounded-full bg-primary/10">
                <Sparkles className="size-4 text-primary" strokeWidth={1.5} />
              </span>
              <h4
                id={headingId}
                className="text-2xl font-bold tracking-tight"
              >
                {content.sectionTitle}
              </h4>
            </div>
            {content.onePartner ? (
              <p className="mb-2 text-sm text-muted-foreground">{content.onePartner}</p>
            ) : null}
            <p className="text-muted-foreground">{content.sectionDescription}</p>
            <div className="mt-8 flex flex-wrap gap-4">
              <Button asChild size="lg" className="gap-2">
                <Link href={primaryHref}>
                  {content.ctaPrimary}
                  <ArrowRight className="ml-2 size-4" aria-hidden />
                </Link>
              </Button>
              <Button asChild variant="outline" size="lg">
                <Link href={secondaryHref}>{content.ctaSecondary}</Link>
              </Button>
            </div>
          </div>
          <div className="relative aspect-video w-full shrink-0 overflow-hidden md:max-w-sm lg:max-w-md">
            <Image
              src={content.imageSrc}
              alt={content.imageAlt}
              fill
              className="object-cover"
              sizes="(max-width: 768px) 100vw, 400px"
            />
          </div>
        </Card>
      </div>
    </section>
  );
};

export { Cta1 };

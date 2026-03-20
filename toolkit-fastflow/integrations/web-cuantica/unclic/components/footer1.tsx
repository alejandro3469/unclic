import Link from "next/link";
import {
  FaApple,
  FaDiscord,
  FaRedditAlien,
  FaTelegramPlane,
  FaTwitter,
} from "react-icons/fa";

import { Logo, LogoImage, LogoText } from "@/components/shadcnblocks/logo";
import { Separator } from "@/components/ui/separator";
import { cn } from "@/lib/utils";

const DEFAULT_SECTIONS = [
  {
    title: "Product",
    links: [
      { name: "Overview", href: "#" },
      { name: "Pricing", href: "#" },
      { name: "Marketplace", href: "#" },
      { name: "Features", href: "#" },
      { name: "Integrations", href: "#" },
    ],
  },
  {
    title: "Company",
    links: [
      { name: "About", href: "#" },
      { name: "Team", href: "#" },
      { name: "Blog", href: "#" },
      { name: "Careers", href: "#" },
      { name: "Contact", href: "#" },
      { name: "Privacy", href: "#" },
    ],
  },
  {
    title: "Resources",
    links: [
      { name: "Help", href: "#" },
      { name: "Sales", href: "#" },
      { name: "Advertise", href: "#" },
    ],
  },
];

export interface Footer1Content {
  tagline: string;
  sections: { title: string; links: { name: string; href: string }[] }[];
  builtWith?: string;
  copyright: string;
  /** When true, hide app store buttons (e.g. when using site copy). */
  hideAppStore?: boolean;
}

interface Footer1Props {
  logo?: {
    url: string;
    src: string;
    alt: string;
    title: string;
    className?: string;
  };
  className?: string;
  content?: Partial<Footer1Content>;
}
const Footer1 = ({
  logo = {
    url: "https://www.shadcnblocks.com",
    src: "https://deifkwefumgah.cloudfront.net/shadcnblocks/block/logos/shadcnblockscom-icon.svg",
    alt: "logo",
    title: "Shadcnblocks.com",
  },
  className,
  content: contentProp,
}: Footer1Props) => {
  const tagline = contentProp?.tagline ?? "Copy the code and make it yours.";
  const sections = contentProp?.sections?.length
    ? contentProp.sections
    : DEFAULT_SECTIONS;
  const builtWith = contentProp?.builtWith;
  const copyright =
    contentProp?.copyright ?? "© 2024 Shadcnblocks.com. All rights reserved.";
  const hideAppStore = contentProp?.hideAppStore ?? false;

  return (
    <section className={cn("py-32 pb-16", className)}>
      <div className="container">
        <footer>
          <div className="flex flex-col justify-between gap-4 md:flex-row md:items-center">
            <Logo url={logo.url || "/"}>
              <LogoImage
                src={logo.src}
                alt={logo.alt}
                title={logo.title}
                className="h-10 dark:invert"
              />
              <LogoText className="text-xl">{logo.title}</LogoText>
            </Logo>
            <div className="flex flex-col gap-4 md:flex-row md:items-center">
              <p className="text-lg font-medium">
                {tagline}
              </p>
              {!hideAppStore && (
                <div className="flex gap-2">
                  <a
                    href="#"
                    className="inline-flex items-center justify-center rounded-lg bg-primary p-2"
                  >
                    <FaApple className="size-6 text-background" />
                  </a>
                  <a
                    href="#"
                    className="inline-flex items-center justify-center rounded-lg bg-primary p-2"
                  >
                    <img
                      src="https://deifkwefumgah.cloudfront.net/shadcnblocks/block/logos/google-play-icon.svg"
                      className="size-6 text-background"
                      alt="google play"
                    />
                  </a>
                </div>
              )}
            </div>
          </div>
          <Separator className="my-14" />
          <div className="grid gap-8 md:grid-cols-2 lg:grid-cols-4">
            {sections.map((section, sectionIdx) => (
              <div key={sectionIdx}>
                <h3 className="mb-4 font-bold">{section.title}</h3>
                <ul className="space-y-4 text-muted-foreground">
                  {section.links.map((link, linkIdx) => (
                    <li
                      key={linkIdx}
                      className="font-medium hover:text-primary"
                    >
                      {link.href.startsWith("/") ? (
                        <Link href={link.href}>{link.name}</Link>
                      ) : (
                        <a href={link.href} target="_blank" rel="noopener noreferrer">
                          {link.name}
                        </a>
                      )}
                    </li>
                  ))}
                </ul>
              </div>
            ))}
            {!contentProp && (
              <div>
                <h3 className="mb-4 font-bold">Legal</h3>
                <ul className="space-y-4 text-muted-foreground">
                  <li className="font-medium hover:text-primary">
                    <a href="#">Term of Services</a>
                  </li>
                  <li className="font-medium hover:text-primary">
                    <a href="#">Privacy Policy</a>
                  </li>
                </ul>
                <h3 className="mt-8 mb-4 font-bold">Social</h3>
                <ul className="flex items-center space-x-6 text-muted-foreground">
                  <li className="font-medium hover:text-primary">
                    <a href="#">
                      <FaDiscord className="size-6" />
                    </a>
                  </li>
                  <li className="font-medium hover:text-primary">
                    <a href="#">
                      <FaRedditAlien className="size-6" />
                    </a>
                  </li>
                  <li className="font-medium hover:text-primary">
                    <a href="#">
                      <FaTwitter className="size-6" />
                    </a>
                  </li>
                  <li className="font-medium hover:text-primary">
                    <a href="#">
                      <FaTelegramPlane className="size-6" />
                    </a>
                  </li>
                </ul>
              </div>
            )}
          </div>
          <Separator className="my-14" />
          {builtWith && (
            <p className="text-sm text-muted-foreground">{builtWith}</p>
          )}
          <p className={cn("text-sm text-muted-foreground", builtWith && "mt-1")}>
            {copyright}
          </p>
        </footer>
      </div>
    </section>
  );
};

export { Footer1 };

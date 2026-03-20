'use client';

import Link from 'next/link';
import Image from 'next/image';
import { ChevronLeft, ChevronRight, Play, Share2 } from 'lucide-react';
import { useCallback, useEffect, useState } from 'react';
import { useRef } from 'react';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from '@/components/ui/card';
import type { CarouselApi } from '@/components/ui/carousel';
import {
  Carousel,
  CarouselContent,
  CarouselItem,
} from '@/components/ui/carousel';
import { BlockContainer } from '@/components/blocks';
import { socialTrendingCarousel } from '@/lib/copy';
import {
  GALLERY_PLACEHOLDERS,
  PLACEHOLDER_VIDEO_URL,
} from '@/lib/placeholders';

type Product = {
  image: string;
  name: string;
  href: string;
};

type Post = {
  name: string;
  userAvatar: string;
  username: string;
  profileLink: string;
  video: string;
  product: Product;
};

const POSTS: Post[] = [
  {
    name: 'UnClic Demos',
    userAvatar: GALLERY_PLACEHOLDERS[0],
    username: '@unclic-demos',
    profileLink: '/soluciones#demos',
    video: PLACEHOLDER_VIDEO_URL,
    product: {
      image: GALLERY_PLACEHOLDERS[0],
      name: 'Demos en vivo',
      href: '/soluciones#demos',
    },
  },
  {
    name: 'Pipeline as Code',
    userAvatar: GALLERY_PLACEHOLDERS[1],
    username: '@unclic-pipeline',
    profileLink: '/capacidades#flow',
    video: PLACEHOLDER_VIDEO_URL,
    product: {
      image: GALLERY_PLACEHOLDERS[2],
      name: 'Flujo CI/CD',
      href: '/capacidades#flow',
    },
  },
  {
    name: 'UnClic Precios',
    userAvatar: GALLERY_PLACEHOLDERS[2],
    username: '@unclic-precios',
    profileLink: '/soluciones#pricing',
    video: PLACEHOLDER_VIDEO_URL,
    product: {
      image: GALLERY_PLACEHOLDERS[3],
      name: 'Precios',
      href: '/soluciones#pricing',
    },
  },
  {
    name: 'UnClic Contacto',
    userAvatar: GALLERY_PLACEHOLDERS[3],
    username: '@unclic',
    profileLink: '/contacto',
    video: PLACEHOLDER_VIDEO_URL,
    product: {
      image: GALLERY_PLACEHOLDERS[4],
      name: 'Contacto',
      href: '/contacto',
    },
  },
  {
    name: 'UnClic Galería',
    userAvatar: GALLERY_PLACEHOLDERS[4],
    username: '@unclic-galeria',
    profileLink: '/insights#gallery',
    video: PLACEHOLDER_VIDEO_URL,
    product: {
      image: GALLERY_PLACEHOLDERS[5],
      name: 'Galería',
      href: '/insights#gallery',
    },
  },
];

function PostCard({
  userAvatar,
  username,
  name,
  video,
  product,
}: Post) {
  const videoRef = useRef<HTMLVideoElement>(null);
  const [isPlaying, setIsPlaying] = useState(false);

  return (
    <Card className="h-full gap-3 p-4">
      <CardHeader className="flex items-center gap-2 p-0">
        <div className="shrink-0 basis-10">
          <Avatar className="size-10 border border-border">
            <AvatarImage src={userAvatar} alt={name} />
            <AvatarFallback className="text-xs font-medium">
              {username.slice(1, 3).toUpperCase()}
            </AvatarFallback>
          </Avatar>
        </div>
        <div className="flex-1 min-w-0">
          <p className="truncate text-sm font-medium leading-normal text-foreground">
            {name}
          </p>
          <p className="truncate text-xs font-medium leading-normal text-muted-foreground">
            {username}
          </p>
        </div>
        <div className="shrink-0 basis-5 flex items-center justify-center">
          <Share2 className="size-5 text-muted-foreground" aria-hidden />
        </div>
      </CardHeader>
      <CardContent className="p-0">
        <AspectRatio ratio={0.75} className="group overflow-hidden rounded-xl">
          <video
            muted
            playsInline
            preload="metadata"
            className="size-full object-cover object-center"
            onPlay={() => setIsPlaying(true)}
            onPause={() => setIsPlaying(false)}
            onMouseEnter={() => videoRef.current?.play()}
            onMouseLeave={() => {
              if (!videoRef.current) return;
              videoRef.current.pause();
              videoRef.current.currentTime = 0;
            }}
            ref={videoRef}
            src={video}
          />
          {!isPlaying && (
            <div className="pointer-events-none absolute top-1/2 left-1/2 z-10 -translate-x-1/2 -translate-y-1/2 transition-opacity duration-500 group-hover:opacity-0">
              <Button size="icon-lg" className="rounded-full" type="button">
                <Play className="size-6 fill-foreground text-foreground" />
              </Button>
            </div>
          )}
        </AspectRatio>
      </CardContent>
      <CardFooter className="p-0">
        <Link
          href={product.href}
          className="flex w-full flex-1 items-center gap-3"
        >
          <div className="shrink-0 basis-10">
            <div className="size-10 overflow-hidden rounded-full bg-muted">
              <Image
                src={product.image}
                alt={product.name}
                width={40}
                height={40}
                className="block size-full object-cover object-center"
              />
            </div>
          </div>
          <div className="min-w-0 flex-1">
            <p className="truncate text-sm font-medium leading-normal text-foreground">
              {product.name}
            </p>
          </div>
          <span className="inline-flex h-8 w-8 shrink-0 items-center justify-center rounded-full border border-input bg-background">
            <ChevronRight className="size-4 text-muted-foreground" aria-hidden />
          </span>
        </Link>
      </CardFooter>
    </Card>
  );
}

export function SocialTrendingCarouselSection() {
  const [api, setApi] = useState<CarouselApi>();
  const [scrollProgress, setScrollProgress] = useState(0);

  const handleScroll = useCallback((emblaApi: CarouselApi) => {
    const progress = Math.max(0, Math.min(1, emblaApi?.scrollProgress() ?? 0));
    setScrollProgress(progress * 100);
  }, []);

  useEffect(() => {
    if (!api) return;
    api.on('scroll', handleScroll);
    api.on('reInit', handleScroll);
    return () => {
      api.off('scroll', handleScroll);
      api.off('reInit', handleScroll);
    };
  }, [api, handleScroll]);

  return (
    <section
      id="social-trending-carousel"
      className="overflow-hidden py-16 md:py-24"
      aria-labelledby="social-trending-carousel-heading"
    >
      <BlockContainer>
        {socialTrendingCarousel.kicker ? (
          <p className="mb-2 text-center text-xs font-semibold uppercase tracking-[0.2em] text-primary">
            {socialTrendingCarousel.kicker}
          </p>
        ) : null}
        <h2
          id="social-trending-carousel-heading"
          className="mb-8 text-center font-serif text-2xl font-medium leading-snug md:text-3xl"
        >
          {socialTrendingCarousel.sectionTitle}
        </h2>
        <Carousel
          opts={{ align: 'start' }}
          className="[&>div]:overflow-visible"
          setApi={setApi}
        >
          <CarouselContent>
            {POSTS.map((item, index) => (
              <CarouselItem
                className="basis-[75%] sm:basis-1/2 lg:basis-1/3 xl:basis-1/4"
                key={index}
              >
                <PostCard {...item} />
              </CarouselItem>
            ))}
          </CarouselContent>
        </Carousel>
        <div className="mt-8 flex items-center gap-3">
          <div className="flex-1">
            <div className="relative h-0.5 w-full overflow-hidden rounded-full bg-muted">
              <div
                className="absolute inset-y-0 -left-full w-full rounded-full bg-primary transition-transform duration-150"
                style={{ transform: `translate3d(${scrollProgress}%, 0, 0)` }}
              />
            </div>
          </div>
          <Button
            onClick={() => api?.scrollPrev()}
            className="shrink-0 rounded-full max-sm:hidden"
            size="icon-lg"
            variant="outline"
            aria-label={socialTrendingCarousel.prevLabel}
          >
            <ChevronLeft className="size-5" />
          </Button>
          <Button
            onClick={() => api?.scrollNext()}
            className="shrink-0 rounded-full max-sm:hidden"
            size="icon-lg"
            variant="outline"
            aria-label={socialTrendingCarousel.nextLabel}
          >
            <ChevronRight className="size-5" />
          </Button>
        </div>
      </BlockContainer>
    </section>
  );
}

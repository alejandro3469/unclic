'use client';

import Image from 'next/image';
import { GALLERY_PLACEHOLDERS } from '@/lib/placeholders';
import { BlockContainer } from '@/components/blocks';
import { gallery } from '@/lib/copy';
import { Card, CardContent } from '@/components/ui/card';
import { PixelImage } from '@/components/ui/pixel-image';

export function GallerySection() {
  return (
    <section
      id="gallery"
      className="bg-gradient-to-b from-rose-50/60 via-orange-50/20 to-background py-16 dark:from-rose-950/20 md:py-24"
      aria-labelledby="gallery-heading"
    >
      <BlockContainer>
        <h2
          id="gallery-heading"
          className="text-2xl font-semibold tracking-tight sm:text-3xl md:text-4xl"
        >
          {gallery.sectionTitle}
        </h2>
        <p className="mt-2 text-muted-foreground">
          {gallery.sectionDescription}
        </p>

        <div className="mt-8 flex justify-center">
          <PixelImage
            src={GALLERY_PLACEHOLDERS[0]}
            alt={gallery.imageAlts[0]}
            customGrid={{ rows: 4, cols: 6 }}
            grayscaleAnimation
            className="rounded-xl"
          />
        </div>

        <div className="mt-8 grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
          {GALLERY_PLACEHOLDERS.map((src, index) => (
            <Card key={index} className="overflow-hidden">
              <CardContent className="p-0">
                <div className="relative aspect-[4/3] bg-muted">
                  <Image
                    src={src}
                    alt={gallery.imageAlts[index] ?? `Imagen de galería ${index + 1}`}
                    fill
                    className="object-cover"
                    sizes="(max-width: 640px) 100vw, (max-width: 1024px) 50vw, 25vw"
                  />
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </BlockContainer>
    </section>
  );
}

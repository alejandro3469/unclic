import { BlockContainer } from '@/components/blocks';
import { homeOpenSource } from '@/lib/copy';
import { cn } from '@/lib/utils';

/** Banda manifesto — tono Vates (OSS, soberanía) sin copiar marca ajena. */
export function HomeOpenSourceSection() {
  return (
    <section
      id={homeOpenSource.id}
      className={cn('section-padding bg-section-manifesto')}
      aria-labelledby="oss-manifesto-title"
    >
      <BlockContainer>
          <p className="text-type-eyebrow tracking-[0.25em] text-nord-8">{homeOpenSource.eyebrow}</p>
          <h2
            id="oss-manifesto-title"
            className="text-type-section-title mt-4 max-w-3xl text-nord-6"
          >
            {homeOpenSource.title}
          </h2>
          <p className="text-type-lead mt-6 max-w-2xl text-nord-4">{homeOpenSource.lead}</p>
          <ul className="mt-10 space-y-4 border-l-2 border-nord-10/60 pl-6">
            {homeOpenSource.bullets.map((line) => (
              <li key={line} className="text-base leading-relaxed text-nord-5">
                {line}
              </li>
            ))}
          </ul>
          <p className="mt-10 max-w-2xl text-lg font-medium leading-relaxed text-nord-6">{homeOpenSource.closing}</p>
          <p className="mt-6 text-sm italic text-nord-3">{homeOpenSource.citeNote}</p>
      </BlockContainer>
    </section>
  );
}

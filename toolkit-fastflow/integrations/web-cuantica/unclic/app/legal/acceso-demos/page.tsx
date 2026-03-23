import type { Metadata } from 'next';
import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';
import { PageIntroWithAnchors } from '@/components/layout/page-intro-with-anchors';
import { legalAccesoDemosLead, legalAccesoDemosPageIntro, pagesMeta } from '@/lib/copy';
import { routes } from '@/lib/routes';

export const metadata: Metadata = {
  title: pagesMeta.legalAccesoDemos.title,
  description: pagesMeta.legalAccesoDemos.description,
};

/** Texto base para revisión legal; sustituir por versión aprobada. */
export default function LegalAccesoDemosPage() {
  return (
    <div className="min-h-screen bg-background">
      <PageIntroWithAnchors
        variant={legalAccesoDemosPageIntro.variant}
        eyebrow={legalAccesoDemosPageIntro.eyebrow}
        navLabel={legalAccesoDemosPageIntro.navLabel}
        links={legalAccesoDemosPageIntro.links}
        className="border-border/80 bg-muted/15 !pt-20 !pb-4 md:!pt-24 md:!pb-5"
      />
      <BlockContainer className="max-w-2xl space-y-10 py-10 text-sm leading-relaxed md:py-12">
        <p>
          <Link href={routes.publicSignup} className="text-primary hover:underline">
            ← Crear cuenta / registro
          </Link>
        </p>
        <p className="text-muted-foreground">{legalAccesoDemosLead}</p>
        <article id="terminos" className="scroll-mt-24 space-y-4">
          <h1 className="text-2xl font-semibold tracking-tight">Términos y condiciones — acceso a demos</h1>
          <p className="text-muted-foreground">
            Documento provisional. Al solicitar acceso declaras haber leído y aceptado estas condiciones. UnClic
            puede actualizarlas; el uso continuado implica aceptación de la versión vigente.
          </p>
          <ul className="list-disc space-y-2 pl-5">
            <li>Las demos son entornos de prueba; no deben usarse para datos productivos ni sensibles.</li>
            <li>El acceso es revocable; no garantizamos disponibilidad ni continuidad del servicio demo.</li>
            <li>Prohibido uso que vulnere leyes, terceros o la integridad de los sistemas mostrados.</li>
          </ul>
        </article>
        <article id="datos" className="scroll-mt-24 space-y-4 border-t border-border pt-10">
          <h2 className="text-xl font-semibold tracking-tight">Política de datos personales (acceso demos)</h2>
          <p className="text-muted-foreground">
            Tratamos el correo y los datos opcionales que nos envíes para gestionar el acceso, dar seguimiento
            comercial acorde a lo que compartas y mejorar el servicio. Base: consentimiento e interés legítimo
            donde aplique. Conservación acotada al fin indicado. Puedes ejercer derechos ARCO según la ley
            aplicable escribiendo al contacto publicado en el sitio.
          </p>
        </article>
      </BlockContainer>
    </div>
  );
}

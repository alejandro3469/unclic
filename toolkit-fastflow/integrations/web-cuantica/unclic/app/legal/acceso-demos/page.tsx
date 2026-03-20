import Link from 'next/link';
import { BlockContainer } from '@/components/blocks';

export const metadata = {
  title: 'Términos y política de datos — acceso a demos',
  description: 'Términos y condiciones y política de datos personales para el acceso a las demos UnClic.',
};

/** Texto base para revisión legal; sustituir por versión aprobada. */
export default function LegalAccesoDemosPage() {
  return (
    <div className="min-h-screen bg-background py-12">
      <BlockContainer className="max-w-2xl space-y-10 text-sm leading-relaxed">
        <p>
          <Link href="/demo/access" className="text-primary hover:underline">
            ← Volver a solicitar acceso
          </Link>
        </p>
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

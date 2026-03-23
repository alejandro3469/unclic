import { site } from '@/lib/copy';

export const metadata = {
  title: 'Acceso — redirección a registro',
  description: `Registro unificado en ${site.name}: correo, verificación y portal.`,
};

export default function DemoAccessLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return children;
}

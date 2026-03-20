import { site } from '@/lib/copy';

export const metadata = {
  title: 'Iniciar sesión',
  description: `Acceso a demos y contenido restringido. ${site.name}.`,
};

export default function LoginLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return children;
}

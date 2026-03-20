import { site } from '@/lib/copy';

export const metadata = {
  title: 'Solicitar acceso',
  description: `Déjanos tu correo para acceder a las demos. ${site.name}.`,
};

export default function DemoAccessLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return children;
}

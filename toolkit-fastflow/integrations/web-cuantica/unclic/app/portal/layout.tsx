import type { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Portal demos',
  description:
    'Hub de acceso a demos internas UnClic: verificación de correo, sesión JWT y enlaces al hub pipeline y ejemplos.',
};

export default function PortalLayout({ children }: { children: React.ReactNode }) {
  return children;
}

import { redirect } from 'next/navigation';
import { routes } from '@/lib/routes';

/** Registro único: mismo flujo que el resto del sitio. */
export default function SignupPage() {
  redirect(routes.publicSignup);
}

'use client';

/**
 * Carga el widget de chat en el cliente. No usar `next/dynamic({ ssr: false })` en
 * `app/layout.tsx` (Server Component): Next 15 lo rechaza. Importar este loader desde el layout.
 */
import { ChatterAssist } from '@/components/chat/chatter-assist';

export function ChatterAssistLoader() {
  return <ChatterAssist />;
}

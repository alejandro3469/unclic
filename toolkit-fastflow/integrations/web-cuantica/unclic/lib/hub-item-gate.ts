import type { HubItem } from '@/lib/hub-links';

/** Enlaces que apuntan a infra/demo en vivo (ocultos en modo allowlist sin sesión). */
export function hubItemNeedsDemoGate(item: HubItem): boolean {
  return item.external && /^https?:\/\//i.test(item.href);
}

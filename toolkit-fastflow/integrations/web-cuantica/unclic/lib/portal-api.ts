import { getUnclicApiBase } from '@/lib/api-base';
import { getPortalJwt } from '@/lib/portal-session';

export type PortalUser = {
  email: string;
  role: 'admin' | 'user';
  emailVerified: boolean;
};

export async function fetchPortalMe(token?: string | null): Promise<{ ok: true; user: PortalUser } | { ok: false }> {
  const base = getUnclicApiBase();
  if (!base) return { ok: false };
  const t = token ?? getPortalJwt();
  if (!t) return { ok: false };
  const res = await fetch(`${base}/v1/auth/me`, {
    headers: { Authorization: `Bearer ${t}` },
  });
  if (!res.ok) return { ok: false };
  const data = (await res.json()) as { ok?: boolean; user?: PortalUser };
  if (!data.ok || !data.user) return { ok: false };
  return { ok: true, user: data.user };
}

/**
 * Adaptador HTTP síncrono hacia el microservicio @unclic/ping.
 * Patrón: anti-corruption / cliente acotado — el resto de la API no conoce detalles del ping.
 */

export type PingHealthResult =
  | { ok: true; status: number; body: unknown }
  | { ok: false; status?: number; error: string };

export async function fetchPingHealth(baseUrl: string): Promise<PingHealthResult> {
  const root = baseUrl.replace(/\/$/, '');
  const url = `${root}/health`;
  try {
    const res = await fetch(url, {
      signal: AbortSignal.timeout(5000),
      headers: { Accept: 'application/json' },
    });
    let body: unknown = null;
    try {
      body = await res.json();
    } catch {
      body = null;
    }
    if (!res.ok) {
      return { ok: false, status: res.status, error: `HTTP ${res.status}` };
    }
    return { ok: true, status: res.status, body };
  } catch (e) {
    const msg = e instanceof Error ? e.message : String(e);
    return { ok: false, error: msg };
  }
}

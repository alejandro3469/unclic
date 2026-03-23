/** Base URL del microservicio Hono (`services/api`) para fetch desde el cliente. */
export function getUnclicApiBase(): string {
  const raw = process.env.NEXT_PUBLIC_UNCLIC_API_URL?.trim();
  return raw ? raw.replace(/\/$/, '') : '';
}

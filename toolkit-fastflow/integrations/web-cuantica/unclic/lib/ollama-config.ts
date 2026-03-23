/**
 * Ollama en local o en contenedor: API HTTP en el puerto por defecto 11434.
 *
 * Build-time (NEXT_PUBLIC_*):
 * - NEXT_PUBLIC_OLLAMA_URL — ej. http://127.0.0.1:11434 (sin /api/… al final)
 * - NEXT_PUBLIC_OLLAMA_MODEL — ej. llama3.2, mistral, qwen2.5 (debe estar `ollama pull` en el servidor)
 *
 * CORS: en el host Ollama define orígenes permitidos, p. ej.:
 *   OLLAMA_ORIGINS=http://localhost:3002,http://127.0.0.1:3002
 * (variable soportada en versiones recientes; ver documentación oficial.)
 */

export function getOllamaBaseUrl(): string | undefined {
  return process.env.NEXT_PUBLIC_OLLAMA_URL?.trim().replace(/\/$/, '') || undefined;
}

export function getOllamaModel(): string {
  return process.env.NEXT_PUBLIC_OLLAMA_MODEL?.trim() || 'llama3.2';
}

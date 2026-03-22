/**
 * PLANTILLA — proxy mínimo a Ollama /openai-compatible o API nativa.
 * Ajusta path y body según doc del motor (Ollama vs DMR).
 * Ollama chat: POST /api/chat — https://github.com/ollama/ollama/blob/main/docs/api.md
 */

import type { Hono } from 'hono';

const ALLOWED = new Set(
  (process.env.LLM_ALLOWED_MODELS ?? 'llama3.2')
    .split(',')
    .map((s) => s.trim())
    .filter(Boolean)
);

export function registerLlmProxy(app: Hono) {
  app.post('/v1/ai/chat', async (c) => {
    // TODO: exigir API key interna o JWT
    const base = process.env.LLM_PROVIDER_URL?.replace(/\/$/, '');
    if (!base) {
      return c.json({ ok: false, error: 'llm_not_configured' }, 503);
    }

    let body: { model?: string; messages?: unknown[] };
    try {
      body = await c.req.json();
    } catch {
      return c.json({ ok: false, error: 'invalid_json' }, 400);
    }

    const model = body.model?.trim();
    if (!model || !ALLOWED.has(model)) {
      return c.json({ ok: false, error: 'model_not_allowed' }, 400);
    }

    const timeout = Number(process.env.LLM_TIMEOUT_MS ?? '25000');
    const ac = new AbortController();
    const t = setTimeout(() => ac.abort(), timeout);

    try {
      // Ejemplo Ollama /api/chat — cambiar si usas DMR OpenAI-compatible
      const res = await fetch(`${base}/api/chat`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ model, messages: body.messages ?? [], stream: false }),
        signal: ac.signal,
      });
      clearTimeout(t);
      if (!res.ok) {
        const text = await res.text();
        return c.json({ ok: false, error: 'upstream', detail: text }, 502);
      }
      const data = await res.json();
      return c.json({ ok: true, data });
    } catch (e) {
      clearTimeout(t);
      console.error('[llm proxy]', e);
      return c.json({ ok: false, error: 'upstream_failed' }, 502);
    }
  });
}

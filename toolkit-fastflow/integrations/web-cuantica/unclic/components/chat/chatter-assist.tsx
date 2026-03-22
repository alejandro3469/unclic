'use client';

import { useCallback, useEffect, useRef, useState } from 'react';
import { MessageCircle, Send, X } from 'lucide-react';

type Msg = { role: 'user' | 'assistant'; text: string };

function getApiBase(): string | null {
  const base = process.env.NEXT_PUBLIC_CHATTERBOT_API_URL?.trim();
  if (!base) return null;
  return base.replace(/\/$/, '');
}

export function ChatterAssist() {
  const baseUrl = getApiBase();
  const [open, setOpen] = useState(false);
  const [input, setInput] = useState('');
  const [msgs, setMsgs] = useState<Msg[]>([
    {
      role: 'assistant',
      text: 'Pregunta por UnClic, FastFlow, Jenkins, Docker o contacto. Respuestas orientativas (no sustituyen a un comercial).',
    },
  ]);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState<string | null>(null);
  const listRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!open) return;
    listRef.current?.scrollTo({ top: listRef.current.scrollHeight, behavior: 'smooth' });
  }, [msgs, open]);

  const send = useCallback(async () => {
    const text = input.trim();
    if (!text || !baseUrl || loading) return;
    setInput('');
    setErr(null);
    setMsgs((m) => [...m, { role: 'user', text }]);
    setLoading(true);
    try {
      const res = await fetch(`${baseUrl}/v1/chat`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: text }),
      });
      const data = (await res.json().catch(() => ({}))) as { reply?: string; detail?: string };
      if (!res.ok) {
        throw new Error(typeof data.detail === 'string' ? data.detail : `HTTP ${res.status}`);
      }
      const reply = data.reply?.trim() || 'Sin respuesta.';
      setMsgs((m) => [...m, { role: 'assistant', text: reply }]);
    } catch (e) {
      const msg = e instanceof Error ? e.message : 'Error de red';
      setErr(msg);
      setMsgs((m) => [
        ...m,
        {
          role: 'assistant',
          text: 'No pude contactar el asistente. Comprueba que el servicio ChatterBot está en marcha y CORS.',
        },
      ]);
    } finally {
      setLoading(false);
    }
  }, [baseUrl, input, loading]);

  if (!baseUrl) return null;

  return (
    <div className="pointer-events-none fixed bottom-4 right-4 z-[100] flex flex-col items-end gap-2 print:hidden">
      {open && (
        <div
          className="pointer-events-auto flex max-h-[min(32rem,70vh)] w-[min(100vw-2rem,22rem)] flex-col overflow-hidden rounded-2xl border border-border bg-card text-card-foreground shadow-xl"
          role="dialog"
          aria-label="Asistente UnClic"
        >
          <div className="flex items-center justify-between border-b border-border bg-primary px-3 py-2 text-primary-foreground">
            <span className="text-sm font-medium">Asistente (ChatterBot)</span>
            <button
              type="button"
              className="rounded-md p-1 hover:bg-primary-foreground/10"
              onClick={() => setOpen(false)}
              aria-label="Cerrar"
            >
              <X className="h-4 w-4" />
            </button>
          </div>
          <div
            ref={listRef}
            className="flex flex-1 flex-col gap-2 overflow-y-auto p-3 text-sm"
            style={{ maxHeight: 'min(24rem,55vh)' }}
          >
            {msgs.map((m, i) => (
              <div
                key={i}
                className={
                  m.role === 'user'
                    ? 'ml-6 rounded-lg bg-muted px-3 py-2 text-foreground'
                    : 'mr-4 rounded-lg border border-border bg-background px-3 py-2 text-muted-foreground'
                }
              >
                {m.text}
              </div>
            ))}
            {loading && <div className="text-xs text-muted-foreground">Escribiendo…</div>}
            {err && <div className="text-xs text-destructive">{err}</div>}
          </div>
          <form
            className="flex gap-2 border-t border-border p-2"
            onSubmit={(e) => {
              e.preventDefault();
              void send();
            }}
          >
            <input
              className="min-w-0 flex-1 rounded-lg border border-input bg-background px-3 py-2 text-sm text-foreground outline-none ring-ring focus:ring-2"
              placeholder="Escribe tu pregunta…"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              maxLength={2000}
              disabled={loading}
              aria-label="Mensaje"
            />
            <button
              type="submit"
              disabled={loading || !input.trim()}
              className="inline-flex shrink-0 items-center justify-center rounded-lg bg-primary px-3 py-2 text-primary-foreground disabled:opacity-50"
              aria-label="Enviar"
            >
              <Send className="h-4 w-4" />
            </button>
          </form>
        </div>
      )}
      <button
        type="button"
        onClick={() => setOpen((o) => !o)}
        className="pointer-events-auto flex h-14 w-14 items-center justify-center rounded-full bg-primary text-primary-foreground shadow-lg transition hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-ring"
        aria-expanded={open}
        aria-label={open ? 'Cerrar asistente' : 'Abrir asistente'}
      >
        <MessageCircle className="h-7 w-7" />
      </button>
    </div>
  );
}

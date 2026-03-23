/**
 * Cliente mínimo para Ollama `/api/chat` (sin streaming).
 * @see https://github.com/ollama/ollama/blob/main/docs/api.md
 */

export type OllamaChatMessage = {
  role: 'user' | 'assistant' | 'system';
  content: string;
};

export type OllamaChatResponse = {
  message?: { role: string; content: string };
  error?: string;
};

export async function ollamaChat(
  baseUrl: string,
  model: string,
  messages: OllamaChatMessage[]
): Promise<string> {
  const url = `${baseUrl.replace(/\/$/, '')}/api/chat`;
  const res = await fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      model,
      messages,
      stream: false,
    }),
  });

  const data = (await res.json().catch(() => ({}))) as OllamaChatResponse;

  if (!res.ok) {
    const msg = data.error || `HTTP ${res.status}`;
    throw new Error(msg);
  }

  const text = data.message?.content?.trim();
  if (!text) {
    throw new Error('Ollama: respuesta vacía o sin message.content');
  }
  return text;
}

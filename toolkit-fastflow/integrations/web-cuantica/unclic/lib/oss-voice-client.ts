/**
 * Cliente browser para STT/TTS OSS (Whisper, Piper, etc.) detrás de URLs públicas con CORS.
 * Contrato documentado en `services/oss-voice/README.md`.
 */

export type SttJsonResponse = {
  text: string;
  language?: string;
};

export type TtsResult =
  | { kind: 'blob'; blob: Blob; mimeType: string }
  | { kind: 'url'; url: string };

function trimBase(url: string): string {
  return url.replace(/\/$/, '');
}

/**
 * POST multipart: campo `file` (nombre compatible con FastAPI UploadFile).
 */
export async function transcribeWithOssStt(
  baseUrl: string,
  audioBlob: Blob,
  filename = 'recording.webm'
): Promise<SttJsonResponse> {
  const base = trimBase(baseUrl);
  const form = new FormData();
  form.append('file', audioBlob, filename);

  const res = await fetch(`${base}/v1/stt`, {
    method: 'POST',
    body: form,
  });

  if (!res.ok) {
    const errText = await res.text().catch(() => '');
    throw new Error(`STT ${res.status}: ${errText || res.statusText}`);
  }

  const data = (await res.json()) as SttJsonResponse;
  if (typeof data.text !== 'string') {
    throw new Error('STT: respuesta sin campo text');
  }
  return data;
}

/**
 * POST JSON { text, voice? }. Acepta cuerpo audio/* o JSON { url } / { audioBase64, mimeType? }.
 */
export async function synthesizeWithOssTts(
  baseUrl: string,
  text: string,
  voice?: string
): Promise<TtsResult> {
  const base = trimBase(baseUrl);
  const res = await fetch(`${base}/v1/tts`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ text, voice: voice || undefined }),
  });

  if (!res.ok) {
    const errText = await res.text().catch(() => '');
    throw new Error(`TTS ${res.status}: ${errText || res.statusText}`);
  }

  const ct = res.headers.get('content-type') || '';
  if (ct.includes('audio/')) {
    const blob = await res.blob();
    return { kind: 'blob', blob, mimeType: ct.split(';')[0].trim() || 'audio/wav' };
  }

  const data = (await res.json()) as { url?: string; audioBase64?: string; mimeType?: string };
  if (data.url) {
    return { kind: 'url', url: data.url };
  }
  if (data.audioBase64) {
    const mime = data.mimeType || 'audio/wav';
    const bin = Uint8Array.from(atob(data.audioBase64), (c) => c.charCodeAt(0));
    return { kind: 'blob', blob: new Blob([bin], { type: mime }), mimeType: mime };
  }

  throw new Error('TTS: respuesta no es audio ni JSON con url/audioBase64');
}

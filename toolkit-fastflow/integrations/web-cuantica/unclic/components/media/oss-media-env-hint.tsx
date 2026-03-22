'use client';

import {
  getOssSttUrl,
  getOssTtsUrl,
  getOssVideoJobUrl,
  getOssVoiceWsUrl,
} from '@/lib/oss-media';

/**
 * Muestra chips discretos si hay URLs OSS configuradas en build (NEXT_PUBLIC_*).
 */
export function OssMediaEnvHint() {
  const stt = getOssSttUrl();
  const tts = getOssTtsUrl();
  const video = getOssVideoJobUrl();
  const ws = getOssVoiceWsUrl();
  if (!stt && !tts && !video && !ws) return null;

  const chips: { key: string; label: string }[] = [];
  if (stt) chips.push({ key: 'stt', label: 'STT OSS enlazado' });
  if (tts) chips.push({ key: 'tts', label: 'TTS OSS enlazado' });
  if (video) chips.push({ key: 'video', label: 'Vídeo / jobs OSS' });
  if (ws) chips.push({ key: 'ws', label: 'WebSocket voz' });

  return (
    <div className="mt-3 flex flex-wrap justify-center gap-2 text-[11px] text-muted-foreground">
      {chips.map((c) => (
        <span
          key={c.key}
          className="rounded-full border border-border bg-muted/50 px-2 py-0.5 font-medium text-foreground/80"
        >
          {c.label}
        </span>
      ))}
    </div>
  );
}

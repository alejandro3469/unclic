/**
 * Configuración para sustituir APIs cerradas por **modelos y servicios open source**
 * (STT, TTS, vídeo). La UI (Orb, reproductor, layout tipo agente) sigue siendo React + shadcn;
 * estos endpoints son los que conectarás desde el cliente o vía tu API.
 *
 * Variables opcionales (build-time, prefijo NEXT_PUBLIC_):
 * - NEXT_PUBLIC_OSS_STT_URL — ej. proxy a faster-whisper / whisper.cpp server
 * - NEXT_PUBLIC_OSS_TTS_URL — ej. Piper HTTP, Coqui server, Bark self-hosted
 * - NEXT_PUBLIC_OSS_VIDEO_JOB_URL — opcional: cola o API que devuelve URL de vídeo generado
 * - NEXT_PUBLIC_OSS_VOICE_WS_URL — WebSocket para eventos de voz (streaming / acks)
 */

export const OSS_MEDIA_HINTS = {
  stt: ['Whisper', 'faster-whisper', 'whisper.cpp'],
  tts: ['Piper', 'Coqui TTS', 'Bark', 'OpenTTS'],
  video: ['ffmpeg', 'Stable Video Diffusion', 'AnimateDiff', 'self-hosted pipelines'],
} as const;

export function getOssSttUrl(): string | undefined {
  return process.env.NEXT_PUBLIC_OSS_STT_URL?.trim() || undefined;
}

export function getOssTtsUrl(): string | undefined {
  return process.env.NEXT_PUBLIC_OSS_TTS_URL?.trim() || undefined;
}

export function getOssVideoJobUrl(): string | undefined {
  return process.env.NEXT_PUBLIC_OSS_VIDEO_JOB_URL?.trim() || undefined;
}

export function getOssVoiceWsUrl(): string | undefined {
  return process.env.NEXT_PUBLIC_OSS_VOICE_WS_URL?.trim() || undefined;
}

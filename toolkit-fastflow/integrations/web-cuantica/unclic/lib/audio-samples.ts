import { audio } from './copy';

/**
 * Pistas de demostración **sin** CDN de demos de ElevenLabs.
 *
 * Por defecto: **SoundHelix** (música algorítmica LGPL — https://www.soundhelix.com/examples/).
 * Sustituye por ficheros en `public/audio/` (p. ej. salida de **Piper** / **Coqui**) y
 * define `NEXT_PUBLIC_AUDIO_USE_LOCAL=1` para usar `/audio/00.mp3` … según `trackTitles`.
 */
const SOUNDHELIX_BASE = 'https://www.soundhelix.com/examples/mp3';

function localTrackSrc(index: number): string {
  const n = String(index).padStart(2, '0');
  return `/audio/${n}.mp3`;
}

function remoteDemoSrc(index: number): string {
  return `${SOUNDHELIX_BASE}/SoundHelix-Song-${index + 1}.mp3`;
}

function trackSrc(index: number): string {
  const useLocal = process.env.NEXT_PUBLIC_AUDIO_USE_LOCAL === '1';
  return useLocal ? localTrackSrc(index) : remoteDemoSrc(index);
}

/** Pistas para el reproductor. Formato compatible con AudioPlayerItem. */
export const AUDIO_TRACKS = audio.trackTitles.map((title, index) => ({
  id: String(index),
  src: trackSrc(index),
  data: { title },
}));

/** Compatibilidad: lista con id numérico y title para secciones que lo usen. */
export const AUDIO_SAMPLES = AUDIO_TRACKS.map((t) => ({
  id: Number(t.id),
  title: t.data.title,
  src: t.src,
}));

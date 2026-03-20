import { audio } from './copy';

/** URLs de audio real (ElevenLabs UI demo tracks — CDN público). Sustituibles por public/audio/ o tus propias URLs. */
const ELEVENLABS_DEMO_BASE =
  'https://storage.googleapis.com/eleven-public-cdn/audio/ui-elevenlabs-io';

/** Pistas reales para el reproductor. Formato compatible con AudioPlayerItem. */
export const AUDIO_TRACKS = audio.trackTitles.map((title, index) => ({
  id: String(index),
  src: `${ELEVENLABS_DEMO_BASE}/${String(index).padStart(2, '0')}.mp3`,
  data: { title },
}));

/** Compatibilidad: lista con id numérico y title para secciones que lo usen. */
export const AUDIO_SAMPLES = AUDIO_TRACKS.map((t) => ({
  id: Number(t.id),
  title: t.data.title,
  src: t.src,
}));

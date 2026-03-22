# Audio local (OSS)

Coloca aquí `00.mp3`, `01.mp3`, … alineados con `audio.trackTitles` en `lib/copy.ts`.

Luego en `.env.local`:

```env
NEXT_PUBLIC_AUDIO_USE_LOCAL=1
```

Generación sugerida (sin ElevenLabs):

- **[Piper](https://github.com/rhasspy/piper)** — TTS ligero, muchas voces.
- **[Coqui TTS](https://github.com/coqui-ai/TTS)** — más pesado, más calidad.
- Exporta MP3 o convierte con **ffmpeg** si tu pipeline da WAV.

Las URLs remotas por defecto usan **SoundHelix** (LGPL) como demo sin API de pago.

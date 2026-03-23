# UI estilo agente / ElevenLabs UI + medios 100 % open source

## Separar tres cosas

| Capa | Qué es | Pago ElevenLabs |
|------|--------|------------------|
| **ElevenLabs UI** (blog 2026) | Librería **open source** de componentes React (shadcn): orb, ondas, chat voz, etc. Instalable con `@elevenlabs/agents-cli components add …`. | No es la API: son **piezas de interfaz**. |
| **ElevenLabs API / Agents** | Voz y agentes hospedados en su nube. | Sí (cuota). |
| **UnClic hoy** | Orb (R3F), reproductor propio (`components/ui/audio-player.tsx`), franja de ondas (`AgentWaveformStrip`), copy orientado a **OSS**. | No usamos sus paquetes npm; las demos de audio ya no vienen de su CDN. |

Objetivo: **misma sensación de producto** (multimodal, agente, audio/vídeo) **con modelos y servicios abiertos** que tú alojes o integres.

## Cómo acercarte a ElevenLabs UI sin pagar

1. **Opción A — Copiar patrones (actual UnClic)**  
   Ya tienes: Orb + lista de pistas + controles + ondas decorativas. Puedes añadir más bloques shadcn (drawer, mensajes, transcripción) siguiendo [ElevenLabs UI](https://ui.elevenlabs.io/) solo como **referencia visual**.

2. **Opción B — CLI de componentes**  
   Si quieres sus primitivos exactos:  
   `pnpm dlx @elevenlabs/agents-cli@latest components add orb` (u otros).  
   **Desacopla** cualquier prop que pida `agentId` o API key: sustituye la fuente de eventos por tu backend (Whisper, Piper, WebSocket propio).

## Audio open source (STT / TTS)

- **STT:** [Whisper](https://github.com/openai/whisper), [faster-whisper](https://github.com/SYSTRAN/faster-whisper), [whisper.cpp](https://github.com/ggerganov/whisper.cpp) + servidor HTTP fino.
- **TTS:** [Piper](https://github.com/rhasspy/piper), [Coqui TTS](https://github.com/coqui-ai/TTS), [Bark](https://github.com/suno-ai/bark) (más pesado).

En el front, define (build):

- `NEXT_PUBLIC_OSS_STT_URL` — base URL de tu proxy STT.
- `NEXT_PUBLIC_OSS_TTS_URL` — base URL de tu TTS.
- `NEXT_PUBLIC_OSS_VOICE_WS_URL` — WebSocket (`ws://` local, `wss://` en prod) para eventos de voz.
- `NEXT_PUBLIC_OSS_VIDEO_JOB_URL` — opcional, colas de render.

La sección **Audio** incluye el panel **`OssVoicePanel`**: grabación con micrófono → `POST /v1/stt`, texto → `POST /v1/tts`, y prueba de WebSocket. Cliente: `lib/oss-voice-client.ts`, hook: `hooks/use-oss-voice-ws.ts`.

### Stub de desarrollo (`services/oss-voice`)

Microservicio Python (FastAPI) con el mismo contrato que espera el front:

| Ruta | Uso |
|------|-----|
| `POST /v1/stt` | `multipart/form-data`, campo `file` |
| `POST /v1/tts` | JSON `{ "text" }` → cuerpo `audio/wav` |
| `WS /ws/voice` | Mensajes JSON (`ping`, `transcript`) |

Arranque local: `npm run dev:oss-voice` (puerto **3005**) o `docker compose up oss-voice`.

Ejemplo `.env.local`:

```env
NEXT_PUBLIC_OSS_STT_URL=http://127.0.0.1:3005
NEXT_PUBLIC_OSS_TTS_URL=http://127.0.0.1:3005
NEXT_PUBLIC_OSS_VOICE_WS_URL=ws://127.0.0.1:3005/ws/voice
```

La sección audio muestra chips si estas variables existen (`OssMediaEnvHint`).

### Ollama (LLM local)

- `NEXT_PUBLIC_OLLAMA_URL` — base `http://127.0.0.1:11434`
- `NEXT_PUBLIC_OLLAMA_MODEL` — ej. `llama3.2` (tras `ollama pull` en el servidor)

Panel de chat: `OllamaChatPanel`. Detalle CORS, Docker e imagen (Stable Diffusion vs catálogo Ollama): [OLLAMA-UNClic-LLM-E-IMAGEN.md](OLLAMA-UNClic-LLM-E-IMAGEN.md).

Pistas del reproductor:

- Remoto por defecto: **SoundHelix** (LGPL).
- Local: `public/audio/00.mp3…` + `NEXT_PUBLIC_AUDIO_USE_LOCAL=1` (ver `public/audio/README.md`).

## Vídeo open source

- Edición y contenedores: **ffmpeg** (imprescindible en pipelines).
- Generación: según GPU y licencias — **Stable Video Diffusion**, **AnimateDiff**, etc. (ver [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md), capa L19).

La demo de vídeo del sitio usa **Big Buck Bunny** (open movie) como MP4 de ejemplo.

## Referencias

- [ElevenLabs UI — blog / getting started](https://elevenlabs.io/blog/introducing-elevenlabs-ui) (componentes OSS).
- Código UnClic: `lib/oss-media.ts`, `lib/audio-samples.ts`, `components/media/`, `components/sections/audio-section.tsx`.

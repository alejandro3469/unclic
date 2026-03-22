# OSS Voice — stub STT / TTS / WebSocket

Contrato alineado con la landing UnClic (`lib/oss-voice-client.ts`):

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/v1/stt` | `multipart/form-data`, campo **`file`**. Respuesta JSON `{ "text", "language?" }`. |
| `POST` | `/v1/tts` | JSON `{ "text", "voice?" }`. Respuesta **`audio/wav`** (binario). |
| `WS` | `/ws/voice` | JSON por mensaje: `ping` → `pong`; `transcript` → `ack`. |

## Local

```bash
cd services/oss-voice
python -m venv .venv && source .venv/bin/activate
pip install -r requirements.txt
CORS_ORIGINS=http://localhost:3002,http://127.0.0.1:3002 uvicorn app.main:app --reload --port 3005
```

`.env.local` en la raíz de `unclic`:

```env
NEXT_PUBLIC_OSS_STT_URL=http://127.0.0.1:3005
NEXT_PUBLIC_OSS_TTS_URL=http://127.0.0.1:3005
NEXT_PUBLIC_OSS_VOICE_WS_URL=ws://127.0.0.1:3005/ws/voice
```

## Sustituir por Whisper / Piper

- Sustituye el cuerpo de `speech_to_text` por tu inferencia (faster-whisper, etc.).
- Sustituye `text_to_speech` por llamada a Piper o devolución de bytes desde Coqui.
- En streaming, emite mensajes WebSocket desde el mismo proceso o un worker.

## Docker Compose

Desde `unclic/`: `docker compose up -d --build oss-voice`

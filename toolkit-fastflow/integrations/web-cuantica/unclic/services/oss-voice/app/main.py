"""
Stub HTTP + WebSocket para desarrollo: mismo contrato que puedes implementar
con Whisper (STT) y Piper (TTS) detrás de este servicio o en rutas distintas.

Contrato (landing UnClic):
  POST /v1/stt  — multipart field "file" (audio webm/wav/…) → {"text": "...", "language": "?"}
  POST /v1/tts  — JSON {"text": "...", "voice": "?"} → audio/wav (cuerpo binario)
  WS  /ws/voice — canal demo (ping/transcript/ack)
"""

from __future__ import annotations

import io
import json
import math
import os
import struct
import wave

from fastapi import FastAPI, File, HTTPException, UploadFile, WebSocket, WebSocketDisconnect
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import Response
from pydantic import BaseModel, Field


def _cors_origins() -> list[str]:
    raw = os.environ.get("CORS_ORIGINS", "http://localhost:3002,http://127.0.0.1:3002")
    return [o.strip() for o in raw.split(",") if o.strip()]


def _sine_wav(duration_sec: float = 0.45, freq: float = 440.0, rate: int = 22050) -> bytes:
    """WAV mono 16-bit (stub TTS — sustituye por Piper/Coqui)."""
    n = int(rate * duration_sec)
    buf = io.BytesIO()
    with wave.open(buf, "wb") as w:
        w.setnchannels(1)
        w.setsampwidth(2)
        w.setframerate(rate)
        frames = bytearray()
        for i in range(n):
            sample = int(32767 * 0.18 * math.sin(2 * math.pi * freq * i / rate))
            frames.extend(struct.pack("<h", sample))
        w.writeframes(bytes(frames))
    return buf.getvalue()


class TtsBody(BaseModel):
    text: str = Field(..., min_length=1, max_length=5000)
    voice: str | None = Field(None, max_length=120)


app = FastAPI(title="UnClic OSS Voice stub", version="0.1.0")
app.add_middleware(
    CORSMiddleware,
    allow_origins=_cors_origins(),
    allow_credentials=False,
    allow_methods=["GET", "POST", "OPTIONS"],
    allow_headers=["*"],
)


@app.get("/health")
def health():
    return {"ok": True, "service": "oss-voice-stub"}


@app.post("/v1/stt")
async def speech_to_text(file: UploadFile = File(...)):
    """Stub STT: cuenta bytes. Sustituye por faster-whisper / whisper.cpp server."""
    try:
        data = await file.read()
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"read_failed: {e!s}") from e
    name = file.filename or "audio"
    snippet = (
        f"(stub STT) Recibido «{name}», {len(data)} bytes. "
        "Conecta Whisper aquí y devuelve JSON {{\"text\":\"...\",\"language\":\"es\"}}."
    )
    return {"text": snippet, "language": "es", "stub": True}


@app.post("/v1/tts")
async def text_to_speech(body: TtsBody):
    """Stub TTS: tono sinusoidal. Sustituye por Piper HTTP o Coqui."""
    # Longitud del audio muy ligera según tamaño del texto (solo demo)
    dur = min(2.0, max(0.35, len(body.text) / 800))
    wav = _sine_wav(duration_sec=dur, freq=523.25 if body.voice else 440.0)
    return Response(content=wav, media_type="audio/wav")


@app.websocket("/ws/voice")
async def voice_websocket(websocket: WebSocket):
    """Canal bidireccional demo para eventos de voz (transcripciones parciales, etc.)."""
    await websocket.accept()
    await websocket.send_json(
        {
            "type": "ready",
            "message": "Canal OSS (stub). Envía {\"type\":\"ping\"} o {\"type\":\"transcript\",\"text\":\"...\"}.",
        }
    )
    try:
        while True:
            raw = await websocket.receive_text()
            try:
                msg = json.loads(raw)
            except json.JSONDecodeError:
                await websocket.send_json({"type": "error", "message": "JSON inválido"})
                continue
            mtype = msg.get("type")
            if mtype == "ping":
                await websocket.send_json({"type": "pong", "stub": True})
            elif mtype == "transcript":
                text = msg.get("text", "")
                await websocket.send_json(
                    {"type": "ack", "echo": text, "note": "En prod, el servidor enviaría deltas desde Whisper streaming."}
                )
            else:
                await websocket.send_json({"type": "error", "message": f"tipo desconocido: {mtype}"})
    except WebSocketDisconnect:
        return

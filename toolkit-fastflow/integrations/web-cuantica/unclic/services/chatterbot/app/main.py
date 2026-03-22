"""
Servicio HTTP mínimo para exponer ChatterBot a la landing UnClic (Next export).
El front llama con CORS a esta URL (NEXT_PUBLIC_CHATTERBOT_API_URL).
"""

from __future__ import annotations

import os
from typing import Any

from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

# Carga perezosa del bot para que la app arranque aunque falle la importación en dev
_chatbot: Any = None


def _cors_origins() -> list[str]:
    raw = os.environ.get("CORS_ORIGINS", "http://localhost:3002,http://127.0.0.1:3002")
    return [o.strip() for o in raw.split(",") if o.strip()]


def _get_bot():
    global _chatbot
    if _chatbot is not None:
        return _chatbot
    try:
        from chatterbot import ChatBot
        from chatterbot.trainers import ListTrainer
    except ImportError as e:
        raise RuntimeError(
            "ChatterBot no instalado. Ejecuta: pip install -r requirements.txt"
        ) from e

    db_path = os.environ.get("CHATTERBOT_DB_PATH", "./data/unclic_chatterbot.sqlite3")
    os.makedirs(os.path.dirname(db_path) or ".", exist_ok=True)
    uri = f"sqlite:///{os.path.abspath(db_path)}"

    bot = ChatBot(
        "UnClicAssistant",
        storage_adapter="chatterbot.storage.SQLStorageAdapter",
        database_uri=uri,
        # BestMatch: respuestas por similitud sobre el corpus entrenado (FAQs).
        logic_adapters=["chatterbot.logic.BestMatch"],
    )

    # Entrenamiento inicial una sola vez (volumen Docker / disco persistente).
    data_dir = os.path.dirname(os.path.abspath(db_path)) or "."
    os.makedirs(data_dir, exist_ok=True)
    marker = os.path.join(data_dir, ".chatterbot_bootstrapped")
    if not os.path.isfile(marker):
        trainer = ListTrainer(bot)
        # Cada `train([entrada, respuesta])` es un intercambio independiente (FAQ).
        faq_pairs = [
            ("Hola", "Hola. ¿En qué puedo ayudarte sobre UnClic, integración o pipelines?"),
            ("Buenos días", "Buenos días. Pregunta por despliegue, Jenkins, Docker o contacto."),
            ("Qué es UnClic", "UnClic es consultoría y entrega técnica en integración, POS y automatización (CI/CD, registro de imágenes, demos)."),
            ("Qué hacéis", "Ayudamos a encajar POS, generic model y pipelines (Jenkins, Docker, registry) con tu operación."),
            ("FastFlow", "FastFlow aquí es la implementación a medida del flujo: pipeline, registry y despliegue alrededor de pos-online y entornos JDE/Oracle."),
            ("Jenkins", "Jenkins suele orquestar build Maven, imagen Docker y push al registry; el detalle depende de tu job y credenciales."),
            ("Docker", "Docker empaqueta la app para reproducir builds y despliegues; encaja con registry y agentes Jenkins."),
            ("Registry", "El registry guarda tags de imagen para promover versiones y rollback controlado."),
            ("Contacto", "Usa la página de contacto o el formulario de la landing para que el equipo responda con contexto."),
            ("Precios", "Los rangos y ofertas están orientados a servicios; lo concreto depende del alcance. Te derivamos a contacto comercial."),
            ("Gracias", "De nada. Si necesitas algo más técnico, deja el mensaje en contacto."),
        ]
        for user_line, bot_line in faq_pairs:
            trainer.train([user_line, bot_line])
        with open(marker, "w", encoding="utf-8") as f:
            f.write("1\n")

    _chatbot = bot
    return _chatbot


app = FastAPI(title="UnClic ChatterBot bridge", version="0.1.0")
app.add_middleware(
    CORSMiddleware,
    allow_origins=_cors_origins(),
    allow_credentials=False,
    allow_methods=["POST", "GET", "OPTIONS"],
    allow_headers=["*"],
)


class ChatBody(BaseModel):
    message: str = Field(..., min_length=1, max_length=4000)


@app.get("/health")
def health():
    return {"ok": True, "service": "chatterbot-bridge"}


@app.post("/v1/chat")
def chat(body: ChatBody):
    text = body.message.strip()
    if not text:
        raise HTTPException(status_code=400, detail="empty_message")
    try:
        bot = _get_bot()
        reply = bot.get_response(text)
        return {"reply": str(reply), "confidence": float(getattr(reply, "confidence", 0) or 0)}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"chatterbot_error: {e!s}") from e

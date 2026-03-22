# ChatterBot — puente HTTP para UnClic

Expone [ChatterBot](https://github.com/gunthercox/ChatterBot) por HTTP para que la **landing Next.js en modo `output: 'export'`** pueda mostrar un asistente flotante (el export **no** incluye rutas `app/api`).

## Cuándo usar esto (estrategia)

- **Sí:** FAQs estables, respuestas entrenables sin API de LLM de pago, demo offline / entorno cerrado.
- **No:** conversación abierta larga, razonamiento complejo o multilingüe pesado — ahí encaja mejor un LLM + RAG (ver plan OSS del repo).

## Desarrollo local

```bash
cd services/chatterbot
python -m venv .venv && source .venv/bin/activate  # Windows: .venv\Scripts\activate
pip install -r requirements.txt
export CORS_ORIGINS="http://localhost:3002,http://127.0.0.1:3002"
export CHATTERBOT_DB_PATH="./data/unclic_chatterbot.sqlite3"
uvicorn app.main:app --reload --port 8765
```

En la raíz de `unclic`, crea `.env.local`:

```env
NEXT_PUBLIC_CHATTERBOT_API_URL=http://127.0.0.1:8765
```

Arranca Next: `npm run dev` (puerto 3002).

## Docker Compose

Desde `unclic/`: `docker compose up -d --build chatterbot`

## Endpoints

- `GET /health`
- `POST /v1/chat` — body JSON `{ "message": "..." }` → `{ "reply": "...", "confidence": number }`

## Entrenamiento

Edita las listas en `app/main.py` (`ListTrainer`) o añade corpus vía [chatterbot-corpus](https://github.com/gunthercox/chatterbot-corpus) si lo necesitas.

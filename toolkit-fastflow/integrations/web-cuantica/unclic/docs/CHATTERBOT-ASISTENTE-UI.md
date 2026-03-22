# ChatterBot en UnClic — uso estratégico con interfaces

[ChatterBot](https://github.com/gunthercox/ChatterBot) es un **motor de diálogo en Python** (respuestas por similitud sobre conversaciones entrenadas). **No dibuja UI**: la estrategia aquí es **separar**:

| Capa | Qué es |
|------|--------|
| **UI** | `components/chat/chatter-assist.tsx` — botón flotante + panel (Next.js, tema Nord). |
| **API HTTP** | `services/chatterbot/` — FastAPI + ChatterBot + CORS. |
| **Despliegue** | Sitio con `output: 'export'` llama al backend por URL pública (igual que el API Node con CORS). |

## Por qué así

- El export estático **no** sirve `app/api/*` en producción; el asistente debe ir a un **origen explícito** (`NEXT_PUBLIC_CHATTERBOT_API_URL`).
- ChatterBot encaja como **FAQ / primer contacto** entrenable sin depender de un LLM de pago.
- Para conversación abierta o RAG documental, valorar el stack OSS del plan maestro (LLM + vectores) en paralelo.

## Activar en local

1. Terminal A — servicio Python:

   ```bash
   npm run dev:chatterbot
   ```

   (o `docker compose up chatterbot`)

2. En `unclic/.env.local`:

   ```env
   NEXT_PUBLIC_CHATTERBOT_API_URL=http://127.0.0.1:8765
   ```

3. Terminal B — sitio:

   ```bash
   npm run dev
   ```

   Debe aparecer el botón flotante; las peticiones van a `POST /v1/chat`.

## Producción

- Despliega el contenedor `chatterbot` (o el binario detrás de Nginx) con **HTTPS**.
- Ajusta `CORS_ORIGINS` al dominio real de la landing.
- Rebuild de la landing con `NEXT_PUBLIC_CHATTERBOT_API_URL=https://api.tu-dominio.com` (o subdominio dedicado).

## Reentrenar / ampliar corpus

- Edita los pares en `services/chatterbot/app/main.py` (`faq_pairs`).
- Borra el volumen Docker `chatterbot_data` **o** elimina `data/unclic_chatterbot.sqlite3` y el marcador `.chatterbot_bootstrapped` en el mismo directorio para forzar bootstrap de nuevo.

## Referencias

- Repositorio: [gunthercox/ChatterBot](https://github.com/gunthercox/ChatterBot)
- Documentación: [docs.chatterbot.us](https://docs.chatterbot.us/)

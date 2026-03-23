# Ollama en UnClic (LLM local) e imagen (Stable Diffusion, etc.)

## Ollama — qué es

[Ollama](https://ollama.com) ejecuta **modelos open source** en tu máquina o servidor y expone una **API HTTP** (`/api/chat`, `/api/generate`, …). La landing puede hablar con él desde el navegador si configuras **CORS** (`OLLAMA_ORIGINS`) y variables `NEXT_PUBLIC_*`.

### En UnClic

- Panel **«Ollama — chat local»** en la sección Audio (`components/media/ollama-chat-panel.tsx`).
- Cliente: `lib/ollama-client.ts` (POST `/api/chat`, sin streaming).
- Config: `lib/ollama-config.ts`.

### Variables `.env.local`

```env
NEXT_PUBLIC_OLLAMA_URL=http://127.0.0.1:11434
NEXT_PUBLIC_OLLAMA_MODEL=llama3.2
```

Instalación app macOS / Linux: [Download Ollama](https://ollama.com/download). Luego:

```bash
ollama pull llama3.2
```

### CORS (imprescindible desde el front)

En el **proceso** que sirve Ollama, permite el origen del sitio (puerto 3002 en dev), p. ej.:

```bash
export OLLAMA_ORIGINS=http://localhost:3002,http://127.0.0.1:3002
```

Con **Docker Compose** en este repo, el servicio `ollama` incluye `OLLAMA_ORIGINS` por defecto para esos orígenes.

### Docker

```bash
docker compose up -d ollama
docker compose exec ollama ollama pull llama3.2
```

En Mac con chip Apple, a veces es más cómodo la **app nativa** que el contenedor.

---

## Imagen: Stable Diffusion (CompVis) vs Ollama

- **[CompVis/stable-diffusion](https://github.com/CompVis/stable-diffusion)** es el proyecto de referencia en **PyTorch** (LDM, checkpoints `.ckpt`, scripts `txt2img.py`, licencia CreativeML Open RAIL-M). Suele ir con **GPU**, conda/diffusers o interfaces tipo ComfyUI / Automatic1111.

- **Ollama** concentra **modelos empaquetados** en su catálogo; puede incluir modelos **de imagen** según lo publiquen ahí, pero **no sustituye** automáticamente el flujo clásico de CompVis.

**Recomendación práctica**

| Necesidad | Camino típico |
|-----------|----------------|
| Chat / razonamiento local | Ollama + `llama3.2`, `mistral`, etc. |
| Imagen estilo SD «clásico» | diffusers / ComfyUI / A1111 + checkpoints |
| Unificar despliegue | Evaluar qué modelos de imagen expone [ollama.com/library](https://ollama.com/library) en tu hardware |

---

## Referencias

- [Ollama API](https://github.com/ollama/ollama/blob/main/docs/api.md)
- Plan capa L19: [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md)
- UI OSS: [UI-OSS-MEDIA-ELEVENLABS-STYLE.md](UI-OSS-MEDIA-ELEVENLABS-STYLE.md)

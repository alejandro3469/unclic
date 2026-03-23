# L19 — Ollama en Docker + PromptFoo (ejemplo copiable)

**Objetivo:** tener **inferencia local** lista en minutos y una **evaluación mínima** de prompts contra Ollama **sin adivinar** rutas ni variables.

**Relacionado:** [07-PROXY-LLM-OLLAMA-DMR.md](07-PROXY-LLM-OLLAMA-DMR.md) · [ejemplos/llm-proxy-snippet.example.ts](ejemplos/llm-proxy-snippet.example.ts) · [../OLLAMA-UNClic-LLM-E-IMAGEN.md](../OLLAMA-UNClic-LLM-E-IMAGEN.md) · [../L19-FIRESHIP-7-HERRAMIENTAS-IA-OSS-SELF-HOST-Y-INTERACCION.md](../L19-FIRESHIP-7-HERRAMIENTAS-IA-OSS-SELF-HOST-Y-INTERACCION.md)

---

## 1. Archivos

| Archivo | Uso |
|---------|-----|
| [16-DOCKER-COMPOSE-L19-OLLAMA.example.yml](16-DOCKER-COMPOSE-L19-OLLAMA.example.yml) | Servicio `ollama` + servicio opcional `promptfoo` (perfil `eval`). |
| [ejemplos/promptfooconfig.l19-ollama.example.yaml](ejemplos/promptfooconfig.l19-ollama.example.yaml) | Config PromptFoo: proveedor `ollama:chat:llama3.2:1b` y 2 tests. |

---

## 2. Arranque (desde `docs/implementacion/`)

Los volúmenes del compose usan `./ejemplos`; **el cwd debe ser esta carpeta** (o edita las rutas del `volumes:`).

```bash
cd docs/implementacion

# 1) Levantar solo Ollama
docker compose -f 16-DOCKER-COMPOSE-L19-OLLAMA.example.yml up -d ollama

# 2) Descargar modelo pequeño (CPU-friendly para prueba)
docker compose -f 16-DOCKER-COMPOSE-L19-OLLAMA.example.yml exec ollama ollama pull llama3.2:1b

# 3) Probar API desde el host (opcional)
curl -s http://127.0.0.1:11434/api/tags | head
```

**DoD Ollama:** `curl http://127.0.0.1:11434/api/tags` devuelve JSON con el modelo listado.

---

## 3. PromptFoo dentro de Docker (misma red que Ollama)

Usa el perfil **`eval`**: el contenedor `promptfoo` recibe `OLLAMA_BASE_URL=http://ollama:11434` (nombre DNS interno de Compose).

```bash
cd docs/implementacion

docker compose -f 16-DOCKER-COMPOSE-L19-OLLAMA.example.yml --profile eval run --rm promptfoo
```

Esto ejecuta equivalente a `npx promptfoo@latest eval -c promptfooconfig.l19-ollama.example.yaml -j 1` (`-j 1` = serial, menos RAM).

**DoD PromptFoo:** la CLI termina con éxito y muestra resultados de los dos tests (Francia / México).

---

## 4. PromptFoo desde el host (sin contenedor Node)

Si ya tienes Node 20+ en el host y Ollama escuchando en `11434`:

```bash
cd docs/implementacion/ejemplos
export OLLAMA_BASE_URL=http://127.0.0.1:11434
npx promptfoo@latest eval -c promptfooconfig.l19-ollama.example.yaml -j 1
```

Si ves `ECONNREFUSED` con `localhost`, usa **`127.0.0.1`** (IPv4), según [doc PromptFoo Ollama](https://www.promptfoo.dev/docs/providers/ollama/).

---

## 5. Integración con OpenViking + Ollama (host o Docker)

OpenViking puede usar modelos vía **LiteLLM** con prefijo `ollama/...` (ver README de [OpenViking](https://github.com/volcengine/OpenViking)). Patrón:

1. Mantén Ollama en Docker (este compose) o binario en el host.
2. En la máquina donde corre **Python + OpenViking**, apunta el proveedor LiteLLM a `OLLAMA_BASE_URL` (p. ej. `http://IP-LAN:11434` o túnel VPN).
3. **No expongas** `11434` a Internet sin TLS y política clara; preferible red privada + proxy con auth ([07-PROXY-LLM-OLLAMA-DMR.md](07-PROXY-LLM-OLLAMA-DMR.md)).

No incluimos compose de OpenViking aquí: su stack (Go, C++, Python) evoluciona rápido; sigue el README oficial y usa este Ollama como **dependencia HTTP**.

---

## 6. Fusionar con el compose principal del monorepo

En [04-DOCKER-COMPOSE-STACK-OPCIONAL.example.yml](04-DOCKER-COMPOSE-STACK-OPCIONAL.example.yml) ya hay un bloque `ollama` comentado/referenciado. Puedes:

- **Copiar** el servicio `ollama` + volumen de `16-…yml` a tu `docker-compose.yml` real, **o**
- Usar **varios ficheros:** `docker compose -f docker-compose.yml -f docs/implementacion/16-DOCKER-COMPOSE-L19-OLLAMA.example.yml up -d ollama`  
  (ajusta rutas si ejecutas desde otra carpeta; los volúmenes relativos deben resolverse desde el **primer** `-f` o unifica rutas absolutas).

En `services/api`, variable típica: `OLLAMA_URL=http://ollama:11434` (solo red Docker).

---

## 7. Jenkins / CI (idea mínima)

Job que:

1. Levanta Ollama en runner con GPU opcional **o** usa runner con modelo ya cacheado.
2. `docker compose … run --rm promptfoo` o `npx promptfoo eval …` con `OLLAMA_BASE_URL` apuntando al servicio.

Mantén **timeouts** altos en CPU (`REQUEST_TIMEOUT_MS` en env, ver doc PromptFoo).

---

*Ejemplos `.example`; versiona imágenes y modelos según política de tu equipo.*

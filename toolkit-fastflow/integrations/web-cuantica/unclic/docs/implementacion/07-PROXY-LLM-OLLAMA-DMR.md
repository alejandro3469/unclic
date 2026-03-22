# Proxy LLM (Ollama / Docker Model Runner) desde el API

## Principio

- El navegador **no** llama directo a Ollama/DMR en prod.
- Solo **`@unclic/api`** (o worker) en red privada habla con el endpoint de inferencia.
- **Timeouts** cortos; **allowlist** de modelos; **sin** datos fiscales/PII sin política firmada.

## URLs típicas (verificar en tu instalación)

| Motor | Base URL típica | Documentación |
|-------|-----------------|---------------|
| Ollama | `http://ollama:11434` (Docker) o `http://127.0.0.1:11434` | [github.com/ollama/ollama](https://github.com/ollama/ollama) |
| Docker Model Runner | Según TCP habilitado — ver | [docs.docker.com/ai/model-runner/api-reference/](https://docs.docker.com/ai/model-runner/api-reference/) |

**No fijamos puerto DMR en este doc** porque depende de tu Desktop/Linux; usa la referencia oficial actual.

## Variables

```bash
LLM_PROVIDER_URL=http://ollama:11434
LLM_ALLOWED_MODELS=llama3.2,gemma2:2b
LLM_TIMEOUT_MS=25000
```

## Ruta API sugerida

`POST /v1/ai/chat` — body mínimo `{ "model": "...", "messages": [...] }` — reenviar solo si `model` está en allowlist.

Snippet: [ejemplos/llm-proxy-snippet.example.ts](ejemplos/llm-proxy-snippet.example.ts).

## Autenticación

- Protege `/v1/ai/*` con **API key** header o JWT (Keycloak) antes de producción.
- Rate limit agresivo (abuso = coste CPU/GPU).

## Cumplimiento

Relee principio OSS seguridad en [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](../PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) §1: OSS no es “más seguro” automáticamente; aquí el riesgo es **filtración de datos** en el prompt.

# L19 — Plan granular: 7 herramientas IA OSS (Fireship, mar. 2026) — self-host, alternativas e interacción

**Fuente del listado:** vídeo *“7 new open source AI tools…”* (Fireship, ~mar. 2026). Los nombres del transcript suelen llegar con typos: **PromptFoo** (no “PropmtFoo”), **MiroFish** (no “MicroFish”).

**Capa UnClic:** todo encaja en **L19 — IA / ML / agentes** (opcional). Ver también [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) §L19, [OLLAMA-UNClic-LLM-E-IMAGEN.md](OLLAMA-UNClic-LLM-E-IMAGEN.md) y [implementacion/07-PROXY-LLM-OLLAMA-DMR.md](implementacion/07-PROXY-LLM-OLLAMA-DMR.md).

**Reglas transversales (sin adivinar en prod):**

1. **Red:** servicios con LLM o memoria **no** expuestos a Internet sin **TLS + auth** (API key, OIDC, VPN, o solo `127.0.0.1`).
2. **Datos:** PII, fiscal, clínicos → **política explícita** antes de enviar texto a ningún modelo (local o cloud).
3. **Coste:** MiroFish + APIs cloud pueden **disparar facturación**; prueba con límites bajos (p. ej. &lt;40 rondas en su README).
4. **Cumplimiento:** cada proyecto tiene **licencia y TOS** distintas; revisa antes de integrar en cliente.

---

## 0. Tabla rápida — ¿qué es “self-hosted” aquí?

| Herramienta | ¿Servidor propio? | Naturaleza real | Interacción principal |
|-------------|-------------------|-----------------|------------------------|
| **Agency Agents** | No (es contenido) | Repo de **prompts / perfiles** para IDEs | Copiar reglas → Cursor, Claude Code, Copilot… |
| **PromptFoo** | Parcial (CLI local; UI local) | **Evaluación** de prompts y modelos | CLI, `npx`, CI, resultados en navegador local |
| **MiroFish** | **Sí** (stack app) | Simulación **multi-agente** + UI web | Docker Compose o Node+Python; HTTP :3000 / :5001 |
| **NanoChat** | **Sí** (código + GPU) | **Entrenamiento / inferencia** tipo GPT pequeño | Python, scripts, chat UI del repo |
| **Impeccable** | No (es “skill”) | **Diseño UI** para asistentes de código | Instalación en agente (Cursor, Claude, etc.) |
| **Heretic** | **Sí** (local, GPU) | **Investigación** sobre pesos de modelos | CLI Python; ver § ética/legal |
| **OpenViking** | **Sí** (librería + servicios) | **Base de contexto** tipo filesystem para agentes | `pip`, CLI Rust opcional, LiteLLM→Ollama |

---

## 1. Patrones de instalación (elige columna por herramienta)

| Modo | Cuándo usar | Ejemplo genérico |
|------|-------------|------------------|
| **A. Binario / app en host** | Dev en laptop o servidor bare metal | `brew install`, descarga `.dmg`, `curl \| bash` |
| **B. Contenedor Docker** | Reproducibilidad, mismo stack en VPS | `docker compose up -d` en repo del proyecto |
| **C. Gestor de paquetes** | Librerías y CLIs | `pip install`, `npm i -g`, `cargo install`, `uv sync` |
| **D. Solo archivos en repo** | Skills, prompts, reglas | `git clone` + copiar a `.cursor/rules` o docs internos |
| **E. PaaS / nube del vendor** | Cuando aceptas SaaS (no “self-host” puro) | Consolas de API keys (OpenAI, Volcengine, Zep…) |

---

## 2. Patrones de interacción con tu stack (UnClic / API / n8n)

| Vía | Descripción | Encaje típico |
|-----|-------------|----------------|
| **HTTP interno** | Tu `services/api` hace de **proxy** hacia Ollama/DMR/OpenViking | [07-PROXY-LLM-OLLAMA-DMR.md](implementacion/07-PROXY-LLM-OLLAMA-DMR.md) |
| **Webhook** | **n8n** self-host llama a tu API o al puerto del LLM en red Docker | L19 + colas L8 |
| **CLI en CI** | **PromptFoo** en GitHub Actions / Jenkins en merge | Calidad de prompts sin UI pública |
| **IDE** | Agency Agents + Impeccable como **contexto** del desarrollador | No toca producción hasta que copies patrones a código |
| **SDK Python/Node** | OpenViking `pip`, LangGraph, etc. | Worker aparte del bundle Next |

---

## 3. Herramienta por herramienta (pasos granulares)

### 3.1 Agency Agents

- **Qué es:** colección de **agentes especializados** (prompts / instrucciones) para usar dentro de **Claude Code, Cursor, Windsurf, Aider, Copilot**, etc. No levanta un microservicio.
- **Repo de referencia:** [github.com/msitarzewski/agency-agents](https://github.com/msitarzewski/agency-agents) (comprobar rama y licencia vigentes).

**Instalación / “hosting” (alternativas):**

| Opción | Pasos |
|--------|--------|
| **D — Clone en disco** | `git clone …/agency-agents.git` → leer `README` → copiar el markdown del rol que necesites. |
| **D — Fork interno** | Fork en tu Gitea → submodule en monorepo → política de actualización trimestral. |
| **D — Empaquetado** | Exportar 2–3 agentes a `.cursor/rules/*.mdc` o a plantillas de equipo. |

**Interacción:**

1. Abrir el archivo del agente (p. ej. “Backend Architect”).
2. Pegar o referenciar en el IDE (“activa este rol”).
3. Iterar en **tu** repo; commits normales.

**DoD:** al menos un agente probado en un issue real; reglas internas documentadas (qué agente para qué tipo de ticket).

---

### 3.2 PromptFoo

- **Qué es:** **evaluación** y comparación de prompts / modelos (tests, regresiones, datasets).
- **Docs:** [promptfoo.dev/docs/installation](https://www.promptfoo.dev/docs/installation/)

**Instalación (alternativas):**

| Opción | Comando / notas |
|--------|------------------|
| **C — npm global** | `npm install -g promptfoo` (Node **20.20+** o **22.22+** según doc oficial). |
| **C — sin instalar** | `npx promptfoo@latest …` |
| **C — Homebrew** | `brew install promptfoo` (macOS/Linux si está disponible en tu entorno). |
| **C — pip** | `pip install promptfoo` (si tu equipo estandariza Python). |

**Uso mínimo:**

```bash
mkdir ~/pofo-demo && cd ~/pofo-demo
npx promptfoo@latest init
npx promptfoo@latest eval
# Ver informe local en navegador cuando la CLI lo indique
```

**Interacción:**

- **CLI** → CI (`promptfoo eval` en Jenkins/GitHub Actions).
- **Archivos** → `promptfooconfig.yaml` versionado junto al producto que usa LLM.
- **Proveedores** → API cloud o **Ollama local** (config en YAML según doc “providers”).

**DoD:** un job CI que falle si la regresión de calidad supera umbral acordado.

---

### 3.3 MiroFish (vídeo: “MicroFish”)

- **Qué es:** motor de **simulación multi-agente** (grafos, memoria, informes). Stack **frontend + backend**.
- **Repo:** [github.com/666ghj/MiroFish](https://github.com/666ghj/MiroFish) · README en inglés: [README-EN.md](https://github.com/666ghj/MiroFish/blob/main/README-EN.md).

**Prerrequisitos típicos:** Node 18+, Python 3.11–3.12, **uv**; claves **LLM** (OpenAI-compatible) y **Zep** en `.env` (ver `.env.example` del repo).

**Instalación (alternativas):**

| Opción | Pasos |
|--------|--------|
| **B — Docker (recomendado para PoC)** | `cp .env.example .env` → rellenar keys → `docker compose up -d` → UI **:3000**, API **:5001**. |
| **A+C — Fuente** | `npm run setup:all` o `npm run setup` + `npm run setup:backend` → `npm run dev`. |
| **Alternativa memoria** | Si no quieres Zep cloud, busca forks tipo **MiroFish-Offline** o integraciones **Mem0** (cookbook en docs Mem0) — validar mantenimiento antes de producción. |

**Interacción:**

- **Navegador** → UI propia.
- **HTTP** → API backend (integrar solo tras **auth** y red privada).
- **Con UnClic:** no hay integración por defecto; patrón sano = **VPN** o **subdominio interno** + reverse proxy (Traefik/Caddy) + sin PII de clientes reales en demos.

**DoD:** compose levanta; un informe de prueba generado con &lt;40 rondas; coste de API revisado.

---

### 3.4 NanoChat (Karpathy)

- **Qué es:** harness **educativo/experimental** para **tokenizar, preentrenar, fine-tunear, evaluar e inferir** modelos tipo GPT en **una máquina con GPU** (enfoque “hackable”).
- **Repo:** [github.com/karpathy/nanochat](https://github.com/karpathy/nanochat)

**Instalación (alternativas):**

| Opción | Pasos |
|--------|--------|
| **C — entorno Python** | Clonar repo → crear venv → `pip install -e .` o seguir README vigente (dependencias PyTorch/CUDA). |
| **A — GPU dedicada** | Cloud GPU (RunPod, Lambda, tu VPS con GPU) o estación local NVIDIA. |
| **No Docker oficial obligatorio** | Si empaquetas tú: Dockerfile multistage + volumen para checkpoints (mantenimiento tuyo). |

**Interacción:**

- **Scripts** del repo (`scripts/`, `nanochat/`) según README.
- **Chat UI** incluida en el flujo del proyecto (sigue doc actual).
- **Con Ollama:** son caminos distintos: NanoChat = **entrenar/tu modelo**; Ollama = **ejecutar** modelos ya empaquetados.

**DoD:** un run corto documentado (dataset, tiempo, coste GPU); decisión explícita “investigación” vs “producto”.

---

### 3.5 Impeccable

- **Qué es:** **skill / guía de diseño** (tipografía, color, motion, UX writing) para que los asistentes de código generen UI menos genérica.
- **Repo / web:** [github.com/pbakaus/impeccable](https://github.com/pbakaus/impeccable) · [impeccable.style](https://impeccable.style)

**Instalación (alternativas):**

| Opción | Pasos |
|--------|--------|
| **D — Descarga para agente** | Seguir instrucciones del sitio para **Cursor, Claude Code, Gemini CLI, Codex CLI**. |
| **D — Repo interno** | Vendor del skill en carpeta `docs/design-skills/` y enlazar desde onboarding del equipo. |

**Interacción:** comandos tipo `/audit`, `/polish` **dentro del IDE** (no es API HTTP de producto).

**DoD:** checklist de “pre-ship” UI aplicado en 1 componente real del repo.

---

### 3.6 Heretic

- **Qué es:** herramienta de **investigación** en Python que aplica técnicas de **abliteración** sobre pesos de modelos transformer (tema sensible: altera alineamiento del modelo).
- **Repo de referencia:** [github.com/p-e-w/heretic](https://github.com/p-e-w/heretic) · PyPI: paquetes tipo `heretic-llm` (verificar nombre vigente en PyPI).

**Advertencia UnClic:**

- Solo donde **tengas derecho** a modificar y redistribuir el modelo (licencia, términos del proveedor, ley local).
- **No** documentamos uso para eludir controles en sistemas ajenos ni para contenido ilícito.
- Tratar como **laboratorio offline**, no como dependencia de un SaaS de cliente sin revisión legal.

**Instalación (alternativas):**

| Opción | Pasos |
|--------|--------|
| **C — venv** | `python -m venv .venv && source .venv/bin/activate` → instalar según README del repo. |
| **A — GPU local** | CUDA según versión PyTorch; tiempo típico largo según hardware. |

**Interacción:** **CLI** y notebooks; salida = **nuevos pesos** o checkpoints; cargar solo en entornos controlados.

**DoD:** acta interna de “modelo base + licencia + propósito investigación”; sin despliegue público sin revisión.

---

### 3.7 OpenViking

- **Qué es:** **base de datos de contexto** para agentes: memoria, recursos y skills bajo un **paradigma tipo filesystem** (`viking://` URIs, niveles L0/L1/L2, trazas de recuperación).
- **Repo:** [github.com/volcengine/OpenViking](https://github.com/volcengine/OpenViking)

**Prerrequisitos (README):** Python ≥3.10, **Go ≥1.22** (componentes AGFS), compilador C++ razonablemente moderno.

**Instalación (alternativas):**

| Opción | Pasos |
|--------|--------|
| **C — pip** | `pip install openviking --upgrade` (ver doc oficial por flags exactos). |
| **C — CLI Rust (opcional)** | Script `install.sh` del repo o `cargo install --git https://github.com/volcengine/OpenViking ov_cli`. |
| **B — contenedor** | Si el proyecto publica compose oficial, úsalo; si no, construye imagen propia a partir del Dockerfile del repo (revisar carpeta `docker/` o CI). |

**Modelos (embeddings + VLM):** el README lista proveedores **Volcengine**, **OpenAI**, **LiteLLM**. Para **self-host puro de LLM**:

- Configurar **LiteLLM** apuntando a **Ollama** (`ollama/...` según [documentación LiteLLM](https://docs.litellm.ai/docs/providers)).

**Interacción:**

- **Python SDK** desde tu worker de agentes.
- **CLI** `ov_cli` para operaciones batch o debugging.
- **Con UnClic:** mismo patrón que Ollama — **solo red privada** + capa auth en tu API.

**DoD:** un notebook o script que cree contexto, recupere por URI y registre la traza; política de retención definida.

---

## 4. Orden sugerido de PoC (una semana de labor)

| Día | Objetivo | Herramientas |
|-----|----------|--------------|
| 1 | Evaluación de prompts en CI | PromptFoo |
| 2 | Mejora de UI vía IDE | Impeccable + 1 agente de Agency Agents |
| 3 | Inferencia local estable | Ollama o DMR (ya en plan UnClic) |
| 4 | Contexto estructurado para un agente demo | OpenViking + LiteLLM→Ollama |
| 5 | Simulación multi-agente (presupuesto API) | MiroFish en Docker |
| 6–7 | Reservado para NanoChat **o** Heretic según GPU y **aprobación** interna |

---

## 5. Enlaces cruzados en este repo

- **Compose Ollama + PromptFoo (ejecutable):** [implementacion/16-L19-OLLAMA-DOCKER-Y-PROMPTFOO.md](implementacion/16-L19-OLLAMA-DOCKER-Y-PROMPTFOO.md) · [implementacion/16-DOCKER-COMPOSE-L19-OLLAMA.example.yml](implementacion/16-DOCKER-COMPOSE-L19-OLLAMA.example.yml) · [implementacion/ejemplos/promptfooconfig.l19-ollama.example.yaml](implementacion/ejemplos/promptfooconfig.l19-ollama.example.yaml)
- Proxy LLM: [implementacion/07-PROXY-LLM-OLLAMA-DMR.md](implementacion/07-PROXY-LLM-OLLAMA-DMR.md)
- Snippet proxy: [implementacion/ejemplos/llm-proxy-snippet.example.ts](implementacion/ejemplos/llm-proxy-snippet.example.ts)
- Ollama + CORS: [OLLAMA-UNClic-LLM-E-IMAGEN.md](OLLAMA-UNClic-LLM-E-IMAGEN.md)
- Curación duplicados IA: [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](CURACION-DUPLICADOS-Y-ELECCION-UNClic.md) §8

---

*Documento de planificación. Versiones y APIs de terceros cambian: siempre confirma en el README y la documentación oficial de cada proyecto.*

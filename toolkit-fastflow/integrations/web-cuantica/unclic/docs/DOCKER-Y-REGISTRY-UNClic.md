# Docker y registry en el stack UnClic / FastFlow

Las **soluciones** UnClic (asesoría, pipeline gestionado, hub, despliegue) asumen **contenedores** como formato de entrega: build en Jenkins → **push** a un **registry** compatible con Docker/OCI → pull en servidor o orquestador. Este documento alinea el mensaje comercial con referencias **oficiales** de Docker y del proyecto Distribution.

---

## 1. Qué es el registry en una frase

Un **registry** es el servicio que **almacena y distribuye** imágenes de contenedor según la especificación **OCI Distribution**. La imagen oficial en Docker Hub implementa ese estándar; es el mismo patrón que usan registros privados en EC2 o en la nube.

- **Imagen oficial:** [registry en Docker Hub](https://hub.docker.com/_/registry) — etiquetas `3`, `latest`; ~17 MB; arquitecturas amd64, arm64, etc.
- **Código:** [distribution/distribution](https://github.com/distribution/distribution) · spec: [opencontainers/distribution-spec](https://github.com/opencontainers/distribution-spec)
- **Documentación de despliegue:** [Distribution docs](https://distribution.github.io/distribution/) (casos más allá de `localhost:5000`)

**Arranque mínimo local (referencia upstream):**

```bash
docker run -d -p 5000:5000 --restart always --name registry registry:3
```

En FastFlow/UnClic el flujo típico es: `docker build` → `docker tag` → `docker push` al registry del entorno → deploy con tag fijo para **rollback**.

---

## 2. Dónde encaja en cada solución UnClic

| Solución (sitio) | Papel de Docker / registry |
|------------------|----------------------------|
| **Asesoría & pipeline** | Dockerfile multi-stage, cache, seguridad básica de imágenes; Jenkins publica al registry acordado. |
| **Registry & despliegue** | Registry privado (imagen oficial o servicio gestionado), tags por entorno, promoción dev→staging→prod. |
| **Pipeline gestionado (outsourcing)** | Operación del registry (espacio, HTTPS, auth), backups de metadatos, limpieza de tags. |
| **Plataforma / hub** | Enlace a demo de registry; narrativa “mismo flujo que en producción”. |
| **Capacidad** [artefactos-registry](PATRON-HUB-CAPACIDADES-SEQUOIA.md) | Imágenes como artefacto de release. |

---

## 3. Ecosistema Docker (para docs y formación cliente)

| Recurso | Uso |
|---------|-----|
| [docker.com](https://www.docker.com) | Posicionamiento producto: Desktop, Hub, Scout, supply chain, agentes/MCP. |
| [docs.docker.com](https://docs.docker.com/) | Manuales: Engine, Dockerfile, Build, Compose, despliegue. |
| [Docker Hub — Official Images](https://hub.docker.com/search?image_filter=official) | Bases endurecidas y servicios estándar (registry, nginx, etc.). |

No es obligatorio vender Docker Desktop; sí dejar claro que el **runtime** de referencia es **compatible con el ecosistema Docker** (CLI, `docker compose`, mismas imágenes).

---

## 4. Mensaje en web y LinkedIn (coherencia)

- **Keywords ya usadas en sitio:** `registry Docker`, `Docker registry`, pilares PIPELINE + REGISTRY + DEPLOY.
- **Especialidades LinkedIn:** incluir *Docker*, *OCI*, *container registry* junto a Jenkins/Gitea/AWS.
- **Diferenciación:** UnClic no sustituye a Docker Hub público; ofrece **pipeline + registry privado + despliegue** alineado a retail/POS.

---

## 5. Toolkit FastFlow (repo padre)

Guías de Jenkins, registry en EC2, HTTPS y pipeline están en `toolkit-fastflow/docs/` y `docs/pipeline-y-registry/`. Este archivo enlaza el **lenguaje del sitio UnClic** con las **fuentes oficiales** anteriores para propuestas y onboarding técnico.

---

## Enlaces rápidos

- [registry — Official Image](https://hub.docker.com/_/registry)  
- [Docker Docs](https://docs.docker.com/)  
- [Docker](https://www.docker.com)

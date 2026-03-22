# Docker — Por qué, open source y cómo replicar

## Por qué lo usamos

- **Contenedores:** Gitea se ejecuta en Docker; el pipeline puede construir imágenes del POS y subirlas a un registry (Docker Registry open source).
- **Reproducibilidad:** Mismo entorno en EC2 y en local; menos “en mi máquina funciona”.
- **Objetivo de automatización:** Open source only; Docker Engine y Docker Registry son open source.

## Open source

- **Proyecto:** [Docker Engine](https://www.docker.com/products/docker-engine/), [Distribution (Registry)](https://github.com/distribution/distribution). Licencia: Apache 2.0.

## Cómo replicar

| Consola | Comando o acción |
|---------|-------------------|
| Terminal (EC2) | `sudo yum install -y docker` (o seguir [docs Docker para Amazon Linux](https://docs.docker.com/engine/install/)) |
| Terminal (EC2) | `sudo systemctl start docker && sudo systemctl enable docker` |
| Terminal (EC2) | `docker compose up -d` en el directorio del compose de Gitea |

## Enlaces

- [INSTALAR-GITEA-SELF-HOSTED](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md) (Gitea vía Docker)
- [Pipeline y registry](../40-pipeline-registry/)

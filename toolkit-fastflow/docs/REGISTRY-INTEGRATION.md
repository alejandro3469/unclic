# Integración de Registry en Jenkins (Toolkit v1)

Esta guía implementa el primer módulo evolutivo: `Containerize + Push Registry`.

## Objetivo

Generar imagen Docker en `main`, etiquetar por commit y branch, y publicar en registry autenticado.

## Variables requeridas en Jenkins

- `ENABLE_REGISTRY=true`
- `REGISTRY_HOST` (ej: `docker.io`, `ghcr.io`, `<account>.dkr.ecr.<region>.amazonaws.com`)
- `REGISTRY_REPOSITORY` (ej: `webcuantica/fastflow-api`)
- `REGISTRY_CREDENTIALS_ID` (credencial tipo username/password)

## Tags que publica el pipeline

- `<image>:<short-sha>`
- `<image>:<branch-normalizada>`
- `<image>:latest`

Recomendación operativa:
- Promoción formal por `sha` o tag inmutable.
- `latest` solo como conveniencia.

## Fuentes oficiales de referencia

- Docker tag: https://docs.docker.com/reference/cli/docker/image/tag/
- Docker build best practices: https://docs.docker.com/build/building/best-practices/
- Docker immutable tags: https://docs.docker.com/docker-hub/repos/manage/hub-images/immutable-tags/
- Jenkins Pipeline syntax: https://www.jenkins.io/doc/book/pipeline/syntax/

## Checklist de validación

1. Build Docker exitoso.
2. Login de registry exitoso.
3. Push de tags `sha`, `branch`, `latest`.
4. Imagen visible en registry.
5. Deploy consume tag trazable (no solo `latest`).

# `.jenkins/` — FastFlow POS Online (Jenkins + IaC auxiliar)

## Resumen

Esta carpeta **no** sustituye al `Jenkinsfile` de la raíz del repo: el `Jenkinsfile` sigue siendo la entrada del job. Aquí vive todo lo **auxiliar**:

- **Plan de diseño** para no adivinar (`PLAN-FASTFLOW-PIPELINE-IAC.md`).
- **Groovy** reutilizable cargado con `load` (`groovy/fastflowIacHelpers.groovy`).
- **Scripts bash** con comentarios finos (`scripts/*.sh`) que el pipeline invoca en stages opcionales.

## Orden de lectura

1. `PLAN-FASTFLOW-PIPELINE-IAC.md` — mapa mental y variables.
2. `../Jenkinsfile` — cabecera + bloque `environment` + stages `IaC: ...`.
3. `scripts/README.md` — qué hace cada script y requisitos.
4. `../deploy/terraform/README.md` — POS en Kubernetes.
5. `../deploy/terraform-localstack/README.md` — laboratorio AWS API.
6. `../deploy/pulumi/README.md` — Pulumi + LocalStack opcional.

## Conexión con otros repos / carpetas

| Fuera de `repo-pos-fastflow` | Relación |
|-----------------------------|----------|
| `unclic/` (landing) | URLs públicas hacia Jenkins/registry/POS vía `NEXT_PUBLIC_DEMO_*`. |
| `toolkit-fastflow/docs/` | Guías maestras (Cloudcraft, índice integración). |
| `delivery/pos-online/` | Copia o espejo del POS; el Jenkinsfile canónico vive aquí en `repo-pos-fastflow`; ver `delivery/pos-online/PIPELINE-SOURCE.md`. |

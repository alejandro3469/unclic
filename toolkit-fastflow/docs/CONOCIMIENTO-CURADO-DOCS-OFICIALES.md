# Conocimiento Curado desde Documentación Oficial

Fecha de curación: 2026-03-10

Objetivo:
- Definir decisiones técnicas del toolkit Fast Flow con base en documentación oficial.
- Reducir ambigüedad en implementación CI/CD y operación.

## Fuentes oficiales consultadas

### Jenkins
- Pipeline as Code: https://www.jenkins.io/doc/book/pipeline/pipeline-as-code/
- Jenkinsfile: https://www.jenkins.io/doc/book/pipeline/jenkinsfile/
- Pipeline Syntax: https://www.jenkins.io/doc/book/pipeline/syntax/

### Docker
- Image tag reference: https://docs.docker.com/reference/cli/docker/image/tag/
- Build best practices: https://docs.docker.com/build/building/best-practices/
- Immutable tags (Docker Hub): https://docs.docker.com/docker-hub/repos/manage/hub-images/immutable-tags/

### Kubernetes
- Deployments: https://kubernetes.io/docs/concepts/workloads/controllers/deployment/
- Probes (liveness/readiness/startup): https://kubernetes.io/docs/concepts/configuration/liveness-readiness-startup-probes/
- kubectl rollout status: https://kubernetes.io/docs/reference/kubectl/generated/kubectl_rollout/kubectl_rollout_status/

### Helm
- helm upgrade: https://helm.sh/docs/helm/helm_upgrade/

### Terraform
- Providers overview: https://developer.hashicorp.com/terraform/language/providers
- Terraform block (required_providers): https://developer.hashicorp.com/terraform/language/terraform
- terraform apply: https://developer.hashicorp.com/terraform/cli/commands/apply

## Decisiones técnicas del toolkit (derivadas de las fuentes)

## 1) Jenkins como orquestador principal

Decisión:
- Mantener Pipeline as Code con `Jenkinsfile` versionado en repo.
- Usar pipeline modular (`load` por stage) para escalabilidad operativa.

Aplicación en toolkit:
- `templates/common/Jenkinsfile`
- `templates/common/.jenkins/*`

Motivo:
- Facilita auditoría, revisión por PR y handoff entre equipos.

## 2) Artefactos reproducibles y trazables

Decisión:
- Generar artefactos con nombres explícitos por entorno y/o commit.
- Archivar artefactos al final de package.

Aplicación:
- Stage `Package` basado en patrón POS Online.
- Política sugerida: no depender solo de `latest` para imagen Docker.

Motivo:
- Permite rollback responsable y trazabilidad de release.

## 3) Etiquetado de imágenes Docker

Decisión:
- Etiquetar al menos con:
  - commit SHA corto
  - branch normalizada
  - `latest` (solo para conveniencia, no para promoción formal)

Aplicación:
- Stage `Containerize + Push Registry` del Jenkinsfile base.

Motivo:
- La referencia oficial de tags y prácticas de build favorece versionado explícito y control de cache/entorno.

## 4) Inmutabilidad de tags para producción

Decisión:
- En ambientes críticos, activar política de tags inmutables en el registry cuando sea posible.

Aplicación:
- Recomendación operativa del toolkit (governance de release).

Motivo:
- Evita sobrescrituras accidentales y mejora auditoría.

## 5) Deploy en Kubernetes con señales de salud

Decisión:
- Definir `readinessProbe` y `livenessProbe` como mínimos de producción.
- Agregar `startupProbe` en apps con arranque lento.

Aplicación:
- Manifiestos base y chart Helm del toolkit.

Motivo:
- Kubernetes usa probes para enrutar tráfico correctamente y reiniciar contenedores no saludables.

## 6) Validación explícita de rollout

Decisión:
- Después de aplicar manifiestos/chart, ejecutar validación con `kubectl rollout status`.

Aplicación:
- Paso obligatorio en runbook de deploy.

Motivo:
- Evita declarar “deploy exitoso” sin confirmar estado real del rollout.

## 7) Helm como mecanismo estándar de promoción

Decisión:
- Usar `helm upgrade --install` con values por entorno.

Aplicación:
- `manifests/helm/fastflow-chart`

Motivo:
- Permite despliegue idempotente y parametrizable por ambiente.

## 8) Terraform con control de providers y apply responsable

Decisión:
- Declarar `required_providers` en bloque `terraform`.
- Usar secuencia explícita: `init -> plan -> apply`.

Aplicación:
- `manifests/terraform/main.tf` (base a completar por cliente/proyecto)

Motivo:
- Reduce deriva de entorno y hace reproducible la infraestructura.

## Checklist mínimo de calidad (para todo proyecto)

1. Pipeline as Code versionado.
2. Build, test y lint antes de package.
3. Artefacto identificable por versión/commit.
4. Imagen Docker etiquetada (sha + branch).
5. Push a registry autenticado.
6. Deploy con validación de salud.
7. Rollout verificado (Kubernetes).
8. Infraestructura declarada (Terraform) para ambientes cloud.

## Riesgos que evita este enfoque

- Releases sin trazabilidad.
- Rollbacks por adivinanza.
- Drift de infraestructura.
- Deploy “verde” sin verificación real de salud.
- Dependencia excesiva de memoria humana.

## Nota de alcance

Este documento define una base confiable y reusable.
La implementación final por cliente depende de:
- stack real,
- restricciones de red/compliance,
- madurez operativa,
- y objetivos de negocio del piloto.

# Citas y Referencias Base (Landing, Product Page, Webinar)

Objetivo:
- Tener una base única de referencias curadas para usar en:
  - landing page
  - product page
  - webinar
  - proposal PDF
  - footer del sitio

Principios:
1. Prioridad 1: documentación oficial (vendor docs).
2. Prioridad 2: libros/autores expertos (cuando aportan criterio operativo).
3. Prioridad 3: artículos complementarios (solo si no contradicen fuentes oficiales).
4. Toda afirmación técnica crítica debe tener fuente trazable.

## Formatos recomendados de cita

## A) Formato web corto (para footer o sección de referencias)

- Jenkins Pipeline as Code — https://www.jenkins.io/doc/book/pipeline/pipeline-as-code/
- Jenkins Pipeline Syntax — https://www.jenkins.io/doc/book/pipeline/syntax/
- Jenkinsfile docs — https://www.jenkins.io/doc/book/pipeline/jenkinsfile/
- Docker image tag reference — https://docs.docker.com/reference/cli/docker/image/tag/
- Docker build best practices — https://docs.docker.com/build/building/best-practices/
- Docker immutable tags — https://docs.docker.com/docker-hub/repos/manage/hub-images/immutable-tags/
- Kubernetes Deployments — https://kubernetes.io/docs/concepts/workloads/controllers/deployment/
- Kubernetes probes — https://kubernetes.io/docs/concepts/configuration/liveness-readiness-startup-probes/
- kubectl rollout status — https://kubernetes.io/docs/reference/kubectl/generated/kubectl_rollout/kubectl_rollout_status/
- Helm upgrade — https://helm.sh/docs/helm/helm_upgrade/
- Terraform providers overview — https://developer.hashicorp.com/terraform/language/providers
- Terraform block — https://developer.hashicorp.com/terraform/language/terraform
- Terraform apply — https://developer.hashicorp.com/terraform/cli/commands/apply

## B) Formato APA (para PDF/propuesta/documento largo)

Ejemplos base (ajustar fecha de consulta cuando se publique):

- Jenkins Project. (2026). *Pipeline as Code*. https://www.jenkins.io/doc/book/pipeline/pipeline-as-code/
- Jenkins Project. (2026). *Pipeline Syntax*. https://www.jenkins.io/doc/book/pipeline/syntax/
- Docker, Inc. (2026). *docker image tag reference*. https://docs.docker.com/reference/cli/docker/image/tag/
- Kubernetes Authors. (2026). *Deployments*. https://kubernetes.io/docs/concepts/workloads/controllers/deployment/
- HashiCorp. (2026). *terraform apply command*. https://developer.hashicorp.com/terraform/cli/commands/apply

## C) Formato “claims + source” (para ventas técnicas)

Patrón:
- Claim: [afirmación concreta].
- Fuente oficial: [link].
- Aplicación en Fast Flow: [cómo lo implementamos].

Ejemplo:
- Claim: Kubernetes Deployment habilita actualizaciones declarativas de Pods/ReplicaSets.
- Fuente oficial: https://kubernetes.io/docs/concepts/workloads/controllers/deployment/
- Aplicación en Fast Flow: Deploy estándar con rollout status y rollback.

## Plantilla para autores/libros expertos (cuando los compartas)

- Autor:
- Libro:
- Año/edición:
- Capítulo/tema aplicable:
- Principio extraído:
- Evidencia práctica en nuestro toolkit:
- ¿Alineado con docs oficiales?: Sí/No
- Uso recomendado: (Landing / Product page / Webinar / PDF técnico)

## Uso recomendado por superficie

- Landing page: 5-10 referencias clave (enlace corto).
- Product page: referencias por bloque técnico (registry, k8s, terraform).
- Footer: lista breve "Fuentes técnicas" con link.
- Webinar/PDF: sección final "Referencias" en formato APA o lista técnica.

## Nota de rigor

Las referencias de autores expertos se usan como respaldo de criterio y framing.
Las decisiones de implementación se anclan primero en documentación oficial vigente.

## Fuentes expertas curadas (libros/autores)

- Archivo base de extracción: `toolkit-fastflow/docs/FUENTES-EXPERTAS-CURADAS.md`
- Regla: no publicar cita formal incompleta si falta autor/título/editorial.
- Uso permitido temporal: "Capítulo experto compartido (Sep 2021)" como respaldo de framing, no como única base técnica.

## Fuentes expertas incorporadas

- Fuente experta 001: Chapter 2 — Pipeline as Code with Jenkins (Sep 2021)
- Fuente experta 002: Part 2 — Operating a self-healing Jenkins cluster (Sep 2021)
- Fuente experta 003: Chapter 3 — Defining Jenkins architecture (Sep 2021)

Referencia de extracción:
- `toolkit-fastflow/docs/FUENTES-EXPERTAS-CURADAS.md`

- Fuente experta 004: Chapter 4 — Baking machine images with Packer (Sep 2021)
  - Autor: Mohamed Labouardy
  - Libro: Pipeline as Code

- Fuente experta 005: Chapter 5 — Discovering Jenkins as code with Terraform (Sep 2021)
  - Autor: Mohamed Labouardy
  - Libro: Pipeline as Code

- Fuente experta 006: Chapter 6 — Deploying HA Jenkins on multiple cloud providers (Sep 2021)
  - Autor: Mohamed Labouardy
  - Libro: Pipeline as Code

- Fuente experta 007: Chapter 7 — Defining a pipeline as code for microservices (Sep 2021)
  - Autor: Mohamed Labouardy
  - Libro: Pipeline as Code

- Fuente experta 008: About this Book — Re-Engineering Legacy Software (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 009: Chapter 1 — Understanding the challenges of legacy projects (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 010: Chapter 2 — Finding your starting point (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 011: Chapter 3 — Preparing to refactor (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 012: Chapter 4 — Refactoring (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 013: Chapter 5 — Re-architecting (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 014: Chapter 6 — The Big Rewrite (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 015: Chapter 7 — Automating the development environment (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 016: Chapter 8 — Extending automation to test, staging, and production environments (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 017: Chapter 9 — Modernizing the development, building, and deployment of legacy software (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 018: Chapter 10 — Stop writing legacy code! (Apr 2016)
  - Autor: Chris Birchall
  - Libro: Re-Engineering Legacy Software

- Fuente experta 019: Chapter 1 — Microservices security landscape (Jul 2020)
  - Autores: Prabath Siriwardena y Nuwan Dias
  - Libro: Microservices Security in Action

- Fuente experta 020: Chapter 2 — First steps in securing microservices (Jul 2020)
  - Autores: Prabath Siriwardena y Nuwan Dias
  - Libro: Microservices Security in Action

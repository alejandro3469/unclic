# Guía de Proveedores Open Source para Microservicios

## Objetivo
Seleccionar una base tecnológica abierta para construir y operar microservicios con costos controlados y alta portabilidad.

## Criterios de selección
1. Madurez y comunidad activa.
2. Facilidad de operación en producción.
3. Compatibilidad con Kubernetes y contenedores.
4. Integración con observabilidad y seguridad.
5. Curva de aprendizaje del equipo.

## Matriz por categoría (Open Source)

| Categoría | Opciones recomendadas | Cuándo elegirlo |
|---|---|---|
| API Gateway | `Kong OSS`, `Traefik`, `Apache APISIX`, `Spring Cloud Gateway` | Cuando necesitas enrutamiento, auth básica, rate limiting y observabilidad en entrada |
| Mensajería / eventos | `RabbitMQ`, `Apache Kafka`, `NATS` | `RabbitMQ` para colas y workflows de negocio; `Kafka` para alto volumen y replay; `NATS` para baja latencia |
| Service discovery/config | `Consul`, `Eureka`, `etcd` | Discovery interno y resolución dinámica de servicios |
| Orquestación de contenedores | `Kubernetes`, `Helm`, `Kustomize` | Estandarizar despliegues y escalar servicios |
| Service mesh | `Istio`, `Linkerd` | mTLS, control de tráfico, retries/circuit-breaking a nivel red |
| Observabilidad (métricas) | `Prometheus`, `Grafana`, `Alertmanager` | Métricas, alertas y tableros operativos |
| Observabilidad (logs) | `OpenSearch + OpenSearch Dashboards`, `ELK`, `Loki` | Búsqueda de logs y análisis operacional |
| Observabilidad (tracing) | `Jaeger`, `Zipkin`, `Tempo` | Trazabilidad distribuida end-to-end |
| Seguridad e identidad | `Keycloak`, `Ory`, `Vault OSS` | OAuth2/OIDC, gestión de identidades y secretos |
| CI/CD | `Jenkins`, `Argo CD`, `Tekton`, `GitLab CE` | Pipelines y despliegues continuos |

## Stack OSS de referencia (recomendado)
- Gateway: `Kong OSS` o `Spring Cloud Gateway`
- Mensajería: `RabbitMQ`
- Runtime: `Kubernetes`
- Observabilidad: `Prometheus + Grafana + Loki + Tempo`
- Seguridad: `Keycloak + Vault OSS`
- CI/CD: `Jenkins + Argo CD`

## Blueprint de adopción por etapa

## Etapa 1: PoC (2-6 semanas)
- `Docker Compose`
- `RabbitMQ`
- `Spring Boot`/`Python` + API Gateway
- Métricas mínimas con `Prometheus` + `Grafana`

## Etapa 2: Preproducción
- Migrar a `Kubernetes`
- Integrar `Keycloak` y `Vault`
- Trazas distribuidas (`Jaeger` o `Tempo`)
- Alertas operativas (`Alertmanager`)

## Etapa 3: Producción
- Alta disponibilidad por zonas
- GitOps (`Argo CD`)
- Hardening de red y mTLS (`Istio` o `Linkerd`)
- SLO/SLI y runbooks por servicio

## Riesgos comunes y mitigación
- Riesgo: alta carga operativa interna.
  - Mitigación: platform team pequeño + plantillas de despliegue.
- Riesgo: divergencia entre equipos.
  - Mitigación: "chassis"/boilerplate por lenguaje + estándares.
- Riesgo: upgrades complejos.
  - Mitigación: calendario de actualización trimestral y ambientes espejo.

## Checklist de decisión OSS
1. ¿Tu equipo puede operar la plataforma 24/7?
2. ¿Tienes owner técnico por categoría (mensajería, observabilidad, seguridad)?
3. ¿El costo de operación interna es menor al costo SaaS equivalente?
4. ¿Necesitas soberanía de datos o despliegue on-prem regulado?

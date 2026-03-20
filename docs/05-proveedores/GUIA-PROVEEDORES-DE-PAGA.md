# Guía de Proveedores de Paga para Microservicios

## Objetivo
Acelerar salida a producción reduciendo carga operativa mediante servicios administrados y soporte empresarial.

## Criterios de selección
1. Tiempo de puesta en marcha.
2. SLA y soporte (24/7, tiempos de respuesta).
3. Costo total (licencia + operación + talento).
4. Integraciones nativas con tu nube y stack actual.
5. Riesgo de lock-in y estrategia de salida.

## Matriz por categoría (de paga)

| Categoría | Proveedores de paga (ejemplos) | Cuándo elegirlo |
|---|---|---|
| API Management/Gateway | `AWS API Gateway`, `Apigee`, `Azure API Management`, `Kong Konnect/Enterprise` | Necesitas gobierno de APIs, portal de consumidores, políticas y analytics avanzados |
| Mensajería administrada | `Amazon MQ (RabbitMQ/ActiveMQ)`, `CloudAMQP`, `Confluent Cloud`, `Aiven` | Quieres colas/streams sin operar clusters ni upgrades complejos |
| Kubernetes administrado | `EKS`, `GKE`, `AKS`, `Red Hat OpenShift` | Operación enterprise con seguridad y escalado gestionados |
| Observabilidad | `Datadog`, `New Relic`, `Dynatrace`, `Splunk Observability`, `Elastic Cloud` | Necesitas trazas/métricas/logs unificados con menor tiempo de integración |
| Identidad y acceso | `Auth0`, `Okta`, `Microsoft Entra ID`, `Ping Identity` | OAuth2/OIDC empresarial con SSO y gobierno de identidades |
| Secrets management | `HCP Vault`, `AWS Secrets Manager`, `Azure Key Vault`, `Google Secret Manager` | Gestión de secretos y rotación administrada |
| CI/CD | `GitHub Actions`, `GitLab SaaS`, `CircleCI`, `Harness`, `CloudBees` | Pipelines listos y menor mantenimiento de infraestructura CI |

## Combinaciones recomendadas por perfil de cliente

## Perfil A: Startup/B2B en crecimiento
- `GKE` o `EKS`
- `CloudAMQP` (RabbitMQ)
- `Datadog`
- `Auth0`
- `GitHub Actions`

## Perfil B: Empresa regulada
- `AKS`/`OpenShift`
- `Amazon MQ` o `Confluent Cloud`
- `Splunk` o `Dynatrace`
- `Okta`/`Entra ID`
- `GitLab SaaS Premium/Ultimate`

## Perfil C: Escala global y alto tráfico
- Multi-región con `EKS/GKE`
- `Confluent Cloud` para eventos críticos
- Observabilidad unificada (`Datadog`/`Dynatrace`)
- API governance (`Apigee` o equivalente)

## RabbitMQ en esquema de paga (recomendado)
- Proveedores típicos: `CloudAMQP` o `Amazon MQ (RabbitMQ)`.
- Configuración mínima:
  - `topic exchange` por dominio
  - `retry queue` con `TTL`
  - `DLX` + `DLQ`
  - métricas de depth/lag/ack-rate y alertas
- Cuándo conviene: cuando el equipo quiere enfocarse en producto y no en operar broker.

## Riesgos comunes y mitigación
- Riesgo: lock-in del proveedor.
  - Mitigación: contratos por interfaz, IaC portable, pruebas de salida.
- Riesgo: sobrecosto por crecimiento rápido.
  - Mitigación: presupuestos por entorno, topes y alertas de consumo.
- Riesgo: dependencias propietarias difíciles de migrar.
  - Mitigación: mantener núcleo en estándares abiertos (HTTP, OpenAPI, AMQP, OIDC).

## Checklist de decisión de paga
1. ¿Cuánto cuesta operar esto in-house vs SaaS administrado?
2. ¿Qué SLA necesitas (RTO/RPO)?
3. ¿Qué nivel de soporte exige negocio/compliance?
4. ¿La salida a producción en <90 días es prioridad?
5. ¿Tienes plan de salida si cambia costo o estrategia del proveedor?

## Nota
Los nombres anteriores son referencia técnica. Valida precios, regiones y SLA en el momento de compra con cada proveedor.

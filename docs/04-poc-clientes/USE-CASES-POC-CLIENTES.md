# Use Cases de PoC para Presentar a Nuevos Clientes

## Objetivo
Disponer de casos de uso demostrables, basados en los ejemplos ya curados, para ejecutar pruebas de concepto (PoC) de 2 a 6 semanas con resultados medibles.

## Formato recomendado por PoC
- `Problema del cliente`
- `Caso de uso`
- `Qué se demuestra`
- `Alcance mínimo (MVP de PoC)`
- `Métrica de éxito`
- `Entregable para comité`

---

## Estándar de mensajería para PoC (RabbitMQ)
- Broker recomendado: `RabbitMQ` para flujos asíncronos de negocio.
- Patrón base:
  - `topic exchange` para eventos de dominio (`order.*`, `payment.*`, `risk.*`).
  - Colas por consumidor (`risk-alert`, `billing`, `notifications`).
  - `dead-letter exchange (DLX)` + `dead-letter queue (DLQ)` para mensajes fallidos.
  - Reintentos con cola de retry y `TTL` antes de reencolar.
- Criterios técnicos mínimos:
  - Consumidores idempotentes.
  - `ack/nack` explícito.
  - Trazabilidad por `correlation_id`.
  - Métricas de colas: profundidad, tasa de consumo, tasa de errores.

---

## UC-01: Alta de orden de venta en arquitectura de microservicios
- Problema del cliente: el flujo de venta depende de un sistema monolítico con releases lentos.
- Caso de uso: capturar una orden de venta y orquestarla entre `orders`, `accounts`, `fees` y `market-gateway`.
- Qué se demuestra: desacoplamiento por capacidades de negocio y despliegue independiente.
- Alcance mínimo (MVP de PoC):
  - API para crear orden de venta.
  - Reserva de posición en cuenta.
  - Cálculo y cobro de comisión.
  - Envío de orden a mercado (stub o sandbox).
- Métrica de éxito:
  - `p95` de latencia end-to-end por debajo del objetivo acordado.
  - 0 inconsistencias de estado entre orden y reserva en pruebas funcionales.
- Entregable para comité: diagrama de secuencia + demo en vivo + reporte de tiempos.

## UC-02: Cambio de regla de comisiones sin afectar otros servicios
- Problema del cliente: cada cambio regulatorio obliga a un release completo.
- Caso de uso: modificar cálculo de comisión solo en `fees-service`.
- Qué se demuestra: independencia de despliegue y reducción de riesgo de cambio.
- Alcance mínimo (MVP de PoC):
  - Versionado de endpoint o regla por feature flag.
  - Pruebas de compatibilidad hacia consumidores.
- Métrica de éxito:
  - 1 despliegue independiente exitoso sin downtime.
  - Sin cambios de contrato en `orders-service`.
- Entregable para comité: evidencia de pipeline y diff funcional antes/después.

## UC-03: Alerta de riesgo por patrón anómalo de órdenes
- Problema del cliente: detección de fraude tardía y manual.
- Caso de uso: publicar evento `order_created` y activar `risk-alert-service`.
- Qué se demuestra: extensión de capacidades sin tocar servicios núcleo.
- Alcance mínimo (MVP de PoC):
  - Event bus con `RabbitMQ` (`topic exchange` + cola `risk-alert`).
  - Regla simple de anomalía (umbral de monto/frecuencia).
  - Manejo de fallos con `DLQ` y política de reintentos.
- Métrica de éxito:
  - Tiempo de detección desde creación de orden < X segundos.
  - Tasa de alertas válidas sobre dataset de prueba > umbral acordado.
- Entregable para comité: dashboard de alertas y trazabilidad por evento.

## UC-04: Resiliencia ante caída de servicio externo de mercado
- Problema del cliente: una caída externa bloquea operación completa.
- Caso de uso: fallback con reintentos, timeout, circuito abierto y cola de reintento.
- Qué se demuestra: continuidad operativa con degradación parcial controlada.
- Alcance mínimo (MVP de PoC):
  - Simulación de indisponibilidad del proveedor.
  - Reintento asíncrono con `RabbitMQ` (cola retry + `TTL` + reencolado).
  - Uso de `DLQ` para mensajes agotados y estado `PENDING_MARKET_SUBMISSION`.
- Métrica de éxito:
  - Cero pérdida de órdenes durante caída simulada.
  - Recuperación automática al restablecer proveedor.
- Entregable para comité: prueba de caos controlada + reporte de recuperación.

## UC-05: Observabilidad end-to-end de una transacción distribuida
- Problema del cliente: incidentes difíciles de diagnosticar por falta de trazas unificadas.
- Caso de uso: instrumentar métricas, logs y tracing correlacionado por `trace_id`.
- Qué se demuestra: diagnóstico rápido y operación basada en evidencia.
- Alcance mínimo (MVP de PoC):
  - Dashboard de latencia/errores por servicio.
  - Trazas distribuidas de extremo a extremo.
  - Alertas básicas por SLO.
- Métrica de éxito:
  - Reducción del MTTR frente a baseline actual.
  - 100% de requests de PoC con trazabilidad completa.
- Entregable para comité: tablero operativo + runbook de incidentes.

## UC-06: Política de pasajeros (regulares vs VIP) guiada por BDD
- Problema del cliente: reglas de negocio ambiguas y defectos por interpretación.
- Caso de uso: definir política en escenarios Given/When/Then (Cucumber).
- Qué se demuestra: alineación negocio-tecnología y documentación viva.
- Alcance mínimo (MVP de PoC):
  - Feature con escenarios para agregar/quitar pasajeros por tipo.
  - Automatización de escenarios críticos en CI.
- Métrica de éxito:
  - Cobertura de criterios de aceptación críticos al 100%.
  - Reducción de retrabajo por ambigüedad de reglas.
- Entregable para comité: feature files ejecutables + reporte de ejecución.

## UC-07: Nueva regla "pasajero no duplicado" implementada con TDD
- Problema del cliente: defectos por datos duplicados e inconsistencias operativas.
- Caso de uso: introducir restricción de unicidad con ciclo red-green-refactor.
- Qué se demuestra: cambio seguro y mantenible con pruebas primero.
- Alcance mínimo (MVP de PoC):
  - Test fallido inicial.
  - Refactor de estructura a `Set`.
  - Test repetido y cobertura del caso borde.
- Métrica de éxito:
  - 0 duplicados permitidos en suite automatizada.
  - Sin regresiones en reglas existentes.
- Entregable para comité: evidencia del ciclo TDD y diff de cobertura.

## UC-08: Pirámide de pruebas para release confiable
- Problema del cliente: releases lentos y frágiles por estrategia de pruebas desbalanceada.
- Caso de uso: estructurar pruebas en niveles unitario, integración, sistema y aceptación.
- Qué se demuestra: balance costo/velocidad/confianza para liberar más seguido.
- Alcance mínimo (MVP de PoC):
  - Pipeline por niveles con criterios de promoción.
  - Integración con quality gates (cobertura, fallos, flakiness).
- Métrica de éxito:
  - Reducción de fallos en producción durante ventana de PoC.
  - Mejora en lead time de cambio.
- Entregable para comité: tablero de calidad por etapa + historial de ejecuciones.

---

## Paquetes de PoC sugeridos

## Paquete A (2 semanas) - Validación rápida
- UC-01 + UC-02 + UC-06
- Ideal para: cliente que requiere prueba de valor temprana sin tocar sistemas críticos.

## Paquete B (4 semanas) - Operación confiable
- UC-01 + UC-04 + UC-05 + UC-08
- Ideal para: cliente con dolores de disponibilidad e incidentes.

## Paquete C (6 semanas) - Transformación de calidad y entrega
- UC-01 + UC-03 + UC-05 + UC-06 + UC-07 + UC-08
- Ideal para: cliente que quiere cambiar forma de entrega (arquitectura + QA).

## Checklist comercial previo a demo
1. Confirmar objetivo de negocio (tiempo, costo, riesgo o compliance).
2. Acordar métricas base y meta (baseline vs objetivo).
3. Definir alcance técnico no negociable del PoC.
4. Establecer criterios de aceptación firmados por negocio y TI.
5. Preparar guion de demo de 20 minutos con evidencia técnica y de negocio.

## Guion de presentación (20 min)
1. Contexto del cliente y dolor actual (3 min).
2. Arquitectura/as-is vs to-be del caso elegido (4 min).
3. Demo funcional del flujo crítico (6 min).
4. Métricas antes/después y riesgos mitigados (4 min).
5. Plan de escalamiento post-PoC (3 min).

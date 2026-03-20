# Jenkins Architecture for Scale (Toolkit)

Base curada desde fuentes expertas + documentación oficial.

## Modos de arquitectura soportados

## 1) single-node (inicio rápido)

Cuándo usar:
- piloto inicial
- equipo pequeño
- baja concurrencia

Ventajas:
- implementación simple
- menor costo operativo inicial

Riesgos:
- punto único de falla
- limitación de throughput

## 2) distributed-static (crecimiento controlado)

Cuándo usar:
- varios proyectos concurrentes
- workloads distintos por stack

Patrón:
- 1 controller Jenkins
- N agents fijos
- labels para enrutamiento de jobs

Ventajas:
- mejor rendimiento y separación de carga
- mayor control por tipo de workload

## 3) distributed-dynamic (escala elástica)

Cuándo usar:
- variación fuerte de carga
- foco en costo/elasticidad

Patrón:
- controller estable
- workers efímeros/dinámicos
- autoscaling por métrica (CPU/cola)

Ventajas:
- optimización de costo
- capacidad bajo demanda

## Reglas de diseño del toolkit

1. No escalar controller para resolver todo; mover ejecución a agents.
2. Labels obligatorios para cargas críticas (ej: `java11`, `docker`, `k8s`).
3. Executors por nodo definidos según CPU/RAM y tipo de job.
4. Logs y métricas de cola/builds como entrada para tuning de capacidad.

## Hoja de transición recomendada

- Fase A: single-node (establecer pipeline confiable)
- Fase B: distributed-static (aislar cargas)
- Fase C: distributed-dynamic (autoscaling y costo)

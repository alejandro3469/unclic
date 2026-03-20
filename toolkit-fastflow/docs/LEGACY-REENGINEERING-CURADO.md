# Re-Engineering Legacy Software (Curado y Aplicable)

Fecha: 2026-03-10
Fuente base: contenido compartido del libro *Re-Engineering Legacy Software* (Chris Birchall, 2016) — About + capítulos 1 al 10.

## Objetivo

Traducir principios de modernización de legado a reglas ejecutables dentro del toolkit Fast Flow para que sirvan en preventa, discovery y delivery técnico.

## Tesis operativa

1. Legado no es solo código viejo; es pérdida de mantenibilidad del sistema completo.
2. Mejorar legado sin métricas es opinión; mejorar legado con métricas es gestión.
3. Refactor no es estética: es reducción de riesgo y aumento de velocidad de entrega.
4. Reescribir todo por defecto es una apuesta de alto riesgo; preferir avance incremental.

## Reglas operativas por bloque de capítulos

## A) Capítulos 1-3 (diagnóstico y decisión)

1. Diagnosticar en 4 ejes: código, infraestructura, flujo y cultura.
2. Priorizar mejoras por Valor-Riesgo-Dificultad (V-R-D).
3. Evaluar primero replace, luego refactor, y rewrite como última opción.

## B) Capítulo 4 (refactor disciplinado)

1. Separar commits de refactor y commits de funcionalidad.
2. Aplicar cambios pequeños, reversibles y validados continuamente.
3. Eliminar stale code y tests tóxicos como quick wins de mantenibilidad.
4. Introducir seams (indirección/inyección) para testear código legacy difícil.

## C) Capítulos 5-6 (re-arquitectura y rewrite)

1. Modularizar por componentes con interfaces explícitas.
2. Re-arquitectar de forma incremental y con entregables de valor por fase.
3. En rewrite, definir alcance explícito y política de migración de datos antes de construir.
4. Evitar cutovers irreversibles sin estrategia de failback.

## D) Capítulos 7-9 (infraestructura y flujo moderno)

1. README operativo breve y ejecutable para onboarding.
2. Provisioning automatizado y reproducible para dev/test/prod.
3. Paridad entre entornos como requisito de calidad operacional.
4. CI/CD mínimo obligatorio para todo repositorio, incluyendo legado.
5. Release y deploy automatizados para eliminar dependencia tribal.

## E) Capítulo 10 (evitar crear nuevo legado)

1. Mantener automatización continua de tareas repetitivas.
2. Proteger flujo de conocimiento: documentación viva + revisión + comunicación.
3. Diseñar componentes pequeños, desacoplados y reemplazables.
4. Revisar periódicamente la salud del sistema, no solo cambios aislados.

## Marco de diagnóstico (4 ejes)

1. Código:
- Testabilidad, complejidad, duplicación, hotspots de defectos.
2. Infraestructura:
- Reproducibilidad de entornos, drift, dependencias obsoletas, observabilidad.
3. Flujo:
- Build/release lead time, tasa de fallas, frecuencia de despliegue, rollback.
4. Cultura:
- Silos de conocimiento, calidad de review, coordinación entre roles, ownership.

## Priorización de refactor (matriz V-R-D)

Evaluar cada iniciativa por:
- Valor (V): impacto real en negocio/equipo.
- Riesgo (R): probabilidad de regresión + radio de impacto.
- Dificultad (D): costo técnico y operativo.

Orden recomendado:
1. Alto valor, bajo riesgo, baja dificultad (quick wins con efecto visible).
2. Alto valor, riesgo medio con controles (tests + rollout + observabilidad).
3. Alto riesgo solo con plan incremental y estrategia de reversión.

## Política Refactor vs Rewrite vs Replace

Regla por defecto:
1. Replace: primero evaluar solución existente (comprada/open source/managed).
2. Refactor: segunda opción para preservar comportamiento y reducir riesgo.
3. Rewrite: última opción, solo si refactor intentado no alcanza objetivos.

Condiciones mínimas para aprobar rewrite:
- Fracaso demostrado de refactor razonable.
- Cambio de paradigma tecnológico realmente necesario.
- Plan incremental con entregables de valor por fase.
- Criterios de salida, presupuesto, riesgos y rollback definidos.

## Instrumentación mínima recomendada

1. CI con inspección continua:
- análisis estático,
- pruebas automatizadas,
- reportes de tendencia.
2. Métricas de ingeniería:
- defectos abiertos/cerrados,
- tiempo de build,
- frecuencia de despliegue,
- MTTR,
- change failure rate.
3. Observabilidad de producción:
- errores,
- latencia,
- saturación de recursos,
- alertas accionables.

## Guardrails de ejecución

1. Cambios pequeños y revisables; evitar lotes masivos.
2. Code review obligatorio en zonas de alto riesgo.
3. Caracterización previa de comportamiento antes de refactor delicado.
4. Objetivos de negocio explícitos en cada iteración.
5. Trazabilidad: cada cambio debe mapear a hipótesis y resultado medible.

## Checklist de modernización curada (listo para ejecución)

1. Baseline:
- KPIs iniciales documentados (fallas, lead time, MTTR, build time).

2. Riesgo:
- Matriz V-R-D aplicada al backlog técnico.

3. Entornos:
- Dev/test/prod provisionados por código, sin pasos manuales ocultos.

4. Pipeline:
- Build/test/análisis/deploy con gates y evidencia.

5. Migración:
- Plan incremental por componente, con rollback probado.

6. Datos:
- Estrategia explícita de compatibilidad y sincronización durante transición.

7. Cultura:
- Code review obligatorio y ownership compartido.

## Aplicación directa al toolkit Fast Flow

1. Discovery:
- usar `QUESTIONARIO-IMPLEMENTACION.md` + este marco para baseline.
2. Arquitectura:
- priorizar pipeline reproducible, entornos declarativos y gates de calidad.
3. Operación:
- dashboards de tendencia para validar mejora real.
4. Gobierno:
- backlog de modernización priorizado con V-R-D y revisado por ciclo.

## Antipatrones a evitar

1. "Refactor por estética" sin hipótesis de valor.
2. "Rewrite total" sin entregables intermedios.
3. "Big-bang migration" sin rollback practicable.
4. "Solo héroes conocen el sistema" (dependencia de personas clave).

## Resultado esperado

Un programa de modernización que:
- reduce riesgo operacional,
- aumenta velocidad de cambio,
- mejora calidad percibida por usuario,
- y deja capacidad instalada en el equipo (no solo código nuevo).

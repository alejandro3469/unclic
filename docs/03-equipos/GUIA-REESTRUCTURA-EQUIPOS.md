# Guía Curada: Reestructura de Equipo para Calidad y Testing

## Objetivo
Estructurar equipos para sostener velocidad de entrega con calidad, usando prácticas de testing (unit, integración, sistema, aceptación), CI/CD y ownership claro.

---

## 1) Equipo Pequeño (3–8 personas)

### Estructura mínima (por sombrero)
- `Feature Owner`: prioriza alcance funcional.
- `Quality Owner`: define cobertura mínima y estabilidad de pruebas.
- `CI Owner`: mantiene pipeline, tiempos y confiabilidad.

### Operativa recomendada
1. PRs pequeños y frecuentes.
2. Pipeline obligatorio en cada PR.
3. Definition of Done con:
   - Unit tests
   - 1 integración crítica
   - 1 aceptación (BDD ligera)
4. Pairing rotativo (dev + calidad) 2 veces por semana.
5. Nightly de regresión completa.

### Métricas mínimas
- Tiempo de ciclo
- Fallos de CI/semana
- Flakiness
- Defectos en producción
- MTTR (mean time to recover)

---

## 2) Equipo Mediano/Grande (15+ personas)

### Estructura por capas
- `Squads de producto`: ownership extremo a extremo de servicios.
- `Platform QA/DevEx`: frameworks, utilidades, estándares de test.
- `Guild de calidad/arquitectura`: prácticas comunes y coaching.

### Gobernanza técnica
1. Contratos entre servicios (contract tests).
2. Versionado semántico y compatibilidad.
3. Estándar global de DoD.
4. Pirámide de pruebas definida por tipo de servicio.

### Pipeline multinivel
- PR pipeline rápido (unit + contract checks).
- Integración por dominio (diaria).
- Regresión completa (nightly/según criticidad).

---

## 3) Plan de transición 30-60-90

### Días 1–30
- Auditoría de pruebas existentes.
- Definición de estándar de calidad y DoD.
- Eliminación de tests flaky críticos.

### Días 31–60
- Adopción TDD/BDD en historias nuevas.
- Contratos de integración entre equipos.
- Centralización de reportes en CI.

### Días 61–90
- Optimización de tiempos de pipeline.
- Automatización de regresión API/UI priorizada por riesgo.
- Ritual quincenal de deuda técnica de pruebas.

---

## 4) Reglas de operación
1. No se cierra historia sin evidencia de pruebas automatizadas.
2. Cada servicio tiene owner de pruebas.
3. Toda caída de CI se atiende como incidente de flujo.
4. Test code se trata como código de producto (review + refactor).

---

## 5) Checklist semanal
- Estado de cobertura por dominio
- Flaky tests abiertos/cerrados
- Tiempo medio de ejecución de pipelines
- Defectos detectados preproducción vs producción
- Riesgos de integración entre equipos


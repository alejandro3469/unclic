# Guía de Gestión de Duplicidad de Código (Capa 16 de FastFlow)

Copiar y pegar es el primer paso hacia el desastre arquitectónico. FastFlow combate la duplicidad para reducir el costo de mantenimiento y evitar la propagación de errores.

## 1. El Costo Oculto de la Duplicidad
- **Doble Trabajo**: Si hay un bug en el código copiado, tienes que arreglarlo en dos (o diez) sitios.
- **Inconsistencia**: El mayor riesgo. Arreglas el bug en un sitio, olvidas el otro, y el sistema falla de forma impredecible.
- **Inflación del Código**: Más líneas = Más complejidad = Más tiempo de comprensión.

## 2. El Principio DRY (Don't Repeat Yourself)
"Cada pieza de conocimiento debe tener una representación única, inequívoca y autoritaria dentro de un sistema".
- En FastFlow, si ves un patrón repetido, es una señal de que falta una abstracción (una clase de utilidad, un servicio común o un componente compartido).

## 3. Detectando Duplicidad con FastFlow
Buscamos duplicidad en tres niveles:
1. **En el mismo archivo**: Fácil de refactorizar extrayendo un método.
2. **Entre archivos del mismo proyecto**: Requiere mover lógica a clases comunes.
3. **Entre diferentes proyectos**: La señal definitiva de que necesitas una librería compartida ("Common Library").

## 4. ¿Por qué duplicamos código?
- **Pereza**: Es más rápido copiar que pensar en una buena abstracción.
- **Miedo al Cambio**: "No quiero tocar el código original por si rompo algo". (Solución: Capa 15 - Tests Unitarios).
- **Falta de Comunicación**: Dos desarrolladores resolviendo el mismo problema por separado.

## 5. Estrategia de Limpieza
1. **Identificar**: Usar las métricas de duplicidad de FastFlow.
2. **Refactorizar**: Aplicar patrones como *Extract Method* o *Inheritance/Composition*.
3. **Validar**: Asegurar que tras eliminar la duplicidad, los tests siguen pasando.

---
*La duplicidad es una deuda que se paga con el tiempo de tu equipo. No dejes que los intereses te consuman.*

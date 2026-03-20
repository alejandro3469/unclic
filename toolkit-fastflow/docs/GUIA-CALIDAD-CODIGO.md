# Guía de Documentación y Calidad del Código (Capa 13 de FastFlow)

En FastFlow, el código no solo debe funcionar; debe ser elegante, comprensible y mantenible. El código que no se entiende es deuda técnica que alguien pagará con creces en el futuro.

## 1. Las 7 Ejes de la Calidad (SonarQube)
FastFlow integra principios de SonarQube para medir lo que parece subjetivo:
1. **Duplicaciones**: Código copiado y pegado es una bomba de tiempo.
2. **Tests Unitarios**: Tu red de seguridad.
3. **Complejidad**: Métodos "monstruo" que nadie se atreve a tocar.
4. **Potenciales Errores**: Patrones de código que suelen causar bugs.
5. **Reglas de Codificación**: Estándares de estilo compartidos.
6. **Comentarios**: La explicación del "por qué", no del "qué".
7. **Diseño y Arquitectura**: Cómo interactúan tus piezas.

## 2. Documentación: ¿Cuándo y Por Qué?
- **El Código es la Documentación**: Nombres de variables y métodos claros (ej: `calcularTotalPedido()` vs `calc()`).
- **Comentarios de API**: Todo método público DEBE estar documentado. Es el contrato con quien usará tu código.
- **El "Por Qué"**: Los comentarios deben explicar la razón de una decisión compleja, no repetir lo que el código ya dice.

## 3. Métricas de Complejidad (McCabe)
FastFlow vigila la **Complejidad Ciclomática**.
- **Regla de Oro**: Si un método tiene una complejidad mayor a 7-10, es hora de refactorizar (dividirlo en piezas más pequeñas).
- **LCOM4 (Lack of Cohesion of Methods)**: Si una clase hace demasiadas cosas que no tienen relación entre sí, debe dividirse. Una clase = Una responsabilidad.

## 4. El Costo de la No-Calidad
- **Estimaciones Fallidas**: Un desarrollador tarda 3 días en algo de 2 horas porque el código es un laberinto.
- **Bugs en Producción**: A mayor complejidad, mayor probabilidad de errores humanos.
- **Rotación de Personal**: Nadie quiere trabajar en un "basurero" de código.

## 5. El Proceso FastFlow
1. **Clean Code**: Escribe código limpio desde el minuto 1.
2. **Refactorización Continua**: Si tocas un archivo, déjalo un poco mejor de lo que lo encontraste.
3. **Análisis Estático**: Deja que las herramientas (como SonarQube o Linters) hagan el trabajo sucio de encontrar errores obvios.

---
*Escribe código como si el próximo que lo fuera a mantener fuera un psicópata violento que sabe dónde vives.*

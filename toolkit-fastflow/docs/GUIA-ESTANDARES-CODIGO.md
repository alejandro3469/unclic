# Guía de Estándares de Codificación y Gestión de Incidencias (Capa 14 de FastFlow)

Los estándares de codificación no son sugerencias; son las leyes que mantienen el orden en el caos de la entrega continua. FastFlow utiliza análisis estático para detectar problemas antes de que lleguen al usuario.

## 1. ¿Qué es una Incidencia (Issue)?
Una incidencia es cualquier patrón en el código que rompe una regla de calidad. No todas son bugs, pero todas merecen atención.
- **Bugs**: Errores lógicos que garantizan fallos (ej: desreferencia de puntero nulo).
- **Vulnerabilidades**: Agujeros de seguridad (ej: inyección SQL).
- **Code Smells**: Código que funciona pero es difícil de mantener (ej: métodos demasiado largos).

## 2. Severidades de FastFlow
Clasificamos los problemas para priorizar el esfuerzo:
1. **Blocker**: Debe fijarse inmediatamente. Riesgo alto de caída del sistema.
2. **Critical**: Riesgo alto de comportamiento inesperado o brecha de seguridad.
3. **Major**: Impacto importante en la mantenibilidad.
4. **Minor**: Problemas de estilo o consistencia.
5. **Info**: Sugerencias de mejora.

## 3. Ejemplos Comunes y por qué importan
- **Bloques Catch Vacíos**: "Tragar" excepciones oculta errores reales que aparecerán en producción sin rastro de por qué.
- **Variables no usadas**: Añaden ruido y confusión al desarrollador que mantiene el código.
- **Falta de llaves `{}` en condicionales**: Aunque el código funcione, es una trampa para el próximo desarrollador que añada una línea pensando que está dentro del `if`.

## 4. El Índice de Cumplimiento (Compliance Index)
FastFlow busca un índice cercano al 100%. Sin embargo, recuerda: añadir código "limpio" diluye el porcentaje pero no elimina los problemas existentes. **Enfócate en reducir el número absoluto de incidencias críticas.**

## 5. Deuda Técnica
Cada incidencia es deuda. Si no la pagas (refactorizando), los intereses (tiempo perdido entendiendo código sucio) acabarán por quebrar tu capacidad de entrega.

---
*Un código que sigue estándares es un código que habla el mismo idioma que todo el equipo.*

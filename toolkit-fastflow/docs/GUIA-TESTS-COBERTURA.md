# Guía de Pruebas Unitarias y Cobertura (Capa 15 de FastFlow)

En FastFlow, si no está probado, no existe. Las pruebas unitarias son la base de la pirámide de automatización y la única forma de garantizar que cada pieza del motor funciona antes de ensamblar el coche.

## 1. Métricas de Éxito de Pruebas
No basta con tener tests; deben pasar.
- **Éxito (Success Rate)**: DEBE ser 100%. Un solo test fallido invalida el despliegue.
- **Tests Saltados (Skipped)**: Un test saltado suele ser un test fallido disfrazado. Evítalos.

## 2. Cobertura de Código (Code Coverage)
FastFlow mide tres tipos de cobertura:
1. **Line Coverage**: ¿Se ejecutó esta línea al menos una vez?
2. **Branch Coverage**: ¿Se probaron todos los caminos lógicos (if/else, switch)? (Esta es la más difícil y valiosa).
3. **Overall Coverage**: El promedio que da una visión general de la salud del proyecto.

## 3. El Peligro del 100% "Falso"
Puedes tener 100% de cobertura y 0% de pruebas reales.
- **Tests sin Aserciones**: Un test que solo recorre el código pero no verifica el resultado es inútil.
- **Happy Path**: No pruebes solo cuando todo va bien. Prueba los límites, los nulos y los errores.

## 4. Continuous Integration (CI) y Feedback
Las pruebas deben ser rápidas. Si los tests tardan horas, el equipo dejará de ejecutarlos.
- **Feedback Rápido**: En FastFlow, el desarrollador debe saber si rompió algo en menos de 5 minutos tras el commit.

## 5. Estrategia FastFlow
1. **Un Test = Una Aserción**: Facilita saber exactamente qué falló.
2. **Independencia**: Los tests no deben depender de la base de datos o de otros tests (usa Mocks).
3. **Mantenibilidad**: Trata el código de tus tests con el mismo respeto que el código de producción.

---
*Las pruebas unitarias no son para encontrar bugs, sino para permitirte cambiar el código sin miedo.*

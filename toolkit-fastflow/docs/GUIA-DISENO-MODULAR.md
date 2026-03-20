# Guía de Diseño Modular y Gestión de Dependencias (FastFlow Design)

Un sistema mal diseñado es como un plato de espagueti: tiras de un lado y se mueve todo el plato. FastFlow promueve un diseño desacoplado y modular.

## 1. El Peligro de los Ciclos de Dependencia
Un ciclo ocurre cuando el Paquete A depende del B, y el B depende del A (directa o indirectamente).
- **Consecuencia**: No puedes actualizar, probar o reutilizar un paquete sin el otro.
- **Solución FastFlow**: Usar interfaces y patrones de diseño (como Inyección de Dependencias) para romper el círculo.

## 2. DSM (Dependency Structure Matrix)
FastFlow utiliza la matriz DSM para visualizar el "caos".
- **Diagonal**: El punto de equilibrio.
- **Triángulo Inferior**: Dependencias saludables (el nivel superior usa al inferior).
- **Triángulo Superior**: Alerta roja. Ciclos o dependencias inversas que complican el diseño.

## 3. Cohesión y Acoplamiento
- **Alta Cohesión**: Cada módulo hace una sola cosa y la hace bien (Single Responsibility Principle).
- **Bajo Acoplamiento**: Los módulos saben lo mínimo posible los unos de los otros.

## 4. Gestión de Librerías Externas
No todas las librerías son tus amigas.
- **Conflictos de Versión**: La pesadilla de "JAR hell" o conflictos de dependencias en Maven/NPM.
- **FastFlow Policy**: Mantener un inventario claro de dependencias externas y auditar sus vulnerabilidades (Capa 2: Seguridad).

## 5. Reglas Arquitectónicas
Puedes definir reglas que SonarQube vigile por ti:
- "La capa de Interfaz de Usuario nunca debe llamar directamente a la Base de Datos".
- "El servicio de Pagos solo puede ser usado por el Orquestador de Pedidos".

---
*Un buen diseño no es el que tiene más piezas, sino el que permite cambiar las piezas sin romper el motor.*

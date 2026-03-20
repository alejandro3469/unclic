# Guía de Desarrollo y Extensibilidad (Capa 26 de FastFlow)

FastFlow no es solo una herramienta, es una plataforma extensible. Esta guía está dirigida a los desarrolladores que necesitan ir más allá de las plantillas estándar y construir integraciones personalizadas, plugins o extensiones sobre el motor de FastFlow.

## 1. Filosofía de Desarrollo en FastFlow
El desarrollo en FastFlow se basa en tres pilares:
1.  **Modularidad**: Cada nueva funcionalidad debe ser un módulo independiente.
2.  **Transparencia**: El código debe ser legible y seguir los estándares de la comunidad de Jenkins.
3.  **Depurabilidad**: Todo código nuevo debe ser fácil de depurar en entornos locales.

## 2. Herramientas del Desarrollador
Para extender FastFlow, recomendamos el siguiente stack:
-   **Java SDK 11/17**: El lenguaje base de Jenkins.
-   **Maven**: Para la gestión de dependencias y ciclos de vida de build.
-   **Visual Studio Code**: Nuestro IDE recomendado (configurado con los archivos en `.vscode/`).
-   **Docker**: Para levantar entornos de prueba aislados.

## 3. Entorno de Depuración (Debug Mode)
FastFlow facilita la depuración de plugins y scripts de pipeline:
-   **mvnDebug**: Permite arrancar Jenkins en modo escucha (puerto 8000) para conectar un depurador.
-   **Attach Debugger**: Usa la configuración de VS Code (`Debug (Attach)`) para poner puntos de interrupción en tu código Java.

## 4. Extensión mediante Jelly y Stapler
Si estás desarrollando interfaces de usuario para Jenkins:
-   **Jelly**: El motor de vistas basado en XML.
-   **Stapler**: El framework que mapea URLs a objetos Java.
-   **Tip FastFlow**: Usa el plugin de IntelliJ o las extensiones de VS Code para autocompletado de etiquetas Jelly.

## 5. Pruebas de Desarrollo
No subas código sin validar. FastFlow integra:
-   **JenkinsRule**: Para levantar un Jenkins ligero en tests unitarios.
-   **Pipeline Unit Testing**: Para validar la sintaxis de tus bibliotecas compartidas antes del push.

## 6. Documentación de Referencia
-   **Pipeline Steps Reference**: Consulta siempre la documentación generada para conocer los parámetros exactos de cada paso.
-   **Javadoc**: Acceso a la API interna de Jenkins para desarrolladores avanzados.

---
*Si puedes imaginarlo, puedes construirlo sobre FastFlow. El límite es tu creatividad, no la herramienta.*

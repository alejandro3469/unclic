# How-to: Configuración de Entorno de Desarrollo con VS Code

Esta guía describe cómo configurar **Visual Studio Code** para desarrollar y depurar extensiones de Jenkins dentro del ecosistema **FastFlow**.

## Prerrequisitos
1.  **Java JDK 11 o 17**: Instalado y configurado en el `PATH`.
2.  **Maven**: Para la gestión del ciclo de vida del proyecto.
3.  **VS Code Extensions**:
    -   `Extension Pack for Java` (Microsoft)
    -   `Debugger for Java` (Microsoft)

## Paso 1: Configuración de Archivos del IDE
FastFlow incluye pre-configuraciones en la carpeta `.vscode/`. Asegúrate de tener los siguientes archivos:

### `.vscode/tasks.json`
Define la tarea `mvnDebug` que arranca Jenkins en modo depuración:
```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "mvnDebug",
            "type": "shell",
            "command": "mvnDebug hpi:run",
            "isBackground": true,
            "problemMatcher": [...]
        }
    ]
}
```

### `.vscode/launch.json`
Configura el depurador para conectarse al proceso de Jenkins:
```json
{
    "configurations": [
        {
            "type": "java",
            "name": "Debug (Attach)",
            "request": "attach",
            "hostName": "localhost",
            "port": "8000",
            "preLaunchTask": "mvnDebug"
        }
    ]
}
```

## Paso 2: Iniciar la Sesión de Depuración
1.  Abre tu proyecto en VS Code.
2.  Presiona `F5` o ve a la vista **Run and Debug** y selecciona **Debug (Attach)**.
3.  VS Code ejecutará automáticamente la tarea `mvnDebug hpi:run`.
4.  Espera a que la consola muestre: `Listening for transport dt_socket at address: 8000`.
5.  El depurador se conectará y podrás poner puntos de interrupción (breakpoints) en tu código Java.

## Paso 3: Uso de Docker para Pruebas Rápidas
Si prefieres no instalar todo localmente, FastFlow recomienda el uso de **Docker Compose**:
1.  `git clone <tu-repo-fastflow>`
2.  `docker-compose up -d`
3.  Accede a Jenkins en `http://localhost:8080`.

## Consejos Pro
-   **Hot Reload**: Usa `JRebel` o el soporte de hot swap de la JVM para ver cambios en el código sin reiniciar Jenkins.
-   **Stapler Views**: Para saltar rápidamente de una clase Java a su vista Jelly, usa `Cmd/Ctrl+Shift+P` (con la extensión adecuada).

---
*Configuración optimizada para la agilidad del desarrollador en FastFlow.*

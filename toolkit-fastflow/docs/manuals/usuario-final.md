# Manual de Usuario Final: Desarrollando con FastFlow

Bienvenido al ecosistema **FastFlow**. Como desarrollador, tu único objetivo es escribir código de calidad y entregar valor. FastFlow se encarga de todo lo demás de forma automática, segura y transparente.

## 1. El Ciclo de Vida del Desarrollador
En FastFlow, tu flujo diario no cambia, se acelera:
1.  **Escribe**: Desarrolla tu funcionalidad y tus tests unitarios en tu rama local.
2.  **Valida**: Usa el modo Debug local (puerto 8000) si necesitas depurar lógica compleja.
3.  **Push**: Sube tus cambios a Git. FastFlow detecta el cambio instantáneamente vía Webhooks.
4.  **Feedback**: Recibe el estado de tu build, tests y escaneos de seguridad en Slack o en tu Pull Request.

## 2. El Jenkinsfile: Tu Contrato de Entrega
Tu repositorio debe contener un archivo `Jenkinsfile` en la raíz. Este archivo define las reglas del juego. 
-   **No reinventes la rueda**: Usa los templates de FastFlow incluidos en la carpeta `templates/stacks/`.
-   **Personalización**: Si necesitas pasos extra, utiliza las funciones de nuestra **Shared Library** (ej: `fastFlowNotify()`).

## 3. Calidad y Seguridad (Definition of Done)
FastFlow no dejará que código inestable llegue a producción. Para que tu build pase, debes cumplir con:
-   **Quality Gate**: Cobertura de tests mínima y límites de complejidad (SonarQube).
-   **Security Gate**: Cero vulnerabilidades críticas en tus dependencias (SAST).

## 4. Despliegue y Rollback
-   **Staging Automático**: Cada vez que haces push a la rama `develop`, tu código se despliega en un entorno de pruebas.
-   **Producción Controlada**: Los despliegues a `master` requieren una aprobación manual en la interfaz de Jenkins.
-   **Rollback de 1-Clic**: Si algo falla tras el deploy, usa el botón de "Rollback" en Jenkins para volver a la versión anterior en segundos.

## 5. Autoservicio (Self-Service)
-   **Logs**: Accede a la interfaz de **Blue Ocean** para ver exactamente dónde falló tu build con visualizaciones claras.
-   **Métricas**: Consulta tu dashboard personal para ver tu Lead Time y frecuencia de despliegue.

---
### 📖 Recursos para el Desarrollador
- [Portal del Desarrollador](../HOME-DESARROLLADOR.md)
- [Glosario de Términos](../GLOSARIO-FASTFLOW.md)
- [How-to: Configuración de VS Code](../how-tos/setup-dev-environment-vscode.md)

*FastFlow: Tú escribes el código, nosotros hacemos que fluya.*

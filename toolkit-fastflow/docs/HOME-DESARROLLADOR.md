# Portal del Desarrollador FastFlow (Implementación Rápida)

Bienvenido, ingeniero de software. Este portal está diseñado para que empieces a fluir con **FastFlow** en cuestión de minutos.

## 1. Tu Pipeline como Código (Jenkinsfile)
En FastFlow, el `Jenkinsfile` es una pieza más de tu código. Olvídate de configurar Jenkins a mano. Usa nuestras plantillas base y personaliza según tus necesidades.

### 🛠️ Tus Herramientas de Desarrollo
- **Quickstart**: Ejecuta `bash quickstart.sh` para tener Jenkins local ahora mismo.
- **VS Code Extension Pack for Java**: Nuestro IDE recomendado.
- **Modo Debug Local**: Usa `mvnDebug` para depurar tus scripts en el puerto 8000.
- **Shared Libraries**: Usa funciones predefinidas como `fastFlowNotify()` y `fastFlowBuild()`.

## 2. El Flujo Diario (Workflow)
1.  **Code**: Escribe tu código y tus tests unitarios.
2.  **Commit & Push**: Sube tus cambios a Git.
3.  **FastFlow Analysis**: El pipeline arranca automáticamente, escanea seguridad y calidad.
4.  **Feedback Instantáneo**: Recibe notificaciones en Slack o directamente en tu PR.
5.  **Deploy Efímero**: Prueba tus cambios en un entorno dinámico de Kubernetes.

## 3. Guías Paso a Paso (How-tos)
- [Conexión Universal a Git](how-tos/conexion-git-universal.md)
- [Configuración de Entorno en VS Code](how-tos/setup-dev-environment-vscode.md)
- [Rollback Instantáneo](how-tos/rollback-instantaneo.md)
- [Configuración de RBAC con GitHub](how-tos/setup-rbac-github.md)

## 4. Recursos para Desarrolladores
- [Manual de Usuario Final](manuals/usuario-final.md)
- [Quickstart: Jenkins en 1 Clic](../quickstart.sh)
- [Guía de Resolución de Problemas](how-tos/troubleshooting-guia.md)
- [Glosario FastFlow](GLOSARIO-FASTFLOW.md)
- [Shared Libraries FastFlow](GUIA-JENKINS-SHARED-LIBRARIES.md)

## 🎥 Demos Técnicos
- [Visualización de Pipelines de Microservicios](GUIA-MICROSERVICIOS-PAC.md)
- [Esquema de Ejecución de Pipeline Universal](GUIA-JENKINS-PAC.md#flujo-de-ejecución-visualización)

---
*FastFlow: Diseñado por desarrolladores, para desarrolladores.*

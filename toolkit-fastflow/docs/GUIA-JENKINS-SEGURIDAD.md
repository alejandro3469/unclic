# Guía de Seguridad y Gobernanza en Jenkins (Capa 23 de FastFlow)

La seguridad no es una opción, es la base de la confianza en tu flujo de entrega. Jenkins, al ser el orquestador central, maneja secretos, código fuente y acceso a infraestructura crítica. Esta guía define los estándares de **FastFlow** para proteger tu "Fábrica de Software".

## 1. El Modelo de Responsabilidad de FastFlow
En FastFlow, la seguridad se aplica en tres capas:
1.  **Seguridad del Maestro (Controller)**: Aislamiento del proceso de Jenkins y endurecimiento del sistema operativo.
2.  **Control de Acceso (RBAC)**: Quién puede hacer qué dentro de la interfaz y la API.
3.  **Seguridad del Pipeline**: Restricción de lo que un build puede hacer en los agentes y el acceso a secretos.

## 2. Control de Acceso: RBAC y OAuth
Olvídate de las cuentas locales. FastFlow recomienda centralizar la identidad:
-   **GitHub/GitLab OAuth**: Usa tu proveedor de Git para autenticar usuarios.
-   **Role-Based Strategy**:
    -   **Admin**: Configuración total del sistema.
    -   **Release Manager**: Capacidad de promover builds a producción.
    -   **Developer**: Lectura de logs y ejecución de builds en ramas de desarrollo.
    -   **Auditor**: Acceso de solo lectura a configuraciones y reportes.

## 3. Aislamiento del Maestro (Controller Isolation)
**Regla de Oro de FastFlow**: "El Maestro no ejecuta builds".
-   Configura el número de ejecutores en el nodo `built-in` a **cero**.
-   Usa **Agentes Efímeros** (Kubernetes Pods) para que cada build tenga su propio entorno limpio y aislado.
-   Esto evita que un script malicioso en un pipeline acceda a los archivos de configuración de Jenkins (`JENKINS_HOME`).

## 4. Gestión de Secretos y Credenciales
Nunca, bajo ninguna circunstancia, escribas contraseñas en un `Jenkinsfile`.
-   **Credentials Store**: Usa el almacén nativo de Jenkins con IDs descriptivos (ej: `aws-deploy-creds`).
-   **Masking**: Jenkins oculta automáticamente los valores de las credenciales en los logs de consola.
-   **Integración Externa**: Para entornos de alta seguridad, FastFlow recomienda **HashiCorp Vault** o **AWS Secrets Manager**.

## 5. Protecciones del Interfaz (UI Security)
FastFlow habilita por defecto:
-   **CSRF Protection**: Evita ataques de falsificación de peticiones entre sitios (habilitado por defecto en versiones modernas).
-   **Content Security Policy (CSP)**: Restringe qué scripts y estilos pueden ejecutarse en el navegador al ver reportes HTML.
-   **Markup Formatter**: Usa `Safe HTML` para prevenir ataques XSS en las descripciones de los trabajos.

## 6. Seguridad en Multibranch y Carpetas de Organización
Cuando conectas Jenkins a un repositorio completo:
-   **Trust Model**: Solo confía en ramas protegidas para despliegues automáticos.
-   **Scan Credentials**: Usa credenciales con permisos mínimos (Read-Only) para el escaneo de ramas.

---
*Un pipeline seguro es un pipeline confiable. En FastFlow, la seguridad es el "Definition of Done" número uno.*

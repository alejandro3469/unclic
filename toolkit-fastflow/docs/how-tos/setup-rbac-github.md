# How-to: Configuración de RBAC con GitHub OAuth

Esta guía detalla cómo implementar el control de acceso basado en roles (RBAC) en Jenkins utilizando GitHub como proveedor de identidad, siguiendo los estándares de **FastFlow**.

## Prerrequisitos
1.  Acceso de administrador a una instancia de Jenkins.
2.  Una cuenta de GitHub con permisos para crear una "GitHub OAuth App".
3.  Plugins instalados: `GitHub Authentication`, `Matrix Authorization Strategy`.

## Paso 1: Crear la App en GitHub
1.  En GitHub, ve a **Settings > Developer settings > OAuth Apps > New OAuth App**.
2.  **Application Name**: `FastFlow Jenkins`.
3.  **Homepage URL**: URL de tu Jenkins (ej: `https://jenkins.fastflow.ai`).
4.  **Authorization callback URL**: `https://jenkins.fastflow.ai/securityRealm/finishLogin`.
5.  Registra la aplicación y guarda el **Client ID** y el **Client Secret**.

## Paso 2: Configurar el Security Realm en Jenkins
1.  Ve a **Manage Jenkins > Configure Global Security**.
2.  En **Security Realm**, selecciona **GitHub Authentication Plugin**.
3.  Introduce el **Client ID** y **Client Secret** generados en el paso 1.
4.  En **OAuth Scopes**, asegúrate de incluir `read:org,user:email`.

## Paso 3: Configurar la Estrategia de Autorización (RBAC)
1.  En la misma pantalla, bajo **Authorization**, selecciona **Project-based Matrix Authorization Strategy**.
2.  **Usuario Administrador**: Añade tu usuario de GitHub y marca todas las casillas (Admin total).
3.  **Grupos de GitHub**: Puedes añadir organizaciones o equipos de GitHub usando el formato `org*team`.
    -   Ejemplo: `FastFlow*developers` con permisos de `Overall/Read` y `Job/Build`.
    -   Ejemplo: `FastFlow*admins` con permisos totales.

## Paso 4: Validación de Seguridad de FastFlow
-   **Acceso Anónimo**: Asegúrate de que el usuario `Anonymous` no tenga ningún permiso marcado.
-   **Agent Isolation**: Verifica que bajo **Manage Jenkins > Nodes**, el nodo `built-in` tenga **0 executors**.
-   **Audit Trail**: Instala el plugin `Audit Trail` para registrar quién realiza cambios en la configuración de seguridad.

## Resolución de Problemas
-   **Redirect Mismatch**: Asegúrate de que la URL de callback en GitHub coincida exactamente con la de Jenkins (incluyendo `https`).
-   **Permissions Lockout**: Si te quedas fuera de Jenkins, edita el archivo `config.xml` en el servidor y cambia `<useSecurity>true</useSecurity>` a `false`, reinicia Jenkins, corrige la configuración y vuelve a activarlo.

---
*Configuración estandarizada por el equipo de FastFlow.*

# How-to: Conexión Universal a Cualquier Git Repo

FastFlow está diseñado para ser agnóstico al proveedor de Git. Aquí te enseñamos cómo conectar tu repositorio, sea cual sea.

## 1. Escenario A: Proveedores en la Nube (GitHub, GitLab, Bitbucket)
FastFlow utiliza el **Branch Source Plugin** de Jenkins para máxima integración.

### Configuración Paso a Paso:
1. En Jenkins, crea un nuevo Item tipo "Multibranch Pipeline".
2. En la sección "Branch Sources", añade tu proveedor (GitHub/GitLab).
3. Añade tus credenciales (Token de API o Usuario/Contraseña).
4. Jenkins descubrirá automáticamente tu repositorio y buscará el `Jenkinsfile`.

## 2. Escenario B: Servidor Git Privado (On-premise / Gitea)
Si tu empresa aloja su propio servidor Git, FastFlow se conecta mediante el protocolo estándar Git.

### Configuración Paso a Paso:
1. En Jenkins, selecciona "Git" como fuente.
2. Introduce la URL de tu repositorio (HTTP o SSH).
3. Configura el ID de credenciales: `git-universal-credentials`.
4. En el `Jenkinsfile`, asegúrate de que el parámetro `GIT_CREDENTIALS_ID` coincida.

## 3. Seguridad y Visibilidad
Para que FastFlow funcione, el servidor de Jenkins debe tener **visibilidad de red** hacia tu repositorio:
- **Red Interna**: Si el servidor Git está en tu VPN, Jenkins debe estar en la misma red o tener acceso permitido por Firewall.
- **SSH Keys**: Recomendamos el uso de llaves SSH para conexiones seguras y automatizadas.

## 4. Webhooks para Flujo Automático
Para que el pipeline se dispare al hacer `git push`:
1. En tu servidor Git, añade un **Webhook**.
2. URL del Webhook: `https://tu-jenkins.com/github-webhook/` (o la URL correspondiente de tu plugin).
3. Evento: `Push`.

---
*Para ayuda avanzada, consulta el [Manual Técnico](../manuals/manual-tecnico.md).*

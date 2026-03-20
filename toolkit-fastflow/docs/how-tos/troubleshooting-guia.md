# Guía de Resolución de Problemas (Troubleshooting)

¿Algo no funciona como esperabas? Sigue esta guía rápida para diagnosticar y solucionar los problemas más comunes en el ecosistema **FastFlow**.

---

## 1. Problemas de Conectividad (Git / Webhooks)
**Síntoma**: El pipeline no arranca automáticamente tras un push.
-   **Check 1**: Verifica que el Webhook en GitHub/GitLab tenga el estado 200 (OK).
-   **Check 2**: Asegúrate de que el servidor Jenkins sea accesible desde Internet (o que el Firewall permita el tráfico del proveedor de Git).
-   **Solución**: Usa `curl` desde el servidor Jenkins para validar el acceso al repositorio Git.

## 2. Errores de Agente (Node Offline)
**Síntoma**: El build se queda en "Waiting for next available executor".
-   **Check 1**: Revisa **Manage Jenkins > Nodes**. ¿Están los agentes conectados?
-   **Check 2**: Si usas Kubernetes, ejecuta `kubectl get pods -n fastflow-system` para ver si el Pod del agente está fallando (CrashLoopBackOff).
-   **Solución**: Revisa los logs del agente: `docker logs jenkins-agent-fastflow`.

## 3. Fallos de Pipeline (Syntax Error)
**Síntoma**: El build falla inmediatamente con "WorkflowScript: line X: unexpected token".
-   **Check 1**: Valida el `Jenkinsfile` con el linter integrado de VS Code.
-   **Check 2**: Asegúrate de que las **Shared Libraries** estén configuradas globalmente en Jenkins.
-   **Solución**: Usa el "Pipeline Syntax" snippet generator en Jenkins para validar pasos complejos.

## 4. Problemas de Permisos (RBAC / Auth)
**Síntoma**: Un usuario no puede ver los logs o ejecutar un build.
-   **Check 1**: Verifica el rol del usuario en **Configure Global Security**.
-   **Check 2**: Si usas GitHub OAuth, asegúrate de que el usuario pertenezca a la organización/equipo configurado.
-   **Solución**: Habilita el log de seguridad de Jenkins para ver fallos de autorización detallados.

## 5. Disco Lleno (No Space Left on Device)
**Síntoma**: Jenkins se vuelve extremadamente lento o los builds fallan al escribir archivos.
-   **Check 1**: Ejecuta `df -h` en el servidor.
-   **Check 2**: Revisa la carpeta `jobs/` y `workspace/`.
-   **Solución**: Activa la política de **Discard Old Builds** y ejecuta el script de limpieza `cleanup.groovy` incluido en el toolkit.

---
*¿Aún tienes problemas? Consulta los logs maestros en `Manage Jenkins > System Log > All Jenkins Logs`.*

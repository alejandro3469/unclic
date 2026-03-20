# Guía de Mantenibilidad, Backup y Migración (Capa 25 de FastFlow)

Un sistema de entrega continua no es un "configura y olvida". Como cualquier pieza crítica de infraestructura, requiere mantenimiento preventivo, copias de seguridad robustas y una estrategia de actualización clara. FastFlow define el estándar de **Operabilidad** para tu plataforma.

## 1. El Estado de Jenkins: ¿Qué Respaldar?
En FastFlow, todo es código, pero hay estados dinámicos en el `JENKINS_HOME` que debemos proteger:
-   **Configuraciones XML**: Jobs, nodos, seguridad y plugins.
-   **Secretos**: La carpeta `secrets/` y el archivo `credentials.xml`.
-   **Historial de Builds**: Logs y artefactos (opcional, pero útil para auditoría).
-   **Fingerprints**: Datos de trazabilidad de archivos.

## 2. Estrategia de Backup de FastFlow
No confíes en plugins de backup internos. Usa herramientas de infraestructura:
-   **Snapshot de Disco**: Si Jenkins corre en un VM (AWS EBS, GCP Persistent Disk), usa snapshots diarios automáticos.
-   **Persistent Volume Claims (PVC)**: En Kubernetes, asegúrate de que el PVC tenga una política de retención y backups programados (ej: con Velero).
-   **Thin Backup**: Para backups rápidos de configuración, el plugin `ThinBackup` es aceptable para exportar archivos XML pequeños a una ruta de red (NFS/S3).

## 3. Actualizaciones Sin Miedo (LTS Strategy)
FastFlow solo recomienda versiones **LTS (Long Term Support)** de Jenkins.
-   **Frecuencia**: Actualiza cada 3-4 meses siguiendo el ciclo de LTS.
-   **Ambiente de Stage**: Nunca actualices producción directamente. Prueba los plugins y la versión core en un entorno espejo.
-   **Plugin Management**: Usa el plugin `Plugin Installation Manager Tool` para definir tus plugins en un archivo `plugins.txt`.

## 4. Migración y Recuperación ante Desastres (DR)
Si tu servidor Jenkins falla catastróficamente:
1.  **Levanta una instancia limpia**: Usa la misma versión de Jenkins.
2.  **Restaura `JENKINS_HOME`**: Copia los archivos desde tu último backup.
3.  **Reinicia Jenkins**: Verifica que los secretos se carguen correctamente.
4.  **Re-escaneo de Repositorios**: Fuerza un escaneo de tus organizaciones para reconstruir los pipelines.

## 5. Limpieza Automática (Housekeeping)
Un Jenkins lento suele ser un Jenkins lleno de basura. FastFlow implementa:
-   **Discard Old Builds**: Configura globalmente para mantener solo los últimos 10-20 builds por rama.
-   **Workspace Cleanup**: Usa el plugin `Workspace Cleanup` al inicio o final de cada pipeline.
-   **Log Rotation**: Rotación de logs del sistema para evitar que el disco se llene.

---
*La confiabilidad de tu flujo de entrega es proporcional a la calidad de tus backups. No esperes al desastre para probar tu restauración.*

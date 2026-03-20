# Manual Técnico FastFlow: Guía Maestra para Operadores y SREs

Este manual proporciona una visión integral de la arquitectura y configuración de **FastFlow**, cubriendo las 28 capas de conocimiento necesarias para operar una fábrica de software de nivel mundial.

## 1. Arquitectura de Referencia (Capa 18)
FastFlow se basa en un modelo de **Maestro Distribuido**:
-   **Controller**: Orquestador central (LTS). Almacena configuraciones, jobs y secretos.
-   **Agentes Efímeros**: Pods de Kubernetes o contenedores Docker que nacen para un build y mueren al finalizar.
-   **Persistencia**: Uso de PVCs (Persistent Volume Claims) con backups automáticos a S3/GCS.

## 2. Implementación de Infraestructura (Capas 19-20)
Usamos **Terraform** para provisionar la red y el cómputo, y **Packer** para asegurar que cada servidor sea una copia exacta del estándar de FastFlow.
-   **Estado Inmutable**: Nunca configuramos servidores a mano. Todo cambio se realiza en el código de Packer y se redespliega con Terraform.

## 3. Seguridad y Gobernanza (Capa 23)
-   **Control de Acceso**: Integración nativa con GitHub/GitLab OAuth.
-   **RBAC**: Estrategia de autorización basada en la matriz de roles (Admin, Manager, Dev, Auditor).
-   **Secretos**: Enmascaramiento automático en logs e integración opcional con HashiCorp Vault.

## 4. Centralización de Inteligencia (Capa 24)
Las **Shared Libraries** de FastFlow permiten que la lógica compleja (deploys, escaneos, notificaciones) viva en un solo lugar.
-   **Estructura**: Carpeta `vars/` para pasos globales y `src/` para clases Groovy.
-   **Versión**: Las librerías pueden versionarse por tags de Git para despliegues controlados.

## 5. Observabilidad y Métricas (Capa 21)
-   **Logs**: Stack EFK (Elasticsearch, Fluent Bit, Kibana) para centralizar la salida de todos los pipelines.
-   **Métricas**: Prometheus y Grafana para visualizar la salud del controlador y el rendimiento de los agentes.
-   **KPIs DORA**: Dashboards pre-configurados para medir la agilidad del negocio.

## 6. Operaciones de Día 2 (Capas 25-28)
-   **Mantenimiento**: Actualizaciones LTS programadas y limpieza automática de workspaces.
-   **Incidentes**: Flujo de respuesta SEV-1/2/3 y cultura de Post-Mortem sin culpa.
-   **FinOps**: Optimización de costos usando instancias Spot y apagado programado de entornos de desarrollo.

---
### 🛠️ Recursos Técnicos Detallados
- [Guía Maestra de Instalación](../GUIA-INSTALACION-ESCENARIOS.md)
- [Guía de Resolución de Problemas](../how-tos/troubleshooting-guia.md)
- [Glosario de Términos](../GLOSARIO-FASTFLOW.md)

*FastFlow: Diseñado para escalar, construido para durar.*

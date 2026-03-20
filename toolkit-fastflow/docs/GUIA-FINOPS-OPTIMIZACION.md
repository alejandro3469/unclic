# Guía de Optimización de Costos (FinOps) (Capa 28 de FastFlow)

Automatizar no debe significar gastar sin control. **FastFlow** incluye políticas de FinOps para asegurar que tu infraestructura de CI/CD sea eficiente y económica.

## 1. El Costo de Jenkins en el Cloud
Los mayores gastos suelen venir de:
-   **Maestro Sobredimensionado**: Servidores encendidos 24/7 con recursos que no se usan.
-   **Agentes "Zombis"**: VMs que no se apagan después de un build.
-   **Almacenamiento Descontrolado**: Gigabytes de artefactos y logs antiguos.

## 2. Estrategias de Ahorro de FastFlow

### 💸 Agentes Efímeros (Spot Instances)
FastFlow recomienda usar **Spot Instances** en AWS o **Preemptible VMs** en GCP para los agentes de build.
-   **Ahorro**: Hasta un 70-90% comparado con instancias bajo demanda.
-   **Implementación**: Configura el plugin de EC2/Kubernetes para usar flotas de instancias Spot.

### 🧹 Políticas de Retención (Housekeeping)
No guardes todo para siempre.
-   **Discard Old Builds**: Configura el pipeline para mantener solo los últimos 10 builds.
-   **S3 Lifecycle**: Mueve los backups de Jenkins a clases de almacenamiento baratas (Glacier) después de 30 días.

### 📉 Apagado Programado
Si tus desarrolladores no trabajan los fines de semana, ¿por qué tus entornos de Staging sí?
-   **Terraform Destroy/Apply**: Usa cronjobs para apagar y levantar entornos no productivos.

## 3. Dashboard de Costos
Integra tus métricas de infraestructura con herramientas de facturación (AWS Cost Explorer API) para visualizar en Grafana cuánto cuesta cada build de microservicio.

---
*FinOps en FastFlow: Entrega rápida al menor costo posible.*

# Catálogo de Productos y Servicios FastFlow

Este documento define la estructura de entrega de **FastFlow**, segmentada por uso, tipo de cliente y jerarquía organizacional. Nuestra meta es que cada usuario encuentre exactamente lo que necesita para acelerar su flujo.

---

## 1. Segmentación por Uso (General vs. Específico)

### 🟢 Uso General (Core FastFlow)
*Componentes transversales a cualquier stack o infraestructura.*
- **Metodología de las Capas**: Guías de la 0 a la 28 que cubren todo el SDLC y operaciones de día 2.
- **Universal Jenkinsfile**: Plantilla base con SAST, Testing y JUnit.
- **Manuales Maestros**: [Manual Técnico](docs/manuals/manual-tecnico.md) y [Manual de Usuario Final](docs/manuals/usuario-final.md).
- **Herramientas de Diagnóstico**: [Glosario](docs/GLOSARIO-FASTFLOW.md) y [Guía de Troubleshooting](docs/how-tos/troubleshooting-guia.md).
- **Seguridad Baseline**: RBAC, OAuth y Hardening de Jenkins.

### 🔵 Uso Específico (Verticales por Stack)
*Soluciones optimizadas para tecnologías determinadas.*
- **Stack Java/Spring**: Configuración de Maven, SonarQube y despliegue de JARs.
- **Stack Microservicios**: Webhooks, Multibranch y orquestación con Kubernetes.
- **Stack Multi-Cloud**: Módulos de Terraform para AWS/Azure/GCP y Packer para imágenes inmutables.

---

## 2. Segmentación por Tipo de Cliente

### 🏢 Clientes Enterprise (Escala y Gobernanza)
- **Shared Libraries**: Centralización de la inteligencia de CI/CD para cientos de equipos.
- **Kubernetes Avanzado**: Estrategias de multi-arquitectura y sidecars.
- **Auditoría y Compliance**: Reportes automáticos de seguridad y logs centralizados.

### 🚀 Clientes Startup (Velocidad y Agilidad)
- **Fast Track Implementation**: Despliegue de pipelines en minutos con el `QUESTIONARIO-IMPLEMENTACION.md`.
- **Serverless & Containers**: Foco en despliegues rápidos en AWS Lambda o Google Cloud Run.
- **Integración de Registry**: Automatización total desde el commit hasta el contenedor.

---

## 3. Segmentación Jerárquica (Rutas de Usuario)

Para facilitar la adopción, hemos creado portales específicos según el rol del usuario:

### 👑 [Portal Ejecutivo (Nivel Estratégico)](docs/HOME-EJECUTIVO.md)
*Para CEOs, CTOs y Managers.*
- **Valor de Negocio**: ROI de la automatización.
- **KPIs y Métricas**: Lead Time, Deployment Frequency, MTTR.
- **Visión de Producto**: ¿Por qué FastFlow importa hoy?

### 📐 [Portal de Arquitectura (Nivel Táctico)](docs/HOME-ARQUITECTO.md)
*Para Tech Leads y Arquitectos.*
- **Gobernanza**: Estándares de código y calidad (SonarQube).
- **Escalabilidad**: Arquitectura distribuida de Jenkins y Kubernetes.
- **Infraestructura como Código**: Terraform y gestión de estados.

### 🛠️ [Portal del Desarrollador (Nivel Operativo)](docs/HOME-DESARROLLADOR.md)
*Para Ingenieros de Software y DevOps.*
- **How-tos**: Guías paso a paso de configuración.
- **Debug y Desarrollo**: Configuración de VS Code y entornos locales.
- **Shared Libraries**: Cómo usar los pasos predefinidos de FastFlow.

---

*FastFlow: La estructura que tu organización necesita para dejar de luchar contra la tubería y empezar a fluir.*

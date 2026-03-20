# Portal de Arquitectura FastFlow (Gobernanza y Escalado)

Bienvenido, arquitecto de sistemas. Este portal está diseñado para ayudarte a diseñar y escalar la infraestructura de **FastFlow** en tu organización.

## 1. Arquitectura de Referencia
FastFlow utiliza un modelo de **Maestro y Agentes Efímeros** sobre Kubernetes, asegurando que la carga de trabajo no impacte el controlador principal.

### 📐 Componentes Clave
- **Infraestructura Inmutable**: Uso de Packer para generar AMIs/Imágenes base seguras y reproducibles.
- **Estado de Terraform**: Gestión centralizada de estados para evitar colisiones en despliegues concurrentes.
- **Shared Libraries**: Centralización de la lógica de CI/CD para que cada equipo use los mismos estándares.

## 2. Los 7 Ejes de Calidad (SonarQube)
Garantizamos la salud del código a través de:
1.  **Complejidad Ciclomática**: Mantén tu código simple.
2.  **Duplicidad (DRY)**: Centraliza la lógica repetida.
3.  **Cobertura de Tests**: Asegura que el código crítico esté probado.
4.  **Estándares de Codificación**: Unifica el estilo de desarrollo.
5.  **Comentarios de Calidad**: Documentación útil, no redundante.
6.  **Arquitectura y Diseño**: Control de dependencias y cohesión.
7.  **Errores Potenciales**: Identificación temprana de bugs lógicos.

## 3. Seguridad y Escalabilidad
- **Instalación por Escenario**: [Guía Maestra para Docker, K8s y Cloud](GUIA-INSTALACION-ESCENARIOS.md).
- **RBAC & OAuth**: Control de acceso granular para usuarios y sistemas.
- **Multibranch & Webhooks**: Orquestación automática para microservicios.
- **Registro y Observabilidad**: Centralización de logs (EFK) y métricas (Prometheus/Grafana).

## 4. Recursos para Arquitectos
- [Manual Técnico FastFlow](manuals/manual-tecnico.md)
- [Deep Dive: Arquitectura Inmutable](../marketing/articles/arquitectura-inmutable.md)
- [Deep Dive: DevSecOps Real con FastFlow](../marketing/articles/devsecops-real.md)
- [Guía de Arquitectura de Jenkins](GUIA-JENKINS-ARQUITECTURA.md)
- [Guía de Seguridad y Hardening](GUIA-SEGURIDAD-HARDENING.md)
- [Guía de Terraform IaC](GUIA-TERRAFORM-IAC.md)
- [Glosario FastFlow](GLOSARIO-FASTFLOW.md)

## 🏗️ Demos de Arquitectura
- [Esquema de Instalación por Escenarios](GUIA-INSTALACION-ESCENARIOS.md)
- [Diagrama de Orquestación de Microservicios](GUIA-MICROSERVICIOS-PAC.md)

---
*FastFlow: La infraestructura que escala contigo.*

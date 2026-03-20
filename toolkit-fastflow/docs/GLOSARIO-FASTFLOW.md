# Glosario FastFlow

Para asegurar que todos los miembros del equipo hablen el mismo idioma, aquí definimos los términos fundamentales del ecosistema **FastFlow**.

| Término | Definición |
| :--- | :--- |
| **Agente (Node)** | Servidor o contenedor donde se ejecutan las tareas del pipeline. FastFlow prefiere agentes efímeros en Kubernetes. |
| **Artefacto** | Resultado binario de un build (JAR, Docker Image, ZIP) que se puede desplegar. |
| **Blue Ocean** | Interfaz moderna de Jenkins para visualizar flujos de trabajo de forma intuitiva. |
| **Capa (Layer)** | Nivel de conocimiento o implementación en la metodología de 28 pasos de FastFlow. |
| **Controller (Maestro)** | El cerebro de Jenkins que orquestra los builds pero no los ejecuta. |
| **Declarative Pipeline** | Sintaxis estructurada de Jenkinsfile recomendada por FastFlow por su legibilidad. |
| **DORA Metrics** | 4 KPIs clave para medir el rendimiento de DevOps (Lead Time, DF, CFR, MTTR). |
| **FinOps** | Práctica de gestión financiera aplicada al consumo de nube e infraestructura. |
| **GitFlow** | Modelo de gestión de ramas (develop, feature, master) utilizado por FastFlow. |
| **IaC (Infrastructure as Code)** | Gestión de infraestructura mediante archivos de configuración (Terraform). |
| **Inmutabilidad** | Principio de no modificar servidores vivos, sino reemplazarlos por imágenes nuevas (Packer). |
| **Multibranch Pipeline** | Proyecto de Jenkins que descubre automáticamente ramas en Git y crea flujos para ellas. |
| **PaC (Pipeline as Code)** | Definición de flujos de CI/CD mediante un archivo `Jenkinsfile` versionado en Git. |
| **Post-Mortem** | Documento de análisis tras un incidente para identificar mejoras sistémicas. |
| **Quality Gate** | Punto de control en el pipeline que bloquea el paso si no se cumplen métricas de calidad (SonarQube). |
| **RBAC** | Control de acceso basado en roles para gestionar permisos de usuarios. |
| **SAST** | Static Application Security Testing. Escaneo de código fuente en busca de vulnerabilidades. |
| **Shared Library** | Repositorio de código Groovy reusable para centralizar lógica de pipelines. |
| **Webhook** | Gatillo automático enviado por Git a Jenkins para iniciar un build tras un push. |

---
*FastFlow: Hablamos el lenguaje de la agilidad.*

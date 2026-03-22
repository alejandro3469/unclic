# Metodología, stack, herramientas y valor aportado

Documento de referencia para incorporar en propuestas comerciales. Describe el stack técnico, las buenas prácticas de desarrollo y el valor que sustenta las estimaciones y los precios.

---

## 1. Stack y herramientas (base de la estimación)

Las estimaciones de tiempo y costo se apoyan en el uso de las siguientes herramientas y en la experiencia previa en proyectos con la misma metodología:

**Control de versiones y colaboración**
- Git: historial trazable, ramas, revisión de cambios.
- Gitea o equivalente: repositorio central, permisos, integración con pipeline.

**Pipeline y automatización**
- Jenkins: pipeline as code (Jenkinsfile), ejecución automática ante commits, jobs por proyecto (build, test, despliegue).
- Scripts de validación: validación de Jenkinsfile, Terraform, K8s y pruebas locales antes de subir.

**Build y calidad**
- Maven (proyectos Java): compilación, pruebas unitarias, empaquetado, gestión de dependencias.
- Java (JDK 11+): entorno de ejecución para aplicaciones backend y herramientas.
- Linters y buenas prácticas: código ordenado, documentación en repo.

**Contenedores e infraestructura**
- Docker: imágenes reproducibles, entorno consistente entre desarrollo y entrega.
- Registry (opcional): almacenamiento de imágenes versionadas, rollback conocido.
- Terraform: infraestructura como código (EC2, redes, security groups) cuando aplica.
- Kubernetes (opcional): orquestación y despliegue en cluster cuando el alcance lo requiere.

**Entorno de entrega**
- Servidores (p. ej. AWS EC2) o hosting acordado: despliegue en entorno estable, verificable.
- Nginx u otro servidor web: entrega de sitios estáticos o reverse proxy cuando aplica.

**Buenas prácticas aplicadas**
- Código versionado y documentado en repo; cambios revisables.
- Pipeline repetible: build, test y despliegue automatizados donde aplica.
- Entorno de pruebas antes de cierre: el entregable se prueba en un entorno acordado y se deja desplegado o listo para uso, de modo que la calidad sea verificable.
- Alcance y cláusulas por escrito: reducción de malentendidos y soporte legal.

---

## 2. Variables de proyecto (qué impacta tiempo y precio)

**Variables típicas que se contemplan en la estimación:**

- Número de entregables (módulos SCORM, pantallas, flujos, reportes).
- Complejidad de la lógica (fórmulas, reglas de negocio, integraciones).
- Número de rondas de revisión incluidas (base: 2; adicionales según cláusulas).
- Disponibilidad y calidad del material proporcionado por el cliente (contenido, datos, aprobaciones).
- Requisitos de seguridad y privacidad (encriptación, políticas, cumplimiento).
- Entorno de despliegue acordado (propio, cliente, nube) y nivel de acceso.
- Inclusión o no de reporte analítico, exportación PDF, integraciones con sistemas externos (cada uno se cotiza según anexo).

**Lo no especificado en la propuesta o en un anexo firmado no se considera incluido** en alcance, plazo ni precio.

---

## 3. Valor aportado (qué sustenta la cotización)

El precio no solo cubre “horas”: refleja el **valor** que se entrega:

**Expertise**
- Conocimiento del stack (Jenkins, Gitea, Maven, Docker, pipeline as code, despliegue).
- Experiencia en proyectos con metodología FastFlow (repetibilidad, trazabilidad, calidad verificable).

**Tiempo dedicado**
- Diseño, desarrollo, pruebas, revisiones y ajustes dentro del alcance acordado.
- Coordinación, documentación y entrega en el entorno acordado.

**Infraestructura y metodología**
- Uso de pipeline e infraestructura reproducible (build, test, despliegue).
- Entorno de pruebas y entrega definido, de modo que el resultado sea comprobable antes del cierre.

**Calidad y garantía**
- Entregable probado en el entorno acordado y desplegado o listo para uso según lo pactado.
- Código y entregables versionados y documentados; cláusulas claras de extensión de plazo y presupuesto para transparencia y seguridad jurídica.

Incluir este bloque (o un resumen) en cada propuesta permite al cliente entender **sobre qué base se estima** y **qué valor recibe** a cambio del precio.

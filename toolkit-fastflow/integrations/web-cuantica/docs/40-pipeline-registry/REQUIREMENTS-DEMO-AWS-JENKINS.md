# FastFlow: requerimientos y alineación con la demo (AWS, Jenkins, Docker, Registry, K8s, Terraform)

Este documento alinea el **producto FastFlow** en este repo con la **demo y el documento Fast Flow Enterprise**: Jenkins, Docker, Registry, Kubernetes y Terraform como un solo sistema.

---

## 1. Objetivo de la demo

Que cualquier persona entienda cómo una organización pasa de un flujo manual y frágil a un **flujo de entrega confiable, trazable y escalable** con:

- **Jenkins** — Coordina el flujo (build, tests, gates).
- **Docker** — Empaqueta la aplicación de forma consistente.
- **Registry** — Gobierna versiones y trazabilidad de imágenes.
- **Kubernetes** — Ejecuta y mantiene los contenedores en estado saludable.
- **Terraform** — Infraestructura como código, reproducible.

FastFlow (este producto) es la **aplicación de ejemplo** que se construye, empaqueta y despliega en ese pipeline.

---

## 2. Qué está implementado en este repo (estado actual)

| Pieza | Estado | Dónde / nota |
|-------|--------|----------------|
| **Aplicación FastFlow** | ✅ | Servidor Express (server/), API (health, leads, admin, /app con token), estáticos (landing, free-tools, docs, contacto, proveedores). |
| **Montar en local** | ✅ | docs/30-instalacion/, docs/60-pos-online/ (pasos y guías). Maven, opcional Jenkins, registry local, dashboard. |
| **Portal de clientes** | ✅ | /app con token; recursos desde server/data/deliverables.json; enlace a código abierto e instalación. |
| **Instalación open source** | ✅ | INSTALAR-FASTFLOW.md (Node 5 min, Docker, verificación, tests). Descargable desde el repo. |
| **Docker (imagen)** | ✅ | Dockerfile en raíz de pos-online; build según docs/40-pipeline-registry/BUILD-REGISTRY-FASTFLOW.md. |
| **Registry (push)** | 📄 Documentado | BUILD-REGISTRY-FASTFLOW.md: tag + push cuando el registry esté definido (Docker Hub, GitLab, AWS ECR). No se hace push desde el repo hasta configurarlo. |
| **Jenkins (pipeline)** | ✅ Documentado | **docs/JENKINS-PIPELINE-FASTFLOW.md** — Jenkinsfile de ejemplo, variables, credenciales, capturas. Pipeline: checkout → test → build imagen → push a registry. Alineado a demo AWS. |
| **Kubernetes** | ✅ Referencia en repo | **deploy/k8s/** — namespace, ConfigMap, Deployment, Service para FastFlow; README con orden de aplicación y verificación. |
| **Terraform** | ✅ Referencia en repo | **deploy/terraform/** — módulo con provider Kubernetes (namespace, ConfigMap, Deployment, Service); variables para imagen y BASE_URL; listo para cluster existente. |

---

## 3. Orden sugerido para la demo (local → portal → repo → pipeline)

1. **Local con capturas:** Seguir la guía local en **docs/30-instalacion/** y **docs/60-pos-online/**: Maven, Jenkins (opcional), registry local, dashboard. Guardar capturas en **docs/capturas/** si aplica (ver docs/capturas/README.md).
2. **Portal:** En **fastflow.html** la sección "Demo en AWS e instalación open source" y "Instalar en tu entorno" enlazan a INSTALACION-OPEN-SOURCE.md y docs/INSTALACION-FASTFLOW-LOCAL.md. Los clientes con token ven en /app los recursos y el enlace a código abierto.
3. **Repo:** Cualquiera puede clonar y seguir **README.md** o **INSTALACION-OPEN-SOURCE.md** (e instalación rápida con script **scripts/install-local.sh**) para tener FastFlow en pocos pasos (Node o Docker).
4. **Pipeline (Jenkins):** **docs/JENKINS-PIPELINE-FASTFLOW.md** — Jenkinsfile de ejemplo, build → test → imagen → push a registry; mismo flujo que la demo en AWS.
5. **K8s / Terraform:** Según la demo AWS o el entorno del cliente; fuera del alcance de este repo pero alineado al documento maestro.

---

## 4. Referencias cruzadas

- **Documento maestro (webinar, landing, pricing):** FAST-FLOW-ENTERPRISE-MAESTRO-RAW.md (raíz del repo pipeline-as-code-with-jenkins).
- **Build y registry:** docs/BUILD-REGISTRY-FASTFLOW.md.
- **Tarea multiagentes (registry + empaquetado):** docs/TAREA-FASTFLOW-REGISTRY-Y-EMPAQUETADO.md, docs/AGENTES-FASTFLOW-REGISTRY-EMPAQUETADO.md.
- **Presentación en vivo:** orden Registry → Jenkins → K8s → Terraform; puntos a mostrar en docs/60-pos-online y docs/40-pipeline-registry.

---

## 5. Resumen

- **FastFlow** está implementado según los requerimientos: local (con guía y capturas), portal (recursos + código abierto), repo (instalación fácil).  
- **Docker y Registry** están listos a nivel de imagen y documentación; el paso Jenkins → build → push se deja implementado o documentado según el entorno.  
- **K8s y Terraform** quedan en el repo de docs y en la demo AWS; este repo entrega la aplicación y la documentación para integrarla en ese sistema.

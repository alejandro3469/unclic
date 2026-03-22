# Requisitos — POS Online + implementación FastFlow

Requisitos para **construir, probar y desplegar** POS Online con el pipeline y el registry (implementación FastFlow).

---

## Obligatorios

- **JDK 11+** (para compilar y ejecutar POS Online).
- **Maven** (para `mvn clean install`, tests y package).
- **generic-model** — Repo local en la **misma carpeta padre** que pos-online. Dependencia obligatoria; debe estar disponible según la configuración del proyecto (Maven local, módulo, etc.).
- **Git** (para clonar y trabajar con el repo).

---

## Para pipeline (Jenkins) y registro de imágenes

- **Docker** — Para construir la imagen de la aplicación y, si aplica, levantar un registry local (`docker run -d -p 5000:5000 registry:2`).
- **Jenkins** (opcional) — Para ejecutar el pipeline desde commits. Ver [INSTALAR-JENKINS.md](INSTALAR-JENKINS.md). Requiere **Java 17 o superior** en el host donde corre Jenkins (desde Jenkins 2.463+, [política oficial](https://www.jenkins.io/doc/book/platform-information/support-policy-java/)).

---

## Para despliegue (Kubernetes / Terraform)

- **kubectl** — Si se usan los manifiestos en `deploy/k8s/` y se tiene un cluster (minikube, kind, EKS, GKE, etc.).
- **Terraform** — Si se usa la configuración en `deploy/terraform/` para provisionar o desplegar en un cluster.

---

## Resumen rápido

| Uso | Necesitas |
|-----|------------|
| Build y tests (Maven) | JDK 11+, Maven, generic-model (repo hermana) |
| Pipeline local (script) | Lo anterior + Docker |
| Jenkins (servidor) | Java 17+, Jenkins instalado; ver INSTALAR-JENKINS.md |
| Registry local | Docker (`registry:2`) |
| Despliegue K8s | kubectl, cluster |
| Despliegue Terraform | Terraform, cluster configurado |

Documentación: [README de docs](../README.md).

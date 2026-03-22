# Tests y validaciones — pos-online + FastFlow

Este documento describe **tests y validaciones** para pos-online con implementación FastFlow: Maven, scripts de pipeline (Jenkins), Terraform, Kubernetes y registry.

---

## 1. Tests Maven (pos-online)

Desde la **raíz del repo pos-online** (donde está `pom.xml`):

```bash
mvn clean test -q
```

Cubre unitarios e integración definidos en el proyecto (JUnit, etc.). Si este toolkit se usa sin `pom.xml`, los scripts de validación omiten Maven.

---

## 2. Validaciones de infra y pipeline (scripts)

Desde la **raíz de pos-online** (o este toolkit):

```bash
bash scripts/run-all-validations.sh
```

Orden típico:

1. **Maven test** — Si existe `pom.xml`: `mvn clean test -q`.
2. **Validación Jenkinsfile** — `scripts/validate-jenkinsfile.sh` (stages Test, Build image, Push to registry).
3. **Validación Terraform** — `scripts/validate-terraform.sh` en `deploy/terraform/`, si `terraform` está en PATH.
4. **Validación K8s** — `scripts/validate-k8s.sh` (dry-run de `deploy/k8s/`), si `kubectl` está en PATH.
5. **Simulación pipeline** — `scripts/simulate-jenkins-pipeline.sh` (Maven + opcional Docker; por defecto `SKIP_DOCKER=1`).

### Scripts individuales

| Script | Uso | Requisito |
|--------|-----|-----------|
| **validate-jenkinsfile.sh** | Comprueba etapas en Jenkinsfile.example (o ruta indicada) | Ninguno |
| **validate-terraform.sh** | init + validate en deploy/terraform | Terraform >= 1.0 |
| **validate-k8s.sh** | dry-run de manifiestos en deploy/k8s | kubectl (opcional) |
| **test-registry.sh** | docker pull desde registry (solo si REGISTRY está definido) | Docker, REGISTRY |
| **run-all-validations.sh** | Maven test + validaciones Jenkinsfile/Terraform/K8s + simulación pipeline | Ver cada script |

---

## 3. Pruebas locales (manual)

- **Build y push (sin Jenkins):**  
  `bash scripts/build-and-push.sh`  
  Ejecuta `mvn test` → `mvn package` → `docker build` (si hay Dockerfile) → push si `REGISTRY` está definido.

- **Test de registry:**  
  `REGISTRY=localhost:5000 bash scripts/test-registry.sh`  
  Comprueba que la imagen `pos-online` se puede hacer pull desde el registry.

---

## 4. Resumen de cobertura

| Área | Qué se valida |
|------|----------------|
| **Build y tests** | Maven (compile, test, package). |
| **Jenkins** | Jenkinsfile.example (stages); build-and-push.sh. |
| **Terraform** | deploy/terraform (init, validate). |
| **Kubernetes** | deploy/k8s (manifiestos, dry-run). |
| **Registry** | test-registry.sh (pull de imagen pos-online). |
| **Pipeline simulado** | simulate-jenkins-pipeline.sh (Maven + opcional Docker). |

El código relevante (scripts, Terraform, K8s, Jenkinsfile) está documentado con comentarios en cabeceras para mantenimiento y presentación al usuario final.

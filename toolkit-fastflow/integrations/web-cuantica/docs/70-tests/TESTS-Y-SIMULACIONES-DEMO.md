# Tests y simulaciones — pos-online + FastFlow

Este documento describe **tests** (Maven) y **simulaciones/pruebas locales** para pos-online: Jenkins, Terraform, Kubernetes y Registry.

---

## 1. Tests Maven (pos-online)

| Comando | Descripción |
|---------|-------------|
| `mvn clean test -q` | Suite de tests del proyecto (unitarios e integración según pom.xml). |

Ejecutar desde la **raíz del repo pos-online** (donde está `pom.xml`). Si en este toolkit no hay `pom.xml`, los scripts omiten Maven.

---

## 2. Simulación del pipeline Jenkins (local)

Sin levantar Jenkins, puedes ejecutar los **mismos pasos** que el Jenkinsfile:

```bash
# Desde la raíz de pos-online (o este toolkit)
bash scripts/simulate-jenkins-pipeline.sh
```

- **Stage Test:** `mvn test -q` en la raíz.
- **Stage Build image:** `docker build -t pos-online:latest .` (si existe Dockerfile en la raíz).
- **Stage Push:** solo si defines `REGISTRY=...` (ej. `REGISTRY=localhost:5000`).

Omitir la construcción Docker (solo tests):

```bash
SKIP_DOCKER=1 bash scripts/simulate-jenkins-pipeline.sh
```

---

## 3. Validación Jenkinsfile

Comprueba que `Jenkinsfile.example` tiene la estructura esperada (stages Test, Build image, Push to registry):

```bash
bash scripts/validate-jenkinsfile.sh
```

O con otro archivo: `bash scripts/validate-jenkinsfile.sh path/to/Jenkinsfile`

---

## 4. Validación Terraform

Valida el Terraform de FastFlow en Kubernetes (`deploy/terraform/`) sin aplicar cambios:

```bash
bash scripts/validate-terraform.sh
```

- Requiere: `terraform` instalado.
- Ejecuta: `terraform init -backend=false`, `terraform validate`, `terraform plan` con variables por defecto.

---

## 5. Validación Kubernetes (manifiestos)

Valida los YAML de `deploy/k8s/` con `kubectl apply --dry-run=client`:

```bash
bash scripts/validate-k8s.sh
```

- Requiere: `kubectl` instalado.
- No requiere cluster activo; solo verifica sintaxis y esquema.

---

## 6. Prueba de Registry (pull)

Tras hacer push de la imagen al registry (local o remoto), verifica que se puede hacer pull:

```bash
REGISTRY=localhost:5000 bash scripts/test-registry.sh
```

Variables opcionales: `IMAGE_NAME`, `IMAGE_TAG`. Si `REGISTRY` no está definido, el script sale con 0 (omitir en CI sin registry).

---

## 7. Ejecutar todas las validaciones locales (smoke)

Desde la raíz de pos-online (o este toolkit):

```bash
bash scripts/run-all-validations.sh
```

O paso a paso:

```bash
# 1. Tests Maven (si hay pom.xml)
mvn clean test -q

# 2. Validar Jenkinsfile
bash scripts/validate-jenkinsfile.sh

# 3. Validar Terraform (opcional, requiere terraform)
bash scripts/validate-terraform.sh

# 4. Validar K8s (opcional, requiere kubectl)
bash scripts/validate-k8s.sh

# 5. Simular pipeline (opcional, requiere Docker)
SKIP_DOCKER=1 bash scripts/simulate-jenkins-pipeline.sh
```

Con Docker instalado y sin `SKIP_DOCKER`, la simulación hace además `docker build`.

---

## 8. Resumen por tecnología

| Tecnología | Tipo | Comando / archivo |
|------------|------|--------------------|
| **Maven** | Tests (unitarios e integración) | `mvn clean test -q` |
| **Jenkins** | Simulación pipeline | `scripts/simulate-jenkins-pipeline.sh` |
| **Jenkins** | Validación Jenkinsfile | `scripts/validate-jenkinsfile.sh` |
| **Terraform** | Validación (init, validate, plan) | `scripts/validate-terraform.sh` |
| **Kubernetes** | Validación manifiestos (dry-run) | `scripts/validate-k8s.sh` |
| **Registry** | Test pull tras push | `scripts/test-registry.sh` (con REGISTRY=...) |

---

## Referencias

- **Pipeline y registry:** `Jenkinsfile.example`, `scripts/build-and-push.sh`, `docs/40-pipeline-registry/JENKINS-PIPELINE-FASTFLOW.md`
- **Registry (tags, rollback):** `server/lib/registry-tags.js`, `server/test/registry-tags-rollback.test.js`
- **Config:** `server/config.js` (registry, env)
- **Deploy:** `deploy/terraform/`, `deploy/k8s/`

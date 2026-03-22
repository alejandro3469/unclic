# Tests y validación local — POS Online + FastFlow

Resumen de **validaciones** y **scripts** para el repo pos-online con la implementación FastFlow. No hay servidor Node; el proyecto es Java (Maven). Las comprobaciones se hacen con Maven, scripts bash y (opcional) Terraform/kubectl.

---

## Validaciones desde la raíz del repo (pos-online)

```bash
bash scripts/run-all-validations.sh
```

Ejecuta (cuando aplica):

1. **Maven test** — Si existe `pom.xml`: `mvn clean test -q`.
2. **Jenkinsfile** — Comprueba que tenga stages Test, Build image, Push (`scripts/validate-jenkinsfile.sh`).
3. **Terraform** — Si `terraform` está en PATH: `terraform validate` en `deploy/terraform/`.
4. **K8s** — Si `kubectl` está en PATH: `kubectl apply --dry-run=client -f deploy/k8s/` (o mensaje si no hay cluster).
5. **Simulación pipeline** — Si existe `pom.xml`: `mvn test`, `mvn package`, sin construir imagen (`SKIP_DOCKER=1`).

Si no hay `pom.xml` (p. ej. estás en la carpeta del toolkit sin el código pos-online), se omiten Maven y simulación.

---

## Scripts individuales

| Script | Requisito | Qué hace |
|--------|-----------|----------|
| **scripts/build-and-push.sh** | Maven, Docker | Pipeline local: mvn test → package → docker build → (opcional) push si `REGISTRY=...` |
| **scripts/simulate-jenkins-pipeline.sh** | Maven, Docker (opcional) | Simula el Jenkinsfile; si no hay `pom.xml`, sale sin error. |
| **scripts/validate-jenkinsfile.sh** | — | Comprueba etapas en Jenkinsfile.example (o ruta indicada). |
| **scripts/validate-terraform.sh** | Terraform (opcional) | `terraform validate` en deploy/terraform/. |
| **scripts/validate-k8s.sh** | kubectl (opcional) | Valida manifiestos en deploy/k8s/. |
| **scripts/test-registry.sh** | Docker, REGISTRY definido | `docker pull` de la imagen (pos-online por defecto). |

---

## Test de registry (opcional)

Con un registry local o remoto:

```bash
REGISTRY=localhost:5000 bash scripts/test-registry.sh
```

Imagen por defecto: `pos-online:latest`. Variables: `IMAGE_NAME`, `IMAGE_TAG`.

---

Documentación general: [README de docs](../README.md).

# Plan detallado — FastFlow POS: Jenkins + Groovy + scripts + Terraform + Pulumi + LocalStack

Este documento es la **fuente de verdad** del diseño antes de tocar código. Evita adivinar cómo encajan las piezas.

---

## 1. Objetivo

- **Un solo repo canónico** para el pipeline POS en esta integración: `repo-pos-fastflow/` (Jenkinsfile en la raíz).
- **Commit → Maven → Docker → Registry → (opcional) IaC** sin romper el flujo actual (aprobar deploy en `main`).
- **IaC opcional** activada por variables del job Jenkins (`FASTFLOW_*`), no por defecto.
- **LocalStack** solo para laboratorio (Terraform/Pulumi apuntando a `localhost:4566`); el agente Jenkins debe poder alcanzar ese endpoint si usas esos stages en CI.

---

## 2. Mapa de archivos (qué es cada cosa)

| Ruta | Rol |
|------|-----|
| `Jenkinsfile` | Pipeline declarativo principal. Llama scripts en `.jenkins/scripts/` y documenta variables `FASTFLOW_*`. |
| `.jenkins/README.md` | Índice humano de la carpeta `.jenkins/`. |
| `.jenkins/groovy/fastflowIacHelpers.groovy` | Cargado con `load` desde el Jenkinsfile; encapsula mensajes y rutas para mantener el Jenkinsfile legible. |
| `.jenkins/scripts/*.sh` | Bash **comentado línea a línea** para Terraform/Pulumi; Jenkins solo ejecuta `bash ...` (sin lógica compleja en Groovy). |
| `deploy/terraform/` | Terraform **Kubernetes** (POS en cluster). Provider `kubernetes`; imagen vía `var.pos_image` (sale del registry tras push). |
| `deploy/terraform-localstack/` | Terraform **AWS API** contra LocalStack (bucket S3 de laboratorio). **No** despliega el JAR del POS. |
| `deploy/pulumi/` | Pulumi TypeScript — bucket S3 de ejemplo; configurable para LocalStack vía config/stack env. |
| `deploy/sst/README.md` | **No** hay app SST en este repo Java; documenta por qué y cuándo tendría sentido SST (otro repo / BFF Node). |

---

## 3. Flujo de datos (cómo conecta con el resto)

1. **Gitea** hace webhook a **Jenkins** → checkout de este repo.
2. Stages **Build → Test → Package → Build image → Push to registry** producen imagen `${REGISTRY}/pos-online:${BUILD_NUMBER}`.
3. **Opcional** — si `FASTFLOW_TF_VALIDATE=true`, se ejecuta `terraform validate` en `deploy/terraform` (requiere `kubectl`/kubeconfig válido en el agente).
4. **Opcional** — si `FASTFLOW_TF_PLAN_K8S=true`, se ejecuta `terraform plan` con `-var="pos_image=${REGISTRY}/pos-online:${IMAGE_TAG}"` para comprobar el manifiesto K8s contra el cluster configurado en `KUBECONFIG`.
5. **Opcional** — si `FASTFLOW_TF_PLAN_LOCALSTACK=true`, se ejecuta plan contra `deploy/terraform-localstack` usando `LOCALSTACK_ENDPOINT` (típicamente `http://host.docker.internal:4566` desde contenedor Jenkins, o `http://localhost:4566` en agente bare metal).
6. **Opcional** — si `FASTFLOW_PULUMI_PREVIEW=true`, `pulumi preview` en `deploy/pulumi` (requiere `pulumi` CLI y login a Pulumi Cloud o backend local).
7. **Approve Deploy → Cleanup → Deploy → Verify** — sin cambio semántico respecto al Jenkinsfile anterior (Docker/JAR remoto o local).

---

## 4. Dependencias en el agente Jenkins

| Herramienta | Obligatoria | Solo si activas |
|-------------|-------------|-----------------|
| Java 17, Maven | Sí | — |
| Docker | Si hay Dockerfile / registry | — |
| Terraform | No | `FASTFLOW_TF_*` |
| `kubectl` + kubeconfig | No | `FASTFLOW_TF_VALIDATE` o `FASTFLOW_TF_PLAN_K8S` |
| Pulumi CLI | No | `FASTFLOW_PULUMI_PREVIEW` |
| LocalStack | No | Plan localstack desde máquina que vea el puerto 4566 |

---

## 5. Variables Jenkins recomendadas (resumen)

Documentadas también en cabecera del `Jenkinsfile`:

- `REGISTRY`, `POS_DEPLOY_*` — ya existentes.
- `FASTFLOW_TF_VALIDATE` — `true` / omitir.
- `FASTFLOW_TF_PLAN_K8S` — `true` / omitir.
- `FASTFLOW_TF_PLAN_LOCALSTACK` — `true` / omitir.
- `LOCALSTACK_ENDPOINT` — ej. `http://localhost:4566`.
- `FASTFLOW_PULUMI_PREVIEW` — `true` / omitir.
- `PULUMI_STACK` — ej. `dev` (nombre del stack Pulumi en `deploy/pulumi`).

---

## 6. Relación con UnClic (sitio estático)

- El landing **no** ejecuta Terraform/Pulumi; solo muestra enlaces si configuras `NEXT_PUBLIC_DEMO_*_URL` en el build del sitio.
- Guía operativa E2E: `unclic/docs/DEMOS-PRODUCCION-END-TO-END.md`.
- LocalStack conceptual: `unclic/docs/LOCALSTACK-IAC-JENKINS-POS.md`.

---

## 7. generic-model

- El POM del POS puede depender de **generic-model** en carpeta hermana; el pipeline asume que el checkout del job ya trae el layout correcto (o artefacto en `.m2`). No duplicamos lógica Maven aquí; si tu job necesita checkout múltiple, documentarlo en el job Jenkins, no solo en este plan.

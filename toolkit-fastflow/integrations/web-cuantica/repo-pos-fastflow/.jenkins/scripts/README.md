# Scripts bash — stages opcionales IaC (FastFlow POS)

Todos son invocados desde el **`Jenkinsfile`** raíz con:

```bash
bash .jenkins/scripts/<nombre>.sh
```

## Requisitos previos en el agente

| Script | Requiere |
|--------|-----------|
| `terraform-validate-k8s.sh` | `terraform` en PATH, `deploy/terraform/*.tf`, acceso kubeconfig si el provider lo exige al validar. |
| `terraform-plan-k8s.sh` | Lo anterior + variables `REGISTRY`, `IMAGE_NAME`, `IMAGE_TAG` exportadas por Jenkins + kubeconfig. |
| `terraform-plan-localstack.sh` | `terraform` + LocalStack accesible en `LOCALSTACK_ENDPOINT`. |
| `pulumi-preview.sh` | `pulumi` CLI + Node deps instalados en `deploy/pulumi` (`npm ci`) + login Pulumi si usas cloud backend. |

## Permisos

Tras clonar, si Git no preserva ejecutable:

```bash
chmod +x .jenkins/scripts/*.sh
```

## Resumen por archivo

- **terraform-validate-k8s.sh** — `terraform init -backend=false` + `validate` en `deploy/terraform` (sin apply).
- **terraform-plan-k8s.sh** — `plan` con `-var="pos_image=..."` alineado a la imagen recién construida.
- **terraform-plan-localstack.sh** — plan contra APIs emuladas (S3 bucket de lab).
- **pulumi-preview.sh** — `pulumi preview` en `deploy/pulumi`.

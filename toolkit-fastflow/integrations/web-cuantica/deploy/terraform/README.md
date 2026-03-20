# Terraform — FastFlow (referencia para agentes y repo de docs)

**Objetivo:** Infraestructura como código para el despliegue de FastFlow. Este módulo de referencia puede usarse en el repo pos-online o en la demo AWS.

## Opciones de uso

1. **Desplegar FastFlow en un cluster K8s existente** (este directorio): usa el provider `kubernetes` para aplicar los manifiestos en `../k8s/`. El cluster (EKS, GKE, minikube, etc.) debe existir y `kubectl` debe estar configurado.
2. **Provisionar el cluster + la app**: normalmente se hace en el repo de docs (módulos Terraform para EKS/GKE + llamada a este módulo o a los YAML de `deploy/k8s/`).

## Requisitos

- Terraform >= 1.0
- `kubectl` configurado apuntando al cluster donde quieres desplegar FastFlow (ej. `kubeconfig` o variables de entorno del provider)

## Uso rápido

```bash
cd deploy/terraform
terraform init
terraform plan
terraform apply
```

Esto crea el namespace, ConfigMap, Deployment y Service de FastFlow en el cluster actual.

## Variables

| Variable | Descripción | Default |
|----------|-------------|--------|
| `fastflow_image` | Imagen completa (registry + tag) del servidor FastFlow | `fastflow-server:latest` |
| `base_url` | BASE_URL para el servidor (ej. URL pública) | `http://localhost:3000` |
| `namespace` | Namespace de Kubernetes para FastFlow | `fastflow` |

Pasar con `-var` o `terraform.tfvars` (no versionar secretos).

## Validación local

```bash
cd deploy/terraform
terraform init -backend=false
terraform validate
# o desde la raíz del repo: bash scripts/validate-terraform.sh
```

Los tests en `server/test/terraform-validate.test.js` comprueban la existencia de .tf y, si terraform está en PATH, ejecutan `terraform validate`.

## Referencias

- **Manifiestos K8s:** `deploy/k8s/`
- **Build y registry:** `docs/pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md`
- **Requerimientos demo:** `docs/pipeline-y-registry/REQUIREMENTS-DEMO-AWS-JENKINS.md`
- **Tests y scripts:** `server/test/README.md`, `scripts/validate-terraform.sh`
- Para el resto de la infra (cluster, registry) ver docs/pipeline-y-registry y la documentación del entorno.

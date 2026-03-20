#!/usr/bin/env bash
# =============================================================================
# terraform-plan-k8s.sh — FastFlow POS
# -----------------------------------------------------------------------------
# Propósito:
#   Ejecutar `terraform plan` contra deploy/terraform/ usando como imagen del
#   contenedor POS la misma referencia que acaba de construir el pipeline
#   Docker (REGISTRY/IMAGE_NAME:IMAGE_TAG).
#
# Quién llama:
#   Jenkinsfile tras "Push to registry" si FASTFLOW_TF_PLAN_K8S=true.
#
# Variables de entorno (las define Jenkins environment { }):
#   REGISTRY     — ej. registry.unclic.consulting o 10.0.0.1:5000
#   IMAGE_NAME   — por defecto pos-online (coincide con Jenkinsfile)
#   IMAGE_TAG    — típicamente BUILD_NUMBER
#
# Requisitos:
#   - terraform en PATH
#   - kubeconfig válido para el cluster objetivo (misma máquina que kubectl)
#
# No hace:
#   - terraform apply (solo plan; revisión humana o otro job para apply)
# =============================================================================
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
TF_DIR="$REPO_ROOT/deploy/terraform"

# Valores por defecto si el job no exportara algo (defensivo)
REGISTRY="${REGISTRY:-}"
IMAGE_NAME="${IMAGE_NAME:-pos-online}"
IMAGE_TAG="${IMAGE_TAG:-latest}"

if [[ -z "${REGISTRY}" ]]; then
  echo "[FastFlow/IaC] WARN: REGISTRY vacío; uso pos_image local ${IMAGE_NAME}:${IMAGE_TAG}"
  POS_IMAGE="${IMAGE_NAME}:${IMAGE_TAG}"
else
  # Misma convención que docker tag en Jenkinsfile: REGISTRY/IMAGE_NAME:TAG
  POS_IMAGE="${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
fi

echo "[FastFlow/IaC] terraform-plan-k8s: pos_image=$POS_IMAGE"

if [[ ! -d "$TF_DIR" ]]; then
  echo "[FastFlow/IaC] ERROR: no existe $TF_DIR"
  exit 1
fi

cd "$TF_DIR"

terraform init -input=false -backend=false

# -input=false evita prompts en CI
# -var pasa la imagen que el cluster debería usar cuando alguien haga apply
echo "[FastFlow/IaC] terraform plan -var=pos_image=..."
# Si el plan detecta drift o errores de API, exit code != 0 y Jenkins falla el stage.
terraform plan -input=false -var="pos_image=${POS_IMAGE}" -out=tfplan-pos.bin

echo "[FastFlow/IaC] terraform-plan-k8s: OK (plan guardado en tfplan-pos.bin)"
</think>
Fix the script - remove || true from terraform plan

<｜tool▁calls▁begin｜><｜tool▁call▁begin｜>
StrReplace
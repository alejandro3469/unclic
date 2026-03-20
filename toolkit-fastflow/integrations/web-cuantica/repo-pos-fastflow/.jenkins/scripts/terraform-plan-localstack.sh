#!/usr/bin/env bash
# =============================================================================
# terraform-plan-localstack.sh — FastFlow POS (laboratorio)
# -----------------------------------------------------------------------------
# Propósito:
#   Plan de Terraform contra LocalStack (APIs AWS emuladas en localhost o red).
#   Crea/recursos de ejemplo (p. ej. bucket S3) en deploy/terraform-localstack/.
#   NO despliega la aplicación POS Java; es solo coherencia de pipeline + IaC.
#
# Quién llama:
#   Jenkinsfile si FASTFLOW_TF_PLAN_LOCALSTACK=true.
#
# Variables:
#   LOCALSTACK_ENDPOINT — por defecto http://localhost:4566
#     Si Jenkins corre en Docker y LocalStack en el host Mac:
#       http://host.docker.internal:4566
#
# Documentación alineada:
#   unclic/docs/LOCALSTACK-IAC-JENKINS-POS.md
#   LocalStack Terraform: https://docs.localstack.cloud/user-guide/integrations/terraform/
# =============================================================================
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
TF_DIR="$REPO_ROOT/deploy/terraform-localstack"
LOCALSTACK_ENDPOINT="${LOCALSTACK_ENDPOINT:-http://localhost:4566}"

echo "[FastFlow/IaC] terraform-plan-localstack: endpoint=$LOCALSTACK_ENDPOINT"
echo "[FastFlow/IaC] terraform-plan-localstack: TF_DIR=$TF_DIR"

if [[ ! -d "$TF_DIR" ]]; then
  echo "[FastFlow/IaC] ERROR: no existe $TF_DIR"
  exit 1
fi

cd "$TF_DIR"

# Pasamos el endpoint como variable TF declarada en variables.tf del módulo
export TF_VAR_localstack_endpoint="$LOCALSTACK_ENDPOINT"

terraform init -input=false -backend=false
terraform plan -input=false -var="localstack_endpoint=${LOCALSTACK_ENDPOINT}" -out=tfplan-localstack.bin

echo "[FastFlow/IaC] terraform-plan-localstack: OK"

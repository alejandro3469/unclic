#!/usr/bin/env bash
# =============================================================================
# Valida la configuración Terraform de FastFlow (deploy/terraform/).
# Uso: bash scripts/validate-terraform.sh
# Requiere: terraform en PATH. Ejecuta init -backend=false y validate.
# =============================================================================
set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$ROOT"

if [ ! -d "deploy/terraform" ]; then
  echo "No existe deploy/terraform; omitiendo validación."
  exit 0
fi

if ! command -v terraform &>/dev/null; then
  echo "terraform no está en PATH; omitiendo validación."
  exit 0
fi

echo "=== Validando Terraform (deploy/terraform) ==="
cd deploy/terraform
terraform init -backend=false -input=false
terraform validate
echo "=== Terraform OK ==="

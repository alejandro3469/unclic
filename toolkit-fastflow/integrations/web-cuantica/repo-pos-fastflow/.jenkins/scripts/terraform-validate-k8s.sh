#!/usr/bin/env bash
# =============================================================================
# terraform-validate-k8s.sh — FastFlow POS
# -----------------------------------------------------------------------------
# Propósito:
#   Validar sintaxis y configuración de Terraform del manifiesto Kubernetes
#   en deploy/terraform/ SIN aplicar cambios (no modify cluster).
#
# Quién llama:
#   Jenkinsfile, stage "IaC: Terraform validate (K8s POS)", cuando
#   FASTFLOW_TF_VALIDATE=true.
#
# Requisitos:
#   - terraform >= 1.0 en PATH
#   - Ficheros deploy/terraform/*.tf presentes tras checkout
#   - KUBECONFIG apuntando a un cluster accesible si validate requiere
#     conexión al provider (depende de versión provider kubernetes)
#
# Salida:
#   Código 0 si validate OK; distinto de 0 si falla (el pipeline marca rojo).
# =============================================================================
set -euo pipefail

# Directorio del script → subir a .jenkins → raíz repo es ../..
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Raíz del repo pos-fastflow (donde están pom.xml y deploy/)
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
# Módulo Terraform que declara namespace + deployment + service del POS
TF_DIR="$REPO_ROOT/deploy/terraform"

echo "[FastFlow/IaC] terraform-validate-k8s: REPO_ROOT=$REPO_ROOT"
echo "[FastFlow/IaC] terraform-validate-k8s: TF_DIR=$TF_DIR"

# Comprobamos que el módulo existe (evita error críptico si path mal clonado)
if [[ ! -d "$TF_DIR" ]]; then
  echo "[FastFlow/IaC] ERROR: no existe $TF_DIR"
  exit 1
fi

cd "$TF_DIR"

# -backend=false evita error si no hay backend remoto configurado en CI;
# solo queremos validar gráfico de recursos localmente.
echo "[FastFlow/IaC] terraform init -backend=false ..."
terraform init -input=false -backend=false

# validate comprueba tipos y atributos; no ejecuta plan contra API salvo que
# el provider lo exija en tu versión.
echo "[FastFlow/IaC] terraform validate ..."
terraform validate

echo "[FastFlow/IaC] terraform-validate-k8s: OK"

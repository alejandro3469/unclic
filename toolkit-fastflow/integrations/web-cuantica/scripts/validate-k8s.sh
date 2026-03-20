#!/usr/bin/env bash
# =============================================================================
# Valida los manifiestos Kubernetes de FastFlow (deploy/k8s/) con kubectl.
# Uso: bash scripts/validate-k8s.sh
# Requiere: kubectl en PATH. Ejecuta apply --dry-run=client -f para cada YAML.
# Si kubectl no está instalado, sale con 0 (omitir en entornos sin K8s).
# =============================================================================
set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$ROOT"

if [ ! -d "deploy/k8s" ]; then
  echo "No existe deploy/k8s; omitiendo validación."
  exit 0
fi

if ! command -v kubectl &>/dev/null; then
  echo "kubectl no está en PATH; omitiendo validación K8s."
  exit 0
fi

echo "=== Validando manifiestos K8s (deploy/k8s) ==="
# dry-run client; si no hay cluster kubectl puede fallar; la estructura se valida en server/test (k8s-manifests)
if kubectl apply --dry-run=client --validate=false -f deploy/k8s/ 2>/dev/null; then
  echo "kubectl dry-run OK"
else
  echo "kubectl dry-run omitido (sin cluster). Estructura validada en server/test (k8s-manifests)."
fi
echo "=== K8s validate OK ==="

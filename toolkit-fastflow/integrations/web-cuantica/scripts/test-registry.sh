#!/usr/bin/env bash
# =============================================================================
# Test de registry: verifica que la imagen se puede hacer pull desde el registry.
# Ejecutar tras push (build-and-push.sh o Jenkins) o en CI para validar el flujo.
# Uso:
#   REGISTRY=localhost:5000 bash scripts/test-registry.sh
#   REGISTRY=registry.gitlab.com/grupo/proyecto IMAGE_NAME=fastflow-server IMAGE_TAG=latest bash scripts/test-registry.sh
# Si REGISTRY no está definido, el script sale con 0 (omitir test).
# =============================================================================
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
# Cargar config central si existe (config/fastflow-config.json)
source "$SCRIPT_DIR/load-config.sh" 2>/dev/null || true

REGISTRY="${REGISTRY:-}"
IMAGE_NAME="${IMAGE_NAME:-pos-online}"
IMAGE_TAG="${IMAGE_TAG:-latest}"

if [ -z "$REGISTRY" ]; then
  echo "REGISTRY no definido; omitiendo test de pull. Para probar: REGISTRY=localhost:5000 bash scripts/test-registry.sh"
  exit 0
fi

echo "=== Test registry: pull ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} ==="
docker pull "${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
echo "OK: pull ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
echo "=== Test registry OK ==="

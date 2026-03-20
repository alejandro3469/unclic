#!/usr/bin/env bash
# =============================================================================
# Template pipeline local para pos-online: test → (opcional) build imagen → push a registry.
# En pos-online el build real es Maven; este script es referencia. Ajustar a la estructura
# de pos-online (pom.xml, Dockerfile si existe) y a la ruta de generic model si aplica.
# Uso (desde la raíz de pos-online, tras copiar este script):
#   bash scripts/build-and-push.sh
# Con push: REGISTRY=registry.gitlab.com/grupo/proyecto bash scripts/build-and-push.sh
# Variables: IMAGE_NAME (default pos-online), IMAGE_TAG (default latest), REGISTRY.
# =============================================================================
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$ROOT_DIR"

# Cargar config central (config/fastflow-config.json) si existe y jq está instalado; env tiene prioridad
if [ -f "$ROOT_DIR/config/fastflow-config.json" ] && command -v jq &>/dev/null; then
  eval "$("$SCRIPT_DIR/load-config.sh")" 2>/dev/null || true
fi
IMAGE_NAME="${IMAGE_NAME:-pos-online}"
IMAGE_TAG="${IMAGE_TAG:-latest}"
REGISTRY="${REGISTRY:-}"

echo "=== pos-online: test (Maven) ==="
mvn test -q

if [ -f "Dockerfile" ]; then
  echo "=== pos-online: build image ==="
  docker build -t "${IMAGE_NAME}:${IMAGE_TAG}" .
  if [ -n "$REGISTRY" ]; then
    echo "=== pos-online: push to registry ==="
    docker tag "${IMAGE_NAME}:${IMAGE_TAG}" "${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
    docker push "${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
    docker tag "${IMAGE_NAME}:${IMAGE_TAG}" "${REGISTRY}/${IMAGE_NAME}:latest"
    docker push "${REGISTRY}/${IMAGE_NAME}:latest"
    echo "Pushed ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} and :latest"
  fi
else
  echo "No Dockerfile en raíz; omitiendo build/push de imagen. Para publicar imagen, añade Dockerfile y define REGISTRY."
fi

echo "=== pos-online build OK ==="

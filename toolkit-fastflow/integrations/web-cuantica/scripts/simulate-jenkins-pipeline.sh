#!/usr/bin/env bash
# Simulación local del pipeline Jenkins (pos-online + FastFlow).
# Ejecuta: mvn test → package → docker build → (opcional) push.
# Uso: bash scripts/simulate-jenkins-pipeline.sh
#      REGISTRY=localhost:5000 bash scripts/simulate-jenkins-pipeline.sh
#      SKIP_DOCKER=1 para no construir imagen.
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$ROOT_DIR"

# Cargar config central (config/fastflow-config.json) si existe
if [ -f "$ROOT_DIR/config/fastflow-config.json" ] && command -v jq &>/dev/null; then
  eval "$("$SCRIPT_DIR/load-config.sh")" 2>/dev/null || true
fi
IMAGE_NAME="${IMAGE_NAME:-pos-online}"
IMAGE_TAG="${IMAGE_TAG:-latest}"
REGISTRY="${REGISTRY:-}"
SKIP_DOCKER="${SKIP_DOCKER:-0}"

echo "[simulate] Workspace: $ROOT_DIR"

if [ ! -f pom.xml ]; then
  echo "No hay pom.xml; ejecutar en repo pos-online."
  exit 0
fi

echo "=== [Stage Test] mvn test ==="
mvn clean test -q

echo "=== [Stage Package] mvn package ==="
mvn package -DskipTests -q

if [ "$SKIP_DOCKER" = "1" ]; then
  echo "=== [Stage Build image] omitido (SKIP_DOCKER=1) ==="
else
  echo "=== [Stage Build image] docker build ==="
  if [ -f Dockerfile ]; then
    docker build -f Dockerfile -t "${IMAGE_NAME}:${IMAGE_TAG}" .
  else
    echo "No hay Dockerfile en la raíz; omitiendo build."
  fi
fi

if [ -n "$REGISTRY" ] && [ "$SKIP_DOCKER" != "1" ] && [ -f Dockerfile ]; then
  echo "=== [Stage Push] docker push ==="
  docker tag "${IMAGE_NAME}:${IMAGE_TAG}" "${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
  docker push "${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
  docker tag "${IMAGE_NAME}:${IMAGE_TAG}" "${REGISTRY}/${IMAGE_NAME}:latest"
  docker push "${REGISTRY}/${IMAGE_NAME}:latest"
fi

echo "=== Simulación pipeline OK ==="

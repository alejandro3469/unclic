#!/usr/bin/env bash
# Exporta variables de entorno desde config/fastflow-config.json para usar en scripts.
# Uso: source scripts/load-config.sh   o   eval "$(bash scripts/load-config.sh)"
# Requiere: jq (opcional; si no está, no se exporta nada y se muestra la ruta del config).
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
CONFIG_FILE="${CONFIG_FILE:-$ROOT_DIR/config/fastflow-config.json}"

if [ ! -f "$CONFIG_FILE" ]; then
  echo "# Config no encontrado: $CONFIG_FILE" >&2
  return 0 2>/dev/null || true
  exit 0
fi

if ! command -v jq &>/dev/null; then
  echo "# jq no instalado; config en $CONFIG_FILE (editar manualmente o usar deploy/config-ui.html)" >&2
  return 0 2>/dev/null || true
  exit 0
fi

# Exportar pipeline (para build-and-push, Jenkins)
IMAGE_NAME="${IMAGE_NAME:-$(jq -r '.pipeline.imageName // .registry.imageName // "pos-online"' "$CONFIG_FILE")}"
IMAGE_TAG="${IMAGE_TAG:-$(jq -r '.pipeline.imageTag // "latest"' "$CONFIG_FILE")}"
REGISTRY="${REGISTRY:-$(jq -r '.pipeline.registry // .registry.url // ""' "$CONFIG_FILE")}"
CONFIG_NAMESPACE="${CONFIG_NAMESPACE:-$(jq -r '.kubernetes.namespace // "fastflow"' "$CONFIG_FILE")}"
CONFIG_APP_BASE_URL="${CONFIG_APP_BASE_URL:-$(jq -r '.app.baseUrl // .terraform.baseUrl // ""' "$CONFIG_FILE")}"

# Si se hace source de este script, exportar en el shell actual; si no, imprimir para eval
if [[ "${BASH_SOURCE[0]}" != "${0}" ]]; then
  export IMAGE_NAME IMAGE_TAG REGISTRY CONFIG_NAMESPACE CONFIG_APP_BASE_URL
else
  echo "export IMAGE_NAME=\"$IMAGE_NAME\""
  echo "export IMAGE_TAG=\"$IMAGE_TAG\""
  echo "export REGISTRY=\"$REGISTRY\""
  echo "export CONFIG_NAMESPACE=\"$CONFIG_NAMESPACE\""
  echo "export CONFIG_APP_BASE_URL=\"$CONFIG_APP_BASE_URL\""
fi

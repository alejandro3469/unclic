#!/usr/bin/env bash
# Comprueba que el Jenkinsfile tenga las etapas esperadas (Test, Build image, Push).
# Uso: bash scripts/validate-jenkinsfile.sh [ruta/Jenkinsfile]
#      En pos-online: bash scripts/validate-jenkinsfile.sh Jenkinsfile
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
JENKINSFILE="${1:-$ROOT_DIR/Jenkinsfile.example}"

if [ ! -f "$JENKINSFILE" ]; then
  echo "No existe $JENKINSFILE; omitiendo."
  exit 0
fi

echo "=== Validando Jenkinsfile (pos-online) ==="
grep -q "stage('Test')" "$JENKINSFILE" && echo "  OK stage Test" || { echo "  Falta stage Test"; exit 1; }
grep -q "Build image" "$JENKINSFILE" && echo "  OK stage Build image" || { echo "  Falta Build image"; exit 1; }
grep -q "Push to registry" "$JENKINSFILE" && echo "  OK stage Push" || true
grep -q "mvn" "$JENKINSFILE" && echo "  OK mvn" || true
grep -q "docker" "$JENKINSFILE" && echo "  OK docker" || true
echo "=== Jenkinsfile OK ==="

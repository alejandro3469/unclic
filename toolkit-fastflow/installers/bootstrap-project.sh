#!/usr/bin/env bash
set -euo pipefail

# Copia la base del toolkit al repo actual

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
TARGET_DIR="${1:-$(pwd)}"

mkdir -p "$TARGET_DIR"
cp -R "$ROOT_DIR/templates/common/." "$TARGET_DIR/"

echo "[OK] Plantilla common copiada en: $TARGET_DIR"
echo "[NEXT] Ajusta Jenkinsfile, variables y credenciales según tu entorno."

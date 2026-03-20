#!/usr/bin/env bash
# =============================================================================
# Simula localmente el pipeline Jenkins de FastFlow (sin Jenkins).
# Pasos: test → build imagen Docker. Opcional: push a registry (REGISTRY=...).
# Equivalente a ejecutar scripts/build-and-push.sh (que ya hace esto).
# Este script existe como documentación y alias; la lógica real está en build-and-push.sh.
# Uso: bash scripts/simulate-jenkins-local.sh
#      REGISTRY=localhost:5000 bash scripts/simulate-jenkins-local.sh
# =============================================================================
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
exec bash "$SCRIPT_DIR/build-and-push.sh" "$@"

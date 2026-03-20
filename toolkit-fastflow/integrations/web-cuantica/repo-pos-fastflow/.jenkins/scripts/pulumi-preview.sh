#!/usr/bin/env bash
# =============================================================================
# pulumi-preview.sh — FastFlow POS (Pulumi opcional)
# -----------------------------------------------------------------------------
# Propósito:
#   Ejecutar `pulumi preview` en deploy/pulumi/ para validar el stack TypeScript
#   (recurso AWS de ejemplo: bucket S3) antes de un deploy real.
#
# Quién llama:
#   Jenkinsfile si FASTFLOW_PULUMI_PREVIEW=true.
#
# Variables:
#   PULUMI_STACK — nombre del stack (ej. dev); default dev
#   PULUMI_ACCESS_TOKEN — si usas Pulumi Cloud (secret en Jenkins credentials, exportar en job)
#
# Antes del primer run en el agente:
#   cd deploy/pulumi && npm ci
#
# LocalStack:
#   Configurar en el stack (ver deploy/pulumi/README.md) endpoints AWS o usar
#   cuenta AWS real de desarrollo (recomendado para Pulumi hasta validar emulación).
# =============================================================================
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
PULUMI_DIR="$REPO_ROOT/deploy/pulumi"
STACK="${PULUMI_STACK:-dev}"

echo "[FastFlow/IaC] pulumi-preview: PULUMI_DIR=$PULUMI_DIR stack=$STACK"

if [[ ! -d "$PULUMI_DIR" ]]; then
  echo "[FastFlow/IaC] ERROR: no existe $PULUMI_DIR"
  exit 1
fi

cd "$PULUMI_DIR"

# Dependencias Node para @pulumi/aws
if [[ -f package-lock.json ]]; then
  npm ci
else
  npm install
fi

# Selecciona o crea stack sin prompt (CI)
if ! pulumi stack select "$STACK" 2>/dev/null; then
  pulumi stack init "$STACK"
fi

echo "[FastFlow/IaC] pulumi preview --non-interactive (stack from env PULUMI_STACK=${STACK}) ..."
pulumi preview --non-interactive --stack "$STACK"

echo "[FastFlow/IaC] pulumi-preview: OK"

#!/usr/bin/env bash
# Limpia el workspace de Jenkins antes del build (node_modules, .next, cachés).
# Ejecutar en el stage Cleanup del pipeline o manualmente en la EC2 de Jenkins.
set -euo pipefail

WORKSPACE="${WORKSPACE:-${1:-.}}"
echo "Limpiando workspace: ${WORKSPACE}"

cd "${WORKSPACE}"

# Limpiar artefactos de builds anteriores
rm -rf node_modules .next out .npm-cache .jenkins-container-home

# Limpiar caché de Next.js si existe
find . -type d -name ".next" -prune -exec rm -rf {} + 2>/dev/null || true

# Limpiar logs temporales
find . -name "*.log" -type f -mtime +7 -delete 2>/dev/null || true

echo "Workspace limpiado. Espacio libre:"
df -h "${WORKSPACE}" | tail -1

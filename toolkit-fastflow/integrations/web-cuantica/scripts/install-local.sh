#!/usr/bin/env bash
# =============================================================================
# Instalación rápida de la implementación FastFlow para pos-online (este repo).
# Este repo no contiene la aplicación pos-online; contiene pipeline, scripts,
# Terraform, K8s y documentación. Para probar el pipeline completo, clonar
# pos-online (y generic model en la carpeta padre) y copiar aquí los scripts
# y el Jenkinsfile según docs/pos-online/.
# Uso: bash scripts/install-local.sh
# =============================================================================
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$ROOT_DIR"

echo "=== FastFlow implementación pos-online ==="
echo "Directorio: $ROOT_DIR"
echo ""
echo "Este repo es la implementación (pipeline, registry, Terraform, K8s, docs)."
echo "No incluye la aplicación pos-online."
echo ""
echo "Siguiente:"
echo "  1. Clonar pos-online y generic model (generic model en la misma carpeta padre que pos-online)."
echo "  2. Copiar a pos-online: Jenkinsfile.example, scripts/, deploy/, docs/ según docs/pos-online/CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md"
echo "  3. En pos-online: instalar Jenkins según docs/instalacion/INSTALAR-JENKINS.md"
echo "  4. Validaciones en este repo: bash scripts/validate-jenkinsfile.sh ; bash scripts/run-all-validations.sh (requiere pom.xml en pos-online para Maven)"
echo ""
echo "Documentación: docs/README.md — Generic model y contexto: docs/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md"

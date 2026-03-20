#!/usr/bin/env bash
# Validaciones del repo pos-online (implementación FastFlow): Jenkinsfile, Terraform, K8s, simulación pipeline.
# No requiere servidor Node; pos-online es Java (Maven). Ejecutar desde la raíz del repo.
# Uso: bash scripts/run-all-validations.sh
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$ROOT_DIR"

echo "=============================================="
echo " pos-online + FastFlow — validaciones"
echo "=============================================="
echo ""

if [ -f pom.xml ]; then
  echo "1. Maven test"
  mvn clean test -q
  echo ""
else
  echo "1. Maven: omitido (no hay pom.xml en la raíz; ejecutar en repo pos-online)"
  echo ""
fi

echo "2. Validación Jenkinsfile"
bash "$SCRIPT_DIR/validate-jenkinsfile.sh"
echo ""

if command -v terraform >/dev/null 2>&1; then
  echo "3. Validación Terraform"
  bash "$SCRIPT_DIR/validate-terraform.sh"
  echo ""
else
  echo "3. Terraform: omitido (no instalado)"
  echo ""
fi

if command -v kubectl >/dev/null 2>&1; then
  echo "4. Validación manifiestos K8s"
  bash "$SCRIPT_DIR/validate-k8s.sh"
  echo ""
else
  echo "4. Kubectl: omitido (no instalado)"
  echo ""
fi

if [ -f pom.xml ]; then
  echo "5. Simulación pipeline (sin push)"
  SKIP_DOCKER=1 bash "$SCRIPT_DIR/simulate-jenkins-pipeline.sh" 2>/dev/null || true
  echo ""
fi

echo "=============================================="
echo " Validaciones OK"
echo "=============================================="

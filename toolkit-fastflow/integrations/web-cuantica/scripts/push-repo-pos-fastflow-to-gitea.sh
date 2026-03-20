#!/usr/bin/env bash
# Sube repo-pos-fastflow a Gitea como repo nuevo.
# Uso (desde toolkit-fastflow/integrations/web-cuantica o desde repo-pos-fastflow):
#
#   export GITEA_USER=alejandro-perez
#   export GITEA_REPO=pos-online-fastflow
#   export GITEA_URL=http://gitea.unclic.consulting:3000
#   bash scripts/push-repo-pos-fastflow-to-gitea.sh
#
# O desde repo-pos-fastflow:
#   export GITEA_USER=alejandro-perez
#   export GITEA_REPO=pos-online-fastflow
#   bash push-repo-pos-fastflow-to-gitea.sh
#
# Antes: crear el repo vacío en Gitea (+ → New Repository, nombre = GITEA_REPO, sin Initialize).

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_DIR="$(cd "$SCRIPT_DIR/../repo-pos-fastflow" 2>/dev/null && pwd || echo "$SCRIPT_DIR")"

if [ -n "$REPO_DIR" ] && [ -f "$REPO_DIR/pom.xml" ] && [ -f "$REPO_DIR/Jenkinsfile" ]; then
  cd "$REPO_DIR"
else
  echo "No se encontró repo-pos-fastflow (pom.xml + Jenkinsfile). Ejecuta desde toolkit-fastflow/integrations/web-cuantica."
  exit 1
fi

GITEA_USER="${GITEA_USER:-}"
GITEA_REPO="${GITEA_REPO:-pos-online-fastflow}"
GITEA_URL="${GITEA_URL:-http://gitea.unclic.consulting:3000}"

if [ -z "$GITEA_USER" ]; then
  echo "Falta GITEA_USER. Ejemplo: export GITEA_USER=alejandro-perez"
  exit 1
fi

REMOTE_URL="${GITEA_URL}/${GITEA_USER}/${GITEA_REPO}.git"
echo "Destino: $REMOTE_URL"

if [ ! -d .git ]; then
  git init
  git add .
  git commit -m "POS completo (mx.com.endtoend) + Jenkinsfile FastFlow"
  git branch -M main
fi

if ! git remote get-url gitea &>/dev/null; then
  git remote add gitea "$REMOTE_URL"
fi
git remote set-url gitea "$REMOTE_URL"

echo "Push a gitea (main)..."
git push -u gitea main

echo "Listo. Repo: $GITEA_URL/$GITEA_USER/$GITEA_REPO"
echo "URL para Jenkins: $REMOTE_URL"

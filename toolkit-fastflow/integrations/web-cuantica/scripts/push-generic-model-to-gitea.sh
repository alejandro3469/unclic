#!/usr/bin/env bash
# Sube smartbussiness-generic-model a Gitea (repo nuevo).
# Uso (desde toolkit-fastflow/integrations/web-cuantica):
#
#   export GITEA_USER=alejandro-perez
#   export GITEA_REPO=smartbussiness-generic-model
#   export GITEA_URL=http://gitea.unclic.consulting:3000
#   bash scripts/push-generic-model-to-gitea.sh
#
# Opcional: crear el repo en Gitea por API (evita crearlo a mano):
#   export GITEA_TOKEN=tu_token
#   bash scripts/push-generic-model-to-gitea.sh
#
# Antes (sin token): en Gitea crear repo vacío (+ → New Repository, nombre = smartbussiness-generic-model, sin Initialize).

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_DIR="$(cd "$SCRIPT_DIR/../smartbussiness-generic-model" 2>/dev/null && pwd)"

if [ -z "$REPO_DIR" ] || [ ! -f "$REPO_DIR/pom.xml" ]; then
  echo "No se encontró smartbussiness-generic-model con pom.xml. Ejecuta desde toolkit-fastflow/integrations/web-cuantica."
  exit 1
fi

GITEA_USER="${GITEA_USER:-alejandro-perez}"
GITEA_REPO="${GITEA_REPO:-smartbussiness-generic-model}"
GITEA_URL="${GITEA_URL:-http://gitea.unclic.consulting:3000}"
GITEA_TOKEN="${GITEA_TOKEN:-}"

REMOTE_URL="${GITEA_URL}/${GITEA_USER}/${GITEA_REPO}.git"
echo "Destino: $REMOTE_URL"

# Crear repo en Gitea por API si hay token
if [ -n "$GITEA_TOKEN" ]; then
  echo "Creando repo en Gitea..."
  HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" -X POST \
    -H "Authorization: token $GITEA_TOKEN" \
    -H "Content-Type: application/json" \
    -d "{\"name\":\"$GITEA_REPO\",\"private\":false}" \
    "$GITEA_URL/api/v1/user/repos" 2>/dev/null || echo "000")
  if [ "$HTTP_CODE" = "201" ] || [ "$HTTP_CODE" = "409" ]; then
    echo "Repo listo (HTTP $HTTP_CODE)."
  else
    echo "AVISO: crear repo devolvió HTTP $HTTP_CODE. Crea el repo a mano en Gitea si hace falta."
  fi
fi

cd "$REPO_DIR"

if [ ! -d .git ]; then
  git init
  git add .
  git commit -m "generic-model 1.0.1-SNAPSHOT + Jenkinsfile FastFlow"
  git branch -M main
else
  git add -A
  git diff --cached --quiet || git commit -m "FastFlow: Jenkinsfile y README para Gitea/Jenkins" || true
  git branch -M main 2>/dev/null || true
fi

if ! git remote get-url gitea &>/dev/null; then
  git remote add gitea "$REMOTE_URL"
fi
git remote set-url gitea "$REMOTE_URL"

# Si había origin de otro sitio (GitLab), no lo borramos; solo push a gitea
echo "Push a gitea (main)..."
git push -u gitea main

echo "Listo. Repo: $GITEA_URL/$GITEA_USER/$GITEA_REPO"
echo "URL para Jenkins: $REMOTE_URL"

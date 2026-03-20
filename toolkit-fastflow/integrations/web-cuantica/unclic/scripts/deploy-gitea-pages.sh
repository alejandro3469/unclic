#!/usr/bin/env bash
# Deploy UnClic sitio estático a la rama gitea-pages en el remoto Gitea.
# Uso: desde toolkit-fastflow/integrations/web-cuantica/unclic ejecutar:
#   bash scripts/deploy-gitea-pages.sh
# O desde la raíz del monorepo:
#   bash toolkit-fastflow/integrations/web-cuantica/unclic/scripts/deploy-gitea-pages.sh

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
UNCLIC="$(cd "$SCRIPT_DIR/.." && pwd)"
# Raíz del repo git (pipeline-as-code-with-jenkins-master), no solo toolkit-fastflow
REPO_ROOT="$(cd "$UNCLIC" && git rev-parse --show-toplevel)"
BRANCH_PAGES="${GITEA_PAGES_BRANCH:-gitea-pages}"
REMOTE="${GITEA_REMOTE:-gitea}"

echo "UnClic: $UNCLIC"
echo "Repo raíz: $REPO_ROOT"
echo "Remoto: $REMOTE, rama: $BRANCH_PAGES"

cd "$UNCLIC"
npm run build

TMPDIR_PAGES=$(mktemp -d)
trap 'rm -rf "$TMPDIR_PAGES"' EXIT

cd "$REPO_ROOT"
GITEA_URL=$(git remote get-url "$REMOTE" 2>/dev/null || true)
if [ -z "$GITEA_URL" ]; then
  echo "Remoto '$REMOTE' no encontrado. Configura: git remote add gitea <url>"
  exit 1
fi

if git ls-remote --exit-code --heads "$GITEA_URL" "$BRANCH_PAGES" 2>/dev/null; then
  git clone --branch "$BRANCH_PAGES" --single-branch "$REPO_ROOT" "$TMPDIR_PAGES"
  cd "$TMPDIR_PAGES"
  find . -mindepth 1 -maxdepth 1 ! -name .git -exec rm -rf {} +
else
  git clone --no-checkout "$REPO_ROOT" "$TMPDIR_PAGES"
  cd "$TMPDIR_PAGES"
  git checkout --orphan "$BRANCH_PAGES"
  git rm -rf . 2>/dev/null || true
fi

git remote add gitea "$GITEA_URL" 2>/dev/null || git remote set-url gitea "$GITEA_URL"

cp -a "$UNCLIC/out/"* .
git add -A
if [ -z "$(git status -s)" ]; then
  echo "Sin cambios; no se hace push."
  exit 0
fi
git commit -m "deploy unclic: sitio estático $(date +%Y-%m-%d)"
git push gitea "$BRANCH_PAGES"
echo "Listo. Sitio subido a $REMOTE $BRANCH_PAGES"

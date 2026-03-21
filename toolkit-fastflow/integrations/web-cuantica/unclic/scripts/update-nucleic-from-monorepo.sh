#!/usr/bin/env bash
# Sincroniza la carpeta unclic del monorepo al repo Gitea nucleic (clone + rsync + commit + push).
# Uso: desde cualquier sitio, bash scripts/update-nucleic-from-monorepo.sh
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SITIO="${SITIO:-"$SCRIPT_DIR/.."}"
NUCLEIC_WORK="${NUCLEIC_WORK:-$HOME/Downloads/nucleic-landing}"
REPO_URL="${NUCLEIC_REPO_URL:-https://gitea.unclic.consulting/alejandro-perez/nucleic.git}"

echo "Origen (unclic): $SITIO"
echo "Clone / trabajo: $NUCLEIC_WORK"
echo "Remote: $REPO_URL"

if [[ ! -f "$SITIO/package.json" ]] || [[ ! -f "$SITIO/Jenkinsfile" ]]; then
  echo "Error: no parece la raíz del proyecto unclic (falta package.json o Jenkinsfile)." >&2
  exit 1
fi

mkdir -p "$(dirname "$NUCLEIC_WORK")"
if [[ -d "$NUCLEIC_WORK/.git" ]]; then
  git -C "$NUCLEIC_WORK" fetch origin
  git -C "$NUCLEIC_WORK" checkout main
  git -C "$NUCLEIC_WORK" pull --ff-only origin main || {
    echo "Advertencia: pull --ff-only falló. Revisa conflictos o haz pull manual en $NUCLEIC_WORK" >&2
    exit 1
  }
else
  rm -rf "$NUCLEIC_WORK"
  git clone "$REPO_URL" "$NUCLEIC_WORK"
  git -C "$NUCLEIC_WORK" checkout main 2>/dev/null || true
fi

rsync -av \
  --delete \
  --exclude node_modules \
  --exclude .next \
  --exclude .idea \
  --exclude .git \
  --exclude tsconfig.tsbuildinfo \
  "$SITIO/" "$NUCLEIC_WORK/"

cd "$NUCLEIC_WORK"
git add -A
if git diff --cached --quiet; then
  echo "Nada que commitear (ya está al día con $SITIO)."
  exit 0
fi

MSG="${NUCLEIC_COMMIT_MSG:-chore(ci): sync landing UnClic desde monorepo}"
git commit -m "$MSG"
git push origin main
echo "Listo: nucleic actualizado y push a origin main."

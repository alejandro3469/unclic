#!/usr/bin/env bash
# Sube el proyecto unclic a Gitea (nucleic) por commits y pushes pequeños (evita HTTP 413).
# Ejecutar desde tu Mac. Te pedirá usuario/contraseña de Gitea en cada push.

set -e
SITIO="${SITIO:-/Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/unclic}"
DEST="${DEST:-$HOME/Downloads/nucleic-landing}"
REPO_URL="https://gitea.unclic.consulting/alejandro-perez/nucleic.git"

echo "Origen: $SITIO"
echo "Destino: $DEST"
read -p "¿Continuar? (s/n) " -n 1 -r; echo
[[ ! $REPLY =~ ^[SsYy]$ ]] && exit 0

rm -rf "$DEST"
mkdir -p "$DEST"
rsync -av --exclude=node_modules --exclude=.next --exclude=.idea --exclude=.git "$SITIO/" "$DEST/"
cd "$DEST"

git init
git checkout -b main
git remote add origin "$REPO_URL"

echo "--- Bloque 1: config y app ---"
git add .gitignore .env.example components.json package.json package-lock.json next.config.js tsconfig.json tailwind.config.ts next-env.d.ts 2>/dev/null || true
git add app/
git commit -m "chore: config y app" || true
git push -u origin main

echo "--- Bloque 2: componentes ui ---"
git add components/ui/
git commit -m "feat: componentes ui" || true
git push origin main

echo "--- Bloque 3: layout y secciones ---"
git add components/layout/ components/sections/
git commit -m "feat: layout y secciones" || true
git push origin main

echo "--- Bloque 4: lib, Jenkinsfile, README ---"
git add lib/ Jenkinsfile README.md
git commit -m "feat: lib, Jenkinsfile, README" || true
git push origin main

echo "--- Bloque 5: docs ---"
git add docs/
git commit -m "docs: documentación" || true
git push origin main

echo "--- Bloque 6: resto ---"
git add public/ 2>/dev/null || true
git add .
git status
if ! git diff --cached --quiet 2>/dev/null; then
  git commit -m "chore: resto de archivos" || true
  git push origin main
fi

echo "Listo. Repo nucleic en Gitea actualizado desde $DEST"

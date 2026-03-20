#!/usr/bin/env bash
# =============================================================================
# Clona el repo pos-online localmente para probar con Jenkins, Terraform, K8s y FastFlow.
# Después, cuando tengas el repo creado en Gitea, usa los comandos de CONECTAR A GITEA más abajo.
#
# Uso:
#   export POS_ONLINE_REPO_URL="https://gitlab.com/tu-org/pos-online.git"   # o tu URL (GitLab, GitHub, etc.)
#   bash scripts/clone-pos-online.sh
#
# O en una sola línea:
#   POS_ONLINE_REPO_URL="https://gitlab.com/tu-org/pos-online.git" bash scripts/clone-pos-online.sh
#
# Opcional: clonar en otra carpeta
#   CLONE_DIR="$HOME/proyectos/pos-online" POS_ONLINE_REPO_URL="https://..." bash scripts/clone-pos-online.sh
# =============================================================================
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
# Por defecto: pos-online como hermano de integrations (p. ej. toolkit-fastflow/pos-online)
CLONE_DIR="${CLONE_DIR:-$REPO_ROOT/pos-online}"

if [ -z "${POS_ONLINE_REPO_URL:-}" ]; then
  echo "Error: indica la URL del repo pos-online en POS_ONLINE_REPO_URL."
  echo ""
  echo "Ejemplo (GitLab):"
  echo "  export POS_ONLINE_REPO_URL=\"https://gitlab.com/tu-org/pos-online.git\""
  echo "  bash scripts/clone-pos-online.sh"
  echo ""
  echo "Ejemplo (Gitea Cloud, cuando ya lo tengas):"
  echo "  export POS_ONLINE_REPO_URL=\"https://cloud.gitea.com/tu-usuario/pos-online.git\""
  echo "  bash scripts/clone-pos-online.sh"
  exit 1
fi

if [ -d "$CLONE_DIR" ]; then
  echo "Ya existe la carpeta $CLONE_DIR."
  echo "Para actualizar: cd $CLONE_DIR && git pull"
  exit 0
fi

echo "Clonando pos-online en: $CLONE_DIR"
git clone "$POS_ONLINE_REPO_URL" "$CLONE_DIR"
echo ""
echo "Listo. Directorio: $CLONE_DIR"
echo ""
echo "--- CONECTAR A GITEA (cuando tengas el repo creado en Gitea) ---"
echo "  cd $CLONE_DIR"
echo "  git remote add gitea https://cloud.gitea.com/TU_USUARIO/pos-online.git"
echo "  git push -u gitea main"
echo "(Si tu rama es master: git push -u gitea master)"
echo ""
echo "Luego en Jenkins → Configure → Repository URL: la URL de Gitea que pusiste en 'git remote add gitea'."

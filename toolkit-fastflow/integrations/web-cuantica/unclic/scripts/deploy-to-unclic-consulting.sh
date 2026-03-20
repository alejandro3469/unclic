#!/usr/bin/env bash
# Deploy UnClic a https://unclic.consulting — build + rsync a EC2 fastflow-vantive.
# Uso: ./scripts/deploy-to-unclic-consulting.sh [ruta-a-clave.pem]
#
# Requisitos:
# - DNS: unclic.consulting y www → IP de la EC2
# - En la EC2: Nginx con server block para unclic.consulting (ver docs/SUBIR-SITIO-A-UNCLIC-CONSULTING.md)

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
OUT_DIR="$PROJECT_DIR/out"
DEPLOY_HOST="${DEPLOY_HOST:-3.22.236.150}"
DEPLOY_USER="${DEPLOY_USER:-ec2-user}"
REMOTE_DIR="$DEPLOY_USER@$DEPLOY_HOST:~/unclic-deploy/"

KEY="${1:-${PEM_KEY:-$HOME/Downloads/gitea-key.pem}}"
if [[ ! -f "$KEY" ]]; then
  echo "Clave SSH no encontrada: $KEY"
  echo "Uso: $0 [ruta-a-clave.pem]"
  echo "O exporta PEM_KEY=... con la ruta a tu .pem"
  exit 1
fi

cd "$PROJECT_DIR"
echo "Build del sitio (npm run build)..."
npm run build

if [[ ! -d "$OUT_DIR" ]]; then
  echo "Error: tras el build no existe $OUT_DIR"
  exit 1
fi

echo "Subiendo out/ a $REMOTE_DIR (host $DEPLOY_HOST)..."
rsync -avz --delete -e "ssh -i $KEY -o StrictHostKeyChecking=accept-new" "$OUT_DIR/" "$REMOTE_DIR"

echo ""
echo "Listo. Ahora en la EC2 ejecuta:"
echo "  ssh -i $KEY $DEPLOY_USER@$DEPLOY_HOST"
echo "  sudo rsync -av --delete /home/$DEPLOY_USER/unclic-deploy/ /usr/share/nginx/unclic/"
echo "  sudo chown -R nginx:nginx /usr/share/nginx/unclic"
echo ""
echo "Luego abre https://unclic.consulting"

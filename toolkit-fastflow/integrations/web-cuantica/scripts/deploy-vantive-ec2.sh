#!/usr/bin/env bash
# Despliega Vantive (Next.js static export) a la EC2 fastflow-vantive.
# Uso: VANTIVE_IP=3.18.111.60 VANTIVE_KEY=/ruta/a/key.pem ./deploy-vantive-ec2.sh
# Desde: toolkit-fastflow/integrations/web-cuantica o desde toolkit-fastflow (ajustar VANTIVE_DIR).

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Desde web-cuantica/scripts -> integrations/kings-joers/vantive = ../../kings-joers/vantive
VANTIVE_DIR="${VANTIVE_DIR:-$(cd "$SCRIPT_DIR/../../kings-joers/vantive" 2>/dev/null && pwd)}"
if [ -z "$VANTIVE_DIR" ] || [ ! -f "$VANTIVE_DIR/package.json" ]; then
  echo "No se encuentra kings-joers/vantive. Exporta VANTIVE_DIR con la ruta al proyecto Vantive."
  exit 1
fi

VANTIVE_IP="${VANTIVE_IP:-3.22.236.150}"
VANTIVE_KEY="${VANTIVE_KEY:-}"

if [ -z "$VANTIVE_KEY" ] || [ ! -f "$VANTIVE_KEY" ]; then
  echo "Exporta VANTIVE_KEY con la ruta a la clave .pem de la EC2 (ej. VANTIVE_KEY=/ruta/a/key.pem)."
  exit 1
fi

echo "=== Build Vantive en $VANTIVE_DIR ==="
cd "$VANTIVE_DIR"
npm ci --no-audit --prefer-offline
npm run build

if [ ! -d "out" ]; then
  echo "Error: no se generó la carpeta out/."
  exit 1
fi

echo "=== Deploy a EC2 $VANTIVE_IP ==="
ssh -i "$VANTIVE_KEY" -o StrictHostKeyChecking=no ec2-user@$VANTIVE_IP "rm -rf /tmp/vantive-deploy && mkdir -p /tmp/vantive-deploy"
rsync -avz --delete -e "ssh -i $VANTIVE_KEY -o StrictHostKeyChecking=no" out/ ec2-user@$VANTIVE_IP:/tmp/vantive-deploy/
ssh -i "$VANTIVE_KEY" -o StrictHostKeyChecking=no ec2-user@$VANTIVE_IP "sudo rsync -av --delete /tmp/vantive-deploy/ /usr/share/nginx/html/ && sudo chown -R nginx:nginx /usr/share/nginx/html/"

echo "=== Listo. Abre http://$VANTIVE_IP o http://vantive.unclic.consulting ==="

#!/usr/bin/env bash
# Arranca POS Online (opcional) y abre la interfaz central del flujo (POS → Jenkins → AWS).
# Uso: desde la raíz del repo (toolkit-fastflow/integrations/web-cuantica) o con POS_ONLINE_DIR apuntando al repo pos-online.
# La única parte manual del flujo es hacer el commit; el resto se observa desde la interfaz.

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
DEPLOY_DIR="$REPO_ROOT/deploy"
# Ruta al repo pos-online (hermano por defecto: ../pos-online)
POS_ONLINE_DIR="${POS_ONLINE_DIR:-$(cd "$REPO_ROOT/../pos-online" 2>/dev/null && pwd)}"

echo "=== Flujo POS → Jenkins (deploy gratuito AWS) ==="
echo "  Interfaz: $DEPLOY_DIR/interfaz-central-flujo-aws.html"
echo ""

# 1. Arrancar POS Online si existe el directorio y hay pom.xml
if [ -n "$POS_ONLINE_DIR" ] && [ -f "$POS_ONLINE_DIR/pom.xml" ]; then
  echo "[1/2] Arrancando POS Online en segundo plano (perfil local)..."
  (cd "$POS_ONLINE_DIR" && mvn spring-boot:run -Dspring-boot.run.profiles=local > /tmp/pos-online.log 2>&1) &
  POS_PID=$!
  echo "      PID: $POS_PID. Log: /tmp/pos-online.log"
  echo "      Esperando 15 s a que Spring Boot arranque..."
  sleep 15
  echo ""
else
  echo "[1/2] POS Online: no se encontró directorio con pom.xml (POS_ONLINE_DIR=$POS_ONLINE_DIR)."
  echo "      Para arrancar POS manualmente: cd <pos-online> && mvn spring-boot:run -Dspring-boot.run.profiles=local"
  echo ""
fi

# 2. Jenkins: en AWS ya corre en la EC2; en local el usuario lo levanta (java -jar jenkins.war o Docker)
echo "[2/2] Jenkins: en deploy gratuito AWS ya corre en la EC2 (http://<IP>:8080)."
echo "      En local: ejecuta Jenkins por tu cuenta y abre la interfaz."
echo ""

# 3. Abrir la interfaz central en el navegador
INTERFAZ="$DEPLOY_DIR/interfaz-central-flujo-aws.html"
if [ -f "$INTERFAZ" ]; then
  echo "Abriendo interfaz central..."
  if command -v open >/dev/null 2>&1; then
    open "$INTERFAZ"
  elif command -v xdg-open >/dev/null 2>&1; then
    xdg-open "$INTERFAZ"
  else
    echo "Abre en el navegador: file://$INTERFAZ"
    echo "O sirve el repo: cd $REPO_ROOT && python3 -m http.server 9000 → http://localhost:9000/deploy/interfaz-central-flujo-aws.html"
  fi
else
  echo "No se encontró $INTERFAZ. Abre manualmente: deploy/control-center-flujo-aws.html"
  [ -f "$DEPLOY_DIR/control-center-flujo-aws.html" ] && open "$DEPLOY_DIR/control-center-flujo-aws.html" 2>/dev/null || true
fi

echo ""
echo "Listo. La única parte manual es hacer commit y push; el resto se observa desde la interfaz (Jenkins, build, registry, app)."

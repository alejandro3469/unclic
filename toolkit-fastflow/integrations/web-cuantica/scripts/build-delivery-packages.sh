#!/usr/bin/env bash
# Genera 3 paquetes limpios (sin .git, sin target/.idea) para entregar al usuario final.
# Uso: desde toolkit-fastflow/integrations/web-cuantica:
#   bash scripts/build-delivery-packages.sh
#
# Crea: delivery/smartbussiness-generic-model, delivery/pos-online, delivery/fastflow-integration

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE="$(cd "$SCRIPT_DIR/.." && pwd)"
DELIVERY="$BASE/delivery"

echo "=== Build delivery packages (sin historial Git) ==="
echo "Base: $BASE"
echo "Destino: $DELIVERY"
echo ""

mkdir -p "$DELIVERY"
# No borrar delivery/ entero para no perder fastflow-integration (docs y scripts)
rm -rf "$DELIVERY/smartbussiness-generic-model" "$DELIVERY/pos-online"

# --- 1. generic-model (código sin .git, target, IDE)
echo "[1/3] Copiando smartbussiness-generic-model..."
SRC_GM="$BASE/smartbussiness-generic-model"
DST_GM="$DELIVERY/smartbussiness-generic-model"
if [ ! -d "$SRC_GM" ] || [ ! -f "$SRC_GM/pom.xml" ]; then
  echo "AVISO: No existe $SRC_GM con pom.xml. Se crea estructura mínima."
  mkdir -p "$DST_GM"
else
  rsync -a --exclude='.git' --exclude='target' --exclude='.idea' --exclude='*.iml' \
    "$SRC_GM/" "$DST_GM/"
fi
# README: título + párrafo de instalación + resto (sin referencias internas)
if [ -f "$DST_GM/README.md" ]; then
  (echo "# SmartBussiness Generic Model"; echo ""; echo "Biblioteca compartida (Maven) para cálculos con precisión SAT. Es dependencia obligatoria de **pos-online**. Para instalación y uso con Jenkins y Gitea, ver la **Guía de instalación** en el repo **fastflow-integration**."; echo ""; tail -n +4 "$DST_GM/README.md") > "$DST_GM/README.md.tmp" && mv "$DST_GM/README.md.tmp" "$DST_GM/README.md"
fi
echo "    -> $DST_GM listo."

# --- 2. pos-online (código sin .git, target, .idea)
echo "[2/3] Copiando pos-online (repo-pos-fastflow)..."
SRC_PO="$BASE/repo-pos-fastflow"
DST_PO="$DELIVERY/pos-online"
if [ ! -d "$SRC_PO" ] || [ ! -f "$SRC_PO/pom.xml" ]; then
  echo "AVISO: No existe $SRC_PO con pom.xml. Se crea estructura mínima."
  mkdir -p "$DST_PO"
else
  rsync -a --exclude='.git' --exclude='target' --exclude='.idea' --exclude='*.iml' \
    --exclude='AUDITORIA-JENKINS-REGISTRY.md' --exclude='PULIDO-MULTIAGENTES.md' \
    "$SRC_PO/" "$DST_PO/"
  # README corto para usuario final (sin referencias externas)
  printf '%s\n' '# pos-online' '' 'Aplicación POS (Java/Spring Boot) con **Jenkinsfile** FastFlow: build, test, package y deploy manual en el puerto 8111. Dependencia obligatoria: **smartbussiness-generic-model** (debe estar instalado en el agente de Jenkins antes de compilar pos-online).' '' 'Para instalación paso a paso (Gitea, Jenkins, jobs, despliegue), ver el **Manual de usuario** y la **Guía de instalación** en el repo **fastflow-integration**.' > "$DST_PO/README.md"
  # deploy/terraform: texto neutro (sin referencias a toolkit ni rutas internas)
  mkdir -p "$DST_PO/deploy/terraform"
  printf '%s\n' '# Terraform — infra para POS / FastFlow' '' 'Despliegue en un cluster Kubernetes existente (EKS, GKE, minikube, kind). Provisiona el cluster con tu propia configuración Terraform o con los manifiestos que uses.' '' '- **Uso:** `terraform init && terraform plan -var="pos_image=REGISTRY/pos-online:latest" && terraform apply`' '- **Variables:** ver `variables.tf`. Por defecto usa imagen `pos-online:latest` (registry opcional).' > "$DST_PO/deploy/terraform/README.md"
  # Sanitizar: quitar archivos de entorno con IPs/credenciales reales; dejar solo plantilla
  RES_PO="$DST_PO/src/main/resources"
  for f in application-envDev.properties application-envQas.properties application-envPrd.properties application-envPpr.properties; do
    [ -f "$RES_PO/$f" ] && rm -f "$RES_PO/$f" && echo "    (eliminado $f con datos sensibles)"
  done
  cp "$SCRIPT_DIR/pos-online-application-env.example.properties" "$RES_PO/application-env.example.properties"
  printf '%s\n' '# Configuración por entorno' '' 'El perfil por defecto es **local** (application-local.properties), válido para desarrollo y para la Guía FastFlow (puerto 8111, H2 en memoria).' '' 'Para otros entornos (DEV, QAS, PRD): copia **application-env.example.properties** a application-envDev.properties (o envQas, envPrd, envPpr), sustituye los placeholders por tus servidores y credenciales, y no subas esos archivos al repositorio.' > "$RES_PO/README-CONFIGURACION.md"
  # Quitar contraseñas que parezcan reales en application-local (dejar vacío para H2 local)
  for key in app.zapata.mysql.password app.fsamano.mysql.password app.demo.mysql.password; do
    sed -i.bak "s|^${key}=.*|${key}=|" "$RES_PO/application-local.properties" 2>/dev/null || true
  done
  rm -f "$RES_PO/application-local.properties.bak"
fi
echo "    -> $DST_PO listo."

# --- 3. fastflow-integration (solo estructura si no existe; no sobrescribir docs)
echo "[3/3] fastflow-integration..."
DST_FF="$DELIVERY/fastflow-integration"
mkdir -p "$DST_FF/docs" "$DST_FF/scripts"
echo "    -> $DST_FF (si ya tiene README y docs, no se sobrescriben)."

echo ""
echo "=== Listo. Directorios en: $DELIVERY ==="
echo "  - smartbussiness-generic-model  (generic model, sin historial)"
echo "  - pos-online                   (pos-online, sin historial)"
echo "  - fastflow-integration         (guías para conectar todo)"
echo ""
echo "Documentación de usuario: fastflow-integration/MANUAL-USUARIO.md y GUIA-INSTALACION.md"

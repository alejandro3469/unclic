#!/bin/bash
# fastflow-quickstart.sh
# Instalador rápido de 1-clic para el entorno local de FastFlow.

set -e

# Colores para la salida
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== 🌊 Bienvenido al Quickstart de FastFlow ===${NC}"
echo "Este script configurará un entorno de Jenkins listo para usar en tu máquina local."

# 1. Verificar requisitos
echo -e "\n${BLUE}[1/4] Verificando requisitos locales...${NC}"
if ! command -v docker &> /dev/null; then
    echo "❌ ERROR: Docker no está instalado. Por favor instálalo primero."
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ ERROR: docker-compose no está instalado."
    exit 1
fi
echo "✅ Docker y docker-compose encontrados."

# 2. Preparar el entorno
echo -e "\n${BLUE}[2/4] Preparando directorios de FastFlow...${NC}"
mkdir -p jenkins_home
chmod 777 jenkins_home
echo "✅ Directorio de persistencia listo."

# 3. Levantar servicios
echo -e "\n${BLUE}[3/4] Iniciando contenedores (docker-compose up)...${NC}"
# Asumimos que el usuario está en la raíz del repo. Si no, lo descargamos.
if [ ! -f "docker-compose.yml" ]; then
    echo "Descargando configuración de Docker Compose..."
    curl -sSL https://raw.fastflow.ai/docker-compose.yml -o docker-compose.yml
fi

docker-compose up -d
echo "✅ Contenedores iniciados en segundo plano."

# 4. Finalizar
echo -e "\n${BLUE}[4/4] Esperando a que Jenkins inicie...${NC}"
sleep 10
PASS=$(docker exec jenkins-fastflow cat /var/jenkins_home/secrets/initialAdminPassword 2>/dev/null || echo "Aún cargando...")

echo -e "\n${GREEN}=== 🎉 ¡FastFlow está listo para usar! ===${NC}"
echo -e "URL de acceso: ${BLUE}http://localhost:8080${NC}"
echo -e "Contraseña inicial: ${BLUE}$PASS${NC}"
echo -e "\nSiguientes pasos:"
echo "1. Accede a la URL y completa el setup inicial."
echo "2. Revisa la guía de inicio rápido en docs/HOME-DESARROLLADOR.md."
echo "3. ¡Empieza a fluir!"

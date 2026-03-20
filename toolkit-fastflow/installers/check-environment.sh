#!/bin/bash
# Check Environment Script - FastFlow Toolkit
# Diagnostica la salud del sistema basado en comandos básicos de Linux.

echo "--- FastFlow Diagnostic Tool ---"
echo "--- Fecha: $(date) ---"

# 1. ¿Dónde estamos? (pwd)
echo "--- Directorio Actual (pwd) ---"
pwd

# 2. ¿Quiénes somos? (whoami)
echo "--- Usuario Actual (whoami) ---"
whoami

# 3. ¿Tenemos espacio? (df -h)
echo "--- Espacio en Disco (df -h) ---"
df -h / | awk 'NR==1 || NR==2'

# 4. ¿Hay memoria? (free -h)
echo "--- Memoria Disponible (free -h) ---"
free -h 2>/dev/null || echo "Comando 'free' no disponible. (Revisa /proc/meminfo)"

# 5. ¿Qué hay aquí? (ls -lh)
echo "--- Contenido del Directorio Actual (ls -lh) ---"
ls -lh | head -n 10

# 6. ¿Qué sistema es este? (cat /etc/os-release)
echo "--- Información del Sistema Operativo ---"
if [ -f /etc/os-release ]; then
    cat /etc/os-release | grep "PRETTY_NAME" | cut -d'=' -f2 | tr -d '"'
else
    uname -a
fi

# 7. Diagnóstico de Red (ip addr, ip route)
echo "--- Interfaces de Red (ip addr) ---"
ip -4 addr show scope global | grep inet || echo "No se encontraron IPs públicas/globales."

echo "--- Tabla de Enrutamiento (ip route) ---"
ip route | grep default || echo "No hay ruta por defecto (Gateway)."

# 8. Conectividad Externa (ping)
echo "--- Conectividad Externa (ping 8.8.8.8) ---"
ping -c 1 8.8.8.8 > /dev/null 2>&1 && echo "Conexión a Internet: OK" || echo "Conexión a Internet: FALLIDA"

# 9. Resolución DNS (nslookup/host)
echo "--- Resolución DNS (google.com) ---"
host google.com > /dev/null 2>&1 || nslookup google.com > /dev/null 2>&1 && echo "DNS: OK" || echo "DNS: FALLIDA"

echo "--- Diagnóstico Completado ---"

#!/usr/bin/env bash
# Limpia el servidor de deploy (fastflow-vantive o similar): backups antiguos del sitio, logs de Nginx.
# Ejecutar manualmente en el servidor de deploy o desde Jenkins con SSH.
set -euo pipefail

DEPLOY_PATH="${DEPLOY_PATH:-/usr/share/nginx/unclic}"
BACKUP_DIR="${BACKUP_DIR:-${DEPLOY_PATH}.backups}"
KEEP_BACKUPS="${KEEP_BACKUPS:-3}"

echo "Limpiando servidor de deploy: ${DEPLOY_PATH}"

# Crear directorio de backups si no existe
mkdir -p "${BACKUP_DIR}"

# Backup del sitio actual antes de limpiar (opcional)
if [ -d "${DEPLOY_PATH}" ] && [ "$(ls -A ${DEPLOY_PATH} 2>/dev/null)" ]; then
    BACKUP_NAME="backup-$(date +%Y%m%d-%H%M%S)"
    echo "Creando backup: ${BACKUP_DIR}/${BACKUP_NAME}"
    tar -czf "${BACKUP_DIR}/${BACKUP_NAME}.tar.gz" -C "$(dirname ${DEPLOY_PATH})" "$(basename ${DEPLOY_PATH})" 2>/dev/null || true
fi

# Mantener solo los últimos N backups
if [ -d "${BACKUP_DIR}" ]; then
    echo "Eliminando backups antiguos (manteniendo últimos ${KEEP_BACKUPS})"
    ls -t "${BACKUP_DIR}"/backup-*.tar.gz 2>/dev/null | tail -n +$((KEEP_BACKUPS + 1)) | xargs rm -f 2>/dev/null || true
fi

# Limpiar logs de Nginx (si existen y son antiguos)
if [ -d /var/log/nginx ]; then
    echo "Limpiando logs de Nginx antiguos (>30 días)"
    find /var/log/nginx -name "*.log" -type f -mtime +30 -delete 2>/dev/null || true
    find /var/log/nginx -name "*.log.*.gz" -type f -mtime +30 -delete 2>/dev/null || true
fi

# Limpiar caché de Nginx (si existe)
if [ -d /var/cache/nginx ]; then
    echo "Limpiando caché de Nginx"
    rm -rf /var/cache/nginx/* 2>/dev/null || true
fi

echo "Limpieza completada. Espacio libre:"
df -h "${DEPLOY_PATH}" | tail -1

if [ -d "${BACKUP_DIR}" ]; then
    echo "Backups guardados en: ${BACKUP_DIR}"
    ls -lh "${BACKUP_DIR}"/*.tar.gz 2>/dev/null | tail -5 || echo "  (sin backups)"
fi

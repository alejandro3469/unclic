#!/usr/bin/env bash
# Instala varios bloques de Shadcn Blocks de una vez.
# Requiere: .env.local con SHADCNBLOCKS_API_KEY
# Uso: desde la raíz de unclic → bash scripts/add-shadcn-blocks.sh
#
# IMPORTANTE: Cuando la CLI pregunte si quieres sobrescribir archivos (button, utils, badge...),
# responde No a todos. No uses -o. Ver docs/INSTALAR-SHADCN-BLOCKS.md.
# Formato de nombres: sin guión (hero2, pricing3). Edita BLOCKS=() con los de la toolbar.

set -e
cd "$(dirname "$0")/.."

if [[ ! -f .env.local ]]; then
  echo "Crea .env.local con SHADCNBLOCKS_API_KEY=sk_live_..."
  exit 1
fi

# Evita "export: not valid in this context" con valores largos o con caracteres especiales
export SHADCNBLOCKS_API_KEY=$(grep '^SHADCNBLOCKS_API_KEY=' .env.local | head -1 | cut -d= -f2- | tr -d '\r' | xargs)

if [[ -z "$SHADCNBLOCKS_API_KEY" ]]; then
  echo "SHADCNBLOCKS_API_KEY no está en .env.local"
  exit 1
fi

# Lista de bloques: formato oficial = categoría + número SIN GUION (hero1, pricing3).
# Ver https://docs.shadcnblocks.com/blocks/shadcn-cli/ — copia el comando de la toolbar.
BLOCKS=(
  hero2
  hero18
  login7
  signup10
  signup3
  cta1
  footer1
  navbar1
  faq1
  feature102
  pricing34
  gallery30
  contact1
  testimonial12
  about18
  team4
)

# -y = proceder sin preguntar "Proceed?"; NO usamos -o para no sobrescribir nuestros componentes
for block in "${BLOCKS[@]}"; do
  echo "--- Añadiendo @shadcnblocks/$block (si pide sobrescribir, responde No) ..."
  npx shadcn add "@shadcnblocks/$block" -y 2>/dev/null || echo "  (omitido o no existe: $block)"
done

echo "Listo. Revisa components/ por los bloques instalados."

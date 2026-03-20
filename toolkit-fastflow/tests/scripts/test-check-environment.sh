#!/bin/bash
# test-check-environment.sh
# Prueba unitaria para el script de diagnóstico de entorno de FastFlow.

# Rutas relativas al proyecto
SCRIPT_PATH="toolkit-fastflow/installers/check-environment.sh"

echo "=== Ejecutando Prueba de Diagnóstico de Entorno ==="

# Verificar que el script existe
if [ ! -f "$SCRIPT_PATH" ]; then
    echo "❌ ERROR: El script $SCRIPT_PATH no existe."
    exit 1
fi

# Hacer el script ejecutable
chmod +x "$SCRIPT_PATH"

# Ejecutar el script y capturar la salida
OUTPUT=$(./"$SCRIPT_PATH")

# Validaciones
echo "Validando secciones clave en la salida..."

# Verificar si contiene las palabras clave esperadas
KEYWORDS=("Linux" "Red" "Seguridad" "Kubernetes" "Observabilidad")
PASSED=true

for keyword in "${KEYWORDS[@]}"; do
    if echo "$OUTPUT" | grep -q "$keyword"; then
        echo "✅ Sección '$keyword' encontrada."
    else
        echo "❌ ERROR: La sección '$keyword' no fue encontrada en la salida."
        PASSED=false
    fi
done

if [ "$PASSED" = true ]; then
    echo "=== ✅ TODAS LAS PRUEBAS DE DIAGNÓSTICO PASARON ==="
    exit 0
else
    echo "=== ❌ ALGUNAS PRUEBAS FALLARON ==="
    exit 1
fi

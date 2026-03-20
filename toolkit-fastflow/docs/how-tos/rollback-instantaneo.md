# How-to: Rollback Instantáneo con FastFlow

En FastFlow, el **Rollback** no es una emergencia, es una capacidad de diseño. Aquí te explicamos cómo ejecutarlo paso a paso.

## 1. El Concepto: Inmutabilidad
Cada vez que haces un despliegue, FastFlow genera una imagen Docker con un **tag único** (basado en el SHA de Git). Esto significa que la versión que funcionó ayer sigue existiendo intacta en tu **Registry**.

## 2. Paso a Paso para el Rollback

### Opción A: Desde Jenkins (Re-despliegue de Versión Conocida)
1. Ve a tu Job de Jenkins.
2. Selecciona "Build with Parameters".
3. En el parámetro de versión (o SHA), introduce el identificador de la última versión estable.
4. Ejecuta el pipeline. Jenkins aplicará esa imagen exacta sobre tu clúster de Kubernetes.

### Opción B: Desde Kubernetes (Rollout Undo)
Si el problema es urgente y necesitas acción inmediata en el clúster:
```bash
# Ver historial de despliegues
kubectl rollout history deployment/tu-aplicacion

# Volver a la versión anterior
kubectl rollout undo deployment/tu-aplicacion
```

## 3. Por qué funciona
Funciona porque seguimos la **Secuencia Crítica**:
- **Receta (Imagen)**: La versión anterior es idéntica a cuando se probó originalmente.
- **Etiquetas (Tags)**: No hay confusión de versiones. Sabemos exactamente qué SHA corresponde a qué estado.
- **Plan B (Rollback)**: El sistema está diseñado para retroceder sin perder datos y sin fricción.

## 4. Recomendación Operativa
- Nunca uses el tag `latest` para despliegues en producción.
- Siempre verifica el estado de salud (`health checks`) después de un rollback para asegurar que el sistema se ha estabilizado.

---
*Para más detalles sobre la operación del clúster, consulta el [Manual Técnico](../manuals/manual-tecnico.md).*

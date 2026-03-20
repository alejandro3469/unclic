# Guía de Conectividad y Descubrimiento (Capa 7 de FastFlow)

En Kubernetes, los Pods son efímeros: nacen y mueren con nuevas IPs. Para que tu aplicación funcione de forma estable, necesitamos un mecanismo que los encuentre siempre. Ese mecanismo es el **Service**.

## 1. El Problema de las IPs Dinámicas
Si tu frontend intenta conectar con tu API usando una IP directa (ej: `10.0.1.45`), tu sistema fallará en cuanto el Pod de la API se reinicie. En FastFlow, prohibimos el uso de IPs directas entre componentes.

## 2. Services: La Dirección Postal Fija
Un Service proporciona una **IP estática** y un **Nombre DNS** que nunca cambian.
- **Service Name**: `numbers-api`
- **DNS Interno**: `http://numbers-api` (Accesible desde cualquier Pod del clúster).

## 3. Tipos de Service en FastFlow
- **ClusterIP (Interno)**: El 90% de tus servicios serán de este tipo. Solo son visibles dentro del clúster. Úsalo para Bases de Datos, APIs internas y colas de mensajes.
- **LoadBalancer (Externo)**: Expone tu aplicación al mundo real. Kubernetes solicita una IP pública al proveedor de nube (AWS, Azure, GCP). Úsalo para tu Frontend o API Gateway.
- **NodePort (Desarrollo)**: Expone el servicio en un puerto específico de cada nodo. Útil para pruebas rápidas en entornos locales como K3s.

## 4. El Vínculo: Labels y Selectors
Un Service no "sabe" qué Pods le pertenecen por su nombre, sino por sus etiquetas.
- **Selector**: `app: numbers-api`
- **Acción**: El Service enviará tráfico a *todos* los Pods que tengan la etiqueta `app: numbers-api`.

## 5. Validación de Conectividad
Usa `kubectl get svc` para ver tus servicios y sus IPs. Si un componente no encuentra a otro, verifica que el **Selector** del Service coincida exactamente con las **Labels** del Deployment.

---
*Para probar la malla de servicios, usa el script `scripts/test-service-mesh.sh`.*

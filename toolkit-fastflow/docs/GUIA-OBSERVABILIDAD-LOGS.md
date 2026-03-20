# Guía de Observabilidad y Centralización de Logs (Capa 11 de FastFlow)

La observabilidad no es solo mirar logs; es entender el estado interno de un sistema a partir de sus salidas externas. En FastFlow, pasamos de ser reactivos a ser predictivos.

## 1. El Stack EFK: El Estándar de Oro
FastFlow utiliza el stack **EFK** (Elasticsearch, Fluentd/Fluent Bit, Kibana) para la gestión centralizada de logs en Kubernetes.

- **Elasticsearch**: El motor de búsqueda y almacenamiento de logs.
- **Fluent Bit / Fluentd**: El agente que recolecta, filtra y envía los logs desde los nodos al motor de búsqueda.
- **Kibana**: La interfaz visual para explorar los logs y crear dashboards.

## 2. Centralización vs. Dispersión
En un entorno de microservicios, los logs dispersos son inútiles.
- **Antes**: Entrar a cada servidor/pod con `kubectl logs` o SSH.
- **Ahora (FastFlow)**: Todos los logs fluyen automáticamente a un único portal de búsqueda.

## 3. Filtrado y Estructura
No todos los logs son iguales. Un log estructurado (en formato JSON) es infinitamente más valioso que un texto plano.
- **Ejemplo de log útil**: `{"timestamp": "...", "service": "orders", "level": "ERROR", "traceId": "abc-123", "message": "DB connection failed"}`.
- **Fluent Bit** se encarga de parsear estos logs y añadir metadatos de Kubernetes (nombre del pod, namespace, etiquetas).

## 4. De Logs a Métricas y Alertas
Los logs son solo el principio.
- **Métricas**: ¿Cuántos errores 500 por segundo estamos teniendo?
- **Alertas**: Si los errores 500 superan el 5% en 1 minuto, notificar a Slack/PagerDuty inmediatamente.

## 5. Mejores Prácticas de FastFlow
1. **No guardes logs en archivos locales**: Los contenedores son efímeros. Si el pod muere, el log muere. Envía todo a `stdout/stderr`.
2. **Usa IDs de Correlación**: Pasa un `traceId` entre microservicios para seguir una petición completa en Kibana.
3. **Niveles de Log Apropiados**: Usa `DEBUG` solo en desarrollo. En producción, `INFO` y `WARN/ERROR` son tus mejores amigos.

---
*La observabilidad es el seguro de vida de tu plataforma. Sin ella, estás volando a ciegas.*

# Kubernetes — POS Online

Aplicar en orden: `namespace.yaml` → `deployment.yaml` → `service.yaml`.

- **Imagen:** Sustituir `pos-online:latest` por `<REGISTRY>/pos-online:latest` si usas registry (Jenkins push).
- **Probar:** `kubectl port-forward svc/pos-online 8111:8111 -n pos-online` y abrir http://localhost:8111/health.

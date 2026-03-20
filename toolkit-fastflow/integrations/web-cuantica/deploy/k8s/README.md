# Kubernetes — Implementación pos-online

Manifiestos para desplegar en un cluster Kubernetes. La imagen se obtiene del pipeline **Jenkins → Docker build → push a registry**. Para **pos-online** sustituir nombres `fastflow-server` por `pos-online` (o el nombre de imagen que use el registry) y ajustar ConfigMap/Secret a la aplicación pos-online.

## Orden de aplicación

Asegúrate de tener el **namespace** (o usar `default`). Si usas el namespace `fastflow`:

```bash
kubectl apply -f namespace.yaml
kubectl apply -f configmap.yaml
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

O desde esta carpeta, con namespace creado:

```bash
kubectl apply -f .
```

## Antes de aplicar

1. **Imagen en registry:** La imagen `fastflow-server` debe estar construida y subida al registry (ver `docs/pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md` y pipeline Jenkins). En `deployment.yaml` sustituir `fastflow-server:latest` por la imagen completa, p. ej.:
   - `tuusuario/fastflow-server:latest` (Docker Hub)
   - `registry.gitlab.com/grupo/proyecto/fastflow-server:latest`
   - `123456789.dkr.ecr.region.amazonaws.com/fastflow-server:latest` (AWS ECR)

2. **ConfigMap:** Editar `configmap.yaml` y poner `BASE_URL` con la URL pública del servicio (ej. `https://fastflow.ejemplo.com`).

3. **Secretos (opcional):** Si usas `SESSION_SECRET` o `ADMIN_PASSWORD`, crear un Secret y referenciarlo en el Deployment con `envFrom` o `env`:

   ```bash
   kubectl create secret generic fastflow-secrets -n fastflow \
     --from-literal=SESSION_SECRET=xxx \
     --from-literal=ADMIN_PASSWORD=yyy
   ```

   Luego en el Deployment añadir:
   ```yaml
   envFrom:
     - secretRef:
         name: fastflow-secrets
   ```

## Verificación

```bash
kubectl get pods,svc -n fastflow
kubectl port-forward svc/fastflow-server 3000:3000 -n fastflow
# Abrir http://localhost:3000 y probar /api/health
```

## Validación local (sin cluster)

Para comprobar que los YAML son válidos sin tener un cluster:

```bash
kubectl apply --dry-run=client -f .
# o desde la raíz del repo: bash scripts/validate-k8s.sh
```

Desde la raíz del repo: `bash scripts/validate-k8s.sh` para validar los YAML en seco.

## Referencias

- **Build y registry:** `docs/pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md`
- **Pipeline Jenkins:** `docs/pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md`
- **Requerimientos demo:** `docs/pipeline-y-registry/REQUIREMENTS-DEMO-AWS-JENKINS.md`
- **Tests y scripts de validación:** `server/test/README.md`, `scripts/validate-k8s.sh`

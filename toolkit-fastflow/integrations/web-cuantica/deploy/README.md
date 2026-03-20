# Deploy — POS Online (implementación FastFlow)

Contenido de esta carpeta para **desplegar** la aplicación POS Online (imagen Docker construida por el pipeline).

---

## Contenido

| Recurso | Descripción |
|---------|-------------|
| **k8s/** | Manifiestos Kubernetes (namespace, deployment, service, configmap). Aplicar con `kubectl apply -f k8s/`. Ajustar imagen a la del registry (pos-online o la que use el proyecto). |
| **terraform/** | Terraform para desplegar en un cluster Kubernetes existente. Variables: imagen, base_url, namespace. |
| **dashboard-demo-jenkins-registry.html** | UI mínima: enlaces a Jenkins, registry y aplicación. Servir estáticamente; ajustar URLs según entorno. |
| **scripts/** | Scripts de verificación (si existen). |

---

## Uso

- **Kubernetes:** `kubectl apply -f k8s/` (tras ajustar la imagen en deployment.yaml).
- **Terraform:** `terraform init && terraform plan -var="fastflow_image=registry/pos-online:latest" && terraform apply`.
- **Dashboard:** Abrir el HTML en navegador o servir con un servidor estático.

Documentación: [docs/README.md](../docs/README.md).

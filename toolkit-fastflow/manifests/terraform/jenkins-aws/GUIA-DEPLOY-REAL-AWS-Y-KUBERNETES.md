# Cómo probar: deploy real en AWS (Terraform) y Kubernetes

Guía para (1) desplegar la infra en AWS con Terraform y (2) probar el flujo Kubernetes (POS en un cluster) de una vez.

---

## Parte 1 — Deploy real en AWS con Terraform

### Requisitos

- Cuenta AWS (CLI configurado: `aws configure`).
- Terraform >= 1.5 en tu máquina.

### Pasos (resumen)

1. **Ir al entorno Terraform (desde la raíz del repo):**

   La ruta es respecto a la **raíz del repo** (carpeta `pipeline-as-code-with-jenkins-master`). Si estás en otra carpeta, usa la ruta relativa que corresponda.

   ```bash
   # Desde la raíz del repo (pipeline-as-code-with-jenkins-master):
   cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev

   # Si estás en repo-pos-fastflow:
   cd ../../../manifests/terraform/jenkins-aws/envs/dev
   ```

2. **Crear el archivo de variables:**  
   `terraform.tfvars` guarda los valores (región, AMIs, etc.) para tu entorno. Se crea copiando el ejemplo de Free Tier; **no se sube a Git.**

   ```bash
   cp terraform.tfvars.free-tier.example terraform.tfvars
   ```

3. **Obtener AMI (ej. us-east-1, Amazon Linux 2):**

   ```bash
   aws ec2 describe-images --owners amazon --region us-east-1 \
     --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
     --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
   ```

   Rellena en `terraform.tfvars`:

   - `jenkins_controller_ami_id`, `jenkins_worker_ami_id`, `gitea_ami_id`, `pos_ami_id` (misma AMI para todos en Free Tier).
   - `aws_region` y `availability_zones` si cambias de región.

4. **Aplicar:**

   ```bash
   terraform init
   terraform validate
   terraform plan -var-file=terraform.tfvars
   terraform apply -var-file=terraform.tfvars
   ```

5. **Anotar salidas:**  
   Tras el apply verás `jenkins_public_ip`, `gitea_public_ip`, `pos_public_ip`. Usa esas IPs para instalar Jenkins (8080), Gitea (3000) y para el deploy del POS (8111). Si activaste Registry: `registry_public_ip` (5000).

**Guía detallada:** [INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](INSTRUCCIONES-DEPLOY-AWS-GRATIS.md). Free Tier: [terraform.tfvars.free-tier.example](envs/dev/terraform.tfvars.free-tier.example).

---

## Parte 2 — Probar Kubernetes “de una vez”

Tienes dos formas: **local (minikube/kind)** o **cluster real (EKS)**. En ambos casos el objetivo es aplicar el despliegue del POS (Terraform o YAML) y comprobar que el POD arranca.

### Opción A — Kubernetes local (minikube o kind)

Sin coste en AWS; sirve para validar manifiestos y el Terraform del POS.

1. **Crear cluster local:**

   **minikube:**
   ```bash
   minikube start
   ```

   **kind:**
   ```bash
   kind create cluster --name fastflow
   ```

2. **Configurar kubeconfig:**  
   Tras arrancar, `kubectl` suele apuntar ya al cluster (`kubectl cluster-info`).

3. **Desplegar el POS en el cluster:**

   **Con Terraform (repo-pos-fastflow):**
   ```bash
   cd toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow/deploy/terraform
   terraform init
   terraform plan -var="pos_image=pos-online:latest"
   terraform apply -var="pos_image=pos-online:latest" -auto-approve
   ```

   **Con YAML (kubectl):**
   ```bash
   cd toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow/deploy/k8s
   kubectl apply -f namespace.yaml
   kubectl apply -f deployment.yaml   # Ajusta la imagen en el YAML si usas registry
   kubectl apply -f service.yaml
   ```

4. **Probar:**
   ```bash
   kubectl port-forward svc/pos-online 8111:8111 -n pos-online
   # Abrir http://localhost:8111/health
   ```

Si la imagen `pos-online:latest` no existe en el cluster, construye y carga en el cluster local (ej. con minikube: `eval $(minikube docker-env)` y `docker build` en el repo del POS; o usa una imagen pública de prueba).

### Opción B — Kubernetes en AWS (EKS)

Para probar contra un cluster EKS real (tiene coste; útil para integración con Jenkins y registry en AWS).

1. **Tener un cluster EKS:**  
   Por ahora el Terraform de este repo no incluye módulo EKS (está previsto en el plan). Opciones:
   - Crear el cluster a mano en la consola AWS (EKS → Create cluster) y anotar nombre y región.
   - O usar otro Terraform/CloudFormation que cree EKS; luego usar ese cluster aquí.

2. **Configurar kubeconfig para EKS:**
   ```bash
   aws eks update-kubeconfig --region <REGION> --name <CLUSTER_NAME>
   kubectl get nodes
   ```

3. **Desplegar el POS en EKS (Terraform):**  
   El Terraform de `repo-pos-fastflow/deploy/terraform` usa el provider Kubernetes con el kubeconfig actual. Si la imagen está en un registry privado (ej. el de tu Jenkins), pasa la imagen y, si aplica, configura el Secret de registry (ver [deploy/k8s/README.md](../../integrations/web-cuantica/repo-pos-fastflow/deploy/k8s/README.md)).

   ```bash
   cd toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow/deploy/terraform
   terraform init
   terraform apply -var="pos_image=<REGISTRY>/pos-online:latest"
   ```

4. **Probar:**  
   `kubectl port-forward` al service o exponer con LoadBalancer/Ingress según tengas configurado el cluster.

**Provider Kubernetes con EKS:** Para que el Terraform de `deploy/terraform` use EKS, el provider `kubernetes` debe usar el mismo kubeconfig (por defecto `~/.kube/config`). Si usas `aws eks get-token`, no hace falta configurar nada más mientras `kubectl` funcione. Ver [repo-pos-fastflow/deploy/terraform/README.md](../../integrations/web-cuantica/repo-pos-fastflow/deploy/terraform/README.md).

---

## Resumen rápido

| Objetivo | Acción |
|----------|--------|
| **Deploy real AWS** | `envs/dev` → tfvars con AMIs → `terraform init && terraform apply -var-file=terraform.tfvars` |
| **Probar K8s sin EKS** | minikube o kind → `deploy/terraform` con `pos_image=...` o `kubectl apply -f deploy/k8s/` → `kubectl port-forward` |
| **Probar K8s en EKS** | Cluster EKS creado → `aws eks update-kubeconfig` → mismo `deploy/terraform` con `pos_image=<REGISTRY>/pos-online:TAG` |

Documentos relacionados: [INICIO-RAPIDO-AWS-GRATIS.md](INICIO-RAPIDO-AWS-GRATIS.md), [PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md](../../integrations/web-cuantica/docs/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md).

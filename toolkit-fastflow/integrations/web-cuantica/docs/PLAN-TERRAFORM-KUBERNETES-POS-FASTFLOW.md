# Plan: Terraform y Kubernetes para automatizar el POS (FastFlow)

Plan para integrar **Terraform** (infraestructura como código) y **Kubernetes** (orquestación del POS) en el flujo actual: commit → Gitea → Jenkins → build imagen → registry → despliegue. Basado en lo que ya existe en el proyecto.

---

## 1. Estado actual (base del plan)

| Componente | Ubicación | Qué hace hoy |
|------------|-----------|----------------|
| **Jenkinsfile** | repo-pos-fastflow | Prepare → Build → Test → Lint → Package → **Build image** → **Push to registry** (si REGISTRY) → Approve → Cleanup → **Deploy** (Docker: local `docker run` o remoto `docker pull` + `docker run` en EC2) → Verify |
| **deploy/k8s/** | repo-pos-fastflow | YAML: namespace, deployment (imagen pos-online:latest), service ClusterIP 8111. Uso manual: `kubectl apply -f ...` |
| **deploy/terraform/** | repo-pos-fastflow | Terraform con provider Kubernetes: crea namespace, deployment, service en un **cluster existente**. Variable `pos_image`. No crea el cluster. |
| **manifests/terraform/jenkins-aws/** | toolkit-fastflow | Terraform AWS: VPC, subnets, **EC2 Jenkins**, autoscaling workers. No incluye Gitea, ni POS, ni EKS. |

**Flujo hoy:** Código → Gitea → Jenkins → imagen Docker → (registry) → deploy en **EC2** (contenedor o JAR). No hay Kubernetes en el pipeline ni infra definida 100% con Terraform.

---

## 2. Objetivo

- **Terraform:** Definir y versionar la infra (VPC, EC2 para Jenkins/Gitea/Registry, y **o bien** EC2 POS **o bien** cluster EKS para el POS).
- **Kubernetes:** Desplegar el POS como workload en un cluster (EKS o existente); imagen desde registry; actualización desde Jenkins.
- **Automatización completa:** Un commit a main → Jenkins construye y sube imagen → despliegue automático en Kubernetes (o, en fase intermedia, en EC2 como ahora).

---

## 3. Fases de implementación

### Fase 1 — Terraform: infra base (AWS)

**Objetivo:** Tener toda la infra necesaria definida en Terraform (reproducible, versionada).

| Paso | Acción | Dónde | Detalle |
|------|--------|-------|---------|
| 1.1 | Reutilizar o extender | `manifests/terraform/jenkins-aws` | Ya existe VPC + EC2 Jenkins. Añadir **módulos o recursos** para: EC2 Gitea, EC2 Registry (opcional), EC2 POS (si se mantiene deploy fuera de K8s). O crear **módulo EKS** para cluster. |
| 1.2 | Módulo EC2 Gitea | Nuevo `modules/gitea` o en `compute` | Instancia tipo t3.micro, security group (22, 3000, 443), salida a internet. Output: IP pública. |
| 1.3 | Módulo EC2 Registry | Nuevo `modules/registry` (opcional) | Instancia o mismo host que Jenkins; puerto 5000; `insecure-registries` documentado. |
| 1.4 | Opción A — EC2 POS | Módulo `pos-demo` | Instancia para deploy actual (Docker); security group 22, 8111 (desde Jenkins), 80/443 para Nginx. |
| 1.5 | Opción B — EKS | Nuevo módulo `eks` | Cluster EKS (terraform-aws-eks o recurso `aws_eks_cluster`); node group; kubeconfig en output. |

**Entregable Fase 1:** `terraform apply` deja listas: VPC, Jenkins, Gitea, (Registry), y **o** EC2 POS **o** cluster EKS. Documentar en README del entorno (ej. `envs/dev/README.md`) cómo aplicar y qué variables usar.

**Fase 1 implementada:** En `manifests/terraform/jenkins-aws` se añadieron los módulos `modules/gitea`, `modules/registry` (opcional, `enable_registry`), y `modules/pos`. El entorno `envs/dev` los invoca; hay que definir en `terraform.tfvars` las variables `gitea_ami_id` y `pos_ami_id` (y opcionalmente `enable_registry` + `registry_ami_id`). Un `terraform apply` deja lista la infra: Jenkins, Gitea, POS en EC2 y, si se activa, Registry. El módulo EKS queda para una fase posterior (Opción B).

---

### Fase 2 — Kubernetes: despliegue del POS en cluster

**Objetivo:** El POS corre como Deployment en un cluster Kubernetes; la imagen viene del registry (Jenkins push).

| Paso | Acción | Dónde | Detalle |
|------|--------|-------|---------|
| 2.1 | Cluster existente | Ya cubierto en repo-pos-fastflow | `deploy/terraform` (provider Kubernetes) y `deploy/k8s/*.yaml` asumen cluster y kubeconfig. |
| 2.2 | Imagen desde registry | deploy/k8s/deployment.yaml y variables Terraform | Sustituir `pos-online:latest` por `REGISTRY/pos-online:TAG`. Variable `pos_image` en Terraform. |
| 2.3 | imagePullPolicy / secrets | Si registry privado | Crear Secret tipo docker-registry; referenciarlo en deployment. Documentar en deploy/k8s/README.md. |
| 2.4 | Ingress (opcional) | deploy/k8s/ingress.yaml | Para exponer POS por HTTPS (ej. pos.dominio.com) con Ingress controller (nginx, ALB). |

**Entregable Fase 2:** Con cluster creado (por Fase 1 o manual) y kubeconfig configurado: `terraform -var="pos_image=REGISTRY/pos-online:latest" apply` en `repo-pos-fastflow/deploy/terraform` despliega el POS. O `kubectl apply -f deploy/k8s/` con imagen actualizada.

---

### Fase 3 — Integración Jenkins ↔ Terraform / Kubernetes

**Objetivo:** Tras Build image y Push to registry, el pipeline **despliegue en Kubernetes** (o siga ofreciendo deploy EC2 como hoy).

| Paso | Acción | Dónde | Detalle |
|------|--------|-------|---------|
| 3.1 | Variable de entorno | Job Jenkins | Ej. `DEPLOY_TO_K8S=true`, `KUBECONFIG` o credencial con kubeconfig, `TF_WORKING_DIR` o `K8S_MANIFESTS_PATH`. |
| 3.2 | Stage "Deploy to Kubernetes" | Jenkinsfile | Condicional: si `DEPLOY_TO_K8S` y imagen en registry, ejecutar **una** de: (A) `kubectl set image deployment/pos-online pos-online=REGISTRY/pos-online:TAG -n pos-online` y `kubectl rollout status`, o (B) `terraform -var="pos_image=REGISTRY/pos-online:TAG" apply -auto-approve` en deploy/terraform. Requiere Terraform y/o kubectl en el agente (o agente con Docker que ejecute imagen con kubectl/terraform). |
| 3.3 | Credenciales | Jenkins | Kubeconfig como Secret file o “Kubernetes credentials” (plugin); o service account para Jenkins dentro del cluster. |
| 3.4 | Deploy EC2 sigue disponible | Jenkinsfile | Si no está `DEPLOY_TO_K8S`, mantener lógica actual (deploy Docker en EC2 o local). |

**Entregable Fase 3:** Build #N → push `REGISTRY/pos-online:N` → stage "Deploy to Kubernetes" actualiza el Deployment en el cluster (o aplica Terraform con la nueva imagen). Verify puede hacer `kubectl exec` o curl al Service/Ingress.

---

### Fase 4 — Terraform: EKS desde cero (opción completa)

**Objetivo:** Si se elige EKS en Fase 1, el cluster se crea con Terraform; luego Fase 2 y 3 usan ese cluster.

| Paso | Acción | Dónde | Detalle |
|------|--------|-------|---------|
| 4.1 | Módulo EKS | manifests/terraform/jenkins-aws/modules/eks | Recurso `aws_eks_cluster`, `aws_eks_node_group`; security groups; output kubeconfig o `aws_eks_cluster.endpoint`, `certificate_authority`. |
| 4.2 | Autenticación | provider kubernetes / aws auth | Configurar provider Kubernetes con cluster creado por Terraform (exec: aws eks get-token). |
| 4.3 | repo-pos-fastflow deploy/terraform | Opcional: backend remoto | Estado Terraform en S3 + DynamoDB para uso en equipo; o local si solo Jenkins aplica. |

**Entregable Fase 4:** Un `terraform apply` en el entorno (ej. dev) crea VPC, Jenkins, Gitea y **EKS**. Otro `terraform apply` en repo-pos-fastflow/deploy/terraform despliega el POS en ese EKS (variable `pos_image` desde Jenkins).

---

## 4. Orden recomendado (resumen)

1. **Fase 1 (Terraform infra):** Extender `manifests/terraform/jenkins-aws` con Gitea (+ Registry si aplica) y, según decisión, EC2 POS o módulo EKS. Aplicar y dejar Jenkins + Gitea (y opcionalmente POS o EKS) listos.
2. **Fase 2 (K8s POS):** Ajustar `repo-pos-fastflow/deploy/k8s` y `deploy/terraform` para usar imagen del registry; probar `terraform apply` / `kubectl apply` a mano contra un cluster (existente o el EKS de Fase 1).
3. **Fase 3 (Jenkins → K8s):** Añadir stage "Deploy to Kubernetes" en el Jenkinsfile; configurar variables y credenciales; probar un build completo hasta deploy en cluster.
4. **Fase 4:** Solo si se va a EKS: implementar módulo EKS en Fase 1 y conectar Fase 2 y 3 con ese cluster.

---

## 5. Archivos a crear o modificar

| Archivo | Acción |
|---------|--------|
| `manifests/terraform/jenkins-aws/modules/gitea/main.tf` (y variables/outputs) | Crear: EC2 Gitea. |
| `manifests/terraform/jenkins-aws/modules/registry/main.tf` | Opcional: EC2 o recurso para registry. |
| `manifests/terraform/jenkins-aws/modules/pos-ec2/main.tf` | Opcional: EC2 para POS (deploy actual). |
| `manifests/terraform/jenkins-aws/modules/eks/main.tf` | Opcional: EKS cluster + node group. |
| `manifests/terraform/jenkins-aws/envs/dev/main.tf` | Modificar: invocar nuevos módulos (gitea, registry, pos o eks). |
| `repo-pos-fastflow/deploy/k8s/deployment.yaml` | Ajustar: imagen desde variable o placeholder REGISTRY/pos-online:TAG. |
| `repo-pos-fastflow/deploy/terraform/variables.tf` | Ya tiene pos_image; añadir si hace falta namespace o registry secret. |
| `repo-pos-fastflow/Jenkinsfile` | Añadir stage "Deploy to Kubernetes" condicional; documentar DEPLOY_TO_K8S, KUBECONFIG. |
| `repo-pos-fastflow/README.md` o docs | Enlazar este plan y pasos de Terraform/K8s. |

---

## 6. Cómo encaja con Terraform (conceptos)

- **Terraform:** Define recursos (EC2, VPC, EKS, y también recursos Kubernetes vía provider `kubernetes`). Estado en `.tfstate`; `terraform plan` / `apply` para cambiar infra y despliegue.
- **Flujo:** Código en Git → Jenkins build → imagen en registry → **Terraform** (con `pos_image=REGISTRY/pos-online:TAG`) actualiza el Deployment en K8s; o **kubectl set image** hace el rollout sin Terraform.
- **Recomendación:** Usar Terraform en repo-pos-fastflow para el despliegue en K8s (namespace + deployment + service) permite versionar cambios de manifiestos en Git; Jenkins solo pasa la variable de imagen y ejecuta `terraform apply`.

---

## 7. Documentos relacionados

| Tema | Documento |
|------|-----------|
| Flujo completo Gitea/Jenkins/POS | [REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md](REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md) |
| Deploy POS en otro servidor (Docker/registry) | [repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md](../repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md) |
| Terraform POS (cluster existente) | [repo-pos-fastflow/deploy/terraform/README.md](../repo-pos-fastflow/deploy/terraform/README.md) |
| K8s YAML POS | [repo-pos-fastflow/deploy/k8s/README.md](../repo-pos-fastflow/deploy/k8s/README.md) |
| Terraform Jenkins (AWS) | [manifests/terraform/jenkins-aws/](../../manifests/terraform/jenkins-aws/) |

---

*Plan basado en el estado actual del proyecto FastFlow (Jenkins, Gitea, Docker, registry, deploy EC2). Ajustar fases y módulos según prioridad (primero EC2 todo en Terraform vs. EKS desde el inicio).*

# Terraform — infra para POS / FastFlow

Despliegue en un cluster Kubernetes existente (EKS, GKE, minikube, kind). El cluster se puede provisionar con [manifests/terraform/jenkins-aws](../../../manifests/terraform/jenkins-aws) en el toolkit.

- **Uso:** `terraform init && terraform plan -var="pos_image=REGISTRY/pos-online:latest" && terraform apply`
- **Variables:** ver `variables.tf`. Por defecto usa imagen `pos-online:latest` (registry opcional).

## Conexión con el pipeline Jenkins

Tras **Push to registry**, el job puede ejecutar (si activas variables en Jenkins):

- `FASTFLOW_TF_VALIDATE=true` → `.jenkins/scripts/terraform-validate-k8s.sh`
- `FASTFLOW_TF_PLAN_K8S=true` → plan con `-var="pos_image=${REGISTRY}/pos-online:${BUILD_NUMBER}"`

Ver cabecera de `../../Jenkinsfile` y `../../.jenkins/PLAN-FASTFLOW-PIPELINE-IAC.md`.

## Laboratorio LocalStack (S3, no K8s)

Módulo aparte: `../terraform-localstack/` — solo APIs AWS emuladas; el POS sigue en este directorio (Kubernetes).

### Usar con EKS (AWS)

1. Crea o ten un cluster EKS y configura kubeconfig:  
   `aws eks update-kubeconfig --region <REGION> --name <CLUSTER_NAME>`
2. Comprueba acceso: `kubectl get nodes`
3. En este directorio: `terraform apply -var="pos_image=<REGISTRY>/pos-online:TAG"`  
   El provider Kubernetes usa por defecto `~/.kube/config`; con EKS no hace falta configurar nada más si `kubectl` ya funciona (IAM/aws eks get-token se usan automáticamente).

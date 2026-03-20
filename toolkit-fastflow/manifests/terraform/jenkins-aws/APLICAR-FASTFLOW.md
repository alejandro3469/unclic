# Cómo aplicar el Terraform FastFlow (Jenkins en AWS)

Guía para el agente o persona que **implementa Terraform** por primera vez. El código despliega VPC, subredes, Jenkins controller (EC2) y un ASG de workers.

---

## Requisitos

- **Terraform** >= 1.6.0
- **AWS CLI** configurado (credenciales con permisos para crear VPC, EC2, ASG, security groups)
- Conocer la **región** y al menos una **AMI** válida (Amazon Linux 2 o Ubuntu 22.04) en esa región

---

## Pasos

### 1. Ir al entorno

```bash
cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
```

### 2. Crear archivo de variables

```bash
cp terraform.tfvars.example terraform.tfvars
```

Editar `terraform.tfvars` y rellenar:

- `aws_region` — región donde desplegar (ej. `us-east-1`)
- `availability_zones` — al menos una AZ de esa región (ej. `["us-east-1a", "us-east-1b"]`)
- `jenkins_controller_ami_id` — ID de una AMI en esa región (Amazon Linux 2 o Ubuntu)
- `jenkins_worker_ami_id` — misma AMI o otra compatible

Para obtener una AMI (Amazon Linux 2 en us-east-1):

```bash
aws ec2 describe-images --owners amazon --region us-east-1 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

### 3. Inicializar y aplicar

```bash
terraform init
terraform validate
terraform plan -var-file=terraform.tfvars
terraform apply -var-file=terraform.tfvars
```

Confirmar con `yes` cuando pida.

### 4. Salidas

Tras `apply`, Terraform muestra (entre otras):

- `jenkins_lb_dns` — IP privada del controller Jenkins (está en subred privada)
- `workers_asg_name` — nombre del Auto Scaling Group de workers

**Acceso a Jenkins:** el controller está en una **subred privada**. Para acceder hace falta:

- **Opción A:** Bastion en subred pública + SSH tunnel al 8080 del controller.
- **Opción B:** (solo para demo) Cambiar temporalmente el módulo compute para usar `public_subnet_ids` y asignar IP pública; luego restringir el security group por IP/VPN según el runbook.

---

## Estructura de módulos

| Módulo       | Qué crea                                      |
|-------------|-----------------------------------------------|
| **network** | VPC, subredes públicas y privadas              |
| **compute** | Security group, instancia EC2 (Jenkins controller) |
| **autoscaling** | Security group, launch template, ASG de workers |

---

## Backend remoto (opcional)

Para trabajar en equipo o no guardar state en local, configurar backend S3 + DynamoDB (locking). En `envs/dev/main.tf` dentro del bloque `terraform { }`:

```hcl
  backend "s3" {
    bucket         = "tu-bucket-terraform-state"
    key            = "fastflow/jenkins-aws/dev/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "terraform-lock"
  }
```

Luego `terraform init` de nuevo.

---

## Destruir

```bash
terraform destroy -var-file=terraform.tfvars
```

---

## Free Tier (1 EC2, sin workers)

Usa **terraform.tfvars.free-tier.example**: `jenkins_in_public_subnet = true`, `workers_desired_capacity = 0`, tipos `t3.micro`. Tras `apply`, la salida `jenkins_public_ip` te da acceso a `http://<IP>:8080` (instala Jenkins por SSH si la AMI no lo trae). Ver **toolkit-fastflow/integrations/web-cuantica/docs/DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md** para desplegar gratis y transferir el repo.

## Referencias

- Blueprint: **toolkit-fastflow/docs/TERRAFORM-JENKINS-BLUEPRINT.md**
- Guía Terraform IaC: **docs/GUIA-TERRAFORM-IAC.md**

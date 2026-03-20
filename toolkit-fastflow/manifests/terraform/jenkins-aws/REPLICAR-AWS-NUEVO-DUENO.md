# Replicar el despliegue en mi cuenta AWS (Free Tier)

Eres el **nuevo dueño del repo** (o tienes acceso a él) y quieres tener **Jenkins en AWS gratis** en tu propia cuenta, replicando el mismo despliegue que usaba el anterior propietario.

---

## Requisitos

- Repo clonado (ya en tu propiedad o con acceso).
- **Cuenta AWS** (si es nueva, tendrás 12 meses de Free Tier; revisa [AWS Free Tier](https://aws.amazon.com/free/)).
- **Terraform** >= 1.6.0 y **AWS CLI** configurado (`aws configure`).

---

## Pasos (resumen)

1. **Clonar / actualizar el repo** y entrar al entorno Terraform:

   ```bash
   cd <ruta-del-repo>/toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
   ```

2. **Crear tu archivo de variables** (no subas `terraform.tfvars` a git):

   ```bash
   cp terraform.tfvars.free-tier.example terraform.tfvars
   ```

3. **Obtener una AMI** en tu región (ej. `us-east-1`):

   ```bash
   aws ec2 describe-images --owners amazon --region us-east-1 \
     --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
     --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
   ```

4. **Editar `terraform.tfvars`** con:
   - `aws_region` y `availability_zones` de tu región.
   - `jenkins_controller_ami_id` y `jenkins_worker_ami_id` = el ID de la AMI anterior.
   - Para **Free Tier** (coste ~0 €):
     - `jenkins_instance_type` = `"t2.micro"`
     - `jenkins_worker_instance_type` = `"t2.micro"`
     - `jenkins_in_public_subnet` = `true`
     - `workers_desired_capacity` = `0`, `workers_min_size` = `0`, `workers_max_size` = `0`

5. **Aplicar Terraform:**

   ```bash
   terraform init
   terraform validate
   terraform plan -var-file=terraform.tfvars
   terraform apply -var-file=terraform.tfvars
   ```

6. **Acceder a Jenkins:** Tras `apply`, usa el valor de **jenkins_public_ip** que muestra Terraform:

   ```text
   http://<jenkins_public_ip>:8080
   ```

   En la instancia tendrás que instalar y configurar Jenkins (y Java 17) según la documentación del producto (por ejemplo **APLICAR-FASTFLOW.md** en este mismo directorio).

---

## Documentación completa

- **DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md** (en este mismo directorio): guía completa de despliegue gratis, transferencia de ownership y réplica.
- **APLICAR-FASTFLOW.md**: pasos detallados de Terraform (init, plan, apply, destruir) y acceso a Jenkins (subred privada con bastión o pública).

---

## Destruir recursos (evitar costes)

Cuando no necesites la infraestructura:

```bash
terraform destroy -var-file=terraform.tfvars
```

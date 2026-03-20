# Sesión: deploy Jenkins en AWS Free Tier (us-east-2)

Documentación de la sesión tal cual: comandos y salidas, con títulos para orientar.

---

## Comprobar identidad AWS

```bash
aws sts get-caller-identity
```

```
{
    "UserId": "AIDAW6EAMEWQ2PKG6AXW5",
    "Account": "477010601377",
    "Arn": "arn:aws:iam::477010601377:user/alejandro-perez"
}
```

---

## Ir al directorio Terraform y crear variables

```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/manifests/terraform/jenkins-aws
cd envs/dev
cp terraform.tfvars.free-tier.example terraform.tfvars
```

---

## Obtener AMI Amazon Linux 2 (us-east-2)

```bash
aws ec2 describe-images --owners amazon --region us-east-2 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

```
ami-040855b0715ee6f0b
```

*(Poner este valor en `terraform.tfvars` en `jenkins_controller_ami_id` y `jenkins_worker_ami_id`; región y AZs en us-east-2.)*

---

## Terraform: comando no encontrado (antes de instalar)

```bash
terraform init
# zsh: command not found: terraform

terraform validate
# zsh: command not found: terraform

terraform plan -var-file=terraform.tfvars
# zsh: command not found: terraform
```

---

## Errores al usar Terraform 1.5.7 (antes de ajustes)

```bash
terraform init
```

- **Error: Unsupported Terraform Core version** — `main.tf` exigía `>= 1.6.0`.
- **Error: Invalid single-argument block definition** — en `modules/autoscaling/variables.tf` (variable `desired_capacity`) y `modules/compute/variables.tf` (variable `use_public_subnet`).

*(Solución aplicada en repo: bajar `required_version` a `>= 1.5.0` y poner variables con `type` y `default` en bloques multilínea.)*

---

## Terraform init correcto (tras instalar Terraform y corregir código)

```bash
terraform init
```

```
Initializing the backend...
Initializing modules...
- autoscaling in ../../modules/autoscaling
- compute in ../../modules/compute
- network in ../../modules/network

Initializing provider plugins...
- Finding hashicorp/aws versions matching ">= 5.0.0"...
- Installing hashicorp/aws v6.36.0...
- Installed hashicorp/aws v6.36.0 (signed by HashiCorp)

Terraform has created a lock file .terraform.lock.hcl to record the provider
selections it made above. Include this file in your version control repository
so that Terraform can guarantee to take the same selections by default when
you run "terraform init" in the future.

Terraform has been successfully initialized!
```

---

## Validar configuración

```bash
terraform validate
```

```
Success! The configuration is valid.
```

---

## Plan (resumen)

```bash
terraform plan -var-file=terraform.tfvars
```

- **14 recursos a crear:** VPC, subnets públicas/privadas, internet gateway, route table, 1 EC2 Jenkins (t3.micro, AMI ami-040855b0715ee6f0b), security groups, ASG workers (capacidad 0).
- **Outputs:** `jenkins_public_ip`, `jenkins_lb_dns`, `vpc_id`, `workers_asg_name`.

---

## Apply

```bash
terraform apply -var-file=terraform.tfvars
```

Se confirma con `yes`. Tras el apply, salida esperada incluye:

- `module.network.aws_vpc.this: Creation complete after 3s [id=vpc-06bab4c9b376d64c0]`
- `module.network.aws_internet_gateway.this: Creation complete after 1s [id=igw-0c279b85357551a91]`
- Subnets, security groups, launch template, etc.
- Al final: **Outputs** con `jenkins_public_ip` → abrir `http://<jenkins_public_ip>:8080`.

*(La sesión mostrada termina durante la creación de recursos; el apply sigue con el resto de recursos hasta completar.)*

---

## Notas

- **Terraform:** instalar con `brew install terraform` (en la sesión se usó 1.5.7; el código se ajustó a `>= 1.5.0`).
- **No subir** `terraform.tfvars` a Git.
- Para destruir: `terraform destroy -var-file=terraform.tfvars` (desde `envs/dev`).

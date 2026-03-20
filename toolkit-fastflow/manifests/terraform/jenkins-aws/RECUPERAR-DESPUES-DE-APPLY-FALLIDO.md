# Recuperar después de un apply fallido (AMI o región incorrecta)

Si ejecutaste `terraform apply` con `ami-xxxxxxxxxxxxxxxxx` o con una **región** en `terraform.tfvars` distinta a la que tenías antes, Terraform puede haber **destruido** parte de la infra (Jenkins, subredes) y **fallar** al crear la nueva (AMI inválida o VPC de otra región). El estado queda a medias.

---

## Qué pasó en tu caso

- La infra **original** estaba en **us-east-2** (Jenkins, VPC, subredes).
- En `terraform.tfvars` tenías **us-east-1** y **ami-xxxxxxxxxxxxxxxxx**.
- Terraform destruyó: Jenkins, asociaciones de route table, subredes.
- Falló al crear: nueva versión del launch template (AMI inválida), subredes y SGs en us-east-1 (la VPC del estado es de us-east-2, no existe en us-east-1).

La **VPC** (`vpc-06bab4c9b376d64c0`) sigue en el estado y en AWS en **us-east-2**. Para no liar más el estado, lo más simple es **volver a usar us-east-2** y una **AMI válida** de us-east-2, y aplicar de nuevo.

---

## Pasos para recuperar

Todo desde la carpeta **envs/dev**:

```bash
cd ../../../manifests/terraform/jenkins-aws/envs/dev
```

(o la ruta que uses para llegar a `envs/dev`).

### 1. Obtener una AMI válida en us-east-2

```bash
aws ec2 describe-images --owners amazon --region us-east-2 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

Copia el valor (ej. `ami-0abc123...`).

### 2. Ajustar terraform.tfvars

Abre `terraform.tfvars` y deja **región us-east-2** y la **misma AMI** en las cuatro variables:

```hcl
aws_region = "us-east-2"
availability_zones   = ["us-east-2a", "us-east-2b"]
# ... resto igual ...

jenkins_controller_ami_id = "ami-XXXXXXXXXXXXX"   # la que obtuviste
jenkins_worker_ami_id     = "ami-XXXXXXXXXXXXX"
gitea_ami_id              = "ami-XXXXXXXXXXXXX"
pos_ami_id                = "ami-XXXXXXXXXXXXX"
```

Sustituye `ami-XXXXXXXXXXXXX` por el ID del paso 1. **Quita** cualquier `ami-xxxxxxxxxxxxxxxxx`.

### 3. Volver a aplicar

```bash
terraform apply -var-file=terraform.tfvars
```

Terraform creará de nuevo las subredes en us-east-2 (con la VPC que sigue en estado), la instancia Jenkins, los security groups de Gitea y POS, y las instancias Gitea y POS. Si pregunta algo, confirma con `yes`.

### 4. Anotar las IPs

Al terminar el apply verás en la salida `jenkins_public_ip`, `gitea_public_ip`, `pos_public_ip`. Úsalas para acceder por SSH o al navegador (Jenkins :8080, Gitea :3000, POS :8111 una vez desplegado).

---

## Si prefieres empezar en us-east-1 desde cero

Si quieres **todo** en us-east-1 y no te importa perder el estado actual:

1. En `terraform.tfvars`: `aws_region = "us-east-1"`, `availability_zones = ["us-east-1a", "us-east-1b"]`, y una AMI válida de us-east-1 en las cuatro variables.
2. Eliminar el estado del módulo network para que Terraform cree una VPC nueva en us-east-1:
   ```bash
   terraform state rm 'module.network'
   ```
3. Luego:
   ```bash
   terraform apply -var-file=terraform.tfvars
   ```
   Fallará porque otros módulos referencian `module.network`; en ese caso suele ser más limpio hacer **terraform destroy** (si algo queda en estado), luego corregir tfvars y volver a **terraform apply** en un estado “vacío” o con el state rm de todos los módulos. Solo hazlo si te has documentado sobre state rm y quieres infra 100 % en us-east-1.

Para la mayoría de casos, **usar us-east-2 y una AMI válida** (pasos 1–4 de arriba) es la opción más segura.

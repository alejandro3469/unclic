# Instrucciones para desplegar en AWS gratis (Free Tier)

**Un solo documento** con todos los pasos para desplegar Jenkins en AWS sin coste (Free Tier). Si repartes trabajo entre agentes, pueden usar esta lista como referencia única.

---

## Requisitos previos

- **Cuenta AWS** (idealmente nueva para 12 meses Free Tier: 750 h/mes t2.micro).
- **Terraform** >= 1.6.0 instalado.
- **AWS CLI** configurado: `aws configure` (Access Key, Secret Key, región).

---

## Pasos (en orden)

### 1. Ir al directorio del Terraform

La ruta es **desde la raíz del repo** (carpeta `pipeline-as-code-with-jenkins-master`):

```bash
# Desde la raíz del repo:
cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
```

Si estás en `repo-pos-fastflow`:  
`cd ../../../manifests/terraform/jenkins-aws/envs/dev`

### 2. Crear el archivo de variables (Free Tier)

`terraform.tfvars` es donde pones tu región, IDs de AMI, etc. Terraform lo usa en `plan`/`apply`. Se crea copiando el ejemplo; **no lo subas a Git** (datos de tu cuenta).

```bash
cp terraform.tfvars.free-tier.example terraform.tfvars
```

### 3. Obtener el ID de una AMI en tu región

Ejemplo para **us-east-1** (Amazon Linux 2):

```bash
aws ec2 describe-images --owners amazon --region us-east-1 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

Copia el valor (ej. `ami-0abc123...`) y pégalo en `terraform.tfvars` en:

- `jenkins_controller_ami_id`
- `jenkins_worker_ami_id`
- `gitea_ami_id` y `pos_ami_id` (misma AMI para Gitea y POS en Free Tier)

### 4. Revisar/ajustar `terraform.tfvars`

Debe quedar algo así (con tu AMI y región):

- `aws_region` = `"us-east-1"` (o tu región)
- `availability_zones` = `["us-east-1a", "us-east-1b"]`
- `jenkins_controller_ami_id` y `jenkins_worker_ami_id` = el ID del paso 3
- `jenkins_instance_type` = `"t2.micro"` o `"t3.micro"` (Free Tier)
- `jenkins_worker_instance_type` = `"t2.micro"` o `"t3.micro"`
- `jenkins_in_public_subnet` = `true` (para acceder por IP pública)
- `workers_desired_capacity` = `0`, `workers_min_size` = `0`, `workers_max_size` = `0`
- `gitea_ami_id` y `pos_ami_id` = misma AMI del paso 3

### 5. Inicializar y aplicar Terraform

```bash
terraform init
terraform validate
terraform plan -var-file=terraform.tfvars
terraform apply -var-file=terraform.tfvars
```

Confirma con `yes` cuando lo pida.

### 6. Anotar las IPs (Jenkins, Gitea, POS)

Al finalizar el `apply`, Terraform muestra:

- **jenkins_public_ip** → `http://<ip>:8080` (instalar Jenkins y Java 17 en la instancia; ver **APLICAR-FASTFLOW.md**).
- **gitea_public_ip** → `http://<ip>:3000` (instalar Gitea en la instancia).
- **pos_public_ip** → instancia para deploy del POS (Jenkins hace deploy aquí; puerto 8111).

### 7. Destruir cuando no lo uses (evitar costes)

```bash
terraform destroy -var-file=terraform.tfvars
```

---

## Resumen rápido (copy-paste)

```bash
cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
cp terraform.tfvars.free-tier.example terraform.tfvars
# Editar terraform.tfvars: poner AMI IDs (ver paso 3)
terraform init
terraform validate
terraform plan -var-file=terraform.tfvars
terraform apply -var-file=terraform.tfvars
# Abrir http://<jenkins_public_ip>:8080
```

---

## Documentos relacionados

| Documento | Cuándo usarlo |
|-----------|----------------|
| **[INICIO-RAPIDO-AWS-GRATIS.md](INICIO-RAPIDO-AWS-GRATIS.md)** | Punto de entrada: desplegar yo / transferir repo / soy nuevo dueño. |
| **[DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md](DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md)** | Guía completa: Free Tier, transferir repo, que otro replique. |
| **[REPLICAR-AWS-NUEVO-DUENO.md](REPLICAR-AWS-NUEVO-DUENO.md)** | Si eres el nuevo dueño del repo y quieres replicar en tu AWS. |
| **[APLICAR-FASTFLOW.md](APLICAR-FASTFLOW.md)** | Init/plan/apply, backend S3, instalación de Jenkins en la instancia. |

---

## Para agentes

Si varios agentes trabajan en paralelo: usar **esta lista** como instrucciones canónicas de deploy. No duplicar pasos en otros docs; enlazar a este archivo como **instrucciones para desplegar en AWS gratis**.

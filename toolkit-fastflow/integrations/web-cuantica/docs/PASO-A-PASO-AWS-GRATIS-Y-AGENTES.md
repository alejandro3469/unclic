# Paso a paso: desplegar en AWS gratis y transferir el repo (con ayuda de agentes)

Guía única para **desplegar tú en AWS gratis (Free Tier)**, **transferir la propiedad del repo** a otra persona y que **ella pueda replicar el despliegue en AWS también gratis**. Incluye cómo usar **otros agentes** (Cursor, Codex, subagentes) para ejecutar Terraform y comprobar pasos.

---

## Empezar aquí

| Si eres… | Haz esto |
|----------|----------|
| **Dueño actual:** quieres desplegar en AWS gratis | Sigue la [Sección 1](#1-desplegar-tú-en-aws-gratis) y opcionalmente [Sección 2](#2-usar-agentes-para-ejecutar-terraform-y-comprobar) para que un agente ejecute comandos. |
| **Dueño actual:** vas a transferir el repo | Revisa [CHECKLIST-TRANSFERENCIA-REPO.md](CHECKLIST-TRANSFERENCIA-REPO.md) y la [Sección 3](#3-preparar-transferencia-y-transferir-repo). |
| **Nuevo dueño:** te han transferido el repo | Ve a [Sección 4](#4-replicar-en-aws-quien-recibe-el-repo) o abre **toolkit-fastflow/manifests/terraform/jenkins-aws/REPLICAR-AWS-NUEVO-DUENO.md**. |

---

## 1. Desplegar tú en AWS (gratis)

Todo el Terraform está en **toolkit-fastflow/manifests/terraform/jenkins-aws/**.

### 1.1 Requisitos

- **Cuenta AWS** (nueva = 12 meses Free Tier).
- **Terraform** ≥ 1.6 y **AWS CLI** configurado: `aws configure` (Access Key, Secret, región p. ej. `us-east-1`).

### 1.2 Comandos (resumen)

Desde la **raíz del repo** (o desde tu máquina con el repo clonado):

```bash
cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
cp terraform.tfvars.free-tier.example terraform.tfvars
```

Obtener AMI (Amazon Linux 2) en tu región, por ejemplo `us-east-1`:

```bash
aws ec2 describe-images --owners amazon --region us-east-1 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

Pega el ID (ej. `ami-0abc123...`) en `terraform.tfvars` en **jenkins_controller_ami_id** y **jenkins_worker_ami_id**.

Luego:

```bash
terraform init
terraform validate
terraform plan -var-file=terraform.tfvars
terraform apply -var-file=terraform.tfvars
```

Confirma con `yes`. Al final Terraform mostrará **jenkins_public_ip**. Acceso:

- **URL:** `http://<jenkins_public_ip>:8080`  
La instancia viene sin Jenkins; hay que instalarlo por SSH (ver [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md](DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md) sección 5).

### 1.3 Destruir (evitar costes)

```bash
cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
terraform destroy -var-file=terraform.tfvars
```

---

## 2. Usar agentes para ejecutar Terraform y comprobar

Puedes pedir a **otros agentes** (Cursor, Codex, o un subagente de tipo “shell”/“generalPurpose”) que ejecuten los pasos por ti. Así se reparten tareas: uno hace init/plan/apply, otro revisa docs o el checklist.

### 2.1 Qué puede hacer un agente por ti

| Tarea | Comando / acción que el agente puede ejecutar |
|-------|------------------------------------------------|
| Ir al directorio correcto | `cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev` (o ruta absoluta). |
| Copiar el ejemplo Free Tier | `cp terraform.tfvars.free-tier.example terraform.tfvars` (solo si no existe ya). |
| Obtener AMI en tu región | `aws ec2 describe-images --owners amazon --region us-east-1 --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" --query 'Images \| sort_by(@, &CreationDate) \| [-1].ImageId' --output text` |
| Terraform init | `terraform init` |
| Terraform validate | `terraform validate` |
| Terraform plan | `terraform plan -var-file=terraform.tfvars` |
| Terraform apply | `terraform apply -var-file=terraform.tfvars` (necesita confirmación; en modo no interactivo usar `-auto-approve` con cuidado). |
| Terraform destroy | `terraform destroy -var-file=terraform.tfvars` |

**Importante:**  
- **terraform.tfvars** no debe subirse a Git (contiene tu región/AMIs; no poner credenciales ahí).  
- Las **credenciales AWS** deben estar en el entorno (p. ej. `aws configure` ya hecho) para que `aws` y `terraform` funcionen en la máquina donde corre el agente.

### 2.2 Cómo pedir ayuda a un agente

Puedes decir algo como:

- *“Ejecuta en el repo: cd a toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev, copia terraform.tfvars.free-tier.example a terraform.tfvars, obtén la última AMI de Amazon Linux 2 en us-east-1 y dime el ID para que lo ponga en terraform.tfvars.”*
- *“En toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev ejecuta terraform init y terraform validate y dime si hay errores.”*
- *“Haz terraform plan -var-file=terraform.tfvars en envs/dev y resume los recursos que se van a crear.”*

Para **apply** y **destroy**, conviene que **tú** des la orden final (o que el agente use `-auto-approve` solo si lo autorizas explícitamente), porque crean o borran recursos en tu cuenta AWS.

### 2.3 Repartir trabajo entre varios agentes

- **Agente A (shell/terminal):** ejecutar `terraform init`, `validate`, `plan`; opcionalmente `apply` si tú lo confirmas.
- **Agente B (docs):** revisar que CHECKLIST-TRANSFERENCIA-REPO.md y DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md estén actualizados; que el README enlace a “Replicar en AWS”.
- **Agente C (explore):** comprobar que en el repo existan `terraform.tfvars.free-tier.example`, que no haya `terraform.tfvars` en Git y que las rutas de esta guía existan.

Así despliegas en AWS gratis y dejas el repo listo para que otro lo replique.

---

## 3. Preparar transferencia y transferir repo

Antes de transferir la propiedad del repo a otra persona:

1. **No subas secretos:** ni `terraform.tfvars`, ni `.env` con claves, ni Access Keys. Solo archivos `.example`.
2. **Documentación en el repo:** esta guía, [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md](DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md) y [CHECKLIST-TRANSFERENCIA-REPO.md](CHECKLIST-TRANSFERENCIA-REPO.md).
3. **README o índice:** enlace a “Replicar en AWS” (por ejemplo a **REPLICAR-AWS-NUEVO-DUENO.md** en `toolkit-fastflow/manifests/terraform/jenkins-aws/`).
4. **Checklist:** completar [CHECKLIST-TRANSFERENCIA-REPO.md](CHECKLIST-TRANSFERENCIA-REPO.md) (dueño actual).

Transferencia:

- **GitHub:** Settings → General → Danger Zone → Transfer ownership.  
- **GitLab:** Settings → General → Advanced → Transfer project.

---

## 4. Replicar en AWS (quien recibe el repo)

La persona que reciba el repo **no necesita tu cuenta AWS**; solo el repo y su propia cuenta (también puede usar Free Tier).

1. Clonar o aceptar la transferencia del repo.
2. Instalar Terraform ≥ 1.6 y AWS CLI; configurar `aws configure` con su cuenta.
3. Seguir los mismos pasos que en la [Sección 1](#1-desplegar-tú-en-aws-gratis):
   - `cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev`
   - `cp terraform.tfvars.free-tier.example terraform.tfvars`
   - Obtener AMI en **su** región y rellenar `jenkins_controller_ami_id` y `jenkins_worker_ami_id` en **su** `terraform.tfvars`.
   - `terraform init` → `terraform plan -var-file=terraform.tfvars` → `terraform apply -var-file=terraform.tfvars`
4. Usar la salida **jenkins_public_ip** → `http://<IP>:8080`. Instalar Jenkins por SSH si la AMI no lo trae (ver [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md](DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md) sección 5).

Guía corta para el nuevo dueño: **toolkit-fastflow/manifests/terraform/jenkins-aws/REPLICAR-AWS-NUEVO-DUENO.md**.

---

## 5. Referencias rápidas

| Documento | Dónde | Para qué |
|-----------|--------|----------|
| **DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md** | `docs/` | Guía completa Free Tier, transferencia y réplica. |
| **CHECKLIST-TRANSFERENCIA-REPO.md** | `docs/` | Checklist dueño actual y nuevo dueño. |
| **REPLICAR-AWS-NUEVO-DUENO.md** | `manifests/terraform/jenkins-aws/` | Guía corta para quien recibe el repo. |
| **APLICAR-FASTFLOW.md** | `manifests/terraform/jenkins-aws/` | Init/plan/apply, AMI, backend S3 opcional. |
| **terraform.tfvars.free-tier.example** | `manifests/terraform/jenkins-aws/envs/dev/` | Variables para 1 EC2, 0 workers, Free Tier. |

---

*Con esta guía puedes desplegarte en AWS gratis, usar agentes para ejecutar Terraform o revisar docs, y dejar el repo listo para transferir y que otra persona replique el despliegue en su cuenta AWS también gratis.*

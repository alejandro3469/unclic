# Desplegar en AWS gratis y transferir el repo a otra persona (replicable)

Guía para **desplegar el pipeline (Jenkins + opcional app) en AWS dentro del Free Tier** y, después, **transferir la propiedad del repositorio** a otra persona para que pueda **replicar el despliegue en AWS también gratis**.

---

## 1. Qué obtienes (y qué paga AWS Free Tier)

- **AWS Free Tier (12 meses):** 750 h/mes de EC2 (equivale a 1 instancia `t2.micro` o `t3.micro` 24/7), 30 GB EBS, ancho de banda limitado. Tras el primer año, el mismo diseño puede seguir siendo barato (1 instancia pequeña).
- **Objetivo:** Una sola EC2 con Jenkins (y opcionalmente Docker/Registry) accesible por IP pública para demo, sin bastión. La siguiente persona que reciba el repo podrá repetir los pasos con su propia cuenta AWS.

---

## 2. Antes de desplegar: preparar el repo para transferencia

Para que quien reciba el repo pueda replicar todo sin depender de ti:

| Paso | Acción |
|------|--------|
| 1 | **No guardar secretos en el repo.** No subas `terraform.tfvars` (solo `terraform.tfvars.example` y `terraform.tfvars.free-tier.example`). Credenciales AWS, tokens, contraseñas: variables de entorno o gestor de secretos. |
| 2 | **Documentar en el repo** qué se despliega y cómo. Esta guía y las referencias (APLICAR-FASTFLOW.md, README del Terraform) deben estar en el repo. |
| 3 | **Dejar claro el “primer uso”.** En el README o en esta guía: “Si te han transferido este repo, empieza por la sección 4 (Replicar en AWS)”. |
| 4 | **Opcional:** Crear un `docs/CHECKLIST-TRANSFERENCIA-REPO.md` con: qué cuentas/servicios creó el dueño original (solo AWS, ¿Jenkins?, ¿Registry?) y qué debe crear el nuevo dueño (cuenta AWS, mismo Terraform, etc.). |

Así, al transferir el repo (GitHub/GitLab “transfer ownership” o fork + cambio de remoto), la otra persona tiene un solo lugar donde leer los pasos.

---

## 3. Desplegar tú en AWS (gratis / Free Tier)

Tienes dos opciones: **Terraform (recomendado)** o **una sola EC2 a mano**.

### 3.1 Opción A: Terraform (Jenkins en 1 EC2, Free Tier)

Ruta recomendada: Terraform con variables de Free Tier (1 EC2 en subred pública, 0 workers).

1. **Requisitos:** [Terraform](https://developer.hashicorp.com/terraform/downloads) ≥ 1.6, [AWS CLI](https://aws.amazon.com/cli/) configurado (`aws configure` con Access Key y región).

2. **Ir al entorno y copiar variables Free Tier:**
   ```bash
   cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
   cp terraform.tfvars.free-tier.example terraform.tfvars
   ```

3. **Obtener una AMI (Amazon Linux 2) en tu región:**
   ```bash
   aws ec2 describe-images --owners amazon --region us-east-1 \
     --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
     --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
   ```
   Pega el ID (ej. `ami-0abc123...`) en `terraform.tfvars` en `jenkins_controller_ami_id` y `jenkins_worker_ami_id`.

4. **Aplicar:**
   ```bash
   terraform init
   terraform validate
   terraform plan -var-file=terraform.tfvars
   terraform apply -var-file=terraform.tfvars
   ```

5. **Salidas:** Tras `apply`, Terraform mostrará `jenkins_public_ip`. Abre en el navegador:
   - `http://<jenkins_public_ip>:8080`
   La instancia viene “en blanco”: tendrás que instalar Jenkins (y Java/Docker si quieres) por SSH. Ver **Sección 5** para un script de bootstrap opcional.

6. **Interfaz central:** Abre **deploy/control-center-flujo-aws.html** (desde el repo, en un navegador). En “URLs (local o AWS)” pega la IP pública en Jenkins y en App (ej. `http://<IP>:8080` y `http://<IP>:8111` si el POS corre en la misma EC2). Desde esa página verás el flujo completo y enlaces a Jenkins, POS y health; la única parte manual es hacer el commit.

6. **Destruir cuando no lo uses:**
   ```bash
   terraform destroy -var-file=terraform.tfvars
   ```

Referencia detallada: **toolkit-fastflow/manifests/terraform/jenkins-aws/APLICAR-FASTFLOW.md**.

### 3.2 Opción B: Una sola EC2 a mano (sin Terraform)

Si prefieres no usar Terraform la primera vez:

1. En AWS Console: EC2 → Launch Instance.
2. Nombre: `jenkins-demo`. AMI: Amazon Linux 2. Tipo: **t2.micro** o **t3.micro**.
3. Crear par de llaves (ej. `jenkins-demo.pem`) y descargar.
4. Red: default VPC o una VPC con subred pública. Asignar IP pública.
5. Security group: permitir **22 (SSH)** y **8080 (Jenkins)** desde tu IP o `0.0.0.0/0` (solo demo).
6. Lanzar, esperar y conectar por SSH:
   ```bash
   ssh -i jenkins-demo.pem ec2-user@<IP_PUBLICA>
   ```
7. Instalar Jenkins (y Java) según la [documentación oficial](https://www.jenkins.io/doc/book/installing/linux/) o el script de la **Sección 5**.

Ventaja: muy rápido. Desventaja: no queda “como código”; quien reciba el repo tendrá que repetir pasos manuales o usar Terraform.

---

## 4. Replicar en AWS (quien recibe el repo)

Objetivo: **que la persona a la que transfieras el repo pueda desplegar en su propia cuenta AWS, también gratis.**

1. **Recibir el repo:** Transferencia de ownership (GitHub/GitLab) o clonar desde el repo que tú indiques.
2. **Requisitos en su máquina:** Terraform ≥ 1.6, AWS CLI. Cuenta AWS (puede ser nueva; Free Tier aplica 12 meses).
3. **Configurar AWS:**  
   `aws configure` con su Access Key ID, Secret y región (ej. `us-east-1`).
4. **Seguir la misma ruta Terraform (Free Tier):**
   - `cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev`
   - `cp terraform.tfvars.free-tier.example terraform.tfvars`
   - Obtener AMI en su región (comando de la sección 3.1) y rellenar `jenkins_controller_ami_id` y `jenkins_worker_ami_id`.
   - `terraform init` → `terraform plan -var-file=terraform.tfvars` → `terraform apply -var-file=terraform.tfvars`
5. **Acceso:** Usar la salida `jenkins_public_ip` → `http://<IP>:8080`. Instalar Jenkins por SSH si la AMI no lo trae (Sección 5).

No necesitan tus credenciales ni tu cuenta AWS; solo el repo y su propia cuenta.

---

## 5. Instalar Jenkins en la EC2 (después del Terraform o EC2 manual)

La AMI estándar no trae Jenkins. Opciones:

- **Manual:** Conectar por SSH y seguir [Installing Jenkins on Linux](https://www.jenkins.io/doc/book/installing/linux/).
- **User-data (opcional):** Puedes añadir un script en el `user_data` del recurso `aws_instance` (Terraform) para instalar Java y Jenkins al arranque. Ejemplo mínimo (Amazon Linux 2):
  ```bash
  #!/bin/bash
  sudo yum update -y
  sudo yum install -y java-11-amazon-corretto
  sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
  sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
  sudo yum install -y jenkins
  sudo systemctl enable jenkins && sudo systemctl start jenkins
  ```
  (Ajusta versiones/repos según la doc oficial.)

Tras la instalación, el acceso sigue siendo `http://<jenkins_public_ip>:8080`.

---

## 6. Resumen para transferir el repo

| Quién | Qué hace |
|-------|----------|
| **Tú (dueño actual)** | Desplegas en AWS (Terraform Free Tier o 1 EC2 manual). Documentas en el repo (esta guía, no subir `terraform.tfvars`). Opcional: CHECKLIST-TRANSFERENCIA-REPO.md. Transfieres el repo (ownership o indicas “clonar desde aquí”). |
| **Nuevo dueño** | Clona/recibe el repo. Configura su AWS CLI. Sigue la **Sección 4** (mismo Terraform Free Tier). Obtiene su propia `jenkins_public_ip` y opcionalmente instala Jenkins (Sección 5). Replicación también gratis dentro del Free Tier. |

---

## 7. Referencias en el repo

| Documento | Contenido |
|-----------|-----------|
| **toolkit-fastflow/manifests/terraform/jenkins-aws/README.md** | Estructura del Terraform (red, compute, autoscaling). |
| **toolkit-fastflow/manifests/terraform/jenkins-aws/APLICAR-FASTFLOW.md** | Pasos detallados init/plan/apply, AMI, backend S3 opcional. |
| **toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev/terraform.tfvars.example** | Variables “completas” (con workers). |
| **toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev/terraform.tfvars.free-tier.example** | Variables para 1 EC2, subred pública, 0 workers (Free Tier). |
| **docs/pipeline-y-registry/REQUIREMENTS-DEMO-AWS-JENKINS.md** | Cómo encaja la demo AWS con Jenkins, Registry, K8s, Terraform. |

---

*Con esta guía puedes desplegar en AWS gratis (Free Tier) y dejar el repo listo para que otra persona replique el mismo despliegue en su cuenta, también gratis.*

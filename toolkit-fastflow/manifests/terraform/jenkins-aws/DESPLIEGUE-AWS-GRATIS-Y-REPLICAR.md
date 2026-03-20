# Desplegar en AWS gratis (Free Tier) y replicar tras transferir el repo

Guía para **desplegar tú mismo** Jenkins (FastFlow) en AWS dentro del **Free Tier**, **transferir la propiedad del repo** a otra persona y que **esa persona pueda replicar el mismo despliegue en su cuenta AWS también gratis**.

---

## 1. Qué incluye el Free Tier y límites

- **EC2:** 750 horas/mes de `t2.micro` (o `t3.micro` donde aplique) durante los primeros 12 meses desde la creación de la cuenta.
- **Una sola instancia** `t2.micro` encendida 24×7 cabe en ese límite.
- **EBS:** 30 GB de almacenamiento tipo gp2 (o equivalente) en free tier.
- **Datos:** Revisa [AWS Free Tier](https://aws.amazon.com/free/) y [Uso de Free Tier EC2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-free-tier-usage.html). La elegibilidad depende de la fecha de creación de la cuenta.

Para **mantener coste ~0 €** en esta guía:

- Usar **1 instancia** tipo **t2.micro** (o t3.micro si tu región/cuenta lo permite en free tier).
- **0 workers** de Jenkins (ASG con desired/min/max = 0).
- Opcional: **Jenkins en subred pública** con IP pública para acceder sin bastión (solo recomendable para demo/pruebas).

---

## 2. Desplegar tú mismo (gratis) en AWS

### 2.1 Requisitos

- Cuenta AWS (nueva para aprovechar 12 meses free tier).
- **Terraform** >= 1.6.0 y **AWS CLI** configurado (`aws configure`).
- Repo clonado (este repo o el que vayas a transferir).

### 2.2 Pasos

1. **Ir al entorno dev del Terraform:**

   ```bash
   cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
   ```

2. **Crear `terraform.tfvars`** (no subirlo a git):

   ```bash
   cp terraform.tfvars.free-tier.example terraform.tfvars
   ```

3. **Obtener una AMI** (Amazon Linux 2 en `us-east-1`):

   ```bash
   aws ec2 describe-images --owners amazon --region us-east-1 \
     --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
     --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
   ```

   Usa ese ID en `jenkins_controller_ami_id` y `jenkins_worker_ami_id` (aunque no levantes workers, el módulo los referencia).

4. **Configurar variables para Free Tier** en `terraform.tfvars`:

   - `aws_region` = `"us-east-1"` (o tu región)
   - `availability_zones` = p. ej. `["us-east-1a", "us-east-1b"]`
   - `jenkins_controller_ami_id` y `jenkins_worker_ami_id` = el ID de la AMI anterior
   - `jenkins_instance_type` = `"t2.micro"`
   - `jenkins_worker_instance_type` = `"t2.micro"`
   - `jenkins_in_public_subnet` = `true` (para acceder a Jenkins por IP pública sin bastión)
   - `workers_desired_capacity` = `0`
   - `workers_min_size` = `0`
   - `workers_max_size` = `0`

5. **Aplicar:**

   ```bash
   terraform init
   terraform validate
   terraform plan -var-file=terraform.tfvars
   terraform apply -var-file=terraform.tfvars
   ```

6. **Acceso a Jenkins:** Tras `apply`, Terraform muestra `jenkins_public_ip`. Abre en el navegador:

   ```text
   http://<jenkins_public_ip>:8080
   ```

   En la instancia tendrás que instalar Jenkins (y Java 17) según la documentación del producto o **APLICAR-FASTFLOW.md**. Esta guía se centra en dejar la infraestructura lista y el repo listo para que otro lo replique.

### 2.3 Destruir (evitar costes al dejar de usar)

```bash
terraform destroy -var-file=terraform.tfvars
```

---

## 3. Transferir la propiedad del repo a otra persona

- **GitHub:** Settings → General → Danger Zone → Transfer ownership.  
- **GitLab:** Settings → General → Advanced → Transfer project.  
- **Otros:** Revisa la opción “Transfer ownership” o “Transfer repository” en la configuración del repo.

Antes de transferir, deja en el repo (y en la rama que el nuevo dueño vaya a usar):

1. Esta guía o un enlace claro a ella (por ejemplo en el **README** del repo o en `docs/`).
2. La guía corta **REPLICAR-AWS-NUEVO-DUENO.md** (mismo directorio que este archivo), para que el nuevo propietario sepa por dónde empezar.

Así el nuevo dueño no depende de este chat ni de información externa.

---

## 4. Cómo puede el nuevo dueño replicar el despliegue en AWS (gratis)

El nuevo propietario del repo debe seguir la guía **REPLICAR-AWS-NUEVO-DUENO.md**, que resume:

1. Clonar el repo (ya en su propiedad).
2. Tener cuenta AWS (idealmente en free tier si quiere coste 0).
3. Seguir los mismos pasos que en la sección 2 de este documento (Terraform en `envs/dev`, mismo `terraform.tfvars` de free tier, `terraform apply`).
4. Verificar acceso a Jenkins por `http://<jenkins_public_ip>:8080`.

Cada persona usa **su propia cuenta AWS** y su propio `terraform.tfvars` (con sus AMIs y región); no comparten infraestructura, solo el mismo código y documentación.

---

## 5. Coordinación con otros agentes

Si usas **varios agentes** (Cursor, Codex, etc.) en paralelo:

- **Un agente** puede encargarse de la documentación (esta guía, REPLICAR-AWS-NUEVO-DUENO, README).
- **Otro** puede revisar o ajustar Terraform (variables free tier, outputs).
- Evitar que dos agentes modifiquen a la vez `terraform.tfvars.example` o los mismos módulos; coordinar según **COORDINACION-AGENTES-FASTFLOW.md** (en `integrations/web-cuantica/docs/`).

---

## 6. Referencias en el repo

| Documento | Ubicación | Uso |
|-----------|-----------|-----|
| **APLICAR-FASTFLOW.md** | Este directorio | Pasos Terraform init/plan/apply y obtención de AMI |
| **README.md** | Este directorio | Estructura de módulos (network, compute, autoscaling) |
| **terraform.tfvars.example** | `envs/dev/` | Ejemplo con comentarios para free tier |
| **REPLICAR-AWS-NUEVO-DUENO.md** | Este directorio | Guía corta para el nuevo dueño del repo |
| **REQUIREMENTS-DEMO-AWS-JENKINS.md** | `integrations/web-cuantica/docs/pipeline-y-registry/` | Requerimientos de la demo (Jenkins, Docker, Registry, K8s) |

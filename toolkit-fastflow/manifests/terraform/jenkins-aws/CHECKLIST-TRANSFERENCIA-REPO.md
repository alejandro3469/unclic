# Checklist: transferir el repo y que otro replique en AWS gratis

Lista de **qué hace el dueño actual** y **qué debe hacer el nuevo dueño** para desplegar en AWS Free Tier y replicar el despliegue sin depender de tu cuenta ni de tus credenciales.

---

## Dueño actual (tú): antes de transferir

| # | Acción | Hecho |
|---|--------|-------|
| 1 | Desplegar en AWS (opcional): seguir **DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md** → sección 2 (Terraform en `envs/dev`, `terraform.tfvars.free-tier.example` → `terraform.tfvars`, `terraform apply`). | ☐ |
| 2 | **No subir** `terraform.tfvars` ni credenciales AWS al repo. Dejar solo `terraform.tfvars.example` y `terraform.tfvars.free-tier.example`. | ☐ |
| 3 | En el README del repo (o en **docs/**) dejar un enlace claro: *"Si te han transferido este repo y quieres desplegar en AWS gratis, empieza por **REPLICAR-AWS-NUEVO-DUENO.md**"* (ruta: `toolkit-fastflow/manifests/terraform/jenkins-aws/`). | ☐ |
| 4 | Asegurar que en el repo están estos archivos: **DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md**, **REPLICAR-AWS-NUEVO-DUENO.md**, **APLICAR-FASTFLOW.md**, **terraform.tfvars.free-tier.example** (en `envs/dev/`). | ☐ |
| 5 | Transferir el repo (GitHub: Settings → Danger Zone → Transfer; GitLab: Settings → Transfer project). | ☐ |

**Servicios que creaste tú (y que el nuevo dueño no usa):** tu cuenta AWS, tu EC2/Jenkins si desplegaste. El nuevo dueño usará **su propia cuenta AWS**.

---

## Nuevo dueño: después de recibir el repo

| # | Acción | Hecho |
|---|--------|-------|
| 1 | Clonar o aceptar el repo (ya en su propiedad). | ☐ |
| 2 | Crear cuenta AWS si no tiene (12 meses Free Tier si es nueva). | ☐ |
| 3 | Instalar **Terraform** ≥ 1.6 y **AWS CLI**; configurar `aws configure` con sus propias credenciales. | ☐ |
| 4 | Seguir **REPLICAR-AWS-NUEVO-DUENO.md** (mismo directorio que este checklist): `cd envs/dev`, `cp terraform.tfvars.free-tier.example terraform.tfvars`, obtener AMI, editar vars, `terraform init` → `plan` → `apply`. | ☐ |
| 5 | Anotar la **jenkins_public_ip** que muestra Terraform; abrir `http://<jenkins_public_ip>:8080`. Instalar Jenkins/Java en la instancia si hace falta (ver **APLICAR-FASTFLOW.md**). | ☐ |

**No necesita:** tus credenciales AWS, tu cuenta, ni acceso a tu infraestructura. Solo el código y la documentación del repo.

---

## Resumen

| Rol | Documento principal | Resultado |
|-----|---------------------|-----------|
| **Dueño actual** | DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md (sección 2) | Tu Jenkins en AWS Free Tier (opcional). Repo listo para transferir. |
| **Dueño actual** | Este checklist | No subir secretos; enlazar REPLICAR-AWS-NUEVO-DUENO en README; transferir. |
| **Nuevo dueño** | REPLICAR-AWS-NUEVO-DUENO.md | Su propia EC2 + Jenkins en su cuenta AWS, también gratis (Free Tier). |

---

## Ubicación de los archivos

- **Este checklist:** `toolkit-fastflow/manifests/terraform/jenkins-aws/CHECKLIST-TRANSFERENCIA-REPO.md`
- **Guía completa (desplegar + transferir + replicar):** `DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md` (mismo directorio)
- **Guía corta nuevo dueño:** `REPLICAR-AWS-NUEVO-DUENO.md` (mismo directorio)
- **Variables Free Tier:** `envs/dev/terraform.tfvars.free-tier.example` → copiar a `envs/dev/terraform.tfvars` (no subir)

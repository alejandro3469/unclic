# Empieza aquí: desplegar gratis en AWS y transferir el repo (con agentes)

**Objetivo:** Desplegar **tú** el pipeline (Jenkins) en **AWS gratis** (Free Tier) y, después, **transferir la propiedad del repo** a otra persona para que pueda **replicar el mismo despliegue en su cuenta AWS también gratis**. Puedes usar **otros agentes** (Cursor, Codex, subagentes) para que te ayuden a ejecutar Terraform y revisar pasos.

**Mapa del módulo web-cuantica + laboratorio completo (Jenkins/Gitea/POS):** [EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md](EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md).

---

## ¿Qué quieres hacer?

| Situación | Sigue este enlace |
|-----------|--------------------|
| **Quiero desplegar yo en AWS gratis (y que un agente me ayude)** | → **[../20-operaciones/PASO-A-PASO-AWS-GRATIS-Y-AGENTES.md](../20-operaciones/PASO-A-PASO-AWS-GRATIS-Y-AGENTES.md)** — Sección 1 (desplegar) y Sección 2 (usar agentes para Terraform). |
| **Voy a transferir el repo a otra persona** | → [../20-operaciones/CHECKLIST-TRANSFERENCIA-REPO.md](../20-operaciones/CHECKLIST-TRANSFERENCIA-REPO.md) y [CHECKLIST en jenkins-aws](../../../../manifests/terraform/jenkins-aws/CHECKLIST-TRANSFERENCIA-REPO.md). |
| **Soy el nuevo dueño del repo y quiero replicar en mi AWS** | → [REPLICAR-AWS-NUEVO-DUENO.md](../../../../manifests/terraform/jenkins-aws/REPLICAR-AWS-NUEVO-DUENO.md) — Guía corta en `toolkit-fastflow/manifests/terraform/jenkins-aws/`. |

---

## Punto de entrada Terraform (mismo contenido, otra ruta)

Si prefieres entrar por la carpeta del Terraform:

- [INICIO-RAPIDO-AWS-GRATIS.md](../../../../manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md) — En `toolkit-fastflow/manifests/terraform/jenkins-aws/`: desplegar yo / transferir / soy nuevo dueño.

---

## Qué pedirle a un agente (copy-paste)

Puedes pegar algo así en otro agente (Cursor, Codex, etc.) para que te ayude:

```
Necesito desplegar en AWS gratis (Free Tier) el Terraform de Jenkins que está en este repo.

1) Ir a: toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
2) Copiar terraform.tfvars.free-tier.example a terraform.tfvars (si no existe)
3) Obtener la última AMI de Amazon Linux 2 en us-east-1 con:
   aws ec2 describe-images --owners amazon --region us-east-1 \
     --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
     --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
4) Decirme el ID de la AMI para que yo lo ponga en terraform.tfvars en jenkins_controller_ami_id y jenkins_worker_ami_id
5) Ejecutar: terraform init y terraform validate y decirme si hay errores.
6) Opcional: terraform plan -var-file=terraform.tfvars y resumir qué recursos se crearán.

No ejecutes terraform apply ni destroy sin que yo lo confirme. El archivo terraform.tfvars no debe subirse a Git.
```

Para **revisar documentación** (dueño actual / nuevo dueño):

```
Revisa que en el repo existan y estén enlazados:
- toolkit-fastflow/manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md
- toolkit-fastflow/manifests/terraform/jenkins-aws/DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md
- toolkit-fastflow/manifests/terraform/jenkins-aws/REPLICAR-AWS-NUEVO-DUENO.md
- toolkit-fastflow/manifests/terraform/jenkins-aws/CHECKLIST-TRANSFERENCIA-REPO.md
- toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev/terraform.tfvars.free-tier.example

Y que en el README principal del repo (o en docs/) haya un enlace claro tipo: "Si te han transferido este repo y quieres desplegar en AWS gratis, empieza por REPLICAR-AWS-NUEVO-DUENO.md".
```

---

## Interfaz central (centro de mando del flujo)

Una vez desplegado en AWS (o en local), **toda la operación del flujo** se hace desde una sola interfaz:

- **Abrir (desde carpeta `web-cuantica`):** [../../deploy/interfaz-central-flujo-aws.html](../../deploy/interfaz-central-flujo-aws.html) — o, desde la raíz del módulo `web-cuantica`, `bash scripts/start-flujo-pos-jenkins.sh` (ver [../../scripts/start-flujo-pos-jenkins.sh](../../scripts/start-flujo-pos-jenkins.sh)).
- **Orden:** 1) Arrancar POS → 2) Jenkins (en AWS ya está en la EC2) → 3) Esta interfaz.
- **Única parte manual:** hacer el **commit** (y push) en el repo pos-online; el resto (Jenkins, build, registry, app) se **observa** desde la interfaz.
- Configura en la misma página las URLs de Jenkins (ej. `http://<IP_EC2>:8080`) y de la app para el deploy gratuito en AWS.

---

## Flujo en tres pasos

1. **Tú (dueño actual):**  
   - Despliegas en AWS con Terraform Free Tier (1 EC2, 0 workers).  
   - Dejas el repo listo: sin `terraform.tfvars` ni secretos en Git; con docs y checklist.  
   - Transfieres el repo (GitHub/GitLab: Transfer ownership).

2. **Nuevo dueño:**  
   - Acepta la transferencia o clona el repo.  
   - Sigue **REPLICAR-AWS-NUEVO-DUENO.md**: crea su `terraform.tfvars` desde el `.free-tier.example`, pone su AMI y su región, y ejecuta `terraform init` → `plan` → `apply` en **su** cuenta AWS.  
   - Obtiene su propia IP y Jenkins en su cuenta, también en Free Tier.

3. **Agentes:**  
   - Pueden ejecutar por ti: `terraform init`, `validate`, `plan`; obtener AMI; revisar que los docs existan.  
   - **apply** y **destroy** es mejor que los confirmes tú (crean/borran recursos en tu cuenta).

---

## Documentos de referencia

| Documento | Dónde | Para qué |
|-----------|--------|----------|
| **PASO-A-PASO-AWS-GRATIS-Y-AGENTES.md** | [docs/20-operaciones/](../20-operaciones/) | Desplegar tú, usar agentes, preparar transferencia, replicar (nuevo dueño). |
| **DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md** | [docs/20-operaciones/](../20-operaciones/) | Guía completa Free Tier + transferencia + réplica. |
| **CHECKLIST-TRANSFERENCIA-REPO.md** | [docs/20-operaciones/](../20-operaciones/) y `manifests/terraform/jenkins-aws/` | Dueño actual: qué hacer antes de transferir. Nuevo dueño: qué hacer al recibir. |
| **INICIO-RAPIDO-AWS-GRATIS.md** | manifests/terraform/jenkins-aws/ | Punto de entrada por rol (desplegar / transferir / nuevo dueño). |
| **REPLICAR-AWS-NUEVO-DUENO.md** | manifests/terraform/jenkins-aws/ | Guía corta para quien recibe el repo y quiere su Jenkins en AWS gratis. |
| **terraform.tfvars.free-tier.example** | manifests/terraform/jenkins-aws/envs/dev/ | Variables para 1 EC2, 0 workers; copiar a `terraform.tfvars` (no subir). |

---

*Con esta página y los enlaces puedes desplegarte en AWS gratis, usar agentes para ejecutar Terraform o revisar docs, y transferir el repo para que otra persona replique el despliegue en su cuenta AWS también gratis.*

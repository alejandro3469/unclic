# Guía paso a paso: deploy en AWS con Terraform (para principiantes)

Esta guía explica **qué hace cada paso**, **por qué** y **qué obtienes al final**, con lenguaje sencillo.

---

## ¿Qué vamos a hacer?

Vamos a decirle a **Terraform** que cree en tu cuenta de **AWS**:

- Una **red (VPC)** con subredes.
- **Cuatro servidores (EC2):**
  1. **Jenkins** — para automatizar builds y pipelines.
  2. **Gitea** — para guardar el código (repositorios Git).
  3. **POS** — donde se desplegará la aplicación POS después del pipeline.
  4. *(Opcional)* **Registry** — para guardar imágenes Docker (por defecto no se crea).

Terraform **no instala** Jenkins ni Gitea dentro de las máquinas; solo **crea las máquinas vacías** en AWS. Después tú (o otro documento) instalas el software en cada una por SSH.

---

## Requisitos antes de empezar

- **Cuenta AWS** (y si es nueva, puedes usar Free Tier).
- **Terraform** instalado en tu ordenador (versión >= 1.5).
- **AWS CLI** configurado: `aws configure` (Access Key, Secret Key, región).

---

## Importante: ¿Desde qué carpeta ejecuto los comandos?

Los comandos de Terraform **solo funcionan** si estás dentro de la carpeta del entorno **dev**:

- Ruta completa de esa carpeta (desde la raíz del repo):  
  `toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev`

**Si estás en otra carpeta** (por ejemplo dentro de `repo-pos-fastflow`), el `cd` que pongas tiene que ser **relativo a donde estás**:

| Estoy en esta carpeta | Comando para llegar a dev |
|------------------------|---------------------------|
| Raíz del repo (`pipeline-as-code-with-jenkins-master`) | `cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev` |
| `repo-pos-fastflow` | `cd ../../../manifests/terraform/jenkins-aws/envs/dev` |
| `unclic` | `cd ../../../manifests/terraform/jenkins-aws/envs/dev` |

Si usas una ruta que no existe (por ejemplo `cd toolkit-fastflow/...` desde dentro de `repo-pos-fastflow`), verás **"No such file or directory"** y los comandos siguientes fallarán.

---

## Paso 1: Ir a la carpeta correcta

**Qué hacemos:** Entrar en la carpeta donde está el Terraform del entorno **dev**.

**Por qué:** Terraform lee los archivos `.tf` y `terraform.tfvars` de la carpeta actual. Si no estás en `envs/dev`, no encuentra la configuración.

**Comando (desde repo-pos-fastflow o unclic):**
```bash
cd ../../../manifests/terraform/jenkins-aws/envs/dev
```

**Comprobar:** Escribe `pwd`. Debe terminar en `.../jenkins-aws/envs/dev`. Lista archivos con `ls` y deberías ver `main.tf`, `variables.tf`, `terraform.tfvars.free-tier.example`, etc.

---

## Paso 2: Crear tu archivo de variables

**Qué hacemos:** Copiar el archivo de ejemplo `terraform.tfvars.free-tier.example` a un archivo llamado `terraform.tfvars`.

**Por qué:** En `terraform.tfvars` pones **tus** valores: región AWS, IDs de AMI (la “imagen” del sistema operativo de cada servidor), etc. Terraform usa este archivo en `plan` y `apply`. El archivo de ejemplo tiene valores de Free Tier (pocas máquinas, tipo t3.micro). **No subas `terraform.tfvars` a Git** (lleva datos de tu cuenta).

**Comando:**
```bash
cp terraform.tfvars.free-tier.example terraform.tfvars
```

**Qué obtienes:** Un archivo `terraform.tfvars` igual al ejemplo, listo para editar.

---

## Paso 3: Obtener un ID de AMI

**Qué hacemos:** Preguntar a AWS: “¿cuál es la última imagen de Amazon Linux 2 en mi región?”. La respuesta es un **AMI ID** (ej. `ami-05024c2628f651b80`).

**Por qué:** Cada servidor (Jenkins, Gitea, POS) necesita una “imagen” de sistema operativo. Esa imagen se identifica con el AMI ID. Si usas otra región, el ID cambia.

**Comando (ejemplo para us-east-1):**
```bash
aws ec2 describe-images --owners amazon --region us-east-1 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

**Qué obtienes:** Una línea con un valor tipo `ami-0abc123def456...`. Copia ese valor.

Si usas **otra región** (ej. `us-east-2`), cambia `--region us-east-1` por tu región y luego en `terraform.tfvars` pon esa misma región en `aws_region` y las zonas en `availability_zones` (ej. `us-east-2a`, `us-east-2b`).

---

## Paso 4: Rellenar las AMI en terraform.tfvars

**Qué hacemos:** Abrir `terraform.tfvars` con un editor y sustituir **todas** las apariciones de `ami-xxxxxxxxxxxxxxxxx` por el AMI ID que obtuviste (ej. `ami-05024c2628f651b80`).

> **Importante:** Nunca hagas `terraform apply` dejando `ami-xxxxxxxxxxxxxxxxx`. Es un placeholder; esa AMI no existe y el apply fallará. Además, la **región** en `aws_region` y `availability_zones` debe ser la misma que usaste al obtener el AMI (si obtuviste AMI de us-east-1, deja us-east-1). Si ya tenías infra en otra región (ej. us-east-2) y pones us-east-1, Terraform **destruirá** la infra antigua y creará una nueva en us-east-1.

**Por qué:** Si dejas el placeholder, Terraform intentará usar una AMI que no existe y fallará. Las variables que debes rellenar son:

- `jenkins_controller_ami_id`
- `jenkins_worker_ami_id`
- `gitea_ami_id`
- `pos_ami_id`

En Free Tier suele usarse **la misma AMI** para las cuatro.

**Qué obtienes:** Un `terraform.tfvars` válido para tu región y tu cuenta.

---

## Paso 5: terraform init

**Qué hacemos:** Inicializar Terraform en esta carpeta: descarga los módulos (network, compute, gitea, pos, etc.) y el plugin de AWS.

**Por qué:** Terraform necesita tener los módulos y el provider antes de poder hacer `plan` o `apply`.

**Comando:**
```bash
terraform init
```

**Qué obtienes:** Mensaje tipo “Terraform has been successfully initialized!”. Se crea/actualiza la carpeta `.terraform` (no hace falta tocarla).

---

## Paso 6: terraform plan

**Qué hacemos:** Terraform **calcula** qué cambios haría en AWS (crear VPC, subredes, máquinas, etc.) **sin aplicar nada todavía**. Muestra un resumen: “X to add, Y to change, Z to destroy”.

**Por qué:** Así ves qué va a pasar antes de gastar recursos o borrar algo. Si ya tenías infra en otra región, el plan puede mostrar que va a **destruir** recursos antiguos y **crear** otros nuevos (por ejemplo al cambiar de us-east-2 a us-east-1).

**Comando:**
```bash
terraform plan -var-file=terraform.tfvars
```

**Qué obtienes:** Un texto largo con el “plan”. Revisa que tenga sentido (crear Jenkins, Gitea, POS, red, etc.). Si ves “destroy” de algo que quieres conservar, **no hagas apply** y revisa región/terraform.tfvars.

---

## Paso 7: terraform apply

**Qué hacemos:** Decir a Terraform que **ejecute** el plan: crea (o modifica) en AWS la VPC, subredes, security groups y las instancias EC2 (Jenkins, Gitea, POS).

**Por qué:** Es el paso que realmente crea la infra. Terraform pedirá confirmación escribiendo `yes`.

**Comando:**
```bash
terraform apply -var-file=terraform.tfvars
```

**Qué obtienes:** Al terminar, Terraform escribe en pantalla las **salidas (outputs)**:

- **jenkins_public_ip** — IP pública del servidor Jenkins. Acceso: `http://<esta_ip>:8080` (después de instalar Jenkins en la máquina).
- **gitea_public_ip** — IP del servidor Gitea. Acceso: `http://<esta_ip>:3000` (después de instalar Gitea).
- **pos_public_ip** — IP del servidor POS. Ahí Jenkins hará el deploy de la app (puerto 8111).
- **jenkins_lb_dns** — IP privada de Jenkins (útil si usas un balanceador o acceso interno).

Anota esas IPs. Los servidores estarán **encendidos pero vacíos**: hay que instalar Jenkins, Gitea, etc. por SSH (ver documentación de instalación del proyecto).

---

## Resumen en una tabla

| Paso | Comando / acción | Para qué |
|------|------------------|----------|
| 1 | `cd ../../../manifests/terraform/jenkins-aws/envs/dev` (o la ruta correcta desde donde estés) | Entrar en la carpeta del Terraform. |
| 2 | `cp terraform.tfvars.free-tier.example terraform.tfvars` | Crear tu archivo de variables. |
| 3 | `aws ec2 describe-images ...` (ver arriba) | Obtener el AMI ID de Amazon Linux 2 en tu región. |
| 4 | Editar `terraform.tfvars` y poner el AMI en las 4 variables | Que Terraform use una imagen válida. |
| 5 | `terraform init` | Descargar módulos y provider. |
| 6 | `terraform plan -var-file=terraform.tfvars` | Ver qué va a crear/cambiar/borrar. |
| 7 | `terraform apply -var-file=terraform.tfvars` y escribir `yes` | Crear la infra en AWS y obtener las IPs. |

---

## Errores típicos

1. **“No such file or directory” al hacer `cd`**  
   Estás en una carpeta desde la que la ruta no es correcta. Usa la tabla de rutas (Paso 1) o `cd` primero a la raíz del repo y luego `cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev`.

2. **“terraform.tfvars does not exist”**  
   No estás en `envs/dev` o no has ejecutado el `cp` del Paso 2. Entra en `envs/dev` y vuelve a hacer `cp terraform.tfvars.free-tier.example terraform.tfvars`.

3. **“The directory has no Terraform configuration files”**  
   Sigue siendo un problema de carpeta: estás en un sitio donde no hay `main.tf` (por ejemplo dentro de `repo-pos-fastflow`). Ve a `envs/dev` como en el Paso 1.

4. **El plan dice “destroy” de mi Jenkins / VPC que ya usaba**  
   Tu `terraform.tfvars` tiene otra región (ej. `us-east-1`) que la que usaste la primera vez (ej. `us-east-2`). Terraform va a destruir la infra antigua y crear una nueva en la nueva región. Si quieres conservar la infra actual, pon en `terraform.tfvars` la **misma** región y mismas zonas que cuando creaste los recursos.

5. **Apply falla con "InvalidAMIID" o "AMI not found"**  
   Dejaste el placeholder `ami-xxxxxxxxxxxxxxxxx` en alguna variable. Sustituye **todas** por un AMI válido de tu región (obtén una con el comando del Paso 3 usando la misma región que en `aws_region`).

6. **Apply falló a medias (destruyó Jenkins/subredes y salieron errores de VPC o AMI)**  
   El estado quedó inconsistente. Usa **[RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md](RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md)**: pon en `terraform.tfvars` la **misma región** que tenía la infra original (ej. us-east-2), una **AMI válida** de esa región en las 4 variables, y vuelve a ejecutar `terraform apply`.

---

## Documentos relacionados

- [INICIO-RAPIDO-AWS-GRATIS.md](INICIO-RAPIDO-AWS-GRATIS.md) — Punto de entrada y otros roles (transferir repo, nuevo dueño).
- [INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](INSTRUCCIONES-DEPLOY-AWS-GRATIS.md) — Lista de pasos detallada para deploy Free Tier.
- [GUIA-DEPLOY-REAL-AWS-Y-KUBERNETES.md](GUIA-DEPLOY-REAL-AWS-Y-KUBERNETES.md) — Deploy en AWS y cómo probar Kubernetes.

# Primeros pasos con una cuenta AWS nueva

Acabas de crear la cuenta (con tarjeta de crédito). La consola ya está abierta. Sigue esto en orden.

---

## 1. Verificar la región

Arriba a la derecha en la consola verás la **región** (ej. *US East (Ohio)* o *N. Virginia*). Anótala:

- **us-east-1** = N. Virginia  
- **us-east-2** = Ohio  
- **mx-central-1** = Mexico (Central), si la activaste  

Para el Free Tier (una EC2 gratis) sirve cualquiera de estas; en las guías usamos **us-east-1** en los ejemplos.

---

## 2. Crear un usuario para usar desde tu PC (recomendado)

No uses el usuario *root* para el día a día. Crea un usuario IAM con acceso por programa (para Terraform y AWS CLI):

1. En la consola, busca **IAM** (escribe "IAM" en la barra de búsqueda).
2. Menú izquierda → **Users** → **Create user**.
3. Nombre: por ejemplo `terraform` o tu nombre.
4. **Next** → en "Set permissions" elige **Attach policies directly** y marca **AdministratorAccess** (para simplificar; en producción restringes más).
5. **Next** → **Create user**.
6. Entra al usuario recién creado → pestaña **Security credentials** → **Create access key**.
7. Elige **Command Line Interface (CLI)** → Next → **Create access key**.
8. **Copia y guarda** el **Access key ID** y el **Secret access key** (solo se muestran una vez). No los subas a Git ni los compartas.

---

## 3. Instalar AWS CLI y configurarlo

En tu Mac (Terminal):

```bash
# Si no tienes AWS CLI (o quieres la v2):
brew install awscli

# Configurar con las credenciales del paso 2
aws configure
```

Te pedirá:

- **AWS Access Key ID:** pega el Access key ID  
- **AWS Secret Access Key:** pega el Secret access key  
- **Default region name:** por ejemplo `us-east-1` o `us-east-2`  
- **Default output format:** puedes dejar `json`  

Comprueba:

```bash
aws sts get-caller-identity
```

Si devuelve tu cuenta y usuario, está bien.

---

## 4. Instalar Terraform

```bash
brew install terraform
terraform version
```

Debe ser >= 1.6.

---

## 5. Qué sigue: desplegar gratis (FastFlow / pos-online)

- **Si tienes el repo pos-online** (con carpeta `terraform/envs/free`):  
  → Abre **docs/INDICE-GUIAS-HACER-TODO.md** y sigue la sección **3. Desplegar en AWS gratis**.  
  → O directamente **docs/DESPLIEGUE-AWS-GRATIS-Y-TRANSFERIR-REPO.md** y **terraform/envs/free/README.md**.

- **Si tienes el repo toolkit-fastflow** (con `manifests/terraform/jenkins-aws`):  
  → **manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md**  
  → O **docs/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md**.

En ambos casos: copiar el `terraform.tfvars.example` a `terraform.tfvars`, poner tu región y la AMI (con el comando `aws ec2 describe-images` que viene en la guía), y luego `terraform init` → `plan` → `apply`.

---

## Resumen

| Paso | Qué hacer |
|------|-----------|
| 1 | Anotar la región en la consola (us-east-1, us-east-2, etc.). |
| 2 | IAM → Create user → Create access key → guardar Access key y Secret key. |
| 3 | `brew install awscli` → `aws configure` → `aws sts get-caller-identity`. |
| 4 | `brew install terraform`. |
| 5 | Seguir la guía de despliegue gratis (pos-online o toolkit) según el repo que uses. |

Tu tarjeta no se cobra mientras uses solo recursos Free Tier (p. ej. 1 EC2 t2.micro, 750 h/mes). Destruye los recursos con `terraform destroy` cuando no los uses para evitar cargos.

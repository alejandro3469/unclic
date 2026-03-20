# Fase: Deploy Jenkins en AWS Free Tier — Estado

Documento único de esta fase: **qué está hecho** y **qué falta por hacer**.

---

## Resumen ejecutivo

| Estado | Descripción |
|--------|-------------|
| **Hecho** | Cuenta AWS, CLI, Terraform, variables, init, plan, apply. Infraestructura creada (VPC, 1 EC2 Jenkins, ASG 0 workers). |
| **Pendiente** | Conectar a la instancia (EC2 Instance Connect), instalar Java 11 + Jenkins, abrir http://IP:8080 y completar el setup de Jenkins. |

---

# PARTE 1 — LO YA HECHO

## 1.1 Cuenta y acceso AWS

- Cuenta AWS (477010601377), usuario IAM **alejandro-perez**.
- AWS CLI instalado (`brew install awscli`) y configurado (`aws configure`): Access Key, Secret, región **us-east-2**, output json.
- Identidad verificada: `aws sts get-caller-identity` → Arn `arn:aws:iam::477010601377:user/alejandro-perez`.

## 1.2 Terraform

- Terraform instalado (`brew install terraform`), versión 1.5.7.
- Directorio de trabajo: `toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev`.
- Archivo de variables: `cp terraform.tfvars.free-tier.example terraform.tfvars` y rellenado con:
  - `aws_region = "us-east-2"`
  - `availability_zones = ["us-east-2a", "us-east-2b"]`
  - `jenkins_controller_ami_id` y `jenkins_worker_ami_id` = `ami-040855b0715ee6f0b` (Amazon Linux 2 en us-east-2).
- AMI obtenida con:
  ```bash
  aws ec2 describe-images --owners amazon --region us-east-2 \
    --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
    --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
  ```
- Ajustes en el código Terraform para compatibilidad con Terraform 1.5.x:
  - `required_version` en `envs/dev/main.tf`: de `>= 1.6.0` a `>= 1.5.0`.
  - Variables con `type` y `default` en una sola línea corregidas a bloque multilínea en:
    - `envs/dev/variables.tf`
    - `modules/autoscaling/variables.tf`
    - `modules/compute/variables.tf`
- `terraform init` ejecutado correctamente (provider hashicorp/aws v6.36.0).
- `terraform validate` → Success.
- `terraform plan -var-file=terraform.tfvars` → 14 recursos a crear.
- `terraform apply -var-file=terraform.tfvars` → confirmado con `yes`; apply completado.

## 1.3 Infraestructura creada en AWS

- **VPC** `vpc-06bab4c9b376d64c0` (fastflow-vpc), CIDR 10.0.0.0/16.
- **Subnets** públicas (fastflow-public-0, fastflow-public-1) y privadas (fastflow-private-0, fastflow-private-1) en us-east-2a y us-east-2b.
- **Internet gateway**, **route table** pública y asociaciones.
- **Security group** para Jenkins (fastflow-jenkins-sg): ingress 22 (SSH), 8080 (Jenkins); egress todo.
- **Instancia EC2** (fastflow-jenkins-controller):
  - Instance ID: `i-040e97fa007277738`
  - Tipo: t3.micro, AMI: amzn2-ami-hvm-2.0.20260302.0-x86_64-gp2
  - IP pública: **3.15.4.160**
  - IP privada: 10.0.1.62
  - Public DNS: ec2-3-15-4-160.us-east-2.compute.amazonaws.com
  - Subnet: fastflow-public-0 (subred pública)
  - **Key pair:** no asignado (la instancia se lanzó sin key pair).
- **ASG** de workers (fastflow-workers-asg) con capacidad 0 (sin workers).
- **Outputs** de Terraform:
  - `jenkins_public_ip` = 3.15.4.160
  - `jenkins_lb_dns` = 10.0.1.62
  - `vpc_id` = vpc-06bab4c9b376d64c0
  - `workers_asg_name` = fastflow-workers-asg

## 1.4 Documentación creada en esta fase

- `docs/SESION-DEPLOY-AWS-FREE-TIER.md` — Sesión de comandos y salidas (deploy hasta apply).
- `docs/QUE-SIGUE-DESPUES-DEL-APPLY.md` — Pasos posteriores al apply (conectar, instalar Jenkins, destruir).
- Este archivo: `docs/FASE-DEPLOY-AWS-ESTADO.md` — Estado de la fase (hecho / por hacer).

---

# PARTE 2 — OPCIONES DE CONEXIÓN A LA INSTANCIA (CONSOLA EC2 → CONNECT)

Al pulsar **Connect** en la instancia **fastflow-jenkins-controller** aparecen cuatro pestañas. Estado de cada una:

| Pestaña | ¿Funciona? | Notas |
|---------|------------|--------|
| **EC2 Instance Connect** | **Sí** | Conexión por IP pública (3.15.4.160), usuario **ec2-user**. No requiere key pair en la instancia (AWS inyecta una key temporal). Es la opción a usar. |
| **SSM Session Manager** | No | "DHMC is not enabled and IAM instance profile is not attached". Error: *Systems Manager's instance management role is not configured for account*. La instancia no tiene rol IAM para SSM. |
| **SSH client** | No | "No associated key pair". No hay key pair asociado a la instancia; no se puede usar `ssh -i key.pem ec2-user@...` desde el Mac sin crear un key pair y recrear la instancia (o usar EC2 Instance Connect). |
| **EC2 serial console** | No | "This account is not authorized to use the EC2 serial console". Requiere autorización en la configuración de la cuenta EC2. |

**Conclusión:** usar **EC2 Instance Connect**: dejar usuario `ec2-user`, pulsar **Connect**; se abre una sesión en el navegador.

---

# PARTE 3 — LO POR HACER (PENDIENTE EN ESTA FASE)

## 3.1 Conectar a la instancia

1. AWS Console → EC2 → Instances.
2. Seleccionar **fastflow-jenkins-controller** (i-040e97fa007277738).
3. **Connect** → pestaña **EC2 Instance Connect**.
4. Usuario: **ec2-user** (por defecto).
5. Pulsar **Connect** (sesión en el navegador).

## 3.2 Instalar Java 11 y Jenkins en la instancia

Unclic/pos-online requiere **Java 11** para Jenkins y para el POS (docs/instalacion). En la sesión abierta (como `ec2-user`), ejecutar:

```bash
# Java 11 (requerido por unclic/pos-online)
sudo yum install -y java-11-amazon-corretto-devel
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install -y jenkins
sudo systemctl enable jenkins
sudo systemctl start jenkins
```

Comprobar que Jenkins está en ejecución:

```bash
sudo systemctl status jenkins
```

Obtener la contraseña inicial:

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

## 3.3 Completar el setup de Jenkins en el navegador

1. Abrir **http://3.15.4.160:8080** en el navegador.
2. Pegar la contraseña obtenida en el paso anterior.
3. Seguir el asistente (instalar plugins sugeridos o elegir plugins, crear usuario admin, etc.).

## 3.4 (Opcional) Mejoras posteriores

- **Key pair para SSH desde el Mac:** crear key pair en EC2, añadir `key_name` al módulo compute y a `terraform.tfvars`, volver a aplicar (recrea la instancia).
- **SSM Session Manager:** asignar un IAM role a la instancia con la política `AmazonSSMManagedInstanceCore` y, si se desea, configurar DHMC en la cuenta.
- **User-data:** añadir un script de user-data en el módulo compute para instalar Java + Jenkins al arrancar la instancia (evita pasos manuales en futuros deploys).

## 3.5 Destruir recursos cuando no se usen

Desde `envs/dev`:

```bash
terraform destroy -var-file=terraform.tfvars
```

Confirmar con `yes`. Elimina VPC, subnets, instancia Jenkins y ASG para evitar costes.

---

# Referencias rápidas

| Documento | Ubicación | Uso |
|-----------|-----------|-----|
| Instrucciones deploy AWS gratis | `INSTRUCCIONES-DEPLOY-AWS-GRATIS.md` | Lista única de pasos (init, plan, apply). |
| Qué sigue después del apply | `docs/QUE-SIGUE-DESPUES-DEL-APPLY.md` | Conectar, instalar Jenkins, URL, destroy. |
| Sesión deploy (comandos y salidas) | `docs/SESION-DEPLOY-AWS-FREE-TIER.md` | Referencia de la sesión de terminal. |
| Estado de la fase (este doc) | `docs/FASE-DEPLOY-AWS-ESTADO.md` | Hecho vs pendiente y opciones de conexión. |

---

**Última actualización:** Fase en curso; infraestructura creada; pendiente conexión a la instancia, instalación de Jenkins y setup en el navegador.

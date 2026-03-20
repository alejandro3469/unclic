# Happy path: replicar todo el flujo (Terraform + Jenkins + Gitea + POS)

Guía paso a paso para que **cualquiera** pueda replicar la infra y los servicios desde cero: Terraform en AWS, acceso a las instancias, instalación de Jenkins y Gitea, y opcionalmente el deploy de la landing UnClic.

---

## Resumen en una frase

Clonas el repo → configuras variables Terraform (región + AMI) → aplicas Terraform → anotas las IPs → entras por navegador (EC2 Instance Connect) a cada instancia → instalas Jenkins en una, Gitea en otra → la tercera (POS) queda lista para que Jenkins haga deploy. Opcional: build de UnClic y rsync a una EC2 con Nginx.

---

## Requisitos previos

| Requisito | Cómo comprobarlo |
|-----------|-------------------|
| **Cuenta AWS** | Acceso a la consola AWS (idealmente Free Tier si es cuenta nueva). |
| **Terraform** >= 1.5 | `terraform version` |
| **AWS CLI** configurado | `aws sts get-caller-identity` (debe devolver tu cuenta). |
| **Editor de texto** | Para editar `terraform.tfvars`. |

No hace falta tener un key pair (.pem) para empezar: las instancias se pueden abrir con **EC2 Instance Connect** desde la consola AWS.

---

## Parte 1 — Terraform: crear la infra en AWS

### 1.1 Dónde está el código

El Terraform está en el repo, dentro de:

- Ruta desde la **raíz del repo** (`pipeline-as-code-with-jenkins-master`):  
  `toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev`

Si estás en otra carpeta, el `cd` debe ser **relativo a donde estás**:

| Si estás aquí | Comando para llegar a envs/dev |
|---------------|---------------------------------|
| Raíz del repo | `cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev` |
| `repo-pos-fastflow` | `cd ../../../manifests/terraform/jenkins-aws/envs/dev` |
| `unclic` | `cd ../../../manifests/terraform/jenkins-aws/envs/dev` |

Comprueba que estás en el sitio correcto:

```bash
ls main.tf variables.tf terraform.tfvars.free-tier.example
```

Deben existir esos archivos.

### 1.2 Crear el archivo de variables

```bash
cp terraform.tfvars.free-tier.example terraform.tfvars
```

**No subas `terraform.tfvars` a Git** (contiene datos de tu entorno).

### 1.3 Obtener una AMI válida

Elige **una** región (por ejemplo `us-east-1` o `us-east-2`) y obtén la última Amazon Linux 2 en esa región.

**us-east-1:**

```bash
aws ec2 describe-images --owners amazon --region us-east-1 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

**us-east-2:**

```bash
aws ec2 describe-images --owners amazon --region us-east-2 \
  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
```

Copia el valor (ej. `ami-040855b0715ee6f0b`).

### 1.4 Rellenar terraform.tfvars

Abre `terraform.tfvars` y:

1. **Región y zonas** — Deben coincidir con la región del comando anterior:
   - Si usaste **us-east-1**:  
     `aws_region = "us-east-1"` y  
     `availability_zones = ["us-east-1a", "us-east-1b"]`
   - Si usaste **us-east-2**:  
     `aws_region = "us-east-2"` y  
     `availability_zones = ["us-east-2a", "us-east-2b"]`

2. **AMIs** — Sustituye **todas** las apariciones de `ami-xxxxxxxxxxxxxxxxx` por el ID que obtuviste (una sola AMI para las cuatro variables):
   - `jenkins_controller_ami_id`
   - `jenkins_worker_ami_id`
   - `gitea_ami_id`
   - `pos_ami_id`

Guarda el archivo.

### 1.5 Aplicar Terraform

En la **misma carpeta** `envs/dev`:

```bash
terraform init
terraform validate
terraform plan -var-file=terraform.tfvars
```

Revisa el plan (debe crear o actualizar VPC, subredes, Jenkins, Gitea, POS). Luego:

```bash
terraform apply -var-file=terraform.tfvars
```

Cuando pida confirmación escribe **yes** y pulsa Enter.

### 1.6 Anotar las salidas

Al terminar el apply, Terraform muestra algo como:

```text
gitea_public_ip   = "18.227.21.135"
jenkins_public_ip = "18.218.37.76"
pos_public_ip     = "3.129.247.127"
jenkins_lb_dns    = "10.0.1.39"
vpc_id            = "vpc-06bab4c9b376d64c0"
workers_asg_name  = "fastflow-workers-asg"
```

Anota **jenkins_public_ip**, **gitea_public_ip** y **pos_public_ip**. Las usarás para conectarte y para las URLs.

---

## Parte 2 — Conectarte a las instancias (SSH)

Las instancias **no tienen key pair** asignado en Terraform. La forma más rápida de entrar es **EC2 Instance Connect** desde la consola AWS.

### 2.1 Abrir una instancia (Jenkins, Gitea o POS)

1. Entra en **AWS Console** → **EC2** → **Instances**.
2. Elige la instancia (por nombre: `fastflow-jenkins-controller`, `fastflow-gitea` o `fastflow-pos`).
3. **Connect** → pestaña **EC2 Instance Connect**.
4. Deja el usuario `ec2-user` y pulsa **Connect**.

Se abre una sesión en el navegador. Ahí ejecutarás los comandos de instalación.

### 2.2 (Opcional) SSH desde tu Mac con clave .pem

Si creas un **Key Pair** en EC2 y descargas el `.pem`, puedes conectarte desde tu máquina. Para que Terraform asigne esa clave a las instancias haría falta añadir la variable `key_name` a los módulos y a `terraform.tfvars`, y volver a aplicar (las instancias se recrearían). Mientras no lo hagas, **EC2 Instance Connect** es suficiente para replicar el flujo. Ver [docs/QUE-SIGUE-DESPUES-DEL-APPLY.md](docs/QUE-SIGUE-DESPUES-DEL-APPLY.md) sección 5.

---

## Parte 3 — Instalar Jenkins en la instancia Jenkins

Jenkins 2.463+ **requiere Java 17 o superior** ([Jenkins Java Support](https://www.jenkins.io/doc/book/platform-information/support-policy-java/)). En Amazon Linux 2 usamos **Corretto 17** ([AWS Corretto 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/amazon-linux-install.html)).

1. Conéctate a la instancia **fastflow-jenkins-controller** con EC2 Instance Connect (ver 2.1).
2. Ejecuta (Amazon Linux 2):

```bash
# Repo Corretto (si falta: "No package java-17-amazon-corretto-devel")
sudo curl -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo

# Java 17 (obligatorio para Jenkins actual)
sudo yum install -y java-17-amazon-corretto-devel
java -version   # debe mostrar 17 ... Corretto

# Repo de Jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install -y jenkins

# Si hay varias JVMs: que Jenkins use Java 17 (ruta estándar Corretto 17 en AL2)
echo 'JENKINS_JAVA_CMD=/usr/lib/jvm/java-17-amazon-corretto/bin/java' | sudo tee -a /etc/sysconfig/jenkins

# Arrancar y habilitar al inicio
sudo systemctl enable jenkins
sudo systemctl start jenkins
```

3. Comprueba: `sudo systemctl status jenkins`
4. Contraseña inicial: `sudo cat /var/lib/jenkins/secrets/initialAdminPassword`
5. En el navegador abre **http://&lt;jenkins_public_ip&gt;:8080**, pega la contraseña y completa el asistente de configuración.

Documentación de referencia: [docs/QUE-SIGUE-DESPUES-DEL-APPLY.md](docs/QUE-SIGUE-DESPUES-DEL-APPLY.md), [APLICAR-FASTFLOW.md](APLICAR-FASTFLOW.md).

---

## Parte 4 — Instalar Gitea en la instancia Gitea

1. Conéctate a la instancia **fastflow-gitea** con EC2 Instance Connect.
2. Opción sencilla con binario (ejemplo en `/home/ec2-user`):

```bash
# Usuario y directorio
sudo useradd --system --shell /bin/bash --comment 'Gitea' --create-home --home-dir /home/gitea gitea
sudo mkdir -p /var/lib/gitea
sudo chown -R gitea:gitea /var/lib/gitea

# Descargar Gitea (ajusta la versión si quieres otra)
curl -sL https://github.com/go-gitea/gitea/releases/download/v1.21.0/gitea-1.21.0-linux-amd64 -o gitea
chmod +x gitea
sudo mv gitea /usr/local/bin/

# Servicio systemd (crear /etc/systemd/system/gitea.service)
sudo tee /etc/systemd/system/gitea.service << 'EOF'
[Unit]
Description=Gitea
After=network.target

[Service]
Type=simple
User=gitea
Group=gitea
WorkingDirectory=/var/lib/gitea
ExecStart=/usr/local/bin/gitea web -c /var/lib/gitea/custom/conf/app.ini
Restart=always
Environment=USER=gitea HOME=/home/gitea GITEA_WORK_DIR=/var/lib/gitea

[Install]
WantedBy=multi-user.target
EOF

sudo mkdir -p /var/lib/gitea/custom/conf
# Crear app.ini. Sustituye 18.227.21.135 por tu gitea_public_ip (la del output de Terraform).
GITEA_IP="18.227.21.135"
sudo tee /var/lib/gitea/custom/conf/app.ini << EOF
[server]
HTTP_PORT = 3000
DOMAIN = $GITEA_IP
ROOT_URL = http://$GITEA_IP:3000/
EOF
sudo chown -R gitea:gitea /var/lib/gitea

sudo systemctl daemon-reload
sudo systemctl enable gitea
sudo systemctl start gitea
```

3. En el navegador abre **http://&lt;gitea_public_ip&gt;:3000** y completa la instalación inicial de Gitea (crear usuario admin, etc.).

Si prefieres otra forma (Docker, paquete), usa la [documentación oficial de Gitea](https://docs.gitea.io/).

---

## Parte 5 — Instancia POS

La instancia **fastflow-pos** ya está creada y con el security group que permite 22, 8111, 80 y 443. No hace falta instalar nada de base para “replicar la infra”: Jenkins hará el deploy (contenedor o JAR) a esta IP cuando configures el pipeline. Si tu flujo usa Docker, instala Docker en esta instancia; si usas solo Java, instala Java. Depende del [Jenkinsfile y documentación del repo POS](https://github.com/web-cuantica/repo-pos-fastflow).

---

## Parte 6 — (Opcional) Deploy de la landing UnClic

Si quieres replicar también el despliegue de la web UnClic (Next.js estático) en un servidor con Nginx:

### 6.1 Build

En tu máquina, en la carpeta del proyecto UnClic (Next.js con `output: 'export'`):

```bash
cd /ruta/al/unclic   # p. ej. toolkit-fastflow/integrations/web-cuantica/unclic
npm run build
```

Se genera la carpeta `out/`.

### 6.2 Subir a la EC2

Necesitas una EC2 con Nginx y un usuario con clave SSH (por ejemplo la misma que usas para Gitea/consulting). El script del repo sube `out/` a `~/unclic-deploy/` en la EC2:

```bash
./scripts/deploy-to-unclic-consulting.sh
```

Por defecto usa `DEPLOY_HOST=3.22.236.150` y la clave `~/Downloads/gitea-key.pem`. Puedes sobreescribir:

```bash
DEPLOY_HOST=18.227.21.135 ./scripts/deploy-to-unclic-consulting.sh /ruta/a/tu-key.pem
```

### 6.3 En la EC2: copiar a Nginx y permisos

El script te recuerda los comandos. Conectado por SSH a esa EC2:

```bash
sudo rsync -av --delete /home/ec2-user/unclic-deploy/ /usr/share/nginx/unclic/
sudo chown -R nginx:nginx /usr/share/nginx/unclic
```

Luego abre https://unclic.consulting (o la URL que apunte ese servidor). Ver [unclic/scripts/deploy-to-unclic-consulting.sh](../../integrations/web-cuantica/unclic/scripts/deploy-to-unclic-consulting.sh) y la documentación en `unclic/docs/` si usas otro host o dominio.

---

## Errores frecuentes y recuperación

| Problema | Qué hacer |
|----------|-----------|
| **`cd: no such file or directory`** o **`terraform.tfvars does not exist`** | Estás en la carpeta equivocada. Usa la tabla de rutas del apartado 1.1 y asegúrate de estar en `envs/dev`. |
| **Plan muestra `ami-xxxxxxxxxxxxxxxxx`** | No has sustituido el placeholder en `terraform.tfvars`. Sustituye las cuatro AMI por un ID válido de tu región. |
| **Apply falla con "InvalidAMIID" o "VPC does not exist"** | Revisa región y AMI. Si el apply destruyó recursos y falló a medias, sigue [RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md](RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md). |
| **No puedo conectarme por SSH** | Usa **EC2 Instance Connect** desde la consola AWS (Connect → EC2 Instance Connect). No hace falta .pem. |

---

## Destruir la infra (evitar costes)

Cuando no la uses, desde `envs/dev`:

```bash
terraform destroy -var-file=terraform.tfvars
```

Confirma con **yes**. Se borran VPC, subredes, instancias Jenkins, Gitea y POS, y el ASG.

---

## Documentos relacionados

| Documento | Para qué |
|-----------|----------|
| [INICIO-RAPIDO-AWS-GRATIS.md](INICIO-RAPIDO-AWS-GRATIS.md) | Punto de entrada y otros roles (transferir repo, nuevo dueño). |
| [GUIA-PASO-A-PASO-PRINCIPIANTES.md](GUIA-PASO-A-PASO-PRINCIPIANTES.md) | Explicación detallada de cada comando Terraform. |
| [RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md](RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md) | Si el apply falló a medias (región o AMI incorrectos). |
| [docs/QUE-SIGUE-DESPUES-DEL-APPLY.md](docs/QUE-SIGUE-DESPUES-DEL-APPLY.md) | Detalle de Jenkins (Java, contraseña inicial, key pair opcional). |
| [GUIA-DEPLOY-REAL-AWS-Y-KUBERNETES.md](GUIA-DEPLOY-REAL-AWS-Y-KUBERNETES.md) | Probar Kubernetes (minikube/kind o EKS). |

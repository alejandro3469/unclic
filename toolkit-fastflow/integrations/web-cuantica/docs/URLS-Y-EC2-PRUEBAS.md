# URLs y EC2 para probar los cambios

Datos de las instancias EC2 FastFlow (Jenkins, Gitea, POS, Vantive, pos-demo) y URLs para probar. **Usar tu dominio (unclic.consulting):** [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

---

## Estado actual de instancias EC2 (6) — consola us-east-2

Tabla según la consola AWS EC2 → Instances (región **us-east-2**). Actualizar cuando cambien IPs o se añadan/eliminen instancias.

| Name | Instance ID | State | Type | AZ | Public IPv4 | Public DNS | Security group | Launch time |
|------|-------------|--------|------|-----|-------------|------------|----------------|-------------|
| fastflow-jenkins-controller | i-0bd179fb77e42be48 | Running | t3.micro | us-east-2a | **18.218.37.76** | ec2-18-218-37-76.us-east-2.compute.amazonaws.com | fastflow-jenkins-sg | 2026/03/17 10:50 |
| fastflow-gitea | i-0e885ccea49ef2ceb | Running | t3.micro | us-east-2a | **18.227.21.135** | ec2-18-227-21-135.us-east-2.compute.amazonaws.com | fastflow-gitea-sg | 2026/03/17 10:50 |
| fastflow-pos | i-071ba367513436d7e | Running | t3.micro | us-east-2a | **3.129.247.127** | ec2-3-129-247-127.us-east-2.compute.amazonaws.com | fastflow-pos-sg | 2026/03/17 10:50 |
| fastflow-gitea | i-0fb60c190b2e03f0c | Running | t3.micro | us-east-2c | **13.58.58.245** | ec2-13-58-58-245.us-east-2.compute.amazonaws.com | gitea-sg | 2026/03/13 13:17 |
| fastflow-vantive | i-0aa28684e29302446 | Running | t3.micro | us-east-2c | **3.22.236.150** | ec2-3-22-236-150.us-east-2.compute.amazonaws.com | vantive-sg | 2026/03/13 13:17 |
| fastflow-pos-demo | i-050684637f905e58b | Running | t3.micro | us-east-2c | **13.58.172.235** | ec2-13-58-172-235.us-east-2.compute.amazonaws.com | launch-wizard-1 | 2026/03/17 09:34 |

*La IP pública puede cambiar si la instancia se detiene/arranca y no tiene Elastic IP. Actualiza este doc y las URLs siguientes cuando cambien.*

---

## Consola EC2 — qué ves (literal)

Ruta: **AWS Console** → **EC2** → **Instances**. Región en cabecera: **United States (Ohio)** = us-east-2. Cuenta: (4770-1060-1377) o similar.

### Tabla Instances

Encabezado: **Instances (1/6)** (o X/6 según selección). Botones: **Connect** | **Instance state** | **Actions** | **Launch instances** | **Launch instance from template** | **Migrate a server**. Filtro **All states**.

**Columnas de la tabla:** Name | Instance ID | Instance state | Instance type | Status check | Alarm status | Availability Zone | Public IPv4 DNS | Public IPv4 address | Elastic IP | IPv6 IPs | Monitoring | Security group name | Key name | Launch time | Platform details | Managed | Operator.

Al hacer clic en una instancia se abre el **panel inferior** con pestañas: **Details** | **Status and alarms** | **Monitoring** | **Security** | **Networking** | **Storage** | **Tags**.

### Panel de detalle (ej. fastflow-jenkins-controller)

En **Details** verás **Instance summary**: Instance ID, Public IPv4 address (con enlace "open address"), Private IPv4 addresses, Public DNS, Instance state, Instance type, VPC ID (ej. fastflow-vpc), Subnet ID (ej. fastflow-public-0), Auto Scaling Group name, IAM role, etc. Y **Instance details**: AMI ID, Monitoring, Platform details (Linux/UNIX), Launch time, Termination protection, Key pair assigned at launch, etc. **Host and placement group**: Tenancy, Virtualization type, Number of vCPUs. Acciones arriba: **Connect**, **Instance state**, **Actions** (Stop, Start, Reboot, Terminate, etc.).

### Security Groups (EC2 → Security Groups)

Tabla con columnas: Name (a veces vacío) | Security group ID | Security group name | VPC ID | Description | Owner | Inbound rules count | Outbound rules count. En este proyecto aparecen, entre otros: **fastflow-jenkins-sg**, **fastflow-gitea-sg**, **fastflow-pos-sg**, **fastflow-workers-sg**, **gitea-sg**, **vantive-sg**, **launch-wizard-1**, **default** (por VPC). Acciones: Create security group, Export to CSV, View details, Edit inbound rules, Edit outbound rules, Delete.

### Conectar por SSH (qué ves al entrar)

Tras `ssh -i clave.pem ec2-user@18.218.37.76` (o la IP que corresponda): banner de **Amazon Linux 2** con aviso *"AL2 End of Life is 2026-06-30"* y recomendación *"A newer version of Amazon Linux is available! Amazon Linux 2023, GA and supported until 2028-03-15"*. Prompt: `[ec2-user@ip-10-0-1-39 ~]$` (el hostname usa la IP privada, ej. 10.0.1.39 para la instancia Jenkins).

---

## Resumen de la instancia (fastflow-jenkins-controller)

| Campo | Valor |
|-------|--------|
| **Instance ID** | i-0bd179fb77e42be48 |
| **Nombre** | fastflow-jenkins-controller |
| **Estado** | Running |
| **Public IPv4** | 18.218.37.76 |
| **Private IPv4** | 10.0.1.39 |
| **Public DNS** | ec2-18-218-37-76.us-east-2.compute.amazonaws.com |
| **Instance type** | t3.micro |
| **VPC** | vpc-06bab4c9b376d64c0 (fastflow-vpc) |
| **Subnet** | subnet-06656a7dd07861399 (fastflow-public-0) |
| **Availability Zone** | us-east-2a |
| **Security group** | fastflow-jenkins-sg |
| **AMI** | amzn2-ami-hvm-2.0.20260302.0-x86_64-gp2 (Amazon Linux 2) |
| **Región** | us-east-2 |

---

## URLs para probar

### Jenkins (fastflow-jenkins-controller)

| Uso | URL |
|-----|-----|
| **Jenkins (probar ahora)** | **http://18.218.37.76:8080** |
| Alternativa por DNS | http://ec2-18-218-37-76.us-east-2.compute.amazonaws.com:8080 |

Primer acceso: contraseña inicial con `sudo cat /var/lib/jenkins/secrets/initialAdminPassword` en la EC2. Ver [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md) Paso 3.

### App POS (fastflow-jenkins-controller, puerto 8111)

Si el POS se despliega en la misma instancia que Jenkins:

| Uso | URL |
|-----|-----|
| **App POS (tras Deploy)** | **http://18.218.37.76:8111** |
| Alternativa por DNS | http://ec2-18-218-37-76.us-east-2.compute.amazonaws.com:8111 |

Requisitos: puerto 8111 abierto en **fastflow-jenkins-sg** y stage Deploy del Jenkinsfile arrancando el JAR en 8111.

### Gitea (dos instancias con nombre fastflow-gitea)

Hay dos instancias llamadas **fastflow-gitea** (distintas AZ y security groups):

| Instance ID | AZ | Public IPv4 | Security group | URL Gitea (:3000) |
|-------------|-----|-------------|----------------|-------------------|
| i-0e885ccea49ef2ceb | us-east-2a | 18.227.21.135 | fastflow-gitea-sg | **http://18.227.21.135:3000** |
| i-0fb60c190b2e03f0c | us-east-2c | 13.58.58.245 | gitea-sg | **http://13.58.58.245:3000** |

Usa la que tengas configurada con Docker + Gitea. Instalación: [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md).

### POS (instancia dedicada fastflow-pos)

| Uso | URL |
|-----|-----|
| **App POS (instancia fastflow-pos)** | **http://3.129.247.127:8111** (si el servicio corre en 8111) |
| DNS | ec2-3-129-247-127.us-east-2.compute.amazonaws.com |

Security group **fastflow-pos-sg**: asegurar que el puerto 8111 (o el que use la app) esté abierto.

### POS demo (fastflow-pos-demo)

| Uso | URL |
|-----|-----|
| **App POS demo** | **http://13.58.172.235:8111** (o el puerto que use la app) |
| DNS | ec2-13-58-172-235.us-east-2.compute.amazonaws.com |

Security group **launch-wizard-1**: abrir los puertos necesarios (22, 8111, etc.).

### Vantive (instancia fastflow-vantive)

| Campo | Valor |
|-------|--------|
| **Instance ID** | i-0aa28684e29302446 |
| **Nombre** | fastflow-vantive |
| **Public IPv4** | 3.22.236.150 |
| **Public DNS** | ec2-3-22-236-150.us-east-2.compute.amazonaws.com |
| **Security group** | vantive-sg (22, 80, 443) |

| Uso | URL |
|-----|-----|
| **Sitio Vantive (Kings & Joers)** | **http://vantive.unclic.consulting** o **http://3.22.236.150** |
| Alternativa por DNS | http://ec2-3-22-236-150.us-east-2.compute.amazonaws.com |

Nginx sirve el contenido desde `/usr/share/nginx/html`. Ver [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) § 6 (fastflow-vantive).

---

## Security Groups (cuál editar para el puerto 8111)

La instancia **fastflow-jenkins-controller** usa el security group **fastflow-jenkins-sg**. Para que la app POS sea accesible en `http://<IP>:8111` hay que **añadir una regla de entrada** en ese SG (Paso 4 del [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md)).

Referencia de los security groups que puedes ver en la consola (VPC del proyecto: `vpc-06bab4c9b376d64c0`):

| Security group name   | Security group ID       | VPC        | Descripción           | Para qué usarlo |
|----------------------|-------------------------|------------|------------------------|-----------------|
| **fastflow-jenkins-sg** | sg-0e030608601ceb35f   | vpc-06bab4c9b376d64c0 | Managed by Terraform | **Este es el que debes editar:** instancia Jenkins; añadir regla TCP 8111 para la app POS. |
| fastflow-workers-sg  | sg-0287f516381ee0974    | vpc-06bab4c9b376d64c0 | Managed by Terraform | Workers (si en el futuro hay agentes en otras instancias). |
| default              | sg-007a541593b48dd9a   | vpc-06bab4c9b376d64c0 | default VPC security group | VPC por defecto; no usar para la EC2 Jenkins. |
| default              | sg-07fb4679879837daf   | vpc-07e18af35928b715f | default VPC security group | Otra VPC; no es la del proyecto FastFlow. |

**Cómo abrir el puerto 8111:** AWS Console → EC2 → **Security Groups** → seleccionar **fastflow-jenkins-sg** (por nombre o por ID `sg-0e030608601ceb35f`) → **Edit inbound rules** → **Add rule** → Type: Custom TCP, Port: **8111**, Source: 0.0.0.0/0 (o tu IP) → **Save rules**.

### Estado de fastflow-jenkins-sg antes de editar (referencia)

Así está el security group **antes** de añadir la regla del puerto 8111. Sirve para comprobar que estás en el SG correcto y qué reglas ya trae Terraform.

**Details (pestaña Details en la consola):**

| Campo | Valor |
|-------|--------|
| Security group name | fastflow-jenkins-sg |
| Security group ID | sg-0e030608601ceb35f |
| Description | Managed by Terraform |
| VPC ID | vpc-06bab4c9b376d64c0 |
| Owner | 477010601377 |
| Inbound rules count | 2 Permission entries |
| Outbound rules count | 1 Permission entry |

**Inbound rules (2) — antes de añadir 8111:**

| Type | Protocol | Port range | Source | Security group rule ID |
|------|----------|------------|--------|------------------------|
| SSH | TCP | 22 | 0.0.0.0/0 | sgr-032051a602ee6914a |
| Custom TCP | TCP | 8080 | 0.0.0.0/0 | sgr-03840388c2e22e274 |

Tras editar y guardar la nueva regla, tendrás **3** reglas de entrada (22, 8080, **8111**). La salida (Outbound) no hace falta tocarla.

### Pantalla "Edit inbound rules" (antes de añadir 8111)

Al pulsar **Edit inbound rules** en fastflow-jenkins-sg verás algo como esto. Sirve para reconocer la pantalla y saber dónde pulsar **Add rule**.

**Texto de la pantalla:**  
*"Inbound rules control the incoming traffic that's allowed to reach the instance."*

**Tabla de reglas existentes (2 filas):**

| Security group rule ID | Type | Protocol | Port range | Source | Acción |
|------------------------|------|----------|------------|--------|--------|
| sgr-03840388c2e22e274 | Custom TCP | Custom | 8080 | 0.0.0.0/0 | Delete |
| sgr-032051a602ee6914a | SSH | Custom | 22 | 0.0.0.0/0 | Delete |

**Botón:** **Add rule** (para añadir la nueva regla).

**Aviso de AWS (debajo):**  
*"Rules with source of 0.0.0.0/0 or ::/0 allow all IP addresses to access your instance. We recommend setting security group rules to allow known IP addresses only."*

**Botones al pie:** **Cancel** | **Preview changes** | **Save rules**.

**Qué hacer:** Pulsar **Add rule** → rellenar: Type = **Custom TCP**, Port range = **8111**, Source = **0.0.0.0/0** (o tu IP si quieres restringir) → **Save rules**.

### Estado después de añadir la regla 8111

Tras guardar, AWS muestra un mensaje de éxito y el security group queda con **3** reglas de entrada.

**Mensaje de éxito:**  
*"Inbound security group rules successfully modified on security group (sg-0e030608601ceb35f | fastflow-jenkins-sg)"*

**Details (tras la edición):**

| Campo | Valor |
|-------|--------|
| Security group name | fastflow-jenkins-sg |
| Security group ID | sg-0e030608601ceb35f |
| Description | Managed by Terraform |
| VPC ID | vpc-06bab4c9b376d64c0 |
| Owner | 477010601377 |
| Inbound rules count | **3** Permission entries |
| Outbound rules count | 1 Permission entry |

**Inbound rules (3) — después de añadir 8111:**

| Security group rule ID | IP version | Type | Protocol | Port range | Source |
|------------------------|------------|------|----------|------------|--------|
| sgr-03840388c2e22e274 | IPv4 | Custom TCP | TCP | 8080 | 0.0.0.0/0 |
| sgr-032051a602ee6914a | IPv4 | SSH | TCP | 22 | 0.0.0.0/0 |
| sgr-09ab52f30d309d8c7 | IPv4 | Custom TCP | TCP | **8111** | 0.0.0.0/0 |

Con esto, cuando la app POS esté levantada en la EC2, podrás abrir **http://&lt;IP&gt;:8111** desde el navegador (por ejemplo http://18.218.37.76:8111 si corre en Jenkins controller).

---

## Conectar a la EC2

- **EC2 Instance Connect:** AWS Console → EC2 → Instances → fastflow-jenkins-controller → **Connect** → pestaña **EC2 Instance Connect** → **Connect**.
- Usuario por defecto: `ec2-user`.

---

## Referencia rápida

- **Probar Jenkins:** **http://18.218.37.76:8080**
- **Probar app POS (en Jenkins controller):** **http://18.218.37.76:8111** — o en instancia dedicada **fastflow-pos:** **http://3.129.247.127:8111**
- **Probar Gitea:** **http://18.227.21.135:3000** o **http://13.58.58.245:3000** (según qué instancia uses)
- **Probar POS demo:** **http://13.58.172.235:8111**
- **Probar Vantive:** **http://vantive.unclic.consulting** o **http://3.22.236.150**

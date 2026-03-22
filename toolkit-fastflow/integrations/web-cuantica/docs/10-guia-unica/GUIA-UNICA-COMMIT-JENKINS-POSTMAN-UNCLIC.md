# Guía única — Commit → Jenkins → POS remoto → Postman → Rollback (dominio propio + Namecheap)

> **Ejecutar** siguiendo el índice. Entradas: [EMPIEZA-AQUI-GEORGE-WEBCUANTICA.md](../00-inicio/EMPIEZA-AQUI-GEORGE-WEBCUANTICA.md) · [EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md](../00-inicio/EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md). Resumen de orden A→K: [GUIA-CRONOLOGICA-UN-SOLO-IMPLEMENTADOR.md](../00-inicio/GUIA-CRONOLOGICA-UN-SOLO-IMPLEMENTADOR.md).

**Qué cubre:** AWS EC2 + Namecheap + Gitea + Jenkins + registry + POS. Dominios de ejemplo: **`unclic.consulting`**, **`webcuantica.com`** → sustituye **`TU_DOMINIO`** (§12.1). **§12** = dominio propio de punta a punta. **Terraform / K8s:** §11.

**Listo cuando:** (1) `https://gitea…` HTTPS + ROOT URL + repos. (2) `https://jenkins…` HTTPS + *Checkout SCM* del job POS. (3) POS health **200** / `"UP"` (p. ej. `:8111`).

---

<a id="indice"></a>

## Índice de navegación

| § | Tema |
|---|------|
| [0](#sec-0) | Manual único: **orden de trabajo** (EC2 → SG → DNS → consolas → Gitea → push → Jenkins) |
| [0.0](#sec-0-fases) | **Tabla maestra:** fases A–I (qué hacer antes de Gitea/Jenkins) |
| [0.1](#sec-0-aws) | **AWS:** crear EC2, clave `.pem`, Elastic IP |
| [0.2](#sec-0-ssh) | **SSH:** entrar a cada servidor y qué instalarías después |
| [0.3](#sec-0-gitea-push) | **Tu PC:** subir **POS** y **generic-model** a repos Gitea |
| [1](#sec-1) | Mapa del laboratorio (IPs y subdominios) |
| [2](#sec-2) | Namecheap → EC2 (DNS) |
| [3](#sec-3) | Security groups |
| [4](#sec-4) | Gitea listo + credencial Jenkins (**§4.1**) |
| [5](#sec-5) | Jenkins: Java, HTTPS Nginx, Maven/Git/Docker, swap (**generic-model** en **§5.3**) |
| — | **generic-model** (FastFlow): repo en Gitea + job Jenkins → `~/.m2` — [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md) |
| [6](#sec-6) | Job `pos-online-pipeline` y variables |
| [7](#sec-7) | Flujo commit → consola Jenkins |
| [8](#sec-8) | Postman |
| [9](#sec-9) | Rollback con registry |
| [10](#sec-10) | Checklist del recorrido exitoso |
| [11](#sec-11) | Documentos relacionados |
| [12](#sec-12) | **Web Cuántica** (`webcuantica.com`): Gitea+Jenkins+HTTPS+POS |

---

<a id="sec-0"></a>

## 0. Manual único (happy path)

Sigue el índice en orden. **§5** completo antes del primer **Build Now** del POS. ¿Cero en AWS? **§0.0 → §0.3** y luego **§1+**.

---

<a id="sec-0-fases"></a>

### 0.0 Orden de trabajo (de la nada al primer *Build Now*)

| Fase | Qué haces (resumen) | Dónde está detallado |
|------|---------------------|----------------------|
| **A** | Crear **EC2** (Gitea, Jenkins, POS como mínimo), **par de claves** `.pem`, **Elastic IP** por instancia que deba tener DNS fijo | **[§0.1](#sec-0-aws)** |
| **B** | Ajustar **security groups** (SSH, puertos Gitea/Jenkins/POS/registry) | **§3** |
| **C** | **Namecheap** (o tu DNS): registros **A** `gitea`, `jenkins`, `pos` → IPs públicas (tras EIP) | **§2** · plantilla **§12.2** |
| **D** | **SSH** a cada máquina: en **Gitea** levantar Docker/Gitea; en **Jenkins** Java + Jenkins; en **POS** preparar host (Docker/SSH deploy según pipeline) | **[§0.2](#sec-0-ssh)** → **§12.3–§12.6** (Gitea + HTTPS) · **§5.1–§5.4** (Jenkins) |
| **E** | **HTTPS** con Nginx + Let’s Encrypt en **Gitea** y **Jenkins** (recomendado antes de tokens y clones por HTTPS) | **§12.6** · **§5.2** |
| **F** | En el **navegador**, Gitea: usuario, **repos vacíos** para POS y **smartbussiness-generic-model** | **§4** |
| **G** | En **tu PC** (toolkit): `git push` del **POS** y del **generic-model** a esos repos | **[§0.3](#sec-0-gitea-push)** · [REPOS-LOCALES-Y-GITEA.md](../20-operaciones/REPOS-LOCALES-Y-GITEA.md) |
| **H** | **Jenkins:** credencial Gitea (**§4.1**), job **generic-model** (instala en `~/.m2`), job **pos-online-pipeline** (**§6**) | [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md) · **§6** |
| **I** | **Build Now** (primero generic-model si encadenas, o al menos una vez antes de confiar en *Test* del POS), luego POS; **Postman** | **§7** · **§8** |

**POS sin *Test* verde** hasta que **generic-model** esté en `~/.m2` de **jenkins** (job **§5.3** / [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md)).

---

<a id="sec-0-aws"></a>

### 0.1 AWS: crear las EC2 (antes de instalar Gitea o Jenkins)

Consola AWS, región fija (p. ej. `us-east-2`). **Una EC2 por rol** (Gitea, Jenkins, POS) salvo que fusiones a propósito.

1. **Launch instances** → nombre (`fastflow-gitea`, …) → AMI **Amazon Linux 2/2023** → **t3.micro** → **key pair** (descarga `.pem`, `chmod 400`).
2. Red: VPC por defecto OK; IP pública o EIP.
3. Disco 8–30 GiB. SG inicial: **22** desde tu IP; amplía en **§3**.
4. **Elastic IP:** Allocate → Associate a cada instancia con DNS. Anota IPs → **§1**, **§2**.

Más: [PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](../00-inicio/PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md), [GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md](../20-operaciones/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md).

---

<a id="sec-0-ssh"></a>

### 0.2 Primera conexión SSH y qué hacer en cada consola

```bash
ssh -i /ruta/a/tu-clave.pem ec2-user@IP_PUBLICA   # AL2; Ubuntu: usuario ubuntu
```

| Máquina | Qué hacer (detalle en la § indicada) |
|---------|-------------------------------------|
| **Gitea** | Docker + Gitea → **§12.3–§12.5**, [INSTALAR-GITEA-SELF-HOSTED.md](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md) |
| **Jenkins** | Java 17 + Jenkins + unlock → **§5.1**; luego **§5.3–§5.4** |
| **POS** | SSH + Docker si aplica + **8111** en SG (**§3**); deploy vía pipeline |

Una sesión SSH por IP. Con DNS y SG OK, usa FQDN en navegador y en `git remote`.

---

<a id="sec-0-gitea-push"></a>

### 0.3 Tu PC: rellenar Gitea con **POS** y **generic-model**

Repos **vacíos** en Gitea (**§4**). **Push solo** desde `repo-pos-fastflow/` y `smartbussiness-generic-model/` (no desde la raíz del monorepo): [REPOS-LOCALES-Y-GITEA.md](../20-operaciones/REPOS-LOCALES-Y-GITEA.md).

**Ruta base (ajusta `RUTA_MONOREPO`):**

```text
RUTA_MONOREPO/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow/          ← POS (FastFlow)
RUTA_MONOREPO/toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model/  ← generic-model
```

#### Paso 1 — POS (`repo-pos-fastflow`)

```bash
cd RUTA_MONOREPO/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow
git status
git remote -v
```

- Si **no** tienes remote hacia Gitea, añádelo (sustituye `TU_DOMINIO`, `TU_USUARIO`, `NOMBRE_REPO_POS`):

```bash
git remote add origin https://gitea.TU_DOMINIO/TU_USUARIO/NOMBRE_REPO_POS.git
# si ya existe "origin" apuntando a otro sitio, usa otro nombre o cambia la URL con: git remote set-url origin <URL_GITEA>
```

- Asegura rama **`main`**: `git branch -M main` (si tu rama principal es main).
- Sube todo el código del POS (incluye **Jenkinsfile** en la raíz del repo):

```bash
git add -A
git commit -m "Initial push: POS + Jenkinsfile"   # si ya hay commits, solo push
git push -u origin main
```

#### Paso 2 — generic-model (`smartbussiness-generic-model`)

```bash
cd RUTA_MONOREPO/toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model
git remote -v
```

- Convención en este toolkit: remote **`gitea`** hacia Gitea (y a veces **`origin`** hacia otro origen). Si falta `gitea`:

```bash
git remote add gitea https://gitea.TU_DOMINIO/TU_USUARIO/smartbussiness-generic-model.git
git push -u gitea main
```

**Alternativa con script** (crea el repo en Gitea por API si tienes token): [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md) → `scripts/push-generic-model-to-gitea.sh`.

#### Paso 3 — En Jenkins (después del push)

1. Crea el job **generic-model** (*Pipeline from SCM* al repo de generic-model) y ejecuta **Build Now** al menos una vez (**artefacto en `~/.m2`**).
2. Configura **pos-online-pipeline** apuntando al repo del POS (**§6**) y credencial **§4.1**.

---

### Uso rápido

- URLs en **navegador**; consola = SSH. EIP + registros **A** en Namecheap (**§2**).
- Jenkins: primero `http://IP:8080`; tras **§5.2** → `https://jenkins.TU_DOMINIO/` (puedes cerrar **8080** público).

**En §5 (Jenkins):** Java 17, Jenkins, Git, Maven, Docker si aplica, swap t3.micro, **generic-model** en `~/.m2` ([GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md), [EC2-INSTALAR-GENERIC-MODEL.md](../20-operaciones/EC2-INSTALAR-GENERIC-MODEL.md)), credencial **§4.1**, job **§6**. Con **POS_DEPLOY_HOST** → **REGISTRY** según **§6**.

**DNS:** `dig +short jenkins.TU_DOMINIO` y con `@8.8.8.8` deben coincidir con la IP de la EC2 Jenkins.

**Jenkins vivo (opcional):** `curl -v --connect-timeout 8 http://IP_JENKINS:8080/login` o túnel `ssh -i TU.pem -L 8080:127.0.0.1:8080 ec2-user@IP_JENKINS`.

---

<a id="sec-1"></a>

## 1. Mapa del laboratorio (ejemplo — **sustituye por tus IPs reales**)

Anota en un papel o tabla las IP que veas hoy en **AWS → EC2 → Instances** (región, p. ej. **us-east-2**).

| Rol | Nombre EC2 (ejemplo) | IP pública (ejemplo marzo 2026) | URL típica (Namecheap) |
|-----|----------------------|----------------------------------|-------------------------|
| **Jenkins + build Docker** | fastflow-jenkins-controller | `18.218.37.76` *(ej.; verificar en AWS)* | **`https://jenkins.unclic.consulting`** · plantilla **`https://jenkins.webcuantica.com`** |
| **Gitea** | fastflow-gitea | *(la de tu instancia Gitea)* | `https://gitea.unclic.consulting` *(tras Nginx)* · **`https://gitea.webcuantica.com`** |
| **POS** (contenedor o JAR) | fastflow-pos / fastflow-pos-demo | *(la del servidor donde corre 8111)* | `http://pos.unclic.consulting:8111` · **`http://pos.webcuantica.com:8111`** *(o HTTPS si añades Nginx en el POS)* |
| **Registry** (opcional dedicado) | a veces misma VPC que Jenkins | IP **privada** o pública `:5000` | `registry.unclic.consulting` / `registry.webcuantica.com` opcional |

`REGISTRY` en la misma VPC: suele ser **IP privada:5000**. [REGISTRY-EC2-GRATIS.md](../20-operaciones/REGISTRY-EC2-GRATIS.md).

---

<a id="sec-2"></a>

## 2. Namecheap → tus EC2 (DNS)

Los ejemplos usan **`unclic.consulting`**; para **`webcuantica.com`** u otro dominio el procedimiento es idéntico (Host = subdominio, Value = IP de la EC2). Tabla y orden para un equipo nuevo: **§12.2**.

1. **Namecheap** → *Domain List* → **unclic.consulting** → **Manage** → **Advanced DNS**.
2. **Host records** (ejemplos):

| Type | Host | Value | Notas |
|------|------|--------|--------|
| A | `jenkins` | IP pública de la EC2 Jenkins | Tras EIP, este valor ya no cambia cada reinicio. |
| A | `gitea` | IP pública de la EC2 Gitea | Puerto app Gitea suele ser **3000**. |
| A | `pos` | IP pública del servidor POS | La app suele estar en **8111** (o detrás de Nginx 443). |
| A | `registry` | IP del registry (si lo expones) | Opcional. |

3. Espera propagación (minutos a horas). Prueba: `ping jenkins.unclic.consulting` o `dig +short jenkins.unclic.consulting`.

Más detalle: [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md), [CHECKLIST-DOMINIO-JENKINS-HTTPS-ROLLBACK.md](../20-operaciones/CHECKLIST-DOMINIO-JENKINS-HTTPS-ROLLBACK.md).

---

<a id="sec-3"></a>

## 3. Security groups (firewall AWS) — mínimo para probar

Ajusta **Inbound rules** en cada instancia:

| Instancia | Puertos entrantes | Quién puede conectar |
|-----------|-------------------|----------------------|
| **Jenkins** | **22**, **80** (HTTP / ACME), **443** (HTTPS Nginx→Jenkins), **8080** (opcional, solo si necesitas Jenkins sin proxy), **8111** (si el POS corre en la misma EC2 en demo) | En lab se abre mucho a `0.0.0.0/0`; en serio: restringe **22**, quita **8080** público cuando solo entres por **443**. |
| **Gitea** | **22**, **3000** | Tu IP o `0.0.0.0/0` en lab. |
| **POS** | **22**, **8111** | **8111:** IP del agente Jenkins (stage *Verify*) **y** tu PC para **Postman**. |
| **Registry** | **5000** | Jenkins y servidor POS deben poder alcanzarlo (idealmente VPC / SG interno). |

---

<a id="sec-4"></a>

## 4. Gitea listo para ti

Plantilla dominio propio (`webcuantica.com`): **§12**. Push desde PC: **§0.3**.

1. Abre en el navegador: `http://gitea.unclic.consulting:3000` (o IP:3000).
2. Crea usuario (o usa el admin inicial).
3. Repos **vacíos** (sin Initialize): POS + **`smartbussiness-generic-model`**. Job generic-model antes de fiarte del *Test* del POS: [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md).
4. **SSH key** o **HTTPS + token** para que Jenkins clone: credencial en Jenkins tipo *Username/Password* o *SSH Username with private key*.

Referencias: [INSTALAR-GITEA-SELF-HOSTED.md](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md), [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](../20-operaciones/CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md), [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md).

### 4.1 Jenkins: credencial para clonar Gitea (HTTPS + token)

1. En **Gitea** → *Settings* → *Applications* → **Generate New Token** (nombre ej. `jenkins-ci`). Acceso a repos **privados:** elige **All (public, private, and limited)** u opción equivalente. Permiso mínimo habitual: **repository → Read**. Copia el token (solo se muestra una vez).
2. En **Jenkins** → **Manage Jenkins** → **Credentials** → **System** → **Global credentials (unrestricted)** → **Add Credentials**.
   - Kind: **Username with password**
   - **Username:** tu usuario Gitea (ej. `tu-usuario`).
   - **Password:** el **token** (no hace falta la contraseña de la cuenta).
   - **ID:** ej. `gitea-https-token` o `alejandro-gitea` (lo elegirás en el job).
3. En el job **Pipeline from SCM** → **Repositories** → **Credentials:** selecciona esa credencial.
4. **Repository URL** ejemplo: `https://gitea.unclic.consulting/USUARIO/pos-online-fastflow.git` o **`https://gitea.webcuantica.com/USUARIO/repo.git`** — **Branch:** `*/main` — **Script Path:** `Jenkinsfile`.

Si la credencial solo existe en el ámbito “User” y no aparece en el desplegable del job, créala en **Global** como arriba.

---

<a id="sec-5"></a>

## 5. Jenkins listo para el pipeline (desde cero)

EC2 Jenkins por SSH (`ec2-user`); luego UI Jenkins en el navegador.

### 5.1 Instalación base y primer acceso

1. **Java 17** (Corretto): `sudo yum install -y java-17-amazon-corretto-devel`
2. **Jenkins** (repositorio oficial Jenkins para AL2, si ya lo configuraste) y arranque: `sudo systemctl enable jenkins --now`
3. Contraseña inicial: `sudo cat /var/lib/jenkins/secrets/initialAdminPassword`
4. En el navegador entra la primera vez por **`http://IP_PUBLICA:8080`** o **`http://jenkins.unclic.consulting:8080`** (si DNS y SG en **8080** ya funcionan) → **Unlock** → plugins sugeridos → crea usuario admin.
5. **Manage Jenkins → System → Jenkins URL:** al inicio puede ser `http://IP:8080`; tras HTTPS (§5.2) debe quedar el FQDN HTTPS de tu dominio, p. ej. **`https://jenkins.unclic.consulting/`** o **`https://jenkins.webcuantica.com/`**.

### 5.2 HTTPS con Nginx + Let’s Encrypt (recomendado; mismo orden que en laboratorio)

**Sustituye** en Nginx y en Certbot el nombre `jenkins.unclic.consulting` por **`jenkins.TU_DOMINIO`** (para Web Cuántica: **`jenkins.webcuantica.com`**). El registro **A** `jenkins` en Namecheap debe apuntar a la **Elastic IP** de esta EC2 **antes** de ejecutar Certbot.

1. En el **security group** de Jenkins abre **TCP 80** y **TCP 443** (además de 22).
2. En la EC2 (**Amazon Linux 2**):
   - `sudo amazon-linux-extras install epel -y`
   - `sudo yum install -y nginx certbot python2-certbot-nginx`  
     *(En AL2 **no** suele existir `python3-certbot-nginx`; usa **`python2-certbot-nginx`**.)*
   - `sudo systemctl enable nginx --now`
   - Crea **`/etc/nginx/conf.d/jenkins.conf`** con `proxy_pass http://127.0.0.1:8080` y `server_name jenkins.TU_DOMINIO;` (mismo patrón que [HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md](../20-operaciones/HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md)) → `sudo nginx -t && sudo systemctl reload nginx`
   - `sudo certbot --nginx -d jenkins.TU_DOMINIO` (redirigir HTTP→HTTPS).
3. Vuelve a **Manage Jenkins → System** y fija **Jenkins URL** = **`https://jenkins.TU_DOMINIO/`** (barra final recomendada).
4. Comprueba renovación: `sudo certbot renew --dry-run`

**Amazon Linux 2023 en la EC2 Jenkins:** usa el mismo patrón que **§12.6** (Gitea): `dnf install nginx certbot` + plugin Nginx si tu AMI lo ofrece; configura `server_name jenkins.webcuantica.com` y `certbot --nginx -d jenkins.webcuantica.com`.

**Paso a paso detallado en AL2 (copiar/pegar):** [HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md](../20-operaciones/HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md) *(cambia `server_name` y `-d` al FQDN real)*.

### 5.3 Maven, Git y Docker (para el pipeline POS)

**Antes del primer Build Now**, en la EC2 Jenkins (orden recomendado):

1. **Git** (obligatorio para *Pipeline from SCM*):  
   `sudo yum install -y git`  
   Comprobar: `sudo -u jenkins git --version`
2. **Maven** (obligatorio para stages *Prepare* / *Build* / *Test* con `mvn`):  
   `sudo yum install -y maven`  
   Comprobar: `sudo -u jenkins mvn -version`
3. **Docker** (para *Build image* / *Deploy* con contenedor): [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](../20-operaciones/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) → `sudo usermod -aG docker jenkins` → `sudo systemctl restart jenkins`
4. **generic-model** en `~/.m2` del usuario **jenkins** (obligatorio para que **Test** resuelva dependencias): [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md), [EC2-INSTALAR-GENERIC-MODEL.md](../20-operaciones/EC2-INSTALAR-GENERIC-MODEL.md)
5. **Tools** en Jenkins (opcional): *Manage Jenkins → Tools* → Maven “Install automatically” **solo** si no usas el `mvn` del sistema; si usas `yum install maven`, no hace falta.

Tras instalar herramientas: `sudo systemctl restart jenkins` y vuelve a lanzar el build.

Pantallas del job: [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](../20-operaciones/JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md).

### 5.4 Swap en t3.micro (paso estándar)

En **t3.micro** (1 GiB RAM) el happy path incluye **2 GiB de swap** para Jenkins + Nginx + Maven con margen.

1. Comprueba: `free -h` — si **Swap** es **0B**, crea swap con los pasos siguientes.
2. Crear **2 GiB** de swap (ajusta `count` si no hay espacio en disco):

```bash
sudo dd if=/dev/zero of=/swapfile bs=1M count=2048
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
grep -q '/swapfile' /etc/fstab || echo '/swapfile swap swap defaults 0 0' | sudo tee -a /etc/fstab
free -h
```

3. No vuelvas a ejecutar `dd` sobre `/swapfile` si ya está activo (`Text file busy` es esperado).
4. Más detalle: [EC2-SWAP-T3MICRO.md](../20-operaciones/EC2-SWAP-T3MICRO.md). Para más carga, valora **t3.small**.

### 5.5 Orden resumido “hasta primer build verde” (checklist EC2)

| # | Acción |
|---|--------|
| 1 | Java 17 + Jenkins + unlock + plugins + usuario admin |
| 2 | SG **80/443** + Nginx + Certbot + **Jenkins URL** HTTPS |
| 3 | **Swap** 2G en t3.micro |
| 4 | `yum install git maven` + `sudo -u jenkins git --version` + `sudo -u jenkins mvn -version` |
| 5 | Docker + usuario `jenkins` en grupo `docker` (si el pipeline usa imagen) |
| 6 | Credencial Gitea (token) + job *Pipeline from SCM* |
| 7 | **generic-model** en `~/.m2` de `jenkins` (antes de confiar en *Test*) |
| 8 | **Build Now** — si pide *Approve Deploy*, **Proceed** |

---

<a id="sec-6"></a>

## 6. Job principal: `pos-online-pipeline` (Pipeline from SCM)

1. **New Item** → **Pipeline** → nombre ej. `pos-online-pipeline`.
2. **Pipeline** → *Definition*: **Pipeline script from SCM**  
   - **Git** → URL del repo en Gitea (HTTPS o SSH según credencial).  
   - **Branch:** `*/main`  
   - **Script Path:** `Jenkinsfile`
3. **Credenciales** para clonar Gitea (si el repo es privado).

### Variables de entorno del job (según dónde corre el POS)

**Solo demo en la misma EC2 que Jenkins** (sin otro servidor):

- Opcional: `REGISTRY` si haces push a registry local.
- No definas `POS_DEPLOY_HOST` → el deploy usa Docker en el propio agente.

**POS en otra EC2** (recomendado para “remoto real”):

| Variable | Ejemplo | Obligatorio |
|----------|---------|-------------|
| `REGISTRY` | `10.0.1.50:5000` | Sí (push + pull) |
| `POS_DEPLOY_HOST` | `3.129.247.127` o `pos.unclic.consulting` | Sí |
| `POS_DEPLOY_USER` | `ec2-user` | No (default) |
| `POS_DEPLOY_KEY_CREDENTIAL_ID` | `pos-deploy-key` | Sí (Secret file .pem) |

Detalle: [ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md](../../repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md).

En Jenkins: **Configure** del job → **Build Environment** / **Parameters** / “Inject environment” según tu versión; muchas veces se usan **Pipeline parameters** o plugin *Environment Injector*. Si no tienes plugin, define valores por defecto en un `environment {}` del Jenkinsfile solo para lab (no commitear secretos).

---

<a id="sec-7"></a>

## 7. Flujo “commit → verificación visual en Jenkins”

1. **En tu PC:** clona el repo (p. ej. `repo-pos-fastflow` del toolkit o el que tengas en Gitea).
2. Haz un cambio trivial (comentario en README), `git commit`, `git push` al remoto **Gitea**.
3. En Jenkins: **Build Now** (o webhook si lo configuraste).
4. Abre el build → **Console Output** (o **Pipeline Steps** / Blue Ocean si lo instalaste).

### Qué deberías ver (orden lógico)

- Checkout / Prepare  
- **Build** (`mvn` compile)  
- **Test**  
- **Package**  
- **Build image** (`docker build`)  
- **Push to registry** (si `REGISTRY` está definido)  
- **Input** “Approve deploy” (si tu Jenkinsfile lo incluye) → pulsar **Proceed**  
- **Cleanup** / **Deploy** (`docker run` local o `ssh` + `docker pull` + `docker run`)  
- **Verify** (`curl` a health)

Línea a línea: [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](../20-operaciones/POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md).

**Éxito:** bola **azul** (stable) y en consola mensaje de health OK o contenedor *running*.

---

<a id="sec-8"></a>

## 8. Postman — petición al POS remoto (tu PC)

1. Instala **Postman** (postman.com).
2. Importa la colección del toolkit:  
   **`docs/postman/pos-online-unclic.postman_collection.json`**  
   (en el repo: `toolkit-fastflow/integrations/web-cuantica/docs/postman/`).
3. Edita la variable de colección **`APP_BASE_URL`**:
   - Si el POS escucha en la IP pública: `http://<IP_POS>:8111`
   - Si usas DNS: `http://pos.unclic.consulting:8111` o **`http://pos.webcuantica.com:8111`** (o `https://pos.webcuantica.com/...` si terminas TLS en Nginx delante del puerto de la app).
4. Ejecuta **Health (actuator)** → `GET {{APP_BASE_URL}}/actuator/health`  
   o **Health (/health)** si tu imagen solo expone `/health`.

**Criterio de éxito:** respuesta **200** y cuerpo con `"status":"UP"` o similar (con **8111** abierto en el **security group** del servidor POS hacia tu red, y el contenedor o JAR en ejecución según tu despliegue).

---

<a id="sec-9"></a>

## 9. Rollback fácil con registry

El pipeline principal sube la imagen con tag **`${BUILD_NUMBER}`** y **`latest`**. Para volver atrás **sin recompilar**:

1. **Manual (SSH al POS):**  
   `docker pull REGISTRY/pos-online:TAG_ANTERIOR` → `docker stop/rm` → `docker run ...:TAG_ANTERIOR`  
   Comandos exactos: [ROLLBACK-POS-DOCKER-REGISTRY.md](../../repo-pos-fastflow/ROLLBACK-POS-DOCKER-REGISTRY.md).

2. **Job Jenkins opcional:** mismo repo Git, **Script Path** = `Jenkinsfile.rollback`, parámetros `ROLLBACK_IMAGE_TAG`, `REGISTRY`, `POS_DEPLOY_HOST`, credencial SSH. Misma documento anterior.

---

<a id="sec-10"></a>

## 10. Checklist del recorrido exitoso (orden desde cero)

- [ ] **EIP** asociadas a Jenkins, Gitea y POS (según diseño) para DNS estable.
- [ ] **DNS** Namecheap (`jenkins`, `gitea`, `pos`, …) con registros **A** a esas IP; comprobado con `dig` y `dig @8.8.8.8`.
- [ ] **Security groups:** Jenkins **22, 80, 443** (+ **8080** solo si aún no usas solo HTTPS; + **8111** si el POS corre en la misma EC2); Gitea **22, 3000**; POS **22, 8111**; registry **5000** si aplica.
- [ ] **Java 17 + Jenkins** instalados, unlock, plugins sugeridos y usuario admin.
- [ ] **Nginx + Certbot** + URL **`https://jenkins.TU_DOMINIO/`** en **Manage Jenkins → System**.
- [ ] **Gitea** accesible; repos creados; credencial Jenkins para clone (**§4.1**).
- [ ] **Git + Maven** con `yum` y verificados: `sudo -u jenkins git --version` y `sudo -u jenkins mvn -version`.
- [ ] **Swap** 2 GiB en **t3.micro** (**§5.4**).
- [ ] **generic-model** en `~/.m2` del usuario **jenkins**.
- [ ] **Docker** en EC2 Jenkins y usuario **jenkins** en grupo `docker` si usas imagen.
- [ ] **Registry** accesible desde Jenkins (push) y desde POS (pull) si hay deploy remoto con `POS_DEPLOY_HOST`.
- [ ] Job **Pipeline from SCM** y variables `REGISTRY` / `POS_DEPLOY_*` según **§6**.
- [ ] **Proceed** pulsado si el pipeline pide aprobación de deploy.
- [ ] **Postman** con `APP_BASE_URL` apuntando al POS y health **200** / **UP**.
- [ ] **Web Cuántica (`webcuantica.com`):** criterios de **§12.9** y cierre **§12.12** (registry + EC2 POS + DNS `pos`).
- [ ] (Opcional) Job **rollback** (`Jenkinsfile.rollback`).
- [ ] (Fase 2) **Terraform / K8s** → [PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md](../90-archivo/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md).

---

<a id="sec-11"></a>

## 11. Documentos relacionados (profundizar)

| Tema | Documento |
|------|-----------|
| Visión del flujo completo (diagrama) | [REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md](../20-operaciones/REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md) |
| HTTPS Jenkins / Gitea | [HTTPS-UNCLIC-GITEA-JENKINS.md](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md), **[HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md](../20-operaciones/HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md)** (Jenkins en AL2), [PASO-1-GUIA-HTTPS-DESDE-CERO.md](../20-operaciones/PASO-1-GUIA-HTTPS-DESDE-CERO.md) |
| Rollback + dominio | [CHECKLIST-DOMINIO-JENKINS-HTTPS-ROLLBACK.md](../20-operaciones/CHECKLIST-DOMINIO-JENKINS-HTTPS-ROLLBACK.md) |
| Qué output esperar en cada comando (producto terminado) | [../60-pos-online/GUIA-FLUJO-FINAL-POS-ONLINE.md](../60-pos-online/GUIA-FLUJO-FINAL-POS-ONLINE.md) |
| Índice general de esta carpeta | [README.md](../README.md) |
| Terraform + Kubernetes (después del pipeline Docker) | [PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md](../90-archivo/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md) |
| Swap en t3.micro | [EC2-SWAP-T3MICRO.md](../20-operaciones/EC2-SWAP-T3MICRO.md) |
| Gitea Docker / EC2 (detalle) | [INSTALAR-GITEA-SELF-HOSTED.md](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md) |
| Namecheap + DNS (pantallas) | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| HTTPS Gitea + Jenkins (tablas iniciales) | [HTTPS-UNCLIC-GITEA-JENKINS.md](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md) |
| Web Cuántica — índice §12 | [WEBCUANTICA-REPLICACION-FASTFLOW.md](../00-inicio/WEBCUANTICA-REPLICACION-FASTFLOW.md) |

---

<a id="sec-12"></a>

## 12. Replicación con dominio propio (Web Cuántica — ej. `webcuantica.com`)

Objetivo: que **George Aguilar** (u otra persona) monte **Gitea** en **`https://gitea.webcuantica.com`**, **Jenkins** en **`https://jenkins.webcuantica.com`**, y conecte ambos **sin adivinar** pasos: DNS Namecheap, Security Groups, Docker, HTTPS, usuario/token, credencial Jenkins y primera vez en Jenkins. Las secciones **§4**, **§4.1**, **§5** y **§6** de esta misma guía siguen aplicando; aquí se concentra la **plantilla de dominio** y el **orden mínimo** para Gitea público con candado.

### 12.1 Variables (cópialas y sustituye una vez)

Define en un bloc de notas (o exporta en shell como en **§12.11**):

| Variable | Ejemplo Web Cuántica | Significado |
|----------|----------------------|-------------|
| `ROOT_DOMAIN` | `webcuantica.com` | Dominio raíz en Namecheap |
| `GITEA_HOST` | `gitea` | Subdominio → **`gitea.webcuantica.com`** |
| `JENKINS_HOST` | `jenkins` | Subdominio → **`jenkins.webcuantica.com`** |
| `POS_HOST` | `pos` | **`pos.webcuantica.com`** (tras registro **A** `pos` → EC2 POS) |
| `IP_GITEA` | *(IPv4 pública EC2 Gitea)* | Tras **Elastic IP**, estable |
| `IP_JENKINS` | *(IPv4 pública EC2 Jenkins)* | Tras **Elastic IP**, estable |
| `IP_POS` | *(IPv4 pública EC2 POS)* | Tras **Elastic IP**, estable |

**FQDN Gitea:** `https://${GITEA_HOST}.${ROOT_DOMAIN}/` — **FQDN Jenkins:** `https://${JENKINS_HOST}.${ROOT_DOMAIN}/`.

### 12.2 Namecheap — DNS con subdominios

1. **Namecheap** → **Domain List** → **`webcuantica.com`** → **Manage** → pestaña **Advanced DNS**.
2. En **HOST RECORDS** → **ADD NEW RECORD** (no borres MX/CNAME de verificación que ya uses).

**Registros mínimos para este laboratorio:**

| Type | Host | Value | TTL | Resultado |
|------|------|--------|-----|-----------|
| **A Record** | `gitea` | `IP_GITEA` (ej. `3.x.x.x`) | Automatic | `gitea.webcuantica.com` → EC2 Gitea |
| **A Record** | `jenkins` | `IP_JENKINS` | Automatic | `jenkins.webcuantica.com` → EC2 Jenkins |
| **A Record** | `pos` | `IP_POS` | Automatic | **Recomendado** para stack completo: `pos.webcuantica.com` → EC2 donde corre el POS (**8111**) |

Opcional: **A** `@` o `www` → landing u otra IP.

3. **Propagación:** 1–30 min típico. En tu PC:
   ```bash
   dig +short gitea.webcuantica.com @8.8.8.8
   dig +short jenkins.webcuantica.com @8.8.8.8
   ```
   Debe coincidir con la IP pública (o Elastic IP) de cada EC2. Si tu PC muestra IP distinta: vaciar caché DNS o usar `dig @8.8.8.8` (igual que **§0 — Comprobar DNS**).

Más capturas y detalle: [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) (sustituye el nombre del dominio).

### 12.3 AWS — EC2 solo para Gitea + Security Group

1. **EC2 → Launch instance**
   - **Name:** ej. `webcuantica-gitea`
   - **AMI:** Amazon Linux 2 o Amazon Linux 2023 (x86_64), **Instance type:** `t3.micro` (Free Tier) o superior si necesitas más RAM
   - **Key pair:** crea/descarga `.pem` para SSH
   - **Network:** VPC por defecto, **Auto-assign public IP:** Enable
   - **Storage:** 8–20 GiB según repos

2. **Security Group (inbound)** — nombre ej. `gitea-webcuantica-sg`:

| Puerto | Protocolo | Origen | Motivo |
|--------|-----------|--------|--------|
| **22** | TCP | Tu IP (`x.x.x.x/32`) o `0.0.0.0/0` en lab | SSH |
| **3000** | TCP | `0.0.0.0/0` *(solo fase inicial)* | Gitea HTTP directo hasta tener Nginx+HTTPS |
| **80** | TCP | `0.0.0.0/0` | **Let’s Encrypt** (HTTP-01) y redirección |
| **443** | TCP | `0.0.0.0/0` | HTTPS público vía Nginx |
| **222** | TCP | opcional | Git por SSH si mapeas `222:22` en Docker (ver [INSTALAR-GITEA-SELF-HOSTED.md](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md)) |

3. **Elastic IP** (recomendado): **EC2 → Elastic IPs → Allocate → Associate** a esta instancia. Vuelve a Namecheap y pon la **IP elástica** en el registro **A** `gitea`.

4. **SSH:** `ssh -i tu-clave.pem ec2-user@IP_GITEA` (usuario `ec2-user` en Amazon Linux).

### 12.4 Instalar Docker + Gitea (Docker Compose)

En la **EC2 Gitea** (resumen alineado a [INSTALAR-GITEA-SELF-HOSTED.md](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md)):

**Amazon Linux 2:**
```bash
sudo yum update -y
sudo yum install -y docker
sudo systemctl enable docker --now
sudo usermod -aG docker ec2-user
# Cerrar sesión SSH y volver a entrar para grupo docker, o usar sudo en los siguientes comandos.
```

**Amazon Linux 2023:**
```bash
sudo dnf update -y
sudo dnf install -y docker
sudo systemctl enable docker --now
sudo usermod -aG docker ec2-user
```

**Docker Compose v2** (si no existe `docker compose`):
```bash
sudo curl -SL "https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo ln -sf /usr/local/bin/docker-compose /usr/bin/docker-compose
```

Crear stack:
```bash
mkdir -p ~/gitea && cd ~/gitea
```

Crea `docker-compose.yml` (versión de imagen ajustable en [dl.gitea.com](https://dl.gitea.com/gitea/)):

```yaml
version: "3"
services:
  server:
    image: docker.gitea.com/gitea:1.25.4
    container_name: gitea
    restart: always
    environment:
      - USER_UID=1000
      - USER_GID=1000
    volumes:
      - ./gitea:/data
      - /etc/timezone:/etc/timezone:ro
      - /etc/localtime:/etc/localtime:ro
    ports:
      - "3000:3000"
      - "222:22"
```

```bash
cd ~/gitea && sudo docker-compose up -d
sudo docker ps
```

Comprueba en navegador: **`http://IP_GITEA:3000`** (o `http://gitea.webcuantica.com:3000` si DNS ya apunta).

### 12.5 Asistente inicial de Gitea (primera vez — HTTP con puerto)

Abre la URL con puerto **:3000** la primera vez. Rellena **como mínimo**:

| Campo | Valor |
|-------|--------|
| **Database** | SQLite3 (ruta por defecto en `/data/...` está bien en Docker) |
| **Server Domain** | `gitea.webcuantica.com` |
| **SSH Server Port** | `222` *(si expones 222 en el SG y en compose)* o déjalo según tu elección |
| **Gitea HTTP Listen Port** | `3000` |
| **Gitea Base URL** | `http://gitea.webcuantica.com:3000/` *(incluye barra final)* |
| **Administrator account** | Usuario admin (ej. `george`), email, contraseña fuerte |

Pulsa **Install Gitea**. Luego inicia sesión como ese administrador.

> **Importante:** cuando actives HTTPS en **§12.6**, cambiarás **ROOT URL** a `https://gitea.webcuantica.com/` (sin puerto). Mientras tanto, el clone HTTPS en Jenkins puede usar la URL con `:3000` o la final `https://...` tras el cambio.

### 12.6 HTTPS público en Gitea (Nginx + Let’s Encrypt en la misma EC2)

**Requisitos:** registro DNS **A** `gitea` → IP correcta; SG con **80** y **443** abiertos.

#### Amazon Linux 2 (mismo patrón que Jenkins en **§5.2**)

```bash
sudo amazon-linux-extras install epel -y
sudo yum install -y nginx certbot python2-certbot-nginx
sudo systemctl enable nginx --now
```

Crear **`/etc/nginx/conf.d/gitea.conf`** (sustituye el FQDN si tu dominio no es webcuantica):

```nginx
server {
    listen 80;
    server_name gitea.webcuantica.com;
    location / {
        proxy_pass http://127.0.0.1:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

```bash
sudo nginx -t && sudo systemctl reload nginx
sudo certbot --nginx -d gitea.webcuantica.com
```

Acepta redirección HTTP → HTTPS. Prueba: **`https://gitea.webcuantica.com`**.

**En Gitea (logueado como admin):** **Site Administration** (rueda) → **Configuration** → **Server**:

- **DOMAIN:** `gitea.webcuantica.com`
- **ROOT URL:** `https://gitea.webcuantica.com/`

Guardar. Si hace falta: `sudo docker restart gitea`.

**Endurecer (opcional):** en el Security Group, **quita** la regla inbound **3000** a `0.0.0.0/0` cuando confirmes que solo entras por **443** (Nginx en localhost sigue llegando al contenedor).

#### Amazon Linux 2023

Suele usarse `dnf install nginx certbot python3-certbot-nginx` (si el paquete existe en tus repos). Si Certbot no trae plugin nginx, usa la ruta **certonly** + proxy manual consultando [HTTPS-UNCLIC-GITEA-JENKINS.md](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md) (ejemplo Ubuntu) o la documentación de Certbot para tu AMI.

```bash
sudo certbot renew --dry-run
```

### 12.7 Usuario operativo + token para Jenkins (no usar admin en jobs)

1. En Gitea: **Sign up** o **Site Administration → User Accounts** y crea un usuario dedicado (ej. `george` para trabajo diario, o `jenkins-ci` solo para CI).
2. Para el pipeline: entra con el usuario que clonará repos **privados**.
3. **Settings** (avatar) → **Applications** → **Generate New Token**
   - **Token name:** `jenkins-webcuantica`
   - **Scopes:** como mínimo acceso de lectura a repositorios (p. ej. **repo** / **read** según versión de Gitea).
4. Copia el token **una sola vez**.

En **Jenkins** → **Manage Jenkins** → **Credentials** → **Global** → **Add Credentials**:

- Kind: **Username with password**
- **Username:** el usuario Gitea (no el token como usuario).
- **Password:** el **token** (Gitea acepta token en lugar de contraseña para HTTPS).
- **ID:** ej. `gitea-webcuantica-token`

En el job **Pipeline from SCM** → **Repository URL:**  
`https://gitea.webcuantica.com/ORG_O_USUARIO/nombre-repo.git`  
**Credentials:** la credencial anterior. Detalle: **§4.1**.

### 12.8 Jenkins — primera vez (admin) y URL pública

En la **EC2 Jenkins** (otra instancia), sigue **§5.1** y **§5.2** sustituyendo el nombre:

1. Primera apertura: `http://IP_JENKINS:8080` → pantalla **Unlock Jenkins**.
2. Contraseña inicial:
   ```bash
   sudo cat /var/lib/jenkins/secrets/initialAdminPassword
   ```
3. **Install suggested plugins** (recomendado en el primer arranque).
4. **Create First Admin User:** usuario (ej. `george`), contraseña, nombre completo, correo.
5. **Instance Configuration:** deja la URL provisional o `http://IP_JENKINS:8080` hasta tener HTTPS.
6. Tras **Nginx + Certbot** para `jenkins.webcuantica.com` (**§5.2**, archivo tipo `jenkins.conf` con `proxy_pass http://127.0.0.1:8080`):  
   **Manage Jenkins → System → Jenkins URL** = **`https://jenkins.webcuantica.com/`**

**Swap en t3.micro** y **Git + Maven + Docker** y **generic-model**: completar **§5.3–5.5** en esta EC2 **antes** del primer build que deba pasar *Test*.

### 12.9 URLs para probar (checklist rápido)

| Paso | URL | Criterio OK |
|------|-----|-------------|
| DNS Gitea | `https://gitea.webcuantica.com` | Carga login Gitea, candado HTTPS |
| DNS Jenkins | `https://jenkins.webcuantica.com` | Login Jenkins, candado HTTPS |
| API salud Gitea (opcional) | `https://gitea.webcuantica.com/api/v1/version` | JSON con versión |
| Clone manual (PC) | `git clone https://gitea.webcuantica.com/usuario/repo.git` | Pide usuario/token o credencial |
| Job Jenkins | Consola del build | *Checkout SCM* OK; stages hasta **Finished: SUCCESS** |
| POS (tras deploy) | `http://pos.webcuantica.com:8111/actuator/health` o `/health` | **200** y `"status":"UP"` |

### 12.10 Orden recomendado (de cero a Gitea + Jenkins + POS operativos)

1. Comprar/configurar dominio en **Namecheap** (`webcuantica.com`).
2. Crear EC2 **Gitea** + **Elastic IP** + SG (**22, 3000, 80, 443**).
3. Registro **A** `gitea` → IP; verificar `dig @8.8.8.8`.
4. Docker + `docker-compose up -d` Gitea; asistente **§12.5**.
5. Nginx + Certbot **§12.6**; actualizar **ROOT URL** en Gitea a `https://.../`.
6. Crear usuario + **token**; crear repo(s) vacíos para el POS **y para `smartbussiness-generic-model`**; subir código + **Jenkinsfile** (rama `main`). Job **`generic-model`** en Jenkins (instala en `~/.m2`): [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md).
7. Crear EC2 **Jenkins** + EIP + SG (**22, 80, 443**, 8080 opcional); registro **A** `jenkins`.
8. Java 17 + Jenkins + unlock + plugins + **usuario admin Jenkins** (**§12.8**).
9. HTTPS Jenkins (**§5.2** con `jenkins.webcuantica.com`); **Jenkins URL** = `https://jenkins.webcuantica.com/`.
10. **Swap** + **git**, **maven**, **docker**, **generic-model** en `.m2` (**§5.3–5.5**).
11. Credencial Jenkins con token Gitea (**§4.1** / **§12.7**); job *Pipeline from SCM* (**§6**).
12. **Registry** accesible desde Jenkins y desde la EC2 POS (misma VPC / SG); definir **`REGISTRY`** en el job.
13. Crear EC2 **POS** + EIP + SG (**22**, **8111** desde la IP del agente Jenkins y desde tu red para Postman); registro **A** `pos` → `IP_POS`.
14. Variables **`POS_DEPLOY_HOST`**, **`POS_DEPLOY_KEY_CREDENTIAL_ID`**, etc. (**§6**) si el deploy es por SSH a la EC2 POS.
15. **Build Now** en `main`; **Proceed** si el pipeline pide aprobación.
16. **Postman** (**§8**) con `APP_BASE_URL` = `http://pos.webcuantica.com:8111` (o la URL final que uses).

### 12.11 Plantilla shell (copiar; solo imprime qué poner en Namecheap)

```bash
# Edita y ejecuta en tu Mac/Linux para ver los FQDN y un recordatorio DNS.
export ROOT_DOMAIN="webcuantica.com"
export IP_GITEA="1.2.3.4"    # sustituir por Elastic IP / IP pública EC2 Gitea
export IP_JENKINS="5.6.7.8"  # sustituir cuando exista la EC2 Jenkins
export IP_POS="9.10.11.12"   # sustituir cuando exista la EC2 POS (stack completo)

echo "Namecheap → Advanced DNS → A records:"
echo "  Host gitea   → ${IP_GITEA}"
echo "  Host jenkins → ${IP_JENKINS}"
echo "  Host pos     → ${IP_POS}"
echo ""
echo "URLs finales (tras HTTPS en Gitea/Jenkins):"
echo "  https://gitea.${ROOT_DOMAIN}"
echo "  https://jenkins.${ROOT_DOMAIN}"
echo "  http://pos.${ROOT_DOMAIN}:8111   (POS típico; añade Nginx+TLS si quieres HTTPS en 443)"
```

### 12.12 Stack POS + registry (cerrar el entorno tipo producción)

Para que **Jenkins**, **Gitea** y el **POS** queden **operando sin pasos a ciegas** (mismo patrón que `unclic.consulting`):

| Pieza | Qué debe quedar cierto |
|-------|-------------------------|
| **VPC / red** | Jenkins puede **push** al registry y la EC2 POS puede **pull** (IP privada en `REGISTRY` o hostname interno; SG con **5000** o el puerto que uses). |
| **Job Jenkins** | `REGISTRY` definido si el pipeline hace *Push to registry* y el POS hace `docker pull`. `POS_DEPLOY_HOST` = IP o `pos.webcuantica.com` si resuelve a la EC2 POS. |
| **EC2 POS** | Docker instalado si el deploy es contenedor; puerto **8111** en SG; clave SSH registrada en Jenkins (**Secret file**) si el Jenkinsfile despliega por SSH. |
| **DNS** | **A** `pos` → **IP_POS** antes de probar Postman por nombre. |
| **Verificación** | **§12.9** (tabla POS) + **§8** Postman; consola Jenkins **Finished: SUCCESS**. |

Detalle de variables y SSH: [ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md](../../repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md). Registry en EC2: [REGISTRY-EC2-GRATIS.md](../20-operaciones/REGISTRY-EC2-GRATIS.md).

### 12.13 Automatización futura (sin adivinar)

Lo que **sí** conviene automatizar con el tiempo (Terraform/Ansible o scripts en el repo del equipo): creación EC2 + SG, instalación Docker, despliegue `docker-compose`, ficheros Nginx y ejecución Certbot (tras DNS), y variables de entorno del job Jenkins. **No** automatices en git: tokens Gitea, contraseñas Jenkins ni `.pem`; guárdalos solo en **Credentials** / gestor de secretos.

---

*Esta guía es el **punto de entrada único**: [índice](#indice-de-navegacion) → DNS/EIP → SG → Gitea+HTTPS → Jenkins+HTTPS → herramientas **§5** → job **§6** → POS+registry **§12.12** → Postman **§8**. Los enlaces de **§11** amplían cada herramienta. Mantén la tabla de la **§1** al día con las IPs de AWS.*

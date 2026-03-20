# Replicar todo: unclic.consulting + Jenkins + Gitea + pos-online

Guía paso a paso para **reproducir desde cero** lo que ya está montado: dominio, DNS, EC2 (Jenkins y Gitea), **HTTPS en Jenkins**, repos en Gitea, jobs en Jenkins (generic-model y pos-online), swap en EC2, y pipeline funcionando. Incluye **qué pantalla ver**, **qué botón o menú usar** y **qué teclear** en cada sitio.

**Índice maestro (replicar estado actual completo):** [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md).

---

## Resumen del resultado final

- **Jenkins** en EC2: **https://jenkins.unclic.consulting** (puerto 443, Nginx + Let's Encrypt wildcard). También http://jenkins.unclic.consulting:8080 (IP 3.15.4.160).
- **Gitea** en EC2: http://gitea.unclic.consulting:3000 (IP 18.223.114.68)
- **Vantive** en EC2: http://vantive.unclic.consulting (IP 3.18.111.60, Nginx, sitio Kings & Joers)
- **Repos en Gitea:** pos-online, smartbussiness-generic-model
- **Repos locales:** repo-pos-fastflow → Gitea pos-online (remote `origin`); smartbussiness-generic-model → Gitea (remote `gitea`). Ver [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md) (dos niveles de Git).
- **Jobs en Jenkins:** generic-model-pipeline (instala JAR en ~/.m2), pos-online-pipeline (build, test, package, **docker build por sh**, deploy 8111)
- **EC2 Jenkins:** Amazon Linux 2, Java 17, Maven 3.0.5, Git, Jenkins, **swap 1 GB** (para evitar OOM en pos-online)

Documentos de referencia: [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md), [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

---

## Fase 1. AWS: EC2 (Jenkins, Gitea, Vantive)

**Dónde:** AWS Console → **EC2** → **Instances** (región **us-east-2**, Ohio).

**Qué hacer:** Tener en ejecución las instancias necesarias:

| Nombre (tag o nombre) | Tipo   | IP pública (ejemplo) | Uso                          |
|------------------------|--------|----------------------|------------------------------|
| fastflow-jenkins-controller | t3.micro | 3.15.4.160          | Jenkins (puerto 8080), builds |
| fastflow-gitea         | t3.micro | 18.223.114.68       | Gitea (puerto 3000)          |
| fastflow-vantive       | t3.micro | 3.18.111.60         | Vantive (Nginx 80/443, sitio Kings & Joers) |

**Vista:** Lista de instancias con columnas Name, Instance ID, Instance state (Running), Public IPv4 address.

**Acción:** Si no existen, **Launch Instance**: Amazon Linux 2 (o 2023 para Vantive), t3.micro, par de claves para SSH. En **Security group**: **22** (SSH); Jenkins **8080**; Gitea **3000**; Vantive **80** y **443**. Anotar las **IP públicas** (cambian si se para/arranca sin Elastic IP).

**Comprobar:** `http://<IP_JENKINS>:8080`, `http://<IP_GITEA>:3000`, `http://<IP_VANTIVE>` o `http://vantive.unclic.consulting`.

---

## Fase 2. Namecheap: DNS para el dominio unclic.consulting

**Dónde:** Namecheap → **Domain List** → clic en **unclic.consulting** → **Manage** → pestaña **Advanced DNS** → sección **HOST RECORDS**.

**Vista:** Tabla con columnas Type, Host, Value, TTL. Botón **ADD NEW RECORD**.

**Qué hacer:** Asegurar registros **A** (o "A + Dynamic DNS") para cada EC2:

| Type | Host   | Value         | TTL        |
|------|--------|---------------|------------|
| A    | jenkins | \<IP_EC2_JENKINS\>  | Automatic  |
| A    | gitea   | \<IP_EC2_GITEA\>    | Automatic  |
| A    | vantive | \<IP_EC2_VANTIVE\>  | Automatic  |

Ejemplo: jenkins → 3.15.4.160, gitea → 18.223.114.68, vantive → 3.18.111.60. En **Host** solo el subdominio. Guardar. No borrar MX/TXT/CNAME que uses para correo o Google.

**Comprobar:** Tras unos minutos, `http://jenkins.unclic.consulting:8080`, `http://gitea.unclic.consulting:3000` y `http://vantive.unclic.consulting` deben abrir las mismas páginas que por IP.

---

## Fase 3. EC2 Jenkins: Java, Maven, Git, Jenkins, swap

**Dónde:** Terminal (SSH) en la EC2 de Jenkins. **Vista:** AWS Console → EC2 → Instances → seleccionar **fastflow-jenkins-controller** → **Connect** → **EC2 Instance Connect** → **Connect** → se abre una pestaña con terminal en el navegador (usuario `ec2-user`).

**Qué teclear (en orden):**

```bash
# Java 17 (Corretto) y Maven
sudo yum install -y java-17-amazon-corretto-devel maven git

# Comprobar
java -version
mvn -version
git --version
```

**Verificar que el usuario jenkins tenga Maven:**

```bash
sudo -u jenkins mvn -version
```

Si sale la versión de Maven, está bien. Si no existe usuario jenkins, Jenkins lo creará al instalarse.

**Instalar Jenkins** (si no está ya): seguir [instalacion/INSTALAR-JENKINS.md](instalacion/INSTALAR-JENKINS.md) o instalación estándar (war o repo yum). Tras el primer arranque, **unlock** con la contraseña inicial que muestra en `/var/lib/jenkins/secrets/initialAdminPassword` y completar el asistente (plugins sugeridos, usuario admin).

**Añadir swap (obligatorio para t3.micro y pos-online):**

**Vista:** Misma terminal SSH en la EC2 de Jenkins. Prompt tipo `[ec2-user@ip-10-0-1-62 ~]$`.

**Qué teclear (copiar y pegar en bloque o línea a línea):**

```bash
sudo dd if=/dev/zero of=/swapfile bs=1M count=1024 status=progress
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
free -h
```

**Qué ver en la consola:** Prompt tipo `[ec2-user@ip-10-0-1-62 ~]$`. Tras `dd` verás progreso en bytes y "1024+0 records in/out". Tras `free -h` algo como:

```
              total        used        free      shared  buff/cache   available
Mem:           940M        634M         67M        408K        237M        168M
Swap:          1.0G          0B        1.0G
```

La línea **Swap** con ~1.0G confirma que el swap está activo. Ver [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md). Opcional: hacer persistente con `echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab`.

---

## Fase 4. Gitea en la EC2 Gitea

**Dónde:** EC2 dedicada a Gitea (IP 18.223.114.68 o gitea.unclic.consulting). Instalar Gitea (Docker o binario) según [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md). En el asistente de instalación: **Server Domain** = `gitea.unclic.consulting`, **Gitea Base URL** = `http://gitea.unclic.consulting:3000/`.

**Vista:** Navegador → `http://gitea.unclic.consulting:3000` → pantalla de login o de “Install Gitea”. Tras instalar: crear **usuario** (ej. alejandro-perez) y **repos vacíos** (o no inicializar) para **pos-online** y **smartbussiness-generic-model**.

**Acción en Gitea (UI):** Clic en **+** (arriba) → **New Repository**. Nombre: `pos-online`, dejar **sin** “Initialize repository” → Create. Repetir para `smartbussiness-generic-model`.

---

## Fase 4b. (Opcional) HTTPS en Jenkins

Para **https://jenkins.unclic.consulting** (certificado Let's Encrypt wildcard, Nginx 443): seguir [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md) y [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md). Certbot (reto DNS), TXT en Namecheap, Nginx en `/etc/nginx/conf.d/jenkins.conf`, regla 443 en security group, Jenkins URL = https://jenkins.unclic.consulting/

---

## Fase 5. Repos locales y push a Gitea

**Importante:** Hay **dos niveles de Git**. El `git status` desde **web-cuantica** (o la raíz del proyecto) corresponde al **repo padre** (toolkit); **no** a pos-online ni generic-model. Para subir a Gitea hay que **entrar en cada repo** y hacer `git add` / `commit` / `push` ahí. Ver [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md).

**Estructura:** `repo-pos-fastflow/` → Gitea pos-online (remote **origin**). `smartbussiness-generic-model/` → Gitea smartbussiness-generic-model (remote **gitea**). Scripts en `scripts/`.

**Pos-online** (dentro de `repo-pos-fastflow`, push con **origin**): `cd repo-pos-fastflow` → `git add . && git commit -m "..." && git push -u origin main`. O usar `scripts/push-repo-pos-fastflow-to-gitea.sh` con GITEA_REPO=pos-online.

**Generic-model** (dentro de `smartbussiness-generic-model`, push con **gitea**): `cd smartbussiness-generic-model` → `git add . && git commit -m "..." && git push -u gitea main`. O `bash scripts/push-generic-model-to-gitea.sh` desde web-cuantica.

**Vista:** En Gitea, al refrescar cada repo debe verse el código (Jenkinsfile, pom.xml, src/, etc.).

---

## Fase 6. Jenkins: credenciales y jobs

**Dónde:** Navegador → **http://jenkins.unclic.consulting:8080** (o http://3.15.4.160:8080). **Importante:** En **Manage Jenkins** → **Nodes** el **Built-In Node** debe ser **Linux**. Si ves **Mac OS X**, estás en el Jenkins de tu Mac, no en la EC2; abre la URL por IP de la EC2.

### 6.1 Credenciales Gitea

**Vista:** Jenkins → **Manage Jenkins** → **Credentials** → **(global)** → **Add Credentials**.

**Qué rellenar:**

- **Kind:** Username with password  
- **Username:** usuario de Gitea (ej. alejandro-perez)  
- **Password:** contraseña (o token) de Gitea  
- **ID:** ej. `gitea-pos-online` (este ID se elige en el job)  
- **Description:** ej. Gitea Jenkins  

Clic **Create**.

### 6.2 Job generic-model-pipeline

**Vista:** Jenkins → **New Item**.

**Acción:**  
- **Enter an item name:** `generic-model-pipeline`  
- **Pipeline** (seleccionar) → **OK**

**Vista:** Página **Configure** del job.

**Qué configurar:**

- **General:** opcional descripción; "Do not allow concurrent builds" si quieres.  
- **Pipeline:**  
  - **Definition:** Pipeline script from SCM  
  - **SCM:** Git  
  - **Repository URL:** `http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model.git`  
    - **Cuidado:** no dejar espacio ni carácter extra (ej. backtick `) delante de `http`.  
  - **Credentials:** elegir la credencial de Gitea (ej. gitea-pos-online)  
  - **Branch Specifier:** `*/main`  
  - **Script Path:** `Jenkinsfile`  

Clic **Save**. Luego **Build Now**. El primer build puede fallar por versiones de plugins Maven (clean/resources); en el repo generic-model están fijadas versiones compatibles con Maven 3.0.5 (ver commits "Maven 3.0.x" en smartbussiness-generic-model). Tras un build exitoso, el artefacto queda en `~/.m2` del usuario jenkins en la EC2.

### 6.3 Job pos-online-pipeline

**Vista:** Jenkins → **New Item**.

**Acción:**  
- **Enter an item name:** `pos-online-pipeline`  
- **Pipeline** → **OK**

**En Configure:**

- **Pipeline:**  
  - **Definition:** Pipeline script from SCM  
  - **SCM:** Git  
  - **Repository URL:** `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git`  
  - **Credentials:** la misma de Gitea  
  - **Branch Specifier:** `*/main`  
  - **Script Path:** `Jenkinsfile`  

**Opcional — encadenar con generic-model:** En **General** no hace falta "Restrict where this project can be run". En **Triggers** activar **Build after other projects are built** y en **Projects to build** poner `generic-model-pipeline`. Así, tras cada build estable de generic-model se lanza pos-online.

Clic **Save**. **Build Now**.

**Qué ver en Console Output:**  
- "Running on Jenkins in **/var/lib/jenkins/workspace/pos-online-pipeline**" (ruta Linux = EC2).  
- Stages: Checkout SCM → Prepare → Build → Test → Lint → Package → (Build image, Push to registry si aplican) → Cleanup → Deploy → Verify instance.  
- Si hubo swap y MAVEN_OPTS en el Jenkinsfile, no debe aparecer "Not enough space". Al final: **Finished: SUCCESS**.

---

## Fase 7. Comprobaciones rápidas

| Dónde | Qué ver / qué hacer |
|-------|----------------------|
| **Manage Jenkins → Nodes** | Built-In Node = **Linux (amd64)**. Si es Mac OS X, estás en el Jenkins local, no en la EC2. |
| **generic-model-pipeline** | Build Now → Console: `mvn clean install -DskipTests`, "Artefacto instalado en ~/.m2". |
| **pos-online-pipeline** | Build Now → Console: Build, Test, Package en verde; al final "Pipeline pos-online OK" o "Finished: SUCCESS". |
| **EC2 (SSH)** | `free -h` → línea Swap ~1.0G. |
| **Gitea** | Repos pos-online y smartbussiness-generic-model con código y Jenkinsfile. |

---

## Errores frecuentes y qué hacer

| Mensaje o síntoma | Causa | Qué hacer |
|--------------------|--------|-----------|
| `protocol '\`http' is not supported` | Backtick o carácter extra en Repository URL | En Configure del job, borrar todo lo que esté **antes** de `http` en Repository URL. |
| `mvn: command not found` | Maven no instalado o job corriendo en otro nodo (Mac) | Instalar Maven en la EC2 (`sudo yum install -y maven`) y asegurar que el job corre en la EC2 (Nodes = Linux). |
| `Could not find artifact ... smartbussiness-generic-model` | generic-model no instalado en ~/.m2 del usuario jenkins | Ejecutar **Build Now** en **generic-model-pipeline** hasta que termine en éxito; luego volver a lanzar pos-online-pipeline. |
| `Not enough space` / `insufficient memory` | EC2 t3.micro sin swap | En la EC2 de Jenkins ejecutar los comandos de swap (Fase 3). Ver [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md). |
| Build corre en `/Users/...` (Mac) | Jenkins usado es el de tu Mac | Abrir **http://3.15.4.160:8080** (IP de la EC2) y crear/configurar los jobs ahí; o quitar túnel SSH que redirija 8080 a tu Mac. |
| maven-clean-plugin / maven-resources-plugin "requires Maven 3.x" | Maven 3.0.5 en EC2, parent Spring Boot usa plugins 3.x | En el pom.xml de smartbussiness-generic-model están fijadas versiones 2.6.1, 2.7, etc. Asegurar que el repo en Gitea tiene esos cambios y volver a Build Now. |

---

## Documentos relacionados

- [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md) — Índice de toda la documentación para replicar el estado actual (HTTPS, repos locales, Jenkinsfiles, avisos).
- [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) — Estado actual y dónde se configura cada cosa.  
- [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) — DNS y URLs.  
- [GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md) — generic-model en Gitea y job Jenkins.  
- [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md) — Swap en la EC2.  
- [EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md) — Instalación manual de generic-model (alternativa al job).  
- [README.md](README.md) — Índice de documentación.

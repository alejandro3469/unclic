# Replicar todo: dominio propio + Jenkins + Gitea + pos-online

Guía paso a paso para **reproducir desde cero** un entorno tipo FastFlow: dominio, DNS, EC2 (Jenkins y Gitea), **HTTPS opcional en Jenkins**, repos en Gitea, jobs en Jenkins (generic-model y pos-online), swap en EC2, y pipeline funcionando. Incluye **qué pantalla ver**, **qué botón o menú usar** y **qué teclear** en cada sitio.

**Valores a sustituir en esta guía:**

- `<TU_DOMINIO>` — tu dominio (ej. `empresa.example`).
- `<IP_JENKINS>`, `<IP_GITEA>` — IPs públicas actuales de cada EC2 (cambian sin Elastic IP).
- `TU_USUARIO` — usuario de Gitea (dueño de los repos).

**Índice maestro:** [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md).

---

## Resumen del resultado final

- **Jenkins** en EC2: idealmente **https://jenkins.&lt;TU_DOMINIO&gt;** (443, Nginx + Let's Encrypt) y/o **http://jenkins.&lt;TU_DOMINIO&gt;:8080** y **http://&lt;IP_JENKINS&gt;:8080**.
- **Gitea** en EC2: **http://gitea.&lt;TU_DOMINIO&gt;:3000** o **http://&lt;IP_GITEA&gt;:3000**.
- **Repos en Gitea:** `pos-online`, `smartbussiness-generic-model` (u otros nombres que elijas).
- **Repos locales:** `repo-pos-fastflow/` → Gitea pos-online (remote `origin`); `smartbussiness-generic-model/` → Gitea (remote `gitea`). Ver [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md).
- **Jobs en Jenkins:** `generic-model-pipeline` (instala JAR en `~/.m2`), `pos-online-pipeline` (build, test, package, docker build por `sh`, deploy 8111 si aplica).
- **EC2 Jenkins:** Amazon Linux 2023 (recomendado), Java 17, Maven, Git, Jenkins, **swap 1 GB** en t3.micro para evitar OOM.

Documentos de referencia: [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md), [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

---

## Fase 1. AWS: EC2 (Jenkins y Gitea)

**Dónde:** AWS Console → **EC2** → **Instances** (ej. región **us-east-2**).

**Qué hacer:** Tener en ejecución al menos **dos** instancias:

| Nombre (tag) | Tipo | IP (placeholder) | Uso |
|--------------|------|------------------|-----|
| fastflow-jenkins-controller | t3.micro | `<IP_JENKINS>` | Jenkins (:8080), builds, a menudo también app POS (:8111) |
| fastflow-gitea | t3.micro | `<IP_GITEA>` | Gitea (:3000) |

Opcional: **tercera** EC2 solo para el POS — ver [50-tecnologias/AWS-EC2.md](../50-tecnologias/AWS-EC2.md).

**Acción:** **Launch Instance**: Amazon Linux 2023 (recomendado), t3.micro, par de claves SSH. **Security group:** **22** (SSH, origen restrictivo); Jenkins **8080** (y **443** si Nginx); Gitea **3000**.

**Comprobar:** `http://<IP_JENKINS>:8080`, `http://<IP_GITEA>:3000`.

---

## Fase 2. DNS: registros A

**Dónde:** Tu registrador → gestión DNS de **TU_DOMINIO** → registros **A** (o equivalente).

| Type | Host | Value | TTL |
|------|------|--------|-----|
| A | jenkins | `<IP_JENKINS>` | Automatic (o la que uses) |
| A | gitea | `<IP_GITEA>` | Automatic |

En **Host** suele ir solo el subdominio (`jenkins`, `gitea`). No borrar MX/TXT/CNAME de correo o verificaciones.

**Comprobar:** Tras propagación, `http://jenkins.<TU_DOMINIO>:8080` y `http://gitea.<TU_DOMINIO>:3000` deben coincidir con el acceso por IP.

---

## Fase 3. EC2 Jenkins: Java, Maven, Git, Jenkins, swap

**Dónde:** SSH a la EC2 Jenkins (EC2 Instance Connect o `ssh -i clave.pem ec2-user@<IP_JENKINS>`).

```bash
sudo yum install -y java-17-amazon-corretto-devel maven git
java -version
mvn -version
git --version
sudo -u jenkins mvn -version
```

**Instalar Jenkins** si falta: [../30-instalacion/INSTALAR-JENKINS.md](../30-instalacion/INSTALAR-JENKINS.md). Unlock con `/var/lib/jenkins/secrets/initialAdminPassword`.

**Swap (recomendado en t3.micro):**

```bash
sudo dd if=/dev/zero of=/swapfile bs=1M count=1024 status=progress
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
free -h
```

Ver [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md). Opcional: persistir en `/etc/fstab`.

---

## Fase 4. Gitea en la EC2 Gitea

**Dónde:** EC2 Gitea (`<IP_GITEA>`). Instalar según [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md). En el asistente: **Server Domain** = `gitea.<TU_DOMINIO>`, **Gitea Base URL** = `http://gitea.<TU_DOMINIO>:3000/` (ajusta si usas HTTPS).

Crear usuario **TU_USUARIO** y repos vacíos **pos-online** y **smartbussiness-generic-model** (+ → New Repository → sin “Initialize” si subes desde local).

---

## Fase 4b. (Opcional) HTTPS en Jenkins

Wildcard / Let's Encrypt + Nginx: [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md), [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md). Sustituye dominios y IPs por los tuyos.

---

## Fase 5. Repos locales y push a Gitea

**Dos niveles de Git:** el repo **padre** (toolkit/monorepo) no es el que subes a Gitea. Entra en cada carpeta de aplicación. Ver [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md).

- **pos-online:** `cd repo-pos-fastflow` → `git push -u origin main` (o script de push del módulo).
- **generic-model:** `cd smartbussiness-generic-model` → `git push -u gitea main`.

---

## Fase 6. Jenkins: credenciales y jobs

**Dónde:** `http://<IP_JENKINS>:8080` o `http://jenkins.<TU_DOMINIO>:8080`. En **Nodes**, Built-In = **Linux** (no Mac local).

### 6.1 Credenciales Gitea

**Manage Jenkins** → **Credentials** → Add **Username with password** (usuario `TU_USUARIO`, token o password), **ID** ej. `gitea-pos-online`.

### 6.2 Job generic-model-pipeline

**New Item** → Pipeline → URL del repo:

`http://gitea.<TU_DOMINIO>:3000/TU_USUARIO/smartbussiness-generic-model.git`

**Branch** `*/main`, **Script Path** `Jenkinsfile`, credencial Gitea. **Build Now**.

### 6.3 Job pos-online-pipeline

Misma configuración con:

`http://gitea.<TU_DOMINIO>:3000/TU_USUARIO/pos-online.git`

Opcional: **Build after other projects** → `generic-model-pipeline`.

**Console:** workspace bajo `/var/lib/jenkins/workspace/...` (Linux). **Finished: SUCCESS**.

---

## Fase 7. Comprobaciones rápidas

| Dónde | Qué ver |
|-------|---------|
| **Nodes** | Built-In = Linux |
| **generic-model-pipeline** | Build OK, artefacto en `~/.m2` |
| **pos-online-pipeline** | Stages en verde |
| **SSH** | `free -h` → Swap ~1G |
| **Gitea** | Código visible en repos |

---

## Errores frecuentes

| Síntoma | Qué hacer |
|---------|-----------|
| Carácter extra delante de `http` en URL del repo | Limpiar campo Repository URL en el job |
| `mvn: command not found` | Maven en EC2; job en nodo Linux |
| Falta artefacto generic-model | Build OK de `generic-model-pipeline` antes que pos-online |
| OOM / memoria | Swap Fase 3 |
| Build en `/Users/...` (Mac) | Usar URL con **IP de la EC2**; revisar túneles y `/etc/hosts` |
| Plugins Maven vs Maven 3.0.x | Alinear versiones en `pom.xml` del generic-model con tu versión de Maven |

---

## Documentos relacionados

- [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md)
- [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md)
- [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md)
- [GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md)
- [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md)
- [EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md)
- [README.md](../README.md)

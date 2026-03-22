# Replicar el flujo completo: Gitea, repos, Jenkins, código local, AWS — cómo conecta todo

> **¿Quieres una sola guía lineal (commit → Jenkins → Postman → rollback)?** Usa primero **[GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)**. Este documento amplía con **diagrama**, tabla de componentes y fases detalladas.

Guía para **replicar de nuevo** todo el flujo que ya hemos montado dos veces: **AWS** (EC2), **DNS** (Namecheap), **Gitea** (repos), **Jenkins** (jobs y pipeline), **código local** (push a Gitea) y **servidor POS dedicado** (deploy remoto). Incluye cómo encaja cada pieza y el orden recomendado.

**Estado documentado:** replicado dos veces; este doc permite repetir el proceso desde cero o en un nuevo entorno.

---

## 1. Cómo conecta todo (visión general)

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│  DESARROLLO LOCAL (Mac/PC)                                                        │
│  repo-pos-fastflow/   →  git push gitea main   →  Gitea (repo pos-online-fastflow)│
└─────────────────────────────────────────────────────────────────────────────────┘
                                          │
                                          ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│  GITEA (EC2 fastflow-gitea)                                                       │
│  https://gitea.<TU_DOMINIO>  ·  Repos: pos-online-fastflow, generic-model         │
└─────────────────────────────────────────────────────────────────────────────────┘
                                          │
                    Jenkins clona (Pipeline from SCM) + credencial Gitea
                                          ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│  JENKINS (EC2 fastflow-jenkins-controller)                                        │
│  https://jenkins.<TU_DOMINIO>  ·  Job: pos-online-fastflow                        │
│  Pipeline: Checkout → Build → Test → Package → [Approve Deploy] → Cleanup → Deploy │
└─────────────────────────────────────────────────────────────────────────────────┘
                                          │
              Deploy local (8111 en Jenkins)  O  Deploy remoto (SSH + scp)
                                          │
                    Si POS_DEPLOY_HOST definido:
                                          ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│  SERVIDOR POS (EC2 fastflow-pos-demo, opcional)                                   │
│  https://pos.<TU_DOMINIO>  ·  Nginx → Java (8111)  ·  JAR en /opt/pos             │
└─────────────────────────────────────────────────────────────────────────────────┘
```

| Origen | Destino | Qué ocurre |
|--------|---------|------------|
| **Código local** | **Gitea** | `git push gitea main` sube el repo (pos-online-fastflow) a Gitea. |
| **Gitea** | **Jenkins** | El job "pos-online-fastflow" está configurado con **Pipeline from SCM** y la URL de Gitea; Jenkins clona el repo (con credencial) y ejecuta el **Jenkinsfile**. |
| **Jenkins** | **Build** | Maven compila y empaqueta; si existe generic-model en ~/.m2 (o en el agente), el build pasa. |
| **Jenkins** | **Deploy** | Sin variables: despliega en la misma EC2 de Jenkins (puerto 8111). Con **POS_DEPLOY_HOST** y credencial SSH: hace **scp** del JAR a la EC2 POS y **ssh** para ejecutar `java -jar`. |
| **Usuario** | **POS** | Tras el deploy, la app en **http://&lt;IP_POS&gt;:8111** o **https://pos.&lt;TU_DOMINIO&gt;** si configuraste Nginx + TLS en la EC2 POS. |

---

## 2. Estado actual (referencia para replicar)

**Sustituye** `<TU_DOMINIO>`, `<IP_JENKINS>`, `<IP_GITEA>`, `TU_USUARIO` por tus valores. Las IPs **cambian** sin Elastic IP — confirma en AWS Console.

| Componente | Nombre EC2 | IP (placeholder) | URL / uso |
|------------|------------|------------------|-----------|
| **Jenkins** | fastflow-jenkins-controller | `<IP_JENKINS>` | `https://jenkins.<TU_DOMINIO>` (o :8080) |
| **Gitea** | fastflow-gitea | `<IP_GITEA>` | `https://gitea.<TU_DOMINIO>` (o :3000) |
| **Landing** | (EC2 Nginx u hosting) | `<IP_LANDING>` | `https://<TU_DOMINIO>`, `www` |
| **POS (dedicado, opc.)** | fastflow-pos-demo | `<IP_POS>` | `https://pos.<TU_DOMINIO>` si aplica |

| Repo en Gitea | URL (patrón) | Rama |
|---------------|--------------|------|
| pos-online-fastflow | `https://gitea.<TU_DOMINIO>/TU_USUARIO/pos-online-fastflow` | main |
| (generic-model) | según tu org/usuario | main |

| Job Jenkins | Repo Gitea | Script Path | Rama |
|--------------|------------|-------------|------|
| pos-online-fastflow | pos-online-fastflow (`TU_USUARIO`) | Jenkinsfile | main |

---

## 3. Orden de replicación (paso a paso)

Sigue este orden para replicar todo el flujo desde cero.

### Fase 1. AWS: EC2

**Objetivo:** Tener las instancias encendidas y anotar sus IP públicas.

1. **AWS Console** → EC2 → Instances (región **us-east-2** u la que uses).
2. Crear o identificar:
   - **fastflow-jenkins-controller** (t3.micro): Jenkins, Maven, Java 17, Git.
   - **fastflow-gitea** (t3.micro): Gitea (puerto 3000).
   - **EC2 landing** (t3.micro, opcional): Nginx para `www` / apex del dominio.
   - **fastflow-pos-demo** (t3.micro, opcional): deploy del POS en servidor dedicado.
3. **Security groups:**
   - Jenkins: SSH (22), 8080 (y 443 si HTTPS), 8111 si deploy local en la misma máquina.
   - Gitea: SSH (22), 3000 (y 443 si HTTPS). Nginx: `client_max_body_size 100m` si vas a hacer push grandes.
   - POS-demo: SSH (22), 80, 443; TCP 8111 desde la IP de Jenkins (para Verify del pipeline).

**Docs:** [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 1, [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md) Paso 1.

---

### Fase 2. DNS (Namecheap)

**Objetivo:** Que los hostnames de **&lt;TU_DOMINIO&gt;** apunten a las EC2.

1. Tu registrador → zona DNS de **&lt;TU_DOMINIO&gt;** → registros **A** (o CNAME).
2. Ejemplo de registros **A**:

| Type | Host | Value (IP) |
|------|------|------------|
| A | @ | `<IP_LANDING>` (landing) |
| A | www | `<IP_LANDING>` |
| A | jenkins | `<IP_JENKINS>` |
| A | gitea | `<IP_GITEA>` |
| A | pos | `<IP_POS>` (si existe instancia POS dedicada) |

3. Esperar unos minutos a que propague.

**Docs:** [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 2, [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

---

### Fase 3. Gitea: instalación y repos

**Objetivo:** Gitea corriendo y repos creados para que Jenkins clone.

1. **Conectar por SSH** a la EC2 de Gitea (o usar EC2 Instance Connect).
2. **Instalar Gitea** (Docker o binario): ver [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md).
3. **Primer arranque:** abrir http://&lt;IP_GITEA&gt;:3000 (o https://gitea.unclic.consulting), completar asistente (Base URL, admin).
4. **Crear repositorio** (ej. **pos-online-fastflow**): vacío, sin README si vas a hacer push desde local.
5. **HTTPS (opcional):** Nginx + Certbot en la EC2 Gitea para https://gitea.unclic.consulting; en Gitea → Configuration → ROOT URL = `https://gitea.unclic.consulting/`. Si usas Nginx como proxy, añadir `client_max_body_size 100m` para pushes grandes.

**Docs:** [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md), [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md).

---

### Fase 4. Jenkins: instalación, job y credenciales

**Objetivo:** Jenkins instalado, job que clona desde Gitea y ejecuta el Jenkinsfile (rama **main**).

1. **Conectar por SSH** a la EC2 de Jenkins.
2. **Java 17, Maven, Git:**  
   `sudo dnf install -y java-17-amazon-corretto maven git` (Amazon Linux 2023) o equivalente.
3. **Instalar Jenkins:** [../30-instalacion/INSTALAR-JENKINS.md](../30-instalacion/INSTALAR-JENKINS.md). Unlock con contraseña de `/var/lib/jenkins/secrets/initialAdminPassword`, completar asistente.
4. **Swap (recomendado en t3.micro):** [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md).
5. **Credenciales Gitea:** Manage Jenkins → Credentials → Add → Username and password (o token). ID ej. **gitea-pos-online**.
6. **Job:** New Item → **Pipeline** → nombre **pos-online-fastflow**.
   - **Pipeline** → Definition: **Pipeline script from SCM**.
   - **SCM:** Git.
   - **Repository URL:** `https://gitea.unclic.consulting/<usuario>/pos-online-fastflow` (el que creaste en Gitea).
   - **Credentials:** la de Gitea.
   - **Branch Specifier:** **main** (no master).
   - **Script Path:** `Jenkinsfile`.
7. **Deploy remoto (opcional):** Si quieres que el POS se despliegue en la EC2 **fastflow-pos-demo**:
   - Credentials → **Secret file** → subir el .pem de la EC2 POS → ID ej. **pos-deploy-key**.
   - En el job, variables de entorno (o Inject environment): **POS_DEPLOY_HOST** = IP de fastflow-pos-demo, **POS_DEPLOY_USER** = ec2-user, **POS_DEPLOY_PATH** = /opt/pos, **POS_DEPLOY_KEY_CREDENTIAL_ID** = pos-deploy-key.

**Docs:** [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md), [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md), [repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md](../../repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md).

---

### Fase 5. Código local: remotes y push a Gitea

**Objetivo:** Que tu repo local (repo-pos-fastflow) suba el código a Gitea para que Jenkins lo clone.

1. **En tu Mac/PC**, en la carpeta del repo (ej. `repo-pos-fastflow` o el clon de pos-online-fastflow):
   ```bash
   git remote add gitea https://gitea.unclic.consulting/<usuario>/pos-online-fastflow.git
   # Si ya tienes origin, mantenerlo; gitea es el remote para este flujo.
   git branch -M main   # si tu rama se llama master, renombrar a main
   git push -u gitea main
   ```
2. Si el repo en Gitea es **privado**, usar usuario/contraseña o token cuando pida credenciales (o configurar credential helper).
3. **generic-model:** Si el build del POS depende de **smartbussiness-generic-model**, ese artefacto debe estar en el agente de Jenkins (por ejemplo: job que clona generic-model y hace `mvn install`, o publicarlo en un repo Maven). Ver [GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md).

**Docs:** [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md), [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md).

---

### Fase 6. Servidor POS dedicado (opcional)

**Objetivo:** EC2 solo para el POS, con Nginx y HTTPS, lista para que Jenkins haga deploy remoto.

1. **EC2 fastflow-pos-demo** (ya creada en Fase 1). Conectar por SSH.
2. **Java 17:** `sudo dnf install -y java-17-amazon-corretto`
3. **Directorio:** `sudo mkdir -p /opt/pos && sudo chown ec2-user:ec2-user /opt/pos`
4. **Nginx:** `sudo dnf install -y nginx`. Crear `/etc/nginx/conf.d/pos.conf` con `server_name pos.unclic.consulting` y `proxy_pass http://127.0.0.1:8111`.
5. **Certbot:** `sudo dnf install -y certbot python3-certbot-nginx` y `sudo certbot --nginx -d pos.unclic.consulting`. Security group: puertos 80 y 443 abiertos desde internet para que Let's Encrypt pueda validar.
6. **Security group:** TCP 8111 desde la IP de Jenkins (para el stage Verify del pipeline).

Tras el primer deploy desde Jenkins, la app quedará en **https://pos.unclic.consulting**.

**Docs:** [PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md](../90-archivo/PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md), [repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md](../../repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md).

---

## 4. Resumen del flujo (una vez replicado)

1. **Desarrollador:** hace cambios en `repo-pos-fastflow` y ejecuta `git push gitea main`.
2. **Gitea:** recibe el push; el repo pos-online-fastflow queda actualizado.
3. **Jenkins:** (con Poll SCM o manual) clona el repo desde Gitea, ejecuta el Jenkinsfile (Prepare → Build → Test → Lint → Package → [Approve Deploy] → Cleanup → Deploy → Verify).
4. **Deploy:** Si **POS_DEPLOY_HOST** está definido, Jenkins copia el JAR a la EC2 POS y arranca la app allí; si no, la arranca en la propia EC2 de Jenkins (puerto 8111).
5. **Usuario final:** Accede a la app en **http://&lt;IP_JENKINS&gt;:8111** o **https://pos.unclic.consulting** según dónde se haya desplegado.

---

## 5. Documentos relacionados

| Tema | Documento |
|------|------------|
| Replicar unclic + Jenkins + Gitea (paso a paso antiguo) | [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) |
| Replicar pos-online FastFlow (IPs y pasos) | [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md) |
| Índice estado actual | [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md) |
| Infraestructura actual | [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) |
| Instalar Gitea | [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md) |
| Instalar Jenkins | [../30-instalacion/INSTALAR-JENKINS.md](../30-instalacion/INSTALAR-JENKINS.md) |
| Deploy POS en otro servidor | [repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md](../../repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md) |
| Plan demo (POS dedicado, email gate) | [PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md](../90-archivo/PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md) |
| DNS Namecheap | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| Índice documentación | [README.md](../README.md) |

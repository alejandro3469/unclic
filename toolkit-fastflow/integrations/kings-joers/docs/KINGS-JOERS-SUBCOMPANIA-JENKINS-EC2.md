# Kings & Joers: subcompañía, Jenkins propio y EC2

Cada **subcompañía** tiene **su propio Jenkins** y sus propias instancias de despliegue. Para **Kings & Joers** se crea:

1. **Nueva EC2 para Jenkins** (solo Kings & Joers).
2. **Nueva EC2 para el servidor Vantive** (donde se sirve la app).
3. **Nuevo repo** para Vantive (en Gitea).

Así web-cuantica (unclic) sigue con su Jenkins y sus jobs; Kings & Joers tiene todo separado.

---

## Resumen de instancias Kings & Joers

| Nombre (tag) | Tipo | Uso | Puertos |
|--------------|------|-----|---------|
| **fastflow-jenkins-kings-joers** | t3.micro | Jenkins de la subcompañía | 22, 8080, 443 (opcional) |
| **fastflow-vantive** | t3.micro | Servidor app Vantive (Nginx) | 22, 80, 443 (opcional) |

Ambas en la misma región (ej. **us-east-2**). Mismo key pair para poder usar SSH desde el Jenkins hacia la EC2 Vantive.

---

## Fase 1. Crear EC2 para Jenkins Kings & Joers

**Dónde:** AWS Console → **EC2** → **Instances** (región **us-east-2**).

1. **Launch Instance**
2. **Name:** `fastflow-jenkins-kings-joers`
3. **AMI:** Amazon Linux 2
4. **Instance type:** **t3.micro**
5. **Key pair:** Elegir o crear uno (guardar .pem; lo usarás también para la EC2 Vantive si quieres deploy por SSH desde este Jenkins).
6. **Network settings — Create security group:**
   - Name: `jenkins-kings-joers-sg`
   - **Inbound:** SSH (22), **Custom TCP 8080** (Jenkins), opcional 443 (HTTPS).
7. **Launch instance**

**En esta EC2 instalar:** Java 17, Git, Jenkins (mismo patrón que en web-cuantica). Ver en el toolkit: `web-cuantica/docs/instalacion/INSTALAR-JENKINS.md` o `web-cuantica/docs/REPLICAR-UNCLIC-COMPLETO.md` Fase 3. Opcional: swap 1 GB si usas builds pesados (t3.micro tiene 1 GB RAM).

**URL Jenkins Kings & Joers:** `http://<IP_JENKINS_KINGS_JOERS>:8080`. Opcional: DNS (ej. jenkins.kingsjoers.com) y HTTPS.

---

## Fase 2. Crear EC2 para servidor Vantive

1. **Launch Instance**
2. **Name:** `fastflow-vantive`
3. **AMI:** Amazon Linux 2
4. **Instance type:** **t3.micro**
5. **Key pair:** **El mismo** que el Jenkins de Kings & Joers (para que el pipeline pueda hacer SSH desde Jenkins a esta EC2).
6. **Network settings — Create security group:**
   - Name: `vantive-sg`
   - **Inbound:** SSH (22), HTTP (80), HTTPS (443 opcional).
7. **Launch instance**

**En esta EC2:** Instalar Nginx (`sudo yum install -y nginx`, `systemctl start nginx`, `systemctl enable nginx`). El pipeline copiará los archivos de la app a `/usr/share/nginx/html/`.

**Anotar la IP** (o dominio si configuras DNS): es el **VANTIVE_HOST** que usarás en el job de Jenkins.

---

## Fase 3. Nuevo repo para Vantive en Gitea

En Gitea (el mismo servidor o uno dedicado): **New Repository** → nombre **vantive-app** (o **kings-joers/vantive** si usas organizaciones). No inicializar si subes desde local.

Subir el código desde la carpeta **kings-joers/vantive/** del toolkit:

```bash
cd toolkit-fastflow/integrations/kings-joers/vantive
git init
git add .
git commit -m "Vantive app + Jenkinsfile FastFlow (Kings & Joers)"
git branch -M main
git remote add origin <URL_REPO_GITEA>
git push -u origin main
```

---

## Fase 4. Configurar el Jenkins de Kings & Joers

1. **Credenciales Gitea** (para clonar el repo): Username + password o token. ID ej. `gitea-kings-joers`.
2. **Credencial SSH para deploy Vantive:** SSH Username with private key, usuario `ec2-user`, clave .pem de la EC2 **fastflow-vantive**. ID: `vantive-deploy-ssh`.
3. **Job vantive-pipeline:** Pipeline from SCM, repo del paso 3, branch main, Script Path `Jenkinsfile`.
4. **Variable VANTIVE_HOST:** En el job, inyectar variable de entorno `VANTIVE_HOST` = IP de la EC2 fastflow-vantive (o dominio si tienes DNS).

Tras **Build Now**, la app se despliega en el servidor Vantive y queda accesible en **http://\<IP_VANTIVE\>** (o el dominio que uses).

---

## Resumen: un Jenkins por subcompañía y subclientes

| Subcompañía | Jenkins | Servidores de apps (subclientes) |
|-------------|---------|-----------------------------------|
| **web-cuantica** (unclic) | EC2 jenkins.unclic.consulting | pos-online (8111), etc. |
| **Kings & Joers** | EC2 fastflow-jenkins-kings-joers | EC2 fastflow-vantive (Vantive); previsto: fastflow-baxter (Baxter). |

Bajo Kings & Joers hay **subclientes** (Vantive, Baxter): cada uno con su repo, su EC2 y su subdominio. Mismo Jenkins, jobs distintos. Por ahora el foco es **Vantive**. Baxter: [BAXTER-SUBCLIENTE-KINGS-JOERS.md](BAXTER-SUBCLIENTE-KINGS-JOERS.md). Vantive paso a paso: [VANTIVE-SERVIDOR-Y-PIPELINE-DESDE-CERO.md](VANTIVE-SERVIDOR-Y-PIPELINE-DESDE-CERO.md).

# Replicar todo lo hecho: pos-online + FastFlow (Jenkins, Gitea, Docker, deploy manual)

Guía para **reproducir desde cero** el entorno pos-online FastFlow tal como está hoy: EC2 Jenkins con Java/Maven/Jenkins/Docker, Gitea con el repo pos-online, pipeline con stages hasta **deploy manual** (aprobación antes de Cleanup/Deploy/Verify), verificación de instancia y envío de peticiones. Incluye IPs actuales y enlaces a cada doc de detalle.

**Resultado final:** Un commit en `main` → Build → Test → Lint → Package → (Build image si hay Dockerfile) → pipeline se detiene en **Approve Deploy** → al pulsar "Desplegar" se ejecutan Cleanup → Deploy → Verify; la app queda en la misma EC2 en el puerto **8111** y se puede comprobar que inicia y recibe peticiones.

---

## 1. Resumen del estado a replicar

| Componente | Qué hay | Dónde |
|------------|---------|--------|
| **EC2 Jenkins** | fastflow-jenkins-controller. Java 17, Maven, Git, Jenkins, **Docker**, **swap 1 GB**. Puerto 8080 (y 443 si HTTPS). | IP 18.119.157.22 (jenkins.unclic.consulting) |
| **EC2 Gitea** | fastflow-gitea. Gitea en 3000. Repos: pos-online, smartbussiness-generic-model. | IP 13.58.58.245 (gitea.unclic.consulting:3000) |
| **Pipeline pos-online** | Prepare → Build → Test → Lint → Package → Build image (si Docker + Dockerfile) → Push to registry (si REGISTRY) → **Approve Deploy** (manual, solo main) → Cleanup → Deploy → Verify instance | Job **pos-online-pipeline** en Jenkins, Jenkinsfile en repo pos-online (Gitea) |
| **Deploy** | Manual: el pipeline se detiene en "Approve Deploy"; al pulsar "Desplegar" se ejecuta pkill + nohup java -jar en puerto **8111** en la EC2 de Jenkins. | [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md) |
| **App pos-online** | JAR en la misma EC2 que Jenkins, puerto **8111**; log en `/tmp/pos-online.log`. | Comprobar con `curl http://18.119.157.22:8111/actuator/health` (Security Group con TCP 8111 abierto) |

Referencia de servidores e IPs: [SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md).

---

## 2. Orden de replicación (pasos numerados)

Sigue este orden; cada paso enlaza a la doc detallada.

### Paso 1. AWS: EC2 (Jenkins y Gitea)

- **Región:** us-east-2 (Ohio).
- **Instancias:** fastflow-jenkins-controller (t3.micro), fastflow-gitea (t3.micro). Opcional: fastflow-vantive para sitio estático.
- **Security Groups:** SSH (22); Jenkins 8080 (y 443 si usas HTTPS); Gitea 3000; **para la app pos-online: TCP 8111** (origen según quién vaya a acceder: 0.0.0.0/0 para pruebas, o IP concreta).
- **Anotar IP públicas** (o usar Elastic IP para que no cambien).

Detalle: [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 1, [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md).

---

### Paso 2. DNS (Namecheap u otro)

- Registros **A**: jenkins → IP de la EC2 Jenkins; gitea → IP de la EC2 Gitea.
- Comprobar: `http://jenkins.unclic.consulting:8080`, `http://gitea.unclic.consulting:3000`.

Detalle: [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 2, [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

---

### Paso 3. EC2 Jenkins: Java, Maven, Git, Jenkins, swap

Conectar por **SSH** o **EC2 Instance Connect** a la EC2 de Jenkins (ej. 18.119.157.22).

```bash
# Java 17, Maven, Git
sudo yum update -y
sudo yum install -y java-17-amazon-corretto-devel maven git

# Verificar
java -version
mvn -version
git --version
```

**Instalar Jenkins** según [instalacion/INSTALAR-JENKINS.md](instalacion/INSTALAR-JENKINS.md) (o doc equivalente). Tras el primer arranque: unlock con la contraseña de `/var/lib/jenkins/secrets/initialAdminPassword`, completar asistente, crear usuario admin.

**Swap 1 GB (recomendado en t3.micro para evitar OOM en Maven):**

```bash
sudo dd if=/dev/zero of=/swapfile bs=1M count=1024 status=progress
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
free -h   # debe mostrar Swap ~1.0G
```

Detalle: [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 3, [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md).

---

### Paso 4. EC2 Jenkins: instalar Docker y permitir uso a Jenkins (y opcional ec2-user)

Para que el pipeline pueda ejecutar el stage **Build image** (y opcionalmente Push to registry):

```bash
# Amazon Linux 2
sudo yum install -y docker
sudo systemctl enable docker
sudo systemctl start docker
sudo systemctl status docker   # active (running)

# Usuario jenkins pueda usar Docker sin sudo
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins
```

**(Opcional)** Para usar `docker` desde SSH como **ec2-user** sin "permission denied":

```bash
sudo usermod -aG docker ec2-user
# Cerrar sesión y volver a entrar, o: newgrp docker
```

Detalle: [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md).

---

### Paso 5. Gitea: instalar y crear repos (pos-online, generic-model)

- En la EC2 Gitea: instalar Gitea (Docker o binario) según [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md).
- Crear usuario (ej. alejandro-perez) y repos **pos-online** y **smartbussiness-generic-model** (vacíos o con código).
- Configurar **pos-online** en Gitea con la URL del repo (ej. `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git`).

Detalle: [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 4.

---

### Paso 6. Repo pos-online: Jenkinsfile con deploy manual

El **Jenkinsfile** del repo pos-online en Gitea debe incluir:

- Stages: Prepare → Build → Test → Lint → Package → Build image (si existe Dockerfile y `docker` está) → Push to registry (si `REGISTRY` definido) → **Approve Deploy** (solo rama `main`) → Cleanup → Deploy → Verify instance.
- **Approve Deploy:** stage con `input(message: '...', ok: 'Desplegar')` para que el pipeline se detenga hasta que alguien pulse "Desplegar".
- **Deploy:** en main, tras aprobar: `pkill -f "pos-online.*jar"`; `nohup java -jar target/pos-online-*.jar --server.port=8111 > /tmp/pos-online.log 2>&1 &`.
- **Verify instance:** `curl` a `http://localhost:8111/actuator/health` o `/health`.

Referencia del Jenkinsfile (copiar/adaptar al repo en Gitea): [../repo-pos-fastflow/Jenkinsfile](../repo-pos-fastflow/Jenkinsfile). Descripción de cada stage: [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md).

---

### Paso 7. Jenkins: credenciales Gitea y job pos-online-pipeline

- **Jenkins** → Manage Jenkins → Credentials → añadir credencial para Gitea (usuario + contraseña o token). ID ej. `gitea-pos-online`.
- **New Item** → nombre **pos-online-pipeline** → tipo **Pipeline**.
- **Configure:** Pipeline from SCM → Git → Repository URL = `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git` (o la URL real), Credentials = la creada, Branch = `main`, Script Path = `Jenkinsfile`.
- Guardar. **Build Now**.

Detalle: [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 6, [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md).

---

### Paso 8. Security Group: abrir puerto 8111

Para que la app pos-online reciba peticiones desde fuera de la EC2:

- AWS Console → EC2 → Instances → **fastflow-jenkins-controller** → Security → Security group.
- **Edit inbound rules** → Add rule: Type **Custom TCP**, Port **8111**, Source (ej. **0.0.0.0/0** para pruebas o IP concreta).

Detalle: [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md) § 3.1.

---

### Paso 9. Comprobar que la instancia inicia y recibe peticiones

Tras un build en **main** en el que se haya pulsado **Desplegar** en el stage Approve Deploy:

**En la EC2 (SSH):**

```bash
pgrep -af "pos-online.*jar"
sudo ss -tlnp | grep 8111
curl -s http://localhost:8111/actuator/health
tail -50 /tmp/pos-online.log
```

**Desde tu PC o otra máquina:**

```bash
curl -s http://18.119.157.22:8111/actuator/health
# o en navegador: http://18.119.157.22:8111/actuator/health
```

Detalle: [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md).

---

### Paso 10. (Opcional) Otro servidor para pos-online

Por defecto la app corre en la **misma EC2 que Jenkins**. Si quieres separar (Jenkins en una EC2, pos-online en otra): crear una segunda EC2, abrir 8111 y 22 en su Security Group, y modificar el stage **Deploy** del Jenkinsfile para hacer SSH a esa EC2, copiar el JAR (o usar imagen Docker) y arrancar la app allí. Credenciales SSH en Jenkins.

Detalle: [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md) § 4, [RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md).

---

## 3. Checklist rápido de replicación

| # | Acción | Doc |
|---|--------|-----|
| 1 | EC2 Jenkins y Gitea en us-east-2; Security Groups 22, 8080, 3000, **8111** | REPLICAR-UNCLIC-COMPLETO Fase 1 |
| 2 | DNS A: jenkins, gitea | REPLICAR-UNCLIC-COMPLETO Fase 2 |
| 3 | EC2 Jenkins: Java 17, Maven, Git, Jenkins, **swap 1 GB** | EC2-SWAP-T3MICRO, INSTALAR-JENKINS |
| 4 | EC2 Jenkins: **Docker** (yum), jenkins en grupo docker, restart Jenkins; opcional ec2-user | POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2 |
| 5 | Gitea instalado; repos pos-online y generic-model | REPLICAR-UNCLIC-COMPLETO Fase 4 |
| 6 | Jenkinsfile en repo pos-online con **Approve Deploy** (input) y Deploy/Verify | repo-pos-fastflow/Jenkinsfile |
| 7 | Jenkins: credencial Gitea, job pos-online-pipeline (Pipeline from SCM, Jenkinsfile) | REPLICAR-UNCLIC-COMPLETO Fase 6 |
| 8 | Security Group Jenkins: **TCP 8111** inbound | POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES |
| 9 | Build Now en main → Approve Deploy → Desplegar → comprobar health y peticiones | POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES |

---

## 4. Documentos relacionados

| Tema | Documento |
|------|-----------|
| Índice general replicar estado | [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md) |
| Guía paso a paso completa (pantallas, botones) | [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) |
| Servidores e IPs actuales | [SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md) |
| Hecho y por hacer pos-online | [POS-ONLINE-FASTFLOW-HECHO-Y-POR-HACER.md](POS-ONLINE-FASTFLOW-HECHO-Y-POR-HACER.md) |
| Pipeline: logs explicados | [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md) |
| Ver instancia y enviar peticiones | [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md) |
| Instalar Docker en EC2 Jenkins | [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) |
| Swap en t3.micro | [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md) |

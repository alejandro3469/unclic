# Guía AWS gratis: sistema retail automatizado con FastFlow (como en producción)

**Objetivo:** Que **cada integrante del equipo** y **cada cliente** tenga **su propio sistema retail automatizado con FastFlow, gratis, operando como en producción** en AWS: pipeline (Jenkins), build, tests, registry y app; instalar, ver, usar y probar cada parte dentro del Free Tier.

---

## 1. Modelo: cada quien con su sistema retail automatizado (FastFlow gratis, como en producción)

| Quién | Cómo tiene su sistema |
|-------|------------------------|
| **Integrante del equipo** | Cuenta AWS propia (Free Tier 12 meses) o un **workspace/state distinto** por persona (ej. `envs/dev-alicia`, `envs/dev-bob`) para no pisarse. Mismo flujo que en producción: commit → Jenkins → build → registry → app. |
| **Cliente** | Cuenta AWS del cliente (Free Tier si es nueva) o **su propia instancia** en cuenta compartida (otro `name_prefix` o carpeta `envs/`). Sistema retail automatizado operando como en producción, sin coste. |

**Regla:** Una instancia **t2.micro** (o t3.micro) por persona/cliente = 750 h/mes gratis. Cada uno tiene **su sistema retail automatizado con FastFlow operando como en producción** siguiendo esta guía.

---

## 2. Instalar en AWS (OS y software en la instancia)

### 2.1 Crear la instancia (Terraform)

Sigue la lista única de pasos:

- **En este repo:** [INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](../../../../manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md) (en `toolkit-fastflow/manifests/terraform/jenkins-aws/`).

Resumen:

```bash
cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
cp terraform.tfvars.free-tier.example terraform.tfvars
# Editar terraform.tfvars: aws_region, availability_zones, jenkins_controller_ami_id, jenkins_worker_ami_id (misma AMI)
terraform init
terraform validate
terraform plan -var-file=terraform.tfvars
terraform apply -var-file=terraform.tfvars
```

Anota la salida **jenkins_public_ip**.

### 2.2 Conectar a la instancia (OS)

- **Opción A — SSH:** Si tienes clave PEM asociada a la instancia, desde tu PC:
  ```bash
  ssh -i /ruta/a/tu-key.pem ec2-user@<jenkins_public_ip>
  ```
- **Opción B — AWS Session Manager:** Sin clave, desde la consola AWS → EC2 → Seleccionar instancia → Conectar → Session Manager.

Ya estás **dentro del OS** (Amazon Linux 2 o la AMI que hayas usado).

### 2.3 Instalar software en la instancia (Jenkins + Java)

En la sesión SSH o Session Manager, ejecuta (ejemplo para **Amazon Linux 2**):

**Java 17 (requerido por Jenkins):**

```bash
sudo yum update -y
sudo yum install -y java-17-amazon-corretto
java -version
```

**Jenkins (war, puerto 8080):**

```bash
sudo su -
mkdir -p /opt/jenkins
cd /opt/jenkins
wget -q https://get.jenkins.io/war-stable/latest/jenkins.war
# Arrancar en segundo plano (o usar systemd para que persista)
nohup java -jar jenkins.war --httpPort=8080 > jenkins.log 2>&1 &
exit
```

Abre en el navegador: `http://<jenkins_public_ip>:8080`. La primera vez Jenkins pide una contraseña inicial (la muestra en `~/.jenkins/secrets/initialAdminPassword` en la instancia).

**Opcional — Docker (para build de imágenes en el mismo servidor):**

```bash
sudo yum install -y docker
sudo systemctl start docker
sudo usermod -aG docker ec2-user
```

Reconectar a la sesión para que el grupo tenga efecto.

---

## 3. Cómo ver cada parte

| Parte | URL (local o AWS) | Qué ver |
|------|-------------------|--------|
| **Jenkins** | `http://<jenkins_public_ip>:8080` (AWS) o `http://localhost:8080` (local) | Dashboard, jobs, último build (azul/rojo), **Console Output** del job. |
| **Aplicación (POS / app)** | `http://<IP_app>:8111` (o puerto configurado); en local `http://localhost:8111` | Página de la app; **/actuator/health** para comprobar que está viva. |
| **Registry** | `http://<IP_registry>:5000/v2/_catalog` (local o en EC2 si lo instalas) | Listado de imágenes; `/v2/<imagen>/tags/list` para tags. |
| **Interfaz central del flujo** | Abrir **deploy/dashboard-flujo-pos-jenkins.html** (en este repo, servida por un servidor HTTP o file://) | Una sola página: enlaces a Jenkins, app, registry; flujo commit → build → registry → app; única parte manual = commit. |

Configura las URLs en **deploy/config-ui.html** (o en `config/fastflow-config.json`): `dashboard.jenkinsUrl`, `dashboard.appUrl`, `dashboard.registryUrl` para que la interfaz central apunte a tu Jenkins en AWS (`http://<tu_IP>:8080`) y a tu app.

---

## 4. Cómo usar cada parte

| Parte | Cómo usarla |
|-------|-------------|
| **Jenkins** | Crear job tipo Pipeline; apuntar al repo (Git); ejecutar. Tras un **commit + push**, si hay webhook o polling, el job se dispara; si no, "Build Now". Ver logs en Console Output. |
| **App (POS)** | Arrancar en local: `mvn spring-boot:run -Dspring-boot.run.profiles=local` (en el repo pos-online). En AWS, si la app está en la misma EC2 u otra, abrir la URL en el navegador. |
| **Registry** | Desde Jenkins (credenciales configuradas) o desde script: `docker push <registry>/<imagen>:<tag>`. Ver tags en la URL del registry. |
| **Interfaz central** | 1) Hacer **commit** (único paso manual). 2) Abrir Jenkins desde la interfaz y comprobar el build. 3) Abrir Registry y comprobar el tag. 4) Abrir App y comprobar que responde. |

---

## 5. Cómo probar cada parte (gratis, PoC)

| Qué probar | Cómo |
|------------|------|
| **Jenkins responde** | Abrir `http://<jenkins_public_ip>:8080` en el navegador; debe cargar la UI de Jenkins. |
| **Job corre** | Ejecutar un build (Build Now o tras push); abrir **Console Output** y ver que las fases (Prepare, Build, Test, Package) terminan correctamente. |
| **App responde** | Abrir `http://<IP_app>:8111/actuator/health` (o la URL de tu app); debe devolver JSON `{"status":"UP"}` o similar. |
| **Registry tiene imágenes** | Abrir `http://<IP_registry>:5000/v2/_catalog`; debe devolver JSON con la lista de imágenes (o vacío si aún no has hecho push). |
| **Flujo completo** | Hacer un cambio, commit, push; ver en la interfaz central el enlace a Jenkins → abrir Console Output; ver en Registry el nuevo tag; ver la app en la URL configurada. |

Todo esto se puede hacer **gratis** en Free Tier: 1 EC2 t2.micro, 0 workers, Jenkins en esa única instancia. El flujo es el **mismo que en producción**: commit → pipeline (build, test, package) → imagen → registry → app; cada quien opera su sistema retail automatizado con FastFlow como en producción.

---

## 6. Resumen: cada quien con su sistema retail automatizado (FastFlow gratis, como en producción)

1. **Crear cuenta AWS** (o usar workspace/state propio en cuenta compartida).
2. **Seguir** [INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](../../../../manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md) → obtener **jenkins_public_ip**.
3. **Conectar** a la instancia (SSH o Session Manager) e **instalar** Java 17 + Jenkins (y opcional Docker) como en la sección 2.3.
4. **Ver** cada parte con las URLs de la sección 3 (Jenkins, app, registry, interfaz central).
5. **Usar** cada parte como en la sección 4 (job en Jenkins, app, registry, interfaz).
6. **Probar** como en la sección 5 (Jenkins responde, job corre, app health, registry catalog, flujo completo).

Cada persona tiene **su sistema retail automatizado con FastFlow, gratis, operando como en producción**, sin afectar al resto.

---

## Referencias en el repo

| Documento | Contenido |
|-----------|-----------|
| [INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](../../../../manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md) | Pasos Terraform (lista única). |
| [INICIO-RAPIDO-AWS-GRATIS.md](../../../../manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md) | Punto de entrada: desplegar yo / transferir repo / nuevo dueño. |
| [APLICAR-FASTFLOW.md](../../../../manifests/terraform/jenkins-aws/APLICAR-FASTFLOW.md) | Terraform init/plan/apply, backend S3, notas Free Tier. |
| [docs/60-pos-online/README.md](../60-pos-online/README.md) | Índice de documentación pos-online (flujo, checklist). |
| **deploy/dashboard-flujo-pos-jenkins.html** | Interfaz central: commit (manual) + ver Jenkins, registry, app. |

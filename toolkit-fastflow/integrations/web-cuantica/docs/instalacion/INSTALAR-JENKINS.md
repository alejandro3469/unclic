# Instalar Jenkins (host Linux, Java 17+)

**Inicio rápido (de cero al primer job):** [GUIA-PONER-JENKINS.md](GUIA-PONER-JENKINS.md).

Guía para instalar Jenkins en un servidor Linux para ejecutar el pipeline de **pos-online** (Maven, Docker, registry). Ver [REQUISITOS.md](REQUISITOS.md) para el resto del entorno.

---

## Requisitos

- **Java 17 o superior** (JDK) en el host donde corre Jenkins. Desde junio 2024 Jenkins 2.463+ [requiere Java 17+](https://www.jenkins.io/doc/book/platform-information/support-policy-java/). El POS puede seguir compilando con Java 11 vía **Global Tool Configuration** (JDK 11 en agentes o contenedores).
- Usuario con permisos para instalar paquetes o ejecutar el war.

---

## Instalación

### Opción 1: Jenkins como paquete (recomendado en Linux)

**Ubuntu/Debian** (instalar OpenJDK 17 antes si hace falta: `sudo apt install openjdk-17-jdk`):

```bash
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt update
sudo apt install jenkins
sudo systemctl enable jenkins
sudo systemctl start jenkins
```

**Amazon Linux 2**: ver [QUE-SIGUE-DESPUES-DEL-APPLY.md](../../../../manifests/terraform/jenkins-aws/docs/QUE-SIGUE-DESPUES-DEL-APPLY.md) (Java 17 Corretto + repo Jenkins + opcional `JENKINS_JAVA_CMD`).

Abrir `http://<tu-servidor>:8080` y seguir el asistente (contraseña inicial en `/var/lib/jenkins/secrets/initialAdminPassword`).

### Opción 2: Jenkins con WAR (cualquier SO con Java 17+)

```bash
wget https://get.jenkins.io/war-stable/latest/jenkins.war
java -jar jenkins.war --httpPort=8080
```

---

## Configuración mínima para el pipeline (pos-online)

1. **Credenciales del registry** (Docker Hub, GitLab Registry, ECR, etc.): en Jenkins → Manage Jenkins → Credentials, añadir usuario/contraseña o token según el registry.
2. **Job tipo “Pipeline”** (o “Pipeline from SCM”): apuntar al repo **pos-online** que contiene el `Jenkinsfile`. Workspace debe ser la raíz del proyecto (donde está `pom.xml` y el Jenkinsfile).
3. **Variables de entorno** (opcional): en el job o globalmente, definir `REGISTRY` para que el stage “Push to registry” haga push.

Referencia del pipeline: [JENKINS-PIPELINE-FASTFLOW.md](../pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md).

---

## Comprobar

- Acceso a la interfaz web en el puerto configurado (por defecto 8080).
- Crear un job Pipeline from SCM, indicar la URL del repo pos-online y la rama; ejecutar y ver que los stages (Test, Package, Build image, Push si REGISTRY está definido) se ejecutan.

Documentación general: [README de docs](../README.md).

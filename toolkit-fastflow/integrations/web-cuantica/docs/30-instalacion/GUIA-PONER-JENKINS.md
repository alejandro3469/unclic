# Guía para poner Jenkins (de cero a primer pipeline)

Objetivo: tener **Jenkins accesible en el navegador** y listo para un job **Pipeline from SCM** (Gitea/Git) con **Jenkinsfile**.  
Documentación detallada del flujo UnClic: [GUIA-PASO-A-PASO-AHORA-JENKINS-UNCLIC.md](../20-operaciones/GUIA-PASO-A-PASO-AHORA-JENKINS-UNCLIC.md) · Checklist: [CHECKLIST-JENKINS-UNCLIC.md](../20-operaciones/CHECKLIST-JENKINS-UNCLIC.md).

---

## Qué necesitas antes

| Requisito | Nota |
|-----------|------|
| **Servidor Linux** | EC2 (Amazon Linux 2/Ubuntu) o VM local |
| **Java 17+** | Obligatorio para Jenkins actual ([política oficial](https://www.jenkins.io/doc/book/platform-information/support-policy-java/)) |
| **Puerto 8080** | Abierto en firewall / Security Group hacia tu IP (o 0.0.0.0/0 solo en pruebas) |
| **Repo con Jenkinsfile** | Ej. pos-online en Gitea |

---

## Camino rápido (elige uno)

### A) Ubuntu / Debian

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt update
sudo apt install -y jenkins
sudo systemctl enable jenkins
sudo systemctl start jenkins
sudo systemctl status jenkins
```

Abre: `http://IP-DEL-SERVIDOR:8080`

### B) Amazon Linux 2 (EC2 típica FastFlow)

```bash
# Java 17 (Corretto)
sudo rpm --import https://yum.corretto.aws/corretto.key
sudo curl -L -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo
sudo yum install -y java-17-amazon-corretto-devel

# Jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install -y jenkins

echo 'JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto' | sudo tee /etc/sysconfig/jenkins

sudo systemctl enable jenkins
sudo systemctl start jenkins
```

*(Opcional POS/Maven en el mismo host: `sudo yum install -y maven`.)*

### C) Solo probar en tu Mac/PC (WAR)

```bash
# Con Java 17 instalado
wget https://get.jenkins.io/war-stable/latest/jenkins.war
java -jar jenkins.war --httpPort=8080
```

---

## Pasos después de instalar

### 1. Desbloquear Jenkins

En el servidor:

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

Pega la contraseña en la pantalla **Unlock Jenkins** → **Continue**.

### 2. Plugins iniciales

Elige **Install suggested plugins**. Espera a que termine.

### 3. Usuario administrador

Crea usuario admin o **Continue as admin** (solo laboratorio).

### 4. URL de Jenkins

Confirma `http://host:8080` (o el dominio si ya pusiste Nginx/HTTPS).

### 5. Job Pipeline desde Git

1. **New Item** → nombre → **Pipeline** → OK.  
2. En **Pipeline** → **Definition: Pipeline script from SCM**.  
3. **SCM: Git** → URL del repo (Gitea/GitHub), credenciales si es privado.  
4. **Script Path:** `Jenkinsfile` (o la ruta que uses).  
5. **Save** → **Build Now**.

Si falla el clone: revisa credenciales, URL accesible desde el servidor Jenkins y rama correcta.

### 6. Firewall / AWS

- Security Group: **Inbound TCP 8080** (y **8111** u otros puertos si despliegas la app en el mismo host).  
- Detalle pantallas: [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](../20-operaciones/JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md).

---

## Siguiente nivel (pos-online / Docker / registry)

- Credenciales del registry: **Manage Jenkins → Credentials**.  
- Variable `REGISTRY` en el job si el Jenkinsfile hace push de imagen.  
- Pipeline FastFlow: [JENKINS-PIPELINE-FASTFLOW.md](../40-pipeline-registry/JENKINS-PIPELINE-FASTFLOW.md).  
- Instalación extendida y requisitos: [INSTALAR-JENKINS.md](INSTALAR-JENKINS.md).

---

## Si algo falla

| Síntoma | Qué revisar |
|---------|-------------|
| No carga :8080 | `sudo systemctl status jenkins`, SG/firewall, Jenkins escuchando `0.0.0.0:8080` |
| Error Java | `java -version` → debe ser 17+ |
| Clone falla | URL Git, SSH vs HTTPS, credenciales en Jenkins |
| Build Maven falla | Maven instalado en el agente/controller, JDK en **Global Tool Configuration** |

---

## Resumen en 5 líneas

1. Instalar **Java 17**.  
2. Instalar **Jenkins** (apt o yum).  
3. Arrancar servicio, abrir **8080**, desbloquear con `initialAdminPassword`.  
4. Plugins sugeridos + admin.  
5. **New Item → Pipeline from SCM** apuntando al repo con **Jenkinsfile**.

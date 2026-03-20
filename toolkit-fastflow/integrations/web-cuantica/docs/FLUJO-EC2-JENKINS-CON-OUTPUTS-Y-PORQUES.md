# Flujo EC2 → Jenkins listo — Con outputs reales y porqués (usuario final)

Este documento recorre **paso a paso** lo que hicimos en la EC2 y en el navegador hasta tener Jenkins listo, con **outputs reales** de terminal y **por qué** se hace cada cosa. Sirve para que el usuario final sepa qué esperar al replicar el flujo.

**Resumen del flujo:** Conectar a la EC2 → Instalar Java 17 (Corretto) → Configurar JAVA_HOME para Jenkins → Instalar y arrancar Jenkins → Obtener contraseña inicial → Unlock Jenkins → Customize Jenkins (plugins) → Create First Admin User + Instance URL → Jenkins is ready.

---

## 1. Conectar a la EC2

### Por qué

- La instancia creada por Terraform no trae Jenkins ni Java; hay que ejecutar comandos **dentro** de la máquina. EC2 Instance Connect abre una terminal en el navegador sin configurar claves SSH.

### Qué hacer

AWS Console → EC2 → Instances → **fastflow-jenkins-controller** → **Connect** → pestaña **EC2 Instance Connect** → **Connect**.

### Output que verás

Al conectar, aparece el banner de Amazon Linux 2 y el prompt:

```
Last login: Thu Mar 12 21:05:24 2026 from ec2-3-16-146-5.us-east-2.compute.amazonaws.com
   ,     #_
   ~\_  ####_        Amazon Linux 2
  ~~  \_#####\
  ...
[ec2-user@ip-10-0-1-62 ~]$
```

Ahí ejecutarás el resto de comandos.

---

## 2. Instalar Java 17 (Amazon Corretto)

### Por qué

- **Jenkins 2.541+** exige **Java 17** como mínimo. Con Java 11 el servicio no arranca (error “older than the minimum required version (Java 17)”).
- **Amazon Corretto** es OpenJDK soportado por AWS, sin coste; en Amazon Linux 2 se instala con `yum` desde el repo `amzn2-core` (paquete `java-17-amazon-corretto-devel`).

### Comando

```bash
sudo yum install -y java-17-amazon-corretto-devel
```

### Output que verás (resumido)

- “Loaded plugins”, “Resolving Dependencies”, “Running transaction check”.
- Lista de paquetes a instalar: `java-17-amazon-corretto-devel` y dependencias (alsa-lib, dejavu fonts, fontconfig, java-17-amazon-corretto-headless, log4j hotpatch, etc.).
- “Downloading packages” (varios RPM) y “Running transaction”.
- Al final algo como:

```
Installed:
  java-17-amazon-corretto-devel.x86_64 1:17.0.18+9-1.amzn2.1

Dependency Installed:
  alsa-lib.x86_64 0:1.1.4.1-2.amzn2    dejavu-fonts-common.noarch 0:2.33-6.amzn2   ...
  java-17-amazon-corretto-headless.x86_64 1:17.0.18+9-1.amzn2.1  ...

Complete!
[ec2-user@ip-10-0-1-62 ~]$
```

Si ves **Complete!** y el prompt, Java 17 está instalado.

---

## 3. Configurar JAVA_HOME para Jenkins

### Por qué

- Jenkins (servicio systemd) usa la variable **JAVA_HOME** para saber qué JDK ejecutar. Si no la defines, puede usar otro Java del sistema (p. ej. 11) y fallar. El archivo `/etc/sysconfig/jenkins` lo lee el servicio al arrancar.

### Comando

```bash
echo 'JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto' | sudo tee /etc/sysconfig/jenkins
```

### Output que verás

```
JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto
[ec2-user@ip-10-0-1-62 ~]$
```

Solo esa línea; el archivo queda guardado. Si tu ruta es distinta (p. ej. `java-17-amazon-corretto.x86_64`), compruébalo con `ls /usr/lib/jvm/` y usa esa en `JAVA_HOME`.

---

## 4. Arrancar Jenkins (tras instalar Jenkins y Maven)

### Por qué

- Sin **Jenkins** instalado y en ejecución no hay pipeline. El servicio se llama `jenkins`; `systemctl start jenkins` lo levanta usando el `JAVA_HOME` que configuraste.

### Comandos (si Jenkins ya estaba instalado pero fallaba por Java)

```bash
sudo systemctl reset-failed jenkins
sudo systemctl start jenkins
sudo systemctl status jenkins
```

### Output que verás (systemctl status)

```
● jenkins.service - Jenkins Continuous Integration Server
   Loaded: loaded (/usr/lib/systemd/system/jenkins.service; enabled; vendor preset: disabled)
   Active: active (running) since Thu 2026-03-12 21:24:33 UTC; 1s ago
 Main PID: 4904 (java)
   CGroup: /system.slice/jenkins.service
           └─4904 /usr/bin/java -Djava.awt.headless=true -jar /usr/share/java/jenkins.war --webroot=...

Mar 12 21:24:33 ... jenkins[4904]: 2026-03-12 21:24:33.256+0000 [id=31]  INFO  jenkins.InitReactorRunner$1#onAttained: Completed initialization
Mar 12 21:24:33 ... jenkins[4904]: 2026-03-12 21:24:33.277+0000 [id=23]  INFO  hudson.lifecycle.Lifecycle#onReady: Jenkins is fully up and running
Mar 12 21:24:33 ... systemd[1]: Started Jenkins Continuous Integration Server.
```

Lo importante: **Active: active (running)** y la línea **“Jenkins is fully up and running”**. Si ves eso, Jenkins está listo para recibir conexiones por el puerto 8080.

---

## 5. Obtener la contraseña inicial (Unlock Jenkins)

### Por qué

- La primera vez que abres la URL de Jenkins, pide una **contraseña de desbloqueo** que está guardada en el servidor (en un archivo). Sin pegarla, no puedes seguir el asistente.

### Comando

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

### Output que verás

Una sola línea (la contraseña en hexadecimal). Ejemplo real:

```
[ec2-user@ip-10-0-1-62 ~]$ sudo cat /var/lib/jenkins/secrets/initialAdminPassword
aa813eef20154f8dab1665433c6c6832
[ec2-user@ip-10-0-1-62 ~]$
```

Copia **solo** esa línea (sin espacios ni saltos) y pégala en el navegador en la pantalla **“Unlock Jenkins”** → **Continue**.

**Pantallas exactas y siguiente paso:** Ver [JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md](JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md) (texto de la pantalla Unlock, Customize Jenkins, Create First Admin User, Instance Configuration, “Jenkins is ready!”).

---

## 6. Resumen de lo que sigue en el navegador (sin outputs de terminal)

| Paso en el navegador | Qué hacer |
|----------------------|-----------|
| **Unlock Jenkins** | Pegar la contraseña del comando anterior → **Continue**. |
| **Customize Jenkins** | **Install suggested plugins** (recomendado). Esperar a que termine. |
| **Create First Admin User** | Rellenar Username, Password, Full name, E-mail. No usar Skip en entorno real. |
| **Instance Configuration** | Jenkins URL: `http://<tu-IP>:8080/` (ej. http://3.15.4.160:8080/). |
| **Save and Finish** → **Start using Jenkins** | Entras al dashboard. |
| **Jenkins is ready!** | Pantalla con “Welcome to Jenkins!”, “Create a job”, “Build History”, etc. |

A partir de ahí: **Paso 4** (abrir puerto 8111 en el Security Group) y **Paso 5** (crear el job Pipeline) de [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md).

---

## Referencias cruzadas

- **Por qué y cómo** (más detalle): [GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md](GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md).  
- **Lista corta de pasos**: [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md).  
- **Pantallas Jenkins (Unlock, Customize, Admin, URL)**: [JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md](JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md).  
- **URLs y datos de la EC2**: [URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md).

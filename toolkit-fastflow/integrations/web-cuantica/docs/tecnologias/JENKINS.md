# Jenkins — Por qué, open source y cómo replicar

**Instalar desde cero (guía del repo):** [../instalacion/GUIA-PONER-JENKINS.md](../instalacion/GUIA-PONER-JENKINS.md).

## Documentación oficial (User Handbook)

**User Documentation Home** — [jenkins.io/doc](https://www.jenkins.io/doc/).

- **User Handbook:** Overview, **Installing Jenkins**, Platform Information, **Using Jenkins**, **Pipeline**, **Blue Ocean**, Managing Jenkins, Securing Jenkins, System Administration, Scaling Jenkins, Troubleshooting Jenkins, **Glossary**.
- **Tutorials:** Guided Tour, **Jenkins Pipeline**, Using Build Tools.
- **Resources:** [Pipeline Syntax reference](https://www.jenkins.io/doc/book/pipeline/syntax/), [Pipeline Steps reference](https://www.jenkins.io/doc/pipeline/steps/), LTS Upgrade guides, **[Build a Java app with Maven](https://www.jenkins.io/doc/tutorials/build-a-java-app-with-maven/)** (tutorial paso a paso).

### Jenkins en Unclic y User Documentation Home

En **FastFlow** el controller suele estar en **http://jenkins.unclic.consulting:8080** (o `http://<IP-EC2>:8080`). La **User Documentation** que replica el tutorial (User Handbook, Build a Java app with Maven, etc.) es la misma que en **[jenkins.io/doc](https://www.jenkins.io/doc/)** — **User Documentation Home** con el índice anterior.

**Tabla de contenidos del tutorial Maven (oficial):** Prerequisites → Fork and clone the sample repository → Start your Jenkins controller → Create your Pipeline project in Jenkins → Create your initial Pipeline as a Jenkinsfile → Add a test stage → Add a final deliver stage → Wrapping up → Cleaning Up Your Environment.

### Qué es Jenkins (documentación de usuario)

**Jenkins** es un servidor de automatización **open source** y **autocontenido** que sirve para automatizar tareas de **construcción, prueba, entrega y despliegue** de software. Se instala con **paquetes nativos**, **Docker** o en **standalone** en cualquier máquina con **JRE**.

- **Sobre esta documentación:** Empieza con **Guided Tour**; hay tutoriales para desarrolladores (Pipeline, **Blue Ocean**). Para detalle: **User Handbook**. Si quieres **extender Jenkins** con plugins propios: documentación para desarrolladores (*Extend Jenkins*).
- **Alcance:** Jenkins es muy extensible vía plugins. El contenido de Guided Tour, Tutorials, Solution pages y User Handbook parte de una instalación con plugins **Blue Ocean** y los **"suggested plugins"** del asistente post-instalación.

### Web jenkins.io (portada)

- **Mensaje:** *"Build great things at any scale"* — *"The leading open source automation server"*, cientos de plugins para construir, desplegar y automatizar proyectos. **Download** | **Documentation**.
- **UI rediseñada:** Cabecera y UI modernizadas (p. ej. Jenkins **2.516.1+**); artículos en el blog (Jan Faracik). **Pipeline Graph View** (grafo de stages, pan/zoom, logs unificados). **Temas:** Dark, Solarized, Catppuccin, Chocolate, Nord; selector desde el icono de usuario. **Jenkins Design Library**; plugin **IntelliJ** para Jelly/Symbols.
- **CI/CD:** Servidor de integración continua extensible o *hub* de entrega continua para cualquier proyecto.
- **Características resumidas:** **Easy installation** (Java, Windows/Linux/macOS/Unix); **Easy configuration** (interfaz web, validación y ayuda); **Plugins** (Update Center); **Extensible** (arquitectura de plugins); **Distributed** (trabajo en varias máquinas).
- **Blog reciente** (ejemplos): Plugin of the Month, Contributor Summit, GSoC, FOSDEM, tuning Java 17. **Patrocinadores:** Atlassian, Datadog, DigitalOcean, IBM, etc.

### Tutorial oficial: Build a Java app with Maven (resumen)

Tutorial oficial para construir una app Java con Maven en Jenkins (Pipeline as Code). Duración orientativa **20–40 minutos** (según máquina y si ya tienes Docker).

**Prerrequisitos:** macOS, Linux, Windows o Chromebook (Linux); **2 GB RAM**; **2 GB** de disco para Jenkins; **Docker**, **Docker Compose**, **Git** (opcional GitHub Desktop).

**Flujo del tutorial:**

1. **Fork y clone** del repo de ejemplo `simple-java-maven-app` en GitHub (en FastFlow puedes usar **Gitea** en lugar de GitHub con la misma idea: repo con `pom.xml` y tests).
2. **Arrancar el controller (tutorial):** Clonar `quickstart-tutorials` y ejecutar `docker compose --profile maven up -d`; Jenkins en **http://localhost:8080** (usuario/contraseña admin del ejemplo). Alternativas sin Docker local: **GitHub Codespaces** o **GitPod** (instrucciones en la doc).
3. **Crear el job Pipeline:** **Dashboard** → **New Item** → nombre del proyecto → tipo **Pipeline** → **OK**. En la configuración: **Pipeline** → **Definition: Pipeline script from SCM** → **SCM: Git** → **Repository URL** del fork (o de Gitea). **Save**.
4. **Jenkinsfile inicial:** En el repo, `Jenkinsfile` con `pipeline { agent any; stages { stage('Build') { steps { sh 'mvn -B -DskipTests clean package' } } } }`. Commit y push; en Jenkins **Build Now**. Ver **#1**, **Pipeline Overview**, salida del stage Build.
5. **Stage Test:** Añadir `stage('Test')` con `sh 'mvn test'` y `post { always { junit 'target/surefire-reports/*.xml' } }`. Commit/push; **Build Now**; columna **Test** en Stage View; informes JUnit en la UI.
6. **Stage Deliver:** Añadir `options { skipStagesAfterUnstable() }` y `stage('Deliver')` con `sh './jenkins/scripts/deliver.sh'`. Commit/push; **Build Now**; ver stage **Deliver** y salida.
7. **Wrapping up:** Los stages Build / Test / Deliver son la base para pipelines Maven más complejos. Más info: Tutorials, User Handbook (Pipeline syntax), blog de Jenkins.
8. **Limpieza (tutorial Docker):** `docker compose --profile maven down -v --remove-orphans`.

En **FastFlow** el flujo es análogo: **Pipeline script from SCM** apuntando a **Gitea** y `Jenkinsfile` en `pos-online` (o `repo-pos-fastflow`), con stages adaptados (build Maven, test, deploy Docker/registry según tu pipeline).

---

## Por qué lo usamos

- **Orquestación del pipeline:** Ejecuta los pasos de build, test y deploy definidos en el `Jenkinsfile` (Pipeline as Code). Un commit en Gitea dispara el job y el código se compila, se prueba y se despliega en la EC2 (o en el target que definas).
- **Objetivo de automatización:** Sin Jenkins tendrías que ejecutar `mvn package`, copiar JARs y arrancar servicios a mano. Con Jenkins todo queda automatizado y trazable (logs, historial de builds).
- **Open source only:** Jenkins es open source; no dependemos de un SaaS propietario para el CI.

## Open source

- **Proyecto:** [Jenkins](https://www.jenkins.io/) (fundación Jenkins).
- **Licencia:** MIT.
- **Código:** [github.com/jenkinsci/jenkins](https://github.com/jenkinsci/jenkins).

## Cómo replicar

1. **Requisitos:** EC2 con Java 17 (Corretto recomendado), puerto 8080 abierto en el security group. Ver [../instalacion/REQUISITOS.md](../instalacion/REQUISITOS.md) y [../instalacion/INSTALAR-JENKINS.md](../instalacion/INSTALAR-JENKINS.md).
2. **Consola:** Terminal (SSH a la EC2) y navegador (Jenkins UI).
3. **Instalación (resumen):** Añadir repo Amazon Linux 2023 para Corretto 17, instalar `java-17-amazon-corretto-headless`, descargar `jenkins.war` o paquete oficial, arrancar con `JENKINS_JAVA_CMD` apuntando a Java 17, abrir `http://<IP-EC2>:8080`.
4. **Desbloqueo:** Copiar la contraseña inicial desde `/var/lib/jenkins/secrets/initialAdminPassword` (en la EC2) y pegarla en la pantalla "Unlock Jenkins".
5. **Job Pipeline:** Crear job tipo "Pipeline", en Pipeline definition elegir "Pipeline script from SCM", SCM = Git, URL = repo Gitea (ej. `http://gitea.unclic.consulting:3000/tu-usuario/pos-online.git`), branch `main` o `master`, Script Path = `Jenkinsfile`.

## Inputs y comandos (referencia)

| Consola | Comando o acción | Input / nota |
|---------|-------------------|--------------|
| Terminal (EC2) | `sudo yum install -y java-17-amazon-corretto-headless` | Instalar Java 17 |
| Terminal (EC2) | `export JENKINS_JAVA_CMD=/usr/bin/java` y arrancar Jenkins | Para usar Java 17 |
| Terminal (EC2) | `sudo cat /var/lib/jenkins/secrets/initialAdminPassword` | Obtener contraseña inicial |
| Navegador | Abrir `http://<IP-EC2>:8080` | Sustituir por IP pública de la EC2 (ej. 18.218.37.76) |
| Jenkins UI | Unlock Jenkins → pegar contraseña | Pegar salida del comando anterior |
| Jenkins UI | New Item → nombre `pos-online-pipeline` → Pipeline → OK | Crear job |
| Jenkins UI | Configure → Pipeline → Definition: Pipeline script from SCM | SCM = Git, Repository URL = URL del repo en Gitea |

URLs de referencia (actualizar IP según [../URLS-Y-EC2-PRUEBAS.md](../URLS-Y-EC2-PRUEBAS.md)):

- Jenkins: `http://18.218.37.76:8080` o `http://jenkins.unclic.consulting:8080`

## Enlaces

- [jenkins.io](https://www.jenkins.io/) — portada (Download, Documentation, blog)
- [User Documentation Home](https://www.jenkins.io/doc/) — mismo contenido que sigues desde el tutorial Maven en la doc pública
- [Build a Java app with Maven (tutorial oficial Jenkins)](https://www.jenkins.io/doc/tutorials/build-a-java-app-with-maven/)
- [Pipeline syntax](https://www.jenkins.io/doc/book/pipeline/syntax/) · [Pipeline steps](https://www.jenkins.io/doc/pipeline/steps/)
- [Instalación detallada Jenkins](../instalacion/INSTALAR-JENKINS.md)
- [Requisitos (Java, Maven, Docker)](../instalacion/REQUISITOS.md)
- [Pantallas Jenkins (New Item, Configure)](../JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md)
- [Tutorial de replicación visual](../REPLICAR-TUTORIAL-VISUAL.md) — pasos con consola, qué ves, comandos y ayudas visuales.

# Guía paso a paso ahora — Jenkins unclic

Sigue estos pasos **en orden**. Al terminar cada uno, pasa al siguiente.  
Checklist para marcar: [CHECKLIST-JENKINS-UNCLIC.md](CHECKLIST-JENKINS-UNCLIC.md).

---

## Paso 1 — ¿Entras ya a Jenkins?

1. Abre en el navegador: **http://jenkins.unclic.consulting:8080** (o **http://3.15.4.160:8080**).
2. ¿Qué ves?
   - **Si ves la pantalla "Unlock Jenkins"** → Ve al **Paso 1b** (desbloquear).
   - **Si ves el dashboard** (lista de jobs, menú a la izquierda) → Ve al **Paso 2**.
   - **Si no carga** → Ve al **Paso 1a** (instalar Jenkins en la EC2).

---

## Paso 1a — Instalar Jenkins en la EC2 (solo si el Paso 1 no cargó)

1. Entra a **AWS Console** → **EC2** → **Instances**.
2. Localiza la instancia **fastflow-jenkins-controller** (o la que uses para Jenkins). Anota la **IP pública** (ej. 3.15.4.160).
3. Selecciónala → **Connect** → pestaña **EC2 Instance Connect** → **Connect**. Se abre una terminal en el navegador.
4. En esa terminal, pega y ejecuta (todo de una vez o por bloques):

```bash
# Java 17
sudo rpm --import https://yum.corretto.aws/corretto.key
sudo curl -L -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo
sudo yum install -y java-17-amazon-corretto-devel

# Jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install -y jenkins

# Java para Jenkins
echo 'JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto' | sudo tee /etc/sysconfig/jenkins

# Maven (para el pipeline)
sudo yum install -y maven

# Arrancar
sudo systemctl enable jenkins
sudo systemctl start jenkins
```

5. Comprueba: `sudo systemctl status jenkins` → debe decir **active (running)**.
6. Vuelve al **Paso 1** y abre de nuevo la URL de Jenkins.

---

## Paso 1b — Desbloquear Jenkins (solo si viste "Unlock Jenkins")

1. En la **misma terminal de la EC2** (Paso 1a) ejecuta:
   ```bash
   sudo cat /var/lib/jenkins/secrets/initialAdminPassword
   ```
2. Copia la **única línea** que sale (contraseña en hexadecimal).
3. En la pantalla "Unlock Jenkins" pega esa contraseña y pulsa **Continue**.
4. En "Customize Jenkins" elige **Install suggested plugins**. Espera a que termine.
5. Crea el usuario admin (o **Skip and continue as admin** si solo pruebas).
6. **Start using Jenkins**. Deberías ver el dashboard.
7. Sigue con el **Paso 2**.

---

## Paso 2 — Abrir puerto 8111 (para la app POS después del Deploy)

1. **AWS Console** → **EC2** → **Security Groups**.
2. Busca **fastflow-jenkins-sg** (o el security group de tu instancia Jenkins) → selecciónalo.
3. **Edit inbound rules** → **Add rule**:
   - Type: **Custom TCP**
   - Port: **8111**
   - Source: **0.0.0.0/0** (o "My IP" si prefieres)
4. **Save rules**.

Sigue con el **Paso 3**.

---

## Paso 3 — Repo en Gitea con Jenkinsfile

Necesitas un repo en Gitea que Jenkins vaya a clonar. Tiene que tener en la **raíz** un archivo **Jenkinsfile** y el código (o al menos `pom.xml` y `src/`).

**Opción A — Ya tienes alejandro-perez/pos-online en Gitea**

1. Abre **http://gitea.unclic.consulting:3000/alejandro-perez/pos-online**.
2. Comprueba que en la raíz ves **Jenkinsfile** y carpeta **src** (o **pom.xml**). Si sí → anota la URL del repo y ve al **Paso 4**.
3. Si **no** tiene Jenkinsfile (solo lo creaste vacío), tienes que subir código. En tu Mac, en el repo que tenga el POS + Jenkinsfile (por ejemplo `proyectos-gitlab/pos-online` o la carpeta `repo-pos-fastflow` del toolkit):
   ```bash
   cd /Users/wallfacer/proyectos-gitlab/pos-online
   git remote add gitea http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git
   git push -u gitea fix/package-and-deploy-scripts:main
   ```
   (Si tu rama se llama `main`, usa `git push -u gitea main`). Luego vuelve a comprobar en Gitea que aparecen Jenkinsfile y src.

**Opción B — Subir repo-pos-fastflow como repo nuevo en Gitea**

1. En Gitea → **+** → **New Repository** → nombre: `pos-online-fastflow` (o el que quieras). No marques "Initialize Repository". Crear.
2. En tu Mac:
   ```bash
   cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow
   git init
   git add .
   git commit -m "POS completo + Jenkinsfile FastFlow"
   git remote add gitea http://gitea.unclic.consulting:3000/alejandro-perez/pos-online-fastflow.git
   git branch -M main
   git push -u gitea main
   ```
   (Sustituye `alejandro-perez/pos-online-fastflow` por tu usuario y nombre de repo.)
3. Anota la URL del repo (ej. `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online-fastflow.git`).

Sigue con el **Paso 4**.

---

## Paso 4 — Credenciales de Gitea en Jenkins

1. En Jenkins → **Manage Jenkins** (menú izquierda).
2. **Credentials** → **(global)** (o el dominio que uses).
3. **Add Credentials**.
4. Rellena:
   - **Kind:** Username with password
   - **Username:** `alejandro-perez` (tu usuario Gitea)
   - **Password:** tu contraseña de Gitea, o un **Access Token** (en Gitea → Settings → Applications → Generate New Token)
   - **ID:** `gitea-pos-online`
   - **Description:** (opcional) Gitea unclic
5. **Create**.

Sigue con el **Paso 5**.

---

## Paso 5 — Crear el job en Jenkins

1. En Jenkins → **New Item**.
2. **Item name:** `pos-online-pipeline`.
3. Tipo: **Pipeline** → **OK**.
4. En la configuración del job:
   - **Pipeline** → **Definition:** **Pipeline script from SCM**.
   - **SCM:** **Git**.
   - **Repository URL:** la URL de tu repo en Gitea, por ejemplo:
     - `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git`
     - o `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online-fastflow.git`
   - **Credentials:** elige `gitea-pos-online` (la que creaste).
   - **Branch:** `*/main` (o `*/master` si tu rama es master).
   - **Script Path:** `Jenkinsfile`.
5. **Save**.

Sigue con el **Paso 6**.

---

## Paso 6 — Primer Build Now (probar checkout)

1. En el job **pos-online-pipeline** → **Build Now**.
2. Abre el **build #1** (o el que salga) → **Console Output**.
3. Revisa:
   - Si ves **"Checking out..."** y luego algo como **"Finished: SUCCESS"** o que pasa al stage **Prepare** → el **checkout** está bien. Si el build falla más adelante (por ejemplo en Build), es el tema de **generic-model** (Paso 7).
   - Si falla en el checkout (error de clone, 403, etc.) → revisa la URL del repo y que la credencial sea la correcta; que el repo exista y tengas permisos.

Sigue con el **Paso 7** (para que el build no falle en compilación).

---

## Paso 7 — Resolver generic-model (si el build falla en Build/Package)

Si en la consola del build ves un error tipo **"Could not find artifact ... smartbussiness-generic-model"**, el POS no puede compilar sin esa dependencia.

**Opción más rápida: instalar generic-model una vez en la EC2**

1. Conéctate a la **EC2 de Jenkins** (como en Paso 1a).
2. Si tienes el repo **generic-model** en tu Mac (por ejemplo en la misma carpeta padre que pos-online), en la EC2 necesitas tenerlo y hacer `mvn install`. Opciones:
   - **A)** Clonar generic-model desde donde esté (GitLab, Gitea, etc.):
     ```bash
     cd /tmp
     git clone https://gitlab.com/TU_ORG/generic-model.git   # o la URL real
     cd generic-model
     mvn install -DskipTests
     ```
     El usuario con el que ejecutes debe ser el mismo con el que corre Jenkins (por defecto `jenkins`). Para que el artefacto quede en el Maven del usuario Jenkins:
     ```bash
     sudo su - jenkins
     cd /tmp
     git clone <URL_GENERIC_MODEL> generic-model
     cd generic-model
     mvn install -DskipTests
     exit
     ```
   - **B)** Si generic-model está en Gitea, usa esa URL en el `git clone`.
3. Vuelve a Jenkins → **Build Now** en **pos-online-pipeline**. El stage Build debería encontrar ya el artefacto en el repositorio Maven local.

**Si no tienes URL de generic-model:** tendrás que añadir generic-model como submódulo al repo que clona Jenkins, o copiarlo dentro del repo, y en el Jenkinsfile (o en el job) añadir un paso que haga `mvn install` en esa carpeta antes del Build del POS. Ver [QUE-FALTA-PROBAR-JENKINS-UNCLIC.md](QUE-FALTA-PROBAR-JENKINS-UNCLIC.md) sección 5.

Sigue con el **Paso 8**.

---

## Paso 8 — Comprobar que el pipeline pasa

1. **Build Now** en **pos-online-pipeline**.
2. Abre el último build → **Console Output**.
3. Deberías ver algo como: **Prepare** → **Build** → **Test** → **Lint** → **Package** → (opcional **Build image**, **Push**, **Deploy** si está en rama main).
4. Si todo va bien: **Finished: SUCCESS**.
5. Si el job está en rama **main** y tiene stage Deploy, la app debería quedar en el puerto **8111**. Prueba en el navegador: **http://3.15.4.160:8111/health** (o la IP de tu EC2).

---

## Resumen de pasos

| # | Qué hacer |
|---|-----------|
| 1 | Abrir Jenkins (o instalar y desbloquear si no está) |
| 2 | Abrir puerto 8111 en el Security Group |
| 3 | Tener repo en Gitea con Jenkinsfile en la raíz |
| 4 | Añadir credenciales de Gitea en Jenkins |
| 5 | Crear job pos-online-pipeline (Pipeline from SCM, URL Gitea, Jenkinsfile) |
| 6 | Build Now y revisar checkout |
| 7 | Resolver generic-model en la EC2 (o en el repo/Jenkinsfile) si falla el Build |
| 8 | Build Now de nuevo y comprobar SUCCESS y app en 8111 |

Si te atascas en un paso, dime en cuál y qué mensaje de error ves (o pega la salida de la consola del build).

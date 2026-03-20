# Paso a paso — Mínimo para hoy (un commit → app levantada)

Lista en orden. Cada paso tiene **acción**, **dónde** y **resultado esperado**. Cuando termines uno, pasa al siguiente.

**URLs para probar:** Jenkins → [http://3.15.4.160:8080](http://3.15.4.160:8080) · App POS (tras Deploy) → [http://3.15.4.160:8111](http://3.15.4.160:8111). Datos completos de la EC2: [URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md).  
**Outputs reales y porqués (usuario final):** [FLUJO-EC2-JENKINS-CON-OUTPUTS-Y-PORQUES.md](FLUJO-EC2-JENKINS-CON-OUTPUTS-Y-PORQUES.md).

---

## Paso 1 — Conectar a la EC2

**Qué hacer:** Abrir sesión en la instancia desde el navegador.

**Dónde:** AWS Console → EC2 → Instances → **fastflow-jenkins-controller** → **Connect** → pestaña **EC2 Instance Connect** → usuario `ec2-user` → **Connect**.

**Resultado esperado:** Se abre una ventana del navegador con una terminal (prompt tipo `[ec2-user@ip-10-0-1-62 ~]$`). Puedes escribir comandos en la instancia.

---

## Paso 2 — Instalar Java 17, Jenkins y Maven en la EC2

**Qué hacer:** En la terminal que abriste en el Paso 1, ejecutar estos comandos (uno tras otro o en bloque). **Jenkins 2.541+ requiere Java 17 como mínimo** (Java 11 ya no es soportado).

```bash
# Repo Corretto (si no lo tienes de antes)
sudo rpm --import https://yum.corretto.aws/corretto.key
sudo curl -L -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo

# Java 17 (requerido por Jenkins 2.x)
sudo yum install -y java-17-amazon-corretto-devel

# Jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install -y jenkins

# Decir a Jenkins qué Java usar (ruta puede ser java-17-amazon-corretto.x86_64; comprobar con ls /usr/lib/jvm/)
echo 'JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto' | sudo tee /etc/sysconfig/jenkins

# Maven (para el pipeline)
sudo yum install -y maven

# Arrancar Jenkins
sudo systemctl enable jenkins
sudo systemctl start jenkins
```

**Dónde:** En la sesión de EC2 Instance Connect (dentro de la instancia).

**Resultado esperado:** Al final, `sudo systemctl status jenkins` muestra `active (running)`. Puedes comprobar Java con `java -version` y Maven con `mvn -version`.

---

## Paso 3 — Contraseña inicial de Jenkins y primer acceso

**Qué hacer:** En la misma terminal de la EC2:

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

Copia la **única línea** que imprime (la contraseña en hexadecimal). Luego en tu navegador abre:

**http://3.15.4.160:8080**

Verás la pantalla **“Unlock Jenkins”** con el campo “Administrator password”. Pega ahí la contraseña y pulsa **Continue**. Luego verás **“Customize Jenkins”** (plugins): elige **Install suggested plugins** (recomendado para el pipeline). Después, crea el usuario admin o **Skip and continue as admin** si solo pruebas; por último **Start using Jenkins** para entrar al dashboard.

**Qué verás en pantalla y qué sigue paso a paso:** Ver [JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md](JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md) — Unlock, comando con output, y sección 5 “Customize Jenkins” (plugins, admin, dashboard).

**Dónde:** Terminal EC2 + navegador en tu Mac.

**Resultado esperado:** Entras en el dashboard de Jenkins (pantalla principal con “Welcome to Jenkins” o el panel de jobs).

---

## Paso 4 — Abrir el puerto 8111 en el Security Group

**Qué hacer:** En AWS Console → EC2 → **Security Groups** → buscar **fastflow-jenkins-sg** → **Edit inbound rules** → **Add rule**: Tipo = Custom TCP, Puerto = **8111**, Origen = 0.0.0.0/0 (o tu IP) → **Save rules**.

**Dónde:** AWS Console (no dentro de la EC2).

**Resultado esperado:** El security group tiene una regla de entrada para el puerto 8111. Cuando la app POS esté levantada en la EC2, podrás abrir `http://3.15.4.160:8111` desde tu navegador.

---

## Paso 5 — Crear el job en Jenkins (Pipeline from SCM)

**Qué hacer:** En Jenkins (http://3.15.4.160:8080) → **New Item** → nombre (ej. `pos-online-pipeline`) → elegir **Pipeline** → OK.

Para ver **cómo son las pantallas** (New Item y Configure con General, Triggers, Pipeline), ver [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md).

En la configuración del job:

- **Pipeline** → Definition: **Pipeline script from SCM**.
- **SCM:** Git.
- **Repository URL:** la URL del repo que tenga el **Jenkinsfile** y el código del POS (p. ej. el repo pos-online, o el repo donde hayas copiado el Jenkinsfile y el `pom.xml`). Debe ser un repo que Jenkins pueda clonar (público o con credenciales).
- **Branch:** `main` o la rama que uses (ej. `master`).
- **Script Path:** `Jenkinsfile` (si está en la raíz del repo).

Guardar.

**Dónde:** Jenkins UI.

**Resultado esperado:** El job existe. Si haces **Build Now**, Jenkins intentará hacer checkout; puede fallar si el repo no es accesible o si falta generic-model (siguiente paso).

---

## Paso 6 — Asegurar generic-model (repos y dependencias)

**Qué hacer:** El proyecto pos-online depende de **generic-model**. Opciones mínimas:

- **A:** El repo que pusiste en el job tiene **generic-model como submódulo de Git**. En el job, en Pipeline → SCM, marcar **Advanced** y opción para actualizar submódulos (e.g. “Additional Behaviours” → “Advanced sub-modules behaviour” / “Recursively update submodules”) para que al hacer checkout se traiga generic-model.
- **B:** El repo que clonas ya incluye generic-model en una carpeta (por ejemplo como copia o subcarpeta). En ese caso no hace falta nada más en Jenkins.
- **C:** Tienes dos repos (pos-online y generic-model). Entonces en el Jenkinsfile o en el job tendrías que hacer checkout de ambos (por ejemplo dos pasos “checkout” a distintas carpetas y configurar Maven para que vea generic-model). Para “mínimo hoy” lo más simple es A o B.

**Dónde:** Repo Git y/o configuración del job en Jenkins.

**Resultado esperado:** Al ejecutar el pipeline, la etapa **Build** (`mvn clean compile`) no falla por “generic-model not found”. Si no tienes aún pos-online con generic-model, puedes crear un job que solo haga checkout y `mvn compile` de un repo de prueba que sí tenga ambas cosas, o posponer el build completo hasta tener el repo listo.

---

## Paso 7 — Trigger del job (Poll SCM)

**Qué hacer:** En la configuración del job → **Build Triggers** → marcar **Poll SCM**. En el campo “Schedule” poner por ejemplo `H/2 * * * *` (cada 2 minutos) o `* * * * *` (cada minuto) para pruebas.

Guardar.

**Dónde:** Jenkins UI → job → Configure.

**Resultado esperado:** Cada X minutos Jenkins comprueba si hubo cambios en el repo; si los hay, lanza un build automáticamente. También puedes seguir usando **Build Now** para probar a mano.

---

## Paso 8 — Deploy real en el Jenkinsfile

**Qué hacer:** En el repo que usa el job, editar el **Jenkinsfile** y sustituir el stage **Deploy** (el que solo hace `sh 'echo "Deploy (main)"'`) por un paso que levante la app en la EC2.

**Opción mínima (JAR):** En el stage Deploy (cuando `branch 'main'` o la rama que uses), ejecutar algo como:

```groovy
stage('Deploy') {
    when { branch 'main' }
    steps {
        sh '''
            pkill -f "pos-online.*jar" || true
            sleep 2
            nohup java -jar target/pos-online-*.jar --server.port=8111 > /tmp/pos-online.log 2>&1 &
            sleep 3
        '''
    }
}
```

(Ajusta el nombre del JAR si tu `pom.xml` genera otro artefacto; el `target/pos-online-*.jar` es el típico de Spring Boot.)

Hacer commit y push del Jenkinsfile al repo. Si el trigger está activo, Jenkins lanzará un build; cuando llegue al stage Deploy, ejecutará el script y la app quedará escuchando en el puerto 8111.

**Dónde:** Repo Git (en tu Mac o donde edites) + commit/push.

**Resultado esperado:** Tras un build exitoso que pase por Deploy, al abrir **http://3.15.4.160:8111** (y con el puerto 8111 abierto, Paso 4) ves la app POS o al menos un health/actuator si lo tiene.

---

## Resumen rápido (orden)

| # | Paso | Dónde |
|---|------|--------|
| 1 | Conectar a la EC2 (EC2 Instance Connect) | AWS Console |
| 2 | Instalar Java 17, Jenkins, Maven; JAVA_HOME; arrancar Jenkins | Terminal en la EC2 |
| 3 | Obtener contraseña inicial y completar setup de Jenkins en el navegador | EC2 + navegador |
| 4 | Abrir puerto 8111 en el security group | AWS Console |
| 5 | Crear job Pipeline from SCM (repo con Jenkinsfile + código POS) | Jenkins |
| 6 | Asegurar generic-model (submódulo o repo que lo incluya) | Repo / job |
| 7 | Activar Poll SCM en el job | Jenkins |
| 8 | Cambiar Deploy en el Jenkinsfile por comando real (java -jar) | Repo + commit |

Cuando todos estén hechos, un **commit en la rama configurada** disparará el pipeline y la app quedará levantada de nuevo en `http://3.15.4.160:8111`.

**Referencias:** Java 17 (Corretto) para Jenkins: [Amazon Corretto](https://aws.amazon.com/corretto/). Contexto Java/cloud: [Oracle Java](https://www.oracle.com/java/). Referencias completas y citas: [GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md](GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md#15-referencias-y-fuentes).

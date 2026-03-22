# pos-online-pipeline: logs explicados línea por línea

Documento **opcional** para leer el **Console Output** del job **pos-online-pipeline** en Jenkins cuando quieras entender qué imprime cada stage. El recorrido principal sigue siendo la **[GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** (§7).

---

## Convención de los logs y de este documento

### Etiquetas que imprime el pipeline (en español)

El Jenkinsfile hace que cada stage imprima cuatro tipos de línea para que quede claro qué se ejecutó, qué se esperaba, qué se obtuvo y qué decisión se tomó:

| Etiqueta en el log | Significado |
|--------------------|-------------|
| **«[COMANDO]»** | Lo que se va a ejecutar o se está ejecutando (comando shell o acción). |
| **«[ESPERADO]»** | Resultado o comportamiento esperado (código de salida, archivos, respuesta). |
| **«[OBTENIDO]»** | Lo que realmente devolvió el comando (código de salida, salida recortada, PID, etc.). |
| **«[DECISIÓN]»** | Qué se hace a continuación en función de lo obtenido (continuar, fallar, omitir). |

Busca en el log esas etiquetas para seguir el flujo paso a paso.

### Cómo se escribe este documento

Para distinguir bien entre texto, variables y código:

- **Texto en español**: explicación normal (como este párrafo).
- **Nombres de variables o parámetros** del pipeline (Groovy/entorno): en `MAYÚSCULAS` o `nombreVariable`, por ejemplo `IMAGE_TAG`, `REGISTRY`, `APP_PORT`.
- **Comandos** del shell o del lenguaje (Groovy, Maven): en código inline `mvn test -B` o en bloque.
- **Literales que aparecen en el log** (lo que imprime el pipeline): entre comillas «así» o en bloque de código cuando son varias líneas.
- **Palabras clave de Groovy/Jenkins** que no son variables: `when`, `expression`, `branch`, `sh`, `echo`, etc., en código para no confundirlas con texto.

---

## Cabecera del build

```
Started by user Alejandro Perez
Obtained Jenkinsfile from git http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git
[Pipeline] Start of Pipeline
[Pipeline] node
Running on Jenkins in /var/lib/jenkins/workspace/pos-online-pipeline
[Pipeline] {
```

| Línea | Significado |
|-------|-------------|
| `Started by user Alejandro Perez` | Quién disparó el build (usuario o "triggered by SCM" si fue un push). |
| `Obtained Jenkinsfile from git ...` | Jenkins tomó el Jenkinsfile del repo indicado (Gitea). |
| `[Pipeline] Start of Pipeline` | Inicio del pipeline declarativo. |
| `[Pipeline] node` | Se asigna un nodo (agente) para ejecutar los steps. |
| `Running on Jenkins in /var/lib/jenkins/workspace/pos-online-pipeline` | El job corre en el **controller** (built-in node), en ese workspace. |
| `[Pipeline] {` | Bloque del pipeline. |

---

## Stage: Checkout SCM (Declarative)

```
[Pipeline] stage
[Pipeline] { (Declarative: Checkout SCM)
[Pipeline] checkout
The recommended git tool is: NONE
using credential gitea-pos-online
 > git rev-parse --resolve-git-dir /var/lib/jenkins/workspace/pos-online-pipeline/.git # timeout=10
Fetching changes from the remote Git repository
 > git config remote.origin.url http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git # timeout=10
Fetching upstream changes from http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git
 > git --version # timeout=10
 > git --version # 'git version 2.47.3'
using GIT_ASKPASS to set credentials Gitea Jenkins
 > git fetch --tags --force --progress -- http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git +refs/heads/*:refs/remotes/origin/* # timeout=10
 > git rev-parse refs/remotes/origin/main^{commit} # timeout=10
Checking out Revision b068e9ff2c38231a8f6dd95134228bced1dd4d37 (refs/remotes/origin/main)
 > git config core.sparsecheckout # timeout=10
 > git checkout -f b068e9ff2c38231a8f6dd95134228bced1dd4d37 # timeout=10
Commit message: "commit"
 > git rev-list --no-walk 87d326e872eb81daffd6ce0ed7f6a229335ea250 # timeout=10
[Pipeline] }
[Pipeline] // stage
```

| Línea / bloque | Significado |
|----------------|-------------|
| `(Declarative: Checkout SCM)` | Stage automático de Jenkins: clonar/actualizar el repo según la config del job (Pipeline from SCM). |
| `using credential gitea-pos-online` | Credencial configurada en el job para acceder a Gitea (usuario/contraseña o token). |
| `git config remote.origin.url` | URL del remoto `origin` (repo en Gitea). |
| `git fetch ... +refs/heads/*:refs/remotes/origin/*` | Descarga las ramas del remoto a `origin/main`, etc. |
| `Checking out Revision b068e9f... (refs/remotes/origin/main)` | Se hace checkout del commit que está en `origin/main` en este momento. |
| `Commit message: "commit"` | Mensaje del último commit de esa revisión. |
| `[Pipeline] // stage` | Fin del stage Checkout. |

---

## Variables de entorno (withEnv)

```
[Pipeline] withEnv
[Pipeline] {
[Pipeline] withEnv
[Pipeline] {
```

Jenkins inyecta las variables definidas en el bloque `environment` del Jenkinsfile (`IMAGE_NAME`, `IMAGE_TAG`, `REGISTRY`, `APP_PORT`, `MAVEN_OPTS`, etc.) para todos los steps que siguen. No se listan en el log; se usan en los `sh` como `${IMAGE_NAME}`, `${env.REGISTRY}`, etc.

---

## Stage: Prepare

Ejemplo de log con la convención **COMANDO / ESPERADO / OBTENIDO / DECISIÓN**:

```
[Pipeline] stage
[Pipeline] { (Prepare)
[Pipeline] sh
+ echo '[COMANDO] Se ejecutará: java -version, mvn -version, pwd'
[COMANDO] Se ejecutará: java -version, mvn -version, pwd
+ echo '[ESPERADO] Versión de Java 17 y Maven; ruta del workspace y rama.'
[ESPERADO] Versión de Java 17 y Maven; ruta del workspace y rama.
+ java -version
openjdk version "17.0.18" ...
+ mvn -version
Apache Maven 3.0.5 ...
+ echo '[OBTENIDO] Workspace: ... ; rama: ...'
[OBTENIDO] Workspace: /var/lib/jenkins/workspace/pos-online-pipeline; rama: origin/main
+ echo '[DECISIÓN] Continuar al stage Build si el entorno está disponible.'
[DECISIÓN] Continuar al stage Build si el entorno está disponible.
[Pipeline] }
[Pipeline] // stage
```

| Línea / bloque | Significado |
|----------------|-------------|
| «[COMANDO]» | Comandos que se ejecutan: `java -version`, `mvn -version`, `pwd`. |
| «[ESPERADO]» | Que existan Java 17 y Maven y se muestre workspace y rama. |
| «[OBTENIDO]» | Valor real de workspace y rama (variables de entorno del job). |
| «[DECISIÓN]» | Seguir al stage Build; si algún comando fallara, el step fallaría. |
| `+` al inicio de línea | El shell está en modo -x: muestra la línea que ejecuta. |

---

## Stage: Build

```
[Pipeline] stage
[Pipeline] { (Build)
[Pipeline] sh
+ echo '[COMANDO] Se ejecuta: mvn clean compile -q'
[COMANDO] Se ejecuta: mvn clean compile -q
+ echo '[ESPERADO] Código de salida 0; directorio target/classes con .class generados.'
[ESPERADO] Código de salida 0; directorio target/classes con .class generados.
+ mvn clean compile -q
+ R=0
+ echo '[OBTENIDO] Código de salida de mvn: 0'
[OBTENIDO] Código de salida de mvn: 0
+ ls -la target/classes ...
+ echo '[DECISIÓN] Si R=0 continuar a Test; si R!=0 el pipeline se marca como fallido.'
[DECISIÓN] Si R=0 continuar a Test; si R!=0 el pipeline se marca como fallido.
[Pipeline] }
[Pipeline] // stage
```

| Etiqueta | Significado |
|----------|-------------|
| «[COMANDO]» | Se ejecuta `mvn clean compile -q` (Maven limpia y compila sin tests). |
| «[ESPERADO]» | Código de salida 0 y que exista `target/classes` con `.class`. |
| «[OBTENIDO]» | Variable `R` = código de salida de `mvn`; listado de `target/classes`. |
| «[DECISIÓN]» | Si `R=0` se pasa a Test; si no, Jenkins marca el build como fallido. |

En el happy path, **§5** de la guía única ya deja **swap** y **generic-model** listos en la EC2 Jenkins antes del primer build.

---

## Stage: Test

```
[Pipeline] stage
[Pipeline] { (Test)
[Pipeline] sh
+ echo '=== Test: ejecutando tests (salida visible, sin -q) ==='
=== Test: ejecutando tests (salida visible, sin -q) ===
+ mvn test -B
[INFO] Scanning for projects...
[INFO] Building pos-online 0.0.1-SNAPSHOT
...
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running mx.com.smartbussiness.generic.utils.DoubleToBigDecimalConverterTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
...
[INFO] Results:
[INFO] Tests run: 54, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
Post stage
[Pipeline] junit
Recording test results
[Pipeline] sh
+ echo '=== Test: resumen surefire ==='
--- target/surefire-reports/mx.com....Test.txt ---
Tests run: 10, Failures: 0, ...
[Pipeline] }
[Pipeline] // stage
```

| Etiqueta en el log | Significado |
|--------------------|-------------|
| «[COMANDO]» | Se ejecuta `mvn test -B` (tests con Maven en modo batch). |
| «[ESPERADO]» | Línea tipo «Tests run: N, Failures: 0, Errors: 0» y «BUILD SUCCESS». |
| «[OBTENIDO]» | Código de salida de `mvn test` (variable `R`); en `post` se listan los `.txt` de Surefire. |
| «[DECISIÓN]» | Si `R=0` continuar a Lint; si no, el pipeline se detiene en este stage. |

`[Pipeline] junit` y «Recording test results»: el bloque `post { always { junit ... } }` publica los XML de Surefire para las gráficas de Jenkins.

---

## Stage: Lint

```
[Pipeline] stage
[Pipeline] { (Lint)
[Pipeline] sh
+ echo '=== Lint: validación Maven ==='
=== Lint: validación Maven ===
+ mvn validate -q 2>/dev/null || true
[Pipeline] }
[Pipeline] // stage
```

| Etiqueta en el log | Significado |
|--------------------|-------------|
| «[COMANDO]» | Se ejecuta `mvn validate -q`. «[ESPERADO]» Validación del POM sin fallar el step. «[OBTENIDO]» Código (se ignora con `|| true`). «[DECISIÓN]» Siempre continuar a Package. |

Sirve como comprobación ligera; no suele ser la causa de un build rojo.

---

## Stage: Package

```
[Pipeline] stage
[Pipeline] { (Package)
[Pipeline] sh
+ echo '=== Package: empaquetando JAR (mvn package -DskipTests) ==='
=== Package: empaquetando JAR (mvn package -DskipTests) ===
+ mvn package -DskipTests -q
+ echo '=== Package: artefactos generados ==='
+ ls -la target/*.jar 2>/dev/null || true
-rw-r--r-- 1 jenkins jenkins 123873983 Mar 13 05:03 target/pos-online-0.0.1-SNAPSHOT.jar
[Pipeline] }
[Pipeline] // stage
```

| Línea | Significado |
|-------|-------------|
| `mvn package -DskipTests -q` | Genera el JAR (no vuelve a ejecutar tests). |
| `ls -la target/*.jar` | Comprueba que exista el JAR; el tamaño (~118 MB) es normal para pos-online. |

---

## Stage: Build image

El stage solo corre si existe `Dockerfile` (condición `when { expression { return fileExists('Dockerfile') } }` en Groovy).

| Etiqueta en el log | Significado |
|--------------------|-------------|
| «[COMANDO]» | Comprobar si existe `docker`; si existe: `cp` del JAR y `docker build -t IMAGE_NAME:IMAGE_TAG .`. |
| «[ESPERADO]» | Si no hay `docker`: omitir stage (exit 0). Si hay: imagen `pos-online:BUILD_NUMBER` creada. |
| «[OBTENIDO]» | «docker no encontrado en PATH» o «docker disponible; se construye la imagen»; luego código de salida de `docker build`. |
| «[DECISIÓN]» | Sin docker: omitir y seguir. Con docker: si build OK continuar a Push to registry (si `REGISTRY` definido). |

Variables del pipeline: `IMAGE_NAME`, `IMAGE_TAG` (número de build). Con Docker instalado en la EC2 Jenkins (**§5.3** de la guía única), este stage construye la imagen cuando existe `Dockerfile`.

---

## Stage: Push to registry

```
[Pipeline] stage
[Pipeline] { (Push to registry)
[Pipeline] sh
+ echo '=== Push to registry: localhost:5000/pos-online ==='
+ docker tag pos-online:14 localhost:5000/pos-online:14
+ docker push localhost:5000/pos-online:14
+ docker tag pos-online:14 localhost:5000/pos-online:latest
+ docker push localhost:5000/pos-online:latest
=== Push: subido 14 y latest ===
[Pipeline] }
[Pipeline] // stage
```

| Línea | Significado |
|-------|-------------|
| `Push to registry` | Solo se ejecuta si en el job está definida la variable **REGISTRY** y no está vacía (ej. `REGISTRY=localhost:5000`). |
| `docker tag ... REGISTRY/pos-online:TAG` | Etiqueta la imagen local para el registry. |
| `docker push ...` | Sube la imagen al registry. Si REGISTRY es `null` o vacío, el stage no debe ejecutarse (fix en Jenkinsfile con `(env.REGISTRY?.toString() ?: '').trim() != ''`). |

Si no ves este stage, en el happy path suele ser porque **REGISTRY** no está definido en el job (deploy solo con JAR local). Con **REGISTRY** definido y registry accesible, verás el push en el log.

---

## Stage: Cleanup (solo rama main)

```
[Pipeline] stage
[Pipeline] { (Cleanup)
[Pipeline] sh
+ echo '=== Cleanup: deteniendo instancia anterior (main) ==='
+ pkill -f "pos-online.*jar" || true
+ sleep 2
+ echo '=== Cleanup: OK ==='
[Pipeline] }
[Pipeline] // stage
```

| Línea | Significado |
|-------|-------------|
| `Cleanup` | Solo corre en rama **main** (`when { branch 'main' }`). |
| `pkill -f "pos-online.*jar"` | Mata procesos Java que estén ejecutando el JAR de pos-online (instancia anterior del deploy). |
| `|| true` | Si no hay proceso, pkill devuelve distinto de 0; el step no falla. |

Así el siguiente stage (Deploy) arranca una sola instancia nueva.

---

## Stage: Deploy (solo rama main)

Solo se ejecuta en rama `main` (`when { branch 'main' }`).

| Etiqueta en el log | Significado |
|--------------------|-------------|
| «[COMANDO]» | `pkill` de instancia anterior; luego `nohup java -jar ... --server.port=APP_PORT > /tmp/pos-online.log`. |
| «[ESPERADO]» | Proceso Java en puerto `APP_PORT` (ej. 8111); log en `/tmp/pos-online.log`. |
| «[OBTENIDO]» | Variable `PID` del proceso; últimas líneas de `/tmp/pos-online.log`. |
| «[DECISIÓN]» | Continuar a Verify; si PID vacío o log con error, Verify puede fallar. |

Tras un deploy correcto, en la EC2 puedes revisar `/tmp/pos-online.log` y que el **security group** permita **8111** si pruebas desde fuera.

---

## Stage: Verify instance (solo rama main)

| Etiqueta en el log | Significado |
|--------------------|-------------|
| «[COMANDO]» | `curl -sf localhost:APP_PORT/actuator/health` o `/health`. |
| «[ESPERADO]» | Respuesta JSON con «status» UP y código HTTP 200. |
| «[OBTENIDO]» | Variable `SALIDA` con la respuesta del health (primeros 500 caracteres). |
| «[DECISIÓN]» | Si la respuesta contiene status UP, deploy correcto; si `curl` falló, el step falla (revisar puerto y `/tmp/pos-online.log`). |

---

## Post Actions y cierre

| Etiqueta / línea | Significado |
|------------------|-------------|
| «[DECISIÓN] Build exitoso...» | Bloque `post { success { echo ... } }`: mensaje con `IMAGE_NAME`, `IMAGE_TAG` y URL de la app. |
| «[DECISIÓN] Build fallido...» | Bloque `post { failure { echo ... } }`: indicación de revisar COMANDO/ESPERADO/OBTENIDO/DECISIÓN del stage en rojo. |
| `Finished: SUCCESS` / `FAILURE` | Estado final del build en Jenkins. |
| `ERROR: script returned exit code 1` | (Si hay fallo) El último `sh` que devolvió código distinto de 0. |

---

## Resumen: orden de stages (happy path)

| Stage | Qué hace en un build exitoso |
|-------|------------------------------|
| Checkout SCM | Clona o actualiza el repo desde Gitea con la credencial del job. |
| Prepare | Muestra Java/Maven y el workspace. |
| Build | `mvn clean compile` |
| Test | `mvn test` y publicación JUnit. |
| Lint | `mvn validate` (ligero). |
| Package | `mvn package -DskipTests` → JAR en `target/`. |
| Build image | `docker build` si hay `Dockerfile` y Docker en el agente. |
| Push to registry | `docker push` si **REGISTRY** está definido. |
| Cleanup | `pkill` de instancia anterior (rama **main**). |
| Deploy | Arranca el JAR en el puerto configurado (rama **main**). |
| Verify instance | `curl` al health (rama **main**). |

Referencias de contexto: [GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md), [REGISTRY-EC2-GRATIS.md](REGISTRY-EC2-GRATIS.md).

---

## Log real de referencia (build #14) — documentación y mejoras

A continuación se recogen fragmentos y el **log completo** de un Console Output real (build #14, versión anterior del Jenkinsfile con mensajes `=== ... ===`) para usar como referencia al documentar o al mejorar el pipeline. Sirve para reconocer salidas típicas, warnings no bloqueantes y por qué algunos stages se omiten.

### Log completo build #14 (texto plano)

Para búsqueda (grep), copiar fragmentos o comparar con un build nuevo, aquí va el log completo en texto plano:

```
Started by user Alejandro Perez
Obtained Jenkinsfile from git http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git
[Pipeline] Start of Pipeline
[Pipeline] node
Running on Jenkins in /var/lib/jenkins/workspace/pos-online-pipeline
[Pipeline] {
[Pipeline] stage
[Pipeline] { (Declarative: Checkout SCM)
[Pipeline] checkout
The recommended git tool is: NONE
using credential gitea-pos-online
 > git rev-parse --resolve-git-dir /var/lib/jenkins/workspace/pos-online-pipeline/.git # timeout=10
Fetching changes from the remote Git repository
 > git config remote.origin.url http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git # timeout=10
Fetching upstream changes from http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git
 > git --version # timeout=10
 > git --version # 'git version 2.47.3'
using GIT_ASKPASS to set credentials Gitea Jenkins
 > git fetch --tags --force --progress -- http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git +refs/heads/*:refs/remotes/origin/*
 > git rev-parse refs/remotes/origin/main^{commit} # timeout=10
Checking out Revision b068e9ff2c38231a8f6dd95134228bced1dd4d37 (refs/remotes/origin/main)
 > git config core.sparsecheckout # timeout=10
 > git checkout -f b068e9ff2c38231a8f6dd95134228bced1dd4d37 # timeout=10
Commit message: "commit"
 > git rev-list --no-walk 87d326e872eb81daffd6ce0ed7f6a229335ea250 # timeout=10
[Pipeline] }
[Pipeline] // stage
[Pipeline] withEnv
[Pipeline] {
[Pipeline] withEnv
[Pipeline] {
[Pipeline] stage
[Pipeline] { (Prepare)
[Pipeline] sh
+ echo '=== Prepare: entorno ==='
=== Prepare: entorno ===
+ java -version
openjdk version "17.0.18" 2026-01-20 LTS
OpenJDK Runtime Environment Corretto-17.0.18.9.1 (build 17.0.18+9-LTS)
OpenJDK 64-Bit Server VM Corretto-17.0.18.9.1 (build 17.0.18+9-LTS, mixed mode, sharing)
+ mvn -version
Apache Maven 3.0.5 (Red Hat 3.0.5-17)
Maven home: /usr/share/maven
Java version: 17.0.18, vendor: Amazon.com Inc.
Java home: /usr/lib/jvm/java-17-amazon-corretto.x86_64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "4.14.355-280.714.amzn2.x86_64", arch: "amd64", family: "unix"
++ pwd
+ echo 'Workspace: /var/lib/jenkins/workspace/pos-online-pipeline; rama: origin/main'
Workspace: /var/lib/jenkins/workspace/pos-online-pipeline; rama: origin/main
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Build)
[Pipeline] sh
+ echo '=== Build: compilando fuentes (mvn clean compile) ==='
=== Build: compilando fuentes (mvn clean compile) ===
+ mvn clean compile -q
+ echo '=== Build: resultado ==='
=== Build: resultado ===
+ ls -la target/classes
+ head -20
total 60
drwxr-xr-x 6 jenkins jenkins   274 Mar 14 00:10 .
drwxr-xr-x 5 jenkins jenkins    66 Mar 14 00:09 ..
-rw-r--r-- 1 jenkins jenkins  7253 Mar 14 00:09 application-envDev.properties
-rw-r--r-- 1 jenkins jenkins  7029 Mar 14 00:09 application-envPpr.properties
-rw-r--r-- 1 jenkins jenkins  6901 Mar 14 00:09 application-envPrd.properties
-rw-r--r-- 1 jenkins jenkins 11760 Mar 14 00:09 application-envQas.properties
-rw-r--r-- 1 jenkins jenkins 18835 Mar 14 00:09 application-local.properties
-rw-r--r-- 1 jenkins jenkins   159 Mar 14 00:09 application.properties
drwxr-xr-x 3 jenkins jenkins   17 Mar 14 00:10 mx
drwxr-xr-x 8 jenkins jenkins   97 Mar 14 00:09 orders
drwxr-xr-x 9 jenkins jenkins  110 Mar 14 00:09 reports
drwxr-xr-x 8 jenkins jenkins   97 Mar 14 00:09 tickets
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Test)
[Pipeline] sh
+ echo '=== Test: ejecutando tests (salida visible, sin -q) ==='
=== Test: ejecutando tests (salida visible, sin -q) ===
+ mvn test -B
[INFO] Scanning for projects...
[WARNING] Some problems were encountered while building the effective model for mx.com.endtoend:pos-online:jar:0.0.1-SNAPSHOT
[WARNING] 'dependencyManagement.dependencies.dependency.exclusions.exclusion.artifactId' for org.quartz-scheduler:quartz:jar with value '*' does not match a valid id pattern. @ org.springframework.boot:spring-boot-dependencies:2.6.3, .../spring-boot-dependencies-2.6.3.pom, line 1595, column 25
[WARNING] 'dependencyManagement...' for com.netflix.eureka:eureka-core:jar with value '*' does not match a valid id pattern. @ spring-cloud-netflix-dependencies:3.1.3, ...
[WARNING] 'dependencyManagement...' for io.opentracing.brave:brave-opentracing:jar with value '*' does not match a valid id pattern. @ spring-cloud-sleuth-dependencies:3.1.3, ...
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[INFO] Building pos-online 0.0.1-SNAPSHOT
[INFO] --- jacoco-maven-plugin:0.8.10:prepare-agent (prepare-agent) @ pos-online ---
[INFO] argLine set to -javaagent:.../org.jacoco.agent-0.8.10-runtime.jar=destfile=.../target/jacoco.exec,includes=**/domain/**:**/application/**:**/infrastructure/**:**/utilities/**:**/utils/**,excludes=...
[INFO] --- maven-resources-plugin:2.7:resources (default-resources) @ pos-online ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 6 resources
[INFO] Copying 234 resources
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ pos-online ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2602 source files to .../pos-online-pipeline/target/classes
[WARNING] .../WebConfig.java uses or overrides a deprecated API. Recompile with -Xlint:deprecation for details.
[WARNING] .../ArticleCFSamanoRepository.java: Some input files use or override a deprecated API that is marked for removal. Recompile with -Xlint:removal for details.
[WARNING] .../OrderBusinessMethodOne.java: Some input files use unchecked or unsafe operations. Recompile with -Xlint:unchecked for details.
[INFO] --- maven-resources-plugin:2.7:testResources (default-testResources) @ pos-online ---
[INFO] skip non existing resourceDirectory .../src/test/resources
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ pos-online ---
[INFO] Compiling 7 source files to .../target/test-classes
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ pos-online ---
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running mx.com.smartbussiness.generic.utils.DoubleToBigDecimalConverterTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.129 s - in mx.com.smartbussiness.generic.utils.DoubleToBigDecimalConverterTest
[INFO] Running mx.com.endtoend.genericCommonsFileds.utilities.DateUtilTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.148 s - in mx.com.endtoend.genericCommonsFileds.utilities.DateUtilTest
[INFO] Running mx.com.endtoend.genericCommonsFileds.utilities.StringUtilTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.002 s - in mx.com.endtoend.genericCommonsFileds.utilities.StringUtilTest
[INFO] Running mx.com.endtoend.PosOnlineApplicationTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.162 s - in mx.com.endtoend.PosOnlineApplicationTest
[INFO] Running mx.com.endtoend.infrastructure.payments.common.converters.InvoiceReferenceConverterTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.002 s - in mx.com.endtoend.infrastructure.payments.common.converters.InvoiceReferenceConverterTest
[INFO] Running mx.com.endtoend.domain.creditNote.business.validations.CreditNoteValidationTest
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.006 s - in mx.com.endtoend.domain.creditNote.business.validations.CreditNoteValidationTest
[INFO] Running mx.com.endtoend.domain.payments.services.PaymentMathServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.002 s - in mx.com.endtoend.domain.payments.services.PaymentMathServiceTest
[INFO] Results:
[INFO] Tests run: 54, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time: 1:32.517s
[INFO] Finished at: Sat Mar 14 00:12:38 UTC 2026
[INFO] Final Memory: 52M/303M
Post stage
[Pipeline] junit
Recording test results
[Checks API] No suitable checks publisher found.
[Pipeline] sh
+ echo '=== Test: resumen surefire ==='
=== Test: resumen surefire ===
+ find target/surefire-reports -name '*.txt' -exec echo '--- {} ---' ';' -exec cat '{}' ';'
--- target/surefire-reports/mx.com.smartbussiness.generic.utils.DoubleToBigDecimalConverterTest.txt ---
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0 ...
--- target/surefire-reports/mx.com.endtoend.genericCommonsFileds.utilities.DateUtilTest.txt ---
Tests run: 7, Failures: 0, ...
--- target/surefire-reports/mx.com.endtoend.genericCommonsFileds.utilities.StringUtilTest.txt ---
Tests run: 10, Failures: 0, ...
--- target/surefire-reports/mx.com.endtoend.PosOnlineApplicationTest.txt ---
Tests run: 1, Failures: 0, ...
--- target/surefire-reports/mx.com.endtoend.infrastructure.payments.common.converters.InvoiceReferenceConverterTest.txt ---
Tests run: 5, Failures: 0, ...
--- target/surefire-reports/mx.com.endtoend.domain.creditNote.business.validations.CreditNoteValidationTest.txt ---
Tests run: 15, Failures: 0, ...
--- target/surefire-reports/mx.com.endtoend.domain.payments.services.PaymentMathServiceTest.txt ---
Tests run: 6, Failures: 0, ...
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Lint)
[Pipeline] sh
+ echo '=== Lint: validación Maven ==='
=== Lint: validación Maven ===
+ mvn validate -q
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Package)
[Pipeline] sh
+ echo '=== Package: empaquetando JAR (mvn package -DskipTests) ==='
=== Package: empaquetando JAR (mvn package -DskipTests) ===
+ mvn package -DskipTests -q
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
+ echo '=== Package: artefactos generados ==='
=== Package: artefactos generados ===
+ ls -la target/pos-online-0.0.1-SNAPSHOT.jar
-rw-r--r-- 1 jenkins jenkins 123873980 Mar 14 00:14 target/pos-online-0.0.1-SNAPSHOT.jar
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Build image)
[Pipeline] fileExists
[Pipeline] sh
+ command -v docker
+ echo '=== Build image: docker no encontrado, omitiendo (instalar en EC2: docs/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) ==='
=== Build image: docker no encontrado, omitiendo (instalar en EC2: docs/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) ===
+ exit 0
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Push to registry)
Stage "Push to registry" skipped due to when conditional
[Pipeline] getContext
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Cleanup)
Stage "Cleanup" skipped due to when conditional
[Pipeline] getContext
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Deploy)
Stage "Deploy" skipped due to when conditional
[Pipeline] getContext
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Verify instance)
Stage "Verify instance" skipped due to when conditional
[Pipeline] getContext
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Declarative: Post Actions)
[Pipeline] echo
Pipeline pos-online OK: pos-online:14. App: http://<EC2>:8111/health
[Pipeline] }
[Pipeline] // stage
[Pipeline] }
[Pipeline] // withEnv
[Pipeline] }
[Pipeline] // withEnv
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
Jenkins 2.541.2
```

*(Rutas largas del workspace abreviadas con `...` en algunas líneas para legibilidad; el log original las muestra completas.)*

### Log build #15 (nueva versión: COMANDO / ESPERADO / OBTENIDO / DECISIÓN)

A partir del build #15 el Jenkinsfile imprime las etiquetas **[COMANDO]**, **[ESPERADO]**, **[OBTENIDO]** y **[DECISIÓN]** en cada stage. Fragmento de referencia (cabecera, Checkout, Prepare y comienzo de Build):

```
Started by user Alejandro Perez
Obtained Jenkinsfile from git http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git
[Pipeline] Start of Pipeline
[Pipeline] node
Running on Jenkins in /var/lib/jenkins/workspace/pos-online-pipeline
[Pipeline] {
[Pipeline] stage
[Pipeline] { (Declarative: Checkout SCM)
[Pipeline] checkout
using credential gitea-pos-online
...
Checking out Revision 8064df49f5514b02044868a97bce7e2de1e799a3 (refs/remotes/origin/main)
Commit message: "commit"
[Pipeline] }
[Pipeline] // stage
[Pipeline] withEnv
[Pipeline] {
[Pipeline] withEnv
[Pipeline] {
[Pipeline] stage
[Pipeline] { (Prepare)
[Pipeline] sh
+ echo '[COMANDO] Se ejecutará: java -version, mvn -version, pwd'
[COMANDO] Se ejecutará: java -version, mvn -version, pwd
+ echo '[ESPERADO] Versión de Java 17 y Maven; ruta del workspace y rama.'
[ESPERADO] Versión de Java 17 y Maven; ruta del workspace y rama.
+ java -version
openjdk version "17.0.18" 2026-01-20 LTS
...
+ mvn -version
Apache Maven 3.0.5 (Red Hat 3.0.5-17)
...
++ pwd
+ echo '[OBTENIDO] Workspace: /var/lib/jenkins/workspace/pos-online-pipeline; rama: origin/main'
[OBTENIDO] Workspace: /var/lib/jenkins/workspace/pos-online-pipeline; rama: origin/main
+ echo '[DECISIÓN] Continuar al stage Build si el entorno está disponible.'
[DECISIÓN] Continuar al stage Build si el entorno está disponible.
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Build)
[Pipeline] sh
+ echo '[COMANDO] Se ejecuta: mvn clean compile -q'
[COMANDO] Se ejecuta: mvn clean compile -q
+ echo '[ESPERADO] Código de salida 0; directorio target/classes con .class generados.'
[ESPERADO] Código de salida 0; directorio target/classes con .class generados.
+ mvn clean compile -q
+ R=0
+ echo '[OBTENIDO] Código de salida de mvn: 0'
[OBTENIDO] Código de salida de mvn: 0
+ ls -la target/classes
+ head -20
total 60 ... application-envDev.properties ... mx/ orders/ reports/ tickets/
+ echo '[DECISIÓN] Si R=0 continuar a Test; si R!=0 el pipeline se marca como fallido.'
[DECISIÓN] Si R=0 continuar a Test; si R!=0 el pipeline se marca como fallido.
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Test)
...
+ echo '[COMANDO] Se ejecuta: mvn test -B'
[COMANDO] Se ejecuta: mvn test -B
+ echo '[ESPERADO] Tests run: N, Failures: 0, Errors: 0; BUILD SUCCESS.'
[ESPERADO] Tests run: N, Failures: 0, Errors: 0; BUILD SUCCESS.
+ mvn test -B
[INFO] ... Tests run: 54, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time: 1:29.825s
[INFO] Final Memory: 63M/308M
+ R=0
+ echo '[OBTENIDO] Código de salida de mvn test: 0'
[OBTENIDO] Código de salida de mvn test: 0
+ echo '[DECISIÓN] Si R=0 continuar a Lint; si R!=0 el pipeline falla (revisar tests fallidos).'
[DECISIÓN] Si R=0 continuar a Lint; si R!=0 el pipeline falla (revisar tests fallidos).
Post stage
[Pipeline] junit
[Pipeline] sh
+ echo '[OBTENIDO] Resumen Surefire por clase:'
[OBTENIDO] Resumen Surefire por clase:
+ find target/surefire-reports -name '*.txt' ...
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Lint)
+ echo '[COMANDO] Se ejecuta: mvn validate -q'
[COMANDO] Se ejecuta: mvn validate -q
+ echo '[ESPERADO] Validación del POM y estructura del proyecto; no debe fallar el step.'
[ESPERADO] Validación del POM y estructura del proyecto; no debe fallar el step.
+ mvn validate -q
+ R=0
+ echo '[OBTENIDO] Código de salida: 0 (se ignora con || true)'
[OBTENIDO] Código de salida: 0 (se ignora con || true)
+ echo '[DECISIÓN] Siempre continuar a Package.'
[DECISIÓN] Siempre continuar a Package.
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Package)
+ echo '[COMANDO] Se ejecuta: mvn package -DskipTests -q'
[COMANDO] Se ejecuta: mvn package -DskipTests -q
+ echo '[ESPERADO] Código 0; JAR en target/pos-online-*.jar'
[ESPERADO] Código 0; JAR en target/pos-online-*.jar
+ mvn package -DskipTests -q
...
```

**Qué aporta esta versión:** en cada stage se ve qué comando se ejecuta, qué se espera, qué se obtuvo (código de salida en `R`, listados, memoria 63M/308M en Test) y qué decisión se toma. Útil para cotejar tu **Console Output** con un build de referencia. Build #15: Total time test 1:29.825s, Final Memory 63M/308M.

### Cabecera y Checkout

- `Started by user Alejandro Perez` — build lanzado a mano.
- `Obtained Jenkinsfile from git http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git` — repo y Jenkinsfile usados.
- `Running on Jenkins in /var/lib/jenkins/workspace/pos-online-pipeline` — agente built-in, workspace del job.
- `using credential gitea-pos-online` — credencial para Gitea.
- `Checking out Revision b068e9f... (refs/remotes/origin/main)` — commit que se está compilando.
- `Commit message: "commit"` — mensaje del commit.

### Prepare

- `openjdk version "17.0.18"` — Java 17 (Corretto).
- `Apache Maven 3.0.5 (Red Hat 3.0.5-17)` — Maven instalado.
- `OS name: "linux", version: "4.14.355-280.714.amzn2.x86_64"` — Amazon Linux 2.
- `Workspace: /var/lib/jenkins/workspace/pos-online-pipeline; rama: origin/main` — ruta y rama.

### Build: salida de `target/classes`

Tras `mvn clean compile -q`, un `ls -la target/classes | head -20` típico muestra:

- `application-envDev.properties`, `application-envPpr.properties`, `application-envPrd.properties`, `application-envQas.properties`, `application-local.properties`, `application.properties` — recursos filtrados.
- Directorios: `mx/`, `orders/`, `reports/`, `tickets/` — paquetes compilados.

En un build verde, `target/classes` existe y contiene los paquetes compilados listados arriba.

### Test: warnings de Maven y resumen

**Warnings que pueden aparecer (no bloquean el build):**

- `'dependencyManagement.dependencies.dependency.exclusions.exclusion.artifactId' for org.quartz-scheduler:quartz:jar with value '*' does not match a valid id pattern` — viene de Spring Boot / Spring Cloud BOM; no impide compilar ni ejecutar tests.
- `Some input files use or override a deprecated API` en `WebConfig.java`, `ArticleCFSamanoRepository.java`, `OrderBusinessMethodOne.java` — deprecaciones o unchecked; el build sigue.

**Líneas útiles para documentación:**

- `Compiling 2602 source files to .../target/classes` — tamaño del proyecto (fuentes).
- `Compiling 7 source files to .../target/test-classes` — tests compilados.
- `[INFO]  T E S T S` — inicio de Surefire.
- Por cada clase: `Tests run: N, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: ... s - in <Clase>`.
- `Tests run: 54, Failures: 0, Errors: 0, Skipped: 0` — resumen global.
- `Total time: 1:32.517s` — duración de la fase test.
- `Final Memory: 52M/303M` — uso de memoria JVM al final de la fase test (dato de referencia).

**Clases de test que aparecen en el log (build #14):**

- `DoubleToBigDecimalConverterTest` (10 tests)
- `DateUtilTest` (7)
- `StringUtilTest` (10)
- `PosOnlineApplicationTest` (1)
- `InvoiceReferenceConverterTest` (5)
- `CreditNoteValidationTest` (15)
- `PaymentMathServiceTest` (6)

### Package

- `SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder". SLF4J: Defaulting to no-operation (NOP) logger implementation` — mensaje conocido cuando no hay binding de SLF4J en el classpath en tiempo de ejecución de Maven; no suele afectar al JAR final si la app usa Logback/Log4j en runtime.
- `ls -la target/pos-online-0.0.1-SNAPSHOT.jar` → `123873980` bytes (~118 MB) — tamaño típico del JAR de pos-online.

### Build image (cuando Docker no está en el agente)

- `[Pipeline] fileExists` — Jenkins comprueba si existe `Dockerfile` (el stage solo corre si existe).
- `+ command -v docker` — el script comprueba si el comando `docker` está en el PATH.
- Mensaje de **omitir** el build de imagen si no hay Docker: el stage termina con **éxito** para no detener el pipeline (en el happy path con Docker instalado verás `docker build` en su lugar).
- `+ exit 0` — el stage termina correctamente.

### Stages omitidos por condición «when»

- `Stage "Push to registry" skipped due to when conditional` — la variable `REGISTRY` no está definida o está vacía.
- `Stage "Cleanup" skipped due to when conditional`  
- `Stage "Deploy" skipped due to when conditional`  
- `Stage "Verify instance" skipped due to when conditional`  

En este build, Cleanup / Deploy / Verify están condicionados a `branch 'main'`. Si el job corre en una rama distinta (o la variable `GIT_BRANCH` no es exactamente `main`), esos stages se omiten. Para que se ejecuten en la rama principal, el job debe estar configurado para construir `main` y la condición `when { branch 'main' }` debe coincidir.

### Cierre

- `Pipeline pos-online OK: pos-online:14. App: http://<EC2>:8111/health` — mensaje de éxito del `post { success }`.
- `Finished: SUCCESS` — estado final del build.

---

## Mensajes que puedes ver (y no impiden el éxito)

En builds que terminan en **`Finished: SUCCESS`** a veces aparecen **avisos** de Maven o del compilador (exclusiones en el POM, APIs deprecadas, mensajes SLF4J, etc.): son habituales y **no invalidan** un pipeline verde. Un stage omitido con `skipped due to when conditional` indica que la condición (`branch`, `REGISTRY`, `Dockerfile`, etc.) no aplicaba en ese build, no necesariamente un error.

---

## Datos de referencia para mejoras (build #14)

- **Fuentes compiladas:** 2602 en `target/classes`, 7 en `target/test-classes`.
- **Tests:** 54 en total, 7 clases de test.
- **Tiempo test:** ~1 min 32 s.
- **Memoria Maven (test):** 52M/303M.
- **Tamaño JAR:** ~118 MB (`pos-online-0.0.1-SNAPSHOT.jar`).
- **Workspace:** `/var/lib/jenkins/workspace/pos-online-pipeline`.
- **Entorno:** Amazon Linux 2, kernel amzn2; Java 17 Corretto; Maven 3.0.5.

Estos valores sirven para estimar tiempos, ajustar timeouts, memoria y para comparar builds futuros.

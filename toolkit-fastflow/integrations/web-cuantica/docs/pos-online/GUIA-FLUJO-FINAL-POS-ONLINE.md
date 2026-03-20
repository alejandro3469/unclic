# Guía del flujo final — pos-online

Documenta **dónde se ejecuta cada paso**, **qué output se obtiene** y **en qué URL, pestaña o app revisarlo**.  
**Esta guía está escrita para cuando el producto esté terminado:** los outputs descritos son los que se obtendrán en ese estado. Si hoy al probar ves advertencias (p. ej. JaCoCo "Rule violated", Lint "integer expression expected"), son esperables hasta completar implementación; al finalizar, el flujo y las URLs de esta guía serán la referencia.

---

## 0. Resumen rápido: comando → output → dónde revisar

| Lo que quieres ver | Output que obtienes | Dónde revisarlo (URL / archivo / pestaña) |
|--------------------|---------------------|--------------------------------------------|
| **Build Maven** | `BUILD SUCCESS`, Tests run: 54, Failures: 0, JAR instalado en target/ y .m2 | **Terminal** donde ejecutaste `mvn clean install`; archivo **target/pos-online-0.0.1-SNAPSHOT.jar** |
| **Reporte de tests** | Tests run por clase (DoubleToBigDecimalConverterTest, PosOnlineApplicationTest, etc.) | **target/surefire-reports/** (XML/TXT); **terminal** (resumen final) |
| **Cobertura JaCoCo** | "Analyzed bundle 'pos-online' with 1571 classes"; cuando esté listo sin "Rule violated" | **target/site/jacoco/index.html** → abrir en **navegador** (file:// o servido) |
| **App en marcha** | "Tomcat started on port(s): 8111", "Started PosOnlineApplication in X seconds" | **http://localhost:8111** (app); **http://localhost:8111/h2-console** (H2); **http://localhost:8111/actuator** (actuator); **terminal** (logs) |
| **Pipeline local** | Prepare ✓, Build ✓, Test ✓, Lint ✓, Package ✓; JARs por perfil en release-jars/ | **Terminal** (todo el log); carpeta **release-jars/** (pos-online-envPrd.jar, etc.) |
| **Jenkins** | Build estable (azul); consola con Prepare → Build → Test → Lint → Package | **http://localhost:8080** → job → último build → **Console Output** (pestaña navegador) |
| **Registry** | Imagen con tag (ej. pos-online:latest o pos-online:main-123) | **http://localhost:5000/v2/_catalog** (JSON); **http://localhost:5000/v2/pos-online/tags/list** (tags) |
| **Dashboard** | Página con enlaces a Jenkins, Registry, App, Config | **deploy/dashboard-demo-jenkins-registry.html** (file:// o servido, ej. **http://localhost:9090/dashboard-demo-jenkins-registry.html**) |
| **Interfaz central del flujo (POS → Jenkins)** | Una sola pantalla: paso manual = commit; resto = enlaces a Jenkins, Registry, App. Para despliegue gratuito AWS. | **deploy/dashboard-flujo-pos-jenkins.html** — observar todo el flujo tras hacer commit. URLs configurables (local o IP EC2). |
| **Config (todas las tecnologías)** | Tablas con Jenkins, Registry, Pipeline, Terraform, K8s, Dashboard | **deploy/config-ui.html** (o **http://localhost:9090/config-ui.html** si usas `node scripts/serve-config-ui.js`) |

### Tabla por comando (qué ejecutar → qué ver → dónde)

| Comando / acción | Dónde se ejecuta | Output que obtendrás (producto terminado) | URL / archivo / pestaña para revisar |
|------------------|------------------|--------------------------------------------|---------------------------------------|
| `mvn clean install` | Terminal, raíz pos-online | `BUILD SUCCESS`, Tests run: 54, Failures: 0, JAR en target/ y .m2 | Terminal; **target/pos-online-0.0.1-SNAPSHOT.jar**; **target/site/jacoco/index.html** (navegador) |
| `mvn spring-boot:run -Dspring-boot.run.profiles=local` | Terminal, raíz pos-online | `Tomcat started on port(s): 8111`, `Started PosOnlineApplication in X seconds` | **http://localhost:8111** (app); **http://localhost:8111/h2-console**; **http://localhost:8111/actuator/health**; terminal (logs) |
| `./run-jenkins-pipeline-local.sh` | Terminal, raíz pos-online | Prepare ✓, Build ✓, Test ✓, Lint ✓, Package ✓; JARs en release-jars/ | Terminal; carpeta **release-jars/** (pos-online-envPrd.jar, pos-online-envQas.jar, pos-online-envPpr.jar) |
| Abrir Jenkins | Navegador | Build estable (azul); consola del job | **http://localhost:8080** → job → último build → **Console Output** |
| Ver registry | Navegador | JSON con repos e imágenes | **http://localhost:5000/v2/_catalog**; **http://localhost:5000/v2/pos-online/tags/list** |
| Abrir dashboard | Navegador | Página con enlaces a Jenkins, Registry, App, Config | **http://localhost:9090/dashboard-demo-jenkins-registry.html** (o file://) |
| Abrir Config UI | Navegador | Todas las configuraciones editables (App, Pipeline, Jenkins, Registry, Terraform, K8s, Dashboard) | **http://localhost:9090/config-ui.html** (o file://) |

---

## 1. Dónde se ejecuta todo

| Dónde | Ruta / entorno |
|-------|-----------------|
| **Repo pos-online** | `proyectos-gitlab/pos-online` (o la ruta donde tengas clonado el repo). |
| **Terminal** | Cualquier terminal con Java 11, Maven y (opcional) Docker, en la raíz del repo. |
| **Servidor de la app** | Mismo equipo o servidor donde ejecutes `mvn spring-boot:run` o el JAR. |
| **Jenkins** | Servidor donde esté instalado Jenkins (local: misma máquina, puerto 8080). |
| **Registry** | Local: `localhost:5000` (registry:2) o GitLab/Docker Hub en navegador. |

---

## 2. Build: `mvn clean install`

### Dónde se ejecuta

- **Comando:** `mvn clean install`
- **Directorio:** Raíz del repo pos-online (`/Users/wallfacer/proyectos-gitlab/pos-online` o equivalente).
- **Interfaz:** Terminal (consola).

### Output esperado (producto terminado)

En la **terminal** verás algo como:

```
[INFO] --- surefire:2.22.2:test (default-test) @ pos-online ---
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running mx.com.smartbussiness.generic.utils.DoubleToBigDecimalConverterTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
...
[INFO] Results:
[INFO] Tests run: 54, Failures: 0, Errors: 0, Skipped: 0
[INFO] --- jar:3.2.2:jar (default-jar) @ pos-online ---
[INFO] Building jar: .../target/pos-online-0.0.1-SNAPSHOT.jar
[INFO] --- spring-boot:2.6.3:repackage (repackage) @ pos-online ---
[INFO] --- jacoco:0.8.10:report (report) @ pos-online ---
[INFO] Analyzed bundle 'pos-online' with 1571 classes
[INFO] --- install:2.5.2:install (default-install) @ pos-online ---
[INFO] Installing .../pos-online-0.0.1-SNAPSHOT.jar to .../.m2/repository/...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  ~27 s
```

- Cuando el producto esté terminado, JaCoCo **no** mostrará `Rule violated for bundle pos-online` (instructions/lines/branches covered); el reporte se generará igual en `target/site/jacoco/`.

### Dónde revisar el resultado

| Qué | Dónde |
|-----|--------|
| JAR instalado | `target/pos-online-0.0.1-SNAPSHOT.jar` y copia en `~/.m2/repository/mx/com/endtoend/pos-online/0.0.1-SNAPSHOT/`. |
| Reporte de tests | `target/surefire-reports/` (XML y TXT por clase de test). |
| Reporte JaCoCo (cobertura) | `target/site/jacoco/index.html` — abrirlo en el **navegador** (file:// o servido por un servidor estático). |
| Logs de Maven | Salida estándar de la terminal donde ejecutaste el comando. |

---

## 3. Ejecutar la aplicación: `mvn spring-boot:run -Dspring-boot.run.profiles=local`

### Dónde se ejecuta

- **Comando:** `mvn spring-boot:run -Dspring-boot.run.profiles=local`
- **Directorio:** Raíz del repo pos-online.
- **Interfaz:** Terminal (el proceso queda en primer plano).

### Output esperado (producto terminado)

En la **terminal** verás algo como:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 :: Spring Boot ::                (v2.6.3)
...
The following profiles are active: local
...
H2 console available at '/h2-console'. Databases available at 'jdbc:h2:mem:pos_security_local', 'jdbc:h2:mem:calzada_pos_local', ...
...
Tomcat started on port(s): 8111 (http)
...
Started PosOnlineApplication in 12.676 seconds (JVM running for 12.823)
```

- Sin errores de conexión a BD o servicios externos (según configuración `local`).

### Dónde revisar la aplicación

| Qué | URL / enlace | Pestaña o app |
|-----|---------------|----------------|
| **Aplicación pos-online** | **http://localhost:8111** | Navegador: pestaña con la URL. En perfil `local` el puerto por defecto es **8111**. |
| **Consola H2 (BD en memoria)** | **http://localhost:8111/h2-console** | Navegador: pestaña. JDBC URL según el log (ej. `jdbc:h2:mem:pos_security_local`). |
| **Actuator (health, info)** | **http://localhost:8111/actuator** | Navegador: lista de endpoints; **http://localhost:8111/actuator/health** para comprobar que la app está viva. |
| **Logs del servidor** | — | Terminal donde se ejecutó `spring-boot:run`: toda la salida (Hibernate, Spring Security, etc.). |

Cuando el producto esté terminado, un endpoint de health (por ejemplo `/actuator/health`) devolverá JSON con estado UP; se puede usar para comprobar desde scripts o desde el dashboard.

---

## 4. Pipeline local: `./run-jenkins-pipeline-local.sh`

### Dónde se ejecuta

- **Comando:** `./run-jenkins-pipeline-local.sh`
- **Directorio:** Raíz del repo pos-online (el script está en la raíz).
- **Interfaz:** Terminal.

### Orden de etapas y output esperado (producto terminado)

En la **terminal** verás una secuencia como:

```
=== Prepare (dependencia obligatoria generic-model) ===
  ✓ generic-model ya en ~/.m2; omitiendo.
=== Build ===
[INFO] BUILD SUCCESS
  ✓ Build OK.
=== Test + JaCoCo ===
[INFO] Tests run: 54, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
  ✓ Test OK.
=== Lint (Checkstyle) ===
  ✓ Lint OK.
=== Package (tres perfiles: envPrd, envQas, envPpr — mismo shell que Jenkins) ===
[PASO 1/3] Perfil envPrd (producción)...
[RESULTADO] exit_code=0
[AFIRMACION] JAR envPrd generado y copiado a release-jars/.
[PASO 2/3] Perfil envQas (QA)...
[PASO 3/3] Perfil envPpr (preproducción)...
```

| Etapa | Qué hace | Output esperado | Dónde revisar |
|-------|----------|-----------------|----------------|
| **Prepare** | Comprueba generic-model (dependencia obligatoria). | `✓ generic-model ya en ~/.m2` o mensaje de descarga. | Terminal. |
| **Build** | `mvn clean install` (o equivalente). | `[INFO] BUILD SUCCESS`; JAR en `target/`. | Terminal; archivo `target/pos-online-0.0.1-SNAPSHOT.jar`. |
| **Test + JaCoCo** | Ejecuta tests y genera reporte de cobertura. | `Tests run: 54, Failures: 0, Errors: 0, Skipped: 0`; `BUILD SUCCESS`. | Terminal; `target/surefire-reports/`; `target/site/jacoco/index.html`. |
| **Lint** | Checkstyle u otro linter. | `BUILD SUCCESS`; sin violaciones críticas. | Terminal. |
| **Package** | Genera JARs por perfil (envPrd, envQas, envPpr) y los copia a `release-jars/`. | `[RESULTADO] exit_code=0` por perfil; JARs en `release-jars/`. | Terminal; carpeta **release-jars/** en la raíz del repo. |

### Dónde revisar el resultado del pipeline local

| Qué | Dónde |
|-----|--------|
| **Logs por etapa** | Salida completa en la **terminal** donde ejecutaste el script. |
| **JARs empaquetados** | Carpeta **release-jars/** en la raíz del repo: `pos-online-envPrd.jar`, `pos-online-envQas.jar`, `pos-online-envPpr.jar` (generados por el script en la etapa Package). |
| **Reportes de tests** | `target/surefire-reports/` y `target/site/jacoco/index.html` (igual que en el apartado 2). |

---

## 5. Jenkins (cuando esté instalado)

### Dónde se ejecuta

- Jenkins corre en un **servidor** (local o remoto), típicamente en el puerto **8080**.
- Los jobs se disparan desde la **UI de Jenkins** o por webhook desde GitLab.

### Output esperado (producto terminado)

- Job tipo **Pipeline** configurado con el Jenkinsfile del repo.
- Cada ejecución: etapas **Prepare → Build → Test → Lint → Package** (y en rama main: **Cleanup + Deploy** si está implementado).
- Build estable (icono azul); en la consola del build se ve el mismo flujo que en `run-jenkins-pipeline-local.sh`.

### Dónde revisar

| Qué | URL / enlace | Pestaña o app |
|-----|---------------|----------------|
| **Dashboard Jenkins** | **http://localhost:8080** (o http://&lt;IP-del-servidor&gt;:8080) | Navegador: pestaña. |
| **Lista de jobs** | http://localhost:8080 (dashboard) | Misma pestaña. |
| **Último build** | Click en el job → último build (#N) | Navegador. |
| **Consola del build** | Job → Build #N → **Console Output** | Navegador: salida línea a línea (equivalente a la terminal del pipeline local). |
| **Permisos y credenciales** | Manage Jenkins → **Credentials** / **Manage Users** | Navegador. |

---

## 6. Registry (cuando esté configurado)

### Dónde se ejecuta

- **Push:** desde Jenkins (paso del pipeline) o desde terminal con `docker push`.
- **Registry local:** contenedor `registry:2` en el puerto **5000**.

### Output esperado (producto terminado)

- Imagen Docker de pos-online (o del artefacto que se containerice) con tag por build (ej. `BUILD_NUMBER` o `latest`).
- Push exitoso en la consola de Jenkins o en la terminal.

### Dónde revisar

| Qué | URL / enlace | Pestaña o app |
|-----|---------------|----------------|
| **Catálogo (registry local)** | **http://localhost:5000/v2/_catalog** | Navegador: pestaña; devuelve JSON con la lista de repositorios. |
| **Tags de una imagen** | **http://localhost:5000/v2/&lt;nombre-imagen&gt;/tags/list** | Navegador: JSON con los tags. |
| **Docker Hub / GitLab Registry** | URL del proveedor (ej. hub.docker.com, GitLab → Container Registry) | Navegador: pestaña; UI del proveedor. |

---

## 7. Dashboard y Config UI

Cuando esté desplegada la página de dashboard en el repo:

### Dónde se abre

- **Dashboard:** `deploy/dashboard-demo-jenkins-registry.html` (en este toolkit) o la copia en pos-online: `deploy/dashboard.html`.
- **Config UI (todas las configuraciones):** `deploy/config-ui.html` — ver y administrar Jenkins, Registry, Pipeline, Terraform, K8s, Dashboard desde un solo lugar (ver **config/README.md** y **docs/README.md**).
- **Cómo abrirlo:**  
  - **file://:** abrir el archivo directamente en el navegador.  
  - **Servido:** `cd deploy && python3 -m http.server 9090` (o `node scripts/serve-config-ui.js` si aplica) y abrir **http://localhost:9090/dashboard-demo-jenkins-registry.html** y **http://localhost:9090/config-ui.html**.

### Qué enlaza el dashboard (cuando el producto esté terminado)

| Enlace | URL típica | Uso |
|--------|------------|-----|
| **Jenkins** | http://localhost:8080 | Acceso rápido al dashboard de Jenkins. |
| **Registry (catálogo)** | http://localhost:5000/v2/_catalog | Ver imágenes en registry local. |
| **Aplicación pos-online** | **http://localhost:8111** | Abrir la app en local (puerto por defecto con perfil `local`). En despliegue real puede ser otra URL (ej. 3000 o dominio). |
| **Config UI** | deploy/config-ui.html (o http://localhost:9090/config-ui.html) | Ver todas las configuraciones (Jenkins, Registry, Pipeline, etc.) en una sola pestaña. |
| **Consola del último build** | Manual: Jenkins → job → último build → Console Output | Recordatorio desde el dashboard (no hay URL directa a la consola de un build concreto). |

---

## 8. Resumen: flujo completo y dónde mirar

| Paso | Comando / acción | Dónde se ejecuta | Output clave | Dónde revisar (URL / archivo / pestaña) |
|------|-------------------|-------------------|--------------|------------------------------------------|
| 1. Build | `mvn clean install` | Terminal, raíz pos-online | BUILD SUCCESS, Tests 54, JAR en target | Terminal; `target/surefire-reports/`; `target/site/jacoco/index.html` |
| 2. Run app | `mvn spring-boot:run -Dspring-boot.run.profiles=local` | Terminal, raíz pos-online | Tomcat 8111, Started PosOnlineApplication | **http://localhost:8111**; **http://localhost:8111/h2-console**; **http://localhost:8111/actuator/health**; terminal |
| 3. Pipeline local | `./run-jenkins-pipeline-local.sh` | Terminal, raíz pos-online | Prepare ✓, Build ✓, Test ✓, Lint ✓, Package ✓; JARs en release-jars/ | Terminal; carpeta **release-jars/** |
| 4. Jenkins | Job Pipeline (Prepare → Build → Test → Lint → Package; main: Deploy) | Servidor Jenkins | Build estable, consola con mismos pasos que el script | **http://localhost:8080** → job → Build #N → **Console Output** |
| 5. Registry | Push desde Jenkins o `docker push` | Jenkins / terminal | Imagen con tag en registry | **http://localhost:5000/v2/_catalog** (local) o UI Docker Hub / GitLab |
| 6. Dashboard | Abrir deploy/dashboard-demo-jenkins-registry.html | Navegador | Página con enlaces a Jenkins, registry, app, Config UI | **http://localhost:9090/dashboard-demo-jenkins-registry.html** (si se sirve deploy/) o file:// |
| 7. Config UI | Abrir deploy/config-ui.html | Navegador | Tablas con todas las configuraciones (Jenkins, Registry, Pipeline, Terraform, K8s) | **http://localhost:9090/config-ui.html** o file://; ver **config/README.md** |

---

## 9. Notas para cuando falten implementaciones

- **JaCoCo:** Si hoy aparecen `Rule violated ... instructions covered ratio is 0.00`, al terminar el producto la cobertura debe cumplir los umbrales (ej. 10% instrucciones, 5% branches) o se ajustarán las reglas; el reporte seguirá en `target/site/jacoco/index.html`.
- **Health/Actuator:** Si en tu prueba no hay endpoint público de health, cuando esté listo se usará `/actuator/health` (o el que definan) para comprobaciones desde el dashboard o scripts.
- **Deploy desde Jenkins:** Si aún no existe la etapa Deploy en el Jenkinsfile, cuando se implemente se verá en la **Console Output** del job y (opcional) en la URL de la aplicación desplegada que se documente.
- **Puertos:** 8111 (app local), 8080 (Jenkins), 5000 (registry local), 9090 (dashboard/config-ui si sirves deploy/); si en tu entorno cambian, actualiza esta guía o el dashboard con las URLs correctas.
- **Script pipeline local:** Si en Lint aparece `integer expression expected` (p. ej. bug en comparación del script), es un detalle del script; el resultado sigue siendo "Lint OK" y no afecta el flujo. Corregir el script en pos-online cuando se pulan los scripts; cuando el producto esté terminado, esta guía sigue siendo válida para outputs y URLs.

Esta guía se puede copiar a pos-online como `docs/GUIA-FLUJO-FINAL.md` (o mantener en docs/pos-online dentro del toolkit). Ajusta puertos y rutas según el entorno real (Linux, otro host, etc.).

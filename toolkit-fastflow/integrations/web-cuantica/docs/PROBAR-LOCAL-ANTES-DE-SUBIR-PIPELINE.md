# Probar localmente antes de subir el pipeline (pos-online)

Qué ejecutar en tu máquina antes de hacer **push** a Gitea para que Jenkins no falle y qué cambios se hicieron para **ver logs detallados** en el pipeline.

---

## 1. Qué probar localmente (orden)

Desde la **raíz del repo pos-online** (clon de Gitea o copia de `repo-pos-fastflow`):

| Paso | Comando | Qué comprueba |
|------|--------|----------------|
| 1 | `mvn -version` | Maven y Java instalados (Java 17 recomendado). |
| 2 | `mvn clean compile` | Compila sin errores (y que **generic-model** esté instalado en `~/.m2` o como submódulo). |
| 3 | `mvn test` | Tests pasan; ves nombres de tests y resultado. |
| 4 | `mvn package -DskipTests` | Genera el JAR en `target/pos-online-*.jar`. |
| 5 (opcional) | `java -jar target/pos-online-*.jar --server.port=8111` | App arranca; probar `curl http://localhost:8111/actuator/health`. |

**Script todo-en-uno** (desde la raíz del repo):

```bash
mvn clean test && mvn package -DskipTests
```

Si eso pasa en local, el pipeline en Jenkins tiene muchas más probabilidades de pasar (salvo diferencias de entorno: generic-model en EC2, memoria, etc.).

---

## 2. Rutas locales y repos en Gitea (verificado con `git remote -v`)

**No confundir:** Si haces `git status` desde **web-cuantica** (o la raíz del proyecto), ves el repo **padre** (p. ej. rama `handoff/george-web-cuantica-front`) y cambios como `repo-pos-fastflow/Jenkinsfile`. Ese repo no es el que sube a Gitea. Para subir a Gitea hay que **entrar en cada repo** (`cd repo-pos-fastflow` o `cd smartbussiness-generic-model`) y allí hacer `git add` / `commit` / `push`. Ver [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md) para la tabla de “dos niveles de Git”.

Ambos repos del toolkit están **conectados a Gitea**. Estado comprobado:

| Repo | Ruta local (absoluta) | Remote a Gitea | Repo en Gitea (URL) |
|------|------------------------|----------------|----------------------|
| **pos-online** | `/Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow` | `origin` | `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git` |
| **smartbussiness-generic-model** | `/Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model` | `gitea` | `http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model.git` |

**Detalle remotes:**

- **repo-pos-fastflow:** `origin` → Gitea pos-online (fetch + push). Rama: `main` → `origin/main`.
- **smartbussiness-generic-model:** `gitea` → Gitea smartbussiness-generic-model; `origin` → GitLab. Rama: `main` → `gitea/main`.

---

## 2.1 Comandos: `cd` a repos locales y push a Gitea

Usar estas rutas y el **remote correcto** en cada repo (pos-online usa `origin`, generic-model usa `gitea`).

### pos-online (push con `origin`)

```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow
git remote -v
git add Jenkinsfile
git status
git commit -m "Pipeline: logs detallados + docker build por sh (sin plugin Docker)"
git push -u origin main
```

### smartbussiness-generic-model (push con `gitea`)

```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model
git remote -v
git add Jenkinsfile
git status
git commit -m "Pipeline: logs detallados por etapa" || true
git push -u gitea main
```

**Alternativa generic-model** (script que hace add, commit y push):

```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica
export GITEA_USER=alejandro-perez
export GITEA_REPO=smartbussiness-generic-model
bash scripts/push-generic-model-to-gitea.sh
```

**Desde la raíz del proyecto** (rutas relativas):

```bash
# pos-online
cd toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow
git add Jenkinsfile && git commit -m "Pipeline: logs + docker por sh" && git push -u origin main

# generic-model
cd toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model
git add Jenkinsfile && git commit -m "Pipeline: logs por etapa" || true
git push -u gitea main
```

---

## 3. Qué se corrigió / añadió para ver logs en Jenkins

En el **Jenkinsfile** se hizo lo siguiente para que en la consola se vea **qué se ejecuta y qué se obtiene**:

| Cambio | Dónde | Efecto |
|--------|--------|--------|
| **Echo por etapa** | Todos los stages | Líneas tipo `=== Prepare: entorno ===`, `=== Test: ejecutando tests ===`, `=== Deploy: arrancando JAR ===` para seguir el flujo. |
| **Quitar `-q` en Test** | Stage Test | Antes: `mvn test -q` (casi sin salida). Ahora: `mvn test -B` (batch). Se ve cada test que se ejecuta y si pasan o fallan. |
| **Publicar resultados JUnit** | Stage Test → post always | `junit '**/target/surefire-reports/*.xml'`. Jenkins muestra la pestaña **Test Result** con resumen y lista de tests. |
| **Resumen surefire en consola** | Stage Test → post always | `find target/surefire-reports -name "*.txt"` y se hace `cat` para ver resumen por clase en la misma consola. |
| **Build** | Stage Build | Tras compilar, se lista `target/classes` (primeras líneas) para ver que se generaron clases. |
| **Package** | Stage Package | Tras empaquetar, se lista `target/*.jar` para ver el JAR generado. |
| **Build image** | Stage Build image | Echo del tag de la imagen y `docker images` de `pos-online`. |
| **Deploy** | Stage Deploy | Echo del puerto, PID del proceso y últimas líneas de `/tmp/pos-online.log`. |
| **Verify** | Stage Verify instance | Echo antes del curl y el cuerpo de la respuesta de health. |

Después de subir este Jenkinsfile a Gitea y lanzar un build en Jenkins, verás:

- En la **consola**: mensajes claros por etapa y salida de `mvn test` (tests uno a uno).
- En **Test Result**: gráfica de tendencia y lista de tests (pasados/fallidos).
- En **Deploy/Verify**: confirmación de PID, log y health.

---

## 4. Resumen: qué falta subir o corregir

- **Subir a Gitea:** el **Jenkinsfile** actualizado (el de `repo-pos-fastflow` con logs y JUnit). Si también cambiaste `pom.xml` o código, sube todo.
- **Corregir (si aplica):**  
  - Que **generic-model** esté disponible en la EC2 de Jenkins (instalado con `mvn install` o vía pipeline).  
  - Que en el job de Jenkins no tengas parámetros que fuercen un script antiguo; que use **Pipeline from SCM** y rama `main` con script path `Jenkinsfile`.

No hace falta “corregir” nada más para los logs: con el Jenkinsfile nuevo, **probar local** (`mvn clean test && mvn package -DskipTests`) y luego **push a main** es suficiente para que el siguiente build muestre logs detallados y resultados de tests.

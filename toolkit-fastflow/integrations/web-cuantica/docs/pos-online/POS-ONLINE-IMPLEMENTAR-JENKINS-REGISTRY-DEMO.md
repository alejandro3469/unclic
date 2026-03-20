# Implementar en pos-online (GitLab): Jenkins + Registry + demo local y presentación

**Para:** Agente que implementa en el repo **pos-online** (GitLab).  
**Objetivo:** Probar todo **local primero** con herramientas open source (Terraform opcional, simular Kubernetes con minikube/k3s opcional), **debuggear por fases** (IDs, interfaces web, consolas, logs, tests), **aplicar tests** a registry, tags, rollbacks, preservar imágenes y configuración, y dejar un **flujo de commit y usuario final** que usuario final pueda ver en el repo local. Todo debe ser **instalable en cualquier Linux o servidor enterprise** y permitir una **presentación desde tu PC** (acceso al servidor local, Jenkins, permisos, pipelines de rutina y de deploy por ramas, registry con versiones/tags y política de retención, UI mínima para acceder a Jenkins).

**Coordinación:** Ver **[CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md](CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md)** y **[docs/README.md](../README.md)**. No mezclar cambios de este repo (toolkit) y pos-online en el mismo commit; repos separados.

---

## 1. Resumen: qué implementar en pos-online

| Componente | Qué hacer en pos-online |
|------------|-------------------------|
| **Jenkins** | Instalación open source (war o paquete). En este repo (toolkit): `docs/instalacion/INSTALAR-JENKINS.md`; en pos-online: `docs/INSTALAR-JENKINS.md`. |
| **Pipeline** | Jenkinsfile en raíz + `.jenkins/` con scripts (prepare, build, test, package, cleanup, deploy por rama). |
| **Registry** | Registry de imágenes (Docker Hub, GitLab Container Registry o registry local `registry:2`). Tags, retención por tiempo. |
| **Tests** | Tests que cubran: build, imagen, push, tags, rollback (desplegar imagen anterior), preservar imágenes y configuración. |
| **Debug por fases** | Cada fase con ID de build, logs en consola, interfaces web (Jenkins, registry si aplica) y tests automatizados. |
| **Flujo usuario final** | Documentar y mostrar: commit → pipeline → imagen en registry → deploy (o rollback). |
| **Instalable** | Script(s) y docs para instalar en Linux/enterprise (Java, Jenkins, Docker, registry). |
| **Presentación** | Guía para presentar desde tu PC: acceso servidor local, Jenkins, permisos, pipelines, registry, UI mínima. |

---

## 2. Probar local primero: opciones open source

### 2.1 Sin Terraform (mínimo para demo en tu PC)

En tu máquina (Linux, Mac o Windows con WSL):

1. **Java 11** — `sudo apt install openjdk-11-jdk` (Linux) o Homebrew (Mac).
2. **Docker** — Docker Engine o Docker Desktop. Para registry local: `docker run -d -p 5000:5000 --restart=always registry:2`.
3. **Jenkins** — `java -jar jenkins.war --httpPort=8080` (o puerto 8081 si 8080 lo usa otra app).
4. **Git** — clonar pos-online, configurar job Pipeline from SCM con el Jenkinsfile del repo.

No hace falta Terraform para esta demo local; Terraform se usa cuando quieras levantar Jenkins en AWS (ver **manifests/terraform/jenkins-aws/** en este toolkit).

### 2.2 Con Terraform (Jenkins en AWS o en una VM local)

- **AWS:** Copiar a pos-online (o referenciar) la estructura de **toolkit-fastflow/manifests/terraform/jenkins-aws/** (README, envs/dev, modules). Ver **APLICAR-FASTFLOW.md** en ese directorio.
- **VM local / enterprise:** El mismo Terraform puede adaptarse para un proveedor distinto (OpenStack, vSphere) o usarse solo como referencia de módulos (red, compute, Jenkins controller).

### 2.3 Simular Kubernetes (opcional)

Para probar “deploy” y rollback con imágenes del registry:

- **minikube** o **k3s** en local: `minikube start` o instalar k3s. Configurar `imagePullPolicy` y usar la imagen del registry local (ej. `localhost:5000/mi-app:tag` con minikube eval $(minikube docker-env) o registry accesible desde el cluster).
- **k3d** (K8s en Docker): útil para CI o demo en un solo equipo.

En pos-online puedes documentar: “Para probar deploy y rollback en local, usar minikube/k3s y aplicar los manifiestos que referencian la imagen del registry”.

---

## 3. Debug por fases: IDs, interfaces, consolas, logs y tests

Cada fase del pipeline debe poder inspeccionarse por **ID de build**, **interfaces web**, **consola** y **tests**.

| Fase | ID / referencia | Dónde ver (interfaces) | Logs / consola | Tests |
|------|------------------|-------------------------|----------------|--------|
| **1. Checkout** | `BUILD_NUMBER`, `GIT_COMMIT` | Jenkins → job → Build #N → Console Output | Salida de `git rev-parse` y archivos en workspace | Test que verifique que el código esperado está (ej. script que compruebe que existe un archivo clave). |
| **2. Build (compile/test)** | `BUILD_NUMBER` | Jenkins → Console Output, “Test” stage | Salida de Maven/npm/test | Tests unitarios e integración en el repo (ej. `mvn test` o `npm test`). |
| **3. Build imagen** | `IMAGE_TAG=${BUILD_NUMBER}` o tag semántico | Jenkins → Console Output; Docker/registry | `docker build` log, `docker images` | Test (script o job) que verifique que la imagen existe localmente o en registry. |
| **4. Push registry** | Mismo tag que la imagen | Jenkins → Console Output; Registry UI (Docker Hub, GitLab, o UI del registry) | `docker push` log | Test que liste tags en registry (API o CLI) y compruebe que el tag existe. |
| **5. Deploy / Rollback** | Tag de imagen desplegada | Jenkins → Console Output; app en navegador; K8s dashboard si aplica | Logs del deploy (kubectl, o script de despliegue) | Test de humo: `curl` al health de la app con la versión desplegada; test de rollback (desplegar tag anterior y verificar). |

**IDs útiles en Jenkins:**

- `BUILD_NUMBER` — número del build.
- `GIT_COMMIT` o `GIT_BRANCH` — commit y rama.
- `IMAGE_TAG` — usar `BUILD_NUMBER` o `GIT_COMMIT` corto para etiquetar la imagen (`app:${BUILD_NUMBER}`, `app:${GIT_COMMIT:0:7}`).

**Interfaces web recomendadas:**

- **Jenkins:** `http://localhost:8080` (o la IP del servidor) — jobs, builds, consola, credenciales.
- **Registry:** Si usas registry local sin UI, usar CLI (`docker pull`, `curl` a la API del registry). GitLab/Docker Hub tienen UI para ver tags e imágenes.
- **UI mínima (ver sección 7):** Una página HTML local con enlaces a Jenkins, al registry (si hay URL) y a la aplicación, para la presentación.

---

## 4. Tests: cubrir registry, tags, rollbacks, preservar imágenes y configuración

Aplicar tests en el repo pos-online (y en el pipeline) para:

### 4.1 Build y tests de aplicación

- Tests unitarios e integración existentes (Maven/npm según stack de pos-online).
- El pipeline debe fallar si estos tests fallan.

### 4.2 Imagen Docker

- **Test 1:** Tras `docker build`, comprobar que la imagen existe (`docker image inspect <nombre>:<tag>` o equivalente).
- **Test 2 (opcional):** Ejecutar el contenedor y hacer `curl` al health/readiness; salir con 0 solo si responde OK.

### 4.3 Registry y tags

- **Test 3:** Tras `docker push`, comprobar que el tag existe en el registry (API del registry o `docker pull` desde registry).
- **Test 4:** Comprobar que se han pusheado al menos dos tags si aplica (ej. `latest` y `BUILD_NUMBER` o `GIT_COMMIT`).

### 4.4 Rollback y preservar imágenes

- **Política de tags:** Documentar y (si aplica) automatizar: “Se conservan las últimas N imágenes” o “imágenes con tag distinto de `latest` se conservan X días”. Implementación depende del registry (Docker Hub retention, GitLab cleanup policy, o script cron que borre tags antiguos).
- **Test 5 (rollback):** Script o job que: (1) anote la versión actual desplegada; (2) despliegue la imagen con tag anterior; (3) verifique que la app responde y (opcional) que la versión reportada es la anterior.
- **Preservar imágenes:** No borrar por defecto las imágenes que se usan para rollback; la política de retención debe estar documentada (ej. en `docs/REGISTRY-RETENTION.md`).

### 4.5 Configuración

- **Test 6:** Tests que validen que la configuración necesaria para build/deploy está presente (variables de entorno o archivos de config sin secretos en el repo). No commitear secretos; usar Jenkins Credentials o variables de entorno inyectadas.

Ejemplo de ubicación en pos-online:

- Tests de app: en el propio proyecto (JUnit, Jest, etc.).
- Tests de pipeline/registry: scripts en `.jenkins/` o en `scripts/` que el pipeline llame (ej. `scripts/verify-image-in-registry.sh`, `scripts/verify-rollback.sh`).

---

## 5. Flujo de commit y usuario final (para mostrar a usuario final)

Objetivo: En el repo local (pos-online) poder **mostrar** un flujo completo de usuario final: desde commit hasta ver la versión en el registry (y opcionalmente desplegar o hacer rollback).

### 5.1 Flujo paso a paso

1. **Commit** — El desarrollador hace push a una rama (ej. `main` o `develop`).
2. **Jenkins** — Pipeline se dispara (webhook o polling). Fases: Checkout → Build/Test → Build imagen → Push a registry (con tag `BUILD_NUMBER` y `latest`).
3. **Registry** — La imagen aparece en el registry con sus tags; se puede ver en la UI o con `docker pull`.
4. **Deploy (opcional)** — Un job o script despliega la imagen `latest` (o un tag fijo) en el entorno elegido (local, K8s, VM).
5. **Rollback (opcional)** — Se elige un tag anterior en el registry y se redespliega; tests de humo verifican que la app responde.

### 5.2 Qué documentar en pos-online para usuario final

- **docs/COMO-PROBAR.md** — Cómo ejecutar el pipeline en local (script tipo `run-jenkins-pipeline-local.sh` si existe), en Jenkins, y cómo comprobar la aplicación (health, artefactos).
- **docs/FLUJO-COMMIT-A-REGISTRY.md** — Este flujo: commit → Jenkins → imagen con tags → registry; cómo ver versiones y cómo hacer rollback. Plantilla en este repo: **docs/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md** (copiar a pos-online y ajustar).
- **README del repo** — Inicio rápido: build, run, pipeline (Prepare → Build → Test → Package; en main, Cleanup + Deploy). Enlace a `docs/README.md` para instalación, pruebas y presentación.

Así usuario final (o cualquier desarrollador) puede abrir el repo local, seguir la doc y reproducir: commit → pipeline → registry → (deploy/rollback).

---

## 6. Instalable en cualquier Linux o servidor enterprise

Requisitos y pasos que deben quedar documentados (y si es posible, scripteados) en pos-online:

### 6.1 Requisitos

- **Sistema:** Cualquier Linux (Debian/Ubuntu, RHEL/CentOS) o servidor enterprise que permita Java y Docker.
- **Java** — 11 (LTS) para Jenkins.
- **Docker** — Engine 20.x+ (para build de imágenes y, opcional, registry local).
- **Git** — para clonar el repo y que Jenkins haga checkout.
- **Jenkins** — 2.x LTS (war o paquete oficial).

### 6.2 Instalación típica (resumen)

- Instalar Java 11, Docker, Git (según distro).
- Instalar Jenkins (ver **docs/INSTALAR-JENKINS.md** en pos-online).
- (Opcional) Registry local: `docker run -d -p 5000:5000 --restart=always registry:2`.
- Clonar pos-online, crear job “Pipeline from SCM”, apuntar al Jenkinsfile, configurar credenciales del registry si aplica.
- Ejecutar un build y comprobar que la imagen aparece en el registry.

Dejar en pos-online un **scripts/install-jenkins-local.sh** (o equivalente) que instale dependencias y levante Jenkins + registry en Docker si el equipo lo desea todo en contenedores. Documentar en `docs/INSTALAR-JENKINS.md` sin autor ni branding (según COORDINACION-AGENTES-POS-ONLINE-Y-FASTFLOW.md).

---

## 7. Presentación desde tu PC: servidor local, Jenkins, permisos, pipelines, registry, UI mínima

Objetivo: Poder hacer una **presentación en vivo** desde tu computadora mostrando el flujo completo.

### 7.1 Qué tener corriendo en local

- **Jenkins** en `http://localhost:8080` (o 8081).
- **Registry** accesible (localhost:5000 si es registry:2, o Docker Hub/GitLab en navegador).
- **Aplicación** desplegada (opcional): en local con `java -jar` o con Docker/K8s.

### 7.2 Orden sugerido para la presentación

1. **Acceso al servidor local** — Mostrar que accedes por navegador a Jenkins (y si aplica al registry).
2. **Permisos** — Gestionar Jenkins: usuarios, credenciales del registry, permisos del job (quién puede ejecutar).
3. **Pipelines de rutina** — Ejecutar un job que hace build + test (y opcionalmente build de imagen sin push).
4. **Pipeline de deploy por ramas** — Mostrar un pipeline multibranch o parametrizado: según la rama (main → deploy a “producción” local, develop → solo build/test). Mostrar un build por rama y los logs.
5. **Registry: versiones y tags** — Abrir el registry (UI o CLI) y mostrar las imágenes y tags generados por los builds; explicar la política de retención (cuánto tiempo se conservan, qué tags se preservan para rollback).
6. **UI mínima** — Una sola página HTML (por ejemplo `docs/demo/index.html` o `deploy/dashboard-demo.html`) con enlaces:
   - **Jenkins** — `http://localhost:8080` (o la URL que uses).
   - **Registry** — Enlace a la UI del registry (Docker Hub, GitLab, o a `http://localhost:5000/v2/_catalog` si usas registry:2 para listar repos).
   - **Aplicación** — URL de la app desplegada (ej. `http://localhost:3000` o el puerto que corresponda).

Así, en la presentación abres esa página y desde ahí accedes a Jenkins, al registry y a la app sin recordar URLs.

### 7.3 UI mínima (plantilla en este repo)

En este repo existe **deploy/dashboard-demo-jenkins-registry.html**: UI mínima con enlaces a Jenkins, registry (catálogo local), aplicación y consola de builds. Para pos-online: copiar ese archivo a `docs/demo/dashboard.html` o `deploy/dashboard.html` y ajustar las URLs (puertos de Jenkins, registry y aplicación según el stack de pos-online). Para GitLab Container Registry o Docker Hub, sustituir el enlace del registry por la URL de la UI correspondiente.

---

## 8. Qué copiar o reutilizar desde este repo (toolkit) (toolkit FastFlow)

| Necesidad | Dónde está (este repo (toolkit)) | Uso en pos-online |
|-----------|---------------------------|--------------------|
| Pipeline Jenkins (build → test → imagen → push) | **Jenkinsfile.example** | Adaptar a la estructura de pos-online (Maven/Java o Node); mismo flujo: test → build imagen → push con tags. |
| Script build+push sin Jenkins | **scripts/build-and-push.sh** | Base para `run-jenkins-pipeline-local.sh` o para probar el flujo en local antes de Jenkins. |
| Terraform Jenkins AWS | **manifests/terraform/jenkins-aws/** | Copiar o referenciar para desplegar Jenkins en AWS; limpiar branding. |
| Build y registry (docs) | **docs/pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md**, **docs/pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md** | Referencia para tags, push, variables y credenciales. |
| Guía local Jenkins + Docker + Registry | **docs/instalacion/GUIA-PASO-A-PASO-LOCAL-Y-EMPAQUETAR.md** (o toolkit-fastflow/docs) | Pasos para Java, Jenkins, Docker, registry local; adaptar a pos-online. |

No copiar a pos-online la documentación interna de agentes (handoff usuario final, multiagentes); solo lo necesario para instalar Jenkins, probar, presentar y hacer el flujo commit → registry → deploy/rollback.

---

## 9. Checklist para el agente que implementa en pos-online

- [ ] Jenkins instalable y documentado (`docs/INSTALAR-JENKINS.md`).
- [ ] Jenkinsfile en raíz + `.jenkins/` con scripts por fase (prepare, build, test, package, cleanup, deploy por rama).
- [ ] Registry configurado (local o GitLab/Docker Hub); tags con `BUILD_NUMBER` o `GIT_COMMIT`; documentada política de retención.
- [ ] Tests aplicados: build, imagen, push, tags, rollback, preservar imágenes, configuración (ver sección 4).
- [ ] Debug por fases: IDs de build, interfaces web (Jenkins, registry), consola y logs, tests por fase.
- [ ] Documentado flujo commit → pipeline → registry → deploy/rollback (`docs/FLUJO-COMMIT-A-REGISTRY.md` o equivalente).
- [ ] Instalable en Linux/enterprise: requisitos y pasos (y opcional script de instalación).
- [ ] Presentación: guía para acceder al servidor local, Jenkins, permisos, pipelines (rutina y deploy por ramas), registry (versiones/tags/retención), UI mínima (dashboard con enlaces).
- [ ] Sin autor ni branding en docs de usuario final (según COORDINACION-AGENTES-POS-ONLINE-Y-FASTFLOW.md).

---

## 10. Mensaje para pegar al agente de pos-online

*"Implementa en pos-online (GitLab) el flujo Jenkins + Registry + demo local, probando todo local primero con open source (Terraform opcional, simular K8s con minikube/k3s opcional). Debug por fases: cada fase con ID de build, interfaces web (Jenkins, registry), consolas y logs, y tests. Aplica tests a todo: registry, tags, rollbacks, preservar imágenes y configuración. Deja documentado el flujo de commit y usuario final para que usuario final pueda ver en el repo local: commit → pipeline → registry → deploy/rollback. Debe ser instalable en cualquier Linux o servidor enterprise; y que desde mi PC pueda hacer una presentación: acceder al servidor local, Jenkins, gestionar permisos, ejecutar pipelines de rutina y de deploy por ramas, ver el registry con versiones/tags y política de retención, y tener una UI mínima para acceder a Jenkins (y registry/app). Usa solo open source (Jenkins, registry:2 o GitLab Registry). Guía completa en este repo (toolkit): docs/POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md. Copiar también deploy/dashboard-demo-jenkins-registry.html y docs/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md; adaptar a pos-online."*

---

## 11. Referencias en este repo (este repo (toolkit))

- **Checklist pos-online:** [CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md](CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md)
- **Registry y build:** [../pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md](../pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md)
- **Pipeline Jenkins:** [../pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md](../pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md), Jenkinsfile.example (raíz)
- **Demo local Jenkins + Registry:** toolkit-fastflow/docs/GUIA-FASTFLOW-LOCAL-DEMO-JENKINS-DOCKER-REGISTRY.md  
- **Terraform Jenkins AWS:** manifests/terraform/jenkins-aws/README.md, APLICAR-FASTFLOW.md  

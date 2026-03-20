# Inventario: repo pos-online (usuario final) — implementación FastFlow para probar

**Repo usuario final:** `pos-online`  
**Ruta típica:** `/Users/wallfacer/proyectos-gitlab/pos-online`  
**Rama de trabajo:** `fix/package-and-deploy-scripts` (o la que se use para dejar listo para usuario final)

Este documento lista qué debe tener el repo pos-online para tener **toda la implementación FastFlow lista para probar** (local, Jenkins, registry, tests, flujo commit → deploy/rollback, presentación). Se basa en el estado actual del repo y en **[POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md](POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md)** y **[CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md](CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md)**.

---

## 1. Estado actual (según `git status` en pos-online)

### Ya modificados / en uso

| Elemento | Estado |
|----------|--------|
| `.gitignore` | Modificado |
| `.jenkins/README.md`, `.jenkins/build/prepare.groovy`, `.jenkins/deploy/cleanup.groovy`, `.jenkins/deploy/deploy.groovy` | Modificados |
| `Jenkinsfile` | Modificado |
| `README.md`, `docs/README.md` | Modificados |
| `run-jenkins-pipeline-local.sh` | Modificado |
| `docs/cicd/*` | Eliminados (correcto para usuario final) |
| `test-commit-*.txt` | Eliminados (correcto) |

### Sin trackear (pendiente de añadir al commit)

| Elemento | Acción recomendada |
|----------|---------------------|
| `deploy/` | Añadir: debe contener al menos el dashboard de demo y (opcional) scripts de verify. |
| `docs/COMO-PRESENTAR-AL-CLIENTE.md` | Añadir. |
| `docs/COMO-PROBAR.md` | Añadir. |
| `docs/COORDINACION-AGENTES.md` | Opcional: es doc interna; para usuario final puede no incluirse o dejarse sin branding. |
| `docs/INSTALAR-JENKINS.md` | Añadir. |
| `terraform/` | Añadir si se usa Terraform (Jenkins en AWS o referencia). |

---

## 2. Checklist: qué debe tener pos-online para “toda la implementación FastFlow”

### 2.1 Documentación de usuario (sin autor ni branding)

- [ ] **README.md** (raíz): qué es el sistema, inicio rápido (build, run), pipeline (Prepare → Build → Test → Lint → Package; en main, Cleanup + Deploy). Enlace a `docs/README.md`.
- [ ] **docs/README.md**: tabla con enlaces a:
  - [ ] **docs/INSTALAR-JENKINS.md**
  - [ ] **docs/COMO-PROBAR.md**
  - [ ] **docs/COMO-PRESENTAR-AL-CLIENTE.md**
  - [ ] (Opcional) **docs/FLUJO-COMMIT-A-REGISTRY.md** — commit → pipeline → registry → deploy/rollback.
- [ ] **docs/INSTALAR-JENKINS.md**: Java 11, Jenkins (war o paquete), credenciales, job Pipeline from SCM, variables de despliegue. Sin firma.
- [ ] **docs/COMO-PROBAR.md**: probar pipeline en local (`run-jenkins-pipeline-local.sh`), en Jenkins, comprobar aplicación (health, JARs). Sin firma.
- [ ] **docs/COMO-PRESENTAR-AL-CLIENTE.md**: mensajes clave, estructura presentación, demo, beneficios, nota de personalización (ellos añaden branding). Sin firma.

### 2.2 Pipeline y scripts

- [ ] **Jenkinsfile** en raíz: stages Prepare → Build → Test → Lint → Package; en main, Cleanup + Deploy. Opcional: stage de build de imagen Docker y push a registry (tags `BUILD_NUMBER`, `latest`).
- [ ] **.jenkins/** con scripts por fase: prepare, build, test, package, cleanup, deploy (según rama).
- [ ] **run-jenkins-pipeline-local.sh**: ejecutar el flujo en local (sin Jenkins) para probar antes de subir.

### 2.3 Registry y Docker (para probar FastFlow completo)

- [ ] **Documentar** en `docs/` cómo usar un registry (Docker Hub, GitLab Container Registry o registry local `registry:2`).
- [ ] **Jenkinsfile** o script que, tras tests: build imagen Docker → tag → push a registry (con `IMAGE_TAG=BUILD_NUMBER` o `GIT_COMMIT`).
- [ ] **Política de retención**: documentar en README o `docs/REGISTRY-RETENTION.md` (ej. últimas N imágenes o X días).

### 2.4 Tests

- [ ] Tests de aplicación (Maven/JUnit o equivalente) que el pipeline ejecute; fallo = pipeline falla.
- [ ] (Opcional) Scripts de verificación: imagen existe tras build; tag existe en registry tras push; test de humo tras deploy; script de rollback (desplegar tag anterior y verificar).

### 2.5 Deploy y presentación

- [ ] **deploy/** con al menos:
  - [ ] **deploy/dashboard.html** (o `docs/demo/dashboard.html`): UI mínima con enlaces a Jenkins, registry y aplicación (copiar desde este repo (toolkit) **deploy/dashboard-demo-jenkins-registry.html** y ajustar URLs/puertos).
- [ ] **terraform/** (opcional): si se usa Terraform para Jenkins en AWS, con README o APLICAR-TERRAFORM.md genérico.

### 2.6 Flujo commit → usuario final (para usuario final / presentación)

- [ ] **docs/FLUJO-COMMIT-A-REGISTRY.md**: flujo en 5 pasos (commit → pipeline → registry → deploy → rollback), dónde ver cada paso, comandos útiles. Puede copiarse la plantilla desde este repo (toolkit) **docs/pipeline-y-registry/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md** y adaptar a pos-online.

---

## 3. Archivos a copiar desde este repo (toolkit) a pos-online

| Origen (este repo (toolkit)) | Destino (pos-online) |
|-----------------------|------------------------|
| `deploy/dashboard-demo-jenkins-registry.html` | `deploy/dashboard.html` (o `docs/demo/dashboard.html`) |
| Contenido de `docs/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md` | Adaptar y guardar como `docs/FLUJO-COMMIT-A-REGISTRY.md` |

Ajustar en el dashboard las URLs (puertos de Jenkins, registry y aplicación según pos-online). Para Jenkinsfile con Docker + registry, usar como referencia **Jenkinsfile.example** y **docs/pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md** en este repo (toolkit); adaptar a Maven/Java de pos-online.

---

## 4. Comandos sugeridos en pos-online (después de aplicar cambios)

```bash
cd /Users/wallfacer/proyectos-gitlab/pos-online

# Añadir todo lo nuevo y los borrados
git add .gitignore .jenkins/ Jenkinsfile README.md docs/README.md run-jenkins-pipeline-local.sh
git add deploy/ docs/COMO-PRESENTAR-AL-CLIENTE.md docs/COMO-PROBAR.md docs/INSTALAR-JENKINS.md
git add docs/FLUJO-COMMIT-A-REGISTRY.md   # si se crea
git add terraform/                        # si aplica
git status

# Eliminaciones (docs/cicd, test-commit-*)
git add -u

# Commit (mensaje según convención del repo)
git commit -m "docs: usuario final - INSTALAR-JENKINS, COMO-PROBAR, COMO-PRESENTAR, deploy dashboard, flujo registry; remove docs/cicd y test-commit"
```

Si **docs/COORDINACION-AGENTES.md** es solo para agentes internos, no hace falta añadirla al commit de usuario final; puede dejarse en .gitignore o en una rama aparte.

---

## 5. Verificación rápida “listo para probar”

En pos-online, comprobar:

1. **Build y tests:** `./run-jenkins-pipeline-local.sh` (o equivalente) pasa.
2. **Docs:** Existen y enlazan bien: `docs/README.md` → INSTALAR-JENKINS, COMO-PROBAR, COMO-PRESENTAR (y opcional FLUJO-COMMIT-A-REGISTRY).
3. **Dashboard:** Abrir `deploy/dashboard.html` en el navegador y que los enlaces (Jenkins, registry, app) coincidan con tu entorno local.
4. **Sin restos:** No queden referencias rotas a `docs/cicd/` ni archivos `test-commit-*.txt`.

---

## 6. Referencias en este repo (toolkit)

- **Guía completa implementación:** docs/pos-online/POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md  
- **Coordinación y checklist:** [CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md](CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md), [docs/README.md](../README.md)
- **Plantilla flujo commit → registry:** [../pipeline-y-registry/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md](../pipeline-y-registry/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md)
- **Dashboard demo:** deploy/dashboard-demo-jenkins-registry.html  

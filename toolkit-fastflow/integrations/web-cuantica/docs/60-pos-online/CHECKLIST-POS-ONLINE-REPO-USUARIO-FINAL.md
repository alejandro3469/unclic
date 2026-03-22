# Checklist: repo pos-online como usuario final con implementación FastFlow

**Repo:** `proyectos-gitlab/pos-online`  
**Ruta típica:** `/ruta/ejemplo/pos-online`  
**Rama actual en el ejemplo:** `fix/package-and-deploy-scripts`

Este documento mapea el **estado de tu repo** con lo que debe tener pos-online para probar toda la implementación FastFlow (Jenkins, Registry, tests, demo local, flujo commit/usuario final). Usa **este toolkit** (o la carpeta donde esté este repo) como origen para copiar archivos al repo pos-online.

---

## 1. Estado actual (según tu `git status`)

### Ya alineado con la limpieza

| Estado | Qué |
|--------|-----|
| **Modified** | `.gitignore`, `.jenkins/README.md`, `.jenkins/build/prepare.groovy`, `.jenkins/deploy/cleanup.groovy`, `.jenkins/deploy/deploy.groovy`, `Jenkinsfile`, `README.md`, `docs/README.md`, `run-jenkins-pipeline-local.sh` |
| **Deleted** | `docs/cicd/*` (eliminado), `test-commit-1.txt` … `test-commit-5.txt` (eliminados) |
| **Untracked** | `deploy/`, `docs/COMO-PRESENTAR-AL-CLIENTE.md`, `docs/COMO-PROBAR.md`, `docs/COORDINACION-AGENTES.md`, `docs/INSTALAR-JENKINS.md`, `terraform/` |

Es decir: ya tienes Jenkins (Jenkinsfile, `.jenkins/`), docs de usuario (INSTALAR-JENKINS, COMO-PROBAR, COMO-PRESENTAR), deploy y terraform sin seguir, y limpieza de cicd y test-commit.

### Falta añadir para “toda la implementación FastFlow” para probar

1. **Dashboard UI mínima** (acceso a Jenkins + Registry desde una sola página).
2. **Flujo commit y usuario final** documentado (para presentación).
3. **Script de test de registry** (opcional pero recomendado).
4. **Índice en `docs/README.md`** con tabla a INSTALAR-JENKINS, COMO-PROBAR, COMO-PRESENTAR (y opcional FLUJO-COMMIT).
5. **Registro en Git** de todo lo anterior (add + commit).

---

## 2. Qué copiar desde este repo (toolkit) al repo pos-online

Origen: **este repo** (toolkit de implementación FastFlow para pos-online). Ruta: donde tengas clonado este repo.

| Copiar desde (este repo) | A (repo pos-online) | Nota |
|--------------------------|---------------------|------|
| `deploy/dashboard-demo-jenkins-registry.html` | `deploy/dashboard.html` | UI mínima: enlaces a Jenkins (8080) y registry (5000). Ajustar URLs si usas otros puertos. |
| `docs/60-pos-online/FLUJO-COMMIT-Y-USUARIO-FINAL.md` | `docs/FLUJO-COMMIT-Y-USUARIO-FINAL.md` | Flujo de commit y usuario final (presentación). Adaptar nombres a pos-online si aplica. |
| `scripts/test-registry.sh` | `scripts/test-registry.sh` | Test de pull tras push. Dar permisos de ejecución; cambiar `IMAGE_NAME` si aplica. |

Comandos de ejemplo (ajusta `TOOLKIT` y `POS_ONLINE` a tus rutas):

```bash
TOOLKIT="<ruta-donde-esté-este-repo-toolkit>"
POS_ONLINE="/ruta/ejemplo/pos-online"

cp "$TOOLKIT/deploy/dashboard-demo-jenkins-registry.html" "$POS_ONLINE/deploy/dashboard.html"
cp "$TOOLKIT/docs/60-pos-online/FLUJO-COMMIT-Y-USUARIO-FINAL.md" "$POS_ONLINE/docs/FLUJO-COMMIT-Y-USUARIO-FINAL.md"
mkdir -p "$POS_ONLINE/scripts"
cp "$TOOLKIT/scripts/test-registry.sh" "$POS_ONLINE/scripts/test-registry.sh"
chmod +x "$POS_ONLINE/scripts/test-registry.sh"
```

---

## 3. Comprobar en pos-online

### 3.1 `docs/README.md`

Debe tener una tabla (o lista) de enlaces a:

- `docs/INSTALAR-JENKINS.md`
- `docs/COMO-PROBAR.md`
- `docs/COMO-PRESENTAR-AL-CLIENTE.md`
- (Opcional) `docs/FLUJO-COMMIT-Y-USUARIO-FINAL.md`

Si no está, añade algo como:

```markdown
## Documentación

| Documento | Descripción |
|-----------|--------------|
| [Instalar Jenkins](../30-instalacion/INSTALAR-JENKINS.md) | Requisitos, instalación, credenciales, job Pipeline. |
| *COMO-PROBAR.md* (en el repo pos-online; no está en el toolkit) | Pipeline en local, en Jenkins, comprobar la aplicación. Ver [FLUJO-COMMIT-Y-USUARIO-FINAL.md](FLUJO-COMMIT-Y-USUARIO-FINAL.md). |
| *COMO-PRESENTAR-AL-CLIENTE.md* (en el repo pos-online; no está en el toolkit) | Mensajes clave, demo, beneficios. Ver [FLUJO-COMMIT-Y-USUARIO-FINAL.md](FLUJO-COMMIT-Y-USUARIO-FINAL.md). |
| [Flujo commit y usuario final](FLUJO-COMMIT-Y-USUARIO-FINAL.md) | Commit → Jenkins → registry; flujo usuario final (para presentación). |
```

### 3.2 Jenkinsfile y registry

- Si tu Jenkinsfile ya hace **build + test + package** y en `main` hace **deploy**, el “FastFlow” a añadir es:
  - (Opcional) paso de **build de imagen Docker** y **push a registry** (Docker Hub, GitLab Container Registry o registry local).
  - Tags: por ejemplo `latest` y `main-<BUILD_NUMBER>` o por rama.
- Documentar en `docs/COMO-PROBAR.md` las fases que correspondan (build, test, package, deploy, y si añades: build imagen, push, test-registry).

### 3.3 Dashboard

- En pos-online, servir `deploy/` y abrir el dashboard:
  - `cd deploy && python3 -m http.server 9000`
  - Navegador: `http://localhost:9000/dashboard.html` (o el nombre que hayas usado).
- Comprobar que los enlaces a Jenkins (ej. 8080) y al registry (ej. 5000 o GitLab) son los correctos para tu entorno.

---

## 4. Sugerencia de add y commit (pos-online)

Ejecutar desde **pos-online** (`/ruta/ejemplo/pos-online`):

```bash
cd /ruta/ejemplo/pos-online

# Añadir docs de usuario y limpieza
git add docs/README.md
git add docs/INSTALAR-JENKINS.md docs/COMO-PROBAR.md docs/COMO-PRESENTAR-AL-CLIENTE.md
git add docs/FLUJO-COMMIT-Y-USUARIO-FINAL.md   # después de copiarlo

# Dashboard y scripts
git add deploy/
git add scripts/test-registry.sh                # después de copiarlo

# Terraform
git add terraform/

# Jenkins y pipeline
git add .gitignore .jenkins/ Jenkinsfile run-jenkins-pipeline-local.sh README.md

# Registrar eliminaciones (cicd, test-commit-*)
git add -u docs/cicd/
git add -u test-commit-*.txt
# o, si ya no existen en disco: git add -u (actualiza todo lo eliminado)

# Estado
git status

# Un solo commit (o partir en dos: limpieza + docs/dashboard)
git commit -m "docs: usuario final - INSTALAR-JENKINS, COMO-PROBAR, COMO-PRESENTAR, dashboard Jenkins/Registry, flujo commit y test registry"
```

Si prefieres **no** incluir `docs/COORDINACION-AGENTES.md` en lo que ve el usuario final, no lo añadas al commit (o muévelo a un doc interno). La checklist de coordinación pide solo docs sin autor ni branding para el usuario final.

---

## 5. Rama recomendada

- **Seguir en `fix/package-and-deploy-scripts`** hasta dejar listo todo lo anterior y probar localmente (run-jenkins-pipeline-local.sh, Jenkins, dashboard, test-registry si aplica).
- Luego **merge a `main`** (o a una rama tipo `release/lista-para-cliente`) cuando quieras entregar el repo al usuario final.
- Si en tu flujo se usa **main** como rama de integración, hacer merge desde `fix/package-and-deploy-scripts` cuando el checklist de la sección 4 de **COORDINACION-AGENTES-POS-ONLINE-Y-FASTFLOW.md** esté cubierto.

---

## 6. Resumen “toda la implementación FastFlow para probar”

En pos-online deberías tener:

| Elemento | Dónde |
|----------|--------|
| Jenkins + pipeline por rama | `Jenkinsfile`, `.jenkins/`, `run-jenkins-pipeline-local.sh` |
| Docs usuario final | `docs/INSTALAR-JENKINS.md`, `COMO-PROBAR.md`, `COMO-PRESENTAR-AL-CLIENTE.md` |
| Índice docs | `docs/README.md` con enlaces a los tres anteriores y a FLUJO-COMMIT |
| Flujo commit y usuario final | `docs/FLUJO-COMMIT-Y-USUARIO-FINAL.md` (copiado de este toolkit) |
| UI mínima Jenkins + Registry | `deploy/dashboard.html` (copiado de este toolkit) |
| Test de registry | `scripts/test-registry.sh` (copiado y adaptado) |
| Infra opcional | `terraform/`, `deploy/` |
| Sin restos de desarrollo | Sin `docs/cicd/` ni `test-commit-*.txt` |

Con esto puedes **probar en local** (Jenkins, registry, tags, flujo commit, presentación) y usar el repo como **usuario final** con toda la implementación FastFlow lista para probar.

---

**Referencias en este repo (toolkit)**

- Guía completa: **docs/60-pos-online/POS-ONLINE-IMPLEMENTACION-JENKINS-REGISTRY-DEMO.md**
- Índice: **docs/README.md**; en pos-online usar este checklist y **docs/60-pos-online/POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md**.

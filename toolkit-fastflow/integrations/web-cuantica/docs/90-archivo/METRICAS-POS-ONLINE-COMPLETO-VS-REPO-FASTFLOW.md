# Métricas: POS Online completo (GitLab) vs repo-pos-fastflow

Documento para tener **métricas iniciales** de la versión completa del POS (monolito central) y del repo de automatización (FastFlow). Al final del proceso de integración se pueden comparar con **métricas finales**.

---

## 1. Versión completa (fuente de verdad)

**Ruta:** `/ruta/ejemplo/pos-online`  
**Origen:** GitLab (pos-online en producción). Repo con el monolito que ya funciona en producción y que hay que automatizar.

### 1.1 Métricas iniciales (referencia)

| Métrica | Valor |
|--------|--------|
| **Archivos totales** (excl. `.git`, `target/`) | **2 531** |
| **Archivos .java** | **2 184** |
| **Líneas de código Java** (aprox.) | **~152 400** |
| **Directorios** (excl. .git, target) | **1 312** |
| **Commits totales** | **1 706** |
| **Rama de referencia** | `fix/package-and-deploy-scripts` (alineada con main) |

### 1.2 Estructura de código (paquete principal)

- **GroupId/paquete base:** `mx.com.endtoend`
- **Módulos principales bajo `src/main/java/mx/com/endtoend/`:**
  - `application/` — admin, advertising, articles, branch, cash, catalogues, clients, closings, companies, creditNote, emails, openings, orderConfigurations, orders, payments, recharges, reports, roles, strategy, userConfigurations, users, validService, warehouses
  - `config/`
  - `domain/` — accountingRecord, advertising, articles, branch, cash, creditNote, etc. (dominio rico con business, dto, ports, services)
  - `genericCommonsFileds/`
  - `genericConfigurations/`
  - `infrastructure/` — múltiples adaptadores (JDE, Oracle, servicios externos)

### 1.3 Dependencia crítica

- **generic-model:** `mx.com.endtoend.smart.bussiness.model:smartbussiness-generic-model:1.0.1-SNAPSHOT`  
  El POS depende de este artefacto (mismo repo padre o publicado en Maven). Para build completo hace falta tener **generic-model** disponible (local o repositorio).

### 1.4 Build y tests (referencia)

- **Build:** `mvn clean install` (Spring Boot 2.6.3, Java 11).
- **Tests:** 54 tests (Surefire), JaCoCo; en tu último run: BUILD SUCCESS, 54 tests, 0 failures (con warnings de cobertura).

---

## 2. Repo FastFlow actual (stub / demo)

**Ruta:** `toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow`  
**Uso previsto:** Repo para probar cómo levantar una instancia del POS con todo lo necesario (Jenkins, Gitea, deploy), lo más cercano a la realidad posible. Hoy es una **app simulada**, no el monolito completo.

### 2.1 Métricas actuales (tras integrar versión completa)

| Métrica | Valor |
|--------|--------|
| **Archivos totales** (excl. .git, target) | **~2 500+** (alineado con versión completa) |
| **Archivos .java** | **2 184** |
| **Líneas de código Java** (aprox.) | **~152 400** |
| **Paquete** | `mx.com.endtoend` (POS completo) |

### 2.2 Contenido actual

- **Código:** Versión completa del POS bajo `src/main/java/mx/com/endtoend/` (application, domain, infrastructure, config, genericCommonsFileds, genericConfigurations).
- **Pipeline/deploy:** `Jenkinsfile`, `Dockerfile`, `deploy/terraform/`, `deploy/k8s/` — listos para automatización.
- **Build:** Maven, Spring Boot 2.6.3, Java 11; **dependencia obligatoria:** generic-model (smartbussiness-generic-model).

---

## 3. Comparación (tras integración)

| Concepto | Versión completa (GitLab) | Repo-pos-fastflow (actual) |
|---------|----------------------------|----------------------------|
| Archivos | 2 531 | ~2 500+ |
| Archivos Java | 2 184 | 2 184 |
| Líneas Java | ~152 400 | ~152 400 |
| Paquete | mx.com.endtoend | mx.com.endtoend |
| generic-model | Sí (obligatorio) | Sí (obligatorio) |
| Jenkinsfile / deploy | Sí (en GitLab) | Sí (en repo-pos-fastflow) |

**Estado:** El repo `repo-pos-fastflow` ya contiene la versión completa del POS (código bajo `src/main/java/mx/com/endtoend/`). El build requiere tener resuelta la dependencia **generic-model** (repositorio Maven local o remoto).

---

## 4. Qué elegir / qué hacer antes

Tienes dos repos en juego:

1. **Clon local del POS “completo”** (ruta en tu máquina) — versión **completa** de cliente; puede estar en Gitea como `TU_USUARIO/pos-online` tras `git push`.
2. **`repo-pos-fastflow`** (dentro de web-cuantica) — **stub** con Jenkinsfile, Terraform, K8s y app simulada; no tiene el POS completo.

### Opción A: Usar el repo que ya está en Gitea (pos-online completo)

- Ese repo **ya tiene** la versión completa si subiste la rama `fix/package-and-deploy-scripts` desde `proyectos-gitlab/pos-online`.
- **Jenkins** puede apuntar a `http://gitea.<TU_DOMINIO>:3000/TU_USUARIO/pos-online.git` y ejecutar el Jenkinsfile de ese repo.
- **Ventaja:** No duplicar código; una sola fuente de verdad (GitLab → Gitea ya subido).
- **Cuidado:** Resolver generic-model en Jenkins (repositorio Maven o copia en workspace).

### Opción B: Que `repo-pos-fastflow` tenga el POS completo

- **Objetivo:** Que `repo-pos-fastflow` sea el repo “todo en uno” para automatización (código completo + Jenkinsfile + deploy).
- **Pasos:**
  1. Copiar el **código fuente completo** de `proyectos-gitlab/pos-online` a `repo-pos-fastflow` (sustituir `src/`, `pom.xml` por los del POS completo; mantener y adaptar `Jenkinsfile`, `Dockerfile`, `deploy/`).
  2. En `repo-pos-fastflow` hacer de `repo-pos-fastflow` un **repositorio Git independiente** (`git init`, añadir todo, commit) y subirlo a Gitea como un repo nuevo (p. ej. `pos-online-fastflow`) o reemplazar el contenido del repo existente.
  3. Configurar en Jenkins la URL de ese repo en Gitea y resolver **generic-model** (ruta local en el job o repositorio Maven).

### Recomendación

- **Para “levantar una instancia del POS con todo lo que necesita” y probar en AWS/Gitea/Jenkins lo más cercano a la realidad:**  
  Usar **Opción A** (repo en Gitea que ya tiene el POS completo desde GitLab) y que Jenkins clone ese repo. Así no tienes que mantener dos copias del código.
- **Para tener un repo “demo/automation” separado** que en el futuro tenga el código completo integrado:  
  Usar **Opción B** y documentar las **métricas finales** de `repo-pos-fastflow` cuando el código completo esté integrado (archivos, líneas, carpetas) para comparar con esta métrica inicial.

---

## 5. Métricas finales (plantilla)

Cuando el repo de automatización tenga el POS completo, rellenar para comparar:

| Métrica | Inicial (stub) | Final (completo) |
|---------|-----------------|------------------|
| Archivos totales (excl. .git, target) | 18 | _____ |
| Archivos .java | 3 | _____ |
| Líneas Java (aprox.) | ~72 | _____ |
| Directorios (excl. .git, target) | — | _____ |

Objetivo: que las métricas finales se acerquen a las de la **versión completa** (sección 1).

---

## 6. Resumen una frase

**Versión completa (GitLab):** 2 531 archivos, 2 184 Java, ~152 400 líneas, paquete `mx.com.endtoend`, dependencia generic-model, 1 706 commits.  
**repo-pos-fastflow (stub):** 18 archivos, 3 Java, ~72 líneas, app simulada.  
**Qué hacer:** Usar el repo que ya subiste a Gitea (pos-online completo) para Jenkins, o copiar el código completo de GitLab a repo-pos-fastflow y subir este como repo de automatización; documentar métricas finales cuando esté integrado.

Referencias: [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](../20-operaciones/CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md), [DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](../20-operaciones/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md) (si existe en el repo).

# Flujo: commit → pipeline → registry → deploy/rollback (plantilla para pos-online)

**Uso:** Copiar este contenido a **pos-online** como `docs/FLUJO-COMMIT-A-REGISTRY.md` (o equivalente) y ajustar nombres de ramas, URLs y comandos al proyecto. Sin autor ni branding para usuario final.

---

## 1. Flujo resumido

1. **Commit** — Desarrollador hace push a la rama (ej. `main` o `develop`).
2. **Pipeline** — Jenkins ejecuta: Checkout → Build/Test → Build imagen Docker → Push a registry (tags: `BUILD_NUMBER`, `latest`).
3. **Registry** — La imagen queda disponible con sus tags; se puede listar en la UI o con la API del registry.
4. **Deploy** — (Opcional) Job o script despliega la imagen (ej. `latest` o tag fijo) en el entorno.
5. **Rollback** — Elegir un tag anterior en el registry y redesplegar; verificar con test de humo.

---

## 2. Cómo ver cada paso en local

| Paso | Dónde verlo |
|------|-------------|
| Commit | Git: `git log -1`; Jenkins: Build → Console Output (variable `GIT_COMMIT`). |
| Pipeline | Jenkins: job → Build #N → Console Output (por fase). |
| Imagen y tags | Registry: UI (Docker Hub, GitLab) o `GET /v2/<repo>/tags/list` (registry local). |
| Deploy | URL de la aplicación; Jenkins Console si el deploy lo hace el pipeline. |
| Rollback | Mismo flujo que deploy usando un tag anterior. |

---

## 3. Comandos útiles (ajustar a pos-online)

- **Probar pipeline sin Jenkins:** ejecutar el script local (ej. `run-jenkins-pipeline-local.sh` o el equivalente que construya, pruebe y haga push).
- **Listar tags en registry local:** `curl -s http://localhost:5000/v2/_catalog` y `curl -s http://localhost:5000/v2/<nombre-imagen>/tags/list`.
- **Ver versión desplegada:** llamar al endpoint de health o versión de la app (ej. `curl http://localhost:3000/api/health`).

---

## 4. Política de retención (ejemplo)

Documentar en el repo: cuántas imágenes o tags se conservan y durante cuánto tiempo (ej. últimas 30 imágenes, o tags con más de 90 días se pueden eliminar). Así se preservan versiones para rollback sin llenar el registry. Implementación según el registry (Docker Hub, GitLab cleanup policy, o script propio).

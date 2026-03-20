# Auditoría — repo-pos-fastflow para Jenkins y Registry

Verificación de que el repo está completo y listo para Jenkins + registry (FastFlow).

---

## 1. POS completo

| Elemento | Estado | Nota |
|----------|--------|------|
| Código fuente `mx.com.endtoend` | OK | 2 184 archivos Java bajo `src/main/java/mx/com/endtoend/` |
| application, domain, infrastructure | OK | Estructura del monolito POS |
| Resources (properties, orders, reports, tickets) | OK | `src/main/resources/` |
| Tests | OK | `src/test/java/mx/` |
| pom.xml (Spring Boot 2.6.3, Java 11) | OK | Incluye generic-model, JPA, seguridad, actuator |
| checkstyle.xml | OK | Raíz del repo |

**Dependencia obligatoria:** `smartbussiness-generic-model:1.0.1-SNAPSHOT`. Debe estar instalada en la EC2 (usuario jenkins) o el stage **Build** falla. Ver [EC2-INSTALAR-GENERIC-MODEL.md](../docs/EC2-INSTALAR-GENERIC-MODEL.md).

---

## 2. Configuración Jenkins

| Elemento | Estado | Nota |
|----------|--------|------|
| Jenkinsfile en raíz | OK | Prepare → Build → Test → Lint → Package → Build image → Push to registry → Cleanup → Deploy → Verify |
| Branch en job | — | Debe ser `*/main` (y la rama `main` debe existir en Gitea tras `git push`) |
| Credenciales Gitea en Jenkins | — | Usuario + token; ID ej. `gitea-pos-online` |
| Repository URL en job | — | `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git` |
| Script Path | OK | `Jenkinsfile` |
| Verify instance | OK | Curl a `/actuator/health` o `/health` (compatible con POS completo) |

**Para que funcione a la primera:** además de lo anterior, en la EC2 deben estar: **Git**, **Java 11** (o 17), **Maven**, y **generic-model** instalado en el repo Maven del usuario `jenkins`. Si usas Docker para “Build image” / “Push to registry”, **Docker** debe estar instalado y el usuario `jenkins` debe poder usarlo (`docker build`, `docker push`).

---

## 3. Configuración Registry (FastFlow)

| Elemento | Estado | Nota |
|----------|--------|------|
| Stage "Push to registry" en Jenkinsfile | OK | Se ejecuta cuando `REGISTRY` está definido |
| Variable REGISTRY en job | — | Definir en el job (Jenkins → job → Configure → Pipeline → Environment) o globalmente |
| Registry corriendo | — | Hay que levantar un registry (p. ej. `registry:2`) en la EC2 o en otro servidor |

**Levantar registry gratis (misma EC2):**

1. En la EC2 donde corre Jenkins (con Docker instalado):
   ```bash
   sudo docker run -d -p 5000:5000 --restart=always --name registry registry:2
   ```
2. Si la EC2 tiene firewall o Security Group, abrir **puerto 5000** (TCP).
3. En Jenkins → job **pos-online-pipeline** → **Configure** → **Pipeline** → añadir variable de entorno:
   - **Name:** `REGISTRY`
   - **Value:** `localhost:5000` (si el registry está en la misma EC2) o `IP_EC2:5000`.
4. Para que `docker push` a un registry HTTP (no HTTPS) funcione desde el agente, en la EC2 puede hacer falta marcar el registry como inseguro (si usas localhost:5000). En `/etc/docker/daemon.json` (o equivalente):
   ```json
   { "insecure-registries": ["localhost:5000", "IP_EC2:5000"] }
   ```
   Luego `sudo systemctl restart docker`.

**Comprobar registry:** `curl http://localhost:5000/v2/_catalog` (o desde fuera `http://<IP_EC2>:5000/v2/_catalog`).

---

## 4. Resumen: orden para que funcione a la primera

1. **Repo en Gitea con rama `main`** — subir código (push desde GitLab o desde repo-pos-fastflow) para que exista `refs/heads/main`.
2. **EC2:** Git, Java, Maven, Docker instalados; **generic-model** instalado para usuario `jenkins`; opcional: registry `registry:2` en 5000 e `insecure-registries` si aplica.
3. **Jenkins:** Credenciales Gitea; job con URL del repo, branch `*/main`, Script Path `Jenkinsfile`; opcional: variable `REGISTRY` para push.
4. **Build Now** — checkout → Prepare → Build (requiere generic-model) → Test → Lint → Package → Build image (requiere Docker) → Push to registry (si REGISTRY definido) → Deploy (en main) → Verify.

Si algo falla, el **Console Output** del build indica el stage y el error (p. ej. generic-model not found, docker: command not found, connection refused al registry).

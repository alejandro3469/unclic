# Clonar pos-online local y conectar a Gitea (para Jenkins / FastFlow)

Pasos para tener el repo **pos-online** en local y, cuando tengas el repo creado en **Gitea**, conectarlo y usarlo en Jenkins (Terraform, K8s, FastFlow).

**Qué es Gitea:** Servicio Git auto-hospedado (open source, MIT): hosting de repos, code review, issues, pull requests, package registry y CI/CD. Puedes usar **Gitea self-hosted** (instalación propia) o **Gitea Cloud** (https://cloud.gitea.com). Es 100% compatible con Git; Jenkins puede clonar desde Gitea igual que desde GitHub o GitLab. Ver: [gitea.com](https://gitea.com), [Documentation](https://docs.gitea.com).

---

## 1. Clonar pos-online en local

Necesitas la **URL del repo pos-online** (GitLab, GitHub o donde esté ahora).

### Opción A: Desde este toolkit (script)

En la terminal, desde la raíz de **web-cuantica** (o desde `toolkit-fastflow/integrations/web-cuantica`):

```bash
# Sustituye por tu URL real (GitLab, GitHub, etc.)
export POS_ONLINE_REPO_URL="https://gitlab.com/tu-org/pos-online.git"

bash scripts/clone-pos-online.sh
```

El script clona en **toolkit-fastflow/pos-online** por defecto (carpeta hermana de `integrations`). Si quieres otra ruta:

```bash
export POS_ONLINE_REPO_URL="https://gitlab.com/tu-org/pos-online.git"
export CLONE_DIR="$HOME/proyectos-gitlab/pos-online"
bash scripts/clone-pos-online.sh
```

### Opción B: Comando directo

Si prefieres no usar el script:

```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow
git clone https://gitlab.com/tu-org/pos-online.git pos-online
cd pos-online
```

(Sustituye la URL por la de tu repo pos-online.)

---

## 2. Cuando tengas el repo en Gitea: conectar y subir

Cuando hayas creado el repositorio **pos-online** en Gitea Cloud (o en tu Gitea self-hosted):

1. Entra en la carpeta clonada:

   ```bash
   cd toolkit-fastflow/pos-online
   # o: cd $HOME/proyectos-gitlab/pos-online
   ```

2. Añade Gitea como remote (sustituye `TU_USUARIO` y la URL si es distinta):

   ```bash
   git remote add gitea https://cloud.gitea.com/TU_USUARIO/pos-online.git
   ```

   Si Gitea está en otro servidor:

   ```bash
   git remote add gitea https://tu-gitea.com/tu-usuario/pos-online.git
   ```

3. Sube la rama que uses (main o master):

   ```bash
   git push -u gitea main
   ```

   Si tu rama principal es `master`:

   ```bash
   git push -u gitea master
   ```

4. En **Jenkins** → job **pos-online-pipeline** → **Configure** → **Repository URL** pon la misma URL de Gitea que usaste en `git remote add gitea` (ej. `https://cloud.gitea.com/TU_USUARIO/pos-online.git`). Si el repo es privado, en **Credentials** añade usuario y token de Gitea. Guarda.

A partir de ahí, cada `git push gitea main` (o el trigger Poll SCM) disparará el pipeline en Jenkins.

---

## 3. Resumen rápido

| Paso | Comando / acción |
|------|------------------|
| Clonar local | `POS_ONLINE_REPO_URL="https://...pos-online.git" bash scripts/clone-pos-online.sh` |
| Ir al repo | `cd toolkit-fastflow/pos-online` (o tu CLONE_DIR) |
| Añadir Gitea | `git remote add gitea https://cloud.gitea.com/TU_USUARIO/pos-online.git` |
| Subir a Gitea | `git push -u gitea main` |
| Jenkins | Configure → Repository URL = URL de Gitea; Credentials si es privado |

---

## Referencias

- Crear y configurar el job en Jenkins: [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md).
- Paso a paso mínimo (EC2, Jenkins, 8111, job, trigger): [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md).

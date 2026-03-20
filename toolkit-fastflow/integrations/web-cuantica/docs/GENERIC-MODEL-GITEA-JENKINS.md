# generic-model en Gitea y Jenkins (automático para pos-online)

El repo **smartbussiness-generic-model** está clonado en la carpeta padre de **repo-pos-fastflow** y se sube a Gitea para que un job de Jenkins lo construya e instale en `~/.m2` del usuario jenkins. Así **pos-online-pipeline** siempre tiene el artefacto disponible sin instalar generic-model a mano en la EC2.

---

## 1. Dónde está el clone local

```
toolkit-fastflow/integrations/web-cuantica/
  repo-pos-fastflow/              ← pos-online (FastFlow)
  smartbussiness-generic-model/   ← clone del original (proyectos-gitlab)
```

Origen del clone: `/Users/wallfacer/proyectos-gitlab/smartbussiness-generic-model`.

---

## 2. Crear repo en Gitea y subir

**Opción A — Con token (crea el repo por API):**

```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica

export GITEA_USER=alejandro-perez
export GITEA_REPO=smartbussiness-generic-model
export GITEA_URL=http://gitea.unclic.consulting:3000
export GITEA_TOKEN=tu_token_de_gitea

bash scripts/push-generic-model-to-gitea.sh
```

El script crea el repo en Gitea (si no existe) y hace push de `smartbussiness-generic-model` a la rama `main`.

**Opción B — Sin token (crear repo a mano):**

1. En Gitea: **+** → **New Repository** → Name: `smartbussiness-generic-model`, sin “Initialize repository”.
2. Luego:

```bash
cd toolkit-fastflow/integrations/web-cuantica
export GITEA_USER=alejandro-perez
export GITEA_REPO=smartbussiness-generic-model
bash scripts/push-generic-model-to-gitea.sh
```

**URL del repo en Gitea:** `http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model`

---

## 3. Job Jenkins “generic-model” en la EC2

En **http://jenkins.unclic.consulting:8080** (o 3.15.4.160:8080):

1. **New Item** → nombre: `generic-model` (o `smartbussiness-generic-model`) → **Pipeline** → OK.
2. **Configure:**
   - **Pipeline** → Definition: **Pipeline script from SCM**
   - **SCM:** Git
   - **Repository URL:** `http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model.git`
   - **Credentials:** la misma de Gitea que usa pos-online-pipeline
   - **Branch:** `*/main`
   - **Script Path:** `Jenkinsfile`
3. **Save**.

Al hacer **Build Now**, el job clona el repo, ejecuta `mvn clean install -DskipTests` y deja el artefacto en `~/.m2/repository` del usuario **jenkins** en la EC2 (Built-In Node = Linux).

---

## 4. Encadenar pos-online-pipeline (artefacto listo en automático)

Para que **pos-online-pipeline** siempre tenga generic-model instalado antes de compilar:

1. **pos-online-pipeline** → **Configure** → **General**.
2. Activa **Build after other projects are built**.
3. En **Projects to build** pon: `generic-model`.
4. (Opcional) **Trigger only if build is stable**.
5. **Save**.

Cada vez que **generic-model** termine bien, se lanzará **pos-online-pipeline**. También puedes seguir lanzando pos-online-pipeline a mano; mientras generic-model se haya ejecutado al menos una vez en esa EC2, el artefacto estará en `~/.m2` y pos-online compilará.

**Alternativa (solo primera vez):** Si prefieres no encadenar, ejecuta **Build Now** en **generic-model** una vez; después pos-online-pipeline puede correr solo cuando quieras.

---

## 5. Resumen

| Paso | Dónde | Qué hacer |
|------|--------|-----------|
| Clone local | web-cuantica/smartbussiness-generic-model | Ya está (copiado del original). |
| Repo en Gitea | scripts/push-generic-model-to-gitea.sh | Ejecutar con GITEA_USER, GITEA_REPO; opcional GITEA_TOKEN para crear repo por API. |
| Job Jenkins | jenkins.unclic.consulting:8080 | Crear job **generic-model** (Pipeline from SCM, repo smartbussiness-generic-model, Script Path Jenkinsfile). |
| Artefacto listo | EC2 (usuario jenkins) | Build Now en generic-model → artefacto en ~/.m2. |
| pos-online | Job pos-online-pipeline | Opcional: “Build after” generic-model; o ejecutar generic-model una vez y luego pos-online cuando quieras. |

Ver también: [EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md) (instalación manual una sola vez si no usas el job).

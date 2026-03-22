# generic-model en Gitea y Jenkins (automático para pos-online)

**smartbussiness-generic-model** (junto a `repo-pos-fastflow/` en `web-cuantica/`) es dependencia Maven del POS. Job Jenkins `mvn install` → artefacto en `~/.m2` de **jenkins**; sin eso el POS falla en *Build*/*Test*.

---

## 1. Dónde está el clone local

```
toolkit-fastflow/integrations/web-cuantica/
  repo-pos-fastflow/              ← pos-online (FastFlow)
  smartbussiness-generic-model/   ← clone del original (proyectos-gitlab)
```

Origen del clone: `/ruta/ejemplo/smartbussiness-generic-model`.

---

## 2. Crear repo en Gitea y subir

**Opción A — Con token (crea el repo por API):**

```bash
cd <RUTA_MONOREPO>/toolkit-fastflow/integrations/web-cuantica

export GITEA_USER=TU_USUARIO
export GITEA_REPO=smartbussiness-generic-model
export GITEA_URL=http://gitea.<TU_DOMINIO>:3000
export GITEA_TOKEN=tu_token_de_gitea

bash scripts/push-generic-model-to-gitea.sh
```

El script crea el repo en Gitea (si no existe) y hace push de `smartbussiness-generic-model` a la rama `main`.

**Opción B — Sin token (crear repo a mano):**

1. En Gitea: **+** → **New Repository** → Name: `smartbussiness-generic-model`, sin “Initialize repository”.
2. Luego:

```bash
cd toolkit-fastflow/integrations/web-cuantica
export GITEA_USER=TU_USUARIO
export GITEA_REPO=smartbussiness-generic-model
bash scripts/push-generic-model-to-gitea.sh
```

**URL del repo en Gitea:** `http://gitea.<TU_DOMINIO>:3000/TU_USUARIO/smartbussiness-generic-model`

---

## 3. Job Jenkins “generic-model” en la EC2

En **http://jenkins.<TU_DOMINIO>:8080** (o <IP_JENKINS>:8080):

1. **New Item** → nombre: `generic-model` (o `smartbussiness-generic-model`) → **Pipeline** → OK.
2. **Configure:**
   - **Pipeline** → Definition: **Pipeline script from SCM**
   - **SCM:** Git
   - **Repository URL:** `http://gitea.<TU_DOMINIO>:3000/TU_USUARIO/smartbussiness-generic-model.git`
   - **Credentials:** la misma de Gitea que usa pos-online-pipeline
   - **Branch:** `*/main`
   - **Script Path:** `Jenkinsfile`
3. **Save**.

Al hacer **Build Now**, el job clona el repo, ejecuta `mvn clean install -DskipTests` y deja el artefacto en `~/.m2/repository` del usuario **jenkins** en la EC2 (Built-In Node = Linux).

---

## 4. Encadenar pos-online-pipeline (artefacto listo en automático)

**pos-online-pipeline** → **Configure** → **General** → **Build after other projects are built** → proyecto `generic-model` → (opcional) solo si estable → **Save**.

O sin encadenar: **Build Now** en **generic-model** una vez; luego el POS compila mientras el JAR siga en `~/.m2`.

---

## 5. Resumen

| Paso | Dónde | Qué hacer |
|------|--------|-----------|
| Clone local | web-cuantica/smartbussiness-generic-model | Ya está (copiado del original). |
| Repo en Gitea | scripts/push-generic-model-to-gitea.sh | Ejecutar con GITEA_USER, GITEA_REPO; opcional GITEA_TOKEN para crear repo por API. |
| Job Jenkins | jenkins.<TU_DOMINIO>:8080 | Crear job **generic-model** (Pipeline from SCM, repo smartbussiness-generic-model, Script Path Jenkinsfile). |
| Artefacto listo | EC2 (usuario jenkins) | Build Now en generic-model → artefacto en ~/.m2. |
| pos-online | Job pos-online-pipeline | Opcional: “Build after” generic-model; o ejecutar generic-model una vez y luego pos-online cuando quieras. |

Ver también: [EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md) (instalación manual una sola vez si no usas el job).

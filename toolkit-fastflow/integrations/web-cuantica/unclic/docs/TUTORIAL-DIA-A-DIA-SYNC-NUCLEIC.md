# Tutorial día a día: UnClic (monorepo) → Gitea **nucleic** → Jenkins

Este documento es la guía **cotidiana** para subir **solo el sitio UnClic** al repo remoto en Gitea (`nucleic`), **sin** publicar el resto del monorepo. El pipeline de Jenkins apunta a ese repo.

---

## Contexto en una frase

| Dónde trabajás | Qué es |
|----------------|--------|
| Carpeta `unclic` dentro del repo grande (`pipeline-as-code-with-jenkins-master`, etc.) | Tu código del landing |
| Repo Gitea **`nucleic`** | Copia **independiente** con la misma raíz que `unclic` (`package.json` en la raíz del repo) |
| Script `scripts/update-nucleic-from-monorepo.sh` | Copia archivos con `rsync` y hace `git commit` + `git push` **solo** en el clon de **nucleic** |

No hace falta hacer `git push` del monorepo hacia Gitea para publicar el sitio: el script empuja **únicamente** el contenido de `unclic` al remoto **nucleic**.

---

## Prerrequisitos

1. **Ruta real** a la raíz de UnClic (donde están `package.json` y `Jenkinsfile`):

   ```text
   …/toolkit-fastflow/integrations/web-cuantica/unclic
   ```

   En muchas máquinas:

   ```text
   ~/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/unclic
   ```

2. **Credenciales Git** para Gitea (HTTPS con usuario/token o SSH) configuradas en tu Mac, para que `git push` a `https://gitea.unclic.consulting/.../nucleic.git` funcione.

3. El script usa por defecto:
   - **Origen:** carpeta del `unclic` (auto-detectada si ejecutás el script desde `unclic/`).
   - **Destino local del clone:** `NUCLEIC_WORK` → por defecto `~/Downloads/nucleic-landing`.
   - **Remoto:** `NUCLEIC_REPO_URL` → por defecto el `nucleic` del equipo (editá el script o exportá la variable si cambia el usuario/URL).

---

## Flujo del día a día (checklist)

1. **Abrí el proyecto** en el IDE en la carpeta **`unclic`** correcta (la del monorepo, no otra copia suelta).
2. **Editá** componentes, copy, estilos, etc. **Guardá los archivos** (⌘S).
3. **Probá en local** si querés: `npm run dev` (puerto según tu `package.json`, p. ej. 3002).
4. **Subí solo UnClic a Gitea nucleic** (desde la raíz `unclic`):

   ```bash
   cd ~/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/unclic
   bash scripts/update-nucleic-from-monorepo.sh
   ```

   Si tu ruta es distinta, ajustá el `cd` a **tu** ruta real.

5. **Revisá la salida:**
   - Si hay cambios en disco respecto al último commit de **nucleic**, verás un **commit** y **`git push`** a `main`.
   - Si dice **«Nada que commitear»**, no hay diferencias: o no guardaste, o editaste otra carpeta (véase [Problemas frecuentes](#problemas-frecuentes)).

6. **Jenkins:** con el job configurado contra el repo **nucleic** y rama `main`, un push nuevo dispara el build (según webhook o sondeo). Ver [JENKINS-NUCLEIC-DEPLOY.md](JENKINS-NUCLEIC-DEPLOY.md) para variables (`DEPLOY_HOST`, etc.).

---

## Comando único (cuando ya estás en `unclic`)

```bash
bash scripts/update-nucleic-from-monorepo.sh
```

No uses rutas de ejemplo como `/ruta/al/monorepo/...`: **no existen** en tu disco; son solo placeholders en documentación vieja.

---

## Variables opcionales del script

| Variable | Ejemplo | Uso |
|----------|---------|-----|
| `SITIO` | `/Users/tu/usuario/.../unclic` | Fuerza la carpeta origen si no ejecutás desde `unclic/`. |
| `NUCLEIC_WORK` | `$HOME/Downloads/nucleic-landing` | Dónde está el clone local de **nucleic**. |
| `NUCLEIC_REPO_URL` | `https://gitea.../usuario/nucleic.git` | Si el remoto cambia. |
| `NUCLEIC_COMMIT_MSG` | `feat: hero copy` | Mensaje del commit en **nucleic**. |

Ejemplo:

```bash
export NUCLEIC_COMMIT_MSG="feat(hero): nuevo titular"
bash scripts/update-nucleic-from-monorepo.sh
```

---

## Qué hace el script por dentro (resumen)

1. `git fetch` / `pull` en `NUCLEIC_WORK` (rama `main`).
2. `rsync` desde `SITIO/` hacia ese clone, **excluyendo** `node_modules`, `.next`, `.git` del origen, `.env*`, etc.
3. `git add -A` y, si hay cambios, `git commit` + `git push origin main`.

**Importante:** el script mira el **contenido de archivos en disco**, no si commiteaste en el monorepo. Si no guardás el archivo en el IDE, no habrá cambio que copiar.

---

## Problemas frecuentes

### «Nada que commitear (ya está al día…)»

- **Causa normal:** no hay archivos modificados en la carpeta `unclic` respecto al último estado ya commiteado en `nucleic-landing`.
- **Causa típica:** olvidaste **guardar** el archivo, o estás editando **otra copia** del proyecto (otra ruta).
- **Prueba rápida:** creá un archivo de prueba y volvé a ejecutar:

  ```bash
  echo "prueba $(date)" >> SYNC-TEST.md
  bash scripts/update-nucleic-from-monorepo.sh
  ```

  Si entonces **sí** commitea, el flujo está bien: borrá `SYNC-TEST.md` cuando no lo necesites, guardá, y ejecutá el script otra vez para subir el borrado.

### `cd: no such file or directory: /ruta/al/monorepo/...`

Esa ruta es un **ejemplo** en documentación; en tu Mac tenés que usar la ruta **real** (p. ej. bajo `Downloads/pipeline-as-code-with-jenkins-master/...`).

### Error al hacer `git push`

Revisá login en Gitea (token, SSH, permisos al repo **nucleic**). Documentación relacionada: [GITEA-NUCLEIC-PUSH.md](GITEA-NUCLEIC-PUSH.md) (incl. pushes grandes / HTTP 413).

### Jenkins no construye

- Rama del job: **`*/main`**.
- Repo URL: el **nucleic** correcto en Gitea.
- Que el **push** haya llegado: mirá en la web de Gitea el último commit en `main`.

---

## Documentación relacionada

| Doc | Contenido |
|-----|-----------|
| [JENKINS-NUCLEIC-DEPLOY.md](JENKINS-NUCLEIC-DEPLOY.md) | Job Jenkins, Docker, variables, deploy rsync |
| [GITEA-NUCLEIC-PUSH.md](GITEA-NUCLEIC-PUSH.md) | Historial: push inicial por bloques, 413 |
| [DEPLOY-SITIO-REMOTO.md](DEPLOY-SITIO-REMOTO.md) | Otros modos de deploy (rsync manual, etc.) |

---

## Resumen de un minuto

1. Editá y **guardá** en `unclic`.  
2. `bash scripts/update-nucleic-from-monorepo.sh`  
3. Confirmá en Gitea que `main` tiene el commit.  
4. Jenkins hace build/deploy si el job está configurado.

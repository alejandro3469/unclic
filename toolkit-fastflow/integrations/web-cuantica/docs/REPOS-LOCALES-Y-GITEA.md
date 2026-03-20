# Repos locales y Gitea — rutas y remotes

Estado comprobado con `git remote -v` en cada repo.

---

## Importante: dos niveles de Git

En tu máquina hay **varios repos Git**:

| Dónde estás (cd) | Qué repo usa `git status` / `git push` |
|------------------|----------------------------------------|
| **web-cuantica** o raíz del proyecto (`pipeline-as-code-with-jenkins-master`) | Repo **padre** (libro/toolkit). Rama ej. `handoff/george-web-cuantica-front`. Aquí ves "modified: repo-pos-fastflow/Jenkinsfile" etc. **Este repo no sube a Gitea pos-online ni generic-model.** |
| **repo-pos-fastflow** (cd dentro de esa carpeta) | Repo **pos-online** → Gitea `alejandro-perez/pos-online`. Remote: `origin`. **Para subir a Gitea: hacer add/commit/push aquí.** |
| **smartbussiness-generic-model** (cd dentro de esa carpeta) | Repo **generic-model** → Gitea `alejandro-perez/smartbussiness-generic-model`. Remote: `gitea`. **Para subir a Gitea: hacer add/commit/push aquí.** |

Regla: **para subir a Gitea siempre hay que estar dentro del repo correspondiente** (`cd repo-pos-fastflow` o `cd smartbussiness-generic-model`) y ejecutar `git add` / `git commit` / `git push` ahí. El `git status` que ves en **web-cuantica** es del repo padre, no de pos-online.

---

## Tabla de referencia

| Repo | Ruta local | Remote a Gitea | Push a Gitea |
|------|------------|----------------|--------------|
| **pos-online** | `.../toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow` | `origin` | `git push -u origin main` |
| **smartbussiness-generic-model** | `.../toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model` | `gitea` | `git push -u gitea main` |

Ruta base: `/Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master`.

---

## URLs Gitea

| Repo en Gitea | URL |
|---------------|-----|
| alejandro-perez/pos-online | `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git` |
| alejandro-perez/smartbussiness-generic-model | `http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model.git` |

---

## Comprobar remotes

```bash
# pos-online
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow
git remote -v
# origin  http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git (fetch)
# origin  http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git (push)

# generic-model
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model
git remote -v
# gitea   http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model.git (fetch)
# gitea   http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model.git (push)
# origin  https://gitlab.com/alejandro.perez3469/smartbussiness-generic-model.git (fetch)
# origin  https://gitlab.com/alejandro.perez3469/smartbussiness-generic-model.git (push)
```

---

---

## Resumen: pasos para subir a Gitea

**pos-online (Jenkinsfile):**
```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow
git add Jenkinsfile && git commit -m "Pipeline: logs + docker por sh" && git push -u origin main
```

**generic-model (Jenkinsfile):**
```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model
git add Jenkinsfile && git commit -m "Pipeline: logs por etapa" && git push -u gitea main
```

Comandos completos y alternativas: [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) § 2.1.

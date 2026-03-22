# Repos locales y Gitea — rutas y remotes

Tres árboles Git distintos: **monorepo padre**, **repo-pos-fastflow**, **smartbussiness-generic-model**. El push a Gitea es **solo** desde las dos últimas carpetas.

| `cd` aquí | Qué es | Push a Gitea |
|-----------|--------|--------------|
| Raíz monorepo / `web-cuantica` | Repo **padre** (toolkit). No sube POS ni generic-model. | — |
| `repo-pos-fastflow/` | POS → `TU_USUARIO/<repo-pos>` | Remote **`origin`** → `git push -u origin main` |
| `smartbussiness-generic-model/` | generic-model | Remote **`gitea`** → `git push -u gitea main` |

Ruta: `<RUTA_MONOREPO>/toolkit-fastflow/integrations/web-cuantica/…`

**URLs típicas:** `http://gitea.<TU_DOMINIO>:3000/TU_USUARIO/<nombre-repo>.git`

```bash
cd <RUTA_MONOREPO>/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow && git remote -v
cd <RUTA_MONOREPO>/toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model && git remote -v
```

**Ejemplo rápido**

```bash
cd .../repo-pos-fastflow
git add Jenkinsfile && git commit -m "Pipeline" && git push -u origin main

cd .../smartbussiness-generic-model
git add Jenkinsfile && git commit -m "Pipeline" && git push -u gitea main
```

Más: [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) § 2.1.

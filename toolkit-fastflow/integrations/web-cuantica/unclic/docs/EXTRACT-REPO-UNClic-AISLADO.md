# Extraer UnClic del monorepo (repo aislado)

Objetivo: un repositorio Git **solo** con el contenido de `unclic/` para Gitea/GitHub/Jenkins sin arrastrar `toolkit-fastflow` completo.

## Opción A — `git filter-repo` (recomendada)

Requiere [git-filter-repo](https://github.com/newren/git-filter-repo).

Desde la raíz del repo grande:

```bash
git filter-repo --path toolkit-fastflow/integrations/web-cuantica/unclic/ --path-rename toolkit-fastflow/integrations/web-cuantica/unclic/:
```

Queda la historia **solo** de esa carpeta, en la raíz del nuevo árbol. Luego:

```bash
git remote add origin https://gitea.tu-dominio/unclic/site.git
git push -u origin main
```

## Opción B — Subdirectorio nuevo sin historia

```bash
mkdir unclic-standalone && cd unclic-standalone
git init
cp -R /ruta/al/monorepo/toolkit-fastflow/integrations/web-cuantica/unclic/. .
git add .
git commit -m "chore: import UnClic desde monorepo"
```

Pierdes historia previa de commits.

## Opción C — `rsync` (como `scripts/update-nucleic-from-monorepo.sh`)

Ya usas rsync hacia **nucleic**; el repo destino puede ser el “aislado” oficial. Ajusta `REPO_URL` y excluye `node_modules`, `.next`, `out`.

## Después de aislar

1. Mueve **`.github/workflows/ci.yml`** a la raíz del nuevo repo (ya está bajo `unclic/.github/` en este árbol; al filtrar con `--path-rename ...:` la ruta queda `./.github/workflows/ci.yml`).
2. Actualiza **Jenkins**: el job debe clonar el **nuevo** repo (sin `UNC_APP_DIR` apuntando al monorepo).
3. Revisa **`scripts/update-nucleic-from-monorepo.sh`**: deja de ser necesario si solo existe un repo, o apunta al clon local del monorepo como origen.

## Microservicios en el repo aislado

- `services/api/` es un paquete **Node independiente** (`package-lock.json` propio).
- `npm ci` en la raíz **no** instala la API; Jenkins y CI ejecutan `npm ci` dentro de `services/api` además del root.

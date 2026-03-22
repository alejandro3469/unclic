# Git: monorepo “sucio” vs trabajar solo en **unclic**

## Por qué `git status` se ve enorme

El repositorio Git está en la **raíz del monorepo** (`pipeline-as-code-with-jenkins-master/`), no dentro de `unclic/`.

Si ejecutás `git status` desde `unclic`, Git muestra **cambios en todo el árbol**: `toolkit-fastflow/integrations/web-cuantica/docs/`, `kings-joers/`, `manifests/terraform/`, etc. Eso no lo genera Shadcn: es el estado real del working tree (ediciones, borrados, archivos nuevos en cualquier carpeta).

---

## Ver solo lo de la carpeta UnClic

Desde la raíz de **unclic** (donde está `package.json`):

```bash
cd /ruta/.../toolkit-fastflow/integrations/web-cuantica/unclic
git status .
git diff .
```

El **`.`** limita la salida a archivos bajo `unclic/`. Así el listado es “limpio” para el sitio.

Desde la **raíz del monorepo**:

```bash
git status -- toolkit-fastflow/integrations/web-cuantica/unclic
```

---

## Repo nucleic (solo UnClic, sin el monorepo)

Para publicar **solo** el landing en Gitea, seguí [TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md](./TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md): el script `scripts/update-nucleic-from-monorepo.sh` copia únicamente `unclic/` al repo **nucleic**. Jenkins no necesita que commitees todo el monorepo.

---

## Archivos que no deberían versionarse

En la raíz del monorepo se añadieron reglas en `.gitignore` para:

- `*.tfstate`, `*.tfstate.backup`
- `terraform.tfvars` (se mantiene `terraform.tfvars.example` si existe)

Si ya estaban trackeados, Git puede seguir mostrándolos hasta que los saques del índice (solo si aplica a tu flujo):

```bash
# Desde la raíz del monorepo; revisar antes de ejecutar
git rm --cached -r ruta/al/archivo-sensible 2>/dev/null || true
```

---

## Muchos `deleted:` fuera de `unclic`

Si no borraste a propósito cientos de `.md` en `web-cuantica/docs/`, podés **recuperar del último commit** (revisá antes):

```bash
cd /ruta/al/pipeline-as-code-with-jenkins-master
git restore toolkit-fastflow/integrations/web-cuantica/docs/
```

Ajustá la ruta al directorio que quieras restaurar. Si los borrados son intencionales, el siguiente paso es **commitear** ese cambio grande aparte, con mensaje claro.

---

## Resumen

| Necesidad | Comando / acción |
|-----------|------------------|
| Estado solo UnClic | `git status .` dentro de `unclic/` |
| No mezclar con todo el mono | Usar path `git status -- <ruta-unclic>` |
| Publicar sitio | Script sync → **nucleic** (tutorial enlazado arriba) |
| Terraform local | `.gitignore` en raíz; no subir `.tfstate` / `terraform.tfvars` |

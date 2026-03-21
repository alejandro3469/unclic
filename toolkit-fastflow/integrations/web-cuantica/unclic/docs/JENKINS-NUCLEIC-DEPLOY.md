# Jenkins: build y deploy automático del landing (repo **nucleic**)

Repo Gitea: `alejandro-perez/nucleic` → la **raíz del repositorio** es el proyecto Next.js (no hay subcarpeta `toolkit-fastflow/...`).

## Job en Jenkins

1. **New Item** → Pipeline (o Multibranch Pipeline).
2. **Pipeline from SCM** → Git.
3. **Repository URL:** `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`
4. **Credentials:** usuario/token Gitea si el repo es privado.
5. **Branch Specifier:** `*/main` (no `master`).
6. **Script Path:** `Jenkinsfile`

## Requisitos en el agente

- **Con Docker (por defecto en el Jenkinsfile):** el nodo debe poder ejecutar contenedores (`node:20-alpine`). En el stage Deploy el propio pipeline instala `rsync` y `openssh-client` dentro del contenedor.
- **Sin Docker:** ver comentarios al final del `Jenkinsfile` (agent `any` + NodeJS tool + rsync/ssh en el host).

## Variables del job (Environment variables)

| Variable | Obligatoria | Ejemplo | Descripción |
|----------|-------------|---------|-------------|
| `DEPLOY_HOST` | No* | `3.x.x.x` o `landing.tudominio.com` | Si está vacía, el build termina bien pero **no** copia a servidor. |
| `DEPLOY_USER` | No | `ec2-user` / `ubuntu` | Usuario SSH. |
| `DEPLOY_PATH` | No | `/usr/share/nginx/landing` | Directorio remoto donde está el `root` de Nginx para el sitio. |
| `DEPLOY_SSH_CREDENTIALS` | No | `id-credencial-jenkins` | ID de credencial tipo **SSH Username with private key** (plugin SSH Agent). Si no se define, se usa la identidad SSH por defecto del agente Jenkins. |
| `UNC_APP_DIR` | No | `.` | Solo monorepo: ruta al `package.json` de UnClic. En **nucleic** déjalo sin definir. |

\* Para automatizar deploy en cada push a `main`, configura al menos `DEPLOY_HOST` y la clave SSH adecuada.

## Servidor (Nginx)

- El contenido de `out/` (export estático de Next) se sincroniza con `rsync --delete` hacia `DEPLOY_PATH`.
- Asegura que Nginx apunte `root` a esa ruta y que el usuario SSH tenga permiso de escritura.

## Sincronizar código local (monorepo) → Gitea nucleic

Si desarrollas en `toolkit-fastflow/integrations/web-cuantica/unclic` dentro de un monorepo, Gitea **nucleic** es un repo aparte. Usa:

```bash
bash scripts/update-nucleic-from-monorepo.sh
```

Opcional:

```bash
export SITIO="/ruta/al/unclic"
export NUCLEIC_WORK="$HOME/Downloads/nucleic-landing"
bash scripts/update-nucleic-from-monorepo.sh
```

Documentación histórica de pushes por bloques: [GITEA-NUCLEIC-PUSH.md](GITEA-NUCLEIC-PUSH.md).

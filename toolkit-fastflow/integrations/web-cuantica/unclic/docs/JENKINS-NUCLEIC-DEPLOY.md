# Jenkins: build y deploy automático del landing (repo **nucleic**)

Repo Gitea: `alejandro-perez/nucleic` → la **raíz del repositorio** es el proyecto Next.js (no hay subcarpeta `toolkit-fastflow/...`).

## Job en Jenkins

1. **New Item** → Pipeline (o Multibranch Pipeline).
2. **Pipeline from SCM** → Git.
3. **Repository URL:** `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`
4. **Credentials:** usuario/token Gitea si el repo es privado.
5. **Branch Specifier:** `*/main` (no `master`).
6. **Script Path:** `Jenkinsfile`

## Requisitos en Jenkins

- **Plugin Docker Pipeline:** Manage Jenkins → Plugins → Available → busca "Docker" (nombre completo: "Docker Pipeline") → Install. Sin este plugin, el Jenkinsfile fallará con "Invalid agent type 'docker'".
- **Docker en el servidor Jenkins:** el servidor donde corre Jenkins debe tener Docker instalado y el usuario `jenkins` debe poder ejecutar `docker` (p. ej. en el grupo `docker`).
- **En el stage Deploy:** el pipeline instala `rsync` y `openssh-client` dentro del contenedor Alpine (`apk add`), así que no necesitas instalarlos en el servidor Jenkins.
- **npm en Docker + usuario Jenkins:** si ves `EACCES` sobre `/.npm`, el `Jenkinsfile` ya fuerza `HOME` y `NPM_CONFIG_CACHE` bajo el workspace del job. Imagen recomendada: `node:22-alpine` (algunas dependencias exigen Node ≥ 22).

## Variables del job (Environment variables)

| Variable | Obligatoria | Ejemplo | Descripción |
|----------|-------------|---------|-------------|
| `DEPLOY_HOST` | No* | `3.x.x.x` o `landing.tudominio.com` | Si está vacía, el build termina bien pero **no** copia a servidor. |
| `DEPLOY_USER` | No | `ec2-user` / `ubuntu` | Usuario SSH. |
| `DEPLOY_PATH` | No | `/usr/share/nginx/landing` | Directorio remoto donde está el `root` de Nginx para el sitio. |
| `DEPLOY_SSH_CREDENTIALS` | No | `id-credencial-jenkins` | ID de credencial tipo **SSH Username with private key** (plugin SSH Agent). Si no se define, se usa la identidad SSH por defecto del agente Jenkins. |
| `UNC_APP_DIR` | No | `.` | Solo monorepo: ruta al `package.json` de UnClic. En **nucleic** déjalo sin definir. |
| `NODEJS_INSTALLATION_NAME` | No | - | Solo si usas `Jenkinsfile.no-docker` (agent any). Nombre de la herramienta NodeJS en Global Tool Configuration. |

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

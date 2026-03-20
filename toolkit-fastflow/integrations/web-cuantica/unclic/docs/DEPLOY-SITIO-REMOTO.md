# Desplegar el sitio UnClic al remoto

El proyecto usa **Next.js con export estático** (`output: 'export'` en `next.config.js`). Tras `npm run build` se genera la carpeta **`out/`** con HTML, CSS y JS estáticos. Esta guía indica cómo subir ese build a un remoto.

---

## 0. Desplegar en https://unclic.consulting (sitio en vivo)

Para **subir el deploy** y que el sitio se actualice en **https://unclic.consulting**:

1. **Desde tu Mac** (con la clave SSH de la EC2 a mano), desde la carpeta **unclic**:
   ```bash
   # Si estás en la raíz del monorepo:
   cd toolkit-fastflow/integrations/web-cuantica/unclic
   bash scripts/deploy-to-unclic-consulting.sh
   ```
   O si ya estás en `unclic`, basta con:
   ```bash
   bash scripts/deploy-to-unclic-consulting.sh
   ```
   El script hace **build** y **rsync** de `out/` a la EC2 (`~/unclic-deploy/`). Si tu clave no está en `~/Downloads/gitea-key.pem`, pásala como argumento:
   ```bash
   bash scripts/deploy-to-unclic-consulting.sh /ruta/a/tu-clave.pem
   ```

2. **En la EC2** (donde corre Nginx para unclic.consulting), copia los estáticos al document root y actualiza permisos:
   ```bash
   sudo rsync -av --delete /home/ec2-user/unclic-deploy/ /usr/share/nginx/unclic/
   sudo chown -R nginx:nginx /usr/share/nginx/unclic
   ```

Configuración del servidor (DNS, Nginx, HTTPS): ver **[SUBIR-SITIO-A-UNCLIC-CONSULTING.md](SUBIR-SITIO-A-UNCLIC-CONSULTING.md)**.

---

## 1. Gitea desde local (build + publicar)

Todo desde tu máquina: build del sitio y subida a Gitea (rama de solo sitio estático) o a un servidor que tú elijas.

### 1.1 Build local

Desde la raíz del monorepo o desde unclic:

```bash
cd toolkit-fastflow/integrations/web-cuantica/unclic
npm ci
npm run build
```

Queda generada la carpeta **`out/`**. Las variables de entorno (Formspree, Cloudcraft, etc.) se leen en build time; si usas `.env.local`, se aplican en este paso.

### 1.2 Subir el sitio a una rama en Gitea (`gitea-pages`)

Gitea no tiene “Pages” integrado como GitHub. Una opción es dejar el contenido estático en una **rama dedicada** (p. ej. `gitea-pages`) y en el servidor servir esa rama con Nginx u otra herramienta (o usar [gitea-pages-static](https://github.com/remram44/gitea-pages-static) si está instalado).

**Desde tu local**, con el remoto `gitea` ya configurado:

```bash
# En la raíz del monorepo
REPO_ROOT="$(pwd)"
UNCLIC="$REPO_ROOT/toolkit-fastflow/integrations/web-cuantica/unclic"
BRANCH_PAGES="gitea-pages"

cd "$UNCLIC"
npm run build

# Clonar solo la rama pages (o crearla vacía) en un directorio temporal
TMPDIR_PAGES=$(mktemp -d)
git clone --branch "$BRANCH_PAGES" --single-branch "$REPO_ROOT" "$TMPDIR_PAGES" 2>/dev/null || \
  ( git clone --single-branch "$REPO_ROOT" "$TMPDIR_PAGES" && cd "$TMPDIR_PAGES" && git checkout --orphan "$BRANCH_PAGES" && git rm -rf . 2>/dev/null || true )

cd "$TMPDIR_PAGES"
cp -a "$UNCLIC/out/"* .
git add -A
git status -s
if [ -n "$(git status -s)" ]; then
  git commit -m "deploy unclic: sitio estático $(date +%Y-%m-%d)"
  git push gitea "$BRANCH_PAGES"
fi
rm -rf "$TMPDIR_PAGES"
```

- La primera vez puede que la rama `gitea-pages` no exista: entonces se crea una rama huérfana, se copia el contenido de `out/` y se hace push a **gitea**.
- En el servidor donde esté Gitea (o en otro que tenga clone del repo), puedes clonar esa rama y apuntar Nginx al directorio, o usar un servicio que sirva esa rama como sitio estático.

### 1.3 Subir el sitio por rsync/SSH a un servidor (desde local)

El código puede estar en Gitea; el **deploy** lo haces tú desde tu máquina copiando `out/` al servidor:

```bash
cd toolkit-fastflow/integrations/web-cuantica/unclic
npm run build
rsync -avz --delete out/ usuario@TU_SERVIDOR:~/unclic-deploy/
```

En el servidor, si Nginx sirve desde `/usr/share/nginx/unclic/`:

```bash
sudo rsync -av --delete ~/unclic-deploy/ /usr/share/nginx/unclic/
```

Así “usas Gitea” para el código y “desde local” para construir y subir el sitio.

### 1.4 Script listo para Gitea + rama `gitea-pages`

Puedes usar el script incluido en el repo (si existe) o guardar lo anterior en `toolkit-fastflow/integrations/web-cuantica/unclic/scripts/deploy-gitea-pages.sh` y ejecutar:

```bash
cd toolkit-fastflow/integrations/web-cuantica/unclic
bash scripts/deploy-gitea-pages.sh
```

El script hará build y push de `out/` a la rama `gitea-pages` del remoto `gitea`.

### 1.5 Si el push falla con HTTP 413 (Request Entity Too Large)

El servidor (o Nginx delante de Gitea) está rechazando el cuerpo del push porque supera el límite por defecto. Hay que **subir el límite en el servidor donde corre Gitea**.

**1. Saber a qué instancia apunta tu Gitea**

Desde la raíz del monorepo (o desde unclic si el remoto está en el repo raíz):

```bash
git remote get-url gitea
```

- Si la URL es por **hostname** (p. ej. `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`): el servidor a configurar es el que tiene ese nombre de dominio. Para saber la IP:
  ```bash
  ping -c1 gitea.unclic.consulting
  ```
  o revisa en tu DNS / Route53 qué instancia EC2 tiene asignado ese nombre. En ese servidor es donde debe tocarse Nginx.
- Si la URL es por **IP** (p. ej. `https://13.58.58.245/...`): esa IP es la instancia a la que conectarte. En tu lista EC2 hay dos "fastflow-gitea" (**18.227.21.135** y **13.58.58.245**); la que coincida con el remoto es la que debes configurar.

**2. Conectarte a esa instancia**

Con la clave que uses para Gitea (p. ej. `gitea-key.pem`):

```bash
ssh -i /ruta/a/gitea-key.pem ec2-user@<IP_DE_GITEA>
```

**3. Aumentar el límite en Nginx**

En el servidor:

```bash
# Ver si Nginx está instalado y qué config usa
sudo nginx -t
sudo grep -r "proxy_pass\|client_max_body_size" /etc/nginx/
```

En el bloque `server` que hace proxy a Gitea (p. ej. en `/etc/nginx/nginx.conf` o en `/etc/nginx/conf.d/*.conf`), añade o ajusta **dentro de ese `server`**:

```nginx
client_max_body_size 50M;
```

Por ejemplo, si tienes algo como:

```nginx
server {
    listen 80;
    server_name gitea.ejemplo.com;
    location / {
        proxy_pass http://127.0.0.1:3000;
        ...
    }
}
```

debe quedar:

```nginx
server {
    listen 80;
    server_name gitea.ejemplo.com;
    client_max_body_size 50M;
    location / {
        proxy_pass http://127.0.0.1:3000;
        ...
    }
}
```

Guarda, comprueba y recarga:

```bash
sudo nginx -t && sudo systemctl reload nginx
```

**4. Reintentar el deploy desde tu Mac**

```bash
cd toolkit-fastflow/integrations/web-cuantica/unclic
bash scripts/deploy-gitea-pages.sh
```

---

## 2. GitHub Pages

### Opción A: GitHub Actions (recomendada)

Cada push a `main` (o la rama que elijas) hace build y sube `out/` a la rama `gh-pages`. El sitio queda en `https://<usuario>.github.io/<repo>/` (o en un dominio custom si lo configuras).

1. En el repo GitHub: **Settings → Pages → Source** = “GitHub Actions”.
2. Crea el workflow en el repo raíz (no dentro de unclic):

**`.github/workflows/deploy-unclic-pages.yml`** (en la raíz del monorepo):

```yaml
name: Deploy UnClic to GitHub Pages

on:
  push:
    branches: [main]
    paths:
      - 'toolkit-fastflow/integrations/web-cuantica/unclic/**'
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

concurrency:
  group: pages
  cancel-in-progress: true

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Setup Node
        uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'
          cache-dependency-path: toolkit-fastflow/integrations/web-cuantica/unclic/package-lock.json

      - name: Install and build
        working-directory: toolkit-fastflow/integrations/web-cuantica/unclic
        run: |
          npm ci
          npm run build

      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3
        with:
          path: toolkit-fastflow/integrations/web-cuantica/unclic/out

  deploy:
    needs: build
    runs-on: ubuntu-latest
    environment: github-pages
    steps:
      - name: Deploy to GitHub Pages
        uses: actions/deploy-pages@v4
```

3. Si el repo es **de organización** o quieres otro origen, en **Settings → Pages** elige la source “GitHub Actions” y la rama que use el workflow.
4. Tras el primer push que toque `toolkit-fastflow/integrations/web-cuantica/unclic/**`, el workflow generará el sitio y GitHub Pages lo publicará.

**Nota:** Si el repo se llama `unclic` y el usuario es `alejandro3469`, la URL será `https://alejandro3469.github.io/unclic/`. Para que sea la raíz (`https://alejandro3469.github.io/unclic/` con rutas tipo `/soluciones`) no hace falta nada más; si usas un subpath, configura `assetPrefix` y `basePath` en `next.config.js`.

### Opción B: Push manual a `gh-pages`

```bash
cd toolkit-fastflow/integrations/web-cuantica/unclic
npm ci
npm run build
# Subir solo out/ a la rama gh-pages (usa un clone o subtree)
npx gh-pages -d out --git "$(git rev-parse --show-toplevel)" -r origin
```

(Requiere `gh-pages` instalado: `npm install -g gh-pages` o `npx gh-pages`.) Luego en GitHub: **Settings → Pages → Source** = rama `gh-pages`, carpeta `/ (root)`.

---

## 2. Servidor propio (EC2, VPS) por rsync/SSH

Build local y copia de `out/` al servidor. Ajusta usuario, IP y rutas.

```bash
cd toolkit-fastflow/integrations/web-cuantica/unclic
npm ci
npm run build
rsync -avz --delete out/ usuario@IP-O-SERVIDOR:~/unclic-deploy/
```

En el servidor, si Nginx (u otro) sirve desde `/usr/share/nginx/unclic/`:

```bash
sudo rsync -av --delete ~/unclic-deploy/ /usr/share/nginx/unclic/
```

Variables de entorno (Formspree, Cloudcraft, LinkedIn/GitHub, etc.) se inyectan en **build time**. Definirlas antes de `npm run build` (o en el entorno del CI que haga el build). Ver `.env.example` o docs de [DEMO-ACCESS-FORMSPREE-GMAIL.md](DEMO-ACCESS-FORMSPREE-GMAIL.md) y [VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md](VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md).

---

## 3. Vercel / Netlify

- **Vercel:** Conectar el repo, **Root Directory** = `toolkit-fastflow/integrations/web-cuantica/unclic`, build command `npm run build`, output directory `out`. Framework preset = Next.js (static export).
- **Netlify:** Igual; **Publish directory** = `out`, **Build command** = `npm run build` (desde la raíz del proyecto unclic).

En ambos, configurar las variables de entorno en el panel (build-time).

---

## Resumen

| Destino           | Build              | Publicación                                      |
|-------------------|--------------------|--------------------------------------------------|
| **Gitea (local)** | `npm run build`    | `scripts/deploy-gitea-pages.sh` → rama gitea-pages; o rsync a servidor |
| GitHub Pages      | `npm run build`    | Actions → upload artifact o gh-pages              |
| Servidor (SSH)    | `npm run build`    | `rsync out/` → servidor → Nginx                  |
| Vercel/Netlify    | En su CI           | Automático desde el repo                         |

Para **Gitea desde local**: build en tu máquina y usa el script o los comandos de la sección 1. El código puede estar en Gitea; el deploy lo haces tú desde tu máquina.

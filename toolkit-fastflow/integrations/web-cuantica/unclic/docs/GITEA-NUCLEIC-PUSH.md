# Subir el landing a Gitea (repo nucleic)

Repo en Gitea: **nucleic**  
URL: `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`

**Importante:** El repo **nucleic** debe contener **solo** el código del landing (`unclic`), no todo el monorepo. Así Jenkins clona solo lo necesario.

---

## Push por bloques (evita HTTP 413)

Si el servidor Gitea limita el tamaño del push (error 413), sube el código **por bloques**: varios commits pequeños y un `git push` después de cada uno. Cada push queda por debajo del límite.

Ajusta la ruta `SITIO` si tu monorepo está en otra carpeta.

### Bloque 0: Copiar y preparar

```bash
cd ~/Downloads
SITIO="/Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/unclic"

# Copiar solo código (sin node_modules, .next, .idea)
rsync -av --exclude=node_modules --exclude=.next --exclude=.idea "$SITIO/" nucleic-landing/
cd nucleic-landing

git init
git checkout -b main
git remote add origin https://gitea.unclic.consulting/alejandro-perez/nucleic.git
```

### Bloque 1: Config y app

```bash
git add .gitignore .env.example components.json package.json package-lock.json next.config.js tsconfig.json tailwind.config.ts next-env.d.ts
git add app/
git commit -m "chore: config y app"
git push -u origin main
```

### Bloque 2: UI

```bash
git add components/ui/
git commit -m "feat: componentes ui"
git push origin main
```

### Bloque 3: Layout y secciones

```bash
git add components/layout/ components/sections/
git commit -m "feat: layout y secciones"
git push origin main
```

### Bloque 4: Lib y Jenkinsfile

```bash
git add lib/ Jenkinsfile README.md
git commit -m "feat: lib, Jenkinsfile, README"
git push origin main
```

### Bloque 5: Docs

```bash
git add docs/
git commit -m "docs: documentación"
git push origin main
```

### Bloque 6: Resto (public, etc.)

```bash
git add public/ 2>/dev/null || true
git add .
git status
git commit -m "chore: resto de archivos" || true
git push origin main
```

Si en algún paso `git add` no encuentra archivos (ej. no hay `public/`), ignora el error y sigue. Al final, `nucleic` en Gitea tiene todo el landing por bloques.

---

## Si estás en un monorepo (un solo push)

Si el límite 413 ya está resuelto en el servidor (Nginx `client_max_body_size 50M`) o el landing es pequeño, puedes subir todo de una vez:

1. Copiar el landing sin node_modules/.next (como en Bloque 0).
2. `git add .` → `git commit -m "Initial commit: landing UnClic"` → `git push -u origin main`.

---

## Crear el repo en Gitea (ya hecho)

En Gitea: **alejandro-perez/nucleic** (privado o público). No inicializar con README si vas a subir el código existente.

---

## Subir un repo existente (landing ya es un repo independiente)

Si el landing **ya es** un repo Git propio (solo esa carpeta, no monorepo), desde su raíz:

```bash
git remote add origin https://gitea.unclic.consulting/alejandro-perez/nucleic.git
git push -u origin main
```

Si tu rama se llama `master`, usa `git push -u origin master`.

---

## Clonar el repo en otra máquina

```bash
git clone https://gitea.unclic.consulting/alejandro-perez/nucleic.git
cd nucleic
```

---

## Jenkins

En el job de Jenkins (landing / unclic):

- **Repository URL:** `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`
- **Branch:** `main`
- **Script Path:** `Jenkinsfile`

Si el repo es privado, configurar en Jenkins las credenciales de Gitea (usuario + contraseña o token).

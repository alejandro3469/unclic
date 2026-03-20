# Plan: Subdominio genérico para landing + Demos (UnClic / Vantive)

Objetivo: **subir a Vantive UnClic** (o servidor UnClic) un **nuevo subdominio con nombre genérico** para este website (sitio-web-cuantica). Dar de baja la configuración/instancia actual que corresponda y **hospedar ahí** el landing; **automatizar con Jenkins** conectado a **Gitea**; y ofrecer desde la sección **Demos** del landing enlaces de **solo visualización** a: demo Jenkins, demo Gitea, demo POS, demo Registry, demo Kubernetes, demo Terraform (un mismo proyecto, usuario puede ver pipelines/interfaces ya creadas, no crear nuevos).

---

## 1. Resumen en una tabla

| Tema | Decisión |
|------|----------|
| **Subdominio** | Nombre genérico por subdominio, ej. **`landing.unclic.consulting`** o **`demos.unclic.consulting`** o **`web.unclic.consulting`**. Se elige uno y se documenta en DNS + Nginx. |
| **Dónde se hospeda** | **Misma EC2 que Vantive** (fastflow-vantive) con un nuevo `server` en Nginx para este subdominio. Así no se crea una instancia nueva; se da de baja solo la “config” o contenido que hoy ocupe ese rol (si lo hubiera) y se sustituye por el build del sitio-web-cuantica. |
| **Qué se hospeda en el link** | El **landing** (sitio-web-cuantica): Next.js export estático (`out/`) servido por Nginx en ese subdominio. |
| **Automatización** | **Jenkins**: job que clona desde **Gitea** (repo sitio-web-cuantica), hace `npm ci && npm run build`, y despliega por **SSH** a la EC2 Vantive copiando `out/` al document root del subdominio. |
| **Gitea** | Repo **nucleic** en Gitea: `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`. Jenkins con Pipeline from SCM, rama main, Script Path `Jenkinsfile`. |
| **Demos** | Una sola “suite” de demos: Jenkins, Gitea, POS, Registry, Kubernetes, Terraform. El usuario **solo ve** lo ya desplegado (pipelines existentes, UIs de demo); **no crea** nuevos pipelines. Enlaces desde la sección **Demos** del landing. |

---

## 2. Subdominio genérico (nombre y DNS)

- **Nombre sugerido:** `landing.unclic.consulting` (genérico: “landing” por subdominio).
- **Alternativas:** `demos.unclic.consulting`, `web.unclic.consulting`, `sitio.unclic.consulting`.

**Pasos:**

1. **Namecheap** → unclic.consulting → **Advanced DNS** → **HOST RECORDS**.
2. **ADD NEW RECORD**: Type **A**, Host **landing** (o el nombre elegido), Value **IP de fastflow-vantive** (ej. 3.18.111.60 o la actual de la EC2 Vantive), TTL Automatic.
3. Actualizar **INFRAESTRUCTURA-UNCLIC-ACTUAL.md** con el nuevo subdominio y su uso.

Si más adelante quieres **varios** subdominios genéricos (uno por producto/sitio), repetir el mismo patrón: un A record por nombre (ej. landing, demos, web).

---

## 3. Hospedaje en la EC2 Vantive (dar de baja config anterior si aplica)

- **EC2:** **fastflow-vantive** (la que hoy sirve vantive.unclic.consulting).
- **Nginx:** Añadir un **nuevo server block** para el subdominio elegido (ej. `landing.unclic.consulting`). No quitar el bloque de `vantive.unclic.consulting` salvo que decidas dar de baja ese sitio.
- **Document root del nuevo subdominio:** p. ej. `/usr/share/nginx/landing` (o `/var/www/landing`). El job de Jenkins copiará ahí el contenido de `out/` del build.

**Dar de baja “esta instancia o su configuración”:**

- Si hoy existe **otra** instancia o **otra** config (otro server_name o otro path) que se usaba como “landing” o “sitio web cuántica”, se **da de baja** esa config o esa instancia y el **único** lugar donde se sirve este landing pasa a ser el nuevo subdominio en fastflow-vantive.

**Ejemplo de Nginx (nuevo archivo en la EC2):**

```nginx
# /etc/nginx/conf.d/landing-unclic.conf
server {
    listen 80;
    server_name landing.unclic.consulting;
    root /usr/share/nginx/landing;
    index index.html;
    location / {
        try_files $uri $uri/ $uri.html /index.html;
    }
}
```

Después: **Certbot** para HTTPS: `sudo certbot --nginx -d landing.unclic.consulting` (o incluir en el wildcard si ya tienes cert *.unclic.consulting).

---

## 4. Jenkins: pipeline para sitio-web-cuantica

- **Job en Jenkins:** ej. **sitio-web-cuantica** o **landing-unclic**.
- **Pipeline from SCM:** repo en Gitea del proyecto sitio-web-cuantica, rama **main**, Script Path **Jenkinsfile**.
- **Stages sugeridos:**
  1. **Checkout** (Git desde Gitea).
  2. **Install** (`npm ci`).
  3. **Build** (`npm run build` → genera `out/`).
  4. **Deploy** (SSH a fastflow-vantive, rsync o scp de `out/` a `/usr/share/nginx/landing`).
- **Credenciales Jenkins:** SSH key o usuario/contraseña para SSH a la EC2 Vantive; credenciales de Gitea si el repo es privado.

El **Jenkinsfile** está en la raíz de `sitio-web-cuantica/` (Checkout → Install → Build → Deploy). En Jenkins, configurar **variables de entorno** del job (o globales): **DEPLOY_HOST** (IP o hostname de fastflow-vantive), **DEPLOY_USER** (ej. `ec2-user`), **DEPLOY_PATH** (ej. `/usr/share/nginx/landing`). Credenciales: SSH private key para el usuario sobre la EC2 Vantive. Un commit a main (o Build Now) dispara el pipeline y actualiza el contenido del subdominio.

---

## 5. Gitea: repo y conexión con Jenkins

- **Repo en Gitea:** **nucleic** — `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`.
- **Contenido:** el código de `toolkit-fastflow/integrations/web-cuantica/sitio-web-cuantica` (incluye **Jenkinsfile** en la raíz). Ver **GITEA-NUCLEIC-PUSH.md** en el sitio para comandos de push.
- **Jenkins:** en el job, Repository URL = `https://gitea.unclic.consulting/alejandro-perez/nucleic.git`, Branch **main**, Script Path **Jenkinsfile**. Poll SCM o webhook para automatizar en cada push.

---

## 6. Demos: un mismo proyecto, solo visualización

Las demos son **interfaces ya desplegadas**; el usuario **no crea** nuevos pipelines, solo **ve** los que ya existen y accede desde el landing.

| Demo | Qué ve el usuario | URL típica (UnClic) |
|------|-------------------|----------------------|
| **Jenkins** | Pipelines ya creados (ej. pos-online, sitio-web-cuantica), builds, logs. Solo lectura/visualización. | https://jenkins.unclic.consulting (o :8080 si no hay proxy) |
| **Gitea** | Repos existentes, commits, ramas. Solo lectura (o cuenta demo con permisos limitados). | https://gitea.unclic.consulting (o :3000) |
| **POS** | App POS desplegada (FastFlow). Solo uso de la app, no crear pipelines. | https://pos.unclic.consulting (o la URL que tengas para el POS) |
| **Registry** | Imágenes ya pusheadas. Solo listar/ver, no push desde la UI pública. | URL del registry (ej. registry.unclic.consulting si existe) |
| **Kubernetes** | Dashboard o vistas de solo lectura de recursos ya desplegados (si tienes K8s demo). | URL del dashboard K8s (ej. k8s.unclic.consulting o enlace a consola) |
| **Terraform** | Estados o outputs ya generados (vistas, no ejecutar apply). | Enlace a docs o a una UI de solo lectura si la hay |

**En el landing (sección Demos):** Enlaces desde cada tarjeta a estas URLs. Texto tipo: “Ver pipelines existentes”, “Abrir demo Jenkins”, etc., sin ofrecer “Crear pipeline”. Las URLs están en **`lib/demos.ts`** y se sobrescriben con **variables de entorno en build** (NEXT_PUBLIC_DEMO_JENKINS_URL, NEXT_PUBLIC_DEMO_GITEA_URL, NEXT_PUBLIC_DEMO_POS_URL, NEXT_PUBLIC_DEMO_REGISTRY_URL, NEXT_PUBLIC_DEMO_K8S_URL, NEXT_PUBLIC_DEMO_TERRAFORM_URL). Si una URL no está configurada, la tarjeta muestra “Próximamente” y el botón deshabilitado.

---

## 7. Checklist de implementación

- [ ] **DNS:** A record para el subdominio elegido (landing / demos / web) → IP de fastflow-vantive.
- [ ] **EC2 Vantive:** Crear directorio (ej. `/usr/share/nginx/landing`), nuevo server block Nginx para ese subdominio, recargar Nginx; Certbot si aplica.
- [ ] **Dar de baja:** Quitar instancia o config anterior que se reemplaza por este landing.
- [ ] **Gitea:** Repo sitio-web-cuantica creado; código + Jenkinsfile subidos.
- [ ] **Jenkins:** Job sitio-web-cuantica (Pipeline from SCM, Gitea), credenciales Gitea + SSH a Vantive; Build y Deploy probados.
- [ ] **Landing:** Sección Demos actualizada con URLs reales (Jenkins, Gitea, POS, Registry, K8s, Terraform) y copy “solo visualización”.
- [ ] **Documentación:** Actualizar INFRAESTRUCTURA-UNCLIC-ACTUAL.md y este plan con URLs y nombres finales.

---

## 8. Documentos relacionados

- [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) — IPs, DNS, servidores.
- [EC2-CREAR-LANDING-UNCLIC.md](EC2-CREAR-LANDING-UNCLIC.md) — Patrón Nginx + DNS para landing.
- [SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md) — Levantar Vantive y actualizar contenido.
- [RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md) — HTTPS y múltiples instancias.
- [PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md](PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md) — Demo POS y URLs para clientes.

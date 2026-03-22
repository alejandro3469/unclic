# HTTPS: marcar como seguro y dar acceso a clientes (unclic, Gitea, Jenkins)

Objetivo: que **unclic.consulting**, **gitea.unclic.consulting** y **jenkins.unclic.consulting** se abran con **https://** (candado) para usar desde el móvil o dar acceso a clientes sin avisos de "no seguro".

**Si desde otro dispositivo ves "This site can't provide a secure connection" o ERR_SSL_PROTOCOL_ERROR:** es porque estás entrando con **https://** pero el servidor solo tiene **HTTP** (puerto 8080/3000 sin certificado). La solución es instalar **Nginx + Let's Encrypt** en cada EC2 como se describe abajo. Resumen de instancias, pipeline y nueva EC2 para pos/landing: **[RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md)**.

---

## 1. Completar la configuración inicial de Gitea (ahora, con HTTP)

En la pantalla **Initial Configuration** de Gitea, rellena así para poder usarlo ya (luego activamos HTTPS):

| Campo | Valor |
|-------|--------|
| **Database Type** | SQLite3 |
| **Path** (SQLite3) | `/data/gitea/gitea.db` (o el path que use tu instalación Docker/volumen) |
| **Site Title** | unclic – Gitea (o el nombre que quieras) |
| **Repository Root Path** | `/data/gitea/repositories` |
| **Run As Username** | `git` (o el usuario con el que corre Gitea) |
| **Server Domain** | `gitea.unclic.consulting` |
| **SSH Server Port** | (vacío si no usas SSH por ahora) |
| **Gitea HTTP Listen Port** | `3000` |
| **Gitea Base URL** | `http://gitea.unclic.consulting:3000/` |
| **Log Path** | `/data/gitea/log` |

Crea el **Administrator Account** (usuario y contraseña de admin). Luego **Install Gitea**.

Cuando tengas HTTPS, cambiarás **Gitea Base URL** en **Site Administration → Configuration → Server** a `https://gitea.unclic.consulting/` (sin puerto).

---

## 2. Cómo conseguir HTTPS (sitio seguro)

Tienes dos caminos:

| Opción | Ventaja | Resumen |
|--------|--------|--------|
| **A. Nginx + Let's Encrypt** en cada EC2 | HTTPS “real” en tu dominio, candado correcto en móvil y para clientes | Instalar Nginx y certbot en la EC2 de Gitea y en la de Jenkins; certificado gratis para gitea.unclic.consulting y jenkins.unclic.consulting. |
| **B. Cloudflare** (proxy) | No instalas certificados en el servidor; Cloudflare termina HTTPS | Pones el dominio en Cloudflare; los A records apuntan a Cloudflare. Gitea y Jenkins en puertos 3000/8080 requieren **Cloudflare Tunnel** (ver más abajo) o poner Nginx en 443. |

Recomendación para **acceso desde móvil y para clientes**: **Opción A (Nginx + Let's Encrypt)** en cada EC2, para tener **https://gitea.unclic.consulting** y **https://jenkins.unclic.consulting** sin puertos y con candado.

**Para todo el dominio y todos los subdominios (Jenkins, Gitea, pos, landing, etc.) con un solo certificado gratis:** usar **wildcard + apex** (unclic.consulting + \*.unclic.consulting). Guía: **[HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md)**.

---

## 3. Opción A: Nginx + Let's Encrypt (paso a paso)

Así quedan **https://gitea.unclic.consulting** y **https://jenkins.unclic.consulting** (sin :3000 ni :8080). Funciona bien en móvil y para dar acceso a clientes.

### 3.1 En la EC2 de **Gitea** (`<IP_GITEA>`)

1. Conéctate por SSH a la EC2 de Gitea.
2. Instala Nginx y certbot (ejemplo en Ubuntu/Debian):
   ```bash
   sudo apt update
   sudo apt install -y nginx certbot python3-certbot-nginx
   ```
3. Crea un bloque para Gitea (Nginx escuchará en 80 primero para que Let's Encrypt valide):
   ```bash
   sudo nano /etc/nginx/sites-available/gitea
   ```
   Contenido (sustituye `gitea.unclic.consulting` si usas otro dominio):
   ```nginx
   server {
       listen 80;
       server_name gitea.unclic.consulting;
       location / {
           proxy_pass http://127.0.0.1:3000;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
       }
   }
   ```
4. Activa el sitio y recarga Nginx:
   ```bash
   sudo ln -s /etc/nginx/sites-available/gitea /etc/nginx/sites-enabled/
   sudo nginx -t && sudo systemctl reload nginx
   ```
5. Pide el certificado (responde el correo si lo pide):
   ```bash
   sudo certbot --nginx -d gitea.unclic.consulting
   ```
   Certbot modificará la config para escuchar en 443 y usar el certificado. Acepta redirigir HTTP → HTTPS.
6. En **Gitea**: **Site Administration** → **Configuration** → **Server**:
   - **DOMAIN:** `gitea.unclic.consulting`
   - **ROOT URL:** `https://gitea.unclic.consulting/`
   Guardar. Reinicia el contenedor de Gitea si hace falta.
7. Abre **https://gitea.unclic.consulting** desde el navegador (y desde el móvil). Debe verse el candado.

### 3.2 En la EC2 de **Jenkins**

> **Amazon Linux 2** (fastflow-jenkins-controller, `yum`, sin `sites-available`): usa la guía detallada **[HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md](HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md)** (Nginx en `conf.d/`, EPEL + certbot, SG 80/443).

En **Ubuntu/Debian** (ejemplo con IP placeholder `<IP_JENKINS>`):

1. SSH a la EC2 de Jenkins.
2. Instala Nginx y certbot (mismo que arriba).
3. Crea el sitio para Jenkins:
   ```bash
   sudo nano /etc/nginx/sites-available/jenkins
   ```
   ```nginx
   server {
       listen 80;
       server_name jenkins.unclic.consulting;
       location / {
           proxy_pass http://127.0.0.1:8080;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
       }
   }
   ```
4. Activa y recarga:
   ```bash
   sudo ln -s /etc/nginx/sites-available/jenkins /etc/nginx/sites-enabled/
   sudo nginx -t && sudo systemctl reload nginx
   ```
5. Certificado:
   ```bash
   sudo certbot --nginx -d jenkins.unclic.consulting
   ```
6. En **Jenkins**: **Manage Jenkins** → **System** → **Jenkins URL:** `https://jenkins.unclic.consulting/`. Guardar.
7. Prueba **https://jenkins.unclic.consulting** (móvil y clientes).

### 3.3 Renovación automática del certificado

Let's Encrypt caduca en 90 días. Certbot suele dejar un cron/systemd timer. Comprueba:
```bash
sudo certbot renew --dry-run
```

---

## 4. Opción B: Cloudflare (resumen)

- Añades **unclic.consulting** a Cloudflare y cambias los nameservers en Namecheap a los que te da Cloudflare.
- En Cloudflare creas **A** (o CNAME): **gitea** → IP de Gitea, **jenkins** → IP de Jenkins.
- Activas el proxy (nube naranja) para que el tráfico pase por Cloudflare y ellos sirvan HTTPS.

**Problema:** Cloudflare solo hace proxy de ciertos puertos (80, 443, 8080, etc.). **Gitea en 3000** no está en la lista. Opciones:

- **Cloudflare Tunnel:** Instalas `cloudflared` en la EC2 de Gitea y expones el puerto 3000 por un túnel. En Cloudflare asignas la ruta pública (ej. `gitea.unclic.consulting`) a ese túnel. Así no abres el puerto 3000 y tienes HTTPS.
- O poner **Nginx en la EC2 de Gitea** en 443 (como en la Opción A) y en Cloudflare apuntar **gitea** a la IP; Cloudflare termina HTTPS y puede enviar tráfico a tu Nginx en 443.

Para **Jenkins en 8080**, Cloudflare puede hacer proxy directo a 8080 en el plan gratuito en algunos casos; para evitar problemas, suele ser más simple usar Nginx en 443 en la EC2 (igual que Gitea).

---

## 5. Orden de acceso para ti y para clientes

Cuando tengas HTTPS:

| Quién | Orden sugerida | URLs |
|-------|----------------|------|
| **Tú / clientes** | 1) Sitio unclic → 2) Gitea → 3) Jenkins | https://unclic.consulting (si tienes algo), https://gitea.unclic.consulting, https://jenkins.unclic.consulting |
| **Móvil** | Mismas URLs; el navegador mostrará candado si usas HTTPS. |
| **Dar acceso a clientes** | Envía enlaces https://gitea.unclic.consulting y https://jenkins.unclic.consulting; crea usuarios en Gitea/Jenkins según necesiten. |

---

## 6. Resumen rápido

| Objetivo | Acción |
|----------|--------|
| Completar Gitea ya | Usa los valores de la tabla del apartado 1 (Base URL con `http://...:3000/`). |
| Marcar como seguro (HTTPS) | Nginx + Let's Encrypt en cada EC2 (apartado 3). |
| Acceso desde móvil / clientes | Usar siempre las URLs **https://** (sin puerto) una vez configurado Nginx y certbot. |
| Después de activar HTTPS | En Gitea: ROOT URL = `https://gitea.unclic.consulting/`. En Jenkins: Jenkins URL = `https://jenkins.unclic.consulting/`. |

Referencias: [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md), [PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md](../90-archivo/PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md).

**Después de HTTPS:** para conectar Jenkins con Gitea, probar pos-online en Jenkins y compartir usuarios con credenciales/permisos: **[CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md)**.

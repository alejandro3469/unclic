# Vantive: arreglar Forbidden, HTTPS y proteger el servidor

Guía para el servidor **fastflow-vantive** (3.22.236.150 / vantive.unclic.consulting): quitar el error "Forbidden", activar **HTTPS** (SSL) y dejarlo abierto al mundo solo en 80/443, protegiendo el resto.

**Nota:** La EC2 es Amazon Linux 2023 (`dnf`). Sin Elastic IP, la IP pública puede cambiar al parar/arrancar; entonces actualiza el A record en Namecheap y el `server_name` en Nginx. Actualmente: **3.22.236.150**.

---

## 1. Arreglar "Forbidden" (403)

Si responde por IP pero **http://vantive.unclic.consulting** o alguna ruta da *Forbidden*:

### En la EC2 (SSH)

1. **Revisar la config de Nginx**  
   ```bash
   sudo cat /etc/nginx/conf.d/vantive.conf
   ```
   Debe tener algo como (sustituye `3.18.111.60` por la IP pública actual si quieres que responda también por IP):
   ```nginx
   server {
       listen 80 default_server;
       listen [::]:80 default_server;
       server_name vantive.unclic.consulting 3.18.111.60;
       root /usr/share/nginx/html;
       index index.html;
       location / {
           try_files $uri $uri/ /index.html;
       }
   }
   ```
   Esa configuración es correcta; si aun así hay 403, sigue con permisos y SELinux.

2. **Comprobar permisos del directorio**  
   Nginx (usuario `nginx` o `www-data`) debe poder leer los ficheros:
   ```bash
   sudo ls -la /usr/share/nginx/html
   # Debe haber index.html y permisos tipo 644 (ficheros) y 755 (directorios)
   sudo chown -R nginx:nginx /usr/share/nginx/html   # si usas nginx
   # o
   sudo chown -R www-data:www-data /usr/share/nginx/html
   sudo chmod -R o+r /usr/share/nginx/html
   sudo find /usr/share/nginx/html -type d -exec chmod o+x {} \;
   ```

3. **SELinux (si está activo)**  
   ```bash
   getenforce
   # Si es Enforcing:
   sudo chcon -R -t httpd_sys_content_t /usr/share/nginx/html
   sudo systemctl reload nginx
   ```

4. **Recargar Nginx**  
   ```bash
   sudo nginx -t && sudo systemctl reload nginx
   ```

Tras esto, tanto la IP como el dominio deberían servir el sitio sin 403.

---

## 2. HTTPS (SSL) con Let's Encrypt – sitio abierto al mundo

Objetivo: **https://vantive.unclic.consulting** (y opcionalmente https por IP si añades un nombre para la IP). El sitio sigue abierto a todo el mundo en 80 y 443.

### Requisitos

- DNS: **vantive.unclic.consulting** debe apuntar a la **IP pública actual** de la EC2 (A en Namecheap).
- En AWS Security Group de la EC2: **80** y **443** abiertos a **0.0.0.0/0** (y 22 para tu IP, ver §3).

### Pasos en la EC2

**Amazon Linux 2023** (usa `dnf`):

```bash
# 1. Instalar certbot
sudo dnf install -y certbot python3-certbot-nginx

# 2. Obtener certificado (Nginx en 80 y server_name correcto)
sudo certbot --nginx -d vantive.unclic.consulting

# 3. Seguir el asistente (email, aceptar términos). Certbot configurará 443 y redirección 80→443.

# 4. Renovación automática (en AL2023 el timer no se inicia por defecto)
sudo systemctl start certbot-renew.timer
sudo systemctl enable certbot-renew.timer
sudo certbot renew --dry-run
```

**Amazon Linux 2** (usa `yum`): `sudo yum install -y certbot python3-certbot-nginx` y luego los mismos pasos 2–4. Comprueba si el timer existe: `systemctl status certbot-renew.timer` (o `certbot.timer`).

Tras esto:

- **https://vantive.unclic.consulting** funcionará.
- **http://vantive.unclic.consulting** redirigirá a https.
- Sigue abierto a todo el mundo en 80 y 443.

---

## 3. Proteger el servidor (sin cerrar el sitio)

Objetivo: sitio público por 80/443, pero el servidor protegido frente a abusos y ataques.

### 3.1 Security Group (AWS)

En **EC2 → Security Groups** del grupo asociado a **fastflow-vantive**:

| Tipo        | Puerto | Origen        | Comentario              |
|------------|--------|---------------|-------------------------|
| HTTP       | 80     | 0.0.0.0/0     | Sitio abierto al mundo  |
| HTTPS      | 443    | 0.0.0.0/0     | Sitio abierto al mundo  |
| SSH        | 22     | **Tu IP**/32  | Solo tú (o VPN/oficina) |

- No abras 22 a 0.0.0.0/0. Así evitas la mayoría de escaneos y ataques por SSH.

### 3.2 Cabeceras de seguridad en Nginx (opcional)

En `/etc/nginx/conf.d/vantive.conf`, dentro del `server` que tenga `listen 443 ssl` (el que crea certbot), puedes añadir:

```nginx
add_header X-Frame-Options "SAMEORIGIN" always;
add_header X-Content-Type-Options "nosniff" always;
add_header X-XSS-Protection "1; mode=block" always;
add_header Referrer-Policy "strict-origin-when-cross-origin" always;
```

Luego:

```bash
sudo nginx -t && sudo systemctl reload nginx
```

### 3.3 Fail2ban para SSH (opcional)

Protege contra fuerza bruta en SSH:

```bash
# Amazon Linux 2023: dnf; Amazon Linux 2: yum
sudo dnf install -y fail2ban
sudo systemctl enable fail2ban
sudo systemctl start fail2ban
```

Configuración mínima en `/etc/fail2ban/jail.local`:

```ini
[sshd]
enabled = true
maxretry = 3
bantime = 3600
```

Reiniciar: `sudo systemctl restart fail2ban`.

### 3.4 Mantener actualizado

```bash
# Amazon Linux 2023
sudo dnf update -y
# Amazon Linux 2: sudo yum update -y
```

Hacerlo de forma periódica (o con un mantenimiento programado).

---

## 4. Si ves ModularStack en vez de Vantive

Si al abrir **https://vantive.unclic.consulting** aparece el sitio de ModularStack (Jenkins, “Un solo acceso”, etc.) en lugar del de Vantive (hemodiálisis, “ESTE SITIO ESTÁ DIRIGIDO EXCLUSIVAMENTE A PROFESIONALES DE LA SALUD EN MÉXICO”), es que en la EC2 está desplegado el contenido equivocado. Hay que **volver a desplegar el build de Vantive** en `/usr/share/nginx/html`.

**Desde tu máquina** (con Node 18+ y la clave `.pem` de la EC2):

```bash
# La clave .pem debe tener permisos restrictivos o SSH la rechazará
chmod 600 /ruta/a/gitea-key.pem

# Ir al proyecto Vantive (Next.js)
cd toolkit-fastflow/integrations/kings-joers/vantive

# Build estático (genera out/)
npm ci --no-audit
npm run build

# Desplegar a la EC2 (sustituir por tu IP y ruta a la key)
export VANTIVE_IP=3.22.236.150
export VANTIVE_KEY=/ruta/a/gitea-key.pem

rsync -avz --delete -e "ssh -i $VANTIVE_KEY -o StrictHostKeyChecking=no" out/ ec2-user@$VANTIVE_IP:/tmp/vantive-deploy/
ssh -i $VANTIVE_KEY -o StrictHostKeyChecking=no ec2-user@$VANTIVE_IP "sudo rsync -av --delete /tmp/vantive-deploy/ /usr/share/nginx/html/ && sudo chown -R nginx:nginx /usr/share/nginx/html/"
```

O usar el script (desde `toolkit-fastflow/integrations/web-cuantica`):

```bash
VANTIVE_IP=3.22.236.150 VANTIVE_KEY=/ruta/a/gitea-key.pem ./scripts/deploy-vantive-ec2.sh
```

Tras el deploy, recarga **https://vantive.unclic.consulting**; deberías ver el sitio Vantive (hemodiálisis, AK 98, etc.).

---

## 4.1 Si ves ModularStack Y “Your connection isn’t private” (ERR_CERT_AUTHORITY_INVALID)

Si al abrir **https://vantive.unclic.consulting** ves el sitio de **ModularStack** y el navegador avisa de que la conexión no es segura, **no estás llegando al servidor Vantive** (3.22.236.150). Estás entrando en otro servidor (donde está ModularStack) y ese tiene otro certificado o uno inválido.

**Qué hacer:**

1. **Comprobar a qué IP apunta el dominio** (en tu Mac):
   ```bash
   dig +short vantive.unclic.consulting
   # o
   nslookup vantive.unclic.consulting
   ```
   Debe salir **3.22.236.150**. Si sale otra IP, el DNS está mal.

2. **Corregir DNS en Namecheap**
   - Entra en Namecheap → Dominio **unclic.consulting** → **Advanced DNS**.
   - Busca el registro **A** con **Host** = `vantive`.
   - El **Value** debe ser **3.22.236.150** (la IP de la EC2 fastflow-vantive).
   - Si pone otra IP, cámbiala a 3.22.236.150 y guarda.
   - Espera unos minutos (hasta 30) a que se propague.

3. **Limpiar caché**
   - **DNS en Mac:** `sudo dscacheutil -flushcache; sudo killall -HUP mDNSResponder`
   - En el navegador: prueba en **ventana de incógnito** o borra caché del sitio.
   - Vuelve a abrir **https://vantive.unclic.consulting** (usa el dominio, no la IP).

4. **Comprobar que el servidor correcto responde**
   - Abre **http://3.22.236.150** en el navegador. Deberías ver el sitio **Vantive** (hemodiálisis). Si en esa IP ves ModularStack, entonces en la EC2 sigue el contenido antiguo y hay que volver a desplegar (§4).
   - Si en 3.22.236.150 ves Vantive pero con el dominio sigues viendo ModularStack, el problema es solo DNS/caché: repasa el paso 1–3.

Cuando el dominio resuelva a 3.22.236.150, **https://vantive.unclic.consulting** usará el certificado Let’s Encrypt de esa EC2 y dejará de mostrar “connection isn’t private”; además se servirá el sitio Vantive.

**Si http://3.22.236.150 da 404** pero en el servidor el contenido de `/usr/share/nginx/html` es el correcto (Vantive), Certbot ha dejado `return 404` en el bloque de puerto 80. En la EC2 edita `/etc/nginx/conf.d/vantive.conf` y sustituye el segundo bloque `server` (el de `listen 80`) por uno que redirija solo el dominio a HTTPS y sirva el sitio cuando se accede por IP: `listen 80` con `root /usr/share/nginx/html`, `index index.html`, `location / { try_files $uri $uri/ /index.html; }` y `if ($host = vantive.unclic.consulting) { return 301 https://$host$request_uri; }`. Quita el `return 404`. Luego `sudo nginx -t && sudo systemctl reload nginx`.

---

## 5. Resumen

| Objetivo              | Acción                                                                 |
|-----------------------|------------------------------------------------------------------------|
| Quitar Forbidden      | Revisar `vantive.conf`, permisos de `/usr/share/nginx/html`, recargar Nginx. |
| HTTPS abierto al mundo| Certbot con `--nginx -d vantive.unclic.consulting`; puertos 80 y 443 desde 0.0.0.0/0. |
| Proteger contra ataques| SSH solo desde tu IP; opcional: cabeceras en Nginx, fail2ban, `dnf update`. |
| Ver Vantive y no ModularStack | Desplegar el build de `kings-joers/vantive` (out/) a la EC2 en `/usr/share/nginx/html` (ver §4). |
| ModularStack + certificado inválido | El dominio no apunta al servidor Vantive. Comprobar DNS (vantive → 3.22.236.150), limpiar caché, usar dominio (no IP); ver §4.1. |

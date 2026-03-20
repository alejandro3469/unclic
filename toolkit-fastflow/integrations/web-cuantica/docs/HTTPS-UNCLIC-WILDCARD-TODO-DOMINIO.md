# HTTPS para todo unclic.consulting: un certificado para dominio y subdominios

**Decisión:** Un solo certificado **Let's Encrypt** que cubra **unclic.consulting** y **\*.unclic.consulting** (wildcard). Sirve para todo lo que levantes bajo el dominio: Jenkins, Gitea, pos-online, landing, webcuantica y cualquier app futura en AWS (free tier) o detrás de Gitea. **Gratis** y válido para todo tipo de aplicaciones.

---

## Por qué esta opción

| Qué | Cómo |
|-----|------|
| **Todo el dominio** | Un cert con `unclic.consulting` + `*.unclic.consulting` (apex + wildcard). |
| **Todos los subdominios** | jenkins, gitea, pos, www, webcuantica, landing, fastflow, etc., sin pedir un cert por cada uno. |
| **Todas las apps** | Da igual si es Nginx, Jenkins, Gitea, Spring Boot, Node: Nginx en cada EC2 usa el mismo cert. |
| **Gratis y “una vez”** | Let's Encrypt + renovación automática (con reto DNS). Un solo proceso de renovación para todo. |

No compras SSL; no repites certificados por servicio. Una configuración que escala a todo lo que añadas bajo unclic.

---

## Cómo funciona en la práctica

1. **Obtienes el certificado una vez** en **una** máquina (por ejemplo la EC2 de Jenkins) usando **validación DNS** (Let's Encrypt comprueba que controlas el dominio con un registro TXT en Namecheap). No hace falta que cada EC2 tenga el puerto 80 abierto para el reto HTTP.
2. **Copias** los archivos del cert (`fullchain.pem`, `privkey.pem`) a **cada** EC2 donde Nginx sirva HTTPS (Jenkins, Gitea, la futura EC2 de pos, la de landing, etc.). En cada una, Nginx apunta a esos archivos.
3. **Renovación:** En la máquina donde corre Certbot, un cron ejecuta `certbot renew`. Tras renovar, un script puede copiar de nuevo el cert a las demás EC2s y recargar Nginx en cada una. Así un solo certificado sirve para todo el dominio y todos los subdominios.

---

## Empezar ya: qué hacer y cuándo tocar Namecheap

**No agregues nada en Namecheap al inicio.** Primero corres Certbot en la EC2; Certbot se pausará y te dirá exactamente qué registro TXT crear. **Ahí** entras al dashboard de Namecheap y lo agregas.

### Orden de pasos

| Paso | Dónde | Qué hacer |
|------|--------|-----------|
| 1 | **EC2 (Jenkins)** | Conectarte por SSH y ejecutar el comando Certbot (abajo). |
| 2 | **Terminal** | Certbot se pausa y muestra algo como: "Please deploy a DNS TXT record under the name _acme-challenge.unclic.consulting with the following value: xYz123AbC..." |
| 3 | **Namecheap** | Entras a **Domain List** → **unclic.consulting** → **Manage** → **Advanced DNS** y agregas el TXT que Certbot indicó. |
| 4 | **Terminal** | Esperas 1–2 min y en la terminal pulsas **Enter**. Certbot comprobará el TXT y generará el certificado. |
| 5 | (Si pide un 2.º TXT) | Repites 3 y 4 con el segundo nombre/valor que Certbot muestre. |

### Qué agregar en el dashboard de Namecheap (cuando Certbot lo pida)

1. Entra a **Namecheap** → **Domain List** → clic en **Manage** del dominio **unclic.consulting**.
2. Abre la pestaña **Advanced DNS**.
3. Clic en **ADD NEW RECORD**.
4. Elige **TXT Record**.
5. Rellena:
   - **Type:** TXT Record (ya elegido).
   - **Host:** el nombre que Certbot te muestre **sin** el dominio. Por ejemplo, si Certbot dice `_acme-challenge.unclic.consulting`, en Host pones solo **`_acme-challenge`**. Si dice `_acme-challenge`, dejas **`_acme-challenge`**.
   - **Value:** el valor largo que Certbot muestre (copia y pega tal cual, entre comillas o sin comillas según lo que Certbot indique; a veces va entre comillas en la pantalla de Certbot, en Namecheap suele pegarse sin comillas).
   - **TTL:** 1 min o **Automatic**.
6. **Save** (icono de guardar / check verde).

Espera **2–5 minutos** para que el DNS propague (Namecheap a veces tarda). Vuelve a la terminal y pulsa **Enter**. Si Certbot pide **dos** TXT con el **mismo** nombre `_acme-challenge.unclic.consulting` pero **valores distintos**: en Namecheap crea **dos** registros TXT (ADD NEW RECORD dos veces), ambos con **Host** `_acme-challenge`, y en cada uno un **Value** distinto (el primero y el segundo que mostró Certbot). Guarda, espera 2–5 min, luego **Enter** para el primero y **Enter** para el segundo.

**Si falló (NXDOMAIN o "Challenge failed"):** el TXT no existía o no había propagado. Ejecuta de nuevo el mismo comando Certbot; dará **valores nuevos**. Añade en Namecheap **todos** los TXT que pida (Host `_acme-challenge`, cada Value), espera **2–5 min** y **solo entonces** pulsa Enter. Para comprobar: `dig TXT _acme-challenge.unclic.consulting +short` (cuando veas el valor, ya propagó).

**Resumen:** Crea **todos** los TXT que pida (si pide dos, dos registros con Host `_acme-challenge`). Espera 2–5 min antes de Enter. Si falla, repite el comando y **sustituye** en Namecheap los TXT por los **valores nuevos** que Certbot muestre (borra los antiguos o edita Value); luego espera y Enter.

**Comprobar que propagó:** `dig TXT _acme-challenge.unclic.consulting +short` (desde tu Mac o cualquier máquina). Debes ver el valor (o los dos) que acabas de poner. Cuando aparezcan, pulsa Enter en la terminal.

### ¿Dónde ejecuto Certbot: en Jenkins o en otro servidor "central"?

**Ejecútalo en la EC2 de Jenkins** (donde ya tienes Nginx y Certbot). No hace falta crear otra EC2 solo para el certificado. Esa máquina será el **origen** del cert: ahí se genera y se renueva; luego copias los archivos a Gitea, pos-online, etc. Jenkins no pasa a ser "la app central" del dominio; solo es el sitio donde corre Certbot por comodidad. Si más adelante quieres un servidor "ops" dedicado, puedes mover Certbot allí; por ahora usar Jenkins es lo más simple y no gasta otro recurso del free tier.

### Comando con el que empezar (en la EC2 de Jenkins)

Cuando estés conectado por SSH a la EC2 donde tienes Certbot instalado (por ejemplo la de Jenkins):

```bash
sudo certbot certonly --manual --preferred-challenges dns -d unclic.consulting -d "*.unclic.consulting"
```

Certbot pedirá un email (para avisos) y aceptar términos. Luego mostrará el primer TXT → **ahí** vas a Namecheap y lo agregas como arriba → esperas → Enter. Si pide un segundo TXT, repites en Namecheap y Enter de nuevo. Al terminar, el certificado quedará en `/etc/letsencrypt/live/unclic.consulting/`.

---

## Requisitos

- **Namecheap:** Dominio unclic.consulting con acceso a **Advanced DNS** (para crear un registro TXT cuando Certbot lo pida, o para usar API si automatizas).
- **Una EC2** (p. ej. la de Jenkins) con Certbot y, si quieres automatizar todo, el plugin **certbot-dns-namecheap** o **certbot-dns-route53** (o hacer el reto TXT a mano cada 90 días).
- **Cada** EC2 que exponga HTTPS: Nginx (o otro proxy) con `ssl_certificate` y `ssl_certificate_key` apuntando a la copia del mismo cert.

---

## Paso 1 — Obtener el certificado wildcard + apex (una vez)

En la EC2 que uses como “origen” del cert (ej. Jenkins), con Certbot instalado.

**Opción A — Reto DNS manual (sin API):**

```bash
sudo certbot certonly --manual --preferred-challenges dns -d unclic.consulting -d "*.unclic.consulting"
```

Certbot te pedirá que crees un registro **TXT** en el DNS. En Namecheap:

- **Advanced DNS** → **Add New Record** → **TXT Record**.
- **Host:** `_acme-challenge` (para el apex) o el que Certbot indique (para wildcard suele ser `_acme-challenge` con subdominio).
- **Value:** el valor que Certbot muestre en pantalla.
- **TTL:** 1 min (o Automatic). Save.

Espera 1–2 minutos y en la terminal de la EC2 pulsa Enter para que Certbot verifique. Si pide un segundo TXT (para el wildcard), repite en Namecheap con el segundo nombre/valor que indique. Al terminar, el cert quedará en:

- `/etc/letsencrypt/live/unclic.consulting/fullchain.pem`
- `/etc/letsencrypt/live/unclic.consulting/privkey.pem`

**Opción B — Automatizado con API de Namecheap (avanzado):**

Si Namecheap te da API (Account → Domain List → Manage → API Access), puedes instalar el plugin y que Certbot cree el TXT solo:

```bash
sudo pip3 install certbot-dns-namecheap   # o el plugin que Namecheap recomiende
```

Crear un archivo de credenciales (proteger permisos) y usar:

```bash
sudo certbot certonly --dns-namecheap --dns-namecheap-credentials /etc/letsencrypt/namecheap.ini -d unclic.consulting -d "*.unclic.consulting"
```

(Consulta la doc del plugin para el formato de `namecheap.ini`.)

---

## Paso 2 — Usar el cert en esta EC2 (Nginx)

**1. Comprobar que el certificado existe** (en la EC2 de Jenkins):

```bash
sudo ls -la /etc/letsencrypt/live/unclic.consulting/
```

Deberías ver `fullchain.pem` y `privkey.pem`.

**2. Crear o reemplazar la config de Nginx** (Amazon Linux 2: `conf.d`):

```bash
sudo nano /etc/nginx/conf.d/jenkins.conf
```

Pega esta config **completa** (redirige HTTP→HTTPS y sirve HTTPS con el wildcard):

```nginx
server {
    listen 80;
    server_name jenkins.unclic.consulting;
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl;
    server_name jenkins.unclic.consulting;
    ssl_certificate /etc/letsencrypt/live/unclic.consulting/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/unclic.consulting/privkey.pem;
    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Guarda (Ctrl+O, Enter, Ctrl+X).

**3. Comprobar y recargar Nginx:**

```bash
sudo nginx -t
sudo systemctl reload nginx
```

**4. Probar:** Abre **https://jenkins.unclic.consulting** en el navegador (candado, sin :8080).

**5. En Jenkins:** Manage Jenkins → System → **Jenkins URL:** `https://jenkins.unclic.consulting/` → Save.

---

## Paso 3 — Llevar el mismo cert a las demás EC2s (Gitea, pos, landing, etc.)

En cada **otra** EC2 (Gitea, pos-online, landing…) no tienes Certbot; solo necesitas **una copia** del cert.

**Desde la EC2 donde está el cert** (ej. Jenkins), copiar a la otra EC2 (sustituye IP y usuario/key):

```bash
sudo scp /etc/letsencrypt/live/unclic.consulting/fullchain.pem ec2-user@18.223.114.68:/tmp/
sudo scp /etc/letsencrypt/live/unclic.consulting/privkey.pem ec2-user@18.223.114.68:/tmp/
```

En la **EC2 de Gitea** (18.223.114.68), crear un directorio y mover los archivos:

```bash
sudo mkdir -p /etc/letsencrypt/unclic-wildcard
sudo mv /tmp/fullchain.pem /tmp/privkey.pem /etc/letsencrypt/unclic-wildcard/
sudo chmod 644 /etc/letsencrypt/unclic-wildcard/fullchain.pem
sudo chmod 600 /etc/letsencrypt/unclic-wildcard/privkey.pem
```

En Nginx de Gitea, usar esos paths:

```nginx
ssl_certificate /etc/letsencrypt/unclic-wildcard/fullchain.pem;
ssl_certificate_key /etc/letsencrypt/unclic-wildcard/privkey.pem;
```

Lo mismo para **cualquier** nueva EC2 (pos.unclic.consulting, www / unclic.consulting para landing, etc.): copias los dos `.pem` a esa EC2 y en Nginx apuntas a ellos. **Un mismo certificado para todo el dominio y todos los subdominios.**

---

## Paso 4 — Renovación automática “una vez por todas”

El cert caduca a los 90 días. Solo se renueva en **la EC2 donde corre Certbot** (la que tiene el cert “maestro”).

**Si usaste reto manual (Opción A):**  
Cada ~90 días, en esa EC2:

```bash
sudo certbot renew --manual --preferred-challenges dns
```

Vuelves a crear el TXT que pida en Namecheap, esperas, Enter. Luego **vuelves a copiar** fullchain.pem y privkey.pem a las demás EC2s y recargas Nginx en cada una (o usas un script que haga scp + ssh systemctl reload nginx).

**Si automatizaste con API (Opción B):**  
En la misma EC2:

```bash
echo '0 0,12 * * * root certbot renew -q --deploy-hook "/usr/local/bin/push-certs-to-ec2s.sh"' | sudo tee /etc/cron.d/certbot-wildcard
```

El script `push-certs-to-ec2s.sh` haría: copiar fullchain.pem y privkey.pem a cada otra EC2 y ejecutar `sudo systemctl reload nginx` allí. Así todo el dominio y todos los subdominios se renuevan con un solo cert, gratis y de forma repetible.

---

## Resumen

| Pregunta | Respuesta |
|----------|-----------|
| ¿Qué certificado usamos? | **Let's Encrypt**: un solo cert para **unclic.consulting** + **\*.unclic.consulting**. |
| ¿Vale para todo? | Sí: dominio, todos los subdominios y cualquier app (Jenkins, Gitea, pos, landing, etc.) bajo unclic en AWS/Gitea. |
| ¿Cuánto cuesta? | Nada. Let's Encrypt es gratis. |
| ¿Dónde se genera? | En una sola EC2 (ej. Jenkins) con validación DNS. |
| ¿Cómo llega a las demás EC2s? | Copiando `fullchain.pem` y `privkey.pem` a cada una; Nginx en cada servidor usa esos archivos. |
| ¿Renovación? | En la EC2 “maestra”: `certbot renew` (manual con TXT o automático con API); luego volver a copiar el cert a las demás EC2s y recargar Nginx. |

Esta es la estrategia que **funciona para todo el dominio, todos los subdominios y todo tipo de aplicaciones** que levantes bajo unclic.consulting con AWS gratis y Gitea.

---

## Registro de lo ya ejecutado (marzo 2026)

Comandos reales, salidas de Certbot/Nginx/Jenkins y lo pendiente (Gitea HTTPS, renovación, etc.): **[REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md)**.

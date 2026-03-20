# Paso 1: Guía HTTPS desde cero (sin comprar SSL)

**No compres SSL en Namecheap.** Usamos **Let's Encrypt**, **gratis**.

**Decisión para todo el dominio y todos los subdominios:** Un solo certificado **wildcard + apex** (unclic.consulting + \*.unclic.consulting) que vale para Jenkins, Gitea, pos-online, landing, webcuantica y cualquier app que levantes bajo unclic en AWS (free tier) y Gitea. Guía completa: **[HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md)**.

Esta guía (abajo) sirve como **arranque rápido por subdominio** (cert por servicio con reto HTTP/standalone). Si quieres **un cert para todo**, sigue el doc del wildcard.

---

## Orden de lo que hay que hacer (resumen)

| Paso | Qué hacer | Dónde |
|------|-----------|--------|
| **0** | No comprar SSL | Namecheap: no hace falta ningún producto de pago |
| **1** | Comprobar que el DNS ya apunta a tu EC2 | Namecheap → Advanced DNS (ya tienes A: jenkins → 3.15.4.160, gitea → 18.223.114.68) |
| **2** | Abrir puerto **80** en la EC2 (para Let's Encrypt) | AWS → Security Group de la EC2 de Jenkins |
| **3** | Conectarte a la EC2 de Jenkins | AWS → Connect (SSH) |
| **4** | Instalar Nginx y Certbot en la EC2 | Comandos en la terminal de la EC2 |
| **5** | Configurar Nginx para jenkins.unclic.consulting (puerto 80) | Archivo en la EC2 |
| **6** | Pedir el certificado gratis con Certbot | Un comando en la EC2 |
| **7** | Decirle a Jenkins que su URL es https | En el navegador, Manage Jenkins → System |

Después de esto tendrás **https://jenkins.unclic.consulting** (candado, sin :8080). Lo mismo se hace luego para Gitea en su EC2.

---

## ¿Qué certificado conviene? ¿Y “una vez por todas”?

| Opción | Ventaja | Desventaja |
|--------|--------|------------|
| **Let's Encrypt (gratis)** | Gratis, confiable, aceptado por todos los navegadores. Con **renovación automática** (abajo) lo configuras una vez y se renueva solo cada ~90 días. | Caduca cada 90 días (por eso se automatiza la renovación). |
| **Comprar SSL en Namecheap** | Un certificado de 1–2 años: lo instalas una vez y no tocas nada hasta que caduque. | Tienes que pagar y acordarte de renovar o comprar de nuevo al expirar. |
| **Wildcard Let's Encrypt** (*.unclic.consulting) | Un solo certificado para todos los subdominios (jenkins, gitea, pos, www…). Una renovación para todo. | Requiere validación por DNS (TXT en Namecheap o API); la primera vez es un poco más de trabajo. |

**Recomendación:** **Let's Encrypt + renovación automática** es la opción que más conviene: gratis, “una vez por todas” (configuras Certbot + cron/hook y no vuelves a preocuparte). Si más adelante quieres un solo cert para todos los subdominios, puedes pasar a **wildcard** con reto DNS. **Para todo el dominio:** usa el cert **wildcard + apex** ([HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md)). Comprar SSL solo si quieres no tocar nada 1–2 años y te parece bien pagar.

**“Una vez por todas”** = después de tener el certificado, configurar la **renovación automática** (ver sección final de esta guía). Así el certificado se renueva solo y no caduca.

---

## Paso 0 — ¿Compro SSL en Namecheap?

**No.** No compres certificados en Namecheap para esto. **Let's Encrypt** da certificados **gratis** y Certbot los instala en tu servidor. Solo necesitas que el **dominio** (unclic.consulting) esté en Namecheap y que los registros **A** de **jenkins** y **gitea** apunten a las IP de tus EC2. Eso ya lo tienes.

---

## Paso 1 — Comprobar DNS (Namecheap)

1. Entra a **Namecheap** → **Domain List** → **unclic.consulting** → **Manage** → pestaña **Advanced DNS**.
2. En **HOST RECORDS** debe haber algo así:

   | Type | Host | Value |
   |------|------|--------|
   | A    | jenkins | 3.15.4.160 |
   | A    | gitea   | 18.223.114.68 |

3. Si **jenkins** no está o tiene otra IP, **edita** el registro y pon **Value** = IP pública de tu EC2 de Jenkins (3.15.4.160). **Save**.
4. No hace falta tocar MX, TXT ni comprar nada más. Con esto Let's Encrypt podrá comprobar que el dominio es tuyo.

---

## Paso 2 — Abrir puerto 80 en la EC2 de Jenkins (AWS)

Let's Encrypt necesita que tu servidor responda en el **puerto 80** (HTTP) para validar el dominio. Si el 80 está cerrado, Certbot fallará.

1. **AWS Console** → **EC2** → **Security Groups** (menú izquierdo).
2. Busca el security group de Jenkins (ej. **fastflow-jenkins-sg**) y selecciónalo.
3. **Edit inbound rules** → **Add rule**:
   - **Type:** HTTP
   - **Port:** 80
   - **Source:** 0.0.0.0/0 (o "Anywhere-IPv4")
4. **Save rules**.

Comprueba que también tengas regla para **22** (SSH) y **8080** (por si quieres seguir entrando por HTTP un rato). Para uso normal después solo necesitarás 22 y 80/443.

---

## Paso 3 — Conectarte a la EC2 de Jenkins

1. **AWS Console** → **EC2** → **Instances**.
2. Selecciona la instancia **fastflow-jenkins-controller** (IP 3.15.4.160).
3. Botón **Connect** → pestaña **EC2 Instance Connect** → **Connect**.
4. Se abre una terminal en el navegador. Ahí ejecutarás los comandos de los pasos 4, 5 y 6.

---

## Paso 4 — Instalar Nginx y Certbot en la EC2 (Jenkins)

En la **misma terminal** de la EC2 (paso 3), ejecuta los comandos según el sistema de la instancia.

**Si tu EC2 es Amazon Linux 2** (lo más probable en AWS):

```bash
# Nginx: en AL2 va por "extras", no por yum directo
sudo amazon-linux-extras install nginx1 -y

# Certbot: EPEL primero, luego certbot
sudo amazon-linux-extras install epel -y
sudo yum install -y certbot python3-certbot-nginx

# Si yum dice "No package certbot available", instalar Certbot por snap:
# sudo yum install -y snapd && sudo systemctl enable --now snapd.socket
# sleep 10 && sudo snap install --classic certbot && sudo ln -sf /snap/bin/certbot /usr/bin/certbot
```

**Si tu EC2 es Ubuntu**:

```bash
sudo apt update
sudo apt install -y nginx certbot python3-certbot-nginx
```

Luego arranca Nginx y habilítalo al inicio:

```bash
sudo systemctl start nginx
sudo systemctl enable nginx
```

Comprueba: `curl -I http://localhost` debe devolver "200 OK" o similar.

---

## Paso 5 — Configurar Nginx para jenkins.unclic.consulting (puerto 80)

En la EC2, crea el archivo de sitio para Jenkins.

**En Amazon Linux 2** la configuración suele estar en `/etc/nginx/conf.d/` (no en sites-available). Usa:

```bash
sudo nano /etc/nginx/conf.d/jenkins.conf
```

Pega esto (y guarda: Ctrl+O, Enter, Ctrl+X):

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

**En Ubuntu** (si usas sites-available):

```bash
sudo nano /etc/nginx/sites-available/jenkins
```

Mismo contenido de arriba. Luego:

```bash
sudo ln -s /etc/nginx/sites-available/jenkins /etc/nginx/sites-enabled/
```

**Comprobar y recargar Nginx (cualquier SO):**

```bash
sudo nginx -t
sudo systemctl reload nginx
```

Si `nginx -t` dice "syntax is ok", sigue al paso 6.

---

## Paso 6 — Pedir el certificado gratis (Let's Encrypt)

Sigue en la **terminal de la EC2**:

```bash
sudo certbot --nginx -d jenkins.unclic.consulting
```

- La primera vez te pedirá un **email** (para avisos de renovación). Usa alejandro@unclic.consulting o el que quieras.
- Acepta los **términos** (A).
- Cuando pregunte si quieres **redirigir HTTP a HTTPS**, elige **Yes (2)**.

Certbot modificará la config de Nginx para escuchar en **443** y usar el certificado. Al terminar deberías ver un mensaje tipo "Successfully received certificate".

**Probar desde tu navegador:** abre **https://jenkins.unclic.consulting** (con **s**, sin :8080). Debe cargar con candado. Si desde otro dispositivo antes veías ERR_SSL_PROTOCOL_ERROR, ahora debe verse bien.

---

## Paso 7 — Decirle a Jenkins que su URL es HTTPS

1. Entra a **https://jenkins.unclic.consulting** (ya con candado).
2. **Manage Jenkins** → **System** (o "Configure System").
3. En **Jenkins URL** pon: `https://jenkins.unclic.consulting/`
4. **Save**.

Listo. Jenkins usará esa URL en enlaces y notificaciones.

---

## Una vez por todas: renovación automática del certificado

Let's Encrypt caduca a los **90 días**. Con renovación automática no vuelves a tocar nada.

**1. Hooks para que Nginx no bloquee el puerto 80 al renovar (si usaste `certonly --standalone`):**

Certbot necesita el puerto 80 libre al renovar. Crea un hook **pre** (parar Nginx) y **post** (arrancar Nginx):

```bash
sudo mkdir -p /etc/letsencrypt/renewal-hooks/pre /etc/letsencrypt/renewal-hooks/post
sudo nano /etc/letsencrypt/renewal-hooks/pre/stop-nginx.sh
```

Contenido (guarda con Ctrl+O, Enter, Ctrl+X):

```bash
#!/bin/bash
systemctl stop nginx
```

```bash
sudo nano /etc/letsencrypt/renewal-hooks/post/start-nginx.sh
```

Contenido:

```bash
#!/bin/bash
systemctl start nginx
```

Guarda.

```bash
sudo chmod +x /etc/letsencrypt/renewal-hooks/pre/stop-nginx.sh
sudo chmod +x /etc/letsencrypt/renewal-hooks/post/start-nginx.sh
```

**2. Cron para que Certbot renueve solo (dos veces al día; solo renueva si falta poco para caducar):**

```bash
echo '0 0,12 * * * root certbot renew -q' | sudo tee /etc/cron.d/certbot
```

**3. Probar que la renovación funciona:**

```bash
sudo certbot renew --dry-run
```

Si termina sin error, el certificado se renovará solo y tendrás HTTPS "una vez por todas".

---

## Resumen: qué hiciste y qué no

- **No compraste SSL** en Namecheap.
- **Sí** comprobaste DNS (A record jenkins → IP de la EC2).
- **Sí** abriste el puerto 80 en el Security Group.
- **Sí** instalaste Nginx y Certbot en la EC2 de Jenkins.
- **Sí** configuraste Nginx para hacer proxy a Jenkins (8080) y pediste el certificado con Certbot (Let's Encrypt gratis).
- **Sí** actualizaste la URL en Jenkins a https.

**Siguiente:** repetir el mismo esquema en la EC2 de **Gitea** (18.223.114.68): Nginx en 80 → Certbot para **gitea.unclic.consulting** → en Gitea poner ROOT URL = `https://gitea.unclic.consulting/`. Los pasos detallados están en [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md) § 3.1 (si Gitea es Ubuntu) o adaptando con `/etc/nginx/conf.d/gitea.conf` si es Amazon Linux.

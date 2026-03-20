# HTTPS wildcard unclic.consulting — Lo que se hizo y lo pendiente

Documentación de lo **realmente ejecutado**, salidas obtenidas y **pendiente**.

---

## 1. Lo que ya se hizo

### 1.1 Certificado wildcard Let's Encrypt (unclic.consulting + *.unclic.consulting)

**Dónde:** EC2 de Jenkins (Amazon Linux 2), por SSH.

**Comando ejecutado:**
```bash
sudo certbot certonly --manual --preferred-challenges dns -d unclic.consulting -d "*.unclic.consulting"
```

**Pasos realizados:**
- Email: `alejandro@unclic.consulting`
- Términos: Yes
- EFF mailing: Yes
- Certbot pidió **dos** registros TXT con el mismo nombre `_acme-challenge.unclic.consulting` y valores distintos.

**En Namecheap (Advanced DNS → unclic.consulting):**
- Se añadieron dos **TXT Record** con Host `_acme-challenge` y los valores que Certbot mostró en esa ejecución (en un intento anterior falló por NXDOMAIN por no esperar propagación).
- Valores que funcionaron en la ejecución exitosa (ejemplo; cada ejecución genera otros): primer valor `lMXe8Raq2-eYZCZHIAwZdezcGgAhy1L5KWTjPM2lA4s`, segundo `bZlhGcGIlbQp0aZrUKFTlFcL8iLmm9n5dEn7Mj6Z7zM`.

**Salida real obtenida:**
```
Waiting for verification...
Cleaning up challenges
Subscribe to the EFF mailing list (email: alejandro@unclic.consulting).

IMPORTANT NOTES:
 - Congratulations! Your certificate and chain have been saved at:
   /etc/letsencrypt/live/unclic.consulting/fullchain.pem
   Your key file has been saved at:
   /etc/letsencrypt/live/unclic.consulting/privkey.pem
   Your certificate will expire on 2026-06-11. To obtain a new or
   tweaked version of this certificate in the future, simply run
   certbot again. To non-interactively renew *all* of your
   certificates, run "certbot renew"
```

**Resumen:** Certificado wildcard instalado en la EC2 de Jenkins; caduca **2026-06-11**.

---

### 1.2 Nginx en la EC2 de Jenkins (HTTPS + redirect HTTP→HTTPS)

**Archivo creado/editado:** `/etc/nginx/conf.d/jenkins.conf`

**Contenido aplicado:**
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

**Comandos ejecutados:**
```bash
sudo nginx -t
sudo systemctl reload nginx
```

**Salida real:**
```
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
```

---

### 1.3 Puerto 443 en el Security Group de Jenkins (AWS)

**Dónde:** AWS Console → EC2 → Security Groups → **fastflow-jenkins-sg** (sg-0e030608601ceb35f).

**Regla añadida:** Inbound → **HTTPS**, puerto **443**, origen **0.0.0.0/0**.

**Reglas inbound actuales:** SSH (22), HTTP (80), HTTPS (443), Custom TCP (8080), Custom TCP (8111).

---

### 1.4 URL de Jenkins (https://jenkins.unclic.consulting/) vía archivo XML

Al guardar desde la UI (Manage Jenkins → System → Jenkins URL) aparecía **HTTP 403 No valid crumb**. Se configuró la URL por archivo en la EC2:

**Comandos ejecutados:**
```bash
sudo tee /var/lib/jenkins/jenkins.model.JenkinsLocationConfiguration.xml << 'EOF'
<?xml version='1.1' encoding='UTF-8'?>
<jenkins.model.JenkinsLocationConfiguration>
  <jenkinsUrl>https://jenkins.unclic.consulting/</jenkinsUrl>
  <adminAddress>alejandro@unclic.consulting</adminAddress>
</jenkins.model.JenkinsLocationConfiguration>
EOF
sudo chown jenkins:jenkins /var/lib/jenkins/jenkins.model.JenkinsLocationConfiguration.xml
sudo systemctl restart jenkins
```

**Salida:** El `tee` imprimió el XML; `chown` y `restart` sin error. Jenkins arrancó con la nueva URL.

---

## 2. Estado actual

| Elemento | Estado |
|----------|--------|
| Certificado wildcard | Emitido; en `/etc/letsencrypt/live/unclic.consulting/`; caduca 2026-06-11 |
| Nginx Jenkins (HTTPS) | Activo; redirect 80→443; proxy a 127.0.0.1:8080 |
| Security group 443 | Abierto en fastflow-jenkins-sg |
| Jenkins URL | https://jenkins.unclic.consulting/ (configurado por XML) |
| Acceso | https://jenkins.unclic.consulting operativo con candado |

---

## 3. Pendiente

### 3.1 Gitea (gitea.unclic.consulting) con el mismo certificado

- **Desde la EC2 de Jenkins** copiar el cert a la EC2 de Gitea (18.223.114.68):
  ```bash
  sudo scp /etc/letsencrypt/live/unclic.consulting/fullchain.pem ec2-user@18.223.114.68:/tmp/
  sudo scp /etc/letsencrypt/live/unclic.consulting/privkey.pem ec2-user@18.223.114.68:/tmp/
  ```
- **En la EC2 de Gitea:** instalar Nginx (si no está), crear directorio para el cert, mover los `.pem`, configurar Nginx para `gitea.unclic.consulting` en 443 con `ssl_certificate` / `ssl_certificate_key` apuntando a esos archivos, abrir puerto 443 en el security group de Gitea, recargar Nginx.
- Detalle completo en **HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md** (Paso 3).

### 3.2 Renovación del certificado (~90 días; antes de 2026-06-11)

- En la EC2 de Jenkins: `sudo certbot renew --manual --preferred-challenges dns` (o el comando que Certbot indique para renovar).
- Certbot pedirá de nuevo registros TXT en Namecheap; crearlos, esperar propagación, continuar.
- Tras renovar, **volver a copiar** `fullchain.pem` y `privkey.pem` a la EC2 de Gitea (y a cualquier otra que use este cert) y recargar Nginx en cada una.
- Opcional: cron o script para renovar y desplegar el cert a las demás EC2s (doc wildcard, Paso 4).

### 3.3 Opcional

- **Eliminar** en Namecheap los TXT `_acme-challenge` una vez obtenido el cert (o dejarlos; en la próxima renovación se usarán valores nuevos).
- **Otras EC2** (pos-online, landing unclic): cuando existan, usar el mismo certificado (copiar los dos `.pem` y configurar Nginx en 443) según **HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md**.

---

## 4. Referencias

- **Guía completa wildcard:** `HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md`
- **Guía paso a paso (un cert por subdominio):** `PASO-1-GUIA-HTTPS-DESDE-CERO.md`
- **Infra y DNS:** `INFRAESTRUCTURA-UNCLIC-ACTUAL.md`

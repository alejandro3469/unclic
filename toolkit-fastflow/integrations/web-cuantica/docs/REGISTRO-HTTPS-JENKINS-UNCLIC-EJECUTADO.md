# Registro: HTTPS Jenkins unclic.consulting (ejecutado y pendiente)

Documentación de lo **ya hecho** (comandos, salidas reales, configuraciones) y de lo que **queda pendiente**. Fecha de ejecución: marzo 2026.

---

## 1. Resumen de lo ejecutado

| Paso | Qué se hizo | Dónde |
|------|-------------|--------|
| 1 | Certificado wildcard Let's Encrypt (unclic.consulting + \*.unclic.consulting) con reto DNS | EC2 Jenkins (3.15.4.160) |
| 2 | Registros TXT en Namecheap para _acme-challenge.unclic.consulting | Namecheap Advanced DNS |
| 3 | Nginx configurado para HTTPS (443) y redirect HTTP→HTTPS para jenkins.unclic.consulting | EC2 Jenkins, /etc/nginx/conf.d/jenkins.conf |
| 4 | Regla inbound HTTPS (443) en security group | AWS fastflow-jenkins-sg |
| 5 | Jenkins URL fijada a https://jenkins.unclic.consulting/ vía XML (evita 403 crumb) | /var/lib/jenkins/jenkins.model.JenkinsLocationConfiguration.xml |

**Resultado:** **https://jenkins.unclic.consulting** operativo con certificado válido hasta **2026-06-11**.

---

## 2. Comandos ejecutados y salidas reales

### 2.1 Certificado wildcard (Certbot, reto DNS manual)

**Comando:**

```bash
sudo certbot certonly --manual --preferred-challenges dns -d unclic.consulting -d "*.unclic.consulting"
```

**Interacción:** Email `alejandro@unclic.consulting`, aceptar términos (Y), compartir con EFF (Y). Certbot pidió **dos** registros TXT con el mismo nombre `_acme-challenge.unclic.consulting` y valores distintos.

**Valores TXT usados en Namecheap (esta ejecución):**

- `lMXe8Raq2-eYZCZHIAwZdezcGgAhy1L5KWTjPM2lA4s`
- `bZlhGcGIlbQp0aZrUKFTlFcL8iLmm9n5dEn7Mj6Z7zM`

**Salida al completar:**

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

---

### 2.2 Nginx: config para Jenkins HTTPS

**Archivo:** `/etc/nginx/conf.d/jenkins.conf`

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

**Comandos de comprobación y recarga:**

```bash
sudo nginx -t
# nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
# nginx: configuration file /etc/nginx/nginx.conf test is successful

sudo systemctl reload nginx
```

---

### 2.3 Jenkins: URL y admin por XML (evitar 403 crumb)

Al guardar desde la UI (Manage Jenkins → System) aparecía **HTTP 403 No valid crumb**. Se aplicó la URL y el admin por archivo:

**Comando:**

```bash
sudo tee /var/lib/jenkins/jenkins.model.JenkinsLocationConfiguration.xml << 'EOF'
<?xml version='1.1' encoding='UTF-8'?>
<jenkins.model.JenkinsLocationConfiguration>
  <jenkinsUrl>https://jenkins.unclic.consulting/</jenkinsUrl>
  <adminAddress>alejandro@unclic.consulting</adminAddress>
</jenkins.model.JenkinsLocationConfiguration>
EOF
```

**Salida:** El contenido XML se imprimió en la terminal (tee). Luego:

```bash
sudo chown jenkins:jenkins /var/lib/jenkins/jenkins.model.JenkinsLocationConfiguration.xml
sudo systemctl restart jenkins
```

Jenkins arrancó de nuevo y la URL quedó en **https://jenkins.unclic.consulting/**.

---

## 3. Namecheap (registros DNS usados)

En **Advanced DNS** de **unclic.consulting** se usaron:

| Type | Host | Value | TTL |
|------|------|--------|-----|
| TXT | _acme-challenge | lMXe8Raq2-eYZCZHIAwZdezcGgAhy1L5KWTjPM2lA4s | Automatic |
| TXT | _acme-challenge | bZlhGcGIlbQp0aZrUKFTlFcL8iLmm9n5dEn7Mj6Z7zM | Automatic |

Los A para **jenkins** (3.15.4.160) y **gitea** (18.223.114.68) ya existían. Los TXT de _acme-challenge pueden borrarse después de emitir el cert o dejarse hasta la próxima renovación.

---

## 4. AWS (security group)

**Security group:** `fastflow-jenkins-sg` (sg-0e030608601ceb35f)

**Inbound rules tras los cambios:**

| Type      | Port | Source     |
|-----------|------|------------|
| SSH       | 22   | 0.0.0.0/0  |
| HTTP      | 80   | 0.0.0.0/0  |
| HTTPS     | 443  | 0.0.0.0/0  |
| Custom TCP| 8080 | 0.0.0.0/0  |
| Custom TCP| 8111 | 0.0.0.0/0  |

La regla **HTTPS 443** se añadió para que Nginx pudiera servir HTTPS desde internet.

---

## 5. Lo que queda pendiente

| Pendiente | Descripción | Referencia |
|-----------|-------------|------------|
| **Gitea en HTTPS** | Copiar fullchain.pem y privkey.pem a la EC2 de Gitea (18.223.114.68), configurar Nginx allí con el mismo cert y server_name gitea.unclic.consulting, abrir 80/443 en su security group. | HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md Paso 3 |
| **Renovación del certificado** | Antes del 2026-06-11 ejecutar en la EC2 de Jenkins `sudo certbot renew --manual --preferred-challenges dns`, crear los TXT que pida en Namecheap, Enter; luego opcionalmente volver a copiar el cert a Gitea (y a otras EC2s que lo usen) y recargar Nginx. | Paso 4 del doc wildcard |
| **Otras EC2s (pos, landing, etc.)** | Cuando existan, copiar el mismo cert y configurar Nginx con server_name correspondiente (pos.unclic.consulting, unclic.consulting, www, etc.). | Paso 3 del doc wildcard |
| **Automatizar renovación** | Opcional: cron para `certbot renew` y script de deploy del cert a las demás EC2s; con reto manual habría que automatizar el TXT (p. ej. API Namecheap) o hacer el renew a mano cada ~90 días. | Paso 4 del doc wildcard |

---

## 6. Referencias

- **Guía wildcard (estrategia y pasos):** [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md)
- **Guía paso a paso por subdominio:** [PASO-1-GUIA-HTTPS-DESDE-CERO.md](PASO-1-GUIA-HTTPS-DESDE-CERO.md)
- **Infraestructura actual (IPs, DNS, security groups):** [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md)

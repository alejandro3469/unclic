# Vantive: push a Gitea, publicar en instancia, subdominio público y HTTPS

Pasos para: **push** al repo **alejandro-perez/vantive** en Gitea → **publicar** en una EC2 → **subdominio** (vantive.unclic.consulting) accesible en internet → **asegurar con HTTPS**.

---

## 1. Push al repo Gitea (vantive)

**Repo:** `http://gitea.unclic.consulting:3000/alejandro-perez/vantive.git`

Desde tu máquina, en la carpeta del código Vantive (toolkit):

```bash
cd /Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/kings-joers/vantive
git init
git checkout -b main
git add .
git commit -m "Vantive app + Jenkinsfile FastFlow (Kings & Joers)"
git remote add origin http://gitea.unclic.consulting:3000/alejandro-perez/vantive.git
git push -u origin main
```

Si Gitea te pide usuario/contraseña: usa tu usuario Gitea (alejandro-perez) y contraseña o **Access Token** (Gitea → Settings → Applications → Generate New Token).

Si el repo en Gitea ya tenía un README inicial:

```bash
git pull origin main --allow-unrelated-histories
# Resolver conflictos si los hay, luego:
git push -u origin main
```

---

## 2. Instancia (EC2) para Vantive

Crear una EC2 solo para servir Vantive (si no existe):

- **Nombre:** `fastflow-vantive`
- **AMI:** Amazon Linux 2, **t3.micro**
- **Security group:** SSH (22), **HTTP (80)**, **HTTPS (443)** from 0.0.0.0/0 (acceso público).
- **Key pair:** el que uses para SSH.

**Conectar** (EC2 → Connect → EC2 Instance Connect) e instalar Nginx:

```bash
sudo yum install -y nginx
sudo systemctl start nginx
sudo systemctl enable nginx
```

**Desplegar la app** (una vez, hasta que tengas pipeline):

- Desde tu Mac (donde está el código):  
  `scp -i tu-clave.pem -r index.html README.md ec2-user@<IP_EC2_VANTIVE>:/tmp/`  
  luego en la EC2:  
  `sudo cp /tmp/index.html /usr/share/nginx/html/`

O clonar desde Gitea en la EC2:

```bash
sudo yum install -y git
git clone http://gitea.unclic.consulting:3000/alejandro-perez/vantive.git /tmp/vantive
sudo cp /tmp/vantive/index.html /usr/share/nginx/html/
```

**Anotar la IP pública** de la EC2 (ej. `54.12.34.56`). Comprobar: `http://<IP>` debe mostrar la página.

---

## 3. Subdominio público (DNS)

Para que **vantive.unclic.consulting** apunte a tu instancia y sea accesible en internet:

**Namecheap** → **unclic.consulting** → **Manage** → **Advanced DNS** → **HOST RECORDS**.

- **ADD NEW RECORD**
- Type: **A**
- Host: **vantive**
- Value: **\<IP de la EC2 Vantive\>**
- TTL: Automatic

Guardar. Tras unos minutos, **http://vantive.unclic.consulting** debe abrir la misma página que por IP (accesible desde todo internet si el security group permite 80/443).

---

## 4. Asegurar con HTTPS

Para **https://vantive.unclic.consulting** tienes dos opciones.

### Opción A: Usar el certificado wildcard existente (recomendado)

Si ya tienes el certificado wildcard de unclic (unclic.consulting + *.unclic.consulting) en la EC2 de Jenkins (o en tu Mac), **copiarlo a la EC2 Vantive** y configurar Nginx ahí.

**En la EC2 Vantive:**

1. Crear directorio para el cert (o usar el que ya exista si copiaste desde Jenkins):
   ```bash
   sudo mkdir -p /etc/letsencrypt/live/unclic.consulting
   ```
2. Copiar desde la EC2 Jenkins (o desde tu máquina) los archivos:
   - `fullchain.pem`
   - `privkey.pem`  
   a `/etc/letsencrypt/live/unclic.consulting/` en la EC2 Vantive (por ejemplo vía `scp` desde Jenkins a Vantive).
3. Permisos:
   ```bash
   sudo chmod 644 /etc/letsencrypt/live/unclic.consulting/fullchain.pem
   sudo chmod 600 /etc/letsencrypt/live/unclic.consulting/privkey.pem
   ```
4. Crear configuración Nginx para HTTPS:

```bash
sudo tee /etc/nginx/conf.d/vantive.conf << 'EOF'
server {
    listen 80;
    server_name vantive.unclic.consulting;
    return 301 https://$host$request_uri;
}
server {
    listen 443 ssl;
    server_name vantive.unclic.consulting;
    ssl_certificate /etc/letsencrypt/live/unclic.consulting/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/unclic.consulting/privkey.pem;
    root /usr/share/nginx/html;
    index index.html;
    location / {
        try_files $uri $uri/ /index.html;
    }
}
EOF
```

5. Comprobar y recargar:
   ```bash
   sudo nginx -t
   sudo systemctl reload nginx
   ```

6. **Security group:** asegurar que la EC2 Vantive tiene regla **HTTPS (443)** from 0.0.0.0/0.

Resultado: **https://vantive.unclic.consulting** accesible en todo internet y seguro.

### Opción B: Certificado solo para vantive (Certbot en la EC2 Vantive)

En la EC2 Vantive:

```bash
sudo yum install -y certbot python3-certbot-nginx
sudo certbot --nginx -d vantive.unclic.consulting
```

Certbot pide email y aceptar términos; configurará Nginx y el certificado. Renovación: `sudo certbot renew` (o vía cron).

---

## 5. Resumen de comprobaciones

| Paso | Comprobar |
|------|-----------|
| Push | En Gitea: repo **alejandro-perez/vantive** con index.html y Jenkinsfile. |
| Instancia | `http://<IP_EC2>` muestra la página Vantive. |
| Subdominio | `http://vantive.unclic.consulting` resuelve y muestra la misma página. |
| HTTPS | `https://vantive.unclic.consulting` abre sin avisos y redirige desde HTTP. |

---

## 6. Documentos relacionados

- [KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md](KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md) — Crear EC2 Jenkins y EC2 Vantive.
- [VANTIVE-SERVIDOR-Y-PIPELINE-DESDE-CERO.md](VANTIVE-SERVIDOR-Y-PIPELINE-DESDE-CERO.md) — Pipeline y flujo completo.

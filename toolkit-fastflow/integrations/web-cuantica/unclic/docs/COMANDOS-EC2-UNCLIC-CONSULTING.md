# Comandos en la EC2 (fastflow-vantive) para unclic.consulting

**Instancia:** fastflow-vantive · **IP:** 3.22.236.150 · **Usuario:** ec2-user  

Conectar por **EC2 Instance Connect** (navegador) o:  
`ssh ec2-user@3.22.236.150`

---

## 1. Crear carpeta del sitio

```bash
sudo mkdir -p /usr/share/nginx/unclic
sudo chown -R nginx:nginx /usr/share/nginx/unclic
```

(Si en tu AMI el usuario de Nginx es `ec2-user` o otro, ajusta el `chown`.)

---

## 2. Configurar Nginx para unclic.consulting y www

```bash
sudo nano /etc/nginx/conf.d/unclic.conf
```

Pega esto (y guarda: Ctrl+O, Enter, Ctrl+X):

```nginx
server {
    listen 80;
    server_name unclic.consulting www.unclic.consulting;
    root /usr/share/nginx/unclic;
    index index.html;
    location / {
        try_files $uri $uri/ $uri.html /index.html;
    }
}
```

Comprobar y recargar:

```bash
sudo nginx -t
sudo systemctl reload nginx
```

---

## 3. Subir el sitio desde tu Mac (deploy en dos pasos)

`ec2-user` no puede escribir en `/usr/share/nginx/unclic`; se sube a `~/unclic-deploy/` y en la EC2 se copia con sudo.

**En tu Mac** (después de `npm run build`):

```bash
rsync -avz --delete -e "ssh -i /path/to/gitea-key.pem" out/ ec2-user@3.22.236.150:~/unclic-deploy/
```

**En la EC2**:

```bash
sudo rsync -av --delete /home/ec2-user/unclic-deploy/ /usr/share/nginx/unclic/
sudo chown -R nginx:nginx /usr/share/nginx/unclic
```

---

## 4. HTTPS con Certbot

En la EC2:

```bash
sudo certbot --nginx -d unclic.consulting -d www.unclic.consulting
```

(Si certbot no está instalado: `sudo yum install -y certbot python3-certbot-nginx` en Amazon Linux.)

---

## Resumen

| Paso | Dónde | Acción |
|------|--------|--------|
| 1 | EC2 | mkdir + chown `/usr/share/nginx/unclic` |
| 2 | EC2 | Crear `/etc/nginx/conf.d/unclic.conf` y `reload nginx` |
| 3 | Mac | `npm run build` y rsync `out/` → EC2 `~/unclic-deploy/`; en EC2: sudo rsync a `/usr/share/nginx/unclic` + chown |
| 4 | EC2 | `certbot --nginx -d unclic.consulting -d www.unclic.consulting` |

DNS en Namecheap: A @ y A www → 3.22.236.150 (ver NAMECHEAP-DNS-UNCLIC-ROOT.md).

# Error 413 al hacer push a Gitea (Payload Too Large)

Si al hacer `git push gitea main` ves:

```
error: RPC failed; HTTP 413 curl 22 The requested URL returned error: 413
```

es porque **Nginx** (o el proxy delante de Gitea) tiene un límite de tamaño de petición por defecto (suele ser 1 MB) y tu push lo supera.

---

## Solución: aumentar el límite en el servidor Gitea

Hay que configurar **Nginx** en la EC2 donde corre Gitea (fastflow-gitea).

### 1. Conectar por SSH a la EC2 de Gitea

Desde tu máquina (ajusta usuario/key/hostname):

```bash
ssh ec2-user@gitea.unclic.consulting
# o: ssh -i tu-key.pem ec2-user@18.223.114.68
```

### 2. Editar la config de Nginx para Gitea

Busca el archivo que define el `server` de Gitea, por ejemplo:

- **Amazon Linux / RHEL:** `/etc/nginx/conf.d/gitea.conf` (o nombre parecido)
- **Ubuntu:** `/etc/nginx/sites-available/gitea`
- o dentro de `/etc/nginx/nginx.conf`

Añade (o cambia) **dentro del bloque `server`** de Gitea, o en el bloque `http` si aplica a todo:

```nginx
client_max_body_size 50M;
```

Ejemplo dentro de un `server`:

```nginx
server {
    listen 80;
    server_name gitea.unclic.consulting;
    client_max_body_size 50M;   # <-- esta línea
    location / {
        proxy_pass http://127.0.0.1:3000;
        # ... resto del proxy
    }
}
```

Guarda el archivo.

### 3. Comprobar y recargar Nginx

```bash
sudo nginx -t
sudo systemctl reload nginx
```

### 4. Volver a hacer push desde tu Mac

```bash
cd /ruta/a/unclic
git push -u gitea main
```

Con `50M` suele ser suficiente para pushes de varios MB. Si algún repo es muy grande, puedes subir a `100M` o más.

---

## Si no tienes acceso a la EC2

Pide a quien administre el servidor Gitea que añada en la config de Nginx del sitio de Gitea:

```nginx
client_max_body_size 50M;
```

y que ejecute `sudo nginx -t && sudo systemctl reload nginx`.

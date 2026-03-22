# HTTPS para Jenkins en Amazon Linux 2 (Nginx + Let’s Encrypt)

**Objetivo:** que **`https://jenkins.unclic.consulting`** abra Jenkins con candado, sin `:8080`, para compartir el enlace (p. ej. WhatsApp) con invitados.

**No hace falta Docker** para esto. Jenkins sigue escuchando en **`127.0.0.1:8080`**; **Nginx** escucha en **80/443** y hace *reverse proxy*.

**Requisitos previos:**

- Registro **A** `jenkins` en Namecheap → **IP pública de esta EC2** (la misma donde corre Jenkins).
- **Security group** de la instancia Jenkins:
  - **TCP 22** (SSH)
  - **TCP 80** (HTTP — validación Let’s Encrypt y redirección)
  - **TCP 443** (HTTPS)
  - **TCP 8080** (opcional; puedes restringirlo a solo `127.0.0.1` cuando Nginx esté estable, o cerrarlo al mundo y entrar solo por 443)

---

## 1. Instalar Nginx y Certbot (Amazon Linux 2)

Conéctate por SSH como `ec2-user` a la EC2 **fastflow-jenkins-controller**.

```bash
# EPEL trae certbot en muchas instalaciones AL2
sudo amazon-linux-extras install epel -y

# En AL2 suele NO existir python3-certbot-nginx; basta nginx + certbot + plugin para Nginx (Python 2):
sudo yum install -y nginx certbot python2-certbot-nginx
```

Si `yum` dice *lock* (`/var/run/yum.pid`), **espera** a que termine el otro `yum` o `sudo rm /var/run/yum.pid` solo si estás seguro de que no hay otro yum corriendo.

```bash
sudo systemctl enable nginx
sudo systemctl start nginx
```

Comprueba: `curl -sI http://127.0.0.1 | head -1` → debería responder **200** o **403** de Nginx.

**Si ya instalaste** `nginx` y `certbot` **sin** el plugin, instálalo después:

```bash
sudo yum install -y python2-certbot-nginx
```

---

## 2. Configurar Nginx como proxy hacia Jenkins

En AL2 los *virtual hosts* suelen ir en **`/etc/nginx/conf.d/`** (no `sites-available` como en Ubuntu).

```bash
sudo tee /etc/nginx/conf.d/jenkins.conf >/dev/null <<'EOF'
server {
    listen 80;
    server_name jenkins.unclic.consulting;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_read_timeout 90;
        proxy_buffering off;
        client_max_body_size 50m;
    }
}
EOF

sudo nginx -t && sudo systemctl reload nginx
```

Prueba **desde tu PC** (DNS correcto): `http://jenkins.unclic.consulting` debería mostrar la interfaz de Jenkins (aún sin candado en HTTPS).

---

## 3. Certificado Let’s Encrypt (HTTPS)

```bash
sudo certbot --nginx -d jenkins.unclic.consulting
```

- Indica un **email** si lo pide.
- Acepta términos.
- Elige **redirigir HTTP → HTTPS** cuando Certbot lo ofrezca.

Certbot modificará la config de Nginx para **443** y el certificado.

**Renovación (cada ~90 días):** suele quedar un *timer*/*cron*. Comprueba:

```bash
sudo certbot renew --dry-run
```

---

## 4. Jenkins: URL del sistema

1. Entra en **`https://jenkins.unclic.consulting`** (usuario/contraseña que creaste en el asistente).
2. **Manage Jenkins** → **System** → **Jenkins URL** = **`https://jenkins.unclic.consulting/`** (sin puerto).
3. Guardar.

Así los enlaces internos, `BUILD_URL` y notificaciones usarán HTTPS.

---

## 5. Invitados por WhatsApp (buenas prácticas mínimas)

- Envía solo **`https://jenkins.unclic.consulting`** (con candado).
- Crea **usuarios** en Jenkins (**Manage Jenkins** → **Users**) o usa un proveedor que tengas configurado; **no** compartas la cuenta `admin`.
- Para limitar qué ven: **Matrix Authorization Strategy** (plugin) o roles; guía más amplia: [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md).

---

## 6. Docker en esta misma EC2 (pipeline POS)

**HTTPS no depende de Docker.** Para el pipeline que hace `docker build`, instala Docker aparte:

- [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md)

---

## 7. Si algo falla

| Síntoma | Qué revisar |
|---------|-------------|
| Certbot falla “connection refused” | **TCP 80** abierto al mundo en el SG; Nginx en marcha; DNS `jenkins` → esta IP. |
| 502 Bad Gateway | `sudo systemctl status jenkins` y que escuche en **8080**: `sudo ss -tlnp \| grep 8080`. |
| Jenkins redirige mal o aviso de proxy | Jenkins URL en **System** debe ser exactamente `https://jenkins.unclic.consulting/`. |
| `certbot` / paquetes no encontrados | `sudo amazon-linux-extras install epel -y` y repetir `yum install`; en AL2 muy antiguo ver notas de [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md). |

---

**Referencia general (Gitea + Jenkins + concepto):** [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)  
**Flujo completo commit → Postman:** [GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)

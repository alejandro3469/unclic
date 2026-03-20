# Cómo subir el sitio a https://unclic.consulting

Pasos para que el landing (este proyecto) se sirva en **https://unclic.consulting** y **https://www.unclic.consulting**.

---

## Resumen

| Paso | Dónde | Qué hacer |
|------|--------|-----------|
| 1 | Namecheap (DNS) | Apuntar **unclic.consulting** (@) y **www** a la IP del servidor |
| 2 | EC2 (Nginx) | Crear server block para `unclic.consulting` y `www`, document root para los estáticos |
| 3 | Build + deploy | Generar `out/` y copiarlo al servidor (manual o con Jenkins) |
| 4 | EC2 (Certbot) | Activar HTTPS para unclic.consulting y www |

**Servidor recomendado:** la misma EC2 **fastflow-vantive** (IP actual **3.22.236.150**), donde ya corre Nginx. Así no creas otra instancia.

---

## 1. DNS en Namecheap

**Dónde:** Namecheap → **unclic.consulting** → **Manage** → **Advanced DNS** → **HOST RECORDS**.

Asegura que el **dominio raíz** y **www** apunten a la IP de la EC2 donde sirves el sitio (ej. Vantive):

| Type | Host | Value | TTL |
|------|------|--------|-----|
| **A** | **@** | **3.22.236.150** | Automatic |
| **A** | **www** | **3.22.236.150** | Automatic |

(Usa la IP actual de **fastflow-vantive** si es otra; compruébala en AWS Console → EC2 → Instances.)

Espera unos minutos a que propague el DNS.

---

## 2. Nginx en la EC2 (fastflow-vantive)

Conéctate por SSH a la EC2 (ej. `ssh -i /path/to/gitea-key.pem ec2-user@3.22.236.150` o `ssh ec2-user@vantive.unclic.consulting`).

### 2.1 Crear la carpeta del sitio

```bash
sudo mkdir -p /usr/share/nginx/unclic
sudo chown -R nginx:nginx /usr/share/nginx/unclic
# Si en tu EC2 el usuario de Nginx es otro (ej. www-data), usa ese usuario.
```

### 2.2 Nuevo server block para unclic.consulting

Crea un archivo, por ejemplo `/etc/nginx/conf.d/unclic.conf`:

```bash
sudo nano /etc/nginx/conf.d/unclic.conf
```

Contenido:

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

Guarda, comprueba y recarga Nginx:

```bash
sudo nginx -t
sudo systemctl reload nginx
```

---

## 3. Subir el sitio (primera vez)

Tienes dos opciones: **manual** o **con Jenkins**.

### Opción A: Deploy manual

En tu Mac, desde la carpeta del proyecto (donde está `package.json`):

```bash
cd /ruta/a/unclic   # o a nucleic-landing si trabajas desde el clon

npm ci
npm run build
```

Se genera la carpeta **`out/`**. Subida en dos pasos (ec2-user no puede escribir en `/usr/share/nginx/unclic`):

**En tu Mac:**

```bash
rsync -avz --delete -e "ssh -i /path/to/gitea-key.pem" out/ ec2-user@3.22.236.150:~/unclic-deploy/
```

**En la EC2:**

```bash
sudo rsync -av --delete /home/ec2-user/unclic-deploy/ /usr/share/nginx/unclic/
sudo chown -R nginx:nginx /usr/share/nginx/unclic
```

Abre **http://unclic.consulting** (aún sin HTTPS). Deberías ver el landing.

### Opción B: Deploy con Jenkins

1. **Job en Jenkins** que use el repo **nucleic** (Gitea), rama **main**, Script Path **Jenkinsfile**.
2. **Variables del job:**  
   - `DEPLOY_HOST=3.22.236.150` (o la IP de fastflow-vantive)  
   - `DEPLOY_USER=ec2-user`  
   - `DEPLOY_PATH=/usr/share/nginx/unclic` (o rsync a `~/unclic-deploy` y en EC2 script que haga sudo rsync)
3. Credenciales SSH para esa EC2.
4. **Build Now** (o push a main). El pipeline hace checkout → `npm ci` → `npm run build` → rsync de `out/` (a `~/unclic-deploy/` o a docroot si el agente tiene permisos).

---

## 4. Activar HTTPS (Certbot)

En la misma EC2:

```bash
sudo certbot --nginx -d unclic.consulting -d www.unclic.consulting
```

Sigue el asistente (email, aceptar términos). Certbot modificará la config de Nginx y configurará HTTPS. Recarga Nginx si hace falta:

```bash
sudo nginx -t && sudo systemctl reload nginx
```

Comprueba **https://unclic.consulting** y **https://www.unclic.consulting**.

---

## 5. Resumen de rutas

| URL | Contenido |
|-----|-----------|
| **https://unclic.consulting** | Este sitio (landing UnClic), servido desde `/usr/share/nginx/unclic` |
| **https://www.unclic.consulting** | Igual (mismo server block) |
| **https://vantive.unclic.consulting** | Sigue siendo el sitio Vantive (otro server block, `/usr/share/nginx/html` u otra ruta) |

---

## Actualizar el sitio después

- **Manual:** `npm run build` en local; rsync `out/` a `ec2-user@3.22.236.150:~/unclic-deploy/`; en EC2: `sudo rsync -av --delete ~/unclic-deploy/ /usr/share/nginx/unclic/` y `sudo chown -R nginx:nginx /usr/share/nginx/unclic`.
- **Jenkins:** push a **main** en el repo nucleic (o Build Now) y el pipeline desplegará solo.

---

## Documentos relacionados

- [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](../../docs/INFRAESTRUCTURA-UNCLIC-ACTUAL.md) — IPs, DNS, EC2.
- [PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md](PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md) — Plan del landing, Jenkins, Gitea.
- [GITEA-NUCLEIC-PUSH.md](GITEA-NUCLEIC-PUSH.md) — Subir código al repo nucleic (Gitea).

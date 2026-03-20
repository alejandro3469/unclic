# Replicar unclic.consulting completo

Guía para replicar desde cero el sitio **https://unclic.consulting** (landing UnClic): DNS, EC2, Nginx, HTTPS, deploy manual y flujo Gitea/Jenkins.

**Carpeta de trabajo recomendada:** `toolkit-fastflow/integrations/web-cuantica/unclic` (ver [README-REPLICAR-Y-TRABAJO.md](README-REPLICAR-Y-TRABAJO.md)).

---

## 1. Resumen en tabla

| # | Dónde | Qué hacer |
|---|--------|-----------|
| 1 | Namecheap | A @ y A www → IP de la EC2 (3.22.236.150 o la actual de fastflow-vantive) |
| 2 | EC2 (fastflow-vantive) | mkdir `/usr/share/nginx/unclic`, Nginx server block, reload |
| 3 | Namecheap | Esperar propagación DNS (5–15 min) |
| 4 | EC2 | Certbot: `sudo certbot --nginx -d unclic.consulting -d www.unclic.consulting` |
| 5 | Mac | Build: `cd unclic && npm run build` |
| 6 | Mac | Rsync a EC2: `out/` → `ec2-user@IP:~/unclic-deploy/` (clave .pem) |
| 7 | EC2 | `sudo rsync -av --delete ~/unclic-deploy/ /usr/share/nginx/unclic/` y `sudo chown -R nginx:nginx /usr/share/nginx/unclic` |
| 8 | Gitea | Repo nucleic con código del landing (push por bloques si hay límite 413) |
| 9 | Jenkins | Job unclic: repo nucleic, rama main, Jenkinsfile; variables DEPLOY_*; Node en el agente |

---

## 2. DNS (Namecheap)

- **Dominio:** unclic.consulting → **Advanced DNS** → **HOST RECORDS**.
- Añadir:
  - **A** | **@** | **3.22.236.150** | Automatic
  - **A** | **www** | **3.22.236.150** | Automatic
- Comprobar IP actual de **fastflow-vantive** en AWS Console → EC2 → Instances.  
- Detalle: [NAMECHEAP-DNS-UNCLIC-ROOT.md](NAMECHEAP-DNS-UNCLIC-ROOT.md).

---

## 3. EC2 fastflow-vantive (Nginx)

- **IP:** 3.22.236.150 (o la actual).
- **Usuario:** ec2-user. **Clave:** gitea-key.pem (ej. `/Users/wallfacer/Downloads/gitea-key.pem`).

### 3.1 Crear directorio y config Nginx

```bash
sudo mkdir -p /usr/share/nginx/unclic
sudo chown -R nginx:nginx /usr/share/nginx/unclic
sudo tee /etc/nginx/conf.d/unclic.conf << 'EOF'
server {
    listen 80;
    server_name unclic.consulting www.unclic.consulting;
    root /usr/share/nginx/unclic;
    index index.html;
    location / {
        try_files $uri $uri/ $uri.html /index.html;
    }
}
EOF
sudo nginx -t && sudo systemctl reload nginx
```

### 3.2 HTTPS (tras propagación DNS)

```bash
sudo certbot --nginx -d unclic.consulting -d www.unclic.consulting
```

Detalle: [COMANDOS-EC2-UNCLIC-CONSULTING.md](COMANDOS-EC2-UNCLIC-CONSULTING.md).

---

## 4. Deploy manual (Mac → EC2) — dos pasos por permisos

`ec2-user` no puede escribir en `/usr/share/nginx/unclic` (es de nginx). Por eso: subir a `~/unclic-deploy/` y en la EC2 copiar con sudo.

### En tu Mac

```bash
cd /ruta/a/unclic   # toolkit-fastflow/integrations/web-cuantica/unclic
npm run build
rsync -avz --delete -e "ssh -i /Users/wallfacer/Downloads/gitea-key.pem" out/ ec2-user@3.22.236.150:~/unclic-deploy/
```

### En la EC2

```bash
sudo rsync -av --delete /home/ec2-user/unclic-deploy/ /usr/share/nginx/unclic/
sudo chown -R nginx:nginx /usr/share/nginx/unclic
# Opcional: rm -rf /home/ec2-user/unclic-deploy
```

Detalle: [SUBIR-SITIO-A-UNCLIC-CONSULTING.md](SUBIR-SITIO-A-UNCLIC-CONSULTING.md).

---

## 5. Gitea (repo nucleic)

- **URL:** https://gitea.unclic.consulting/alejandro-perez/nucleic.git
- **Rama:** main.
- Código: solo el landing (unclic). Si el push da **HTTP 413**, usar push por bloques (varios commits y un push por cada uno).  
- Detalle: [GITEA-NUCLEIC-PUSH.md](GITEA-NUCLEIC-PUSH.md), [GITEA-413-PUSH-RESUELTO.md](GITEA-413-PUSH-RESUELTO.md).

---

## 6. Jenkins (job unclic)

- **Repository URL:** https://gitea.unclic.consulting/alejandro-perez/nucleic.git  
- **Branch:** */main  
- **Script Path:** Jenkinsfile  
- **Variables (job o global):** DEPLOY_HOST=3.22.236.150, DEPLOY_USER=ec2-user, DEPLOY_PATH=/usr/share/nginx/unclic  
- **Credenciales:** Gitea (usuario/contraseña); SSH para la EC2 Vantive (deploy).  
- En la EC2 de Jenkins debe estar instalado **Node.js** (`npm ci`, `npm run build`).  
- Si el deploy hace rsync directo a `/usr/share/nginx/unclic`, el agente Jenkins debe poder escribir ahí (p. ej. clave SSH de un usuario con sudo o rsync a ~/unclic-deploy y script en la EC2 que haga sudo rsync).

---

## 7. Documentos relacionados (orden)

| Doc | Contenido |
|-----|-----------|
| [README-REPLICAR-Y-TRABAJO.md](README-REPLICAR-Y-TRABAJO.md) | Dónde seguir trabajando e índice de docs |
| [NAMECHEAP-DNS-UNCLIC-ROOT.md](NAMECHEAP-DNS-UNCLIC-ROOT.md) | Registros A @ y www |
| [COMANDOS-EC2-UNCLIC-CONSULTING.md](COMANDOS-EC2-UNCLIC-CONSULTING.md) | Comandos en la EC2 (Nginx, Certbot, deploy) |
| [SUBIR-SITIO-A-UNCLIC-CONSULTING.md](SUBIR-SITIO-A-UNCLIC-CONSULTING.md) | Subir sitio (manual y Jenkins) |
| [GITEA-NUCLEIC-PUSH.md](GITEA-NUCLEIC-PUSH.md) | Subir código a nucleic (por bloques) |
| [GITEA-413-PUSH-RESUELTO.md](GITEA-413-PUSH-RESUELTO.md) | Solución 413 (client_max_body_size en Nginx Gitea) |
| [PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md](PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md) | Plan subdominio, demos, Jenkins |

---

*IP y rutas corresponden al estado actual (fastflow-vantive 3.22.236.150). Actualizar si la instancia o el dominio cambian.*

# Desplegar Vantive ya en AWS (gratis) — Guía urgente

Pasos mínimos para tener el sitio Vantive en línea con AWS Free Tier (EC2 existente o nueva).

---

## Opción A: Usar la EC2 que ya existe (lo más rápido)

Servidor **fastflow-vantive** (IP **3.22.236.150**, dominio vantive.unclic.consulting). Región **us-east-2** (Ohio).

### 1. Arrancar la EC2 (si está parada)

1. AWS Console → **EC2** → **Instances** (región **us-east-2**).
2. Busca la instancia **fastflow-vantive**.
3. Si el estado es **Stopped**: selecciónala → **Instance state** → **Start instance**.
4. Espera **Running**. Si no usas IP elástica, la IP puede cambiar: anótala y actualiza el registro DNS **vantive** en Namecheap con la nueva IP.

### 2. Conectar por SSH y levantar Nginx

**Conectar:** EC2 → selecciona **fastflow-vantive** → **Connect** → **EC2 Instance Connect** → **Connect**.

En la terminal del navegador:

```bash
sudo systemctl start nginx
sudo systemctl enable nginx
sudo systemctl status nginx
```

Debe aparecer `Active: active (running)`.

### 3. Desplegar el contenido (desde tu máquina)

En tu PC (con Node 18+ y la clave SSH de la EC2):

```bash
# Desde la raíz del repo toolkit-fastflow (o integrations/web-cuantica)
cd toolkit-fastflow/integrations/kings-joers/vantive

npm ci --no-audit
npm run build
# Se genera la carpeta out/

# Desplegar a la EC2 (IP actual 3.22.236.150)
export VANTIVE_IP=3.22.236.150
export VANTIVE_KEY=/ruta/a/tu-key.pem   # clave PEM de la EC2

rsync -avz --delete -e "ssh -i $VANTIVE_KEY -o StrictHostKeyChecking=no" out/ ec2-user@$VANTIVE_IP:/tmp/vantive-deploy/
ssh -i $VANTIVE_KEY -o StrictHostKeyChecking=no ec2-user@$VANTIVE_IP "sudo rsync -av --delete /tmp/vantive-deploy/ /usr/share/nginx/html/ && sudo chown -R nginx:nginx /usr/share/nginx/html/"
```

### 4. Comprobar

Abre en el navegador:

- **http://vantive.unclic.consulting**
- o **http://3.22.236.150**

---

## Opción B: EC2 nueva en Free Tier (si no existe fastflow-vantive)

### 1. Crear la instancia

1. AWS Console → **EC2** → **Launch Instance**.
2. **Name:** `fastflow-vantive`.
3. **AMI:** Amazon Linux 2.
4. **Instance type:** **t3.micro** (Free tier).
5. **Key pair:** Crear o usar una existente (necesaria para SSH).
6. **Security group:** Permitir **22 (SSH)**, **80 (HTTP)**, **443 (HTTPS)** desde 0.0.0.0/0 (o restringir a tu IP).
7. **Launch**.

Anota la **Public IPv4**.

### 2. Instalar Nginx en la EC2

Conectar por **EC2 Instance Connect** o SSH:

```bash
sudo yum update -y
sudo yum install -y nginx
sudo systemctl start nginx
sudo systemctl enable nginx
```

### 3. Configurar Nginx para SPA (rutas Next.js)

```bash
sudo tee /etc/nginx/conf.d/vantive.conf << 'EOF'
server {
    listen 80;
    server_name _;
    root /usr/share/nginx/html;
    index index.html;
    location / {
        try_files $uri $uri/ $uri.html /index.html;
    }
}
EOF
sudo systemctl reload nginx
```

### 4. Desplegar contenido

Desde tu PC (mismo bloque de comandos que en Opción A, usando la **nueva IP** de la EC2).

### 5. DNS (opcional)

En Namecheap (o tu DNS): registro **A** para **vantive** (o el subdominio que uses) apuntando a la IP pública de la EC2.

---

## Script automático (desde repo)

Desde **toolkit-fastflow** (raíz del repo):

```bash
chmod +x integrations/web-cuantica/scripts/deploy-vantive-ec2.sh
VANTIVE_IP=3.22.236.150 VANTIVE_KEY=/ruta/a/tu-key.pem ./integrations/web-cuantica/scripts/deploy-vantive-ec2.sh
```

Desde **integrations/web-cuantica**:

```bash
chmod +x scripts/deploy-vantive-ec2.sh
VANTIVE_IP=3.22.236.150 VANTIVE_KEY=/ruta/a/tu-key.pem ./scripts/deploy-vantive-ec2.sh
```

El script hace: build en `kings-joers/vantive` (Next.js `out/`) y rsync a la EC2 en `/usr/share/nginx/html/`.

---

## Resumen

| Qué hacer              | Dónde / comando |
|------------------------|------------------|
| EC2 ya existe          | Opción A: arrancar → Nginx → build + rsync |
| EC2 nueva              | Opción B: crear t3.micro → Nginx → config → build + rsync |
| Probar                 | http://vantive.unclic.consulting o http://&lt;IP&gt; |

Documentación detallada: [SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md), [kings-joers/vantive/docs/JENKINS-VANTIVE-AWS.md](../../kings-joers/vantive/docs/JENKINS-VANTIVE-AWS.md).

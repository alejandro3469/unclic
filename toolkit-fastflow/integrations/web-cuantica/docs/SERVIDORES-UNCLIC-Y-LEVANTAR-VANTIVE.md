# Servidores unclic.consulting y levantar Vantive

Lista de los **tres servidores** (EC2) y pasos para **levantar el de Vantive** (arrancar instancia, Nginx y comprobar el sitio).

---

## 1. Los tres servidores (resumen)

| Servidor | Nombre EC2 | IP pública | URL | Puertos | Uso |
|----------|------------|------------|-----|---------|-----|
| **Jenkins** | fastflow-jenkins-controller | 18.119.157.22 | https://jenkins.unclic.consulting, http://jenkins.unclic.consulting:8080 | 22, 8080, 443 | Pipeline pos-online, generic-model |
| **Gitea** | fastflow-gitea | 13.58.58.245 | http://gitea.unclic.consulting:3000 | 22, 3000 | Repos pos-online, smartbussiness-generic-model, vantive |
| **Vantive** | fastflow-vantive (i-0aa28684e29302446) | 3.22.236.150 | http://vantive.unclic.consulting, https://vantive.unclic.consulting | 22, 80, 443 | Sitio estático Kings & Joers (Nginx) |

- **Región AWS:** us-east-2 (Ohio).
- **DNS:** Namecheap → unclic.consulting → Advanced DNS → A records: **jenkins** → 18.119.157.22, **gitea** → 13.58.58.245, **vantive** → 3.22.236.150.
- **Arrancar/parar:** AWS Console → EC2 → Instances → seleccionar instancia → **Start** / **Stop**.

---

## 2. Levantar el servidor Vantive

Cuando la EC2 **fastflow-vantive** está parada o quieres asegurar que el sitio responde.

### 2.1 Arrancar la EC2 (si está parada)

**Dónde:** AWS Console → **EC2** → **Instances** (región **us-east-2**).

**Qué hacer:**
1. Localiza la instancia **fastflow-vantive** (nombre o Instance ID `i-0aa28684e29302446`).
2. Si **Instance state** es **Stopped**, selecciónala → menú **Instance state** → **Start instance**.
3. Espera a que el estado pase a **Running**. La **Public IPv4** puede haber cambiado si no usas Elastic IP; si cambió, actualiza en Namecheap el A record **vantive** con la nueva IP.

### 2.2 Conectar por SSH y arrancar Nginx

**Dónde:** Misma consola EC2 → selecciona **fastflow-vantive** → **Connect** → **EC2 Instance Connect** → **Connect** (se abre terminal en el navegador).

**Qué teclear:**

```bash
# Arrancar Nginx y dejarlo activo tras reinicios
sudo systemctl start nginx
sudo systemctl enable nginx

# Comprobar estado
sudo systemctl status nginx
```

**Qué ver:** `Active: active (running)`. Si falla, revisar logs con `sudo journalctl -u nginx -n 30`.

### 2.3 Comprobar el sitio

En el navegador abre:

- **http://vantive.unclic.consulting**
- o **http://3.22.236.150**

Deberías ver el sitio estático Vantive (Kings & Joers). El contenido se sirve desde **/usr/share/nginx/html** (Nginx en la EC2). La config de Nginx está en `/etc/nginx/conf.d/vantive.conf` con `server_name vantive.unclic.consulting 3.22.236.150`.

### 2.4 (Opcional) Actualizar contenido desde Gitea

Si el sitio se despliega desde el repo **vantive** en Gitea (por pipeline o a mano), clonar o hacer pull y copiar a `/usr/share/nginx/html` en la EC2. Ejemplo desde tu máquina (con SSH o key):

```bash
# En la EC2, si tienes git y credenciales para Gitea:
cd /tmp
git clone http://gitea.unclic.consulting:3000/alejandro-perez/vantive.git
sudo rsync -av --delete vantive/ /usr/share/nginx/html/
```

O usar el job de Jenkins de Kings & Joers si está configurado para desplegar en esta EC2 (ver `toolkit-fastflow/integrations/kings-joers/docs/KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md`).

---

## 3. Resumen rápido

| Objetivo | Dónde / qué hacer |
|----------|--------------------|
| Ver los tres servidores | Tabla §1 de este doc; detalle en [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md). |
| Levantar Vantive (EC2 parada) | EC2 → Instances → fastflow-vantive → Start instance. |
| Levantar Nginx en Vantive | SSH a 3.22.236.150 → `sudo systemctl start nginx && sudo systemctl enable nginx`. |
| Probar Vantive | Abrir http://vantive.unclic.consulting o http://3.22.236.150. |

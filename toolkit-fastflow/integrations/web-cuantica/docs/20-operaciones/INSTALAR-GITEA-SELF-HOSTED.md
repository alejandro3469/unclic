# Instalar y hospedar Gitea (self-hosted) para proyectos, repos y clientes

Guía para **descargar e instalar Gitea** en tu propio servidor (VPS, EC2, máquina local) y usarlo como Git para tus proyectos y clientes. Compatible con Jenkins (Repository URL), Terraform y el flujo FastFlow.

**Documentación oficial:** [docs.gitea.com](https://docs.gitea.com) · Descargas: [dl.gitea.com](https://dl.gitea.com/gitea/).

---

## 1. Opción recomendada: Docker (rápido)

Si tienes **Docker** y **Docker Compose**, es la forma más sencilla.

### Crear carpeta y docker-compose

```bash
mkdir -p gitea
cd gitea
```

Crea `docker-compose.yml` con este contenido (Gitea 1.25.4, SQLite, puerto 3000):

```yaml
version: "3"

networks:
  gitea:
    external: false

services:
  server:
    image: docker.gitea.com/gitea:1.25.4
    container_name: gitea
    environment:
      - USER_UID=1000
      - USER_GID=1000
    restart: always
    networks:
      - gitea
    volumes:
      - ./gitea:/data
      - /etc/timezone:/etc/timezone:ro
      - /etc/localtime:/etc/localtime:ro
    ports:
      - "3000:3000"
      - "222:22"
```

### Arrancar

```bash
docker compose up -d
```

Gitea quedará en **http://localhost:3000** (o http://&lt;IP-del-servidor&gt;:3000). La primera vez se abre el **asistente de instalación** en el navegador.

### Primer uso (asistente web)

1. Abre **http://localhost:3000** (o la IP de tu servidor).
2. **Base de datos:** deja **SQLite3** y ruta `/data/gitea/gitea.db` (en Docker ya está bien).
3. **Configuración general:**  
   - **Dominio:** tu dominio o la IP (ej. `gitea.midominio.com` o `192.168.1.10`).  
   - **URL raíz:** `http://gitea.midominio.com:3000/` o `http://192.168.1.10:3000/`.  
   - **SSH:** si usas SSH en otro puerto, ajústalo (en el compose está 222:22).
4. **Cuenta de administrador:** usuario, email, contraseña.
5. **Instalar Gitea**. Luego ya puedes crear repositorios y organizaciones para proyectos y clientes.

---

## 2. Opción: binario en Linux (sin Docker)

Para un servidor Linux (VPS, EC2, etc.) sin Docker.

### Requisitos

- Git instalado: `git --version`
- Usuario dedicado (recomendado): `git`

### Descargar el binario

Elige la versión en [dl.gitea.com/gitea/](https://dl.gitea.com/gitea/). Ejemplo para **Linux 64-bit** (1.25.4):

```bash
# Sustituye la versión si hay una más reciente
GITEA_VERSION="1.25.4"
wget -O gitea "https://dl.gitea.com/gitea/${GITEA_VERSION}/gitea-${GITEA_VERSION}-linux-amd64"
chmod +x gitea
```

**macOS:** usa `darwin-arm64` (Apple Silicon) o `darwin-amd64` (Intel). **Windows:** `windows-4.0-amd64`.

### Usuario y directorios (Linux)

```bash
# Usuario git (Ubuntu/Debian)
sudo adduser --system --shell /bin/bash --gecos 'Git' --group --disabled-password --home /home/git git

# Directorios de datos
sudo mkdir -p /var/lib/gitea/{custom,data,log}
sudo chown -R git:git /var/lib/gitea
sudo chmod -R 750 /var/lib/gitea

sudo mkdir /etc/gitea
sudo chown root:git /etc/gitea
sudo chmod 770 /etc/gitea
```

### Instalar binario y ejecutar

```bash
sudo cp gitea /usr/local/bin/gitea
export GITEA_WORK_DIR=/var/lib/gitea
sudo -u git GITEA_WORK_DIR=/var/lib/gitea /usr/local/bin/gitea web -c /etc/gitea/app.ini
```

La primera vez Gitea crea `/etc/gitea/app.ini` si no existe. Abre **http://&lt;servidor&gt;:3000** y completa el asistente (base de datos SQLite, URL, admin).

### Servicio systemd (opcional, para que arranque solo)

Crea `/etc/systemd/system/gitea.service`:

```ini
[Unit]
Description=Gitea
After=network.target

[Service]
Type=simple
User=git
Group=git
WorkingDirectory=/var/lib/gitea
ExecStart=/usr/local/bin/gitea web -c /etc/gitea/app.ini
Environment=GITEA_WORK_DIR=/var/lib/gitea
Restart=always

[Install]
WantedBy=multi-user.target
```

Luego:

```bash
sudo systemctl daemon-reload
sudo systemctl enable gitea
sudo systemctl start gitea
```

---

## 3. Usar Gitea con Jenkins y clientes

- **Crear repos:** En Gitea → New Repository (ej. `pos-online`, repos por cliente).
- **URL de clonación:** La que muestra cada repo (HTTPS o SSH). Es la que pones en **Jenkins** → job → Configure → **Repository URL**.
- **Repos privados:** En Jenkins añade **Credentials** (usuario Gitea + token o contraseña).
- **Clientes:** Puedes crear una **organización** por cliente y repos dentro, o un repo por proyecto; da permisos por usuario/equipo.

Ver: [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md) para conectar un repo local a Gitea y a Jenkins.

---

## 4. Puertos y firewall

| Puerto | Uso |
|--------|-----|
| **3000** | Web (HTTP). Para producción suele ponerse Nginx/Caddy delante y HTTPS. |
| **22** (o 222 en Docker) | SSH para `git clone` por SSH. |

Si Gitea está en una EC2, abre el puerto 3000 (y 222 si usas SSH del contenedor) en el **Security Group**.

---

## 5. Hospedar Gitea en AWS (gratis, disponible para todos)

Sí: **puedes hospedar Gitea en AWS** y dejarlo **disponible para todos** (tu equipo, clientes, quien tenga la URL). Con Free Tier puedes hacerlo **sin coste** dentro de los límites.

### Gratis con AWS Free Tier

- **EC2:** 750 horas/mes gratis (12 meses) con instancias **t2.micro** o **t3.micro** (1 vCPU, 1 GB RAM). Una instancia 24/7 cabe en ese cupo.
- **Opciones:**
  - **A) Misma EC2 que Jenkins:** Instalas Gitea (Docker o binario) en la misma máquina donde corre Jenkins. Jenkins usa el puerto 8080, Gitea el 3000. Ahorras una instancia.
  - **B) EC2 solo para Gitea:** Una instancia t3.micro solo con Gitea. Útil si quieres separar o dar más recursos a Gitea.

### Paso a paso: EC2 solo para Gitea (opción B)

**No uses la instancia de Jenkins** (fastflow-jenkins-controller). Esa es solo para Jenkins. Para Gitea necesitas **una instancia nueva**.

1. **Crear la EC2** (consola AWS):
   - En la pantalla **EC2 → Instances**, pulsa **Launch instance** (botón naranja).
   - **Name and tags → Name:** `gitea` o `fastflow-gitea`.
   - **Application and OS Images (AMI):** Amazon Linux 2023 (el que viene por defecto) o Amazon Linux 2. Dejar **64-bit (x86)**.
   - **Instance type:** t3.micro (Free Tier). Ya viene seleccionado.
   - **Key pair (login):** Obligatorio en la consola. Elige **Create new key pair** (nombre ej. `gitea-key`), descarga el .pem y guárdalo; o selecciona uno que ya tengas si vas a usar SSH desde tu Mac. Con EC2 Instance Connect también puedes conectar sin key en muchas AMIs.
   - **Network settings:** Dejar la VPC que venga (por defecto o la de Jenkins). **Auto-assign public IP:** Enable.
   - **Firewall (security groups):** **Create security group**. Nombre: `gitea-sg`. Reglas de entrada:
     - **SSH**, TCP 22, origen 0.0.0.0/0 (o "My IP" si prefieres).
     - **Custom TCP**, puerto **3000**, origen 0.0.0.0/0 (web Gitea).
     - **Custom TCP**, puerto **222**, origen 0.0.0.0/0 (opcional; SSH para Git con Docker).
     - (Puedes quitar HTTP 80 y HTTPS 443 si no los usarás; Gitea va en 3000.)
   - **Configure storage:** 8 GiB (por defecto) está bien.
   - **Summary → Number of instances:** 1.
   - **Launch instance**.

   O con Terraform (si ya tienes módulos en el repo): definir una segunda instancia + security group con 22, 3000, 222. Anota la **IP pública** de la nueva instancia.

2. **Conectar a la EC2 (Gitea):**  
   EC2 → Instances → selecciona la instancia **gitea** (no fastflow-jenkins-controller) → **Connect** → **EC2 Instance Connect** → Connect. (O `ssh -i tu-key.pem ec2-user@<IP-gitea>`.)

3. **Instalar Docker y Gitea (recomendado):**
   ```bash
   # Amazon Linux 2023 usa dnf; Amazon Linux 2 usa yum
   sudo dnf update -y   # o: sudo yum update -y
   sudo dnf install -y docker   # o: sudo yum install -y docker
   sudo systemctl start docker
   sudo systemctl enable docker
   sudo usermod -aG docker ec2-user
   ```
   Cerrar sesión y volver a conectar para que el grupo `docker` aplique (o usar `sudo` en los comandos siguientes). Luego:
   ```bash
   # Docker Compose: en Amazon Linux 2023 el plugin no está en dnf; instalar binario:
   sudo curl -SL "https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
   sudo chmod +x /usr/local/bin/docker-compose
   mkdir -p gitea && cd gitea
   ```
   Crear `docker-compose.yml` con el contenido de la **sección 1** de este doc (imagen `docker.gitea.com/gitea:1.25.4`, puertos `3000:3000` y `222:22`). Ejecutar (con guion: docker-compose):
   ```bash
   sudo docker-compose up -d
   ```

4. **Elastic IP (opcional pero recomendado):**  
   EC2 → Elastic IPs → Allocate → Asociar a la instancia de Gitea. Así la URL no cambia al reiniciar.

5. **Primer uso:**  
   Abre **http://&lt;IP-pública&gt;:3000** (o http://&lt;Elastic-IP&gt;:3000). Completa el asistente de Gitea (SQLite, URL raíz = `http://<IP>:3000/`, cuenta de admin). Ya está disponible para todos los que tengan la URL.

### Dejarlo disponible para todos

1. **Security Group** de la EC2 donde corre Gitea:
   - **Entrada TCP 3000** (web): Origen **0.0.0.0/0** si quieres que cualquiera con la URL pueda acceder, o restringe a IPs/redes de confianza.
   - **Entrada TCP 222** (o 22): Solo si usas SSH para Git; mismo criterio (0.0.0.0/0 o restringir).

2. **URL de acceso:**  
   `http://<IP-pública-EC2>:3000`  
   Cualquiera con esa URL puede abrir Gitea en el navegador (y clonar repos públicos o con credenciales los privados).

3. **IP fija (recomendado):** Asigna una **Elastic IP** a la EC2 para que la IP no cambie al reiniciar. Así la URL sigue siendo la misma y puedes darla a clientes o documentación.

4. **Dominio (opcional):** Si tienes un dominio (ej. `gitea.miempresa.com`), crea un registro DNS (A o CNAME) apuntando a la IP de la EC2. En el asistente de Gitea pon esa URL como “Dominio” y “URL raíz”.

### Seguridad rápida

- **Repos privados:** Solo usuarios con cuenta y permisos los ven; Jenkins usa Credentials.
- **HTTPS:** Para producción conviene poner Nginx o Caddy delante con HTTPS (certificado gratis con Let’s Encrypt). Sin HTTPS, las contraseñas van en claro por la red.
- **Restringir por IP:** Si “todos” son solo tu oficina o clientes con IP fija, en el Security Group limita el origen (ej. tu IP) en vez de 0.0.0.0/0.

### Resumen

| Pregunta | Respuesta |
|----------|-----------|
| ¿Puede estar disponible para todos? | Sí: abre el puerto 3000 (y opcional 222) y usa la IP o dominio. |
| ¿Puedo hospedarlo gratis en AWS? | Sí: Free Tier (EC2 t3.micro/t2.micro, 750 h/mes). Misma EC2 que Jenkins o una aparte. |

---

## 6. Enlaces oficiales

| Recurso | URL |
|---------|-----|
| Documentación | https://docs.gitea.com |
| Instalación binario | https://docs.gitea.com/installation/install-from-binary |
| Instalación Docker | https://docs.gitea.com/installation/install-with-docker |
| Descargas | https://dl.gitea.com/gitea/ |
| Sitio Gitea | https://gitea.com |

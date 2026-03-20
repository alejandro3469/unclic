# Infraestructura actual del sitio unclic.consulting

Documento de referencia: **qué existe hoy**, **dónde se configura cada cosa** y **qué está disponible**. Actualizar este doc cuando cambies DNS, EC2 o servicios.

**Para replicar todo desde cero** (pantallas, teclas, comandos): **[REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md)**. **Índice de todo lo documentado para replicar el estado actual:** [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md). **Lista de los tres servidores y cómo levantar Vantive:** [SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md).

---

## 1. Resumen en una tabla

| Componente | Dónde se configura | Valor / URL actual | Estado |
|------------|--------------------|--------------------|--------|
| **Dominio** | Namecheap | unclic.consulting | Activo |
| **DNS (subdominios)** | Namecheap → Advanced DNS → HOST RECORDS | Ver tabla §2 | Configurado |
| **Correo** | Namecheap (MX) + Google Workspace | @unclic.consulting vía Google | Activo |
| **Jenkins** | AWS EC2 | **https://jenkins.unclic.consulting** (443) y http://jenkins.unclic.consulting:8080 (3.15.4.160) | Disponible |
| **Gitea** | AWS EC2 | http://gitea.unclic.consulting:3000 (18.223.114.68) | Disponible |
| **EC2 Jenkins** | AWS Console (us-east-2) | fastflow-jenkins-controller, 3.15.4.160 | Running |
| **EC2 Gitea** | AWS Console (us-east-2) | fastflow-gitea, 18.223.114.68 | Running |
| **EC2 Vantive** | AWS Console (us-east-2) | fastflow-vantive, 3.18.111.60 | Running |
| **Vantive** | AWS EC2 (Nginx) | http://vantive.unclic.consulting (3.18.111.60) | Disponible |

---

## 2. DNS en Namecheap (unclic.consulting)

**Dónde:** Namecheap → **Domain List** → **unclic.consulting** → **Manage** → pestaña **Advanced DNS** → **HOST RECORDS**.

### Registros actuales (estado conocido)

| Type | Host | Value | TTL | Uso |
|------|------|--------|-----|-----|
| **A (+ Dynamic DNS)** | **gitea** | **18.223.114.68** | Automatic | Gitea → EC2 fastflow-gitea |
| **A (+ Dynamic DNS)** | **jenkins** | **3.15.4.160** | Automatic | Jenkins → EC2 fastflow-jenkins-controller |
| **A (+ Dynamic DNS)** | **vantive** | **3.18.111.60** | Automatic | Vantive (Kings & Joers) → EC2 fastflow-vantive |
| CNAME | eozwmgwcml3s | gv-la7opzav7oocjz.dv.googlehosted.com. | 1 min | Verificación Google Workspace |
| TXT | @ | google-site-verification=... | 1 min | Verificación dominio Google |
| TXT | google._domainkey | v=DKIM1;k=rsa;p=... | 1 min | DKIM correo (Google) |
| MX | @ | SMTP.GOOGLE.COM. | 1 | Correo @unclic.consulting (Google) |

- **Cambiar IP de Jenkins o Gitea:** editar el A record correspondiente en esta misma pantalla (o asignar Elastic IP en AWS y no tocarlo).
- **Añadir otro subdominio:** ADD NEW RECORD → tipo A (o CNAME) → Host = subdominio, Value = IP (o destino).

---

## 3. AWS EC2 (us-east-2)

**Dónde:** AWS Console → **EC2** → **Instances** (región **Ohio / us-east-2**).

### Instancias actuales

| Nombre | Instance ID | IP pública | Tipo | Uso | Puerto(s) |
|--------|-------------|------------|------|-----|-----------|
| **fastflow-jenkins-controller** | i-040e97fa007277738 | **3.15.4.160** | t3.micro | Jenkins, pipeline pos-online | 8080 (Jenkins) |
| **fastflow-gitea** | i-0fb60c190b2e03f0c | **18.223.114.68** | t3.micro | Gitea (repos) | 3000 (Gitea) |
| **fastflow-vantive** | i-0aa28684e29302446 | **3.18.111.60** | t3.micro | Vantive (Nginx, sitio Kings & Joers) | 80 (HTTP), 443 (HTTPS) |

- **Security groups:** fastflow-jenkins-sg (Jenkins), gitea-sg (Gitea), vantive-sg (Vantive). Deben permitir tráfico entrante en 8080, 3000 y 80/443 respectivamente (y 22 para SSH si aplica).
- **Elastic IP:** hoy no hay; si reinicias/recréas la EC2, la IP puede cambiar y hay que actualizar los A records en Namecheap (o asignar Elastic IP y no tocarlos).

---

## 4. Servicios y URLs disponibles

| Servicio | URL de acceso | Dónde se configura la URL |
|----------|----------------|---------------------------|
| **Jenkins** | https://jenkins.unclic.consulting (o :8080) | Jenkins → Manage Jenkins → System → Jenkins URL; Nginx en EC2 para 443 |
| **Gitea** | http://gitea.unclic.consulting:3000 | Gitea: asistente de instalación o `/data/gitea/conf/app.ini` (ROOT_URL, DOMAIN) |
| **Vantive** | http://vantive.unclic.consulting (o http://3.18.111.60) | Nginx en EC2 fastflow-vantive, `root /usr/share/nginx/html` |
| **Job pos-online** | http://jenkins.unclic.consulting:8080/job/pos-online/ | Job creado en Jenkins (Pipeline from SCM, repo en Gitea) |

- **Sin proxy inverso:** las URLs llevan puerto (:8080, :3000). Para quitar el puerto y usar HTTPS, ver [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md) y [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

---

## 5. Correo (unclic.consulting)

- **Proveedor:** Google Workspace (MX → SMTP.GOOGLE.COM, DKIM en TXT google._domainkey).
- **Dónde se configura:** Namecheap solo tiene los registros MX y TXT; la gestión de cuentas y buzones es en **Google Admin** (admin.google.com) para el dominio unclic.consulting.
- **Uso:** correo tipo alejandro@unclic.consulting; se puede usar como admin en Gitea/Jenkins.

---

## 6. Qué está instalado en cada EC2 (estado conocido)

### fastflow-jenkins-controller (3.15.4.160)

- **SO:** Amazon Linux 2.
- **Servicios:** Jenkins (puerto 8080).
- **Herramientas:** Java 17 (Amazon Corretto), Maven 3.0.5, Git. Usuario `jenkins` puede ejecutar `mvn` y `git`.
- **Memoria (t3.micro, 1 GB):** el pipeline pos-online usa `MAVEN_OPTS` con heap bajo. Si falla por OOM ("Not enough space"), **añade swap en la EC2** — ver **[EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md)**.
- **Job pos-online:** Pipeline from SCM, repo en Gitea (rama main), Script Path `Jenkinsfile`. El job debe ejecutarse en este nodo (no en agente externo) para usar Maven/Java de la EC2.

### fastflow-gitea (18.223.114.68)

- **Servicios:** Gitea (puerto 3000).
- **Repos:** Por ejemplo `alejandro-perez/pos-online` (rama main) para el pipeline Jenkins.

### fastflow-vantive (3.18.111.60)

- **SO:** Amazon Linux 2023.
- **Servicios:** Nginx (puertos 80, 443). Sitio estático Vantive (Kings & Joers).
- **Document root:** `/usr/share/nginx/html`; `index.html` del repo `vantive` en Gitea.
- **Config Nginx:** `/etc/nginx/conf.d/vantive.conf` — `server_name vantive.unclic.consulting 3.18.111.60`.
- **Security group:** vantive-sg (HTTP 80, HTTPS 443, SSH 22).

---

## 7. Dónde cambiar qué (resumen)

| Quiero… | Dónde |
|--------|--------|
| Cambiar IP de jenkins.unclic.consulting, gitea.unclic.consulting o vantive.unclic.consulting | Namecheap → unclic.consulting → Advanced DNS → editar A record (Host jenkins, gitea o vantive) |
| Añadir/editar MX o DKIM (correo) | Namecheap → Advanced DNS (MX, TXT); cuentas en Google Admin |
| Ver/arrancar/parar EC2 Jenkins, Gitea o Vantive | AWS Console → EC2 → Instances (us-east-2) |
| Cambiar puertos o reglas de red de una EC2 | AWS Console → EC2 → Security Groups (fastflow-jenkins-sg, gitea-sg, vantive-sg) |
| Cambiar la URL que usa Jenkins (enlaces, notificaciones) | Jenkins → Manage Jenkins → System → Jenkins URL |
| Cambiar la URL que usa Gitea | Gitea app.ini (ROOT_URL, DOMAIN) o asistente inicial |
| Añadir repos o usuarios Gitea | Interfaz web Gitea (gitea.unclic.consulting:3000) |
| Crear/editar jobs o credenciales Jenkins | Interfaz web Jenkins (jenkins.unclic.consulting:8080) |

---

## 8. Comprobar que estás en el Jenkins de la EC2 (no en el de tu Mac)

Jenkins en la **EC2** está en **3.15.4.160** y el dominio **jenkins.unclic.consulting** apunta ahí. Si ves **Built-In Node = Mac OS X** en Manage Jenkins → Nodes, estás entrando al Jenkins de **tu Mac**, no al de la EC2.

**Qué hacer:**

1. **Abre la URL por IP** (sin depender del dominio): **http://3.15.4.160:8080**
2. **Manage Jenkins** → **Nodes**. El **Built-In Node** debe decir **Linux** (no Mac OS X). Si es Linux, ese es el Jenkins de la EC2.
3. Usa **esa** interfaz para el job pos-online (crear/reconfigurar job, Build Now). Ahí Maven y Git están instalados.
4. Cuando compruebes que 3.15.4.160:8080 es Linux, puedes usar **http://jenkins.unclic.consulting:8080** (debe resolver a 3.15.4.160). Si en tu red ese dominio abre otro Jenkins (Mac), revisa: no tengas un **túnel SSH inverso** (-R 8080:localhost:8080) que esté exponiendo tu Mac en el puerto 8080 de la EC2, y no tengas en **hosts** (Mac) una línea que mande jenkins.unclic.consulting a 127.0.0.1.

**Resumen:** Jenkins “oficial” para unclic es el de la EC2 (3.15.4.160). Comprueba siempre en Nodes que Built-In = **Linux**.

---

## 9. Documentos relacionados

- **Replicar estado actual (índice de toda la documentación):** [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md)
- **Repos locales y Gitea (rutas, remotes, dos niveles de Git, push):** [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md)
- **Probar local y subir pipeline (comandos push, logs en Jenkinsfile):** [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md)
- **Avisos Jenkins y prioridad (pipeline POS primero):** [JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md](JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md)
- **SSL para todo el dominio y todos los subdominios (un cert wildcard gratis):** [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md)
- **Registro de lo ejecutado (Jenkins HTTPS, comandos y salidas reales, pendientes):** [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md)
- **Diagnóstico SSL (ERR_SSL_PROTOCOL_ERROR), fallo pipeline y nueva instancia pos/landing:** [RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md)
- **Dominio y DNS detallado:** [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md)
- **HTTPS (Nginx, Let's Encrypt):** [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)
- **Gitea + Jenkins seguro y usuarios:** [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md)
- **Crear EC2 para landing unclic (gratis):** [EC2-CREAR-LANDING-UNCLIC.md](EC2-CREAR-LANDING-UNCLIC.md). (Vantive: subcompañía Kings & Joers, `integrations/kings-joers/`.)
- **Registry en EC2 (opcional):** [REGISTRY-EC2-GRATIS.md](REGISTRY-EC2-GRATIS.md)
- **Índice de documentación:** [README.md](README.md)

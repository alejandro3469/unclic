# Elementos a crear y pipeline para que el POS corra en otro servidor

El pipeline desplega con **Docker e imagen en registry**: se construye la imagen en **Build image**, se sube en **Push to registry** (variable `REGISTRY`) y en **Deploy** se ejecuta un contenedor (local: `docker run` con la imagen construida; remoto: `docker pull` desde el registry y `docker run` en el servidor POS). Para deploy remoto son obligatorios **REGISTRY** (para push y pull) y los elementos siguientes.

---

## 1. Elementos que han de ser creados

### 1.1 Servidor dedicado al POS (EC2 u otro)

| Elemento | Descripción |
|----------|-------------|
| **Instancia EC2** (o VM) | Una máquina **solo para el POS** con **Docker** instalado (para deploy con imagen). Usuario SSH (ej. `ec2-user`). Nombre sugerido: `fastflow-pos-demo`. |
| **Docker en el servidor POS** | Necesario para deploy con contenedor: el pipeline hace `ssh` y ejecuta `docker pull REGISTRY/pos-online:TAG` y `docker run`. El usuario de deploy debe poder usar `docker` sin sudo (grupo `docker`). |
| **Acceso al registry** | El servidor POS debe poder hacer `docker pull` al **REGISTRY** (misma red/VPC, o registry con acceso desde la IP del servidor; si el registry es HTTP, configurar `insecure-registries` en el servidor). |
| **Puerto de la app** | El contenedor mapea el puerto **8111**. Debe ser accesible desde la IP del agente Jenkins (Verify) o solo desde Nginx en la misma máquina. |

### 1.2 Red y seguridad

| Elemento | Descripción |
|----------|-------------|
| **Security group (EC2 POS)** | SSH (22) desde la IP del agente Jenkins (o desde tu IP para administrar). Puerto de la app (8111): abierto desde la IP del agente Jenkins para que el pipeline haga `curl` en Verify; o solo localhost si Nginx hace proxy y Verify se hace vía SSH. |
| **DNS** (opcional) | Registro **A** `pos` → IP del servidor POS (ej. `pos.unclic.consulting`). Luego Nginx + Certbot en el servidor POS para HTTPS. |

### 1.3 Nginx en el servidor POS (recomendado para producción/demo)

| Elemento | Descripción |
|----------|-------------|
| **Nginx** | Reverse proxy en el servidor POS: `proxy_pass http://127.0.0.1:8111`; `server_name pos.unclic.consulting`. |
| **Certbot** | `sudo certbot --nginx -d pos.unclic.consulting` para HTTPS. |
| **Documentar URL final** | Tras deploy, la app se puede usar en **https://pos.unclic.consulting** (vía Nginx). |

### 1.4 Jenkins: credenciales y variables del job

| Elemento | Descripción |
|----------|-------------|
| **Clave SSH** para el servidor POS | Par de claves (.pem) o clave privada del usuario que hará deploy en el servidor POS. El agente Jenkins debe poder hacer `ssh -i clave user@POS_HOST` sin contraseña. |
| **Credencial en Jenkins** | **Tipo:** "Secret file". Subir el archivo .pem (clave privada). Anotar el **ID** de la credencial (ej. `pos-deploy-key`). |
| **Variables de entorno del job** | **REGISTRY** (obligatorio para deploy remoto con Docker), **POS_DEPLOY_HOST**, **POS_DEPLOY_USER**, **POS_DEPLOY_KEY_CREDENTIAL_ID**. Ver tabla en la sección 2. |

---

## 2. Variables del pipeline para deploy en otro servidor

Si estas variables están definidas (en el job o en el Jenkinsfile), el pipeline desplegará en el **servidor remoto**. Si no, seguirá desplegando en el **agente Jenkins** (comportamiento actual).

| Variable | Obligatoria | Ejemplo | Descripción |
|----------|-------------|---------|-------------|
| **REGISTRY** | Sí (para deploy remoto con Docker) | `localhost:5000` o `registry.empresa.internal:5000` | Registry donde se hace push de la imagen; el servidor POS hará `docker pull` desde aquí. Sin REGISTRY, el deploy remoto falla. |
| **POS_DEPLOY_HOST** | Sí (para deploy remoto) | `3.22.236.151` o `pos.unclic.consulting` | Host o IP del servidor donde debe correr el contenedor POS. |
| **POS_DEPLOY_USER** | No | `ec2-user` | Usuario SSH en el servidor POS (debe poder ejecutar `docker`). Por defecto: `ec2-user`. |
| **POS_DEPLOY_PATH** | No (solo si se usa deploy JAR) | `/opt/pos` | Con deploy Docker no se usa; el contenedor corre con la imagen del registry. |
| **POS_DEPLOY_KEY_CREDENTIAL_ID** | Sí (para deploy remoto) | `pos-deploy-key` | ID de la credencial Jenkins "Secret file" con la clave privada (.pem) para SSH al servidor POS. |
| **APP_PORT** | No | `8111` | Puerto del contenedor (mapeo host:8111). Por defecto: `8111`. |

### Cómo configurar en Jenkins

1. **Manage Jenkins** → **Credentials** → **Add** → **Secret file** → subir el .pem del servidor POS → ID: `pos-deploy-key` (o el que uses).
2. **Job** → **Configure** → **Pipeline**:
   - Si usas "Pipeline script from SCM", define las variables en **Pipeline** → **Environment** (no todas las versiones lo permiten).  
   - O bien en **Pipeline** → **Script** (si no usas SCM) define allí `environment { POS_DEPLOY_HOST = '...'; ... }`.  
   - La forma más portable: **Parameters** (Choice o String) y en el Jenkinsfile leer `params.POS_DEPLOY_HOST` o usar **Inject environment** (plugin) para inyectar POS_DEPLOY_HOST, etc.
3. Alternativa: en el **Jenkinsfile** (rama main) poner valores por defecto con `env.POS_DEPLOY_HOST = env.POS_DEPLOY_HOST ?: ''` y que el valor real se inyecte desde el job (Variables de entorno definidas en el job cuando se usa "Pipeline script from SCM" con "Use Groovy Sandbox" suele requerir definir las variables en la configuración del job si existen).

Recomendación: definir en el **job** las variables de entorno **POS_DEPLOY_HOST**, **POS_DEPLOY_USER**, **POS_DEPLOY_PATH**, **POS_DEPLOY_KEY_CREDENTIAL_ID** (y la credencial Secret file con ese ID). Así el mismo Jenkinsfile sirve para deploy local (variables vacías) o remoto (variables rellenadas).

---

## 3. Comportamiento del pipeline (resumen) — Docker y registry

Cuando existe **Dockerfile** (caso actual), el despliegue usa **imagen Docker** y **registry**:

| Stage | Deploy local (sin POS_DEPLOY_HOST) | Deploy remoto (con POS_DEPLOY_HOST, REGISTRY y credencial) |
|-------|------------------------------------|-------------------------------------------------------------|
| **Build image** | `docker build -t pos-online:TAG .` en el agente | Igual |
| **Push to registry** | Opcional (si `REGISTRY` definido, se hace push) | **Obligatorio**: definir `REGISTRY` para que la imagen esté disponible en el servidor |
| **Cleanup** | `docker stop pos-online; docker rm pos-online` en el agente | `ssh` al servidor POS y `docker stop pos-online; docker rm pos-online` |
| **Deploy** | `docker run -d --name pos-online -p 8111:8111 pos-online:TAG` en el agente | `ssh` y en el servidor: `docker pull REGISTRY/pos-online:TAG` y `docker run -d --name pos-online -p 8111:8111 REGISTRY/pos-online:TAG` |
| **Verify instance** | `curl http://localhost:8111/health` desde el agente | `curl http://${POS_DEPLOY_HOST}:8111/actuator/health` (o `/health`) desde el agente Jenkins |

Si el servidor POS no expone el puerto 8111 al agente Jenkins (solo Nginx en 80/443), el Verify se puede hacer por **SSH + curl localhost:8111** en el servidor; el Jenkinsfile incluye esa variante si se desea (opcional).

### Rollback (misma EC2 POS, imagen ya publicada)

No hace falta volver a ejecutar el pipeline completo. En el servidor (o vía SSH):

```bash
docker pull REGISTRY/pos-online:BUILD_ANTERIOR
docker stop pos-online && docker rm pos-online
docker run -d --name pos-online -p 8111:8111 --restart=unless-stopped REGISTRY/pos-online:BUILD_ANTERIOR
```

**Job Jenkins opcional:** segundo pipeline **from SCM** con **Script Path** = `Jenkinsfile.rollback` (mismo repo Git). Parámetros: tag (`ROLLBACK_IMAGE_TAG`), `REGISTRY`, `POS_DEPLOY_HOST`, usuario SSH, ID de credencial `.pem`. Guía detallada: [ROLLBACK-POS-DOCKER-REGISTRY.md](ROLLBACK-POS-DOCKER-REGISTRY.md).

Si tienes **varias** instancias POS, usa el mismo job y cambia solo `POS_DEPLOY_HOST` en cada ejecución (o duplica el job con host por defecto distinto).

---

## 4. Checklist rápido (deploy con Docker y registry)

- [ ] Registry en marcha (p. ej. `registry:2` en puerto 5000); variable **REGISTRY** definida en el job (ej. `localhost:5000` o IP:5000).
- [ ] EC2 (o VM) dedicada al POS con **Docker** instalado; usuario de deploy en el grupo `docker`.
- [ ] Servidor POS puede hacer `docker pull` al REGISTRY (red/VPC o `insecure-registries` si aplica).
- [ ] Clave SSH (.pem) para el usuario de deploy; credencial "Secret file" en Jenkins (ID ej. `pos-deploy-key`).
- [ ] Variables del job: **REGISTRY**, **POS_DEPLOY_HOST**, **POS_DEPLOY_USER**, **POS_DEPLOY_KEY_CREDENTIAL_ID**.
- [ ] Security group del servidor POS: puerto 8111 accesible desde la IP del agente Jenkins (Verify).
- [ ] (Opcional) Nginx + Certbot en el servidor POS; DNS `pos.unclic.consulting` → IP del servidor.

---

## 5. Documentos relacionados

- [ROLLBACK-POS-DOCKER-REGISTRY.md](ROLLBACK-POS-DOCKER-REGISTRY.md) — Rollback manual + job `Jenkinsfile.rollback`.
- [PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md](../docs/PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md) — Plan completo: instancia dedicada, email gate, Terraform/K8s.
- [README.md](README.md) — Uso del repo y pipeline actual (deploy en agente).
- [../docs/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](../docs/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) — Jenkins y EC2.

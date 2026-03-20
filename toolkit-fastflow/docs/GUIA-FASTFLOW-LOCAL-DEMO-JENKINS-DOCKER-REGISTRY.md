# Guía FastFlow: montar local la demo (Jenkins + Docker + Registry)

Esta guía te permite reproducir **en local** el mismo flujo que usamos en la demo en AWS: Jenkins, Docker, Registry y (opcional) el sitio FastFlow. Primero montamos todo local con capturas de referencia; luego ese contenido está en el portal y en el repo como código open source fácil de instalar.

---

## Objetivo

- Tener **Jenkins** corriendo en local (o en una VM/EC2 como en la demo).
- Configurar un **pipeline** que haga build → imagen Docker → push a **Registry**.
- Probar el **sitio FastFlow** (landing + portal) en local y, si aplica, empaquetado como imagen.
- Dejar documentado y descargable para que otros puedan instalar el toolkit como open source.

---

## Requisitos previos

| Componente | Versión recomendada | Notas |
|------------|---------------------|--------|
| **Java** | 11 (LTS) | Jenkins requiere Java; en la demo AWS usamos Java 11. |
| **Jenkins** | 2.x LTS | Instalado en local o en servidor (ej. EC2). |
| **Docker** | 20.x+ | Docker Engine o Docker Desktop. Necesario para build y push de imágenes. |
| **Registry** | Opcional en local | Docker Hub, GitLab Container Registry, AWS ECR o registry local (ej. `registry:2`). |
| **Git** | 2.x | Para clonar el repo y disparar pipelines. |

---

## 1. Java 11

En la demo usamos **Java 11** como ruta estándar. Verifica en tu máquina:

```bash
java -version
```

Deberías ver algo como `openjdk version "11.x.x"` o similar.

**[CAPTURA: Salida de `java -version` en terminal mostrando Java 11.]**

Si no lo tienes (Mac con Homebrew):

```bash
brew install openjdk@11
# Añadir al PATH según indicación de Homebrew
```

En Linux (Debian/Ubuntu): `sudo apt install openjdk-11-jdk`. En la VM/EC2 de la demo se instaló de la misma forma.

---

## 2. Jenkins en local

### 2.1 Instalación (resumen)

- **Mac/Linux (war):** descargar `jenkins.war` y ejecutar `java -jar jenkins.war --httpPort=8080`.
- **O con paquete:** ver [Jenkins instalación oficial](https://www.jenkins.io/doc/book/installing/).

Tras arrancar, abrir en el navegador `http://localhost:8080` (o la IP del servidor si es remoto, como en AWS).

**[CAPTURA: Pantalla de bienvenida de Jenkins (Unlock Jenkins) con el cuadro para la contraseña inicial.]**

Obtener la contraseña inicial:

```bash
# En Mac/Linux, si instalaste con war en el home del usuario:
cat ~/.jenkins/secrets/initialAdminPassword
```

**[CAPTURA: Terminal mostrando el comando y la contraseña inicial.]**

### 2.2 Configuración inicial

- Instalar los plugins sugeridos (o selección mínima).
- Crear usuario admin.
- Jenkins listo para crear el primer job.

**[CAPTURA: Dashboard de Jenkins tras la configuración inicial (lista de jobs vacía o con un job de ejemplo).]**

### 2.3 Puerto 8080

Si en tu máquina el **proxy local de FastFlow** usa también el 8080, puedes:

- Cambiar Jenkins a otro puerto (ej. `--httpPort=8081`), o
- Usar el proxy en otro puerto: `PROXY_PORT=8081 node proxy-local.js`.

En la demo AWS, Jenkins estaba en el puerto 8080 del servidor y se accedía por IP/puerto (y luego se restringió por IP/VPN).

---

## 3. Docker

### 3.1 Instalar Docker

- **Docker Desktop (Mac/Windows):** [docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop/).
- **Linux:** ver [docs.docker.com/engine/install](https://docs.docker.com/engine/install/).

Verificar:

```bash
docker --version
docker run hello-world
```

**[CAPTURA: Terminal con `docker --version` y salida correcta de `hello-world`.]**

### 3.2 Docker y Jenkins

Para que Jenkins pueda construir imágenes, el agente que ejecuta los jobs debe tener Docker disponible:

- **Local:** si Jenkins corre en tu Mac/PC, Docker Desktop basta; el usuario con el que corre Jenkins debe poder usar `docker` (grupo `docker` en Linux).
- **Demo AWS:** en el servidor se instaló Docker y Jenkins podía ejecutar `docker build` y `docker push`.

**[CAPTURA: Opcional — Job de Jenkins con un paso "Execute shell" que ejecuta `docker build`.]**

---

## 4. Registry

El **Registry** es donde se guardan las imágenes Docker para poder usarlas en otros entornos (por ejemplo Kubernetes o otro servidor).

### 4.1 Opciones

| Registry | Uso típico |
|----------|------------|
| **Docker Hub** | Público o privado; fácil para demos. |
| **GitLab Container Registry** | Si el código está en GitLab. |
| **AWS ECR** | Si el despliegue es en AWS. |
| **Registry local** | `docker run -d -p 5000:5000 registry:2` para pruebas. |

### 4.2 Configurar credenciales en Jenkins

En Jenkins: **Manage Jenkins → Credentials → Add**. Crear credencial tipo **Username and password** con el usuario y token (o contraseña) del registry. Anotar el **ID** (ej. `registry-dockerhub-id`).

**[CAPTURA: Jenkins → Credentials → formulario de nueva credencial (Username and password) con ID visible.]**

### 4.3 Probar push desde terminal (opcional)

Para validar que el registry funciona antes de usar Jenkins:

```bash
docker login
docker tag fastflow-server:latest <tu-usuario>/fastflow-server:latest
docker push <tu-usuario>/fastflow-server:latest
```

**[CAPTURA: Terminal con `docker push` exitoso; o Docker Desktop mostrando la imagen en "Images" y en el repositorio remoto.]**

---

## 5. Pipeline en Jenkins (build → imagen → registry)

Un pipeline típico de FastFlow para este flujo:

1. **Checkout** del repo.
2. **Build** (si aplica: tests, lint).
3. **Build de imagen Docker** del servidor FastFlow (o del componente que corresponda).
4. **Push** de la imagen al registry con tags (ej. `latest`, `branch`, `sha`).

Variables que suele usar el pipeline (según [REGISTRY-INTEGRATION.md](REGISTRY-INTEGRATION.md)):

- `ENABLE_REGISTRY=true`
- `REGISTRY_HOST` (ej. `docker.io`, o la URL del ECR/GitLab)
- `REGISTRY_REPOSITORY` (ej. `webcuantica/fastflow-api`)
- `REGISTRY_CREDENTIALS_ID` (ID de la credencial en Jenkins)

**[CAPTURA: Jenkins → Job → Configure → sección Pipeline con fragmento del Jenkinsfile (stages: Build, Docker build, Push to registry).]**

**[CAPTURA: Ejecución del job mostrando los pasos en azul (éxito) hasta "Push to registry".]**

---

## 6. Sitio FastFlow en local (landing + portal)

Además del pipeline, puedes levantar el **producto FastFlow** (landing, portal de clientes, API) en tu máquina.

### 6.1 Con Node (desarrollo)

Desde el repo (ruta donde está `server/` y el proxy):

```bash
# Hosts (una vez): copiar deploy/hosts-local-unclic.txt a /etc/hosts
cd server && npm install && npm run init-db && npm start
# En otra terminal:
cd deploy && node proxy-local.js
```

Abrir en el navegador (con hosts configurados):

- **FastFlow:** http://webcuantica.fastflow.unclic.consulting:8080
- **Hub:** http://unclic.consulting:8080

**[CAPTURA: Navegador mostrando la landing de FastFlow (hero, título, CTA).]**

**[CAPTURA: Página /app con token (portal de clientes) mostrando recursos: Diagnóstico, Documentación, Contacto, Proveedores.]**

### 6.2 Con Docker (imagen del producto)

Desde la raíz de web-cuantica (donde está `server/Dockerfile`):

```bash
docker build -f server/Dockerfile -t fastflow-server:latest .
docker run -p 3000:3000 -e BASE_URL=http://localhost:3000 fastflow-server:latest
```

Abrir `http://localhost:3000`. Ver [BUILD-REGISTRY-FASTFLOW.md](BUILD-REGISTRY-FASTFLOW.md) en el repo de integración web-cuantica.

---

## 7. Documentación en el portal y en el repo

- **Portal FastFlow:** Los clientes con acceso (link con token) ven en **Documentación** el catálogo de guías (incluida esta y la de integración de Registry). Ahí aparecen las mismas tecnologías: Jenkins, Docker, Registry, Kubernetes, Terraform.
- **Repo open source:** Todo el código y las guías están en el repo. Cualquier persona puede clonar, seguir la [Guía de instalación por escenario](GUIA-INSTALACION-ESCENARIOS.md) o el README de instalación rápida, e instalar el toolkit de forma reproducible.

**[CAPTURA: Página docs.html del sitio FastFlow mostrando el listado de documentos (catálogo) con entradas como "Integración de Registry en Jenkins", "Guía FastFlow: montar local la demo".]**

---

## 8. Resumen de flujo (local ↔ demo AWS)

| Paso | Local | Demo AWS |
|------|--------|----------|
| Java | Java 11 en tu Mac/Linux | Java 11 en la instancia EC2 |
| Jenkins | localhost:8080 (o 8081) | http://&lt;IP&gt;:8080 (luego restringido por IP/VPN) |
| Docker | Docker Desktop / Engine | Docker instalado en el servidor |
| Registry | Docker Hub / registry local / ECR | El que se configure (ej. ECR o Docker Hub) |
| Sitio FastFlow | server + proxy en 3000 + 8080 | Mismo stack; opcional en otra instancia o contenedor |

---

## Referencias en este repo

- [Integración de Registry en Jenkins](REGISTRY-INTEGRATION.md) — Variables, tags y checklist.
- [Guía maestra de instalación por escenario](GUIA-INSTALACION-ESCENARIOS.md) — Docker, Kubernetes, Cloud VMs.
- [Build y Registry (web-cuantica)](../integrations/web-cuantica/docs/BUILD-REGISTRY-FASTFLOW.md) — Build de la imagen del servidor FastFlow.
- Probar sitio en local: en web-cuantica, ver **PROBAR-LOCAL.md**.

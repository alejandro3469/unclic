# pos-online (FastFlow web-cuantica): hecho y por hacer

Estado del pipeline pos-online en Jenkins y de la EC2 **fastflow-jenkins-controller**. Última actualización a partir del trabajo del 13 Mar 2026.

---

## 1. Hecho

### 1.1 Pipeline (Jenkinsfile)

- **Build image** si no hay Docker: el stage no falla; imprime mensaje y continúa (Package → Deploy → Verify).
- Commit en Gitea: `87d326e` — "Pipeline: omitir Build image si docker no está instalado (FastFlow web-cuantica)".

### 1.2 EC2 Jenkins (fastflow-jenkins-controller)

- **IP pública:** 18.119.157.22 (jenkins.unclic.consulting).
- **IP privada (ej.):** 10.0.1.62 (hostname `ip-10-0-1-62`).
- **SO:** Amazon Linux 2 (AL2 EOL 2026-06-30).
- **Swap 1 GB añadido** (13 Mar 2026):
  - `sudo dd if=/dev/zero of=/swapfile bs=1M count=1024`
  - `sudo chmod 600 /swapfile` → `sudo mkswap /swapfile` → `sudo swapon /swapfile`
  - Comprobado con `free -h`: **Swap: 1.0G** (para evitar OOM en `mvn clean compile`).

### 1.3 Documentación creada/actualizada

| Doc | Contenido |
|-----|-----------|
| [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) | Instalar Docker en la EC2 de Jenkins (yum/dnf), usuario `jenkins` en grupo `docker`, reiniciar Jenkins. |
| [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md) | Añadir swap 1 GB en t3.micro; referencia a la IP actual de Jenkins. |
| [COMO-PROBAR-REGISTRY.md](COMO-PROBAR-REGISTRY.md) | Cómo abrir y usar el registry (API HTTP, URLs _catalog y tags/list, dashboard, curl). |
| [REGISTRY-EC2-GRATIS.md](REGISTRY-EC2-GRATIS.md) | Levantar registry:2 en la EC2, puerto 5000, insecure-registries, variable REGISTRY en Jenkins. |
| [RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md) | Enlace a la guía de Docker cuando falla Build image. |
| [docs/README.md](../README.md) | Enlace a POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2 en la sección Pipeline. |

### 1.4 Comprobaciones en la EC2

- `curl -s http://localhost:5000/v2/_catalog` → **sin respuesta**: en esa EC2 el registry **no está levantado** (puerto 5000 sin servicio). Normal si aún no se ha instalado Docker ni ejecutado `docker run ... registry:2`.

---

## 2. Por hacer

### 2.1 Inmediato (para que el pipeline pase de punta a punta)

| # | Acción | Dónde / Cómo |
|---|--------|----------------|
| 1 | **Persistir el swap** | En la EC2 de Jenkins: `echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab` |
| 2 | **Lanzar de nuevo el pipeline** | Jenkins → **pos-online-pipeline** → **Build Now**. Debe completar Prepare → Build → Test → Lint → Package → (Build image omitido si no hay docker) → Deploy → Verify. |
| 3 | **Comprobar la app** | `curl -s http://18.119.157.22:8111/actuator/health` o abrir en navegador (puerto 8111 abierto en Security Group). |

### 2.2 Opcional: Docker y Build image

| # | Acción | Doc / Comandos |
|---|--------|-----------------|
| 4 | Instalar Docker en la EC2 de Jenkins | [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md): `sudo yum install -y docker`, `sudo systemctl start docker`, `sudo usermod -aG docker jenkins`, `sudo systemctl restart jenkins`. |
| 5 | Tras instalar Docker | El siguiente Build Now ejecutará también el stage **Build image** (pos-online:&lt;BUILD_NUMBER&gt;). |

### 2.3 Opcional: Registry (imágenes versionadas, rollback)

| # | Acción | Doc / Comandos |
|---|--------|-----------------|
| 6 | Levantar registry en la EC2 | [REGISTRY-EC2-GRATIS.md](REGISTRY-EC2-GRATIS.md): `sudo docker run -d -p 5000:5000 --restart=always --name registry registry:2`. |
| 7 | Configurar Docker para localhost:5000 (sin TLS) | En la EC2: `insecure-registries` en `/etc/docker/daemon.json`, `sudo systemctl restart docker`, volver a arrancar el contenedor registry. |
| 8 | Variable REGISTRY en Jenkins | Job **pos-online-pipeline** → Configure → Environment: `REGISTRY=localhost:5000`. |
| 9 | Abrir puerto 5000 (acceso externo) | Security Group de la EC2 Jenkins: entrada TCP 5000 (0.0.0.0/0 o tu IP). Luego `curl -s http://18.119.157.22:5000/v2/_catalog`. |
| 10 | Probar / “interfaz” del registry | [COMO-PROBAR-REGISTRY.md](COMO-PROBAR-REGISTRY.md): URLs `.../v2/_catalog` y `.../v2/../60-pos-online/tags/list`; dashboard `deploy/dashboard-demo-jenkins-registry.html`. |

### 2.4 Resumen por prioridad

1. **Ahora:** persistir swap (fstab) y **Build Now** en Jenkins.
2. **Si quieres imágenes Docker:** instalar Docker en la EC2 y reiniciar Jenkins (doc §2.2).
3. **Si quieres registry y push:** levantar registry:2, insecure-registries, REGISTRY en el job, opcionalmente abrir 5000 (doc §2.3).

---

## 3. Referencia rápida

| Qué | Dónde |
|-----|-------|
| Plantilla Jenkins / Gitea / URLs | [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md), [URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md) |
| Swap (OOM) | [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md) |
| Docker en Jenkins | [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) |
| Registry en EC2 | [REGISTRY-EC2-GRATIS.md](REGISTRY-EC2-GRATIS.md) |
| Cómo probar registry | [COMO-PROBAR-REGISTRY.md](COMO-PROBAR-REGISTRY.md) |

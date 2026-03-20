# Documentación maestra — índice y pendientes (solo repos locales)

**Uso:** Referencia única de **qué está documentado**, **estado actual real** (IPs, URLs, servicios) y **qué falta por documentar o actualizar**. Todo anotado aquí **solo en repos locales**; no se envía ni se sube a ningún remoto hasta que decidas hacerlo.

---

## 1. Estado actual (datos reales a fecha de uso)

### 1.1 Servidores EC2 (us-east-2)

| Servidor | Nombre EC2 | IP pública | Hostname interno | URLs | Puertos |
|----------|------------|------------|-------------------|------|---------|
| **Jenkins** | fastflow-jenkins-controller | **18.119.157.22** | ip-10-0-1-62 | https://jenkins.unclic.consulting, :8080 | 22, 8080, 443, **8111** (pos-online) |
| **Gitea** | fastflow-gitea | **13.58.58.245** | — | **https://gitea.unclic.consulting** (HTTPS), :3000 si se usa directo | 22, 80, 443, 3000 |
| **Vantive** | fastflow-vantive | 3.22.236.150 | — | http(s)://vantive.unclic.consulting | 22, 80, 443 |

- **DNS (Namecheap):** A **jenkins** → 18.119.157.22, A **gitea** → 13.58.58.245, A **vantive** → 3.22.236.150.
- **pos-online** corre en la **misma EC2 que Jenkins** (18.119.157.22), puerto **8111**; log en `/tmp/pos-online.log`. Ver [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md).

### 1.2 Gitea (estado actual)

- **URL de acceso:** **https://gitea.unclic.consulting/** (HTTPS, Nginx + Certbot en EC2 13.58.58.245). ROOT_URL en Gitea = `https://gitea.unclic.consulting/`.
- **Repos en Gitea (ejemplos):** alejandro-perez/pos-online, alejandro-perez/smartbussiness-generic-model, alejandro-perez/vantive, alejandro-perez/pos-online-fastflow, alejandro-perez/smartbussiness-generic-model-fastflow, alejandro-perez/fastflow-integration (nombres según lo creado en Gitea).
- **Usuario con acceso solo a un repo:** alejandro-perez3469 (alejandro.perez3469@gmail.com), colaborador **solo** en **fastflow-integration**. Para más repos: Settings → Collaborators en cada repo.
- **Jenkins** hoy clona con `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git`. Opcional: cambiar a `https://gitea.unclic.consulting/alejandro-perez/pos-online.git` (sin puerto).

### 1.3 Jenkins y pipeline pos-online

- **Job:** pos-online-pipeline. Pipeline from SCM, Script Path = Jenkinsfile.
- **Último build exitoso (#20):** Prepare → Build → Test (54 tests) → Lint → Package → Build image (pos-online:20) → Push to registry (skipped, when) → Approve Deploy (manual, aprobado) → Cleanup → Deploy → Verify (actuator/health UP).
- **Deploy:** Manual (Approve Deploy); se ejecuta pkill + nohup java -jar en puerto 8111 en la EC2 de Jenkins; log en `/tmp/pos-online.log`.
- **Ver logs pos-online (en la EC2):** `tail -50 /tmp/pos-online.log`, `tail -f /tmp/pos-online.log`; filtrar ruido: `tail -200 /tmp/pos-online.log | grep -v "Hibernate:"` o `grep -E "INFO|WARN|ERROR"`. Si el prompt es `ec2-user@ip-10-0-1-62`, ya estás en la EC2 correcta (no hace falta ssh a 18.119.157.22 desde esa misma máquina).

---

## 2. Entregables (delivery/) y scripts

### 2.1 Paquetes de entrega

- **Origen:** `scripts/build-delivery-packages.sh` (ejecutar desde `web-cuantica`).
- **Salida:** `delivery/smartbussiness-generic-model`, `delivery/pos-online`, `delivery/fastflow-integration` (sin historial Git; pos-online sin application-envDev/Qas/Prd/Ppr, con application-env.example.properties y README-CONFIGURACION.md).
- **Checklist y auditoría:** [delivery/ANTES-DE-SUBIR-A-GITEA.md](../delivery/ANTES-DE-SUBIR-A-GITEA.md): 1) Ejecutar build-delivery-packages.sh, 2) Buscar referencias propias (grep unclic, IPs, nombres), 3) Revisar properties sensibles, 4) Subir a Gitea (repos separados, sin .git en delivery si se inicializa nuevo en cada uno).
- **Plantilla env:** `scripts/pos-online-application-env.example.properties` (copiada a pos-online en delivery).

### 2.2 Scripts relevantes (web-cuantica/scripts/)

| Script | Propósito |
|--------|-----------|
| build-delivery-packages.sh | Genera los 3 directorios en delivery/ (generic-model, pos-online, fastflow-integration) sin .git/target, sanitiza env y README. |
| pos-online-application-env.example.properties | Plantilla de configuración por entorno para pos-online (delivery). |
| push-repo-pos-fastflow-to-gitea.sh | Push de repo-pos-fastflow a Gitea (origin). |
| push-generic-model-to-gitea.sh | Push de smartbussiness-generic-model a Gitea (gitea). |
| validate-jenkinsfile.sh | Valida Jenkinsfile. |
| validate-terraform.sh | Valida Terraform. |
| validate-k8s.sh | Valida manifiestos K8s. |
| run-all-validations.sh | Ejecuta todas las validaciones. |
| simulate-jenkins-pipeline.sh | Simula pipeline local. |
| test-registry.sh | Prueba registry Docker. |
| clone-pos-online.sh | Clona pos-online. |
| deploy-vantive-ec2.sh | Despliega Vantive en EC2 (Nginx, rsync). |
| load-config.sh, generate-config-ui.js, serve-config-ui.js | Config central y UI (config/fastflow-config.json, deploy/config-ui.html). |

---

## 3. Repos locales vs Gitea

- **repo-pos-fastflow** → push a Gitea como pos-online (remote `origin`). Ruta: `.../web-cuantica/repo-pos-fastflow`.
- **smartbussiness-generic-model** → push a Gitea como smartbussiness-generic-model (remote `gitea`). Ruta: `.../web-cuantica/smartbussiness-generic-model`.
- **Repos en Gitea subidos como entregables:** pos-online-fastflow, smartbussiness-generic-model-fastflow, fastflow-integration (nombres según creación en Gitea). Ver delivery/ANTES-DE-SUBIR-A-GITEA.md.
- Detalle de remotes y comandos: [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md), [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md).

---

## 4. Documentación existente (índice rápido)

- **Índice del know-how (qué está documentado y dónde):** [INDICE-KNOW-HOW.md](INDICE-KNOW-HOW.md).
- **Índice general:** [docs/README.md](README.md).
- **Replicar estado actual:** [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md), [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md).
- **Ver instancia y logs pos-online:** [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md).
- **Pipeline y logs del job:** [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md).
- **Docker en EC2 Jenkins:** [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md).
- **HTTPS Gitea/Jenkins:** [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md), [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md).
- **Servidores y Vantive:** [SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md).
- **Infraestructura (referencia):** [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) — **tiene IPs antiguas**, ver §5.
- **Generic model y dependencia:** [DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md), [GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md).
- **AGENTS.md (raíz):** Guía para agentes IA (pos-online, generic-model, FastFlow, archivos clave, comandos).

---

## 5. Documentos con IPs o datos desactualizados (pendiente de actualizar)

Actualizar cuando quieras que la doc refleje el estado real. **No se envía nada a remoto**; solo editar en local.

| Documento | Qué está desactualizado | Valor actual correcto |
|-----------|-------------------------|------------------------|
| **INFRAESTRUCTURA-UNCLIC-ACTUAL.md** | Jenkins 3.15.4.160, Gitea 18.223.114.68 | Jenkins **18.119.157.22**, Gitea **13.58.58.245** |
| **REPLICAR-ESTADO-ACTUAL-INDICE.md** | Tabla §1: Jenkins 3.15.4.160, Gitea 18.223.114.68 | Usar 18.119.157.22 y 13.58.58.245; Gitea con HTTPS |
| **URLS-Y-EC2-PRUEBAS.md** | IP 3.15.4.160 para Jenkins y app | 18.119.157.22 (Jenkins y 8111) |
| **REPLICAR-UNCLIC-COMPLETO.md** | Gitea IP 18.223.114.68 | 13.58.58.245; mencionar HTTPS gitea.unclic.consulting |
| **DOMINIO-NAMECHEAP-UNCLIC-EC2.md** | Gitea 18.223.114.68, Jenkins 3.15.4.160 | 13.58.58.245, 18.119.157.22 |
| **REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md** | Referencia Gitea 18.223.114.68 | 13.58.58.245 |
| **PASO-1-GUIA-HTTPS-DESDE-CERO.md** | EC2 Gitea 18.223.114.68 | 13.58.58.245 |
| **RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md** | fastflow-gitea 18.223.114.68 | 13.58.58.245 |

Los siguientes **ya tienen las IPs correctas** (18.119.157.22, 13.58.58.245 donde aplica): REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md, SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md, POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md, POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md, POS-ONLINE-FASTFLOW-HECHO-Y-POR-HACER.md, EC2-SWAP-T3MICRO.md, COMO-PROBAR-REGISTRY.md.

---

## 6. Pendientes por documentar o anotar (opcional)

- **Jenkins URL del job:** Opcionalmente documentar el cambio de Repository URL a `https://gitea.unclic.consulting/alejandro-perez/pos-online.git` (ya documentado en §1.2 como opcional).
- **Gitea: usuario solo un repo:** Ya anotado en §1.2; si quieres doc dedicada: “Crear usuario en Gitea y dar acceso solo a un repo” (Settings → Collaborators).
- **Handoff / rama handoff:** Si existe rama o handoff para otro desarrollador (ej. George), anotar en un doc de handoff o en este maestro la ruta y el propósito.
- **Pipeline #21+:** Cuando haya nuevos builds, los números y resultado se pueden seguir consultando en Jenkins; este doc no necesita actualizarse por cada build.
- **Recuperar docs del historial:** [COMPATIBILIDAD-Y-HISTORIAL.md](COMPATIBILIDAD-Y-HISTORIAL.md), [historial/README.md](historial/README.md) — cómo recuperar archivos que solo existen en commits anteriores.

---

## 7. Resumen de acciones “solo local”

| Acción | Dónde |
|--------|--------|
| Consultar estado actual (IPs, Gitea HTTPS, logs, delivery) | Este doc (§1–§3). |
| Ver logs pos-online | SSH a 18.119.157.22 → `tail -f /tmp/pos-online.log` (o filtros §1.3). |
| Generar entregables | `bash scripts/build-delivery-packages.sh`; revisar delivery/ANTES-DE-SUBIR-A-GITEA.md. |
| Actualizar IPs en docs | Editar en local los archivos listados en §5 (cuando decidas). |
| No enviar nada a remoto | No push, no deploy, hasta que lo indiques. |

---

*Documento creado para anotar todo lo relevante en repos locales. Actualizar este archivo cuando cambien IPs, URLs, servicios o entregables.*

# Rollback del POS con Docker + registry (FastFlow)

## Idea

Cada build del pipeline principal publica la imagen con **dos tags**:

- **`pos-online:<BUILD_NUMBER>`** — inmutable; es el que usas para **volver atrás**.
- **`pos-online:latest`** — apunta al último push (útil, pero no basta para rollback preciso).

El rollback **no recompila** el JAR: solo **pull** de una imagen ya existente en el registry y **reemplaza** el contenedor en el servidor POS.

---

## Comando manual (en el servidor POS o vía SSH)

Sustituye `TU_REGISTRY` y `NUMERO_BUILD_ANTERIOR` (ej. `41`):

```bash
docker stop pos-online && docker rm pos-online
docker run -d --name pos-online -p 8111:8111 --restart=unless-stopped \
  TU_REGISTRY/pos-online:NUMERO_BUILD_ANTERIOR
```

Antes, si la imagen no está en el disco del servidor:

```bash
docker pull TU_REGISTRY/pos-online:NUMERO_BUILD_ANTERIOR
```

**Ejemplo** con registry en la misma VPC (`REGISTRY=10.0.1.50:5000`, tag `42`):

```bash
docker pull 10.0.1.50:5000/pos-online:42
docker stop pos-online && docker rm pos-online
docker run -d --name pos-online -p 8111:8111 --restart=unless-stopped \
  10.0.1.50:5000/pos-online:42
```

Comprobar:

```bash
curl -sf http://127.0.0.1:8111/actuator/health || curl -sf http://127.0.0.1:8111/health
```

Desde fuera (DNS o IP pública del POS, p. ej. `pos.unclic.consulting` → **13.58.172.235** en tu Namecheap para `fastflow-pos-demo`):

```bash
curl -sf http://13.58.172.235:8111/health
```

---

## Job Jenkins opcional: `pos-online-rollback` (o `rollbackInstances`)

En lugar de entrar a mano al servidor, crea un **segundo job** en Jenkins:

| Campo | Valor |
|-------|--------|
| **New Item** | Pipeline, nombre sugerido: `pos-online-rollback` |
| **Definition** | Pipeline script from SCM |
| **Repo** | Mismo que `pos-online-pipeline` (Gitea `pos-online` / `pos-online-fastflow`) |
| **Branch** | `main` |
| **Script Path** | **`Jenkinsfile.rollback`** |

La primera vez que guardes, Jenkins cargará los **parámetros** definidos en ese archivo (tag, registry, host, credencial SSH).

### Parámetros (resumen)

| Parámetro | Ejemplo | Uso |
|-----------|---------|-----|
| `ROLLBACK_IMAGE_TAG` | `41` | Tag **numérico** del build que quieres restaurar (mismo que en el registry). |
| `REGISTRY` | `IP:5000` o `registry.unclic.consulting:443` | Igual que en el job de build. |
| `POS_DEPLOY_HOST` | `3.129.247.127` o `pos.unclic.consulting` | Instancia **fastflow-pos** (o la que ejecute el contenedor). |
| `POS_DEPLOY_USER` | `ec2-user` | Usuario SSH con permiso `docker`. |
| `POS_DEPLOY_KEY_CREDENTIAL_ID` | `pos-deploy-key` | Credencial Jenkins tipo **Secret file** (.pem). |
| `APP_PORT` | `8111` | Puerto host → contenedor. |
| `IMAGE_NAME` | `pos-online` | Nombre de imagen (coincide con el Jenkinsfile principal). |

Tras **Build with Parameters**, el job hace SSH, `docker pull`, `stop/rm`, `run` y un `curl` de verificación.

---

## Cómo saber qué tag usar

1. En **Jenkins**, abre el build **bueno anterior** y mira el número de build (ej. **#41**). El tag de imagen suele ser **`41`** si usas `IMAGE_TAG = BUILD_NUMBER`.
2. En el **registry**, lista tags (UI o `curl` API v2) y elige el tag antes del deploy malo.
3. Anota en un runbook: “Producción estable = tag **57**” tras cada release validado.

---

## Relación con otros documentos

| Documento | Contenido |
|-----------|-----------|
| `Jenkinsfile` | Pipeline principal: push de `:${BUILD_NUMBER}` y `:latest`. |
| `Jenkinsfile.rollback` | Job opcional solo rollback (este repo). |
| `ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md` | Variables `REGISTRY`, `POS_DEPLOY_*`, credencial SSH. |
| `docs/CHECKLIST-DOMINIO-JENKINS-HTTPS-ROLLBACK.md` (toolkit) | DNS, HTTPS, EIP, checklist general. |

---

## Varias instancias POS (“1/6”)

Si tienes **varias** EC2 con el mismo rol:

- Crea **un job de rollback por instancia** (distinto `POS_DEPLOY_HOST` por defecto), **o**
- Mismo job y en cada ejecución eliges el **host** en el parámetro `POS_DEPLOY_HOST`.

No hace falta un sexto archivo por instancia: el parámetro es el selector lógico.

---

## Seguridad

- Limita quién puede ejecutar el job de rollback (Matrix / Role strategy).
- Los tags antiguos en el registry son **sensibles** (código desplegable); protege el registry y el Jenkins.

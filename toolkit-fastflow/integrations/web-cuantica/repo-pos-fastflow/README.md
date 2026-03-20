# POS Online FastFlow — repo para Gitea + Jenkins

Repositorio con la **versión completa** del POS (monolito `mx.com.endtoend`), listo para **Gitea** y **Jenkins**: **commit → build → test → package → deploy** en ~2–5 minutos. Incluye **Terraform**, **Kubernetes** y **Registry** para levantar el flujo con solo open source.

**Guía única (dominio Namecheap, EC2, Jenkins, Postman, rollback):** en el toolkit → **[../docs/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../docs/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)**.

## Qué incluye

| Componente | Uso |
|------------|-----|
| **POS completo** | Código fuente `mx.com.endtoend` (application, domain, infrastructure), Spring Boot 2.6.3, Java 11, puerto 8111 |
| **Jenkinsfile** | Prepare → Build → Test → Package → Build image → Push to registry → [opcional] IaC (Terraform K8s / LocalStack, Pulumi) → [aprobación] → Deploy → Verify |
| **Jenkinsfile.rollback** | Job **opcional** en Jenkins: parámetros (tag de imagen, `REGISTRY`, host POS, credencial SSH) → `docker pull` + `stop/rm` + `run` + verificación. Ver [ROLLBACK-POS-DOCKER-REGISTRY.md](ROLLBACK-POS-DOCKER-REGISTRY.md). |
| **`.jenkins/`** | Plan FastFlow (`PLAN-FASTFLOW-PIPELINE-IAC.md`), Groovy helpers, scripts bash para Terraform/Pulumi |
| **deploy/terraform** | Despliegue en cluster K8s existente (imagen pos-online) |
| **deploy/terraform-localstack** | Laboratorio S3 contra LocalStack (no sustituye K8s) |
| **deploy/pulumi** | Stack TypeScript (bucket lab; enlace opcional en UnClic `NEXT_PUBLIC_DEMO_PULUMI_URL`) |
| **deploy/k8s** | Namespace, Deployment, Service (puerto 8111) |
| **Dockerfile** | Imagen para registry y despliegue como contenedor |
| **Registry** | Variable `REGISTRY` en el job: obligatoria para deploy remoto (push y pull de la imagen) |

**Dependencia obligatoria:** el build requiere **generic-model** (`mx.com.endtoend.smart.bussiness.model:smartbussiness-generic-model:1.0.1-SNAPSHOT`). Debe estar instalado en el repositorio Maven local o en un repo al que Jenkins tenga acceso (p. ej. publicar antes desde el repo generic-model en la misma carpeta padre).

## En 2–5 min: subir a Gitea y probar

1. **Crear repo en Gitea**  
   En http://gitea.unclic.consulting:3000 → **New Repository** → nombre `pos-online` (o el que uses). Anotar la URL, ej. `http://gitea.unclic.consulting:3000/TU_USUARIO/pos-online.git`.

2. **Desde este repo local** (dentro de `repo-pos-fastflow`), o desde el toolkit con el script:
   ```bash
   # Opción A: script (desde toolkit-fastflow/integrations/web-cuantica)
   export GITEA_USER=alejandro-perez
   export GITEA_REPO=pos-online-fastflow
   bash scripts/push-repo-pos-fastflow-to-gitea.sh
   ```
   ```bash
   # Opción B: a mano (desde repo-pos-fastflow)
   git init
   git add .
   git commit -m "FastFlow: POS completo (mx.com.endtoend), Jenkins, Terraform, K8s, Registry"
   git remote add gitea http://gitea.unclic.consulting:3000/TU_USUARIO/pos-online.git
   git branch -M main
   git push -u gitea main
   ```
   Antes: crear el repo vacío en Gitea (sin "Initialize Repository").

3. **Jenkins**  
   En http://jenkins.unclic.consulting:8080 → job **pos-online-pipeline** (o New Item → Pipeline).  
   Configure → **Pipeline from SCM** → Git → **Repository URL** = la URL de Gitea anterior. **Branch** = `main`, **Script Path** = `Jenkinsfile`. Guardar.

   **Rollback (opcional):** otro job **Pipeline from SCM** con el mismo repo y **Script Path** = `Jenkinsfile.rollback` (nombre sugerido: `pos-online-rollback`). Parámetros al ejecutar: tag del build anterior, `REGISTRY`, `POS_DEPLOY_HOST`, credencial SSH. Detalle: [ROLLBACK-POS-DOCKER-REGISTRY.md](ROLLBACK-POS-DOCKER-REGISTRY.md).

4. **Build Now**  
   En el job, **Build Now**. Revisar **Console Output**: Prepare → Build → Test → Package → Build image → (Push to registry si `REGISTRY` definido) → Aprobación → Cleanup → Deploy (contenedor Docker) → Verify.

5. **Comprobar app y logs**  
   - App: http://&lt;EC2&gt;:8111/health (IP de la EC2 Jenkins o del servidor POS si deploy remoto).  
   - Deploy local: `docker logs pos-online` en la EC2 Jenkins.  
   - Peticiones: `curl http://<EC2>:8111/health`.

## Tests de build y peticiones a la instancia

- **Tests en pipeline:** el stage **Test** ejecuta `mvn test` (suite completa del POS).  
- **Despliegue con Docker y registry:** el pipeline construye la imagen, la sube al registry (si `REGISTRY` está definido) y despliega un contenedor (local: `docker run` en el agente; remoto: `docker pull` + `docker run` en el servidor POS; para remoto es obligatorio definir `REGISTRY`).
- **Peticiones a la instancia:** tras **Deploy**, el stage **Verify instance** hace `curl` a `http://localhost:8111/health` (o al host remoto). Desde fuera: `curl http://<EC2>:8111/health`.

## Estado del repo

Este repo contiene la **versión completa** del POS (código bajo `src/main/java/mx/com/endtoend/`). Métricas en **`docs/METRICAS-POS-ONLINE-COMPLETO-VS-REPO-FASTFLOW.md`** (toolkit web-cuantica).

**Auditoría Jenkins + Registry:** [AUDITORIA-JENKINS-REGISTRY.md](AUDITORIA-JENKINS-REGISTRY.md) — comprobación de que el repo está listo y qué falta (generic-model, REGISTRY, registry en EC2). **Registry gratis en EC2:** [docs/REGISTRY-EC2-GRATIS.md](../docs/REGISTRY-EC2-GRATIS.md).

**Deploy en otro servidor (instancia dedicada al POS):** [ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md](ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md) — elementos a crear (EC2 POS, credencial SSH, variables del job) y cómo el pipeline despliega en un servidor remoto cuando se definen `POS_DEPLOY_HOST` y la credencial.

**Rollback por imagen en registry (sin recompilar):** [ROLLBACK-POS-DOCKER-REGISTRY.md](ROLLBACK-POS-DOCKER-REGISTRY.md) — comandos manuales y job Jenkins `Jenkinsfile.rollback`.

## Terraform y Kubernetes

- **deploy/terraform:** despliega el POS en un cluster Kubernetes **existente** (namespace + deployment + service). Uso: `terraform init && terraform plan -var="pos_image=REGISTRY/pos-online:TAG" && terraform apply`.
- **deploy/k8s:** mismos recursos en YAML para `kubectl apply -f deploy/k8s/`.
- **Plan completo (Terraform + K8s + Jenkins):** [docs/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md](../docs/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md) — fases para infra con Terraform (EC2/EKS), despliegue del POS en K8s y stage Jenkins "Deploy to Kubernetes".

## Referencias

- Plan demo: `docs/PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md` (en el toolkit web-cuantica).
- Plan Terraform + Kubernetes: [docs/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md](../docs/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md).
- Pulido multiagentes: **PULIDO-MULTIAGENTES.md** en este repo.

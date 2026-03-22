# Plan: subdominio para landing + sección Demos

**Objetivo:** publicar un **subdominio** (ej. `landing.<TU_DOMINIO>` o `demos.<TU_DOMINIO>`) con el sitio estático o Next export, y enlazar desde el hub **solo demos de solo lectura** (Jenkins, Gitea, POS, registry, K8s, Terraform) ya existentes.

**Dónde hospedar:** una **EC2 con Nginx** (puede ser la misma del POS con varios `server_name`, o una instancia dedicada). Ver [EC2-CREAR-LANDING-UNCLIC.md](../20-operaciones/EC2-CREAR-LANDING-UNCLIC.md) y [unclic/README.md](../../unclic/README.md).

**DNS:** registro **A** del subdominio → IP pública de esa EC2.

**Automatización:** job en **Jenkins** (Pipeline from SCM) que construye (`npm ci && npm run build` o copia de `out/`) y despliega por **SSH/rsync** al directorio servido por Nginx. Variables típicas: `DEPLOY_HOST`, `DEPLOY_USER`, `DEPLOY_PATH`.

**Gitea:** el código del landing vive en un repo tuyo (nombre a elegir); Jenkins clona con credencial si el repo es privado.

**Checklist (resumido):**

- [ ] DNS A → `<IP_LANDING>`
- [ ] Nginx: `server_name` del subdominio + `root` o proxy
- [ ] TLS: Certbot si expones HTTPS público
- [ ] Jenkins: job con deploy SSH probado
- [ ] En el hub, enlaces **solo lectura** a las URLs de demo (sin credenciales en el repo)

**Relacionado:** [REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md](../20-operaciones/REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md), [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](../20-operaciones/INFRAESTRUCTURA-UNCLIC-ACTUAL.md).

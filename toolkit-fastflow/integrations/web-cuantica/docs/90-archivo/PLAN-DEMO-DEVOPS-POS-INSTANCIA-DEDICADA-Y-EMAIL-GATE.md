# Plan detallado: Demo DevOps (POS en instancia dedicada, email gate, acceso invitado) y luego Terraform/Kubernetes

Objetivo: **primero** subir el POS a una **instancia dedicada solo al POS**, probar el deploy como si fuera producción, y dejar **Jenkins y Gitea libres** para que los usuarios accedan a la demo de DevOps **después de dejar solo su correo electrónico**, entrando con **usuario invitado**. **Después** de eso: Terraform y Kubernetes.

---

## Orden de fases (resumen)

| Fase | Qué | Cuándo |
|------|-----|--------|
| **1** | POS en instancia EC2 dedicada (solo POS) | Primero |
| **2** | Probar deploy y “go-live” como producción (Jenkins → instancia POS) | Tras Fase 1 |
| **3** | Email gate (solo correo) → acceso a demo con usuario invitado (Jenkins, Gitea, POS) | Tras Fase 2 |
| **4** | Terraform y Kubernetes | Después de dejar la demo lista |

---

## Fase 1 — POS en instancia dedicada (solo POS)

Objetivo: una EC2 que **solo** sirva la aplicación POS; sin Jenkins ni Gitea en esa máquina.

### 1.1 Provisionar EC2 dedicada al POS

| Paso | Acción | Dónde | Notas |
|------|--------|-------|--------|
| 1.1.1 | Crear instancia EC2 (Amazon Linux 2023 o similar) en la misma VPC/región que Jenkins y Gitea | AWS Console → EC2 → Launch Instance | t3.small o t3.micro según carga; nombre ej. `fastflow-pos-demo` |
| 1.1.2 | Security group: SSH (22) desde tu IP o bastion; HTTP (80) y HTTPS (443) desde 0.0.0.0/0; puerto de la app (ej. 8080 o 8111) solo desde Nginx en la misma instancia o desde Jenkins si deploy por SSH | EC2 → Security Groups | Evitar abrir el puerto de la app a todo Internet si Nginx hace proxy |
| 1.1.3 | Par de claves (.pem) para SSH; anotar IP pública (ej. POS_EC2_IP) | EC2 | La usará Jenkins para deploy por SSH/rsync si aplica |
| 1.1.4 | DNS: crear registro **A** `pos` → IP de esta EC2 | Namecheap → unclic.consulting → Advanced DNS | `pos.unclic.consulting` apuntará a esta instancia |

### 1.2 Preparar la EC2 POS (solo app + Nginx)

| Paso | Acción | Comandos / referencia |
|------|--------|------------------------|
| 1.2.1 | Conectar por SSH a la nueva EC2 | `ssh -i tu-clave.pem ec2-user@POS_EC2_IP` |
| 1.2.2 | Instalar Java (si el POS es JAR) o Docker (si el POS va en contenedor) | `sudo dnf install java-17-amazon-corretto` o instalar Docker |
| 1.2.3 | Crear directorio de deploy (ej. `/opt/pos` o `/home/ec2-user/pos`) y permisos | `sudo mkdir -p /opt/pos`, `sudo chown ec2-user:ec2-user /opt/pos` |
| 1.2.4 | Instalar Nginx como reverse proxy para la app | `sudo dnf install nginx` |
| 1.2.5 | Configurar Nginx: `server_name pos.unclic.consulting`; `proxy_pass http://127.0.0.1:8111` (o el puerto donde corra el JAR/contenedor) | `/etc/nginx/conf.d/pos.conf` | Ver [HTTPS-UNCLIC-GITEA-JENKINS](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md) como referencia de proxy |
| 1.2.6 | Certbot para HTTPS | `sudo certbot --nginx -d pos.unclic.consulting` |
| 1.2.7 | (Opcional) Usuario de sistema para ejecutar la app (no root) | `sudo useradd -r -s /bin/false posapp` |

Resultado: EC2 con Nginx y HTTPS en `pos.unclic.consulting`, lista para recibir el deploy del POS (JAR o imagen Docker).

### 1.3 Ajustar pipeline (Jenkins) para desplegar en la instancia POS

| Paso | Acción | Dónde |
|------|--------|--------|
| 1.3.1 | En Jenkins: credenciales SSH con la clave .pem de la EC2 POS (o usuario + clave) para `ec2-user@POS_EC2_IP` | Manage Jenkins → Credentials |
| 1.3.2 | En el Jenkinsfile del POS: stage **Deploy** debe copiar el artefacto (JAR o imagen) a la EC2 POS y (re)iniciar el servicio allí (rsync + systemd, o docker pull + docker run) | Repo pos-online / repo-pos-fastflow |
| 1.3.3 | Variables de entorno o parámetros del job: `POS_HOST=POS_EC2_IP`, `POS_USER=ec2-user`, `POS_DEPLOY_PATH=/opt/pos` | Jenkins job → Configure |

Referencia: [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2](../20-operaciones/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md), [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO](../20-operaciones/REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md).

---

## Fase 2 — Probar deploy y “go-live” como producción

Objetivo: validar que un commit (o Build Now) ejecuta el pipeline y deja el POS funcionando en la instancia dedicada, como si fuera producción.

### 2.1 Checklist de validación

| Paso | Acción | Cómo comprobar |
|------|--------|----------------|
| 2.1.1 | Ejecutar pipeline (push a main o Build Now) | Jenkins: job pos-online → consola sin errores |
| 2.1.2 | Verificar que el artefacto llega a la EC2 POS | SSH a POS: `ls -la /opt/pos` (o ruta configurada); proceso Java/Docker corriendo |
| 2.1.3 | Verificar que la app responde vía Nginx | `curl -I https://pos.unclic.consulting` → 200 |
| 2.1.4 | Probar un segundo deploy (cambio mínimo + commit) | Mismo flujo; app actualizada en pos.unclic.consulting |
| 2.1.5 | Documentar URLs y credenciales de servicio (para el equipo) | Jenkins URL, Gitea URL, POS URL; usuario invitado (siguiente fase) |

### 2.2 Dejar Jenkins y Gitea “libres” para la demo

- **Jenkins** y **Gitea** siguen en sus instancias actuales (o las que uses).
- Asegurar que existen **usuarios invitado** en ambos con acceso solo a repos/jobs del POS (ver [GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO](../20-operaciones/GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md)).
- No hace falta cambiar URLs; la landing ya puede enlazar a `https://jenkins.unclic.consulting`, `https://gitea.unclic.consulting`, `https://pos.unclic.consulting` una vez que el usuario haya pasado la puerta de email.

---

## Fase 3 — Email gate (solo correo) y acceso a la demo con usuario invitado

Objetivo: el usuario **solo deja su correo electrónico**; después puede acceder a la demo de DevOps (Jenkins, Gitea, POS) con **usuario invitado**.

### 3.1 Flujo de usuario

1. Usuario entra en la landing (unclic.consulting) y hace clic en “Probar la demo” o similar.
2. **Antes** de ver enlaces a Jenkins/Gitea/POS: se muestra un formulario o modal pidiendo **solo el correo electrónico**.
3. Tras enviar el correo: se muestra la página de demo (o se redirige a `/demo`) con:
   - Enlaces a **Jenkins**, **Gitea**, **POS** (ya implementado en `unclic/app/demo/page.tsx` y `GuestCredentialsBlock`).
   - Bloque de **credenciales de usuario invitado** (usuario/contraseña para Jenkins y Gitea; POS puede ser público o con invitado si aplica).
4. El usuario abre Jenkins, Gitea y POS en pestañas y entra con las credenciales invitado.

### 3.2 Implementación del email gate

| Paso | Acción | Dónde / técnica |
|------|--------|------------------|
| 3.2.1 | Definir punto de captura: “Probar la demo” → no ir directo a `/demo`; ir a una ruta intermedia o mostrar modal | Landing UnClic: botón CTA “Probar la demo”, enlace en TopBanner |
| 3.2.2 | Crear página o modal “Deja tu correo para acceder a la demo”: un solo campo (email) + botón Enviar | Nueva ruta ej. `/demo/access` o componente modal en `/` |
| 3.2.3 | Backend o serverless para guardar el correo (opciones: API route Next.js → base de datos o hoja de cálculo; Formspree; Google Sheets; Lambda + DynamoDB) | Decisión según stack: si Next.js en Vercel, API route + servicio externo; si EC2, API en la misma app o microservicio) |
| 3.2.4 | Tras enviar el correo: guardar en sesión/cookie que “ya dejó el correo” y redirigir a `/demo` (o mostrar la misma página de demo con enlaces y credenciales invitado) | Cookie o sessionStorage; redirect a `/demo` |
| 3.2.5 | En `/demo`: si no hay “acceso concedido” (cookie/sesión), redirigir a la página de captura de email | Middleware o check en `demo/page.tsx` |
| 3.2.6 | (Opcional) Email de bienvenida con los enlaces y credenciales invitado | Servicio de correo (SES, SendGrid, etc.) o manual |

### 3.3 Credenciales invitado (ya documentado)

- Mantener **un solo usuario invitado** en Gitea y otro en Jenkins, con acceso **solo a repos/jobs del POS**.
- En la página `/demo` seguir mostrando el bloque de credenciales (componente `GuestCredentialsBlock`) y los enlaces a Jenkins, Gitea y POS.
- Referencia: [GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO](../20-operaciones/GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md).

### 3.4 Resumen Fase 3

| Entregable | Descripción |
|-------------|-------------|
| Página/modal “Solo correo” | Usuario no ve enlaces a Jenkins/Gitea/POS hasta dejar el correo |
| Almacenamiento de correos | BD, Sheet o servicio elegido |
| Redirección a `/demo` | Tras enviar correo, usuario llega a la página con enlaces y credenciales invitado |
| `/demo` protegida | Si no ha pasado por la captura, redirigir a “deja tu correo” |

---

## Fase 4 — Terraform y Kubernetes (después)

Objetivo: **una vez la demo DevOps (POS + Jenkins + Gitea + email gate + invitado) esté lista**, pasar a infraestructura como código y, cuando corresponda, Kubernetes.

### 4.1 Terraform

| Paso | Acción | Alcance |
|------|--------|---------|
| 4.1.1 | Definir módulos Terraform para: VPC (si no existe), security groups, EC2 para POS, EC2 para Jenkins, EC2 para Gitea (o reutilizar los actuales como recurso importado) | `toolkit-fastflow/manifests/terraform/` o en `web-cuantica/deploy/terraform/` |
| 4.1.2 | Variables para IPs, nombres, dominios; salidas para IPs y nombres de instancias | tfvars, outputs |
| 4.1.3 | Documentar cómo levantar desde cero (terraform apply) y cómo importar estado actual si las EC2 ya existen | README en la carpeta Terraform |
| 4.1.4 | (Opcional) Terraform para Route53 si se usa DNS en AWS; si Namecheap sigue siendo manual, dejarlo documentado | Según decisión DNS |

Referencias: [CAPITULO-DESPLIEGUE-OPERACION-AWS](CAPITULO-DESPLIEGUE-OPERACION-AWS.md), [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO](../20-operaciones/DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md).

### 4.2 Kubernetes

| Paso | Acción | Alcance |
|------|--------|---------|
| 4.2.1 | Cuando se decida usar K8s: definir manifiestos (Deployments, Services, Ingress) para POS, y opcionalmente Jenkins/Gitea si se migran a K8s | `deploy/k8s/` o equivalente |
| 4.2.2 | Cluster: EKS o K8s en EC2 (kubeadm); documentar acceso (kubeconfig) y CI (Jenkins pipeline que haga deploy a K8s) | Docs + pipeline |
| 4.2.3 | Registry de imágenes: ya documentado en el repo; el pipeline puede construir imagen, push a registry y desplegar en K8s | [COMO-PROBAR-REGISTRY](../20-operaciones/COMO-PROBAR-REGISTRY.md), [BUILD-REGISTRY-FASTFLOW](../40-pipeline-registry/BUILD-REGISTRY-FASTFLOW.md) |

---

## Orden de ejecución recomendado (checklist alto nivel)

```
[ ] Fase 1.1  Provisionar EC2 dedicada al POS (IP, SG, DNS pos.unclic.consulting)
[ ] Fase 1.2  Preparar EC2 POS: Java/Docker, Nginx, HTTPS
[ ] Fase 1.3  Ajustar Jenkinsfile y credenciales para deploy a EC2 POS
[ ] Fase 2.1  Probar pipeline completo y validar https://pos.unclic.consulting
[ ] Fase 2.2  Confirmar usuarios invitado en Jenkins y Gitea (solo POS)
[ ] Fase 3.2  Implementar email gate (solo correo) y protección de /demo
[ ] Fase 3.3  Revisar copy y UX: “Deja tu correo” → luego acceso a demo
[ ] Fase 4.1  Terraform para EC2 (y opcional VPC/SG)
[ ] Fase 4.2  Kubernetes cuando se decida (manifiestos, EKS o K8s en EC2)
```

---

## Documentos relacionados

| Tema | Documento |
|------|-----------|
| Demo POS + dominio + suite | [PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA](PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md) |
| Usuario invitado y enlaces demo | [GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO](../20-operaciones/GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md) |
| HTTPS Gitea/Jenkins | [HTTPS-UNCLIC-GITEA-JENKINS](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md) |
| Replicar POS FastFlow completo | [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO](../20-operaciones/REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md) |
| Deploy POS con Jenkins en EC2 | [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2](../20-operaciones/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) |
| Índice documentación | [README](../README.md) |

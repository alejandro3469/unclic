# Demos UnClic en producción — flujo end-to-end (HTTPS, correo, commit → POS)

Objetivo: que **tú** (p. ej. `alejandro@unclic.consulting`) puedas probar **en remoto** cada pieza con **HTTPS**, y tener documentado el camino **commit → build → imagen → registry → deploy POS** (Jenkins, Linux, Docker, opcional K8s / Terraform / Pulumi).

> **Importante:** Este repo puede definir **URLs, copy y gates en el sitio estático**. Levantar Jenkins, Gitea, registry, POS, certificados TLS y cluster lo haces en **tus EC2 / DNS** (o LocalStack solo para laboratorio). No sustituye al índice del toolkit: [../../docs/README.md](../../docs/README.md).

---

## 1. Mapa de URLs públicas (todas con HTTPS)

| Pieza | Host típico | Qué debe responder |
|--------|-------------|-------------------|
| Sitio UnClic | `https://unclic.consulting` | Estáticos desde `out/` (Nginx). |
| Login / demo | `https://unclic.consulting/login`, `/demo`, `/demo/access` | Gate + sesión según [DEMO-ACCESO-ALLOWLIST-INFRA.md](DEMO-ACCESO-ALLOWLIST-INFRA.md). |
| Jenkins | `https://jenkins.unclic.consulting` | UI Jenkins, job Pipeline POS. |
| Gitea | `https://gitea.unclic.consulting` | Repo `pos-online` (+ generic-model), webhook a Jenkins. |
| Registry | `https://registry.unclic.consulting` | Docker Registry v2 (API + UI si instalaste). |
| POS | `https://pos.unclic.consulting` | App Java o contenedor detrás de Nginx/reverse proxy. |
| K8s (opcional) | `https://k8s.unclic.consulting` o dashboard | Solo si expones dashboard/ingress con TLS; **no obligatorio** para la primera demo. |
| Terraform (opcional) | URL de solo lectura | Estado en remoto, CI artifact, o doc enlazado. |
| Pulumi (opcional) | `https://app.pulumi.com/...` | Stack de org/proyecto con historial. |
| Cloudcraft | Share link con `?key=` | [GUIA-CLOUDCRAFT](../../../../docs/GUIA-CLOUDCRAFT-COMPLETA.md) en `toolkit-fastflow/docs/`. |

**Checklist rápido:** desde el móvil (4G), abre cada URL; el candado del navegador debe ser válido (Let’s Encrypt u otro).

---

## 2. Variables de entorno del sitio (build de producción)

Copia `.env.example` → `.env.local`, luego en el servidor de build (o CI) las mismas como `export` antes de `npm run build`.

### Acceso con tu correo

```bash
NEXT_PUBLIC_DEMO_ACCESS_MODE=allowlist
NEXT_PUBLIC_DEMO_ALLOWED_EMAILS=alejandro@unclic.consulting
# Opcional preview interno:
# NEXT_PUBLIC_DEMO_UNLOCK_TOKEN=token-largo-secreto
```

### URLs de demos (deben coincidir con DNS reales)

```bash
NEXT_PUBLIC_DEMO_JENKINS_URL=https://jenkins.unclic.consulting
NEXT_PUBLIC_DEMO_GITEA_URL=https://gitea.unclic.consulting
NEXT_PUBLIC_DEMO_POS_URL=https://pos.unclic.consulting
NEXT_PUBLIC_DEMO_REGISTRY_URL=https://registry.unclic.consulting
# Opcionales cuando existan:
# NEXT_PUBLIC_DEMO_K8S_URL=https://...
# NEXT_PUBLIC_DEMO_TERRAFORM_URL=https://...
# NEXT_PUBLIC_DEMO_PULUMI_URL=https://app.pulumi.com/ORG/PROJ/STACK
```

### Credenciales mostradas en la web (solo demo)

```bash
NEXT_PUBLIC_DEMO_GUEST_USER=demo-invitado
NEXT_PUBLIC_DEMO_GUEST_PASSWORD=...   # solo si quieres mostrarlas; si no, el usuario contacta
```

Vuelve a ejecutar `npm run build` y despliega `out/` — ver [DEPLOY-SITIO-REMOTO.md](DEPLOY-SITIO-REMOTO.md).

---

## 3. Flujo “commit → POS” (lo que debe existir detrás)

Orden lógico (alineado a FastFlow / pos-online):

1. **Gitea:** push a rama que dispara el job (o PR merge según tu Jenkinsfile).
2. **Webhook:** Gitea → Jenkins (URL y secret configurados).
3. **Jenkins:** checkout, `mvn package`/tests, `docker build`, `docker push` al **registry** con tag versionado.
4. **Deploy:** job o stage que en **Linux** hace pull de la imagen (o artefacto), reinicia servicio **systemd**/compose, o aplica **Kubernetes** (`kubectl`/Helm) si ya tienes cluster.
5. **POS:** responde en `https://pos.unclic.consulting` (mismo pipeline que validaste en demo).

Referencias detalladas en el monorepo:

- Replicar Jenkins + Gitea + POS: [REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md](../../docs/REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md)
- POS + Terraform/K8s (contexto): [PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md](../../docs/PLAN-TERRAFORM-KUBERNETES-POS-FASTFLOW.md)
- LocalStack / Pulumi / Jenkins (laboratorio): [LOCALSTACK-IAC-JENKINS-POS.md](LOCALSTACK-IAC-JENKINS-POS.md)

---

## 4. Seguridad (no solo el allowlist del sitio)

El allowlist de UnClic **oculta enlaces** a usuarios casuales; **no** autentica contra Jenkins/Gitea.

Para demos restringidas:

- **Nginx** delante de Jenkins/Gitea/registry con **Basic Auth** o **IP allowlist**, **o**
- Cuentas solo para invitados + desactivar registro público en Gitea.

Ver [DEMO-ACCESO-ALLOWLIST-INFRA.md](DEMO-ACCESO-ALLOWLIST-INFRA.md).

---

## 5. Orden sugerido “para terminar de montar”

1. DNS A/AAAA para `unclic.consulting`, `jenkins.`, `gitea.`, `pos.`, `registry.` → IPs correctas.  
2. Certificados TLS en cada host (Certbot o ACM + LB).  
3. Gitea + Jenkins + registry operativos y con HTTPS.  
4. Job Jenkins verde de punta a punta hasta POS.  
5. `.env.local` con allowlist + URLs; `npm run build`; `deploy-to-unclic-consulting.sh`.  
6. Probar: incognito → `/login` con `alejandro@unclic.consulting` → hub → abrir cada demo.  
7. (Opcional) Rellenar `NEXT_PUBLIC_DEMO_K8S_URL`, `TERRAFORM`, `PULUMI` y rebuild.

---

## 6. Código del hub

Las tarjetas salen de `lib/hub-links.ts` y `lib/demos.ts`. La variable **Pulumi** es `NEXT_PUBLIC_DEMO_PULUMI_URL` (desde el build estático).

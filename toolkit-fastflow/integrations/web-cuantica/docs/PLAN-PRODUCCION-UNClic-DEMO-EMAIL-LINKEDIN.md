# Plan producción UnClic: sitio en vivo, demo con email gate, correo bienvenida Gmail, experiencia FastFlow y LinkedIn

Objetivo: **poner en producción ya** el sitio UnClic, la demo FastFlow visible para todos **tras dejar el correo**, envío de **correo de bienvenida con Gmail**, y que al entrar a la demo vean **en tiempo real** Jenkins, Gitea, pipelines, Terraform/Pulumi/SST open source; después **cerrar el perfil LinkedIn**.

Referencias: [PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md](PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md), [SUBIR-SITIO-A-UNCLIC-CONSULTING.md](../unclic/docs/SUBIR-SITIO-A-UNCLIC-CONSULTING.md), [GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md](GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md), [PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md](PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md).

---

## Resumen en fases

| Fase | Qué | Estado |
|------|-----|--------|
| **1** | Sitio UnClic en producción (unclic.consulting) | Docs listos; ejecutar deploy |
| **2** | Email gate: dejar correo para acceder a /demo | Implementado en código |
| **3** | Correo de bienvenida con Gmail | Formspree auto-responder o Zapier + Gmail |
| **4** | Experiencia demo: Jenkins/Gitea solo lectura, ver pipelines, open source | Copy y enlaces; configurar usuarios invitado |
| **5** | LinkedIn: perfil alineado a UnClic y demos | Guía en unclic/docs |

---

## Fase 1 — Sitio UnClic en producción (ya mismo)

Seguir al pie de la letra: **[unclic/docs/SUBIR-SITIO-A-UNCLIC-CONSULTING.md](../unclic/docs/SUBIR-SITIO-A-UNCLIC-CONSULTING.md)**.

### Pasos resumidos

1. **DNS (Namecheap):** A @ y www → IP de fastflow-vantive (ej. 3.22.236.150).
2. **EC2 (Nginx):** Server block `unclic.consulting` y `www`, root `/usr/share/nginx/unclic`.
3. **Build y deploy:** En el repo unclic (o nucleic en Gitea): `npm ci && npm run build` → genera `out/`. Subir con rsync a `ec2-user@IP:~/unclic-deploy/`; en EC2: `sudo rsync -av --delete ~/unclic-deploy/ /usr/share/nginx/unclic/`.
4. **HTTPS:** En EC2 `sudo certbot --nginx -d unclic.consulting -d www.unclic.consulting`.

Opcional: **Jenkins** job que clona desde Gitea (nucleic), hace build y deploy por SSH (ver doc SUBIR-SITIO y PLAN-SUBDOMINIO).

---

## Fase 2 — Email gate (dejar correo para ver la demo)

Implementado en el código:

- **Ruta `/demo/access`:** formulario con un solo campo (correo) + botón "Acceder a la demo".
- Al enviar: se envía el correo a Formspree (o endpoint configurable); se guarda en sesión que ya pasó la puerta y se redirige a `/demo`.
- **Ruta `/demo`:** si no hay "acceso concedido" (sessionStorage), redirige a `/demo/access`.
- CTAs "Probar la demo" / "Comenzar" en la landing llevan a `/demo/access` (no directo a `/demo`).

Variables de entorno (build):

- `NEXT_PUBLIC_DEMO_ACCESS_FORM_ID` — ID del formulario Formspree (ej. `xyzwabcd`) para que el action sea `https://formspree.io/f/xyzwabcd`. Si no se define, el form sigue mostrándose y se puede configurar después.

---

## Fase 3 — Correo de bienvenida con Gmail

El sitio es estático (Next.js `output: 'export'`), por tanto no hay backend en el mismo servidor. Opciones:

### Opción A — Formspree (recomendada para “ya mismo”)

1. Crear formulario en [Formspree](https://formspree.io): tipo "Contact" o "Demo access", campo `email`.
2. En Formspree → Form Settings → **Autoresponder**: activar y redactar el correo de bienvenida (asunto y cuerpo). Formspree enviará ese correo desde su dominio al email del usuario.
3. Si quieres que el correo **salga desde tu Gmail**: en Formspree no se puede forzar "From: tu@gmail.com" en el plan gratuito; el usuario recibe un correo de Formspree. Para que **llegue desde Gmail** usa Opción B.

### Opción B — Zapier / Make.com + Gmail

1. Formspree envía el correo (o usa webhook de Formspree).
2. **Zapier:** Trigger "Formspree - New Submission" → Action "Gmail - Send Email". Redactas la plantilla de bienvenida y el envío sale de tu Gmail.
3. **Make.com (Integromat):** Igual: módulo Formspree (webhook) → Gmail Send Email.

Así el usuario recibe un correo de bienvenida **desde tu Gmail** con los enlaces a la demo (unclic.consulting/demo) y, si quieres, las credenciales invitado.

### Opción C — Backend propio (más adelante)

Si más adelante desplegáis Next.js con server (o una función serverless), se puede tener una API route que reciba el email, lo guarde y envíe el correo con Nodemailer + Gmail (contraseña de aplicación). Ver [docs de Nodemailer + Gmail](https://nodemailer.com/usage/using-gmail/). No es necesario para "producción ya mismo".

---

## Fase 4 — Experiencia demo: qué ven en tiempo real

Objetivo: que al entrar a `/demo` vean **cómo funciona FastFlow en tiempo real** y **cada interfaz** (Jenkins, Gitea, POS, registry, Terraform/Pulumi/SST open source), **solo lectura**.

### 4.1 Jenkins

- **URL:** https://jenkins.unclic.consulting (o la que tengáis).
- **Usuario invitado:** solo lectura (ver [GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md](GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md)).
- **Qué ven:** jobs y pipelines ya creados, builds ejecutados, logs del pipeline que desplegó la app. **No pueden crear nuevos jobs** (permisos Matrix: Read, View Status).
- En la página `/demo`: enlace "Abrir Jenkins" + bloque de credenciales invitado (componente `GuestCredentialsBlock`).

### 4.2 Gitea

- **URL:** https://gitea.unclic.consulting.
- **Usuario invitado:** acceso solo a repos del POS (o los que expongáis).
- **Qué ven:** repos, commits, ramas, cómo se conecta el pipeline (webhook, Jenkins desde Gitea). **No pueden crear repos ni modificar** (rol Read).

### 4.3 POS (app desplegada)

- **URL:** https://pos.unclic.consulting (instancia dedicada según [PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md](PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md)).
- **Qué ven:** la aplicación desplegada por el pipeline; ven el resultado del deploy en tiempo real.

### 4.4 Peticiones automáticas desde el sitio de demo

Si tenéis un backend o servicios que hacen peticiones automáticas (health checks, métricas) y queréis que se vean: se puede añadir en la página `/demo` una sección "Llamadas desde nuestro sitio" con un panel que muestre (por ejemplo) las últimas peticiones o un log read-only. Eso requiere un endpoint o integración adicional; para "ya mismo" basta con los enlaces a Jenkins/Gitea/POS y el copy que explique que el pipeline desplegó la app.

### 4.5 Terraform, Pulumi, SST open source

- **Qué ven:** que usáis **open source** (Terraform, Pulumi, SST). Opciones:
  - Enlaces desde `/demo` (o hub) a **docs** en el repo (ej. `docs/tecnologias/TERRAFORM.md`, `PULUMI.md`, `SST.md`) o a repos públicos donde tengáis ejemplos.
  - Si tenéis un dashboard o salidas de Terraform/Pulumi de solo lectura, poner la URL en `lib/demos.ts` (`NEXT_PUBLIC_DEMO_TERRAFORM_URL`, etc.) y una tarjeta "Ver estado Terraform" / "Ver stack Pulumi".
- Copy en la página demo: "Terraform, Pulumi y SST los usamos en modo open source; aquí puedes ver documentación y enlaces a los proyectos."

### 4.6 Resumen experiencia demo

| Qué | Dónde | Permiso usuario invitado |
|-----|--------|---------------------------|
| Jenkins | jenkins.unclic.consulting | Ver jobs y pipelines, logs; no crear |
| Gitea | gitea.unclic.consulting | Ver repos y commits; no crear/modificar |
| POS | pos.unclic.consulting | Usar la app desplegada |
| Registry | registry.unclic.consulting | Listar/ver imágenes (solo lectura) |
| Terraform/Pulumi/SST | Docs y enlaces en el sitio | Solo lectura / documentación |

Configuración de usuarios invitado: [GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md](GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md).

---

## Fase 5 — LinkedIn

Objetivo: **terminar el perfil LinkedIn** alineado a UnClic, pipeline as code y demos.

Documentos en el repo:

- **unclic/docs/LINKEDIN-ALEJANDRO-PEREZ-PULIDO.md** — Contenido y estructura del perfil.
- **unclic/docs/GUIA-LINKEDIN-PERSONAL-ESTRUCTURA-SEQUOIA.md** — Estructura tipo Sequoia.
- **unclic/docs/CV-LINKEDIN-INSTAGRAM-UNClic.md** — CV y redes.
- **unclic/docs/PATRON-LINKEDIN-PAGINA-EMPRESA-SEQUOIA.md** — Página empresa (si aplica).

Checklist rápido LinkedIn:

1. Foto y banner actualizados.
2. Headline: Pipeline as Code, CI/CD, UnClic (o el que tengáis en copy).
3. Resumen (About): alineado a la copy del sitio (hub de demos, Jenkins, Gitea, retail/alto volumen).
4. Experiencia: UnClic con descripción que enlace a unclic.consulting y al hub de demos.
5. Sección "Featured": enlace a https://unclic.consulting y/o a https://unclic.consulting/demo (tras email gate).
6. Destrezas: Jenkins, Gitea, Docker, CI/CD, Terraform, etc.

---

## Orden de ejecución recomendado (checklist)

```
[ ] 1.1  DNS y Nginx para unclic.consulting (Fase 1)
[ ] 1.2  Build unclic (npm run build) y rsync a EC2; Certbot HTTPS
[ ] 2.1  Probar /demo/access y /demo (email gate en código)
[ ] 2.2  Configurar NEXT_PUBLIC_DEMO_ACCESS_FORM_ID (Formspree) en build
[ ] 3.1  Formspree: formulario Demo Access + autoresponder O Zapier/Make + Gmail
[ ] 4.1  Jenkins y Gitea: usuarios invitado solo lectura (ver doc)
[ ] 4.2  Confirmar URLs demo (Jenkins, Gitea, POS, registry) en .env o demos.ts
[ ] 4.3  Enlaces a docs Terraform/Pulumi/SST desde /demo o hub
[ ] 5.1  Completar perfil LinkedIn según docs en unclic/docs
```

---

## Documentos relacionados

| Tema | Documento |
|------|-----------|
| Formspree + Gmail (configurar correo bienvenida) | [unclic/docs/DEMO-ACCESS-FORMSPREE-GMAIL.md](../unclic/docs/DEMO-ACCESS-FORMSPREE-GMAIL.md) |
| Demo POS + email gate (fases 1–3) | [PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md](PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md) |
| Subir sitio a unclic.consulting | [unclic/docs/SUBIR-SITIO-A-UNCLIC-CONSULTING.md](../unclic/docs/SUBIR-SITIO-A-UNCLIC-CONSULTING.md) |
| Usuario invitado Jenkins/Gitea | [GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md](GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md) |
| Subdominio landing + Jenkins | [PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md](PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md) |
| LinkedIn | [unclic/docs/LINKEDIN-ALEJANDRO-PEREZ-PULIDO.md](../unclic/docs/LINKEDIN-ALEJANDRO-PEREZ-PULIDO.md) |

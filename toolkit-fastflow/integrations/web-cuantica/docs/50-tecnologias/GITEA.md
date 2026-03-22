# Gitea — Por qué, open source y cómo replicar

## Qué es Gitea (según web oficial)

**Gitea** se presenta como *"The Best Open Source Self-Hosted Git Service"*: un servicio de desarrollo todo-en-uno que incluye **Git hosting**, **code review**, **colaboración en equipo**, **package registry** y **CI/CD**. Es **open source bajo licencia MIT**, ligero, fácil de instalar y muy personalizable.

- **Características clave (web oficial):** instalación y configuración sencillas, alto rendimiento, colaboración (code review, issues, pull requests), compatibilidad total con Git, control de acceso y seguridad, multiplataforma.
- **Por qué elegir Gitea (web oficial):** Open source (control total de repos y datos), self-hosted (infra propia), comunidad activa, escalable, mantenimiento simplificado.

**Getting Started oficial:** descargar en [gitea.io](https://gitea.io/), seguir la [documentación](https://docs.gitea.com/) de instalación y configuración, personalizar la instancia y usar Git/colaboración desde la interfaz web.

### Web oficial (gitea.io) — navegación y secciones

- **Cabecera:** **Products** | **Resources** | **Community** | **Pricing** | **Cloud** | **Sign In** | **Contact Us**. Hero: *"The Best Open Source Self-Hosted Git Service"*; **Download for Linux - ARM64** (Installations →).
- **Key Features (web):** 1) Easy Installation and Configuration, 2) High Performance, 3) Collaboration Made Simple (code reviews, issues, pull requests), 4) Full Git Compatibility (100% Git compatible), 5) Security and Access Control, 6) Cross-Platform Compatibility.
- **Why Choose Gitea:** Open Source, Self-Hosted, Community Support, Scalable, Simplified Maintenance.
- **Productos:** **Gitea Cloud** — elegir proveedor y región, trial gratis (*Start Free Gitea Cloud Trial*). **Gitea Enterprise** — Gitea mejorado para empresas, trial gratis (*Start Free Gitea Enterprise Trial*). *"Private, Fast, Reliable DevOps Platform"*.
- **Métricas:** Docker Pulls 293M+, Github Stars 53K+, Installations 390K+, Contributors 1,173+.
- **Dev/Deploy:** Code Hosting, **CI/CD** (Gitea Actions, compatible con GitHub Actions, 20K+ plugins), **Projects** (issues, labels, kanban), **Packages** (20+ tipos: Cargo, Chef, Composer, Maven, NPM, NuGet, PyPI, RubyGems, Container, Helm, etc.). **Run Gitea Anywhere:** Linux, Windows, macOS, FreeBSD, Kubernetes; x86, arm64; SQLite, MySQL, PostgreSQL, TiDB, MS SQL; single server o réplicas. **Integraciones:** Slack, Discord, MS Teams, Lark; Jenkins, Drone, Woodpecker, ArgoCD; API y Webhooks. **Testimonials** (AppleBoy, Dan K., Tony Brix, Kaviraj R., Sachin R.). Empresas: Google, Two Sigma, MTK, Mastercard, Openstack.
- **Pricing:** *"Pricing plans for teams of all sizes"*. **Management Mode:** Self Managed | Cloud Managed. **Open Source** — Gratis, MIT, *Download Now*; incluye Code hosting, Issue tracking, Pull requests, Project management, Gitea Actions (CI/CD), Packages, actualizaciones, comunidad. **Enterprise** — 9,5 / 19 USD por usuario/mes, trial 30 días, *Contact Sales*; compromiso 1 año; SAML SSO, Audit Logs, Kubernetes AutoScaling Runners, soporte prioritario, SLA, descuentos para non-profits. **FAQ:** How do you define a user?, What is included?, Discounts non-profits?, PPP?, BAA HIPAA?, I have a different question. Newsletter: *"Want product news and updates?"* — Email, Subscribe.
- **Footer:** *SOC 2 Type 2 Certified*. *"Private, Fast, Reliable DevOps Platform"*. LinkedIn, X, GitHub, Gitea. © 2026 CommitGo, Inc. **Products:** Gitea Cloud, Gitea Enterprise, Gitea, Gitea Runner, Tea Command-line Tool. **Support:** Pricing, Documentation, Tutorials, API, Blog, Forum, Chatroom. **About Us:** What is DevOps, Why Gitea, Contact Us. **Compliance.** **Legal:** Privacy, Terms.

---

## Interfaz Gitea — qué ves (perfil / actividad)

En la cabecera: **Logo** | **Issues** | **Pull Requests** | **Milestones** | **Explore**.

- **Gráfico de contribuciones:** Calendario de los últimos 12 meses (ej. Apr–Mar), días Lun/Mié/Vie resaltados; texto *"X contributions in the last 12 months"*; enlaces **Less** / **More**.
- **Actividad reciente:** Lista de eventos tipo *"usuario pushed to main at usuario/repo"* con fecha (yesterday, 2 days ago, …), hash de commit (corto, ej. ae67dd33e4) y mensaje del commit en la línea siguiente. También *"usuario created branch main in usuario/repo"*, *"usuario created repository usuario/repo"*. En pushes con varios commits: enlace **Compare N commits »**.
- **Paginación:** **Previous** | **1** | **Next**.
- **Repositories:** Sección con filtros **Repository** | **Organization**. Verás contador y pestañas **All** | Sources | Forks | Mirrors | Collaborative. Ejemplos de nombres en un entorno FastFlow: `usuario/pos-online`, `usuario/smartbussiness-generic-model`, `usuario/pos-online-fastflow`, etc. (los tuyos pueden variar).
- **Pie de página:** *"Powered by Gitea"*. **Version:** 1.25.4 (o la instalada). **Page:** Xms. **Template:** Xms. **Licenses** | **API**.

### Issues (lista)

Desde **Issues** en la cabecera: filtros **In your repositories** (contador ej. 0), **0 Open** | **0 Closed**. Si no hay resultados, mensaje tipo *"No results"* y *"Try adjusting your search filters."*

### Pull Requests (lista)

Desde **Pull Requests**: mismos filtros que Issues y además **Assigned to you** | **Created by you** | **Review requested** | **Reviewed by you** | **Mentioning you** (cada uno con contador). **0 Open** | **0 Closed**. Mismo estado vacío y mismo pie (Powered by Gitea, Version, Page Xms, Template Xms, Licenses, API).

### Explore (explorar)

Desde **Explore** en la cabecera: pestañas **Repositories** | **Users** | **Organizations**.

- **Repositories:** Lista de repos; cada fila muestra **usuario/repo** (enlace), etiqueta **Private** (o Public), **lenguaje** (Java, TypeScript, JavaScript, Shell, etc.), dos contadores (ej. 0 0), **Updated** (yesterday / X days ago) y opcionalmente la **descripción** del repo (ej. *"Pos Online automated with Fast Flow (Jenkins, Registry...) for Web Cuantica"*).
- Mismo pie de página (Powered by Gitea, Version, Page Xms, Template Xms, Licenses, API).

---

## Por qué lo usamos (en FastFlow)

- **Repositorio Git self-hosted:** Es la fuente única del código (pos-online, generic-model). Jenkins clona desde Gitea y reacciona a pushes (webhook o polling).
- **Control total y privacidad:** El código no pasa por GitHub/GitLab corporativo; lo tienes en tu propia EC2.
- **Objetivo de automatización:** Pipeline as Code exige que el código esté en un repo accesible; Gitea es ese repo y dispara (o es consultado por) Jenkins para cada cambio. Encaja con “solo open source” y self-hosted.

## Open source

- **Proyecto:** [Gitea](https://gitea.io/).
- **Licencia:** MIT.
- **Código:** [github.com/go-gitea/gitea](https://github.com/go-gitea/gitea).
- **Documentación oficial:** [docs.gitea.com](https://docs.gitea.com/) — instalación, configuración, API, tutoriales.

## Cómo replicar

1. **Requisitos:** EC2 con Docker (o binario Linux). Ver [../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md).
2. **Consola:** Terminal (SSH a la EC2) y navegador (Gitea en puerto 3000).
3. **Instalación (Docker):** Crear `docker-compose.yml` con servicio Gitea (imagen `gitea/gitea`), volumen para datos, puerto 3000:3000. `docker compose up -d`. Primera vez: abrir `http://<IP-EC2>:3000` y completar el asistente (DB ya en SQLite, Server domain = `gitea.unclic.consulting`, Base URL = `http://gitea.unclic.consulting:3000/`).
4. **Repos:** Crear repos `pos-online` y `generic-model` (o los que uses). Añadir remote desde tu máquina: `git remote add gitea http://gitea.unclic.consulting:3000/tu-usuario/pos-online.git`, luego `git push gitea main`.
5. **Jenkins:** En el job Pipeline, Repository URL = esa misma URL de Gitea; credenciales si el repo es privado.

## Inputs y comandos (referencia)

| Consola | Comando o acción | Input / nota |
|---------|-------------------|--------------|
| Terminal (EC2) | `docker compose up -d` (en el directorio del compose de Gitea) | Arrancar Gitea |
| Navegador | `http://<IP-EC2>:3000` | Ej. 13.58.58.245 o gitea.unclic.consulting:3000 |
| Gitea UI | First-run: Server domain = `gitea.unclic.consulting`, Base URL = `http://gitea.unclic.consulting:3000/` | Ajustar si usas otra URL |
| Terminal (local) | `git remote add gitea http://gitea.unclic.consulting:3000/usuario/pos-online.git` | Sustituir usuario y repo |
| Terminal (local) | `git push gitea main` | Subir rama main al repo Gitea |

URLs de referencia:

- Gitea: `http://13.58.58.245:3000` o `http://gitea.unclic.consulting:3000`

## Enlaces

**Este repo:**

- [Instalar Gitea self-hosted](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md)
- [Clonar pos-online y conectar a Gitea](../20-operaciones/CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md)
- [Repos locales y Gitea](../20-operaciones/REPOS-LOCALES-Y-GITEA.md)
- [Tutorial de replicación visual](../90-archivo/REPLICAR-TUTORIAL-VISUAL.md)

**Gitea oficial:**

- [gitea.io](https://gitea.io/) — web, descargas (Linux ARM64/x86, etc.)
- [Documentation](https://docs.gitea.com/) — instalación, configuración, uso
- [Tutorials](https://docs.gitea.com/usage/tutorials/) — guías de uso
- [API](https://docs.gitea.com/development/api-usage/) — integraciones
- [GitHub go-gitea/gitea](https://github.com/go-gitea/gitea) — código fuente

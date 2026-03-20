# Documentación — POS Online + implementación FastFlow

Índice de la documentación que aplica a **POS Online** y a la implementación FastFlow (pipeline, registry, Jenkins, Terraform, K8s).

---

## Punto de entrada — una sola guía (commit → Jenkins → Postman → rollback)

| Documento | Descripción |
|-----------|-------------|
| **[GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** | **Manual único (happy path):** dominio **Namecheap** y subdominios, EC2 (Jenkins, Gitea, POS), security groups, variables del job, **Console Output**, **Postman** ([colección](postman/pos-online-unclic.postman_collection.json)), **rollback**. Incluye **§12** plantilla **`webcuantica.com`** (Gitea HTTPS, token, Jenkins primera vez). |
| **[WEBCUANTICA-REPLICACION-FASTFLOW.md](WEBCUANTICA-REPLICACION-FASTFLOW.md)** | **Entrada Web Cuántica:** enlace directo a **§12** de la guía única para replicar con dominio propio (`gitea.webcuantica.com`, `jenkins.webcuantica.com`). |
| **[HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md](HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md)** | **Jenkins con https://jenkins.unclic.consulting** en **Amazon Linux 2:** Nginx + Let’s Encrypt (sin Docker); SG 80/443; invitados por enlace seguro. |
| **[postman/README.md](postman/README.md)** | Importar colección Postman y variable `APP_BASE_URL`. |

---

## Documentación por tecnología y replicación (por qué, open source, cómo replicar)

| Documento | Descripción |
|-----------|-------------|
| **[DOCUMENTACION-POR-TECNOLOGIA-Y-REPLICACION.md](DOCUMENTACION-POR-TECNOLOGIA-Y-REPLICACION.md)** | **Índice maestro:** objetivo (automatizar con **solo open source**), enlace a **un doc por tecnología** (Jenkins, Gitea, Docker, Terraform, Pulumi, LocalStack, AWS EC2, Maven/Java, Namecheap DNS, Cloudcraft, Next.js, SST, K8s) con por qué la usamos, licencia y cómo replicar. Enlace al **tutorial de replicación visual** y dónde colocar assets. |
| **[tecnologias/](tecnologias/README.md)** | **Un documento por tecnología:** cada archivo (JENKINS.md, GITEA.md, DOCKER.md, etc.) explica por qué la usamos, que es open source y cómo replicar esa pieza. Incluye inputs y comandos por consola. |
| **[REPLICAR-TUTORIAL-VISUAL.md](REPLICAR-TUTORIAL-VISUAL.md)** | **Tutorial paso a paso con consolas, comandos, URLs y ayudas visuales:** en cada paso se indica en qué consola estás, qué ves, qué comando o clic ejecutar, qué URL abrir, y referencia a asset o HTML. Placeholders para capturas; ampliable con tus assets. |
| **deploy/replicar-tutorial-visual.html** | **Ayudas visuales en HTML:** pantallas simuladas (AWS EC2, Namecheap DNS, Jenkins Unlock, New Item) para seguir el tutorial sin depender solo de texto. Abrir en navegador junto con REPLICAR-TUTORIAL-VISUAL.md. |
| **tecnologias/assets/** | Carpeta para **capturas y assets** del tutorial (p. ej. aws-ec2-instances-table.png, jenkins-unlock-screen.png). Referenciar desde REPLICAR-TUTORIAL-VISUAL.md. |

---

## Documentación maestra (estado actual y pendientes — solo local)

| Documento | Descripción |
|-----------|-------------|
| **[DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md](DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md)** | **Referencia única:** estado actual (IPs 18.119.157.22 Jenkins, 13.58.58.245 Gitea), Gitea HTTPS, delivery/ y scripts, logs pos-online, repos locales vs Gitea, **lista de docs con IPs desactualizadas** y pendientes por documentar. Todo anotado solo en repos locales; no se envía a remoto. |
| **[INDICE-KNOW-HOW.md](INDICE-KNOW-HOW.md)** | **Know-how documentado:** tabla de todo lo que está documentado (replicar, instalar Jenkins/Docker, push Gitea, jobs, logs pos-online, delivery, HTTPS, generic-model, validaciones, DNS, servidores, facturación; Vantive: origen, deploy, probar, seguridad, HTTPS, costes, Jenkins+AWS) y lista de lo que no tiene how-to dedicado (opcional). |

---

## Documentación formal con diagramas (estilo capítulo)

| Documento | Descripción |
|-----------|-------------|
| **[CAPITULO-DESPLIEGUE-OPERACION-AWS.md](CAPITULO-DESPLIEGUE-OPERACION-AWS.md)** | **Capítulo 3 — Desplegar y operar en AWS:** concepto de alojar la pila en AWS, formas de interactuar (Console, CLI, IaC), región/AZ/VPC, flujo “un commit → app levantada” con **diagramas Mermaid**, **guía paso a paso** (Fases A–D) y **enlaces a documentación oficial de AWS**. Sustituye notas de desarrollador por una secuencia ordenada y diagramas. |
| **[CAPITULO-SERVICIOS-CORE-AWS.md](CAPITULO-SERVICIOS-CORE-AWS.md)** | **Capítulo 4 — Servicios core de AWS:** computación (EC2), almacenamiento (EBS), redes (VPC, Security Groups), gestión (Terraform). Qué usamos y qué no; diagramas y enlaces oficiales. |
| **[CAPITULO-FACTURACION-Y-PRECIOS.md](CAPITULO-FACTURACION-Y-PRECIOS.md)** | **Capítulo 6 — Facturación y precios:** drivers de coste (compute, storage, transferencia), Free Tier, Billing Dashboard, Budgets, planes de soporte. **Nuestro plan dejado claro:** una EC2 Free Tier, destruir cuando no se use, Basic support. |
| **[AWS-BILLING-Y-CUENTA-CONSOLA.md](AWS-BILLING-Y-CUENTA-CONSOLA.md)** | **Consola Billing and Cost Management y Account:** menú lateral (Bills, Cost Explorer, Free Tier, Budgets, Savings Plans, etc.) y página Account (ID, ARN, contacto, regiones habilitadas/deshabilitadas, IAM billing, cierre de cuenta). Solo estructura de la UI; no datos personales. |
| **[PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md)** | **Mínimo para hoy:** lista ordenada (Pasos 1–8) para tener “un commit → app levantada”: conectar EC2, instalar Java/Jenkins/Maven, abrir 8111, crear job, trigger, Deploy real. |
| **[GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md](GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md)** | **Para el usuario final:** explica **por qué** se hace cada cosa y **cómo** hacerla (cuenta AWS, Terraform, EC2, Java 11/Corretto, Jenkins, Maven, puerto 8111, job, generic-model, trigger, Deploy real). Incluye problemas frecuentes y tabla resumen. |
| **[URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md)** | **URLs y EC2 para probar:** datos de la instancia fastflow-jenkins-controller (IP, DNS, ID), **URL para Jenkins** (http://3.15.4.160:8080) y para la app POS (http://3.15.4.160:8111) cuando esté desplegada. |
| **[CHECKLIST-DOMINIO-JENKINS-HTTPS-ROLLBACK.md](CHECKLIST-DOMINIO-JENKINS-HTTPS-ROLLBACK.md)** | **Namecheap + IP pública + EIP + Security Group + HTTPS + rollback por tags en registry** (Jenkins en EC2, ej. `jenkins.unclic.consulting`). |
| **[JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md)** | **Pantallas Jenkins (crear y configurar job):** New Item (nombre, tipo Pipeline) y Configure (General, Triggers, Pipeline, Advanced) con los textos y opciones que verás en la UI (Jenkins 2.541.2). |
| **[INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md)** | **Instalar y hospedar Gitea (self-hosted):** descargar e instalar Gitea para proyectos, repos y clientes. Opciones: Docker (recomendado) y binario en Linux; primer arranque, uso con Jenkins. |
| **[CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md)** | **Clonar pos-online y conectar a Gitea:** script y pasos para clonar el repo local y, cuando tengas Gitea, añadir remote y push; luego configurar Jenkins con la URL de Gitea. |
| **[REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md)** | **Replicar estado actual — índice:** punto de entrada para reproducir todo lo montado (Jenkins HTTPS, Gitea, repos, pipelines con logs, deploy 8111). Tabla estado actual, orden de replicación e índice de docs por tema. |
| **[REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md)** | **Replicar todo (guía paso a paso):** desde cero hasta Jenkins + Gitea + pos-online funcionando. Qué pantalla, qué botón, qué teclear (AWS, Namecheap, EC2, swap, HTTPS opcional, Gitea, repos locales, Jenkins jobs, errores frecuentes). |
| **[REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md)** | **Replicar todo lo hecho (pos-online FastFlow):** EC2 Jenkins/Gitea, Java/Maven/Jenkins/Docker, swap, deploy manual (Approve Deploy), puerto 8111, verificar instancia; pasos numerados y checklist con IPs actuales. |
| **[REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md](REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md)** | **Replicar el flujo completo (ya replicado dos veces):** cómo conecta todo (diagrama y tabla), estado actual (IPs, URLs, repos), orden de replicación en 6 fases (AWS → DNS → Gitea → Jenkins → código local → servidor POS). Referencia única para volver a montar todo. |
| **[INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md)** | **Infraestructura actual unclic.consulting:** qué existe, dónde se configura cada cosa (Namecheap DNS, AWS EC2, Jenkins HTTPS, Gitea, correo) y qué está disponible. Documento de referencia a actualizar cuando cambie algo. |
| **[SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md)** | **Servidores (Jenkins, Gitea, Vantive):** tabla de los tres EC2; pasos para **levantar Vantive** (Start instance, Nginx, comprobar sitio). |
| **[DESPLIEGUE-URGENTE-VANTIVE-AWS-GRATIS.md](DESPLIEGUE-URGENTE-VANTIVE-AWS-GRATIS.md)** | **Desplegar Vantive ya en AWS (gratis):** guía urgente — EC2 existente o nueva, Nginx, build + rsync; incluye script `scripts/deploy-vantive-ec2.sh`. |
| **[REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md)** | **Repos locales y Gitea:** rutas locales, remotes (origin/gitea), dos niveles de Git, comandos push para pos-online y generic-model. |
| **[PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md)** | **Probar local y subir pipeline:** qué ejecutar antes de push (mvn test, package), comandos cd + push a Gitea, cambios en Jenkinsfiles (logs, JUnit, docker por sh). |
| **[JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md](JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md)** | **Avisos Jenkins y prioridad:** built-in node, Java 17 EOL, AL2, CSP; cuándo solucionar; prioridad = pipeline POS completo (deploy, registry, rollback). |
| **[DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md)** | **Usar dominio unclic.consulting (Namecheap) con EC2:** A records para jenkins.unclic.consulting y gitea.unclic.consulting; configurar Gitea/Jenkins con esas URLs; correo y HTTPS opcional. |
| **[EC2-CREAR-LANDING-UNCLIC.md](EC2-CREAR-LANDING-UNCLIC.md)** | **Crear servidor gratis para landing unclic:** EC2 t3.micro, security group 22/80/443, DNS landing.unclic.consulting, Nginx. (Vantive: ver `integrations/kings-joers/`.) |
| **[PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md](PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md)** | **Plan demo POS con FastFlow para Web Cuántica:** cómo pasar el POS a Gitea con FastFlow, usar el dominio unclic.consulting y la suite Google Workspace (correo, Calendar, Meet, Docs) para esta primera demo. Checklist y fases A–D. |
| **[PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md](PLAN-DEMO-DEVOPS-POS-INSTANCIA-DEDICADA-Y-EMAIL-GATE.md)** | **Plan detallado demo DevOps:** (1) POS en instancia EC2 dedicada; (2) probar deploy como producción; (3) email gate (solo correo) → acceso a Jenkins/Gitea/POS con usuario invitado; (4) después Terraform y Kubernetes. Orden de fases y checklist. |
| **Cloudcraft (en `toolkit-fastflow/docs/`)** | **[GUIA-CLOUDCRAFT-COMPLETA.md](../../../docs/GUIA-CLOUDCRAFT-COMPLETA.md)** — Guía maestra: índice de docs Cloudcraft, resumen de lo hecho, flujo rápido, UnClic (variables y sección). **[CLOUDCRAFT-DOCUMENTACION-Y-UI.md](../../../docs/CLOUDCRAFT-DOCUMENTACION-Y-UI.md)** — Live scanning, vista/embed en UnClic. **[PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md](../../../docs/PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md)** — Plan paso a paso (Fases 0–5, IAM, Share, .env). **[cloudcraft-fastflow-blueprint-snapshot.json](../../../docs/cloudcraft-fastflow-blueprint-snapshot.json)** — Snapshot JSON del diagrama. |

---

**Arquitectura en tiempo real (Cloudcraft):** Ver fila **Cloudcraft** en la tabla anterior. Punto de entrada: [GUIA-CLOUDCRAFT-COMPLETA.md](../../../docs/GUIA-CLOUDCRAFT-COMPLETA.md) — enlaza documentación precisa, plan granular y snapshot del blueprint.

**Compatibilidad e historial:** [COMPATIBILIDAD-Y-HISTORIAL.md](COMPATIBILIDAD-Y-HISTORIAL.md) — qué copiar al pos-online (mínimo sin complicar), fusionar Jenkinsfile, y **cómo recuperar docs que solo están en el historial** (tabla con commit y rutas). [historial/README.md](historial/README.md) — recuperar docs desde commits.

**Configuración central:** Todas las configuraciones (Jenkins, Registry, Pipeline, Terraform, K8s, App) en **config/fastflow-config.json**. Ver y administrar desde una sola UI: **deploy/config-ui.html** (cargar/editar/descargar). Ver [config/README.md](../config/README.md).

---

## 0. Cuenta AWS nueva (acabas de crear la cuenta)

| Documento | Descripción |
|-----------|-------------|
| **[PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md)** | Acabas de crear la cuenta con tu tarjeta: ver región, crear usuario IAM, instalar AWS CLI y Terraform, y qué sigue para desplegar gratis. |

---

## 1. Copia del proyecto y contexto

| Documento | Descripción |
|-----------|-------------|
| **[copia/COPIA-POS-ONLINE-FASTFLOW.md](copia/COPIA-POS-ONLINE-FASTFLOW.md)** | Copia en palabras sencillas: qué es POS Online, generic-model (dependencia obligatoria), JD Edwards/Oracle/ambientes, y FastFlow como implementación (no producto). |
| [DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md) | Generic model (ruta, obligatoriedad), JDE, Oracle, entornos, bases de datos, fase de arquitectura. |
| **[GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md)** | **generic-model en Gitea y Jenkins:** clone en web-cuantica, script para subir a Gitea, job Jenkins que instala artefacto en ~/.m2, encadenar con pos-online-pipeline. |
| [EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md) | Instalar generic-model en la EC2 (manual): clonar smartbussiness-generic-model y `mvn install` como usuario jenkins. |
| [COMPATIBILIDAD-Y-HISTORIAL.md](COMPATIBILIDAD-Y-HISTORIAL.md) | Compatibilidad con pos-online; qué copiar; **docs solo en historial** (commit y rutas para recuperar). |
| [historial/README.md](historial/README.md) | Cómo recuperar docs que solo existen en commits anteriores. |

---

## 2. Configuración central y UI

| Documento / Recurso | Descripción |
|---------------------|-------------|
| **config/fastflow-config.json** (en la raíz del repo) | Un solo archivo con todas las configuraciones: Jenkins, Registry, Pipeline, Terraform, K8s, Dashboard. |
| **config/README.md** | Cómo editar el config, override con variables de entorno, sincronizar con Jenkins. |
| **deploy/config-ui.html** | UI para ver y administrar todas las configuraciones. Generar con `node scripts/generate-config-ui.js`. Opcional: `node scripts/serve-config-ui.js` y abrir http://localhost:9090/config-ui.html |

---

## 3. Requisitos e instalación

| Documento | Descripción |
|-----------|-------------|
| [instalacion/REQUISITOS.md](instalacion/REQUISITOS.md) | Requisitos del entorno (JDK, Maven, Docker, generic-model). |
| [instalacion/INSTALAR-JENKINS.md](instalacion/INSTALAR-JENKINS.md) | Instalación de Jenkins (Java, job Pipeline from SCM, credenciales registry). |
| **[instalacion/GUIA-PONER-JENKINS.md](instalacion/GUIA-PONER-JENKINS.md)** | **Poner Jenkins de cero:** Ubuntu, Amazon Linux 2, WAR; unlock, plugins, Pipeline from SCM; enlaces al flujo UnClic. |

---

## 4. Pipeline, build y registry

| Documento | Descripción |
|-----------|-------------|
| [pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md](pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md) | Pipeline Jenkins: test → package → build imagen → push a registry. |
| [pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md](pipeline-y-registry/BUILD-REGISTRY-FASTFLOW.md) | Build de imagen Docker y uso del registry (tags, push). |
| **[POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md)** | **pos-online: instalar Docker en la EC2 de Jenkins** — Si el stage "Build image" falla con `docker: command not found`, pasos para instalar Docker (AL2/AL2023) y que el usuario `jenkins` pueda usarlo. |
| **[POS-ONLINE-FASTFLOW-HECHO-Y-POR-HACER.md](POS-ONLINE-FASTFLOW-HECHO-Y-POR-HACER.md)** | **pos-online FastFlow: hecho y por hacer** — Estado actual (swap, Jenkinsfile, docs) y checklist (persistir swap, Build Now, Docker, registry). |
| **[POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md)** | **pos-online-pipeline: logs explicados** — Console Output del job explicado línea por línea (Checkout, Prepare, Build, Test, Package, Build image, Push, Deploy, Verify, Post). |
| [pipeline-y-registry/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md](pipeline-y-registry/FLUJO-COMMIT-A-REGISTRY-TEMPLATE.md) | Plantilla: flujo commit → pipeline → registry → deploy/rollback. |
| [pipeline-y-registry/REQUIREMENTS-DEMO-AWS-JENKINS.md](pipeline-y-registry/REQUIREMENTS-DEMO-AWS-JENKINS.md) | Requerimientos demo AWS con Jenkins (si aplica). |
| **[VERIFICACION-FLUJO-UN-COMMIT-LEVANTA-INSTANCIA.md](VERIFICACION-FLUJO-UN-COMMIT-LEVANTA-INSTANCIA.md)** | **Comprobar si un commit recorre todo el flujo hasta levantar la app (POS) de nuevo:** qué hay hoy, qué falta (Maven, job Jenkins, Deploy real, 8111) y lista mínima para que se cumpla. |
| **[GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md](GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md)** | **Guía única AWS gratis:** cada quien con **su sistema retail automatizado con FastFlow, gratis, operando como en producción**. Instalar (OS + Jenkins), ver, usar y probar cada parte. |
| [EMPIEZA-AQUI-AWS-GRATIS-Y-AGENTES.md](EMPIEZA-AQUI-AWS-GRATIS-Y-AGENTES.md) | Punto de entrada: desplegar gratis en AWS, transferir repo, usar agentes. |
| **Interfaz central (deploy/)** | **deploy/dashboard-flujo-pos-jenkins.html** — Ver todo el flujo (commit → Jenkins → registry → app). URLs configurables para AWS (IP EC2). |
| **[AWS — Terraform](../../manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md)** | **INSTRUCCIONES-DEPLOY-AWS-GRATIS.md** (lista única), **INICIO-RAPIDO-AWS-GRATIS.md**, **REPLICAR-AWS-NUEVO-DUENO.md** en [manifests/terraform/jenkins-aws/](../../manifests/terraform/jenkins-aws/). |

---

## 5. Pos-online (implementación y repo)

| Documento | Descripción |
|-----------|-------------|
| **[pos-online/README.md](pos-online/README.md)** | **Índice pos-online** y enlace a la guía AWS gratis (instalar, ver, usar, probar; PoC por persona). |
| **[pos-online/GUIA-FLUJO-FINAL-POS-ONLINE.md](pos-online/GUIA-FLUJO-FINAL-POS-ONLINE.md)** | Flujo final: qué comando, qué output, dónde revisar (build, app, Jenkins, registry, dashboard). |
| [pos-online/POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md](pos-online/POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md) | Implementar en pos-online: Jenkins, Registry, demo local. |
| [pos-online/FLUJO-COMMIT-Y-USUARIO-FINAL.md](pos-online/FLUJO-COMMIT-Y-USUARIO-FINAL.md) | Flujo de commit y flujo usuario final. |
| [pos-online/CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md](pos-online/CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md) | Checklist repo listo para usuario final. |
| [pos-online/INVENTARIO-POS-ONLINE-REPO-USUARIO-FINAL.md](pos-online/INVENTARIO-POS-ONLINE-REPO-USUARIO-FINAL.md) | Inventario de lo que debe tener el repo pos-online. |

---

## 6. HTTPS, Gitea, Jenkins y demo (usuario invitado solo POS)

| Documento | Descripción |
|-----------|-------------|
| **[GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md](GITEA-JENKINS-HTTPS-USUARIO-POS-Y-LINK-DEMO.md)** | **Gitea y Jenkins con HTTPS, usuario restringido solo a proyectos POS, y enlaces de demo:** cómo disponer Gitea/Jenkins por HTTPS, crear usuario invitado con acceso solo a repos/jobs del POS, poner en la web el link a la demo del POS y enlaces a Jenkins/Gitea que redirijan con sesión iniciada (credenciales invitado o auto-login). Incluye checklist de ejecución. |
| [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md) | HTTPS: Nginx + Let's Encrypt para gitea.unclic.consulting y jenkins.unclic.consulting. |
| [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md) | Configurar Gitea/Jenkins (HTTPS, credenciales, usuarios y permisos). |

---

## 7. Tests y validación

| Documento | Descripción |
|-----------|-------------|
| [tests-y-validacion/TESTS-Y-VALIDACION-LOCAL.md](tests-y-validacion/TESTS-Y-VALIDACION-LOCAL.md) | Scripts de validación (Jenkinsfile, Terraform, K8s) y simulación pipeline. |
| [tests-y-validacion/CODIGO-DOCUMENTADO-Y-TESTS.md](tests-y-validacion/CODIGO-DOCUMENTADO-Y-TESTS.md) | Resumen de validaciones y pruebas (Maven + scripts). |

---

## Compatibilidad

- **Punto de entrada pos-online:** [pos-online/README.md](pos-online/README.md) — lista de documentos de esta carpeta y rutas que sí existen en el repo.
- Para aplicar en el repo pos-online basta con **pos-online/** y **pipeline-y-registry/**; el resto de docs de este toolkit complementa instalación y tests.

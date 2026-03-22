# Cuatro planes curados de integración OSS (estilo UnClic / servidor propio)

**Propósito:** traducir el **catálogo** del atlas y los docs en **cuatro arquetipos** listos para explicar a clientes o para diseñar **un sistema integrado en servidor(es) propios**. Cada plan nombra **productos mundiales muy usados** solo como **referencia de categoría** (no implica afiliación ni que sean reemplazos legales “plug and play”).

**Prerrequisito:** decisiones de duplicados y default UnClic → [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](CURACION-DUPLICADOS-Y-ELECCION-UNClic.md).

**Mapa técnico único:** [MAPA-STACK-OSS-UNIFICADO-CAPAS-MICROS-ADAPTADORES.md](MAPA-STACK-OSS-UNIFICADO-CAPAS-MICROS-ADAPTADORES.md).

---

## Cómo contrastar con vídeo / tutoriales (p. ej. YouTube)

No dependemos de un canal concreto; usa **búsquedas** que cruzan herramienta + self-host:

| Tema | Ejemplos de búsqueda |
|------|----------------------|
| Suite archivos + office | `Nextcloud docker self hosted`, `Collabora online docker compose` |
| Chat empresa | `Mattermost docker install`, `self hosted slack alternative` |
| Git + CI | `Gitea Jenkins webhook`, `self hosted github alternative` |
| ERP | `ERPNext docker production`, `Frappe bench install` |
| Virtualización | `XCP-ng homelab`, `Xen Orchestra install` |
| Grist | `lawrence.video grist` (tutorial ya enlazado en [implementacion/13-GRIST-HOJA-RELACIONAL-SELF-HOST.md](implementacion/13-GRIST-HOJA-RELACIONAL-SELF-HOST.md)) |
| NetAlertX (red LAN) | `NetAlertX docker self hosted`, Lawrence Systems + «NetAlertX» — vídeo [youtube.com/watch?v=R3b5cxLZMpo](https://www.youtube.com/watch?v=R3b5cxLZMpo); guía [implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md](implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md) |
| RustDesk (remoto) | `RustDesk docker self host`, Lawrence — [youtube.com/watch?v=FIEcTNjFZNA](https://www.youtube.com/watch?v=FIEcTNjFZNA); [implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md](implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md) |

**Criterio:** priorizar vídeos que muestren **TLS**, **volúmenes**, **backups** y **versión** de imagen acorde a la doc oficial del proyecto.

---

## Resumen de los 4 planes

| # | Nombre corto | Analogía “productos mundiales” | Integración en servidor propio |
|---|----------------|----------------------------------|--------------------------------|
| **1** | **UnClic + FastFlow** | Sitio comercial + pipeline como **GitHub Actions / Netlify** (categoría) | Next export + API + Gitea + Jenkins + registry + proxy; **NetAlertX opc.** en red admin (LAN); **RustDesk opc.** para soporte a VPS |
| **2** | **Suite trabajo y archivos** | **Google Workspace + Slack + Zoom/Drive** (categorías) | Nextcloud + Collabora + Mattermost + BBB (o Jitsi) + Keycloak + **RustDesk** soporte puestos |
| **3** | **Plataforma de desarrollo** | **GitHub / GitLab SaaS + artefactos** (categoría) | Gitea + Jenkins + Registry + SonarQube Community (opc.) + Keycloak + Uptime Kuma + **RustDesk** a runners/builders |
| **4** | **Negocio completo on-prem** | Combinación **2 + 3 + CRM/BI/PM** (como stack interno grande) | VM o contenedores por dominio bajo **hipervisor** (XCP-ng + XO) o un solo host Docker fuerte + **RustDesk** MSP |

---

## Plan 1 — UnClic (producto web + FastFlow + fiscal MX opcional)

### Referencia mercado (categorías)

- Alojamiento web gestionado, CI en nube, registro de contenedores: **Vercel / Netlify / GitHub Actions / ECR** (solo como *idea de capacidades*).

### Stack curado (el que este repo persigue)

| Capa | Pieza | Rol |
|------|--------|-----|
| L17 | **Next.js** (export) | Landing, `/integraciones`, formularios → API |
| L5 | **Hono API** + **ping** | Leads, webhooks futuros, orquestación demo |
| L9 | **Caddy / Nginx** | TLS, CORS en origen correcto |
| L1–L4 | **Gitea + Jenkins + Registry** | Commit → build → imagen → deploy |
| L6 | **PostgreSQL** (cuando se active) | Datos app |
| L8 | **LavinMQ o RabbitMQ** (opc.) | Webhook → worker |
| L14–L15 | **Stripe + Facturapi** | Pagos + CFDI (adaptadores) |
| L16 | **ERPNext** (opc.) | Maestros y pedidos vía adaptador REST |
| L12 (opc.) | **NetAlertX** | Solo si gestionas **varias máquinas / VLAN** en la misma red que el despliegue; inventario y alertas de cambios en LAN — ver [14-NETALERTX-…](implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md). **No** forma parte del sitio estático exportado. |
| L10 (opc.) | **RustDesk** | Soporte remoto a servidores/clientes sin **TeamViewer/AnyDesk** SaaS; servidor hbbs/hbbr en VM aislada + política de claves — [15-RUSTDESK-…](implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md). |

### Fases

1. **MVP:** sitio estático + API en subdominio + TLS + leads.  
2. **E2:** compose estable + CORS prod + pipeline Jenkins publicando imágenes.  
3. **E3+:** Stripe + cola + ERPNext + Keycloak en rutas sensibles.  
4. **Opc. homelab/oficina:** NetAlertX en segmento interno para visibilidad de red (tras revisar legalidad del escaneo).  
5. **Opc. soporte:** RustDesk self-host para operar despliegues con consentimiento del cliente.

### Diagrama lógico (igual al mapa maestro)

Ver figura en [MAPA-STACK-OSS-UNIFICADO-CAPAS-MICROS-ADAPTADORES.md](MAPA-STACK-OSS-UNIFICADO-CAPAS-MICROS-ADAPTADORES.md) §1.

---

## Plan 2 — Suite colaboración y archivos (análogo Workspace + Slack + videollamadas)

### Referencia mercado (categorías)

| Categoría global | Ejemplos de producto (referencia) |
|------------------|-----------------------------------|
| Archivos + sync | Google Drive, Dropbox, OneDrive |
| Documentos | Google Docs, Word online |
| Chat equipo | Slack, Microsoft Teams (chat) |
| Videollamadas | Zoom, Google Meet |
| Identidad | Google / Microsoft IdP |
| Soporte remoto | TeamViewer, AnyDesk, Chrome Remote Desktop |

### Stack OSS curado en **1–2 servidores** (integración)

| Dominio funcional | OSS | Notas |
|-------------------|-----|--------|
| Archivos + calendario + enlace externo | **Nextcloud** | Hub principal; TLS obligatorio |
| Ofimática | **Collabora** (o OnlyOffice) | Integrado con Nextcloud |
| Chat | **Mattermost** | Equipos, canales, integraciones |
| Videollamadas | **BigBlueButton** (escala) o **Jitsi** (simple) | BBB más pesado; planificar CPU/red |
| SSO | **Keycloak** | OIDC para Nextcloud + Mattermost (según doc de cada uno) |
| Formularios internos | **Formbricks** (opc.) | Revisar cambios de SSO en changelog |
| Tablas / mini-apps | **Grist** | CSV → app; ver [13-GRIST-…](implementacion/13-GRIST-HOJA-RELACIONAL-SELF-HOST.md) |
| Estado servicios | **Uptime Kuma** | Health público/privado |
| Red / descubrimiento | **NetAlertX** | Dispositivos en LAN, plugins (p. ej. UniFi), alertas ante cambios; [14-NETALERTX-…](implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md) |
| Soporte remoto | **RustDesk** | Alternativa OSS a TeamViewer/AnyDesk; servidor propio; [15-RUSTDESK-…](implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md) |

### Despliegue tipo “un servidor fuerte”

- **Opción A — Todo en Docker:** redes Docker por zona (`frontend_proxy`, `collab`, `bbb` aislado si aplica).  
- **Opción B — VMs:** una VM **collab** (Nextcloud+Collabora), una VM **comms** (Mattermost), BBB en host dedicado si hay carga.

### Fases

1. **MVP:** Nextcloud + TLS + backups + cuentas.  
2. **Fase 2:** Mattermost + Keycloak (login único donde sea posible).  
3. **Fase 3:** Collabora + Grist + Formbricks; BBB cuando haya ancho de banda y CPU.

### Qué reutiliza del catálogo UnClic

Mismas piezas **Keycloak**, **proxy TLS**, disciplina **adaptadores** y [OPERACION-PRODUCCION-CHECKLIST.md](implementacion/OPERACION-PRODUCCION-CHECKLIST.md). No exige el sitio Next de UnClic.

---

## Plan 3 — Plataforma desarrollo (análogo GitHub / GitLab cloud + CI)

### Referencia mercado (categorías)

- **GitHub.com**, **GitLab SaaS**, **Bitbucket Cloud**: repositorios, MR/PR, CI, paquetes.

### Stack OSS curado integrado

| Función | OSS | Notas |
|---------|-----|--------|
| Git + UI + permisos | **Gitea** | Organizaciones, repos, webhooks |
| CI | **Jenkins** | Pipelines declarativos; agents Docker o SSH |
| Imágenes | **Docker Registry** o **Harbor** | Harbor si escaneo y políticas |
| Calidad (opc.) | **SonarQube Community** | Un solo proyecto para empezar |
| Secretos build | **Vaultwarden** o secretos Jenkins nativos | Política por equipo |
| SSO | **Keycloak** | Opcional; Gitea soporta OAuth |
| Monitor | **Prometheus + Grafana** + **Uptime Kuma** | Métricas Jenkins + disponibilidad |
| Red interna (opc.) | **NetAlertX** | Inventario dinámico de nodos y runners en la red de desarrollo |
| Soporte remoto | **RustDesk** | Acceso a estaciones de desarrollo o servidores de build con auditoría y claves propias |

### Un servidor / dos nodos

- **Nodo 1:** Gitea + Registry + Jenkins (CPU y disco para workspace de build).  
- **Nodo 2 (opc.):** runners pesados o SonarQube para no matar el interactive de Gitea.

### Fases

1. Gitea + TLS + backups; 2) Jenkins + webhook push; 3) Registry + promoción de tags; 4) Sonar opcional.

### Qué reutiliza del catálogo UnClic

**Idéntico** al brazo L1–L4 del Plan 1; es la base del **toolkit FastFlow** documentado para pos-online.

---

## Plan 4 — Sistema integrado “negocio en servidor(es) propios” (Vates + planes 2 y 3)

### Referencia mercado (categorías)

Combinación de **suite ofimática**, **desarrollo**, **CRM**, **BI**, **PM**, **virtualización** — como grandes cuentas usan **portales + data center**, aquí en versión **OSS + self-host**.

### Stack curado por **capas lógicas** (mismo catálogo recopilado)

| Capa | OSS | Comentario |
|------|-----|------------|
| **HV** | **XCP-ng + Xen Orchestra** | VMs para aislar BBB, Nextcloud pesado, ERP |
| L1–L4 | Gitea, Jenkins, Registry | Igual Plan 3 |
| Collab | Nextcloud, Collabora, Mattermost, BBB | Igual Plan 2 |
| L10 | Keycloak + LDAP opcional | Directorio único |
| Soporte remoto | **RustDesk** | MSP / helpdesk; ver [15-RUSTDESK-…](implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md) |
| L16 CRM | **EspoCRM** | Ventas; Advanced Pack si aplica licencia |
| BI | **Metabase** | Dashboards sobre PG/servicios |
| PM | **Plane** | Open core; issues/roadmap |
| Monitoreo | **Netdata** + **Uptime Kuma** + **NetAlertX** | Netdata/Kuma: host y HTTP; **NetAlertX**: descubrimiento LAN y alertas de cambio (complementa **NetBox**) |
| Infra CMDB | **NetBox** | Red y DC documentados |
| Inventario | **Snipe-IT** | Hardware |
| Datos tabulares | **Grist** | Ops/finanzas internas |
| L19 radar | **Open WebUI** + **Ollama** | Solo red interna + GPU si hay |

### Estilo de integración deseado (principios)

1. **Un hipervisor o varias VMs** antes de mezclar todo en un solo Compose caótico.  
2. **Identidad central** (Keycloak) y **proxy TLS** homogéneo (Caddy/Traefik).  
3. **Backups por VM o por volumen** + prueba de restore documentada.  
4. **No exponer** Metabase, Jenkins, XO a Internet sin VPN o allowlist.

### Fases

1. HV + 2 VM (edge/proxy + Gitea).  
2. VM collab (Nextcloud).  
3. VM comms (Mattermost) + BBB cuando proceda.  
4. ERP/CRM/BI/PM según prioridad de negocio.  
5. L19 solo con política de datos firmada.

### Qué reutiliza del Plan 1 (UnClic)

Si esta empresa también **vende por web** con tu stack: la **API + Next** del Plan 1 vive en **una VM** o contenedor aparte, con los mismos adaptadores Stripe/Facturapi; el resto del Plan 4 es **back-office y plataforma**.

---

## Matriz rápida: qué plan elegir

| Situación del cliente | Plan |
|------------------------|------|
| Solo web comercial + pipeline + POS/retail integrado FastFlow | **1** |
| Huye de Drive/Slack/Meet y quiere todo interno | **2** |
| Equipo dev quiere salir de GitHub cloud con CI propio | **3** |
| MSP / fabricante / empresa mediana “todo en casa” | **4** |

---

## Mantenimiento de estos planes

- Tras cada cambio grande en `ossAtlasTools` o [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md), revisar que los **cuatro planes** sigan alineados con [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](CURACION-DUPLICADOS-Y-ELECCION-UNClic.md).  
- Los **nombres comerciales** en este doc son **solo analogías** para comunicación con no técnicos.

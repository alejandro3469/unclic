# Empresa operando casi 100% OSS — referencia Vates (2025)

Sirve para **refinar el plan UnClic**: política de sourcing explícita, **escala organizativa sin rearmar el datacenter**, sustituciones documentadas (por qué cambian de herramienta), **radar** vs **producción**, y lista de piezas **bajo observación** (riesgo operativo).

## Fuentes (orden de lectura)

1. **Lista, política y narrativa (fuente primaria):** [The Open Source we use at Vates: 2025 edition](https://vates.tech/blog/the-open-source-we-use-at-vates-2025-edition/) — Olivier Lambert, 23 abr 2025.  
2. **Modelo de negocio y compromiso OSS:** [Vates — Open Source Commitment](https://vates.tech/en/about-vates/open-source-commitment/).  
3. **Contexto en vídeo (inglés):** Lawrence Systems — *Building a Business on Open Source: How Vates Gets It Right* — [hilo con enlace al YouTube](https://forums.lawrencesystems.com/t/building-a-business-on-open-source-how-vates-gets-it-right-youtube-release/24788).  
4. **Mapa en UnClic:** **`/integraciones`**, filtro **Operación empresa OSS** (`lib/copy.ts`).  
5. **Capas L1–L19 + HV:** [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](../PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) §“Caso empresa OSS”.

---

## Contexto: escala ~3 → ~100 personas, infra “casi igual”

Del artículo: crecimiento fuerte **sin** migrar masivamente a nube ni multiplicar racks. Siguen **tres servidores de cómputo** comprados en **2019**, con **RAM reacondicionada** y **dos SSD NVMe** añadidos; servidor de **almacenamiento** ya era reacondicionado y amortizado. Mensaje para el plan: **optimizar equipos, runbooks y herramientas** antes de asumir capex/opex de más hierro o SaaS.

---

## Política de sourcing (texto operativo, alineada al artículo)

Orden de preferencia que Vates **formalizó** al crecer y delegar sysadmin:

1. **Por defecto:** herramientas **open source** y **self-hosted**.  
2. **Si no hay opción viable OSS:** usar la **mejor herramienta self-hosted** disponible (aunque no sea OSS) y **cuestionar esa elección cada año**.  
3. **Si hace falta SaaS:** priorizar proveedores **de la región** (ej. Francia / Europa), **alineados con estándares abiertos** y **prácticas responsables de datos**.

**Excepciones de larga duración** que ellos reconocen: **HR SaaS (Lucca)**, software de **contabilidad** o **cumplimiento** de nicho. El marco guía **la mayoría** de decisiones; no es dogma ciego.

**Implicación UnClic:** en propuestas a cliente, dejar escrito **qué categorías** son OSS self-host, qué es **excepción** (y por qué), y **cadencia de revisión** (anual mínimo para lo “cerrado” o dudoso).

---

## Nuevas herramientas y sustituciones (2025) — con el “por qué”

| Pieza | Rol | Nota del artículo (útil para el plan) |
|-------|-----|----------------------------------------|
| **Grist** | Hojas relacionales / apps ligeras | Entre Excel y Access; **Calc + Collabora** siguen para hojas simples. Tutorial Docker + API (Lawrence Systems, 2025): [13-GRIST-HOJA-RELACIONAL-SELF-HOST.md](13-GRIST-HOJA-RELACIONAL-SELF-HOST.md). |
| **Plane** | PM (issues, sprints, roadmap) | Sustituye **Wekan**; **open core** — aceptan el trade-off por madurez y cercanía a **Jira** con proyectos más complejos. |
| **Metabase** | BI / dashboards | Sustituye enfoque **Kibana + ElasticSearch** cuando los datos son más **relacionales** y maduros. |
| **Formbricks** | Formularios / feedback | Estilo Google Forms self-host; **advertencia:** en una actualización reciente **quitaron SSO** — seguir changelog si dependes de SSO. |
| **Docusaurus** | Docs de producto (XCP-ng, XO) | Sustituye **VuePress** (deprecado); **búsqueda local (LunR)**, sin depender de terceros para indexar. |
| **BigBlueButton** | Videoconferencia | Sustituye **Jitsi** para reuniones **más grandes** y redes complejas; más latencia **por diseño**; **grabaciones** importantes para formación y all-hands. |
| **Vaultwarden** | Passwords (compatible Bitwarden) | Mejor que “solo Firefox”; compartir en equipo y **2FA** integrado. |

---

## Radar (no en producción aún en el relato)

| Pieza | Estado | Detalle citado |
|-------|--------|----------------|
| **ClickHouse** | Candidato data warehouse | Evaluación en meses; necesidad analítica creciente. |
| **Open WebUI** | Experimentación | UI tipo ChatGPT self-host; **servidor GPU 8×16 GiB**; modelos locales (ej. LLaMA); **primeros días, prometedor**. |

En el plan UnClic: mapear como **L19** (UI + inferencia) y **L6/analítica** (ClickHouse) solo tras **PoC** y política de datos — ver [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](../PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) §L19.

---

## Bajo observación (riesgo / posible sustitución)

| Pieza | Problema / hipótesis | Dirección |
|-------|----------------------|-----------|
| **Zammad** | Rendimiento y **consumo de recursos** | Valorar **dividir** funciones o reemplazo especializado en soporte técnico; mencionan **Grafana OnCall** para escalaciones. |
| **Remark.js** (slides) | Sigue funcionando | Explorando **Marp** como flujo más moderno desde Markdown. |

**Práctica a copiar:** mantener en el plan una tabla **“bajo observación”** (como deuda de tooling), no solo backlog de features.

---

## Stack estable (sin cambios sustanciales en el artículo)

- **Virtualización y backup:** XCP-ng, Xen Orchestra  
- **SSO / directorio:** OpenLDAP, Keycloak  
- **Correo y calendarios:** BlueMind, CalDAV  
- **Archivos:** Nextcloud  
- **Chat:** Mattermost  
- **Blog y foros:** Ghost, NodeBB  
- **Código:** Gitea  
- **Infra (documentación de red/DCI):** NetBox  
- **Inventario hardware:** Snipe-IT  
- **Estado de servicios:** Uptime Kuma  
- **Monitorización:** Netdata (**cartera UnClic:** añadir **NetAlertX** para descubrimiento/alertas en LAN — [14-NETALERTX-RED-DESCOBERTA-MONITOR.md](14-NETALERTX-RED-DESCOBERTA-MONITOR.md))  
- **Build:** Koji  
- **CDN (paquetes XCP-ng):** Mirrorbits  
- **Marketing automation:** Mautic  
- **CRM:** EspoCRM — nota: usan **Advanced Pack** para automatización de ventas; uso creciente, satisfacción explícita en el post  
- **Slides (legacy activo):** Remark.js  
- **Portapapeles / paste seguro:** PrivateBin  

---

## Doble demostración (mensaje estratégico)

Del cierre del artículo:

1. **Sí** se puede **operar a escala** confiando en OSS (y self-host donde aplica).  
2. **Sí** se puede **crecer un negocio sostenible construyendo** OSS (ej. **XCP-ng**, **Xen Orchestra**) que otros usan en datacenters.

Para UnClic: separar en propuestas **“operación interna con OSS”** vs **“producto/servicio que vendemos”**; el toolkit FastFlow encaja en el segundo eje sin confundirlo con clonar el stack entero de Vates.

---

## Cómo refinar nuestro plan (checklist corta)

- [ ] **Política por escrito** (3 niveles + excepciones explícitas + revisión anual de lo no-OSS).  
- [ ] **Open core** permitido con **criterio** (ej. Plane): documentar licencia, límites y coste.  
- [ ] **Radar anual:** ClickHouse, Open WebUI, etc. — no mezclar con “prometido al cliente”.  
- [ ] **Observación activa:** Zammad-like — métricas (CPU, latencia tickets) y alternativas antes del cuello de botella.  
- [ ] **Cambios de herramienta con causa** (Metabase vs ELK, BBB vs Jitsi) para no “cambiar por moda”.  
- [ ] **Excepciones regionales SaaS** cuando el cliente exija residencia de datos UE (alineado al punto 3 de la política).

---

## Relación con UnClic (landing + API)

UnClic **no** sustituye ese stack empresarial; documenta **capas** y **integraciones** de referencia. Para despliegue y checklist operativo: [README.md](README.md) y [OPERACION-PRODUCCION-CHECKLIST.md](OPERACION-PRODUCCION-CHECKLIST.md).

### Soporte remoto (cartera UnClic, no en lista Vates 2025)

Para **helpdesk / MSP** sin **TeamViewer–like** SaaS: **RustDesk** con servidor propio — [15-RUSTDESK-REMOTO-SELF-HOST.md](15-RUSTDESK-REMOTO-SELF-HOST.md) (tutorial [Lawrence Systems](https://www.youtube.com/watch?v=FIEcTNjFZNA)).

### Contraste: economía nube vs hardware propio (37signals)

Vates centra el relato en **qué** ejecutar (OSS, self-host, política). Otro ancla útil para **desarrollador** es el episodio **REWORK — *Leaving the Cloud*** (37signals, 2022): argumenta **cuándo** la nube compensa vs **owned + colocation**, desmonta el mito de “menos equipo de ops” a escala mediana y habla de lock-in y TCO. Resumen operativo: [12-37signals-REWORK-LEAVING-CLOUD-DESARROLLADOR.md](12-37signals-REWORK-LEAVING-CLOUD-DESARROLLADOR.md).

# Curación: duplicados entre capas / herramientas y **elección UnClic**

**Propósito:** donde el atlas y el plan listan **varias OSS para el mismo trabajo**, esta tabla fija **qué usa el producto UnClic** (repo + demos FastFlow) y **qué queda como alternativa** para otros arquetipos o clientes.

**Relacionado:** [PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md](PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md) · [PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md](PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md) · `/integraciones` (`lib/copy.ts`).

---

## Leyenda

| Columna | Significado |
|---------|-------------|
| **Elección UnClic** | Default para **este** repo y narrativa FastFlow/UnClic salvo decisión explícita por cliente. |
| **En atlas** | Sigue en `/integraciones` como opción educativa. |
| **No descartado** | Puede usarse en **planes 2–4** (arquetipos) aunque no sea default UnClic. |

---

## 1. Entrega y CI (L1–L4)

| Función | Candidatos en cartera | **Elección UnClic** | Por qué |
|---------|------------------------|---------------------|---------|
| Git self-host | Gitea, GitLab CE, Forgejo | **Gitea** | Alineado a toolkit FastFlow documentado; ligero; webhooks → Jenkins. |
| CI / pipeline | Jenkins, Woodpecker, GitLab CI, Drone | **Jenkins** | Ya en demos y `Jenkinsfile`; Groovy + pipelines POS documentados. |
| Registry imágenes | Docker Registry, Harbor, Gitea packages | **Docker Registry** (o Harbor si el cliente exige escaneo) | Mínimo viable; Harbor si política de seguridad lo exige. |
| IaC | OpenTofu, Terraform, Pulumi | **OpenTofu** (preferido en doc) | Licencia Hashicorp; mantener **términos** si quedas en Terraform. |

---

## 2. Runtime, proxy, front (L5, L9, L17)

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| App web pública | Next.js, Astro, SvelteKit | **Next.js** (export estático + API routes donde aplique) | Código actual del sitio; Astro solo como alternativa contenido/L17 en plan. |
| API servicios | Hono, Express, Fastify | **Hono** | `services/api` existente + OpenAPI. |
| PaaS self-host opcional | Coolify, CapRover, Dokku | **Coolify** (pendiente en matriz) | Doc enlazada en PLAN-OSS; evaluación por VPS cliente. |
| Reverse proxy + TLS | Caddy, Traefik, Nginx | **Caddy o Nginx** | Caddy por simplicidad TLS; Nginx si el cliente ya está estandarizado. |

---

## 3. Datos, cache, colas (L6–L8)

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| DB transaccional app | PostgreSQL, MariaDB, SQLite | **PostgreSQL** (cuando se active en compose) | Encaje típico API + ERPNext/MariaDB separado. |
| Edge / SQLite distribuido | Turso, libSQL | **No default UnClic** | Curación “2026”; PoC aparte. |
| Cache | Redis, Valkey, Memcached | **Redis o Valkey** | Una sola pieza L7 cuando haga falta sesión/rate-limit. |
| Cola / broker | RabbitMQ, NATS, LavinMQ, Kafka | **LavinMQ o RabbitMQ** | **LavinMQ** si prioridad = ligero + AMQP; **RabbitMQ** si ya hay skills/equipo. **NATS** si modelo eventos pub/sub dominante. **Kafka** solo E4+ con volumen real. |
| OLAP / warehouse | ClickHouse, Metabase sobre PG | **Metabase → PG** primero; **ClickHouse** radar | UnClic no arranca en DWH; cliente analítico fuerte → plan 4. |
| Hojas → app interna | Grist, Airtable-like, solo Nextcloud | **Grist** (PoC) | Ya documentado §13 + Vates; no sustituye ERP. |

---

## 4. Identidad y secretos (L10–L11)

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| IdP / OIDC | Keycloak, Authelia, Zitadel | **Keycloak** (primera opción doc) | Más referencias en guías UnClic; Authelia si solo “login delante”. |
| Secretos equipo | Vaultwarden, Vault OSS | **Vaultwarden** (empresa); **no** bloquea UnClic web | Plan empresa / Vates. |

### 4b. Acceso remoto (soporte IT — no es IdP)

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| Escritorio remoto self-host | **RustDesk**, MeshCentral, Apache Guacamole | **RustDesk** | Docker oficial, clientes multiplataforma, analogía clara TeamViewer/AnyDesk; tutorial [Lawrence](https://www.youtube.com/watch?v=FIEcTNjFZNA) + [15-RUSTDESK-REMOTO-SELF-HOST.md](implementacion/15-RUSTDESK-REMOTO-SELF-HOST.md). |

---

## 5. Observabilidad (L12)

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| Métricas + dashboards | Prometheus+Grafana, Netdata, Uptime Kuma | **Prometheus + Grafana** para API/servicios; **Uptime Kuma** para HTTP externo | Encaje estándar con `services/api` scrape; Kuma barato para demos URL. |
| **Descubrimiento / inventario LAN** (hosts, cambios, plugins) | **NetAlertX**, LibreNMS, Zabbix, nmap manual | **NetAlertX** (opc.; **no** parte del runtime del sitio Next) | Complementa Kuma/Netdata: alertas ante equipos nuevos o cambios; tutorial [Lawrence Systems](https://www.youtube.com/watch?v=R3b5cxLZMpo) + [implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md](implementacion/14-NETALERTX-RED-DESCOBERTA-MONITOR.md). |

---

## 6. ERP y back-office (L16)

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| ERP integral | ERPNext, Odoo CE, Dolibarr | **ERPNext** | Ya en mapa maestro + adaptador REST Frappe en docs. |
| CRM solo | EspoCRM, SuiteCRM | **EspoCRM** | Atlas “operación empresa”; no es default **dentro** del repo UnClic hasta integración explícita. |

---

## 7. Pagos y fiscal (L14–L15) — casi nunca OSS puro

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| Pagos | Stripe, Whop, otros | **Stripe** (primero en matriz) | Adaptador en diseño; webhooks a API. |
| CFDI / PAC MX | Facturapi, ERP module | **Facturapi** + sync **ERPNext** cuando aplique | Legalidad y PAC; no reinventar timbrado. |

---

## 8. IA / agentes (L19)

| Función | Candidatos | **Elección UnClic** | Por qué |
|---------|------------|---------------------|---------|
| Inferencia local | Ollama, Docker Model Runner | **Ollama** (default doc) + **DMR** si estándar Docker estricto | Misma capa; elegir uno por cliente. |
| Workflows low-code | n8n, Windmill, Temporal | **n8n** (primera integración ligera) | Temporal si orquestación larga E4+. |
| Agentes código | LangGraph, crewai, etc. | **LangGraph** solo con caso claro | Worker aparte; no default sitio público. |
| UI chat interna | Open WebUI | **Open WebUI** (radar / PoC) | Tras Ollama/DMR y política de datos. |

---

## 9. Colaboración “tipo producto mundial” (no todo en repo UnClic)

Estas piezas **compiten** entre sí a nivel mercado; en **UnClic web** no van todas — sí en **planes 2–4**.

| Necesidad (análogo mundial) | Opciones OSS | Default **plan arquetipo 2** |
|-----------------------------|--------------|------------------------------|
| Chat (Slack / Teams) | Mattermost, Rocket.Chat, Element | **Mattermost** |
| Videollamadas (Zoom / Meet) | BigBlueButton, Jitsi | **BBB** si escala/red; **Jitsi** si simplicidad |
| Archivos (Drive / Dropbox) | Nextcloud, Seafile | **Nextcloud** |
| Ofimática (365 / Google Docs) | Collabora, OnlyOffice + Nextcloud | **Collabora** (mismo ecosistema Vates) |
| Calendario / correo grupo | (complejo) | **SaaS regional** o pieza específica cliente — ver plan 4 |

---

## 10. Reglas de uso de esta curación

1. **UnClic = fila “Elección UnClic”** para implementación en **este** repo y discurso “FastFlow demo”.  
2. **Cliente con stack distinto:** usar [PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md](PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md) y documentar desviación en propuesta.  
3. **Atlas** puede listar **más** herramientas que el repo; eso es intencional (cartera).  
4. Revisar esta tabla **trimestral** si cambia `PLAN-OSS` o dependencias mayores (ej. migración Jenkins → otro CI).

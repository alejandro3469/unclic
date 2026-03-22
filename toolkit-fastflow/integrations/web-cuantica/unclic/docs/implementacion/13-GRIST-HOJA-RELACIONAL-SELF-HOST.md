# Grist — hoja relacional self-host (referencia operativa)

**Qué es:** spreadsheet con **modelo relacional** (tablas enlazadas, vistas, permisos, historial), útil cuando un Excel “casi base de datos” ya no alcanza. Caso de uso citado en [Vates 2025](https://vates.tech/blog/the-open-source-we-use-at-vates-2025-edition/) junto a Calc + Collabora para casos simples.

**No sustituye** un ERP ni Postgres transaccional de tu API; encaja en **L6 / datos estructurados internos**, prototipos y equipos que quieren **CSV → app usable** sin desarrollar front desde cero.

---

## Fuentes oficiales

| Recurso | URL |
|---------|-----|
| Documentación | [support.getgrist.com](https://support.getgrist.com/) |
| **Grist en Docker** (instalación) | [Install Grist on Docker](https://support.getgrist.com/install/grist-on-docker/) |
| Código (core) | [github.com/gristlabs/grist-core](https://github.com/gristlabs/grist-core) |
| Canal YouTube (Grist Labs) | [YouTube @gristlabs](https://www.youtube.com/@gristlabs) |

---

## Tutorial en vídeo (Lawrence Systems, ago 2025)

Recorrido práctico: **Docker**, opciones de **autenticación**, **permisos**, importación, plantillas, formularios desde documento en blanco, **historial**, **widgets**, rendimiento e **API / integraciones**.

| Recurso | URL |
|---------|-----|
| Vídeo principal | [lawrence.video/grist](https://lawrence.video/grist) |
| Grist Con 2025 (evento) | [lawrence.video/gristcon](https://lawrence.video/gristcon) |
| Foro Lawrence Systems | Buscar **«Grist»** / *compose* en [forums.lawrencesystems.com](https://forums.lawrencesystems.com/) (hilos con ejemplos de Compose compartidos por la comunidad) |

**Capítulos del vídeo (mapa rápido):**

| Tiempo | Tema |
|--------|------|
| 00:00 | Qué es Grist |
| 00:44 | Precios y hosting (incl. self-host) |
| 02:00 | Autenticación |
| 02:45 | Docker |
| 04:32 | Permisos |
| 05:30 | Plantillas demo |
| 07:44 | Importar / exportar |
| 08:56 | De documento vacío a formulario |
| 11:20 | Historial de documento |
| 12:50 | Widgets personalizados |
| 14:10 | Rendimiento |
| 15:09 | Integraciones y API |

---

## Checklist antes de producción

- [ ] **TLS** delante (proxy ya usado en UnClic: Caddy / NPM / Traefik — ver [01-CORS-STATIC-API-TLS.md](01-CORS-STATIC-API-TLS.md)).  
- [ ] **Auth** acordada con el cliente (SSO/OIDC si aplica; revisar doc Grist para el modo elegido).  
- [ ] **Backups** del volumen / DB que use Grist + [OPERACION-PRODUCCION-CHECKLIST.md](OPERACION-PRODUCCION-CHECKLIST.md).  
- [ ] **Permisos** por equipo probados (no asumir “todos ven todo”).  
- [ ] Si expones **API**, rate limits y red interna / VPN según sensibilidad de datos.

---

## Relación con el plan UnClic

- Capas: **L6** (datos estructurados internos) en [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](../PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md); contexto empresa OSS en [11-EMPRESA-OSS-VATES-REFERENCIA.md](11-EMPRESA-OSS-VATES-REFERENCIA.md).  
- Atlas web: **`/integraciones`** — tarjeta **Grist** (doc oficial + enlace al tutorial Lawrence).

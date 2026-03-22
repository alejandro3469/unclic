# NetAlertX — descubrimiento y monitorización de red (self-host)

**Qué es:** herramienta **open source** para **descubrir dispositivos** en la red local, detectar **cambios** (nuevos equipos, IP, puertos, desconexiones), **enriquecer datos** con plugins y enviar **alertas**. Complementa a **Uptime Kuma** (HTTP/servicios) y **Netdata** (métricas por host): NetAlertX se centra en **visibilidad L2/L3** e **inventario dinámico**, incluso cosas que un firewall puede no listar si escaneas desde dentro.

**No sustituye** un SIEM enterprise ni la política de seguridad perimetral; **sí** ayuda en **homelab**, **oficina** y **plan 4** (operación on-prem).

---

## Fuentes oficiales

| Recurso | URL |
|---------|-----|
| Sitio / proyecto | [netalertx.com](https://netalertx.com/) |
| Documentación | [docs.netalertx.com](https://docs.netalertx.com/) |
| Instalación Docker | [DOCKER_INSTALLATION](https://docs.netalertx.com/DOCKER_INSTALLATION/) |
| GitHub | [github.com/netalertx/NetAlertX](https://github.com/netalertx/NetAlertX) |
| Imagen Docker (Hub) | [hub.docker.com/r/jokobsk/netalertx](https://hub.docker.com/r/jokobsk/netalertx) |

---

## Tutorial en vídeo (Lawrence Systems, jun 2025)

| Recurso | URL |
|---------|-----|
| Vídeo (YouTube) | [youtube.com/watch?v=R3b5cxLZMpo](https://www.youtube.com/watch?v=R3b5cxLZMpo) |
| Hilo foro (enlaces + contexto) | [forums.lawrencesystems.com — NetAlertX release](https://forums.lawrencesystems.com/t/discover-monitor-your-network-with-this-self-hosted-open-source-tool-youtube-release/25181) |

**Capítulos (referencia rápida):**

| Tiempo | Tema |
|--------|------|
| 00:00 | ¿Sabes qué hay en tu red? |
| 01:17 | Qué es NetAlertX y para qué sirve |
| 03:18 | Docker Compose |
| 03:42 | Configuración de red del contenedor |
| 05:58 | Descubrimientos programados |
| 09:00 | Plugins |
| 09:38 | Plugin UniFi |
| 10:33 | Dispositivos descubiertos y monitorización |
| 13:00 | Nmap manual y automático |
| 14:50 | Importar / exportar y mantenimiento |

---

## Relación con los planes UnClic

| Plan | Uso típico |
|------|------------|
| **1 — UnClic + FastFlow** | **Opcional** en la **red donde vive** el VPS/oficina del pipeline (no en el CDN del sitio estático). Útil si administras varias máquinas o VLANs detrás del mismo despliegue. |
| **2 — Suite trabajo** | Recomendado si hay **Wi‑Fi + LAN** con muchos clientes; detecta apariciones nuevas y cambios. |
| **3 — Plataforma dev** | Opcional en la **red de build** (runners, registry, Gitea); inventario de nodos y alertas. |
| **4 — Negocio on-prem** | Muy alineado con **MSP / varias VMs**; encaja junto a **Netdata**, **Uptime Kuma** y **NetBox** (CMDB más estático vs inventario activo). |

Documento maestro: [PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md](../PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md).

---

## Checklist operativo

- [ ] **Modo red Docker:** el contenedor debe ver las subredes a escanear (según doc: `network_mode: host` en Linux o configuración explícita de interfaces — **no copiar ciegamente** compose de terceros sin entender alcance).  
- [ ] **TLS** si expones la UI (idealmente **solo VPN** o red interna; no publicar escaneos a Internet).  
- [ ] **Legal / política:** escaneo solo en **redes que administres**; documentar en el cliente.  
- [ ] **Plugins** (UniFi, Pi-hole, Nmap, etc.): revisar [documentación de plugins](https://docs.netalertx.com/PLUGINS_DEV).  
- [ ] **Backups** de configuración / DB según [OPERACION-PRODUCCION-CHECKLIST.md](OPERACION-PRODUCCION-CHECKLIST.md).

---

## Curación (vs otras herramientas)

- **NetBox / Snipe-IT:** más **CMDB / inventario** manual o sync; NetAlertX aporta **descubrimiento activo/pasivo** y alertas de cambio.  
- **LibreNMS / Zabbix:** más pesados, enfoque **NMS** clásico; NetAlertX es más **ligero** para “¿qué apareció en mi LAN?”.  
- Ver también: [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](../CURACION-DUPLICADOS-Y-ELECCION-UNClic.md) §5.

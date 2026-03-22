# RustDesk — escritorio remoto self-host (alternativa TeamViewer / AnyDesk)

**Qué es:** cliente y servidor **open source** para **control remoto** multiplataforma con **servidor propio** (hbbs / hbbr), sin depender de la nube pública de RustDesk para señalización y relay si no quieres. Analogía de mercado: **TeamViewer**, **AnyDesk**, **Chrome Remote Desktop** (categorías).

**No es** un IdP (eso sigue siendo **Keycloak** u otro L10); es **acceso remoto** para soporte, administración de VMs o teletrabajo con **datos bajo tu control**.

---

## Fuentes oficiales

| Recurso | URL |
|---------|-----|
| Sitio | [rustdesk.com](https://rustdesk.com/) |
| Documentación | [rustdesk.com/docs](https://rustdesk.com/docs/en/) |
| Servidor OSS (Docker) | [Self-host — Docker](https://rustdesk.com/docs/en/self-host/rustdesk-server-oss/docker/) |
| GitHub (cliente/servidor) | [github.com/rustdesk/rustdesk](https://github.com/rustdesk/rustdesk) |

---

## Tutorial Lawrence Systems (feb 2025)

| Recurso | URL |
|---------|-----|
| Vídeo (YouTube) | [youtube.com/watch?v=FIEcTNjFZNA](https://www.youtube.com/watch?v=FIEcTNjFZNA) — *Open Source Self Hosted Teamviewer Replacement* |
| Hilo foro (contexto + enlaces) | [forums.lawrencesystems.com/t/…/24183](https://forums.lawrencesystems.com/t/open-source-self-hosted-teamviewer-replacement-youtube-release/24183) |

**Capítulos (referencia rápida):**

| Tiempo | Tema |
|--------|------|
| 00:00 | Acceso remoto self-host con RustDesk |
| 01:18 | Qué es RustDesk |
| 02:15 | Seguridad |
| 03:01 | Cómo funciona (ID / relay / cifrado) |
| 07:16 | Docker |
| 09:14 | Clientes |
| 11:15 | Funciones de conexión |

---

## Relación con los planes UnClic (1–4)

| Plan | Uso típico |
|------|------------|
| **1** | Soporte a **VPS / despliegues** del cliente sin SaaS de terceros; servidor RustDesk en VM pequeña o mismo host **aislado**. |
| **2** | Soporte a puestos con **Nextcloud / Mattermost**; acceso guiado a equipos de oficina. |
| **3** | Administración de **runners**, agents Jenkins o máquinas de build. |
| **4** | Operación **MSP / datacenter**: acceso a hosts tras VPN o bastión; combina con política de red y **NetBox** (qué existe) + **NetAlertX** (qué aparece). |

Índice maestro: [PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md](../PLANES-CURADOS-CUATRO-ARQUETIPOS-INTEGRACION.md).

---

## Seguridad (mínimo imprescindible)

- [ ] **No exponer** puertos del servidor a Internet sin **firewall**, **VPN** o **allowlist**; el vídeo Lawrence insiste en consideraciones de seguridad.  
- [ ] **Claves / key** del servidor: rotación y alcance por equipo documentado en [OPERACION-PRODUCCION-CHECKLIST.md](OPERACION-PRODUCCION-CHECKLIST.md).  
- [ ] **2FA / política de sesiones** en el lado cliente donde la plataforma lo permita; RustDesk no sustituye política corporativa completa.  
- [ ] **Solo redes y equipos** que la organización **autorice** por escrito (cumplimiento / GDPR / datos médicos, etc., van aparte).

---

## Curación (vs otras OSS)

| Alternativa | Cuándo considerarla |
|-------------|---------------------|
| **MeshCentral** | Gestión web intensiva, muchos endpoints Windows |
| **Apache Guacamole** | RDP/VNC/SSH vía navegador sin instalar cliente en el usuario |
| **RustDesk** | **Default cartera UnClic**: cliente ligero multi-OS, servidor Docker documentado, buena relación esfuerzo/resultado |

Tabla maestra: [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](../CURACION-DUPLICADOS-Y-ELECCION-UNClic.md) §4b.

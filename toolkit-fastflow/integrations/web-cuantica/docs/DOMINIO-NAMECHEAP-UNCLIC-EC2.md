# Usar el dominio unclic.consulting (Namecheap) con las instancias EC2

Si tienes el dominio **unclic.consulting** en Namecheap y quieres usarlo para **Jenkins** y **Gitea** (y opcionalmente la app POS) en lugar de las IPs, sigue estos pasos.

**Resumen:** En Namecheap → Advanced DNS añades registros **A** (o CNAME) que apunten subdominios a las IP públicas de las EC2. Luego configuras Jenkins y Gitea para usar esas URLs.

---

## 0. Interfaz Namecheap — qué ves (Domain Details)

Ruta: **Namecheap** → **Domain List** → **unclic.consulting** → **Manage**. Encabezado: **Domains → Details**, dominio **unclic.consulting**.

**Pestañas principales:** **Domain** | **Products** | **Sharing & Transfer** | **Advanced DNS**

### Pestaña Domain (Status & validity)

- **STATUS & VALIDITY:** ACTIVE, fechas de vigencia (ej. Mar 9, 2026 – Mar 9, 2027), **AUTO-RENEW**, **ADD YEARS**.
- **Domain Privacy PROTECTION:** mismas fechas, AUTO-RENEW, ADD YEARS, **SHOW DETAILS**.
- **PremiumDNS PROTECTION:** mismas fechas.
- **NAMESERVERS:** Namecheap PremiumDNS (u otros si los cambias).
- **REDIRECT DOMAIN:** mensaje tipo *"You haven't defined any Redirect Domain yet."* Botones **ADD REDIRECT**, **ADD WILDCARD REDIRECT**.
- **REDIRECT EMAIL:** *"Your domain is using other email service"* (si usas correo externo).
- **PRIVATE EMAIL:** oferta de correo @tu-dominio (BUY NOW).
- **DOMAIN CONTACTS (Whois):** Registrant, Administrator, Technical, Billing. Cada uno con **EDIT**. Los datos se guardan en WHOIS público.
- **OTHER DOMAIN SETTINGS:** Parking Page (OFF / TURN ON), **Sell Domain** (List a domain on the Marketplace, **SELL DOMAIN**).

### Pestaña Sharing & Transfer

- **Share Access:** cambiar permisos de los managers del dominio; **New Manager** (email o usuario Namecheap).
- **Change Ownership:** **New Owner** (transferir propiedad completa a otra persona/cuenta).
- **Transfer Out:** transferir el dominio a otro registrador. Requiere **Domain Lock** OFF y **Auth Code** (se envía al email del registrante). **Domain Lock:** ON | **UNLOCK**.

### Dónde está el DNS (registros A, MX, etc.)

Los registros que apuntan a tus EC2 (A, CNAME, MX) están en la pestaña **Advanced DNS**, sección **HOST RECORDS** (y **MAIL SETTINGS** para MX). No están en Domain ni en Sharing & Transfer.

### Pestaña Advanced DNS — qué ves (literal)

- **DNS TEMPLATES:** *Choose DNS Template* (selector de plantilla).
- **HOST RECORDS:** tabla con columnas **Type** | **Host** | **Value** | **TTL**. Botones **Actions**, **Filters**. Al final: **ADD NEW RECORD**, **SHOW MORE**.
- **DNSSEC:** Status.
- **MAIL SETTINGS:** *Custom MX*. Tabla Type | Host | Value | TTL. Botón **ADD NEW RECORD**.
- **DYNAMIC DNS:** Status.
- **PERSONAL DNS SERVER:** Register Nameserver, **ADD NAMESERVER**, Find Nameservers, Standard Nameservers, **SEARCH**.

Para añadir o editar un A record que apunte a una EC2: en **HOST RECORDS** usa **ADD NEW RECORD**, elige Type (A Record o A + Dynamic DNS Record), Host (ej. `jenkins`, `gitea`, `pos`), Value (IP de la EC2), TTL (p. ej. Automatic).

---

## 1. Subdominios sugeridos y estado DNS

| Servicio | Subdominio | IP en Namecheap (actual) | IP instancia EC2 actual | URL (HTTP) |
|----------|------------|--------------------------|--------------------------|------------|
| Raíz / Vantive | @, www | 3.22.236.150 | 3.22.236.150 (fastflow-vantive) | https://unclic.consulting, http://3.22.236.150 |
| Jenkins | jenkins | **18.119.157.22** | 18.218.37.76 (fastflow-jenkins-controller) | http://jenkins.unclic.consulting:8080 |
| Gitea | gitea | 13.58.58.245 | 13.58.58.245 (fastflow-gitea) | http://gitea.unclic.consulting:3000 |
| POS demo | pos | 13.58.172.235 | 13.58.172.235 (fastflow-pos-demo) | http://pos.unclic.consulting:8111 |

**Atención:** En Namecheap, **jenkins** apunta a **18.119.157.22**; la instancia **fastflow-jenkins-controller** actual tiene IP **18.218.37.76**. Si jenkins.unclic.consulting debe apuntar a la instancia nueva, en Namecheap → Advanced DNS cambia el registro **A** de **jenkins** a **18.218.37.76**.

Las IPs pueden cambiar si detienes/arrancas instancias sin Elastic IP; con Elastic IP fijas la IP y el DNS sigue funcionando.

---

## 2. Configurar DNS en Namecheap

1. Entra en **Namecheap** → **Domain List** → **unclic.consulting** → **Manage** → pestaña **Advanced DNS**.
2. En **HOST RECORDS** (o "ADD NEW RECORD") añade:

**Ejemplo para Jenkins (IP actual de la instancia):**

| Type | Host | Value | TTL |
|------|------|--------|-----|
| A Record (o A + Dynamic DNS) | jenkins | 18.218.37.76 | Automatic |

**Ejemplo para Gitea:**

| Type | Host | Value | TTL |
|------|------|--------|-----|
| A Record (o A + Dynamic DNS) | gitea | 13.58.58.245 | Automatic |

- **Host:** solo el subdominio: `jenkins`, `gitea`, `pos` (Namecheap añade unclic.consulting).
- **Value:** la IP pública de la EC2 correspondiente.
- Guarda los cambios. La propagación puede tardar unos minutos (1–30 min con TTL bajo).

3. **No borres** los registros que ya uses (MX para Google, CNAME de verificación, etc.). Solo añade o corrige los A necesarios.

### Estado actual DNS en Namecheap (HOST RECORDS — referencia)

Lo que ves en la tabla **HOST RECORDS** (columnas Type, Host, Value, TTL) en **Namecheap → Domain List → unclic.consulting → Manage → Advanced DNS**:

| Type | Host | Value | TTL | Uso |
|------|------|--------|-----|-----|
| A Record | @ | 3.22.236.150 | Automatic | Raíz → Vantive (fastflow-vantive) |
| A + Dynamic DNS | gitea | 13.58.58.245 | Automatic | gitea.unclic.consulting → Gitea |
| A + Dynamic DNS | jenkins | 18.119.157.22 | Automatic | jenkins.unclic.consulting → **Revisar:** instancia actual Jenkins = 18.218.37.76 |
| A Record | pos | 13.58.172.235 | Automatic | pos.unclic.consulting → POS demo (fastflow-pos-demo) |
| A Record | www | 3.22.236.150 | Automatic | www.unclic.consulting → Vantive |

**MAIL SETTINGS (Custom MX):**

| Type | Host | Value | TTL |
|------|------|--------|-----|
| MX Record | @ | SMTP.GOOGLE.COM. | 1 min |

**Si jenkins.unclic.consulting no abre la instancia actual:** en Namecheap cambia el valor del registro **jenkins** de `18.119.157.22` a `18.218.37.76` (IP actual de fastflow-jenkins-controller). Ver [URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md) para la tabla actual de instancias e IPs.

---

## 3. Usar las URLs en Gitea y Jenkins

**Gitea:** Si aún no has hecho "Install Gitea", en el asistente pon:
- **Server Domain:** `gitea.unclic.consulting`
- **Gitea Base URL:** `http://gitea.unclic.consulting:3000/`

Si Gitea ya está instalado, edita `/data/gitea/conf/app.ini` dentro del contenedor (o el volumen) y ajusta `ROOT_URL` y `DOMAIN`; luego reinicia el contenedor.

**Jenkins:** En **Manage Jenkins** → **System** → **Jenkins URL** puedes poner `http://jenkins.unclic.consulting:8080/`. Los jobs y notificaciones usarán esa URL.

**Jenkins (job) → Repository URL:** Si el repo está en Gitea, la URL puede ser `http://gitea.unclic.consulting:3000/tu-usuario/pos-online.git` (o HTTPS cuando lo tengas).

---

## 4. Puertos en la URL

Como Jenkins usa **8080** y Gitea **3000**, las URLs llevan puerto:
- http://jenkins.unclic.consulting**:**8080  
- http://gitea.unclic.consulting**:**3000  

Para evitar poner el puerto en el navegador puedes poner delante un **proxy inverso** (Nginx o Caddy) en una EC2 que escuche en 80/443 y redirija a 8080 y 3000; así accederías con http://jenkins.unclic.consulting y http://gitea.unclic.consulting. Opcional y un poco más de trabajo.

---

## 5. HTTPS (sitio seguro para móvil y clientes)

Para que el navegador muestre **candado** y puedas acceder desde el móvil o dar acceso a clientes sin "no seguro":
- Guía paso a paso: **[HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)** — configuración inicial de Gitea, Nginx + Let's Encrypt para https://gitea.unclic.consulting y https://jenkins.unclic.consulting, y opción Cloudflare.
- Resumen: Nginx en cada EC2 escuchando en 443 + certificado gratis (Let's Encrypt con `certbot --nginx`). Luego en Gitea y Jenkins cambias la URL a `https://...` (sin puerto).

---

## 6. Correo (unclic.consulting) y Google Workspace

El correo **@unclic.consulting** está configurado con **Google** (en Namecheap → Advanced DNS, **MAIL SETTINGS** → Custom MX: `@` → `SMTP.GOOGLE.COM.`). Añadir o cambiar A records para jenkins/gitea **no afecta** el correo. Puedes usar ese mismo correo como administrador en Gitea o en Jenkins.

### Qué ves al usar el correo con Google (Gmail / Workspace)

- **Inicio de sesión:** Cuenta **alejandro@unclic.consulting** (u otro usuario @unclic.consulting).
- **Cabecera / menú:** Enlace **Administrador: unclic.consulting** (abre una pestaña nueva) y **Consola del administrador** para gestionar el dominio y usuarios de Workspace.
- **Avisos típicos:** Por ejemplo *"Podrías perder el acceso a Gmail — Un teléfono de recuperación te ayudará si olvidas tu contraseña"* con botones **Descartar** o **Agregar teléfono de recuperación**.
- **Selector de cuentas:** "Ocultar más cuentas", "Predeterminada", "Agregar otra cuenta", "Salir de todas las cuentas" (Política de Privacidad, Condiciones del Servicio).
- **Accesibilidad:** Opción "Cómo usar Correo de unclic con lectores de pantalla" (ir al contenido).

### Planes y administración

- **Google Workspace** (workspace.google.com): correo corporativo @tu-dominio, Drive, Meet, Calendar, Chat, Docs, Sheets, Gemini, etc. Ediciones típicas: Starter, Standard, Plus, Enterprise (precios y funciones en la web oficial).
- **Consola del administrador:** gestionar usuarios, dominios, seguridad y facturación del Workspace de unclic.consulting.

---

## 7. Resumen rápido

| Dónde | Qué hacer |
|-------|-----------|
| Namecheap → unclic.consulting → Advanced DNS | **@ / www** → 3.22.236.150 (Vantive); **gitea** → 13.58.58.245; **jenkins** → 18.218.37.76 (si no apunta ya a la instancia actual); **pos** → 13.58.172.235 |
| Gitea (asistente o app.ini) | Server Domain = gitea.unclic.consulting, Base URL = http://gitea.unclic.consulting:3000/ |
| Jenkins (System) | Jenkins URL = http://jenkins.unclic.consulting:8080/ |
| Navegador | http://jenkins.unclic.consulting:8080 · http://gitea.unclic.consulting:3000 · http://pos.unclic.consulting:8111 · https://unclic.consulting (Vantive) |

Cuando las IPs cambien (p. ej. al reiniciar EC2 sin Elastic IP), actualiza los A records en Namecheap con las nuevas IPs, o asigna **Elastic IP** a cada instancia para no tener que cambiarlos.

---

## Siguiente: HTTPS y compartir usuarios

Para dejar todo **seguro (HTTPS)** y **compartir acceso** a clientes/colaboradores con credenciales y permisos (Gitea, Jenkins, pos-online): **[CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md)**.

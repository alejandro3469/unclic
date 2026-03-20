# Patrón «Log In» multi-portal (tipo Sequoia)

En sitios enterprise B2B, **Iniciar sesión** no suele ser un solo URL: el usuario elige **qué es** (cliente plataforma, empleado final, partner, comunidad).

**Referencia Sequoia:** el menú **Log In** despliega varias entradas a **dominios y productos distintos** (SSO, comunidad, red de partners, portal empleado en terceros).

---

## 1. Entradas típicas en Sequoia (ejemplo)

| Etiqueta en UI | Audiencia | Destino (referencia) |
|----------------|-----------|----------------------|
| **Sequoia Login** | Clientes / admins plataforma | [login.sequoia.com](https://login.sequoia.com/) |
| **Sequoia WorkLife Portal** | Empleados / dependientes (beneficios) | Portal tipo *WorkLife* (flujo email / empleado) |
| **Grove Online Community** | Clientes + HR (comunidad) | [community.sequoia.com/private/login](https://community.sequoia.com/private/login) — *Grove Community*, login social “Login with Sequoia”, powered by Gainsight |
| **Global Network Partners** | Partners / red global | [globalnetwork.sequoia.com](https://globalnetwork.sequoia.com/s/login/?ec=302&startURL=%2Fs%2F) |

Además, ecosistema relacionado (no siempre bajo el mismo dropdown):

| Portal | Uso |
|--------|-----|
| **Employee Portal** (ej. PrismHR) | Portal empleado / PEO — ej. [seq-ep.prismhr.com](https://seq-ep.prismhr.com/uex/#/auth/login) (username/password, PrismONE ID) |

**Idea de diseño:** una etiqueta **Log In** + **submenú** claro por **persona**; cada enlace abre **origen distinto** (cookies/SSO no mezclados entre comunidad y backoffice).

---

## 2. Equivalente UnClic (cuando existan entornos)

No hace falta replicar cuatro portales el día uno. La **plantilla** sirve para no mezclar **demo pública**, **Jenkins/Gitea de cliente** y **documentación**.

| Rol | Etiqueta sugerida | Destino típico |
|-----|-------------------|----------------|
| **Cliente / operación** | Acceso Jenkins / CI | URL del Jenkins del cliente o demo protegida (`NEXT_PUBLIC_JENKINS_URL` o similar) |
| **Código / repositorio** | Acceso Gitea | URL Gitea del entorno |
| **Solo lectura / hub** | Hub de demos | `#hub-demos` o URL estática de documentación |
| **Comunidad** (futuro) | Comunidad / foro | Slack, Discourse, o [Grove-like](https://community.sequoia.com/private/login) solo si lo tenéis |
| **Partners** (futuro) | Red de partners | Portal aparte, como [Global Network](https://globalnetwork.sequoia.com/s/login/) |

**Header UnClic hoy:** suele bastar **un** enlace *Acceso* o *Demo* → `#hub-demos`. Cuando haya **dos URLs reales** (ej. Jenkins + Gitea), sustituir por **dropdown** con 2–3 líneas descriptivas (no solo “Login”).

---

## 3. Buenas prácticas (de la referencia)

- **Texto por audiencia** (“Empleado”, “Cliente plataforma”, “Comunidad”) evita que el usuario entre al login equivocado.
- **Dominios separados** para comunidad vs SSO corporativo (menos confusión de sesión).
- Avisos tipo *pop-up blockers* en portales legacy: si UnClic enlaza a Jenkins con ventanas nuevas, documentar en UI.

---

## Enlaces citados (solo referencia Sequoia)

- [Grove Community login](https://community.sequoia.com/private/login)  
- [Sequoia Login](https://login.sequoia.com/)  
- [Global Network Partners login](https://globalnetwork.sequoia.com/s/login/?ec=302&startURL=%2Fs%2F)  
- [Employee Portal ejemplo PrismHR](https://seq-ep.prismhr.com/uex/#/auth/login)

Relacionado: [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md) · footer **Login** en columnas Conectar.

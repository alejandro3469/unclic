# Namecheap: DNS para unclic.consulting (dominio raíz y www)

Para que el sitio se vea en **https://unclic.consulting** y **https://www.unclic.consulting**, en Namecheap deben existir registros **A** para el host **@** y para **www**.

---

## Registros que debes tener (resumen)

| Type | Host | Value | TTL |
|------|------|--------|-----|
| **A** | **@** | **3.22.236.150** | Automatic (o 1 min) |
| **A** | **www** | **3.22.236.150** | Automatic (o 1 min) |

La IP **3.22.236.150** es la de tu EC2 **vantive** (donde está o estará el Nginx que sirve el landing). Si más adelante usas otra IP, cambia el Value de estos dos registros.

---

## Pasos en Namecheap

1. Entra en **Domain List** → **unclic.consulting** → **Manage**.
2. Abre la pestaña **Advanced DNS**.
3. En **HOST RECORDS**, pulsa **ADD NEW RECORD**.

### Registro 1 (dominio raíz)

- **Type:** **A Record**
- **Host:** **@**
- **Value:** **3.22.236.150**
- **TTL:** Automatic (o 1 min)
- Guardar.

### Registro 2 (www)

- **ADD NEW RECORD** otra vez.
- **Type:** **A Record**
- **Host:** **www**
- **Value:** **3.22.236.150**
- **TTL:** Automatic (o 1 min)
- Guardar.

---

## Comprobar

Tras 5–15 minutos (o más si el DNS tarda):

- `ping unclic.consulting` → debe responder **3.22.236.150**
- `ping www.unclic.consulting` → debe responder **3.22.236.150**

Luego, en la EC2, Nginx debe tener un server block para `unclic.consulting` y `www.unclic.consulting` con el document root del landing, y Certbot para HTTPS. Ver **SUBIR-SITIO-A-UNCLIC-CONSULTING.md**.

---

## Estado actual de tus HOST RECORDS (referencia)

| Type | Host | Value |
|------|------|--------|
| A + Dynamic DNS | gitea | 13.58.58.245 |
| A + Dynamic DNS | jenkins | 18.119.157.22 |
| A | vantive | 3.22.236.150 |
| CNAME | eozwmgwcml3s | gv-la7opzav7oocjz.dv.googlehosted.com. |
| TXT | @ | google-site-verification=... |

**Faltan:** A @ → 3.22.236.150 y A www → 3.22.236.150 para que unclic.consulting y www apunten al servidor del sitio.

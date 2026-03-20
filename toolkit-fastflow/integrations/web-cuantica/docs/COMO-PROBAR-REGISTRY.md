# Cómo probar y usar el Registry (FastFlow pos-online)

El **Docker Registry** (registry:2) **no tiene interfaz gráfica**: es una API HTTP. Se "abre" y se usa así.

---

## 1. ¿Tiene interfaz gráfica?

**No.** El registry estándar (`registry:2`) solo expone una **API HTTP**. En el navegador verás **JSON**, no una pantalla con botones. Eso es lo normal.

---

## 2. Cómo “abrir” el registry en el navegador

Con el registry corriendo (en la EC2 de Jenkins o en local), abre estas URLs:

| Qué ver | URL (misma EC2) | URL desde fuera (ej. tu Mac) |
|--------|------------------|------------------------------|
| **Catálogo** (lista de imágenes) | http://localhost:5000/v2/_catalog | http://**18.119.157.22**:5000/v2/_catalog |
| **Tags de pos-online** | http://localhost:5000/v2/pos-online/tags/list | http://**18.119.157.22**:5000/v2/pos-online/tags/list |

- Sustituye **18.119.157.22** por la IP pública de tu EC2 de Jenkins (o usa **jenkins.unclic.consulting** si apunta a esa IP y tienes 5000 abierto).
- El **puerto 5000** debe estar abierto en el Security Group de la EC2 si quieres acceder desde fuera.

**Ejemplo de respuesta** (catálogo tras un push):

```json
{"repositories":["pos-online"]}
```

**Ejemplo** (tags de pos-online):

```json
{"name":"pos-online","tags":["12","11","latest"]}
```

---

## 3. Usar el dashboard como “interfaz”

En este repo hay una **página con enlaces** a Jenkins, Registry y App (no es un UI del registry, solo enlaces):

- **Archivo:** `deploy/dashboard-demo-jenkins-registry.html`
- **Cómo abrirlo:**  
  - En local: abre el archivo con el navegador (`file:///.../deploy/dashboard-demo-jenkins-registry.html`)  
  - O sirve la carpeta `deploy/` con un servidor HTTP y entra a esa ruta.

En esa página hay un enlace **“Registry local — catálogo”** que apunta por defecto a `http://localhost:5000/v2/_catalog`. Si el registry está en la **EC2**, cambia en el HTML `localhost` por la IP de la EC2 (o edita `config/fastflow-config.json` si el dashboard lee esa config).

---

## 4. Probar desde terminal (curl)

En la EC2 donde corre el registry:

```bash
curl -s http://localhost:5000/v2/_catalog
curl -s http://localhost:5000/v2/pos-online/tags/list
```

Desde tu Mac (con el puerto 5000 abierto en el Security Group):

```bash
curl -s http://18.119.157.22:5000/v2/_catalog
curl -s http://18.119.157.22:5000/v2/pos-online/tags/list
```

---

## 5. Resumen: “cómo lo uso”

| Acción | Cómo |
|--------|------|
| **Ver qué imágenes hay** | Abre en el navegador `http://<IP-EC2>:5000/v2/_catalog` (o localhost si estás en la EC2). |
| **Ver tags de pos-online** | Abre `http://<IP-EC2>:5000/v2/pos-online/tags/list`. |
| **Tener “interfaz” de enlaces** | Abre `deploy/dashboard-demo-jenkins-registry.html` y usa el enlace al catálogo (ajusta la URL si el registry está en la EC2). |
| **Probar desde terminal** | `curl -s http://<host>:5000/v2/_catalog` y `curl -s http://<host>:5000/v2/pos-online/tags/list`. |

Para **levantar** el registry en la EC2 y configurar Jenkins: **[REGISTRY-EC2-GRATIS.md](REGISTRY-EC2-GRATIS.md)**.

# Antes de subir los entregables a Gitea

Sigue este checklist para asegurarte de que los **tres directorios** en `delivery/` están listos para el cliente: misma funcionalidad que tu repo, **sin historial de commits** y **sin datos tuyos**; ellos rellenan todo con su información.

---

## 1. Coordinación de funcionalidad

Los entregables deben tener **la misma funcionalidad** que tu repo de trabajo:

| Entregable | Origen | Qué debe coincidir |
|------------|--------|--------------------|
| **delivery/pos-online** | repo-pos-fastflow | Jenkinsfile, Dockerfile, código fuente, deploy/ (manifiestos). El README y algún doc se reemplazan por versiones neutras. |
| **delivery/smartbussiness-generic-model** | smartbussiness-generic-model | Código, Jenkinsfile, pom.xml. README con párrafo inicial neutro (instalación vía fastflow-integration). |
| **delivery/fastflow-integration** | contenido manual | Solo documentación y postman; sin referencias a tu dominio, IPs ni nombres. |

**Acción:** Ejecuta el script de build para copiar de nuevo desde tus repos de trabajo a `delivery/`:

```bash
cd <ruta-web-cuantica>
bash scripts/build-delivery-packages.sh
```

Así `delivery/pos-online` y `delivery/smartbussiness-generic-model` quedan con la misma funcionalidad que los tuyos; el script ya deja README y textos sensibles en versión neutra.

---

## 2. Revisión: nada tuyo en el entregable

Comprueba que en **toda** la carpeta `delivery/` no quede:

- Tu dominio, subdominios o URLs (ej. tu-dominio-real).
- Tus IPs (ej. 1.2.3.4 o las que uses).
- Nombres de usuario, correos o tokens.
- Rutas internas de tu máquina (ej. /Users/tu-usuario/...).
- Referencias a “toolkit”, “manifests” o repos que el cliente no tenga.

**Acción:** Buscar en `delivery/` (incluido fastflow-integration):

```bash
cd delivery
grep -r "unclic\|wallfacer\|alejandro\|18\.119\|13\.58\|3\.15\|gitea\.unclic\|jenkins\.unclic" --include="*.md" --include="*.json" --include="*.sh" . 2>/dev/null | grep -v "ANTES-DE-SUBIR-A-GITEA.md" || echo "OK: sin datos propios en el entregable"
```
(Excluye este mismo archivo, que solo contiene el comando como instrucción.)

El resultado debe estar vacío. Si aparece algo, edita el archivo y sustituye por placeholders (ej. `TU_DOMINIO`, `GITEA_URL`, `<JENKINS_EC2_IP>`).

**Auditoría de datos sensibles (qué hace el script y qué comprobar):**

- **pos-online:** El script **elimina** `application-envDev.properties`, `application-envQas.properties`, `application-envPrd.properties` y `application-envPpr.properties` (contienen IPs internas, credenciales de BD y nombres de clientes internos). En su lugar deja **application-env.example.properties** (solo placeholders) y **README-CONFIGURACION.md** para que el usuario final cree sus propios archivos con sus datos.
- **Nada tuyo ni de un cliente concreto:** No debe quedar tu nombre, tu dominio, tus IPs, ni nombres de clientes específicos (Baxter, Vantive, etc.). Todo el contenido debe **solo invitar al usuario a introducir sus propios datos** (hoja de valores, variables Postman, placeholders en la guía).
- **Comprobación extra (opcional):** Buscar IPs privadas o credenciales que pudieran haberse colado:  
  `grep -rE "10\.|192\.168\.|password=|\.password=" delivery/pos-online/src/main/resources --include="*.properties" 2>/dev/null || true`  
  No debe listar archivos (o solo application-env.example.properties con placeholders tipo TU_PASSWORD).

---

## 3. Documentación solo para su objetivo

En `delivery/` solo debe haber:

- **pos-online:** Código + Jenkinsfile + Dockerfile + README corto que apunta a fastflow-integration. Nada de auditorías internas ni docs de “cómo lo tenemos nosotros”.
- **smartbussiness-generic-model:** Código + Jenkinsfile + README (descripción técnica + una línea de instalación vía fastflow-integration).
- **fastflow-integration:** Manual de usuario, Guía de instalación (con hoja de valores para que **ellos** rellenen), colección Postman, scripts opcionales de push. Sin referencias a tu entorno.

Si en generic-model hay documentos que hablan de migraciones internas o de “nuestro” proceso, valora dejarlos (si son técnicos y genéricos) o excluirlos en el script de build. Por defecto el script no los toca; la búsqueda del paso 2 detecta fugas de datos.

---

## 4. Nombres de los repos en Gitea

Usa **exactamente** estos nombres (el cliente y la Guía de instalación los esperan así):

| Repo en Gitea | Contenido |
|---------------|-----------|
| **smartbussiness-generic-model** | generic model (Maven) |
| **pos-online** | aplicación pos-online + Jenkinsfile + Dockerfile |
| **fastflow-integration** | manual, guía de instalación, Postman |

---

## 5. Subir a Gitea (cuando todo esté listo)

1. Crea en **tu** Gitea **tres repositorios vacíos** (sin “Initialize repository”):  
   `smartbussiness-generic-model`, `pos-online`, `fastflow-integration`.

2. En cada directorio de `delivery/` (sin `.git`), inicializa un repo nuevo y haz push:
   ```bash
   cd delivery/smartbussiness-generic-model
   git init
   git add .
   git commit -m "generic-model: entrega inicial (FastFlow)"
   git branch -M main
   git remote add origin <URL_REPO_GITEA_GENERIC_MODEL>
   git push -u origin main
   ```
   Repite para `delivery/pos-online` y `delivery/fastflow-integration` con la URL de cada repo.

3. **Dar acceso a George (o al usuario final):** En Gitea, en **cada** uno de los tres repos: **Settings** → **Collaborators** → **Add Collaborator**. Añade el usuario de George (o el que use el cliente) y elige el nivel (p. ej. **Read** para solo clonar/ver, **Write** si va a hacer push). Así puede acceder en cuanto esté subida la versión sin historial. Él clona y sigue la **Guía de instalación** rellenando la hoja de valores con **su** Gitea, **su** Jenkins, **su** dominio, etc.

---

## Resumen

| Paso | Qué hacer |
|------|-----------|
| 1 | Ejecutar `build-delivery-packages.sh` para sincronizar funcionalidad y textos neutros. |
| 2 | Buscar en `delivery/` referencias a tu dominio, IPs o nombres; el script ya quita application-env* con datos sensibles y deja plantilla; comprobar que no quede nada tuyo ni de clientes concretos. |
| 3 | Confirmar que solo queda documentación que permita al cliente alcanzar su objetivo. |
| 4 | Nombres de repos: `smartbussiness-generic-model`, `pos-online`, `fastflow-integration`. |
| 5 | Crear los 3 repos vacíos en Gitea, `git init` + push en cada directorio de delivery, luego en cada repo **Settings → Collaborators → Add Collaborator** para dar acceso a George (o al usuario final). |

Así tus repos de trabajo pueden seguir teniendo más información y configuraciones; el entregable tendrá la misma funcionalidad, sin historial y sin nada tuyo, y ellos solo tendrán que rellenar sus datos.

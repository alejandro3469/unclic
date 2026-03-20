# Configuración central — todas las tecnologías en un solo lugar

Todas las configuraciones de **App, Pipeline, Jenkins, Registry, Terraform, Kubernetes y Dashboard** se definen en **un solo archivo**: `config/fastflow-config.json`. Puedes **verlas y administrarlas desde la UI** sin editar JSON a mano.

---

## Administrar desde la UI (recomendado)

1. **Abrir la UI de config:**  
   Abre `deploy/config-ui.html` en el navegador (o sirve la carpeta `deploy/` con `python3 -m http.server 9000` y entra en `http://localhost:9000/config-ui.html`).

2. **Ver y editar:**  
   Cada sección (App, Pipeline, Jenkins, Registry, Terraform, Kubernetes, Dashboard) se muestra en tarjetas con **campos editables**. Cambia los valores que necesites.

3. **Aplicar y guardar:**  
   - Pulsa **«Aplicar cambios (formulario)»** para que los valores editados se apliquen en memoria.  
   - Pulsa **«Descargar config JSON»** para descargar el archivo.  
   - Guarda el archivo descargado como **`config/fastflow-config.json`** en el repo (sustituye el anterior). Así todos los scripts usarán la nueva configuración.

4. **Opciones adicionales:**  
   - **Cargar archivo:** sube un `fastflow-config.json` desde disco para verlo o seguir editando.  
   - **Editar JSON completo:** para editar todo el JSON en bloque y aplicar.

---

## Uso en scripts

Los scripts leen este archivo (requieren `jq` en el PATH):

```bash
source scripts/load-config.sh   # exporta IMAGE_NAME, IMAGE_TAG, REGISTRY, etc.
bash scripts/build-and-push.sh
```

O sin `source`, el script `build-and-push.sh` (y `simulate-jenkins-pipeline.sh`, `test-registry.sh`) cargan el config automáticamente si existe `config/fastflow-config.json` y `jq`. Las variables de entorno tienen prioridad sobre el JSON.

---

## Estructura del JSON

| Sección | Uso |
|---------|-----|
| **app** | Nombre, descripción, puerto, ruta de health y URL base de pos-online. |
| **pipeline** | Nombre de imagen, tag, URL del registry, objetivos Maven y ruta del Dockerfile. |
| **jenkins** | URL, puerto, nombre del job, ID de credenciales del registry y workspace. |
| **registry** | URL, puerto, path del catálogo, nombre de imagen, tag por defecto y días de retención. |
| **terraform** | Namespace, imagen, baseUrl y carpeta de Terraform. |
| **kubernetes** | Namespace, imagen, baseUrl, puerto del servicio, ConfigMap y Deployment. |
| **dashboard** | URLs que enlaza el dashboard (Jenkins, registry, app, repoUrl para el enlace «Abrir repo» en la interfaz del flujo). |

---

## Archivos

| Qué | Dónde |
|-----|--------|
| **Config (fuente de verdad)** | `config/fastflow-config.json` |
| **UI para ver y administrar** | `deploy/config-ui.html` |
| **Script que exporta variables** | `scripts/load-config.sh` |

**Nota:** Si Jenkins y la app pos-online corren en la misma máquina, usa puertos distintos (ej. Jenkins 8080, app 8081) y ajusta `app.baseUrl` y `dashboard.appUrl` en la UI.

# POS Online — Implementación FastFlow

Pipeline, registry y despliegue **a la medida** para **POS Online** (Java, Maven), con dependencia obligatoria de **generic-model** (repo local en la misma carpeta padre). Contexto: **JD Edwards**, **Oracle**, ambientes y bases de datos según la arquitectura y fase del proyecto.

FastFlow aquí **no es un producto separado**: es esta implementación (Jenkins, Maven, Docker, registry, Terraform, K8s) aplicada solo a POS Online.

**Manual único (happy path):** commit → Jenkins → Postman → rollback — **[docs/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](docs/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)**; colección Postman en `docs/postman/`.

**Si te han transferido este repo** y quieres desplegar en AWS gratis: **[docs/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md](docs/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md)** (instalar, ver, usar y probar cada parte; cada quien con su muestra gratis “”). Terraform: **toolkit-fastflow/manifests/terraform/jenkins-aws/REPLICAR-AWS-NUEVO-DUENO.md**.

---

## Requisitos

- **JDK 11+**
- **Maven**
- **Docker** (para imagen y registry)
- Repo **generic-model** en carpeta hermana (mismo padre que pos-online)

---

## Build y tests

```bash
mvn clean install
```

Desde la raíz del repo (donde está `pom.xml`). Asegúrate de tener generic-model disponible según la configuración del proyecto.

---

## Pipeline (Jenkins)

- **Jenkinsfile.example** — Copiar como `Jenkinsfile` y ajustar `REGISTRY` y credenciales.
- Etapas: Test (Maven) → Package → Build image (Docker) → Push to registry (si `REGISTRY` está definido).

Sin Jenkins, mismo flujo en local:

```bash
bash scripts/build-and-push.sh
REGISTRY=mi-registry.com bash scripts/build-and-push.sh   # con push
```

---

## Configuración central y UI de administración

Todas las configuraciones (Jenkins, Registry, Pipeline, Terraform, K8s, App, Dashboard) están en **un solo archivo** y se pueden **ver y administrar desde una sola UI**.

| Qué | Dónde |
|-----|--------|
| **Archivo de configuración** | `config/fastflow-config.json` — URLs, nombres de imagen, namespace, puertos, etc. |
| **Scripts que usan el config** | `build-and-push.sh` lee este archivo (si existe `jq`) para IMAGE_NAME, REGISTRY, etc.; las variables de entorno tienen prioridad. |
| **UI para ver y editar config** | Abrir `deploy/config-ui.html` en el navegador: cargar el JSON, ver todas las secciones, editar y descargar. Si sirves la carpeta (ej. `python3 -m http.server 9000` desde el repo), la UI puede cargar automáticamente `config/fastflow-config.json`. |
| **Cargar variables en el shell** | `source scripts/load-config.sh` (requiere `jq`) para exportar IMAGE_NAME, REGISTRY, etc. |

Ver **config/README.md** para la estructura del JSON.

---

## Validaciones

```bash
bash scripts/run-all-validations.sh
```

Ejecuta (si aplica): Maven test, validación de Jenkinsfile, Terraform, manifiestos K8s y simulación del pipeline. Si no hay `pom.xml` en la raíz (p. ej. solo estás en la carpeta del toolkit), se omiten Maven y simulación.

---

## Documentación

| Documento | Contenido |
|-----------|-----------|
| **docs/copia/COPIA-POS-ONLINE-FASTFLOW.md** | Copia del proyecto en palabras sencillas: POS Online, generic-model, JDE/Oracle, FastFlow como implementación. |
| **docs/README.md** | Índice de documentación. |
| **docs/COMPATIBILIDAD-Y-HISTORIAL.md** | Compatibilidad al copiar; recuperar docs del historial (`git show`). |
| **docs/instalacion/INSTALAR-JENKINS.md** | Instalación de Jenkins. |
| **docs/instalacion/REQUISITOS.md** | Requisitos del entorno. |
| **docs/pos-online/** | Implementación en pos-online: checklist, flujo commit, demo. |
| **docs/pipeline-y-registry/** | Pipeline Jenkins, registry, build de imagen. |
| **deploy/k8s/** | Manifiestos Kubernetes. |
| **deploy/terraform/** | Terraform para despliegue. |
| **config/fastflow-config.json** | Configuración central (Jenkins, Registry, Pipeline, Terraform, K8s, Dashboard). |
| **config/README.md** | Uso del config y override con variables de entorno. |
| **deploy/dashboard-demo-jenkins-registry.html** | UI mínima: Jenkins + registry + app; enlace a **config-ui.html**. |
| **deploy/dashboard-flujo-pos-jenkins.html** | **Interfaz central del flujo:** commit (manual) → ver Jenkins, registry, app. URLs configurables (local o IP EC2). |
| **deploy/config-ui.html** | UI para ver y administrar todas las configuraciones (cargar/editar/descargar config JSON). |
| **AWS gratis: sistema retail automatizado con FastFlow (como en producción)** | **docs/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md** — Cada quien con su sistema retail automatizado con FastFlow, gratis, operando como en producción. Terraform: **manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md**, REPLICAR-AWS-NUEVO-DUENO.md. |

---

## Estructura (resumen)

```
pos-online/                    # este repo
  Jenkinsfile.example
  pom.xml
  config/
    fastflow-config.json       # configuración central (un solo archivo)
    README.md
  scripts/                     # build-and-push, load-config, validate-*, test-registry, simulate-*
  deploy/
    k8s/                       # manifests
    terraform/
    dashboard-demo-jenkins-registry.html
  docs/
    copia/                     # COPIA-POS-ONLINE-FASTFLOW.md
    instalacion/
    pos-online/
    pipeline-y-registry/
    tests-y-validacion/

../generic-model/              # dependencia obligatoria (misma carpeta padre)
```

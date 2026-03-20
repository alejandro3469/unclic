# 🌊 FastFlow Toolkit (Plug-and-Play)

**FastFlow** es una plataforma integral de aceleración de entrega continua (CI/CD) diseñada para transformar el caos operativo en un flujo de valor constante. Nuestro toolkit permite a las organizaciones estandarizar su infraestructura y automatizar su ciclo de vida de software con el menor esfuerzo posible.

---

## 🗺️ Mapa de Navegación por Perfil

Para una experiencia personalizada, selecciona tu ruta de usuario según tu rol en la organización:

### 👑 [Portal Ejecutivo](docs/HOME-EJECUTIVO.md)
*Visión estratégica, ROI, KPIs de negocio (DORA) y gobernanza de alto nivel.*

### 📐 [Portal de Arquitectura](docs/HOME-ARQUITECTO.md)
*Diseño de sistemas, escalabilidad, seguridad (RBAC), calidad (SonarQube) e Infraestructura como Código (IaC).*

### 🛠️ [Portal del Desarrollador](docs/HOME-DESARROLLADOR.md)
*Implementación técnica, How-tos paso a paso, configuración de IDE (VS Code) y Shared Libraries.*

---

## 🚀 ¿Cómo empezar con FastFlow?

Nuestro modelo de adopción se basa en el **mínimo esfuerzo y máximo impacto**:

1.  **Quickstart (1-Click)**: Ejecuta `bash quickstart.sh` para levantar un entorno local en segundos.
2.  **Onboarding**: El cliente completa el [Cuestionario de Implementación](QUESTIONARIO-IMPLEMENTACION.md).
3.  **Diagnóstico**: Se valida el entorno con el script `installers/check-environment.sh`.
4.  **Selección de Escenario**: Consulta la [Guía Maestra de Instalación](docs/GUIA-INSTALACION-ESCENARIOS.md) para Docker, K8s o Cloud.
5.  **Despliegue de Tubería**: Se integran los [Templates Universales](templates/common/Jenkinsfile) en el repositorio del cliente.

## 🧩 Build de catálogo Docs (Web Cuantica)

Para regenerar el catálogo de `docs.html` desde los markdown en `docs/`:

```bash
node scripts/build-docs-catalog.js
```

Este comando actualiza automáticamente:
- `integrations/web-cuantica/js/docs-catalog-data.js`

---

## 💡 Filosofía FastFlow: El Arte de Fluir

El objetivo de FastFlow es **eliminar la fricción innecesaria**.

### 🍳 La Analogía de la Cocina Profesional
En una cocina de alta gama, no se improvisa la higiene ni las medidas.
-   **Receta** = Imágenes Inmutables (Docker/Packer).
-   **Etiquetas** = Versionado en el Registry.
-   **Plan de Emergencia** = Estrategias de Rollback Automatizado.

### 🏠 La Analogía de la Rutina Familiar
Una mañana sin estructura es una mañana perdida. FastFlow proporciona la rutina necesaria (Levantarse, Desayuno, Mochila) para que tu equipo llegue a su destino (Producción) con toda su energía intacta.

---

## 📂 Estructura del Toolkit

-   `CATALOGO-PRODUCTO.md`: Definición completa de niveles de servicio y jerarquías.
-   `installers/`: Scripts de automatización para preparar servidores y proyectos.
-   `templates/`: El "Cerebro" de FastFlow (Jenkinsfiles, Shared Libraries y Stacks).
-   `manifests/`: Planos de infraestructura (Kubernetes, Terraform, Helm).
-   `marketing/`: Activos para comunicar el valor de FastFlow a toda la organización.
-   `docs/`: El cuerpo de conocimiento completo (Capas 0-26).

---

*FastFlow: No luches contra la tubería. Deja que el software fluya.*

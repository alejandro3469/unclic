# Cloudcraft — Por qué y cómo replicar

## Por qué lo usamos

- **Diagramas de arquitectura conectados a AWS:** Live scanning dibuja la infra real (VPC, EC2, etc.). Documentación fiel sin mantener diagramas a mano.
- **Opcional:** No es obligatorio para el pipeline; sirve para documentar y mostrar la arquitectura en la UI de UnClic. SaaS (Datadog); no es open source pero es complemento de visualización.

## Cómo replicar

1. Cuenta Cloudcraft Pro; en Cloudcraft: Add AWS Account (rol IAM con ReadOnlyAccess, External ID). 2) Live → Scan now → Auto layout → guardar blueprint. 3) Share & Export → Get shareable link; configurar en UnClic `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL`. Ver [toolkit-fastflow/docs/GUIA-CLOUDCRAFT-COMPLETA.md](../../../docs/GUIA-CLOUDCRAFT-COMPLETA.md).

---

## Sitios oficiales (qué ofrecen)

### [cloudcraft.co](https://cloudcraft.co) (marketing)

- **Cabecera:** LOG IN, SIGN UP; navegación **Home**, **Solutions**, **Pricing**, **Blog**.
- **Mensaje principal:** diagramas de arquitectura **AWS y Azure** en segundos; CTAs **TRY CLOUDCRAFT FREE** y **REQUEST A DEMO**; dato social (~425k+ profesionales).
- **Diseñador visual:** cientos de componentes AWS/Azure, iconos personalizados, vista **2D** (análisis) y **3D** (presentación).
- **FinOps:** refactorizar arquitectura y ver cambios de coste (solo lectura); encaje con **Datadog Cloud Cost Management**.
- **Documentación y colaboración:** diagramas vivos, plugin **Confluence**, **embed**; onboarding y auditorías.
- **Cloudcraft Live:** escaneo automático multi-región, ahorro de tiempo semanal frente a mantener diagramas a mano; filtrado para troubleshooting.
- **Pie:** Copyright Datadog, Inc.; enlaces a **Documentation**, **API Docs**, **Cloudcraft Status**, **Security**, **Legal** (Terms, Privacy, etc.).

### Producto Cloudcraft en Datadog ([página de producto](https://www.datadoghq.com/product/cloudcraft/))

- En el sitio Datadog aparece en **Product → Infrastructure** (junto a Storage Management, Cloud Cost Management, etc.): diagramas profesionales de arquitectura en tiempo real.
- Bloques habituales: **instant visibility** (diagramas automáticos, multi-cuenta), **govern infrastructure** (cobertura del agente, tags, recursos huérfanos, dependencias), **deep visibility** (incidentes, metadatos, integración con Cloud Security Management).
- **Works great with:** Infrastructure Monitoring, Cloud Cost Management.
- Recursos: documentación, ebooks (p. ej. diagramar arquitectura siguiendo buenas prácticas), prueba **14 días** del ecosistema Datadog (según la página de producto).

**Documentación en Docs Datadog (dos productos distintos):**

| Doc | Qué es |
|-----|--------|
| **[Cloudcraft in Datadog](https://docs.datadoghq.com/datadog_cloudcraft/)** | Producto **dentro de la app Datadog**: diagrama en vivo solo lectura a partir de la integración AWS/Azure/GCP. Menú Docs: **Infrastructure → Cloudcraft** (y no confundir con standalone). |
| **[Cloudcraft (Standalone)](https://docs.datadoghq.com/cloudcraft/)** | **app.cloudcraft.co**: blueprints, Live scanning con cuenta AWS conectada a Cloudcraft, API de blueprints, embed — lo que usa el plan FastFlow/UnClic con enlaces compartibles. |

---

## Cloudcraft in Datadog

Resumen de la documentación oficial: [Cloudcraft in Datadog — Overview](https://docs.datadoghq.com/datadog_cloudcraft/).

- **Qué hace:** Visualización **en vivo, solo lectura** de la arquitectura para explorar y analizar la infra integrada en Datadog. Cubre **AWS, Azure y GCP**. No sustituye la documentación del **Cloudcraft standalone** (blueprints en cloudcraft.co).
- **Casos de uso que cita la doc:** trazar incidentes por dependencias de infra; detectar si el origen es infra (p. ej. tráfico cross-región → latencia o coste); analizar misconfiguraciones de seguridad; onboarding; reducir MTTR y gobierno proactivo.
- **Prerrequisitos:** permiso **`cloudcraft_read`**; **recopilación de recursos** activada en la cuenta cloud (AWS/Azure/GCP). En AWS recomiendan política gestionada **SecurityAudit** o **ReadOnlyAccess**.
- **Overlays de seguridad:** ver misconfiguraciones e identidad requiere **Cloud Security**; datos sensibles, **Sensitive Data Scanner** y permiso **`data_scanner_read`** para activar esa capa. Con permisos restrictivos, Cloudcraft **omite** recursos inaccesibles y puede mostrar **aviso en la UI**.
- **Coste AWS:** la recopilación puede impactar **CloudWatch**; la doc sugiere desactivar **Usage metrics** en la pestaña **Metric Collection** del tile de integración AWS si se quieren evitar cargos.
- **Primer uso:** **Infrastructure → Resources → Cloudcraft**. Si hay **más de 10.000 recursos**, filtrar por cuenta, región o tags.
- **Desplegable de cuenta:** nombre desde tags de cuenta en el tile AWS; en Azure, nombre de suscripción; en GCP, IDs de proyecto desde el tile de Google Cloud.
- **Group By:** agrupa por tipo (p. ej. VPC, región); **Show All Controls**; capa **Network ACL** vía menú **+ Tags**. Agrupar por tags de **AWS/Azure** o labels **GCP**; **no** agrupa por tags solo del Agent (p. ej. `env` local).
- **Vistas guardadas:** filtros → **+ Save as new view**; recuperar desde el menú superior.
- **Exploración:** zoom y hover; **clic** abre panel lateral (observabilidad, coste, seguridad, enlaces a otros productos Datadog).
- **Proyección:** alternar **3D (por defecto)** y **2D** (vista cenital).
- **Filtros y búsqueda:** menú **+ Filter**, **More Filters** (tags custom, tags Terraform, etc.). **Búsqueda** por nombre, ID o tag: resalta coincidencias y **atenúa** el resto sin regenerar el diagrama entero.
- **Permisos RBAC:** `cloudcraft_read` viene en el rol **Read Only** por defecto; en roles custom, añadirlo manualmente.
- **Siguientes capas (overlays):** **Infrastructure**, **Observability** (Agent y features), **Security** (IAM, firewall, security groups), **Cloud Cost Management**.
- **Lectura adicional en la doc:** enlaces al standalone, blog (visualizaciones AWS, riesgos de seguridad).

**Navegación típica en Docs Datadog:** en el índice lateral, **ESSENTIALS → Cloudcraft (Standalone)** vs **INFRASTRUCTURE → Cloudcraft** (in-app). La cabecera del sitio Docs puede incluir avisos puntuales (eventos, promos).

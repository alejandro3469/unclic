# Cloudcraft: documentación precisa y arquitectura en tiempo real en la UI

[Cloudcraft](https://cloudcraft.co) (parte de Datadog) permite crear diagramas de arquitectura AWS (y Azure) que se mantienen **precisos** y **conectados a la infra real**, y mostrarlos en la interfaz de UnClic para **monitorización en tiempo real** en la UI.

**Importante:** En **Docs Datadog** existen **dos guías**: **[Cloudcraft (Standalone)](https://docs.datadoghq.com/cloudcraft/)** (app.cloudcraft.co, blueprints, API — alineado con embed/UnClic) y **[Cloudcraft in Datadog](https://docs.datadoghq.com/datadog_cloudcraft/)** (diagrama dentro de **Infrastructure → Resources → Cloudcraft**, integración Datadog, GCP además de AWS/Azure, overlays de coste/seguridad). Resumen de la segunda: [CLOUDCRAFT.md — Cloudcraft in Datadog](../integrations/web-cuantica/docs/tecnologias/CLOUDCRAFT.md#cloudcraft-in-datadog).

**Documentación Cloudcraft — Índice:** [GUIA-CLOUDCRAFT-COMPLETA.md](GUIA-CLOUDCRAFT-COMPLETA.md) — Guía maestra que enlaza plan paso a paso, snapshot del blueprint, UnClic (variables y sección) y fuentes oficiales.

**Plan detallado (rehecho):** [PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md](PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md) — Tareas muy granulares (sub-tareas 0.1.1, 1.2.1, etc.), citas literales de la documentación oficial, ejemplos (ARN, .env, curl), “Si falla” en pasos críticos y fuentes numeradas (Datadog/Cloudcraft) para que funcione a la primera.

**Snapshot del blueprint FastFlow:** [cloudcraft-fastflow-blueprint-snapshot.json](cloudcraft-fastflow-blueprint-snapshot.json) — Export JSON del diagrama Live (regiones us-east-2, us-east-1, us-west-1, us-west-2: VPCs, EC2 Jenkins/Gitea/POS/Vantive, ASG, EBS, IGW, EventBridge). Útil como respaldo o para comparar cambios de arquitectura.

---

## Por qué Cloudcraft para documentación y UI

- **Documentación siempre al día:** Los diagramas no son dibujos estáticos; con **Live scanning** Cloudcraft genera y actualiza la vista a partir de tu cuenta AWS real.
- **Una sola fuente de verdad:** La misma arquitectura (VPC, EC2 Jenkins/Gitea/POS, subnets, security groups) que despliega Terraform o Pulumi se refleja en el diagrama.
- **Visible en la UI de UnClic:** Puedes embeber o enlazar el diagrama en el sitio UnClic para que clientes o equipos internos vean la arquitectura **en tiempo real** sin entrar a la consola AWS.
- **Colaboración y auditorías:** Diagramas compartidos, filtros por tags, y documentación lista para presentaciones o auditorías de seguridad.

Referencia: [Cloudcraft](https://cloudcraft.co) · [Documentación Cloudcraft (Datadog)](https://docs.datadoghq.com/cloudcraft/) · [API Cloudcraft](https://developers.cloudcraft.co/).

### Resumen de las páginas públicas (para contexto de producto)

| Origen | Contenido útil |
|--------|-----------------|
| **cloudcraft.co** | Home / Solutions / Pricing / Blog; mensaje AWS+Azure; diseñador 2D/3D; Live scanning; FinOps + Cloud Cost Management; Confluence y embed; pie con Docs, API, Status, Legal. |
| **Datadog → Cloudcraft** | Producto bajo **Infrastructure**: visibilidad instantánea, gobierno de infra, troubleshooting profundo; *Works great with* Infra Monitoring y Cloud Cost Management; ebooks y trial 14 días del suite Datadog. |

Detalle redactado: [docs/tecnologias/CLOUDCRAFT.md](../integrations/web-cuantica/docs/tecnologias/CLOUDCRAFT.md) (sección *Sitios oficiales*).

---

## Conectar la arquitectura real (Live scanning)

1. **Cuenta Cloudcraft** (Pro/Enterprise para Live scanning y API).
2. **Vincular cuenta AWS:** En Cloudcraft, conectar la cuenta AWS donde está desplegada la infra FastFlow (Jenkins, Gitea, POS en EC2). Cloudcraft usa permisos de solo lectura para descubrir recursos.
3. **Live scanning:** Activar el escaneo automático para la región (p. ej. `us-east-2`) donde están las instancias. Los diagramas se generan y actualizan a partir de la infra real (VPC, EC2, security groups, etc.).
4. **Filtros por tags:** Si tu Terraform/Pulumi etiqueta recursos (p. ej. `Name=fastflow-jenkins-controller`), puedes filtrar en Cloudcraft para mostrar solo la arquitectura FastFlow.

Así la **documentación del sitio** y el **diagrama en la UI de UnClic** reflejan la arquitectura real, no un esquema desactualizado.

---

## Mostrar el diagrama en la UI de UnClic

### Opción A: Enlace a Cloudcraft

En la sección **Arquitectura en tiempo real** del sitio UnClic hay un CTA que abre el diagrama en Cloudcraft (en nueva pestaña). El usuario debe tener acceso a Cloudcraft o usar un enlace compartido (read-only) si lo configuras.

### Opción B: Embed en la página (recomendado para monitoreo en la UI)

1. En Cloudcraft, abre el diagrama generado por Live scanning y usa la opción de **compartir** o **embed** (si tu plan lo incluye). Obtén la URL de vista/embed (p. ej. `https://app.cloudcraft.co/view/...` o la URL que proporcione la función de embedding).
2. En el proyecto UnClic, define la variable de entorno **pública** (para el cliente Next.js). Crea o edita `.env.local` en la raíz del proyecto UnClic:
   - `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL` — URL del diagrama para abrir en nueva pestaña.
   - `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL` — URL para embeber en iframe (si Cloudcraft proporciona una URL embeddable).
   Ejemplo: `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL=https://app.cloudcraft.co/view/...`
3. La sección **Arquitectura en tiempo real** (`#architecture-live`) mostrará:
   - Si `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL` está definida: un iframe con el diagrama (monitoreo en tiempo real en la UI).
   - Si solo `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL` está definida: un botón/enlace para abrir el diagrama en Cloudcraft.
   - Si ninguna está definida: un texto explicativo y un enlace genérico a Cloudcraft para que el equipo configure la URL cuando tenga el diagrama listo.

Así **conectas la arquitectura real** (Live scanning) **a la interfaz** para verla monitoreada en tiempo real en la UI de UnClic.

### Opción C: API para snapshot (avanzado)

Con la [API de Cloudcraft](https://developers.cloudcraft.co/) puedes obtener una **imagen (PNG)** o JSON del diagrama y mostrarla en UnClic (p. ej. vía un job que actualice la imagen periódicamente y la sirva desde tu CDN o desde una ruta API). Requiere API key y ejecución en backend; la clave no debe exponerse en el cliente. Útil si no usas embed directo y quieres una imagen siempre actualizada en la landing.

---

## Resumen

| Objetivo | Acción |
|----------|--------|
| Documentación precisa | Conectar AWS a Cloudcraft y activar Live scanning; diagramas alineados a la infra real. |
| Arquitectura en la UI de UnClic | Configurar `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL` y/o `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL` en el build de UnClic. |
| Monitoreo en tiempo real en la UI | Usar embed URL en la sección «Arquitectura en tiempo real» para mostrar el diagrama dentro del sitio. |

La infra que despliegas con Terraform o Pulumi (Jenkins, Gitea, POS, VPC) queda documentada en Cloudcraft y, opcionalmente, visible y monitoreada en tiempo real en la interfaz de UnClic.

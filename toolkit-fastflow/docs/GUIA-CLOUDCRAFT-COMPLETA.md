# Guía completa Cloudcraft — FastFlow y UnClic

Documentación **única** que enlaza todo lo relacionado con Cloudcraft en el toolkit FastFlow: integración AWS, diagramas Live, blueprint guardado, enlace a UnClic y referencias oficiales.

---

## Índice de documentación Cloudcraft

| Documento | Uso |
|-----------|-----|
| **[CLOUDCRAFT-DOCUMENTACION-Y-UI.md](CLOUDCRAFT-DOCUMENTACION-Y-UI.md)** | Por qué Cloudcraft, Live scanning, opciones de vista/embed en UnClic, variables de entorno, API (PNG/snapshot). |
| **[PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md](PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md)** | Plan paso a paso (Fases 0–5) con sub-tareas numeradas, texto literal de la consola IAM, criterios de éxito y “Si falla”. Fuentes oficiales Datadog/Cloudcraft. |
| **[cloudcraft-fastflow-blueprint-snapshot.json](cloudcraft-fastflow-blueprint-snapshot.json)** | Snapshot en JSON del diagrama Live (regiones us-east-2, us-east-1, us-west-1, us-west-2: VPCs, EC2, ASG, EBS, IGW, EventBridge). Respaldo y comparación de cambios. |
| **Cloudcraft in Datadog (Docs)** | Producto distinto: diagrama en la app Datadog (`Infrastructure → Resources → Cloudcraft`). Ver [datadog_cloudcraft](https://docs.datadoghq.com/datadog_cloudcraft/) y [CLOUDCRAFT.md — Cloudcraft in Datadog](../integrations/web-cuantica/docs/tecnologias/CLOUDCRAFT.md#cloudcraft-in-datadog). |

**En web-cuantica:** [docs/README.md](../integrations/web-cuantica/docs/README.md) — Índice general; en la sección “Arquitectura en tiempo real (Cloudcraft)” enlaza a esta guía y al plan granular.

---

## Standalone vs Cloudcraft in Datadog

| | **Cloudcraft standalone** ([docs/cloudcraft](https://docs.datadoghq.com/cloudcraft/)) | **Cloudcraft in Datadog** ([docs/datadog_cloudcraft](https://docs.datadoghq.com/datadog_cloudcraft/)) |
|--|--|--|
| **Dónde** | app.cloudcraft.co, blueprints, API | Dentro de Datadog: **Infrastructure → Resources → Cloudcraft** |
| **Origen del diagrama** | Cuenta AWS enlazada al producto Cloudcraft (rol Cloudcraft) | Integración **Datadog** AWS/Azure/GCP + recopilación de recursos |
| **FastFlow / UnClic** | Es el flujo documentado aquí (share link, embed, snapshot JSON export) | Complemento si ya usas Datadog para ver la misma infra con overlays (coste, seguridad, Agent) |

---

## Resumen de lo que está hecho

- **Integración AWS → Cloudcraft:** Cuenta AWS conectada vía rol IAM (Trusted entity: Another AWS account, External ID, ReadOnlyAccess). Flujo “Install your first Cloudcraft Integration” en 6 pasos documentado en el plan granular.
- **Diagrama Live:** Blueprint con infra real en us-east-2 (y otras regiones si se escanean): fastflow-vpc, EC2 (Jenkins, Gitea, POS, Vantive, pos-demo), ASG, EBS, IGW, EventBridge.
- **Snapshot en repo:** `cloudcraft-fastflow-blueprint-snapshot.json` guarda la estructura del diagrama para respaldo y diff.
- **UnClic:** Sección “Arquitectura en tiempo real” (`#architecture-live`) que usa `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL`, opcionalmente `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL`, y **`NEXT_PUBLIC_CLOUDCRAFT_STATIC_IMAGE`** (captura PNG/WebP en el repo para visitantes sin login). Blueprint de la demo: https://app.cloudcraft.co/blueprint/504a8a6e-fca3-404d-a280-63ab8fe9d0c1 . Si el blueprint es privado: ver **[VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md](../integrations/web-cuantica/unclic/docs/VISUALES-CLOUDCRAFT-Y-DEMOS-REPO.md)**.

---

## Flujo rápido (referencia)

1. **Requisitos:** Cuenta AWS con infra FastFlow (o prevista), permisos IAM para crear rol, Cloudcraft Pro, usuario Owner o Administrator en Cloudcraft.
2. **Conectar AWS:** Cloudcraft → Add AWS Account → abrir enlace IAM → Create role (Trusted entity: AWS account, Another AWS account, Account ID y External ID de Cloudcraft, ReadOnlyAccess, nombre ej. `cloudcraft`) → copiar Role ARN → pegarlo en Cloudcraft + nickname + primera región → Save.
3. **Diagrama Live:** Pestaña Live → cuenta y región (ej. us-east-2) → Scan now → Auto layout → guardar blueprint.
4. **URL para UnClic:** Share & Export → Get shareable link → copiar URL (vista y/o embed).
5. **UnClic:** En `unclic/.env.local`: `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL=<URL>` y opcional `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL=<URL>`. Reiniciar `npm run dev` o volver a build. En producción, definir las mismas variables en el entorno de build.

Detalle paso a paso y pantallas: **[PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md](PLAN-CLOUDCRAFT-UNCLIC-TAREAS-GRANULARES.md)**.

---

## UnClic: variables de entorno y sección

- **Ruta del proyecto:** `toolkit-fastflow/integrations/web-cuantica/unclic/`
- **Archivo de env (local):** `.env.local` en la raíz de `unclic/` (no commitear; debe estar en `.gitignore`).
- **Variables:**
  - `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL` — URL para abrir el diagrama en Cloudcraft (nueva pestaña).
  - `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL` — (opcional) URL para embeber en iframe.
- **Comportamiento:** Definidas en build time (Next.js). Si se cambian, reiniciar dev o volver a hacer build. En producción configurar en el entorno de build (Vercel, Docker, CI, etc.).
- **Componente:** `components/sections/architecture-live-section.tsx`; textos en `lib/copy.ts` (bloque `architectureLive`).
- **Sección en la página:** `app/page.tsx` incluye `ArchitectureLiveSection`; ancla `#architecture-live`.

---

## Fuentes oficiales

- [Cloudcraft (web)](https://cloudcraft.co) — marketing: Solutions, Pricing, Live scanning, 2D/3D, FinOps, Confluence/embed.
- [Cloudcraft en Datadog (producto)](https://www.datadoghq.com/product/cloudcraft/) — visibilidad, gobierno de infra, integración con Infra Monitoring y Cloud Cost Management (menú Product → Infrastructure).
- [Cloudcraft in Datadog (documentación)](https://docs.datadoghq.com/datadog_cloudcraft/) — in-app, permiso `cloudcraft_read`, Group By, vistas guardadas, overlays.
- [Cloudcraft standalone (documentación)](https://docs.datadoghq.com/cloudcraft/) — blueprints, conectar AWS a Cloudcraft, API, FAQ.
- [Connect your AWS Account to Cloudcraft](https://docs.datadoghq.com/cloudcraft/getting-started/connect-aws-account-with-cloudcraft/)
- [Create your first live cloud diagram](https://docs.datadoghq.com/cloudcraft/getting-started/create-your-first-cloudcraft-diagram/)
- [Shareable link security](https://docs.datadoghq.com/cloudcraft/faq/shareable-link-security/)
- [Cloudcraft API (Blueprints, export PNG)](https://docs.datadoghq.com/cloudcraft/api/blueprints/)

---

## Checklist de documentación

- [x] Guía maestra (este documento)
- [x] Plan granular con pasos IAM y UI Cloudcraft
- [x] Snapshot del blueprint en JSON
- [x] Doc “Cloudcraft: documentación y UI” con opciones vista/embed/API
- [x] UnClic: sección arquitectura + variables documentadas en plan Fase 4 y en CLOUDCRAFT-DOCUMENTACION-Y-UI
- [x] Índice en web-cuantica/docs/README.md

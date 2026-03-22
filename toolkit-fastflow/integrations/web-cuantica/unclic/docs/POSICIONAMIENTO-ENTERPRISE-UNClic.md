# Posicionamiento enterprise — UnClic / FastFlow

Documento interno para **mensaje común** en web, propuestas y redes: qué prometemos, qué está en demo y qué es roadmap.

---

## Para quién sacan más provecho

| Perfil | Por qué |
|--------|---------|
| **Retail / cadena / muchas tiendas** | Mismo pipeline para varios entornos; rollback y versiones alineadas a operación. |
| **Alto volumen de ventas** (por minuto u hora) | Menos riesgo en cada deploy: artefactos versionados, POS desplegable de forma controlada. |
| **Due diligence de TI** | Un solo URL (**hub de demos**) con Jenkins, Gitea, POS, registry, diagrama Cloudcraft. |
| **Equipos que ya usan Git** | Jenkinsfile en repo; Gitea como Git interno; encaje con cultura de revisiones. |

---

## Qué está demostrado hoy (sitio UnClic)

- **CI/CD:** Jenkins, Gitea, **Docker** (build/push/pull), **registry OCI** (mismo estándar que [imagen oficial registry](https://hub.docker.com/_/registry)), app POS en demo. Detalle: [DOCKER-Y-REGISTRY-UNClic.md](DOCKER-Y-REGISTRY-UNClic.md).
- **Infra documentada:** Cloudcraft (blueprint / vista); sección de arquitectura en vivo en la landing.
- **Contenido:** audio/vídeo con patrón tipo agente (UI OSS) y medios reproducibles; flujos explicados.
- **Correo:** posición *Google Workspace / Gmail corporativo* como parte típica del ecosistema cliente (enlace configurable).

---

## Roadmap explícito (honesto en ventas)

| Ítem | Estado |
|------|--------|
| **Pasarela de pagos** | En roadmap; integración al mismo ecosistema que POS/pipeline. |
| **Sello digital / CFDI (SAT México)** | Planeado (p. ej. entorno Windows dedicado para certificado); unificación documentada como objetivo enterprise. |

No prometer fechas en la web sin cerrar alcance; usar “contactar para hoja de ruta”.

---

## Mensaje único (elevator pitch)

*UnClic concentra en un solo sitio las demos de un stack serio: pipeline as code, POS y arquitectura cloud documentada. Pensado para negocios que no pueden permitirse un deploy opaco — retail, cadena o alto throughput. El siguiente paso es unificar pagos y cumplimiento fiscal en el mismo sistema.*

---

## Coherencia con documentación técnica

- Toolkit FastFlow: `toolkit-fastflow/docs/` (Jenkins, registry, AWS, Cloudcraft standalone vs Datadog).
- UnClic: `unclic/docs/` (SEO, copia, este archivo, CV/redes).

Actualizar **NEXT_PUBLIC_*** en `.env.example` cuando añadas nuevas demos al hub (`lib/hub-links.ts`).

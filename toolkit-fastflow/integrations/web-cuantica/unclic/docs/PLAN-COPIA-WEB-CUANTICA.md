# Plan de copia — Landing UnClic

Objetivo: redactar la copia que se integrará en los componentes del sitio (hero, valor, productos/servicios, pricing, cliente ideal, expectativas). **Sin nombres de clientes concretos.** Enfoque tipo Datadog: por suscripción o pago por sprint; solo lo que ya ofrecemos y tenemos capacidad de vender.

---

## 1. Fuentes recopiladas (repos padre FastFlow y web-cuantica)

| Documento | Qué aporta |
|-----------|------------|
| **toolkit-fastflow/docs/HOME-EJECUTIVO.md** | Visión FastFlow: ROI, DORA, gobernanza, seguridad. |
| **toolkit-fastflow/docs/GUIA-PRIMITIVAS-FASTFLOW.md** | 6 primitivas (pipelines, auditoría, extensibilidad, análisis de entrega, estrategia arquitectura, automatización). |
| **toolkit-fastflow/docs/GLOSARIO-FASTFLOW.md** | Términos: Pipeline as Code, Jenkins, Registry, DORA, Shared Library, etc. |
| **toolkit-fastflow/docs/REFERENCIAS-PARA-LANDING-FOOTER.md** | Enlaces técnicos (Jenkins, Docker, K8s, Terraform, Helm). |
| **web-cuantica/docs/listos-para-word/METODOLOGIA-STACK-VALOR-APORTADO.md** | Stack (Git, Gitea, Jenkins, Maven, Docker, Registry, Terraform, K8s), variables de proyecto, valor aportado. |
| **web-cuantica/docs/propuestas/PROPUESTA-VANTIVE-ALCANCE-TIEMPOS-COSTOS-CLAUSULAS.md** | Alcance, tiempos (días laborables), opciones de precio, cláusulas (revisiones, límites). |
| **web-cuantica/docs/listos-para-word/PROPUESTA-VANTIVE-LISTO-WORD.md** | Cómo se describe despliegue, stack, garantía; sin mencionar cliente. |
| **web-cuantica/README.md** | POS + FastFlow, Jenkins, registry, config central, demos. |
| **web-cuantica/docs/PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md** | Demo: Jenkins, Gitea, app POS, URLs, flujo commit → pipeline. |

---

## 2. Bloques de copia a producir

| Bloque | Uso | Contenido clave |
|--------|-----|------------------|
| **Hero / headline** | Arriba del sitio | Una línea de valor: automatización que reduce lead time, pipeline como código, entrega continua. |
| **Value proposition** | Debajo del hero | 2–3 frases: qué es lo que vendemos (automatización Jenkins + registry, FastFlow aplicado a tu app), para quién (equipos que quieren CI/CD sin montar todo desde cero). |
| **Productos y servicios** | Sección “Qué ofrecemos” | Lista **solo** de lo que ya tenemos capacidad de vender: pipeline as code (Jenkinsfile), Jenkins + Gitea, registry de imágenes, despliegue (EC2, opcional K8s/Terraform), demos (Jenkins, registry, FastFlow aplicado al POS), sitios/landings estáticos (Next.js, deploy repetible). Sin prometer lo que no damos (ej. no ofrecemos “todo lo que vende Datadog”). |
| **Pricing** | Sección precios | Opciones: **suscripción** (mensual/trimestral por pipeline o por entorno) y/o **pago por sprint** (alcance acotado, N sprints, entregas con feedback). Tono: claro, sin cifras inventadas; “Cotización según alcance” o paquetes tipo Starter/Team si se definen. |
| **Cliente ideal / a quién nos dirigimos** | Sección “Para quién” | Criterios para **evitar** lo que pasó con proyectos donde el alcance se movió sin control: clientes que **definen alcance por escrito**, que **respetan sprints y plazos**, que dan **feedback en tiempo acordado** (entrega continua), que entienden que **revisiones adicionales y alcance nuevo se cotizan aparte**. Lenguaje positivo: “Trabajamos mejor con equipos que…” (alcance claro, 1–2 rondas de revisión incluidas, disponibilidad de material en plazo). |
| **Expectativas: tiempo y sprints** | Texto corto en “Cómo trabajamos” | Plazos en **días laborables o sprints** (ej. 1 sprint = 2 semanas); **feedback en cada entrega** (no acumular al final); **revisiones incluidas** (ej. 2 rondas); lo que exceda, por anexo. Así el cliente sabe qué esperar y nosotros protegemos el alcance. |
| **Servicios (lista tipo Datadog)** | Sección “Servicios” o “Soluciones” | Lista escueta, solo lo que ofrecemos: **Pipeline as Code** (Jenkinsfile, Jenkins, Gitea), **Registry** (imágenes versionadas, rollback), **Despliegue** (EC2, Nginx; opcional K8s/Terraform), **Demos** (Jenkins, registry, FastFlow aplicado al POS — probar desde la UI), **Sitios y landings** (Next.js estático, deploy repetible), **Consultoría** (auditoría de infraestructura, diseño de pipeline). No listar observabilidad completa ni productos que no tengamos. |
| **Footer / referencias** | Pie de página | Enlaces a documentación técnica (Jenkins, Docker, K8s, Terraform, Helm) como en REFERENCIAS-PARA-LANDING-FOOTER. |
| **CTA** | Botones y cierre | “Probar la demo”, “Solicitar cotización”, “Ver planes” — sin prometer soporte 24/7 ni cosas fuera de capacidad. |

---

## 3. Tono y restricciones

- **Tono:** Profesional, claro, tipo Datadog (valor medible, entrega continua, observabilidad del flujo) pero **solo sobre lo que realmente ofrecemos**.  
- **No mencionar** clientes por nombre (ni Vantive ni otros). Hablar de “productos y servicios”, “demos”, “proyectos”.  
- **Pricing:** Suscripción o pago por sprint; “cotización según alcance” donde no haya paquete fijo.  
- **Cliente ideal:** Dejar explícito que buscamos equipos con alcance definido, feedback en tiempo y respeto a sprints/plazos, para evitar desalineación de expectativas.  
- **Legal/transparencia:** Alcance por escrito, revisiones y extensiones según cláusulas (referencia genérica en copy; el detalle en propuesta/contrato).

---

## 4. Archivos de copia generados

- **docs/copia/COPY-PRODUCTOS-SERVICIOS-PRICING.md** — Textos listos para integrar: hero, valor, productos/servicios, pricing, cliente ideal, expectativas (tiempo, sprints, feedback), servicios (lista), CTA y footer.  
- **docs/copia/COPY-FRAGMENTOS.md** — Versiones cortas (headlines, bullets) para cards o banners.  
- **docs/copia/COPY-PRICING-PAGE.md** — Copia completa de la página de pricing (estilo Datadog): hero, modelos, productos por categoría, “cuando se combina”, soporte, FAQ.  
- **lib/copy-pricing.ts** — Datos estructurados para la UI (exportados desde la copia anterior).  
- **components/sections/pricing-section.tsx** — Sección de pricing que consume `lib/copy-pricing.ts`; integrada en `app/page.tsx`.  
- **docs/copia/COPY-LANDING-DATADOG-ESTILO.md** — Copia de landing al estilo Datadog: hero, “Why us?”, beneficios por sección (Simplify, Resolve, Optimize, etc.), cierre de plataforma y bloque de confianza (sin logos de clientes).  
- **docs/copia/COPY-LANDING-RONDESIGN-ESTILO.md** — Copia al estilo Rondesign: hero con confianza, bloques pregunta/respuesta (“¿Son los adecuados?”, “¿Tienen nivel?”, “¿Cuál es el plan?”), servicios con etiquetas y “Desde X sprints”, caso genérico, testimonial placeholder, proceso Brief → Propuesta → Contrato, “¿Qué recibo?” y contacto.  
- **docs/copia/COPY-DEVOPS-AS-A-SERVICE-DAAS.md** — Posicionamiento DaaS: qué es DaaS, dónde encajamos (managed CI/CD, IaC parcial; no observabilidad completa ni 24/7), beneficios que sí podemos usar, competencia, y copia lista para web (headlines, value prop, “qué incluye / qué no”, sugerencias SEO).  
- **docs/copia/COPY-BENEFICIOS-TESTING-EN-PIPELINE-DATADOG-ESTILO.md** — Beneficios al estilo Datadog Continuous Testing: tests en el pipeline (automáticos en cada commit), menos mantenimiento de herramientas, romper silos dev/entrega, visibilidad y menos MTTR. Incluye qué no ofrecemos (codeless, self-healing, APM) y cierre de sección.  
- **docs/copia/COPY-LANDING-OFERTA-Y-FAQ-ESTILO-SPENDBASE.md** — Oferta limitada + FAQ estilo Spendbase: hero oferta, valor repetido, testimonial, “cómo funciona”, FAQ (propiedad repo, acceso, salida, elegibilidad, facturación) y CTA. Sin créditos AWS.  
- **docs/copia/COPY-LANDING-HOMEPAGE-ESTILO-SPENDBASE.md** — Homepage completa estilo Spendbase: hero (“Deja de entregar a ciegas”), “backed by”, estadísticas, “tu elección”, “see how fast you can ship”, testimonial, journey (Día 1 / Semana 1 / Sprint 1), use cases, recursos y footer. Sin cashback ni créditos.  
- **docs/copia/COPY-COOKIE-BANNER-Y-FORMULARIO-DEMO.md** — Banner de cookies (EN/ES), consentimiento GDPR (Settings / Reject All / Accept, versión corta y larga), formulario “Solicitar demo”, página thank you.  
- **docs/copia/COPY-PRICING-TABLA-ESTILO-PALARK.md** — Tabla de precios estilo Palark: título “DevOps & CI/CD service pricing”, selector EUR/USD, planes Starter (XS) y Team (S) con características (pipeline, entornos, registry, soporte, consultoría incl., etc.), nota T&M para otros servicios, CTA Contactar / Agendar videollamada, footer.  
- **docs/copia/COPY-LANDING-ESTILO-ERICSSON-ORCHESTRATION.md** — Landing estilo Ericsson (orquestación): hero “Soluciones de orquestación del pipeline”, “Commit. Build. Deploy.”, bloques “Despliega tan rápido como hagas commit”, beneficios clave, funcionalidades clave, portfolio (Pipeline + env, Sitio, Consultoría), métricas, recursos, casos, “Solicitar demo guiada”, “Colabora con un equipo que entrega”, temas relacionados, footer.  
- **docs/copia/COPY-LANDING-ESTILO-CLERK.md** — Landing estilo Clerk (producto feature-led): hero “Pipeline as Code – Everything you need. Reproducible by default.”, bloques “Reproducible by default” (alcance, 2 rondas, doc, entrega verificada), “Pipeline en minutos”, “Componentes listos” (Jenkinsfile, Jenkins, Gitea, registry), “Tests en el pipeline”, “Alcance y control”, “Visibilidad y rollback”, CTA “Start now, no strings attached” / Solicitar demo, footer (Producto, Recursos, Empresa, Legal).  
- **docs/copia/COPY-AWS-MARKETPLACE-LISTING-DEVOPS-DAAS.md** — Ficha tipo AWS Marketplace (DevOps as a Service): descripción corta para listing, Overview (challenges, scaling with our support, key deliverables, service highlights, caja resumen sin 24/7 y precios desde 150–250 USD), Details (Sold by, Categories, Delivery method), Pricing (custom, request private offer, rangos Starter/Team/sprint), Legal, Resources, Support (suscripción, demo gratuita, sin 24/7 salvo acuerdo). Incluye listing card para resultados de búsqueda y categorías para filtros.  
- **docs/copia/COPY-LANDING-ESTILO-AZURE-DEVOPS.md** — Landing estilo Azure DevOps (pestañas): hero, pestañas Overview / Products / Security & trust / Pricing / Customer stories / Resources / Next steps; Overview (solución completa o a medida), Products (grid: Pipeline as Code, Jenkins+Gitea, Registry, Deploy, Sites, Consulting), Security (alcance y transparencia), Pricing (Starter, Team, por sprint), Customer stories (placeholders), Resources (What is pipeline as code?, CI/CD as a service?, Scope and revisions), Next steps (Request demo, See pricing, Contact), footer.  
- **docs/copia/COPY-LANDING-ESTILO-GLOBALLOGIC-DEVOPS-DAAS.md** — Landing estilo GlobalLogic (DevOps-as-a-Service): hero “Championing pipeline-as-code…”, How we help, Our capabilities (Advisory, Pipeline as Code/CI/CD, Registry, Deploy, Visibility/docs), Case studies (placeholders), Featured insights/Resources, FAQs (stability, resilience, scalability), CTA “Let’s generate impact together”, footer. En COPY-COOKIE-BANNER-Y-FORMULARIO-DEMO.md añadido cookie banner estilo GlobalLogic (“We value your privacy”, categorías Necessary/Statistics/Marketing, Allow all / Customize / Necessary only).
- **docs/copia/COPY-COOKIE-BANNER-Y-FORMULARIO-DEMO.md** — Añadida variante corta del banner (solo “Accept”): “This website stores cookies on your computer…” + enlace Privacy Policy (EN/ES).
- **docs/copia/COPY-RECURSO-BLOG-DEVOPS-DAAS-WHAT-IT-IS.md** — Recurso/blog “DevOps as a Service: What It Is, How It Works, and Why It Matters”: estructura tipo artículo (Introduction, What is DaaS, Managed service, Platform vs DaaS, Benefits, Who benefits, Providers, Trends, Conclusion), adaptado a lo que ofrecemos (managed CI/CD, alcance por escrito; sin 24/7 ni observabilidad completa). Para Blog, Resources o Featured insights; SEO y credibilidad.

---

## 5. Próximos pasos

1. Integrar los textos de `docs/copia/COPY-PRODUCTOS-SERVICIOS-PRICING.md` en los componentes del sitio (Hero, SectionBlock, DemosSection, PricingSection, Footer).  
2. Sustituir placeholders del sitio por estos textos.  
3. Si se definen precios fijos o paquetes (Starter/Team), actualizar la sección de pricing en el mismo archivo y en la UI.

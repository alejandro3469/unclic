# Patrón “Capabilities” y hub pillar (tipo Sequoia)

**Referencia principal:** [sequoia.com/total-comp-benefits](https://www.sequoia.com/total-comp-benefits/) — página **pillar** que agrupa una familia de capacidades bajo un mensaje único.

También refleja el **mega menú Capabilities**: Total Comp & Benefits · Compensation & Equity · Benefits · HR & Payroll · Business Risk.

---

## 1. Mega menú Capabilities (Sequoia) → UnClic

| Sequoia | Hub UnClic sugerido | Idea |
|---------|---------------------|------|
| **Total Comp & Benefits** | **`/capacidades/pipeline-integral`** | Vista 360°: Git + Jenkins + registry + deploy + doc |
| Compensation & Equity | `/capacidades/versionado-releases` | Ramas, tags, rollback, Jenkinsfile |
| Benefits | `/capacidades/artefactos-registry` | Imágenes, tags, promoción entre entornos |
| HR & Payroll (PEO) | `/capacidades/operacion-gestionada` | Jenkins/Gitea operados (paralelo Outsourcing) |
| Business Risk | `/capacidades/trazabilidad-seguridad` | Auditoría, HTTPS, secretos, compliance básica |

Hoy el **footer Capacidades** enlaza a anclas; estas rutas son **fase 2** para páginas hijas.

---

## 2. Estructura de la página pillar (ej. Total Comp & Benefits)

| Bloque Sequoia | Equivalente UnClic |
|----------------|---------------------|
| Subnav sticky | **Advisory · Outsourcing · Platform · Todas las capacidades** → mismas 4 Solutions con enlaces |
| **Eyebrow** `TOTAL COMP & BENEFITS` | **`PIPELINE & ENTREGA INTEGRAL`** |
| **H1** “no brainer — X and Y belong together” | **Obvio: el pipeline y el código que despliegas van en el mismo sitio** (Git + CI/CD alineados) |
| Párrafo % spend / compete | **Gran parte del riesgo de producto está en cómo despliegas**; sin visión unificada compites mal en velocidad y estabilidad |
| Hero visual | Hub, diagrama Cloudcraft o flujo commit→deploy |
| **“The power to unlock strategic levers”** | **Palancas que solo un stack integrado desbloquea** |
| Tabla **YOUR CHALLENGE / THE SEQUOIA ADVANTAGE** (3 filas) | **TU RETO / VENTAJA UNClic** — fila 1: datos en silos (Jenkins vs Git vs AWS) → vista unificada en hub + diagrama. Fila 2: reactivo / poco soporte → asesoría + plantillas. Fila 3: solo software sin estrategia → advisory + plataforma documentada |
| Cada fila con “With Sequoia OS…” / “With 20+ years…” | Cierre por fila: **Con el hub demostrable** / **Con X años en retail/POS/pipeline** (solo verdad) |
| **Right-fit solutions** (3 columnas) | Misma tríada: **Asesoría & pipeline** · **Pipeline gestionado** · **Plataforma & hub (OS)** — enlaces a `/soluciones/...` |
| Imágenes por columna | Capturas reales de Jenkins, Gitea, Cloudcraft |
| **“Total Comp & Benefits Capabilities”** (grid) | **Capacidades de entrega** — tarjetas con “Ver más” |
| Tarjetas: Analytics, Benchmarking, Headcount… | **Analytics de pipeline** (logs, duración) · **Comparar con estándar** (madurez CI/CD) · **Plan de releases** · **Páginas colaborativas** (doc viva) · **Modelado** (coste cloud / tiempo de deploy) — adaptar a lo que ofrezcáis |
| Enlaces “All Compensation / All Benefits” | **Todas las capacidades de build** / **Todas las de deploy** (sub-índices) |
| Testimonios SMB / MID / ENTERPRISE | Retail, cadena, equipo PE |
| Latest Insights | Carrusel blog |
| CTA “Get in touch with an expert” | **Habla con un asesor** / `#contacto` |

---

## 3. Bloque “FEATURED REPORT” (desde mega menú)

En Sequoia: informe descargable + CTA **Get the Report**.

**UnClic:** **INFORME / GUÍA DESTACADA** — ej. *¿Tu estrategia de despliegue está lista para 2026?* → PDF o doc del toolkit (FastFlow, checklist Jenkins) → **Descargar guía**.

---

## 4. Capacidades hijas bajo el pillar (ejemplo Total Comp)

En el menú desplegable, bajo **Total Comp & Benefits** aparecen: Analytics, Benchmarking, Financial Modeling, Headcount Planning, Collaborative Pages.

**Paralelo UnClic** bajo **Pipeline integral**:

| Capacidad hija | Contenido |
|----------------|-----------|
| **Analytics** | Visibilidad de builds, fallos, tiempo al verde |
| **Benchmarking** | Comparar prácticas vs estándar (maturity) |
| **Modelado** | Escenarios de coste o frecuencia de release |
| **Planificación** | Roadmap de entornos y versiones |
| **Colaboración** | Un solo lienzo: arquitectura + runbooks |

---

## 5. Página hija de capacidad (ej. Analytics)

**Referencia:** [sequoia.com/total-comp-benefits/analytics](https://www.sequoia.com/total-comp-benefits/analytics/) — página **nivel 3** bajo el pillar Total Comp & Benefits.

**Ruta UnClic sugerida:** `/capacidades/pipeline-integral/analytics` o `/capacidades/analytics-pipeline`.

| Bloque Sequoia | Equivalente UnClic |
|----------------|---------------------|
| **Eyebrow** `TOTAL COMP & BENEFITS ANALYTICS` | **`PIPELINE & ENTREGA · ANALYTICS`** o **`OBSERVABILIDAD DEL CI/CD`** |
| **H1** “Keep your eye on the bigger picture” | **Mantén la visión del pipeline completo** — builds, fallos, tendencias, no solo el último error |
| Párrafo intro (unified view, leadership) | Jenkins + Gitea + registry + diagrama en **una narrativa** para CTO/CIO y equipo; briefings con datos reales |
| **H2** “Activate your strategy…” | **Activa la mejora continua con una vista unificada** — commits, ramas, duración media, tasa de éxito |
| Bullets (single source of truth, boardroom, drill down) | **Una fuente de verdad** (Git + Jenkins) · **Listo para comité** (slides de salud del pipeline) · **Drill-down** por repo, rama, entorno, responsable |
| Imagen ejecutivo + datos | Captura **Console Output** + **Blue Ocean** o métricas agregadas |
| **H2** “The right … analytics to drive decisions” | **La analítica adecuada para decidir sobre releases** |
| Párrafo no-code workspace | Espacio de lectura para **ejecutivos, Finanzas (coste cloud), HR tech** — sin tocar Jenkins a mano para ver el pulso |
| **Pre-built Dashboards** + sub-bullets | **Dashboards sugeridos:** resumen ejecutivo · vista por persona (CTO, lead dev, DevOps) · drill por servicio/rama · headcount de jobs / fallos recurrentes |
| **Board-ready Decks** | **Informe mensual de entrega** — plantilla exportable o sección fija en doc |
| **Custom Reporting** | **Reportes bajo demanda** — logs filtrados, comparativa entre sprints |
| **Secure Access** | **Acceso seguro** — enlaces read-only, demo invitado, sin exponer credenciales |
| **Flexible solutions** (3 tarjetas $) | Misma tríada **Asesoría · Outsourcing · Plataforma** con enlace a `/soluciones/...` |
| Client success + Insights + Resources + CTA | Igual que pillar; CTA **Hablemos** / asesor |

### Otras hijas del mismo molde

**Benchmarking** → `/capacidades/.../benchmarking` — madurez CI/CD vs industria.  
**Modelado** → escenarios de coste/tiempo.  
**Planificación** → calendario de releases.  
**Colaboración** → arquitectura viva + runbooks compartidos.

---

## Enlaces Sequoia (referencia)

- [Hub Total Comp & Benefits](https://www.sequoia.com/total-comp-benefits/)  
- [Analytics (hija)](https://www.sequoia.com/total-comp-benefits/analytics/)  
- Menú **Capabilities** en [sequoia.com](https://www.sequoia.com)

Documentos relacionados: [PATRON-PAGINA-SOLUCION-SEQUOIA.md](PATRON-PAGINA-SOLUCION-SEQUOIA.md) · [IA-SITIO-ESTILO-ENTERPRISE.md](IA-SITIO-ESTILO-ENTERPRISE.md).

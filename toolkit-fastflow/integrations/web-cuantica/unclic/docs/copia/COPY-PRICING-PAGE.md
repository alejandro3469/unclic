# Copia — Página de Pricing (estilo Datadog)

Pricing flexible y transparente, pensado para escalar con tu equipo. Solo productos y servicios que ya ofrecemos. Descuentos por volumen o compromiso multi‑mes según acuerdo.

---

## Hero de la página Pricing

**Título**  
Pricing

**Subtítulo**  
Precios flexibles y transparentes, pensados para escalar con tu negocio.

**Línea opcional**  
Descuentos por volumen o compromiso multi‑mes disponibles.

**CTAs**  
- [Probar la demo](#demos)  
- [Contactar](#contacto)  
- [Ver planes](#planes)

---

## Modelos de facturación

**Suscripción** — Pago mensual o trimestral por pipeline activo o por entorno. Previsibilidad y mantenimiento del flujo incluido.

**Pago por sprint** — Alcance acotado por sprint (p. ej. 2 semanas). Entregas con feedback en cada ciclo; revisiones base incluidas.

---

## Productos por categoría (bloques tipo Datadog)

### Pipeline y CI/CD

| Producto | Descripción breve | Precio (texto) |
|----------|-------------------|----------------|
| **Pipeline as Code** | Jenkinsfile, Jenkins, Gitea; build, test y despliegue automático ante commit. | Cotización según alcance |
| **Registry de imágenes** | Imágenes Docker versionadas; despliegue y rollback con versión conocida. | Incluido con pipeline o por entorno |

**Starting at (línea opcional):** *Cotización según alcance (un pipeline, un repo, un entorno).*

**Features (bullets):**
- Jenkinsfile versionado en Git, multibranch
- Ejecución automática ante commit (webhook)
- Build con Maven, tests y empaquetado integrados
- Registry opcional para imágenes versionadas

---

### Despliegue e infraestructura

| Producto | Descripción breve | Precio (texto) |
|----------|-------------------|----------------|
| **Despliegue** | EC2, Nginx; opcional Kubernetes y Terraform. Entorno acordado y repetible. | Por entorno / por sprint |
| **Infraestructura como código** | Terraform para redes, instancias y seguridad; documentación y repetibilidad. | Según alcance |

**Starting at:** *Por entorno o por sprint; cotización según alcance.*

**Features:**
- Despliegue en servidor (p. ej. EC2) o en cluster (K8s)
- Configuración y documentación en repo
- Rollback con versión conocida cuando hay registry

---

### Demos y sitios

| Producto | Descripción breve | Precio (texto) |
|----------|-------------------|----------------|
| **Demos en vivo** | Acceso a Jenkins, registry y app de ejemplo (POS con FastFlow). | Gratuito (muestra) |
| **Sitios y landings** | Sitios estáticos (p. ej. Next.js) con despliegue repetible por pipeline. | Por proyecto / por sprint |

**Features:**
- Probar el flujo commit → build → registry → deploy sin compromiso
- Sitios con contenido y estructura acordados por escrito; despliegue en entorno acordado

---

### Consultoría

| Producto | Descripción breve | Precio (texto) |
|----------|-------------------|----------------|
| **Consultoría y diseño** | Auditoría de infraestructura, diseño de pipeline y estrategia de despliegue. | Por proyecto / por sprint |

**Features:**
- Alcance pactado por escrito
- Entregables: documentación, diseño de pipeline, recomendaciones

---

## Cuando se combina con otros planes

- Pipeline + Registry + Despliegue en un mismo entorno: **suscripción por entorno** (mensual o trimestral).
- Varios repos o entornos: **plan Team**; cotización según alcance.
- Proyectos por fases: **pago por sprint** con entregas y feedback en cada ciclo.

---

## Soporte y servicio

**Incluido en todos los planes:**
- Documentación del pipeline y del despliegue en repo
- Alcance y revisiones definidos por escrito (propuesta o anexo)
- Entrega probada en el entorno acordado antes del cierre

**No incluido por defecto:**
- Soporte 24/7
- Revisiones ilimitadas (incluimos un número base de rondas; las adicionales por anexo)
- Alcance no especificado en la propuesta o anexo firmado

**Planes de soporte:** Desde soporte por email/chat en horario laboral hasta acuerdos a medida. Consultar según necesidad.

---

## Preguntas frecuentes (Pricing)

**¿Cómo se factura la suscripción?**  
Por pipeline activo o por entorno (Jenkins + registry + despliegue). Mensual o trimestral; descuentos por compromiso multi‑mes según acuerdo.

**¿Qué incluye el pago por sprint?**  
Alcance acotado por sprint (p. ej. 2 semanas): entregas parciales, feedback en cada ciclo y un número base de rondas de revisión (p. ej. 2). Lo adicional en alcance o revisiones se pacta por anexo.

**¿Hay facturación anual?**  
Sí. Podemos pactar compromiso anual con descuento; la cotización se adapta al alcance y al número de entornos o pipelines.

**¿Necesito contratar varios productos a la vez?**  
No. Puedes empezar por un pipeline y un entorno; Registry y Despliegue se añaden según necesidad. La consultoría y los sitios/landings se cotizan por proyecto o por sprint.

**¿Qué no está incluido en el precio?**  
Todo lo no especificado en la propuesta o en un anexo firmado: revisiones por encima de las pactadas, alcance nuevo, soporte post‑entrega ilimitado, formación in situ sin pacto por escrito.

**¿Hay ofertas para partners o equipos grandes?**  
Sí. Descuentos por volumen y compromiso multi‑mes; planes a medida para equipos con varios repos o entornos. Contactar para cotización.

---

## Resumen para UI (cards de planes)

| Plan | Descripción | CTA |
|------|-------------|-----|
| **Starter** | Un pipeline, un repo, un entorno. Suscripción o pago por sprint. | Solicitar cotización |
| **Team** | Múltiples repos o entornos, registry compartido, documentación y demos. | Solicitar cotización |
| **Demos** | Acceso gratuito a Jenkins, registry y app de ejemplo. | Probar la demo |

*Todos los precios reflejan expertise, tiempo, uso de infraestructura de pipeline y despliegue, y entrega probada en el entorno acordado. Cotización vigente según alcance y variables del proyecto.*

---

## Precios de referencia (calculados por costes y competencia)

*Detalle del cálculo: [CALCULO-PRECIOS-Y-COMPETENCIA.md](CALCULO-PRECIOS-Y-COMPETENCIA.md).*

**Suscripción (por pipeline o entorno):**
- Starter (1 pipeline, 1 repo, 1 entorno): desde **150–250 USD/mes** (2 775–4 625 MXN/mes).
- Team (varios repos/entornos, registry compartido): desde **350–550 USD/mes** (6 475–10 175 MXN/mes).

**Pago por sprint (2 semanas):**
- Setup pipeline + un entorno: **1 200–2 500 USD/sprint** (22 200–46 250 MXN).
- Mantenimiento / ampliación: **600–1 200 USD/sprint** (11 100–22 200 MXN).
- Sitio/landing + deploy: **800–1 800 USD/sprint** (14 800–33 300 MXN).

**One-off:** Setup inicial 2 000–4 000 USD (37 000–74 000 MXN); consultoría 1 000–2 500 USD (18 500–46 250 MXN).

**Por qué nuestra oferta es mejor:** Operación eficiente (AWS, metodología ya desarrollada); precios muy por debajo de consultoría USA (15k–50k USD por proyecto); alcance y revisiones por escrito; opción suscripción o pago por sprint.

# Copia — Productos, servicios, pricing y cliente ideal

Texto listo para integrar en componentes del sitio UnClic. Sin nombres de clientes. Enfoque: suscripción o pago por sprint; solo lo que ofrecemos y tenemos capacidad de vender.

---

## Hero / Headline

**Opción A (corto)**  
Pipeline como código. Entrega continua que sí escala.

**Opción B (valor)**  
Automatiza build, prueba y despliegue. Menos lead time, más control.

**Opción C (problema/solución)**  
De commit a producción en horas, no en semanas. Jenkins, registry y despliegue listos para tu stack.

---

## Value proposition (2–3 frases)

Ofrecemos **automatización de CI/CD** con pipeline as code (Jenkinsfile), Jenkins, registry de imágenes y despliegue repetible en tu entorno (EC2, opcional Kubernetes). No vendemos un producto genérico: aplicamos la metodología FastFlow a tu aplicación — Java, Maven, Docker — para que tengas build, tests, imágenes versionadas y rollback sin montar todo desde cero. Ideal para equipos que quieren reducir lead time y tener entregas verificables en un entorno acordado.

---

## Productos y servicios (qué ofrecemos)

- **Pipeline as Code** — Jenkinsfile versionado en Git, multibranch, ejecución automática ante commit. Build (Maven), tests, empaquetado y calidad integrados.
- **Jenkins + Gitea** — Orquestación del pipeline y repositorio central con permisos e integración lista para el flujo.
- **Registry de imágenes** — Almacenamiento de imágenes Docker versionadas; despliegue y rollback con versión conocida.
- **Despliegue** — En servidor (p. ej. EC2) o en cluster (Kubernetes) cuando el alcance lo requiera. Nginx, configuración y entorno acordado.
- **Infraestructura como código** — Terraform para redes, instancias y seguridad cuando aplica; documentación y repetibilidad.
- **Demos en vivo** — Acceso a entornos de prueba: Jenkins, registry y aplicación de ejemplo (POS con FastFlow) para ver el flujo commit → build → registry → deploy.
- **Sitios y landings** — Sitios estáticos (p. ej. Next.js) con despliegue repetible por pipeline; contenido y estructura acordados por escrito.
- **Consultoría y diseño** — Auditoría de infraestructura, diseño de pipeline y estrategia de despliegue dentro del alcance pactado.

*No ofrecemos observabilidad completa ni productos fuera de este catálogo. Lo no listado se cotiza por anexo si tenemos capacidad.*

---

## Las 6 primitivas (resumen para valor)

1. **Pipelines** — Tubería estandarizada; Jenkinsfile multibranch por repositorio.
2. **Auditoría de infraestructura** — Descubrimiento de activos, puertos y versiones cuando aplica.
3. **Extensibilidad** — Shared Libraries para pasos personalizados (tests de carga, notificaciones, etc.).
4. **Análisis de entrega** — Métricas tipo DORA (lead time, frecuencia de despliegue) cuando el pipeline lo permite.
5. **Estrategia de arquitectura** — Diseño y prototipado de despliegue (multi-ambiente, Terraform) según alcance.
6. **Automatización** — Rollback y disparadores basados en estado del despliegue cuando está en alcance.

---

## Pricing

**Modelos:**

- **Suscripción** — Pago mensual o trimestral por pipeline activo o por entorno (Jenkins + registry + despliegue). Incluye mantenimiento del flujo, actualizaciones de configuración dentro del alcance y documentación. Ideal para equipos que quieren previsibilidad.
- **Pago por sprint** — Alcance acotado por sprint (p. ej. 2 semanas). Incluye entregas con feedback en cada ciclo; revisiones base (p. ej. 2 rondas) incluidas; lo adicional por anexo. Ideal para proyectos con alcance definido por fases.

**Opciones de paquete (cuando se definan):**

- **Starter** — Un pipeline, un repo, despliegue en un entorno. Cotización según alcance.
- **Team** — Múltiples repos o entornos, registry compartido, documentación y demos. Cotización según alcance.

*Todos los precios reflejan expertise, tiempo, uso de infraestructura de pipeline y despliegue, y entrega probada en el entorno acordado. Cotización vigente según alcance y variables del proyecto.*

---

## Cliente ideal / A quién nos dirigimos

Trabajamos mejor con equipos y organizaciones que:

- **Definen alcance por escrito** — Lo que está dentro (y fuera) del proyecto queda en propuesta, anexo u orden de encargo. Así evitamos desalineación de expectativas.
- **Respetan sprints y plazos** — Entienden que los plazos se estiman en días laborables o sprints; las extensiones por alcance nuevo o revisiones extra se pactan por anexo.
- **Dan feedback en tiempo acordado** — Entrega continua significa revisión en cada entrega, no acumular comentarios al final. Disponibilidad de material (contenido, aprobaciones) en los plazos pactados.
- **Asumen un número claro de revisiones** — Incluimos un número base de rondas de revisión (p. ej. 2); las adicionales se cotizan aparte. Evitamos “revisiones ilimitadas” sin pacto.
- **Conocen el stack** — Git, pipeline, despliegue en servidor o contenedores. No es necesario que lo operen ellos; sí que entiendan que entregamos código versionado, pipeline repetible y despliegue en un entorno acordado.

*Buscamos proyectos donde el alcance, el tiempo y el feedback estén alineados desde el inicio. Así protegemos la calidad de la entrega y la relación a largo plazo.*

---

## Expectativas: tiempo, sprints y feedback

- **Plazos** — Los estimamos en **días laborables** o en **sprints** (p. ej. 1 sprint = 2 semanas). Cada hito (diseño, desarrollo, revisiones, despliegue) se indica en la propuesta.
- **Revisiones** — Incluimos un número base de **rondas de revisión** (por ejemplo, 2). Cada ronda = entrega → comentarios del cliente → ajustes y reentrega. Las rondas adicionales se acuerdan por anexo (tiempo y, si aplica, ajuste de precio).
- **Feedback continuo** — Esperamos comentarios en cada entrega parcial, no solo al final. La disponibilidad de material del cliente (textos, assets, aprobaciones) en los plazos acordados impacta los tiempos.
- **Entrega y cierre** — El entregable se **prueba en el entorno acordado** y se deja desplegado o listo para uso según lo pactado. El cierre se da cuando se cumple el alcance y se acepta la entrega según los criterios definidos.

*Lo no especificado en la propuesta o en un anexo firmado no se considera incluido en alcance, plazo ni precio.*

---

## Servicios (lista corta para UI / cards)

| Servicio | Descripción breve |
|----------|-------------------|
| Pipeline as Code | Jenkinsfile, Jenkins, Gitea; build, test y despliegue automático. |
| Registry | Imágenes Docker versionadas; rollback conocido. |
| Despliegue | EC2, Nginx; opcional Kubernetes y Terraform. |
| Demos | Jenkins, registry y app de ejemplo en vivo. |
| Sitios y landings | Next.js (u otro estático) con deploy repetible. |
| Consultoría | Auditoría de infraestructura y diseño de pipeline. |

---

## CTA (llamadas a la acción)

- **Hero:** «Ver demos» / «Solicitar cotización»
- **Post valor:** «Probar la demo» / «Ver planes»
- **Pricing:** «Elegir plan» / «Cotización según alcance»
- **Cierre:** «Contactar» / «Definir alcance»

*No prometemos soporte 24/7 ni servicios fuera del catálogo. «Probar la demo» apunta a los entornos de demostración (Jenkins, registry, POS FastFlow).*

---

## Footer / Referencias técnicas

Enlaces sugeridos para el pie (según REFERENCIAS-PARA-LANDING-FOOTER):

- Jenkins  
- Docker  
- Kubernetes  
- Terraform  
- Helm  

*Texto sugerido para el bloque de referencias:* «Construido con Jenkins, Docker, Kubernetes y Terraform. Pipeline as Code para entrega reproducible.»

---

## Variables del proyecto (recordatorio para propuestas)

Al cotizar, se consideran: número de entregables, complejidad de lógica, rondas de revisión incluidas, disponibilidad de material del cliente, requisitos de seguridad, entorno de despliegue (propio, cliente, nube). Lo no especificado en la propuesta o en un anexo firmado no se considera incluido.

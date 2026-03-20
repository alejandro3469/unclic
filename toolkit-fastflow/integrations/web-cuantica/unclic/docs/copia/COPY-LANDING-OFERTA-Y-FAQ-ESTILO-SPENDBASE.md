# Copia landing — Oferta limitada + FAQ (estilo Spendbase)

Estructura inspirada en páginas tipo Spendbase: headline de oferta, valor repetido, testimonial, bloques “cómo funciona” en pasos, FAQ y CTA. Adaptada a **UnClic**: demo gratuita, pipeline desde X sprints, **sin créditos AWS**. Sirve para una landing de conversión (demo / cotización).

---

## Hero — Oferta

**Línea superior (badge)**  
Oferta limitada

**Headline**  
Acceso a demo gratuita · Pipeline desde 1 sprint.

**Subheadline**  
Ellos redujeron el lead time; tú también puedes.

**CTA**  
Solicitar demo · Reclamar acceso

---

## Bloque de valor repetido (visual)

*Para uso en UI: tarjetas o tiras que repiten el mismo beneficio.*

**Frase a repetir (ejemplos):**
- *Pipeline desde 1 sprint*
- *Lead time en horas, no en semanas*
- *Demo en minutos*

*No usar “créditos AWS” ni cifras de créditos; nosotros no ofrecemos eso. Sí: “demo gratuita”, “pipeline desde 1 sprint”, “cotización según alcance”.*

---

## Lo que dicen (testimonial)

**Título**  
Nuestros clientes dicen:

**Cita (placeholder)**  
“Sin papeleo innecesario. Su equipo montó el pipeline, nos dio acceso a la demo y en poco tiempo teníamos build y deploy automatizados. Alcance claro desde el primer día.”

**[Nombre], [Cargo], [Empresa o tipo de proyecto]**

*Si no hay testimonial real, usar genérico: “Equipo técnico, startup” o quitar nombre y dejar solo la cita.*

---

## Cómo funciona (pasos tipo “Hot deal”)

**Título**  
Empieza a entregar más rápido

**Pasos:**

| # | Título | Descripción |
|---|--------|-------------|
| **1** | Habla con nosotros | Cuéntanos tu stack y tu objetivo (un repo, un entorno, sitio estático, etc.). |
| **2** | Te damos acceso a la demo | Sin compromiso: Jenkins, registry y app de ejemplo para que veas el flujo commit → build → deploy. |
| **3** | Cotización a tu medida | Si encaja, te enviamos propuesta: alcance, plazos (sprints o suscripción) y precio. Sin sorpresas. |

**CTA**  
Solicitar demo

---

## Kickstart (4 pasos resumidos)

**Título**  
Arranca con nosotros

1. **Habla con nosotros** — Opciones (demo, pipeline, sitio, consultoría).  
2. **Definimos alcance** — Por escrito: qué está incluido, revisiones, entorno.  
3. **Usas la demo o arrancamos el proyecto** — Acceso a la demo o primer sprint según acuerdo.  
4. **Pipeline listo (o cotización)** — Entregable en el entorno acordado o propuesta clara para el siguiente paso.

**CTA**  
Solicitar demo

---

## FAQ — Preguntas frecuentes

**Título**  
Aclara las dudas

- **¿Seguimos siendo dueños de nuestro repositorio y código?**  
  Sí. El código vive en tu repo (Gitea o el que uses); el Jenkinsfile y la configuración del pipeline pueden estar en tu repo o en el nuestro según acuerdo. Tú mantienes la propiedad del código.

- **¿Qué nivel de acceso tienen a nuestros sistemas?**  
  Solo el necesario para el alcance pactado: por ejemplo, acceso a un repo para configurar el pipeline, o a un servidor para desplegar. Se define por escrito en la propuesta o anexo. No gestionamos tu cuenta AWS ni tu facturación.

- **Si dejamos de trabajar juntos, ¿qué pasa con el pipeline y el despliegue?**  
  Te entregamos el Jenkinsfile, la documentación y lo desplegado en el entorno acordado. Puedes seguir operando el pipeline en tu propio Jenkins o migrar a otro proveedor; no hay “encerronas”.

- **¿Cuál es el criterio de elegibilidad?**  
  Trabajamos mejor con equipos que definen alcance por escrito, dan feedback en tiempo acordado y respetan plazos y número de revisiones. No hay requisito de tamaño mínimo; sí expectativa de alineación en alcance y comunicación.

- **¿Qué obtienen ustedes a cambio?**  
  Una relación comercial clara: tú pagas por el servicio (suscripción o por sprint); nosotros entregamos pipeline, documentación y despliegue según lo pactado. Sin comisiones ocultas ni reventa de créditos.

- **¿Hay compromisos mínimos?**  
  Depende del servicio: la demo no implica compromiso; un proyecto por sprint tiene el alcance de ese sprint; una suscripción puede ser mensual o trimestral según lo acordado. Todo se deja por escrito.

- **¿Los precios vienen de ustedes o de un tercero?**  
  Los precios los fijamos nosotros según alcance, tiempo y valor entregado. No somos revendedores de AWS ni de otras plataformas; facturamos nuestro servicio (pipeline, despliegue, consultoría).

- **¿Recibimos facturación directa de AWS o solo de ustedes?**  
  Si la infraestructura (EC2, etc.) es tuya, tú recibes la factura de AWS. Si usas nuestra infra para la demo o para un entorno gestionado por nosotros, la facturación de ese entorno es con nosotros según lo pactado.

**CTA bajo FAQ**  
¿Listo para empezar? · Solicitar demo

---

## Uso en componentes

- **Hero:** Badge “Oferta limitada” + headline (demo / pipeline desde 1 sprint) + “Ellos redujeron lead time; tú también” + CTA.  
- **Bloque repetido:** Tarjetas o banda con “Pipeline desde 1 sprint” o “Demo en minutos” (sin créditos AWS).  
- **Testimonial:** Una cita + nombre/cargo (o genérico).  
- **Cómo funciona / Kickstart:** Lista numerada o cards con los 3–4 pasos.  
- **FAQ:** Acordeón o lista con las 8 preguntas; refuerza transparencia (propiedad del código, acceso, salida, elegibilidad, compromisos, facturación).  
- **CTA final:** “Solicitar demo” o “Reclamar acceso a la demo”.

# Copia landing — Estilo Clerk (producto, feature-led, “secure by default” → “reproducible by default”)

Estructura inspirada en la página de **User Authentication** de Clerk: hero corto, bloques por capacidad (cada uno con headline, valor y 3–4 bullets), “components” listos para usar, cierre “Start now, no strings attached” y footer por categorías. Adaptada a **Pipeline as Code** y CI/CD. “Secure by default” → **“Reproducible by default”**.

---

## Navegación

- Productos · Docs · Changelog (opcional) · Empresa · Precios  
- Iniciar sesión (si aplica) · **Empezar** / **Solicitar demo**

---

## Hero

**Headline**  
Pipeline as Code

**Subheadline**  
Todo lo que necesitas. Reproducible por defecto.

**Cuerpo**  
Autenticación y gestión de usuarios no; **pipeline y despliegue**, sí. Un flujo simple y repetible, con todo lo necesario para que cada commit termine en build, tests y despliegue verificable. Alcance por escrito, sin sorpresas.

**CTA**  
Empezar · Solicitar demo

---

## Bloque 1 — Reproducible por defecto (badges / bullets)

*Clerk: SOC2, HIPAA, Bot detection, Password leak. Nosotros: control y trazabilidad.*

**Headline**  
Reproducible por defecto.

**Bullets:**

- **Alcance por escrito** — Lo que está incluido (y lo que no) queda en propuesta o anexo. Sin scope creep sin acuerdo.
- **2 rondas de revisión incluidas** — Las adicionales se cotizan por anexo. Transparencia desde el día uno.
- **Documentación en repo** — Jenkinsfile y guías versionados en Git. Cualquier persona del equipo puede revisar el flujo.
- **Entrega verificada** — El entregable se prueba en el entorno acordado antes del cierre. No “te lo mando y tú ves”.

**CTA**  
Empezar

---

## Bloque 2 — Pipeline en minutos

*Clerk: “Social SSO – Add in seconds”. Nosotros: pipeline en minutos.*

**Headline**  
Añade pipeline a tu repo en minutos.

**Cuerpo**  
Cuando el flujo está definido, un nuevo repo o entorno puede tener build, test y deploy en poco tiempo. Nosotros te damos el Jenkinsfile, Jenkins y Gitea operados; tú te centras en el producto.

**Bullets:**

- **Un repo, un entorno (Starter)** — La base: un pipeline, un repositorio, un entorno de despliegue. Escalable a más según acuerdo.
- **Integración en un clic (conceptual)** — No configuras Jenkins a mano; el flujo viene definido y documentado.
- **Elige tu stack** — Maven, Docker, Git; opcional registry y Kubernetes según alcance.
- **Enlace automático** — Commit en tu rama dispara el pipeline; no hace falta lanzar builds a mano.

**CTA**  
Ver demo

---

## Bloque 3 — Componentes listos (como Clerk Components)

*Clerk: “Pre-built components, ready for everything” – SignIn, SignUp, etc. Nosotros: pipeline “pre-built”.*

**Headline**  
Pipeline listo, donde lo necesites.

**Cuerpo**  
Añade Jenkinsfile, Jenkins, Gitea y (opcional) registry a tu flujo. El pipeline vive en tu repo o en el nuestro según acuerdo; mantienes el control del código y del flujo.

**Bullets / elementos:**

- **Jenkinsfile** — Definición del flujo en Git; multibranch, declarativo.
- **Jenkins + Gitea** — Orquestación y repositorio central; operados por nosotros.
- **Registry** — Imágenes versionadas; despliegue y rollback con versión conocida cuando aplica.
- **Tu dominio, tu marca** — Documentación y entregables bajo tu repo o tu entorno; no “caja negra”.

**CTA**  
Explorar componentes · Solicitar demo

---

## Bloque 4 — Tests en el pipeline

*Clerk: MFA, Passwordless. Nosotros: tests como parte del flujo.*

**Headline**  
Tests en cada ejecución.

**Cuerpo**  
Los tests forman parte del pipeline: se ejecutan en cada build (p. ej. Maven `test`). No hace falta una plataforma de testing aparte para tener calidad en el flujo.

**Bullets:**

- **Automático** — Commit → build → tests → package → deploy. Si los tests fallan, el pipeline no avanza (según configuración).
- **Visibilidad** — Logs y estado en Jenkins; sabes en qué etapa falló y por qué.
- **Repetible** — Mismo flujo en cada ejecución; mismo entorno de build para todos.

**CTA**  
Ver cómo funciona

---

## Bloque 5 — Alcance y control (como “Enterprise SSO” / control avanzado)

**Headline**  
Alcance y control, sin implementarlo tú.

**Cuerpo**  
Olvídate de definir desde cero qué incluye cada proyecto y cómo se factura. Alcance por escrito, número de revisiones incluido y lo adicional por anexo. Implementamos la transparencia en la propuesta y en el contrato.

**Bullets:**

- **Alcance en la propuesta** — Qué está incluido (pipeline, entornos, revisiones) y qué no.
- **Revisiones acotadas** — Incluimos un número base (p. ej. 2); las extra se pactan por anexo.
- **Sin sorpresas en facturación** — Suscripción o pago por sprint; lo no especificado no está incluido.

**CTA**  
Solicitar cotización

---

## Bloque 6 — Control avanzado (visibilidad y rollback)

*Clerk: “Advanced security”. Nosotros: visibilidad y rollback.*

**Headline**  
Visibilidad y rollback, sin montar la infra.

**Cuerpo**  
Trabajar con nosotros significa tener pipeline operado, documentado y desplegado en el entorno acordado. La visibilidad y el control del flujo son parte del servicio.

**Bullets:**

- **Visibilidad del pipeline** — Estado y logs en Jenkins; trazabilidad commit → build → artefacto → deploy.
- **Rollback con versión conocida** — Cuando hay registry, despliegas una versión anterior sin recompilar desde cero.
- **Documentación en repo** — Jenkinsfile y guías versionados; el flujo es auditable y repetible.

**CTA**  
Ver demo

---

## Bloque 7 — CTA final (“Start now, no strings attached”)

*Clerk: “Integrate complete user management in minutes. Free for your first 50,000… No credit card required.”*

**Headline**  
Empieza ya, sin compromiso.

**Cuerpo**  
Prueba la demo en minutos. Acceso a Jenkins, registry y app de ejemplo. Sin tarjeta de crédito. Si encaja, te enviamos una propuesta con alcance y precio.

**CTA**  
Solicitar demo · Empezar

---

## Footer (estructura tipo Clerk)

**Producto**  
- Pipeline y CI/CD  
- Despliegue  
- Consultoría  
- (SDKs no aplica; sí “Stack: Jenkins, Gitea, Maven, Docker”)

**Recursos**  
- Documentación  
- Changelog (opcional)  
- Precios  
- Glosario (opcional)

**Empresa**  
- Sobre nosotros  
- Blog (opcional)  
- Contacto  
- Legal (Términos, Privacidad, Cookies; no “Do not sell” salvo que aplique)

**Soporte**  
- Contacto · Email de soporte

**Copyright**  
© [Año] [Nombre]. Todos los derechos reservados.

---

## Resumen de bloques

1. Hero — Pipeline as Code · Todo lo que necesitas. Reproducible por defecto.  
2. Reproducible por defecto — Alcance por escrito, 2 rondas, documentación, entrega verificada.  
3. Pipeline en minutos — Un repo un entorno, integración, elige stack, enlace automático.  
4. Componentes listos — Jenkinsfile, Jenkins, Gitea, registry, tu dominio.  
5. Tests en el pipeline — Automático, visibilidad, repetible.  
6. Alcance y control — Propuesta, revisiones, sin sorpresas en facturación.  
7. Visibilidad y rollback — Estado en Jenkins, rollback, documentación.  
8. CTA final — Empieza ya, sin compromiso; solicitar demo.  
9. Footer — Producto, Recursos, Empresa, Legal, Soporte.

# Copia landing — Estilo Datadog (Why us, beneficios, confianza)

Estructura inspirada en la página de producto de Datadog (APM): hero con valor claro, bloque «Why us», beneficios por sección y cierre de confianza. Adaptada a **Pipeline as Code**, **entrega continua** y **metodología FastFlow**. Sin nombres de clientes.

---

## Hero (arriba del fold)

**Headline**  
Pipeline as Code y entrega continua a tu medida.

**Subheadline**  
Detecta, corrige y entrega más rápido: trazabilidad de commit a producción, pipeline versionado en Git y despliegue repetible, con control sobre alcance, revisiones y coste.

**CTA**  
Probar la demo · Sin tarjeta. Solicitar cotización.

**Nota legal (pie del formulario si hay signup)**  
Al solicitar cotización aceptas que te contactemos según nuestra política de privacidad.

---

## Empezar en minutos (estilo trial Datadog)

**Headline**  
Regístrate para tu prueba o solicita la demo.

**Value prop (1 línea)**  
Visibilidad de punta a punta. Pipeline y despliegue a tu escala.

**Subheadline**  
Configura tu acceso en minutos:

| Paso | Tiempo | Acción |
|------|--------|--------|
| **1. Regístrate / Solicita demo** | 30 segundos | Correo o formulario; sin tarjeta. |
| **2. Recibe acceso a la demo** | 1–2 minutos | Te enviamos el enlace a Jenkins, registry y app de ejemplo (o lo activamos en el mismo día). |
| **3. Explora el pipeline** | 1–2 minutos | Abre Jenkins, lanza un build o revisa el flujo commit → build → registry → deploy. |

**CTA**  
Solicitar demo · Registrarse

*Variante más corta para UI:* “1. Solicita la demo (30 s) · 2. Recibe el enlace (1–2 min) · 3. Prueba el pipeline (1–2 min).”

---

## WHY [UNCLIC / FASTFLOW]?

**Título del bloque:** ¿Por qué elegirnos?

- **Pipeline de punta a punta en minutos**  
  Configuración en poco tiempo; visualización del flujo commit → build → test → registry → deploy, con control sobre qué se ejecuta y dónde se despliega.

- **Pipeline como código (Jenkinsfile)**  
  Todo el flujo versionado en Git: multibranch, sin “magia” en la UI; cambios revisables y repetibles.

- **Seguimiento automático de despliegues**  
  Cada build genera artefacto o imagen versionada; despliegue y rollback con versión conocida; opción de comparar estados antes/después.

- **Control sobre alcance y coste**  
  Sin roll-ups ocultos: alcance por escrito, número de revisiones incluido, lo adicional por anexo. Suscripción o pago por sprint según lo pactado.

- **Menos tiempo manteniendo herramientas**  
  Nosotros operamos Jenkins, Gitea y el registry; tú recibes el pipeline listo, la documentación en repo y el despliegue en el entorno acordado.

---

## PRODUCT BENEFITS (beneficios por sección)

### Simplifica la complejidad del flujo de entrega

- Monitoriza todo el camino del código: desde el commit hasta el despliegue en un solo flujo (build, tests, imagen, registry, deploy).
- Identifica cuellos de botella, fallos de build, tests que fallan y despliegues lentos gracias a un pipeline estándar y documentado.
- Recoge y deja trazable cada paso (logs, artefactos, versiones) para poder auditar y repetir.

### Resuelve problemas de entrega más rápido

- Localiza fallos con el pipeline centralizado: en qué etapa falla (build, test, deploy) y con qué versión.
- Logs y artefactos en contexto: el Jenkinsfile y los stages definen qué se ejecutó y qué se desplegó.
- Menos cambios de contexto: un solo lugar (Jenkins + repo) para ver estado del pipeline y del despliegue.

### Optimiza el tiempo de entrega (lead time)

- Reduce el tiempo desde commit hasta producción con automatización y pasos estándar (Maven, Docker, registry).
- Métricas tipo DORA cuando el pipeline lo permite: frecuencia de despliegue, lead time, tasa de fallo, tiempo de recuperación.
- Rollback con versión conocida: vuelta atrás sin recompilar desde cero.

### Detecta problemas antes de que escalen

- Despliegue solo cuando el pipeline pasa (build + tests); menos sorpresas en producción.
- Verificación en entorno acordado antes del cierre: el entregable se prueba y se deja desplegado según lo pactado.
- Alcance y revisiones por escrito: menos malentendidos y menos “scope creep” sin acuerdo.

### Alertas y control solo sobre lo que importa

- Revisiones incluidas (p. ej. 2 rondas); las adicionales se pactan por anexo. Sin promesas de “revisiones ilimitadas” que desdibujan el alcance.
- Feedback en cada entrega (entrega continua): comentarios en tiempo acordado, no todo al final.
- Precios claros: suscripción o pago por sprint; lo no especificado en la propuesta no está incluido.

### Menos tiempo escalando y manteniendo herramientas

- Pipeline listo en hosts, contenedores o PaaS: Jenkins, Gitea, opcional registry y despliegue en EC2 o Kubernetes.
- Integración con Maven, Docker, Git; soporte para estándares (Jenkinsfile declarativo, pipelines multibranch).
- Una metodología reutilizable: mismo patrón para varios repos o entornos cuando el alcance lo permite.

### Visibilidad y control del pipeline

- Ver el estado del pipeline en Jenkins (y en el repo vía Jenkinsfile); logs por build y por stage.
- Artefactos e imágenes versionadas: retención acordada (p. ej. últimas N versiones) para controlar coste y espacio.
- Trazabilidad: commit → build → test → imagen → deploy; correlación con el código que lo generó.

### Extiende el pipeline sin redeployar la app

- Ajustar el Jenkinsfile (nuevos stages, nuevos entornos) y hacer commit; el pipeline se actualiza en la siguiente ejecución.
- Shared Libraries cuando aplica: pasos reutilizables (notificaciones, tests de carga) sin duplicar código en cada repo.
- Consultoría y diseño de pipeline dentro del alcance pactado: auditoría, recomendaciones y documentación.

---

## Cierre de plataforma (frase tipo “Essential for the Cloud Age”)

**Título**  
La plataforma de entrega continua para equipos que quieren control.

**Cuerpo**  
UnClic reúne pipeline as code (Jenkinsfile), Jenkins, Gitea, registry opcional y despliegue repetible para que tu aplicación, tu infraestructura y tu flujo de entrega sean trazables y verificables. Alcance por escrito, revisiones acotadas, entrega probada en el entorno acordado.

**Diagrama (concepto para UI)**  
Commit → Git (Gitea) → Webhook → Jenkins → Build (Maven) → Test → Package → [Registry] → Deploy (EC2/K8s) → Verificación.

---

## Confianza (sin logos de clientes)

**Título**  
Hecho para equipos que entregan.

**Cuerpo (opciones)**

- *Construido para equipos que valoran alcance claro, feedback en cada entrega y pipeline versionado en Git.*
- *Metodología probada en proyectos con entrega continua: pipeline as code, despliegue repetible y documentación en repo.*
- *Sin nombres de clientes en la web; referencias y casos de uso bajo acuerdo.*

**No usar:** “Loved by thousands” ni logos de empresas. Sí usar: “Built for teams that ship”, “Trusted by teams that value clear scope”, “Methodology built over years of pipeline and delivery work”.

---

## Uso en componentes

- **Hero:** Headline + subheadline + CTA (ver demos / solicitar cotización).
- **Sección “Why us”:** Las 5 bullets anteriores como cards o lista con icono.
- **Product benefits:** Cada subsección (Simplifica…, Resuelve…, Optimiza…, etc.) como bloque con título + bullets; opcional imagen o diagrama.
- **Cierre:** Bloque “La plataforma de entrega continua…” + diagrama de flujo.
- **Trust:** Una línea o bloque corto “Hecho para equipos que entregan” sin logos.

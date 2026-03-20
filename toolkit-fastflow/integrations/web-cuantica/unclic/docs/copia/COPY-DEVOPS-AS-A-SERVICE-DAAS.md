# Posicionamiento: DevOps as a Service (DaaS)

Cómo encajamos en el marco **DevOps as a Service** y qué copia usar en el sitio para aparecer en búsquedas y comparativas sin prometer lo que no ofrecemos.

---

## Qué es DaaS (resumen)

**DevOps as a Service (DaaS)** es un modelo de entrega en el que un proveedor externo gestiona pipelines de software, automatización de infraestructura y, en muchos casos, monitorización y seguridad. Las empresas consumen estas capacidades como servicio gestionado en lugar de montar y mantener un equipo DevOps interno y una toolchain compleja.

**Componentes típicos de DaaS (mercado):**

- **Managed CI/CD pipelines** — Flujos automatizados de integración y entrega continua.
- **Infrastructure as Code (IaC)** — Scripts (Terraform, Ansible) para aprovisionar y gestionar recursos en la nube.
- **Monitoring & observability** — Herramientas para salud y rendimiento del sistema (Prometheus, Grafana, etc.).
- **DevSecOps** — Seguridad y cumplimiento integrados en el pipeline.
- **Cloud cost optimization (FinOps)** — Análisis y ajuste de costes en la nube.

**Tipos de proveedores:**

- **Plataformas cloud (tooling):** Azure DevOps, AWS CodePipeline/CodeBuild/CodeDeploy, GCP.
- **Proveedores gestionados (expertise):** equipos embebidos o suscripciones mensuales para CI/CD, infra y soporte (ej. Kloia, Naviteq, SDH 2k–6k USD/mes).

---

## Dónde encajamos nosotros

Somos un **proveedor de expertise y servicio gestionado** en la parte de **CI/CD y despliegue**, no una plataforma cloud ni un DaaS “todo en uno”.

| Componente DaaS | Lo ofrecemos | Notas |
|-----------------|--------------|--------|
| **Managed CI/CD** | Sí | Jenkins, Gitea, Jenkinsfile, build/test/deploy; pipelines operados por nosotros. |
| **IaC** | Parcial | Terraform cuando aplica (redes, instancias, seguridad); documentación y repetibilidad. |
| **Monitoring / observability** | No como producto | No ofrecemos Prometheus, Grafana ni observabilidad completa. |
| **DevSecOps** | Parcial | Buenas prácticas y pipeline estándar; escaneo de seguridad según alcance pactado, no como suite completa. |
| **FinOps** | No como producto | No ofrecemos “cloud cost optimization” como servicio; sí control de costes en nuestra propia infra (AWS). |
| **Soporte 24/7** | No | Soporte en horario laboral según acuerdo; no prometemos 24/7. |

**Resumen:** Ofrecemos **CI/CD y despliegue como servicio gestionado** (pipeline as code, Jenkins, registry, deploy en EC2/K8s), con **alcance por escrito** y **suscripción o pago por sprint**. Nos posicionamos como alternativa a montar un equipo DevOps interno o a contratar consultoría one-off cara, no como sustituto de un DaaS completo con observabilidad y 24/7.

---

## Beneficios DaaS que sí podemos usar (con honestidad)

- **Eficiencia de costes:** Evita el coste de un equipo DevOps interno (salarios altos, contratación). Nuestros precios están por debajo de un DevOps full-time y de proyectos one-off de consultoría (15k–50k USD).  
- **Time-to-market:** Equipos con automatización bien montada despliegan mucho más a menudo que con procesos manuales. Nosotros entregamos el pipeline listo y documentado para que puedas desplegar con frecuencia.  
- **Escalabilidad:** Podemos escalar a más repos o entornos (plan Team, sprints adicionales) sin que tengas que gestionar la complejidad de Jenkins, Gitea y el registry por tu cuenta.  
- **No prometer:** “24/7”, “observabilidad completa”, “FinOps como producto”. Sí decir: “soporte según acuerdo”, “pipeline y despliegue verificables”, “alcance claro”.

---

## Competencia DaaS (referencia rápida)

- **Plataformas:** Azure DevOps, AWS CodePipeline/CodeBuild/CodeDeploy, GCP — venden tooling y minutos; nosotros vendemos diseño, operación y metodología (Jenkins + Gitea + tu entorno).  
- **MSPs:** Proveedores con suscripciones 2k–6k USD/mes o equipos embebidos; algunos con 24/7. Nosotros nos diferenciamos con **alcance explícito**, **sin 24/7** y precios que parten de suscripciones más bajas o pago por sprint.

---

## Copia para el sitio (DaaS)

### Headlines / taglines

- *CI/CD como servicio gestionado. Pipeline as code, Jenkins y despliegue sin montar un equipo DevOps.*  
- *DevOps as a Service con alcance claro: pipelines gestionados, entrega continua y despliegue en tu entorno.*  
- *Managed CI/CD: pipelines automatizados, registry y despliegue repetible. Suscripción o por sprint.*

### Value proposition (1–2 frases)

- *Ofrecemos CI/CD y despliegue como servicio gestionado: diseñamos y operamos tu pipeline (Jenkins, Gitea, registry), tú te centras en el producto. Alcance por escrito, sin 24/7 ni observabilidad completa; sí pipeline listo, documentación y entrega verificable.*  
- *En lugar de construir y mantener un equipo DevOps interno, consume pipeline as code y despliegue como servicio: Jenkinsfile, Jenkins, Gitea, registry opcional y deploy en EC2 o Kubernetes, con suscripción o pago por sprint.*

### Sección “Qué incluye” (estilo DaaS)

- **Managed CI/CD:** Flujos automatizados de integración y entrega (build, test, package, deploy) operados por nosotros.  
- **Pipeline as Code:** Jenkinsfile versionado en Git; cambios revisables y repetibles.  
- **Infraestructura como código (cuando aplica):** Terraform para redes e instancias; documentación en repo.  
- **Despliegue en tu entorno:** EC2, Nginx; opcional Kubernetes. Entrega verificada en el entorno acordado antes del cierre.  
- **Alcance y revisiones por escrito:** Sin sorpresas; lo no pactado se cotiza por anexo.

### Lo que no incluye (transparencia)

- Observabilidad completa (Prometheus, Grafana, APM).  
- Soporte 24/7.  
- FinOps o optimización de costes cloud como producto.  
- DevSecOps como suite completa (sí buenas prácticas y pipeline estándar según alcance).

### Meta / SEO (sugerencia)

- *DevOps as a Service · CI/CD gestionado · Pipeline as code · Jenkins · Entrega continua · Suscripción por sprint*  
- *Managed CI/CD y despliegue. Pipeline as code (Jenkinsfile), Jenkins, Gitea, registry. Alcance claro, sin 24/7. UnClic.*

---

## Uso

- **Hero o About:** Usar “CI/CD como servicio gestionado” o “DevOps as a Service con alcance claro”.  
- **Servicios:** Incluir “Managed CI/CD” y “Pipeline as Code” en las descripciones.  
- **Pricing / FAQ:** Dejar explícito qué sí y qué no (no 24/7, no observabilidad completa).  
- **Comparativas:** Si el cliente busca “DevOps as a Service” o “managed CI/CD”, este doc y la copia ayudan a posicionarnos y a comparar con AWS/Azure/MSPs con honestidad.

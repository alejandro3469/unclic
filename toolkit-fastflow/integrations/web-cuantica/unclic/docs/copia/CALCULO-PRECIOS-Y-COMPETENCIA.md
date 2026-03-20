# Cálculo de precios y comparativa con competencia

Base de costes (AWS, IA, componentes UI, horas invertidas), precios sugeridos y argumentario frente a competidores para ofrecer mejor valor y poder explicar el porqué.

---

## 1. Nuestra base de costes (qué gastamos)

### 1.1 Infraestructura (AWS)

| Concepto | Coste típico | Notas |
|----------|---------------|--------|
| 3 × t3.micro (Jenkins, Gitea, app/landing) | ~22 USD/mes | Fijo; sin añadir más instancias |
| EBS (discos) | ~0,10 USD/GB/mes | Mantener mínimo; revisar Volumes |
| Datos salida (tráfico) | ~0,09 USD/GB | Retención de artefactos en Jenkins; evitar servir vídeos/archivos pesados desde EC2 |
| **Presupuesto mensual de referencia** | **35–50 USD/mes** | Con margen hasta ~81 USD (1 500 MXN) si sube tráfico/EBS |

*Fuente: PLAN-COSTO-1500-MXN-Y-JENKINS (unclic).*

**Cálculo anual (referencia):** 35 × 12 = **420 USD/año** (mínimo) a 50 × 12 = **600 USD/año** (con margen).

---

### 1.2 Herramientas e IA

| Concepto | Estimación | Notas |
|----------|------------|--------|
| Cursor / IDE + asistencia IA | 20–50 USD/mes | Suscripción equipo o pro |
| APIs (OpenAI, etc.) si aplica | 10–80 USD/mes | Según uso en generación de código, copy, revisión |
| **Total IA/herramientas** | **30–130 USD/mes** | Redondeo conservador: **50 USD/mes** para cálculos |

**Cálculo anual:** 50 × 12 = **600 USD/año**.

---

### 1.3 Tiempo invertido (componentes UI, pipeline, metodología)

- **Horas/día de trabajo:** ~12 h/día (desarrollo, pipeline, UI, documentación, demos).
- **Días laborables/mes:** ~20.
- **Horas/mes:** 12 × 20 = **240 h/mes**.
- **Meses (o años) hasta “llegar a esto”:** muchos meses, inclusive años (toolkit FastFlow, demos, sitio UnClic, componentes reutilizables, guías).

Para no fijar un número arbitrario de “años”, usamos la idea de **amortización**: ese tiempo ya está invertido; el valor queda en la metodología, plantillas, Jenkinsfile, registry, documentación y componentes que reutilizamos. Por tanto:

- **No cobramos “años de trabajo” en un solo proyecto.**  
- **Sí cobramos:** (1) tiempo dedicado a *tu* proyecto (sprint o suscripción), (2) una parte del coste de operación (AWS + herramientas) y (3) un margen razonable.

**Tarifa de referencia (labor):** En México/LATAM, consultoría DevOps/desarrollo senior suele estar en **800–1 500 MXN/h** (≈ 40–75 USD/h). Para cálculos usamos **1 000 MXN/h** (≈ **50 USD/h**) como referencia media.

---

### 1.4 Resumen de costes operativos mensuales (referencia)

| Concepto | USD/mes | MXN/mes (aprox. 18,5) |
|----------|---------|------------------------|
| AWS (3 EC2 + EBS + tráfico contenido) | 35–50 | 650–925 |
| IA y herramientas | 30–130 (uso 50) | 555–2 405 (uso 925) |
| **Total operativo** | **85–180** (uso **~100**) | **~1 570–3 330** (uso **~1 850**) |

Con **1 cliente en suscripción** que paga solo la parte de infra compartida, necesitamos que la suscripción cubra al menos una fracción de ese coste + margen. Con **varios clientes o proyectos por sprint**, el coste fijo se reparte y el precio por cliente puede ser menor manteniendo rentabilidad.

---

## 2. Precios sugeridos (a partir de costes y competencia)

### 2.1 Competencia de referencia

| Competidor / tipo | Rango típico | Qué incluye |
|-------------------|--------------|-------------|
| **Consultoría Jenkins/CI-CD (USA)** | 15 000–50 000 USD por proyecto (4–8 semanas) | Pipeline, arquitectura, seguridad, transferencia de conocimiento |
| **Consultoría DevOps (USA)** | 100–300 USD/h (senior 125–200 USD/h) | Por hora; proyectos a medida |
| **Consultoría DevOps (México/LATAM)** | 800–1 500 MXN/h (40–75 USD/h) | Menor que USA; mismo tipo de trabajo |
| **GitLab CI (SaaS)** | 29 USD/usuario/mes (Premium) | Minutos de runners; no incluye diseño de pipeline a medida ni despliegue en tu EC2 |
| **GitHub Actions** | Gratis hasta 2 000 min/mes (repos privados); luego por minuto | Idem; no incluye Jenkins, Gitea ni metodología empaquetada |
| **Propuestas propias ya usadas (referencia)** | 22 000–38 000 € (Baxter); 160 000 MXN (versión pesos); “según cotización” (Vantive) | Proyectos 1–2 meses, alcance amplio (app + landing + despliegue) |

**Conclusión:** Un setup completo de pipeline (Jenkins + Gitea + registry + despliegue) en USA puede costar **15k–50k USD**. Nosotros podemos estar **por debajo** porque: (1) ya tenemos el toolkit y la metodología (coste amortizado), (2) operamos con infra barata (AWS 35–50 USD/mes), (3) no somos Big 4 ni boutique USA.

---

### 2.2 Precios sugeridos (USD y MXN)

Tipo de cambio de referencia: **1 USD ≈ 18,5 MXN**. Ajustar según cotización vigente.

#### Suscripción (por pipeline o por entorno)

| Plan | USD/mes | MXN/mes | Qué cubre |
|------|---------|---------|-----------|
| **Starter** (1 pipeline, 1 repo, 1 entorno) | 150–250 | 2 775–4 625 | Jenkins + Gitea + pipeline activo + despliegue en un entorno; mantenimiento del flujo y documentación en repo. |
| **Team** (varios repos o entornos, registry compartido) | 350–550 | 6 475–10 175 | Varios pipelines, registry, documentación y demos; soporte por email/chat en horario laboral según acuerdo. |

**Por qué este rango:**  
- Por debajo de 1 desarrollador part-time (≈ 50 USD/h × 20 h = 1 000 USD/mes).  
- Por encima del coste operativo por cliente (fracción de 85–100 USD/mes + margen).  
- Muy por debajo de 15k–50k USD de un proyecto one-off de consultoría USA.

#### Pago por sprint (2 semanas)

| Alcance típico | USD/sprint | MXN/sprint | Qué incluye |
|----------------|------------|------------|-------------|
| **Setup pipeline + un entorno** (nuevo cliente) | 1 200–2 500 | 22 200–46 250 | Jenkinsfile, Jenkins/Gitea config, build/test/deploy, 2 rondas de revisión, entrega en entorno acordado. |
| **Mantenimiento / ampliación** (cliente existente) | 600–1 200 | 11 100–22 200 | Mejoras acotadas, nuevo repo o etapa en pipeline, documentación. |
| **Sitio/landing + deploy** (estático, contenido acordado) | 800–1 800 | 14 800–33 300 | Diseño/estructura, contenido, pipeline de deploy, 2 rondas de revisión. |

**Por qué este rango:**  
- 1 sprint ≈ 40–80 h de trabajo implícitas (según complejidad). A 50 USD/h: 2 000–4 000 USD; nosotros proponemos menos porque reutilizamos toolkit y plantillas.  
- Por debajo del equivalente a 4–8 semanas de consultoría USA (15k–50k USD).  
- Alineado con propuestas propias en MXN/€ (ej. 160 000 MXN para proyecto 1–2 meses más amplio).

#### One-off (setup inicial opcional)

| Concepto | USD | MXN | Notas |
|----------|-----|-----|--------|
| **Setup inicial (un pipeline + un entorno)** | 2 000–4 000 | 37 000–74 000 | Una vez; luego suscripción mensual si quieren mantenimiento. |
| **Consultoría (auditoría + diseño de pipeline)** | 1 000–2 500 | 18 500–46 250 | Entregable: documento y recomendaciones; alcance por escrito. |

---

### 2.3 Descuentos sugeridos

- **Compromiso trimestral:** 5–10 % sobre el total del trimestre.  
- **Compromiso anual:** 10–15 % sobre el total del año.  
- **Multi-entorno / varios pipelines (Team):** Precio por segundo y tercer entorno con descuento (ej. -15 % por entorno adicional).

---

## 3. Por qué nuestra oferta es mejor (argumentario para el cliente)

### 3.1 Mensajes clave (para web y propuestas)

1. **Precios basados en costes reales y en nuestra capacidad.**  
   No cobramos “como en USA”: nuestra operación (AWS, herramientas, metodología ya desarrollada) nos permite ofrecer pipeline as code, Jenkins, registry y despliegue a precios muy por debajo de una consultoría tradicional (15k–50k USD por proyecto).

2. **Ya está construido.**  
   La metodología FastFlow, las plantillas de Jenkinsfile, la integración con Gitea, el flujo build → test → registry → deploy y la documentación ya existen. Tú no pagas “desde cero”; pagas por aplicarlo a tu repo y tu entorno y por el mantenimiento que acordemos.

3. **Transparencia.**  
   Alcance por escrito, número de revisiones incluido (p. ej. 2), lo que exceda por anexo. Sin sorpresas en facturación si se respetan los acuerdos.

4. **Flexibilidad.**  
   Suscripción si quieres previsibilidad; pago por sprint si prefieres proyectos acotados. No hace falta contratar “todo” ni compromisos largos para empezar.

5. **Comparado con GitLab/GitHub Actions.**  
   Ellos venden minutos de ejecución y productos SaaS. Nosotros te damos **tu** Jenkins, **tu** Gitea, **tu** registry y el diseño del pipeline a tu medida, desplegado en tu entorno (o en el nuestro). Ideal si quieres control, privacidad o integración con sistemas ya existentes.

6. **Comparado con contratar a un DevOps senior.**  
   Un desarrollador/DevOps senior full-time cuesta mucho más que una suscripción Starter o Team. Nosotros entregamos el pipeline, la documentación y el mantenimiento acordado sin que tengas que gestionar la contratación ni la rotación.

---

### 3.2 Frases listas para copiar (pricing y “por qué nosotros”)

- *“Nuestros precios reflejan una operación eficiente (infraestructura en AWS, metodología ya desarrollada) y se sitúan muy por debajo de la consultoría tradicional de pipeline y CI/CD en USA (15k–50k USD por proyecto).”*

- *“Ofrecemos suscripción mensual o trimestral por pipeline/entorno, o pago por sprint para proyectos acotados. Sin compromisos ocultos: alcance y revisiones por escrito.”*

- *“Lo que pagas incluye: pipeline as code (Jenkinsfile), Jenkins, Gitea, registry opcional, despliegue en el entorno acordado y documentación en repo. No vendemos minutos de cloud; vendemos el diseño, la configuración y el mantenimiento de tu flujo de entrega.”*

- *“Pensado para equipos que quieren reducir lead time y tener entregas verificables sin asumir el coste de un equipo DevOps dedicado ni de un proyecto one-off de seis cifras.”*

---

## 4. Cómo usar este documento

- **En propuestas:** Usar los rangos de la sección 2 (USD y MXN) y el argumentario de la sección 3. Ajustar según moneda del cliente (MXN, USD, EUR).  
- **En la web (pricing):** Si se muestran precios, usar “Desde X USD/mes” o “Desde X MXN/sprint” con los mínimos de la tabla; si no, mantener “Cotización según alcance” y en la sección “Por qué nuestros precios” o FAQ incluir las frases de 3.2.  
- **Actualización:** Revisar cada 6–12 meses: costes AWS/IA, tipo de cambio, y tarifas de competencia (consultoría, GitLab, GitHub) para mantener los rangos y el argumentario al día.

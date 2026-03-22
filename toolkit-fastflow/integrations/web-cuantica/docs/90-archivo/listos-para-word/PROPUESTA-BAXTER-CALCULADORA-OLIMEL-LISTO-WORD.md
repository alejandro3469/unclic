# PROPUESTA — Calculadora Olimel/Numetzah y presencia digital para Baxter

**Destinatario:** Baxter (Joers / Kings & Joers)  
**Asunto:** Desarrollo de la calculadora clínica Olimel/Numetzah para NPT (nutrición parenteral total), landing y entorno de entrega, alineado con la misión y los requisitos de Baxter.

---

## Introducción

En Baxter creen que cada persona merece la oportunidad de una vida sana, libre de enfermedad y llena de posibilidad. Unen la innovación que salva y sostiene vidas con los médicos, enfermeras y farmacéuticos que la hacen realidad: cuidado hospitalario, nutricional y quirúrgico, con un compromiso claro con los resultados del paciente y con el apoyo a los profesionales que prescriben y cuidan.

Esta propuesta plantea una solución digital pensada para colaborar con ustedes en ese objetivo: una **calculadora clínica** (Olimel/Numetzah) que permita a los profesionales de la salud calcular los requerimientos de NPT, elegir ecuaciones predictivas o ingresar resultados de calorimetría, y obtener la adaptación de una o dos opciones de Olimel (formato/bolsa) que mejor se ajusten al perfil del paciente, con tasas de oxidación visibles y alertas cuando corresponda. Incluye registro seguro de HCP, flujo clínico según checklist acordado, portafolio Olimel, guías ESPEN/ASPEN/ESPHGAN y reporte analítico acordado, además de la presencia en web (landing) y el despliegue en un entorno que cumpla con las políticas de privacidad y encriptación que Baxter requiere. El alcance se detalla a continuación, con variables consideradas, stack y buenas prácticas en que se basa la estimación, opciones de precio y cláusulas claras para dar transparencia y seguridad a ambas partes.

---

## 1. Alcance y entregables

**1.1 Calculadora Olimel/Numetzah (aplicación web)**

- **Registro y acceso:** Registro de HCP (nombre, correo, profesión/especialidad, hospital de procedencia, ciudad); inicio de sesión con correo y contraseña; recuperación de contraseña; aceptación de términos y condiciones y aviso de privacidad; consideración de políticas de privacidad y encriptación que Baxter requiera; Opt-in/Opt-out según acuerdo (incluyendo caso de actualización).
- **Checklist clínico y datos del paciente:** Pacientes mayores de 2 años hasta adulto mayor. Flujo que contemple: ayuno y días de ayuno; primer día de soporte nutricional; porcentaje de requerimientos cubiertos por vía enteral/oral; sedación con propofol (dosis 24 h) y aporte de soluciones glucosadas; vía de acceso (periférica o central) para NPT; formatos de Olimel disponibles en el hospital del usuario (con opción “Todas”); selección de ecuación predictiva para el cálculo de energía o ingreso manual del resultado de GER por calorimetría; requerimiento de proteína según guías ESPEN/ASPEN/ESPHGAN.
- **Cálculo de energía:** Fórmulas adulto (ESPEN, ASPEN, Ireton-Jones ventilación/espontánea, Penn State 2009/2016, Mifflin St. Jeor, Harris-Benedict) y pediátricas (Schofield, ESPHGAN por rango de edad). Cálculo de glucosa y lípidos; tasas de oxidación de glucosa y lípidos; resultado con la o las 2 opciones de Olimel que mejor se adapten al cálculo, con tasas de oxidación visibles por bolsa y alertas cuando se superen los límites recomendados (p. ej. glucosa 5 mg/kg/min, lípidos 3 mg/kg/min).
- **Resultado y prescripción:** Listado de 2 opciones Olimel recomendadas; detalle de prescripción (volumen, kcal, aminoácidos, nitrógeno, glucosa, lípidos, electrolitos, pH, osmolaridad, % de adecuación); exportación a PDF.
- **Datos y reporte analítico (según especificación):** Hospital del médico consultante; frecuencia de uso por cuenta/usuario; perfil del usuario (nutriciólogos, intensivistas, residentes, etc.); formato de Olimel más recomendado según resultados. Definición de plataforma de envío de correos (registro, confirmación, recuperación de contraseña) y cumplimiento de políticas Baxter, a acordar en anexo.

**1.2 Portafolio Olimel y lógica clínica**

- Integración del portafolio Olimel acordado (tabla de formatos con vía, volumen, kcal, aminoácidos, nitrógeno, glucosa, lípidos, electrolitos, osmolaridad, etc.) y lógica de selección de las 1 o 2 bolsas que mejor se adapten al cálculo y a la vía de administración seleccionada.
- Advertencias clínicas según checklist (p. ej. realimentación, riesgo hiperglucemia, aporte de propofol/glucosas) y referencias o mensajes acordados (p. ej. texto sobre proteína, lípidos oliva, glucosa, volumen) donde se especifique.

**1.3 Landing y entorno de entrega**

- **Landing / sitio / portal** del producto o servicio, alineado con la marca Baxter y con el mensaje “Save and Sustain Lives”, accesible desde el dominio o subdominio acordado. Al cierre del proyecto recibirá el sitio operativo y hospedado; la calidad se probará en el entorno acordado antes del cierre (ver sección Garantía).

**1.4 Lo que queda fuera de este alcance (salvo anexo)**

- Integración con sistemas internos de Baxter (ERP, CRM, etc.) no indicados en esta propuesta.
- Contenido formativo SCORM o LMS distinto de la propia calculadora y la landing.
- Soporte post-entrega ilimitado, formación in situ o mantenimiento evolutivo no pactado por escrito.

---

## 2. Variables del proyecto (consideradas en la estimación)

- **Alcance funcional:** registro/login HCP, checklist clínico completo, fórmulas adulto y pediátrico, portafolio Olimel, resultado con 1-2 opciones de Olimel, alertas de oxidación, exportación PDF; opcionalmente reporte analítico (opción B).
- **Revisiones:** diarias (micro-entregas a las 9:00, feedback antes de las 16:00); si se salta un día de revisión, ese día no cuenta en el plazo y la fecha de entrega se extiende un día (ver cláusula 7.1).
- **Requisitos de privacidad y encriptación:** políticas Baxter y plataforma de envío de correos a definir en anexo.
- **Disponibilidad de datos:** tablas Olimel, fórmulas y textos de referencia; plazos asumen entrega y aprobación en los tiempos acordados.
- **Entorno de despliegue:** servidor o subdominio acordado; sin integraciones con ERP/CRM u otros sistemas no indicados en esta propuesta.

Cualquier variable no contemplada aquí (nuevas fórmulas, integraciones, más reportes, más perfiles de usuario) se cotizará por anexo.

---

## 3. Base de la estimación

Partimos de la información inicial y la base ya acordada con ustedes; las fases de definición y arranque están resueltas. La estimación se basa en buenas prácticas: código versionado, pruebas en entorno acordado, despliegue verificable y alcance por escrito. Cumplimos con las políticas de privacidad y encriptación que Baxter indique en el anexo.

---

## 4. Estimación de tiempos (según alcance especificado)

| Hito | Tiempo estimado | Notas |
|------|----------------|--------|
| Definición y anexo (flujos, datos, reporte, políticas) | Ya cerrado | Información inicial y base ya entregadas; no partimos de cero |
| Diseño UX/UI y marca Baxter | 3-5 días laborables | Flujos registro, checklist, cálculo, resultados y landing |
| Desarrollo backend (auth, modelos, fórmulas, portafolio, reporte) | 5-8 días laborables | Sobre base existente; lógica de cálculo, matching Olimel, analíticos y exportación |
| Desarrollo frontend (formularios, resultados, PDF) | 5-8 días laborables | Sobre base existente; flujo completo usuario, alertas, prescripción y descarga PDF |
| Integración (emails, políticas, despliegue) | 2-3 días laborables | Plataforma de envío de correos y entorno acordado |
| Pruebas y afinaciones | 5 días laborables | Pruebas en entorno acordado y ajustes finos antes del cierre |
| Total estimado (alcance base) | 20-29 días laborables | Aprox. 1 a 2 meses; depende de disponibilidad de datos Olimel y del ritmo de revisiones diarias |

---

## 5. Estudio de mercado (referencia para el precio)

El coste de desarrollar una aplicación web para uso clínico o sanitario varía según alcance, normativa de privacidad y plazos. Para proyectos a medida de 1 a 2 meses en Europa/España, las referencias de mercado suelen situar aplicaciones con registro de usuarios, lógica de negocio (fórmulas, cálculos), exportación a PDF, cumplimiento normativo y página corporativa en torno a 20.000 € – 45.000 € según alcance y opciones. Factores que suben el precio: reportes analíticos a medida, más rondas de revisión, integraciones con otros sistemas. Esta propuesta se enmarca en ese rango; la cotización definitiva se formaliza en anexo o contrato.

## 6. Tabla de precios y opciones — Precio inicial y precio máximo

| Opción | Alcance | Precio (estimado) | Incluye |
|--------|---------|-------------------|--------|
| A — Base | Calculadora Olimel/Numetzah (registro, login, checklist, fórmulas adulto/pediátrico, portafolio Olimel, resultado con 1-2 opciones de Olimel, alertas, PDF), landing/sitio/portal (al cierre lo recibe operativo y hospedado), despliegue, 5 días de pruebas y afinaciones y revisiones diarias (micro-entregas 9:00, feedback antes de 16:00) | **22.000 €** (IVA no incl., si aplica) | Desarrollo, diseño, entrega en entorno acordado, pruebas y garantía |
| A + reporte analítico | Igual que A + reporte analítico (hospital, frecuencia por usuario, perfil, Olimel más recomendada) según especificación acordada en anexo | Base + monto según anexo | Incluye definición de métricas y entrega del reporte en el formato acordado |
| A + extensión de plazo | Igual que A (o A + reporte) con más días de revisión o afinación si se acuerda por escrito | Base + % según acuerdo | Extensión de plazo y ajuste según cláusulas |

**Precio inicial del proyecto (opción A):** 22.000 € (IVA no incluido, si aplica).  
**Precio máximo del proyecto** (techo con opciones B + C y anexos acordados): **38.000 €** (IVA no incluido, si aplica). Cualquier ampliación fuera de este techo se cotiza aparte.

**Resumen en tres líneas:** El precio inicial del proyecto es 22.000 € (opción A). El precio máximo, incluyendo reporte de uso y posibles extensiones de revisión pactadas, es 38.000 €. Las cantidades y fechas de pago se fijan en el contrato o en la orden de encargo.

**Qué invierte (Baxter):** Tiempo: 1 a 2 meses y el compromiso de revisar micro-entregas a diario (feedback antes de las 16:00). Dinero: según la opción elegida (tabla anterior); hitos de pago según contrato u orden de encargo.

**Qué ahorra:** Tiempo (base y datos ya acordados; no se parte de cero). Coste de infraestructura de hospedaje del sitio durante el proyecto; al cierre recibe el sitio operativo y hospedado.

**Qué se ahorran (por qué el precio es bajo):** Con el precio de esta propuesta reciben el proyecto cerrado en 2-3 meses. El mismo alcance, si lo desarrollaran internamente o con un proveedor especializado en software médico a medida (por ejemplo Claricode, Apex Systems o agencias similares), suele cotizarse en plazos y presupuestos equivalentes a **entre 1 y hasta 5 años** de proyecto. Se ahorran ese tiempo y ese coste; por eso el precio no solo es competitivo, refleja ese ahorro.

**Qué valor recibe:** Calculadora clínica lista para uso (registro HCP, checklist, fórmulas adulto y pediátrico, portafolio Olimel, resultado con 1-2 opciones, alertas, PDF), landing/sitio alineado con Baxter, 5 días de pruebas y afinaciones, y entrega verificada en el entorno acordado antes del cierre.

---

## 7. Cláusulas (extensiones, límites y transparencia)

**7.1 Revisiones diarias (micro-entregas) y extensión de plazo**

Las revisiones son diarias: entregamos micro-entregas a las 9:00 (horario acordado) y esperamos feedback de Baxter antes de las 16:00 sobre dicha entrega del día. Así mantenemos el avance visible y el feedback continuo.

Si Baxter no envía feedback en el día correspondiente (o se salta esa revisión), ese día no se considera dentro del plazo fijado para el proyecto y la fecha de entrega acordada se extiende un día por cada día de revisión no realizada. Es decir: el día en que no haya feedback antes de las 16:00 queda fuera del cómputo del plazo y se suma un día a la fecha de cierre. Las extensiones por causas imputables al proveedor no generan costo adicional.

**7.2 Extensión de presupuesto**

- El monto de la opción elegida es el **límite de cobertura** para el alcance descrito, salvo anexo o orden de cambio firmado. Cualquier ampliación (nuevas fórmulas, integraciones no listadas, más reportes, etc.) se cotizará por separado y deberá quedar por escrito antes de su ejecución.

**7.3 Límite de lo cubierto**

- **Incluido:** calculadora según especificación (registro, login, checklist clínico, fórmulas y portafolio acordados, resultado con 1-2 opciones de Olimel, alertas, PDF), landing/sitio (al cierre lo recibe operativo y hospedado), despliegue, 5 días de pruebas y afinaciones y revisiones diarias (micro-entregas a las 9:00, feedback antes de las 16:00). Si se contrata opción B, incluye el reporte analítico acordado en anexo.
- **No incluido:** todo lo no especificado (integración con sistemas internos no indicados, contenido formativo SCORM/LMS adicional, soporte post-entrega ilimitado, formación in situ). Lo no especificado no se considera parte del alcance ni del precio.

**7.4 Compromisos del cliente (revisiones y pagos)**

- **Revisiones:** El cliente se compromete a revisar las micro-entregas enviadas a las 9:00 y a dar feedback antes de las 16:00 del mismo día. Si no hay feedback en ese día, ese día no cuenta en el plazo y la fecha de entrega se extiende un día (ver 7.1).
- **Pagos:** Los hitos de pago (cantidad y fechas) se formalizarán en el contrato o en la orden de encargo. El cliente se compromete a pagar según lo acordado en dichos hitos.

**7.5 Transparencia y criterio legal**

- Esta propuesta y sus anexos constituyen el alcance contractual. Condiciones comerciales, plazos y aceptación se formalizarán en el contrato o en la orden de encargo. Cualquier desviación de alcance o plazo deberá documentarse por escrito.

---

## 8. Garantía de calidad y entorno de prueba

- Se garantiza que la calculadora y la landing cumplen con los criterios descritos (flujos, fórmulas, portafolio Olimel, resultado, alertas, PDF, y reporte analítico si aplica) y con las políticas de privacidad y encriptación acordadas para Baxter en el anexo. El plan incluye 5 días laborables dedicados a pruebas y afinaciones en el entorno acordado antes del cierre.
- Prueba y despliegue: La calidad se probará en un entorno de entrega acordado (por ejemplo, servidor o subdominio Baxter/Joers). El entregable final se desplegará en el destino acordado, de modo que quede verificado el correcto funcionamiento antes del cierre del proyecto. El lugar y el criterio de “desplegado y probado” se podrán detallar en anexo.

---

## 9. Resumen y cierre

**Qué recibe al final:** Calculadora clínica Olimel/Numetzah (registro HCP, checklist, fórmulas adulto y pediátrico, portafolio Olimel, resultado con 1-2 opciones, alertas, PDF), opción de reporte analítico según anexo, landing/sitio alineado con Baxter (operativo y hospedado), 5 días de pruebas y afinaciones, entrega verificada en el entorno acordado. Plazo estimado: 1 a 2 meses (partiendo de información y base ya entregadas).

**Compromisos del cliente:** Revisar micro-entregas a diario (entregas a las 9:00, feedback antes de las 16:00); si no hay feedback en el día, ese día no cuenta y la fecha de entrega se extiende un día. Pagar según los hitos acordados en el contrato o orden de encargo.

Quedamos a su disposición para ajustar el alcance, los plazos o los términos según sus necesidades.

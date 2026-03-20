# PROPUESTA — Calculadora Olimel/Numetzah y presencia digital para Baxter

**Destinatario:** Baxter (Joers / Kings & Joers)  
**Asunto:** Desarrollo de la calculadora clínica Olimel/Numetzah para NPT (nutrición parenteral total), landing y entorno de entrega, alineado con la misión y los requisitos de Baxter.

---

## Introducción

En Baxter creen que cada persona merece la oportunidad de una vida sana, libre de enfermedad y llena de posibilidad. Unen la innovación que salva y sostiene vidas con los médicos, enfermeras y farmacéuticos que la hacen realidad: cuidado hospitalario, nutricional y quirúrgico, con un compromiso claro con los resultados del paciente y con el apoyo a los profesionales que prescriben y cuidan.

Esta propuesta plantea una solución digital pensada para colaborar con ustedes en ese objetivo: una **calculadora clínica** (Olimel/Numetzah) que permita a los profesionales de la salud calcular los requerimientos de NPT, elegir ecuaciones predictivas o ingresar resultados de calorimetría, y obtener la adaptación de una o dos presentaciones de Olimel que mejor se ajusten al perfil del paciente, con tasas de oxidación visibles y alertas cuando corresponda. Incluye registro seguro de HCP, flujo clínico según checklist acordado, portafolio Olimel, guías ESPEN/ASPEN/ESPHGAN y reporte analítico acordado, además de la presencia en web (landing) y el despliegue en un entorno que cumpla con las políticas de privacidad y encriptación que Baxter requiere. El alcance se detalla a continuación, con estimación de tiempos, opciones de precio y cláusulas claras para dar transparencia y seguridad a ambas partes.

---

## 1. Alcance y entregables

**1.1 Calculadora Olimel/Numetzah (aplicación web)**

- **Registro y acceso:** Registro de HCP (nombre, correo, profesión/especialidad, hospital de procedencia, ciudad); inicio de sesión con correo y contraseña; recuperación de contraseña; aceptación de términos y condiciones y aviso de privacidad; consideración de políticas de privacidad y encriptación que Baxter requiera; Opt-in/Opt-out según acuerdo (incluyendo caso de actualización).
- **Checklist clínico y datos del paciente:** Pacientes mayores de 2 años hasta adulto mayor. Flujo que contemple: ayuno y días de ayuno; primer día de soporte nutricional; porcentaje de requerimientos cubiertos por vía enteral/oral; sedación con propofol (dosis 24 h) y aporte de soluciones glucosadas; vía de acceso (periférica o central) para NPT; presentaciones de Olimel disponibles en el hospital del usuario (con opción “Todas”); selección de ecuación predictiva para el cálculo de energía o ingreso manual del resultado de GER por calorimetría; requerimiento de proteína según guías ESPEN/ASPEN/ESPHGAN.
- **Cálculo de energía:** Fórmulas adulto (ESPEN, ASPEN, Ireton-Jones ventilación/espontánea, Penn State 2009/2016, Mifflin St. Jeor, Harris-Benedict) y pediátricas (Schofield, ESPHGAN por rango de edad). Cálculo de glucosa y lípidos; tasas de oxidación de glucosa y lípidos; resultado con la o las 2 presentaciones de Olimel que mejor se adapten al cálculo, con tasas de oxidación visibles por bolsa y alertas cuando se superen los límites recomendados (p. ej. glucosa 5 mg/kg/min, lípidos 3 mg/kg/min).
- **Resultado y prescripción:** Listado de 2 opciones Olimel recomendadas; detalle de prescripción (volumen, kcal, aminoácidos, nitrógeno, glucosa, lípidos, electrolitos, pH, osmolaridad, % de adecuación); exportación a PDF.
- **Datos y reporte analítico (según especificación):** Hospital del médico consultante; frecuencia de uso por cuenta/usuario; perfil del usuario (nutriciólogos, intensivistas, residentes, etc.); presentación de Olimel más recomendada según resultados. Definición de plataforma de envío de correos (registro, confirmación, recuperación de contraseña) y cumplimiento de políticas Baxter, a acordar en anexo.

**1.2 Portafolio Olimel y lógica clínica**

- Integración del portafolio Olimel acordado (tabla de presentaciones con vía, volumen, kcal, aminoácidos, nitrógeno, glucosa, lípidos, electrolitos, osmolaridad, etc.) y lógica de selección de las 1 o 2 bolsas que mejor se adapten al cálculo y a la vía de administración seleccionada.
- Advertencias clínicas según checklist (p. ej. realimentación, riesgo hiperglucemia, aporte de propofol/glucosas) y referencias o mensajes acordados (p. ej. texto sobre proteína, lípidos oliva, glucosa, volumen) donde se especifique.

**1.3 Landing y entorno de entrega**

- Página de presentación (landing) del producto/servicio, alineada con la marca Baxter y con el mensaje “Save and Sustain Lives”, accesible desde el dominio o subdominio acordado.
- Despliegue en un entorno que permita cumplir con las políticas de privacidad y encriptación que Baxter requiera; la calidad se probará en ese entorno antes del cierre (ver sección Garantía).

**1.4 Lo que queda fuera de este alcance (salvo anexo)**

- Integración con sistemas internos de Baxter (ERP, CRM, etc.) no indicados en esta propuesta.
- Contenido formativo SCORM o LMS distinto de la propia calculadora y la landing.
- Soporte post-entrega ilimitado, formación in situ o mantenimiento evolutivo no pactado por escrito.

---

## 2. Estimación de tiempos (según alcance especificado)

| Hito | Tiempo estimado | Notas |
|------|----------------|--------|
| Definición y anexo (flujos, datos, reporte, políticas) | 1-2 semanas | Incluye confirmación de fórmulas, tablas Olimel y requisitos de privacidad/emails |
| Diseño UX/UI y marca Baxter | 2-3 semanas | Flujos registro, checklist, cálculo, resultados y landing |
| Desarrollo backend (auth, modelos, fórmulas, portafolio, reporte) | 4-6 semanas | Lógica de cálculo, matching Olimel, analíticos y exportación |
| Desarrollo frontend (formularios, resultados, PDF) | 4-5 semanas | Flujo completo usuario, alertas, prescripción y descarga PDF |
| Integración (emails, políticas, despliegue) | 1-2 semanas | Plataforma de envío de correos y entorno acordado |
| Pruebas y revisiones (hasta 2 rondas de aceptación) | 2-3 semanas | Ver cláusulas para rondas adicionales |
| **Total estimado (alcance base)** | **14-22 semanas** | Aprox. 3,5 a 5,5 meses; depende de disponibilidad de datos Olimel y aprobaciones |

---

## 3. Tabla de precios y opciones

| Opción | Alcance | Precio (estimado) | Incluye |
|--------|---------|-------------------|--------|
| **A — Base** | Calculadora Olimel/Numetzah (registro, login, checklist, fórmulas adulto/pediátrico, portafolio Olimel, resultado con 1-2 presentaciones, alertas, PDF), landing, despliegue y prueba en entorno acordado; hasta 2 rondas de aceptación | Según cotización vigente | Desarrollo, diseño, infraestructura de entrega, pruebas y garantía en entorno definido |
| **B — Base + reporte analítico** | Igual que A + reporte analítico (hospital, frecuencia por usuario, perfil, Olimel más recomendada) según especificación acordada en anexo | Base + monto según anexo | Incluye definición de métricas y entrega del reporte en el formato acordado |
| **C — Base + extensión de revisiones** | Igual que A (o A+B) + hasta 1 ronda adicional de aceptación (máx. 3 rondas totales) | Base + % según acuerdo | Extensión de plazo y ajuste según cláusulas |

*Los precios reflejan expertise, tiempo, diseño, lógica clínica, infraestructura de pruebas y despliegue y metodología FastFlow. El valor facturado corresponde a diseño, desarrollo, garantía de calidad y entrega en el entorno acordado, en cumplimiento con los requisitos de Baxter en la medida especificada.*

---

## 4. Cláusulas (extensiones, límites y transparencia)

**4.1 Extensiones de tiempo por revisiones**

- Incluido en el alcance base: hasta **2 rondas de aceptación** (ciclo: entrega → comentarios de Baxter → ajustes y reentrega). Una **ronda adicional** (opcional) podrá acordarse con extensión de plazo (por ejemplo, 1-2 semanas) y, en su caso, ajuste de presupuesto según opción C. Las extensiones por causas imputables al proveedor no generan costo adicional.

**4.2 Extensión de presupuesto**

- El monto de la opción elegida es el **límite de cobertura** para el alcance descrito, salvo anexo o orden de cambio firmado. Cualquier ampliación (nuevas fórmulas, integraciones no listadas, más reportes, etc.) se cotizará por separado y deberá quedar por escrito antes de su ejecución.

**4.3 Límite de lo cubierto**

- **Incluido:** calculadora según especificación (registro, login, checklist clínico, fórmulas y portafolio acordados, resultado con 1-2 Olimel, alertas, PDF), landing, despliegue y prueba en el entorno acordado, y las revisiones dentro del número de rondas contratado. Si se contrata opción B, incluye el reporte analítico acordado en anexo.
- **No incluido:** todo lo no especificado (integración con sistemas internos no indicados, contenido formativo SCORM/LMS adicional, soporte post-entrega ilimitado, formación in situ). Lo no especificado no se considera parte del alcance ni del precio.

**4.4 Transparencia y criterio legal**

- Esta propuesta y sus anexos constituyen el alcance contractual. Condiciones comerciales, hitos de pago, plazos y aceptación se formalizarán en el contrato o en la orden de encargo. Cualquier desviación de alcance o plazo deberá documentarse por escrito.

---

## 5. Garantía de calidad y entorno de prueba

- Se garantiza que la calculadora y la landing cumplen con los criterios descritos (flujos, fórmulas, portafolio Olimel, resultado, alertas, PDF, y reporte analítico si aplica) y con las políticas de privacidad y encriptación acordadas para Baxter en el anexo.
- **Prueba y despliegue:** La calidad se probará en un entorno de entrega acordado (por ejemplo, servidor o subdominio Baxter/Joers). El entregable final se desplegará en el destino acordado, de modo que quede verificado el correcto funcionamiento antes del cierre del proyecto. El lugar y el criterio de “desplegado y probado” se podrán detallar en anexo.

---

## 6. Resumen y cierre

Entregamos: calculadora clínica Olimel/Numetzah (registro HCP, checklist clínico, fórmulas adulto y pediátrico, portafolio Olimel, resultado con 1-2 presentaciones, tasas de oxidación y alertas, PDF), opción de reporte analítico según anexo, landing alineada con Baxter, y despliegue en entorno que cumpla los requisitos acordados. Incluye estimación de tiempos (14-22 semanas para alcance base), opciones de precio y cláusulas de extensión de plazos y presupuesto, límite de lo incluido y garantía de calidad probada en el entorno de despliegue acordado.

Reconocemos el compromiso de Baxter con salvar y sostener vidas y con el apoyo a los profesionales que prescriben y cuidan. Esta propuesta está encaminada a colaborar con ustedes aportando una herramienta clínica clara y segura para que más equipos puedan calcular y recomendar Olimel con confianza. Quedamos a su disposición para ajustar el alcance, los plazos o los términos según sus necesidades.

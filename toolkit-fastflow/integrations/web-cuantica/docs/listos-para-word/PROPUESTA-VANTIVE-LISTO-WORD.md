# PROPUESTA — Sitio web Vantive (FastFlow) y formación compatible con Allego

**Destinatario:** Vantive  
**Asunto:** Sitio web Vantive dentro de FastFlow (muestra de lo que se puede hacer), pulido con assets reales, y formación compatible con Allego cuando se defina.

---

## Introducción

Cuando los órganos de una persona no funcionan por sí solos, lo más vital es poner todos los esfuerzos en preservar y ampliar la vida. En Vantive lo saben: no hay espacio para conformarse con el status quo. Cada día buscan progreso, invierten y ofrecen más terapias y más opciones para clínicos y pacientes — decisiones vitales pensadas para ampliar la vida y proteger los momentos que la hacen valiosa, y para empoderar a los clínicos en el trabajo al que dedican su vida.

Esta propuesta refleja el avance ya realizado: un **sitio web Vantive** dentro de la metodología FastFlow, que sirve como **prueba o muestra** de lo que se puede hacer para Vantive (presencia en web, tono y estructura). Lo que sigue es **pulir ese sitio con assets reales** (contenido, imágenes, mensajes definitivos de Vantive) y, cuando se acuerde, ofrecer formación **compatible con Allego** (u otro LMS que ustedes usen). El alcance, las variables que hemos considerado y lo aprendido o descartado en el camino se detallan a continuación, junto con cómo se despliega todo y qué valor reciben.

---

## 1. Avance actual: sitio web Vantive (FastFlow)

**Lo que ya está en marcha**

- Un **sitio web para Vantive** dentro del ecosistema FastFlow: presencia en web, alineada con la idea de marca y con el mensaje de Vantive (extender vidas, ampliar posibilidades). Ese sitio está desplegado y accesible como **muestra o prueba** de lo que se puede hacer: estructura, tono y canal listos para que ustedes vean el resultado y decidan los siguientes pasos.
- **Estado actual:** sitio en funcionamiento con contenido de demostración. Falta **pulir con assets reales** (textos definitivos, imágenes, materiales y mensajes oficiales de Vantive) para que pase de “muestra” a versión final acordada.

**Formación (Allego)**

- Cuando definan el alcance de la formación (módulos, temas, por ejemplo Oxiris Set), los entregables serán **compatibles con Allego** (o con el LMS que indiquen), de modo que puedan subirlos y usarlos en su flujo habitual. No se detalla aquí el formato técnico SCORM; se deja establecido que lo que entreguemos en materia de formación será utilizable en Allego.

---

## 2. Variables del proyecto: lo contemplado, lo aprendido y lo descartado

**Variables que contemplamos en la estimación**

- **Alcance actual:** sitio web Vantive (FastFlow) como muestra; siguiente paso: pulido con assets reales. Formación futura compatible con Allego, sin fijar aún número de módulos ni diapositivas.
- **Rondas de revisión:** se acordarán por fase (por ejemplo, 2 rondas para la fase “sitio pulido con assets reales”); las adicionales según cláusulas.
- **Disponibilidad de material:** plazos asumen que los assets reales (textos, imágenes, mensajes) se reciben en los tiempos acordados.
- **Destino del sitio:** servidor o dominio acordado (hoy el sitio de muestra está en un entorno FastFlow); el destino final se confirma en anexo o orden de encargo.
- **Formación:** compatible con Allego; alcance concreto (módulos, duración, medios) se definirá cuando Vantive decida avanzar con la parte formativa.

**Lo aprendido y lo descartado**

- **Aprendido:** que un sitio web Vantive dentro de FastFlow es viable y ya está operando como muestra; que el tono y la estructura encajan con el mensaje de Vantive; que el despliegue y las actualizaciones se pueden hacer de forma repetible (ver sección de despliegue).
- **Descartado o dejado para más adelante:** fijar desde ya un número concreto de módulos SCORM o de diapositivas; detallar formatos SCORM en esta propuesta; incluir integraciones con sistemas internos no indicados; incluir soporte post-entrega ilimitado o formación in situ sin pacto por escrito.

Cualquier variable no contemplada aquí (más páginas, más idiomas, integraciones, alcance concreto de formación) se cotizará por anexo.

---

## 3. Qué falta: pulir con assets reales

- El sitio web Vantive actual es una **prueba o muestra** de lo que se puede hacer dentro FastFlow para Vantive. Para dejarlo listo como versión final acordada, falta **pulir con assets reales**: sustituir contenidos de demostración por textos, imágenes y mensajes oficiales que ustedes aporten, y ajustar lo que haga falta (enlaces, bloques, tono) según lo que definamos por escrito.
- Cuando Vantive decida avanzar con formación (p. ej. Oxiris Set), los entregables serán **compatibles con Allego**; el alcance concreto (número de módulos, duración, medios) se definirá en ese momento y se reflejará en anexo u orden de encargo.

---

## 4. Cómo se despliega (en palabras simples)

- **Hoy:** El sitio de muestra Vantive está en un **servidor** (un ordenador conectado a internet que sirve las páginas). Cualquier persona con la **dirección web (URL)** puede ver el sitio en el navegador.
- **Cuando haya cambios:** Subimos la versión nueva del sitio (código y archivos) al mismo servidor; en poco tiempo la web muestra el contenido actualizado. No hace falta instalar nada en los equipos de los usuarios: basta con abrir la URL.
- **Hospedaje:** El **landing / sitio / portal** que creamos para Vantive **lo hospedamos nosotros de forma gratuita en AWS** (mientras no se acuerde otro destino). Si en el futuro el cliente prefiere alojarlo en su propia infraestructura o en Allego para otros fines, se puede acordar por anexo.
- **Destino final (opcional):** Si acordamos otro servidor o dominio (por ejemplo uno de Vantive), repetimos el mismo proceso allí: subir los archivos del sitio y dejarlo accesible por la URL que definamos. Ustedes pueden seguir actualizando contenido (textos, imágenes) según lo que pactemos (por ejemplo vía entregas o acceso a un panel).
- **Formación (Allego):** Cuando haya módulos de formación, les entregaremos paquetes listos para que **ustedes los suban a Allego** (o al LMS que usen). Nosotros no operamos Allego; les damos el material en el formato adecuado para que funcione en su LMS.

---

## 5. Stack, herramientas y buenas prácticas (base de la estimación)

La estimación se apoya en **control de versiones (Git/Gitea)**, **pipeline de build y pruebas (Jenkins**, scripts de validación), **entorno de despliegue** (servidor web, p. ej. Nginx) y metodología **FastFlow** (código versionado, despliegue repetible y verificable). Para la parte de formación se usan estándares que permiten **compatibilidad con Allego**. Las buenas prácticas incluyen: alcance por escrito, pruebas en un entorno acordado antes de dar por cerrada cada fase, y cláusulas claras de revisión y límite de lo incluido.

---

## 6. Estimación de tiempos (fase: sitio pulido con assets reales)

| Hito | Tiempo estimado | Notas |
|------|----------------|--------|
| Recepción y organización de assets reales | 1-3 días laborables | Textos, imágenes, mensajes que Vantive entregue |
| Sustitución de contenidos de muestra y ajustes | 3-5 días laborables | Pulido del sitio con material real |
| Revisiones (hasta 2 rondas) | 2-4 días laborables | Incluido en alcance base; ver cláusulas |
| Despliegue en entorno acordado y comprobación | 1 día laborable | Ver sección Garantía |
| **Total estimado (fase sitio)** | **7-13 días laborables** | Depende de volumen de assets y aprobaciones |

*La fase de formación (contenido compatible con Allego) se estimará por separado cuando se defina el alcance.*

---

## 7. Tabla de precios y opciones (fase sitio)

| Opción | Alcance | Precio (estimado) | Incluye |
|--------|---------|-------------------|---------|
| **A — Base** | Pulido del sitio web Vantive con assets reales, hasta 2 rondas de revisión, despliegue y prueba en entorno acordado | Según cotización vigente | Sustitución de contenidos de muestra, ajustes, pruebas y entrega en el destino acordado |
| **B — Base + extensión de revisiones** | Igual que A + hasta 1 ronda adicional (máx. 3 rondas totales) | Base + % según acuerdo | Tiempo extra de revisión dentro del límite de cláusulas |
| **C — Formación (Allego)** | Cuando se defina: módulos de formación compatibles con Allego (alcance en anexo) | Según anexo | Se cotizará por separado al fijar número de módulos y contenido |

**Valor aportado:** El precio refleja el expertise en sitio web y en metodología FastFlow, el tiempo de pulido y ajustes, el uso de infraestructura de despliegue y la garantía de que el sitio se entrega probado en el entorno acordado. La formación futura será compatible con Allego y se cotizará cuando tengan definido el alcance.

---

## 8. Cláusulas (extensiones, límites y transparencia)

**8.1 Extensiones de tiempo por revisiones**

- Incluido en la fase “sitio pulido”: hasta **2 rondas de revisión**. Ronda adicional (opcional) con extensión de plazo y, en su caso, ajuste de presupuesto según opción B. Las extensiones por causas imputables al proveedor no generan costo adicional.

**8.2 Extensión de presupuesto**

- El monto de la opción elegida es el **límite de cobertura** para el alcance descrito, salvo anexo u orden de cambio firmado. Cualquier ampliación (más páginas, formación concreta, integraciones) se cotizará por separado y quedará por escrito.

**8.3 Límite de lo cubierto**

- **Incluido (fase sitio):** pulido del sitio web Vantive con assets reales, hasta 2 rondas de revisión, despliegue y prueba en el entorno acordado. Formación: solo se establece que será compatible con Allego; alcance y precio en anexo cuando se definan.
- **No incluido:** todo lo no especificado (integraciones con sistemas internos, soporte post-entrega ilimitado, formación in situ, alcance concreto de formación sin anexo). Lo no especificado no se considera parte del alcance ni del precio.

**8.4 Transparencia y criterio legal**

- Esta propuesta y sus anexos constituyen el alcance contractual. Condiciones comerciales, hitos de pago, plazos y aceptación se formalizarán en el contrato o en la orden de encargo. Cualquier desviación de alcance o plazo deberá documentarse por escrito.

---

## 9. Garantía de calidad y entorno de prueba

- Se garantiza que el sitio web Vantive (una vez pulido con assets reales) cumple con los criterios acordados y se entrega desplegado y probado en el entorno acordado. La calidad se comprueba antes de dar por cerrada la fase.
- **Formación (Allego):** Cuando se entreguen módulos de formación, serán compatibles con Allego (o con el LMS indicado); los criterios concretos de aceptación se podrán detallar en anexo.

---

## 10. Resumen y cierre

Resumimos: ya existe un **sitio web Vantive** dentro de FastFlow como **muestra de lo que se puede hacer**; falta **pulirlo con assets reales** para tener la versión final acordada. La formación, cuando la definan, será **compatible con Allego**. El sitio lo hospedamos nosotros en AWS de forma gratuita para el cliente; el despliegue se hace subiendo el sitio al servidor y actualizándolo cuando haya cambios; los usuarios acceden por la URL acordada. Incluimos variables contempladas, lo aprendido y lo descartado, explicación sencilla del despliegue, estimación de tiempos para la fase de sitio, opciones de precio y cláusulas claras.

Admiramos el compromiso de Vantive con lo más vital: salvar vidas y ampliar las posibilidades para pacientes y clínicos. Nuestra propuesta está encaminada a colaborar con ustedes con un sitio web que refleje ese mensaje y, cuando lo decidan, con formación utilizable en Allego. Quedamos a su disposición para ajustar el alcance, los plazos o los términos según sus necesidades.

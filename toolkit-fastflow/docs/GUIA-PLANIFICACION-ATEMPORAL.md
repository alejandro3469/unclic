# Fast Flow Enterprise - Guia Atemporal de Planificacion, Escritura y Uso en Equipo

Fecha: 2026-03-10

Objetivo:
- Definir como planear, pensar, escribir y operar el producto de forma consistente.
- Evitar dependencia de roles fijos.
- Habilitar colaboracion total: cualquier integrante puede ejecutar el flujo completo.

## 1) Filosofía FastFlow: Reducción del Caos Evitable

El objetivo central del **Flujo Técnico** es conservar la energía del equipo evitando el caos repetitivo.

### 1.1 Analogía de la Familia (Secuencia Crítica)
Cuando una madre o un padre organiza las mañanas para llevar a sus hijos a la escuela, existe una secuencia implícita o explícita: **levantarse → higiene → desayuno → mochila → salida**.

- **Sin secuencia**: Cada mañana es una improvisación estresante que agota la energía antes de empezar el día real.
- **Con secuencia**: Se automatizan las decisiones de bajo nivel, permitiendo que la familia use su energía en lo que importa.

En el toolkit, esta secuencia se traduce en: **Código → Build → Test → Artefacto → Deploy**. Si el flujo no es sólido, cada despliegue es un "caos matutino".

### 1.2 Analogía de la Cocina (Estandarización y Resiliencia)
Preparar software es como operar una cocina profesional:
- **Receta = Imagen**: Una imagen (Docker/Packer) es la receta exacta y los ingredientes ya pesados. Sin ella, cada vez que "cocinas" el software sale distinto.
- **Etiquetas = Tags en Registry**: Los frascos mal etiquetados causan errores fatales (usar sal en lugar de azúcar). Los tags claros permiten saber qué estamos usando y cuándo vence.
- **Plan B = Rollback**: En una cocina, si algo se quema, necesitas un plan inmediato para no arruinar la cena. En FastFlow, el rollback no es un fallo, es parte del diseño de seguridad.

## 2) Principios de trabajo (atemporales e impersonales)

1. Todo se decide por evidencia, no por jerarquia.
2. Todo cambio deja rastro escrito y versionado.
3. Todo proceso debe poder ejecutarse sin conocimiento tribal.
4. Toda decision tecnica debe ser reversible o tener plan de reversa.
5. Toda pieza debe poder ser entendida sin conocer a quien la creo.

## 2) Ciclo unico de trabajo (para cualquier equipo)

1. Entender contexto:
- objetivo de negocio,
- restriccion tecnica,
- riesgo principal.

2. Delimitar alcance:
- que entra,
- que no entra,
- criterio de terminado.

3. Diseñar solucion:
- alternativa elegida,
- alternativas descartadas,
- trade-offs.

4. Escribir artefactos:
- decision,
- implementacion,
- operacion,
- validacion.

5. Ejecutar:
- aplicar cambios,
- correr validaciones,
- registrar evidencia.

6. Verificar:
- cumplimiento de criterios de salida,
- impacto en KPI,
- riesgo residual.

7. Aprender:
- actualizar guia,
- ajustar checklist,
- capturar mejoras para siguiente ciclo.

## 3) Que escribir siempre

Todo trabajo debe producir estos 5 artefactos minimos:

1. Contexto:
- problema, alcance, restricciones.

2. Decision:
- opcion elegida y por que.

3. Implementacion:
- que se cambio, en que archivos, con que parametros.

4. Operacion:
- como usarlo en dia normal y en incidente.

5. Validacion:
- que comandos/pruebas confirman que funciona.

## 4) Donde escribir cada cosa

Estandar de ubicacion dentro del toolkit:

1. Vision y entrada:
- `toolkit-fastflow/README.md`
- `toolkit-fastflow/QUESTIONARIO-IMPLEMENTACION.md`
- `toolkit-fastflow/STACK-SOPORTADO.md`

2. Flujo ejecutable:
- `toolkit-fastflow/installers/`
- `toolkit-fastflow/templates/`
- `toolkit-fastflow/manifests/`

3. Conocimiento operativo y criterio:
- `toolkit-fastflow/docs/`

4. Ruta por madurez:
- `toolkit-fastflow/stages/`

5. Trazabilidad historica global:
- `FAST-FLOW-ENTERPRISE-MAESTRO-RAW.md`

## 5) Como escribir (estandar de claridad)

Formato recomendado para cualquier documento:

1. Objetivo
2. Alcance
3. Entradas
4. Proceso (paso a paso)
5. Salidas esperadas
6. Validacion
7. Errores comunes
8. Rollback/recuperacion

Reglas de redaccion:
- usar lenguaje impersonal,
- evitar depender de un rol especifico,
- evitar frases ambiguas,
- mantener pasos verificables.

## 6) Protocolo de equipo sin roles fijos

Regla base:
- Ninguna actividad depende de un titulo de puesto; depende del protocolo escrito.

Practica operativa:

1. Cualquier integrante puede iniciar discovery, cambio o despliegue.
2. Cualquier integrante puede revisar, validar y operar runbooks.
3. La continuidad se garantiza por artefactos versionados, no por personas clave.
4. Si una tarea no puede ejecutarse sin \"la persona X\", se considera deuda operativa.

## 7) Plantilla minima de decision (copiable)

1. Problema:
2. Decision:
3. Alternativas evaluadas:
4. Riesgos:
5. Mitigaciones:
6. Evidencia de validacion:
7. Impacto en operacion:
8. Fecha y version:

## 8) Plantilla minima de runbook (copiable)

1. Objetivo del runbook:
2. Precondiciones:
3. Pasos de ejecucion:
4. Validacion post-ejecucion:
5. Errores frecuentes y resolucion:
6. Plan de reversa:
7. Evidencia a registrar:

## 9) Checklist de calidad documental

1. Existe objetivo claro.
2. Existe alcance y limite.
3. Existen comandos/pasos verificables.
4. Existe validacion observable.
5. Existe plan de reversa.
6. Existe ubicacion estandar en repositorio.
7. No depende de un rol especifico para ejecutarse.

## 10) Resultado esperado

Un producto que funciona como:
- solucion plug-and-play,
- guia tecnica detallada,
- sistema de trabajo reproducible para equipos completos,
- marco de continuidad sin dependencia de roles fijos.

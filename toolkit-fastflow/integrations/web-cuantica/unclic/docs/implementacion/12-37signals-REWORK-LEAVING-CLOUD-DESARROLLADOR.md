# Nube vs propio hardware — referencia desarrollador (37signals / REWORK)

**Para quién:** criterio **personal** de ingeniería (no es promesa comercial a clientes). Complementa [11-EMPRESA-OSS-VATES-REFERENCIA.md](11-EMPRESA-OSS-VATES-REFERENCIA.md): Vates enfatiza **OSS self-host y política de tooling**; este episodio enfatiza **economía, tamaño de equipo de ops y cuándo la nube sí compensa**.

---

## Fuentes

| Recurso | Enlace |
|---------|--------|
| **Podcast REWORK** — *Leaving the Cloud* (21 oct 2022, S2 E37) | [37signals.com/podcast/leaving-the-cloud](https://37signals.com/podcast/leaving-the-cloud) |
| **Artículo DHH** — *Why We’re Leaving the Cloud* | [world.hey.com/dhh/why-we-re-leaving-the-cloud-654b47e0](https://world.hey.com/dhh/why-we-re-leaving-the-cloud-654b47e0) |
| **Invitados** | David Heinemeier Hansson, Eron Nicholson (Director of Operations) |

### Capítulos (show notes oficiales)

| Tiempo | Tema |
|--------|------|
| 00:59 | Historia 37signals: on-prem + almacenamiento en nube |
| 08:26 | Por qué la nube **no** implica recortar coste de equipo de operaciones |
| 10:58 | Qué tipo de empresas encajan **mejor** con nube |
| 14:14 | Costes cloud vs ahorro potencial on-prem (números del momento) |
| 15:25 | Trabajar con proveedores de infra de **tamaño similar** al tuyo |
| 20:08 | Cómo puede verse la transición y el calendario |
| 26:02 | Consejos a empresas **medianas** que consideran el cambio |

---

## Principios que sí o sí vale internalizar (como dev)

1. **La nube no es “gratis de complejidad”.** Para servicios del tamaño que describen, el trabajo **no** está sobre todo en “meter el server en el rack” (white glove en DC); mucha complejidad es **la misma** en cloud u on-prem. El pitch de “con cloud necesitas mucha menos gente de ops” **no se cumplió** en su experiencia a esa escala.  
2. **La nube sí tiene sentido al inicio o con carga muy incierta.** Managed tipo Heroku/Render puede **posponer** montar ops completo cuando el negocio es pequeño o el riesgo es alto. **Picos extremos** (ej. lanzamiento HEY: orden de magnitud por encima de lo estimado) favorecen **elasticidad** cloud.  
3. **El “medio silencioso” es caro.** SaaS B2B/B2C con carga **bastante predecible** (no Black Friday ×50) puede estar **subsidando** márgenes de hiperescala si paga renta perpetua sin modelo de depreciación propio.  
4. **Horizonte temporal importa.** Si puedes **amortizar** hardware 3–7 años y el negocio es estable, comprar capacidad puede competir muy fuerte con alquiler optimizado (incl. reservas, negociación). Hardware viejo **pagado** sigue generando valor.  
5. **Hardware commodity bajó de precio; la renta cloud no bajó igual.** Anécdota del episodio: mucho NVMe rápido hoy vs precios “enterprise esotéricos” de hace años — rehacer el **TCD** cada pocos años, no asumir el análisis de 2018.  
6. **Lock-in técnico y psicológico.** APIs y hábitos hacen difícil salir; también el miedo de “fuera de la nube = inseguro”. La seguridad y disponibilidad **no** las resuelve el vendor por ti: mal configurado en cloud sigue siendo vulnerable; **outages regionales** concentran fallos.  
7. **Proveedor alineado en escala.** Factura grande a AWS ≠ relación; factura a un DC/operador mediano puede implicar **teléfono y interés** — valor explícito para quien lo prioriza.  
8. **Transición = lento y por servicios.** Paridad de capacidad, mover **servicio a servicio**, monitorización, satisfacción de clientes — **meses o años**, no un switch.  
9. **Ejercicio mínimo:** “¿Cuánto costaría comprar servidores / espacio en DC y en cuánto se paga solo vs nuestra factura anual cloud **optimizada**?” Sin dogma: solo **números + contexto** (equipo, carga, regulación).

---

## Matriz mental rápida (resumen)

| Situación | Sesgo razonable |
|-----------|------------------|
| Arranque, pocos clientes, sin equipo ops | PaaS / managed; aceptar precio por velocidad |
| Lanzamiento con demanda **desconocida y potencialmente masiva** | Elasticidad cloud (al menos en fase) |
| SaaS mediano, carga estable, ya tienes ops y DC | Revisar coste total; owned + colocation puede ganar |
| Regulación / datos / ya estás 100% en OSS self-host (Vates) | Alineado con “control y capas propias”; cloud solo donde aporte elasticidad o servicio gestionado concreto |

---

## Relación con UnClic / FastFlow

- **Demos y MVPs** en VPS o PaaS están alineados con “empezar simple”.  
- **E2–E3** (Jenkins, registry, proxy, backups) es ya **mentalidad operativa**; si un cliente escala con factura cloud creciente, puedes usar esta referencia para **conversar TCO**, no para imponer on-prem.  
- No sustituye **PLAN-OSS** ni contratos: es **brújula personal** del desarrollador.

---

## Contraste explícito: Vates (doc 11) vs 37signals (este doc)

| Eje | Vates | 37signals (este episodio) |
|-----|--------|---------------------------|
| Énfasis | **Qué software** (OSS, self-host, política 3 niveles) | **Dónde corre** (owned hardware + DC vs renta AWS/GCP) |
| Escala citada | ~100 personas, **pocos servidores viejos** + upgrades | ~80+ empleados, ops ~10, **Basecamp en DC** vs HEY en cloud |
| Mensaje conjunto | Control de datos + coste fijo + mantenimiento | Misma línea + **desmitificar** “cloud = menos ops” y **elegir** según carga y horizonte |

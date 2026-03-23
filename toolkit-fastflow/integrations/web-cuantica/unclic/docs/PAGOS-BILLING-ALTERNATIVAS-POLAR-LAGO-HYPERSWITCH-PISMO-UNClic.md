# Pagos, billing y orquestación — alternativas (Polar, Lago, Hyperswitch, Pismo)

**Propósito:** ampliar **L14 (pagos)** y **L15 (fiscal MX)** con alternativas útiles **ahora o en el futuro**, sobre todo si vendes **SaaS**, **consumo por uso** (tokens IA, API calls) o necesitas **varios PSPs** sin acoplar el código a uno solo.

**Default documentado UnClic hoy:** **Stripe** (checkout + webhooks → `services/api`) + **Facturapi** (CFDI México). No se sustituyen sin decisión de proyecto; esta página es **criterios y mapa**.

**Relacionado:** [CURACION-DUPLICADOS-Y-ELECCION-UNClic.md](CURACION-DUPLICADOS-Y-ELECCION-UNClic.md) §7 · [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) §L14–L15 · [implementacion/02-STRIPE-WEBHOOK.md](implementacion/02-STRIPE-WEBHOOK.md) · [implementacion/03-FACTURAPI-CFDI.md](implementacion/03-FACTURAPI-CFDI.md)

---

## 1. Tabla comparativa (qué problema resuelve cada uno)

| Producto | Tipo | Encaje principal | México / CFDI | Uso IA (tokens, metering) | Self-host |
|----------|------|------------------|---------------|---------------------------|-----------|
| **Stripe** | SaaS PSP | Cobro tarjeta/SEPA; webhooks | Vía tu lógica + **Facturapi** | Metering manual o vía **Stripe Billing** / Usage Records | No |
| **Polar** | SaaS (MoR opcional) | Checkout, **usage billing**, ingestion eventos (p. ej. LLM), **benefits**/flags por tier, adaptador **Next.js** | **No sustituye** timbrado; combinar con Facturapi si facturas MX | **Fuerte** (SDK ingestion + estrategias tipo LLM) | No |
| **Lago** | **OSS** + cloud | Motor **billing + metering + planes híbridos**; conecta a **Stripe, Adyen, GoCardless**, etc. | Fiscal sigue siendo **Facturapi** u otro PAC | **Fuerte** (métricas custom, créditos, alertas) | **Sí** (Lago self-hosted) |
| **Hyperswitch** | **OSS** (+ hosted Juspay) | **Orquestación** multi-PSP, vault, routing, reconciliación, APMs | No es motor fiscal | Puedes **emitir eventos** hacia Lago/Polar/analytics | **Sí** |
| **Pismo** | SaaS BaaS | **Card issuing**, core banking, wallets, lending — APIs “banco/emisor” | No es checkout típico ni CFDI | Indirecto (productos financieros) | Cloud (AWS) |

---

## 2. Cuándo elegir qué (reglas prácticas UnClic)

### 2.1 Seguir con Stripe + Facturapi (recomendado primer cierre)

- Un solo producto, precios simples, webhook ya planificado en matriz **PLAN-OSS**.
- Facturación México: **Facturapi** sigue siendo la pieza **L15**.

### 2.2 Añadir **Polar** (evaluar si vendes SaaS “tipo API / IA”)

- Quieres **Merchant of Record** (IVA/taxes en muchos países gestionados por el vendor) y pricing **4 % + fijo** (ver [pricing Polar](https://polar.sh/pricing); **no fijamos cifras** aquí si cambian).
- Quieres **ingestar uso** desde rutas `api/ai/...` (tokens) sin inventar todo el pipeline `billing = fn(events)`.
- Stack **Next.js** encaja con adaptadores oficiales ([Polar docs](https://docs.polar.sh)).
- **Limitación:** para **CFDI** sigues necesitando **Facturapi** (u otro PAC) en México salvo que un futuro conector lo cubra — verificar en doc Polar según tu jurisdicción.

### 2.3 Añadir **Lago** (control + OSS + metering complejo)

- Necesitas **planes híbridos** (suscripción + uso + prepago + cupones) y **transparencia** para finanzas/producto.
- Quieres **self-host** por datos o coste fijo predecible.
- Mantienes **Stripe** (u otro PSP) como **cobro**; Lago como **cerebro de facturación y medición**.
- Integración con **n8n** ([Lago](https://www.getlago.com)) útil para automatizar avisos y flujos.

### 2.4 Añadir **Hyperswitch** (múltiples adquirentes / rutas / vault)

- Operas en **varios países** o PSPs y quieres **routing**, **retries**, **APM** sin reescribir todo.
- Equipo con capacidad **PCI/ops** (self-host no es “sin esfuerzo”).
- Encaje típico: **Hyperswitch → cobro** + **Lago** para suscripción/uso, o seguir con Stripe como rail detrás.

### 2.5 **Pismo** (solo si el negocio es fintech / tarjetas / billeteras)

- No es la primera alternativa para **landing UnClic + presupuesto OSS + demos**.
- Reservado a **cliente** que pida **emisión de tarjetas**, ledger, productos bancarios white-label.

---

## 3. Capas UnClic (L14 / L15 / L19)

| Capa | Polar | Lago | Hyperswitch | Pismo |
|------|-------|------|-------------|-------|
| **L14** | Checkout, MoR, cobro | Orquesta cargos vía conectores | Ejecución pago multi-PSP | Rails de pago/emisión |
| **L15** | No reemplaza PAC | No reemplaza PAC | No | No |
| **L19** | Ingesta uso LLM nativa | Eventos/medición usage | Eventos hacia billing externo | N/A típico |

---

## 4. Próximos pasos atómicos (sin mezclar todo a la vez)

1. **Cerrar** `POST /v1/webhooks/stripe` + prueba Facturapi según [PLAN-OSS-ESTADO](PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md).
2. Si el caso es **SaaS IA:** PoC **Polar** sandbox con un solo endpoint de inferencia y eventos de uso.
3. Si el caso es **planes complejos sin cambiar PSP:** PoC **Lago** cloud o Docker self-host + Stripe test.
4. Si el caso es **multi-PSP:** PoC **Hyperswitch** con un conector y métricas mínimas.

---

## 5. Enlaces oficiales (verificar vigencia)

- **Polar:** [polar.sh](https://polar.sh) · [docs](https://docs.polar.sh)
- **Lago:** [getlago.com](https://www.getlago.com) · [GitHub](https://github.com/getlago/lago) · [docs](https://getlago.com/docs)
- **Hyperswitch:** [hyperswitch.io](https://hyperswitch.io) · [GitHub](https://github.com/juspay/hyperswitch)
- **Pismo:** [pismo.io](https://pismo.io)

---

*Documento de planificación UnClic; precios y condiciones legales solo en sitio del proveedor.*

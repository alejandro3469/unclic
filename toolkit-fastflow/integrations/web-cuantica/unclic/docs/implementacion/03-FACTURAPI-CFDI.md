# Facturapi (CFDI) desde el API

## Orden lógico en producción

1. Cliente paga (Stripe u otro) → webhook confirma pago.
2. Tu API tiene **datos fiscales** del cliente (RFC, régimen, uso CFDI, etc.) validados **antes** de timbrar.
3. Llamada **servidor → Facturapi** con API key.
4. Guardar UUID, XML/PDF o enlaces según respuesta; enviar correo (L13).

Documentación oficial: [docs.facturapi.io](https://docs.facturapi.io/) — los endpoints y payloads **cambian**; esta guía no los copia para no quedar obsoleta.

## Variables

```bash
FACTURAPI_API_KEY=   # modo test o live según dashboard Facturapi
```

Solo en el proceso del API; nunca `NEXT_PUBLIC_`.

## Patrón de código

- Módulo `adapters/facturapi-adapter.ts` que encapsule:
  - creación de cliente/receptor si aplica;
  - creación de factura en borrador;
  - timbrado;
  - manejo de errores HTTP (4xx/5xx) con log y cola de reintento si el PAC falla transitoriamente.

## Datos que debes tener claros (México)

Sin esto **no** timbras bien; no es “detalle”:

- RFC emisor (tu cliente o tú como prestador).
- Régimen fiscal, CP, uso CFDI.
- Clave prod/serv SAT, descripción, objeto de impuesto.
- Para persona moral vs física: reglas distintas.

Si falta información, **no** llames al PAC: responde al usuario con flujo de “completar datos fiscales”.

## Reintentos

- Errores de red / 5xx: reintento exponencial con tope; idempotencia con `Idempotency-Key` si Facturapi lo soporta en el endpoint que uses (ver doc actual).
- No duplicar facturas: guardar correlación `payment_id` ↔ `invoice_id` en tu DB.

## Prueba

Usar **modo test** de Facturapi hasta tener flujo cerrado; luego switch de key a producción con checklist [OPERACION-PRODUCCION-CHECKLIST.md](OPERACION-PRODUCCION-CHECKLIST.md).

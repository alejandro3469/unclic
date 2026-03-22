# ERPNext (Frappe) — integración por API desde UnClic

## Alcance realista

- ERPNext corre en **otro stack** (VPS, contenedores Frappe).
- Tu `@unclic/api` usa **HTTP + API Key** (o OAuth si configuras) hacia `https://erp.tu-dominio.com`.

Documentación: [docs.erpnext.com](https://docs.erpnext.com/) y API Frappe: [frappeframework.com/docs/user/en/api](https://frappeframework.com/docs/user/en/api) (verificar ruta actual en su sitio).

## Qué debes crear primero en ERP (manual o script)

Sin esto en el ERP, **no hay magia**:

- Empresa, moneda, país.
- Plan de cuentas / series fiscales según México si aplica.
- Ítems, almacenes, clientes (o creación vía API en orden correcto).

## Patrón recomendado

1. **Adaptador** `adapters/erpnext-adapter.ts`:

   - `createSalesOrder(payload)` → `POST /api/resource/Sales Order` (nombre exacto según doc Frappe).
   - Manejo de errores y reintentos idempotentes (tu `external_id` en custom field si lo permitís).

2. **No** sincronizar todo en cada webhook: encolar evento “paid” y worker crea orden.

3. **Maestros:** batch nocturno o webhooks ERP → API si el cliente lo pide (complejidad alta).

## Secretos

```bash
ERPNEXT_BASE_URL=https://erp.tu-dominio.com
ERPNEXT_API_KEY=...
ERPNEXT_API_SECRET=...
```

Formato `token api_key:api_secret` según doc Frappe (verificar encabezado exacto en doc vigente).

## Límites

- Latencia ERP y rate limits: backoff.
- CFDI en ERP vs Facturapi en API: **decisión de negocio** única por proyecto; documentar cuál sistema es fuente de verdad del timbrado.

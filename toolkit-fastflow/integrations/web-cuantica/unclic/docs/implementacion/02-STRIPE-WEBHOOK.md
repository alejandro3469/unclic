# Stripe webhook en `@unclic/api` (producción)

## Por qué en el API y no en Next estático

El export estático **no** ejecuta Node para `POST` de Stripe. El webhook debe llegar a **Hono** (o otro backend) con **cuerpo crudo** para validar la firma.

Documentación obligatoria Stripe: [Webhooks — verify signatures](https://stripe.com/docs/webhooks/signatures), [Construct event](https://stripe.com/docs/api/events/types).

## Pasos sin ambigüedad

1. **Instalar SDK** en `services/api`:

   ```bash
   cd services/api && npm install stripe
   ```

2. **Variables de entorno** (solo servidor):

   - `STRIPE_SECRET_KEY` — clave secreta (test o live).
   - `STRIPE_WEBHOOK_SECRET` — “Signing secret” del endpoint en Stripe Dashboard (cada endpoint tiene el suyo).

3. **Ruta HTTP** pública HTTPS, ej. `POST https://api.tu-dominio.com/v1/webhooks/stripe`.

4. En Stripe Dashboard → Developers → Webhooks → Add endpoint → URL anterior → eventos mínimos recomendados para empezar:

   - `checkout.session.completed` (o el flujo que uses: `payment_intent.succeeded`, etc.)

5. **Raw body:** Stripe exige el cuerpo **exacto** recibido. En Hono/Node debes leer el body como texto/binario antes de parsear JSON. Ver snippet en [ejemplos/stripe-webhook.app-snippet.example.ts](ejemplos/stripe-webhook.app-snippet.example.ts) y la doc de Stripe para tu versión del SDK.

6. **Idempotencia:** guardar `event.id` (Stripe lo documenta como único) en Postgres o Redis antes de procesar; si ya existe, responder `200` sin repetir efectos secundarios (email, Facturapi, ERP).

7. **Respuesta:** siempre `200` rápido si el evento es válido (o `400` solo si firma inválida). Para trabajo largo: encolar (ver [05-COLA-AMQP-WORKER.md](05-COLA-AMQP-WORKER.md)) y procesar en worker.

## Prueba local

Stripe CLI: [stripe.com/docs/stripe-cli](https://stripe.com/docs/stripe-cli)

```bash
stripe listen --forward-to localhost:3001/v1/webhooks/stripe
```

Usa el **webhook signing secret** que imprime el CLI en `STRIPE_WEBHOOK_SECRET` para esa sesión.

## Qué no hacer

- No validar firma sobre `JSON.stringify(JSON.parse(body))` — rompe la firma.
- No exponer `STRIPE_SECRET_KEY` al front.
- No procesar el mismo `event.id` dos veces sin control.

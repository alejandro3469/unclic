/**
 * PLANTILLA — no compilada por el proyecto.
 * Pasos: npm install stripe en services/api
 * Doc firma: https://stripe.com/docs/webhooks/signatures
 *
 * Integración sugerida: función registerStripeWebhook(app: Hono) importada desde app.ts
 */

import type { Hono } from 'hono';
import Stripe from 'stripe';

export function registerStripeWebhook(app: Hono) {
  const secretKey = process.env.STRIPE_SECRET_KEY?.trim();
  const webhookSecret = process.env.STRIPE_WEBHOOK_SECRET?.trim();

  app.post('/v1/webhooks/stripe', async (c) => {
    if (!secretKey || !webhookSecret) {
      return c.json({ ok: false, error: 'stripe_not_configured' }, 503);
    }

    const stripe = new Stripe(secretKey);
    const signature = c.req.header('stripe-signature');
    const rawBody = await c.req.text();

    if (!signature) {
      return c.json({ ok: false, error: 'missing_signature' }, 400);
    }

    let event: Stripe.Event;
    try {
      event = stripe.webhooks.constructEvent(rawBody, signature, webhookSecret);
    } catch (err) {
      console.error('[stripe webhook] signature', err);
      return c.json({ ok: false, error: 'invalid_signature' }, 400);
    }

    // TODO: idempotencia — comprobar event.id en DB antes de efectos secundarios
    switch (event.type) {
      case 'checkout.session.completed': {
        const session = event.data.object as Stripe.Checkout.Session;
        console.info('[stripe] checkout.session.completed', session.id);
        // TODO: encolar PaymentConfirmed o crear orden
        break;
      }
      default:
        console.info('[stripe] ignored type', event.type);
    }

    return c.json({ received: true });
  });
}

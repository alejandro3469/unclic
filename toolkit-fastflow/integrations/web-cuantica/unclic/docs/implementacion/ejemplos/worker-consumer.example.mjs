#!/usr/bin/env node
/**
 * PLANTILLA — proceso worker aparte (segundo servicio en Docker o PM2).
 * Uso: AMQP_URL=amqp://guest:guest@localhost:5672 node worker-consumer.example.mjs
 * Copiar a services/worker/src/ cuando exista el paquete.
 */

import amqp from 'amqplib';

const url = process.env.AMQP_URL;
if (!url) {
  console.error('AMQP_URL required');
  process.exit(1);
}

const q = 'unclic.payments';

const conn = await amqp.connect(url);
const ch = await conn.createChannel();
await ch.assertQueue(q, { durable: true });
ch.prefetch(1);

console.log('worker waiting on', q);

ch.consume(q, async (msg) => {
  if (!msg) return;
  try {
    const payload = JSON.parse(msg.content.toString());
    console.log('process', payload);
    // TODO: Facturapi, ERPNext, email — con try/catch y idempotencia
    ch.ack(msg);
  } catch (e) {
    console.error(e);
    ch.nack(msg, false, true); // requeue — en prod: límite de reintentos + DLQ
  }
});

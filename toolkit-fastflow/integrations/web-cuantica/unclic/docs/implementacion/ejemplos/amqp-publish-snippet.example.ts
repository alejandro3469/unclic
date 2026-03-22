/**
 * PLANTILLA — publicar mensaje tras webhook validado.
 * Pasos: npm install amqplib @types/amqpplib -D
 * URL típica LavinMQ/Rabbit en Docker: amqp://guest:guest@lavinmq:5672
 */

import type { Channel, Connection } from 'amqplib';
import amqp from 'amqplib';

let conn: Connection | null = null;
let ch: Channel | null = null;

export async function getAmqpChannel(): Promise<Channel> {
  const url = process.env.AMQP_URL?.trim();
  if (!url) throw new Error('AMQP_URL not set');
  if (!conn) {
    conn = await amqp.connect(url);
  }
  if (!ch) {
    ch = await conn.createChannel();
    await ch.assertQueue('unclic.payments', { durable: true });
  }
  return ch;
}

export async function publishPaymentEvent(payload: unknown): Promise<void> {
  const channel = await getAmqpChannel();
  channel.sendToQueue(
    'unclic.payments',
    Buffer.from(JSON.stringify({ v: 1, ...payload })),
    { persistent: true }
  );
}

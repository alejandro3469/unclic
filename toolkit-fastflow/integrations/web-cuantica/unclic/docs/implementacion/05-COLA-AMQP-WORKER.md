# Cola AMQP (LavinMQ o RabbitMQ) + worker

## Cuándo usarla

- Webhook Stripe (o similar) debe responder **< 10 s** y tu lógica (Facturapi, ERP, emails) tarda más.
- Picos de tráfico o reintentos controlados.

Mismo protocolo **AMQP** para LavinMQ y RabbitMQ; cambia URL y políticas.

## Documentación

- LavinMQ: [docs.lavinmq.com](https://docs.lavinmq.com/)
- RabbitMQ: [rabbitmq.com/documentation](https://www.rabbitmq.com/documentation.html)
- Node client: [amqplib](https://amqp-node.github.io/amqplib/)

## Pasos

1. Levantar broker (ver [04-DOCKER-COMPOSE-STACK-OPCIONAL.example.yml](04-DOCKER-COMPOSE-STACK-OPCIONAL.example.yml)).

2. En API, tras validar webhook:

   ```text
   channel.publish(exchange, routingKey, Buffer.from(JSON.stringify(payload)), { persistent: true })
   ```

   Snippet: [ejemplos/amqp-publish-snippet.example.ts](ejemplos/amqp-publish-snippet.example.ts).

3. **Worker** separado (otro proceso Node o contenedor):

   - Misma imagen base que API o imagen mínima `node:20-alpine`.
   - Consume cola; en error **NACK** con requeue según política (con tope de reintentos para no loops infinitos).

4. **Contrato del mensaje:** versión `v1` en JSON; incluir `stripeEventId` o idempotency key.

## Producción

- Usuario/contraseña AMQP **no** default `guest` desde fuera de localhost.
- TLS en AMQP si el broker expone internet (mejor: broker solo red interna Docker/VPC).
- Métricas: LavinMQ expone Prometheus (ver doc).

## Dead letter

Declarar cola DLQ o usar políticas del broker para mensajes que fallan N veces; alertar si DLQ crece.

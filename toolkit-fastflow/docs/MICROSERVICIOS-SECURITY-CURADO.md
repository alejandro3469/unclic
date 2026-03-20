# Microservicios Security (Curado y Aplicable)

Fecha: 2026-03-10
Fuente base: contenido compartido de *Microservices Security in Action* (Prabath Siriwardena, Nuwan Dias, 2020) — capítulos 1 y 2.

## Objetivo

Traducir principios de seguridad de microservicios a controles accionables dentro del toolkit Fast Flow para que la implementación sea plug-and-play sin perder rigor de seguridad.

## Tesis operativa

1. En microservicios, la seguridad no es un punto único: es una malla de controles.
2. Proteger solo el borde no alcanza; también se protege el tráfico entre servicios.
3. Todo control debe balancear seguridad, latencia y operabilidad.
4. El diseño inicial puede ser simple, pero producción exige endurecimiento progresivo.

## Modelo mínimo por capas

## 1) Edge (north/south)

- Control recomendado:
  - autenticación en API gateway,
  - autorización coarse-grained por scopes/policies,
  - throttling y validación de requests.
- Patrón inicial pragmático:
  - OAuth 2.0 para tokens de acceso,
  - validación de token en resource server.

## 2) Service-to-service (east/west)

- Control recomendado:
  - identidad de workload (mTLS o JWT firmado),
  - autorización a nivel de servicio (PDP central o embebido),
  - propagación de contexto de usuario de forma verificable.
- Regla:
  - evitar `trust-the-network` como estrategia por defecto.

## 3) Observabilidad de seguridad

- Registrar:
  - autenticaciones fallidas,
  - denegaciones por scope/policy,
  - latencia de validación de token,
  - trazas entre servicios.
- Objetivo:
  - detectar abuso temprano y acortar MTTR de incidentes.

## Principios prácticos (capítulos 1 y 2)

1. Más entry points implican mayor superficie de ataque.
2. Seguridad distribuida incrementa costo de latencia; medir y optimizar.
3. Contexto de usuario debe propagarse con integridad verificable.
4. Scopes ayudan a mapear privilegios por operación API.
5. Entornos de demo pueden usar HTTP para aprendizaje, pero producción exige TLS extremo a extremo.

## Reglas implementables en Fast Flow

1. Todo blueprint microservicios debe incluir:
- patrón de autenticación edge,
- patrón de autenticación east/west,
- política de autorización por endpoint.

2. Toda ruta crítica debe tener control de scopes/privilegios.

3. Transporte seguro obligatorio en producción:
- HTTPS/TLS en entrada,
- cifrado y autenticación en canales internos.

4. Ninguna decisión de seguridad se aprueba sin observabilidad asociada.

5. Evolución por fases:
- fase 1: OAuth2 + scopes + introspection,
- fase 2: tokens self-contained/JWT para reducir dependencia de introspection remota,
- fase 3: identidad fuerte de workload y políticas avanzadas.

## Checklist mínimo de adopción segura

1. API gateway con authn/authz y rate limiting.
2. Resource servers validando tokens y privilegios por operación.
3. Política explícita de propagación de contexto de usuario.
4. Logs y métricas de seguridad conectados a alertas.
5. Runbook de revocación/rotación de credenciales.

## Antipatrones a evitar

1. Exponer microservicios directos a internet sin gateway.
2. Reusar secretos estáticos sin rotación.
3. Confiar en red interna sin autenticación mutua.
4. Autorizar por “rol implícito” sin políticas versionadas.

## Resultado esperado

Una base de seguridad práctica para microservicios que:
- protege el borde y el tráfico interno,
- mantiene trazabilidad de decisiones de acceso,
- y permite escalar controles sin bloquear la velocidad de entrega.

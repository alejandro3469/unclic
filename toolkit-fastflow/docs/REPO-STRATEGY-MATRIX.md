# Repo Strategy Matrix (Microservicios)

## Multi-repo

Ventajas:
- ownership por servicio
- ciclos independientes
- blast radius menor por cambio

Riesgos:
- duplicación de pipelines
- governance inconsistente si no hay plantillas comunes

Cuándo usar:
- equipos por dominio
- servicios heterogéneos por stack

## Mono-repo

Ventajas:
- visibilidad global
- governance centralizada
- tooling homogéneo

Riesgos:
- pipeline más complejo
- potencial saturación del CI central

Cuándo usar:
- equipo compacto
- alto acoplamiento entre servicios

## Regla Fast Flow

- Elegir estrategia por costo de coordinación real.
- No forzar una sola estrategia para todos los clientes.
- Mantener contrato común: calidad, trazabilidad, promoción y rollback.

# Jenkins Multibranch + Webhooks (Toolkit)

## Objetivo

Activar feedback continuo: push/PR -> build automático con contexto por rama.

## Patrón recomendado

1. Jenkins job tipo multibranch pipeline.
2. Descubrimiento de ramas activado.
3. `Jenkinsfile` versionado por rama.
4. Webhook SCM para disparo inmediato.

## Reglas de gobernanza

- Ramas protegidas (main/preprod/develop según modelo).
- PR checks obligatorios antes de merge.
- Evitar ejecución manual recurrente si hay webhook funcional.

## Jenkins detrás de firewall

Opciones:
1. Exposición segura controlada (LB/reverse proxy + hardening).
2. Forwarder de webhook (API gateway/lambda/middleware).
3. Polling SCM solo cuando webhook no sea viable.

## Seguridad

- Preferir token/API key sobre password plano.
- Rotar credenciales de integración periódicamente.
- Validar firma/secret de webhook cuando aplique.

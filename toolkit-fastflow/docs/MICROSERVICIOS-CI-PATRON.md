# Patrón CI para Microservicios (Toolkit)

Objetivo:
- Estandarizar CI para múltiples servicios sin crear "spaghetti pipelines".

## Decisión de diseño

- Multi-repo recomendado por defecto para microservicios con ownership claro.
- Mono-repo se evalúa cuando la organización prioriza coordinación central y tooling homogéneo.

## Contrato mínimo por servicio

Cada servicio debe incluir en su repo:
1. `Jenkinsfile` en raíz.
2. script de build/test reproducible.
3. metadata de versión/artefacto.
4. política de branch definida.

## Estructura mínima de pipeline por servicio

- Checkout
- Quality checks
- Unit tests
- Build artifact/container
- Push artifact
- (opcional) deploy no productivo

## Regla de escalabilidad

- Evitar copiar pipelines completos entre repos.
- Reusar segmentos con librerías compartidas o plantillas comunes.

## Integración con Fast Flow Toolkit

- `templates/common/Jenkinsfile` como base.
- overlays por stack para adaptar comandos de build/test.

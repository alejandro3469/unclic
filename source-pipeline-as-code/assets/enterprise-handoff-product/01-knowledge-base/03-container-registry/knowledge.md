# Container Registry - Knowledge Base

## Qué es y por qué importa

El registry es el inventario central de versiones desplegables. Permite:

- Versionado por commit/tag
- Rollback rápido
- Trazabilidad exacta de qué corre en cada entorno

## Patrón recomendado

- Tags: `commit-sha`, `develop`, `preprod`, `latest`.
- Escaneo de seguridad antes de promoción.
- Control de acceso por credenciales y entornos.

## Evidencia en fuentes

- Login/push al registry en pipeline: `_book_reference_txt/chapter2/Jenkinsfile.declarative.txt`
- Push multitag por rama + scan: `_book_reference_txt/chapter14/Jenkinsfile.txt`
- Infra de Nexus/registry: `_book_reference_txt/chapter9/nexus/terraform/*.txt`


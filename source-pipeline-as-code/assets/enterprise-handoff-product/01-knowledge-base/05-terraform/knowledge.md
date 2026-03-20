# Terraform - Knowledge Base

## Rol en el sistema

Terraform estandariza infraestructura como código para clientes con distintas condiciones base.

## Componentes observados en fuentes

- Jenkins master + seguridad + balanceador
- EKS networking y workers
- Módulos serverless (Lambda + API Gateway + DynamoDB + SQS)

## Beneficios directos enterprise

- Repetibilidad entre clientes
- Menos errores de configuración manual
- Menor tiempo de onboarding operativo

## Evidencia en fuentes

- Jenkins infra: `_book_reference_txt/chapter5/jenkins_master.tf.txt`
- EKS base: `_book_reference_txt/chapter11/eks/*.txt`
- Serverless modular: `_book_reference_txt/chapter12/terraform/*.txt`


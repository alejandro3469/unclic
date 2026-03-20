# Terraform Jenkins Blueprint (AWS)

Base curada desde fuente experta (Chapter 5) + prácticas actuales.

## Objetivo

Desplegar Jenkins como código con foco en:
- red privada segura,
- escalado de workers,
- operación reproducible,
- mínima fricción de onboarding.

## Arquitectura objetivo (faseada)

Fase 1 (MVP seguro)
- VPC
- subred pública (bastion opcional)
- subred privada (jenkins controller)
- SG con mínimo privilegio
- LB delante de Jenkins (HTTP/HTTPS)

Fase 2 (escala)
- ASG de workers
- launch template
- autojoin cluster
- cloudwatch alarms (scale-out / scale-in)

Fase 3 (resiliencia)
- multi-AZ reforzada
- storage compartido/estrategia HA controller (según necesidad)
- backup y recuperación

## Decisiones modernizadas (importantes)

1. Sintaxis Terraform moderna (HCL actual):
- `tags = {}` en lugar de bloques `tags {}` legacy.
- `required_providers` en bloque `terraform`.

2. State remoto recomendado:
- Backend remoto (ej: S3 + locking) para colaboración y seguridad.

3. Balanceador:
- En implementaciones nuevas se recomienda evaluar `aws_lb` (ALB/NLB).
- `aws_elb` (Classic) puede existir por compatibilidad, pero no es la ruta preferida para nuevos despliegues.

4. IAM:
- Menor privilegio obligatorio.
- Evitar credenciales hardcodeadas en `.tf`.

5. Secrets:
- Variables sensibles y/o gestor seguro.

## Flujo operativo Terraform

1. `terraform init`
2. `terraform validate`
3. `terraform plan -var-file=...`
4. `terraform apply -var-file=...`

## Integración con toolkit

Ruta base:
- `toolkit-fastflow/manifests/terraform/jenkins-aws/`

Contiene:
- estructura modular por capas
- entorno `dev` de referencia
- variables clave para adaptar por cliente

## Referencias oficiales sugeridas

- Terraform providers: https://developer.hashicorp.com/terraform/language/providers
- Terraform block: https://developer.hashicorp.com/terraform/language/terraform
- terraform apply: https://developer.hashicorp.com/terraform/cli/commands/apply
- AWS provider docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs

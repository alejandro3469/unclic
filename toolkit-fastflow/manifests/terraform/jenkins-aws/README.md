# Jenkins AWS with Terraform (Toolkit Skeleton)

Este directorio contiene un esqueleto modular para desplegar Jenkins en AWS.

## Estructura

- `modules/network`: VPC/subnets/rtables baseline.
- `modules/compute`: Jenkins controller + SG + LB placeholder.
- `modules/autoscaling`: Jenkins workers ASG + alarm placeholders.
- `envs/dev`: composición del entorno de referencia.

## Uso rápido

```bash
cd envs/dev
terraform init
terraform validate
terraform plan -var-file=terraform.tfvars
terraform apply -var-file=terraform.tfvars
```

## Variables (primer uso)

En `envs/dev` existe **terraform.tfvars.example**. Para **Free Tier (1 EC2, gratis)** usa **terraform.tfvars.free-tier.example**: cópialo a `terraform.tfvars`, rellena los IDs de AMI y tendrás Jenkins en subred pública (IP pública) y 0 workers. Ver **APLICAR-FASTFLOW.md** para pasos completos. Guía de despliegue gratis y transferencia de repo: **toolkit-fastflow/integrations/web-cuantica/docs/DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md**.

## Desplegar gratis (Free Tier) y replicar tras transferir el repo

- **HAPPY-PATH-REPLICAR-COMPLETO.md** — **Replicar todo:** Terraform + Jenkins + Gitea + POS + (opcional) deploy UnClic; todo lo que hace falta para que cualquiera replique el flujo.
- **INSTRUCCIONES-DEPLOY-AWS-GRATIS.md** — **Lista única:** todos los pasos para desplegar en AWS gratis (para ti o para dar a un agente).
- **INICIO-RAPIDO-AWS-GRATIS.md** — Punto de entrada: desplegar yo / transferir repo / soy nuevo dueño (enlaza al resto).
- **DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md** — Guía completa: Free Tier, transferir repo, que otro replique.
- **REPLICAR-AWS-NUEVO-DUENO.md** — Guía corta para el **nuevo propietario** del repo.
- **CHECKLIST-TRANSFERENCIA-REPO.md** — Checklist: dueño actual antes de transferir y nuevo dueño para replicar en AWS.

## Alternativa: Pulumi

Si prefieres **Pulumi** (IaC en TypeScript/Python/Go) en lugar de Terraform, existe un stack equivalente que provisiona la misma arquitectura (VPC, Jenkins, Gitea, POS) y se combina con Jenkins open source: **[manifests/pulumi/jenkins-aws/](../../pulumi/jenkins-aws/)**. Ver el README de ese directorio para requisitos, configuración e integración con Jenkins.

## Desarrollo local (LocalStack, saving local)

Para probar Terraform **sin usar AWS** (estado local, sin coste): **[LocalStack](https://localstack.cloud)** + `tflocal`. Ver [manifests/localstack/](../../localstack/).

## Notas

- Es base adaptable, no plantilla cerrada para todos los clientes.
- Ajusta CIDRs, AZs, SGs, tipos de instancia y políticas IAM.
- El controller queda en subred privada; para acceso desde internet (demo) usar bastion o colocar en subred pública y restringir SG por IP/VPN.
- Para colaboración de equipo usa backend remoto (S3 + DynamoDB).

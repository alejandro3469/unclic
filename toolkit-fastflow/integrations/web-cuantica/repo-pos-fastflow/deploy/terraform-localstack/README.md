# Terraform — LocalStack (laboratorio, no POS runtime)

## Qué es esto

Módulo **aparte** de `deploy/terraform/` (Kubernetes). Aquí solo practicas **APIs AWS emuladas** (S3) en LocalStack.

## Flujo recomendado

1. Arranca LocalStack en tu máquina o en un host accesible desde el agente Jenkins.
2. Desde la raíz del repo:

```bash
cd deploy/terraform-localstack
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
terraform init -backend=false
terraform plan -var='localstack_endpoint=http://localhost:4566'
```

3. Opcional apply local:

```bash
terraform apply -var='localstack_endpoint=http://localhost:4566' -auto-approve
```

## Jenkins

Variable del job: `FASTFLOW_TF_PLAN_LOCALSTACK=true` y `LOCALSTACK_ENDPOINT` correcto para la red del agente.

## Referencias

- [LOCALSTACK-IAC-JENKINS-POS.md](../../unclic/docs/LOCALSTACK-IAC-JENKINS-POS.md) (hermano `unclic/` bajo `web-cuantica/`)
- [LocalStack Terraform](https://docs.localstack.cloud/user-guide/integrations/terraform/)

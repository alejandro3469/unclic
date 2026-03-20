# Pulumi — laboratorio FastFlow POS

## Resumen

Proyecto **TypeScript** mínimo: un bucket S3 etiquetado para demos de IaC. El **POS Java** sigue desplegándose vía Docker/registry/Terraform K8s del mismo repo.

## Conexión con Jenkins

- Script: `../../.jenkins/scripts/pulumi-preview.sh`
- Variable job: `FASTFLOW_PULUMI_PREVIEW=true`
- Secret opcional: `PULUMI_ACCESS_TOKEN` (Pulumi Cloud)

## Uso local

```bash
cd deploy/pulumi
npm ci
pulumi login              # o pulumi login --local
pulumi stack init dev
pulumi preview
pulumi up                 # solo en cuenta que aceptes tocar
```

## Enlace en el sitio UnClic

En el build del landing:

```bash
NEXT_PUBLIC_DEMO_PULUMI_URL=https://app.pulumi.com/TU_ORG/fastflow-pos-lab/dev
```

(ajusta org/proyecto/stack a los tuyos)

## LocalStack

Si necesitas emulación, revisa la guía oficial Pulumi+LocalStack y variables `AWS_ENDPOINT_URL` / configuración de provider; para muchos equipos es más simple usar **Terraform** en `deploy/terraform-localstack/` contra LocalStack y **Pulumi** solo contra AWS dev.

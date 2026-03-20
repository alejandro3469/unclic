# LocalStack — Por qué, open source y cómo replicar

## Qué es LocalStack (según web oficial)

**LocalStack** ofrece *"Local Cloud Development — Your Answer to Unlocking Cloud Scale"*: replica aplicaciones cloud funcionales en infra local para desarrollar sin la complejidad ni los retrasos del desarrollo cloud tradicional. **Secure by design**, sin entornos compartidos, eficiente en coste y más rápido que la nube. Comportamiento cloud real, sin sorpresas.

- **LocalStack for AWS:** Emula 100+ servicios AWS localmente (S3, Lambda, DynamoDB, SQS, SNS, API Gateway, ECS, ECR, Step Functions, SageMaker, Glue, EventBridge, etc.). Feedback inmediato, control total de la infra. Release reciente: 4.x.
- **LocalStack for Snowflake:** Emula Snowflake localmente para probar consultas y optimizar antes de desplegar.
- **Comunidad:** 300M+ Docker pulls, 8M+ sesiones semanales, 60k+ GitHub stars, 35k+ Slack users, 500+ contributors. Empresas como IBM, Workday, Block, JetBrains, 3M, Bosch, Apple, Adobe; case studies en la web. Testimonios de Mitchell Hashimoto (HashiCorp), Corey Quinn, Yan Cui (AWS Serverless Hero), etc.
- **Integraciones:** AWS Integration, Snowflake Integrations; encaja en tus flujos con poca o ninguna configuración.

## Dashboard — Getting Started (qué ves)

En [app.localstack.cloud](https://app.localstack.cloud) (Workspace, trial Ultimate si aplica):

1. **Start LocalStack:** `localstack start`. Estado: *Not Running* / *Running*. Endpoint: **localhost.localstack.cloud:4566**. *"LocalStack runs on port 4566 by default."* Enlace **Docs**.
2. **Configure AWS credentials:** `export AWS_ACCESS_KEY_ID="test"` etc., *"Already have credentials? Skip this step"*, *"Need permanent setup? Configure with AWS CLI"*.
3. **Deploy an example S3 bucket:** `aws s3 mb s3://bucket1 --endpoint-url=http://localhost.localstack.cloud:4566`. *"Optionally, use awslocal to skip setting the endpoint."* **Docs**.

Puede aparecer el aviso *"We're making major changes to how our users access LocalStack for AWS — Learn More"*.

## Por qué lo usamos

- **Emular AWS en local:** Probar S3, Lambda, DynamoDB sin gastar ni tocar la cuenta real. Desarrollo y pruebas más rápidas.
- **Objetivo de automatización:** Compatible con Pulumi/Terraform; mismo código contra LocalStack o AWS cambiando endpoints. Open source (Community).

## Open source

- **Proyecto:** [LocalStack](https://localstack.cloud/). Licencia: Apache 2.0 (Community). Enterprise: Sign in, Talk to Sales.

## Enlaces oficiales

- **Web:** [localstack.cloud](https://localstack.cloud) — Get started for free, Talk to Sales, Enterprise.
- **Documentación:** [LocalStack for AWS Docs](https://docs.localstack.cloud/aws/), [LocalStack for Snowflake Docs](https://docs.localstack.cloud/snowflake/).
- **Dashboard:** [app.localstack.cloud](https://app.localstack.cloud).
- **Recursos:** Blog, Case Studies. **Soporte:** Get Help, FAQ, Request a Demo, Try for Free. **Company:** About, Values, Careers. **Legal:** Terms, Enterprise SLA, Privacy, DPA.
- **Productos:** AWS Emulator, Snowflake Emulator, Pricing.

## Cómo replicar

| Consola | Comando |
|---------|---------|
| Terminal (local) | `brew install localstack/tap/localstack-cli` (macOS) o ver [Getting Started](https://docs.localstack.cloud/getting-started/install/) |
| Terminal (local) | `localstack start` (o `docker compose up -d` en `manifests/localstack/`) |
| Terminal (local) | `export AWS_ACCESS_KEY_ID=test AWS_SECRET_ACCESS_KEY=test AWS_DEFAULT_REGION=us-east-1` |
| Terminal (local) | `awslocal s3 mb s3://bucket1` (ejemplo) |

Endpoint: `http://localhost:4566`. Ver [manifests/localstack/README.md](../../../manifests/localstack/README.md).

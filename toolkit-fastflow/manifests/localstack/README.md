# LocalStack: desarrollo y pruebas locales (saving local)

[LocalStack](https://localstack.cloud) emula servicios AWS (y otros) en tu máquina para que puedas **desarrollar y probar sin usar la nube real**. Así **ahorras coste**, evitas dependencias de entornos compartidos y guardas estado/infra **local**.

---

## Instalación (CLI y ejecución local)

**Getting Started oficial:** [Install, configure, and deploy LocalStack](https://docs.localstack.cloud/getting-started/install/).

### Opción 1: Homebrew (macOS)

```bash
brew install localstack/tap/localstack-cli
```

### Opción 2: Binario

Descarga el binario para tu arquitectura desde la [página de instalación](https://docs.localstack.cloud/getting-started/install/):

- **x86-64** (Intel/AMD)
- **ARM64** (Apple Silicon, Graviton, etc.)

Coloca el binario en tu `PATH` y ejecuta `localstack`.

### Opción 3: Docker (la que usa este repo)

En este directorio usamos **Docker Compose** para levantar LocalStack (ver sección siguiente). La CLI instalada con Brew o binario puede usarse para gestionar o iniciar LocalStack en tu máquina si prefieres no usar Docker; la API y el endpoint siguen siendo `http://localhost:4566` por defecto.

**Nota:** LocalStack ofrece planes Cloud (Workspace, trial Ultimate, etc.) para equipos; para desarrollo local en este toolkit basta con la instalación CLI + Docker o solo Docker.

### Auth token (funciones con licencia / Ultimate trial)

Si tienes una cuenta LocalStack con trial **Ultimate** o licencia, puedes activar todas las funciones configurando tu **auth token** en la CLI (solo en tu máquina; **nunca lo subas al repo**):

```bash
localstack auth set-token <TU_TOKEN>
```

- Obtén tu token en el [dashboard de LocalStack](https://app.localstack.cloud) (Getting Started → Set auth token).
- El token da acceso a las funciones de la licencia; guárdalo en un gestor de contraseñas y no lo incluyas en `.env`, en scripts ni en el repositorio.

Docs: [LocalStack — Authentication](https://docs.localstack.cloud/references/auth-token/).

---

En el toolkit FastFlow usamos LocalStack para:

- **Guardar estado local**: Pulumi con backend `file://` y Terraform con state local; todo queda en disco sin tocar AWS.
- **Probar IaC sin gastar**: ejecutar `pulumi up` / `terraform apply` contra LocalStack (S3, Lambda, DynamoDB, SQS, etc.) en lugar de AWS.
- **Iterar rápido**: feedback inmediato, sin esperar despliegues en la nube.

La **infra completa** (Jenkins, Gitea, POS en EC2 con VPC real) sigue desplegándose en **AWS** cuando lo necesites; LocalStack sirve para desarrollo diario, tests y demos de componentes que usen S3, Lambda, SQS, etc.

---

## Qué es LocalStack (según web y dashboard oficial)

- **Tagline oficial:** *"Local Cloud Development — Your Answer to Unlocking Cloud Scale."* Replica aplicaciones cloud completas en infra local para desarrollar sin la complejidad ni los retrasos del desarrollo cloud tradicional.
- **Productos:** **LocalStack for AWS** (emula 100+ servicios: S3, Lambda, DynamoDB, SQS, SNS, API Gateway, ECS, ECR, Step Functions, SageMaker, Glue, EventBridge, etc.). **LocalStack for Snowflake** (emular Snowflake localmente para pruebas de consultas y flujos). Versión reciente: LocalStack for AWS 4.x.
- **Comunidad y uso:** 300M+ Docker pulls, 8M+ sesiones semanales, 60k+ estrellas en GitHub, 35k+ usuarios en Slack, 500+ contribuidores. *"Over 50,000 developers love LocalStack"*. Empresas como IBM, Workday, Block, JetBrains, 3M, Bosch, Apple, Adobe, etc. usan o han referenciado LocalStack; hay case studies en la web.
- **Open source** (Community); oferta **Enterprise** (Sign in, Talk to Sales). Secure by design, no shared environments, cost efficient, faster than the cloud.
- **Dashboard:** [app.localstack.cloud](https://app.localstack.cloud) — Workspace, Getting Started (Start LocalStack, Configure credentials, Deploy S3 bucket), System Status. Puede aparecer el aviso *"We're making major changes to how our users access LocalStack for AWS — Learn More"*.
- Documentación: [LocalStack for AWS Docs](https://docs.localstack.cloud/aws/), [LocalStack for Snowflake Docs](https://docs.localstack.cloud/snowflake/).

---

## Quick start (según Getting Started oficial)

Flujo mínimo para arrancar LocalStack, configurar credenciales y probar con un bucket S3 (igual que en el [dashboard Getting Started](https://app.localstack.cloud)).

### 1. Arrancar LocalStack

Con la CLI instalada (Brew o binario):

```bash
localstack start
```

LocalStack queda escuchando en **puerto 4566** por defecto. Endpoint: `http://localhost.localstack.cloud:4566` (o `http://localhost:4566`).

**Alternativa con Docker** (la que usa este repo): ver sección [Arrancar LocalStack (Docker)](#arrancar-localstack-docker).

### 2. Configurar credenciales AWS

Para que la CLI de AWS (o SDKs) apunten a LocalStack sin tocar tu configuración real de AWS:

```bash
export AWS_ACCESS_KEY_ID="test"
export AWS_SECRET_ACCESS_KEY="test"
export AWS_DEFAULT_REGION="us-east-1"
```

¿Ya tienes un perfil AWS? Puedes usar otro terminal o un `.env` que no subas al repo. Para un setup permanente con AWS CLI: [Configure with AWS CLI](https://docs.localstack.cloud/getting-started/install/#configure-with-aws-cli) (docs oficiales).

### 3. Crear un bucket S3 de prueba

```bash
aws s3 mb s3://bucket1 --endpoint-url=http://localhost.localstack.cloud:4566
```

Para no escribir `--endpoint-url` en cada comando, instala **awslocal** (wrapper que usa LocalStack por defecto):

```bash
pip install awscli-local
awslocal s3 mb s3://bucket1
awslocal s3 ls
```

Docs: [LocalStack — AWS CLI](https://docs.localstack.cloud/user-guide/integrations/aws-cli/).

---

## Arrancar LocalStack (Docker)

Desde este directorio:

```bash
cd toolkit-fastflow/manifests/localstack
docker compose up -d
```

Comprobar:

```bash
curl -s http://localhost:4566/_localstack/health | head -20
```

Para usar la CLI de AWS contra LocalStack (opcional):

```bash
pip install awscli-local
awslocal s3 ls
# o
AWS_ACCESS_KEY_ID=test AWS_SECRET_ACCESS_KEY=test \
  aws --endpoint-url=http://localhost:4566 s3 ls
```

### Dashboard y System Status (troubleshooting)

En el [dashboard de LocalStack](https://app.localstack.cloud) (Workspace) puedes ver **System Status**: estado de la instancia y lista de servicios emulados (S3, Lambda, DynamoDB, API Gateway, EC2, etc.). Por defecto el endpoint configurado es **localhost.localstack.cloud:4566**.

Si aparece **"Could not connect to running LocalStack instance"** y todos los servicios en **offline**:

1. **LocalStack no está en marcha.** Arranca con `localstack start` (CLI) o `docker compose up -d` en este directorio.
2. **El endpoint no es accesible desde el navegador.** Comprueba que LocalStack escucha en el puerto 4566: `curl -s http://localhost:4566/_localstack/health`.
3. **Puerto o host distintos.** Si usas otro puerto o un host remoto, actualiza la URL del endpoint en la configuración del dashboard (arriba) para que coincida con donde corre LocalStack.

Mientras LocalStack no esté corriendo y accesible, el dashboard mostrará todos los servicios como *offline*; al conectar, reflejará el estado real de cada servicio.

---

## Guardar estado local con Pulumi

Para que **Pulumi** no use Pulumi Cloud y guarde el estado en disco (saving local):

```bash
export PULUMI_BACKEND_URL=file://$(pwd)/.pulumi-local
export PULUMI_CONFIG_PASSPHRASE=localdev
```

Luego, en un proyecto Pulumi (p. ej. `manifests/pulumi/jenkins-aws`), usa un stack específico para LocalStack y apunta los endpoints a LocalStack.

### Opción A: `pulumilocal` (recomendado)

El wrapper [pulumi-local](https://github.com/localstack/pulumi-local) configura endpoints y credenciales por ti:

```bash
pip install pulumi-local
cd toolkit-fastflow/manifests/pulumi/jenkins-aws
# Backend local (guardar estado en disco)
export PULUMI_BACKEND_URL=file://$(pwd)/.pulumi-local
export PULUMI_CONFIG_PASSPHRASE=localdev
pulumilocal stack init localstack
pulumilocal up --cwd .
```

Ver [LocalStack — Pulumi](https://docs.localstack.cloud/user-guide/integrations/pulumi/).

### Opción B: Config manual

Copia `Pulumi.localstack.yaml.example` a `Pulumi.localstack.yaml` en tu proyecto Pulumi, ajusta si hace falta, y usa ese stack:

```bash
pulumi stack select localstack
pulumi up
```

El ejemplo incluye credenciales de prueba y endpoints apuntando a `http://localhost:4566`. **Importante:** el stack actual de `jenkins-aws` crea VPC/EC2; muchas características de EC2 en LocalStack tienen limitaciones. Para pruebas locales puras conviene un programa que solo use S3/Lambda/DynamoDB (por ejemplo el de [manifests/sst/example](../sst/example/) desplegado contra LocalStack vía endpoints).

---

## Guardar estado local con Terraform

Para **Terraform** contra LocalStack sin modificar tu código:

```bash
pip install terraform-local
cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev
tflocal init
tflocal plan
tflocal apply
```

`tflocal` genera un override que apunta el provider AWS a `http://localhost:4566`. El state queda en local (por defecto `terraform.tfstate` en el directorio). Ver [LocalStack — Terraform](https://docs.localstack.cloud/aws/integrations/infrastructure-as-code/terraform).

**Nota:** Los módulos actuales (VPC, EC2 Jenkins/Gitea/POS) están pensados para AWS real; en LocalStack la emulación de EC2 tiene limitaciones. Para pruebas locales de recursos como S3, Lambda o SQS, usa un módulo/stack reducido o el ejemplo de SST.

---

## SST y LocalStack

SST usa el SDK de AWS; puedes apuntar las credenciales y el endpoint a LocalStack (variables de entorno o configuración del provider). La integración no está tan empaquetada como `pulumilocal`/`tflocal`; para Lambdas y Buckets en local suele usarse LocalStack con `AWS_ENDPOINT_URL=http://localhost:4566` y credenciales de prueba en el entorno donde corre `sst dev` o el runtime. Consulta [Transparent endpoint injection](https://docs.localstack.cloud/aws/capabilities/networking/transparent-endpoint-injection) y [AWS SDKs](https://docs.localstack.cloud/aws/integrations/aws-sdks/) si quieres que tu app use LocalStack sin cambiar código.

---

## Resumen rápido

| Objetivo              | Herramienta   | Cómo |
|-----------------------|---------------|------|
| Guardar estado local  | Pulumi        | `PULUMI_BACKEND_URL=file://...` + stack `localstack` con endpoints LocalStack. |
| Guardar estado local  | Terraform     | `tflocal`; state en disco en el directorio del proyecto. |
| Probar Pulumi vs AWS  | Pulumi        | `pulumilocal up` con LocalStack levantado. |
| Probar Terraform vs AWS | Terraform   | `tflocal apply` con LocalStack levantado. |
| Arrancar emulador     | Docker        | `docker compose up -d` en este directorio. |

---

## Samples y demos oficiales (LocalStack Samples)

[**LocalStack Samples**](https://github.com/localstack-samples/) es la organización de GitHub con **samples y demos** que corren sobre LocalStack. Útil para aprender patrones y copiar ejemplos de IaC, Lambda, S3, DynamoDB, etc.

- **[Developer Hub](https://docs.localstack.cloud/developer-hub/)** — Índice de muestras y guías.
- **Repos destacados:**
  - [localstack-demo](https://github.com/localstack-samples/localstack-demo) — Demo simple desplegada con LocalStack (TypeScript).
  - [localstack-terraform-samples](https://github.com/localstack-samples/localstack-terraform-samples) — Muestras Terraform para LocalStack (HCL).
  - [localstack-pulumi-samples](https://github.com/localstack-samples/localstack-pulumi-samples) — Proyectos Pulumi de ejemplo (Python).
  - [sample-lambda-s3-image-resizer-hot-reload](https://github.com/localstack-samples/sample-lambda-s3-image-resizer-hot-reload) — Lambda + S3 (hot reload, eventos).
  - [sample-terraform-fullstack-serverless-shipment-app](https://github.com/localstack-samples/sample-terraform-fullstack-serverless-shipment-app) — App full-stack (React, Spring Boot, S3, Lambda, DynamoDB) con Terraform.
  - [multi-iac-devops](https://github.com/localstack-samples/multi-iac-devops) — IaC para desplegar S3 en LocalStack y AWS (TypeScript, archivado).

Hay decenas de repos (Terraform, Pulumi, Lambda, CodePipeline, K8s, Cloud Pods, etc.); explora [github.com/localstack-samples](https://github.com/localstack-samples/) para más.

---

## Web oficial — estructura (recursos y enlaces)

En [localstack.cloud](https://localstack.cloud): **Get started for free**, **Talk to Sales**, **Enterprise**, **Sign in**. Productos: **AWS Emulator**, **Snowflake Emulator**, **Pricing**. **Documentation:** LocalStack for AWS Docs, LocalStack for Snowflake Docs. **Support:** Contact Sales, Get Help, FAQ, Request a Demo, Try for Free. **Company:** About us, Values, Careers (We're hiring!). **Legal:** Terms & Conditions, Enterprise SLA, Privacy Policy, DPA. **Resources:** Blog, Case Studies. **Follow:** Slack, GitHub. © 2025 LocalStack.

---

## Referencias

- [LocalStack Samples (GitHub)](https://github.com/localstack-samples/) — Samples y demos oficiales
- [LocalStack Developer Hub](https://docs.localstack.cloud/developer-hub/)
- [Getting Started — Install LocalStack](https://docs.localstack.cloud/getting-started/install/) (Brew, binario, credenciales AWS, ejemplo S3)
- [LocalStack — AWS CLI / awslocal](https://docs.localstack.cloud/user-guide/integrations/aws-cli/)
- [LocalStack for AWS Docs](https://docs.localstack.cloud/aws/)
- [LocalStack — Pulumi](https://docs.localstack.cloud/user-guide/integrations/pulumi/)
- [LocalStack — Terraform](https://docs.localstack.cloud/aws/integrations/infrastructure-as-code/terraform)
- [Accessing LocalStack via the endpoint URL](https://docs.localstack.cloud/aws/capabilities/networking/accessing-endpoint-url)
- [pulumi-local (PyPI)](https://pypi.org/project/pulumi-local/) · [terraform-local (PyPI)](https://pypi.org/project/terraform-local/)

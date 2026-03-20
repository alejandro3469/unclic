# FastFlow: Jenkins + Gitea + POS on AWS (Pulumi)

Infraestructura como código con **Pulumi** (TypeScript) equivalente al stack Terraform `jenkins-aws`: VPC, Jenkins controller, Gitea y POS en EC2. Pensado para usarse con **Jenkins open source** como motor de CI/CD.

## Get Started with Pulumi

Pulumi es una plataforma de IaC que usa lenguajes de programación (TypeScript, Python, Go, etc.) para automatizar y gestionar infra en la nube. **Pulumi IaC es gratuito y open source**; opcionalmente puedes usar [Pulumi Cloud](https://www.pulumi.com/docs/pulumi-cloud/) para estado, equipos y políticas.

- **[Download & Install](https://www.pulumi.com/docs/get-started/install/)** — Instalar el CLI.
- **Por proveedor:** [Get started with Pulumi & AWS](https://www.pulumi.com/docs/get-started/aws/) | [Azure](https://www.pulumi.com/docs/get-started/azure/) | [Google Cloud](https://www.pulumi.com/docs/get-started/gcp/) | [Kubernetes](https://www.pulumi.com/docs/get-started/kubernetes/).

Recursos útiles al empezar:
- **[Concepts](https://www.pulumi.com/docs/concepts/)** — Modelo de programación, stacks, state, recursos.
- **[Migration](https://www.pulumi.com/docs/migration/)** — Migrar o convivir con Terraform, CloudFormation, etc.

Este proyecto asume que ya tienes el CLI instalado; si es la primera vez con Pulumi, sigue los enlaces anteriores antes de desplegar.

## Por qué Pulumi + Jenkins

- **Pulumi**: IaC en lenguajes reales (TypeScript aquí), IDE support, tests y reutilización. [Comparativa con Terraform](https://www.pulumi.com/docs/iac/comparisons/terraform/).
- **Jenkins**: Open source, pipelines, plugins, integración con Gitea y registro de imágenes. Este proyecto **provisiona la infra** donde corre Jenkins; los pipelines los defines en tu Jenkinsfile.

Flujo típico:

1. **Pulumi** crea la VPC y las EC2 (Jenkins, Gitea, POS).
2. Instalas Jenkins y Gitea en las instancias (Java 17 + Jenkins, ver [QUE-SIGUE-DESPUES-DEL-APPLY](../../terraform/jenkins-aws/docs/QUE-SIGUE-DESPUES-DEL-APPLY.md)).
3. **Jenkins** ejecuta pipelines (build, test, push a registry, deploy a la instancia POS).

Opcional: un job en Jenkins que ejecute `pulumi up` / `pulumi destroy` para gestionar la misma infra desde el propio Jenkins (credenciales AWS en Jenkins, Pulumi CLI en el agente).

Para definir y desplegar la **capa de aplicación** (Next.js, Lambdas, Buckets, contenedores) en la misma cuenta AWS puedes usar **[SST](https://sst.dev/docs)** (built on Pulumi, open source): [manifests/sst/](../../sst/).

Para **desarrollo y pruebas locales** sin usar AWS (guardar estado local, ahorrar coste): **[LocalStack](https://localstack.cloud)** + Pulumi backend `file://` o `pulumilocal`: [manifests/localstack/](../../localstack/).

---

## Requisitos

- **[Pulumi CLI](https://www.pulumi.com/docs/get-started/install/)** — Instalación oficial:
  - **macOS:** `brew install pulumi/tap/pulumi` o script: `curl -fsSL https://get.pulumi.com | sh`
  - **Linux/Windows:** ver [Download & Install](https://www.pulumi.com/docs/get-started/install/).
  - Comprobar: `pulumi version`. Si falta en el PATH, revisar la [documentación de errores comunes](https://www.pulumi.com/docs/get-started/install/#common-errors-and-warnings).
- **Node.js 18+** (para este proyecto TypeScript).
- **AWS** configurado (credenciales CLI o `AWS_ACCESS_KEY_ID` / `AWS_SECRET_ACCESS_KEY`). Pulumi usa el mismo [provider de credenciales](https://www.pulumi.com/registry/packages/aws/installation-configuration/) que la CLI.

Requisitos mínimos recomendados (según [docs](https://www.pulumi.com/docs/get-started/install/#minimum-system-requirements)): 2 GHz CPU, 4 GB RAM, 1 GB disco libre.

**Opcional — Agent Skills (Cursor, VS Code, Copilot, etc.):** para que tu asistente de IA use flujos y patrones de Pulumi: `npx skills add pulumi/agent-skills --skill '*'`. Ver [Download & Install → Enhance your AI coding assistant](https://www.pulumi.com/docs/get-started/install/#enhance-your-ai-coding-assistant-with-agent-skills).

---

## Configuración

1. Clonar/copiar el stack de ejemplo y rellenar AMIs de **Amazon Linux 2** para tu región:

   ```bash
   cp Pulumi.dev.yaml.example Pulumi.dev.yaml
   ```

2. Obtener una AMI válida (ej. `us-east-2`):

   ```bash
   aws ec2 describe-images --owners amazon --region us-east-2 \
     --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
     --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
   ```

3. Editar `Pulumi.dev.yaml` y sustituir los tres `ami-xxxxxxxxxxxxxxxxx` por ese ID (o uno por servicio si quieres distintas AMIs).

4. (Opcional) Configurar por CLI en lugar de YAML:

   ```bash
   pulumi config set aws:region us-east-2
   pulumi config set jenkins-aws:jenkinsControllerAmiId ami-xxxx
   pulumi config set jenkins-aws:giteaAmiId ami-xxxx
   pulumi config set jenkins-aws:posAmiId ami-xxxx
   ```

---

## Desplegar

```bash
cd toolkit-fastflow/manifests/pulumi/jenkins-aws
npm install
pulumi stack select dev   # o crear: pulumi stack init dev
pulumi preview            # revisar cambios
pulumi up                 # aplicar (confirmar con yes)
```

Al finalizar, Pulumi imprime las salidas: `jenkinsPublicIp`, `giteaPublicIp`, `posPublicIp`, `jenkinsUrl`, `giteaUrl`. Usa esas IPs para conectarte por EC2 Instance Connect e instalar Jenkins (Java 17) y Gitea según la [guía post-apply](../../terraform/jenkins-aws/docs/QUE-SIGUE-DESPUES-DEL-APPLY.md) y el [happy path](../../terraform/jenkins-aws/HAPPY-PATH-REPLICAR-COMPLETO.md) (mismos pasos; la infra la crea Pulumi en lugar de Terraform).

---

## Destruir

```bash
pulumi destroy
```

Confirma con `yes`. Se eliminan VPC, security groups e instancias.

---

## Integración con Jenkins

### Opción A: Pulumi provisiona; Jenkins solo ejecuta pipelines

- Usas Pulumi desde tu máquina o desde un job “infra” para hacer `up`/`destroy`.
- En las EC2 creadas instalas Jenkins (Java 17) y Gitea.
- El resto de jobs de Jenkins son pipelines de aplicación (build, test, deploy a la instancia POS). No necesitas Pulumi dentro de Jenkins.

### Opción B: Jenkins ejecuta Pulumi (infra desde Jenkins)

- Instala Pulumi CLI en el agente (o controller) que ejecute el job.
- Configura credenciales AWS en Jenkins (AWS credentials plugin o variables de entorno).
- Crea un job tipo Pipeline que haga `git clone` de este repo, `npm install`, `pulumi up` (o `pulumi preview` en un branch). Así puedes cambiar infra desde un pipeline (por ejemplo, en un branch “infra” o “deploy-dev”).

Ambas opciones son compatibles con Jenkins open source y con la documentación existente de FastFlow (Terraform); solo cambia la herramienta que crea la VPC y las EC2.

---

## Referencias

**Pulumi**
- [Docs — Get Started](https://www.pulumi.com/docs/get-started/) — Introducción, instalación y primer despliegue.
- [Concepts](https://www.pulumi.com/docs/concepts/) — Modelo de programación, stacks, state.
- [Deployments & Workflows](https://www.pulumi.com/docs/deployments/) — Despliegues, drift, state, automatización.
- [Secrets & Configuration](https://www.pulumi.com/docs/secrets/) — Secretos y configuración por entorno.
- [Registry](https://www.pulumi.com/registry/) — Paquetes para 150+ proveedores y servicios.
- [Migration](https://www.pulumi.com/docs/migration/) — Migrar desde Terraform, CloudFormation y otros.
- [Pulumi AWS Provider](https://www.pulumi.com/registry/packages/aws/)

**Este repo**
- [Jenkins Java 17 requirement](https://www.jenkins.io/doc/book/platform-information/support-policy-java/)
- Terraform equivalente: `manifests/terraform/jenkins-aws/` (misma arquitectura; migración en [Pulumi Migration](https://www.pulumi.com/docs/migration/)).

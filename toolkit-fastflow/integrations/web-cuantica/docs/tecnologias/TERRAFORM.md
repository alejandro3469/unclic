# Terraform — Por qué, open source y cómo replicar

## Qué es Terraform (según web oficial)

**Terraform** es una herramienta de **infrastructure as code (IaC)** que permite construir, cambiar y versionar infraestructura de forma segura y eficiente. Incluye componentes de bajo nivel (instancias de cómputo, almacenamiento, redes) y de alto nivel (entradas DNS, funciones SaaS).

- **Tagline:** *"Automate Infrastructure on Any Cloud"*.
- **Navegación principal:** Install | Tutorials | Documentation | Sandbox | Registry | Try Cloud (búsqueda ⌘/ctrl K).

## Estructura de la web oficial

- **Get Started:** Tutoriales hands-on por proveedor: **Amazon Web Services**, **Azure**, **HCP Terraform**, **Google Cloud Platform**, **Oracle Cloud**, **Docker**. *"Follow a code-complete, hands-on tutorial to learn the Terraform basics with your favorite infrastructure provider."*
- **Sandbox:** Terraform sandbox con herramientas y servicios preinstalados para experimentar.
- **Best Practices:** Terraform style guide (convenciones recomendadas), Phases of Terraform adoption (diseño de flujos a escala).
- **Featured Documentation:** Configuration Language (describir infra en HCL), Terraform CLI (flujos basados en CLI), HCP Terraform (colaboración en equipo), Terraform Enterprise, Terraform MCP Server (BETA), Terraform Migrate, Provider Use, Plugin Development, Registry Publishing, Integration Program.
- **Popular Use Cases:** Ejemplos (multi-cloud Kubernetes, preview environments con GitHub Actions y Vercel, golden image pipeline con HCP Packer, etc.).
- **Get Certified:** Terraform Associate 004 Certification Exam, Terraform Authoring and Operations Professional Certification Exam.
- **Recursos:** Tutorial Library, Certifications, Community Forum, Support, GitHub, Terraform Registry, Developer.

## Por qué lo usamos

- **IaC (Infrastructure as Code):** Definimos VPC, EC2, security groups en HCL; mismo resultado en cualquier cuenta/región. Replicar = ejecutar `terraform apply`.
- **Objetivo de automatización:** La infra es parte del pipeline; todo versionado en Git, sin clics manuales en la consola para crear instancias.

## Open source

- **Proyecto:** [Terraform](https://www.terraform.io/) (HashiCorp). Licencia: BSL (source available); runtime MPL 2.0. OpenTF disponible como fork 100% open source.

## Enlaces oficiales

- **Sitio:** [terraform.io](https://www.terraform.io/) — Install, Tutorials, Documentation, Sandbox, Registry, Try Cloud.
- **Documentación:** Configuration Language, Terraform CLI, HCP Terraform, Terraform Enterprise.
- **Registry:** [registry.terraform.io](https://registry.terraform.io/) — módulos y providers.
- **Footer:** Certifications, System Status, Terms of Use, Security, Privacy, Trademark Policy, Trade Controls, Accessibility, Give Feedback.

## Cómo replicar (FastFlow)

| Consola | Comando |
|---------|---------|
| Terminal (local) | `cd toolkit-fastflow/manifests/terraform/jenkins-aws` |
| Terminal (local) | `terraform init` |
| Terminal (local) | `terraform plan -out=tfplan` |
| Terminal (local) | `terraform apply tfplan` |

Outputs: IP pública de la EC2, etc. Ver docs en `manifests/terraform/jenkins-aws/`.

# Pulumi — Por qué, open source y cómo replicar

## Qué es Pulumi (según web oficial)

**Pulumi** es una plataforma de infraestructura como código: *"Modern Infrastructure as Code, Now with Agentic AI."* *"The cloud infrastructure platform engineers love and enterprises trust. Any cloud, any language. Secure and governed by default."* Confían en ella más de 3.700 empresas (Docker, Supabase, A+E Networks, Mercedes-Benz R&D, Mindbody, etc.).

- **The Complete Platform for Infrastructure Teams:** IaC, gestión de secretos, políticas y gobernanza, e IA en una sola plataforma. Lenguajes reales (TypeScript, Python, Go, C#, Java, YAML), IDE y tests. **Secure by Default:** secretos cifrados, credenciales dinámicas, auditoría; SOC 2 Type II. **AI for Infrastructure:** generar infra desde requisitos, revisar PRs, depurar despliegues.
- **Pulumi Neo:** Agente de IA para infraestructura; entiende el contexto, respeta políticas y ejecuta tareas de punta a punta (ej. Werner Enterprises: de 3 días a 4 horas en aprovisionamiento).
- **Pulumi ESC:** Secretos y configuración centralizados; integración con HashiCorp Vault, AWS Secrets Manager, Azure Key Vault; credenciales dinámicas con OIDC.
- **Pulumi Insights:** Visibilidad multi-cloud, búsqueda en lenguaje natural, políticas automáticas, cumplimiento en tiempo real.
- **Internal Developer Platforms (IDP):** Plataformas de autoservicio, plantillas, APIs, golden paths.
- **Open source:** *"Pulumi is open source. Built by engineers for engineers."* Comunidad activa (Slack, testimonios en Twitter/X).

## Navegación y documentación

- **Cabecera:** Get Started | Slack | Docs | Registry | Pulumi Neo | Contact Us | Sign In. Búsqueda: ⌘/ctrl-k.
- **Docs Home → Get Started:** Introduction, **Download & Install Pulumi**, Infrastructure as Code, Deployments & Workflows, Version Control, Secrets & Configuration, Insights & Governance, Internal Developer Platform, Infrastructure AI, Administration. **Reference:** Tutorials, Registry. **Support & Troubleshooting.**

## Descarga e instalación (según docs oficiales)

- **Última versión:** 3.226.0 (consultar [Available versions](https://www.pulumi.com/docs/reference/install/) y [CHANGELOG](https://github.com/pulumi/pulumi/releases)).
- **Elegir SO:** macOS | Windows | Linux.
- **macOS (Homebrew):** `brew install pulumi/tap/pulumi`. Binarios: amd64, arm64 (macOS Ventura 13+).
- **Verificar:** `pulumi version`.
- **Errores habituales:** *Pulumi not found* → revisar `$PATH`. *New version warning* → actualizar con `curl -sSL https://get.pulumi.com | sh` o desactivar con `PULUMI_SKIP_UPDATE_CHECK=1`.
- **Versión concreta:** `curl -fsSL https://get.pulumi.com | sh -s -- --version <version>`. **Dev:** `--version dev`.
- **Requisitos mínimos (orientativos):** CPU 2 GHz+, RAM 4 GB+, disco 1 GB+ (varía con runtimes y providers).
- **Migración 2.0 → 3.0:** Ver migration guide en la documentación.
- **Agent Skills (IA):** Para Cursor, VS Code, GitHub Copilot, etc.: `npx skills add pulumi/agent-skills --skill '*'`. Claude: `claude plugin marketplace add pulumi/agent-skills` y `claude plugin install pulumi-migration` / `pulumi-authoring`.
- **Desinstalar:** Eliminar el directorio de instalación y la carpeta `~/.pulumi`.

## Por qué lo usamos

- **IaC en código (TypeScript/Go/Python):** Alternativa a Terraform; mismo objetivo (EC2, VPC, etc.) con lenguajes de programación. Útil si prefieres tipos y reutilización de código.
- **Objetivo de automatización:** Open source; Pulumi CLI y runtimes son Apache 2.0.

## Open source

- **Proyecto:** [Pulumi](https://www.pulumi.com/). Licencia: Apache 2.0.

## Enlaces oficiales

- **Web:** [pulumi.com](https://www.pulumi.com/) — Get Started, Contact Us, Product (For Engineers, For Enterprises), Docs, Blog, Pricing, Company.
- **Documentación:** [pulumi.com/docs](https://www.pulumi.com/docs/) — Get Started, Download & Install, IaC, Deployments, Secrets, Insights, IDP, Infrastructure AI.
- **Registry:** [pulumi.com/registry](https://www.pulumi.com/registry/) — paquetes y componentes.
- **Pulumi Neo:** interfaz conversacional para infra, uso y documentación.
- **Footer:** Enterprise, Case Studies, Request a Demo, Support, Events, About, Community, Careers. **Legal:** Terms & conditions, Privacy policy, Acceptable use policy, Trademark usage, Professional services agreement. © Pulumi 2026.

## Cómo replicar (FastFlow)

| Consola | Comando |
|---------|---------|
| Terminal (local) | `cd toolkit-fastflow/manifests/pulumi/jenkins-aws` |
| Terminal (local) | `npm install` y configurar `Pulumi.dev.yaml` (según example) |
| Terminal (local) | `pulumi up` (stack dev) |

Ver [manifests/pulumi/jenkins-aws/README.md](../../../../manifests/pulumi/jenkins-aws/README.md).

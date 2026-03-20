# AWS EC2 — Por qué, estándar abierto y cómo replicar

## Web principal de AWS (aws.amazon.com)

Al abrir [aws.amazon.com](https://aws.amazon.com) verás la portada de AWS (idioma: English; enlaces Contact us, AWS Marketplace, Support, My account).

- **Navegación:** Discover AWS | Products | Solutions | Pricing | Resources | **Search** | **Sign in to console** | **Create account**.
- **Hero:** *"Get the greatest choice of cloud and AI capabilities"* — *"Modernize faster and scale more efficiently with an unmatched portfolio of over 240 comprehensive services"* — **Start free with AWS**.
- **What's new:** Lanzamientos y anuncios (p. ej. OpenAI y Amazon, AI path to production, Amazon Connect). Historias de clientes por sector (Aerospace, Advertising, Automotive, Media, Manufacturing, etc.).
- **Industries:** Financial services, Healthcare, Government, Telecommunications, Advertising, Manufacturing, Media & Entertainment, Games; *"Powering what's next in every industry"*.
- **AWS Global Infrastructure:** *"The AWS Cloud spans 123 Availability Zones within 39 Geographic Regions"* (con planes de más AZs y regiones, p. ej. Saudi Arabia, Chile). **North America:** AWS GovCloud (US-East, US-West), Canada (Central, West Calgary), Mexico (Central), US West (Northern California, Oregon), **US East (Northern Virginia)**, **US East (Ohio)** = us-east-2, etc. Edge Locations: 31 (North America).
- **Free Tier:** *"Try AWS for free"* — $100 en créditos al registrarte, hasta 6 meses sin coste bajo condiciones; *"Track your AWS Free Tier usage"*, cost alerts.
- **Footer:** Learn (What Is AWS?, Cloud Computing, Agentic AI, Security, What's New, Blogs), Resources (Getting Started, Training, Trust Center, Solutions Library, Architecture Center, FAQs), Developers (Builder Center, SDKs, .NET/Python/Java/PHP/JavaScript on AWS), Help (Contact Us, Support Ticket, re:Post, Knowledge Center), Legal (Privacy, Site terms, Cookie Preferences). © 2026 Amazon Web Services, Inc.

Para ir a EC2: **Sign in to console** → [console.aws.amazon.com](https://console.aws.amazon.com/) → servicios → EC2, o directo a [console.aws.amazon.com/ec2](https://console.aws.amazon.com/ec2/).

### Billing and Cost Management / Account

En la consola, **Billing and Cost Management** incluye menú lateral: Bills, Payments, **Cost Explorer**, **Free Tier**, Budgets, **Pricing Calculator**, Savings Plans, Reservations, preferencias de pago/facturación, etc. La página **Account** muestra ID de cuenta, proveedor de servicio, ARN, datos de contacto, contactos alternativos (Billing/Operations/Security), tabla **AWS Regions** (Enabled/Disabled por región), acceso IAM a datos de facturación y enlaces a GovCloud, soporte, cierre de cuenta. Ver **[AWS-BILLING-Y-CUENTA-CONSOLA.md](../AWS-BILLING-Y-CUENTA-CONSOLA.md)** (estructura literal de la UI, sin datos personales).

## Por qué lo usamos

- **Servidores donde corre todo:** Jenkins, Gitea y la app POS (y opcionalmente Vantive) corren en instancias EC2. Es el lugar donde se ejecuta el pipeline y los servicios.
- **Objetivo de automatización:** La infra se crea con IaC (Terraform o Pulumi); EC2 es el recurso que aloja el software open source (Jenkins, Gitea, Docker). No sustituimos open source por servicios propietarios; usamos EC2 como “metal” gestionado.
- **Estándar y sin lock-in:** Las APIs de AWS son públicas; la lógica de negocio está en código (Jenkinsfile, Maven, Git). Cambiar de región o de cuenta es reproducir Terraform/Pulumi en otra cuenta.

## Open source / estándar

- EC2 es un **servicio gestionado** de AWS (no es software que instalas; es IaaS). Lo usamos como capa de computación estándar; el stack encima (OS, Java, Jenkins, Gitea, Docker) es open source.
- **Documentación:** [AWS EC2](https://aws.amazon.com/ec2/), [Amazon Linux 2023](https://docs.aws.amazon.com/linux/al2023/latest/ug/what-is-amazon-linux.html).

## Cómo replicar

1. **Consola:** Navegador → [AWS Console](https://console.aws.amazon.com/) → EC2 → Instances (región us-east-2 o la que uses).
2. **Crear instancias:** Opción A) Terraform: `cd manifests/terraform/jenkins-aws`, `terraform init`, `terraform apply`; Opción B) Manual: Launch Instance, AMI Amazon Linux 2023, t3.micro, security group con 22, 8080, 3000, 8111 según necesidad.
3. **Qué ves en la consola:** EC2 → Instances: tabla con columnas Name, Instance ID, Instance state, Instance type, Status check, Availability Zone, Public IPv4 DNS, Public IPv4 address, Elastic IP, Monitoring, Security group name, Key name, Launch time, Platform details, Managed, Operator. Botones: Connect, Instance state, Actions, Launch instances. Al seleccionar una instancia, panel inferior con pestañas **Details** (Instance summary: Public/Private IP, VPC, Subnet, Instance details: AMI, Launch time, etc.), **Status and alarms**, **Monitoring**, **Security**, **Networking**, **Storage**, **Tags**. Cabecera: región (p. ej. United States (Ohio) = us-east-2), cuenta.
4. **Security Groups:** EC2 → Security Groups. Tabla: Security group ID, Security group name, VPC ID, Description, Inbound/Outbound rules count. En FastFlow: fastflow-jenkins-sg, fastflow-gitea-sg, fastflow-pos-sg, gitea-sg, vantive-sg, launch-wizard-1, default. Editar inbound rules para 22 (SSH), 8080 (Jenkins), 3000 (Gitea), 8111 (POS). Ver [../URLS-Y-EC2-PRUEBAS.md](../URLS-Y-EC2-PRUEBAS.md) y [../DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../DOMINIO-NAMECHEAP-UNCLIC-EC2.md).
5. **SSH:** Tras conectar, banner Amazon Linux 2 (aviso AL2 End of Life 2026-06-30; recomendación Amazon Linux 2023). Prompt: `[ec2-user@ip-10-0-1-39 ~]$` (hostname con IP privada).

## Inputs y comandos (referencia)

| Consola | Comando o acción | Input / nota |
|---------|-------------------|--------------|
| Navegador | Abrir https://console.aws.amazon.com/ec2/ | Región: us-east-2 (o la tuya) |
| AWS Console | EC2 → Instances → Launch instance | Name: fastflow-jenkins-controller, AMI: Amazon Linux 2023, t3.micro |
| AWS Console | Security groups → Edit inbound rules → Add rule | Type: Custom TCP, Port: 8080, Source: 0.0.0.0/0 (o tu IP) |
| Terminal (local) | `ssh -i clave.pem ec2-user@<Public-IP>` | Conectar por SSH (sustituir clave e IP) |

URLs:

- AWS Console EC2: `https://console.aws.amazon.com/ec2/home?region=us-east-2#Instances:`

## Enlaces

- **Web AWS:** [aws.amazon.com](https://aws.amazon.com) — portada, Products, Solutions, Pricing, Free Tier, Sign in to console.
- [Billing y Account (consola)](../AWS-BILLING-Y-CUENTA-CONSOLA.md)
- [Capítulo servicios core AWS](../CAPITULO-SERVICIOS-CORE-AWS.md)
- [URLs y EC2 para probar](../URLS-Y-EC2-PRUEBAS.md)
- [Terraform Jenkins/EC2](../../../manifests/terraform/jenkins-aws/) (en toolkit-fastflow)
- [Tutorial de replicación visual](../REPLICAR-TUTORIAL-VISUAL.md)

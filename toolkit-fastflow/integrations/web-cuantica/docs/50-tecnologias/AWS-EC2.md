# AWS EC2 — Por qué, estándar abierto y cómo replicar

## Cuántas instancias tiene que crear George

**Respuesta directa:** para el flujo **FastFlow** (Jenkins → Gitea → POS en EC2), George debe crear **3 instancias EC2** (una por rol):

| # | Rol | Nombre sugerido (tag Name) | Security group típico | Puertos relevantes |
|---|-----|------------------------------|------------------------|---------------------|
| 1 | **Jenkins** | `fastflow-jenkins-controller` | `fastflow-jenkins-sg` | 22 (SSH), 8080 (Jenkins) |
| 2 | **Gitea** | `fastflow-gitea` | `fastflow-gitea-sg` | 22, 3000 (Gitea HTTP) |
| 3 | **POS** | `fastflow-pos` | `fastflow-pos-sg` | 22, 8111 (app POS) |

- **Tipo:** `t3.micro` (o equivalente Free Tier) y **Amazon Linux 2023** salvo que la guía única indique otra AMI.
- **Región:** la misma para las tres (ej. **us-east-2** Ohio).

**No** hacen falta más de 3 EC2 para montar el pipeline descrito en la guía única. Servicios extra (otra demo, landing, Gitea legacy, etc.) son **opcionales** y suman instancias.

### Ejemplo en una cuenta real (referencia: 6 instancias)

En una cuenta de equipo puede haber **6** EC2 en paralelo: las **3 núcleo** más **3 opcionales/legacy**:

| Name (consola) | Rol | Nota |
|----------------|-----|------|
| `fastflow-jenkins-controller` | Jenkins | Núcleo |
| `fastflow-gitea` (fastflow-gitea-sg) | Gitea | Núcleo |
| `fastflow-pos` | POS | Núcleo |
| `fastflow-gitea` (gitea-sg) | Gitea | **Legacy / duplicado** de un despliegue anterior; George **no** necesita dos Gitea para FastFlow. |
| `fastflow-pos-demo` | POS demo | Opcional |
| *(otra)* | Landing Next.js / sitio estático | Opcional (Nginx en EC2 aparte) |

En la consola verás algo como **Instances (6/6)** si tienes ese despliegue completo; **George solo necesita alinear 3** con la guía única.

---

## Por qué lo usamos

- **Dónde corre todo:** Jenkins, Gitea y el POS viven en EC2; ahí se ejecuta el pipeline.
- **IaC:** Terraform/Pulumi crean la infra; EC2 aloja el software open source (Jenkins, Gitea, Docker). EC2 = computación; no sustituimos el stack open source por servicios propietarios de AWS para el core del flujo.
- **Sin lock-in de lógica:** Jenkinsfile, Maven, Git siguen siendo portables; otra cuenta/región = repetir IaC.

## Open source / estándar

- EC2 es **IaaS** (servicio de AWS), no un paquete open source. Encima instalas **OS + Java + Jenkins + Gitea + Docker** (open source).
- Docs: [AWS EC2](https://aws.amazon.com/ec2/), [Amazon Linux 2023](https://docs.aws.amazon.com/linux/al2023/latest/ug/what-is-amazon-linux.html).

## Cómo replicar (resumen)

1. **Consola:** [EC2 → Instances](https://console.aws.amazon.com/ec2/) (elige región, ej. **us-east-2**).
2. **Opción A — Terraform:** `cd` a `toolkit-fastflow/manifests/terraform/jenkins-aws` → `terraform init` → `terraform apply` (ajusta variables según el README del módulo).
3. **Opción B — Manual:** **Launch instance** ×3 (una por tabla de arriba): AMI **Amazon Linux 2023**, **t3.micro**, security group con **22** + el puerto del servicio (8080 / 3000 / 8111). Restringe origen (tu IP) en producción; `0.0.0.0/0` solo para pruebas controladas.
4. **Security groups:** nombres de ejemplo en FastFlow: `fastflow-jenkins-sg`, `fastflow-gitea-sg`, `fastflow-pos-sg`. Más detalle: [URLS-Y-EC2-PRUEBAS.md](../20-operaciones/URLS-Y-EC2-PRUEBAS.md), [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md).
5. **SSH:** `ssh -i clave.pem ec2-user@<Public-IPv4>`. Si ves banner **Amazon Linux 2**, considera migrar a **AL2023** (AL2 tiene EOL anunciado).

### Consola AWS (portada y facturación) — breve

- Portada pública: [aws.amazon.com](https://aws.amazon.com) → **Sign in to console** para ir a la consola.
- **Billing / Account** (menú de la consola): facturas, Free Tier, presupuestos; estructura de pantallas: [AWS-BILLING-Y-CUENTA-CONSOLA.md](../90-archivo/AWS-BILLING-Y-CUENTA-CONSOLA.md).

## Inputs y comandos (referencia)

| Dónde | Acción | Nota |
|--------|--------|------|
| Navegador | Abrir `https://console.aws.amazon.com/ec2/home?region=us-east-2#Instances:` | Cambia región si no usas us-east-2 |
| EC2 | Launch instance (×3 para núcleo) | Nombres: ver tabla “George” arriba |
| EC2 → Security groups | Inbound: 22, 8080 / 3000 / 8111 según instancia | Origen restrictivo cuando puedas |
| Terminal local | `ssh -i clave.pem ec2-user@<IP-pública>` | Sustituir clave e IP |

## Enlaces

- [Billing y Account (consola)](../90-archivo/AWS-BILLING-Y-CUENTA-CONSOLA.md)
- [Capítulo servicios core AWS](../90-archivo/CAPITULO-SERVICIOS-CORE-AWS.md)
- [URLs y EC2 para probar](../20-operaciones/URLS-Y-EC2-PRUEBAS.md)
- [Terraform Jenkins/EC2](../../../../manifests/terraform/jenkins-aws/)
- [Tutorial de replicación visual](../90-archivo/REPLICAR-TUTORIAL-VISUAL.md)

# Documentación por tecnología — FastFlow (solo open source)

Cada documento en esta carpeta corresponde a **una tecnología** usada en el proyecto. En todos se sigue la misma estructura:

1. **Por qué la usamos** — Qué problema resuelve y cómo encaja en el objetivo de **automatizar con open source**.
2. **Open source** — Licencia y proyecto upstream; confirmación de que es solo open source (o servicio estándar sin lock-in donde aplica).
3. **Cómo replicar** — Pasos concretos para instalar/configurar solo esta tecnología; comandos, inputs y referencias a consolas.
4. **Inputs y comandos** — Lista o tabla de comandos ejecutados, en qué consola y con qué inputs (para copiar/pegar).

---

## Índice de documentos

| Archivo | Tecnología | Consola principal |
|---------|------------|------------------|
| [JENKINS.md](JENKINS.md) | Jenkins | Terminal (EC2), navegador (Jenkins UI :8080) |
| [GITEA.md](GITEA.md) | Gitea | Terminal (EC2), navegador (Gitea :3000) |
| [DOCKER.md](DOCKER.md) | Docker | Terminal (EC2) |
| [TERRAFORM.md](TERRAFORM.md) | Terraform | Terminal (local) |
| [PULUMI.md](PULUMI.md) | Pulumi | Terminal (local) |
| [LOCALSTACK.md](LOCALSTACK.md) | LocalStack | Terminal (local), Docker |
| [AWS-EC2.md](AWS-EC2.md) | AWS EC2 | AWS Console (navegador) |
| [MAVEN-JAVA.md](MAVEN-JAVA.md) | Maven + Java | Terminal (EC2, pipeline) |
| [NAMECHEAP-DNS.md](NAMECHEAP-DNS.md) | Namecheap DNS | Namecheap (navegador) |
| [CLOUDCRAFT.md](CLOUDCRAFT.md) | Cloudcraft | Navegador (app.cloudcraft.co), AWS IAM Console |
| [NEXTJS-UNCLIC.md](NEXTJS-UNCLIC.md) | Next.js (UnClic) | Terminal (local), navegador (:3002) |
| [SST.md](SST.md) | SST | Terminal (local) |
| [KUBERNETES.md](KUBERNETES.md) | Kubernetes | Terminal (kubectl), AWS EKS o cluster |

---

## Objetivo común

Todas estas tecnologías se usan para **automatizar** el flujo de desarrollo y despliegue (Pipeline as Code, CI/CD) usando **solo open source** (o estándares abiertos). El tutorial de replicación completo está en [../90-archivo/REPLICAR-TUTORIAL-VISUAL.md](../90-archivo/REPLICAR-TUTORIAL-VISUAL.md).

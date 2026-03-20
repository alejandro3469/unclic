# Documentación por tecnología y replicación completa

**Objetivo del proyecto:** Automatizar el ciclo de desarrollo y despliegue (Pipeline as Code, CI/CD) usando **solo software open source**. Cada tecnología que usamos está documentada por separado (por qué la usamos, cómo encaja en ese objetivo, cómo replicarla) y existe un **tutorial de replicación visual** con consolas, comandos, URLs y ayudas visuales (HTML/botones) para seguir cada paso sin perderse.

---

## Índice

1. [Por qué esta documentación](#por-qué-esta-documentación)
2. [Documento por tecnología](#documento-por-tecnología)
3. [Tutorial de replicación visual](#tutorial-de-replicación-visual)
4. [Dónde colocar assets](#dónde-colocar-assets)

---

## Por qué esta documentación

- **Un doc por tecnología:** Para cada herramienta (Jenkins, Gitea, Docker, AWS, etc.) queda claro **por qué** la usamos, que es **open source** (o servicio estándar sin vendor lock-in donde aplica), y **cómo replicar** solo esa pieza.
- **Un tutorial de replicación:** Un único flujo paso a paso que indica **en qué consola** estás (terminal, AWS Console, Namecheap, Jenkins UI, etc.), **qué ves** al abrirla, **qué comando o clic** ejecutar, **qué URL** abrir, y ayudas visuales (botones, pantallas en HTML o referencias a capturas) para no depender solo de texto.
- **Inputs y comandos reales:** Se documentan los inputs exactos, comandos ejecutados y en qué consola se ejecuta cada uno, para poder copiar/pegar y replicar.

---

## Documento por tecnología

Cada archivo en **docs/tecnologias/** explica:

| Doc | Tecnología | Por qué la usamos | Open source | Cómo replicar (resumen) |
|-----|------------|-------------------|-------------|-------------------------|
| [JENKINS.md](tecnologias/JENKINS.md) | Jenkins | Orquestación del pipeline (build, test, deploy); Pipeline as Code en Git | Sí (MIT) | Instalación en EC2, Java 17, puerto 8080, job Pipeline desde Gitea |
| [GITEA.md](tecnologias/GITEA.md) | Gitea | Repositorio Git self-hosted; fuente única del código para Jenkins | Sí (MIT) | Docker en EC2, puerto 3000, repos pos-online y generic-model |
| [DOCKER.md](tecnologias/DOCKER.md) | Docker | Contenedores para Gitea, imágenes del POS, registry | Sí (Apache 2.0) | Instalación en EC2, docker-compose para Gitea, build/push a registry |
| [TERRAFORM.md](tecnologias/TERRAFORM.md) | Terraform | IaC para EC2, VPC, security groups; reproducir infra igual en cualquier cuenta | Sí (Mozilla 2.0) | apply en manifests/terraform/jenkins-aws, outputs para IPs |
| [PULUMI.md](tecnologias/PULUMI.md) | Pulumi | Alternativa IaC (código en TypeScript/Go); mismo objetivo que Terraform | Sí (Apache 2.0) | manifests/pulumi/jenkins-aws, pulumi up con stack dev |
| [LOCALSTACK.md](tecnologias/LOCALSTACK.md) | LocalStack | Emular AWS en local; ahorro y pruebas sin tocar la nube real | Sí (Apache 2.0) | Docker o CLI, puerto 4566, awslocal o endpoints en Pulumi/Terraform |
| [AWS-EC2.md](tecnologias/AWS-EC2.md) | AWS EC2 | Servidores donde corren Jenkins, Gitea, POS; estándar de facto en la nube | Servicio gestionado (APIs abiertas) | Consola EC2, instancias, security groups, IPs; enlazado a Terraform/Pulumi |
| [MAVEN-JAVA.md](tecnologias/MAVEN-JAVA.md) | Maven + Java | Build del POS (Java); Maven define dependencias y ciclo de vida | Sí (Apache 2.0 / Eclipse) | Java 17 (Corretto), mvn test / package en pipeline |
| [NAMECHEAP-DNS.md](tecnologias/NAMECHEAP-DNS.md) | Namecheap DNS | Dominio unclic.consulting y subdominios (jenkins, gitea, pos) a IPs EC2 | Servicio de dominio | Advanced DNS, registros A para jenkins, gitea, pos, www |
| [CLOUDCRAFT.md](tecnologias/CLOUDCRAFT.md) | Cloudcraft | Diagramas de arquitectura AWS conectados a la infra real (Live) | SaaS (Datadog) | Integración AWS (rol IAM), blueprint, share URL; opcional para doc |
| [NEXTJS-UNCLIC.md](tecnologias/NEXTJS-UNCLIC.md) | Next.js (UnClic) | Landing y UI de demos (Jenkins, Gitea, arquitectura, POS) | Sí (MIT) | Proyecto unclic, npm run dev, variables Cloudcraft opcionales |
| [SST.md](tecnologias/SST.md) | SST (Serverless Stack) | Opcional: serverless (Lambda, S3) con IaC tipo Pulumi | Sí (MIT) | manifests/sst, sst dev contra AWS o LocalStack |
| [KUBERNETES.md](tecnologias/KUBERNETES.md) | Kubernetes | Opcional: orquestación de contenedores en cluster | Sí (Apache 2.0) | deploy/k8s, aplicar manifests cuando el despliegue sea en K8s |

En **docs/tecnologias/README.md** está el índice de todos los docs y la convención (por qué, open source, cómo replicar).

---

## Tutorial de replicación visual

**[REPLICAR-TUTORIAL-VISUAL.md](REPLICAR-TUTORIAL-VISUAL.md)** (y opcionalmente **deploy/replicar-tutorial-visual.html** para ver las ayudas visuales en navegador) es el tutorial que:

- Indica **en qué consola** estás en cada paso (Terminal local, AWS Console, Namecheap, Jenkins, Gitea, etc.).
- Describe **qué ves** al abrir esa consola (o enlaza a un fragmento HTML que simula botones/pantallas).
- Da **comandos exactos** o **inputs** a teclear, y **URLs** a abrir.
- Incluye **ayudas visuales**: botones o pantallas replicadas en HTML (o placeholders para tus capturas) para que el usuario sepa dónde hacer clic.

Orden típico del tutorial:

1. Cuenta AWS y consola EC2  
2. Terraform (o Pulumi) para crear instancias  
3. Namecheap: DNS (A records)  
4. Terminal: SSH a EC2, instalar Java, Jenkins, Maven, Docker  
5. Jenkins UI: desbloquear, crear job Pipeline, apuntar a Gitea  
6. Gitea: instalar (Docker), crear repos, push desde local  
7. Verificar: URLs (jenkins.unclic.consulting:8080, gitea.unclic.consulting:3000, POS :8111)

Cada paso puede tener un bloque **Consola**, **Qué ves**, **Comando/Acción**, **URL**, **Ayuda visual (HTML o asset)**.

---

## Dónde colocar assets

- **Capturas de pantalla:** Por ejemplo `docs/tecnologias/assets/` o `docs/assets/replicar/` (p. ej. `aws-console-ec2-list.png`, `jenkins-new-item.png`). En el tutorial se referencian con `![descripción](assets/nombre.png)`.
- **HTML de pantallas simuladas:** En **deploy/** (p. ej. `deploy/replicar-tutorial-visual.html`) o en `docs/tecnologias/` como fragmentos incrustables (botones, tablas que imitan la consola AWS o Jenkins).
- Cuando pases assets (imágenes, HTML), se enlazarán desde REPLICAR-TUTORIAL-VISUAL.md y desde cada doc de tecnología si aplica.

---

## Resumen

| Qué | Dónde |
|-----|--------|
| Índice y objetivo (open source, replicar) | Este doc (DOCUMENTACION-POR-TECNOLOGIA-Y-REPLICACION.md) |
| Por tecnología (por qué, open source, replicar) | docs/tecnologias/*.md |
| Tutorial paso a paso con consolas, comandos, URLs, ayudas visuales | REPLICAR-TUTORIAL-VISUAL.md (+ deploy/replicar-tutorial-visual.html si se usa) |
| Assets (capturas, HTML de botones/pantallas) | docs/tecnologias/assets/ o docs/assets/replicar/ (según lo que pases) |

# Web Cuántica — replicar Gitea + Jenkins + dominio propio (`webcuantica.com`)

**Para:** George Aguilar y el equipo que replique el laboratorio FastFlow con **dominio propio** en **Namecheap** y servidores en **AWS**.

**Guía única (todo en un solo documento):**  
→ **[GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** — [índice navegable](GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md#indice) y sección **§12** (dominio **`webcuantica.com`**).

Ahí encontrarás, en orden:

| Tema | Dónde en la guía |
|------|------------------|
| Variables (`ROOT_DOMAIN`, subdominios `gitea` / `jenkins`) | **§12.1** |
| Namecheap → registros **A** para subdominios | **§12.2** |
| EC2 Gitea, **Elastic IP**, **Security Group** (22, 3000, 80, 443) | **§12.3** |
| Docker + **docker-compose** Gitea | **§12.4** |
| Asistente inicial Gitea (dominio + Base URL con `:3000`) | **§12.5** |
| **HTTPS público** (Nginx + Let’s Encrypt en Amazon Linux 2) + **ROOT URL** `https://gitea.../` | **§12.6** |
| Usuario operativo + **token** y credencial Jenkins | **§12.7** |
| **Primera vez en Jenkins** (unlock, plugins, admin, Jenkins URL HTTPS) | **§12.8** |
| **URLs para probar** | **§12.9** |
| Orden de ejecución de punta a punta | **§12.10** |
| Plantilla shell para imprimir DNS | **§12.11** |
| **POS + registry** (cerrar Gitea + Jenkins + POS operativos) | **§12.12** |
| Automatización futura (Terraform/Ansible) | **§12.13** |

El resto del flujo (Maven, Docker en Jenkins, swap t3.micro, job *Pipeline from SCM*, Postman, rollback) está en las secciones **§0–§11** de la misma guía (sustituye `unclic.consulting` por `webcuantica.com` donde aplique).

**Documentos de apoyo:** [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md), [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md), [HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md](HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md), [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

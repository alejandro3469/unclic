# Web Cuántica — replicar Gitea + Jenkins + dominio propio (`webcuantica.com`)

**Mapa del repo:** [EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md](EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md) · **Procedimiento:** [GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md) ([índice](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md#indice-de-navegacion), **§12** = `webcuantica.com`).

**Toolkit:** `repo-pos-fastflow/` (POS, `origin` → Gitea) + `smartbussiness-generic-model/` (Maven obligatorio del POS, `gitea` → Gitea) + `scripts/push-generic-model-to-gitea.sh`. Flujo Jenkins: [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md) · guía **§4** + **§5.3**. Manual en EC2: [EC2-INSTALAR-GENERIC-MODEL.md](../20-operaciones/EC2-INSTALAR-GENERIC-MODEL.md). Git: [REPOS-LOCALES-Y-GITEA.md](../20-operaciones/REPOS-LOCALES-Y-GITEA.md).

**§12 en la guía (referencia rápida):**

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
| **generic-model** (Gitea + job Jenkins → `~/.m2`) | **[GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md)** y guía **§4** + **§5.3** |

**§0–§11** (misma guía): Maven, Docker, swap, jobs, Postman, rollback (`unclic.consulting` → `webcuantica.com`).

**Apoyo:** [INSTALAR-GITEA-SELF-HOSTED.md](../20-operaciones/INSTALAR-GITEA-SELF-HOSTED.md) · [HTTPS-UNCLIC-GITEA-JENKINS.md](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md) · [HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md](../20-operaciones/HTTPS-JENKINS-AMAZON-LINUX2-NGINX-CERTBOT.md) · [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md).

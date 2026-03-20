# Índice del know-how — qué está documentado y dónde

Resumen de **todo el know-how documentado** en este repo (web-cuantica y kings-joers/vantive). Para estado actual (IPs, servicios) y pendientes: [DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md](DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md).

---

## 1. pos-online y FastFlow (web-cuantica)

| Know-how | Dónde está |
|----------|------------|
| Replicar todo desde cero (EC2, DNS, Jenkins, Gitea, pipeline) | [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md), [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md), [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md) |
| Instalar Jenkins (Java, Maven, job, credenciales) | [instalacion/INSTALAR-JENKINS.md](instalacion/INSTALAR-JENKINS.md), [instalacion/REQUISITOS.md](instalacion/REQUISITOS.md) |
| Instalar Docker en la EC2 de Jenkins | [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) |
| Añadir swap en t3.micro (evitar OOM) | [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md) |
| Push a Gitea (pos-online, generic-model), remotes | [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md), [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) |
| Crear y configurar job Jenkins (Pipeline from SCM, Git, Script Path) | [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md), REPLICAR-UNCLIC-COMPLETO.md Fase 6 |
| Credenciales Gitea/Jenkins, usuarios (incl. usuario solo un repo) | [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md); ver también DOCUMENTACION-MAESTRA §1.2 |
| Ver que pos-online está corriendo, ver logs, enviar peticiones | [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md) |
| Entender el console output del pipeline (cada stage) | [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md) |
| Generar paquetes de entrega (delivery), checklist y auditoría | `scripts/build-delivery-packages.sh`, [delivery/ANTES-DE-SUBIR-A-GITEA.md](../delivery/ANTES-DE-SUBIR-A-GITEA.md); DOCUMENTACION-MAESTRA §2.1 |
| HTTPS en Gitea y Jenkins (Nginx, Certbot) | [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md), [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md) |
| Instalar generic-model en EC2 o vía job Jenkins | [EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md), [GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md) |
| Dependencia generic-model, contexto JDE/Oracle | [DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md) |
| Validar Jenkinsfile, Terraform, K8s, simular pipeline | `scripts/validate-jenkinsfile.sh`, `run-all-validations.sh`, etc.; [tests-y-validacion/TESTS-Y-VALIDACION-LOCAL.md](tests-y-validacion/TESTS-Y-VALIDACION-LOCAL.md) |
| Recuperar docs que solo están en commits anteriores | [COMPATIBILIDAD-Y-HISTORIAL.md](COMPATIBILIDAD-Y-HISTORIAL.md), [historial/README.md](historial/README.md) |
| Desbloquear Jenkins (contraseña inicial, plugins) | [JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md](JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md) |
| DNS Namecheap, registros A (jenkins, gitea, vantive) | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md), [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) |
| Servidores (Jenkins, Gitea, Vantive), levantar Vantive | [SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md](SERVIDORES-UNCLIC-Y-LEVANTAR-VANTIVE.md) |
| Facturación AWS, Free Tier, presupuestos | [CAPITULO-FACTURACION-Y-PRECIOS.md](CAPITULO-FACTURACION-Y-PRECIOS.md) |
| Estado actual (IPs, URLs, servicios), scripts, delivery | [DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md](DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md) §1–§3; [AGENTS.md](../AGENTS.md) |

---

## 2. Vantive (kings-joers/vantive)

| Know-how | Dónde está |
|----------|------------|
| Origen y propósito del proyecto (sin inventar) | `kings-joers/vantive/docs/ORIGEN-Y-PROPOSITO-DEL-PROYECTO.md` |
| Desplegar (build npm, rsync a EC2, script) | `kings-joers/vantive/docs/deploy/COMO-DESPLEGAR.md` |
| Probar (build local, EC2 por IP/dominio, levantar instancia y Nginx) | `kings-joers/vantive/docs/probar/COMO-PROBAR.md` |
| Asegurar la instancia (Security Group, SSH, Nginx, fail2ban) | `kings-joers/vantive/docs/seguridad/ASEGURAR-INSTANCIA.md` |
| HTTPS perpetuo (Certbot, renovación automática, DNS) | `kings-joers/vantive/docs/https/HTTPS-PERPETUO.md` |
| Free Tier, costes y límites (solo datos verificables) | `kings-joers/vantive/docs/costes-y-limites/AWS-FREE-TIER-Y-LIMITES-REALES.md` |
| Jenkins + AWS (EC2 o S3/CloudFront) para Vantive | `kings-joers/vantive/docs/JENKINS-VANTIVE-AWS.md`, `kings-joers/vantive/docs/JENKINS-VANTIVE-AWS.md`, `kings-joers/vantive/docs/PROBAR-LOCAL-DEPLOY-VANTIVE.md` |
| Subdominio, puertos, Allego | `kings-joers/vantive/docs/SUBDOMINIO-VANTIVE-ALLEGO.md` |
| **Compatibilidad Next.js con Allego (oficial), tipo de paquete de entrega (no .jar)** | `kings-joers/vantive/docs/ALLEGO-COMPATIBILIDAD-Y-ENTREGA-OFICIAL.md` (SCORM, LTI, enlace; qué entregar: out/, ZIP SCORM, URL+LTI) |
| Script deploy desde web-cuantica | `scripts/deploy-vantive-ec2.sh`; [DESPLIEGUE-URGENTE-VANTIVE-AWS-GRATIS.md](DESPLIEGUE-URGENTE-VANTIVE-AWS-GRATIS.md), [VANTIVE-HTTPS-Y-SEGURIDAD.md](VANTIVE-HTTPS-Y-SEGURIDAD.md) |

---

## 3. Know-how no documentado (opcional)

Temas para los que **no hay un how-to dedicado**; opcional documentar más adelante:

| Tema | Nota |
|------|------|
| Rotar o sustituir la clave PEM de una EC2 | No hay doc paso a paso; se asume nueva key pair en AWS y reconfigurar SSH. |
| Crear usuario en Gitea y dar acceso solo a un repo | Mencionado en DOCUMENTACION-MAESTRA §1.2 (Settings → Collaborators); no hay guía con capturas. |
| Runbook cuando el pipeline falla en un stage concreto | POS-ONLINE-PIPELINE-LOGS-EXPLICADOS y POS-ONLINE-VER-INSTANCIA (§2.5) ayudan; no hay "si falla en X, hacer Y" por stage. |
| Recuperar acceso a la cuenta AWS | Existe [RECUPERAR-ACCESO-AWS-Y-PONER-EN-PRODUCCION.md](RECUPERAR-ACCESO-AWS-Y-PONER-EN-PRODUCCION.md). |

---

**Resumen:** El know-how de pos-online, FastFlow, Gitea, Jenkins, delivery, HTTPS, generic-model, DNS, servidores y facturación está documentado en docs/ (y en la documentación maestra). El de Vantive (origen, deploy, probar, seguridad, HTTPS, costes, Jenkins+AWS, subdominio) está en kings-joers/vantive/docs/ y en los docs compartidos de web-cuantica. Los puntos del §3 son los únicos sin how-to dedicado.

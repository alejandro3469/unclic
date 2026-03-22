# Pos-online: documentación FastFlow

Documentación para el **repo pos-online**: Jenkins, registry, pipeline y despliegue. Sin referencias a otros productos ni clientes.

---

## Documentos (solo lo esencial)

| Documento | Uso |
|-----------|-----|
| **[GUIA-FLUJO-FINAL-POS-ONLINE.md](GUIA-FLUJO-FINAL-POS-ONLINE.md)** | Flujo completo: qué comando, qué output, dónde revisar (build, app, pipeline local, Jenkins, registry, dashboard). |
| [POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md](POS-ONLINE-IMPLEMENTAR-JENKINS-REGISTRY-DEMO.md) | Paso a paso: implementar Jenkins + Registry + demo local en pos-online. |
| [FLUJO-COMMIT-Y-USUARIO-FINAL.md](FLUJO-COMMIT-Y-USUARIO-FINAL.md) | Flujo de commit (desarrollador) y flujo de usuario final (cómo probar y presentar). |
| [CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md](CHECKLIST-POS-ONLINE-REPO-USUARIO-FINAL.md) | Checklist: repo listo para usuario final. |
| [INVENTARIO-POS-ONLINE-REPO-USUARIO-FINAL.md](INVENTARIO-POS-ONLINE-REPO-USUARIO-FINAL.md) | Inventario de lo que debe tener el repo pos-online. |

---

## AWS gratis: cada quien con su sistema retail automatizado (FastFlow como en producción)

**Cada integrante del equipo y cada cliente** tiene **su propio sistema retail automatizado con FastFlow, gratis, operando como en producción** en AWS:

→ **[GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md](../20-operaciones/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md)** (en `docs/20-operaciones/`)

Ahí: instalar (OS + Jenkins en la instancia), **ver** cada parte (Jenkins, app, registry), **usar** y **probar** cada parte; flujo igual que en producción (commit → pipeline → registry → app). Todo gratis en Free Tier.

Terraform (lista única): **toolkit-fastflow/manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md**.

---

## Otra documentación

- **Pipeline y registry:** docs/40-pipeline-registry/
- **Instalación:** docs/30-instalacion/INSTALAR-JENKINS.md, REQUISITOS.md
- **Copia del proyecto:** docs/90-archivo/copia/COPIA-POS-ONLINE-FASTFLOW.md
- Índice general: [docs/README.md](../README.md)

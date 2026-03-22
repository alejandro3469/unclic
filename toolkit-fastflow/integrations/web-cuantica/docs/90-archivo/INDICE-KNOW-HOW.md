# Índice del know-how — qué está documentado y dónde

Resumen del know-how en **`integrations/web-cuantica`** (POS Online + FastFlow). Índice maestro sin datos sensibles: [DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md](DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md).

---

## 1. pos-online y FastFlow

| Know-how | Dónde está |
|----------|------------|
| Replicar desde cero (EC2, DNS, Jenkins, Gitea, pipeline) | [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](../20-operaciones/REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md), [REPLICAR-UNCLIC-COMPLETO.md](../20-operaciones/REPLICAR-UNCLIC-COMPLETO.md), [REPLICAR-ESTADO-ACTUAL-INDICE.md](../20-operaciones/REPLICAR-ESTADO-ACTUAL-INDICE.md) |
| Instalar Jenkins (Java, Maven, job, credenciales) | [../30-instalacion/INSTALAR-JENKINS.md](../30-instalacion/INSTALAR-JENKINS.md), [../30-instalacion/REQUISITOS.md](../30-instalacion/REQUISITOS.md) |
| Instalar Docker en la EC2 de Jenkins | [POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](../20-operaciones/POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md) |
| Añadir swap en t3.micro (evitar OOM) | [EC2-SWAP-T3MICRO.md](../20-operaciones/EC2-SWAP-T3MICRO.md) |
| Push a Gitea (pos-online, generic-model), remotes | [REPOS-LOCALES-Y-GITEA.md](../20-operaciones/REPOS-LOCALES-Y-GITEA.md), [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](../20-operaciones/PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) |
| Crear y configurar job Jenkins (Pipeline from SCM) | [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](../20-operaciones/JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md), [REPLICAR-UNCLIC-COMPLETO.md](../20-operaciones/REPLICAR-UNCLIC-COMPLETO.md) Fase 6 |
| Credenciales Gitea/Jenkins, usuarios | [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](../20-operaciones/CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md) |
| Ver pos-online corriendo, logs, peticiones | [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](../20-operaciones/POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md) |
| Console output del pipeline | [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](../20-operaciones/POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md) |
| Paquetes `delivery/`, auditoría antes de publicar | `scripts/build-delivery-packages.sh`, [delivery/ANTES-DE-SUBIR-A-GITEA.md](../../delivery/ANTES-DE-SUBIR-A-GITEA.md) |
| HTTPS Gitea/Jenkins | [HTTPS-UNCLIC-GITEA-JENKINS.md](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md), [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](../20-operaciones/REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md) |
| generic-model en EC2 / job Jenkins | [EC2-INSTALAR-GENERIC-MODEL.md](../20-operaciones/EC2-INSTALAR-GENERIC-MODEL.md), [GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md) |
| Dependencia generic-model, JDE/Oracle | [DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](../20-operaciones/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md) |
| Validaciones locales | [../70-tests/TESTS-Y-VALIDACION-LOCAL.md](../70-tests/TESTS-Y-VALIDACION-LOCAL.md), `scripts/run-all-validations.sh` |
| Recuperar docs antiguos | [COMPATIBILIDAD-Y-HISTORIAL.md](../20-operaciones/COMPATIBILIDAD-Y-HISTORIAL.md), [historial/README.md](historial/README.md) |
| Unlock Jenkins | [JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md](../20-operaciones/JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md) |
| DNS (Namecheap u otro), registros A jenkins / gitea | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md), [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](../20-operaciones/INFRAESTRUCTURA-UNCLIC-ACTUAL.md) |
| Landing Next.js (UnClic) | [../../unclic/README.md](../../unclic/README.md), [EC2-CREAR-LANDING-UNCLIC.md](../20-operaciones/EC2-CREAR-LANDING-UNCLIC.md) |
| Facturación AWS, Free Tier | [CAPITULO-FACTURACION-Y-PRECIOS.md](CAPITULO-FACTURACION-Y-PRECIOS.md) |
| Landing / demos en docs | [DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md](DOCUMENTACION-MAESTRA-INDICE-Y-PENDIENTE.md); [AGENTS.md](../../AGENTS.md) |

---

## 2. Know-how sin guía dedicada (opcional)

| Tema | Nota |
|------|------|
| Rotar clave PEM de EC2 | Procedimiento estándar AWS: nueva key pair y actualizar acceso SSH. |
| Usuario Gitea solo un repo | Gitea → repo → **Collaborators**. |
| Pipeline falla en un stage | Ver logs en [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](../20-operaciones/POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md). |
| Recuperar acceso AWS | [RECUPERAR-ACCESO-AWS-Y-PONER-EN-PRODUCCION.md](../20-operaciones/RECUPERAR-ACCESO-AWS-Y-PONER-EN-PRODUCCION.md). |

# Replicar el estado actual — índice de documentación

Punto de entrada para **reproducir todo lo que está montado ahora**: Jenkins (con HTTPS), Gitea, repos (pos-online y generic-model), pipelines con logs detallados, deploy en 8111, y flujo de trabajo local (dos niveles de Git, push a Gitea).

**Guía pos-online + FastFlow:** [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md) — EC2 Jenkins/Gitea, Java/Maven/Jenkins/Docker, swap, deploy manual, verificación. **Sustituye IPs y dominios** en cada doc por los tuyos.

---

## 1. Estado objetivo (plantilla)

| Componente | Qué montar | Dónde está documentado |
|------------|------------|-------------------------|
| **AWS** | Mínimo **2 EC2** (Jenkins + Gitea), región ej. us-east-2. Puertos: 22, 8080 (Jenkins), 3000 (Gitea); 443 si Nginx/HTTPS. Opcional 3.ª EC2 para POS. | [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 1, [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md), [50-tecnologias/AWS-EC2.md](../50-tecnologias/AWS-EC2.md) |
| **DNS** | Registros **A**: `jenkins` → IP Jenkins, `gitea` → IP Gitea. Opcional TXT (wildcard ACME). | [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 2, [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| **Jenkins (EC2)** | Java 17, Maven, Git, Jenkins, swap en t3.micro. HTTPS opcional (Nginx + Let's Encrypt). | [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 3, [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md), [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md) |
| **Gitea (EC2)** | Gitea en 3000 (o detrás de HTTPS). Repos: `TU_USUARIO/pos-online`, `TU_USUARIO/smartbussiness-generic-model`. | [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 4, [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md) |
| **Repos locales** | **repo-pos-fastflow** → Gitea (remote `origin`). **smartbussiness-generic-model** → Gitea (remote `gitea`). Repo padre (toolkit) ≠ repos de Gitea. | [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md) |
| **Push a Gitea** | pos-online: `cd repo-pos-fastflow` → `git push -u origin main`. generic-model: `cd smartbussiness-generic-model` → `git push -u gitea main`. | [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md), [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) § 2.1 |
| **Jenkinsfiles** | pos-online: logs por etapa, `mvn test -B` (sin -q), JUnit, **docker build por `sh`** (no plugin), deploy 8111, verify. generic-model: logs por etapa, mvn install. | [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) § 3, [repo-pos-fastflow/Jenkinsfile](../../repo-pos-fastflow/Jenkinsfile), [smartbussiness-generic-model/Jenkinsfile](../../smartbussiness-generic-model/Jenkinsfile) |
| **Jobs Jenkins** | generic-model-pipeline (instala en ~/.m2). pos-online-pipeline (Pipeline from SCM, repo pos-online, Script Path Jenkinsfile). Credenciales Gitea. | [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 6 |
| **Probar antes de push** | `mvn clean test && mvn package -DskipTests` en cada repo. | [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) § 1 |
| **Avisos Jenkins** | Built-in node, Java 17 EOL, Amazon Linux 2, CSP: se pueden ignorar al inicio; prioridad = pipeline completo. | [JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md](../90-archivo/JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md) |

---

## 2. Orden recomendado para replicar desde cero

1. **AWS y DNS** → [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fases 1 y 2.
2. **EC2 Jenkins** (Java, Maven, Git, Jenkins, swap) → Fase 3.
3. **HTTPS Jenkins** (certificado wildcard, Nginx, 443) → [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md) y [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md) (o [PASO-1-GUIA-HTTPS-DESDE-CERO.md](PASO-1-GUIA-HTTPS-DESDE-CERO.md)).
4. **EC2 Gitea** e instalación Gitea → Fase 4.
5. **Repos locales y push** (entrar en cada repo, push con `origin` o `gitea`) → [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md) y Fase 5 (ajustando comandos a “dos niveles de Git”).
6. **Credenciales y jobs en Jenkins** → Fase 6.
7. **Comprobaciones** → Fase 7 y [QUE-FALTA-PROBAR-JENKINS-UNCLIC.md](../90-archivo/QUE-FALTA-PROBAR-JENKINS-UNCLIC.md).

---

## 3. Índice completo de documentos por tema

### Infraestructura y DNS

| Documento | Para qué sirve |
|-----------|----------------|
| [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) | Referencia: qué existe, IPs, DNS, Security Groups, URLs. Actualizar cuando cambie algo. |
| [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) | Dominio unclic.consulting, registros A para jenkins y gitea, opciones HTTPS. |
| [URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md) | URLs de Jenkins y app POS (IP, puertos 8080, 8111). |

### HTTPS y certificados

| Documento | Para qué sirve |
|-----------|----------------|
| [REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md](REGISTRO-HTTPS-JENKINS-UNCLIC-EJECUTADO.md) | Lo ya hecho: cert wildcard, Nginx, Jenkins URL HTTPS. Comandos y salidas reales. |
| [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md) | Guía cert wildcard Let's Encrypt (DNS) para unclic.consulting y *.unclic.consulting. |
| [PASO-1-GUIA-HTTPS-DESDE-CERO.md](PASO-1-GUIA-HTTPS-DESDE-CERO.md) | HTTPS desde cero (conceptos, Certbot, Nginx). |
| [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md) | HTTPS para Gitea y Jenkins (opciones, proxy). |
| [HTTPS-WILDCARD-UNCLIC-REALIZADO-Y-PENDIENTE.md](HTTPS-WILDCARD-UNCLIC-REALIZADO-Y-PENDIENTE.md) | Resumen realizado vs pendiente (Jenkins, Gitea, otras EC2). |

### EC2 Jenkins: instalación y swap

| Documento | Para qué sirve |
|-----------|----------------|
| [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 3 | Java, Maven, Git, Jenkins, swap en la EC2 de Jenkins. |
| [../30-instalacion/INSTALAR-JENKINS.md](../30-instalacion/INSTALAR-JENKINS.md) | Instalación detallada de Jenkins. |
| [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md) | Swap 1 GB en t3.micro para evitar OOM en builds. |
| [JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md](JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md) | Desbloquear Jenkins (contraseña inicial, plugins). |
| [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md) | Pantallas: New Item, Configure (Pipeline from SCM, Git, Script Path). |

### Gitea y repos

| Documento | Para qué sirve |
|-----------|----------------|
| [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md) | Instalar Gitea (Docker o binario), dominio, primer arranque. |
| [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md) | Rutas locales, remotes (origin/gitea), dos niveles de Git, comandos push. |
| [PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md](PROBAR-LOCAL-ANTES-DE-SUBIR-PIPELINE.md) | Qué probar local (mvn test, package), comandos push con cd, cambios en Jenkinsfiles (logs, JUnit, docker por sh). |
| [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md) | Clonar pos-online y añadir remote Gitea. |
| [GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md) | generic-model en Gitea, script push, job Jenkins que instala en ~/.m2. |
| [EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md) | Instalar generic-model en EC2 a mano (alternativa al job). |

### Pipeline y Jenkins (jobs, avisos, prioridad)

| Documento | Para qué sirve |
|-----------|----------------|
| [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md) | **Replicar todo lo hecho:** EC2 Jenkins/Gitea, Docker, swap, Jenkinsfile con deploy manual (Approve Deploy), puerto 8111, verificar instancia; pasos numerados y checklist. |
| [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fases 5–7 | Push a Gitea, credenciales, jobs generic-model-pipeline y pos-online-pipeline, comprobaciones, errores frecuentes. |
| [JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md](../90-archivo/JENKINS-AVISOS-Y-PRIORIDAD-POS-PIPELINE.md) | Avisos (built-in node, Java 17 EOL, AL2, CSP): qué son, cuándo solucionar, prioridad = pipeline POS completo. |
| [QUE-FALTA-PROBAR-JENKINS-UNCLIC.md](../90-archivo/QUE-FALTA-PROBAR-JENKINS-UNCLIC.md) | Checklist: Jenkins, repo, credenciales, job, generic-model, HTTPS opcional. |
| [../40-pipeline-registry/JENKINS-PIPELINE-FASTFLOW.md](../40-pipeline-registry/JENKINS-PIPELINE-FASTFLOW.md) | Pipeline: test, package, imagen, registry. |
| [RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md) | Diagnóstico SSL, fallo pipeline, instancias pos/landing. |

### Tests, validación y flujo

| Documento | Para qué sirve |
|-----------|----------------|
| [../70-tests/TESTS-Y-VALIDACION-LOCAL.md](../70-tests/TESTS-Y-VALIDACION-LOCAL.md) | Scripts validación (Maven, Jenkinsfile, Terraform, K8s), simulación pipeline. |
| [VERIFICACION-FLUJO-UN-COMMIT-LEVANTA-INSTANCIA.md](VERIFICACION-FLUJO-UN-COMMIT-LEVANTA-INSTANCIA.md) | Comprobar que un commit recorre build → deploy → app en 8111. |

### Landings (EC2 adicionales)

| Documento | Para qué sirve |
|-----------|----------------|
| [EC2-CREAR-LANDING-UNCLIC.md](EC2-CREAR-LANDING-UNCLIC.md) | Crear EC2 (t3.micro) para landing; security groups 22/80/443; DNS; Nginx. Ver también [unclic/README.md](../../unclic/README.md). |

### Otros (AWS nueva cuenta, seguridad, registry)

| Documento | Para qué sirve |
|-----------|----------------|
| [PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](../00-inicio/PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md) | Cuenta AWS nueva: región, IAM, CLI, Terraform. |
| [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md) | Credenciales, usuarios, tokens Gitea. |
| [REGISTRY-EC2-GRATIS.md](REGISTRY-EC2-GRATIS.md) | Registry Docker en EC2 (opcional). |
| [../40-pipeline-registry/BUILD-REGISTRY-FASTFLOW.md](../40-pipeline-registry/BUILD-REGISTRY-FASTFLOW.md) | Build imagen y push a registry. |

---

## 4. Rutas locales (resumen)

- **Repo padre (toolkit):** no es el que sube a Gitea. `git status` en web-cuantica o raíz = este repo.
- **pos-online (Gitea):**  
  `cd .../toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow`  
  → `git push -u origin main`
- **generic-model (Gitea):**  
  `cd .../toolkit-fastflow/integrations/web-cuantica/smartbussiness-generic-model`  
  → `git push -u gitea main`

Ruta base absoluta (ejemplo): `<RUTA_MONOREPO>`.  
Detalle: [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md).

---

## 5. Índice general de la documentación

[README.md](../README.md) — Índice de toda la documentación (capítulos, requisitos, pipeline, pos-online, tests).

# Guía para agentes — pos-online + FastFlow (implementación a la medida)

Si eres un agente de IA trabajando en este repo, el contexto es **pos-online** con la **implementación FastFlow** (pipeline, registry, despliegue). FastFlow aquí es solo la implementación a la medida para pos-online y generic-model (JDE, Oracle, ambientes, arquitectura).

---

## Contexto

- **pos-online:** Aplicación (Maven/Java) que usa el negocio para ventas y operación en múltiples puntos.
- **generic model:** Repo en la **misma carpeta padre** que pos-online; **dependencia obligatoria**. El pipeline y la arquitectura deben considerarla.
- **FastFlow aquí:** Implementación a la medida: Jenkins, Docker, Registry (tags, rollback), Terraform, Kubernetes, dashboard mínima. Todo aplicado a pos-online y a su contexto (JD Edwards, Oracle, entornos, bases de datos, fase de arquitectura).

---

## Archivos clave

1. **docs/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md** — Generic model (ruta, obligatoriedad), JD Edwards, Oracle, entornos, bases de datos, fase de arquitectura.
2. **docs/pos-online/README.md** y **docs/pos-online/** — Índice, checklist, flujo commit, implementación Jenkins/Registry/demo para pos-online. Compatibilidad: rutas que existen en el repo; doc extra puede estar en historial de commits.
3. **docs/instalacion/INSTALAR-JENKINS.md** — Instalación Jenkins, credenciales, job Pipeline.
4. **docs/instalacion/REQUISITOS.md** — Requisitos (Java, Maven, Docker, Jenkins, etc.).
5. **docs/pipeline-y-registry/** — Pipeline, build imagen, registry, flujo commit a registry.
6. **Jenkinsfile.example** — Template de pipeline para pos-online (Maven + opcional Docker/registry).
7. **scripts/** — validate-jenkinsfile, validate-terraform, validate-k8s, test-registry, simulate-jenkins-pipeline, run-all-validations.
8. **deploy/terraform/**, **deploy/k8s/** — Infra y despliegue.
9. **docs/README.md** — Índice de toda la documentación.
10. **docs/COMPATIBILIDAD-Y-HISTORIAL.md** — Compatibilidad con pos-online (qué copiar, fusionar Jenkinsfile). Docs que solo están en el historial de commits: cómo recuperarlos con `git show <commit>:<ruta>`.
11. **docs/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md** — Guía única usuario final: Namecheap, EC2 (Jenkins/Gitea/POS), SG, variables job, consola Jenkins, Postman (`docs/postman/`), rollback registry (`repo-pos-fastflow/ROLLBACK-POS-DOCKER-REGISTRY.md`, `Jenkinsfile.rollback`).

---

## Comandos importantes

- Validar Jenkinsfile: `bash scripts/validate-jenkinsfile.sh`
- Validar Terraform: `bash scripts/validate-terraform.sh`
- Validar K8s: `bash scripts/validate-k8s.sh`
- Todas las validaciones: `bash scripts/run-all-validations.sh`
- En pos-online (tras copiar): `mvn test`; `./run-jenkins-pipeline-local.sh` si existe.

---

## Reglas rápidas

- No introducir referencias a productos o clientes ajenos a pos-online; mantener solo lo aplicable a pos-online, generic-model y contexto JDE/Oracle.
- Mantener la implementación alineada a pos-online, generic model y contexto JDE/Oracle/arquitectura.
- Al cambiar pipeline o scripts, documentar en docs/pos-online o docs/pipeline-y-registry según corresponda.

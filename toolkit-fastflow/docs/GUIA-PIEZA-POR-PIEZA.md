# Fast Flow Enterprise - Guia Pieza por Pieza (Plug-and-Play + Operacion)

Fecha: 2026-03-10

Objetivo:
- Explicar como funciona cada pieza del toolkit.
- Permitir implementacion plug-and-play sin perder entendimiento tecnico.
- Estandarizar operacion, validacion y troubleshooting.
- Operar con lenguaje atemporal e impersonal, sin dependencia de roles fijos.

Guia complementaria obligatoria:
- `toolkit-fastflow/docs/GUIA-PLANIFICACION-ATEMPORAL.md`

## 1) Vista general del sistema

El toolkit esta organizado en 5 capas:

1. Descubrimiento:
- `toolkit-fastflow/QUESTIONARIO-IMPLEMENTACION.md`
- `toolkit-fastflow/STACK-SOPORTADO.md`

2. Bootstrap:
- `toolkit-fastflow/installers/bootstrap-project.sh`
- `toolkit-fastflow/installers/bootstrap-server.sh`

3. Pipeline base (Con Calidad y Seguridad Embebida):
- `toolkit-fastflow/templates/common/Jenkinsfile` (Ciclo: Build -> Test -> Security -> Package -> Deploy)
- `toolkit-fastflow/templates/common/scripts/run-package-stage-local.sh`
- `toolkit-fastflow/templates/common/docs/cicd/INDICE_CI_CD.md`
- Estrategia de Testing: Unitarios, Integración y Seguridad (MockMvc, @WithMockUser).

4. Plataforma de despliegue:
- Kubernetes:
  - `toolkit-fastflow/manifests/kubernetes/deployment.yaml`
  - `toolkit-fastflow/manifests/kubernetes/service.yaml`
- Helm:
  - `toolkit-fastflow/manifests/helm/fastflow-chart/Chart.yaml`
  - `toolkit-fastflow/manifests/helm/fastflow-chart/values.yaml`
  - `toolkit-fastflow/manifests/helm/fastflow-chart/templates/deployment.yaml`
- Terraform:
  - `toolkit-fastflow/manifests/terraform/main.tf`
  - `toolkit-fastflow/manifests/terraform/jenkins-aws/...`
- Packer:
  - `toolkit-fastflow/manifests/packer/jenkins-controller.pkr.hcl`
  - `toolkit-fastflow/manifests/packer/jenkins-agent.pkr.hcl`
  - `toolkit-fastflow/manifests/packer/scripts/controller-setup.sh`
  - `toolkit-fastflow/manifests/packer/scripts/agent-setup.sh`

5. Playbooks por madurez y conocimiento curado:
- `toolkit-fastflow/stages/manual/README.md`
- `toolkit-fastflow/stages/monolito/README.md`
- `toolkit-fastflow/stages/microservicios/README.md`
- `toolkit-fastflow/stages/cloud/README.md`
- `toolkit-fastflow/docs/*.md`

## 2) Flujo end-to-end recomendado

1. Marco de trabajo:
- Alinear ciclo, escritura y protocolo comun con `GUIA-PLANIFICACION-ATEMPORAL.md`.

2. Discovery:
- Completar `QUESTIONARIO-IMPLEMENTACION.md`.
- Confirmar stack permitido en `STACK-SOPORTADO.md`.

3. Bootstrap inicial:
- Ejecutar `installers/bootstrap-project.sh` sobre repo objetivo.
- Ejecutar `installers/bootstrap-server.sh` en nodo Jenkins/bastion.

4. Pipeline:
- Incorporar `templates/common/Jenkinsfile`.
- Ajustar variables de entorno y credenciales Jenkins.
- Probar empaque local con `templates/common/scripts/run-package-stage-local.sh`.

5. Despliegue:
- Elegir modo: Kubernetes directo, Helm o Terraform (o combinacion).
- Validar health checks y estrategia de rollback.

6. Operacion:
- Activar multibranch + webhook.
- Medir KPIs (build time, deploy frequency, failure rate, MTTR).
- Evolucionar segun etapa (`stages/`).

## 3) Pieza por pieza

## 3.1 `QUESTIONARIO-IMPLEMENTACION.md`

Que hace:
- Captura decisiones de negocio/tecnicas para elegir ruta de implementacion.

Entradas:
- Contexto del cliente (equipo, stack, infraestructura, compliance).

Salida:
- Perfil objetivo (manual/monolito/microservicios/cloud) y backlog de implementacion.

Validacion:
- Debe estar completo antes de tocar pipeline o infraestructura.

Errores comunes:
- Saltar discovery y arrancar directo con manifiestos.

---

## 3.2 `STACK-SOPORTADO.md`

Que hace:
- Define limites oficiales del producto para evitar implementaciones fuera de alcance.

Entradas:
- Stack real del cliente.

Salida:
- Decision: soportado, soportado con ajuste o no soportado.

Validacion:
- Revisar antes de bootstrap tecnico.

Errores comunes:
- Forzar una receta fuera de stack soportado.

---

## 3.3 `installers/bootstrap-project.sh`

Que hace:
- Inicializa estructura base de proyecto para CI/CD.

Entradas:
- Ruta del repo objetivo.

Salida:
- Archivos base de pipeline/documentacion copiados y listos para configurar.

Validacion:
- Confirmar que se crearon rutas de `templates/common` en el repo destino.

Errores comunes:
- Ejecutar en directorio equivocado y sobreescribir archivos no deseados.

---

## 3.4 `installers/bootstrap-server.sh`

Que hace:
- Prepara entorno servidor para ejecutar toolchain de automatizacion.

Entradas:
- Host objetivo, permisos, red y repos habilitados.

Salida:
- Nodo base listo para correr pipeline/provisioning.

Validacion:
- Verificar binarios requeridos (git, docker/kubectl/helm/terraform segun uso).

Errores comunes:
- No validar permisos sudo/usuario y fallar en mitad del bootstrap.

---

## 3.5 `templates/common/Jenkinsfile`

Que hace:
- Orquesta pipeline estandar (build/test/package/deploy) como codigo versionado.

Entradas:
- Codigo fuente, variables de entorno, credenciales.

Salida:
- Artefactos empaquetados y/o desplegados segun etapa.

Validacion:
- Ejecutar en rama de prueba y revisar logs por stage.
- Validar naming de artefacto, exit codes y resultados de gates.

Errores comunes:
- Credenciales faltantes.
- Variables de entorno sin definir.
- Saltar gates de calidad para "salir rapido".

---

## 3.6 `templates/common/scripts/run-package-stage-local.sh`

Que hace:
- Permite ejecutar localmente la fase de package para debugging rapido.

Entradas:
- Repositorio local con dependencias necesarias.

Salida:
- Artefacto local equivalente al stage de package del pipeline.

Validacion:
- Comparar salida local vs salida Jenkins para detectar drift.

Errores comunes:
- Diferencia de versiones locales vs runner Jenkins.

---

## 3.7 `templates/common/docs/cicd/INDICE_CI_CD.md`

Que hace:
- Indice operativo de pipeline para onboarding rapido del equipo.

Entradas:
- Ninguna (documentacion base).

Salida:
- Mapa de pasos CI/CD y modulos opcionales.

Validacion:
- Mantener alineado con Jenkinsfile real.

Errores comunes:
- Documentacion desactualizada frente al pipeline vigente.

---

## 3.8 `stages/*/README.md`

Que hace:
- Define plan de evolucion por madurez (manual -> monolito -> microservicios -> cloud).

Entradas:
- Estado actual de equipo/plataforma.

Salida:
- Hoja de ruta y criterios de paso entre etapas.

Validacion:
- Cada etapa debe cerrar con KPIs visibles.

Errores comunes:
- Saltar de etapa por moda sin completar controles basicos.

---

## 3.9 Kubernetes base (`manifests/kubernetes/*`)

Que hace:
- Provee despliegue y servicio base para workloads en cluster.

Entradas:
- Imagen versionada, namespace, variables/configuracion.

Salida:
- Deployment y Service activos en cluster.

Validacion:
- `kubectl apply -f ...`
- `kubectl rollout status deployment/<name>`
- Verificar probes/readiness y servicio expuesto.

Errores comunes:
- Desplegar tags mutables sin trazabilidad.
- No definir/validar probes.

---

## 3.10 Helm chart (`manifests/helm/fastflow-chart/*`)

Que hace:
- Parametriza despliegue Kubernetes por entorno con values.

Entradas:
- `values.yaml` + overrides por entorno.

Salida:
- Release Helm instalada/actualizada de forma idempotente.

Validacion:
- `helm upgrade --install ...`
- `helm get values <release>`
- `kubectl rollout status ...`

Errores comunes:
- Mezclar valores de dev/prod.
- No versionar cambios de values.

---

## 3.11 Terraform root (`manifests/terraform/main.tf`)

Que hace:
- Punto de entrada para IaC y composicion de modulos.

Entradas:
- Variables + backend + providers.

Salida:
- Plan/apply de infraestructura declarativa.

Validacion:
- `terraform init`
- `terraform plan`
- `terraform apply` con aprobacion.

Errores comunes:
- Aplicar sin revisar plan.
- Manejo inseguro de estado/variables sensibles.

---

## 3.12 Terraform Jenkins AWS (`manifests/terraform/jenkins-aws/*`)

Que hace:
- Implementa blueprint Jenkins en AWS por modulos:
  - network
  - compute
  - autoscaling
  - envs/dev

Entradas:
- `terraform.tfvars` por entorno.

Salida:
- Infra Jenkins base con red aislada y capacidad de escalar workers.

Validacion:
- Revisar outputs de red/compute.
- Verificar conectividad y politicas SG.

Errores comunes:
- Tratar modulo como "one-size-fits-all" sin ajustar seguridad/red.

---

## 3.13 Packer (`manifests/packer/*`)

Que hace:
- Genera imagenes inmutables para Jenkins controller y agents.

Entradas:
- Templates `.pkr.hcl` + scripts de setup.

Salida:
- Imagenes versionadas listas para deploy por reemplazo.

Validacion:
- `packer init`
- `packer validate`
- `packer build`

Errores comunes:
- Parchar maquinas en caliente en lugar de reconstruir imagen.

---

## 3.14 Multi-cloud readmes (`manifests/gcp|azure|digitalocean/README.md`)

Que hace:
- Explica estrategia de portabilidad y overlays por proveedor.

Entradas:
- Requerimientos de negocio/costo/compliance por cloud.

Salida:
- Ruta de adopcion multi-cloud con paridad funcional.

Validacion:
- Confirmar mismos contratos operativos: CI/CD, seguridad base, observabilidad.

Errores comunes:
- Buscar paridad 1:1 tecnica en lugar de paridad funcional.

## 4) Como validar que el producto esta realmente plug-and-play

Checklist de aceptacion:

1. Un nuevo repo puede quedar con pipeline minimo operando en menos de 1 dia.
2. El equipo puede ejecutar package/deploy sin conocimiento tribal.
3. Cada deploy deja evidencia (version, logs, estado de rollout).
4. Existe rollback practicable y probado.
5. Las decisiones de arquitectura estan documentadas y versionadas.

## 5) Troubleshooting rapido

Sintoma: pipeline falla en autenticacion.
- Revisar credenciales Jenkins y variables requeridas por stage.

Sintoma: deploy "exitoso" pero app no responde.
- Revisar probes/readiness, servicio y `rollout status`.

Sintoma: drift entre local y CI.
- Ejecutar script local de package y comparar versiones/herramientas.

Sintoma: terraform cambia demasiado en cada plan.
- Revisar estado remoto, providers y variables por entorno.

Sintoma: lentitud operativa al crecer el equipo.
- Activar multibranch + webhook + matriz repo strategy + workers dinamicos.

## 6) Regla de oro del toolkit

El toolkit no se limita a copiar templates:
- define criterio,
- fuerza trazabilidad,
- reduce riesgo operativo,
- y deja capacidad instalada para evolucionar sin depender de heroes.

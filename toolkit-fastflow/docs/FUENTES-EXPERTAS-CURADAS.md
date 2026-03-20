# Fuentes Expertas Curadas (Libros / Autores)

Fecha: 2026-03-10

Objetivo:
- Convertir contenido de libros/autores en reglas accionables para el toolkit.
- Separar criterio experto (framing) de base normativa (docs oficiales).

## Fuente experta 001

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Pipeline as Code con Jenkins
- Identificación compartida: "Chapter two — Pipeline as code with Jenkins" (published September 2021)
- Estado bibliográfico: pendiente de completar autor/título/editorial exactos para cita formal final

## Principios extraídos (curados)

1. Pipeline as Code reduce errores humanos de configuración manual en CI/CD.
2. Jenkinsfile versionado en repo habilita auditoría y code review del pipeline.
3. Estandarizar stages (checkout/test/build/push/deploy) mejora legibilidad y gobernanza.
4. Multibranch pipelines eliminan alta carga manual de jobs por rama.
5. Separar workflow por ramas/entornos mejora control de promoción.
6. Declarative pipeline reduce complejidad para adopción inicial; scripted mantiene flexibilidad avanzada.
7. Blue Ocean/Stage View aceleran diagnóstico operacional.
8. Replay + linter reducen "debug commits" y mejoran productividad.
9. GitFlow puede ser útil en equipos grandes, pero puede ser sobrecarga en equipos pequeños.

## Alineación con base oficial (estado)

- Pipeline as Code en Jenkinsfile versionado: ALINEADO.
- Multibranch con Jenkinsfile por rama: ALINEADO.
- Declarative vs scripted: ALINEADO (con matiz de flexibilidad Groovy en bloques `script`).
- Replay/lint para iterar pipeline: ALINEADO.
- GitFlow: VÁLIDO COMO ESTRATEGIA, NO REQUISITO UNIVERSAL.

Nota:
- La recomendación de branch model en Fast Flow queda "agnóstica": GitFlow cuando aporta valor, trunk-based o simplificado en equipos pequeños.

## Reglas implementables en el toolkit (derivadas)

1. Todo proyecto debe tener `Jenkinsfile` en raíz.
2. Pipeline base debe separar stages con naming estable.
3. Todo stage crítico debe dejar logs explícitos y exit_code.
4. Habilitar modo multibranch para repos con ramas de entorno/PR.
5. Definir lane declarative por defecto; scripted solo para casos avanzados.
6. Incluir validación de Jenkinsfile antes de merge (linter/API/CLI).
7. Mantener guía de branch strategy por tamaño de equipo.

## Cambios de producto que justifica esta fuente

- Fortalecer sección de multibranch y branch governance en docs del toolkit.
- Añadir guía de validación Jenkinsfile (linter) en flujo de desarrollo.
- Añadir política de "no debug commits": usar Replay/linter primero.

## Citas listas para uso web (provisional)

- Capítulo experto: "Pipeline as Code with Jenkins" (Chapter 2, Sept 2021, bibliografía completa pendiente de confirmación).

## APA provisional (completar cuando se confirme metadato)

- Autor, A. A. (2021, septiembre). *Pipeline as Code with Jenkins* (Cap. 2). [Editorial/Plataforma]. [URL]

## Metadatos faltantes para cerrar cita formal

- Autor(es)
- Título completo del libro
- Editorial/plataforma oficial
- URL pública estable o ISBN

## Política de uso en marketing

- Se puede citar como "referencia experta" en landing/producto mientras se completa bibliografía.
- Para PDF comercial/formal se recomienda usar referencia completa (APA) antes de publicación final.

## Fuente experta 002

- Tipo: parte de libro (contenido compartido por usuario)
- Tema: Operating a self-healing Jenkins cluster
- Identificación compartida: "Part 2 — Operating a self-healing Jenkins cluster" (published September 2021)
- Estado bibliográfico: pendiente de completar autor/título/editorial exactos para cita formal final

## Principios extraídos (curados) — Fuente 002

1. Jenkins no debe diseñarse como instancia única rígida para cargas crecientes.
2. Arquitectura distribuida (controller + workers) mejora escalabilidad y resiliencia operativa.
3. Workers dinámicos son clave para eficiencia de costo y elasticidad.
4. Provisionamiento de Jenkins y su entorno debe ser reproducible (IaC + imágenes preconfiguradas).
5. El modelo multi-cloud requiere estandarizar bootstrap y evitar dependencias específicas de un solo proveedor.
6. Plugins y configuración deben declararse/provisionarse como código para reducir deriva.

## Alineación con base oficial (estado) — Fuente 002

- Jenkins arquitectura distribuida: ALINEADO (concepto estándar del ecosistema Jenkins).
- IaC para infraestructura Jenkins: ALINEADO con estrategia Terraform del toolkit.
- Provisionamiento reproducible de imagen/base: ALINEADO con enfoque de bootstrap automatizado.
- Multi-cloud: VÁLIDO como objetivo de portabilidad; implementación por fases recomendada.

## Reglas implementables en el toolkit (derivadas) — Fuente 002

1. Separar perfiles de instalación (`core`, `k8s`, `full`) y mantenerlos idempotentes.
2. Definir nodo Jenkins con rol explícito (controller/agent) en documentación operativa.
3. Priorizar workers efímeros cuando el costo/carga lo justifique.
4. Mantener pipeline desacoplado del proveedor cloud (AWS/GCP/Azure/DO) mediante variables y módulos.
5. Modelar infraestructura base con Terraform y documentación de plan/apply por entorno.
6. Versionar configuración crítica de Jenkins y su provisioning.

## Cambios de producto que justifica esta fuente — Fuente 002

- Añadir guía de arquitectura Jenkins para escala (controller + agents) en docs del toolkit.
- Añadir roadmap de evolución a workers dinámicos (fase 2/3).
- Añadir blueprint multi-cloud (AWS first, cloud-agnostic next).

## Citas listas para uso web (provisional)

- Parte experta: "Operating a self-healing Jenkins cluster" (Part 2, Sept 2021, bibliografía completa pendiente de confirmación).

## APA provisional (completar cuando se confirme metadato) — Fuente 002

- Autor, A. A. (2021, septiembre). *Operating a self-healing Jenkins cluster* (Part 2). [Editorial/Plataforma]. [URL]

## Metadatos faltantes para cerrar cita formal — Fuente 002

- Autor(es)
- Título completo del libro
- Editorial/plataforma oficial
- URL pública estable o ISBN

## Fuente experta 003

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Defining Jenkins architecture
- Identificación compartida: "Chapter 3 — Defining Jenkins architecture" (published September 2021)
- Estado bibliográfico: pendiente de completar autor/título/editorial exactos para cita formal final

## Principios extraídos (curados) — Fuente 003

1. Jenkins standalone sirve para cargas pequeñas, pero para escala real conviene arquitectura distribuida (controller + agents).
2. El controller debe orquestar/schedulear y los agents ejecutar builds para evitar cuello de botella.
3. Labels y # of executors son mecanismos clave de gobernanza de capacidad y aislamiento de jobs.
4. El método de conexión de agentes depende del entorno (SSH preferente en Unix; JNLP/servicio en otros casos).
5. Escalar workers dinámicamente con métricas (CPU/cola) mejora costo y throughput.
6. Seguridad de red es parte del diseño de CI/CD: VPC, subred privada, SG/NACL, bastion o LB.
7. Para resiliencia del plano de control, se requiere estrategia HA (proxy/failover + storage compartido).
8. AWS CLI e IAM con principio de menor privilegio son base para automatizar despliegue cloud.

## Alineación con base oficial (estado) — Fuente 003

- Arquitectura distribuida Jenkins: ALINEADO.
- Escalado dinámico por métricas cloud: ALINEADO como patrón operativo.
- Seguridad por capas de red (VPC/subnets/SG): ALINEADO con buenas prácticas cloud.
- HA de Jenkins controller: VÁLIDO, pero depende de complejidad y costo del cliente.
- IAM least privilege: ALINEADO (requisito de seguridad empresarial).

## Reglas implementables en el toolkit (derivadas) — Fuente 003

1. Definir 3 modos de arquitectura Jenkins en documentación:
   - single-node
   - distributed-static
   - distributed-dynamic
2. Requerir labels de nodo para jobs críticos por tipo de workload.
3. Definir política de executors por nodo (evitar sobreasignación ciega).
4. Establecer baseline de red para producción:
   - Jenkins en subred privada
   - acceso por LB o bastion
   - SG restrictivo
5. Definir umbrales de escalado recomendados para workers (ejemplo inicial CPU 80/20, ajustable).
6. Integrar guía de IAM/CLI para automatización de Terraform y provisioning.

## Cambios de producto que justifica esta fuente — Fuente 003

- Añadir documento de blueprint de arquitectura Jenkins por etapa de madurez.
- Añadir documento de seguridad mínima de red para despliegue en nube.
- Añadir documento de operación de workers (labels, executors, thresholds).

## Citas listas para uso web (provisional)

- Capítulo experto: "Defining Jenkins architecture" (Chapter 3, Sept 2021, bibliografía completa pendiente de confirmación).

## APA provisional (completar cuando se confirme metadato) — Fuente 003

- Autor, A. A. (2021, septiembre). *Defining Jenkins architecture* (Cap. 3). [Editorial/Plataforma]. [URL]

## Metadatos faltantes para cerrar cita formal — Fuente 003

- Autor(es)
- Título completo del libro
- Editorial/plataforma oficial
- URL pública estable o ISBN

## Fuente experta 004

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Baking machine images with Packer
- Identificación compartida: "Chapter 4 — Baking machine images with Packer" (published September 2021)
- Autor identificado: Mohamed Labouardy
- Libro identificado: Pipeline as Code
- Estado bibliográfico: pendiente de confirmar editorial/ISBN/URL pública final

## Principios extraídos (curados) — Fuente 004

1. Enfoque inmutable (bake and replace) reduce drift y fallas por cambios manuales.
2. Packer permite estandarizar imágenes reproducibles para controller y workers.
3. Provisioning de Jenkins debe automatizar seguridad/plugins/config inicial.
4. Separar imagen de controller e imagen de agent simplifica escalado.
5. El workflow Packer debe integrarse en CI para regenerar imágenes bajo cambio controlado.

## Riesgos de obsolescencia detectados (importante)

El capítulo usa ejemplos de época (2021) que hoy deben actualizarse:

- Uso de Java 8 para Jenkins: desactualizado para Jenkins moderno.
- Plantillas JSON legacy de Packer: preferible HCL2 actual.
- Algunas recetas/plugin APIs pueden variar por versión.

Decisión del toolkit:
- Mantener principios (inmutable + automation) y actualizar implementación a stack vigente.

## Alineación con base oficial (estado) — Fuente 004

- Inmutable con Packer: ALINEADO.
- Builders/provisioners y multi-cloud: ALINEADO.
- Jenkins init scripts/JCasC para bootstrap: ALINEADO como técnica de provisioning.

## Reglas implementables en el toolkit (derivadas) — Fuente 004

1. Toda imagen Jenkins debe salir de Packer + control de versión.
2. Controller y agents deben tener imágenes separadas.
3. Pipeline de imagen debe ejecutar: init -> validate -> build.
4. Imagen nueva se despliega por reemplazo controlado, no parcheo manual en caliente.
5. Mantener inventario y retención de imágenes para rollback y control de costo.

## Cambios de producto que justifica esta fuente — Fuente 004

- Crear baseline Packer dentro del toolkit.
- Publicar guía de inmutable + integración con Terraform.
- Agregar política de compatibilidad vigente (Java/Jenkins/stack) para evitar recetas antiguas.

## Citas listas para uso web (provisional)

- Labouardy, M. — *Pipeline as Code*, Chapter 4: Baking machine images with Packer (Sep 2021).

## APA provisional (completar editorial/ISBN/URL)

- Labouardy, M. (2021, septiembre). *Pipeline as Code* (Cap. 4, Baking machine images with Packer). [Editorial]. [URL/ISBN]

## Fuente experta 005

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Discovering Jenkins as code with Terraform
- Identificación compartida: "Chapter 5 — Discovering Jenkins as code with Terraform" (published September 2021)
- Autor identificado: Mohamed Labouardy
- Libro identificado: Pipeline as Code
- Estado bibliográfico: pendiente de confirmar editorial/ISBN/URL pública final

## Principios extraídos (curados) — Fuente 005

1. IaC permite que la infraestructura sea repetible, auditable y más segura al eliminar cambios manuales.
2. Terraform facilita modelar infraestructura cloud con enfoque declarativo y control de cambios.
3. Separar red segura (VPC/subnets/routing) de cómputo Jenkins mejora gobernanza.
4. Jenkins controller en red privada + acceso controlado es patrón de seguridad base.
5. Workers efímeros bajo ASG permiten autoscaling y autorecovery.
6. Alarmas de escalado deben basarse en métricas y enfriamiento para evitar inestabilidad.
7. `terraform plan` antes de `apply` es gate de seguridad operativo.
8. Variables y modularización reducen acoplamiento por ambiente/proveedor.

## Riesgos de obsolescencia detectados (importante)

El capítulo incluye ejemplos válidos conceptualmente pero con elementos a modernizar:

- Sintaxis Terraform legacy (`tags {}` / interpolaciones antiguas) debe migrarse a HCL moderna.
- Enfoque ELB clásico puede requerir actualización a opciones modernas según caso.
- Manejo de secretos/credenciales requiere endurecimiento actual (evitar exposición en archivos locales).

## Alineación con base oficial (estado) — Fuente 005

- Proceso `init/plan/apply`: ALINEADO.
- Provider AWS y modelado modular: ALINEADO.
- VPC privada + bastion/LB: ALINEADO como patrón enterprise.
- ASG y alarmas para workers: ALINEADO como patrón de escalado.

## Reglas implementables en el toolkit (derivadas) — Fuente 005

1. Toda implementación cloud debe iniciar por módulo de red.
2. Jenkins controller y workers deben modelarse en módulos separados.
3. ASG workers es patrón recomendado para etapa de escala.
4. Variables sensibles y estado deben tratarse con controles de seguridad.
5. `plan` aprobado es requisito antes de `apply` en entornos críticos.

## Cambios de producto que justifica esta fuente — Fuente 005

- Incorporar blueprint Terraform Jenkins AWS en toolkit.
- Estandarizar estructura modular por capas (network/compute/autoscaling).
- Formalizar guía de operación Terraform en el playbook.

## Citas listas para uso web (provisional)

- Labouardy, M. — *Pipeline as Code*, Chapter 5: Discovering Jenkins as code with Terraform (Sep 2021).

## APA provisional (completar editorial/ISBN/URL)

- Labouardy, M. (2021, septiembre). *Pipeline as Code* (Cap. 5, Discovering Jenkins as code with Terraform). [Editorial]. [URL/ISBN]

## Fuente experta 006

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Deploying HA Jenkins on multiple cloud providers
- Identificación compartida: "Chapter 6 — Deploying HA Jenkins on multiple cloud providers" (published September 2021)
- Autor identificado: Mohamed Labouardy
- Libro identificado: Pipeline as Code
- Estado bibliográfico: pendiente de confirmar editorial/ISBN/URL pública final

## Principios extraídos (curados) — Fuente 006

1. El modelo Jenkins + IaC + imágenes inmutables puede repetirse en múltiples nubes.
2. La estandarización de templates reduce tiempo al portar arquitectura entre proveedores.
3. Workers bajo autoscaling son palanca principal de costo y elasticidad.
4. El objetivo práctico multi-cloud es equivalencia funcional, no identidad técnica exacta.
5. Cada cloud requiere adaptar identidad/seguridad/red, manteniendo el mismo contrato operativo.

## Riesgos de obsolescencia detectados (importante)

- Ejemplos 2021 pueden usar sintaxis/providers/versiones desactualizadas.
- Configuraciones de red o balanceo pueden haber cambiado en recursos recomendados.
- No todos los enfoques de autoscaling son equivalentes entre clouds.

Decisión del toolkit:
- Mantener principios portables.
- Implementar con proveedores/sintaxis vigentes y hardening actual.

## Alineación con base oficial (estado) — Fuente 006

- Multi-cloud con Packer/Terraform: ALINEADO.
- Reutilización de blueprint: ALINEADO.
- Autoscaling workers: ALINEADO como patrón de escalado.

## Reglas implementables en el toolkit (derivadas) — Fuente 006

1. Definir un core cloud-agnostic y overlays por proveedor.
2. Mantener paridad de capacidades (network, compute, scaling, security baseline).
3. Publicar matriz de madurez por proveedor (qué está GA, beta, roadmap).
4. Evitar prometer full parity si el proveedor no soporta feature equivalente.

## Cambios de producto que justifica esta fuente — Fuente 006

- Agregar estrategia multi-cloud en docs del toolkit.
- Crear skeletons por proveedor dentro de manifests.
- Formalizar criterio de activación multi-cloud por negocio/costo/riesgo.

## Citas listas para uso web (provisional)

- Labouardy, M. — *Pipeline as Code*, Chapter 6: Deploying HA Jenkins on multiple cloud providers (Sep 2021).

## APA provisional (completar editorial/ISBN/URL)

- Labouardy, M. (2021, septiembre). *Pipeline as Code* (Cap. 6, Deploying HA Jenkins on multiple cloud providers). [Editorial]. [URL/ISBN]

## Fuente experta 007

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Defining a pipeline as code for microservices
- Identificación compartida: "Chapter 7 — Defining a pipeline as code for microservices" (published September 2021)
- Autor identificado: Mohamed Labouardy
- Libro identificado: Pipeline as Code
- Estado bibliográfico: pendiente de confirmar editorial/ISBN/URL pública final

## Principios extraídos (curados) — Fuente 007

1. Multibranch + Jenkinsfile por rama reduce trabajo manual y mejora trazabilidad de cambios.
2. Webhooks de SCM son el gatillo natural para feedback continuo.
3. Estrategia de repos (multi-repo vs mono-repo) impacta directamente complejidad de CI/CD.
4. Pipeline y código en el mismo repo facilitan review/auditoría del flujo de entrega.
5. En entornos restringidos, puede usarse forwarder de webhook en lugar de exposición directa.
6. Clonación/export de jobs vía XML/API existe, pero se recomienda priorizar Pipeline as Code.

## Riesgos de obsolescencia detectados (importante)

- Ejemplos con versiones antiguas de runtimes/plugins pueden requerir ajustes.
- Import/export XML de jobs no debe sustituir IaC moderno del pipeline.
- Polling SCM no debe ser default si webhook está disponible.

## Alineación con base oficial (estado) — Fuente 007

- Multibranch Jenkins: ALINEADO.
- Webhooks como trigger primario: ALINEADO.
- Repo strategy como decisión arquitectónica: ALINEADO.
- XML cloning: útil como técnica operativa puntual, no como patrón principal.

## Reglas implementables en el toolkit (derivadas) — Fuente 007

1. Definir multibranch como modo recomendado para repos de producto.
2. Exigir `Jenkinsfile` en raíz de cada servicio.
3. Definir trigger principal por webhook y fallback por polling controlado.
4. Establecer matriz de estrategia repo según tamaño/equipo/acoplamiento.
5. Evitar dependencia de configuración manual de jobs en UI.

## Cambios de producto que justifica esta fuente — Fuente 007

- Documento patrón CI microservicios.
- Documento de multibranch + webhooks.
- Matriz de decisión mono-repo vs multi-repo.

## Citas listas para uso web (provisional)

- Labouardy, M. — *Pipeline as Code*, Chapter 7: Defining a pipeline as code for microservices (Sep 2021).

## APA provisional (completar editorial/ISBN/URL)

- Labouardy, M. (2021, septiembre). *Pipeline as Code* (Cap. 7, Defining a pipeline as code for microservices). [Editorial]. [URL/ISBN]

## Fuente experta 008

- Tipo: sección de libro (contenido compartido por usuario)
- Tema: About this Book / roadmap de modernización de software legacy
- Identificación compartida: "Re-Engineering Legacy Software — About this Book" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 008

1. La reingeniería de legado no es solo código: involucra flujo, tooling, infraestructura y cultura organizacional.
2. La causa raíz del deterioro suele ser pérdida de información y mala priorización sostenida.
3. La mejora sostenible requiere combinación de refactor técnico + automatización + gobernanza.
4. No existe bala de plata: la modernización se ejecuta por fases con objetivos medibles.
5. La selección de herramientas debe responder al problema real (inspección, build, despliegue, observabilidad, onboarding).

## Alineación con base oficial (estado) — Fuente 008

- Enfoque sistémico (código + proceso + infraestructura): ALINEADO.
- Mejora por fases con métricas: ALINEADO.
- Prioridad en trazabilidad y transferencia de conocimiento: ALINEADO.

## Reglas implementables en el toolkit (derivadas) — Fuente 008

1. Todo plan de modernización debe tener roadmap por etapas con entregables verificables.
2. Cada etapa debe incluir outcome técnico y outcome operativo de negocio.
3. Debe existir documentación viva del flujo (onboarding, build, deploy, rollback, ownership).
4. Evitar decisiones "solo por stack"; justificar por riesgo, costo, complejidad y velocidad.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, About this Book (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (About this Book). [Editorial]. [URL/ISBN]

## Fuente experta 009

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Understanding the challenges of legacy projects
- Identificación compartida: "Chapter 1 — Understanding the challenges of legacy projects" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 009

1. "Legacy" es un problema de mantenibilidad del proyecto completo (código + herramientas + infra + comunicación).
2. El legado suele combinar antigüedad, tamaño, herencia y documentación incompleta.
3. El problema central es informacional: conocimiento crítico no compartido ni preservado.
4. Deuda técnica acumulada reduce velocidad y flexibilidad de entrega en el tiempo.
5. La calidad de infraestructura (dependencias, entornos, onboarding) impacta tanto como la calidad del código.
6. Cultura de miedo al cambio y silos de conocimiento acelera la degradación del sistema.

## Alineación con base oficial (estado) — Fuente 009

- Visión holística de calidad de software: ALINEADO.
- Enfoque de deuda técnica como riesgo acumulativo: ALINEADO.
- Importancia de testabilidad y documentación ejecutable: ALINEADO.

## Reglas implementables en el toolkit (derivadas) — Fuente 009

1. Diagnóstico inicial obligatorio en 4 ejes: código, infraestructura, flujo, cultura de equipo.
2. Definir "legacy hotspots" por evidencia (fallas, complejidad, frecuencia de cambio).
3. Toda intervención debe contemplar transferencia explícita de conocimiento.
4. Evitar cambios grandes sin guardrails de pruebas, rollback y observabilidad.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 1: Understanding the challenges of legacy projects (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 1, Understanding the challenges of legacy projects). [Editorial]. [URL/ISBN]

## Fuente experta 010

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Finding your starting point
- Identificación compartida: "Chapter 2 — Finding your starting point" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 010

1. El inicio correcto de modernización es medición objetiva, no intuición.
2. La mejora debe estar guiada por datos continuos (bugs, complejidad, performance, fallas, lead time).
3. El equipo necesita observabilidad de calidad compartida (CI + inspección continua).
4. Herramientas de análisis estático aceleran priorización de refactor (FindBugs/PMD/Checkstyle en el contexto original).
5. Métricas y dashboards visibles reducen fricción emocional y mejoran foco de ejecución.

## Alineación con base oficial (estado) — Fuente 010

- CI como plataforma de inspección continua: ALINEADO.
- Medición para decidir prioridades de remediación: ALINEADO.
- Dashboards y trazabilidad operativa: ALINEADO.

## Reglas implementables en el toolkit (derivadas) — Fuente 010

1. Baseline de calidad inicial antes de cualquier refactor mayor.
2. Definir y publicar KPIs mínimos: defectos, tasa de fallas, tiempo de build, tiempo de recuperación.
3. Integrar escaneo estático y reportes en pipeline como gate no opcional.
4. Mantener tendencia histórica para evaluar si la modernización realmente mejora resultados.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 2: Finding your starting point (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 2, Finding your starting point). [Editorial]. [URL/ISBN]

## Fuente experta 011

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Preparing to refactor
- Identificación compartida: "Chapter 3 — Preparing to refactor" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 011

1. Refactor exitoso es un problema de alineación humana antes que de técnica.
2. Se requiere consenso de equipo y patrocinio organizacional para sostener cambios.
3. Toda modernización debe priorizar por valor, riesgo y dificultad.
4. Regla operativa: intentar reemplazar con solución existente, luego refactor, y reescritura como última opción.
5. Reescrituras big-bang concentran riesgo; enfoque incremental reduce exposición y entrega valor temprano.
6. Todo proyecto de mejora necesita objetivos de negocio explícitos para evitar deriva de alcance.

## Alineación con base oficial (estado) — Fuente 011

- Priorización por impacto/riesgo: ALINEADO.
- Modernización incremental en lugar de reescritura total por defecto: ALINEADO.
- Gobernanza de cambios con revisión y gates: ALINEADO.

## Reglas implementables en el toolkit (derivadas) — Fuente 011

1. Cada iniciativa debe tener business case y criterio de salida.
2. Toda tarea de refactor debe etiquetarse por valor/riesgo/dificultad.
3. Preferir slicing incremental por componentes sobre programas de reemplazo monolíticos.
4. Establecer política de code review y pruebas como control de riesgo de refactor.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 3: Preparing to refactor (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 3, Preparing to refactor). [Editorial]. [URL/ISBN]

## Fuente experta 012

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Refactoring
- Identificación compartida: "Chapter 4 — Refactoring" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 012

1. Refactor efectivo exige disciplina: cambios pequeños, reversibles y separados de cambios funcionales.
2. Eliminar stale code y tests tóxicos genera mejoras rápidas de mantenibilidad.
3. Reducir nulls implícitos y mutabilidad innecesaria baja probabilidad de errores.
4. Separar lógica de negocio de detalles de implementación mejora testabilidad y evolución.
5. Para legado no testeable, introducir capas de indireccion habilita mocks y pruebas seguras.

## Reglas implementables en el toolkit (derivadas) — Fuente 012

1. Establecer política de commits separados: refactor vs feature/fix.
2. Incluir checklist de calidad para detectar stale code y tests inservibles.
3. Reforzar pruebas en varios niveles (unit/integration/system), no solo cobertura unitaria.
4. Documentar patrones de seam/injection para código legacy difícil de testear.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 4: Refactoring (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 4, Refactoring). [Editorial]. [URL/ISBN]

## Fuente experta 013

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Re-architecting
- Identificación compartida: "Chapter 5 — Re-architecting" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 013

1. Re-arquitectura es refactor a escala de componentes, no solo de clases.
2. Modularizar fuerza interfaces explícitas y reduce acoplamiento accidental.
3. Monolito, front/back, SOA y microservicios son trade-offs, no dogmas.
4. El enfoque incremental reduce riesgo frente a migraciones big-bang.
5. La autonomía de equipos debe balancearse con estándares de plataforma y observabilidad.

## Reglas implementables en el toolkit (derivadas) — Fuente 013

1. Diseñar cambios por componente con contratos claros y criterios de corte.
2. Seleccionar arquitectura por contexto de negocio, no por tendencia tecnológica.
3. Definir estrategia incremental con hitos de valor por fase.
4. Acompañar autonomía técnica con guardrails operativos comunes.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 5: Re-architecting (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 5, Re-architecting). [Editorial]. [URL/ISBN]

## Fuente experta 014

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: The Big Rewrite
- Identificación compartida: "Chapter 6 — The Big Rewrite" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 014

1. Toda reescritura debe iniciar con alcance explícito y criterios de salida.
2. La implementación legacy es referencia útil, pero no debe dictar el diseño nuevo.
3. La migración de base de datos es el principal foco de riesgo operativo.
4. Si coexisten sistema viejo y nuevo, se requiere sincronización robusta y monitoreable.
5. La estrategia incremental con rollback reduce exposición frente a cutovers definitivos.

## Reglas implementables en el toolkit (derivadas) — Fuente 014

1. Exigir documento de alcance para todo programa de rewrite.
2. Definir estrategia de datos (shared DB vs DB separada) con riesgos y mitigaciones.
3. Mantener runbooks de sincronización/recuperación y monitoreo de consistencia.
4. Evitar cutovers irreversibles sin plan de failback.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 6: The Big Rewrite (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 6, The Big Rewrite). [Editorial]. [URL/ISBN]

## Fuente experta 015

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Automating the development environment
- Identificación compartida: "Chapter 7 — Automating the development environment" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 015

1. El README es activo crítico de onboarding y transferencia de conocimiento.
2. Entornos reproducibles reducen fricción, errores manuales y dependencia de personas.
3. Automatizar provisioning mejora paridad entre equipos y acelera arranque.
4. Reducir dependencias externas (DBs compartidas, tickets a Ops) aumenta autonomía.

## Reglas implementables en el toolkit (derivadas) — Fuente 015

1. Mantener README operativo mínimo en cada implementacion cliente.
2. Formalizar bootstrap automatizado para proyecto y servidor.
3. Evitar prerequisitos manuales no documentados.
4. Priorizar entornos locales reproducibles para desarrollo.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 7: Automating the development environment (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 7, Automating the development environment). [Editorial]. [URL/ISBN]

## Fuente experta 016

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Extending automation to test, staging, and production environments
- Identificación compartida: "Chapter 8 — Extending automation to test, staging, and production environments" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 016

1. Paridad entre entornos es control de riesgo, no comodidad técnica.
2. Infraestructura automatizada habilita trazabilidad y recuperación rápida.
3. Inventarios por entorno y roles reutilizables reducen deriva.
4. CI (Jenkins) como orquestador de provisioning mejora consistencia operacional.
5. Inmutabilidad y cloud facilitan resiliencia y escalabilidad.

## Reglas implementables en el toolkit (derivadas) — Fuente 016

1. Aplicar el mismo baseline técnico entre dev/test/prod con variables por entorno.
2. Ejecutar provisioning desde pipeline controlado y auditable.
3. Versionar scripts de provisioning como código de producto.
4. Usar inventarios separados por entorno para minimizar errores de blast radius.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 8: Extending automation to test, staging, and production environments (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 8, Extending automation to test, staging, and production environments). [Editorial]. [URL/ISBN]

## Fuente experta 017

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Modernizing the development, building, and deployment of legacy software
- Identificación compartida: "Chapter 9 — Modernizing the development, building, and deployment of legacy software" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 017

1. Toolchain desactualizado eleva costo de contribución y frena modernización.
2. CI mínimo obligatorio por código base evita “islas legacy” sin gobernanza.
3. Release y deploy deben ser automatizados y repetibles.
4. Scripts de despliegue son documentación ejecutable y transferible.

## Reglas implementables en el toolkit (derivadas) — Fuente 017

1. Mantener baseline de CI para todos los repos, sin excepciones.
2. Estandarizar pipelines de build/test/release/deploy.
3. Versionar automatizaciones de deploy y ejecutar pruebas periódicas para evitar bit-rot.
4. Evitar procesos de despliegue dependientes de memoria tribal.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 9: Modernizing the development, building, and deployment of legacy software (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 9, Modernizing the development, building, and deployment of legacy software). [Editorial]. [URL/ISBN]

## Fuente experta 018

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Stop writing legacy code!
- Identificación compartida: "Chapter 10 — Stop writing legacy code!" (published April 2016)
- Autor identificado: Chris Birchall
- Libro identificado: Re-Engineering Legacy Software
- Estado bibliográfico: pendiente de confirmar ISBN/URL pública final

## Principios extraídos (curados) — Fuente 018

1. Calidad sostenible incluye código, documentación, toolchain, infraestructura y cultura.
2. La pérdida de conocimiento es un riesgo continuo que exige mecanismos activos de comunicación.
3. El mantenimiento es continuo: la deuda no atendida crece de forma no lineal.
4. Automatizar tareas repetitivas protege al equipo actual y al sucesor.
5. Diseñar componentes pequeños y reemplazables reduce riesgo de legado futuro.

## Reglas implementables en el toolkit (derivadas) — Fuente 018

1. Mantener documentación corta, versionada y revisada por PR.
2. Exigir revisiones técnicas periódicas a nivel sistema, no solo por ticket.
3. Convertir procesos manuales de alto riesgo en automatización ejecutable.
4. Diseñar módulos desacoplados y desechables por componente.

## Citas listas para uso web (provisional)

- Birchall, C. — *Re-Engineering Legacy Software*, Chapter 10: Stop writing legacy code! (Apr 2016).

## APA provisional (completar editorial/ISBN/URL)

- Birchall, C. (2016, abril). *Re-Engineering Legacy Software* (Cap. 10, Stop writing legacy code!). [Editorial]. [URL/ISBN]

## Fuente experta 019

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: Microservices security landscape
- Identificación compartida: "Chapter 1 — Microservices security landscape" (published July 2020)
- Autores identificados: Prabath Siriwardena y Nuwan Dias
- Libro identificado: Microservices Security in Action
- Estado bibliográfico: pendiente de confirmar editorial/ISBN/URL pública final

## Principios extraídos (curados) — Fuente 019

1. La seguridad en microservicios es más compleja por superficie de ataque, distribución y observabilidad.
2. Edge security (API gateway) y east/west security (service-to-service) deben coexistir.
3. Zero-trust y autenticación mutua entre servicios son lineamientos base en entornos distribuidos.
4. Scope/autorización deben aplicarse en capas (borde y servicio).
5. Gestión de credenciales/políticas en entornos inmutables debe ser automatizada.

## Reglas implementables en el toolkit (derivadas) — Fuente 019

1. Todo blueprint cloud/microservicios debe incluir baseline de seguridad de borde y de servicio.
2. Exigir TLS/identidad de workload para tráfico east/west en madurez avanzada.
3. Integrar trazabilidad de seguridad en observabilidad (logs, métricas, trazas).
4. Evitar el antipatrón trust-the-network como estrategia por defecto.

## Citas listas para uso web (provisional)

- Siriwardena, P., & Dias, N. — *Microservices Security in Action*, Chapter 1: Microservices security landscape (Jul 2020).

## APA provisional (completar editorial/ISBN/URL)

- Siriwardena, P., & Dias, N. (2020, julio). *Microservices Security in Action* (Cap. 1, Microservices security landscape). [Editorial]. [URL/ISBN]

## Fuente experta 020

- Tipo: capítulo de libro (contenido compartido por usuario)
- Tema: First steps in securing microservices
- Identificación compartida: "Chapter 2 — First steps in securing microservices" (published July 2020)
- Autores identificados: Prabath Siriwardena y Nuwan Dias
- Libro identificado: Microservices Security in Action
- Estado bibliográfico: pendiente de confirmar editorial/ISBN/URL pública final

## Principios extraídos (curados) — Fuente 020

1. OAuth 2.0 en edge es base pragmática para autenticación/autorización inicial de microservicios.
2. Scopes permiten control granular por operación de API.
3. Token introspection es útil para diseños iniciales; JWT self-contained mejora escalabilidad posterior.
4. HTTP en demos puede ser didáctico, pero producción exige HTTPS en todos los flujos.

## Reglas implementables en el toolkit (derivadas) — Fuente 020

1. Definir patrón de control de acceso por scopes para endpoints críticos.
2. Exigir transporte seguro (HTTPS/TLS) en toda implementación productiva.
3. Versionar política de autorización junto con pipeline y despliegue.
4. Mantener separación clara entre autenticación de cliente y autorización de operación.

## Citas listas para uso web (provisional)

- Siriwardena, P., & Dias, N. — *Microservices Security in Action*, Chapter 2: First steps in securing microservices (Jul 2020).

## APA provisional (completar editorial/ISBN/URL)

- Siriwardena, P., & Dias, N. (2020, julio). *Microservices Security in Action* (Cap. 2, First steps in securing microservices). [Editorial]. [URL/ISBN]

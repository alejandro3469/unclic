# Dependencia generic model y contexto (JD Edwards, Oracle, arquitectura)

Este documento describe la **dependencia obligatoria** de pos-online con **generic model** y el contexto de **JD Edwards**, **Oracle**, **entornos**, **bases de datos** y **fase de arquitectura** en que se encuentra el proyecto. Debe tenerse en cuenta en la implementación FastFlow (pipeline, build, despliegue) y en la copia final al repo pos-online.

---

## 1. Generic model — dependencia obligatoria

- **generic model** es un repositorio local que está en la **misma carpeta padre** que pos-online.
- Es **dependencia obligatoria** para pos-online: el build y el pipeline deben poder resolverla (como sibling repo, submódulo o referencia según la integración elegida).

### Estructura típica

```
<carpeta-padre>/
  ../60-pos-online/          ← repo pos-online (donde se integra esta implementación FastFlow)
  generic-model/       ← repo generic model (obligatorio)
```

### Implicaciones para el pipeline

- **Build:** Si pos-online depende de generic model (JAR, módulo Maven, etc.), el pipeline debe ejecutarse desde un workspace que tenga acceso a generic model (por ejemplo, clonando ambos o usando submódulos).
- **Tests:** Los tests de pos-online pueden depender de artefactos o configuraciones de generic model; el job de Jenkins debe resolver la ruta o variable que apunte a generic model.
- **Empaquetado / imagen Docker:** Si la imagen incluye pos-online y dependencias, generic model (o sus artefactos) debe estar disponible en el contexto de build.

---

## 2. JD Edwards (JDE)

- El ecosistema pos-online puede integrarse o coexistir con **JD Edwards (Oracle)**.
- Considerar en la implementación:
  - **Entornos** JDE (desarrollo, QA, producción) y cómo se mapean a ramas o a variables del pipeline.
  - **Conexiones** a sistemas JDE (APIs, bases de datos, archivos) y su configuración por entorno.
  - **Fase de arquitectura** en que está el cliente (migración, coexistencia, modernización) para no asumir un esquema único.

---

## 3. Oracle — entornos y bases de datos

- **Bases de datos Oracle** pueden ser parte del entorno de pos-online (datos maestros, transacciones, reporting).
- En la implementación FastFlow:
  - **Variables de entorno** o ConfigMaps/Secrets para URLs de BD, usuarios y entornos (dev, QA, prod).
  - **Migraciones** o scripts de schema si aplican; no ejecutarlos en el pipeline sin criterio por entorno.
  - Documentar en el repo pos-online qué entornos y qué BDs se usan y cómo se configuran (sin secretos en repo).

---

## 4. Arquitectura y fase del proyecto

- La **fase de arquitectura** (por ejemplo: pre-migración, migración activa, post-migración a cloud o a JDE) condiciona:
  - Qué se despliega (on-prem, cloud, híbrido).
  - Cómo se integra generic model (monolito, servicios, etc.).
  - Qué niveles de pruebas (unitarias, integración con JDE/Oracle) se ejecutan en el pipeline.
- Dejar documentado en pos-online la **fase actual** y las **decisiones de arquitectura** relevantes para el pipeline (ramas, entornos, registry, rollback).

---

## 5. Resumen para la implementación que se queda en pos-online

- **Generic model:** Siempre disponible como sibling repo (o equivalente); el pipeline y los scripts de este repo deben adaptarse a la ruta real en pos-online.
- **JDE / Oracle / entornos / DB:** Considerar en variables, documentación y en la configuración de despliegue (Terraform, K8s, o scripts de pos-online).
- **Arquitectura y fase:** Documentar en el repo pos-online para que quien mantenga el pipeline sepa qué se despliega y en qué orden (generic model, pos-online, integraciones).

Referencias en este repo: **docs/60-pos-online/** (checklist, flujo commit, implementación demo), **docs/30-instalacion/**, **docs/40-pipeline-registry/**.

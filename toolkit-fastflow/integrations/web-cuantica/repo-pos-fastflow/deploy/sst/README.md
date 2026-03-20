# SST (Serverless Stack) — alcance en FastFlow POS Online

## Estado en este repo

**No hay código SST** en `repo-pos-fastflow` porque el producto principal es una aplicación **Java/Spring Boot** empaquetada como JAR y **Docker**, con pipeline en `Jenkinsfile`.

[SST](https://sst.dev/) orienta despliegues **Node** / **serverless** / frontends conectados a AWS (Lambda, API Gateway, etc.). Mezclarlo con el POS monolítico añade un segundo runtime sin beneficio inmediato para el flujo **Maven → Docker → registry → K8s/Linux**.

## Cuándo tendría sentido añadir SST

- Un **BFF** o **portal** en Node en otro repositorio, con su propio pipeline.
- Una **lambda** auxiliar (webhooks, integraciones) desacoplada del JAR del POS.

En ese caso, el repositorio SST sería **independiente** o una carpeta hermana; el job Jenkins del POS **no** debe bloquearse en SST.

## Dónde documentar el E2E

- `unclic/docs/DEMOS-PRODUCCION-END-TO-END.md` — checklist HTTPS y variables del sitio.
- `.jenkins/PLAN-FASTFLOW-PIPELINE-IAC.md` — plan Jenkins + Terraform + Pulumi + LocalStack.

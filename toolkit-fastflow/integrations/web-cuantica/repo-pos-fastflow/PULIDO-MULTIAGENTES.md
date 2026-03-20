# Pulido multiagentes — repo POS FastFlow

Checklist para que **varios agentes** (Cursor, Codex, etc.) pulan este repo en paralelo y quede listo en **2–5 min** para push a Gitea y primer Build Now en Jenkins.

## Reglas

- **Un solo agente** toca el **Jenkinsfile** en la misma iteración (evitar conflictos).
- Después de cambios en código o pipeline: ejecutar **tests** (`mvn test`) y, si hay script, **validación** del Jenkinsfile.
- **Handoffs:** si un agente hace deploy/terraform o k8s, otro puede hacer docs o tests; no pisar los mismos archivos sin coordinar.

## Checklist por rol (opcional)

| Rol | Tareas | Archivos típicos |
|-----|--------|------------------|
| **A — Tests y lista** | Asegurar `mvn test` pasa; añadir test de integración si falta; validar Jenkinsfile | `src/test/`, `Jenkinsfile`, `scripts/validate-jenkinsfile.sh` |
| **B — Contenido** | README claro; este PULIDO-MULTIAGENTES; comentarios en Jenkinsfile/stages | `README.md`, `PULIDO-MULTIAGENTES.md`, `Jenkinsfile` |
| **C — Deploy / runbook** | deploy/terraform y deploy/k8s consistentes; README de deploy; variables de registry | `deploy/terraform/`, `deploy/k8s/`, `deploy/*/README.md` |
| **D — Docs** | Enlazar con plan demo (web-cuantica); documento "listo para Gitea en 2–5 min" | `README.md`, referencias a `docs/PLAN-DEMO-...` |

## Orden sugerido (2–5 min)

1. **Validar build y tests**  
   `mvn clean test` y `mvn package -DskipTests` en la raíz del repo. Deben pasar.

2. **Validar Jenkinsfile**  
   Si existe `scripts/validate-jenkinsfile.sh`, ejecutarlo. Si no, revisar a mano que los stages (Build, Test, Package, Deploy, Verify instance) estén correctos.

3. **Revisar README**  
   Comprobar que los pasos "En 2–5 min" tienen la URL de Gitea correcta (placeholder `TU_USUARIO`) y la IP de EC2 si aplica (3.15.4.160 o la que uses).

4. **Push y Build Now**  
   Cuando Gitea responda: `git push gitea main`, luego en Jenkins **Build Now**. Ver logs y `curl http://<EC2>:8111/health`.

## Archivos que no deben romperse

- `pom.xml` — compilación y tests.
- `Jenkinsfile` — stages en orden; Deploy debe levantar JAR en 8111 y Verify hacer curl a /health.
- `src/main/resources/application.properties` — `server.port=8111`.

## Referencias

- **AGENTS.md** (toolkit web-cuantica): contexto unclic, subdominios, multiagentes.
- **docs/PLAN-DEMO-POS-FASTFLOW-WEB-CUANTICA.md**: pasos DNS, Gitea, Jenkins, primer build.
- **docs/CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md**: conectar repo local con Gitea.

# Stack: Java + Maven + Spring Boot

Base recomendada para uso inmediato:
- Jenkinsfile modular en `templates/common`
- `mvn clean install`, `mvn test`, `mvn checkstyle:check`
- package multi-entorno (envPrd/envQas/envPpr)

Variables sugeridas:
- `MAVEN_REPO_LOCAL`
- `MAVEN_CLI_OPTS`
- `DEPLOY_SERVER_HOST_ENVPRD` / `DEPLOY_APP_DIR_ENVPRD`

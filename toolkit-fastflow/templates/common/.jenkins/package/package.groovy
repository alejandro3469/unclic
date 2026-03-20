// Basado en patrón POS Online: package por perfiles de entorno

def run(steps) {
  steps.sh '''
    set -e
    M2="${MAVEN_REPO_LOCAL:-$HOME/.m2/repository}"
    mkdir -p release-jars

    mvn package -U -DskipTests -Dmaven.repo.local="$M2" -Dspring.profiles.active=envPrd $MAVEN_CLI_OPTS
    mv target/*-SNAPSHOT.jar target/app-envPrd.jar 2>/dev/null || true

    mvn package -U -DskipTests -Dmaven.repo.local="$M2" -Dspring.profiles.active=envQas $MAVEN_CLI_OPTS
    mv target/*-SNAPSHOT.jar target/app-envQas.jar 2>/dev/null || true

    mvn package -U -DskipTests -Dmaven.repo.local="$M2" -Dspring.profiles.active=envPpr $MAVEN_CLI_OPTS
    mv target/*-SNAPSHOT.jar target/app-envPpr.jar 2>/dev/null || true

    cp target/app-env*.jar release-jars/ || true
  '''
}
return this

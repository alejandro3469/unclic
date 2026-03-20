#!/usr/bin/env bash
set -euo pipefail

M2="${MAVEN_REPO_LOCAL:-$HOME/.m2/repository}"
MAVEN_CLI_OPTS="${MAVEN_CLI_OPTS:---batch-mode --errors --fail-at-end --show-version}"

mkdir -p release-jars

mvn package -U -DskipTests -Dmaven.repo.local="$M2" -Dspring.profiles.active=envPrd $MAVEN_CLI_OPTS
mv target/*-SNAPSHOT.jar target/app-envPrd.jar 2>/dev/null || true

mvn package -U -DskipTests -Dmaven.repo.local="$M2" -Dspring.profiles.active=envQas $MAVEN_CLI_OPTS
mv target/*-SNAPSHOT.jar target/app-envQas.jar 2>/dev/null || true

mvn package -U -DskipTests -Dmaven.repo.local="$M2" -Dspring.profiles.active=envPpr $MAVEN_CLI_OPTS
mv target/*-SNAPSHOT.jar target/app-envPpr.jar 2>/dev/null || true

cp target/app-env*.jar release-jars/ || true
ls -lh release-jars || true

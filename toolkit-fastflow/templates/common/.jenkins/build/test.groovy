def run(steps) {
  steps.sh 'mvn test -Dmaven.repo.local=${MAVEN_REPO_LOCAL} ${MAVEN_CLI_OPTS}'
}
return this

/**
 * fastflowIacHelpers.groovy — helpers para el pipeline Declarative del POS (FastFlow).
 *
 * CÓMO SE USA DESDE EL Jenkinsfile (mismo repo, raíz del workspace):
 *   def ffIac = load '.jenkins/groovy/fastflowIacHelpers.groovy'
 *   ffIac.echoStageBanner('IaC: Terraform validate')
 *
 * POR QUÉ EXISTE:
 *   - Mantiene el Jenkinsfile más corto.
 *   - Centraliza prefijos de log [FastFlow/IaC] para buscar en consola de Jenkins.
 *
 * NO CONTIENE:
 *   - Lógica de negocio Maven/Docker (sigue en stages shell del Jenkinsfile).
 *   - Credenciales: el job debe usar withCredentials en el Jenkinsfile si aplica.
 *
 * COMPATIBILIDAD:
 *   - Jenkins 2.x + Pipeline (CPS). Groovy del sandbox permitido (solo echo y rutas).
 */

/** Imprime un banner visible en el log Blue Ocean / consola. */
void echoStageBanner(String title) {
    echo "[FastFlow/IaC] ========== ${title} =========="
}

/** Ruta relativa al workspace del job (checkout de repo-pos-fastflow). */
String workspaceRoot() {
    return env.WORKSPACE ?: '.'
}

/** Directorio Terraform Kubernetes (manifiesto POS). */
String terraformK8sDir() {
    return "${workspaceRoot()}/deploy/terraform"
}

/** Directorio Terraform LocalStack (laboratorio S3). */
String terraformLocalstackDir() {
    return "${workspaceRoot()}/deploy/terraform-localstack"
}

/** Directorio proyecto Pulumi. */
String pulumiDir() {
    return "${workspaceRoot()}/deploy/pulumi"
}

// Jenkins `load` espera que el script devuelva `this` para asignar a una variable.
return this

/**
 * fastFlowUtils.groovy
 * Biblioteca Compartida Estándar de FastFlow
 * 
 * Proporciona utilidades comunes para simplificar los Jenkinsfiles y centralizar
 * la lógica de negocio del flujo de entrega.
 */

/**
 * Envía una notificación estandarizada a Slack o Consola.
 * @param Map config [status: 'SUCCESS'|'FAILURE', channel: '#deploy-alerts']
 */
def notify(Map config = [:]) {
    def status = config.status ?: 'SUCCESS'
    def color = (status == 'SUCCESS') ? 'good' : 'danger'
    def message = "FastFlow: Pipeline ${env.JOB_NAME} [${env.BUILD_NUMBER}] - Status: ${status}"
    
    echo "--- NOTIFICACIÓN FASTFLOW ---"
    echo message
    
    // Si el plugin de Slack está configurado, descomentar:
    // slackSend(channel: config.channel, color: color, message: message)
}

/**
 * Realiza un escaneo de seguridad rápido usando herramientas predefinidas.
 */
def securityScan() {
    stage('FastFlow Security Scan') {
        echo "Ejecutando escaneo de seguridad FastFlow..."
        // Ejemplo: Escaneo de secretos en el código
        sh 'grep -rE "password|secret|key" . || true'
        
        // Integración con herramientas SAST (Snyk, Checkmarx, etc.)
        echo "Validando dependencias..."
    }
}

/**
 * Wrapper para ejecución de comandos en contenedores específicos.
 * @param String containerName Nombre del contenedor en el Pod de K8s
 * @param Closure body Bloque de código a ejecutar
 */
def inContainer(String containerName, Closure body) {
    container(containerName) {
        body()
    }
}

/**
 * Genera un reporte de calidad simplificado para el dashboard.
 */
def generateQualityReport() {
    echo "Generando Reporte de Calidad FastFlow..."
    // Lógica para recolectar resultados de JUnit, SonarQube, etc.
    archiveArtifacts artifacts: '**/target/*.xml', allowEmptyArchive: true
}

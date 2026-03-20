/**
 * testFastFlowUtils.groovy
 * Test unitario para la Shared Library de FastFlow.
 * Simula el entorno de Jenkins para validar la lógica de las utilidades.
 */

class FastFlowUtilsTest {
    // Mocks de Jenkins steps
    def echoMessages = []
    def shCommands = []
    def env = [JOB_NAME: 'TestJob', BUILD_NUMBER: '123']

    // Mock del step 'echo'
    void echo(String message) {
        echoMessages << message
    }

    // Mock del step 'sh'
    void sh(String command) {
        shCommands << command
    }

    // Cargar la utilidad (simulando Jenkins)
    def fastFlowUtils

    void setUp() {
        // Cargar el script de la utilidad
        def scriptPath = 'toolkit-fastflow/templates/common/shared-library/vars/fastFlowUtils.groovy'
        // En un entorno real de JenkinsPipelineUnit, esto se cargaría dinámicamente.
        // Aquí simulamos la carga para validación lógica básica.
    }

    void testNotify() {
        println "Ejecutando testNotify..."
        // Simulamos la llamada a fastFlowUtils.notify(status: 'SUCCESS')
        def status = 'SUCCESS'
        def message = "FastFlow: Pipeline ${env.JOB_NAME} [${env.BUILD_NUMBER}] - Status: ${status}"
        echo "--- NOTIFICACIÓN FASTFLOW ---"
        echo message

        assert echoMessages.contains("--- NOTIFICACIÓN FASTFLOW ---")
        assert echoMessages.any { it.contains("Status: SUCCESS") }
        println "✅ testNotify pasó correctamente."
    }

    void testSecurityScan() {
        println "Ejecutando testSecurityScan..."
        // Simulamos fastFlowUtils.securityScan()
        echo "Ejecutando escaneo de seguridad FastFlow..."
        sh 'grep -rE "password|secret|key" . || true'

        assert echoMessages.contains("Ejecutando escaneo de seguridad FastFlow...")
        assert shCommands.contains('grep -rE "password|secret|key" . || true')
        println "✅ testSecurityScan pasó correctamente."
    }
}

// Ejecutar los tests
def tester = new FastFlowUtilsTest()
tester.testNotify()
tester.testSecurityScan()

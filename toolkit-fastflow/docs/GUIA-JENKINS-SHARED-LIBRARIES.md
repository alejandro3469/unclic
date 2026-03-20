# Guía de Bibliotecas Compartidas (Shared Libraries) (Capa 24 de FastFlow)

La repetición es el enemigo de la mantenibilidad. En FastFlow, centralizamos la lógica compleja de CI/CD en **Shared Libraries** para que tus `Jenkinsfile` sean cortos, limpios y fáciles de entender.

## 1. ¿Qué es una Shared Library?
Es un repositorio de código Groovy que Jenkins carga automáticamente para extender el lenguaje de tus pipelines. En lugar de copiar y pegar 50 líneas de código para enviar una notificación a Slack, simplemente llamas a una función: `fastFlowNotify()`.

## 2. Estructura de una Biblioteca FastFlow
Un repositorio de biblioteca compartida sigue esta estructura:
-   **`src/`**: Clases Groovy estándar para lógica compleja.
-   **`vars/`**: Scripts que definen variables globales o pasos personalizados (los más usados).
-   **`resources/`**: Archivos estáticos (JSON, YAML, XML) que tus scripts necesitan.

## 3. Beneficios del Enfoque FastFlow
1.  **Centralización de la Inteligencia**: Si cambia la forma en que escaneamos vulnerabilidades, solo lo cambiamos en un lugar.
2.  **Abstracción**: Los desarrolladores no necesitan ser expertos en Groovy. Solo usan comandos simples.
3.  **Versatilidad**: Puedes versionar tu biblioteca (`@master`, `@v2.0`) para probar cambios sin romper todos los pipelines.

## 4. Ejemplo de Paso Personalizado: `fastFlowBuild.groovy`
En `vars/fastFlowBuild.groovy`, definimos un estándar para compilar aplicaciones:
```groovy
def call(Map config = [:]) {
    stage('Build Application') {
        echo "Compilando con FastFlow para: ${config.type}"
        if (config.type == 'maven') {
            sh 'mvn clean package -DskipTests'
        } else if (config.type == 'npm') {
            sh 'npm install && npm run build'
        }
    }
}
```
En el `Jenkinsfile`, el desarrollador solo escribe: `fastFlowBuild(type: 'maven')`.

## 5. Mejores Prácticas de FastFlow
-   **Keep it Simple**: No conviertas tu biblioteca en un framework gigante e incomprensible.
-   **Unit Testing**: Usa **JenkinsPipelineUnit** para probar tu biblioteca antes de desplegarla.
-   **Documentación**: Cada paso en `vars/` debe tener un comentario explicando sus parámetros.
-   **Seguridad**: Evita usar `@Grab` o cargar scripts externos dinámicamente desde la biblioteca.

---
*Shared Libraries son el "cerebro" de tu infraestructura. Mantén la inteligencia en un solo lugar y la simplicidad en todas partes.*

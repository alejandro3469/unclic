# Jenkins: pipeline para pos-online (build → test → imagen → registry)

Referencia para configurar un **job en Jenkins** que construya, pruebe y empaquete **pos-online** (Maven) y opcionalmente construya imagen Docker y la suba a un registry. Implementación FastFlow a la medida para pos-online.

---

## Flujo del pipeline

1. **Checkout** — Código desde el repo (Git). Workspace: raíz del repo pos-online (donde está `pom.xml`). La dependencia **generic-model** debe estar disponible (carpeta hermana o submódulo).
2. **Build** — `mvn clean compile -q`.
3. **Tests** — `mvn test -q`. Si fallan, el pipeline se detiene.
4. **Package** — `mvn package -DskipTests -q`.
5. **Build imagen Docker** (opcional) — Si existe `Dockerfile` en la raíz: `docker build -t pos-online:${BUILD_NUMBER} .` desde la raíz.
6. **Tag y push a registry** (opcional) — `docker tag` y `docker push` cuando `REGISTRY` esté definido (credenciales en Jenkins).

---

## Ejemplo de Jenkinsfile (declarative)

Ejecutar desde la **raíz del repo pos-online** (donde está `pom.xml` y el `Jenkinsfile`). Ver **Jenkinsfile.example** en este toolkit.

```groovy
pipeline {
  agent any
  environment {
    REGISTRY = ''
    IMAGE_NAME = 'pos-online'
    IMAGE_TAG = "${env.BUILD_NUMBER ?: 'latest'}"
  }
  stages {
    stage('Build') {
      steps { sh 'mvn clean compile -q' }
    }
    stage('Test') {
      steps { sh 'mvn test -q' }
    }
    stage('Package') {
      steps { sh 'mvn package -DskipTests -q' }
    }
    stage('Build image') {
      when { expression { return fileExists('Dockerfile') && env.REGISTRY?.trim() != '' } }
      steps {
        script { docker.build("${IMAGE_NAME}:${IMAGE_TAG}", ".") }
      }
    }
    stage('Push to registry') {
      when { expression { return env.REGISTRY?.trim() != '' } }
      steps {
        sh """
          docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${env.REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
          docker push ${env.REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
          docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${env.REGISTRY}/${IMAGE_NAME}:latest
          docker push ${env.REGISTRY}/${IMAGE_NAME}:latest
        """
      }
    }
  }
  post {
    success { echo "Pipeline pos-online OK: ${IMAGE_NAME}:${IMAGE_TAG}" }
    failure { echo "Pipeline pos-online failed." }
  }
}
```

---

## Variables y credenciales en Jenkins

| Variable / Credencial | Uso |
|-----------------------|-----|
| `REGISTRY` | URL del registry (Docker Hub: tu usuario; GitLab: `registry.gitlab.com/grupo/proyecto`; AWS ECR: `123456789.dkr.ecr.region.amazonaws.com`). |
| Credenciales Docker | ID de credencial en Jenkins (usuario + contraseña o token) para `docker login` al registry. |

---

## Ejecución en demo (local o AWS)

1. Crear un **Pipeline job** (o Pipeline from SCM) y apuntar al repo pos-online.
2. Configurar credenciales del registry en **Manage Jenkins → Credentials** si vas a hacer push.
3. Ejecutar el job: debe pasar build, tests, package y (si hay Dockerfile y REGISTRY) construir imagen y hacer push.
4. La imagen se puede usar en Kubernetes, ECS o el entorno que corresponda (Terraform, deploy scripts).

---

## Referencias en este repo

- **Build y registry (manual):** docs/40-pipeline-registry/BUILD-REGISTRY-FASTFLOW.md
- **Requerimientos demo AWS:** docs/40-pipeline-registry/REQUIREMENTS-DEMO-AWS-JENKINS.md
- **Instalación Jenkins:** docs/30-instalacion/INSTALAR-JENKINS.md
- **Jenkinsfile.example** en la raíz de este toolkit.

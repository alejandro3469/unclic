# Guía de Flujos de Trabajo y CI/CD (Capa 10 de FastFlow)

¿Cómo interactúan los desarrolladores con el sistema? En FastFlow, definimos dos ciclos de trabajo (loops) para maximizar la velocidad sin sacrificar la estabilidad.

## 1. El Inner Loop (Ciclo Interno)
Es lo que sucede en la laptop del desarrollador.
- **Enfoque**: Rapidez.
- **Herramientas**: Docker Desktop, K3s, Minikube.
- **Regla FastFlow**: El entorno local debe emular al de producción lo más fielmente posible usando Docker Compose o manifiestos de K8s simplificados.

## 2. El Outer Loop (Ciclo Externo)
Es lo que sucede cuando haces `git push`.
- **Enfoque**: Seguridad, Calidad y Despliegue.
- **Herramientas**: Jenkins, Registry, Clúster de Kubernetes.
- **Flujo**: Build -> Test -> Security Scan -> Containerize -> Push -> Deploy.

## 3. Estrategias de Desarrollo
- **Docker-Centric**: Los desarrolladores son dueños de sus Dockerfiles y Compose files. Ideal para equipos con alta madurez técnica.
- **K8s-as-a-Service (PaaS)**: El equipo de plataforma provee el flujo. El desarrollador solo se preocupa por el código. El pipeline se encarga de empaquetar (usando Buildpacks o BuildKit).

## 4. Aislamiento con Namespaces
No despliegues todo en un solo lugar. FastFlow utiliza **Namespaces** para crear clústeres virtuales:
- `dev-team-a`: Para pruebas rápidas.
- `staging`: Para validación final (QA).
- `production`: El entorno sagrado.

## 5. Políticas de Imagen
- **IfNotPresent**: Recomendado para desarrollo (ahorra ancho de banda).
- **Always**: Obligatorio para producción (garantiza que usas la versión correcta).

---
*Para configurar tus ambientes de desarrollo, usa el script `scripts/setup-environments.sh`.*

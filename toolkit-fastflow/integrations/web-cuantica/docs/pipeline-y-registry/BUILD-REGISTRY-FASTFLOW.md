# Build y Registry — pos-online (implementación FastFlow)

**Objetivo:** Construir la imagen Docker de **pos-online** y (opcional) subirla a un registry. Flujo Jenkins → build Maven → imagen → registry → despliegue (K8s, ECS, etc.).

---

## Build de la imagen

Desde la **raíz del repo pos-online** (donde está `pom.xml` y, si aplica, `Dockerfile`):

```bash
# Primero compilar y empaquetar
mvn clean package -DskipTests -q

# Si hay Dockerfile en la raíz
docker build -t pos-online:latest .
```

Para etiquetar para un registry (ej. Docker Hub o GitLab):

```bash
docker tag pos-online:latest <registry>/pos-online:latest
# Ejemplo Docker Hub: docker tag pos-online:latest tuusuario/pos-online:latest
# Ejemplo GitLab:     docker tag pos-online:latest registry.gitlab.com/grupo/proyecto/pos-online:latest
```

---

## Push a registry (cuando esté definido)

```bash
docker login <registry>
docker push <registry>/pos-online:latest
```

El registry (Docker Hub, GitLab Container Registry, AWS ECR, etc.) se define según el entorno. Credenciales en Jenkins para el job de pipeline.

---

## Referencias

- **Pipeline Jenkins:** docs/pipeline-y-registry/JENKINS-PIPELINE-FASTFLOW.md
- **Requerimientos demo:** docs/pipeline-y-registry/REQUIREMENTS-DEMO-AWS-JENKINS.md
- **Instalación Jenkins:** docs/instalacion/INSTALAR-JENKINS.md  

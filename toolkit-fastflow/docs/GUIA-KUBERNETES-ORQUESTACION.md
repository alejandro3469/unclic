# Guía de Orquestación con Kubernetes (Capa 5 de FastFlow)

Kubernetes es el cerebro de la infraestructura moderna. En FastFlow, lo utilizamos para que tus aplicaciones no solo corran, sino que se auto-sanen, escalen y se actualicen sin tiempo de inactividad.

## 1. Conceptos Fundamentales
- **Cluster**: Un conjunto de servidores (nodos) que trabajan como una sola unidad lógica.
- **API**: El lenguaje que usas para decirle a Kubernetes qué quieres (vía archivos YAML).
- **Manifests**: Tus "plano" de la aplicación. Describen el estado deseado.

## 2. Los Recursos de FastFlow en K8s
Para que una app sea "FastFlow-ready" en Kubernetes, necesita dominar estos recursos:
- **Pod**: La unidad mínima (donde vive tu contenedor).
- **Deployment**: Define cuántas copias de tu app quieres y cómo se actualizan (Rolling Update).
- **Service**: La dirección fija para que otros componentes encuentren tu app.
- **ConfigMap & Secrets**: Para separar la configuración y las contraseñas del código.

## 3. Beneficios del Flujo con K8s
1. **Self-healing**: Si un contenedor falla, Kubernetes lo reinicia. Si un nodo muere, Kubernetes mueve los contenedores a otro.
2. **Escalabilidad**: Sube o baja el número de copias según la demanda.
3. **Consistencia**: El mismo archivo YAML despliega igual en tu laptop (Docker Desktop/K3s) que en la nube (AKS/EKS).

## 4. Herramientas de Control
La herramienta principal es `kubectl` (pronunciado "cube-cuttle").
- `kubectl get nodes`: Verifica la salud de tus servidores.
- `kubectl apply -f manifest.yaml`: Despliega tu aplicación.
- `kubectl get pods`: Mira tus aplicaciones en acción.

---
*Para configurar tu entorno local, recomendamos Docker Desktop o K3s.*

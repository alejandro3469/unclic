# Kubernetes - Knowledge Base

## Rol en el sistema

Kubernetes orquesta contenedores con estado deseado:

- Deploy declarativo
- Escalado
- Auto-healing
- Exposición por servicios/ingress

## Formas de adopción identificadas

- `kubectl apply` directo
- Helm charts por entorno
- Jenkins X para flujos GitOps-like

## Evidencia en fuentes

- Deploy con kubectl desde Jenkins: `_book_reference_txt/chapter11/deployment/kubectl/Jenkinsfile.txt`
- Deploy con Helm y metadatos de build: `_book_reference_txt/chapter11/Jenkinsfile.eks.txt`
- Valores de chart por entorno: `_book_reference_txt/chapter11/deployment/helm/watchlist/values.yaml.txt`


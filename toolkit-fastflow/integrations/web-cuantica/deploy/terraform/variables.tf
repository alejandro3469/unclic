# Variables para el módulo Terraform FastFlow (despliegue en cluster K8s existente).
# Uso: terraform plan -var="fastflow_image=registry.io/mi/fastflow-server:latest"

variable "namespace" {
  description = "Namespace de Kubernetes para FastFlow"
  type        = string
  default     = "fastflow"
}

variable "fastflow_image" {
  description = "Imagen completa del servidor FastFlow (registry + nombre + tag)"
  type        = string
  default     = "fastflow-server:latest"
}

variable "base_url" {
  description = "BASE_URL del servidor (ej. URL pública del sitio)"
  type        = string
  default     = "http://localhost:3000"
}

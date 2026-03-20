variable "namespace" {
  description = "Namespace Kubernetes para POS Online"
  type        = string
  default     = "pos-online"
}

variable "pos_image" {
  description = "Imagen completa (registry/pos-online:tag)"
  type        = string
  default     = "pos-online:latest"
}

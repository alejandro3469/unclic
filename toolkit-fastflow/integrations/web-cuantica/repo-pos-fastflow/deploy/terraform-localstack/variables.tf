# -----------------------------------------------------------------------------
# variables.tf — módulo Terraform "lab" contra LocalStack (FastFlow / POS pipeline)
#
# Contexto:
#   Este módulo NO sustituye deploy/terraform/ (Kubernetes). Sirve para que el
#   mismo job Jenkins pueda ejecutar un `terraform plan` contra APIs emuladas
#   y practicar el flujo commit → CI → IaC sin tocar AWS real.
#
# Conexión con Jenkins:
#   .jenkins/scripts/terraform-plan-localstack.sh pasa -var=localstack_endpoint=...
# -----------------------------------------------------------------------------

variable "localstack_endpoint" {
  description = "URL base del gateway LocalStack (sin path). Ej: http://localhost:4566"
  type        = string
  default     = "http://localhost:4566"
}

variable "lab_bucket_name" {
  description = "Nombre globalmente único del bucket S3 de laboratorio (solo dentro de LocalStack)."
  type        = string
  default     = "fastflow-pos-lab-artifacts"
}

variable "aws_region" {
  description = "Región simulada; LocalStack la acepta aunque no sea una región real."
  type        = string
  default     = "us-east-1"
}

# =============================================================================
# main.tf — Terraform AWS provider → LocalStack (laboratorio FastFlow)
# =============================================================================
# Resumen:
#   Define un bucket S3 ficticio en LocalStack para alinear el pipeline Jenkins
#   con prácticas IaC. Los artefactos reales del POS siguen yendo al Docker
#   registry (ver Jenkinsfile stages Build image / Push).
#
# Relación con otros archivos:
#   - deploy/terraform/main.tf     → Kubernetes, imagen pos_image
#   - .jenkins/scripts/terraform-plan-localstack.sh → ejecuta init/plan aquí
#   - unclic/docs/LOCALSTACK-IAC-JENKINS-POS.md      → contexto conceptual
#
# Credenciales:
#   LocalStack ignora valores reales; test/test es convención documentada.
# =============================================================================

terraform {
  required_version = ">= 1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.0"
    }
  }
}

provider "aws" {
  region                      = var.aws_region
  access_key                  = "test"
  secret_key                  = "test"
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true
  # LocalStack S3 suele requerir path-style (ver doc LocalStack + Terraform).
  s3_use_path_style = true

  endpoints {
    s3  = var.localstack_endpoint
    iam = var.localstack_endpoint
    sts = var.localstack_endpoint
  }
}

# Recurso mínimo: bucket. En AWS real cambiarías políticas, encryption, etc.
resource "aws_s3_bucket" "pos_lab" {
  bucket = var.lab_bucket_name
}

resource "aws_s3_bucket_versioning" "pos_lab" {
  bucket = aws_s3_bucket.pos_lab.id
  versioning_configuration {
    status = "Suspended"
  }
}

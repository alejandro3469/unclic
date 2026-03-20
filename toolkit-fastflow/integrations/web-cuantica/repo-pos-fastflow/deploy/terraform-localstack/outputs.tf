# -----------------------------------------------------------------------------
# outputs.tf — valores útiles tras apply (en LocalStack son locales al emulador)
# -----------------------------------------------------------------------------

output "lab_bucket_id" {
  description = "ID/nombre del bucket de laboratorio"
  value       = aws_s3_bucket.pos_lab.id
}

output "localstack_endpoint_used" {
  description = "Endpoint configurado en el provider"
  value       = var.localstack_endpoint
}

output "registry_public_ip" {
  description = "IP pública del Registry; push/pull http://<ip>:5000 (insecure-registries si no TLS)"
  value       = aws_instance.registry.public_ip
}

output "registry_private_ip" {
  value = aws_instance.registry.private_ip
}

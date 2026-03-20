output "gitea_public_ip" {
  description = "IP pública de Gitea; acceso http://<ip>:3000 o vía Nginx :80/:443"
  value       = aws_instance.gitea.public_ip
}

output "gitea_private_ip" {
  value = aws_instance.gitea.private_ip
}

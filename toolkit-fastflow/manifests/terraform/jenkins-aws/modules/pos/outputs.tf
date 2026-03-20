output "pos_public_ip" {
  description = "IP pública de la instancia POS; app en http://<ip>:8111 tras deploy"
  value       = aws_instance.pos.public_ip
}

output "pos_private_ip" {
  value = aws_instance.pos.private_ip
}

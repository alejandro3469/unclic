# Infraestructura como código (OpenTofu / Terraform)

Esta carpeta es el **anclaje** para evolucionar de “un VPS + Nginx” a nubes declarativas.

## Documentación oficial

- **OpenTofu:** [opentofu.org/docs](https://opentofu.org/docs/)
- **Terraform lenguaje:** [developer.hashicorp.com/terraform/language](https://developer.hashicorp.com/terraform/language) (revisa licencia si usas binario HashiCorp)

## Próximos pasos sugeridos (no implementados aquí)

1. `terraform { required_providers { aws = { ... } } }` y módulo **EC2** + **security group** (22/80/443).
2. **Outputs**: IP pública, zona DNS.
3. Separar **estado** remoto (S3 + lock Dynamo o equivalente) antes de equipo >1 persona.

Los recursos reales dependen de tu cuenta AWS/GCP; no se commitea `terraform.tfstate` con secretos.

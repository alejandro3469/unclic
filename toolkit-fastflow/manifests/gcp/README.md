# GCP Blueprint Skeleton

Base para llevar el modelo Jenkins + workers autoscalables a GCP.

Capas:
- network (VPC + subredes)
- bastion opcional
- jenkins controller VM
- workers group + autoscaler

Nota:
- Mantener parity funcional con AWS blueprint.
- Ajustar autenticación con service account y políticas mínimas.

# Azure Blueprint Skeleton

Base para llevar el modelo Jenkins + workers autoscalables a Azure.

Capas:
- VNet + subredes
- bastion administrado/opcional
- jenkins controller VM
- workers VM scale set + autoscale

Nota:
- Mantener parity funcional con AWS blueprint.
- Ajustar identidad con service principal/managed identity y mínimo privilegio.

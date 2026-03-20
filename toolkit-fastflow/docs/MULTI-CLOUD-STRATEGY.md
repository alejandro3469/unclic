# Multi-Cloud Strategy (Toolkit)

Objetivo:
- Reusar el mismo modelo operativo Fast Flow en AWS, GCP, Azure y DigitalOcean.
- Evitar lock-in prematuro sin aumentar complejidad innecesaria.

## Principio de diseño

- Core común (siempre):
  - pipeline as code
  - artefactos versionados
  - seguridad base
  - observabilidad mínima
- Capa cloud-specific (variable):
  - red
  - cómputo
  - balanceo
  - autoscaling
  - identidad/permisos

## Ruta recomendada

1. AWS first (camino más avanzado del toolkit actual).
2. GCP second (estructura compatible para workers autoscalables).
3. Azure third (equivalentes de red/VM scale set).
4. DigitalOcean para escenarios costo-sensibles/startup.

## Regla de adopción

- Multi-cloud no es requisito inicial.
- Se activa cuando hay necesidad real de:
  - compliance/región,
  - continuidad de negocio,
  - negociación de costos,
  - dependencia estratégica.

## Mapeo de capacidades por proveedor

- AWS: EC2 + ASG + CloudWatch + VPC
- GCP: Compute Engine + MIG/Autoscaler + VPC
- Azure: VM/VMSS + Monitor Autoscale + VNet
- DigitalOcean: Droplets + snapshots + autoscaling limitado según servicio

## Contrato del toolkit para multi-cloud

1. Misma lógica de pipeline y promotion.
2. Misma política de tags/versionado de artefactos.
3. Mismo cuestionario de entrada.
4. Mismo scorecard KPI.
5. Implementación por fase, no big bang.

# Packer + Infraestructura Inmutable (Baseline Curado)

Objetivo:
- Estandarizar el enfoque de "bake and replace" para Jenkins y workers.
- Evitar drift de configuración en servidores mutables.

## Principios

1. No parchear manualmente instancias en producción.
2. Toda actualización se empaqueta en una nueva imagen.
3. Despliegue = crear instancia nueva + retirar instancia anterior.
4. Versionar imágenes y conservar historial para rollback.

## Flujo recomendado

1. Definir plantilla Packer en HCL2.
2. Ejecutar `packer init`.
3. Validar con `packer validate`.
4. Construir con `packer build`.
5. Publicar ID/metadata de imagen.
6. Desplegar con Terraform usando la imagen nueva.
7. Reemplazar nodos antiguos de forma controlada.

## Decisiones de actualización para 2026

- Usar Packer HCL2 (preferido sobre JSON legacy).
- No usar recetas antiguas con Java 8 para Jenkins moderno.
- Controller Jenkins: runtime Java 17+.
- Builds legacy Java 11: lane separado por tools/agents.

## Integración con toolkit

Ubicación de plantillas base:
- `toolkit-fastflow/manifests/packer/jenkins-controller.pkr.hcl`
- `toolkit-fastflow/manifests/packer/jenkins-agent.pkr.hcl`

Scripts base:
- `toolkit-fastflow/manifests/packer/scripts/controller-setup.sh`
- `toolkit-fastflow/manifests/packer/scripts/agent-setup.sh`

## Referencias oficiales

- Packer docs: https://developer.hashicorp.com/packer/docs
- Packer templates HCL: https://developer.hashicorp.com/packer/docs/templates/hcl_templates
- amazon-ebs builder: https://developer.hashicorp.com/packer/integrations/hashicorp/amazon/latest/components/builder/ebs
- Jenkins Linux install: https://www.jenkins.io/doc/book/installing/linux/

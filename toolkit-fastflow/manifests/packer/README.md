# Packer Templates (Toolkit)

Plantillas base para enfoque inmutable de Jenkins.

## Archivos

- `jenkins-controller.pkr.hcl`: AMI base para controller Jenkins.
- `jenkins-agent.pkr.hcl`: AMI base para workers/agents Jenkins.
- `scripts/controller-setup.sh`: provisioning base controller.
- `scripts/agent-setup.sh`: provisioning base agent.

## Uso

```bash
cd toolkit-fastflow/manifests/packer
packer init .
packer validate -var-file=vars.pkrvars.hcl jenkins-controller.pkr.hcl
packer build -var-file=vars.pkrvars.hcl jenkins-controller.pkr.hcl
```

## Nota

Estas plantillas son base portable. Ajusta:
- AMI base por región
- tipo de instancia de bake
- configuración de red (VPC/subnet/sg)
- políticas IAM mínimas

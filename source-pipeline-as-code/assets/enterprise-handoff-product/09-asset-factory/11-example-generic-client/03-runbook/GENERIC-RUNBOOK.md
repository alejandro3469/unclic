# Runbook - Generic Client Service

## Servicio
- Nombre: [SERVICE_NAME]
- Dueño técnico: [TECH_OWNER]
- Entornos: [ENV_LIST]

## Operación diaria
- Verificar última ejecución de pipeline
- Revisar alertas y health checks
- Confirmar estado de servicios críticos

## Incidentes comunes
### Build/Deploy failure
- Síntoma: [SYMPTOM]
- Diagnóstico: [DIAGNOSIS_STEPS]
- Mitigación: [MITIGATION_STEPS]
- Escalación: [ESCALATION_OWNER]

### Resource saturation
- Síntoma: [CPU/MEM/DISK_ALERT]
- Diagnóstico: [RESOURCE_CHECKS]
- Mitigación: [SCALING/TUNING]
- Escalación: [INFRA_OWNER]

## Rollback
- Trigger: [ROLLBACK_CONDITION]
- Procedure: [ROLLBACK_STEPS]
- Validation: [POST_ROLLBACK_CHECKS]

## Contactos
- Dev: [DEV_CONTACT]
- Ops: [OPS_CONTACT]
- Security: [SEC_CONTACT]

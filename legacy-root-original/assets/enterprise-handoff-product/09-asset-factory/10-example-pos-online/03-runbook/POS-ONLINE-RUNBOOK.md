# Runbook - POS Online

## Servicio
- Nombre: pos-online
- Dueño técnico: Equipo backend / DevOps
- Entorno: main -> envPrd (referencia principal)

## Operación diaria
- Revisar estado de último build en Jenkins
- Verificar logs de pipeline y resultados de stages
- Confirmar salud de instancia después de deploy

## Incidentes comunes
### Incidente A: Falla en Package
- Síntoma: stage Package falla por manejo de artefactos
- Diagnóstico: revisar nombres de JAR y comandos de renombrado/copia
- Mitigación: validar script de package y existencia de artefactos esperados
- Escalación: líder técnico backend

### Incidente B: Inestabilidad por recursos
- Síntoma: lentitud, fallas intermitentes o OOM
- Diagnóstico: revisar RAM disponible, procesos Java, disco y swap
- Mitigación: ajustar recursos (p. ej. instancia), reducir presión de procesos simultáneos
- Escalación: owner de infraestructura

## Rollback
- Condición de rollback: health check fallido o degradación severa post deploy
- Pasos:
  1. Detener proceso desplegado recientemente
  2. Restaurar artefacto anterior conocido
  3. Iniciar servicio y validar health
- Verificación post-rollback: health OK + logs estables

## Contactos
- Dev owner: backend lead
- Ops owner: DevOps/Jenkins owner
- Seguridad: responsable de credenciales y accesos

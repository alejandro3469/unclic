# Cuestionario de Implementación Fast Flow

Llena este formulario para seleccionar el modelo de implementación con mínimo esfuerzo.

## 1) Contexto del cliente

- Nombre del cliente:
- Responsable técnico:
- Responsable negocio:
- ¿Implementación asistida o self-service?:

## 2) Etapa de madurez actual

Selecciona una:
- [ ] Manual (deploy ad-hoc, poca trazabilidad)
- [ ] Monolito (app única, release con fricción)
- [ ] Microservicios (varios servicios, coordinación compleja)
- [ ] Cloud (multi-entorno con IaC)

## 3) Stack principal

Selecciona stack base:
- [ ] Java + Maven + Spring Boot
- [ ] Node.js
- [ ] Python
- [ ] .NET
- [ ] Otro (especificar):

## 4) Repositorio y colaboración

- Provider actual (GitHub/GitLab/Bitbucket/otro):
- ¿Darán acceso al repo al equipo Fast Flow?:
- ¿Modelo de trabajo?:
  - [ ] Fast Flow implementa
  - [ ] Co-implementación
  - [ ] Cliente implementa con guía

## 5) Pipeline actual

- ¿Ya tienen Jenkins?:
- ¿Tienen Pipeline as Code?:
- ¿Tienen tests automáticos?:
- ¿Tienen quality gate?:
- ¿Tienen rollback probado?:

## 6) Runtime y despliegue

- ¿Deploy actual?:
  - [ ] Manual en servidor
  - [ ] Script remoto
  - [ ] Contenedores
  - [ ] Kubernetes
- ¿Necesitan aprobación manual antes de deploy?:
- ¿Health endpoint disponible (`/actuator/health` o similar)?:

## 7) Módulos opcionales a activar

- [ ] Registry (Docker Hub / ECR / GHCR / GitLab Registry)
- [ ] Kubernetes (manifiestos/Helm)
- [ ] Terraform (entornos reproducibles)
- [ ] Observabilidad base (logs, métricas, alertas)
- [ ] **Seguridad Avanzada (RBAC + GitHub OAuth)**
- [ ] **Shared Libraries (Centralización de lógica)**
- [ ] **Mantenimiento Automatizado (Backups/Cleanup)**

## 8) Seguridad y Gobernanza

- ¿Tienen política de rotación de secretos?:
- ¿Cómo gestionan los usuarios en Jenkins actualmente?:
- ¿Requieren auditoría de cambios (Audit Log)?:
- ¿Usan algún Vault externo (HashiCorp/AWS/GCP)?:

## 9) Reutilización de Código

- ¿Tienen lógica de CI/CD repetida en múltiples repositorios?:
- ¿Les interesa centralizar estándares en una Shared Library?:
- ¿Tienen desarrolladores con conocimientos de Groovy?:

## 10) Restricciones

- Compliance/regulación:
- Restricciones de red:
- Restricciones de costo:
- Fecha objetivo de piloto:

## 11) Atribución y Partners

- Partner Source:
- ID de Consultor (unclic.consulting):
- ¿Desea integrar con su dominio personalizado?:

## 12) Resultado esperado del piloto (2-4 semanas)

- Servicio piloto:
- KPIs objetivo (Lead Time, DF, CFR, MTTR):
- Criterio de éxito:

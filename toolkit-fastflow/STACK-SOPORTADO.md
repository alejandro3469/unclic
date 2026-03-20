# Stacks Soportados y Límites del Toolkit

## Soporte oficial (plug-and-Play)

0. **Capa 0: Linux (Foundation)**
   - Distribuciones recomendadas: Ubuntu Server (LTS), Amazon Linux 2/2023, Debian 11/12, RHEL/CentOS Stream.
   - Requisitos: Shell Bash 4+, visibilidad de red por SSH.

1. Java + Maven + Spring Boot (prioridad alta, basado en POS Online).
2. Node.js (soporte base).
3. Python (soporte base).
4. .NET (soporte base).

## Política de portabilidad Linux

- Runtime Jenkins: Java 17+ (requisito de Jenkins moderno).
- Build lane Java cliente: Java 11 soportado.
- Instalación por familia Linux: Debian/Ubuntu y RPM-like (RHEL, Amazon Linux, derivados).
- Módulos opcionales activables por perfil: `core`, `k8s`, `full`.

## Lane Java inicial (objetivo inmediato)

- JDK 11 para compilación de proyectos.
- Maven 3.9.x.
- Spring Boot en línea compatible con Java 11 (por ejemplo 2.7.x).
- Evolución a Java 17/Boot 3+ por fase cuando el cliente esté listo.

## Etapas por madurez

- Manual: base Jenkins + build/test + deploy controlado.
- Monolito: + package por entorno + rollback + health checks.
- Microservicios: + registry + versionado de imágenes + Helm/K8s.
- Cloud: + Terraform + ambientes reproducibles + promotion controlada.

## Alcance estándar de soporte

Incluye:
- Plantilla pipeline modular.
- Scripts de package/deploy.
- Documentación de operación.
- Checklists de entrada/salida.

No incluye (sin acuerdo adicional):
- Refactor profundo de arquitectura.
- Reescritura completa de pipeline legado no compatible.
- Integraciones propietarias fuera de alcance.

## Política de mínimo esfuerzo para cliente

- Se prioriza cuestionario + configuración declarativa.
- Se evita lógica oculta y pasos manuales no documentados.
- Cada módulo se puede activar de forma incremental.

# Jenkins Network Security Baseline (Cloud)

Objetivo:
- Definir baseline mínimo de seguridad para Jenkins en nube.

## Reglas mínimas

1. Jenkins controller en subred privada.
2. Acceso administrativo por bastion/VPN o canal equivalente seguro.
3. Exposición pública solo vía balanceador/reverse proxy (si aplica).
4. Security Groups de mínimo privilegio (ingress/egress restringidos).
5. Rotación y gestión segura de credenciales.

## Puertos típicos (ajustar por entorno)

- UI Jenkins interna: 8080 (no exponer directo a internet en producción).
- SSH admin: restringido por IP origen.
- Agentes: según método de conexión definido (SSH/JNLP/otros).

## Recomendaciones operativas

- Segmentar entornos (dev/preprod/prod).
- Habilitar TLS en acceso externo.
- Mantener auditoría de cambios de red y credenciales.

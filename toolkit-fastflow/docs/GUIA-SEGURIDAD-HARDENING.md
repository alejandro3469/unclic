# Guía de Seguridad y Hardening (Capa 2 de FastFlow)

En el mundo de la entrega continua, tu pipeline es el blanco favorito. Si un atacante compromete tu Jenkins, tiene las llaves de tu producción.

## 1. La Paradoja del Defensor
- **Defensores**: Deben proteger *todo*, *siempre*. Un solo olvido (un puerto abierto, una contraseña débil) es suficiente para un desastre.
- **Atacantes**: Solo necesitan encontrar *una* debilidad para ganar.

En FastFlow, aplicamos la mentalidad de **Defensa en Profundidad**.

## 2. Superficie de Ataque (Attack Surface)
Cada componente de tu flujo técnico suma a la superficie de ataque:
- El servidor Jenkins y sus agentes.
- Tus repositorios de código.
- Tu registro de imágenes (Registry).
- Tu clúster de Kubernetes.

**Regla de Oro**: Si no lo necesitas, apágalo. Si no lo usas, bórralo.

## 3. Prácticas de Hardening
1. **Principio de Menor Privilegio**:
   - El usuario `jenkins` no debe ser `root`.
   - Usa credenciales con permisos limitados (solo lectura de Git, solo push a Registry).
2. **Protección de Secretos**:
   - Nunca escribas contraseñas en el `Jenkinsfile`. Usa el **Credential Store** de Jenkins.
   - Usa herramientas como HashiCorp Vault para gestionar secretos dinámicos.
3. **Control de Red**:
   - Tu Jenkins no debería ser accesible desde internet público. Usa VPN o Bastion Hosts.
   - Cierra todos los puertos innecesarios (solo deja 443/80 y SSH restringido).
4. **Parcheo Continuo**:
   - Aplica actualizaciones de seguridad de Linux y Jenkins de forma regular.

## 4. 2FA y Autenticación Fuerte
No confíes solo en el usuario/contraseña. Habilita **Autenticación de Dos Factores (2FA)** para todos los accesos administrativos a tus herramientas de flujo.

---
*Para auditorías de seguridad, recomendamos realizar simulaciones de ataque (Penetration Testing) de forma periódica.*

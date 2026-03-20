# Guía de Redes para DevOps (Capa 1 de FastFlow)

En CI/CD, si no hay red, no hay flujo. Tu código vive en Git, tus imágenes en un Registry y tu despliegue en un Clúster. Entender cómo se conectan es vital.

## 1. El Alfabeto del Networking: TCP/IP
- **IP Address**: Tu dirección postal en la red. Sin ella, no existes.
- **NAT (Network Address Translation)**: La magia que permite que muchos servidores compartan una sola IP pública. Es la base de la seguridad en la nube (VPC).
- **Subnetting (CIDR)**: Cómo dividimos el pastel. Ejemplo: `/24` significa que los primeros 3 octetos son la red, y el último es para tus servidores.

## 2. Herramientas de Diagnóstico (Tus Radares)
Cuando un build falla al conectar con Git, usa estos comandos:

- `ip addr`: Muestra tus interfaces. ¿Tienes una IP asignada? ¿Está en el rango correcto?
- `ip route`: ¿Sabes por dónde salir a internet? Verifica el `default gateway`.
- `ping <ip>`: La prueba de vida más básica.
- `dhclient`: Solicita una IP dinámica si tu servidor se quedó "mudo".

## 3. Resolución de Problemas (Protocolo FastFlow)
Si Jenkins no puede alcanzar el Registry:
1. **Local**: ¿Tengo IP? (`ip addr`)
2. **Gateway**: ¿Puedo ver mi router? (`ping 192.168.1.1`)
3. **Externo**: ¿Puedo ver internet? (`ping 8.8.8.8`)
4. **DNS**: ¿Puedo traducir nombres? (`nslookup google.com`)

## 4. Configuración Estática vs Dinámica
- **DHCP**: Ideal para agentes de Jenkins temporales (nodos que suben y bajan).
- **Estática**: Obligatoria para el Servidor Jenkins y Base de Datos, para que su dirección no cambie nunca.

---
*Para profundizar en NAT y subnetting, consulta "Troubleshooting network issues" en la sección de referencias.*

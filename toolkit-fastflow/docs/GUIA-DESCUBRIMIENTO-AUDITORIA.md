# Guía de Descubrimiento y Auditoría de Activos (Capa 3 de FastFlow)

¿Qué tienes exactamente en tu red? En FastFlow, la primera fase de la seguridad es la **Visibilidad**. Si no sabes que un servidor existe, no puedes protegerlo ni incluirlo en tu flujo técnico.

## 1. Las Tres Sub-fases del Descubrimiento
1. **Host Discovery**: Identificar qué máquinas están encendidas y tienen una IP en tu rango de red.
2. **Service Discovery**: Identificar qué servicios (Jenkins, Docker, Bases de Datos) están escuchando en esas máquinas.
3. **Vulnerability Discovery**: Identificar debilidades en esos servicios.

## 2. Definición del Alcance (Scoping)
Antes de auditar tu red FastFlow, define cómo vas a trabajar:
- **Black-box**: No sabes nada del entorno. Simulas a un atacante externo que acaba de entrar.
- **Grey-box**: Tienes una lista de rangos de IP. Es el enfoque más común en auditorías internas de DevOps.
- **White-box**: Tienes acceso total a inventarios y diagramas de red.

## 3. Artefactos de Auditoría
En cada ejercicio de descubrimiento en FastFlow, generamos dos archivos clave:
- `targets.txt`: La lista de IPs que vamos a incluir en nuestro flujo de monitoreo y seguridad.
- `ignore.txt`: La lista de exclusión (Blacklist) de máquinas que no debemos tocar (sistemas críticos de legado o fuera de alcance).

## 4. Footprinting (Enumeración)
El **Footprinting** es el arte de recolectar información sobre un sistema desconocido. En FastFlow, automatizamos este proceso para detectar "Shadow IT" (servidores que alguien levantó pero que nadie está gestionando).

---
*Para realizar escaneos técnicos, consulta el script `installers/scan-network.sh` y la guía de Nmap.*

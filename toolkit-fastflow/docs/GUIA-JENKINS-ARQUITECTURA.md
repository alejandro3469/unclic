# Guía de Arquitectura Distribuida y Escalado de Jenkins (Capa 18 de FastFlow)

Un solo servidor Jenkins no es suficiente para la escala de FastFlow. Para garantizar la alta disponibilidad y el rendimiento, utilizamos una arquitectura distribuida de **Maestro-Agente**.

## 1. El Maestro (Jenkins Controller)
Es el cerebro de la operación. Sus responsabilidades incluyen:
- **Programación**: Decidir qué trabajo se hace y cuándo.
- **Distribución**: Enviar las tareas a los agentes disponibles.
- **Monitorización**: Vigilar el estado de los agentes y recolectar los resultados.
- **Interfaz**: Servir el dashboard web para los usuarios.

## 2. Los Agentes (Jenkins Agents/Workers)
Son el músculo. Son ejecutables Java que corren en máquinas remotas y hacen el trabajo sucio:
- **Ejecución**: Corren los comandos definidos en el Jenkinsfile.
- **Escalabilidad**: Puedes añadir cientos de agentes para manejar cargas pesadas.
- **Aislamiento**: Si un agente falla durante un build pesado, el Maestro sigue vivo.

## 3. Etiquetas (Labels): El Sistema de Clasificación
FastFlow utiliza etiquetas para dirigir trabajos a máquinas específicas.
- **Ejemplo**: `agent { label 'linux-docker' }` asegura que el trabajo solo corra en agentes que tengan Docker instalado sobre Linux.
- **Flexibilidad**: Puedes tener agentes especializados (ej: con GPUs para IA, o Windows para .NET legacy).

## 4. Estrategias de Conexión
- **SSH**: El estándar para agentes Linux. El Maestro se conecta proactivamente al agente.
- **JNLP (Java Web Start)**: Ideal para agentes detrás de firewalls o en Windows. El agente se conecta al Maestro.

## 5. Escalado Dinámico en el Cloud (AWS/K8s)
FastFlow no mantiene agentes encendidos 24/7 si no son necesarios.
- **Auto-scaling**: Usamos grupos de auto-escalado (ASG) o pods efímeros en Kubernetes.
- **Costo-Eficiencia**: Cuando la cola de trabajos crece, se lanzan nuevos agentes. Cuando se vacía, se destruyen.

---
*La arquitectura distribuida transforma a Jenkins de un servidor solitario en una granja de construcción imparable.*

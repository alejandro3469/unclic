# Guía de Arquitectura de Clusters y Multi-arquitectura (Capa 12 de FastFlow)

Entender qué ocurre debajo del capó de Kubernetes es vital para diseñar sistemas de alta disponibilidad (HA). FastFlow está diseñado para correr en cualquier lugar, desde nubes públicas hasta centros de datos privados con hardware mixto.

## 1. Anatomía del Plano de Control (Control Plane)
El cerebro de FastFlow no es una sola pieza, sino un conjunto de componentes trabajando en armonía:
- **API Server**: El único punto de entrada. Todo pasa por aquí.
- **etcd**: La base de datos de la verdad. Si pierdes etcd, pierdes el cluster.
- **Scheduler**: El "maitre" que decide en qué mesa (nodo) se sienta cada comensal (Pod).
- **Controller Manager**: El vigilante que asegura que el estado deseado coincida con el real.

## 2. Nodos de Trabajo (Worker Nodes)
Donde vive la acción:
- **kubelet**: El agente secreto en cada nodo que recibe órdenes del API Server.
- **kube-proxy**: El guardia de tráfico que gestiona las reglas de red.
- **Container Runtime**: El motor (Docker, containerd) que realmente corre los contenedores.

## 3. Alta Disponibilidad (HA)
En producción, FastFlow requiere un número impar de nodos de control (mínimo 3) para evitar el problema de "split-brain" y permitir que el sistema siga funcionando si uno cae.

## 4. Multi-arquitectura: El Poder de la Mezcla
Kubernetes es agnóstico al hardware. Un solo cluster de FastFlow puede tener:
- Nodos **Linux x86_64** (Estándar).
- Nodos **ARM64** (Ahorro de costos en AWS Graviton o Raspberry Pi).
- Nodos **Windows Server** (Para aplicaciones legacy .NET).

### ¿Cómo lo logramos?
Usamos **Taints** (manchas) y **Tolerations** (tolerancias) para asegurar que cada carga de trabajo caiga en el hardware adecuado.
- Ejemplo: Una App de Windows solo se programará en nodos que tengan el "taint" de Windows.

## 5. Instalación con kubeadm
FastFlow utiliza `kubeadm` para la inicialización de clusters por su madurez y flexibilidad.
```bash
# Inicialización típica de FastFlow
sudo kubeadm init --pod-network-cidr="10.244.0.0/16"
```

---
*La infraestructura es el cimiento. Si el cimiento es sólido y entiende su hardware, el flujo nunca se detiene.*

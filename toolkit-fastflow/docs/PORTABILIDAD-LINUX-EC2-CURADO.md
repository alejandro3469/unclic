# Portabilidad Linux + EC2 (Curado desde Docs Oficiales)

Fecha: 2026-03-10

Objetivo:
- Que el toolkit sea instalable en Linux estándar.
- Priorizar Java 11 + Maven + Spring Boot en fase inicial.
- Habilitar expansión rápida a otros stacks con mínima fricción.

## 1) Decisión clave de compatibilidad Java/Jenkins

Hecho oficial:
- Jenkins moderno (LTS actual) requiere Java 17+ para controller y agentes.
  Fuente: https://www.jenkins.io/doc/book/platform-information/support-policy-java/

Hecho oficial adicional:
- El Java del runtime de Jenkins es independiente del Java usado en builds.
  Fuente: https://www.jenkins.io/doc/book/platform-information/support-policy-java/

Decisión del toolkit:
- Runtime Jenkins: Java 17.
- Builds Java del proyecto: Java 11 (toolchain o JDK instalado adicional).

Esto permite cumplir tu objetivo:
- Operar Jenkins actualizado y seguro.
- Seguir construyendo proyectos Java 11 + Maven + Spring Boot.

## 2) Baseline de stack inicial (Java 11 lane)

- Java build lane: JDK 11.
- Maven: 3.9.x (Maven 3.9 requiere JDK 8+).
  Fuente: https://maven.apache.org/install
- Spring Boot compatible con Java 11:
  - Spring Boot 2.7.18 es compatible hasta Java 21 y soporta Maven 3.5+.
  Fuente: https://docs.spring.io/spring-boot/docs/2.7.18/reference/html/getting-started.html

Nota de evolución:
- Spring Boot actual (4.x) requiere Java 17+, así que el lane Java 11 debe quedarse en línea de framework compatible.
  Fuente: https://docs.spring.io/spring-boot/system-requirements.html

## 3) Linux portability real (distribuciones comunes)

## Jenkins
- Instaladores oficiales para Debian/Ubuntu, Fedora y RHEL-derivatives.
  Fuente: https://www.jenkins.io/doc/book/installing/linux/

## Docker Engine + Compose
- Soporte de instalación oficial en Ubuntu, Debian, RHEL, CentOS, Fedora.
  Fuentes:
  - https://docs.docker.com/engine/install/ubuntu/
  - https://docs.docker.com/installation/rhel/
  - https://docs.docker.com/engine/installation/
  - https://docs.docker.com/compose/install/linux/

## Kubernetes toolchain
- `kubectl` oficial para Linux (binario o paquetes).
  Fuente: https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/
- `kubeadm` requisitos mínimos y prerequisitos de host.
  Fuente: https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/

## Helm
- Instalación oficial por script/repositorio.
  Fuente: https://helm.sh/docs/intro/install/

## Terraform
- Instalación oficial para Linux vía repositorio/binario.
  Fuentes:
  - https://developer.hashicorp.com/terraform/install
  - https://developer.hashicorp.com/terraform/intro/getting-started/install.html

## 4) AWS EC2 medium como ambiente inicial

Referencia oficial de tipos generales:
- `t3.medium` = 2 vCPU, 4 GiB RAM.
  Fuente: https://aws.amazon.com/ec2/instance-types/general-purpose/

Implicación técnica (inferencia operativa):
- Un `t3.medium` es suficiente para piloto con Jenkins + Docker + build Java.
- Si además ejecutas cluster Kubernetes completo en la misma máquina, será limitado.
- Recomendación: en `t3.medium`, usar inicialmente:
  - Jenkins + Docker + registry push
  - kubectl/helm client
  - cluster remoto o fase posterior para kubeadm completo

## 5) Ruta para “free tier” o bajo costo

Hecho oficial AWS:
- Elegibilidad de free tier depende de fecha de creación de cuenta.
  Fuente: https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/LaunchingAndUsingInstances.html

Hecho oficial adicional:
- Se puede listar programáticamente qué tipos son free-tier-eligible.
  Fuente: https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-free-tier-usage.html

Decisión de producto:
- Baseline recomendado de prueba: `t3.medium` (estabilidad operativa inicial).
- Variante económica/free: `t3.micro`/`t3.small`/`t4g.micro` según elegibilidad de cuenta y carga.

## 6) Estrategia multi-stack rápida (sin romper el baseline Java)

Arquitectura de toolkit:
- Core común (siempre): Jenkins + Docker + credenciales + pipeline gates.
- Capa stack:
  - Java: Maven + JDK lane.
  - Node: npm + versión LTS.
  - Python: venv + pip.
  - .NET: SDK/runtime Linux.

Fuentes oficiales de expansión:
- npm/node instalación recomendada: https://docs.npmjs.com/cli/v11/configuring-npm/install/
- Node LTS y descargas: https://nodejs.org/en/download/package-manager
- Python venv: https://docs.python.org/3/library/venv.html
- pip install: https://pip.pypa.io/en/stable/cli/pip_install/
- .NET Linux install: https://learn.microsoft.com/en-us/dotnet/core/install/linux-ubuntu

## 7) Niveles de instalación recomendados (producto)

Nivel A - Core Linux (MVP)
- Java 17 runtime (Jenkins)
- Java 11 build lane
- Maven
- Docker + compose plugin
- Jenkins pipeline modular

Nivel B - Core + Delivery moderno
- Todo Nivel A
- Registry (build/tag/push)
- Helm + kubectl client

Nivel C - Core + Platform
- Todo Nivel B
- Terraform
- kubeadm/kubelet (si aplica en ese servidor)

## 8) Riesgos y mitigaciones

Riesgo: intentar correr todo en un único host pequeño.
Mitigación: activar módulos por fase (core -> registry -> k8s -> terraform).

Riesgo: mezclar runtime Java de Jenkins con Java del build.
Mitigación: separar explícitamente Java 17 (Jenkins) y Java 11 (build lane).

Riesgo: deriva por distro Linux.
Mitigación: script bootstrap por familia (apt/dnf/yum) + verificación post instalación.

## 9) Estado de implementación en este repo

Ya aplicado:
- `toolkit-fastflow/installers/bootstrap-server.sh`
  - perfiles `core`, `k8s`, `full`
  - detección de familia Linux
  - instalación orientada a portabilidad

Siguiente sugerido:
- añadir wrappers por stack (`node`, `python`, `.net`) sobre el mismo core.

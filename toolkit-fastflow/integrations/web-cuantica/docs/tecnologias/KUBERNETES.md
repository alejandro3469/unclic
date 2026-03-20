# Kubernetes — Por qué, open source y cómo replicar

## Qué es Kubernetes (según web oficial)

**Kubernetes (K8s)** es un sistema **open source** para automatizar el despliegue, el escalado y la gestión de aplicaciones en contenedores.

Agrupa los contenedores que forman una aplicación en unidades lógicas para facilitar la gestión y el descubrimiento. Kubernetes se basa en 15 años de experiencia ejecutando cargas de trabajo en producción en Google, combinados con ideas y prácticas de la comunidad.

- **Planet scale:** Diseñado con los mismos principios que permiten a Google ejecutar miles de millones de contenedores a la semana; puede escalar sin aumentar el equipo de operaciones.
- **Never outgrow:** Desde pruebas locales hasta empresas globales, la flexibilidad de Kubernetes crece contigo para entregar aplicaciones de forma consistente y sencilla.
- **Run K8s anywhere:** Al ser open source, puedes usar infraestructura on-premises, híbrida o en la nube pública y mover cargas de trabajo donde te interese.

**Proyecto graduado en CNCF** (Cloud Native Computing Foundation). En la web oficial hay **case studies** (p. ej. Babylon, Booz Allen, Booking.com, AppDirect) y vídeo introductorio.

## Características (resumen oficial)

| Característica | Descripción |
|----------------|-------------|
| Automated rollouts and rollbacks | Despliegues progresivos y monitoreo de salud; rollback automático si algo falla. |
| Service discovery and load balancing | IP por Pod, DNS único para un conjunto de Pods, load balancing sin tocar la app. |
| Storage orchestration | Montaje automático de almacenamiento (local, cloud, NFS, iSCSI, etc.). |
| Secrets and configuration management | Desplegar y actualizar Secrets y config sin reconstruir la imagen ni exponer secretos. |
| Automatic bin packing | Colocación de contenedores según recursos y restricciones; mezcla de cargas críticas y best-effort. |
| Batch execution | Gestión de trabajos batch y CI; reemplazo de contenedores que fallan. |
| Self-healing | Reinicio de contenedores caídos, reemplazo de Pods, reasociación de almacenamiento, integración con autoscalers. |
| Horizontal scaling | Escalar con comando, UI o de forma automática según CPU. |
| Vertical scaling | Ajuste automático de requests/limits según uso real. |
| IPv4/IPv6 dual-stack | Asignación de IPv4 e IPv6 a Pods y Services. |
| Designed for extensibility | Añadir funcionalidad sin modificar el código upstream. |

## Por qué lo usamos (FastFlow / pos-online)

- **Opcional:** Orquestación de contenedores en cluster (EKS u otro). Cuando el despliegue pase de “una EC2 con JAR” a “contenedores en cluster”, usamos manifests K8s (deploy/k8s/).
- **Objetivo de automatización:** Todo open source; pipelines pueden hacer `kubectl apply` o Helm.

## Open source

- Proyecto: [kubernetes.io](https://kubernetes.io), código en [github.com/kubernetes/kubernetes](https://github.com/kubernetes/kubernetes).
- Licencia: Apache 2.0. Documentación bajo CC BY 4.0.

## Enlaces oficiales

- **Sitio principal:** [kubernetes.io](https://kubernetes.io) (descarga en la sección download; vídeo y eventos en portada).
- **Documentación:** [kubernetes.io/docs](https://kubernetes.io/docs).
- **Blog:** [kubernetes.io/blog](https://kubernetes.io/blog).
- **Training:** [kubernetes.io/training](https://kubernetes.io/training).
- **Descarga:** [kubernetes.io/docs/setup](https://kubernetes.io/docs/setup).
- **Eventos:** KubeCon + CloudNativeCon — Europe (Amsterdam, 23-26 Mar 2026), North America (Salt Lake City, 9-12 Nov 2026).
- **Case studies:** en la portada de kubernetes.io (Babylon, Booz Allen, Booking.com, AppDirect, etc.).
- **Licencia:** The Kubernetes Authors; documentación bajo CC BY 4.0; The Linux Foundation®.

## Cómo replicar (FastFlow)

| Consola | Comando |
|---------|---------|
| Terminal (local) | `kubectl apply -f toolkit-fastflow/integrations/web-cuantica/deploy/k8s/` (cuando tengas cluster y contexto configurado) |

Ver [deploy/k8s/README.md](../../deploy/k8s/README.md).

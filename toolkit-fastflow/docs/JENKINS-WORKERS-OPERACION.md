# Jenkins Workers - Operación y Gobernanza

## Objetivo

Definir reglas de operación para agents Jenkins que reduzcan colas, fallos y retrabajo.

## Labels sugeridos

- `java11`
- `docker`
- `k8s`
- `highcpu`
- `windows` / `linux`

## Política de executors

- Evitar subir executors sin medir RAM/CPU real.
- Jobs pesados (build/test integración) deben correr con menos concurrencia por nodo.
- Jobs livianos pueden usar más concurrencia.

## Scheduling

- Jobs críticos deben declarar label explícito.
- Jobs genéricos pueden usar pools compartidos.

## Salud operativa mínima

Monitorear por nodo:
- disco disponible
- uso de memoria
- latencia de red
- clock drift
- cola de jobs

## Umbrales iniciales (ajustables)

- scale-out: CPU promedio workers > 80%
- scale-in: CPU promedio workers < 20%

Estos umbrales son punto de partida; deben calibrarse por tipo de build.

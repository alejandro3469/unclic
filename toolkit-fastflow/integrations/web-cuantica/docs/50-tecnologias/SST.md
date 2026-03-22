# SST (Serverless Stack) — Por qué, open source y cómo replicar

## Qué es SST (según web oficial)

**SST** permite desplegar todo lo que necesita tu aplicación con **una sola configuración**. *"For whatever you build. Deploy everything your app needs with a single config."*

- **Navegación:** Blog | Docs | Examples | 25K | AI | Search (⌘K) | Console.
- **Instalación:** `npm i sst`. **Novedad:** Windows support in beta.
- **Ejemplo en portada (conceptual):** Un solo archivo de config en el que defines base de datos (p. ej. PlanetScale), email (sst.aws.Email), API (sst.aws.Service con imagen, memoria, link a recursos), y frontend (sst.aws.Nextjs con path, domain, DNS p. ej. Cloudflare). Los componentes se enlazan con `link: [db, email]`, etc.
- **Público:** *"LOVED BY THOUSANDS OF TEAMS"*. © 2026 Anomaly Innovations. **Footer:** Guide | About | Contact.

## Por qué lo usamos

- **Opcional:** Para stacks serverless (Lambda, S3, API Gateway) con IaC en TypeScript. Alternativa o complemento a Terraform/Pulumi para servicios gestionados serverless.
- **Objetivo de automatización:** Open source (MIT); mismo enfoque “código que define infra”.

## Open source

- **Proyecto:** [sst.dev](https://sst.dev/). Licencia MIT.

## Enlaces oficiales

- **Web:** [sst.dev](https://sst.dev/) — What is SST, Get Started, npm i sst, Console.
- **Docs:** documentación y ejemplos en la web. Búsqueda ⌘K.

## Cómo replicar (FastFlow)

| Consola | Comando |
|---------|---------|
| Terminal (local) | `cd toolkit-fastflow/manifests/sst/example` (o el proyecto SST que uses) |
| Terminal (local) | `npm install` |
| Terminal (local) | `npx sst dev` (o `sst deploy`) |

Ver [manifests/sst/README.md](../../../../manifests/sst/README.md).

# SST en el toolkit FastFlow

[SST](https://sst.dev/docs) es un framework para definir y desplegar **aplicaciones full-stack en tu propia infraestructura**, con todo en código en un único `sst.config.ts`. Usa **Pulumi** por debajo (y proveedores Terraform vía Pulumi); es **open source** y no requiere cuenta Pulumi.

En este repo SST se usa como **opción para la capa de aplicación**: frontends (Next.js, Remix, Astro, etc.), APIs en contenedores, Lambdas, Buckets, Postgres, cron jobs. La **infra de CI** (Jenkins, Gitea, POS en EC2) la sigue gestionando **Pulumi** o Terraform (ver [manifests/pulumi/jenkins-aws](../pulumi/jenkins-aws/) y [manifests/terraform/jenkins-aws](../terraform/jenkins-aws/)). Para **desarrollo local** sin AWS (guardar estado local): [LocalStack](../localstack/).

---

## Qué es SST (resumen)

- **Componentes de alto nivel**: `sst.aws.Nextjs`, `sst.aws.Function`, `sst.aws.Bucket`, `sst.aws.Cluster` + `Service`, `sst.aws.Postgres`, `sst.aws.Cron`, etc. Defines la app en código; SST crea la infra en AWS (o Cloudflare, Vercel, 150+ proveedores).
- **Linking**: enlazas recursos (p. ej. un Bucket) a tu app y los usas en runtime con el [SDK](https://sst.dev/docs/reference/sdk/) (`Resource.MyBucket.name`) sin hardcodear.
- **Dev unificado**: `sst dev` despliega cambios de infra, ejecuta funciones en Live, abre túnel a la VPC y arranca frontend/backend juntos.
- **Stages**: `sst deploy --stage dev`, `--stage production`, `--stage pr-123` para distintos entornos.

Docs oficiales: [sst.dev/docs](https://sst.dev/docs) · [Components](https://sst.dev/docs/components/) · [Linking](https://sst.dev/docs/linking/) · [CLI](https://sst.dev/docs/reference/cli/).

---

## Instalación

En un proyecto Node:

```bash
npm install sst
```

Sin Node (global):

```bash
curl -fsSL https://sst.dev/install | bash
```

---

## Cómo encaja con Pulumi y Jenkins

| Capa        | Herramienta   | Uso típico |
|------------|---------------|------------|
| **Infra CI** | Pulumi / Terraform | VPC, EC2 Jenkins, Gitea, POS (y opcionalmente workers, registry). |
| **App**      | **SST**       | Next.js (p. ej. UnClic), APIs en contenedor, Lambdas, Buckets, DBs. |

Flujos posibles:

1. **Pulumi** crea Jenkins + Gitea + POS. **SST** se usa en el repo de la app (p. ej. UnClic): añades `sst.config.ts` en la raíz del frontend y despliegas con `sst deploy --stage production`. Jenkins puede ejecutar `sst deploy` en un job (credenciales AWS en Jenkins).
2. **SST** también puede definir contenedores (`sst.aws.Cluster` + `sst.aws.Service`) y Lambdas en la misma cuenta/región que la infra de Pulumi; no hay conflicto.
3. **Drop-in**: en el repo [unclic](../../integrations/web-cuantica/unclic/) (Next.js) puedes añadir un `sst.config.ts` con `new sst.aws.Nextjs("UnClic", { ... })` y usar `sst dev` / `sst deploy` para ese frontend.

---

## Estructura de proyecto SST

**Drop-in (recomendado para un solo frontend):** `sst.config.ts` en la raíz de tu app.

```
unclic/
├─ next.config.js
├─ sst.config.ts
├─ package.json
├─ app/
└─ ...
```

**Monorepo:** `sst.config.ts` en la raíz; puedes partir la config en `infra/`.

```
my-app/
├─ sst.config.ts
├─ package.json
├─ packages/
│  ├─ frontend
│  ├─ backend
│  └─ functions
└─ infra/
```

Ver [Project structure](https://sst.dev/docs/#project-structure) y [Set up a monorepo](https://sst.dev/docs/set-up-a-monorepo/).

---

## Comandos

| Comando | Uso |
|--------|-----|
| `sst dev` | Entorno de desarrollo: watcher de infra, funciones Live, túnel VPC, arranque de frontend/containers. |
| `sst deploy --stage production` | Despliegue a producción. |
| `sst deploy --stage dev` | Despliegue al stage `dev`. |
| `sst deploy --stage pr-123` | Preview por pull request. |

---

## Ejemplo mínimo en este repo

En **[example/](./example/)** hay un proyecto mínimo con **Bucket + Function** y linking. Útil para ver la estructura de `sst.config.ts` (formato `$config` con `app()` y `run()`).

```bash
cd example
npm install
npx sst dev
```

Para **Next.js** (p. ej. UnClic) usa **drop-in** en la raíz del frontend:

1. En la raíz del proyecto Next.js: `npm install sst` y luego `npx sst init` (elige AWS). Se crea `sst.config.ts` con el formato actual.
2. Opcional: añadir un Bucket y enlazarlo al componente Nextjs (ver [Next.js on AWS with SST](https://sst.dev/docs/start/aws/nextjs/)).
3. `npx sst dev` o `npx sst deploy --stage production`.

Guía completa: [Build a Next.js app in AWS](https://sst.dev/docs/start/aws/nextjs/).

---

## Console (opcional)

[SST Console](https://sst.dev/docs/console/) permite auto-deploy, entornos de preview y monitorización. Es un servicio opcional (con free tier); el núcleo de SST es open source y los datos de tu app se quedan en tu lado.

---

## Referencias

- [SST Docs](https://sst.dev/docs) — Overview, components, linking, CLI.
- [SST Components](https://sst.dev/docs/components/) — AWS, Cloudflare, etc.
- [SST CLI](https://sst.dev/docs/reference/cli/) — dev, deploy, stage.
- [Next.js on AWS with SST](https://sst.dev/docs/start/aws/nextjs/)
- [FAQ: Is SST open-source?](https://sst.dev/docs/#is-sst-open-source-if-its-based-on-pulumi-and-terraform) — Usa solo las partes open source de Pulumi/Terraform; no requiere cuenta Pulumi.
- [FAQ: How does SST compare to Pulumi?](https://sst.dev/docs/#how-does-sst-compare-to-cdk-for-terraform-or-pulumi) — SST para desarrolladores (componentes, linking, dev mode); Pulumi para ingeniería de plataforma.

# Ejemplo mínimo SST (FastFlow)

Ejemplo mínimo con un **Bucket** y una **Function** enlazados. Sirve como referencia para añadir SST a tu app (p. ej. Next.js en UnClic).

## Requisitos

- Node.js 18+
- [AWS credentials configuradas](https://sst.dev/docs/iam-credentials#credentials) (CLI o env vars).

## Uso

```bash
npm install
npx sst dev
```

En otro terminal puedes invocar la función (URL en la salida de `sst dev`). Para desplegar a un stage:

```bash
npx sst deploy --stage dev
# o
npx sst deploy --stage production
```

## Next.js (UnClic u otra app)

Para desplegar un frontend Next.js con SST, usa **drop-in** en la raíz del proyecto Next.js:

1. En la raíz del repo Next.js: `npm install sst` y `npx sst init` (elige AWS).
2. O crea a mano un `sst.config.ts` como en [SST — Next.js on AWS](https://sst.dev/docs/start/aws/nextjs) (Bucket + `sst.aws.Nextjs` con `link: [bucket]`).
3. `npx sst dev` / `npx sst deploy --stage production`.

La documentación completa está en [manifests/sst/README.md](../README.md).

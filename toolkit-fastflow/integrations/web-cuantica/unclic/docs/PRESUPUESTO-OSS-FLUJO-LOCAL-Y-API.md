# Presupuesto OSS — flujo local completo y API

## Build (verificado)

```bash
npm run typecheck
npm run test:api
npm run build   # genera out/
```

Si el disco está casi lleno y falla el build con `ENOSPC`, libera espacio o deja activo `webpack.cache = false` en `next.config.js` (ya configurado para producción).

## Flujo local: web + API

1. **Terminal A — API** (puerto 3001):

   ```bash
   cd services/api && npm run dev
   ```

   Con CORS:

   ```bash
   CORS_ORIGINS=http://localhost:3002,http://127.0.0.1:3002 npm run dev
   ```

2. **`.env.local`** en la raíz de `unclic`:

   ```env
   NEXT_PUBLIC_UNCLIC_API_URL=http://127.0.0.1:3001
   ```

3. **Terminal B — Next**:

   ```bash
   npm run dev
   ```

4. Abre **http://localhost:3002/presupuesto-oss** y completa el cuestionario hasta **Calcular estimación**.

## API para otra app self-hosted

- `GET http://<tu-api>:3001/v1/oss-assessment/schema`
- `POST http://<tu-api>:3001/v1/oss-assessment/estimate` con JSON de respuestas (`phase`, `domain`, `team`, `needs`, `cloud`, `sensitivity`, `contextNotes?`).

Añade el **origen** de la otra app a `CORS_ORIGINS` en el API.

## AWS

Misma imagen/contenedor del API detrás de ALB o IP elástica; HTTPS obligatorio en prod; el front estático con `NEXT_PUBLIC_UNCLIC_API_URL=https://api.tu-dominio.com` y **rebuild** de `out/`.

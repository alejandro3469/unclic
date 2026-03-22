# @unclic/ping

Microservicio **mínimo** para demostrar:

- Segundo proceso HTTP en el mismo repositorio.
- Llamada **API → ping** vía adaptador (`services/api/src/adapters/ping-adapter.ts`).
- `GET /v1/orchestration/health` en la API cuando `PING_SERVICE_URL` apunta aquí.

## Desarrollo

```bash
npm ci
npm run dev
# http://localhost:3010/health
```

## Pruebas

```bash
npm test
```

Ver [docs/ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md](../../docs/ARQUITECTURA-MICROSERVICIOS-MODULOS-Y-ADAPTADORES.md).

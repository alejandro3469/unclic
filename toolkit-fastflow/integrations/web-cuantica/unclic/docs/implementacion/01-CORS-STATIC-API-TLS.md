# CORS, sitio estático (`out/`) y API con TLS

## Contexto real UnClic

- Next con `output: 'export'` → **no** hay Route Handlers en el artefacto servido al público.
- El navegador llama a `NEXT_PUBLIC_UNCLIC_API_URL` → debe ser **HTTPS** en prod y estar en **CORS_ORIGINS** del API.

Referencias código: `services/api/src/app.ts` (`parseCorsOrigins`), `next.config.js`.

## 1. Variables (valores literales, sin adivinar nombres)

**Build del front** (CI o máquina de build):

```bash
export NEXT_PUBLIC_UNCLIC_API_URL=https://api.tu-dominio.com
npm run build
# Desplegar carpeta out/ al servidor estático o CDN
```

**Runtime del API** (mismo servidor o otro):

```bash
CORS_ORIGINS=https://tu-dominio.com,https://www.tu-dominio.com
```

- Sin barra final.
- Si usas varios orígenes, separados por coma (el código hace `split(',')`).
- `CORS_ORIGINS=*` es válido en código pero **no recomendado** si más adelante usas credenciales.

## 2. Caddy (ejemplo mínimo)

Dos sitios: `tu-dominio.com` → archivos `out/`, `api.tu-dominio.com` → reverse proxy a `127.0.0.1:3001`.

```caddyfile
tu-dominio.com {
    root * /var/www/unclic/out
    file_server
    try_files {path} /index.html
}

api.tu-dominio.com {
    reverse_proxy 127.0.0.1:3001
}
```

Documentación: [caddyserver.com/docs](https://caddyserver.com/docs/).

## 3. Nginx (equivalente)

- `root` apuntando a `out/`.
- `location /` con `try_files $uri $uri/ /index.html` para SPA/rutas Next export.
- `location` o `server` aparte para `api.` con `proxy_pass http://127.0.0.1:3001`.

Documentación: [nginx.org/en/docs](https://nginx.org/en/docs/).

## 4. Verificación (comandos)

```bash
curl -sI https://api.tu-dominio.com/health
curl -s -X OPTIONS https://api.tu-dominio.com/v1/leads/email \
  -H "Origin: https://tu-dominio.com" \
  -H "Access-Control-Request-Method: POST" -I
```

Debes ver cabeceras `Access-Control-Allow-Origin` acordes a tu lista.

## 5. Errores frecuentes

| Síntoma | Causa típica |
|---------|----------------|
| CORS error en browser | Origen del sitio no está en `CORS_ORIGINS` o API detrás de otro host sin el header. |
| Lead 404 | `NEXT_PUBLIC_UNCLIC_API_URL` vacío en build → el front llama ruta inexistente en estático. |
| Mixed content | Front HTTPS llamando API HTTP → bloqueado por el navegador. |

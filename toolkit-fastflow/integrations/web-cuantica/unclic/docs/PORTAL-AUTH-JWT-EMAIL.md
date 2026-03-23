# Portal UnClic — autenticación tipo enterprise (email + JWT)

## Qué es

- **Sitio público:** home, presupuesto OSS, integraciones, contacto, empresa, etc. siguen accesibles sin cuenta.
- **Portal `/portal`:** hub con enlaces a demos **protegidas** (`/demo`, `/flow-demo`) cuando hay sesión JWT válida.
- **Flujo:** registro → correo con enlace de verificación (48 h) → login con contraseña → JWT en `sessionStorage` → el gate de demos valida el token contra `GET /v1/auth/me`.

No es Auth0/Okta: es **auth propia** en el microservicio Hono (`services/api`), adecuada para demos y pruebas. En producción dura puedes sustituir por un IdP (OIDC) reutilizando las mismas rutas protegidas en el front.

## Variables (front — `.env.local`)

| Variable | Uso |
|----------|-----|
| `NEXT_PUBLIC_UNCLIC_API_URL` | Base de la API (ej. `http://localhost:3001`). Si está definida, `/login` pide **correo + contraseña**. |
| `NEXT_PUBLIC_PORTAL_AUTH_REQUIRED` | `true` / `1`: `/demo` y `/flow-demo` **solo** JWT (más `?unlock=` interno). Si es `false` o no existe, se mantiene el modo legacy “solo correo” + allowlist. |

## Variables (API — entorno del proceso `services/api`)

| Variable | Uso |
|----------|-----|
| `AUTH_JWT_SECRET` | Secreto HS256 (recomendado ≥ 32 caracteres). **Obligatorio** para login. |
| `AUTH_STORE_PATH` | JSON de usuarios (por defecto `./data/auth-store.json`). No commitear. |
| `PORTAL_PUBLIC_URL` | Origen del sitio estático para enlaces del email (`https://unclic.consulting`). |
| `GMAIL_SMTP_*` | Mismo SMTP que leads; necesario para **enviar** el correo de verificación. |
| `UNCLIC_ADMIN_EMAILS` | Lista separada por comas; bootstrap crea admin verificado si no existe. |
| `UNCLIC_ADMIN_PASSWORD` | Contraseña inicial del/los admin del bootstrap. |
| `UNCLIC_SEED_USER_EMAIL` / `UNCLIC_SEED_USER_PASSWORD` | Usuario demo adicional verificado al arrancar. |
| `AUTH_LOG_VERIFICATION_LINK` | `1`: si no hay SMTP, loguea el enlace de verificación en consola (solo dev). |

## Rutas API

- `POST /v1/auth/register` — body `{ email, password }` (mín. 8 caracteres).
- `GET /v1/auth/verify-email?token=…`
- `POST /v1/auth/login` — body `{ email, password }` → `{ token, user }`.
- `GET /v1/auth/me` — header `Authorization: Bearer <jwt>`.

## Arranque local rápido

1. `.env.local` en la raíz de `unclic`: `NEXT_PUBLIC_UNCLIC_API_URL=http://localhost:3001`, `NEXT_PUBLIC_PORTAL_AUTH_REQUIRED=true`.
2. En la shell de la API: `AUTH_JWT_SECRET=...`, `CORS_ORIGINS=http://localhost:3002`, `PORTAL_PUBLIC_URL=http://localhost:3002`, SMTP si quieres correo real; opcional `UNCLIC_ADMIN_EMAILS` + `UNCLIC_ADMIN_PASSWORD` + usuario seed.
3. `npm run dev:api` y `npm run dev`.
4. Abre `/portal` → crear cuenta o iniciar sesión → entra a `/demo`.

## Seguridad

- Con `output: 'export'`, el gate es **en el cliente**; la protección fuerte de Jenkins/Gitea/POS sigue siendo **Basic Auth / VPN / IAM** en servidor, como ya documentaste para demos.
- Los JWT expiran a los 7 días (configurable en código en `auth/jwt.ts`).

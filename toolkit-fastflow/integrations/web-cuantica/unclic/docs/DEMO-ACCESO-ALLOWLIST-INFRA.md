# Acceso a demos solo para correos autorizados (tú + otro usuario)

El sitio puede restringir **enlaces en la web** a Jenkins, Gitea, POS, registry, Cloudcraft y tarjetas del hub a quien haya iniciado sesión con un **correo permitido**.

## Cómo activarlo

En **`.env.local`** (y en el build de producción, mismas variables):

```bash
NEXT_PUBLIC_DEMO_ACCESS_MODE=allowlist
NEXT_PUBLIC_DEMO_ALLOWED_EMAILS=correo1@dominio.com,correo2@dominio.com
```

- Los correos se comparan en minúsculas (sin espacios extra).
- Si activas `allowlist` y **dejas la lista vacía**, **nadie** podrá pasar el gate (evita despliegues accidentales abiertos).

Vuelve a hacer **`npm run build`** y despliega.

## Comportamiento

| Ruta / zona | Efecto |
|-------------|--------|
| `/login` | Solo correos en la lista continúan a `/demo`. |
| `/demo/access` | Igual: si el correo no está en la lista, se muestra error y no se guarda sesión. |
| `/demo` | `DemoGate` exige sesión válida (correo permitido o bypass con token, ver abajo). |
| Hub en `/soluciones`, bloque Demos, arquitectura Cloudcraft | Enlaces externos quedan bloqueados hasta login autorizado. |

## Preview interno sin correo (opcional)

Con allowlist activo, **`?unlock=1` ya no abre** la demo (evita que cualquiera con el truco entre).

Si necesitas un enlace de prueba sin correo:

```bash
NEXT_PUBLIC_DEMO_UNLOCK_TOKEN=un-secreto-largo-aleatorio
```

Entonces abre: `https://tu-dominio/demo?unlock=un-secreto-largo-aleatorio`

Eso marca sesión “bypass” en el navegador y verás enlaces como con acceso completo. **No compartas** ese token.

## Limitaciones importantes (léelo)

1. **El proyecto usa `output: 'export'`** (sitio estático). La lista de correos va en variables **`NEXT_PUBLIC_*`**: quien sepa inspeccionar el JavaScript del sitio **puede ver los correos** y **puede intentar falsificar `sessionStorage`** (usuarios muy técnicos). Esto es una **barrera UX / confidencialidad ligera**, no autenticación fuerte.

2. **Jenkins, Gitea, POS, registry y AWS** siguen siendo URLs públicas en Internet mientras tus servidores los expongan así. Para que **solo tú y otra persona** puedan usarlos de verdad:
   - **Nginx** delante con **Basic Auth**, o
   - **VPN** / **IP allowlist** en security group, o
   - **Autenticación propia** de cada producto + cuentas solo para esas dos personas.

3. Combina **allowlist en la web** + **protección en infra** para un modelo coherente.

## Modo abierto (por defecto)

Sin `NEXT_PUBLIC_DEMO_ACCESS_MODE=allowlist`, el comportamiento anterior se mantiene: cualquiera puede usar `/login` y `/demo/access` como antes, y `?unlock=1` sigue abriendo `/demo`.

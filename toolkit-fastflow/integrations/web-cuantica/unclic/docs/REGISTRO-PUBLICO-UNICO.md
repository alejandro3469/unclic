# Registro público único

## Comportamiento

- **Un solo formulario de alta:** `/portal/registro` (alias en código: `routes.publicSignup` / `routes.portalRegistro`).
- **Flujo:** correo + contraseña → verificación por enlace → login → portal (demos, módulos, JWT).
- **Rutas de compatibilidad:** `/demo/access` y `/signup` redirigen al mismo registro. Opcional: `?next=/demo` (u otra ruta interna) para conservar destino tras iniciar sesión.

## UI pública

- **Un botón de acción principal (relleno)** en cabecera y hero: “Crear cuenta” → `/portal/registro`.
- **Sin formularios duplicados** en home/CTA: se eliminaron `LeadEmailForm`, tarjeta login demo y el formulario embebido del Hero154.
- **Enlaces de texto** (no segundo botón principal): “Iniciar sesión”, “Portal”, “Ver servicios”, etc.
- **Precios / demos:** CTAs de tarjeta como `Button variant="outline"` hacia el mismo registro para no competir con el CTA principal de la cabecera.

## Lead email (API)

El componente `components/lead-email-form.tsx` sigue en el repo por si se reutiliza en el portal o intranet; **no** está montado en la landing por defecto.

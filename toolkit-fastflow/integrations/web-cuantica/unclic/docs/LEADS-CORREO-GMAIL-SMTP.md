# Leads: solo correo + Gmail (SMTP)

## Qué hace

- Formulario en la sección CTA (home y páginas que usen `CtaSection`): el visitante deja **solo su correo**.
- Con **`NEXT_PUBLIC_UNCLIC_API_URL`**: el navegador llama a **`POST …/v1/leads/email`** del microservicio (`services/api`). Imprescindible si despliegas el sitio como **estático** (`output: 'export'`), porque no existen Route Handlers en prod.
- Sin esa variable (típico `next dev`): `POST /api/lead-email` en Next valida el correo y envía **dos correos** vía Gmail SMTP:
  1. **A ti** (`LEAD_NOTIFY_TO` o la misma cuenta Gmail): asunto `[UnClic] Nuevo contacto: …`, `Reply-To` = correo del lead.
  2. **Al visitante**: confirmación automática (“Gracias por tu interés…”).

## Configuración (`.env.local`)

Copia de `.env.example` las variables:

- `GMAIL_SMTP_USER` — cuenta Gmail que envía (p. ej. `tu@gmail.com`).
- `GMAIL_SMTP_APP_PASSWORD` — [contraseña de aplicación](https://myaccount.google.com/apppasswords) (requiere 2FA en Google).
- Opcional: `LEAD_NOTIFY_TO` — bandeja donde quieres recibir los avisos (si no, usa `GMAIL_SMTP_USER`).
- Opcional: `LEAD_FROM_NAME` — nombre del remitente (por defecto `UnClic`).

En **Vercel / servidor**, define las mismas variables en el panel del hosting. Sin ellas, el formulario responde 503 y muestra mensaje de configuración.

## Seguridad

- Las llaves **nunca** van al cliente; solo en el servidor.
- Campo honeypot `website` (oculto) para reducir spam básico.
- Para volumen alto, valorar **Turnstile/reCAPTCHA** y límites de tasa.

## Archivos

- `services/api/` — mismo contrato JSON + Gmail (ver [MICROSERVICIOS-Y-DOCKER.md](MICROSERVICIOS-Y-DOCKER.md))
- `app/api/lead-email/route.ts` — solo desarrollo / despliegues con servidor Next
- `components/lead-email-form.tsx`
- `lib/copy-lead-email.ts` — textos
- `lib/email/smtp.ts`, `lib/email/send-lead-notifications.ts`

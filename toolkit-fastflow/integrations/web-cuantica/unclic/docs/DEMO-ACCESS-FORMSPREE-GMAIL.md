# Email gate demo: Formspree y correo de bienvenida con Gmail

La ruta **`/demo/access`** pide un solo campo (correo). Al enviar, el correo se envía a **Formspree** y el usuario obtiene acceso a **`/demo`** (Jenkins, Gitea, POS, credenciales invitado). Para que reciba un **correo de bienvenida desde Gmail**, sigue una de las opciones siguientes.

---

## 1. Formspree (rápido)

1. Entra en [formspree.io](https://formspree.io) y crea un formulario (ej. "UnClic Demo Access").
2. Añade el campo que necesites (el código envía `email` y `_subject`). Formspree ya captura el email por defecto.
3. Copia el **Form ID** (la parte final de la URL, ej. `xyzwabcd`).
4. En tu build (o en Vercel/EC2), define:
   ```bash
   NEXT_PUBLIC_DEMO_ACCESS_FORM_ID=xyzwabcd
   ```
5. En Formspree → **Settings** → **Autoresponder**: actívalo y escribe el asunto y cuerpo del correo de bienvenida (con el enlace a `https://unclic.consulting/demo`). Ese correo lo envía Formspree (no sale “desde” tu Gmail, pero llega al usuario).

---

## 2. Gmail vía Zapier o Make.com

Para que el correo de bienvenida **salga desde tu Gmail**:

1. Formspree: crea el formulario y el Form ID como arriba; en **Integrations** o **Webhooks** copia la URL del webhook.
2. **Zapier:**  
   - Trigger: **Webhooks by Zapier** → “Catch Hook” (pega la URL de Formspree).  
   - Action: **Gmail** → “Send Email”. Redacta asunto y cuerpo (plantilla de bienvenida con enlace a `/demo`). El envío sale de tu Gmail.
3. **Make.com (Integromat):**  
   - Módulo **Webhooks** → “Custom webhook” (misma URL de Formspree).  
   - Módulo **Gmail** → “Send an email” con la plantilla de bienvenida.

Así el usuario recibe un correo de bienvenida **desde tu Gmail** con el enlace a la demo.

---

## 3. Variables de entorno

| Variable | Uso |
|---------|-----|
| `NEXT_PUBLIC_DEMO_ACCESS_FORM_ID` | Form ID de Formspree (solo el ID). Si no se define, el formulario sigue funcionando pero no se envía a Formspree; aun así se concede acceso a `/demo` al enviar (sessionStorage). |

---

## 4. Rutas

| Ruta | Comportamiento |
|------|----------------|
| `/demo/access` | Formulario “Deja tu correo” → envía a Formspree (si hay Form ID) → guarda acceso → redirige a `/demo`. |
| `/demo` | Si no hay acceso (sessionStorage o `?unlock=1`), redirige a `/demo/access`. Si hay acceso, muestra enlaces a Jenkins, Gitea, POS y credenciales invitado. |

---

## 5. Plan completo

Ver **[PLAN-PRODUCCION-UNClic-DEMO-EMAIL-LINKEDIN.md](../../docs/PLAN-PRODUCCION-UNClic-DEMO-EMAIL-LINKEDIN.md)** (fases sitio en producción, email gate, Gmail, experiencia demo, LinkedIn).

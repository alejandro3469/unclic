# Plan Cloudcraft → UnClic: tareas granulares con fuentes oficiales

Plan **rehecho** con tareas **más granulares** y **sostenido en documentación oficial**, ejemplos y fuentes fiables para que el flujo **funcione a la primera**. Cada sub-tarea tiene criterio de éxito verificable y, cuando aplica, qué hacer si falla.

---

## Fuentes oficiales usadas en este plan

| # | Tema | URL | Uso en el plan |
|---|------|-----|-----------------|
| 1 | Conectar AWS a Cloudcraft | [Connect your AWS Account to Cloudcraft](https://docs.datadoghq.com/cloudcraft/getting-started/connect-aws-account-with-cloudcraft/) | Fase 1: pasos IAM, ARN, Save Account. |
| 2 | Primer diagrama live | [Create your first live cloud diagram](https://docs.datadoghq.com/cloudcraft/getting-started/create-your-first-cloudcraft-diagram/) | Fase 2: Live tab, Scan now, Auto layout, Live vs Snapshot. |
| 3 | Filtros y nueva experiencia Live | [Crafting Better Diagrams: Live Diagramming and Filtering](https://docs.datadoghq.com/cloudcraft/getting-started/crafting-better-diagrams/) | Fase 2: filtros por tipo y tags (Terraform, Custom). |
| 4 | Enlace compartible (Share) | [How are shared blueprint links secured?](https://docs.datadoghq.com/cloudcraft/faq/shareable-link-security/) | Fase 3: **Share & Export > Get shareable link** (por defecto deshabilitado). |
| 5 | Roles Cloudcraft | [Roles and Permissions](https://docs.datadoghq.com/cloudcraft/account-management/roles-and-permissions/) | Fase 0: solo **Owner** o **Administrator** pueden conectar cuentas AWS. |
| 6 | Componentes AWS soportados | [What AWS components are supported?](https://docs.datadoghq.com/cloudcraft/faq/supported-aws-components/) | Fase 2: VPC, Subnets, EC2, Security Groups, etc. están soportados. |
| 7 | API Blueprints (list, export) | [Cloudcraft API - Blueprints](https://docs.datadoghq.com/cloudcraft/api/blueprints/) | Fase 3 (alternativa) y Fase 5: GET /blueprint, GET /blueprint/{id}/png. |
| 8 | Cómo Cloudcraft se conecta a AWS | [How does Cloudcraft connect to my AWS account?](https://docs.datadoghq.com/cloudcraft/faq/how-cloudcraft-connects-to-aws/) | Fase 1: cross-account role, solo lectura, datos no almacenados en Cloudcraft. |
| 9 | Política IAM mínima (opcional) | [Create a custom IAM policy](https://docs.datadoghq.com/cloudcraft/advanced/minimal-iam-policy/) | Fase 1: alternativa a ReadOnlyAccess si se requiere política más restrictiva. |
| 10 | Variables de entorno Next.js | [Environment Variables \| Next.js](https://nextjs.org/docs/app/building-your-application/configuring/environment-variables) | Fase 4: NEXT_PUBLIC_ se inyecta en **build time**; en producción configurar en el build. |

---

## Fase 0: Requisitos previos (comprobar antes de empezar)

**Objetivo:** Tener todo lo necesario para que ningún paso falle por falta de permiso o acceso.

### 0.1 Cuenta AWS con infra FastFlow

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 0.1.1 | Abrir la [consola AWS](https://console.aws.amazon.com/) e iniciar sesión en la cuenta donde está (o estará) la infra FastFlow. | Inicias sesión sin error. | — |
| 0.1.2 | Ir a **EC2** → **Instances** y comprobar que existen (o podrás crear) instancias en la región que usarás (p. ej. **us-east-2**). Para el plan se asume que la infra FastFlow (Jenkins, Gitea, POS) está o se desplegará en esa cuenta/región. | Ves la lista de instancias (vacía o con instancias). | — |

**Si falla:** Sin cuenta AWS o sin acceso, no se puede conectar Cloudcraft. Crea una cuenta o pide acceso a quien administre la cuenta.

---

### 0.2 Permisos IAM para crear un rol

La documentación oficial exige: *"An AWS account with permission to create IAM roles"* y que si no tienes acceso a la página Create Role, *"contact your AWS account's administrator"*.

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 0.2.1 | En la consola AWS, ir a **IAM** → **Roles**. | Ves la lista de roles. | [1] Requirements |
| 0.2.2 | Clic en **Create role**. Si no ves el botón o obtienes "Access Denied", no tienes permisos suficientes. | La página "Create role" se abre (paso "Select trusted entity"). | [1] Add account – alert |

**Si falla:** Necesitas permisos que incluyan crear roles IAM (p. ej. `iam:CreateRole`, `iam:AttachRolePolicy`). Política típica: **AdministratorAccess** o una política personalizada que permita crear roles y adjuntar **ReadOnlyAccess**. Contacta al administrador de la cuenta.

---

### 0.3 Suscripción Cloudcraft Pro y rol Owner o Administrator

*"An active Cloudcraft Pro subscription"* y *"A Cloudcraft user with the Owner or Administrator role"*. Solo Owner y Administrator pueden **Connect new AWS accounts**; los Users no.

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 0.3.1 | Abrir [https://app.cloudcraft.co](https://app.cloudcraft.co) e iniciar sesión. | Entras al dashboard de Cloudcraft. | [1] Requirements |
| 0.3.2 | Comprobar que la suscripción es **Pro** (o superior). En el menú de usuario o **Settings** / **Billing** debe indicar plan Pro. | El plan mostrado es Pro (o Enterprise). | [1] – [Cloudcraft Pricing](https://www.cloudcraft.co/pricing) |
| 0.3.3 | Comprobar tu rol: **User** → **Account** (o **Team settings** / **Members**). Tu usuario debe aparecer como **Account Owner** o **Administrator**. | Rol = Owner o Administrator. | [5] Account Owner / Administrator |

**Si falla:** Si eres **User**, no podrás añadir cuentas AWS. Un Owner o Administrator debe darte rol Administrator o conectar la cuenta AWS y compartirla con tu equipo.

---

## Fase 1: Conectar la cuenta AWS a Cloudcraft

**Objetivo:** Cloudcraft puede leer (solo lectura) los recursos de tu cuenta AWS. *"Cloudcraft uses a cross-account role to securely access your AWS environment"* ([8]). La UI actual de Cloudcraft muestra el flujo **"Install your first Cloudcraft Integration"** con **6 pasos**; sigue exactamente lo que aparece en pantalla.

### 1.1 Abrir el flujo "Add AWS Account" en Cloudcraft

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 1.1.1 | En Cloudcraft, si ves la pantalla **"Install your first Cloudcraft Integration"** ("You haven't installed any Integrations yet"), clic en **Add AWS Account**. Si no, ir a **User** (o menú de usuario) → **AWS accounts** y clic en **Add AWS Account**. | Se muestra el asistente de 6 pasos con el paso 1 destacado. | UI Cloudcraft – Add AWS Account |
| 1.1.2 | Dejar abierta la pestaña o ventana de Cloudcraft; en el **paso 1** verás el enlace para abrir la consola AWS. | Ves el texto: "Open the AWS IAM Console to the Create Role page" y un botón/enlace. | UI – paso 1 |

**Si falla:** Si no ves "Add AWS Account", tu rol puede ser User; ver Fase 0.3.

---

### 1.2 Paso 1 — Abrir la consola IAM en "Create Role"

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 1.2.1 | En Cloudcraft, en el **paso 1**, clic en **"Open the AWS IAM Console to the Create Role page"** (o el enlace equivalente). | Se abre una nueva pestaña con la consola AWS IAM en la página **Create role** (paso "Select trusted entity"). | UI – paso 1 / [1] |

---

### 1.3 Paso 2 — Verificar Trust policy (External ID y MFA)

En la consola AWS verás **Step 1: Select trusted entity** (luego Step 2: Add permissions, Step 3: Name, review, and create). Cloudcraft, si abriste desde su enlace, suele pre-rellenar los campos. **Verifica** que coincidan con lo que Cloudcraft muestra en su paso 2.

**Qué verás en IAM (texto literal de la consola):**

- **Trusted entity type** — Varias opciones: *AWS service*, *AWS account*, *Web identity*, *SAML 2.0 federation*, *Custom trust policy*. Debe estar elegida **AWS account** (*"Allow entities in other AWS accounts belonging to you or a 3rd party to perform actions in this account"*).
- Bajo **An AWS account**: aparecen *This account (tu-ID)* — p. ej. *This account (477010601377)* — y **Another AWS account**. Debes tener seleccionado **Another AWS account** (no "This account").
- **Account ID** — Debe ser el **ID de 12 dígitos que Cloudcraft indica** (cuenta de Cloudcraft, no el tuyo). Ejemplo que Cloudcraft puede mostrar: *968898580625*.
- **Options** — **Require external ID** debe estar **marcado** (*"Best practice when a third party will assume this role"*). El campo **External ID** debe contener el valor que Cloudcraft muestra (ej. *ca8d8176-f10d-45be-b69c-5b6c15165a73*). No lo cambies.
- **Require MFA** — Debe estar **desmarcado**.

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 1.3.1 | En **Select trusted entity**, comprobar **Trusted entity type** = **AWS account** (no AWS service, Web identity, etc.). | La opción "AWS account" está seleccionada. | Consola IAM |
| 1.3.2 | Seleccionar **Another AWS account** (no "This account (tu-ID)"). En **Account ID** debe estar el ID de 12 dígitos que Cloudcraft indica en su paso 2 (cuenta de Cloudcraft). | Account ID = el de Cloudcraft (12 dígitos). | Consola IAM / UI Cloudcraft |
| 1.3.3 | Comprobar **Require external ID** marcado y **External ID** con el valor que Cloudcraft muestra. No modificar. | Require external ID = checked; External ID = valor de Cloudcraft. | Consola IAM |
| 1.3.4 | Comprobar **Require MFA** desmarcado. | Require MFA = unchecked. | Consola IAM / [1] |
| 1.3.5 | Clic en **Next**. | Avanzas a **Step 2: Add permissions**. | Consola IAM |

**Si falla:** Si abriste Create role a mano (sin el enlace de Cloudcraft), rellena tú: Trusted entity type = AWS account, Another AWS account, Account ID y External ID **exactamente** como en el paso 2 de Cloudcraft. Si usas "This account" o tu propio Account ID, Cloudcraft no podrá asumir el rol.

---

### 1.4 Paso 3 — Adjuntar política ReadOnlyAccess

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 1.4.1 | En la pantalla **Add permissions**, en el cuadro de búsqueda escribir **ReadOnlyAccess** y pulsar Enter (o buscar). | Se filtra la lista de políticas. | UI – paso 3 / [1] |
| 1.4.2 | Seleccionar la política **ReadOnlyAccess** con tipo **AWS managed - job function** (o "AWS managed"). Descripción: read-only access to AWS services and resources. | La política ReadOnlyAccess está marcada. | UI – paso 3 |
| 1.4.3 | Clic en **Next** para ir a la pantalla de revisión. | Avanzas a "Review and create" (o "Name, review, and create"). | UI – paso 3 |

**Si falla:** Si necesitas una política más restrictiva, Cloudcraft indica "If you have advanced security needs, follow these instructions to create a more restrictive policy" → ver [9] Minimal IAM policy.

---

### 1.5 Paso 4 — Nombre del rol y Create Role

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 1.5.1 | En la pantalla **Review**, en **Role name** dar un nombre al rol (Cloudcraft sugiere **cloudcraft**; puedes usar ese u otro, p. ej. **cloudcraft-readonly**). Opcional: Description, Tags. | El nombre está rellenado. | UI – paso 4 / [1] |
| 1.5.2 | Clic en **Create Role**. | Mensaje de éxito; vuelves a la lista de roles. | UI – paso 4 |

---

### 1.6 Paso 5 — Copiar Role ARN y pegarlo en Cloudcraft

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 1.6.1 | En la lista de roles de AWS, localizar el rol que acabas de crear (p. ej. **cloudcraft**) y clic en su nombre. | Se abre la página del rol (Summary). | UI – paso 5 / [1] |
| 1.6.2 | En la pestaña **Summary**, copiar el **Role ARN** (ej. `arn:aws:iam::123456789012:role/cloudcraft`). | Tienes el ARN en el portapapeles. | UI – paso 5 |
| 1.6.3 | Volver a la pestaña de **Cloudcraft**. En el **paso 5**, en el campo **Role ARN**, pegar el ARN (Ctrl+V / Cmd+V). No añadir espacios ni comillas. | El campo Role ARN muestra el ARN completo. | UI – paso 5 |
| 1.6.4 | En **Give the account a Cloudcraft nickname** (o "Account name"), escribir un nombre identificable, p. ej. **FastFlow-prod** o **MiCuentaAWS**. | El nickname queda rellenado. | UI – paso 5 |

---

### 1.7 Paso 6 — Elegir región y finalizar

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 1.7.1 | En Cloudcraft, en el **paso 6**, elegir **la primera región** en la que quieres dibujar en Cloudcraft (p. ej. **us-east-2** si ahí está tu infra FastFlow). | La región está seleccionada. | UI – paso 6 |
| 1.7.2 | Si hay botón **Save**, **Done** o **Complete** (o el asistente avanza solo), completar el flujo. | La cuenta aparece en Cloudcraft (Integrations o User → AWS accounts) y está lista para usar en la pestaña Live. | UI |

**Si falla:** "Invalid ARN" → comprueba que pegaste el ARN completo sin espacios. "Unable to assume role" → el trust policy debe tener el **External ID** exacto que Cloudcraft mostró en el paso 2; si creaste el rol sin usar el enlace de Cloudcraft, edita el trust policy del rol en IAM y añade la condición `sts:ExternalId` con el valor de Cloudcraft.

---

## Fase 2: Crear el diagrama Live de la arquitectura FastFlow

**Objetivo:** Un blueprint que refleje la infra real y se actualice con Live scanning. Componentes relevantes para FastFlow (VPC, Subnets, EC2, Security Groups, etc.) están [soportados por Cloudcraft](https://docs.datadoghq.com/cloudcraft/faq/supported-aws-components/) ([6]).

### 2.1 Abrir la vista Live y elegir cuenta y región

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 2.1.1 | En Cloudcraft, en la barra o pestañas superiores, seleccionar **AWS** (no Azure). | La interfaz muestra opciones para AWS. | [2] "select the AWS or Azure tab" |
| 2.1.2 | Seleccionar la pestaña **Live** (junto a "Blueprint" o similar). | Ves el panel Live con selectores de **Account** y **Region**. | [2] "then the Live tab" |
| 2.1.3 | En **Account**, elegir la cuenta que añadiste en Fase 1 (p. ej. **FastFlow-prod**). Si solo hay una, suele estar preseleccionada. | La cuenta seleccionada es la correcta. | [2] "If you have only added one AWS account... it is automatically selected" |
| 2.1.4 | En **Region**, elegir la región donde está tu infra (p. ej. **us-east-2**). Para una sola región, elegir solo esa. | La región correcta está seleccionada. | [2] "Select the region you want to perform the scan in" |

---

### 2.2 Tipo Live vs Snapshot y ejecutar escaneo

*"Below the Scan now button is a toggle that says Live or Snapshot"*; *"If you select Live, the diagram continuously updates"*; *"Click Scan now"* → *"A Scan complete message appears"* ([2]).

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 2.2.1 | Localizar el conmutador **Live** / **Snapshot**. Dejarlo en **Live** (no Snapshot) para que el diagrama se actualice con los cambios en AWS. | El toggle está en "Live". | [2] "Enable the toggle for Live" |
| 2.2.2 | Clic en el botón **Scan now** (debajo del selector de región o del toggle). | Comienza el escaneo (puede haber indicador de carga). | [2] "Click Scan now" |
| 2.2.3 | Esperar hasta que aparezca el mensaje **Scan complete** (o equivalente). | El mensaje "Scan complete" es visible; debajo suelen listarse los componentes detectados. | [2] "A Scan complete message appears once the scan is finished" |

**Si falla:** "Limited AWS access" o errores al escanear → el rol IAM puede no tener permisos suficientes; revisa que el rol tenga **ReadOnlyAccess** adjunta ([1]). Si usas política custom, actualiza según [9].

---

### 2.3 (Opcional) Nueva experiencia Live y filtros por tags

Para diagramas más enfocados: *"Enable the New Live Experience switch at the top of the Live tab"*; filtros por **Resource type** y por **Custom tags, AWS tags, Terraform tags** ([3]).

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 2.3.1 | Si en la parte superior del tab Live hay un interruptor **"New Live Experience"** (o "New live experience"), activarlo. En usuarios nuevos puede estar activado por defecto. | El panel muestra opciones de filtro (Resource, Custom tags, etc.). | [3] Enable new live experience |
| 2.3.2 | (Opcional) En **Resource**, deseleccionar tipos que no necesites y dejar solo los que correspondan a FastFlow (p. ej. **EC2**, **VPC**, **Subnets**, **Security Groups**). | Menos ruido en el diagrama. | [3] Filter resources |
| 2.3.3 | (Opcional) Si tu Terraform/Pulumi etiqueta recursos (p. ej. `Author=fastflow`, `Name=fastflow-*`), en **Custom tags** o **Terraform tags** seleccionar solo las etiquetas que identifiquen la pila FastFlow. | El layout posterior incluirá solo recursos que coincidan. | [3] "filter by tags" |

---

### 2.4 Aplicar Auto layout y guardar el blueprint

*"Select Auto layout under the Live/Snapshot toggle"* → en el diálogo *"choose Replace existing components"* para un diagrama nuevo → *"Select Layout"* ([2]). Luego guardar el blueprint con un nombre reconocible.

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 2.4.1 | Debajo del toggle Live/Snapshot, clic en **Auto layout**. | Se abre un cuadro de diálogo con opciones de layout y lista de componentes. | [2] "Select Auto layout under the Live/Snapshot toggle" |
| 2.4.2 | En el desplegable **Options** (o equivalente), elegir **Replace existing components** si es el primer diagrama (así se añaden todos los componentes escaneados al canvas). Si ya tenías componentes, "Include existing components" o "Leave existing components" según quieras. | La opción elegida está seleccionada. | [2] "Choose Replace existing components from the menu" |
| 2.4.3 | En la lista de componentes AWS, dejar seleccionados los que quieras en el diagrama (o todos). Clic en **Layout** (o **Apply layout** en la nueva experiencia). | El canvas se rellena con los nodos (VPC, EC2, subnets, security groups, etc.) y sus conexiones. | [2] "Select Layout to automatically add all of the components" |
| 2.4.4 | Guardar el blueprint: menú **File** → **Save** (o Ctrl+S / Cmd+S), o botón **Save**. Si pide nombre, usar uno identificable, p. ej. **FastFlow - Jenkins Gitea POS**. | El blueprint aparece guardado (indicador "Saved" o en "My blueprints"). | [2] |

**Si falla:** Si "Auto layout" no aparece, asegúrate de que el escaneo haya terminado (Scan complete). Si el diagrama queda vacío, comprueba que la región y la cuenta son las correctas y que hay recursos en esa región.

---

## Fase 3: Obtener la URL para ver o embeber el diagrama

**Objetivo:** Tener la URL que UnClic usará en `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL` y, si aplica, en `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL`. *"By default, shared links are disabled. You can enable sharing by navigating to **Share & Export > Get shareable link**"* ([4]). El enlace contiene una clave secreta; tratarlo como contraseña.

### 3.1 Abrir el blueprint y habilitar el enlace compartible

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 3.1.1 | En Cloudcraft, abrir el blueprint guardado en Fase 2 (desde **My blueprints** o el selector de diagramas en la barra lateral). | El diagrama se muestra en el editor/vista. | — |
| 3.1.2 | En el menú del blueprint (arriba o en ⋮), buscar **Share & Export** (o **Share**). Clic en **Share & Export**. | Se abre un submenú o panel. | [4] "Share & Export > Get shareable link" |
| 3.1.3 | Clic en **Get shareable link** (o "Get shareable link" / "Share diagram"). Si los enlaces compartibles están deshabilitados por defecto, activar la opción que genera o habilita el enlace. | Aparece una URL (y a veces un botón "Copy"). | [4] "enable sharing by navigating to Share & Export > Get shareable link" |
| 3.1.4 | Copiar la URL completa. Suele tener la forma `https://app.cloudcraft.co/view/<BLUEPRINT_ID>?key=<KEY>` (el parámetro `key` es la parte secreta; no la compartas en repos públicos). Para embed, si la UI ofrece "Embed URL" o "Embed", copiar esa; si no, probar la misma URL añadiendo `&embed=true`. | Tienes una URL que, al abrirla en el navegador (en ventana privada si quieres), muestra el diagrama. | [4] – Stack Overflow / doc: formato con key |

**Si falla:** Si no ves "Share & Export" o "Get shareable link", tu plan o rol puede limitar el uso de enlaces compartibles; revisa [Cloudcraft pricing](https://www.cloudcraft.co/pricing) o contacta soporte.

---

### 3.2 (Alternativa) Obtener el Blueprint ID vía API

Si necesitas el ID para construir la URL o para la Fase 5: *"GET https://api.cloudcraft.co/blueprint"* con *"Authorization: Bearer \<API_KEY\>"*; la respuesta incluye un array `blueprints` con objetos que tienen `id` y `name` ([7]).

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 3.2.1 | Obtener una API key de Cloudcraft: **User** → **Settings** o **API** → Create/generar API key. No exponer la key en frontend ni en repos. | Tienes el valor de la API key. | [7] – [5] Manage API keys |
| 3.2.2 | Llamar a la API: `GET https://api.cloudcraft.co/blueprint` con header `Authorization: Bearer TU_API_KEY`. Ejemplo con curl: `curl -H "Authorization: Bearer TU_API_KEY" https://api.cloudcraft.co/blueprint` | Respuesta JSON con `blueprints` y cada elemento con `id`, `name`, etc. | [7] List blueprints |
| 3.2.3 | En el JSON, localizar el blueprint por `name` (p. ej. "FastFlow - Jenkins Gitea POS") y copiar el valor de `id` (UUID). La URL de vista será `https://app.cloudcraft.co/view/<id>`; si tienes key de share, añadir `?key=<key>`. | Tienes el `id` del blueprint. | [7] Example response |

---

## Fase 4: Configurar UnClic para mostrar el diagrama en la UI

**Objetivo:** La sección "Arquitectura en tiempo real" del sitio UnClic muestre el enlace o el iframe. Las variables `NEXT_PUBLIC_*` se inyectan en **build time** ([10]); en producción deben estar definidas en el entorno de build.

### 4.1 Archivo de entorno local y variables

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 4.1.1 | En tu máquina, ir a la raíz del proyecto UnClic: `toolkit-fastflow/integrations/web-cuantica/unclic/`. Comprobar que existen `package.json`, `app/`, y que hay (o puede crearse) `.env.local`. | Estás en la raíz del proyecto Next.js. | — |
| 4.1.2 | Crear o abrir el archivo **`.env.local`** en esa raíz. No commitear este archivo (debe estar en `.gitignore`). | El archivo `.env.local` existe y es editable. | [10] .env.local |
| 4.1.3 | Añadir una línea con la URL de vista (la obtenida en Fase 3). Formato exacto, sin comillas a menos que la URL contenga espacios (que no debería): `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL=https://app.cloudcraft.co/view/XXXXXXXX?key=YYYY` (sustituir XXXXXXXX y YYYY por tu blueprint id y key). Ejemplo real: `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL=https://app.cloudcraft.co/view/bp37712a-c507-4c62-ad8b-7d981cacb3be?key=abc123...` | Una sola línea, sin espacios alrededor del `=`, URL completa. | [10] – CLOUDCRAFT-DOCUMENTACION-Y-UI.md |
| 4.1.4 | (Opcional) Si quieres iframe en lugar de solo enlace, añadir: `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL=<misma URL o URL con &embed=true>`. Algunas UIs de Cloudcraft ofrecen una URL de embed distinta; si no, probar la misma que VIEW con `&embed=true` al final. | Si usas embed, la segunda variable está definida. | — |
| 4.1.5 | Guardar el archivo. | `.env.local` guardado. | — |

**Si falla:** Si en el navegador la variable sale `undefined`, recuerda que Next.js embebe `NEXT_PUBLIC_*` en **build time**: hay que **reiniciar** `npm run dev` o volver a ejecutar `npm run build` para que tome los cambios de `.env.local` ([10]).

---

### 4.2 Probar en desarrollo y en producción

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 4.2.1 | En la raíz de UnClic, ejecutar `npm run dev` (o reiniciar si ya estaba corriendo). | El servidor de desarrollo arranca. | [10] |
| 4.2.2 | Abrir en el navegador la URL del sitio (p. ej. `http://localhost:3000`). Ir a la sección **Arquitectura en tiempo real** (scroll o ancla `/#architecture-live`). | Ves la sección; si hay VIEW_URL: botón "Ver diagrama en Cloudcraft"; si hay EMBED_URL: iframe con el diagrama. Si no hay ninguna variable: mensaje para configurar URLs. | Componente ArchitectureLiveSection |
| 4.2.3 | Clic en "Ver diagrama en Cloudcraft" (si aplica): debe abrirse en nueva pestaña la URL de Cloudcraft y mostrarse el diagrama. | El diagrama se ve en Cloudcraft. | — |
| 4.2.4 | **Producción:** En el entorno donde hagas `npm run build` (Vercel, Docker, CI, etc.), definir las mismas variables de entorno **en ese entorno** antes del build. En Vercel: Project → Settings → Environment Variables. En Docker/CI: export o archivo de env inyectado en el comando de build. | Tras desplegar, la sección en producción muestra el enlace o iframe correctamente. | [10] "NEXT_PUBLIC_ variables are inlined at build time" |

---

## Fase 5 (opcional): Exportar imagen del blueprint vía API

Para documentación estática o reportes: *"GET /blueprint/{blueprint_id}/{format}"* con `format` = `svg`, `png`, `pdf`, o `mxGraph`; header `Authorization: Bearer \<API_KEY>`; query params opcionales: `width`, `height`, `scale`, `transparent`, `grid`, etc. ([7]).

| Sub-tarea | Acción | Criterio de éxito | Fuente |
|-----------|--------|-------------------|--------|
| 5.1 | Tener API key (ver 3.2.1) y el **blueprint_id** (UUID del blueprint). | Tienes ambos valores. | [7] |
| 5.2 | Llamar a `GET https://api.cloudcraft.co/blueprint/{blueprint_id}/png` con header `Authorization: Bearer TU_API_KEY`. Opcional: `?width=1920&height=1080`. Ejemplo: `curl -H "Authorization: Bearer TU_API_KEY" "https://api.cloudcraft.co/blueprint/bp37712a-c507-4c62-ad8b-7d981cacb3be/png?width=1920" -o diagram.png` | Obtienes un archivo binario (imagen PNG). | [7] Export a blueprint as an image |
| 5.3 | Usar la imagen en docs o en UnClic: guardar el binario y enlazarlo, o crear una API route en Next.js que llame a Cloudcraft (con la API key solo en servidor) y devuelva la imagen; enlazar esa ruta desde la doc o desde un `<img src="...">`. | La documentación o la UI muestran la imagen actualizada cuando se ejecuta el script o se llama a la ruta. | — |

---

## Resumen de entregables por fase

| Fase | Entregable |
|------|------------|
| 0 | Cuenta AWS con infra (o prevista); permisos IAM para crear rol; Cloudcraft Pro; usuario Owner o Administrator. |
| 1 | Cuenta AWS conectada en Cloudcraft (Role ARN guardado en Cloudcraft). |
| 2 | Blueprint Live guardado (nombre identificable) con VPC, EC2, subnets, security groups, etc. |
| 3 | URL de vista (y opcionalmente de embed) copiada; tratada como secreta. |
| 4 | `.env.local` con `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL` (y opcionalmente `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL`); build/dev probado; en producción, variables definidas en el entorno de build. |
| 5 (opcional) | API key; script o API route que exporta PNG/SVG del blueprint para docs. |

---

## Checklist final (evitar olvidos)

- [ ] Rol IAM creado con **ReadOnlyAccess**; **Require MFA** desmarcado ([1]).
- [ ] Role ARN pegado en Cloudcraft y **Save Account** ([1]).
- [ ] Diagrama en modo **Live** (no Snapshot) ([2]).
- [ ] Blueprint **guardado** con nombre reconocible ([2]).
- [ ] **Share & Export > Get shareable link** habilitado y URL copiada ([4]).
- [ ] `.env.local` con `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL` (sin comillas, una sola línea) ([10]).
- [ ] Tras cambiar `.env.local`, **reiniciar** `npm run dev` o **re-ejecutar** `npm run build` ([10]).
- [ ] En **producción**, variables `NEXT_PUBLIC_*` definidas en el entorno de **build** (Vercel, CI, Docker, etc.) ([10]).
- [ ] Sección **Arquitectura en tiempo real** (`#architecture-live`) comprobada en dev y en el despliegue.

Con estas tareas más granulares y las referencias a documentación oficial y fuentes citadas, el flujo queda sostenido en lecturas y ejemplos fiables para que funcione a la primera.

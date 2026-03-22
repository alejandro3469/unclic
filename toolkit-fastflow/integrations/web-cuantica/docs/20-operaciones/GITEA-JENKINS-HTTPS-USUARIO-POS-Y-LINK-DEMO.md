# Gitea y Jenkins: HTTPS, usuario restringido (solo POS) y enlaces de demo con sesión invitado

Objetivo: tener **Gitea** y **Jenkins** disponibles por **HTTPS**, con un **usuario restringido** que solo vea los proyectos del POS; poner en la web un **enlace a la demo del POS** y, en esa demo, **enlaces a Jenkins y Gitea** que redirijan al usuario con **sesión ya iniciada** (usuario invitado para la página web).

---

## 1. Resumen ejecutivo

| Objetivo | Cómo lograrlo | Dónde se ejecuta |
|----------|----------------|------------------|
| Gitea y Jenkins por HTTPS | Nginx + Let's Encrypt en cada EC2 | EC2 Gitea, EC2 Jenkins |
| Usuario restringido solo a proyectos POS | Usuario en Gitea/Jenkins con permisos solo sobre repos/jobs del POS | Gitea (Settings), Jenkins (Matrix/Role) |
| Link a la demo del POS en la web | Sección Demos con URL del POS (env o config) | Landing UnClic, `lib/demos.ts` |
| Enlaces Jenkins/Gitea que abran con sesión iniciada | Opción A: enlaces + credenciales invitado; Opción B: endpoint de auto-login (mismo dominio) | Página de demo / backend en dominio Jenkins/Gitea |

---

## 2. Requisitos previos

- EC2 con Gitea (puerto 3000) y EC2 con Jenkins (puerto 8080).
- Dominios (o subdominios): p. ej. `gitea.unclic.consulting`, `jenkins.unclic.consulting`, `pos.unclic.consulting`.
- Repos del POS ya creados en Gitea y jobs del POS en Jenkins.

Referencias: [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md), [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md).

---

## 3. Disponer Gitea y Jenkins con HTTPS

Para que las URLs de demo sean **https://** y el navegador no marque "no seguro":

1. En la **EC2 de Gitea**: instalar Nginx + certbot, configurar proxy a `127.0.0.1:3000`, ejecutar `sudo certbot --nginx -d gitea.unclic.consulting`. Actualizar en Gitea **ROOT URL** a `https://gitea.unclic.consulting/`.
2. En la **EC2 de Jenkins**: igual con Nginx + certbot para `jenkins.unclic.consulting`, proxy a `127.0.0.1:8080`. En Jenkins, **Jenkins URL** = `https://jenkins.unclic.consulting/`.

Guía paso a paso: **[HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)**.

Resultado: `https://gitea.unclic.consulting` y `https://jenkins.unclic.consulting` con candado.

---

## 4. Usuario restringido solo a proyectos del POS

### 4.1 Gitea: usuario invitado solo con acceso a repos del POS

1. **Gitea** → **Site Administration** → **Users** → **Create User**.
2. Crear usuario, p. ej. `demo-invitado` (o `guest-pos`), contraseña segura pero compartible para la demo.
3. Por cada **repositorio del POS** (p. ej. `pos-online`, `repo-pos-fastflow`):
   - Entrar al repo → **Settings** → **Collaborators** (o, si usas organización, **Teams**).
   - Añadir al usuario `demo-invitado` con rol **Read** (solo lectura para la demo).
4. No añadir a `demo-invitado` a otros repos ni darle permisos de administrador. Así el usuario invitado **solo ve los proyectos del POS** que tú definas.

Opcional: crear una **Organización** (p. ej. `pos-demo`) con solo los repos del POS y dar a `demo-invitado` acceso solo a esa organización.

### 4.2 Jenkins: usuario invitado solo con acceso a jobs del POS

1. **Jenkins** → **Manage Jenkins** → **Security** → asegurar que **Enable security** está activado y hay **Matrix-based security** (o **Role-based**).
2. **Manage Jenkins** → **Manage Users** → **Create User**: usuario `demo-invitado`, contraseña para la demo.
3. En **Manage Jenkins** → **Security** → **Matrix-based security**:
   - Añadir usuario `demo-invitado`.
   - Marcar solo: **Read** (Overall), **Read** (Job), **View Status** (Job). Opcional: **Build** (Job) si quieres que pueda lanzar builds de la demo.
   - Para restringir **solo a jobs del POS**: usar **Project-based Matrix** o **Role-based** (Plugin "Role-based Authorization") y crear un rol que aplique solo a jobs cuyo nombre coincida con `pos-*` o la carpeta/job del POS. Así el invitado solo ve y (opcional) ejecuta jobs del POS.

Referencia detallada: [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md) (secciones 5.1 y 5.2).

---

## 5. Link a la demo del POS en la web

En la landing UnClic:

- La sección **Demos** ya expone tarjetas con enlaces a Jenkins, Gitea, POS, Registry, etc.
- Las URLs salen de **variables de entorno** en build (o valores por defecto en código):

  - `NEXT_PUBLIC_DEMO_POS_URL` → URL de la app POS (p. ej. `https://pos.unclic.consulting`).
  - `NEXT_PUBLIC_DEMO_JENKINS_URL` → `https://jenkins.unclic.consulting`.
  - `NEXT_PUBLIC_DEMO_GITEA_URL` → `https://gitea.unclic.consulting`.

- Código: **unclic/lib/demos.ts** (`baseUrls`) y **unclic/components/sections/demos-section.tsx** (tarjetas y botón "Abrir demo").

Para que el "link a la demo del POS" lleve a una **página dedicada** (donde además estén los enlaces a Jenkins y Gitea con sesión invitado), puedes:

- Opción A: En la landing, el botón "Probar con usuario de demo" (o "Abrir demo") apuntar a una ruta interna, p. ej. `/demo`, que muestre:
  - Enlace a la app POS.
  - Enlaces "Abrir Jenkins" y "Abrir Gitea" (con flujo de sesión invitado según sección 6).
- Opción B: Mantener el enlace directo a la URL del POS y, en la **propia app POS** (o en una página estática bajo el mismo dominio), incluir una sección "Acceso a Jenkins y Gitea" con los mismos enlaces.

---

## 6. Cómo lograr que al hacer clic en Jenkins/Gitea se abran con sesión ya iniciada (usuario invitado)

Por seguridad, los navegadores no permiten que un sitio (p. ej. unclic.consulting) inyecte cookies de sesión en otro dominio (jenkins.unclic.consulting o gitea.unclic.consulting). Por tanto, "sesión ya iniciada" tiene que resolverse en el **dominio de Jenkins** o **Gitea**, o mostrando credenciales.

### Opción A (recomendada para implementar ya): Enlaces + credenciales del usuario invitado

- En la **página de demo** (o en la sección Demos de la landing):
  - Botón/enlace **"Abrir Jenkins"** → `https://jenkins.unclic.consulting` (nueva pestaña).
  - Botón/enlace **"Abrir Gitea"** → `https://gitea.unclic.consulting` (nueva pestaña).
  - Un bloque tipo: **"Usuario invitado (solo POS)"** con usuario y contraseña (o "Ver credenciales" que las muestre/modal). El visitante hace clic en el enlace y, en la pantalla de login de Jenkins/Gitea, pega esas credenciales.
- Ventajas: seguro, sin desarrollo backend; el usuario invitado ya está restringido solo a POS (pasos 4.1 y 4.2).
- Contras: no es "un clic" sin escribir; se puede mejorar con "Copiar usuario" / "Copiar contraseña" en la página.

### Opción B: Auto-login desde un endpoint en el mismo dominio que Jenkins/Gitea

Para que "al hacer link allí los redirija con la sesión iniciada" **sin** que el usuario escriba contraseña:

1. **Mismo dominio**: Un endpoint bajo `jenkins.unclic.consulting` (y otro bajo `gitea.unclic.consulting`) que reciba un **token de un solo uso o de tiempo limitado** (generado por tu backend cuando el usuario viene desde la landing/demo).
2. **Flujo**:
   - Usuario en la web hace clic en "Abrir Jenkins (sesión invitado)".
   - La web redirige a `https://jenkins.unclic.consulting/demo-login?t=TOKEN` (o similar).
   - En la EC2 de Jenkins, un **servicio pequeño** (Nginx + Lua, o un microservicio en 127.0.0.1) responde en `/demo-login`:
     - Valida el token (contra una lista o API que tú controles).
     - Si es válido: hace **login programático** a Jenkins (API: `crumb` + `j_username` + `j_password` con el usuario `demo-invitado`), obtiene la cookie de sesión y **redirige** al usuario a `https://jenkins.unclic.consulting/` con esa cookie (Set-Cookie en la respuesta del mismo dominio).
   - El usuario llega a Jenkins ya autenticado.
3. Para **Gitea** el mismo esquema: endpoint bajo `gitea.unclic.consulting` (p. ej. `/demo-login?t=TOKEN`) que valide token, haga login vía API con `demo-invitado` y redirija con la cookie de sesión.

Requisitos: desplegar ese endpoint en el mismo host/dominio que Jenkins (y que Gitea); almacenar y validar tokens (p. ej. en Redis o en un archivo con TTL); no exponer la contraseña del invitado en el cliente.

### Opción C: Basic Auth en la URL (solo si aceptas el riesgo)

- Jenkins permite en algunos casos: `https://demo-invitado:PASSWORD@jenkins.unclic.consulting/`. El navegador puede enviar Basic Auth y "loguear". **Riesgo**: la contraseña queda en historial y si alguien comparte el enlace. No recomendado para producción; solo entornos de demo muy controlados.

Resumen: para **ejecutarlo ya**, usar **Opción A**. Para "un clic y ya dentro", planificar **Opción B** (endpoint de auto-login en cada dominio).

---

## 7. Cómo lo ejecutaremos (orden y checklist)

### Fase 1: HTTPS y URLs

1. [ ] Completar HTTPS en Gitea y Jenkins según [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md).
2. [ ] En Gitea: **ROOT URL** = `https://gitea.unclic.consulting/`.
3. [ ] En Jenkins: **Jenkins URL** = `https://jenkins.unclic.consulting/`.
4. [ ] Configurar en la web (build UnClic) las variables: `NEXT_PUBLIC_DEMO_JENKINS_URL`, `NEXT_PUBLIC_DEMO_GITEA_URL`, `NEXT_PUBLIC_DEMO_POS_URL` (y si aplica Registry) para que los enlaces de la sección Demos apunten a esas URLs.

### Fase 2: Usuario restringido (solo POS)

5. [ ] **Gitea**: crear usuario `demo-invitado`; añadirlo solo como colaborador (Read) a los repos del POS.
6. [ ] **Jenkins**: crear usuario `demo-invitado`; en Matrix (o Roles) dar solo permisos de lectura (y opcional Build) sobre los jobs del POS; si usas roles, restringir por nombre/carpeta del POS.

### Fase 3: Página de demo y enlaces

7. [ ] Decidir si el "link a la demo del POS" es: (A) enlace directo a la URL del POS, o (B) ruta interna (p. ej. `/demo`) que muestre POS + Jenkins + Gitea.
8. [ ] En esa página (o en la sección Demos actual): asegurar que hay **enlaces visibles** a Jenkins y Gitea (ya presentes en `DEMO_LINKS` si las URLs están configuradas).
9. [ ] **Opción A (rápida)**: en la misma página o en un bloque "Credenciales invitado (solo POS)", mostrar (o revelar con botón) usuario y contraseña de `demo-invitado` para que el visitante pegue en Jenkins/Gitea al abrir los enlaces.
10. [ ] **Opción B (avanzada)**: implementar endpoint `/demo-login?t=TOKEN` en dominio Jenkins y en dominio Gitea; generar token desde tu backend cuando el usuario entra desde la demo; enlaces "Abrir Jenkins (entrar como invitado)" que redirijan a esa URL con token para auto-login.

### Fase 4: Comprobación

11. [ ] Abrir la landing → Demos → comprobar que el enlace al POS lleva a la app POS.
12. [ ] Comprobar que los enlaces a Jenkins y Gitea abren `https://jenkins.unclic.consulting` y `https://gitea.unclic.consulting` (candado).
13. [ ] Con usuario `demo-invitado`: en Gitea solo se ven repos del POS; en Jenkins solo se ven (y opcional ejecutan) jobs del POS.

---

## 8. Implementado en la landing (UnClic)

- **Variables de entorno**: `lib/demos.ts` usa `NEXT_PUBLIC_DEMO_JENKINS_URL`, `NEXT_PUBLIC_DEMO_GITEA_URL`, `NEXT_PUBLIC_DEMO_POS_URL`, `NEXT_PUBLIC_DEMO_REGISTRY_URL` y, para la Opción A, `NEXT_PUBLIC_DEMO_GUEST_USER` (por defecto `demo-invitado`) y `NEXT_PUBLIC_DEMO_GUEST_PASSWORD` (opcional).
- **Sección Demos**: Tarjetas con enlaces a Jenkins, Gitea, POS, Registry, etc. Botón **"Probar con usuario de demo"** → enlace a **`/demo`**.
- **Página `/demo`**: Enlaces a POS, Jenkins y Gitea (abrir en nueva pestaña) y bloque **"Usuario invitado (solo POS)"** con usuario y contraseña (si está definida), con botones **Copiar usuario** / **Copiar contraseña**. Si no se define `NEXT_PUBLIC_DEMO_GUEST_PASSWORD`, se muestra "Solicitar en contacto" con enlace a `#contacto`.
- **Bloque de credenciales**: También visible en la sección Demos de la home (debajo de la tarjeta "Prueba sin registrarte"). Componente reutilizable: `components/demo/guest-credentials-block.tsx`.
- **`.env.example`**: Incluye las variables de demo y usuario invitado para referencia.

---

## 9. Referencias rápidas

| Tema | Documento |
|------|-----------|
| HTTPS Gitea y Jenkins | [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md) |
| Usuarios y permisos | [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md) |
| Demos en la web | `unclic/lib/demos.ts`, `unclic/components/sections/demos-section.tsx` |
| POS y pipeline | [60-pos-online/README.md](../60-pos-online/README.md), [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md) |

---

## 10. Resumen en una frase

**Gitea y Jenkins se exponen por HTTPS; se crea un usuario invitado restringido solo a proyectos del POS; en la web se ofrece un link a la demo del POS y en esa demo enlaces a Jenkins y Gitea; el visitante abre Jenkins/Gitea y usa las credenciales del invitado (o, en una implementación avanzada, un endpoint de auto-login en cada dominio) para entrar ya con sesión iniciada.**

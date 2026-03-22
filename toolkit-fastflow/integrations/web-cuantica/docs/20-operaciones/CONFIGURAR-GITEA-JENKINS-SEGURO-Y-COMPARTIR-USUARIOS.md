# Configurar Gitea y Jenkins en seguro (HTTPS) y compartir usuarios con credenciales

Objetivo: **terminar la configuración de Gitea**, poner **HTTPS** en Gitea y Jenkins (y sitio) para que no aparezca "sitio no seguro", **conectar Jenkins con Gitea**, probar **pos-online en Jenkins** y **compartir acceso** a clientes/colaboradores solo con **credenciales y permisos** (acceso remoto controlado).

**Estado actual:** Ya creaste la cuenta de administrador en Gitea ("Account was successfully created. Welcome!"). Siguen: configurar URLs, HTTPS, Jenkins ↔ Gitea, job pos-online y usuarios.

---

## Resumen rápido (orden sugerido)

| Paso | Qué hacer | Dónde |
|------|-----------|--------|
| 1 | Ajustar Gitea (dominio y URL base) | Gitea → Site Administration |
| 2 | Crear repo en Gitea y subir pos-online (o esqueleto) | Gitea + git push |
| 3 | Activar HTTPS (Nginx + Let's Encrypt) en Gitea y Jenkins | EC2 Gitea y EC2 Jenkins |
| 4 | Crear credenciales de Gitea en Jenkins y job que apunte al repo | Jenkins |
| 5 | Probar pipeline pos-online en Jenkins | Jenkins → Build Now |
| 6 | Crear usuarios en Gitea y Jenkins; asignar permisos | Gitea / Jenkins |
| 7 | Compartir URLs https y credenciales a clientes/colaboradores | Tú |

Referencias de fondo: [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md), [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md).

---

## 1. Terminar configuración de Gitea (dominio y URL)

Si instalaste Gitea con la IP o con HTTP, conviene que **dominio y URL base** coincidan con cómo van a acceder los usuarios (para enlaces y clonación).

### 1.1 Con dominio unclic (recomendado)

1. En **Gitea** → **Site Administration** (icono engranaje abajo a la izquierda) → **Configuration** → pestaña **Server**.
2. Ajusta:
   - **DOMAIN:** `gitea.unclic.consulting`
   - **ROOT URL:**  
     - Mientras no tengas HTTPS: `http://gitea.unclic.consulting:3000/`  
     - Cuando tengas HTTPS (paso 3): `https://gitea.unclic.consulting/`
3. Guarda. Si Gitea corre en Docker, reinicia el contenedor: `docker restart gitea` (en la EC2 de Gitea).

### 1.2 Si aún usas IP

- **DOMAIN:** la IP pública de la EC2 de Gitea (ej. `18.223.114.68`).
- **ROOT URL:** `http://18.223.114.68:3000/` (luego podrás cambiar a HTTPS con dominio cuando lo configures).

---

## 2. Crear repositorio en Gitea y conectar pos-online

Para que Jenkins pueda clonar pos-online desde Gitea:

1. En **Gitea** → **+** (arriba) → **New Repository**.
2. **Repository Name:** `pos-online` (o el nombre que uses).
3. Visibilidad: **Private** (recomendado) o **Public**.
4. Crear el repo (sin inicializar con README si ya tienes código local).
5. En tu máquina, en el repo pos-online (GitLab o local):
   ```bash
   git remote add gitea https://gitea.unclic.consulting:3000/TU_USUARIO/pos-online.git
   git push -u gitea main
   ```
   (Sustituye `TU_USUARIO` por tu usuario de Gitea. Si usas IP: `http://18.223.114.68:3000/TU_USUARIO/pos-online.git`.)

Si el repo es **privado**, en Gitea puedes generar un **Access Token** (Settings → Applications → Generate New Token) y usar ese token como contraseña en `git push` o en Jenkins.

---

## 3. HTTPS para que no aparezca "sitio no seguro"

Para que el sitio, Gitea y Jenkins se abran con **candado** y permitan conectar a la mayor cantidad de usuarios (incluido móvil y clientes) sin avisos de "no seguro":

- Sigue la guía **[HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)**.
- En resumen:
  - **EC2 Gitea (18.223.114.68):** Nginx + `certbot --nginx -d gitea.unclic.consulting` → **https://gitea.unclic.consulting**
  - **EC2 Jenkins (3.15.4.160):** Nginx + `certbot --nginx -d jenkins.unclic.consulting` → **https://jenkins.unclic.consulting**
  - Tras activar HTTPS, en Gitea pon **ROOT URL** = `https://gitea.unclic.consulting/` y en Jenkins **Jenkins URL** = `https://jenkins.unclic.consulting/`.

Si tienes más subdominios (p. ej. sitio principal, app), repite Nginx + certbot para cada uno en la EC2 que corresponda; así todas las interfaces quedan en HTTPS y sin "sitio no seguro".

---

## 4. Jenkins: credenciales de Gitea y job para pos-online

### 4.1 Credenciales de Gitea en Jenkins

Para que Jenkins pueda clonar el repo (sobre todo si es privado):

1. **Jenkins** → **Manage Jenkins** → **Credentials** → **(global)** → **Add Credentials**.
2. **Kind:** Username with password (o "Username and password").
3. **Scope:** Global.
4. **Username:** tu usuario de Gitea.
5. **Password:** tu contraseña de Gitea o un **Access Token** (recomendado: Gitea → Settings → Applications → Generate New Token).
6. **ID:** p. ej. `gitea-pos-online` (lo usarás en el job).
7. **Description:** "Gitea unclic pos-online" (opcional). Guardar.

### 4.2 Job que use el repo de Gitea

1. **Jenkins** → **New Item** → nombre `pos-online-pipeline` → **Pipeline** → OK.
2. En **Configure**:
   - **Pipeline** → **Definition:** Pipeline script from SCM.
   - **SCM:** Git.
   - **Repository URL:**  
     `https://gitea.unclic.consulting/TU_USUARIO/pos-online.git`  
     (Con HTTPS ya configurado, sin `:3000`. Si aún usas HTTP: `http://gitea.unclic.consulting:3000/TU_USUARIO/pos-online.git`.)
   - **Credentials:** selecciona la credencial que creaste (ej. `gitea-pos-online`).
   - **Branch:** `*/main` (o `*/master` según tu rama).
   - **Script Path:** `Jenkinsfile`.
3. Guardar.

### 4.3 Probar pos-online en Jenkins

- **Build Now**. Revisa la consola del build: debe hacer checkout desde Gitea, luego las etapas del Jenkinsfile (Prepare, Build, Test, etc.). Si falla por dependencias (p. ej. generic-model), ver [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md) (sección 6).

---

## 5. Compartir acceso: solo con credenciales y permisos

Objetivo: que **clientes y colaboradores** entren **de forma remota** solo si tienen **credenciales y permisos** (sitio, Jenkins o Gitea, según el caso). Así puedes ir agregando personas sin exponer nada sin control.

### 5.1 Gitea (repos y equipos)

- **Site Administration** → **Users** → **Create User** (o que cada uno se registre si abriste el registro).
- Por repo: **Settings** del repo → **Collaborators** (o **Teams** si usas organizaciones) y añades usuarios con rol **Read**, **Write** o **Admin**.
- Para Jenkins (clonar repo privado): los mismos usuarios/tokens que añadas en Jenkins como Credentials (o un usuario de servicio con token).

Así solo quien tenga cuenta y permisos en Gitea ve/clona los repos que tú definas.

### 5.2 Jenkins (quién puede ver y ejecutar jobs)

- **Manage Jenkins** → **Users** (o **Security** → **Enable security** si no está).
- Crear usuarios: **Manage Jenkins** → **Manage Users** → **Create User** (nombre, contraseña).
- **Manage Jenkins** → **Security** → **Matrix-based security** (o **Role-based**): asigna a cada usuario (o rol) permisos como **Read**, **Build**, **Workspace**, etc. Solo así pueden entrar por **https://jenkins.unclic.consulting** y usar lo que les des.

Compartir "usuario de Jenkins" = dar la URL **https://jenkins.unclic.consulting** (cuando tengas HTTPS) y las **credenciales** (usuario + contraseña) del usuario que creaste para ellos, con los permisos mínimos necesarios.

### 5.3 Sitio e interfaces (subdominios)

- Todas las URLs que des (unclic.consulting, gitea, jenkins, app, etc.) deben ser **https://** una vez configurado Nginx + certificados (paso 3). Así al entrar no verán "sitio no seguro".
- El control de **quién entra** lo haces con:
  - **Gitea:** usuarios y permisos por repo/organización.
  - **Jenkins:** usuarios y matriz de permisos.
  - **Sitio web (si aplica):** si tienes login en la app, solo quien tenga credenciales entra; si es estático, el "control" es no publicar la URL o ponerla tras un proxy con auth.

---

## 6. Checklist "listo para compartir"

- [ ] Gitea: DOMAIN y ROOT URL correctos (y tras HTTPS, ROOT URL con `https://`).
- [ ] Gitea: repo **pos-online** creado y código subido (`git push gitea main`).
- [ ] HTTPS activo en **gitea.unclic.consulting** y **jenkins.unclic.consulting** (Nginx + Let's Encrypt).
- [ ] Jenkins: **Jenkins URL** = `https://jenkins.unclic.consulting/`.
- [ ] Jenkins: credencial de Gitea creada y job **pos-online-pipeline** con Repository URL de Gitea y Script Path `Jenkinsfile`.
- [ ] Jenkins: **Build Now** del job pos-online corre y hace checkout + etapas sin error (o solo fallos conocidos p. ej. generic-model).
- [ ] Gitea: usuarios/colaboradores creados y permisos por repo asignados.
- [ ] Jenkins: usuarios creados y permisos (Matrix/Role) asignados; compartes URL + credenciales solo a quien deba tener acceso.

---

## 7. Resumen una frase

**Gitea y Jenkins quedan listos para FastFlow:** Gitea con dominio y (opcional) HTTPS, repo pos-online en Gitea, Jenkins con credenciales de Gitea y job que clona y ejecuta el Jenkinsfile de pos-online; HTTPS en todas las interfaces para que no aparezca "sitio no seguro"; clientes y colaboradores acceden de forma remota solo con las credenciales y permisos que tú configures en Gitea y en Jenkins.

Referencias: [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md), [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md), [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md), [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md).

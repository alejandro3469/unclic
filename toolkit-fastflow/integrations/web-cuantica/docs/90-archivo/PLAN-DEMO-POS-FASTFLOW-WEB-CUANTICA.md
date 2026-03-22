# Plan: Demo POS con FastFlow para Web Cuántica (dominio + suite)

Objetivo: **ofrecer a Web Cuántica una demo del POS con FastFlow** usando el dominio **<TU_DOMINIO>** y la suite **Google Workspace**, y sentar la base para ofrecer software de calidad a proveedores.

---

## 1. Qué tenemos ya

| Recurso | Estado | Uso en la demo |
|---------|--------|-----------------|
| **Dominio <TU_DOMINIO>** (Namecheap) | Activo | URLs profesionales: jenkins.<TU_DOMINIO>, gitea.<TU_DOMINIO> |
| **Google Workspace** (correo @<TU_DOMINIO>) | Activo (verificación en curso) | Contacto con Web Cuántica, agendar demo, documentación |
| **EC2 Jenkins** (fastflow-jenkins-controller) | Running, <IP_JENKINS> | Pipeline: build, test, package, deploy del POS |
| **EC2 Gitea** (fastflow-gitea) | Running, <IP_GITEA> | Repo del POS; Jenkins clona desde aquí |
| **Puerto 8111** (app POS) | Abierto en fastflow-jenkins-sg | URL de la app tras el deploy |

---

## 2. Plan en orden (qué falta y cómo hacerlo)

### Fase A — Dominio y URLs profesionales

| # | Acción | Dónde | Doc |
|---|--------|-------|-----|
| A.1 | Añadir registros **A** en Namecheap: **jenkins** → <IP_JENKINS>, **gitea** → <IP_GITEA> | Namecheap → <TU_DOMINIO> → Advanced DNS | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| A.2 | (Opcional) A record **app** → <IP_JENKINS> para http://app.<TU_DOMINIO>:8111 | Mismo | Mismo |
| A.3 | En **Gitea** (si ya instalaste): configurar Server Domain = `gitea.<TU_DOMINIO>`, Base URL = `http://gitea.<TU_DOMINIO>:3000/` (o editar app.ini y reiniciar contenedor) | Gitea → Configuration / app.ini | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| A.4 | En **Jenkins**: Manage Jenkins → System → Jenkins URL = `http://jenkins.<TU_DOMINIO>:8080/` | Jenkins UI | Mismo |

**Resultado:** Acceso por dominio: http://jenkins.<TU_DOMINIO>:8080 y http://gitea.<TU_DOMINIO>:3000.

---

### Fase B — Pasar el POS a Gitea con FastFlow aplicado

| # | Acción | Dónde | Doc |
|---|--------|-------|-----|
| B.1 | Tener el **código pos-online** en local (clonado de GitLab/GitHub o del repo que uses). Debe incluir **Jenkinsfile** (el del FastFlow: build, test, package, deploy en 8111) y **pom.xml**. **Opción rápida:** usar el repo listo **repo-pos-fastflow** (app simulada + Jenkinsfile + Terraform + K8s + Registry): `toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow` → ver su README para push a Gitea en 2–5 min. Si no, copiar `Jenkinsfile.example` como `Jenkinsfile` en la raíz de pos-online. | Tu Mac / repo local | [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](../20-operaciones/CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md), [PASO-A-PASO-MINIMO-HOY.md](../20-operaciones/PASO-A-PASO-MINIMO-HOY.md) Paso 8, **repo-pos-fastflow/README.md** |
| B.2 | En **Gitea** (http://gitea.<TU_DOMINIO>:3000 o http://<IP_GITEA>:3000): crear repositorio **pos-online** (vacío o con README). Anotar la URL HTTPS de clonación, ej. `http://gitea.<TU_DOMINIO>:3000/tu-usuario/pos-online.git` | Gitea UI | — |
| B.3 | En local, en la carpeta del repo pos-online: `git remote add gitea http://gitea.<TU_DOMINIO>:3000/tu-usuario/pos-online.git` (o la URL que te dio Gitea). Luego `git push -u gitea main` (o `master`). Si hay conflicto con `origin`, sube solo la rama que use el pipeline. | Terminal (Mac) | [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](../20-operaciones/CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md) |
| B.4 | Asegurar que el **Jenkinsfile** en el repo tiene el stage **Deploy** real (levantar JAR en 8111), no solo `echo "Deploy"`. Ver [PASO-A-PASO-MINIMO-HOY.md](../20-operaciones/PASO-A-PASO-MINIMO-HOY.md) Paso 8. Si lo cambias, hacer commit y `git push gitea main`. | Repo pos-online | Paso 8 del paso a paso |

**Resultado:** El repo **pos-online** está en Gitea con FastFlow aplicado (Jenkinsfile que hace build, test, package y deploy en la EC2).

---

### Fase C — Jenkins apuntando a Gitea y primer build

| # | Acción | Dónde | Doc |
|---|--------|-------|-----|
| C.1 | En **Jenkins** (http://jenkins.<TU_DOMINIO>:8080 o IP:8080): job **pos-online-pipeline** (o crearlo). Configure → **Pipeline** → Pipeline script from SCM → **Repository URL** = URL de Gitea del pos-online (ej. `http://gitea.<TU_DOMINIO>:3000/tu-usuario/pos-online.git`). Branch = `main`, Script Path = `Jenkinsfile`. | Jenkins UI | [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](../20-operaciones/JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md) |
| C.2 | Si el repo en Gitea es **privado**: Jenkins → Manage Jenkins → Credentials → añadir Username/Password (usuario Gitea + token o contraseña). En el job, en Repository URL, elegir esas credenciales. | Jenkins UI | — |
| C.3 | **Triggers:** Poll SCM (ej. `H/2 * * * *` cada 2 min) para que un push dispare el build. Guardar. | Jenkins UI | Paso 7 del paso a paso |
| C.4 | **Build Now** (o hacer un push a `main`). Revisar que el pipeline pasa Checkout → Build → Test → Package → Deploy. Tras Deploy, la app queda en el puerto 8111 de la EC2 Jenkins. | Jenkins UI + EC2 | [PASO-A-PASO-MINIMO-HOY.md](../20-operaciones/PASO-A-PASO-MINIMO-HOY.md) |

**Resultado:** Un commit (o Build Now) ejecuta el pipeline completo y la app POS queda disponible en **http://<IP_JENKINS>:8111** (o http://app.<TU_DOMINIO>:8111 si configuraste el A record).

---

### Fase D — Uso del dominio y la suite para la demo

| # | Uso | Cómo |
|---|-----|------|
| D.1 | **URLs de la demo** | Dar a Web Cuántica: Jenkins = http://jenkins.<TU_DOMINIO>:8080, Gitea = http://gitea.<TU_DOMINIO>:3000, App POS = http://<IP_JENKINS>:8111 (o app.<TU_DOMINIO>:8111). Así la demo se ve con marca <TU_DOMINIO>. |
| D.2 | **Correo** | Contactar a Web Cuántica desde **alejandro@<TU_DOMINIO>** (Google Workspace): invitación a la demo, enlaces, soporte. |
| D.3 | **Agendar la demo** | Usar **Google Calendar** (misma cuenta Workspace) para fijar fecha/hora y enviar invitación por correo. |
| D.4 | **Documentación / handoff** | Usar **Google Docs** o **Drive** para compartir un documento de handoff (qué es la demo, URLs, cómo hacer un commit y ver el pipeline, contacto). Puedes redactar desde la doc de este repo y subir un PDF o Doc a Drive para compartir con Web Cuántica. |
| D.5 | **Reunión de demo** | **Google Meet** (desde Calendar o Meet) para la sesión en vivo: enseñar Jenkins, Gitea, un commit y la app actualizándose. |

**Resultado:** La demo se ofrece con dominio <TU_DOMINIO> y toda la suite (correo, Calendar, Meet, Docs/Drive) para comunicación y entrega de documentación.

---

## 3. Guía paso a paso (sigue en este orden)

### Paso 1 — DNS en Namecheap

1. Entra en **Namecheap** → **Domain List** → **<TU_DOMINIO>** → **Manage**.
2. Abre la pestaña **Advanced DNS**.
3. Pulsa **ADD NEW RECORD**.
4. **Primer registro:** Type = **A Record**, Host = **jenkins**, Value = **<IP_JENKINS>**, TTL = 1 min (o Automatic). Guardar.
5. **Segundo registro:** Type = **A Record**, Host = **gitea**, Value = **<IP_GITEA>**, TTL = 1 min. Guardar.
6. (Opcional) **Tercer registro:** A Record, Host = **app**, Value = **<IP_JENKINS>** (para la app POS en app.<TU_DOMINIO>:8111).

**Comprueba:** En 2–5 minutos, en el navegador prueba http://jenkins.<TU_DOMINIO>:8080 y http://gitea.<TU_DOMINIO>:3000 (deben cargar Jenkins y Gitea).

---

### Paso 2 — Gitea con el dominio

1. Abre **http://gitea.<TU_DOMINIO>:3000** (o http://<IP_GITEA>:3000 si el DNS aún no propagó).
2. Si **aún no completaste la instalación inicial** (asistente): en Server Domain pon **gitea.<TU_DOMINIO>**, en Gitea Base URL pon **http://gitea.<TU_DOMINIO>:3000/** y crea el usuario administrador. Instala.
3. Si **Gitea ya está instalado**: entra con tu usuario admin → **Site Administration** (icono de llave) → **Configuration** → **Server**. En **DOMAIN** pon `gitea.<TU_DOMINIO>`, en **ROOT URL** pon `http://gitea.<TU_DOMINIO>:3000/`. Guardar. Si no ves esa pantalla, edita el archivo de configuración en el servidor (ver [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md)).

---

### Paso 3 — Jenkins con el dominio

1. Abre **http://jenkins.<TU_DOMINIO>:8080** (o http://<IP_JENKINS>:8080).
2. **Manage Jenkins** → **System**.
3. Busca **Jenkins URL** y pon **http://jenkins.<TU_DOMINIO>:8080/**.
4. **Save**.

---

### Paso 4 — Crear el repo pos-online en Gitea

1. En Gitea (http://gitea.<TU_DOMINIO>:3000), inicia sesión.
2. Pulsa **+** (o **New Repository**).
3. **Repository Name:** `pos-online`.
4. **Visibility:** Public o Private (si es Private, luego añadirás credenciales en Jenkins).
5. No marques "Initialize repository" con README si vas a hacer push de un repo ya existente; si no tienes código aún, puedes marcar README.
6. **Create Repository**.
7. **Anota la URL** que muestra (ej. `http://gitea.<TU_DOMINIO>:3000/tu-usuario/pos-online.git`). La usarás en el Paso 6 y en Jenkins.

---

### Paso 5 — Tener pos-online en local con Jenkinsfile FastFlow

1. **Si ya tienes el repo pos-online clonado** (GitLab, GitHub, etc.): ve a esa carpeta en la terminal. Si **no**, clona primero:
   ```bash
   cd toolkit-fastflow
   git clone <URL-DE-TU-POS-ONLINE> pos-online
   cd pos-online
   ```
2. Comprueba que existe **Jenkinsfile** en la raíz. Si **no existe**, cópialo desde este repo:
   ```bash
   cp /ruta/a/toolkit-fastflow/integrations/web-cuantica/Jenkinsfile.example Jenkinsfile
   ```
   (Ajusta la ruta a tu máquina.)
3. Abre el **Jenkinsfile** y asegúrate de que el stage **Deploy** hace algo como (no solo `echo "Deploy"`):
   ```groovy
   stage('Deploy') {
       when { branch 'main' }
       steps {
           sh '''
               pkill -f "pos-online.*jar" || true
               sleep 2
               nohup java -jar target/pos-online-*.jar --server.port=8111 > /tmp/pos-online.log 2>&1 &
           '''
       }
   }
   ```
   Si lo cambias: `git add Jenkinsfile && git commit -m "Deploy real en 8111"`.

---

### Paso 6 — Subir pos-online a Gitea

1. En la terminal, dentro de la carpeta **pos-online**:
   ```bash
   git remote add gitea http://gitea.<TU_DOMINIO>:3000/TU_USUARIO_GITEA/pos-online.git
   ```
   (Sustituye **TU_USUARIO_GITEA** por el usuario con el que creaste el repo en Gitea.)
2. Sube la rama:
   ```bash
   git push -u gitea main
   ```
   (Si tu rama se llama `master`, usa `git push -u gitea master`.)
3. Si pide usuario/contraseña, usa tu usuario y contraseña de Gitea (o un token si lo configuraste).

**Comprueba:** En Gitea, en el repo pos-online deberías ver el código y el Jenkinsfile.

---

### Paso 7 — Crear/configurar el job en Jenkins

1. Abre **http://jenkins.<TU_DOMINIO>:8080**.
2. Si el job **pos-online-pipeline** no existe: **New Item** → nombre `pos-online-pipeline` → **Pipeline** → OK.
3. Entra en el job → **Configure**.
4. **Pipeline** → Definition = **Pipeline script from SCM**. SCM = **Git**.
5. **Repository URL:** la URL de Gitea del pos-online, ej. `http://gitea.<TU_DOMINIO>:3000/tu-usuario/pos-online.git`.
6. **Branch:** `main` (o `master`).
7. **Script Path:** `Jenkinsfile`.
8. Si el repo es **privado:** en Credentials, **Add** → Kind = Username with password, usuario y contraseña de Gitea → guardar y elegir en el job.
9. **Triggers:** marcar **Poll SCM**, Schedule = `H/2 * * * *`.
10. **Save**.

---

### Paso 8 — Primer build y comprobar la app

1. En el job **pos-online-pipeline**, pulsa **Build Now**.
2. Entra en el build (número #1) y revisa la **Console Output**. Debe pasar: Checkout → Build → Test → Package → Deploy.
3. Si **Deploy** termina bien, la app está en el puerto 8111. Abre en el navegador: **http://<IP_JENKINS>:8111** (o http://app.<TU_DOMINIO>:8111 si configuraste el A record app).

**Comprueba:** Ves la aplicación POS (o al menos una respuesta del servidor). Si falla en Build (p. ej. generic-model), ver [PASO-A-PASO-MINIMO-HOY.md](../20-operaciones/PASO-A-PASO-MINIMO-HOY.md) Paso 6.

---

### Paso 9 — Preparar la demo para Web Cuántica

1. **URLs para compartir:** Jenkins = http://jenkins.<TU_DOMINIO>:8080, Gitea = http://gitea.<TU_DOMINIO>:3000, App POS = http://<IP_JENKINS>:8111.
2. **Correo:** Desde **alejandro@<TU_DOMINIO>** (Gmail/Workspace), envía a Web Cuántica un correo con esas URLs y una fecha propuesta para la demo.
3. **Calendar:** Crea un evento en Google Calendar con la fecha/hora de la demo e invita por correo a los de Web Cuántica.
4. **Meet:** En el evento de Calendar añade un enlace de Google Meet para la reunión en vivo.
5. (Opcional) **Docs/Drive:** Crea un documento con “Demo POS FastFlow”, URLs, y “Un commit en main dispara el pipeline y actualiza la app”; compártelo por enlace con Web Cuántica.

Cuando llegue el día de la demo: abre Meet, comparte pantalla, muestra Jenkins y Gitea, haz un pequeño commit y push a `main`, y que vean cómo se dispara el build y la app se actualiza en :8111.

---

## 4. Checklist resumido (primera demo)

- [ ] **DNS:** A records jenkins y gitea en Namecheap.
- [ ] **Gitea:** Configurado con gitea.<TU_DOMINIO>; repo pos-online creado.
- [ ] **POS en Gitea:** Código + Jenkinsfile (FastFlow) subidos (git remote gitea + push).
- [ ] **Jenkins:** Job pos-online-pipeline con Repository URL = Gitea; Poll SCM; credenciales si repo privado.
- [ ] **Deploy:** Un build exitoso que deje la app en :8111.
- [ ] **URLs para Web Cuántica:** jenkins.<TU_DOMINIO>:8080, gitea.<TU_DOMINIO>:3000, app en :8111.
- [ ] **Suite:** Correo @<TU_DOMINIO> para contacto; Calendar + Meet para agendar y hacer la demo; Docs/Drive para handoff si lo quieres.

---

## 5. Próximos pasos (después de esta demo)

- **HTTPS:** Nginx/Caddy + Let's Encrypt o Cloudflare para https://jenkins.<TU_DOMINIO> y https://gitea.<TU_DOMINIO>.
- **Más clientes/proveedores:** Mismo flujo (repo en Gitea, job en Jenkins, dominio <TU_DOMINIO>) para otras demos o entregas; usar la suite para cada cliente (correo, Calendar, Docs).
- **Elastic IP:** Asignar IP fija a las EC2 para que el DNS no se rompa al reiniciar.

---

## 6. Referencias rápidas

| Tema | Documento |
|------|-----------|
| Dominio <TU_DOMINIO> + EC2 | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| Clonar POS y conectar a Gitea | [CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md](../20-operaciones/CLONAR-POS-ONLINE-Y-CONECTAR-GITEA.md) |
| Pasos Jenkins (job, trigger, deploy) | [PASO-A-PASO-MINIMO-HOY.md](../20-operaciones/PASO-A-PASO-MINIMO-HOY.md) |
| Pantallas Jenkins (Configure) | [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](../20-operaciones/JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md) |
| URLs e IPs EC2 | [URLS-Y-EC2-PRUEBAS.md](../20-operaciones/URLS-Y-EC2-PRUEBAS.md) |

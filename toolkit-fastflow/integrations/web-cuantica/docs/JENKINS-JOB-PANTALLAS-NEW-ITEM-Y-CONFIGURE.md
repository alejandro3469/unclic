# Jenkins — Pantallas "New Item" y "Configure" del job (usuario final)

Qué verás al crear un job de tipo Pipeline y al configurarlo. Referencia para reconocer las pantallas (Jenkins 2.541.2).

---

## 1. Pantalla "New Item"

**Ruta:** Jenkins (http://&lt;IP&gt;:8080) → **New Item** (en el menú lateral o superior).

**Elementos que verás:**

- **Cabecera:** Skip to content · Jenkins · All · **New Item** · Search · Manage Jenkins · [usuario] (ej. Alejandro Perez).
- **Título de página:** **New Item**.
- **Campo:** **Enter an item name** (ej. `pos-online-pipeline`).
- **Sección:** **Select an item type** — lista de tipos (Freestyle project, Pipeline, etc.). Debes elegir **Pipeline**.
- **Botón:** **OK** (para continuar a la configuración).
- **Pie:** REST API · Jenkins 2.541.2 (o la versión instalada).

**Qué hacer:** Escribir el nombre del job (ej. `pos-online-pipeline`) → seleccionar **Pipeline** → **OK**. Se abre la pantalla **Configure** del job.

---

## 2. Pantalla "Configure" del job (pos-online-pipeline)

**Ruta:** Tras crear el job → clic en el nombre del job → **Configure** (o desde el menú del job).

**Cabecera:** Jenkins · [nombre del job, ej. pos-online-pipeline] · **Configure** · Search · Manage Jenkins · [usuario].

**Secciones principales (tabs o bloques):**

### General

- **Description** — Plain text, Preview (opcional).
- **Discard old builds** (?)  
- **Do not allow concurrent builds**  
- **Do not allow the pipeline to resume if the controller restarts**  
- **GitHub project** (?)  
- **Pipeline speed/durability override** (?)  
- **Preserve stashes from completed builds** (?)  
- **This project is parameterized** (?)  
- **Throttle builds** (?)

### Triggers

Texto: *"Set up automated actions that start your build based on specific events, like code changes or scheduled times."*

Opciones (con ? de ayuda):

- **Build after other projects are built** (?)  
- **Build periodically** (?)  
- **GitHub hook trigger for GITScm polling** (?)  
- **Poll SCM** (?) — **esta es la que usamos** para que Jenkins consulte el repo cada X minutos.  
- **Trigger builds remotely (e.g., from scripts)** (?)

### Pipeline

Texto: *"Define your Pipeline using Groovy directly or pull it from source control."*

- **Definition:**  
  - **Pipeline script** (?) — script inline.  
  - **Pipeline script from SCM** — **esta es la que usamos**: repo Git + Jenkinsfile.  
- **Use Groovy Sandbox** (?)  
- **Pipeline Syntax** — enlace para generar fragmentos de Pipeline.  
- **Advanced** — opciones adicionales.

### Advanced

Opciones avanzadas del job.

**Botones al pie:** **Save** | **Apply** | REST API · Jenkins 2.541.2.

---

## 3. Paso a paso: cómo llenar la pantalla Configure

Estás en la pantalla **Configure** del job (ej. pos-online-pipeline). Sigue estos pasos en orden.

**Bloque "Pipeline":**

1. **Definition** — Elige **"Pipeline script from SCM"** (no "Pipeline script").
2. En **SCM** selecciona **Git**.
3. **Repository URL** — Pega la URL del repo que tenga el Jenkinsfile y el código. Ejemplos: GitHub `https://github.com/tu-org/pos-online.git`, Gitea Cloud `https://cloud.gitea.com/tu-usuario/pos-online.git`, o cualquier Git (GitLab, Bitbucket, Gitea self-hosted). Si **no tienes repo aún**: créalo en Gitea Cloud, GitHub u otro; sube el Jenkinsfile y el código; la URL de clonación que te den es la que pones aquí. Repo privado → en Credentials añade usuario y token o contraseña.
4. **Branch** — En "Branch Specifier" escribe `main` o `master` (la rama que uses).
5. **Script Path** — Escribe `Jenkinsfile` (si está en la raíz). Si no, la ruta relativa (ej. `ci/Jenkinsfile`).

**Bloque "Triggers":**

6. Baja a **Triggers** y marca **Poll SCM**.
7. En **Schedule** escribe `H/2 * * * *` (cada 2 min) o `* * * * *` (cada minuto para pruebas).

**Guardar:**

8. Abajo pulsa **Save** (o Apply y luego Save).

*Opcional:* En General → Description puedes poner un texto corto. Si el repo tiene submódulos (generic-model), en Pipeline → Git → **Additional Behaviours** añade "Recursively update submodules". Ver [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md) Paso 6.

---

## 4. Resumen (qué configurar)

Para el flujo “un commit → app levantada” (ver [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md)):

| Dónde | Qué hacer |
|-------|-----------|
| **Pipeline** | Definition: **Pipeline script from SCM**. SCM: **Git**. Repository URL: URL del repo (pos-online o el que tenga el Jenkinsfile). Branch: `main` (o tu rama). Script Path: `Jenkinsfile`. |
| **Triggers** | Marcar **Poll SCM**. Schedule: p. ej. `H/2 * * * *` (cada 2 min) o `* * * * *` (cada min) para pruebas. |
| **Save** | Guardar la configuración. |

---

## Referencias

- **Lista de pasos:** [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md).  
- **Por qué y cómo (job, SCM, trigger):** [GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md](GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md) secciones 9 y 11.  
- **URLs y EC2:** [URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md).

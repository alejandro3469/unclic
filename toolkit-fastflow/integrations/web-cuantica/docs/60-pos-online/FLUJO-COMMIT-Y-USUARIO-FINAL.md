# Flujo de commit y flujo de usuario final (pos-online)

Documento de referencia para **mostrar en el repo local** cómo se hace un commit y cuál es el flujo que ve el usuario final. En **pos-online** se puede copiar o adaptar este contenido a `docs/COMO-PROBAR.md` o `docs/FLUJO-COMMIT-Y-USUARIO-FINAL.md` (sin branding).

---

## 1. Flujo de commit (desarrollador)

Pasos reproducibles en local:

1. **Clonar el repo**  
   `git clone <url-pos-online>` y `cd pos-online`.

2. **Rama**  
   Trabajar en `develop` para features; integrar en `main` para releases (o según política del equipo).

3. **Cambios y commit**  
   ```bash
   git add .
   git commit -m "feat: descripción del cambio"
   ```

4. **Push**  
   `git push origin main` (o `develop`). Si hay webhook, Jenkins recibe el evento.

5. **Jenkins**  
   Pipeline corre para la rama pusheada: Test → Build imagen → (en main) Push a registry → Deploy.

6. **Comprobar**  
   - Jenkins: ver el build en la consola (logs por fase).  
   - Registry: ver el nuevo tag (ej. `main-123`, `latest`).  
   - App: si hay deploy, verificar la URL de la aplicación.

Resumen: **un commit** → **push** → **Jenkins corre automáticamente** → **registry tiene nueva versión** → **deploy visible** (si aplica).

---

## 2. Flujo de usuario final

El usuario final (o el cliente que recibe el sistema):

1. **Instala** siguiendo `docs/INSTALAR-JENKINS.md` (y opcional script de instalación) en su Linux o servidor enterprise.

2. **Accede al servidor** (SSH o escritorio) y abre en el navegador la **UI mínima** (dashboard) que enlaza a Jenkins y al registry. Ver `deploy/dashboard-demo-jenkins-registry.html` (en este toolkit) o la copia en pos-online (`deploy/dashboard.html`).

3. **Jenkins**  
   Según permisos: ver jobs, ejecutar pipeline de rutina, ejecutar "Build with parameters" para deploy por rama, ver logs de cada build.

4. **Registry**  
   Ver listado de versiones/tags; elegir una versión anterior para **rollback** (si existe job o script de rollback que despliegue esa imagen).

5. **Aplicación**  
   La app (pos-online) está desplegada en la URL configurada; el usuario final la usa con normalidad (navegador, API, etc.).

---

## 3. Qué mostrar en vivo (presentación)

- Hacer **un commit** en una rama y **push**.
- Abrir **Jenkins** y mostrar que el pipeline se ha disparado y los logs de cada fase.
- Abrir el **registry** (o la UI mínima "Versiones") y mostrar el **nuevo tag**.
- Opcional: **rollback** eligiendo un tag anterior y ejecutando el job o script de rollback.
- Todo desde **una sola máquina** (mi PC) con Jenkins y registry en local, instalable y reproducible en cualquier Linux/enterprise.

---

## Referencias

- Guía completa para pos-online: **docs/POS-ONLINE-IMPLEMENTACION-JENKINS-REGISTRY-DEMO.md**
- Coordinación: **docs/COORDINACION-AGENTES-POS-ONLINE-Y-FASTFLOW.md**

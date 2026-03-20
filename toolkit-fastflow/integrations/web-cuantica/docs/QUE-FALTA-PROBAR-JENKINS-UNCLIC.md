# Qué falta para probar el Jenkins de unclic

Checklist para poder ejecutar el pipeline pos-online en **Jenkins** (http://jenkins.unclic.consulting:8080 o https://jenkins.unclic.consulting).

---

## 1. Jenkins y EC2 (si aún no está)

| Paso | Qué hacer | Referencia |
|------|-----------|------------|
| Conectar a la EC2 | AWS Console → EC2 → fastflow-jenkins-controller → Connect (EC2 Instance Connect) | [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md) § 1 |
| Java 17 + Jenkins + Maven | En la EC2: instalar Corretto 17, Jenkins, Maven; `systemctl start jenkins` | § 2 |
| Desbloquear Jenkins | `sudo cat /var/lib/jenkins/secrets/initialAdminPassword` → pegar en http://3.15.4.160:8080 → Install suggested plugins → crear admin | § 3 |
| Puerto 8111 (app POS) | EC2 → Security Groups → fastflow-jenkins-sg → Add rule TCP 8111 | § 4 |

**Comprobar:** Abres http://jenkins.unclic.consulting:8080 (o http://3.15.4.160:8080) y entras al dashboard.

---

## 2. Repo que Jenkins vaya a clonar

Jenkins necesita una **URL de repo** que tenga en la raíz un **Jenkinsfile** y el código del POS (o al menos un proyecto que compile).

| Opción | Repo | Qué tiene |
|--------|------|-----------|
| **A** | Gitea **alejandro-perez/pos-online** (el que ya creaste) | Si hiciste `git push gitea` desde `proyectos-gitlab/pos-online`, tiene el POS completo + Jenkinsfile. Falta resolver **generic-model** (abajo). |
| **B** | Subir **repo-pos-fastflow** a Gitea | En el toolkit está `repo-pos-fastflow` con POS completo + Jenkinsfile. Hacer `git init` en esa carpeta, añadir remote Gitea, push a un repo nuevo (ej. `pos-online-fastflow`). Igual hay que resolver generic-model. |

**Comprobar:** En el navegador, abres la URL del repo en Gitea y ves en la raíz un archivo `Jenkinsfile` y la carpeta `src/`.

---

## 3. Credenciales de Gitea en Jenkins

Si el repo en Gitea es **privado**:

1. Jenkins → **Manage Jenkins** → **Credentials** → **(global)** → **Add Credentials**.
2. Kind: **Username with password**. Username: tu usuario Gitea (ej. `alejandro-perez`). Password: contraseña o **Access Token** (Gitea → Settings → Applications → Generate New Token).
3. ID: ej. `gitea-pos-online`. Guardar.

**Comprobar:** La credencial aparece en la lista con el ID que pusiste.

---

## 4. Job en Jenkins que apunte al repo

1. Jenkins → **New Item** → nombre `pos-online-pipeline` → **Pipeline** → OK.
2. **Configure:**
   - **Pipeline** → Definition: **Pipeline script from SCM**.
   - **SCM:** Git.
   - **Repository URL:** `http://gitea.unclic.consulting:3000/alejandro-perez/pos-online.git` (o la URL de tu repo en Gitea).
   - **Credentials:** la que creaste (ej. `gitea-pos-online`).
   - **Branch:** `*/main` (o `*/master` según la rama).
   - **Script Path:** `Jenkinsfile`.
3. Guardar.

**Comprobar:** En el job, **Build Now**. La consola debe mostrar **checkout** del repo. Si falla aquí, revisa URL y credenciales.

---

## 5. Resolver generic-model (para que el build no falle)

El POS compila solo si Maven encuentra:

`mx.com.endtoend.smart.bussiness.model:smartbussiness-generic-model:1.0.1-SNAPSHOT`

Sin esto, el stage **Build** (o **Package**) falla con dependencia no encontrada.

| Opción | Cómo |
|--------|------|
| **A. Submódulo** | El repo que clona Jenkins tiene **generic-model como submódulo Git**. En el job → Configure → Pipeline → Git → **Additional Behaviours** → "Recursively update submodules". Así en el workspace queda algo como `generic-model/` al lado de `pom.xml`. Hay que configurar el `pom.xml` o el Jenkinsfile para que Maven vea ese módulo (por ejemplo instalando antes `generic-model` con `mvn install` en esa carpeta). |
| **B. Incluido en el repo** | El repo que clonas ya trae generic-model en una subcarpeta (copia o submódulo) y el build sabe usarlo (p. ej. multi-módulo Maven o paso previo `mvn install -f generic-model`). |
| **C. Instalar en la EC2 una vez** | En la EC2 (donde corre Jenkins), clonar el repo **generic-model** (misma carpeta padre que pos-online en tu máquina) y ejecutar `mvn install`. Así el artefacto queda en `~/.m2/repository` del usuario con el que corre Jenkins. Luego el job de pos-online al hacer `mvn compile` lo encontrará. |
| **D. Paso previo en el pipeline** | En el Jenkinsfile, antes del stage Build: clonar (o usar otro job que publique) generic-model y hacer `mvn install` en esa carpeta; después el `mvn compile` del POS lo encuentra en el repositorio Maven local del agente. |

**Comprobar:** **Build Now** del job; el stage **Build** (o **Package**) termina sin error de “Could not find artifact ... generic-model”.

---

## 6. (Opcional) HTTPS y dominio

Para que no salga “sitio no seguro” y puedas compartir el enlace con clientes:

- En la EC2 de Jenkins: Nginx + `certbot --nginx -d jenkins.unclic.consulting` ([HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)).
- En Jenkins: **Manage Jenkins** → **System** → **Jenkins URL:** `https://jenkins.unclic.consulting/`.

No es obligatorio para **probar** el pipeline; solo para uso en producción y compartir acceso.

---

## Resumen: orden mínimo para probar

1. [ ] Jenkins instalado y accesible (http://jenkins.unclic.consulting:8080).
2. [ ] Repo en Gitea con **Jenkinsfile** en la raíz y código POS (alejandro-perez/pos-online o repo-pos-fastflow subido).
3. [ ] Credenciales de Gitea en Jenkins (si el repo es privado).
4. [ ] Job **pos-online-pipeline** (Pipeline from SCM, URL de Gitea, Script Path = Jenkinsfile).
5. [ ] **generic-model** resuelto (submódulo, incluido en repo, instalado en EC2 o paso previo en pipeline).
6. [ ] **Build Now** → checkout OK, Build/Test/Package OK (y Deploy si está en rama main).

Si algo falla, el **Console Output** del build indica en qué paso (checkout, compile, test, package) y el mensaje de error (p. ej. generic-model not found).

Referencias: [CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md](CONFIGURAR-GITEA-JENKINS-SEGURO-Y-COMPARTIR-USUARIOS.md), [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md), [DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md).

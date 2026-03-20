# Verificación: ¿Un commit recorre todo el flujo hasta levantar la instancia (POS) de nuevo?

**Objetivo que quieres:** Solo instalar y probar el POS automatizado: **una instancia**, **un commit** que pase por todo el flujo hasta que **la instancia (app) quede levantada de nuevo**. No varios sitios gratis; solo eso.

**Conclusión tras revisar el repo y el flujo:** Con lo que tenemos hoy **no se cumple** todavía. Abajo se detalla qué hay, qué falta y qué hay que hacer para que **sí** se cumpla.

---

## 1. Qué quieres que pase (flujo deseado)

1. Haces **un commit** (por ejemplo en pos-online o en un repo que tenga el Jenkinsfile y el código del POS).
2. **Jenkins** (en la EC2 que levantamos) detecta el commit y ejecuta el pipeline.
3. El pipeline hace: **Prepare → Build → Test → Lint → Package** (y opcionalmente Build image, Push registry).
4. Al final, un paso **Deploy** hace que **la aplicación POS quede levantada de nuevo** en esa misma instancia (o en un contenedor en ella), de forma que puedas abrir la app (por ejemplo en el puerto 8111) y ver que está viva.

Eso es “todo el flujo hasta que levante la instancia de nuevo”.

---

## 2. Qué tenemos hoy (después de Terraform + instalar Jenkins en la EC2)

| Qué hay | Estado |
|--------|--------|
| **1 EC2** en AWS (IP 3.15.4.160) | ✅ Creada por Terraform |
| **Jenkins** en esa EC2 (Java 11 + Jenkins) | ⏳ En curso (siguiendo QUE-SIGUE-DESPUES-DEL-APPLY) |
| **Job en Jenkins** (Pipeline from SCM, repo pos-online) | ❌ No configurado |
| **Maven** en la EC2 (para `mvn compile`, `mvn test`, `mvn package`) | ❌ No instalado |
| **Repo pos-online** (con Jenkinsfile) accesible por Jenkins | ❌ No configurado (no hay job ni clone) |
| **Dependencia generic-model** (carpeta hermana o submódulo) | ❌ No dispuesta en la EC2 |
| **Paso Deploy** en el Jenkinsfile | ❌ Es un **placeholder**: solo `sh 'echo "Deploy (main)"'` — **no levanta nada** |
| **Puerto 8111** (app POS) abierto en el security group de la EC2 | ❌ Solo 22 y 8080; 8111 no está abierto |
| **Registry / Docker** (opcional para “levantar de nuevo”) | ❌ No obligatorio si el Deploy solo corre el JAR en la misma EC2 |

---

## 3. Por qué hoy NO se cumple el flujo

- **Commit → Jenkins:** No hay job “Pipeline from SCM” apuntando al repo del POS; aunque hagas commit, Jenkins no hace nada.
- **Build/Test/Package:** El pipeline necesita Maven y el código (pos-online + generic-model) en el workspace de Jenkins; hoy no está instalado ni configurado.
- **“Levantar la instancia de nuevo”:** El stage **Deploy** del `Jenkinsfile.example` no ejecuta nada real; solo imprime un mensaje. No arranca el JAR ni un contenedor, así que la app no queda “levantada” tras el pipeline.

Por tanto: **un commit hoy no recorre todo el flujo hasta dejar la instancia (app) levantada de nuevo**.

---

## 4. Qué hay que hacer para que SÍ se cumpla (lista mínima)

Para que **un commit** dispare todo y **la instancia (app POS) quede levantada de nuevo**, hace falta lo siguiente.

### 4.1 En la EC2 (instancia única)

| Paso | Qué hacer | Por qué |
|------|-----------|--------|
| 1 | Terminar de instalar **Java 11 + Jenkins** (como en QUE-SIGUE-DESPUES-DEL-APPLY). | Sin Jenkins no hay pipeline. |
| 2 | Instalar **Maven** en la EC2 (`sudo yum install -y maven` o descarga desde Apache). | El pipeline ejecuta `mvn clean compile`, `mvn test`, `mvn package`. |
| 3 | (Opcional) Instalar **Docker** en la EC2 si quieres que el Deploy use contenedor en lugar de JAR. | Solo si eliges deploy vía `docker run` en la misma máquina. |
| 4 | Abrir el **puerto 8111** en el security group de la EC2 (ingress TCP 8111 desde 0.0.0.0/0 o tu IP). | Para poder abrir la app POS en `http://<IP>:8111` desde fuera. **Si la EC2 ya existe:** en AWS Console → EC2 → Security Groups → fastflow-jenkins-sg → Edit inbound rules → Add rule: TCP 8111, 0.0.0.0/0. **Si aplicas Terraform de nuevo:** el módulo compute ya incluye la regla 8111 (tras el cambio en el repo). |

### 4.2 En Jenkins

| Paso | Qué hacer | Por qué |
|------|-----------|--------|
| 5 | Crear un **job tipo “Pipeline”** (o “Pipeline from SCM”). | Para que Jenkins ejecute el Jenkinsfile. |
| 6 | Configurar el job para que tome el código desde el **repo pos-online** (Git). URL del repo, rama (ej. main), y que el Jenkinsfile esté en la raíz del repo. | Así cada commit (o cada poll) dispara el pipeline. |
| 7 | Asegurar que **generic-model** esté disponible: como submódulo en pos-online o como segundo proyecto en el workspace (multibranch/pipe que clone ambos). | pos-online depende de generic-model para compilar. |
| 8 | Configurar **trigger** del job: “Poll SCM” (ej. cada 2 min) o **webhook** desde Git para que un commit dispare el build. | Sin esto, el commit no inicia el pipeline. |

### 4.3 En el Jenkinsfile (Deploy real)

| Paso | Qué hacer | Por qué |
|------|-----------|--------|
| 9 | Sustituir el **Deploy** actual (`sh 'echo "Deploy (main)"'`) por un paso que **realmente levante la app** en esa misma EC2. Dos opciones mínimas: | Es lo que falta para “hasta que levante la instancia de nuevo”. |
| 9a | **Opción A (JAR):** En el stage Deploy, por ejemplo: matar el proceso anterior del JAR (si existe), luego `nohup java -jar target/pos-online-*.jar --server.port=8111 &` (o con `spring-boot.run.profiles` si aplica). | La “instancia” que se levanta es el proceso de la app en la EC2; al terminar el pipeline, la app queda escuchando en 8111. |
| 9b | **Opción B (Docker):** Si instalaste Docker: en Deploy hacer `docker stop pos-online || true`, `docker rm pos-online || true`, luego `docker run -d --name pos-online -p 8111:8111 pos-online:${IMAGE_TAG}` (o la imagen que haya construido el pipeline). | La “instancia” que se levanta es el contenedor; la app queda en 8111. |
| 10 | Dejar el Deploy condicionado a rama `main` (o la rama que uses para “producción”) si quieres que solo los commits en main levanten la app. | Evita que cada rama levante la app en la misma EC2. |

### 4.4 Resumen mínimo para que se cumpla

- **EC2:** Jenkins + Java 11 + Maven; opcional Docker; puerto 8111 abierto en el security group.
- **Jenkins:** Un job Pipeline (from SCM) con repo pos-online, Jenkinsfile en raíz, generic-model disponible, trigger por poll o webhook.
- **Jenkinsfile:** Deploy que ejecute de verdad el JAR o `docker run` en la misma EC2 para que la app quede en 8111.

Con eso, **un commit** (en la rama configurada) sí puede recorrer todo el flujo y **dejar la instancia (app POS) levantada de nuevo**.

---

## 5. Qué NO hace falta para este objetivo concreto

- **Varios sitios / varios clientes:** No hace falta; con una sola EC2 y un solo job basta para “un commit → flujo completo → app levantada”.
- **Registry externo:** No es obligatorio si el Deploy solo corre el JAR en la misma EC2 (o una imagen construida localmente con `docker run`).
- **Kubernetes / otra instancia:** No hace falta para “levantar la instancia de nuevo” en el sentido de “la app en la misma EC2”; se puede hacer con proceso JAR o con un contenedor en esa misma máquina.
- **Terraform otra vez:** No hace falta volver a aplicar Terraform para este flujo; solo abrir el puerto 8111 (por consola AWS o Terraform si quieres dejarlo en código).

---

## 6. Checklist rápido (para comprobar que sí se cumple)

Cuando lo tengas montado, puedes comprobar así:

- [ ] En la EC2: Java 11, Jenkins y Maven instalados; opcional Docker; puerto 8111 abierto.
- [ ] En Jenkins: job “Pipeline from SCM” apuntando al repo pos-online (con Jenkinsfile en raíz); generic-model disponible; trigger (poll o webhook).
- [ ] Jenkinsfile: stage Deploy hace algo real (ejecutar JAR o `docker run` en la EC2).
- [ ] Haces un commit en la rama configurada → el job se dispara → el pipeline pasa Prepare, Build, Test, Lint, Package y Deploy.
- [ ] Tras el pipeline, `http://<IP_EC2>:8111` (o `/actuator/health`) responde y la app está levantada “de nuevo”.

Si todo eso se cumple, **sí** tienes un commit que recorre todo el flujo hasta levantar la instancia (app) de nuevo.

---

**Resumen:** La tirada de “instalar varios sitios gratis” no es necesaria para tu objetivo actual. Para “solo instalar y probar el POS automatizado: un commit que pase por todo hasta levantar la instancia de nuevo”, hace falta completar los puntos de la sección 4 (y opcional 5). Con lo actual (solo EC2 + Jenkins instalándose) **aún no** se cumple; con esos pasos **sí** se puede cumplir.

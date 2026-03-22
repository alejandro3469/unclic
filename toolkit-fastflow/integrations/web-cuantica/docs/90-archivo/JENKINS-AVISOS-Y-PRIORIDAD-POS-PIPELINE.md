# Avisos de Jenkins: cuándo solucionarlos y prioridad (pipeline POS)

Los avisos que ves en **Manage Jenkins** se pueden dejar para **después**. La prioridad ahora es **ver el pipeline del POS completo** en Jenkins (build, tests, deploy real, registry, monitoreo y rollback).

---

## 1. Prioridad: primero pipeline POS completo

**Objetivo inmediato:**

- Ejecutar el pipeline **pos-online** en Jenkins de punta a punta.
- Ver **resultado de tests** (reportes, éxito/fallo).
- Hacer un **deploy real** del POS online (app corriendo en un servidor, accesible).
- **Monitorear** el despliegue (health, logs).
- Poder hacer **rollback** entre versiones (volver a una versión anterior).
- **Deploy automático** de versiones con **registry** (imagen Docker) y Jenkins (pipeline que construye, pushea a registry y despliega).

Los avisos de Jenkins (Java 17 EOL, AL2, CSP, built-in node) **no bloquean** esto. Se pueden resolver más adelante.

---

## 2. Avisos de Jenkins: qué son y cuándo solucionarlos

| Aviso | Qué significa | ¿Urgente? | Cómo solucionarlo (cuando toque) |
|-------|----------------|-----------|-----------------------------------|
| **Building on the built-in node** | Los jobs corren en el mismo servidor que el controller. Para más seguridad y escalar se usan agents. | No | Más adelante: añadir un agent (EC2 o Docker) en Manage Jenkins → Nodes. Por ahora el built-in node basta para el POS. |
| **Java 17 end of life** | Jenkins dejará de dar soporte a Java 17 a partir del 31 mar 2026. | No (aún hay margen) | Cuando puedas: instalar Java 21 (o LTS recomendado) en la EC2 y apuntar Jenkins a esa JVM (JENKINS_JAVA o config del servicio). Reiniciar Jenkins. |
| **Amazon Linux 2 end of life** | Jenkins ya no “soporta” oficialmente AL2 (desde nov 2023). AL2 sigue recibiendo parches de Amazon hasta jun 2026. | No | A medio plazo: migrar la EC2 a **Amazon Linux 2023** (nueva instancia o in-place upgrade si está disponible). No es obligatorio esta semana. |
| **Content Security Policy (CSP)** | CSP desactivado por compatibilidad. Activarlo reduce riesgo de XSS. | No | En Manage Jenkins → System → opción de CSP (o variable de entorno/documentación de tu versión). Probar con “report only” primero por si algún plugin rompe. |

**Resumen:** Puedes hacer **Ignore** o **Dismiss** en todos por ahora y **enfocarte en el pipeline POS**. Cuando tengas el flujo completo (build → test → deploy → registry → rollback), puedes volver a estos puntos.

---

## 3. Cómo tener el pipeline POS completo (deploy real, registry, rollback)

Para ver el pipeline completo, tests, deploy real, monitoreo y rollback con registry y Jenkins:

| Paso | Qué hacer | Dónde / Cómo |
|------|------------|----------------|
| **1. Pipeline que pase** | Asegurar que el job **pos-online-pipeline** termina en verde (checkout, build, test, package). | Jenkins → job → Build Now; revisar Console Output. Si falla en generic-model o Maven, ver QUE-FALTA-PROBAR-JENKINS-UNCLIC.md y EC2-SWAP-T3MICRO.md. |
| **2. Deploy en esta EC2 (8080/8111)** | El Jenkinsfile ya tiene stage Deploy (nohup java -jar ...). Que ese stage se ejecute y la app quede escuchando en 8111. | Abrir en Security Group el puerto 8111 si no está; ver que el stage Deploy y Verify instance pasen. Probar http://jenkins.unclic.consulting:8111/health o la IP:8111. |
| **3. Registry (opcional pero útil)** | Si quieres imágenes Docker: tener un registry (en EC2 o Docker Hub). El Jenkinsfile tiene stage “Push to registry” cuando REGISTRY está definido. | Configurar REGISTRY en el job (credenciales + URL). Ver REGISTRY-EC2-GRATIS.md o BUILD-REGISTRY-FASTFLOW.md. |
| **4. Deploy en otra EC2 (opcional)** | Que el pipeline despliegue en una **segunda** EC2 (p. ej. fastflow-pos-online): SSH + scp del JAR o pull de imagen desde registry + run. | Añadir en el Jenkinsfile un step que haga ssh a la EC2 de deploy, copie JAR o ejecute `docker run` con la imagen del registry. Credenciales SSH en Jenkins. |
| **5. Monitoreo** | Ver que la app responde: health, logs. | Curl a /actuator/health o /health; revisar logs en la EC2 (/tmp/pos-online.log o salida del contenedor). Opcional: health check en el job (Verify instance ya hace curl). |
| **6. Rollback** | Poder desplegar una **versión anterior** (JAR o imagen por tag). | Guardar artefactos por BUILD_NUMBER o tag de imagen (ej. pos-online:42, pos-online:41). Script o job “rollback” que despliegue el JAR o `docker run pos-online:<tag-anterior>`. |
| **7. Deploy automático** | Que cada push a main (o tag) dispare build → test → push a registry → deploy. | Pipeline from SCM ya corre al hacer Build Now; para automático: webhook de Gitea a Jenkins (trigger on push) o polling. El mismo pipeline que hace push a registry puede hacer el deploy al terminar. |

Documentación existente en el repo que ayuda:

- **QUE-FALTA-PROBAR-JENKINS-UNCLIC.md** — checklist Jenkins + Gitea + generic-model.
- **RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md** — fallo del pipeline #9, nueva instancia para pos, landing.
- **REGISTRY-EC2-GRATIS.md** / **../40-pipeline-registry/BUILD-REGISTRY-FASTFLOW.md** — registry para imágenes.
- **docs/60-pos-online/** — flujo POS, inventario, checklist.

---

## 4. Orden recomendado (resumen)

1. **Ahora:** Ignorar o cerrar los avisos de Jenkins; no es necesario solucionarlos para seguir.
2. **Siguiente:** Hacer que **pos-online-pipeline** corra completo (checkout → build → test → package → deploy → verify). Ver resultado de tests en la consola y que la app responda en 8111 (o en la EC2 de deploy).
3. **Después:** Añadir registry (si quieres imágenes) y paso de push; luego deploy automático y rollback por versión/tag.
4. **Cuando tengas tiempo:** Java 21, AL2023, agents, CSP (en ese orden de impacto).

Así te enfocas en **ver el pipeline POS completo, deploy real y monitoreo** y dejas los avisos de Jenkins para más adelante.

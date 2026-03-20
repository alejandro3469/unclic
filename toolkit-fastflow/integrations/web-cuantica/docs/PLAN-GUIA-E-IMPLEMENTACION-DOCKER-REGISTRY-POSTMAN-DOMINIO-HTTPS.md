# Plan: guía escrita e implementación (Docker, registry, Postman, dominio, HTTPS)

Dos planes detallados: **(A) Guía escrita** (qué documentar y cómo, sin mencionar dominios propios; el usuario reemplaza por el suyo) y **(B) Implementación** (código y configuración que irá a los repos de entrega, y pruebas finales en tu instancia). Las tareas son granulares y comprobables. Antes de redactar/implementar, revisar docs oficiales (AWS, Let's Encrypt/certbot, Namecheap DNS, Postman) y nuestros docs en `docs/` para que la guía y el código cumplan a la primera.

---

# PLAN A — Guía escrita (documentación para el usuario final)

**Objetivo:** Una guía de instalación ampliada que incluya deploy con Docker, redeploy con registry, pruebas con Postman desde local, y configuración de dominio propio + HTTPS (ej. Namecheap → EC2, Nginx + Let's Encrypt) para que Jenkins, Gitea y la app pos-online estén disponibles con HTTPS para todo el mundo. Todo con placeholders: "tu-dominio.com", "jenkins.tudominio.com", "gitea.tudominio.com", "app.tudominio.com"; no incluir ni mencionar dominios reales del proveedor.

---

## A.1 Estructura de la guía (orden sugerido)

1. **Hoja de valores** (existente) — Añadir variables: `TU_DOMINIO`, `SUBDOMINIO_JENKINS`, `SUBDOMINIO_GITEA`, `SUBDOMINIO_APP` (ej. `jenkins`, `gitea`, `app`), `REGISTRY_HOST` (ej. `localhost:5000` o IP:5000).
2. **Pasos 1–9** (existentes) — EC2, Java/Jenkins/swap, Docker, Gitea, push, jobs, puerto 8111, primer despliegue.
3. **Paso 10. Docker y registry (build, push, redeploy)** — Objetivo: poder construir imagen, subirla al registry y opcionalmente hacer redeploy desde la imagen. Valor: trazabilidad por tags y rollback.
4. **Paso 11. Probar el backend desde Postman (local)** — Objetivo: enviar peticiones desde tu PC al backend en 8111 y ver en los logs del servidor que pos-online procesa la petición. Valor: validar integración y flujo end-to-end.
5. **Paso 12. Dominio propio: conectar DNS (ej. Namecheap) a las instancias AWS** — Objetivo: usar tu dominio y subdominios en lugar de IPs. Valor: URLs estables y preparación para HTTPS.
6. **Paso 13. HTTPS (Nginx + Let's Encrypt)** — Objetivo: Jenkins, Gitea y la app accesibles por HTTPS para todo el mundo. Valor: candado en navegador y acceso desde cualquier dispositivo/cliente.

Cada paso con: **Objetivo**, **Pasos concretos**, **Valor alcanzado**, y referencias a la hoja de valores (reemplazar `TU_DOMINIO`, etc.).

---

## A.2 Tareas granulares — Guía escrita

### Bloque 1: Revisión de fuentes

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| A.2.1 | Revisar documentación oficial de **certbot** (Let's Encrypt) para Nginx en Linux (Amazon Linux 2 / RHEL). | Anotar comandos y requisitos (puerto 80 abierto, dominio apuntando a la IP). |
| A.2.2 | Revisar **Namecheap**: cómo añadir registros A/CNAME (Advanced DNS), TTL, propagación. | Anotar pasos genéricos sin usar un dominio real. |
| A.2.3 | Revisar **AWS**: Security Groups (puertos 80, 443), Elastic IP (opcional pero recomendado para DNS). | Incluir en la guía la apertura de 80/443 donde aplique. |
| A.2.4 | Revisar nuestros docs: `DOMINIO-NAMECHEAP-UNCLIC-EC2.md`, `HTTPS-UNCLIC-GITEA-JENKINS.md`, `REGISTRY-EC2-GRATIS.md`, `COMO-PROBAR-REGISTRY.md`, `POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md`. | Extraer pasos reutilizables y reescribirlos con placeholders (ningún "unclic" ni IP/dominio real). |

### Bloque 2: Redacción Paso 10 — Docker y registry

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| A.2.5 | Redactar **Objetivo** y **Valor alcanzado** del paso Docker + registry. | Texto corto y claro en la guía. |
| A.2.6 | Incluir: asegurar que existe **Dockerfile** en el repo pos-online; qué hace el stage "Build image" y "Push to registry"; variable **REGISTRY** en el job (ej. `localhost:5000`). | Usuario puede seguir sin asumir nuestro entorno. |
| A.2.7 | Incluir: levantar **registry:2** en la EC2 (docker run), **insecure-registries** en `/etc/docker/daemon.json`, reinicio de Docker, Security Group **5000** si se quiere acceso externo. | Comandos copiables con placeholder `<JENKINS_EC2_IP>` o "tu EC2 Jenkins". |
| A.2.8 | Incluir: cómo **comprobar** que la imagen se subió (`curl .../v2/_catalog`, `.../v2/pos-online/tags/list`). | Sin referencias a dominios propios. |
| A.2.9 | (Opcional) Redactar **redeploy desde imagen**: si el usuario quiere desplegar con `docker run` de la imagen del registry en lugar de `java -jar`, pasos mínimos (pull, stop contenedor anterior, run nuevo). | Si se implementa en Jenkinsfile la opción deploy-vía-docker, enlazar aquí. |

### Bloque 3: Redacción Paso 11 — Postman y ver logs

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| A.2.10 | Redactar **Objetivo** y **Valor alcanzado**: probar el backend desde Postman en tu PC y ver en los logs del servidor que pos-online procesa la petición. | Texto en la guía. |
| A.2.11 | **Setup Postman:** crear nueva Request; método GET; URL base `http://<JENKINS_EC2_IP>:8111` (o `https://<SUBDOMINIO_APP>.<TU_DOMINIO>` cuando tengan HTTPS). Añadir a la hoja de valores: `APP_BASE_URL`. | Usuario puede reemplazar con su IP o dominio. |
| A.2.12 | Listar **endpoints de ejemplo** sin autenticación (o los que estén públicos): p. ej. `GET /actuator/health`, `GET /actuator/info` si existe; indicar que otros endpoints pueden requerir OAuth/token y dejarlo para documentación de la app. | Sin revelar datos sensibles. |
| A.2.13 | **Ver logs en el servidor:** SSH a la EC2, `tail -f /tmp/pos-online.log` (o la ruta que use el Deploy); indicar que al enviar la petición desde Postman debe aparecer la línea correspondiente en el log. | Paso comprobable. |
| A.2.14 | Incluir (o enlazar) **colección Postman de ejemplo** (JSON export) con una request GET a `/actuator/health` y variable de entorno `APP_BASE_URL`. | Archivo en fastflow-integration (ej. `postman/pos-online-ejemplo.json`) o instrucciones para crearla. |

### Bloque 4: Redacción Paso 12 — Dominio (Namecheap u otro)

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| A.2.15 | Redactar **Objetivo** y **Valor alcanzado**: usar tu propio dominio y subdominios para Jenkins, Gitea y la app (URLs estables, preparación para HTTPS). | Sin mencionar ningún dominio real. |
| A.2.16 | Instrucción explícita: **"Reemplaza en toda la guía TU_DOMINIO y los subdominios por tu dominio y subdominios propios."** Incluir en la hoja de valores: `TU_DOMINIO`, `SUBDOMINIO_JENKINS`, `SUBDOMINIO_GITEA`, `SUBDOMINIO_APP`. | Usuario sabe que debe sustituir. |
| A.2.17 | **Namecheap (o proveedor genérico):** acceder a Advanced DNS / gestión DNS del dominio; añadir **registros A** (o CNAME): `SUBDOMINIO_JENKINS` → IP de la EC2 Jenkins; `SUBDOMINIO_GITEA` → IP de la EC2 Gitea; `SUBDOMINIO_APP` → misma IP que Jenkins (o la EC2 donde corra la app). TTL y propagación. | Redactado para cualquier dominio. |
| A.2.18 | Recomendar **Elastic IP** (AWS) para las EC2 para que las IP no cambien al reiniciar. | Una línea o nota en el paso. |
| A.2.19 | Configurar **Gitea** (Server Domain / ROOT URL) y **Jenkins** (Jenkins URL) con las URLs basadas en dominio (ej. `http://gitea.tudominio.com:3000/`, `http://jenkins.tudominio.com:8080/`) antes de pasar a HTTPS. | Comandos o pantallas genéricas. |

### Bloque 5: Redacción Paso 13 — HTTPS (Nginx + Let's Encrypt)

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| A.2.20 | Redactar **Objetivo** y **Valor alcanzado**: Jenkins, Gitea y la app accesibles por **HTTPS** para todo el mundo (candado, sin avisos "no seguro"). | Texto en la guía. |
| A.2.21 | **Requisitos:** puerto 80 y 443 abiertos en Security Group de cada EC2; dominio y subdominios apuntando a las IP correctas (Paso 12). | Lista comprobable. |
| A.2.22 | **EC2 Gitea:** instalar Nginx y certbot (paquete según distro: Amazon Linux 2 vs Ubuntu); crear bloque server en 80 para `SUBDOMINIO_GITEA.TU_DOMINIO` → proxy_pass a 127.0.0.1:3000; ejecutar `certbot --nginx -d SUBDOMINIO_GITEA.TU_DOMINIO`; actualizar Gitea ROOT URL a `https://...` (sin puerto). | Comandos y fragmentos Nginx con placeholders. |
| A.2.23 | **EC2 Jenkins:** mismo esquema para `SUBDOMINIO_JENKINS.TU_DOMINIO` → proxy a 127.0.0.1:8080; certbot; Jenkins URL = `https://...`. | Idem. |
| A.2.24 | **App pos-online (misma EC2 que Jenkins o otra):** bloque server para `SUBDOMINIO_APP.TU_DOMINIO` → proxy a 127.0.0.1:8111; certbot para ese subdominio. | Idem. |
| A.2.25 | **Renovación:** `certbot renew --dry-run`; indicar que certbot suele configurar cron/systemd timer. | Una nota. |
| A.2.26 | Tras HTTPS: actualizar **hoja de valores** y **Postman**: usar `https://SUBDOMINIO_APP.TU_DOMINIO` como APP_BASE_URL (sin puerto). | Consistencia con Paso 11. |

### Bloque 6: Integración en el documento único e índice

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| A.2.27 | Integrar los nuevos pasos (10–13) en **GUIA-INSTALACION.md** (delivery) manteniendo numeración y formato Objetivo / Pasos / Valor alcanzado. | Un solo documento fluido. |
| A.2.28 | Actualizar **tabla resumen "Valor por paso"** al final de la guía con los pasos 10–13. | Tabla completa. |
| A.2.29 | Actualizar **MANUAL-USUARIO.md** (delivery): mencionar que la guía incluye Docker, registry, Postman, dominio propio y HTTPS. | Sin referencias a dominios reales. |
| A.2.30 | Revisión final: búsqueda de "unclic", IPs reales y nombres de dominio reales en la guía y el manual; reemplazar por placeholders. | Cero apariciones de datos propios. |

---

# PLAN B — Implementación (código y pruebas en tu instancia)

**Objetivo:** Integrar en los repos de entrega (y en repo-pos-fastflow / smartbussiness-generic-model de trabajo) el código y la configuración necesarios para: deploy con Docker, redeploy con registry, flujo completo probado; y asegurar que Postman y dominio/HTTPS estén documentados (la parte ejecutable es la guía; la implementación es código limpio + ejemplos). Probar todo por última vez en tu instancia antes de dar por cerrado el paquete de entrega.

---

## B.1 Código y configuración que van a los repos

### Repo pos-online (repo-pos-fastflow y delivery/pos-online)

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| B.1.1 | **Dockerfile:** Verificar que existe y está documentado (comentarios breves: qué hace cada línea, que Jenkins copia JAR a target/app.jar). Usar base imagen que no cambie sin motivo (ej. eclipse-temurin:11-jre-alpine). | Dockerfile con comentarios; sin datos sensibles. |
| B.1.2 | **Jenkinsfile:** Quitar cualquier comentario que mencione "unclic" o dominios reales. Dejar solo referencias genéricas (ej. "EC2", "Gitea", "registry"). | `grep -r unclic Jenkinsfile` en blanco. |
| B.1.3 | **Jenkinsfile:** Confirmar que los stages Build image, Push to registry y Deploy (java -jar) están correctos y documentados en comentarios inline si hace falta. Opcional: añadir stage o rama alternativa que haga **deploy con Docker** (pull imagen del registry, stop/rm contenedor anterior, docker run -p 8111:8111). | Comportamiento actual conservado; opción Docker deploy documentada o implementada. |
| B.1.4 | **README (delivery/pos-online):** No mencionar dominios reales; indicar que la guía en fastflow-integration cubre Docker, registry y redeploy. | Una línea o párrafo. |

### Repo fastflow-integration (delivery)

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| B.1.5 | **GUIA-INSTALACION.md:** Tras redactar Plan A, incluir pasos 10–13 (Docker/registry, Postman, dominio, HTTPS) con placeholders. Hoja de valores ampliada (REGISTRY, APP_BASE_URL, TU_DOMINIO, subdominios). | Un solo archivo de guía actualizado. |
| B.1.6 | **Postman:** Crear carpeta `postman/` en fastflow-integration. Incluir archivo **pos-online-ejemplo.postman_collection.json** (o .json de colección) con al menos: variable `APP_BASE_URL` (ej. `http://localhost:8111`), request GET a `/actuator/health`. Opcional: request a un endpoint más de la app si está documentado y es público. | Importable en Postman; README o sección en la guía que explique cómo importar y sustituir APP_BASE_URL. |
| B.1.7 | **Documentación Postman en la guía:** En el Paso 11, enlazar o incrustar instrucciones: descargar/importar colección, definir variable de entorno o collection variable APP_BASE_URL, enviar request, ver respuesta; en paralelo SSH y `tail -f /tmp/pos-online.log` para ver la petición en el log. | Usuario puede reproducir sin preguntas. |
| B.1.8 | **Scripts:** Revisar scripts en `scripts/` (push a Gitea); que no contengan dominios ni usuarios reales; usar variables de entorno (GITEA_URL, GITEA_USER) como ya se hace. | Sin hardcode de dominios. |

### Repo generic-model (delivery)

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| B.1.9 | **README y comentarios:** Sin referencias a dominios o URLs propias; referencia a la guía en fastflow-integration para instalación. | Ya cubierto en limpieza anterior; verificar. |

---

## B.2 Pruebas en tu instancia (antes de dar por cerrado)

Ejecutar en **tu** entorno (tu Jenkins, tu Gitea, tu EC2) para validar que el flujo y la guía son correctos.

| ID | Tarea | Criterio de cumplimiento |
|----|--------|---------------------------|
| B.2.1 | **Build completo:** Job generic-model OK; job pos-online-pipeline OK (Prepare → Build → Test → Lint → Package → Build image → Push to registry si REGISTRY definido). | Console Output sin errores en esos stages. |
| B.2.2 | **Registry:** Con REGISTRY=localhost:5000 (o tu valor), comprobar que la imagen aparece en `curl http://localhost:5000/v2/_catalog` y `.../v2/pos-online/tags/list`. | Tags visibles (build number y latest). |
| B.2.3 | **Deploy (java -jar):** En rama main, Approve Deploy → Desplegar; comprobar que el proceso arranca, que Verify instance pasa y que `curl http://<EC2>:8111/actuator/health` devuelve UP. | App responde en 8111. |
| B.2.4 | **Postman (local):** Desde tu PC, configurar Postman con base URL = `http://<JENKINS_EC2_IP>:8111`; GET /actuator/health; en paralelo en la EC2 `tail -f /tmp/pos-online.log` y comprobar que la petición aparece en el log. | Respuesta 200 y línea en log. |
| B.2.5 | **(Opcional) Deploy con Docker:** Si implementaste opción de deploy con imagen (docker run desde registry), ejecutar un build que use esa ruta y comprobar que la app responde en 8111. | Funciona o se documenta como opcional. |
| B.2.6 | **(Opcional) Dominio + HTTPS:** Si tienes dominio y subdominios apuntando a tus EC2, seguir los pasos 12 y 13 de la guía (con tu dominio real en tu copia) y comprobar que https://jenkins..., https://gitea..., https://app... abren con candado. | Certificados válidos y servicios accesibles. |
| B.2.7 | **Checklist final:** Documento breve (puede estar en docs/ o al final de GUIA-INSTALACION): lista de comprobaciones (build, push registry, deploy, curl/Postman, logs, HTTPS si aplica) para que quien entregue pueda validar antes de enviar al cliente. | Checklist ejecutable. |

---

## B.3 Orden sugerido de ejecución

1. **Plan A (guía):** Bloques A.2.1–A.2.4 (revisión de fuentes); luego A.2.5–A.2.9 (Paso 10); A.2.10–A.2.14 (Paso 11); A.2.15–A.2.19 (Paso 12); A.2.20–A.2.26 (Paso 13); A.2.27–A.2.30 (integración y revisión).
2. **Plan B (implementación):** B.1.1–B.1.4 (pos-online); B.1.5–B.1.8 (fastflow-integration); B.1.9 (generic-model). Luego B.2.1–B.2.7 (pruebas en tu instancia). Si algo falla, ajustar código o guía y repetir las pruebas afectadas.
3. **Cierre:** Actualizar delivery con los archivos finales (GUIA-INSTALACION, MANUAL-USUARIO, postman collection, Jenkinsfile/Dockerfile sin referencias propias) y ejecutar `build-delivery-packages.sh` si aplica; verificar que los tres paquetes (generic-model, pos-online, fastflow-integration) contienen solo lo acordado y sin datos sensibles.

---

## Referencias rápidas (docs propios)

- Dominio y DNS: `docs/DOMINIO-NAMECHEAP-UNCLIC-EC2.md`
- HTTPS: `docs/HTTPS-UNCLIC-GITEA-JENKINS.md`
- Registry: `docs/REGISTRY-EC2-GRATIS.md`, `docs/COMO-PROBAR-REGISTRY.md`
- Ver instancia y peticiones: `docs/POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md`
- Pipeline y Jenkinsfile: `repo-pos-fastflow/Jenkinsfile`, `repo-pos-fastflow/Dockerfile`
- Guía actual de entrega: `delivery/fastflow-integration/GUIA-INSTALACION.md`, `MANUAL-USUARIO.md`

Documentación oficial a consultar: Certbot (certbot.eff.org), Namecheap (Advanced DNS), AWS (Security Groups, Elastic IP), Postman (variables de entorno y colecciones).

---

# Ambigüedades del plan — detalle y decisiones pendientes

Cada ítem indica **dónde** está la ambigüedad, **en qué consiste** y **qué hay que definir** para eliminarla.

---

## 1. Redeploy con Docker (opcional)

**Dónde:** A.2.9, B.1.3, B.2.5.

**Ambigüedad:** No está decidido si se implementa la opción de **deploy con Docker** (pull imagen del registry, `docker run` en lugar de `java -jar`). La guía (A.2.9) dice "si el usuario quiere..." y "si se implementa en Jenkinsfile, enlazar aquí"; B.1.3 la marca como opcional; B.2.5 prueba "si implementaste".

**Qué definir:**
- **Decisión:** ¿Se implementa deploy-vía-Docker en el Jenkinsfile (sí/no)?
- Si **sí:** mecanismo concreto (variable de entorno `USE_DOCKER_DEPLOY`, stage condicional, etc.); dónde en la guía se enlaza (Paso 10, subsección "Redeploy desde imagen").
- Si **no:** A.2.9 se redacta solo como "opcional futuro" o se quita; B.2.5 se deja como "no aplica" o se elimina.

---

## 2. Endpoints de ejemplo para Postman

**Dónde:** A.2.12, B.1.6 (segundo endpoint opcional).

**Ambigüedad:** Se pide listar "endpoints sin autenticación" y opcionalmente "un endpoint más de la app". En pos-online hay `/actuator/**` permitido; otros (p. ej. `/strategy/list`, `/company/...`) pueden requerir OAuth. No está fijado qué rutas se documentan como "seguras para probar" ni si se incluye algún ejemplo con token.

**Qué definir:**
- Lista cerrada de rutas a documentar en la guía y en la colección Postman (mínimo: `GET /actuator/health`).
- Decidir si se añade un segundo request (p. ej. `GET /actuator/info` o uno de negocio) y cuál.
- Si se documenta "otros endpoints pueden requerir OAuth" con un enlace a doc de la app o solo una frase genérica.

---

## 3. Colección Postman: archivo vs instrucciones

**Dónde:** A.2.14, B.1.6.

**Ambigüedad:** "Incluir (o enlazar) colección Postman de ejemplo (JSON export)" vs "o instrucciones para crearla". No está decidido si se entrega un **archivo .json** importable o solo **pasos escritos** para que el usuario cree la request en Postman.

**Qué definir:**
- Entregable: **archivo** (recomendado: `postman/pos-online-ejemplo.postman_collection.json`, formato Postman Collection v2.1) **o** solo instrucciones en la guía.
- Si es archivo: nombre exacto y que la guía indique "Importar este archivo en Postman y editar la variable APP_BASE_URL".

---

## 4. Nombre de variable: REGISTRY vs REGISTRY_HOST

**Dónde:** A.1 (hoja de valores: `REGISTRY_HOST`), A.2.6 (variable en Jenkins: `REGISTRY`).

**Ambigüedad:** En la hoja de valores se propone `REGISTRY_HOST`; en el job de Jenkins la variable estándar es `REGISTRY`. Puede haber confusión (dos nombres para lo mismo).

**Qué definir:**
- Un solo nombre en toda la guía y en la hoja de valores. Recomendación: **REGISTRY** (coincide con el job). En A.1 cambiar "REGISTRY_HOST" por "REGISTRY" y ejemplos `localhost:5000`.

---

## 5. Certbot / Nginx: distros a documentar

**Dónde:** A.2.22, A.2.23 (Nginx + certbot en EC2 Gitea y Jenkins).

**Ambigüedad:** "Paquete según distro: Amazon Linux 2 vs Ubuntu". Los comandos son distintos (yum/dnf vs apt; nombres de paquetes). No está fijado si la guía incluye **ambas** distros o solo una (p. ej. solo Amazon Linux 2 porque es lo que usamos en EC2).

**Qué definir:**
- Alcance: **solo Amazon Linux 2** (comandos yum/dnf y nombres de paquetes para AL2) **o** dos subsecciones (AL2 y Ubuntu/Debian) con comandos para cada una.
- En cualquier caso, dejar por escrito los comandos exactos (instalar nginx, certbot, certbot-nginx o equivalente) para la distro elegida.

---

## 6. App en la misma EC2 que Jenkins vs EC2 separada (HTTPS)

**Dónde:** A.2.24 (bloque server para pos-online).

**Ambigüedad:** "App pos-online (misma EC2 que Jenkins o otra)". Si es la **misma** EC2, Nginx debe tener **varios** `server` (jenkins en 8080, app en 8111) en un solo archivo o sitio; si es **otra** EC2, es un Nginx distinto. La redacción no aclara si se documentan los dos casos o solo uno.

**Qué definir:**
- Caso por defecto en la guía: **misma EC2** (un Nginx, dos server blocks: `jenkins.tudominio.com` → 8080, `app.tudominio.com` → 8111).
- Si además se documenta "app en otra EC2": una nota o subsección indicando que en esa EC2 se instala Nginx y certbot solo para el subdominio de la app.

---

## 7. Proveedor DNS: solo Namecheap o genérico

**Dónde:** A.2.17, título Paso 12 ("Namecheap u otro").

**Ambigüedad:** "Namecheap (o proveedor genérico)" sin precisar si los pasos son **solo para Namecheap** (Advanced DNS, nombres de menús) o si hay pasos genéricos + variantes (Cloudflare, Route53, GoDaddy tienen UIs distintas).

**Qué definir:**
- Opción A: guía **centrada en Namecheap** (Advanced DNS, Host Records, A record) y una frase: "En otros proveedores busca la sección de DNS o Zone File y añade un registro A con el mismo valor."
- Opción B: una tabla o lista "Namecheap: X; Cloudflare: Y; Route53: Z" con el equivalente de "añadir A record".
- Decidir una opción y redactar en consecuencia.

---

## 8. Dónde va el checklist final

**Dónde:** B.2.7.

**Ambigüedad:** "Documento breve (puede estar en docs/ o al final de GUIA-INSTALACION)". No está fijado el **sitio** del checklist (subsección al final de la guía vs archivo aparte en fastflow-integration).

**Qué definir:**
- **Un** lugar: por ejemplo "última sección de GUIA-INSTALACION.md" con título "Checklist antes de entregar al cliente", **o** archivo `fastflow-integration/CHECKLIST-ENTREGA.md` enlazado desde la guía.
- Contenido mínimo del checklist: build OK, push registry OK, deploy OK, curl/Postman OK, logs vistos, (opcional) HTTPS probado.

---

## 9. Prueba de HTTPS en el cierre (obligatoria u opcional)

**Dónde:** B.2.6, B.2.7.

**Ambigüedad:** B.2.6 es "(Opcional) Dominio + HTTPS"; B.2.7 pide checklist "HTTPS si aplica". No está claro si **cerrar** el paquete de entrega exige haber probado HTTPS en tu instancia o no.

**Qué definir:**
- Si el entregable incluye los pasos 12 y 13 (dominio + HTTPS), decidir: **validación obligatoria** (quien entrega debe haber probado al menos una vez HTTPS en su entorno) **u opcional** (la guía está lista pero no es obligatorio probar HTTPS para dar por cerrado).
- Reflejarlo en B.2.6/B.2.7 y en el checklist (ítem "HTTPS probado" como obligatorio o opcional).

---

## 10. Ruta del log cuando hay deploy por Docker

**Dónde:** A.2.13 (ver logs: `tail -f /tmp/pos-online.log`).

**Ambigüedad:** Hoy el Deploy usa `java -jar` y el log es `/tmp/pos-online.log`. Si se implementa deploy con **Docker**, el log sería `docker logs <container>`. La guía no aclara si se documenta solo la ruta actual o también la variante Docker.

**Qué definir:**
- Por ahora: documentar solo **/tmp/pos-online.log** (deploy actual con java -jar).
- Si se implementa deploy con Docker (ver punto 1): añadir una línea o nota: "Si desplegaste con Docker, ver logs con: `docker logs -f <nombre_contenedor_pos_online>`."

---

## 11. Registry en otra máquina (no localhost)

**Dónde:** A.2.7 (insecure-registries, registry en la EC2).

**Ambigüedad:** Se documenta registry en la **misma** EC2 (localhost:5000, insecure-registries). Si el usuario pone el registry en **otra** EC2 o servidor, haría falta TLS o marcar esa IP como insecure-registry en el cliente Docker del agente Jenkins. No está definido el alcance.

**Qué definir:**
- Alcance de la guía: **solo registry en la misma EC2 que Jenkins** (localhost:5000), con una nota tipo "Si en el futuro usas un registry en otra máquina, tendrás que configurar TLS o insecure-registries con la IP de ese servidor."
- O bien: subsección opcional "Registry en otra EC2" con los pasos mínimos (abrir 5000, daemon.json en el agente con la IP del registry).

---

## 12. Formato y nombre del archivo de colección Postman

**Dónde:** B.1.6.

**Ambigüedad:** "pos-online-ejemplo.postman_collection.json (o .json de colección)" — Postman permite exportar en **Collection v2.1** (recomendado) o formato legacy. El nombre del archivo puede ser con o sin prefijo `.postman_collection`.

**Qué definir:**
- Formato de exportación: **Postman Collection v2.1** (compatible con import/export estándar).
- Nombre del archivo: por ejemplo `pos-online-ejemplo.postman_collection.json` (Postman suele usar esta extensión para reconocer colecciones). Dejarlo escrito en B.1.6 y en la guía.

---

## Resumen de decisiones a tomar

| # | Tema | Decisión necesaria |
|----|------|--------------------|
| 1 | Redeploy con Docker | Implementar sí/no; si sí, mecanismo (env var, stage condicional). |
| 2 | Endpoints Postman | Lista de rutas a documentar; si hay segundo request y cuál. |
| 3 | Colección Postman | Entregar archivo JSON vs solo instrucciones. |
| 4 | REGISTRY vs REGISTRY_HOST | Unificar nombre (recomendado: REGISTRY). |
| 5 | Certbot/Nginx | Solo AL2 o AL2 + Ubuntu; comandos exactos. |
| 6 | App misma EC2 vs otra (HTTPS) | Caso por defecto (recomendado: misma EC2, dos server blocks). |
| 7 | DNS | Solo Namecheap con nota genérica u otros proveedores explícitos. |
| 8 | Checklist final | Dentro de GUIA-INSTALACION vs archivo CHECKLIST-ENTREGA.md. |
| 9 | HTTPS en cierre | Probar HTTPS obligatorio u opcional para dar por cerrado. |
| 10 | Log con Docker deploy | Solo /tmp/pos-online.log ahora; nota para docker logs si hay deploy Docker. |
| 11 | Registry en otra máquina | Solo same-EC2 o subsección opcional para registry remoto. |
| 12 | Formato Postman | v2.1 y nombre de archivo exacto. |

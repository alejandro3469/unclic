# Instancias AWS, HTTPS y pipeline pos-online — diagnóstico y pasos

Resumen: **por qué ves ERR_SSL_PROTOCOL_ERROR** en otro dispositivo, **qué puede haber fallado en el pipeline #9**, y cómo dejar **todo en HTTPS** y **levantar otra instancia gratuita** para pos-online y/o landing unclic.consulting.

---

## 1. Por qué ves "This site can't provide a secure connection" (ERR_SSL_PROTOCOL_ERROR)

Cuando entras desde **otro dispositivo** (no tu Mac) a **jenkins.unclic.consulting**:

- Si usas **https://jenkins.unclic.consulting** (con **s**), el navegador espera **TLS/SSL** en el servidor.
- En tu EC2 hoy **solo está escuchando HTTP en el puerto 8080** (Jenkins a pelo). No hay certificado ni puerto 443.
- El servidor responde con HTTP, no con HTTPS → el navegador muestra **ERR_SSL_PROTOCOL_ERROR** ("sent an invalid response").

**Conclusión:** No es que “falle Jenkins”; es que **HTTPS no está configurado**. Hay que poner un **proxy inverso (Nginx) en 443** con certificado (Let's Encrypt) delante de Jenkins (y de Gitea).

**Qué hacer (resumen):**

1. En la EC2 de **Jenkins** (3.15.4.160): instalar **Nginx** y **Certbot**, configurar `server_name jenkins.unclic.consulting` en 80, luego `sudo certbot --nginx -d jenkins.unclic.consulting`. Así queda **https://jenkins.unclic.consulting** (sin :8080).
2. En la EC2 de **Gitea** (18.223.114.68): lo mismo para **gitea.unclic.consulting** (proxy a 127.0.0.1:3000, luego certbot).
3. En **Jenkins**: **Manage Jenkins** → **System** → **Jenkins URL:** `https://jenkins.unclic.consulting/`.
4. En **Gitea**: **ROOT URL** = `https://gitea.unclic.consulting/`.

Guía paso a paso completa: **[HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)**.

Cuando Nginx y Certbot estén hechos, **todos los sitios y subdominios serán secure (HTTPS)** y accesibles desde cualquier dispositivo sin aviso de “no seguro”.

---

## 2. Qué pudo fallar en el pipeline pos-online #9

El pipeline tiene: Checkout → Prepare → Build → Test → Lint → Package → **Build image** → **Push to registry** → Cleanup → **Deploy** → **Verify instance**.

Para saber **qué falló** hay que abrir el **Console Output** del build #9 en Jenkins y buscar la primera etapa en rojo.

| Si falló en… | Causa habitual | Qué hacer |
|-------------|----------------|-----------|
| **Build / Package** | OOM (memoria), dependencia generic-model | Swap en EC2 ([EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md)); MAVEN_OPTS ya restrictivos; generic-model instalado en EC2 o en pipeline. |
| **Build image** | Docker no instalado o usuario `jenkins` no puede usar Docker | **Instalar Docker en la EC2 de Jenkins:** ver **[POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md](POS-ONLINE-JENKINS-INSTALAR-DOCKER-EC2.md)** (yum/dnf, `usermod -aG docker jenkins`, reiniciar Jenkins). Si no instalas Docker, el Jenkinsfile **omite** el build de imagen y el pipeline sigue (Package → Deploy → Verify). |
| **Push to registry** | REGISTRY vacío o credenciales incorrectas | Si no usas registry, REGISTRY está vacío y el stage se salta. Si lo usas, configurar REGISTRY y credenciales en el job. |
| **Deploy** | JAR no arranca (OOM, puerto en uso) | Revisar `/tmp/pos-online.log` en la EC2. Puerto 8111 abierto en Security Group (fastflow-jenkins-sg). |
| **Verify instance** | La app no respondió en 3 s o no tiene /actuator/health ni /health | Revisar que el JAR levante en 8111; que el health endpoint exista. |

**Recomendación:** Abre **Build #9** → **Console Output**, busca la línea que dice `FAILED` o el stage que termina en error, y aplica la fila de la tabla. Si compartes el fragmento de consola donde falla, se puede afinar más.

---

## 3. Levantar otra instancia gratuita AWS para pos-online (deploy separado)

La idea: **dejar Jenkins y Gitea en sus EC2 actuales** y usar **una tercera EC2 (t3.micro, free tier)** solo para **ejecutar la app pos-online** (JAR o contenedor) detrás de Nginx + HTTPS.

**Pasos resumidos:**

1. **AWS Console** → EC2 → **Launch instance** (misma región us-east-2).
   - Name: ej. `fastflow-pos-online`.
   - AMI: Amazon Linux 2.
   - Tipo: **t3.micro** (free tier).
   - Key pair: crear o usar existente.
   - Security group: SSH (22), HTTP (80), HTTPS (443) y, si quieres acceso directo al JAR, TCP 8111 (opcional; mejor solo 80/443 y Nginx proxy).

2. **DNS (Namecheap):** Nuevo registro **A**: Host = `pos` (o `pos-online`) → Value = IP pública de la nueva EC2. Queda **pos.unclic.consulting** (o el nombre que elijas).

3. **En la nueva EC2:**
   - Instalar Java 17 (Corretto) para ejecutar el JAR.
   - Opción A: **Jenkins despliega aquí por SSH** (crear credencial en Jenkins, en el Jenkinsfile en Deploy hacer `ssh` a esta EC2, copiar JAR y lanzar `java -jar`).
   - Opción B: **Desplegar a mano** la primera vez: copiar el JAR desde tu máquina o desde la EC2 de Jenkins (`scp`) y ejecutar `nohup java -jar pos-online-*.jar --server.port=8111 &`.
   - Instalar **Nginx + Certbot**: proxy de 80/443 a 127.0.0.1:8111 y `certbot --nginx -d pos.unclic.consulting`. Así **https://pos.unclic.consulting** sirve la app con candado.

4. **Pipeline (opcional):** Si quieres que el Jenkinsfile haga deploy en esta EC2, añadir en el stage Deploy: SSH a la nueva instancia, subir JAR, reiniciar el servicio (o script que haga `pkill` + `nohup java -jar ...`). Ver [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) para mantener un solo lugar con IPs y nombres.

Con esto tienes **una instancia dedicada al pos-online** con HTTPS y disponible en toda la web.

---

## 4. Landing unclic.consulting en un nuevo servidor (cuarta instancia o mismo que pos)

Quieres **una app que sea el landing de unclic.consulting**, automatizada como el POS (pipeline que construye y despliega).

**Opciones:**

| Opción | Descripción |
|--------|-------------|
| **A. Nueva EC2 solo para landing** | Cuarta instancia t3.micro. Pipeline (en Jenkins) que construye el sitio estático (hub + landings de web-cuantica) y despliega por SSH: copia archivos a `/var/www/unclic` y Nginx sirve esos estáticos. Certbot para **unclic.consulting** y **www**. |
| **B. Misma EC2 que pos-online** | En la tercera EC2 (pos-online), Nginx con dos server_name: `pos.unclic.consulting` → proxy al JAR 8111; `unclic.consulting` y `www.unclic.consulting` → root a `/var/www/unclic` (contenido estático). Un solo servidor para app POS y landing. |
| **C. EC2 de Jenkins o Gitea** | No recomendable: mezclar tráfico público del landing con Jenkins/Gitea complica seguridad y mantenimiento. |

Recomendación: **B** (misma EC2 que pos, distintos server_name) ahorra una instancia; **A** si quieres separar del todo.

**Contenido del landing:** El repo **web-cuantica** (toolkit-fastflow/integrations/web-cuantica) tiene `deploy/hub-unclic/`, `deploy/landings/`, proxy estático. El pipeline puede hacer `npm run build` (si hay) o copiar esas carpetas y servir con Nginx. Certbot para **unclic.consulting** y **www.unclic.consulting** en esa EC2.

**Todos los sitios y subdominios en HTTPS:** En cada EC2 donde sirvas algo público (Jenkins, Gitea, pos-online, landing), usar **Nginx en 443 + Let's Encrypt**. No dejar servicios solo en HTTP para acceso público; así evitas ERR_SSL_PROTOCOL_ERROR y el aviso de “no seguro” en cualquier dispositivo.

---

## 5. Resumen de instancias y estado objetivo

| Instancia | IP actual | Uso | Estado objetivo |
|-----------|-----------|-----|------------------|
| **fastflow-jenkins-controller** | 3.15.4.160 | Jenkins (pipeline) | Nginx + Certbot → **https://jenkins.unclic.consulting** |
| **fastflow-gitea** | 18.223.114.68 | Gitea (repos) | Nginx + Certbot → **https://gitea.unclic.consulting** |
| **fastflow-pos-online** (nueva) | (nueva IP) | App pos-online (JAR) | Nginx + Certbot → **https://pos.unclic.consulting** (o subdominio que elijas) |
| **fastflow-landing** (opcional o misma que pos) | (nueva IP o misma que pos) | Landing unclic.consulting | Nginx + Certbot → **https://unclic.consulting** y **https://www.unclic.consulting** |

**DNS (Namecheap):** Un registro **A** por subdominio (jenkins, gitea, pos, etc.) apuntando a la IP de la EC2 correspondiente. Tras Nginx + Certbot, **todo queda secure y disponible en toda la web**.

---

## 6. Documentos relacionados

- **HTTPS paso a paso:** [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md)
- **Infraestructura actual (IPs, DNS, security groups):** [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md)
- **Swap en t3.micro (OOM):** [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md)
- **Qué falta para probar Jenkins / generic-model:** [QUE-FALTA-PROBAR-JENKINS-UNCLIC.md](QUE-FALTA-PROBAR-JENKINS-UNCLIC.md)

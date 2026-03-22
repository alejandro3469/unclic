# pos-online: ver que la instancia está corriendo y enviar peticiones

Cómo **comprobar en la EC2** que la app pos-online está en marcha y cómo **enviarle peticiones** desde otra instancia, otro servidor o desde tu máquina local.

---

## 0. Deploy manual

El **deploy no es automático**. El pipeline deja todo listo hasta un paso antes del deploy (Prepare → Build → Test → Lint → Package → [Build image] → [Push to registry]) y en la rama **main** se detiene en el stage **Approve Deploy**. Ahí Jenkins muestra un mensaje y un botón **Desplegar**; solo al pulsarlo se ejecutan Cleanup → Deploy → Verify instance. Así se controla cuándo se levanta la instancia. Tras el deploy, conviene **comprobar que la instancia inicia y que puede recibir peticiones** (secciones 2 y 3 de este doc).

---

## 1. Dónde corre la app

La app pos-online se despliega en la **misma EC2 donde corre Jenkins** (fastflow-jenkins-controller):

- **IP pública:** 18.119.157.22  
- **Puerto de la app:** 8111  
- **No usa Docker para el deploy:** el pipeline arranca el JAR con `java -jar` (ver [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md) y el Jenkinsfile, stage Deploy).

Si en el último build no se pulsó **Desplegar** en el stage Approve Deploy, o los stages Cleanup/Deploy/Verify salieron *skipped* (p. ej. rama distinta de `main`), la app **no se habrá desplegado**. Para que esté corriendo: en un build de rama `main`, aprobar el Deploy en Jenkins cuando aparezca el mensaje, o arrancar el JAR a mano en la EC2.

---

## 2. Ver en la instancia AWS (por consola/SSH) que está corriendo

Conéctate por **SSH** (o **EC2 Instance Connect**) a la EC2 de Jenkins (**18.119.157.22**).

### 2.1 Proceso Java del JAR

```bash
# Ver si hay un proceso Java ejecutando el JAR de pos-online
pgrep -af "pos-online.*jar"
```

Si la app está levantada, verás una línea con `java -jar ... pos-online-0.0.1-SNAPSHOT.jar`. Si no devuelve nada, la app no está corriendo.

### 2.2 Puerto 8111 en escucha

```bash
# Ver qué proceso escucha en el puerto 8111 (Amazon Linux 2 / 2023)
sudo ss -tlnp | grep 8111
# o
sudo netstat -tlnp | grep 8111
```

Deberías ver que el puerto **8111** está en estado LISTEN. Si no aparece, la app no está escuchando en ese puerto.

### 2.3 Petición local en la EC2 (health)

```bash
# Comprobar que la app responde dentro de la propia EC2
curl -s http://localhost:8111/actuator/health
# o, si no tiene actuator:
curl -s http://localhost:8111/health
```

Si está bien, la respuesta será algo como `{"status":"UP"}`. Si falla (connection refused, timeout), la app no está arriba o no escucha en 8111.

### 2.4 Log de la aplicación

**Dónde estás:** Si tu prompt es `ec2-user@ip-10-0-1-62` (o similar), **ya estás en la EC2** de Jenkins/pos-online (18.119.157.22). No hace falta hacer `ssh ec2-user@18.119.157.22` desde esa misma máquina.

```bash
# Ver las últimas líneas del log (el pipeline escribe aquí al hacer Deploy)
tail -50 /tmp/pos-online.log

# Seguir el log en vivo
tail -f /tmp/pos-online.log
```

Ahí ves si Spring Boot arrancó correctamente o si hubo error (OOM, puerto en uso, etc.). Si ves muchas líneas `Hibernate: drop table if exists ... CASCADE` y luego `Closing JPA EntityManagerFactory` / `SessionFactory shut-down`, la app **se apagó** (ShutdownHook): por un nuevo deploy que hizo `pkill`, o porque el proceso terminó. Comprueba si sigue corriendo con `pgrep -af "pos-online.*jar"`.

**Menos ruido (ocultar SQL de Hibernate):**

```bash
tail -200 /tmp/pos-online.log | grep -v "Hibernate:"
# o solo líneas con nivel INFO/ERROR
tail -f /tmp/pos-online.log | grep -E "INFO|WARN|ERROR"
```

### 2.5 La app arranca y se cae (proceso ya no está, curl vacío)

Si `pgrep -af "pos-online.*jar"` no devuelve nada y el log solo muestra "Starting PosOnlineApplication" y poco más (perfil **local** activo), la app **murió durante el arranque**. Causas frecuentes:

- **OOM (memoria):** en t3.micro (1 GB), Spring Boot + perfil local puede quedarse sin memoria. Comprobar: `dmesg | tail -30` (buscar "Out of memory" o "Killed process").
- **Perfil `local`:** suele esperar BD o configuración que no existe en la EC2. Arrancar con otro perfil, p. ej. `--spring.profiles.active=envDev` o el que corresponda al entorno sin BD local.
- **Excepción al iniciar:** no siempre queda en el log. Ejecutar en **primer plano** para ver el error:
  ```bash
  cd /var/lib/jenkins/workspace/pos-online-pipeline
  sudo -u jenkins java -jar target/pos-online-0.0.1-SNAPSHOT.jar --server.port=8111
  ```
  (Ctrl+C para salir.) Ahí verás la traza completa si falla por BD, config, etc.

**Resumen:** Revisar `dmesg` por OOM; probar otro perfil (`--spring.profiles.active=...`); o ejecutar el JAR en foreground para ver la excepción.

---

## 3. Enviar peticiones desde fuera de la EC2

Para que **otra instancia**, **otro servidor** o tu **máquina local** puedan llegar a la app, el **Security Group** de la EC2 debe permitir tráfico entrante en el puerto **8111**.

### 3.1 Abrir el puerto 8111 en AWS

1. **AWS Console** → **EC2** → **Instances**.
2. Selecciona la instancia **fastflow-jenkins-controller** (18.119.157.22).
3. Pestaña **Security** → clic en el **Security Group** (ej. fastflow-jenkins-sg).
4. **Edit inbound rules** → **Add rule**:
   - **Type:** Custom TCP  
   - **Port range:** 8111  
   - **Source:** según quién deba acceder:
     - **Cualquiera:** 0.0.0.0/0 (acceso desde internet).
     - **Solo tu IP:** My IP (o la IP concreta).
     - **Solo otra EC2/servidor:** IP o CIDR de ese servidor.
5. **Save rules**.

### 3.2 Desde tu máquina local (Mac/Linux/Windows)

Con el puerto 8111 abierto (y, si usaste 0.0.0.0/0, sin firewall local bloqueando):

```bash
# Health
curl -s http://18.119.157.22:8111/actuator/health
curl -s http://18.119.157.22:8111/health

# O abrir en el navegador
# http://18.119.157.22:8111/actuator/health
# http://18.119.157.22:8111/
```

Sustituye **18.119.157.22** por la IP pública actual de la EC2 si ha cambiado.

### 3.3 Desde otra instancia EC2 o servidor

Desde esa máquina (SSH o script):

```bash
# Sustituye 18.119.157.22 por la IP pública de la EC2 de Jenkins
curl -s http://18.119.157.22:8111/actuator/health
```

Si el Security Group solo permite la IP de ese servidor, funcionará. Si el grupo no tiene regla para 8111 desde ese origen, la conexión será rechazada (timeout).

### 3.4 Si usas un dominio (ej. pos.unclic.consulting)

Si en Namecheap (o tu DNS) creas un registro **A** que apunte a **18.119.157.22**, podrás usar:

```bash
curl -s http://pos.unclic.consulting:8111/actuator/health
```

El puerto **8111** hay que seguir poniéndolo en la URL salvo que pongas un proxy (Nginx) en 80/443 que reenvíe a 8111.

---

## 4. ¿Hace falta otro servidor para pos-online?

**No es obligatorio.** Hoy el pipeline despliega en la **misma EC2 donde corre Jenkins** (fastflow-jenkins-controller): el stage Deploy ejecuta `java -jar` en ese nodo y la app escucha en el puerto 8111. Una sola instancia sirve para Jenkins y para la app pos-online.

**Levantar otro servidor tiene sentido si:**
- Quieres separar Jenkins (CI) de la app (producción o preproducción): una EC2 para Jenkins y otra para pos-online.
- Necesitas más capacidad o aislar la app (reinicios de Jenkins no afectan a pos-online, o viceversa).

En ese caso: creas una segunda EC2 (p. ej. fastflow-pos-online), abres en su Security Group el puerto 8111 (y 22 para SSH), y cambias el stage **Deploy** del Jenkinsfile para que, en lugar de ejecutar `java -jar` en el agente actual, haga **SSH** a esa EC2, copie el JAR (o la imagen Docker) y arranque la app allí. Requiere credenciales SSH en Jenkins y que el usuario jenkins pueda conectar a la nueva EC2. Ver [RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md) (opción “Deploy en otra EC2”).

---

## 5. Resumen

| Qué quieres hacer | Dónde / comando |
|-------------------|------------------|
| Ver si el proceso está corriendo (en la EC2) | SSH → `pgrep -af "pos-online.*jar"` |
| Ver si el puerto 8111 está en escucha (en la EC2) | SSH → `sudo ss -tlnp \| grep 8111` |
| Probar que la app responde (desde la EC2) | SSH → `curl -s http://localhost:8111/actuator/health` |
| Ver el log de la app (en la EC2) | SSH → `tail -50 /tmp/pos-online.log` |
| Permitir acceso desde internet | AWS → Security Group → Inbound rule TCP 8111, Source 0.0.0.0/0 (o IP concreta) |
| Enviar petición desde tu PC | `curl http://18.119.157.22:8111/actuator/health` o navegador |
| Enviar petición desde otra EC2/servidor | Mismo `curl`; Security Group debe permitir el origen |

**Nota:** El deploy es **manual** (stage Approve Deploy). Si no se pulsó Desplegar o el build no era de rama main, la app no se habrá arrancado. Para que esté corriendo: en un build de **main**, aprobar el Deploy cuando Jenkins lo pida, o arrancar el JAR a mano en la EC2:

```bash
# En la EC2, desde el workspace del job o con el JAR copiado
nohup java -jar /var/lib/jenkins/workspace/pos-online-pipeline/target/pos-online-0.0.1-SNAPSHOT.jar --server.port=8111 > /tmp/pos-online.log 2>&1 &
```

Referencias: [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md), [URLS-Y-EC2-PRUEBAS.md](URLS-Y-EC2-PRUEBAS.md), [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md) (stage Deploy).

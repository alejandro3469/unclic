# Jenkins — Pantalla “Unlock” y comando para la contraseña inicial

Documentación para el usuario final: qué verás en el navegador (pantalla de Jenkins) y qué ejecutar en la EC2 para obtener la contraseña, con ejemplo de salida.

---

## 1. Qué verás en el navegador (Jenkins)

Al abrir **http://3.15.4.160:8080** (o tu IP:8080) la primera vez, la pestaña del navegador muestra **“Sign in - Jenkins”** y Jenkins muestra un modal de **desbloqueo**. El texto es exactamente este (tal como sale en Jenkins 2.541):

```
Getting Started

Unlock Jenkins

To ensure Jenkins is securely set up by the administrator, a password has been written
to the log (not sure where to find it?) and this file on the server:

    /var/lib/jenkins/secrets/initialAdminPassword

Please copy the password from either location and paste it below.

Administrator password

    [ campo de contraseña ]   [ Continue ]
```

- El enlace **“not sure where to find it?”** lleva a la documentación de Jenkins para localizar los logs en tu sistema.
- Debes **copiar la contraseña** del archivo indicado (desde la EC2), **pegarla** en el campo “Administrator password” y pulsar **Continue**.

---

## 2. Comando en la EC2 para obtener la contraseña

Conéctate a la instancia (EC2 Instance Connect) y ejecuta:

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

---

## 3. Ejemplo de salida en la terminal

La salida es **una sola línea**: la contraseña (hexadecimal). No hay más texto. Ejemplo:

```
[ec2-user@ip-10-0-1-62 ~]$ sudo cat /var/lib/jenkins/secrets/initialAdminPassword
aa813eef20154f8dab1665433c6c6832
[ec2-user@ip-10-0-1-62 ~]$
```

Debes copiar **solo** la línea de la contraseña (`aa813eef20154f8dab1665433c6c6832` en el ejemplo), sin espacios ni saltos de línea, y pegarla en el campo “Administrator password” en la pantalla de Jenkins.

---

## 4. Resumen para el usuario final

| Dónde | Qué hacer |
|-------|-----------|
| **Navegador** | Abrir http://&lt;IP-pública&gt;:8080. Verás “Unlock Jenkins” y un campo “Administrator password”. |
| **Terminal (EC2)** | Ejecutar: `sudo cat /var/lib/jenkins/secrets/initialAdminPassword` |
| **Copiar** | La única línea que imprime el comando (ej. `aa813eef20154f8dab1665433c6c6832`) |
| **Pegar** | En “Administrator password” en la pantalla de Jenkins y pulsar **Continue** |

---

## 5. Qué sigue después de Unlock: “Customize Jenkins”

Tras pulsar **Continue**, aparece la pantalla **“Customize Jenkins”** (Getting Started, Jenkins 2.541.2). El texto es similar a:

```
Getting Started
Customize Jenkins

Plugins extend Jenkins with additional features to support many different needs.
```

**Qué hacer aquí:**

1. **Install suggested plugins** (recomendado) — Instala el conjunto de plugins sugeridos (Pipeline, Git, etc.). Tardará unos minutos; al terminar pasarás a crear el usuario admin.
2. **Select plugins to install** — Eliges tú qué plugins instalar (más control, más pasos).
3. **None** — No instalar nada ahora (Jenkins mínimo; puedes añadir plugins luego desde Manage Jenkins → Plugins).

Para seguir el [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md) (job Pipeline, Git, Maven), conviene **Install suggested plugins**.

**Después de instalar plugins (o si elegiste None):**

- Jenkins te pedirá **Create First Admin User** (nombre, contraseña, nombre completo, email). Puedes rellenar y guardar, o **Skip and continue as admin** si solo estás probando.
- Tras eso verás la **Jenkins URL** (ej. http://3.15.4.160:8080/) y **Start using Jenkins**. Pulsa **Start using Jenkins** y entrarás al **dashboard** (pantalla principal con el menú y la lista de jobs).

A partir de ahí sigue el **Paso 4** del [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md) (abrir puerto 8111) y el **Paso 5** (crear el job Pipeline).

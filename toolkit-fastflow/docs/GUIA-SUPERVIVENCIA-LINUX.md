# Guía de Supervivencia Linux para DevOps (Capa 0 de FastFlow)

FastFlow se ejecuta sobre **Linux**. No importa si usas Kubernetes, Docker o Terraform; el motor invisible que lo hace posible es el sistema operativo.

## 1. La Filosofía del Sistema de Archivos
En Linux, **todo es un archivo**. Entender dónde están las cosas es clave para que tu pipeline no falle.

### Directorios Críticos en FastFlow:
- `/etc/`: Donde viven las **Recetas de Configuración**. Aquí Jenkins, Docker y Nginx guardan sus reglas de juego.
- `/var/`: El almacén de los **Logs y Datos Variables**. Si tu disco se llena aquí, tu pipeline se detiene.
- `/home/`: Tu espacio personal de trabajo. Donde el usuario `jenkins` guarda su `workspace`.

## 2. Herramientas de Navegación (Tus Ojos en el Servidor)
Cuando entras a un servidor por SSH, no tienes ratón. Estas son tus herramientas de supervivencia:

- `ls -lh`: **Lista** los archivos. El flag `-h` los hace legibles para humanos (KB, MB, GB).
- `pwd`: **¿Dónde estoy?** Imprime tu ruta actual. Vital para scripts de automatización.
- `cd`: **Cambia de directorio**. Usa rutas absolutas (empiezan por `/`) para moverte con precisión.
- `cat` y `less`: **Lee** archivos de configuración o logs sin editarlos (evita accidentes).

## 3. Gestión de Archivos (Tus Manos en el Servidor)
- `touch`: Crea un archivo vacío (útil para disparar webhooks o tests).
- `mkdir -p`: Crea directorios. El flag `-p` crea toda la ruta si no existe (ideal para organizar despliegues).
- `cp` y `mv`: Copia y mueve archivos. Recuerda: mover un archivo en el mismo directorio es como renombrarlo.
- `rm -rf`: El comando más peligroso. Borra de forma recursiva y forzada. Úsalo solo en procesos de `cleanup`.

## 4. El Concepto de Shell (Bash)
La **Shell** es la capa de software que te permite hablar con el hardware. En FastFlow, escribimos scripts de Bash para que las herramientas (Jenkins, Docker) trabajen juntas. Es el pegamento de tu automatización.

---
*Para profundizar, recomendamos leer "Welcome to Linux" y practicar cada comando en tu terminal.*

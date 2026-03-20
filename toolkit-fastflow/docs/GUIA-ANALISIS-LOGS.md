# Guía de Procesamiento de Datos y Logs (Capa 4 de FastFlow)

En FastFlow, los datos son evidencia. Saber procesar logs y archivos de texto de forma eficiente es lo que separa a un administrador de un arquitecto de flujo.

## 1. El Arte de la Tubería (Pipes)
El operador `|` (pipe) permite que la salida de un comando sea la entrada del siguiente. Esto permite construir herramientas complejas a partir de piezas simples.

### Comandos Esenciales de Análisis:
- `cat`: Muestra el contenido completo (tu punto de partida).
- `grep`: Filtra líneas por texto (ej: `grep "ERROR"`).
- `cut`: Extrae columnas específicas (ej: extraer solo la IP de un log).
- `sort -u`: Ordena y elimina duplicados.
- `wc -l`: Cuenta cuántas líneas (o eventos) hay en total.

## 2. Redirección de Salida
Usa el operador `>` para guardar tus resultados en un archivo.
Ejemplo: `cat access.log | grep "404" > errores-404.txt`.

## 3. tmux: Tu Terminal en Segundo Plano
Cuando ejecutas una tarea de larga duración (como un despliegue masivo o una migración de base de datos), no querrás que se detenga si tu conexión se cae.

- **Sesiones**: `tmux` crea un entorno virtual que sigue corriendo aunque cierres tu terminal.
- **Ventanas**: Puedes tener múltiples "pestañas" de terminal en una sola conexión SSH.
- **Comandos Clave**:
  - `Ctrl-b c`: Nueva ventana.
  - `Ctrl-b d`: Desacoplar (dejar corriendo en segundo plano).
  - `tmux a`: Volver a entrar a la sesión.

## 4. Ejemplo Práctico: Auditoría de Accesos
```bash
# ¿Cuántos usuarios únicos accedieron hoy con error?
cat fastflow-access.log | grep "500" | cut -d',' -f2 | sort -u | wc -l
```

---
*Para dominar estas herramientas, practica con archivos de logs reales en tu entorno de pruebas.*

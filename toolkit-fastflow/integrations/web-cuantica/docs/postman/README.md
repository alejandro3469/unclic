# Postman — probar pos-online (UnClic / FastFlow)

## Archivo para importar

| Archivo | Uso |
|---------|-----|
| **[pos-online-unclic.postman_collection.json](pos-online-unclic.postman_collection.json)** | Importar en Postman (**Import** → elegir este JSON). |

## Configuración

1. Tras importar, abre la colección → **Variables** (o edita variables de colección).
2. **`APP_BASE_URL`**: base del POS **sin** barra final.
   - Ejemplo IP: `http://3.129.247.127:8111`
   - Ejemplo DNS: `http://pos.unclic.consulting:8111`
3. Ejecuta **Health (actuator)** o **Health (/health)** según exponga tu build.

## Guía paso a paso completa

Ver **[GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** (commit → Jenkins → Postman → rollback).

## Copia en paquete de entrega

Existe una colección equivalente en `delivery/fastflow-integration/postman/` para entregas genéricas (placeholders). La de esta carpeta está alineada con el flujo **unclic.consulting** documentado arriba.

# Maven + Java — Por qué, open source y cómo replicar

## Por qué lo usamos

- **Build del POS:** La aplicación pos-online es Java/Maven; el pipeline ejecuta `mvn test`, `mvn package` y despliega el JAR.
- **Objetivo de automatización:** Build repetible y declarativo (pom.xml); todo en open source (Eclipse Adoptium/Corretto, Apache Maven).

## Open source

- **Java:** Amazon Corretto 17 (GPL+CE) o Eclipse Temurin. **Maven:** Apache Maven, licencia Apache 2.0.

## Cómo replicar

| Consola | Comando |
|---------|---------|
| Terminal (EC2 o local) | `java -version` (debe ser 17) |
| Terminal (en repo pos-online) | `mvn test` |
| Terminal (en repo pos-online) | `mvn package -DskipTests` |

En Jenkins el job Pipeline ejecuta estos comandos en el agente. Ver [REQUISITOS](../instalacion/REQUISITOS.md): Java 17 (Corretto).

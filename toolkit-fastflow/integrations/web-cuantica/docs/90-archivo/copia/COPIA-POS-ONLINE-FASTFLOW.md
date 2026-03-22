# Copia del proyecto: POS Online con FastFlow (implementación a la medida)

**Para:** Cualquier persona que necesite entender qué es este repo y qué es la implementación FastFlow aquí.  
**Contexto:** POS Online es el sistema; FastFlow en este repo **no es un producto aparte**, es la **implementación a la medida** de pipeline, registry y despliegue para POS Online (y su dependencia generic-model), en el contexto JD Edwards, Oracle, ambientes y bases de datos en la fase actual.

---

## 1. En una frase

**POS Online** es la aplicación principal (Java, Spring Boot): punto de venta en línea, integrada con **generic-model** (repo local obligatorio en la misma carpeta padre) y con **JD Edwards / Oracle** (ambientes, bases de datos, arquitectura según la fase del proyecto). **FastFlow**, aquí, es solo la **implementación** de cómo se construye, se prueba, se empaqueta en imagen y se sube a un registry para desplegar y hacer rollback: Jenkins, Maven, Docker, registry, Terraform y Kubernetes. No hay “producto FastFlow” separado; todo aplica a POS Online y a generic-model.

---

## 2. Qué es cada cosa (palabras sencillas)

### POS Online

- Aplicación en **Java** (Spring Boot) para **punto de venta en línea**.
- Se construye con **Maven** (`mvn clean install`): compila, pasa tests y genera el JAR.
- Depende de **generic-model**: otro repo que debe estar en la **misma carpeta padre** que pos-online (obligatorio para compilar y probar).
- Se conecta con **JD Edwards** y **Oracle** (ambientes, bases de datos); la arquitectura y la fase en la que están definen qué ambientes y qué bases se usan.

### Generic-model

- Repo **local** en la carpeta hermana de pos-online (mismo padre).
- Es una **dependencia obligatoria** para POS Online: sin él, el build puede fallar.
- Debe estar compilado/instalado según cómo pos-online lo referencie (Maven local, módulo, etc.).

### JD Edwards, Oracle, ambientes y bases de datos

- POS Online opera en un contexto **enterprise**: JD Edwards (ERP), Oracle (bases de datos), varios **ambientes** (desarrollo, pruebas, producción) y las **bases de datos** y la **arquitectura** correspondientes a la fase actual del proyecto.
- La implementación FastFlow (pipeline, registry, despliegue) debe contemplar estos ambientes y esta arquitectura a la hora de desplegar o hacer rollback.

### FastFlow en este repo (solo implementación)

- **No es un producto** que se vende aparte.
- Es la **implementación a la medida** para POS Online (y generic-model) de:
  - **Tubo de trabajo (pipeline):** Maven test → package → construcción de imagen Docker → subida al registry (con tags).
  - **Registry:** lugar donde se guardan las versiones (imágenes) de la aplicación para desplegar y volver atrás (rollback).
  - **Jenkins:** el programa que ejecuta el pipeline cuando hay un commit.
  - **Dashboard:** una página mínima para ver Jenkins y el registry (y la app desplegada).
  - **Terraform y Kubernetes:** para definir la infraestructura y el despliegue de la aplicación en los ambientes que correspondan.

Todo lo anterior aplica **solo** a POS Online (y a generic-model) y a la fase y arquitectura en la que se encuentra el proyecto.

---

## 3. Estructura de repos (recordatorio)

- **Carpeta padre**
  - **pos-online** (este repo): aplicación + implementación FastFlow (Jenkinsfile, scripts, deploy, docs).
  - **generic-model**: dependencia obligatoria; mismo nivel que pos-online.

---

## 4. Etapa actual (resumen)

| Qué | Estado |
|-----|--------|
| POS Online (Java) | Maven; tests; JAR. Dependencia: generic-model (carpeta hermana). |
| Pipeline + registry | Jenkinsfile y scripts (build-and-push, simulate). Jenkins en docs/30-instalacion. |
| Despliegue | Terraform y K8s en deploy/. Registry y rollback documentados. |
| JDE / Oracle / ambientes | Contexto del proyecto; alinear implementación a la fase y arquitectura. |

---

## 5. Mensaje corto para copiar/pegar

> Este repo es POS Online con la implementación FastFlow a la medida: pipeline (Maven, Docker, registry), Jenkins, dashboard, Terraform y K8s. No hay producto FastFlow separado. POS Online depende de generic-model (repo en la misma carpeta padre). El contexto es JD Edwards, Oracle, ambientes y bases de datos según la arquitectura y la fase actual del proyecto.

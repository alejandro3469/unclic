# 02-cicd-pipeline - Notas Privadas

## Apertura sugerida

"Hoy el reto no es compilar código; es entregar cambios con seguridad y frecuencia."

## Puntos clave

- Flujo: commit -> build -> test -> lint -> scan -> package -> deploy -> health.
- Si falla un gate, no se promueve.
- Producción con aprobación explícita.
- Evidencia completa por build y commit.

## Evidencias concretas

- Libro: `_book_reference_txt/chapter14/Jenkinsfile.txt`
- Caso real: `/Users/wallfacer/proyectos-gitlab/pos-online/docs/cicd/FLUJO-COMPLETO.md`

## Cierre de sección

"El objetivo no es automatizar por automatizar, es reducir riesgo y aumentar throughput de negocio."


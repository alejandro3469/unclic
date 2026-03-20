# CI/CD Pipeline - Knowledge Base

## Flujo recomendado

`Commit -> Build -> Test -> Lint -> Security Scan -> Package -> Push -> Deploy -> Health Check`

## Reglas de control

- Si un stage falla, no avanza.
- Deploy a producción con aprobación explícita.
- Promoción por ramas/entornos.

## Buenas prácticas operativas

- Stash/unstash o artifacts para no recompilar innecesariamente.
- Logs legibles con prefijos estables.
- Variables por entorno para evitar hardcoding.

## Evidencia en fuentes

- Pipeline declarativo y push: `_book_reference_txt/chapter9/Jenkinsfile.declarative.txt`
- Pipeline enterprise por ramas y aprobación: `_book_reference_txt/chapter14/Jenkinsfile.txt`
- Caso real en producción: `/Users/wallfacer/proyectos-gitlab/pos-online/docs/cicd/FLUJO-COMPLETO.md`


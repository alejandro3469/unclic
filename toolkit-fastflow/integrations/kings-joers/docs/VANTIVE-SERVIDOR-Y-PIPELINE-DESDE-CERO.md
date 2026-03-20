# Vantive: servidor + pipeline desde cero (Kings & Joers)

Guía para tener **Vantive** en la subcompañía **Kings & Joers**:

1. **Servidor gratuito** (EC2) solo para Vantive.
2. **Disponible en la web** (HTTP 80, opcional HTTPS).
3. **Pipeline FastFlow** en el **Jenkins de Kings & Joers** que despliega desde un **nuevo repo** a esa instancia.

Se usa el **Jenkins de Kings & Joers** (nueva EC2), no el de web-cuantica.

---

## Resumen

| Qué | Dónde |
|-----|--------|
| Jenkins | EC2 **fastflow-jenkins-kings-joers** (puerto 8080) |
| Servidor Vantive | EC2 **fastflow-vantive** (Nginx, 80, 443) |
| Repo | Gitea **vantive-app** (nuevo repo) |
| Job | **vantive-pipeline** en el Jenkins de Kings & Joers |
| URL app | http://\<IP_VANTIVE\> o dominio (ej. vantive.unclic.consulting) |

---

## Orden de pasos

1. **Crear EC2 Jenkins Kings & Joers** e instalar Jenkins (Java, Git). Ver [KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md](KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md) Fase 1.
2. **Crear EC2 servidor Vantive** (fastflow-vantive), security group 22, 80, 443. Instalar Nginx. Ver [KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md](KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md) Fase 2.
3. **DNS (opcional):** Registro A en tu dominio (ej. vantive) → IP de fastflow-vantive.
4. **Crear repo Vantive en Gitea** y subir el código de **kings-joers/vantive/** (index.html, Jenkinsfile, README). Ver [KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md](KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md) Fase 3.
5. **En el Jenkins de Kings & Joers:** credencial Gitea, credencial SSH **vantive-deploy-ssh** (clave .pem de la EC2 Vantive), job **vantive-pipeline** (Pipeline from SCM, repo vantive-app, Jenkinsfile), variable **VANTIVE_HOST** = IP o dominio del servidor Vantive. Ver [KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md](KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md) Fase 4.
6. **Build Now** → el pipeline despliega en la EC2 Vantive. La app queda visible en **http://\<IP_VANTIVE\>** (o el dominio configurado).

---

## Código de la app

Está en **kings-joers/vantive/**: index.html, Jenkinsfile, README. El Jenkinsfile usa la credencial `vantive-deploy-ssh` y la variable `VANTIVE_HOST` inyectada en el job.

---

## Documentos relacionados

- [KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md](KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md) — Nueva subcompañía, dos EC2 (Jenkins + Vantive), nuevo repo, un Jenkins por subcompañía.
- [README.md](../README.md) — Visión general de Kings & Joers.

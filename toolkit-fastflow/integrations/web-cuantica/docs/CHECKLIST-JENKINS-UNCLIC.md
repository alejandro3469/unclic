# Checklist — Jenkins unclic (marca lo que ya hiciste)

Marca con `[x]` según vayas completando. **Recorrido principal:** [GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md). Pasos ampliados: [GUIA-PASO-A-PASO-AHORA-JENKINS-UNCLIC.md](GUIA-PASO-A-PASO-AHORA-JENKINS-UNCLIC.md).

---

## Infra y acceso

- [ ] Abro **http://jenkins.unclic.consulting:8080** (o http://3.15.4.160:8080) y veo algo (Unlock o dashboard).
- [ ] Java 17 + Jenkins + Maven en la EC2; Jenkins desbloqueado (contraseña inicial, plugins, admin), según **§5** de la guía única.
- [ ] En AWS: Security Group de la EC2 Jenkins tiene regla **TCP 8111** (para la app POS).

## Repo en Gitea

- [ ] Tengo un repo en Gitea con **Jenkinsfile** en la raíz (ej. alejandro-perez/pos-online o pos-online-fastflow).
- [ ] Si uso repo-pos-fastflow: lo subí con el script o a mano:
  ```bash
  export GITEA_USER=alejandro-perez
  export GITEA_REPO=pos-online-fastflow
  bash scripts/push-repo-pos-fastflow-to-gitea.sh
  ```
  (Antes: crear el repo vacío en Gitea, sin "Initialize Repository".)

## Jenkins: credenciales y job

- [ ] Jenkins → Manage Jenkins → Credentials → Add: usuario Gitea + contraseña o token, ID `gitea-pos-online`.
- [ ] Jenkins → New Item → `pos-online-pipeline` → Pipeline from SCM → Git → URL del repo Gitea, credencial, Branch `*/main`, Script Path `Jenkinsfile` → Save.

## Primer build

- [ ] Build Now en **pos-online-pipeline**; en Console Output veo **checkout** correcto.
- [ ] **generic-model** instalado en `~/.m2` del usuario **jenkins** ([EC2-INSTALAR-GENERIC-MODEL.md](EC2-INSTALAR-GENERIC-MODEL.md)), antes del build de confianza.
- [ ] Build termina en **SUCCESS**; si hay Deploy en main, pruebo **http://&lt;IP-EC2&gt;:8111/health**.

---

Cuando todo esté marcado, el Jenkins de unclic está listo para probar el pipeline pos-online.

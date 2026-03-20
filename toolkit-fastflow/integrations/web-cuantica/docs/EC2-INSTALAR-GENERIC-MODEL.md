# Instalar generic-model en la EC2 de Jenkins

Para que el job **pos-online-pipeline** pueda compilar, Maven debe encontrar el artefacto **smartbussiness-generic-model:1.0.1-SNAPSHOT**. Una forma es instalarlo **una vez** en la EC2 con el usuario con el que corre Jenkins (`jenkins`).

---

## 1. Conectarte a la EC2

- **AWS Console** → EC2 → Instances → **fastflow-jenkins-controller** → **Connect** → EC2 Instance Connect → Connect.

---

## 2. URL del repo generic-model

Necesitas la **URL de clonación** del repo generic-model (GitLab, Gitea, etc.). Ejemplos:

- GitLab: `https://gitlab.com/TU_ORG/generic-model.git`
- Gitea: `http://gitea.unclic.consulting:3000/alejandro-perez/smartbussiness-generic-model.git`

Sustituye en los comandos siguientes `URL_DEL_REPO_GENERIC_MODEL` por tu URL real.

**Alternativa automatizada:** Si subes generic-model a Gitea y creas un job Jenkins **generic-model** que haga `mvn install`, el artefacto se guarda en ~/.m2 en cada build y pos-online-pipeline puede encadenarse (“Build after” generic-model). Ver **[GENERIC-MODEL-GITEA-JENKINS.md](GENERIC-MODEL-GITEA-JENKINS.md)**.

---

## 3. Instalar como usuario jenkins (recomendado)

En la terminal de la EC2, pega y ejecuta (sustituye la URL):

```bash
# Sustituye URL_DEL_REPO_GENERIC_MODEL por la URL real del repo generic-model
GENERIC_MODEL_URL="URL_DEL_REPO_GENERIC_MODEL"

sudo su - jenkins -s /bin/bash -c "
  set -e
  cd /tmp
  rm -rf smartbussiness-generic-model
  git clone $GENERIC_MODEL_URL smartbussiness-generic-model
  cd smartbussiness-generic-model
  mvn install -DskipTests -q
  echo 'generic-model instalado en ~/.m2/repository (usuario jenkins)'
"
```

Si `git clone` pide usuario/contraseña y el repo es privado, tendrás que usar una URL con token o configurar credenciales. Alternativa: clonar como `ec2-user`, hacer `mvn install`, y copiar el artefacto al directorio Maven de jenkins (más engorroso).

---

## 4. Comprobar

```bash
sudo su - jenkins -s /bin/bash -c "ls -la ~/.m2/repository/mx/com/endtoend/smart/bussiness/model/smartbussiness-generic-model/"
```

Deberías ver la versión (ej. `1.0.1-SNAPSHOT`). Luego en Jenkins: **Build Now** en **pos-online-pipeline**; el stage Build no debería fallar por generic-model.

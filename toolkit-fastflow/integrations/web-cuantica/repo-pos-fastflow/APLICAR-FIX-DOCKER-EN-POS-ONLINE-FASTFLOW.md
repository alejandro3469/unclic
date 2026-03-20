# Aplicar el fix de deploy Docker en el repo pos-online-fastflow (Jenkins)

El job de Jenkins **pos-online-fastflow** clona desde `https://gitea.unclic.consulting/alejandro-perez/pos-online-fastflow`.  
El fix (deploy con Docker cuando existe la imagen) está en este repo; si el push a **pos-online-fastflow** falla por **HTTP 413** (límite de tamaño en Nginx/Gitea), aplica el cambio así:

## Opción A: Aplicar el parche en un clon de pos-online-fastflow

```bash
# 1. Clonar solo el repo que usa Jenkins (historia mínima)
git clone --depth 1 https://gitea.unclic.consulting/alejandro-perez/pos-online-fastflow.git /tmp/pos-online-fastflow
cd /tmp/pos-online-fastflow

# 2. Copiar el Jenkinsfile (y docs) desde este repo
cp /ruta/a/repo-pos-fastflow/Jenkinsfile .
cp /ruta/a/repo-pos-fastflow/README.md .
cp /ruta/a/repo-pos-fastflow/ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md .

# 3. Commit y push (solo 3 archivos, poco peso)
git add Jenkinsfile README.md ELEMENTOS-Y-PIPELINE-DEPLOY-OTRO-SERVIDOR.md
git commit -m "fix: deploy con Docker cuando existe imagen (docker images -q); doc Docker/registry"
git push origin main
```

Sustituye `/ruta/a/repo-pos-fastflow` por la ruta real, por ejemplo:
`/Users/wallfacer/Downloads/pipeline-as-code-with-jenkins-master/toolkit-fastflow/integrations/web-cuantica/repo-pos-fastflow`.

## Opción B: Subir el límite de Nginx en el servidor Gitea

En la EC2 donde corre Gitea (o el proxy), en la config de Nginx que sirve a Gitea añade o aumenta:

```nginx
client_max_body_size 100m;
```

Luego recarga Nginx y vuelve a intentar desde este repo:

```bash
cd repo-pos-fastflow
git push fastflow main --force-with-lease
```

(Requiere tener el remote `fastflow` apuntando a pos-online-fastflow.)

## Después de actualizar pos-online-fastflow

En Jenkins, lanza **Build Now**. El próximo build hará checkout del commit nuevo y deberías ver:

- **Approve Deploy:** "Imagen Docker lista (y en registry si REGISTRY definido). ¿Desplegar contenedor en puerto 8111?"
- **Cleanup:** `docker stop pos-online; docker rm pos-online`
- **Deploy:** `docker run -d --name pos-online -p 8111:8111 pos-online:<TAG>`

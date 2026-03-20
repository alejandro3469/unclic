# Levantar Registry gratis en la EC2 (FastFlow)

Para que el pipeline **pos-online** haga **push de la imagen** a un registry (etiquetas, rollback, FastFlow), puedes levantar un **Docker Registry** abierto (registry:2) en la misma EC2 de Jenkins, sin coste extra.

---

## 1. Requisitos

- **Docker** instalado en la EC2 (donde corre Jenkins).
- **Puerto 5000** libre (o otro que elijas).

---

## 2. Levantar el registry en la EC2

Conéctate a la EC2 por SSH (o EC2 Instance Connect) y ejecuta:

```bash
sudo docker run -d -p 5000:5000 --restart=always --name registry registry:2
```

Comprueba que esté corriendo:

```bash
sudo docker ps | grep registry
curl -s http://localhost:5000/v2/_catalog
```

Deberías ver `{"repositories":[]}` (vacío al inicio).

---

## 3. Abrir el puerto 5000 en el Security Group

Para acceder al registry desde fuera (opcional; para push desde Jenkins en la misma máquina no hace falta):

- **AWS Console** → EC2 → **Security Groups** → el de tu instancia Jenkins.
- **Edit inbound rules** → **Add rule**: Custom TCP, port **5000**, Source 0.0.0.0/0 (o tu IP).
- **Save**.

Desde fuera: `curl http://<IP_PUBLICA_EC2>:5000/v2/_catalog`.

---

## 4. Registry HTTP (inseguro) en la misma EC2

Si Jenkins y el registry están en la **misma EC2**, el job hará `docker push localhost:5000/pos-online:...`. Por defecto Docker exige HTTPS para push; para un registry sin TLS hay que marcar el registro como inseguro.

En la EC2:

```bash
sudo mkdir -p /etc/docker
echo '{ "insecure-registries": ["localhost:5000", "127.0.0.1:5000"] }' | sudo tee /etc/docker/daemon.json
sudo systemctl restart docker
sudo docker start registry || sudo docker run -d -p 5000:5000 --restart=always --name registry registry:2
```

(Si ya tenías `daemon.json`, añade solo `"insecure-registries": ["localhost:5000"]` al JSON y reinicia Docker.)

---

## 5. Variable REGISTRY en Jenkins

Para que el stage **Push to registry** del Jenkinsfile haga push:

1. Jenkins → job **pos-online-pipeline** → **Configure**.
2. En **Pipeline**, busca **Environment** o la sección donde se definen variables (a veces está en "Pipeline" o en "Build Environment").
3. Añade una variable:
   - **Name:** `REGISTRY`
   - **Value:** `localhost:5000` (registry en la misma EC2) o `IP_EC2:5000` (si el agente Jenkins usa otra máquina).
4. **Save**.

En el siguiente **Build Now**, si el stage "Build image" termina bien, el stage "Push to registry" hará `docker push localhost:5000/pos-online:<tag>` y `docker push localhost:5000/pos-online:latest`.

---

## 6. Comprobar que hay imágenes

Tras un build exitoso con push:

```bash
curl -s http://localhost:5000/v2/_catalog
curl -s http://localhost:5000/v2/pos-online/tags/list
```

O desde fuera: `http://<IP_EC2>:5000/v2/_catalog` y `http://<IP_EC2>:5000/v2/pos-online/tags/list`.

---

## 7. Resumen

| Paso | Acción |
|------|--------|
| 1 | En la EC2: `sudo docker run -d -p 5000:5000 --restart=always --name registry registry:2` |
| 2 | Security Group: abrir TCP 5000 (si quieres acceso externo) |
| 3 | En la EC2: configurar `insecure-registries` en `/etc/docker/daemon.json` si usas localhost:5000 sin HTTPS |
| 4 | En Jenkins: variable de entorno `REGISTRY=localhost:5000` en el job |
| 5 | Build Now; revisar "Push to registry" en Console Output; comprobar con `curl .../v2/_catalog` |

Referencias: [QUE-FALTA-PROBAR-JENKINS-UNCLIC.md](QUE-FALTA-PROBAR-JENKINS-UNCLIC.md), [repo-pos-fastflow/AUDITORIA-JENKINS-REGISTRY.md](repo-pos-fastflow/AUDITORIA-JENKINS-REGISTRY.md).

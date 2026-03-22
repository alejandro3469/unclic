# pos-online (FastFlow): instalar Docker en la EC2 de Jenkins

Cuando el pipeline **pos-online-pipeline** falla en el stage **Build image** con:

```text
docker: command not found
```

es porque **Docker no está instalado** (o no está en el PATH) en el nodo donde corre Jenkins — normalmente la misma EC2 del controller (**fastflow-jenkins-controller**, ej. 18.119.157.22).

Sigue estos pasos **en la EC2 de Jenkins** (SSH o EC2 Instance Connect).

---

## 1. Detectar el sistema (Amazon Linux 2 vs 2023)

```bash
cat /etc/os-release
# Amazon Linux 2: PRETTY_NAME="Amazon Linux 2"
# Amazon Linux 2023: PRETTY_NAME="Amazon Linux 2023"
```

O por kernel: si `uname -r` contiene **amzn2** es Amazon Linux 2; si contiene **amzn2023** (o similar) es Amazon Linux 2023.

---

## 2. Instalar Docker

**Amazon Linux 2** (yum):

```bash
sudo yum update -y
sudo yum install -y docker
sudo systemctl enable docker
sudo systemctl start docker
sudo systemctl status docker   # debe estar active (running)
```

**Amazon Linux 2023** (dnf):

```bash
sudo dnf update -y
sudo dnf install -y docker
sudo systemctl enable docker
sudo systemctl start docker
sudo systemctl status docker   # debe estar active (running)
```

---

## 3. Permitir que Jenkins use Docker

El pipeline corre con el usuario **jenkins**. Ese usuario debe poder ejecutar `docker` sin sudo:

```bash
# Añadir el usuario jenkins al grupo docker
sudo usermod -aG docker jenkins

# Comprobar que jenkins está en el grupo
groups jenkins
# Debe incluir "docker"
```

**Reiniciar Jenkins** para que cargue el nuevo grupo:

```bash
sudo systemctl restart jenkins
# o, si Jenkins se arranca de otra forma:
# sudo service jenkins restart
```

Tras 1–2 minutos, en la UI de Jenkins (https://jenkins.unclic.consulting o :8080) verifica que Jenkins esté arriba. Luego lanza de nuevo el job **pos-online-pipeline** → Build Now.

---

## 4. Permitir que ec2-user use Docker (opcional)

Si te conectas por SSH como **ec2-user** y al ejecutar `docker ps` ves *permission denied while trying to connect to the Docker daemon socket*, es porque solo el usuario **jenkins** está en el grupo `docker`. Para que **ec2-user** también pueda usar `docker` sin sudo:

```bash
sudo usermod -aG docker ec2-user
```

Para que el cambio tenga efecto en la sesión actual, cierra sesión y vuelve a entrar por SSH, o ejecuta:

```bash
newgrp docker
```

Después, `docker ps` (y el resto de comandos `docker`) funcionará sin sudo para ec2-user.

---

## 5. Comprobar desde la EC2 (opcional)

Conectar por SSH y comprobar que el usuario con el que corre Jenkins puede usar docker:

```bash
# Como usuario jenkins (o simular)
sudo su - jenkins -s /bin/bash -c "docker run --rm hello-world"
```

Si ves el mensaje "Hello from Docker!", el siguiente build del pipeline debería pasar el stage **Build image**.

---

## 6. Resumen

| Paso | Acción |
|------|--------|
| 1 | Entrar en la EC2 **fastflow-jenkins-controller** (18.119.157.22) |
| 2 | Instalar Docker (`yum` en AL2, `dnf` en AL2023) y arrancar el servicio |
| 3 | `sudo usermod -aG docker jenkins` |
| 4 | `sudo systemctl restart jenkins` |
| 5 | (Opcional) `sudo usermod -aG docker ec2-user` y `newgrp docker` o reentrar por SSH, para usar `docker` como ec2-user |
| 6 | En Jenkins: **pos-online-pipeline** → Build Now |

Si no quieres instalar Docker por ahora, el **Jenkinsfile** puede estar configurado para **omitir** el stage "Build image" cuando el comando `docker` no existe; así el pipeline igual hace Build → Test → Package → Deploy → Verify (solo se salta la imagen Docker). Ver [RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md](RESUMEN-INSTANCIAS-SSL-Y-PIPELINE-UNCLIC.md) §2.

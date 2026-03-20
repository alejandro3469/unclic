# Qué sigue después del `terraform apply`

Cuando el apply termina, Terraform muestra los **outputs**. Con ellos puedes acceder a Jenkins (una vez instalado en la instancia).

---

## 1. Anotar la IP de Jenkins

En tu sesión quedó:

- **jenkins_public_ip** = `3.15.4.160`
- **jenkins_lb_dns** = `10.0.1.62` (IP privada)
- **vpc_id** = `vpc-06bab4c9b376d64c0`

URL de acceso (cuando Jenkins esté instalado y arrancado):

```text
http://3.15.4.160:8080
```

---

## 2. Comprobar que la instancia responde

La AMI es **Amazon Linux 2** sin Jenkins. Al abrir `http://3.15.4.160:8080` verás error de conexión o “connection refused” hasta que instales y arranques Jenkins.

---

## 3. Entrar en la instancia por SSH (EC2 Instance Connect)

La instancia **no tiene key pair** asignado en Terraform. La forma más rápida de entrar es **EC2 Instance Connect** desde la consola AWS:

1. **AWS Console** → **EC2** → **Instances**.
2. Seleccionar la instancia **fastflow-jenkins-controller**.
3. **Connect** → pestaña **EC2 Instance Connect** (deja el usuario `ec2-user` y la key que propone) → **Connect**.

Se abre una sesión en el navegador. Si prefieres terminal local, copia el comando SSH que muestra la consola (usa la key temporal de EC2 Instance Connect).

---

## 4. Instalar Java 17 y Jenkins en la instancia

**Jenkins 2.463+ (desde junio 2024) exige Java 17 o superior** en el controlador. Ver [Jenkins Java Support](https://www.jenkins.io/doc/book/platform-information/support-policy-java/) y [Require Java 17](https://jenkins.io/blog/2024/06/11/require-java-17). En Amazon Linux 2 usamos **Amazon Corretto 17** ([guía oficial AWS](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/amazon-linux-install.html)).

Dentro de la instancia (como `ec2-user`), en Amazon Linux 2:

```bash
# Repo Corretto (si tu AMI no tiene el paquete: "No package java-17-amazon-corretto-devel")
sudo curl -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo

# Java 17 (requerido por Jenkins 2.463+; el POS puede compilar con Java 11 vía Global Tool Configuration)
sudo yum install -y java-17-amazon-corretto-devel
java -version   # debe mostrar openjdk 17 ... Corretto

# Repo de Jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install -y jenkins

# Si hay varias JVMs: forzar que Jenkins use Java 17 (ruta estándar Corretto 17 en AL2)
echo 'JENKINS_JAVA_CMD=/usr/lib/jvm/java-17-amazon-corretto/bin/java' | sudo tee -a /etc/sysconfig/jenkins

# Arrancar y habilitar
sudo systemctl enable jenkins
sudo systemctl start jenkins
```

Comprobar:

```bash
sudo systemctl status jenkins
```

La contraseña inicial de Jenkins suele estar en:

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

Luego abre en el navegador: **http://3.15.4.160:8080**, pega esa contraseña y completa el asistente de configuración.

---

## 5. (Opcional) Key pair para SSH desde tu Mac

Si quieres SSH desde tu máquina (por ejemplo `ssh -i mi-key.pem ec2-user@3.15.4.160`):

1. Crear un key pair en EC2 (Consola → Key Pairs → Create) y descargar el `.pem`.
2. Añadir la variable `key_name` al módulo compute y a `terraform.tfvars`, y volver a aplicar (la instancia se **recreará**).

Mientras no lo hagas, EC2 Instance Connect sigue siendo la opción más rápida.

---

## 6. Destruir cuando no lo uses (evitar costes)

Desde `envs/dev`:

```bash
terraform destroy -var-file=terraform.tfvars
```

Confirmar con `yes`. Borra la VPC, subnets, instancia Jenkins y ASG.

---

## Resumen rápido

| Paso | Acción |
|------|--------|
| 1 | Anotar `jenkins_public_ip` (ej. 3.15.4.160) |
| 2 | Conectar a la instancia: EC2 → Instances → Connect → EC2 Instance Connect |
| 3 | Dentro: instalar Java 17 (Corretto) + Jenkins (yum), `systemctl start jenkins` |
| 4 | Abrir http://&lt;jenkins_public_ip&gt;:8080 y usar la contraseña inicial |
| 5 | Cuando no lo uses: `terraform destroy -var-file=terraform.tfvars` |

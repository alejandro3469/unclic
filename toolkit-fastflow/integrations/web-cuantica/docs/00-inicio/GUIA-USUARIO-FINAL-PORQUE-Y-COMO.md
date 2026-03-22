# Guía para el usuario final — Por qué y cómo (todo documentado)

> **Si tu objetivo es montar el entorno ya:** ve directo a la **[guía única](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** (índice → pasos). Este documento es **contexto y “por qué”** (cuenta, Terraform, EC2, Jenkins…); **no** sustituye esa guía y **sí** se solapa en varios pasos — léelo solo si necesitas el marco mental o enseñar a otra persona.

Esta guía explica **por qué** hacemos cada cosa y **cómo** hacerla, pensada para quien usa o replica el flujo por primera vez (usuario final, nuevo dueño del repo, o alguien que sigue la documentación sin haberlo montado antes).

---

## 1. Qué es esto y para quién

**Qué es:** Un flujo para tener **una sola instancia en AWS** (Free Tier) donde corre **Jenkins** y, tras un **commit** en el repositorio, el pipeline construye la aplicación (POS) y la **vuelve a levantar** en esa misma máquina. Objetivo: **un commit → todo el flujo → app disponible de nuevo** en `http://<IP>:8111`.

**Para quién:** Usuario final que quiere instalar y probar el POS automatizado sin montar varios entornos; quien recibe el repo y debe replicar el despliegue; quien necesita entender cada paso (por qué y cómo).

**Documentos relacionados:**  
- Lista corta de pasos: [PASO-A-PASO-MINIMO-HOY.md](../20-operaciones/PASO-A-PASO-MINIMO-HOY.md).  
- **Flujo EC2 → Jenkins con outputs reales y porqués:** [FLUJO-EC2-JENKINS-CON-OUTPUTS-Y-PORQUES.md](../20-operaciones/FLUJO-EC2-JENKINS-CON-OUTPUTS-Y-PORQUES.md) (comandos, salidas de terminal y explicación paso a paso para usuario final).  
- Conceptos y diagramas: [Capítulo 3](../90-archivo/CAPITULO-DESPLIEGUE-OPERACION-AWS.md), [Capítulo 4](../90-archivo/CAPITULO-SERVICIOS-CORE-AWS.md), [Capítulo 6](../90-archivo/CAPITULO-FACTURACION-Y-PRECIOS.md).  
- Comprobar si el flujo se cumple: [VERIFICACION-FLUJO-UN-COMMIT-LEVANTA-INSTANCIA.md](../20-operaciones/VERIFICACION-FLUJO-UN-COMMIT-LEVANTA-INSTANCIA.md).

---

## 2. Cuenta AWS y CLI

### Por qué

- Sin una **cuenta AWS** no puedes crear recursos (EC2, VPC, etc.).  
- La **AWS CLI** en tu ordenador permite ejecutar comandos (por ejemplo obtener una AMI o, si usas scripts, desplegar) sin depender solo de la consola web.  
- **Configurar la CLI** (`aws configure`) hace que esos comandos usen tu identidad (usuario IAM) y tu región; así no tienes que poner credenciales en cada comando.

### Cómo

1. Crear una cuenta en [aws.amazon.com](https://aws.amazon.com) (tarjeta necesaria para verificación; el Free Tier evita cargos dentro de los límites).  
2. Crear un **usuario IAM** (no usar el root): Consola AWS → IAM → Users → Create user → asignar política (por ejemplo AdministratorAccess para pruebas) → crear **Access Key** (Access Key ID + Secret Access Key).  
3. En tu Mac (terminal): `brew install awscli` (si no está instalado).  
4. Ejecutar `aws configure` e introducir: Access Key ID, Secret Access Key, región (ej. `us-east-2`), output (ej. `json`).  
5. Comprobar: `aws sts get-caller-identity` debe mostrar tu usuario y cuenta.

**Resultado:** Tu máquina puede hablar con AWS como tu usuario IAM en la región elegida.

---

## 3. Terraform: por qué y cómo

### Por qué

- **Terraform** describe la infraestructura (red, servidor, reglas de firewall) en código. Así se puede repetir el mismo entorno en otra cuenta o región sin volver a hacer clics a mano.  
- El repo incluye módulos para **una VPC**, **subredes**, **una instancia EC2** y **security groups**. Aplicando ese código (“terraform apply”) se crea la máquina donde luego instalarás Jenkins y la app.

### Cómo

1. Instalar Terraform en el Mac: `brew install terraform`.  
2. Ir al directorio del código: `cd toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev` (o la ruta equivalente en tu repo).  
3. Crear tu archivo de variables: `cp terraform.tfvars.free-tier.example terraform.tfvars`.  
4. Obtener un **ID de AMI** válido en tu región (imagen de sistema para la EC2). En us-east-2, por ejemplo:
   ```bash
   aws ec2 describe-images --owners amazon --region us-east-2 \
     --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \
     --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text
   ```
5. Editar `terraform.tfvars`: poner `aws_region` (ej. `"us-east-2"`), `availability_zones` (ej. `["us-east-2a", "us-east-2b"]`) y los IDs de AMI en `jenkins_controller_ami_id` y `jenkins_worker_ami_id`.  
6. Ejecutar: `terraform init` → `terraform validate` → `terraform plan -var-file=terraform.tfvars` → si todo cuadra, `terraform apply -var-file=terraform.tfvars` y confirmar con `yes`.  
7. Anotar el output **jenkins_public_ip** (tu IP pública de la EC2); es la IP para acceder a Jenkins y, más adelante, a la app.

**Resultado:** Existe una EC2 en una VPC, con IP pública y reglas que abren los puertos 22 y 8080 (y 8111 si el módulo ya lo incluye o lo añades). La máquina arranca con solo el sistema operativo (Amazon Linux 2); Jenkins y la app se instalan después a mano o por pipeline.

**Importante:** No subas `terraform.tfvars` a Git (contiene datos de tu entorno). Para destruir todo cuando no lo uses: `terraform destroy -var-file=terraform.tfvars`.

---

## 4. Conectar a la EC2: por qué y cómo

### Por qué

- La EC2 creada por Terraform **no trae Jenkins ni Maven**; hay que instalarlos dentro de la máquina.  
- Para ejecutar comandos dentro de la EC2 necesitas una **sesión** (terminal). Como la instancia se lanzó **sin key pair**, no puedes usar SSH desde tu Mac con un .pem; la opción más simple es **EC2 Instance Connect**, que abre una terminal en el navegador sin configurar claves.

### Cómo

1. AWS Console → **EC2** → **Instances**.  
2. Seleccionar la instancia **fastflow-jenkins-controller**.  
3. Pulsar **Connect**.  
4. En la pestaña **EC2 Instance Connect**, dejar el usuario **ec2-user** y pulsar **Connect**.  
5. Se abre una nueva pestaña con una terminal (prompt tipo `[ec2-user@ip-10-0-1-62 ~]$`). Ahí ejecutarás los comandos de instalación.

**Resultado:** Tienes una terminal “dentro” de la instancia para instalar Java, Jenkins y Maven.

---

## 5. Instalar Java (17 para Jenkins, 11 opcional para la app): por qué y cómo

### Por qué

- **Jenkins 2.541+** exige **Java 17** como mínimo (versiones soportadas: 17, 21, 25). Con Java 11 el servicio no arranca y en el log aparece: “older than the minimum required version (Java 17)”.  
- La aplicación **POS** (pos-online) puede compilarse con **Java 11 o 17**; si quieres compilar con 11, en Jenkins puedes configurar un JDK 11 como herramienta y usarlo en el job. Para el servidor Jenkins hay que usar **Java 17**.  
- En **Amazon Linux 2** los paquetes Corretto **no están en los repos por defecto**; hay que **añadir el repositorio de Amazon Corretto** antes de instalar. Usamos **Amazon Corretto** como distribución OpenJDK sin coste, con soporte a largo plazo y certificada Java SE; Amazon la usa en miles de servicios en producción ([AWS Corretto](https://aws.amazon.com/corretto/)).

### Cómo

En la terminal de EC2 Instance Connect:

1. Añadir clave y repo de Corretto (si no lo hiciste antes):
   ```bash
   sudo rpm --import https://yum.corretto.aws/corretto.key
   sudo curl -L -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo
   ```
2. Instalar **Java 17** (para Jenkins):
   ```bash
   sudo yum install -y java-17-amazon-corretto-devel
   ```
3. Configurar Jenkins para usar Java 17 (el servicio lee `/etc/sysconfig/jenkins`):
   ```bash
   echo 'JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto' | sudo tee /etc/sysconfig/jenkins
   ```
   Si la ruta es distinta (p. ej. `java-17-amazon-corretto.x86_64`), compruébalo con `ls /usr/lib/jvm/` y usa esa en `JAVA_HOME`.
4. Comprobar: `java -version` (como ec2-user puede seguir mostrando 11 si tienes ambos; lo importante es que Jenkins use 17 vía `JAVA_HOME`).

**Opcional:** Para compilar la app POS con Java 11, instala también `java-11-amazon-corretto-devel` y configura en Jenkins un “JDK” apuntando a esa instalación.

**Resultado:** Jenkins arranca con Java 17; la instancia puede tener además Java 11 para el build de la app si lo configuras en el job. La decisión de usar Java 17 para la plataforma (Jenkins) está alineada con la tendencia a cloud-native y con el soporte a largo plazo del ecosistema Java; el informe *Java in the Era of AI & Cloud-Native Innovation* (VDC Research, 2025) señala a Java como lenguaje #1 en desarrollo cloud-native ([Oracle Java](https://www.oracle.com/java/), acceso al informe VDC).

---

## 6. Instalar Jenkins: por qué y cómo

### Por qué

- **Jenkins** es el servidor que ejecuta el **pipeline** (build, tests, package, deploy) cuando detecta un commit en el repositorio. Sin Jenkins no hay automatización “un commit → app levantada”.

### Cómo

En la misma terminal de la EC2:

1. Añadir el repo oficial de Jenkins para Red Hat/CentOS/Amazon Linux:
   ```bash
   sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
   sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
   ```
2. Instalar: `sudo yum install -y jenkins`  
3. Activar e arrancar el servicio:
   ```bash
   sudo systemctl enable jenkins
   sudo systemctl start jenkins
   ```
4. Comprobar: `sudo systemctl status jenkins` debe mostrar **active (running)**.  
5. Contraseña inicial: `sudo cat /var/lib/jenkins/secrets/initialAdminPassword`  
6. En el navegador abrir **http://&lt;jenkins_public_ip&gt;:8080** (la IP que anotaste), pegar la contraseña en la pantalla **“Unlock Jenkins”** y completar el asistente (plugins, usuario admin).

Para ver **qué pantalla verás** y un **ejemplo de salida** del comando (estilo Jenkins/terminal), ver [JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md](../20-operaciones/JENKINS-UNLOCK-PANTALLA-Y-OUTPUT.md). Para el **flujo completo en la EC2 con outputs reales** (Java, JAVA_HOME, systemctl, contraseña inicial) y porqués, ver [FLUJO-EC2-JENKINS-CON-OUTPUTS-Y-PORQUES.md](../20-operaciones/FLUJO-EC2-JENKINS-CON-OUTPUTS-Y-PORQUES.md).

**Resultado:** Jenkins está instalado y accesible por web; puedes crear jobs y configurar el pipeline.

---

## 7. Instalar Maven: por qué y cómo

### Por qué

- El pipeline del POS usa **Maven** para compilar, ejecutar tests y empaquetar (`mvn clean compile`, `mvn test`, `mvn package`). Si Maven no está instalado en la EC2, el job de Jenkins falla en las etapas de Build o Package.

### Cómo

En la misma terminal de la EC2:

```bash
sudo yum install -y maven
```

Comprobar: `mvn -version` debe mostrar la versión de Maven.

**Resultado:** El agente de Jenkins (la propia EC2) puede ejecutar Maven cuando corra el pipeline.

---

## 8. Abrir el puerto 8111: por qué y cómo

### Por qué

- La aplicación **POS** escucha por defecto en el **puerto 8111**. Aunque el pipeline la levante dentro de la EC2, para acceder desde tu navegador necesitas que el **security group** de la instancia permita tráfico entrante en el puerto 8111; si no, el firewall de AWS bloquea la conexión.

### Cómo

1. AWS Console → **EC2** → **Security Groups**.  
2. Buscar el security group de la instancia Jenkins (ej. **fastflow-jenkins-sg**).  
3. Seleccionarlo → **Edit inbound rules** → **Add rule**.  
4. Tipo: **Custom TCP**. Puerto: **8111**. Origen: **0.0.0.0/0** (o restringir a tu IP si prefieres).  
5. **Save rules**.

**Resultado:** Cuando la app esté levantada en la EC2, podrás abrir `http://<jenkins_public_ip>:8111` desde tu navegador.

---

## 9. Crear el job en Jenkins (Pipeline from SCM): por qué y cómo

### Por qué

- Jenkins necesita un **job** que diga “usa este repositorio y este Jenkinsfile”. Sin job configurado, un commit no dispara nada.  
- **Pipeline from SCM** hace que Jenkins clone el repo y ejecute el **Jenkinsfile** que está en la raíz (o donde indiques); así el pipeline (Build, Test, Package, Deploy) está versionado en el propio código.

### Cómo

1. En Jenkins (**http://<IP>:8080**) → **New Item**.  
2. Nombre (ej. `pos-online-pipeline`) → tipo **Pipeline** → OK.  
3. En la configuración del job:  
   - **Pipeline** → Definition: **Pipeline script from SCM**.  
   - **SCM:** Git.  
   - **Repository URL:** la URL del repo que tenga el **Jenkinsfile** y el código del POS (p. ej. el repo pos-online). Debe ser accesible por Jenkins (público o con credenciales en Jenkins).  
   - **Branch:** `main` o la rama que uses.  
   - **Script Path:** `Jenkinsfile` (si está en la raíz).  
4. Guardar.

**Resultado:** El job existe; al hacer **Build Now** o al activar un trigger (poll/webhook), Jenkins clonará el repo y ejecutará el Jenkinsfile. Si el repo no tiene la dependencia **generic-model** resuelta (submódulo o carpeta), el build puede fallar en compilación; ver siguiente sección.

---

## 10. generic-model (dependencia del POS): por qué y cómo

### Por qué

- El proyecto **pos-online** depende del proyecto **generic-model** para compilar. Si Jenkins solo clona pos-online y generic-model no está (ni como submódulo ni en el mismo workspace), Maven falla con errores de dependencia.

### Cómo (opciones mínimas)

- **Opción A:** El repo que usa el job tiene **generic-model como submódulo Git**. En el job, en la sección Pipeline/SCM, en **Advanced** (o “Additional Behaviours”), activar la opción para actualizar submódulos de forma recursiva, para que al hacer checkout se descargue también generic-model.  
- **Opción B:** El repo que clonas ya incluye generic-model en una subcarpeta o como copia; entonces no hace falta configurar nada más.  
- **Opción C:** Dos repos distintos: configurar el job o el Jenkinsfile para hacer checkout de ambos en carpetas que Maven pueda usar (por ejemplo ajustando rutas o módulos). Para el mínimo, A o B suelen ser más simples.

**Resultado:** Al ejecutar el pipeline, la etapa de compilación encuentra generic-model y el build puede continuar.

---

## 11. Trigger del job (Poll SCM): por qué y cómo

### Por qué

- Para que **un commit** dispare el pipeline hace falta un **trigger**. **Poll SCM** hace que Jenkins consulte el repositorio cada X tiempo (ej. cada 2 minutos); si detecta cambios, lanza un build. Otra opción es un **webhook** desde Git hacia Jenkins (más inmediato, pero requiere configurar el repo y posiblemente la red).

### Cómo

1. En la configuración del job → **Build Triggers**.  
2. Marcar **Poll SCM**.  
3. En **Schedule** poner por ejemplo `H/2 * * * *` (cada 2 minutos) o `* * * * *` (cada minuto) para pruebas.  
4. Guardar.

**Resultado:** Cuando hagas push al repo en la rama configurada, en los próximos minutos Jenkins detectará el cambio y ejecutará el pipeline (o puedes seguir usando **Build Now** para probar a mano).

---

## 12. Deploy real en el Jenkinsfile: por qué y cómo

### Por qué

- El **Jenkinsfile** de ejemplo tiene un stage **Deploy** que solo ejecuta `sh 'echo "Deploy (main)"'`; no arranca ningún proceso. Para que “la instancia (app) quede levantada de nuevo” hace falta que el Deploy **ejecute de verdad** la aplicación en la EC2 (por ejemplo el JAR generado en `target/` o un contenedor Docker).

### Cómo (opción mínima con JAR)

En el repo, editar el **Jenkinsfile** y sustituir el stage Deploy por algo que:

1. Opcionalmente mate el proceso anterior del JAR (para no dejar varias instancias colgadas).  
2. Arranque el JAR de Spring Boot en segundo plano en el puerto 8111.

Ejemplo de pasos dentro del stage Deploy (rama main):

```groovy
stage('Deploy') {
    when { branch 'main' }
    steps {
        sh '''
            pkill -f "pos-online.*jar" || true
            sleep 2
            nohup java -jar target/pos-online-*.jar --server.port=8111 > /tmp/pos-online.log 2>&1 &
            sleep 3
        '''
    }
}
```

(Ajustar el nombre del JAR si tu `pom.xml` genera otro artefacto.) Hacer commit y push del Jenkinsfile. Tras un build exitoso que pase por este stage, la app quedará escuchando en el puerto 8111.

**Resultado:** Un commit que pase por Build, Test, Package y Deploy deja la aplicación POS corriendo en la EC2; puedes abrir `http://<IP>:8111` (con el puerto 8111 abierto en el security group).

---

## 13. Resumen: orden y documentos de referencia

| Orden | Qué hacer | Por qué | Dónde está el “cómo” |
|-------|-----------|--------|----------------------|
| 1 | Cuenta AWS + usuario IAM + CLI | Poder crear y gestionar recursos en AWS | Sección 2 de esta guía; Cap. 3 |
| 2 | Terraform: init, plan, apply | Crear la EC2 y la red de forma reproducible | Sección 3; INSTRUCCIONES-DEPLOY-AWS-GRATIS (jenkins-aws) |
| 3 | Conectar a la EC2 (EC2 Instance Connect) | Entrar en la instancia para instalar software | Sección 4; PASO-A-PASO-MINIMO-HOY Paso 1 |
| 4 | Instalar Java 17 (repo Corretto) y JAVA_HOME para Jenkins | Jenkins 2.x requiere Java 17 mínimo | Sección 5 |
| 5 | Instalar Jenkins y Maven; arrancar Jenkins | Pipeline necesita Jenkins y Maven en la EC2 | Secciones 6 y 7; PASO-A-PASO Paso 2 |
| 6 | Primer acceso a Jenkins (contraseña inicial, asistente) | Configurar Jenkins y crear usuario | PASO-A-PASO Paso 3 |
| 7 | Abrir puerto 8111 en el security group | Poder acceder a la app en :8111 | Sección 8; PASO-A-PASO Paso 4 |
| 8 | Crear job Pipeline from SCM (repo + Jenkinsfile) | Que Jenkins ejecute el pipeline al detectar commits | Secciones 9 y 10; PASO-A-PASO Pasos 5 y 6 |
| 9 | Activar Poll SCM en el job | Que un commit dispare el build | Sección 11; PASO-A-PASO Paso 7 |
| 10 | Deploy real en el Jenkinsfile (java -jar) | Que la app quede levantada tras el pipeline | Sección 12; PASO-A-PASO Paso 8 |

**Documentos para el usuario final:**

- **Esta guía:** por qué y cómo de cada paso.  
- **PASO-A-PASO-MINIMO-HOY.md:** lista numerada de pasos con resultado esperado.  
- **VERIFICACION-FLUJO-UN-COMMIT-LEVANTA-INSTANCIA.md:** comprobar qué falta para que el flujo se cumpla.  
- **Capítulos 3, 4 y 6:** conceptos AWS, servicios usados, facturación y Free Tier.  
- **QUE-SIGUE-DESPUES-DEL-APPLY.md** (en jenkins-aws): qué hacer tras el apply de Terraform (incluye instalación en la EC2; en Amazon Linux 2 puede ser necesario añadir el repo de Corretto para Java 11 como en la sección 5 de esta guía).

---

## 14. Problemas frecuentes y qué hacer

| Problema | Causa habitual | Qué hacer |
|----------|----------------|-----------|
| `No package java-11-amazon-corretto-devel available` | El repo de Corretto no está añadido en la EC2. | Añadir clave y repo de Corretto (comandos en sección 5) y luego instalar de nuevo. |
| `terraform: command not found` | Terraform no instalado en el Mac. | `brew install terraform`. |
| `Running with Java 11 ... older than the minimum required version (Java 17)` | Jenkins 2.541+ requiere Java 17. | Instalar Java 17: `sudo yum install -y java-17-amazon-corretto-devel` (con repo Corretto). Poner `JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto` en `/etc/sysconfig/jenkins`, luego `sudo systemctl reset-failed jenkins` y `sudo systemctl start jenkins`. |
| `Job for jenkins.service failed` (status=1/FAILURE) | Jenkins no encuentra Java o la versión es &lt; 17 (falta JAVA_HOME o Java 17). | Ver fila anterior (Java 17). Si ya tienes Java 17: `echo 'JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto' | sudo tee /etc/sysconfig/jenkins`, luego `sudo systemctl reset-failed jenkins` y `sudo systemctl start jenkins`. |
| Jenkins no arranca o no responde en :8080 | Servicio no iniciado, JAVA_HOME no configurado, o firewall. | Ver fila anterior (JAVA_HOME). Si ya está active: `sudo systemctl status jenkins`; si no, `sudo systemctl start jenkins`. Revisar que el security group permita el puerto 8080. Ver logs: `sudo journalctl -u jenkins -n 60 --no-pager`. |
| Build falla con “generic-model not found” | El workspace no tiene la dependencia. | Resolver generic-model (submódulo o repo que lo incluya) como en la sección 10. |
| No se puede abrir http://IP:8111 | Puerto 8111 no abierto en el security group o la app no está levantada. | Añadir regla de entrada TCP 8111 (sección 8). Comprobar que el stage Deploy del Jenkinsfile arranca el JAR (sección 12). |
| “Un commit no dispara el pipeline” | No hay trigger (Poll SCM o webhook). | Activar Poll SCM en el job (sección 11). |

---

## 15. Referencias y fuentes

Las decisiones sobre versiones de Java y la elección de la distribución JDK se apoyan en documentación y ofertas oficiales:

| Tema | Fuente | URL | Cita / uso en esta guía |
|------|--------|-----|-------------------------|
| **Java como plataforma enterprise y cloud-native** | Oracle Java | [https://www.oracle.com/java/](https://www.oracle.com/java/) | Oracle Java como plataforma #1; enlace al informe VDC *Java in the Era of AI & Cloud-Native Innovation*. |
| **Informe Java + cloud + IA** | VDC Research (acceso vía Oracle) | Enlace desde [Oracle Java](https://www.oracle.com/java/) (“Java in the Era of AI & Cloud Native Innovation”) | Java #1 en desarrollo cloud-native; fundamento para usar una versión LTS actual (17) en la plataforma. |
| **Amazon Corretto (OpenJDK sin coste, LTS)** | AWS | [https://aws.amazon.com/corretto/](https://aws.amazon.com/corretto/) | Distribución usada en esta guía para Java 17 (Jenkins) y opcionalmente Java 11 (build POS). Corretto: no coste, multiplataforma, listo para producción, soporte a largo plazo; Amazon lo ejecuta en miles de servicios. Descargas e instalación: Corretto 25, 21, 17, 11, 8. |

*Documento: guía para usuario final con por qué y cómo de cada paso, alineada a PASO-A-PASO-MINIMO-HOY y a los capítulos de documentación.*

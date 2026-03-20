# Explicación paso a paso — como si fuéramos niños

Este documento explica **por qué** hacemos cada cosa, **qué** hacer, **cómo** hacerlo, **dónde** hacerlo y **qué resultado exacto** obtienes. Todo con palabras sencillas.

---

# 1. ¿Por qué queremos una cuenta en AWS?

**Por qué:** AWS es como un parque enorme donde Amazon te deja “montar” ordenadores en la nube. Sin una cuenta, no puedes entrar al parque.  
**Qué hacer:** Crear una cuenta en AWS y un usuario (por ejemplo *alejandro-perez*) con permiso para usar EC2, VPC, etc.  
**Dónde:** En la web de AWS (console.aws.amazon.com) → crear cuenta; luego en IAM crear el usuario y darle una “llave” (Access Key + Secret).  
**Resultado exacto:** Tienes un nombre de usuario y una llave secreta. Con eso puedes decirle a AWS “soy yo, déjame crear cosas”.  
**Otra vez por qué:** Porque si no demuestras quién eres, AWS no te deja tocar nada; la llave es como tu carnet del parque.

---

# 2. ¿Por qué instalamos la AWS CLI en el Mac?

**Por qué:** La consola web de AWS está bien para mirar, pero para crear muchas cosas a la vez es más fácil usar órdenes por escrito (comandos). La AWS CLI es un programa que traduce esos comandos en peticiones a AWS.  
**Qué hacer:** Instalar la AWS CLI en tu Mac.  
**Cómo:** En la terminal escribes `brew install awscli` (sin nada más en la línea).  
**Dónde:** En la Terminal de tu Mac, en cualquier carpeta.  
**Resultado exacto:** Si escribes `aws --version` te sale un número de versión. Eso significa que ya puedes usar comandos como `aws ec2 ...` o `aws sts ...`.  
**Otra vez por qué:** Porque más adelante vamos a pedir una “foto” de un sistema operativo (AMI) y a crear una máquina; eso se hace con comandos `aws`, no solo con clics.

---

# 3. ¿Por qué hacemos “aws configure”?

**Por qué:** Aunque tengas la AWS CLI instalada, el programa no sabe **quién eres** en AWS. Tienes que decirle tu llave (Access Key y Secret) y en qué “sede” quieres trabajar (la región).  
**Qué hacer:** Configurar la CLI con tu identidad y tu región.  
**Cómo:** En la terminal escribes `aws configure`. Te preguntará: Access Key ID → pegas el tuyo; Secret Access Key → pegas el tuyo; región → por ejemplo `us-east-2`; formato de salida → `json`.  
**Dónde:** En la Terminal del Mac.  
**Resultado exacto:** La próxima vez que uses cualquier comando `aws`, la CLI usará esa identidad y esa región sin preguntarte de nuevo.  
**Otra vez por qué:** Porque cada comando que lancemos (crear máquina, listar imágenes, etc.) tiene que ir firmado con tu llave; si no configuras, cada vez fallaría “no sé quién eres”.

---

# 4. ¿Por qué ejecutamos “aws sts get-caller-identity”?

**Por qué:** Para comprobar que la configuración está bien: que la CLI sabe quién eres y que AWS te reconoce.  
**Qué hacer:** Ejecutar un comando que solo “pregunta” a AWS “¿quién soy?”.  
**Cómo:** `aws sts get-caller-identity`  
**Dónde:** En la Terminal del Mac.  
**Resultado exacto:** AWS te devuelve un texto con tu *UserId*, tu *Account* (número de cuenta) y tu *Arn* (algo como `arn:aws:iam::477010601377:user/alejandro-perez`). Si ves tu usuario y tu cuenta, todo está bien.  
**Otra vez por qué:** Así nos aseguramos de que no estamos usando la llave de otro ni una región equivocada antes de crear cosas que cuestan dinero o tiempo.

---

# 5. ¿Por qué vamos a la carpeta “envs/dev” del Terraform?

**Por qué:** Todo el “dibujo” de lo que queremos en AWS (una red, una máquina, un grupo de máquinas) está escrito en archivos de Terraform. Esos archivos están en una carpeta concreta: `envs/dev`. Si no estás en esa carpeta, Terraform no encuentra los archivos y no puede hacer nada.  
**Qué hacer:** Ir con la terminal a esa carpeta.  
**Cómo:** `cd toolkit-fastflow/manifests/terraform/jenkins-aws` y luego `cd envs/dev` (o todo en uno si ya sabes la ruta completa).  
**Dónde:** En la Terminal, desde tu carpeta de usuario o desde donde tengas el repo.  
**Resultado exacto:** El “prompt” de la terminal muestra que estás en `.../jenkins-aws/envs/dev`. A partir de ahí, los comandos `terraform init`, `terraform plan`, etc. leerán los archivos de esa carpeta.  
**Otra vez por qué:** Terraform busca en la carpeta actual el archivo `main.tf` y los módulos; si no estás en `envs/dev`, no los encuentra y da error.

---

# 6. ¿Por qué copiamos “terraform.tfvars.free-tier.example” a “terraform.tfvars”?

**Por qué:** Los archivos `.tf` dicen *qué* queremos (una VPC, una máquina, etc.) pero no guardan *tus* datos (tu región, tu AMI, cuántas máquinas). Esos datos van en un archivo aparte para no mezclarlos con el código y para no subirlos a Git. El archivo de ejemplo tiene la “plantilla” para el Free Tier; al copiarlo a `terraform.tfvars` creas *tu* archivo de datos.  
**Qué hacer:** Copiar el ejemplo a un archivo que Terraform sí lee.  
**Cómo:** `cp terraform.tfvars.free-tier.example terraform.tfvars`  
**Dónde:** Dentro de `envs/dev`, en la terminal.  
**Resultado exacto:** Aparece un archivo nuevo `terraform.tfvars` con los mismos contenidos que el ejemplo (región, tipo de máquina, 0 workers, etc.). Luego lo editamos para poner la región correcta y el ID de la AMI.  
**Otra vez por qué:** Porque si no existe `terraform.tfvars` (o no le pasas `-var-file=terraform.tfvars`), Terraform no sabe qué región usar ni qué AMI, y falla.

---

# 7. ¿Por qué pedimos el ID de una AMI con “aws ec2 describe-images”?

**Por qué:** La AMI es como la “foto” de un disco ya instalado (por ejemplo Linux). AWS no tiene una sola “Linux”; tiene muchas fotos, cada una con un ID distinto y válido solo en una región. Necesitamos el ID de una AMI que exista **en tu región** (por ejemplo us-east-2) para decirle a Terraform “crea la máquina con esta foto”.  
**Qué hacer:** Preguntar a AWS “dame el ID de la última imagen de Amazon Linux 2 en mi región”.  
**Cómo:**  
`aws ec2 describe-images --owners amazon --region us-east-2 \`  
`  --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" \`  
`  --query 'Images | sort_by(@, &CreationDate) | [-1].ImageId' --output text`  
**Dónde:** En la terminal del Mac (la región debe ser la misma que la que pusiste en `aws configure` o la que quieras usar).  
**Resultado exacto:** La terminal imprime una sola línea con algo como `ami-040855b0715ee6f0b`. Ese es el ID que debes pegar en `terraform.tfvars` en `jenkins_controller_ami_id` y `jenkins_worker_ami_id`.  
**Otra vez por qué:** Porque si pones un ID inventado o de otra región, Terraform intentará crear la máquina con una “foto” que no existe ahí y fallará.

---

# 8. ¿Por qué editamos “terraform.tfvars” (región, AZs, AMI)?

**Por qué:** El archivo de ejemplo puede tener otra región (por ejemplo us-east-1) o AMIs de relleno (ami-xxx...). Nosotros queremos usar us-east-2 y la AMI que acabamos de obtener, para que todo coincida con tu cuenta y tu configuración.  
**Qué hacer:** Abrir `terraform.tfvars` y cambiar: `aws_region` a `"us-east-2"`, `availability_zones` a `["us-east-2a", "us-east-2b"]`, y los dos campos de AMI al ID que te dio el comando anterior.  
**Cómo:** Con un editor de texto (Cursor, VS Code, nano, etc.) abres el archivo y cambias esas líneas.  
**Dónde:** El archivo está en `envs/dev/terraform.tfvars`.  
**Resultado exacto:** Cuando ejecutes `terraform plan` o `terraform apply`, Terraform creará la red y la máquina en us-east-2 y con la AMI correcta; si no, podría intentar crear en otra región o con una AMI inválida.  
**Otra vez por qué:** Porque Terraform lee ese archivo para saber *dónde* y *con qué imagen* crear las cosas; si está mal, el plan falla o creas cosas donde no quieres.

---

# 9. ¿Por qué instalamos Terraform (“brew install terraform”)?

**Por qué:** Terraform es el programa que lee nuestros archivos (.tf y .tfvars) y traduce eso en órdenes para AWS (“crea esta VPC”, “crea esta máquina”). Sin Terraform instalado, al escribir `terraform init` la terminal dice “comando no encontrado”.  
**Qué hacer:** Instalar Terraform en el Mac.  
**Cómo:** `brew install terraform`  
**Dónde:** En la terminal del Mac.  
**Resultado exacto:** Si escribes `terraform version` ves un número (por ejemplo 1.5.7). A partir de ahí puedes usar `terraform init`, `terraform plan`, `terraform apply`.  
**Otra vez por qué:** Porque los pasos siguientes son todos de Terraform; sin el programa, no hay forma de ejecutarlos.

---

# 10. ¿Por qué cambiamos “required_version” y la sintaxis de las variables en Terraform?

**Por qué:** El código original pedía Terraform >= 1.6.0 y tenía variables escritas en una sola línea con `type` y `default` juntos. En Terraform 1.5.x eso da error: “Unsupported version” y “Invalid single-argument block”. Para que funcione con la versión que instalamos (1.5.7) y para que el parser entienda bien las variables, bajamos la versión requerida a >= 1.5.0 y separamos `type` y `default` en varias líneas.  
**Qué hacer:** Editar `envs/dev/main.tf` (cambiar `>= 1.6.0` a `>= 1.5.0`) y los archivos de variables en `envs/dev` y en los módulos (autoscaling, compute) para que cada variable con default tenga su bloque en varias líneas.  
**Cómo:** Abrir cada archivo y hacer los cambios indicados (sustituir la línea por un bloque con `type` en una línea y `default` en otra).  
**Dónde:** En el repo, en `envs/dev/main.tf` y en `modules/autoscaling/variables.tf`, `modules/compute/variables.tf`, `envs/dev/variables.tf`.  
**Resultado exacto:** Al ejecutar `terraform init` ya no aparece el error de versión ni el error de “single-argument block”; Terraform puede cargar todos los módulos y variables.  
**Otra vez por qué:** Porque si no arreglamos eso, Terraform se niega a seguir y no podemos hacer init ni plan.

---

# 11. ¿Por qué ejecutamos “terraform init”?

**Por qué:** Terraform necesita descargar el “plugin” de AWS (el programa que sabe hablar con EC2, VPC, etc.) y cargar los módulos (network, compute, autoscaling) que están en carpetas aparte. Eso solo se hace una vez por carpeta; `terraform init` prepara esa carpeta para poder hacer plan y apply.  
**Qué hacer:** Inicializar el directorio de trabajo de Terraform.  
**Cómo:** Dentro de `envs/dev` ejecutas `terraform init`.  
**Dónde:** En la terminal, dentro de `toolkit-fastflow/manifests/terraform/jenkins-aws/envs/dev`.  
**Resultado exacto:** Terraform escribe en la carpeta `.terraform` (donde guarda el provider de AWS) y muestra algo como “Terraform has been successfully initialized!”. A partir de ahí puedes usar `terraform plan` y `terraform apply`.  
**Otra vez por qué:** Porque sin init, Terraform no tiene el plugin de AWS ni los módulos cargados y no puede calcular qué crear en la nube.

---

# 12. ¿Por qué ejecutamos “terraform validate”?

**Por qué:** Para comprobar que los archivos .tf no tienen errores de sintaxis o de lógica (archivos mal escritos, referencias rotas, etc.) antes de hacer un plan. Así nos aseguramos de que el “dibujo” está bien antes de tocar AWS.  
**Qué hacer:** Validar la configuración.  
**Cómo:** `terraform validate`  
**Dónde:** En la misma carpeta `envs/dev`, en la terminal.  
**Resultado exacto:** Si todo está bien, sale “Success! The configuration is valid.” Si hay un error, Terraform indica en qué archivo y línea está el problema.  
**Otra vez por qué:** Porque es más rápido y seguro detectar errores con validate que haciendo un plan que puede fallar a mitad.

---

# 13. ¿Por qué ejecutamos “terraform plan -var-file=terraform.tfvars”?

**Por qué:** El “plan” es como una lista de la compra: Terraform lee nuestros archivos y tus variables y calcula *qué* va a crear en AWS (cuántas VPCs, subnets, máquinas, etc.) sin crearlo todavía. Así vemos si lo que va a pasar es lo que queremos antes de dar el sí definitivo.  
**Qué hacer:** Generar el plan de ejecución.  
**Cómo:** `terraform plan -var-file=terraform.tfvars`  
**Dónde:** En `envs/dev`, en la terminal.  
**Resultado exacto:** Terraform imprime una lista larga de “will be created” (por ejemplo: 1 VPC, 4 subnets, 1 internet gateway, 1 instancia, 1 security group, etc.) y al final “Plan: 14 to add, 0 to change, 0 to destroy”. No se crea nada aún; solo se muestra.  
**Otra vez por qué:** Porque si algo estuviera mal (región, AMI, número de máquinas), lo vemos en el plan y podemos corregir antes del apply; el apply ya crea cosas de verdad y consume recursos.

---

# 14. ¿Por qué ejecutamos “terraform apply -var-file=terraform.tfvars”?

**Por qué:** El apply es el paso que **sí** crea los recursos en AWS: la VPC, las subnets, la máquina Jenkins, los security groups, etc. Hasta este momento todo era solo “dibujo” y “plan”; con apply le decimos a Terraform “hazlo”.  
**Qué hacer:** Aplicar el plan (crear todo en AWS).  
**Cómo:** `terraform apply -var-file=terraform.tfvars`. Terraform vuelve a mostrar el plan y pregunta “Do you want to perform these actions?”; escribes `yes` y pulsas Enter.  
**Dónde:** En `envs/dev`, en la terminal.  
**Resultado exacto:** Terraform va creando cada recurso y muestra líneas como “Creation complete” para cada uno. Al terminar, muestra los “Outputs”: por ejemplo `jenkins_public_ip = "3.15.4.160"`, `vpc_id = "vpc-06bab4c9b376d64c0"`, etc. Esa IP es la que usarás para acceder a Jenkins más adelante.  
**Otra vez por qué:** Porque sin apply, la infraestructura no existe; el apply es el que “enciende” la red y la máquina en AWS.

---

# 15. ¿Por qué la instancia no tiene “key pair” y qué implica?

**Por qué:** En el Terraform no configuramos ninguna variable `key_name` para la instancia. AWS permite crear una máquina sin key pair; así no tienes que crear ni guardar un archivo .pem. La consecuencia es que **no** puedes conectarte por SSH normal desde tu Mac (porque SSH con key necesita ese archivo .pem que no existe).  
**Qué implica:** No puedes usar la pestaña “SSH client” del botón Connect (te dice “No associated key pair”). Sí puedes usar “EC2 Instance Connect”, porque esa opción no usa tu key: AWS inyecta una key temporal y te abre una sesión en el navegador.  
**Dónde importa:** En la consola de EC2, al pulsar Connect: la única opción que funciona sin más configuración es EC2 Instance Connect.  
**Resultado exacto:** Si intentas SSH desde tu Mac, falla. Si usas EC2 Instance Connect en el navegador, se abre una terminal dentro de la máquina y puedes instalar Jenkins desde ahí.  
**Otra vez por qué:** Porque queríamos simplificar el primer deploy (menos pasos); si más adelante quieres SSH desde el Mac, tendrías que crear un key pair y volver a aplicar Terraform (recreando la instancia).

---

# 16. ¿Por qué usamos “EC2 Instance Connect” para entrar a la instancia?

**Por qué:** Porque es la única forma que tenemos sin configurar nada más: no requiere key pair en la instancia ni rol IAM para SSM. AWS te abre una sesión en el navegador usando la IP pública y un usuario (ec2-user); por detrás, AWS pone una key temporal solo para esa conexión.  
**Qué hacer:** En la consola de AWS ir a EC2 → Instances → seleccionar *fastflow-jenkins-controller* → Connect → pestaña *EC2 Instance Connect* → dejar el usuario *ec2-user* → Connect.  
**Dónde:** En la web de AWS (consola), en la región us-east-2.  
**Resultado exacto:** Se abre una ventana del navegador con una terminal conectada a la instancia. Ahí ya eres el usuario `ec2-user` dentro de la máquina Linux y puedes ejecutar comandos (por ejemplo instalar Java y Jenkins).  
**Otra vez por qué:** Porque necesitamos estar “dentro” de la máquina para instalar Jenkins; EC2 Instance Connect es la forma más rápida con lo que tenemos ahora.

---

# 17. ¿Por qué instalamos Java 11 y Jenkins dentro de la instancia?

**Por qué:** La AMI que usamos es solo “Linux” (Amazon Linux 2); no trae Jenkins instalado. Jenkins corre en Java. En **unclic/pos-online** la documentación pide **Java 11** para el servidor Jenkins y para compilar/ejecutar el POS (ver docs/instalacion/REQUISITOS.md e INSTALAR-JENKINS.md). Por eso usamos 11 y no 17: para que el mismo Jenkins que va a construir el POS use la misma versión que el proyecto exige.  
**Qué hacer:** En la terminal que abriste con EC2 Instance Connect, ejecutar los comandos que instalan Java 11 (Amazon Corretto 11) y Jenkins (repo + yum) y luego activar e iniciar el servicio Jenkins.  
**Cómo:**  
`sudo yum install -y java-11-amazon-corretto-devel`  
(luego el repo de Jenkins, rpm --import, yum install jenkins)  
`sudo systemctl enable jenkins && sudo systemctl start jenkins`  
**Dónde:** Dentro de la sesión de EC2 Instance Connect (dentro de la instancia).  
**Resultado exacto:** El servicio Jenkins está corriendo con Java 11. Si ejecutas `sudo systemctl status jenkins` ves “active (running)”. La contraseña inicial está en `/var/lib/jenkins/secrets/initialAdminPassword`; con `sudo cat` la ves para copiarla.  
**Otra vez por qué:** Porque si no instalamos Jenkins, al abrir http://3.15.4.160:8080 no hay nada que responda; y porque unclic en concreto requiere Java 11 para Jenkins y POS, no 17.

---

# 18. ¿Por qué abrimos “http://3.15.4.160:8080” en el navegador?

**Por qué:** 3.15.4.160 es la IP pública de tu instancia Jenkins (la que Terraform te mostró en los outputs). El puerto 8080 es el que Jenkins usa por defecto para la interfaz web. Abriendo esa URL llegas a la pantalla de “desbloqueo” de Jenkins, donde pegas la contraseña inicial.  
**Qué hacer:** En el navegador (Chrome, Safari, etc.) escribir `http://3.15.4.160:8080` y pulsar Enter.  
**Dónde:** En tu Mac (o cualquier ordenador con internet).  
**Resultado exacto:** Ves la página de Jenkins pidiendo “Unlock Jenkins” y un cuadro para pegar la contraseña. Pegas la que obtuviste con `sudo cat /var/lib/jenkins/secrets/initialAdminPassword` y sigues el asistente (instalar plugins, crear usuario admin). Al terminar, ya tienes Jenkins listo para crear jobs y pipelines.  
**Otra vez por qué:** Porque el objetivo de todo este proceso es tener Jenkins accesible por web en esa IP y puerto; hasta que no abres la URL y completas el setup, Jenkins no está “listo para usar”.

---

# 19. ¿Por qué hacemos “terraform destroy” cuando no usamos la infra?

**Por qué:** Mientras la VPC y la instancia existan en AWS, pueden generar coste (aunque sea poco en Free Tier). Si dejas de usar Jenkins y no destruyes, la instancia sigue encendida y la cuenta puede pasar del Free Tier o acumular cargos. Destroy borra todo lo que Terraform creó (VPC, subnets, instancia, security groups, etc.) para que no sigas “gastando”.  
**Qué hacer:** Cuando ya no necesites Jenkins en AWS, ejecutar destroy.  
**Cómo:** Desde `envs/dev` ejecutas `terraform destroy -var-file=terraform.tfvars` y cuando pregunte, escribes `yes`.  
**Dónde:** En la terminal del Mac, dentro de `envs/dev`.  
**Resultado exacto:** Terraform elimina la instancia, los security groups, las subnets, la VPC, etc. Al terminar, en la consola de EC2 ya no verás la instancia fastflow-jenkins-controller ni esa VPC. La IP 3.15.4.160 deja de ser tuya.  
**Otra vez por qué:** Para no pagar por recursos que no usas y para dejar la cuenta limpia; si más adelante quieres volver a tener Jenkins, puedes hacer apply de nuevo y tendrás una nueva IP y una instancia nueva.

---

# Resumen en una tabla (qué, dónde, resultado)

| Paso | Qué hacer | Dónde | Resultado exacto |
|------|-----------|--------|-------------------|
| Cuenta AWS | Crear usuario IAM y llave | Web AWS / IAM | Tienes Access Key y Secret |
| AWS CLI | `brew install awscli` | Terminal Mac | Comando `aws` existe |
| Configurar CLI | `aws configure` | Terminal Mac | Comandos aws usan tu identidad |
| Comprobar identidad | `aws sts get-caller-identity` | Terminal Mac | Ves tu UserId y Account |
| Ir a Terraform | `cd .../envs/dev` | Terminal Mac | Estás en la carpeta correcta |
| Variables | `cp ...terraform.tfvars` y editar | envs/dev | Archivo con tu región y AMI |
| AMI | `aws ec2 describe-images ...` | Terminal Mac | Obtienes un ami-xxxxx |
| Terraform | `brew install terraform` | Terminal Mac | Comando `terraform` existe |
| Ajustes código | Editar main.tf y variables.tf | Repo | init ya no da error |
| Init | `terraform init` | envs/dev | Provider y módulos listos |
| Validate | `terraform validate` | envs/dev | “Configuration is valid” |
| Plan | `terraform plan -var-file=...` | envs/dev | Lista de 14 recursos a crear |
| Apply | `terraform apply -var-file=...` + yes | envs/dev | Recursos creados, IP 3.15.4.160 |
| Conectar | Connect → EC2 Instance Connect | Consola AWS EC2 | Terminal en el navegador |
| Instalar Jenkins | Comandos yum y systemctl | Dentro de la instancia | Jenkins corriendo en 8080 |
| Abrir Jenkins | http://3.15.4.160:8080 | Navegador | Pantalla Unlock + asistente |
| Destroy | `terraform destroy -var-file=...` | envs/dev | Todo borrado, sin coste |

---

*Documento: explicación paso a paso con porqués, qué hacer, cómo, dónde y resultado exacto, en lenguaje sencillo.*

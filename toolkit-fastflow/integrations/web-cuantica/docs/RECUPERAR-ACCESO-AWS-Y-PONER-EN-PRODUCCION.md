# Recuperar acceso a AWS y poner la app en producción (gratis, tu dominio, HTTPS)

Guía para **volver a entrar a tu cuenta AWS**, **levantar de nuevo** las instancias (o crearlas si desaparecieron) y dejar la app **en producción con tu dominio y HTTPS**, accesible en todo el mundo.

---

## 1. No puedo entrar a AWS (cerré sesión / no recuerdo)

### Entrar a la consola de AWS

- **URL:** [https://console.aws.amazon.com/](https://console.aws.amazon.com/)
- **Cuenta:** el email con el que creaste la cuenta (o el alias que hayas puesto).
- **Contraseña:** la que definiste al registrarte. Si no la recuerdas: **Sign in** → **Forgot password** y sigue el flujo con tu email.

Si usabas un **usuario IAM** (no root), la URL suele ser algo como:
`https://TU-CUENTA-ID.signin.aws.amazon.com/console`  
(o desde la página de login eliges “IAM user” y pones cuenta + usuario).

### Volver a usar AWS desde terminal (Terraform / CLI)

Si ya no tienes las credenciales en tu Mac o las borraste:

1. **Consola AWS** → **IAM** → **Users** → tu usuario (ej. `terraform`) → **Security credentials**.
2. **Create access key** → uso **Command Line Interface (CLI)** → crear.
3. **Copia** Access Key ID y Secret (solo se muestran una vez).
4. En tu Mac:
   ```bash
   aws configure
   ```
   Pega Access Key ID, Secret, región (ej. `us-east-1`), output `json`.

5. Comprobar:
   ```bash
   aws sts get-caller-identity
   ```

Todo esto está detallado en: **[PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md)** (secciones 2 y 3).

---

## 2. “Apagué los contenedores” / “Desaparecieron las instancias”

En AWS no hay “contenedores” que se apaguen como en Docker local. Lo que suele pasar:

| Lo que hiciste | Qué pasa |
|----------------|----------|
| **Parar (Stop)** la EC2 | La instancia sigue existiendo; al **Iniciar (Start)** de nuevo, **la IP pública cambia** (salvo que tengas Elastic IP). Tus DNS (Namecheap) seguirían apuntando a la IP vieja → “no carga”. |
| **Terminar (Terminate)** la EC2 | La instancia se borra. No hay forma de recuperarla; hay que **crear una nueva** (Terraform o a mano). |
| **Terraform destroy** | Se destruyen todas las EC2 y la red que definió ese Terraform. Para tener de nuevo Jenkins/app hay que volver a hacer `terraform apply`. |
| **Free Tier** | No “desaparecen” por ser gratis; desaparecen si las **terminaste** o hiciste **destroy**. Si solo las paraste, en EC2 → Instances siguen apareciendo (estado “stopped”); puedes iniciarlas de nuevo. |

### Qué hacer según tu caso

- **Solo paraste las EC2:**  
  Consola → **EC2** → **Instances** → selecciona las que estén **Stopped** → **Instance state** → **Start**.  
  Si **no** tenías Elastic IP, anota la **nueva IP pública** y actualiza los registros **A** en Namecheap (ver [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md)).

- **Terminaste las EC2 o hiciste `terraform destroy`:**  
  Hay que **volver a crear** todo. Sigue la sección 3 más abajo (desplegar con Terraform y, si quieres, dominio + HTTPS).

---

## 3. Poner la app en producción (gratis, tu dominio, HTTPS, accesible en todo el mundo)

Orden recomendado:

### Paso A — Desplegar en AWS (gratis, Free Tier)

1. **Requisitos:** Cuenta AWS, Terraform ≥ 1.6, AWS CLI configurado (`aws configure`).  
   Si acabas de recuperar acceso: [PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md).

2. **Una sola lista de pasos (recomendada):**  
   **[toolkit-fastflow/manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](../../manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md)**  
   Ahí tienes: `cd` al directorio, copiar `terraform.tfvars.free-tier.example` → `terraform.tfvars`, obtener AMI, `init` → `plan` → `apply`. Al final obtienes **jenkins_public_ip** y accedes a `http://<IP>:8080`.

3. **Guía completa (Free Tier + transferir repo):**  
   [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md](DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md) (incluye instalar Jenkins por SSH, sección 5).

4. **Punto de entrada por rol:**  
   [PASO-A-PASO-AWS-GRATIS-Y-AGENTES.md](PASO-A-PASO-AWS-GRATIS-Y-AGENTES.md) — “Desplegar tú en AWS gratis” y uso de agentes.  
   O [manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md](../../manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md).

### Paso B — Tu dominio (Namecheap u otro)

- En el registrador (Namecheap → Advanced DNS) creas registros **A** que apunten subdominios a las **IP públicas** de tus EC2 (Jenkins, Gitea, Vantive, etc.).
- Así accedes por `http://jenkins.tudominio.com` en lugar de `http://IP:8080`.
- **Documentación:** [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) (mismo proceso para cualquier dominio; solo cambias el nombre del dominio).

**Importante:** Si no usas **Elastic IP**, cada vez que pares e inicies la EC2 la IP puede cambiar y tendrás que actualizar los A en el DNS.

### Paso C — HTTPS (sitio seguro, accesible en todo el mundo)

- Pones **Nginx** en cada EC2 en el puerto **443** y un certificado **Let's Encrypt** (gratis).
- Opción recomendada en el repo: **un solo certificado wildcard** para `tudominio.com` y `*.tudominio.com`, así no pides un cert por cada subdominio.
- **Guía paso a paso:** [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md) (válida para cualquier dominio; solo cambias “unclic.consulting” por el tuyo).
- **Resumen de opciones (Gitea + Jenkins):** [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md).

Con dominio + HTTPS, la app queda **segura y accesible desde cualquier país** (las EC2 en AWS están en una región, pero el acceso es por internet; si más adelante quieres CDN, se puede añadir CloudFront u otro).

### Réplica completa (Jenkins + Gitea + HTTPS + varios servicios)

Si quieres reproducir **todo** el entorno (varias EC2, DNS, Jenkins con HTTPS, Gitea, etc.):

- **Índice general:** [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md).
- **Pasos por fases:** [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) (Fases 1: AWS, 2: DNS, 3: Jenkins + HTTPS, etc.).

---

## 4. Resumen: documentos clave por lo que quieres hacer

| Objetivo | Documento |
|----------|-----------|
| **Entrar de nuevo / configurar usuario y CLI** | [PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md) |
| **Desplegar Jenkins en AWS gratis (lista única)** | [INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](../../manifests/terraform/jenkins-aws/INSTRUCCIONES-DEPLOY-AWS-GRATIS.md) |
| **Desplegar gratis + instalar Jenkins + transferir repo** | [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md](DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md) |
| **Punto de entrada AWS (desplegar yo / nuevo dueño)** | [INICIO-RAPIDO-AWS-GRATIS.md](../../manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md) |
| **Dominio (Namecheap) → EC2 (registros A)** | [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| **HTTPS (Let's Encrypt wildcard, todo el dominio)** | [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md) |
| **Replicar todo (AWS + DNS + Jenkins + Gitea + HTTPS)** | [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md) y [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) |

---

*Con esto puedes recuperar el acceso a AWS, volver a levantar (o crear) las instancias y dejar la app en producción bajo tu dominio con HTTPS.*

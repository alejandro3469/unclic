# LocalStack + Terraform / Pulumi / SST + Jenkins + registry + demo POS

Guía para **probar IaC en local** y encajarlo con **Jenkins**, **registry** y el **POS** sin confundir qué simula LocalStack y qué no.

---

## 1. Qué es cada capa (no mezclar)

| Capa | Herramienta típica | LocalStack ayuda |
|------|-------------------|------------------|
| **APIs tipo AWS** (S3, IAM, Lambda, ECR, ECS “API”, etc.) | Terraform AWS provider, Pulumi AWS | **Sí** — endpoint `http://localhost:4566` |
| **Kubernetes** (tu `deploy/terraform` del POS usa `provider "kubernetes"`) | kind / k3d / minikube + mismo `.tf` | **No** viene de LocalStack; necesitas **cluster local real** o seguir usando tu K8s de demo |
| **Imagen Docker del POS** | Jenkins `docker build` + push | Registry **real** (p. ej. tuyo) **o** ECR emulado en LocalStack **o** registry Docker local |
| **SST** (Next/API serverless en AWS) | SST v2/v3 | **Parcial / incómodo** con LocalStack; suele ser mejor **cuenta AWS dev** o front export estático (UnClic ya es static) |

**Conclusión:** Para “todo integrado” lo razonable es:

1. **LocalStack** → recursos **AWS-shaped** (S3 artefactos, ECR de prueba, IAM de prueba, Lambda de prueba, etc.).
2. **Cluster K8s local** (kind/k3d) → mismo Terraform **Kubernetes** que `repo-pos-fastflow/deploy/terraform` apuntando a `KUBECONFIG` local.
3. **Jenkins** → job que según parámetro ejecuta **plan/apply** contra LocalStack **y/o** empaqueta/pushea imagen hacia el registry que elijas en esa prueba.

---

## 2. Terraform + LocalStack (recomendado como primer paso)

Documentación alineada con LocalStack: [Terraform / LocalStack](https://docs.localstack.cloud/user-guide/integrations/terraform/).

### Opción A — `tflocal` (menos fricción)

```bash
pip install terraform-local   # proporciona tflocal
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1
# LocalStack arriba en :4566
tflocal init
tflocal plan
```

`terraform-local` inyecta endpoints hacia LocalStack.

### Opción B — Provider AWS con `endpoints` (control explícito)

En un `.tf` o `terraform.tf` dedicado a **solo LocalStack**:

```hcl
provider "aws" {
  access_key                  = "test"
  secret_key                  = "test"
  region                      = "us-east-1"
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    s3       = "http://localhost:4566"
    iam      = "http://localhost:4566"
    ecr      = "http://localhost:4566"
    sts      = "http://localhost:4566"
    lambda   = "http://localhost:4566"
    # añade solo lo que uses
  }
}
```

Mantén **otro** directorio o workspace para **AWS real** sin esos `endpoints`.

### POS actual (`kubernetes` provider)

Tu módulo en `repo-pos-fastflow/deploy/terraform` **no usa AWS**; usa **Kubernetes**. Para probarlo en local:

1. Crea un cluster: `kind create cluster` (o k3d).
2. `export KUBECONFIG=...`
3. Carga la imagen del POS en el cluster: `kind load docker-image ...` o push a un registry accesible desde el cluster.
4. `terraform plan -var="pos_image=..."` como siempre.

Eso es **independiente** de LocalStack. LocalStack entra cuando quieras **ECR + IAM + S3** al estilo AWS alrededor del mismo flujo.

---

## 3. Pulumi + LocalStack

Pulumi permite fijar endpoints por servicio (similar a Terraform). Ver [Pulumi + LocalStack](https://docs.localstack.cloud/user-guide/integrations/pulumi/).

Patrón:

- Stack `dev-localstack` con configuración `aws:region` y `aws:endpoints` (o variables de entorno que el provider AWS de Pulumi respete según versión).
- Mismo criterio: **recursos AWS** en LocalStack; **Kubernetes** con provider `kubernetes` contra tu kind/k3d.

---

## 4. SST + LocalStack (expectativas realistas)

SST despliega sobre **AWS real** (CloudFormation / CDK-style). El soporte “oficial” y estable contra LocalStack **no** es equivalente al de Terraform/Pulumi.

**Recomendación para tu caso:**

- **UnClic (landing):** ya es **static export**; no necesitas SST ni LocalStack para eso.
- **POS / APIs:** si algún día usas SST, valora **cuenta AWS de desarrollo** con presupuesto/límites o mantén **Terraform/Pulumi** para infra que quieras **replicar en LocalStack**.

Si insistes en experimentar SST+LocalStack, hazlo como **spike aislado** en un repo pequeño; no bloquees el pipeline del POS en eso hasta validar que los recursos que usas están emulados bien.

---

## 5. Registry + Jenkins + demos POS

### Modelo dual (claro para el pipeline)

| Modo | Registry | Quién consume |
|------|----------|----------------|
| **Demo real** | `registry.unclic.consulting` (o el que ya uses) | EC2 / K8s demo |
| **Lab local** | ECR en LocalStack **o** `registry:2` en Docker (`localhost:5000`) | kind/k3d o contenedor local |

### Jenkins (idea de stages)

1. **Build** — igual que hoy (Maven / imagen).
2. **Push imagen** — según parámetro del job:
   - `REGISTRY_TARGET=localstack` → login ECR LocalStack + push (requiere scripts `awslocal ecr ...`).
   - `REGISTRY_TARGET=prod` → push al registry real (lo que ya tengas).
3. **Terraform / Pulumi** — stage opcional:
   - `IAC_TARGET=localstack` → `tflocal plan` o `pulumi preview` con stack local.
   - `IAC_TARGET=aws` → plan contra cuenta real (credenciales IAM del agente).

Así **no mezclas** credenciales: el agente que toca AWS real no tiene por qué ser el mismo que el de pruebas LocalStack.

### Demo POS aplicada

- **Solo validar IaC:** `tflocal` / Pulumi contra LocalStack para S3 + ECR + (si aplica) un recurso que emules.
- **Desplegar POS como hoy:** Terraform **Kubernetes** + imagen en un registry que el cluster vea; LocalStack puede ser solo el sitio donde “pruebas” políticas IAM o buckets de artefactos, no el runtime del JAR.

---

## 6. Orden sugerido (para no atascarte)

1. LocalStack arriba + `aws s3 mb` / `tflocal` con un bucket de prueba.
2. `awslocal ecr create-repository` + push de una imagen **hello-world** desde Jenkins o a mano.
3. kind + tu Terraform **kubernetes** del POS con `pos_image` apuntando a imagen cargada en kind.
4. Pulumi: replica solo la parte AWS que necesites en LocalStack.
5. SST: último y solo si tienes un caso concreto; considera AWS dev antes.

---

## 7. Implementación en código (repo POS)

- Plan y variables Jenkins: `repo-pos-fastflow/.jenkins/PLAN-FASTFLOW-PIPELINE-IAC.md`
- Stages opcionales en el `Jenkinsfile` del mismo repo (`IaC: Terraform…`, `Pulumi preview`).

## 8. Referencias

- LocalStack — Terraform: https://docs.localstack.cloud/user-guide/integrations/terraform/
- LocalStack — Pulumi: https://docs.localstack.cloud/user-guide/integrations/pulumi/
- AWS CLI `--endpoint-url`: https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-options.html
- Terraform POS existente (K8s): `repo-pos-fastflow/deploy/terraform/main.tf`

---

## 9. Enlace desde documentación UnClic

- Allowlist / sitio: [DEMO-ACCESO-ALLOWLIST-INFRA.md](./DEMO-ACCESO-ALLOWLIST-INFRA.md)
- Deploy sitio: [DEPLOY-SITIO-REMOTO.md](./DEPLOY-SITIO-REMOTO.md)

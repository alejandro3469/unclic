# Tutorial de replicación visual — Paso a paso con consolas, comandos, URLs y ayudas visuales

Este tutorial lleva al usuario por **cada paso** para replicar todo el entorno FastFlow (Jenkins, Gitea, POS, DNS, opcional Cloudcraft). En cada paso se indica: **en qué consola** estás, **qué ves** al abrirla, **qué comando o clic** ejecutar, **qué URL** abrir, y una **ayuda visual** (referencia a HTML o asset) para saber dónde hacer clic.

Cuando tengas **assets** (capturas, HTML de pantallas), súbelos a `docs/50-tecnologias/assets/` o `docs/assets/replicar/` y sustituye los placeholders `[ASSET: nombre]` por la ruta real (p. ej. sintaxis Markdown de imagen apuntando a `50-tecnologias/assets/tu-captura.png`, o un enlace a un fragmento HTML en `deploy/replicar-tutorial-visual.html`).

---

## Convención de cada paso

Cada paso sigue este esquema:

- **Paso N.** Título
- **Consola:** Terminal local | Terminal EC2 (SSH) | Navegador (AWS Console) | Navegador (Namecheap) | Navegador (Jenkins) | Navegador (Gitea)
- **Qué ves:** Descripción breve de lo que aparece en pantalla al abrir esa consola.
- **Comando / Acción:** Comando exacto a ejecutar o acción (clic en qué botón).
- **URL:** Enlace directo (si aplica).
- **Ayuda visual:** Referencia a asset o fragmento HTML (botones, tabla simulada).

---

## Fase 0: Requisitos previos

- Cuenta AWS con permisos para crear EC2 y security groups (y opcional IAM roles).
- Dominio en Namecheap (ej. unclic.consulting) para DNS.
- En local: Git, Terraform o Pulumi (según el camino elegido), navegador.

---

## Paso 1. Abrir la consola AWS EC2

| Campo | Valor |
|-------|--------|
| **Consola** | Navegador (AWS Console) |
| **Qué ves** | Tras iniciar sesión en AWS, la consola principal. En la barra de servicios: Compute, Storage, etc. Debes ir a **EC2**. |
| **Comando / Acción** | Clic en **EC2** (bajo Compute) o abrir la URL directa de EC2 en tu región. |
| **URL** | https://console.aws.amazon.com/ec2/home?region=us-east-2#Instances: |
| **Ayuda visual** | [ASSET: aws-console-ec2-link] — Barra lateral: "EC2" bajo "Compute". O usa el buscador superior: escribir "EC2" y elegir "EC2" (Virtual servers in the cloud). |

---

## Paso 2. Lista de instancias EC2

| Campo | Valor |
|-------|--------|
| **Consola** | Navegador (AWS Console → EC2) |
| **Qué ves** | Menú izquierdo: **Instances**, **Instance state**, **Launch instance**. Tabla central: columnas Name, Instance ID, Instance state, Instance type, Public IPv4 address, etc. Si no hay instancias, la tabla está vacía. |
| **Comando / Acción** | Si ya tienes instancias creadas por Terraform: anotar la **Public IPv4** de la instancia con nombre `fastflow-jenkins-controller`. Si no: seguir Paso 3 (Terraform) o Launch instance manual. |
| **URL** | (misma que Paso 1) |
| **Ayuda visual** | [ASSET: aws-ec2-instances-table] — Tabla con columnas: Name, Instance ID, Instance state, Instance type, Public IPv4 address. Filas: fastflow-jenkins-controller, fastflow-gitea, etc. |

Fragmento HTML de ejemplo (botones que verías) — para incluir en `deploy/replicar-tutorial-visual.html`:

```html
<!-- Ejemplo: botones de la consola EC2 -->
<div class="mock-console" data-step="2">
  <p><strong>EC2 → Instances</strong></p>
  <p>Acciones visibles:</p>
  <button type="button" class="mock-btn">Launch instance</button>
  <button type="button" class="mock-btn">Connect</button>
  <p>Tabla: Name | Instance ID | Instance state | Instance type | Public IPv4 address</p>
</div>
```

---

## Paso 3. Crear infra con Terraform (terminal local)

| Campo | Valor |
|-------|--------|
| **Consola** | Terminal (local, en tu máquina) |
| **Qué ves** | Prompt del shell (bash/zsh). Debes estar en el directorio del repo que contiene Terraform. |
| **Comando / Acción** | <pre>cd toolkit-fastflow/manifests/terraform/jenkins-aws
terraform init
terraform plan -out=tfplan
terraform apply tfplan</pre> |
| **URL** | (N/A; todo en terminal) |
| **Ayuda visual** | [ASSET: terminal-terraform-output] — Salida típica de `terraform apply`: "Apply complete! Resources: X added, 0 changed, 0 destroyed." y outputs con IP pública. |

---

## Paso 4. Namecheap — Advanced DNS

| Campo | Valor |
|-------|--------|
| **Consola** | Navegador (Namecheap) |
| **Qué ves** | Namecheap → Domain List → unclic.consulting → **Manage** → pestaña **Advanced DNS**. Sección **HOST RECORDS** con tabla Type, Host, Value, TTL. Botón "ADD NEW RECORD". |
| **Comando / Acción** | Añadir o editar registro **A**: Host = `jenkins`, Value = `<IP-EC2-Jenkins>` (ej. 18.218.37.76). Repetir para `gitea`, `pos`, `@`/`www` según [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md). |
| **URL** | https://ap.www.namecheap.com/domains/list/ (tras login, elegir dominio → Manage → Advanced DNS) |
| **Ayuda visual** | [ASSET: namecheap-host-records] — Tabla HOST RECORDS: Type A, Host jenkins, Value (IP), TTL Automatic. Botón "ADD NEW RECORD". |

---

## Paso 5. Conectar por SSH a la EC2 Jenkins

| Campo | Valor |
|-------|--------|
| **Consola** | Terminal (local) |
| **Qué ves** | Prompt del shell. |
| **Comando / Acción** | <pre>ssh -i /ruta/a/tu-clave.pem ec2-user@18.218.37.76</pre> Sustituir IP por la Public IPv4 de tu instancia Jenkins y la ruta de la clave. |
| **URL** | N/A |
| **Ayuda visual** | [ASSET: terminal-ssh] — Tras conectar: prompt `[ec2-user@ip-10-0-x-x ~]$`. |

---

## Paso 6. Instalar Java 17 y Jenkins (en la EC2)

| Campo | Valor |
|-------|--------|
| **Consola** | Terminal (EC2, ya conectado por SSH) |
| **Qué ves** | Prompt `ec2-user@...`. |
| **Comando / Acción** | <pre>sudo yum install -y java-17-amazon-corretto-headless
# Seguir con instalación Jenkins según docs (war o pkg)
# Arrancar Jenkins; luego en navegador abrir :8080</pre> Ver [../30-instalacion/INSTALAR-JENKINS.md](../30-instalacion/INSTALAR-JENKINS.md) para la secuencia exacta. |
| **URL** | (Tras arrancar Jenkins) http://18.218.37.76:8080 o http://jenkins.unclic.consulting:8080 |
| **Ayuda visual** | [ASSET: jenkins-unlock] — Pantalla "Unlock Jenkins" con campo para pegar contraseña inicial. |

---

## Paso 7. Desbloquear Jenkins (navegador)

| Campo | Valor |
|-------|--------|
| **Consola** | Navegador (Jenkins UI) |
| **Qué ves** | Página "Unlock Jenkins" con un cuadro de texto y el mensaje "Copy the password from the file shown below and paste it here." |
| **Comando / Acción** | En la EC2 (SSH): <pre>sudo cat /var/lib/jenkins/secrets/initialAdminPassword</pre> Copiar la salida y pegarla en el cuadro de texto de la página. Clic en **Unlock**. |
| **URL** | http://jenkins.unclic.consulting:8080 o http://&lt;IP&gt;:8080 |
| **Ayuda visual** | [ASSET: jenkins-unlock-screen] — Botón **Unlock** y campo de contraseña. |

Fragmento HTML de ayuda visual:

```html
<div class="mock-console" data-step="7">
  <p><strong>Unlock Jenkins</strong></p>
  <p>Copy the password from the file shown below and paste it here.</p>
  <input type="text" readonly value="[pegar aquí la contraseña]" />
  <button type="button" class="mock-btn">Unlock</button>
</div>
```

---

## Paso 8. Crear job Pipeline en Jenkins

| Campo | Valor |
|-------|--------|
| **Consola** | Navegador (Jenkins UI, ya desbloqueado) |
| **Qué ves** | Dashboard de Jenkins. Enlace **New Item** (o "Create new jobs"). |
| **Comando / Acción** | Clic en **New Item**. Nombre: `pos-online-pipeline`. Tipo: **Pipeline**. Clic en **OK**. En la siguiente pantalla: **Pipeline** → Definition: **Pipeline script from SCM**. SCM: **Git**. Repository URL: `http://gitea.unclic.consulting:3000/tu-usuario/pos-online.git`. Guardar. |
| **URL** | http://jenkins.unclic.consulting:8080/view/all/newJob |
| **Ayuda visual** | [ASSET: jenkins-new-item] — Pantalla New Item: campo "Enter an item name", lista "Pipeline", "Freestyle project", etc. Botón **OK**. Ver [JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md](../20-operaciones/JENKINS-JOB-PANTALLAS-NEW-ITEM-Y-CONFIGURE.md). |

---

## Pasos siguientes (resumen)

- **Paso 9.** Gitea: instalar (Docker) en EC2 Gitea, abrir :3000, primer uso, crear repos. Consola: Terminal (EC2 Gitea), Navegador (Gitea). URL: http://gitea.unclic.consulting:3000.
- **Paso 10.** Local: clonar repo, `git remote add gitea ...`, `git push gitea main`. Consola: Terminal (local).
- **Paso 11.** Jenkins: ejecutar job (Build Now), ver logs. Consola: Navegador (Jenkins). URL: http://jenkins.unclic.consulting:8080/job/pos-online-pipeline/.
- **Paso 12.** Verificar POS: abrir http://&lt;IP&gt;:8111 (o pos.unclic.consulting:8111) si el pipeline desplegó la app.

Cada uno de estos puede expandirse con la misma estructura: Consola, Qué ves, Comando/Acción, URL, Ayuda visual.

---

## Dónde colocar tus assets

- **Imágenes (capturas):** `docs/50-tecnologias/assets/` — p. ej. `aws-ec2-instances-table.png`, `jenkins-unlock-screen.png`, `namecheap-host-records.png`. En este doc, reemplaza `[ASSET: nombre]` por una imagen Markdown cuya ruta sea relativa a este archivo, p. ej. `../50-tecnologias/assets/tu-archivo.png`.
- **HTML de pantallas simuladas:** En `deploy/replicar-tutorial-visual.html` (fragmentos por paso). Puedes abrir ese HTML en el navegador y usarlo como guía visual junto con este markdown.
- **Lista de inputs/comandos ejecutados:** Los comandos ya están en las tablas de cada paso. Para una tabla maestra "todos los comandos en orden", ver sección equivalente en [DOCUMENTACION-POR-TECNOLOGIA-Y-REPLICACION.md](DOCUMENTACION-POR-TECNOLOGIA-Y-REPLICACION.md) y los docs en [../50-tecnologias/](../50-tecnologias/).

---

## Enlaces rápidos (URLs reales)

| Servicio | URL (actualizar IP/dominio si cambia) |
|----------|--------------------------------------|
| AWS Console EC2 us-east-2 | https://console.aws.amazon.com/ec2/home?region=us-east-2#Instances: |
| Namecheap Domain List | https://ap.www.namecheap.com/domains/list/ |
| Jenkins | http://jenkins.unclic.consulting:8080 |
| Gitea | http://gitea.unclic.consulting:3000 |
| POS (demo) | http://pos.unclic.consulting:8111 |
| UnClic (local dev) | http://localhost:3002 |

IPs actuales y más URLs: [URLS-Y-EC2-PRUEBAS.md](../20-operaciones/URLS-Y-EC2-PRUEBAS.md).

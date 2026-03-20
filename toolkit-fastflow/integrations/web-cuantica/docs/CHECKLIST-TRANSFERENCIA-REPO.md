# Checklist: transferir el repo y que otro replique en AWS

Lista para el **dueño actual** antes de transferir el repo, y para el **nuevo dueño** al recibirlo.

---

## Para el dueño actual (antes de transferir)

- [ ] **No hay secretos en el repo:** ni `terraform.tfvars`, ni `.env` con claves, ni Access Keys. Solo ejemplos (`.example`).
- [ ] **Documentación de despliegue en el repo:** [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md](DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md) y, si aplica, [APLICAR-FASTFLOW.md](../../../manifests/terraform/jenkins-aws/APLICAR-FASTFLOW.md) o equivalente en tu estructura.
- [ ] **README o docs explican “primer uso”:** enlace a “Replicar en AWS” (sección 4 de la guía) para quien reciba el repo.
- [ ] **Terraform Free Tier listo:** existe `terraform.tfvars.free-tier.example` en el entorno dev (o equivalente) y la guía indica cómo usarlo.
- [ ] **Decidir qué transfieres:** solo código + docs, o también acceso a algo (p. ej. “Jenkins ya desplegado en esta URL”). Si es solo repo, el nuevo dueño despliega todo en su cuenta.

---

## Para el nuevo dueño (al recibir el repo)

- [ ] **Clonar o aceptar transferencia** del repo.
- [ ] **Crear (o tener) cuenta AWS** y configurar AWS CLI (`aws configure`).
- [ ] **Seguir la guía “Replicar en AWS”** en [DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md](DESPLEGAR-AWS-GRATIS-Y-TRANSFERIR-REPO.md) (Terraform Free Tier).
- [ ] **No usar `terraform.tfvars` del dueño anterior:** crear el tuyo desde `terraform.tfvars.free-tier.example` y rellenar AMIs en tu región.
- [ ] **Instalar Jenkins en la EC2** (si la AMI no lo trae) según la sección 5 de la guía.
- [ ] **Opcional:** Restringir el security group (solo tu IP) y cambiar contraseñas por defecto.

---

## Servicios que cada uno usa (resumen)

| Servicio | Dueño actual | Nuevo dueño |
|----------|----------------|-------------|
| **AWS** | Su cuenta, Free Tier | Su propia cuenta, Free Tier |
| **Terraform** | Mismo código en el repo | Mismo código (clone/transfer) |
| **Jenkins** | Instalado en su EC2 (o no) | Lo instala en su EC2 (guía sección 5) |

No se comparten cuentas ni credenciales; solo el repositorio y la documentación.

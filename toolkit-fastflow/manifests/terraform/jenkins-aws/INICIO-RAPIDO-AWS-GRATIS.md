# Inicio rápido: AWS gratis y transferir el repo (punto de entrada)

**Objetivo:** Desplegar Jenkins (FastFlow) en AWS dentro del Free Tier, y después transferir el repo a otra persona para que pueda **replicar el mismo despliegue en su cuenta AWS también gratis**.

---

## ¿Qué rol tienes?

| Situación | Sigue este documento |
|-----------|------------------------|
| **Replicar todo de punta a punta (happy path)** | → **[HAPPY-PATH-REPLICAR-COMPLETO.md](HAPPY-PATH-REPLICAR-COMPLETO.md)** — Terraform + Jenkins + Gitea + POS + opcional UnClic; todo lo que hace falta para que cualquiera replique. |
| **Primera vez / quiero entender todo (qué se ejecuta y qué obtengo)** | → **[GUIA-PASO-A-PASO-PRINCIPIANTES.md](GUIA-PASO-A-PASO-PRINCIPIANTES.md)** — Explicación paso a paso para principiantes. |
| **El apply falló a medias (errores de AMI o VPC)** | → [RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md](RECUPERAR-DESPUES-DE-APPLY-FALLIDO.md) — Recuperar usando la misma región y una AMI válida. |
| **Quiero desplegar yo en AWS gratis ahora** | → **[INSTRUCCIONES-DEPLOY-AWS-GRATIS.md](INSTRUCCIONES-DEPLOY-AWS-GRATIS.md)** — Pasos en orden (lista única). O [DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md](DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md) sección 2. |
| **Deploy real AWS y probar Kubernetes** | → [GUIA-DEPLOY-REAL-AWS-Y-KUBERNETES.md](GUIA-DEPLOY-REAL-AWS-Y-KUBERNETES.md) — Terraform apply en AWS + probar K8s (minikube/kind o EKS). |
| **Voy a transferir el repo a otra persona** | → [CHECKLIST-TRANSFERENCIA-REPO.md](CHECKLIST-TRANSFERENCIA-REPO.md) — Checklist dueño actual (no subir secretos, enlazar doc para nuevo dueño, transferir). |
| **Soy el nuevo dueño del repo y quiero replicar en mi AWS** | → [REPLICAR-AWS-NUEVO-DUENO.md](REPLICAR-AWS-NUEVO-DUENO.md) — Pasos en mi cuenta AWS (Free Tier). |

---

## Flujo en una frase

1. **Tú:** Despliegas en AWS (opcional) con Terraform Free Tier → dejas el repo listo (sin `terraform.tfvars` ni secretos) → transfieres el repo.
2. **Nuevo dueño:** Clona el repo → configura su AWS CLI → ejecuta el mismo Terraform en `envs/dev` con `terraform.tfvars.free-tier.example` → obtiene su propia IP y Jenkins en su cuenta, también gratis.

---

## Dónde está el Terraform

```text
toolkit-fastflow/manifests/terraform/jenkins-aws/
├── INICIO-RAPIDO-AWS-GRATIS.md          ← estás aquí (punto de entrada)
├── HAPPY-PATH-REPLICAR-COMPLETO.md       ← replicar todo (Terraform + Jenkins + Gitea + POS + opcional UnClic)
├── GUIA-PASO-A-PASO-PRINCIPIANTES.md    ← qué se ejecuta, por qué y qué obtienes (principiantes)
├── INSTRUCCIONES-DEPLOY-AWS-GRATIS.md   ← pasos en orden para desplegar (lista única)
├── DESPLIEGUE-AWS-GRATIS-Y-REPLICAR.md  ← guía completa (desplegar + transferir + replicar)
├── REPLICAR-AWS-NUEVO-DUENO.md          ← guía corta para el nuevo dueño
├── CHECKLIST-TRANSFERENCIA-REPO.md      ← checklist dueño actual vs nuevo dueño
├── APLICAR-FASTFLOW.md                  ← Terraform init/plan/apply, AMI, backend S3
├── README.md                            ← estructura de módulos
└── envs/dev/
    ├── terraform.tfvars.free-tier.example  ← copiar a terraform.tfvars (no subir)
    ├── terraform.tfvars.example
    └── ...
```

---

## Uso con varios agentes

Si **varios agentes** (Cursor, Codex, etc.) trabajan en paralelo:

- **Agente A (docs):** Mantener este INICIO-RAPIDO, DESPLIEGUE-AWS-GRATIS-Y-REPLICAR, REPLICAR-AWS-NUEVO-DUENO, CHECKLIST-TRANSFERENCIA-REPO.
- **Agente B (Terraform):** Ajustar módulos, variables, `terraform.tfvars.free-tier.example`; no modificar a la vez los mismos archivos que A (ver **COORDINACION-AGENTES-FASTFLOW.md** en `integrations/web-cuantica/docs/`).

Así puedes desplegar en AWS gratis y dejar el repo listo para que otro lo replique en su cuenta, también gratis.

# Empieza aquí — George (o cualquier colaborador)

Mapa del módulo y **qué documento abrir**. El procedimiento detallado está en la **guía única**.

---

## 1. Dónde estás en el disco (raíz del módulo)

```text
toolkit-fastflow/integrations/web-cuantica/    ← raíz del módulo (WEB-CUANTICA)
├── AGENTS.md          ← reglas para agentes de IA
├── README.md          ← resumen del toolkit
├── config/            ← JSON central (Jenkins, registry, K8s…)
├── deploy/            ← HTML dashboards, terraform/, k8s/
├── docs/              ← documentación numerada 00–90 + postman/
├── repo-pos-fastflow/              ← POS de demo: Jenkinsfile, .jenkins/
├── smartbussiness-generic-model/   ← generic-model (Maven); dependencia obligatoria del POS → Gitea + job Jenkins
├── scripts/           ← validar Jenkinsfile, Terraform, K8s; `push-generic-model-to-gitea.sh`
└── unclic/            ← landing Next.js (hub, /demo)
```

El POS “de cliente” suele ser **otro clon**; aquí va pipeline + docs de referencia.

---

## 2. Camino único (lo mínimo)

| Paso | Acción | Documento |
|------|--------|-----------|
| 1 | Laboratorio completo | **[GUIA-UNICA…](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** — índice en orden; **§0** si empiezas en AWS; **§12** = `webcuantica.com` |
| 2 | *(Opcional)* Contexto **por qué / cómo** | [GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md](GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md) |
| 3 | *(Opcional)* **Orden A→K** (cuenta AWS → pipeline) | [GUIA-CRONOLOGICA-UN-SOLO-IMPLEMENTADOR.md](GUIA-CRONOLOGICA-UN-SOLO-IMPLEMENTADOR.md) |
| 4 | Cuenta AWS **recién creada** (IAM, CLI) | [PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md) |

---

## 3. Otros objetivos (una fila cada uno)

| Objetivo | Abre |
|----------|------|
| Web Cuántica / dominio propio | [WEBCUANTICA-REPLICACION-FASTFLOW.md](WEBCUANTICA-REPLICACION-FASTFLOW.md) · [EMPIEZA-AQUI-GEORGE-WEBCUANTICA.md](EMPIEZA-AQUI-GEORGE-WEBCUANTICA.md) |
| generic-model (Gitea + Jenkins) | [../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md](../20-operaciones/GENERIC-MODEL-GITEA-JENKINS.md) |
| Terraform EC2 Jenkins (monorepo) | [../20-operaciones/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md](../20-operaciones/GUIA-AWS-GRATIS-INSTALAR-VER-USAR-PROBAR.md) + [Terraform AWS](../../../../manifests/terraform/jenkins-aws/INICIO-RAPIDO-AWS-GRATIS.md) |
| Solo landing UnClic | [../../unclic/README.md](../../unclic/README.md) |
| Pipeline / rollback en este repo | [../../repo-pos-fastflow/README.md](../../repo-pos-fastflow/README.md) |
| Copiar al pos-online “real” | [../60-pos-online/README.md](../60-pos-online/README.md) |
| LocalStack / pruebas locales | [../../unclic/docs/LOCALSTACK-IAC-JENKINS-POS.md](../../unclic/docs/LOCALSTACK-IAC-JENKINS-POS.md) |

**Índice de todos los `.md`:** [../README.md](../README.md).

---

## 4. Agentes de IA y docs históricos

- **[../../AGENTS.md](../../AGENTS.md)** — reglas y validación.
- Docs solo en commits viejos: **[../20-operaciones/COMPATIBILIDAD-Y-HISTORIAL.md](../20-operaciones/COMPATIBILIDAD-Y-HISTORIAL.md)**, **[../90-archivo/historial/README.md](../90-archivo/historial/README.md)**.

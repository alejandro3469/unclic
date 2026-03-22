# Compatibilidad y documentación en el historial

Compatibilidad con **pos-online** y cómo **recuperar docs que solo existen en commits anteriores** (sin complicar código ni copia).

---

## 1. Compatibilidad con pos-online

### Jenkinsfile

- Pos-online puede tener ya un **Jenkinsfile** con etapas propias (Prepare, Build, Test, Lint, Package, etc.).
- **No reemplazar todo:** fusionar solo las etapas de imagen y registry de `Jenkinsfile.example` (Build image, Push to registry).
- En pos-online el workspace es la raíz (donde está `pom.xml`).

### Scripts

- **Sin pom.xml** (solo toolkit): `run-all-validations.sh` omite Maven; valida Jenkinsfile, Terraform, K8s.
- **Con pom.xml** (en pos-online): `run-all-validations.sh` ejecuta `mvn test` y simulación; `build-and-push.sh` hace mvn + docker + push si `REGISTRY`.
- Validar el Jenkinsfile real: `bash scripts/validate-jenkinsfile.sh Jenkinsfile` (por defecto usa `Jenkinsfile.example`).

### Qué copiar al pos-online (mínimo, sin complicar)

| Nivel | Qué copiar |
|-------|------------|
| **Mínimo** | `Jenkinsfile.example`, `deploy/dashboard-demo-jenkins-registry.html`, `docs/60-pos-online/FLUJO-COMMIT-Y-USUARIO-FINAL.md`, `docs/30-instalacion/INSTALAR-JENKINS.md`, `scripts/validate-jenkinsfile.sh`, `scripts/test-registry.sh`. |
| **Completo** | Lo anterior + `scripts/validate-terraform.sh`, `validate-k8s.sh`, `run-all-validations.sh`, `build-and-push.sh`, `simulate-jenkins-pipeline.sh`, `deploy/terraform/`, `deploy/k8s/`, `docs/60-pos-online/`, `docs/20-operaciones/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md`. |

Solo copiar lo que aplique; no es obligatorio usar todos los scripts.

---

## 2. Docs que solo están en el historial de commits

Algunos documentos existían en commits anteriores (handoff/otro contexto) y **ya no están en el árbol actual**. Para consultarlos o recuperarlos, desde la **raíz del repo** (pipeline-as-code-with-jenkins-master):

```bash
git show <COMMIT>:<ruta>
```

### Commit de referencia

Usar el commit donde aún existían esos docs (por ejemplo el último que los tenía):

| Commit   | Descripción |
|----------|-------------|
| `5dfdc92` | Docs de handoff, registry y empaquetado (lista abajo). |

### Rutas para recuperar (ejemplo con 5dfdc92)

| Doc (solo en historial) | Comando para ver |
|-------------------------|------------------|
| AGENTES-FASTFLOW-REGISTRY-EMPAQUETADO.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/AGENTES-FASTFLOW-REGISTRY-EMPAQUETADO.md` |
| TAREA-FASTFLOW-REGISTRY-Y-EMPAQUETADO.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/TAREA-FASTFLOW-REGISTRY-Y-EMPAQUETADO.md` |
| HANDOFF-GEORGE-WEB-CUANTICA-FRONT.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/HANDOFF-GEORGE-WEB-CUANTICA-FRONT.md` |
| LISTO-PARA-ENVIAR-GEORGE.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/LISTO-PARA-ENVIAR-GEORGE.md` |
| PARA-GEORGE-RESPUESTA.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/PARA-GEORGE-RESPUESTA.md` |
| README-HANDOFF-GEORGE.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/README-HANDOFF-GEORGE.md` |
| BRANCH-HANDOFF-GEORGE.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/BRANCH-HANDOFF-GEORGE.md` |
| GEORGE-FRONT-FILELIST.txt | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/GEORGE-FRONT-FILELIST.txt` |
| ARQUITECTURA-MODERNIZACION-UNCLIC.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/ARQUITECTURA-MODERNIZACION-UNCLIC.md` |
| LANDINGS-Y-ENTREGA-CLIENTES.md | `git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/LANDINGS-Y-ENTREGA-CLIENTES.md` |

**Guardar en local** (desde la raíz del repo): añadir `> toolkit-fastflow/integrations/web-cuantica/docs/90-archivo/historial/<nombre>` al final. Ejemplo:  
`git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/AGENTES-FASTFLOW-REGISTRY-EMPAQUETADO.md > toolkit-fastflow/integrations/web-cuantica/docs/90-archivo/historial/AGENTES-FASTFLOW-REGISTRY-EMPAQUETADO.md`

Cualquier otro archivo en ese commit: usar la ruta `toolkit-fastflow/integrations/web-cuantica/docs/<nombre>`.

Ver también **docs/90-archivo/historial/README.md** en este repo.

---

## 3. Resumen

- **Compatibilidad:** Jenkinsfile y scripts para pos-online (Maven, Dockerfile/registry opcional). Sin `pom.xml` aquí se omiten Maven y simulación.
- **Copia:** Mínimo = dashboard + flujo commit + INSTALAR-JENKINS + validate-jenkinsfile + test-registry; el resto opcional.
- **Historial:** Docs que ya no están en el árbol se recuperan con `git show 5dfdc92:<ruta>` (tabla arriba); para pulir o referencia.

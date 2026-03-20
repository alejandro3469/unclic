# Código documentado y cobertura de tests (FastFlow demo)

Resumen de la **documentación en código** (comentarios y JSDoc) y de los **tests de integración, simulaciones y pruebas locales** para Jenkins, Terraform, Kubernetes y Registry.

---

## 1. Código documentado con comentarios

### Servidor (server/)

| Archivo | Qué se documentó |
|---------|-------------------|
| **db/index.js** | Cabecera del módulo, `resolveDbPath`, `ensureDataDir`, `getDb`, `initSchema`, `initDb`. |
| **db/init.js** | CLI de inicialización de DB (npm run init-db). |
| **config.js** | Ya tenía JSDoc; sección `registry` para imagen, tags y retención. |
| **routes/api.js** | `upsertSubscriber`, `createToken` y rutas (health, leads, admin). |
| **routes/admin.js** | Rutas de panel (login, logout, sitios, estadísticas). |
| **routes/access.js** | Área de cliente por token, recursos desde deliverables. |
| **lib/email.js** | Envío de correo y stub si SMTP no configurado. |
| **lib/registry-tags.js** | Ya documentado: formatTag, getPreviousTag, applyRetention, isValidTag. |
| **index.js** | Monolito FastFlow, createApp, estáticos. |

### Deploy e infra

| Archivo | Qué se documentó |
|---------|-------------------|
| **deploy/terraform/main.tf** | Cabecera: requisitos, uso (terraform init/plan/apply). |
| **deploy/k8s/deployment.yaml** | Cabecera: imagen, namespace, ConfigMap, probes. |
| **deploy/k8s/service.yaml** | Cabecera: ClusterIP, puerto 3000, Ingress/LB. |
| **deploy/k8s/configmap.yaml** | Cabecera: NODE_ENV, BASE_URL, secretos aparte. |
| **deploy/k8s/namespace.yaml** | Cabecera: namespace fastflow. |
| **scripts/build-and-push.sh** | Uso, REGISTRY, IMAGE_NAME, IMAGE_TAG. |
| **scripts/install-local.sh** | Uso, pasos siguientes (entorno local). |
| **scripts/test-registry.sh** | Uso, pull tras push. |
| **Jenkinsfile.example** | Stages, variables, workspace. |

---

## 2. Tests de integración y simulaciones

### Tests en server/test/

| Test | Qué cubre |
|------|-----------|
| **api.test.js** | Health, leads, HTML del sitio (app en marcha). |
| **access.test.js** | /app sin token (401), token inválido (403), token válido (200). |
| **admin.test.js** | Login, /api/admin/subscribers y /stats con y sin sesión. |
| **config.test.js** | Claves de config, llm, registry. |
| **integration-registry-config.test.js** | config.registry (url, imageName, defaultTag, retentionDays). |
| **jenkins-pipeline-simulation.test.js** | Jenkinsfile.example y build-and-push.sh: stages Test/Build/Push, IMAGE_NAME, REGISTRY. |
| **k8s-manifests.test.js** | deploy/k8s/: namespace, deployment (container, health), service (3000), configmap (NODE_ENV, BASE_URL). |
| **terraform-validate.test.js** | deploy/terraform: main.tf, variables.tf, provider y recursos; opcionalmente `terraform validate` si está en PATH. |
| **registry-api.test.js** | test-registry.sh existe y usa REGISTRY/IMAGE_NAME/IMAGE_TAG; opcional GET /v2/_catalog si REGISTRY_URL. |
| **registry-tags-rollback.test.js** | formatTag, getPreviousTag, applyRetention, isValidTag (rollback y retención). |
| Otros (scripts, deploy) | Scripts de validación, deploy (K8s, Terraform), dashboard. |

### Scripts de validación (scripts/)

| Script | Qué hace |
|--------|----------|
| **validate-terraform.sh** | `terraform init -backend=false` y `terraform validate` en deploy/terraform (si terraform en PATH). |
| **validate-k8s.sh** | Lista YAML de deploy/k8s; si hay kubectl, `kubectl apply --dry-run=client` (si no hay cluster, no falla). |
| **run-all-local-tests.sh** | 1) npm test en server, 2) validate-terraform, 3) validate-k8s, 4) test-registry.sh si REGISTRY definido. |

---

## 3. Cómo ejecutar todo

```bash
# Desde la raíz de pos-online (o este toolkit)

# Solo tests del servidor (unit + integración + Jenkins/K8s/Terraform/Registry)
cd server && npm test

# Todas las pruebas locales (servidor + Terraform + K8s + registry opcional)
bash scripts/run-all-local-tests.sh

# Test de registry (pull) cuando tengas registry local o remoto
REGISTRY=localhost:5000 bash scripts/test-registry.sh
```

---

## 4. Resumen de cobertura

- **Jenkins:** Jenkinsfile y build-and-push.sh validados por tests (estructura y etapas); script ejecutable en local.
- **Terraform:** Estructura de .tf validada en tests; `terraform validate` vía script y opcionalmente en test.
- **Kubernetes:** Manifiestos YAML validados en tests (namespace, deployment, service, configmap); kubectl dry-run vía script si hay cluster.
- **Registry:** Config en app, tags/rollback/retención en registry-tags.test.js y registry-api.test.js; test-registry.sh para pull; dashboard-demo-jenkins-registry.html como UI mínima.

Todo el código relevante del proyecto demo queda documentado con comentarios y cubierto por tests de integración, simulaciones y pruebas locales.

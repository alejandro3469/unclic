# Documentación maestra — índice (versión pública)

**Uso:** Índice de **qué está documentado** en `docs/` y rutas útiles. **No incluye IPs, correos ni credenciales reales** — este archivo está pensado para **divulgación**. Para un registro operativo privado, mantén una copia local con tus valores en [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](../20-operaciones/INFRAESTRUCTURA-UNCLIC-ACTUAL.md) (plantilla) o en tu propio gestor de secretos.

---

## 1. Estado desplegado (referencia genérica)

- **EC2:** típicamente **Jenkins** + **Gitea** (y opcionalmente **POS** en instancia aparte). Región ejemplo: `us-east-2`.
- **DNS:** registros **A** `jenkins` y `gitea` → IPs públicas actuales de cada instancia.
- **Gitea:** HTTPS o HTTP según configuración; repos `TU_USUARIO/pos-online`, `TU_USUARIO/smartbussiness-generic-model`.
- **Jenkins:** jobs `generic-model-pipeline` y `pos-online-pipeline`; POS a menudo en puerto **8111** en la misma EC2 que Jenkins.

Detalle paso a paso: [REPLICAR-UNCLIC-COMPLETO.md](../20-operaciones/REPLICAR-UNCLIC-COMPLETO.md), [REPLICAR-ESTADO-ACTUAL-INDICE.md](../20-operaciones/REPLICAR-ESTADO-ACTUAL-INDICE.md).

---

## 2. Entregables (`delivery/`) y scripts

- **Paquetes:** `scripts/build-delivery-packages.sh` — ver [delivery/ANTES-DE-SUBIR-A-GITEA.md](../../delivery/ANTES-DE-SUBIR-A-GITEA.md) (buscar datos sensibles antes de publicar).
- **Scripts:** `push-repo-pos-fastflow-to-gitea.sh`, `push-generic-model-to-gitea.sh`, validaciones, `run-all-validations.sh`, etc. — carpeta `scripts/`.

---

## 3. Repos locales vs Gitea

- **repo-pos-fastflow** → Gitea (remote `origin`).
- **smartbussiness-generic-model** → Gitea (remote `gitea`).
- Detalle: [REPOS-LOCALES-Y-GITEA.md](../20-operaciones/REPOS-LOCALES-Y-GITEA.md).

---

## 4. Documentación relacionada

- [INDICE-KNOW-HOW.md](INDICE-KNOW-HOW.md)
- [docs/README.md](../README.md)
- [POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md](../20-operaciones/POS-ONLINE-VER-INSTANCIA-Y-ENVIAR-PETICIONES.md)
- [POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md](../20-operaciones/POS-ONLINE-PIPELINE-LOGS-EXPLICADOS.md)
- [HTTPS-UNCLIC-GITEA-JENKINS.md](../20-operaciones/HTTPS-UNCLIC-GITEA-JENKINS.md)
- [DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](../20-operaciones/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md)
- [AGENTS.md](../../AGENTS.md)

---

## 5. Docs históricos con datos viejos

Algunos runbooks pueden mencionar **dominios o IPs de ejemplo**. Sustitúyelos siempre por **tus** valores. Lista de reconciliación: revisar archivos que enlazan [URLS-Y-EC2-PRUEBAS.md](../20-operaciones/URLS-Y-EC2-PRUEBAS.md), [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) y guías HTTPS cuando actualices infra.

---

## 6. Pendientes (opcional)

- Actualizar jobs Jenkins con URL HTTPS de Gitea cuando proceda.
- Documentar handoff / rama de entrega en un doc interno si aplica.
- Recuperar docs antiguos: [COMPATIBILIDAD-Y-HISTORIAL.md](../20-operaciones/COMPATIBILIDAD-Y-HISTORIAL.md), [historial/README.md](historial/README.md).

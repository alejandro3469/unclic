# Alcance: ¿dónde vive UnClic y qué repo es la verdad?

## Lectura obligatoria (1 minuto)

- **Estas implementaciones** (landing, API, presupuesto OSS, voz, Ollama, ChatterBot, Docker Compose, docs de producto UnClic) deben mantenerse **en este árbol**:  
  **`toolkit-fastflow/integrations/web-cuantica/unclic`** dentro del monorepo donde trabajas.
- **El sitio público UnClic** (marca, dominio, Jenkins de deploy) se publica desde el repo **Gitea `nucleic`** (`alejandro-perez/nucleic`): ahí está la **raíz del proyecto Next** que construye y despliega Jenkins. Ese contenido se **sincroniza** desde esta carpeta `unclic/` (script `scripts/update-nucleic-from-monorepo.sh` o flujo equivalente que uses).
- **No es un proyecto “para George”.** Cualquier doc del toolkit con nombres de colaborador es **contexto histórico o operativo del monorepo**; el **producto y el sitio** son **UnClic** (Alejandro / unclic.consulting). Los agentes de IA y los equipos deben asumir **UnClic + nucleic** como destino, no un handoff genérico a terceros.

## Flujo recomendado

1. **Desarrollar y commitear** en el monorepo, carpeta `unclic/`.
2. **Probar** `npm run build`, API, etc., localmente.
3. **Subir a `nucleic`** lo que deba ir a producción (sync + push Gitea).
4. **Jenkins** en el servidor que ya tengas configurado para `nucleic` genera `out/` y despliega.

## Qué NO implica este doc

- No sustituye la configuración de Jenkins, SSH ni secretos (sigue [JENKINS-NUCLEIC-DEPLOY.md](JENKINS-NUCLEIC-DEPLOY.md) u el que tengas en `nucleic`).
- No obliga a que todo el monorepo se publique: solo la **landing UnClic** y sus microservicios asociados según tu compose/deploy.

## Referencias

- [EXTRACT-REPO-UNClic-AISLADO.md](EXTRACT-REPO-UNClic-AISLADO.md) — aislar solo `unclic/` si hiciera falta.
- [GIT-MONOREPO-SOLO-UNClic.md](GIT-MONOREPO-SOLO-UNClic.md) — trabajar monorepo vs repo único.
- [README.md](../README.md) — entrada rápida al proyecto.

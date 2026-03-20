# Baxter — subcliente de Kings & Joers (previsto)

**Baxter** es otro **subcliente** del mismo subcliente **Kings & Joers**, igual que Vantive pero como proyecto distinto:

- **Landing propio** (contenido específico de Baxter).
- **Subdominio propio** (p. ej. **baxter.unclic.consulting**).
- **Repo propio** en Gitea (ej. alejandro-perez/baxter o kings-joers/baxter).
- **Instancia propia** (EC2) para servir el landing, o compartir patrón con Vantive (Nginx, 80/443, HTTPS).

El **mismo Jenkins** de Kings & Joers puede tener un job **baxter-pipeline** que haga checkout del repo Baxter y deploy a la EC2 de Baxter (igual que vantive-pipeline → EC2 Vantive).

---

## Estado

- **Documentado** como subcliente bajo Kings & Joers.
- **Por ahora no se implementa**; el foco está en **Vantive** (push, instancia, subdominio, HTTPS).
- Cuando se trabaje en Baxter: crear carpeta `kings-joers/baxter/` (landing + Jenkinsfile), repo en Gitea, EC2 fastflow-baxter, DNS baxter → IP, HTTPS; mismo patrón que [VANTIVE-PUBLICAR-SUBDOMINIO-HTTPS.md](VANTIVE-PUBLICAR-SUBDOMINIO-HTTPS.md).

---

## Resumen

| Subcliente | Bajo     | Landing / subdominio           | Repo   | EC2 / servidor   |
|------------|----------|---------------------------------|--------|------------------|
| Vantive    | Kings & Joers | vantive.unclic.consulting  | vantive| fastflow-vantive |
| Baxter     | Kings & Joers | baxter.unclic.consulting   | baxter | fastflow-baxter (previsto) |

Mismo Jenkins (Kings & Joers), mismos flujos (Gitea → pipeline → deploy por SSH); cada subcliente con su repo, su instancia y su subdominio.

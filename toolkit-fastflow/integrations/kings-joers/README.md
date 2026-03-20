# Kings & Joers — subcompañía FastFlow

**Kings & Joers** es una subcompañía con infraestructura propia en FastFlow:

- **Un Jenkins propio** (una instancia EC2 solo para esta subcompañía).
- **Varios subclientes/proyectos**, cada uno con su propio landing, subdominio y repo (por ahora: **Vantive** y **Baxter**).
- **Servidores de despliegue** propios (una EC2 por app o por subcliente, p. ej. servidor Vantive, servidor Baxter).

Cada subcompañía tiene **su propio Jenkins**; no se comparte el Jenkins de web-cuantica (unclic).

---

## Subclientes de Kings & Joers

Bajo Kings & Joers hay **subclientes** (proyectos distintos), cada uno con:

- Su propio **landing / aplicación**.
- Su propio **subdominio** (p. ej. vantive.unclic.consulting, baxter.unclic.consulting).
- Su propio **repo** en Gitea y, si aplica, su propia **EC2** para servir la app.

| Subcliente | Landing / subdominio | Estado |
|------------|----------------------|--------|
| **Vantive** | vantive.unclic.consulting, repo vantive | **En foco ahora** — ver `vantive/` y docs. |
| **Baxter** | baxter.unclic.consulting, landing propio | Previsto; mismo Jenkins Kings & Joers, otro repo y otra instancia. Ver [docs/BAXTER-SUBCLIENTE-KINGS-JOERS.md](docs/BAXTER-SUBCLIENTE-KINGS-JOERS.md). |

Por ahora **solo nos enfocamos en Vantive**. Baxter se documenta como subcliente futuro; cuando toque, se añadirá código (p. ej. `baxter/`) y guías de despliegue igual que para Vantive.

---

## Estructura

```
kings-joers/
  README.md           (este archivo)
  vantive/            Subcliente Vantive: código + Jenkinsfile
  docs/               Documentación: EC2, Jenkins, Vantive (y referencia Baxter)
```

*(En el futuro: `baxter/` con su landing y pipeline.)*

---

## Infraestructura Kings & Joers

| Componente | Descripción |
|------------|-------------|
| **Jenkins** | EC2 fastflow-jenkins-kings-joers. Puerto 8080 (y 443 si HTTPS). Jobs de todos los subclientes (Vantive, Baxter, etc.). |
| **Servidor Vantive** | EC2 (ej. fastflow-vantive). Nginx, 80/443. App y landing de Vantive. |
| **Servidor Baxter** | EC2 (ej. fastflow-baxter) — previsto. Landing y subdominio propios. |
| **Repos** | Un repo por subcliente en Gitea (vantive, baxter, etc.). El mismo Jenkins clona y despliega a la EC2 que corresponda. |

Gitea puede ser compartido; cada subcliente tiene su propio repo.

---

## Orden recomendado

1. **Crear EC2 Jenkins** para Kings & Joers → instalar Jenkins, Java, Git (ver [docs/KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md](docs/KINGS-JOERS-SUBCOMPANIA-JENKINS-EC2.md)).
2. **Crear EC2 servidor Vantive** → Nginx, puertos 80/443 (ver [docs/VANTIVE-SERVIDOR-Y-PIPELINE-DESDE-CERO.md](docs/VANTIVE-SERVIDOR-Y-PIPELINE-DESDE-CERO.md)).
3. **Crear repo Vantive** en Gitea y subir el código de **vantive/**.
4. **Configurar job en el Jenkins de Kings & Joers** (vantive-pipeline) y credencial SSH para deploy.

Documentación detallada: [docs/](docs/).

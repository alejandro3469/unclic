# Visuales fieles en el repo (Cloudcraft, Jenkins, Gitea, POS)

Los diagramas **en vivo** en Cloudcraft son **privados por defecto**. Si abres  
`https://app.cloudcraft.co/view/…` o el blueprint sin **enlace compartible**, verás *“Sorry, this blueprint is not public”* y **Login**.  
Para que **cualquiera** vea la arquitectura **real** en UnClic sin cuenta Cloudcraft, hay **tres vías** (puedes combinarlas).

---

## 1) Enlace compartible (sigue siendo “vivo” en Cloudcraft)

1. Entra a Cloudcraft con tu cuenta (dueño del blueprint).
2. Abre el blueprint de la demo.
3. **Share & Export** → **Get shareable link** (genera URL con `?key=…`).
4. En `unclic/.env.local` (y en el build de producción):
   - `NEXT_PUBLIC_CLOUDCRAFT_VIEW_URL=<url completa>`
   - Opcional: `NEXT_PUBLIC_CLOUDCRAFT_EMBED_URL=<misma base>&embed=true`
5. `npm run build` y despliega.

Documentación: [GUIA-CLOUDCRAFT-COMPLETA.md](../../../../docs/GUIA-CLOUDCRAFT-COMPLETA.md), [shareable link security](https://docs.datadoghq.com/cloudcraft/faq/shareable-link-security/).

---

## 2) Captura PNG/WebP en el repo (100 % visible sin login)

Ideal cuando **no** quieres exponer clave en URL o el embed falla en iframes.

### Desde el navegador (mientras ves el diagrama ya cargado)

| Método | Pasos |
|--------|--------|
| **Chrome / Edge** | `F12` → menú ⋮ del panel → **Run command** (o `Cmd+Shift+P`) → **Capture full size screenshot** (página completa) o captura de **área** si el canvas es grande. |
| **Firefox** | Herramientas desarrollador → icono cámara en barra, o extensiones tipo “Full Page Screen Capture”. |
| **macOS** | `Cmd+Shift+4` → espacio o arrastrar sobre la ventana del diagrama. |

Ajustes útiles en Cloudcraft antes de capturar:

- Zoom que deje legibles VPC / EC2 / etiquetas.
- Ocultar paneles laterales si estorban (máximo área del diagrama).

### Export vía API (PNG del blueprint)

Si tienes **API key** de Cloudcraft: [Blueprints API — export PNG](https://docs.datadoghq.com/cloudcraft/api/blueprints/). Útil para CI o actualizaciones repetibles.

### Dónde guardar en UnClic

1. Exporta a **PNG** o **WebP** (WebP pesa menos).
2. Coloca el archivo aquí:

   `unclic/public/images/architecture/cloudcraft-demo.webp`  
   (o `.png`; el nombre debe coincidir con la variable de entorno.)

3. En `.env.local` y en producción:

   ```bash
   NEXT_PUBLIC_CLOUDCRAFT_STATIC_IMAGE=/images/architecture/cloudcraft-demo.webp
   ```

4. Commitea **solo** la imagen (es una foto fiel de tu infra; no sustituye Live scanning).

La landing mostrará esa imagen cuando **no** uses embed, o puedes usarla **además** del botón “Abrir en Cloudcraft” si configuras también `VIEW_URL`.

---

## 3) Otros visuales que conviene versionar (misma carpeta)

Misma idea: **captura real** mientras estás logueado, nada generado por IA.

| Qué | Archivo sugerido | Notas |
|-----|------------------|--------|
| Jenkins (pipeline / job) | `public/images/demos/jenkins-pipeline.png` | Ocultar secretos en UI. |
| Gitea (repo / commits) | `public/images/demos/gitea-repo.png` | Sin tokens en barra de URL. |
| POS / app desplegada | `public/images/demos/pos-demo.png` | Pantalla representativa. |
| Registry (listado tags) | `public/images/demos/registry-tags.png` | Si aplica. |

Puedes enlazarlos desde secciones o galería cuando existan rutas en el sitio; lo importante es **tener los archivos en `public/images/`** y actualizarlos cuando cambie la demo.

---

## Checklist rápido

- [ ] Blueprint privado → o bien **share link** o bien **captura** en repo.
- [ ] Imagen en `public/images/architecture/` + `NEXT_PUBLIC_CLOUDCRAFT_STATIC_IMAGE`.
- [ ] Tras cambios de infra en AWS → **nueva captura** o nuevo scan + export.
- [ ] No commitear `.env.local` con `key=` si la política de seguridad lo prohíbe (en ese caso prioriza solo imagen estática).

---

## Referencias en código

- Sección arquitectura: `components/sections/architecture-live-section.tsx`
- Variables: `unclic/.env.example`
- Snapshot JSON del diagrama (estructura, no imagen): `toolkit-fastflow/docs/cloudcraft-fastflow-blueprint-snapshot.json`

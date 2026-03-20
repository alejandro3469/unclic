# Prompts ganadores — logo UnClic (Gemini + retoque)

Referencia de diseño estructural del sitio: [REFERENCIA-DISENO-SEQUOIA.md](REFERENCIA-DISENO-SEQUOIA.md).  
**Marca:** UnClic no está asociada a Sequoia Capital; los prompts evitan copiar marcas ajenas.

---

## Evolución (qué funcionó)

| Iteración | Enfoque |
|-----------|---------|
| 1 | Isotipo abstracto: nodo + pipeline / tres puntos → un camino; vino `#6B1F2A`; enterprise editorial. |
| 2 | Árbol geométrico mínimo (tronco + ramas); burdeos sobre crema; 32px; sin marca Sequoia. |
| 3 | Árbol = metáfora de **pipeline** con ramas; usable como SVG/favicon. |
| 4 | Más **minimal enterprise**: fondo blanco, logo negro (más control en print/UI). |
| 5 | Más **flow**, transmitir **calma**. |
| 6 | Fusión: árbol-pipeline con **hojas**, encuadre **cuadrado o circular**, landing + favicon; simplificar a minimal blanco/negro; estilo **Google/Apple** (iconos muy reducidos). |
| **Final** | Retoque en **Photoshop** sobre salida de IA → SVG definitivo en repo. |

---

## Prompt base (inglés) — isotipo pipeline / nodo

```
Minimal abstract logo mark for a B2B tech consultancy "UnClic", single confident gesture: one node connecting to a short pipeline or three aligned dots merging into one path. Flat vector style, premium enterprise SaaS. Color: deep wine red #6B1F2A on off-white, or reversed monochrome for favicon. Generous padding, geometric, no text, no letters. Inspired by editorial financial/advisory sites: calm, authoritative, lots of negative space. SVG-like crisp edges, scalable icon.
```

**Negativo recomendado (añadir al final):**  
`no text, no letters, no Sequoia Capital logo, no famous brands, no watermark`

---

## Prompt — árbol geométrico (variante orgánica-controlada)

```
Abstract stylized tree silhouette as a minimal logo mark: thick trunk splitting into few clean branches, geometric not organic cartoon. Represents growth and stability for enterprise DevOps consulting. Single color deep burgundy on cream background. Flat, iconic, works at 32px. No text, no realistic bark, no Sequoia Capital branding.
```

---

## Prompt compuesto ganador (iteración final antes de Photoshop)

Úsalo como **bloque único** si quieres reproducir la dirección visual:

```
Abstract stylized tree silhouette as a minimal logo mark: thick trunk splitting into few clean branches, geometric not organic cartoon. Represents growth and stability for enterprise DevOps consulting. The tree reads as a pipeline: branches like stages or flow lines; subtle leaf shapes. Square or circular bounding box for favicon and header. Deep burgundy on cream OR simplified to enterprise minimal: white background, logo black only. Very minimal like Google or Apple app icons. Calm flow, peaceful, authoritative. No text, no letters, no Sequoia Capital branding. SVG-friendly flat vector, scalable.
```

---

## Post-proceso (lo que cerró el asset)

1. **Gemini / IA:** varias pasadas con el compuesto anterior.  
2. **Photoshop:** contraste, unificación de pesos de trazo, recorte para encabezado.  
3. **Export SVG:** archivo canónico del repo.

---

## Archivo en el sitio

| Uso | Ruta |
|-----|------|
| Logo header / footer | `public/images/brand/unclic-logo.svg` |
| Componente React | `components/ui/unclic-logo.tsx` (marca por imagen) |

**Nota:** El SVG incluye fondo blanco en trazos compuestos; sobre `bg-background` del sitio se integra bien. Para fondos oscuros, valorar variante `unclic-logo-dark.svg` (invertir o sustituir `.cls-1`).

---

## Favicon

El mark es **apaisado** (~295×160). Para favicon cuadrado: recortar zona central del árbol o re-exportar con `viewBox` cuadrado desde el mismo maestro en Figma/Illustrator.

---

*Documento vivo: actualizar si cambiáis el prompt o el archivo fuente del logo.*

**Última actualización del SVG en repo:** versión refinada en `public/images/brand/unclic-logo.svg` (mismo `viewBox` 294.96×160.08; favicon/metadata siguen apuntando ahí).

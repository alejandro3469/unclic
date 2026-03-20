# Sistema de tipografía — UnClic

Pareja **Roboto + Space Mono** (Google Fonts). Cuándo usar cada familia.

---

## Familias

| Variable / clase   | Fuente      | Uso estándar                          |
|--------------------|-------------|----------------------------------------|
| `font-sans` (default) | Roboto  | Cuerpo, UI, navegación, botones, formularios |
| `font-serif`       | Space Mono | Títulos de sección, hero, citas, código, acentos |
| `font-roboto`      | Roboto     | Cuando quieras solo Roboto (p. ej. datos, chips) |
| `font-space-mono`  | Space Mono | Cuando quieras solo Space Mono (p. ej. código, lead) |
| `font-mono`        | Space Mono | Código, datos técnicos                 |

---

## Cuándo usar SANS (Roboto)

- **Body** y párrafos
- **Navegación**, enlaces, menús
- **Botones**, labels, inputs, formularios
- **Listas**, tablas, datos
- **Footer**, legal, metadata
- **Captions** y textos auxiliares

**Clase Tailwind:** `font-sans` (es el default del sitio).

---

## Cuándo usar SERIF / display (Space Mono)

- **h1, h2, h3** — ya aplicado en `globals.css` a todos los headings de sección
- **Hero title** (título principal de la landing)
- **Lead / eyebrow** (línea corta encima del título)
- **Blockquotes** y testimonios (cita)
- **Código**, rutas, nombres técnicos
- **Etiquetas editoriales** (“Pipeline as Code”, etc.)

**Clase Tailwind:** `font-serif` o `font-space-mono`. Para títulos no hace falta: los `h1`, `h2`, `h3` ya usan Space Mono por defecto.

---

## Resumen rápido

- **¿Es título de sección o hero?** → Space Mono (ya aplicado en h1–h3).
- **¿Es cuerpo, UI, botón, nav, formulario o lista?** → Roboto (`font-sans`).
- **¿Es cita, código o texto técnico?** → `font-serif` o `font-space-mono`.

Las fuentes se cargan con `next/font/google` (Roboto, Space_Mono) en `app/layout.tsx`.

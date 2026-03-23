# Sistema de tipografía — UnClic

Stack **Inter** (cuerpo + títulos, estilo shadcn **Nova**) + **Space Mono** (código / acentos técnicos) + **Ubuntu** (solo wordmark del logo).

---

## Familias

| Variable / clase     | Fuente       | Uso estándar                                      |
|----------------------|-------------|---------------------------------------------------|
| `font-sans` (default) | Inter      | Cuerpo, UI, navegación, botones, formularios      |
| `font-serif`         | Inter      | Títulos h1–h3, hero, lead (misma familia, más peso) |
| `font-inter`         | Inter      | Alias explícito de Inter                          |
| `font-space-mono`    | Space Mono | Código, rutas, etiquetas técnicas                 |
| `font-mono`          | Space Mono | Igual que arriba                                  |
| `font-ubuntu`        | Ubuntu      | Solo `UnClicLogo`                                 |

---

## Cuándo usar SANS (Inter)

- **Body** y párrafos
- **Navegación**, menús
- **Botones**, labels, inputs
- **Listas**, tablas
- **Footer**, metadata

---

## Cuándo usar monoespacio

- **Código**, comandos, IDs
- **Eyebrows** muy técnicos (opcional)

Los **h1, h2, h3** usan **Inter** semibold + `tracking-tight` (`globals.css`).

---

## Tema de color

Las variables semánticas (`--background`, `--foreground`, `--chart-1` … `--chart-5`) siguen el patrón **zinc** de la documentación shadcn; la paleta **Nord** sigue disponible como `bg-nord-*` / `text-nord-*` para acentos.

Carga de fuentes: `next/font/google` en `app/layout.tsx` — **Inter**, **Space_Mono**, **Ubuntu**.

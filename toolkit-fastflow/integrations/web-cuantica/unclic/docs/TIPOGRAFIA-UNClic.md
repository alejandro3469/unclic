# Tipografía UNCLIC — escala y uso

Referencia práctica alineada a patrones habituales (cuerpo **16px**, escala modular ~**1.25**, jerarquía clara tipo Material / sistemas de diseño web).

## Familia

- **Inter** (`font-sans`) para todo el texto de interfaz y marketing.
- **Space Mono** (`font-mono`) solo donde aporte (métricas, código, números de fase).
- **Ubuntu** reservado al logo / marca si aplica.

No usar `font-serif` en marketing: en el tema apunta a Inter; evita variaciones visuales innecesarias.

## Tokens CSS (`app/globals.css`)

| Clase | Uso |
|--------|-----|
| `text-type-hero` | H1 principal home / héroes — tamaño fluido con tope (~56px), sin `text-7xl`/`text-8xl`. |
| `text-type-page-title` | H1 de páginas internas (intro, portales) — `clamp` moderado. Combinar con `font-light` o `font-semibold`. |
| `text-type-section-title` | H2 de sección (bloques, pricing, FAQ, demos). **24px → 30px** en `md`. |
| `text-type-card-title` | H3 / títulos de tarjeta — **18px → 20px** en `md`. |
| `text-type-lead` | Subtítulos y párrafos de apertura — `text-base` / `md:text-lg`, `text-muted-foreground`. |
| `text-type-body-sm` | Texto secundario, pies de sección — `text-sm` (+ opcional `md:text-base`). |
| `text-type-eyebrow` | Línea superior en mayúsculas — `text-xs`, tracking amplio. |
| `text-type-eyebrow-accent` | Igual con color `primary`. |
| `text-type-stat` | Números destacados (trust strip, métricas). |

## Cuerpo base

- `body`: **1rem (16px)** en todos los breakpoints (se eliminó el salto a 17px en `md` para no competir con la escala de títulos).

## Qué evitar

- Títulos de sección con `text-4xl`/`text-5xl` mezclados con otros en `text-2xl` sin criterio.
- Héroes con `text-8xl` salvo necesidad extrema de marca; el token `text-type-hero` acota el rango.
- Mezclar `text-display-*` / `text-body-lg` legacy en cabeceras nuevas; preferir los tokens anteriores.

## Legacy en `tailwind.config.ts`

Siguen existiendo `display-sm` / `display-md` / `display-lg` y `body-lg` por compatibilidad; las nuevas pantallas deben usar los **tokens de `globals.css`**.

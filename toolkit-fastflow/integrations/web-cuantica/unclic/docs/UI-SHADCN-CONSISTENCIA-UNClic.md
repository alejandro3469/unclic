# UI — consistencia con shadcn/ui (UnClic)

**Objetivo:** reducir “mezcla de estilos” y alinear la interfaz con el **design system** que ya usamos: [shadcn/ui](https://ui.shadcn.com) (Radix + Tailwind + `components.json` en raíz UnClic).

**Tema visual:** semántica tipo shadcn **Nova + Inter** (variables zinc + `--chart-1…5`) en `app/globals.css`; paleta **Nord** opcional como utilidades `nord/*`. Sí es obligatorio usar **los mismos patrones de componentes y tokens** que la doc shadcn.

---

## 1. Fuente de verdad

| Origen | Uso |
|--------|-----|
| [ui.shadcn.com/docs/components](https://ui.shadcn.com/docs/components) | Componentes base (Button, Card, Input, Sheet, Accordion, …). |
| [ui.shadcn.com/blocks](https://ui.shadcn.com/blocks) | Inspiración de secciones; implementación en `components/sections/*` con las primitivas anteriores. |
| [ui.shadcn.com/charts](https://ui.shadcn.com/charts/area) | Gráficos (Recharts) cuando haga falta métricas en UI. |
| [ui.shadcn.com/docs/directory](https://ui.shadcn.com/docs/directory) | Registros comunitarios opcionales (`npx shadcn add @…`); revisar código al instalar. |
| [ui.shadcn.com/create](https://ui.shadcn.com/create) / [docs/installation](https://ui.shadcn.com/docs/installation) | Nuevos proyectos o añadir piezas con la CLI. |
| **Este repo** | `components/ui/*` + `components/blocks/*` (`BlockContainer`, `BlockSection`). |

Nuevas piezas: **añadir con** `npx shadcn@latest add …` (o pegar desde la doc oficial) y adaptar solo copy/clases necesarias.

**No introducir** (salvo decisión explícita y doc en este archivo): `motion`, Three/WebGL (`three`, `@react-three/*`), `globe.gl`, `react-icons`, animaciones tipo border-beam / canvas pesado, ni componentes copiados de `@shadcnblocks/*` como dependencia de runtime. Iconos: **`lucide-react`**. Logo del sitio: **`UnClicLogo`** / `next/image` + enlaces estándar.

---

## 2. Reglas rápidas

1. **Botones que navegan o envían acción** → `Button` de `@/components/ui/button` con `asChild` + `Link` o `type="submit"`. No imitar botones con `<div>` o `<Link className="rounded-md bg-primary…">` salvo excepción documentada.
2. **Formularios** → `Input`, `Label`, `Textarea`, `Select` de `@/components/ui/*`; errores con `text-sm text-destructive`.
3. **Contenedores** → `BlockContainer` desde `@/components/blocks` (`px-4 sm:px-6 lg:px-8` + `max-w-content` 72rem, opción `wide` → 80rem). La clase Tailwind `container` en `tailwind.config.ts` está **calibrada al mismo tope 72rem** por si queda en demos legacy; en páginas propias preferir siempre `BlockContainer`.
4. **Superficies** → `Card`, `border-border`, `bg-card`, `text-muted-foreground`; radios `rounded-md` / `rounded-lg` o `rounded-card` según token.
5. **Barra sticky** → constante compartida `HEADER_BAR` en `header.tsx`: `border-b` + `glass-subtle` (equivale al patrón blur del ecosistema shadcn/Vercel).
6. **Utilidades solo UnClic** → `.text-gradient-impact`, `.btn-impact`, `.bg-section-manifesto` son **marca**; no extender sin revisión. Para CTAs genéricos preferir `Button variant="default"`.

---

## 3. Inconsistencias ya corregidas (referencia)

- Navbar móvil usaba `bg-background/95` y desktop `glass-subtle` → unificado a **`HEADER_BAR`** + **`BlockContainer`** en móvil.
- Sheet móvil: enlaces con apariencia de botón → **`Button` + `asChild`** (`outline` / `default`).
- Footer: `container` → **`BlockContainer`**; iconos sociales → **`Button` `ghost` `icon`**; títulos de columna → **`text-sm font-semibold`** (patrón tipo Blocks).

---

## 4. Pendiente habitual (auditoría)

- Sustituir `<button className="…">` sueltos por `Button` donde aplique.
- Revisar secciones legacy con bordes/radius arbitrarios (`rounded-[15px]`) vs `rounded-lg` / `rounded-card`.
- Formularios de contacto/login: alinear con **Field** (`components/ui/field.tsx`) si el bloque shadcn lo usa.

---

## 5. Relacionado

- [UI-OSS-MEDIA-ELEVENLABS-STYLE.md](UI-OSS-MEDIA-ELEVENLABS-STYLE.md) — estilo agente / audio (UI sigue siendo shadcn; sin Orb 3D en runtime).
- [FLUJO-NORMAL-BLOQUE-Y-COPY.md](FLUJO-NORMAL-BLOQUE-Y-COPY.md) — flujo bloque → sección → copy.

# Integrar el bloque Hero154 (Shadcn Blocks) en UnClic

## Qué necesitás saber

1. **El bloque original** (`@shadcnblocks/hero154`) trae:
   - Formulario con **react-hook-form** + **zod** + **`@hookform/resolvers`**.
   - Componentes **`Field` / `FieldLabel` / `FieldError`** (no siempre vienen en un proyecto base; aquí hay un `components/ui/field.tsx` mínimo compatible).
   - **`AspectRatio`** (ya lo teníamos).
   - Imágenes **externas** (Cloudfront de Shadcn Blocks). Van con `<img>`; si más adelante usás `next/image`, añadí el host en `next.config.js` → `images.remotePatterns` (ya incluido para `deifkwefumgah.cloudfront.net`).

2. **Export estático** (`output: 'export'`): el bloque es compatible; el submit del formulario puede ir a un servicio externo (Formspree, API) o a una ruta estática con query — no hace falta Server Actions.

3. **Dos caminos para instalar el bloque:**
   - **CLI oficial** (con API key de Shadcn Blocks): `npx shadcn add @shadcnblocks/hero154` — ver [INSTALAR-SHADCN-BLOCKS.md](./INSTALAR-SHADCN-BLOCKS.md). No sobrescribas `button.tsx` / `utils` sin revisar.
   - **Integración manual** (en el repo): dependencias en `package.json` + `components/sections/hero154.tsx` + **mismo copy que el hero principal** → objeto **`hero`** en `lib/copy.ts` (más `leadEmailPlaceholder` para el campo email).

4. **Detalles del código del snippet que pegaste:**
   - Typo: **`HeroFrom`** → debe ser **`HeroForm`**.
   - Zod: **`.required({ email: true })`** no es el patrón habitual en Zod 3; basta con `z.object({ email: z.string().email(...) })`.

---

## Qué hicimos en el proyecto (resumen)

| Archivo | Rol |
|---------|-----|
| `package.json` | `react-hook-form`, `zod`, `@hookform/resolvers` |
| `components/ui/field.tsx` | Primitivos Field para el formulario |
| `components/sections/hero154.tsx` | Bloque Hero154 + copy desde `lib/copy.ts` |
| `lib/copy.ts` → `hero154` | Titular, subtítulo, placeholder, CTA, líneas de confianza |
| `app/page.tsx` | `<Hero154 />` en lugar de `<Hero />` |
| `next.config.js` | `remotePatterns` para Cloudfront (por si usás `next/image`) |

El hero anterior (**Hero70**) sigue en `components/sections/hero.tsx` por si querés volver atrás.

---

## Día a día: cambiar textos o volver al hero viejo

**Textos del Hero154:** editá **`lib/copy.ts` → `hero`** (mismo bloque que alimentaba al Hero con carrusel). El placeholder del email es **`hero.leadEmailPlaceholder`**. Las dos líneas bajo el formulario usan **`hero.heroFeatures[0]` y `[1]`** (títulos).

**Volver al hero con carrusel (Hero70):** en `app/page.tsx`:

```tsx
import { Hero } from '@/components/sections/hero';
// ...
<Hero />
```

**Lead del formulario:** en `hero154.tsx`, función `onSubmit` de `HeroForm` — hoy hace `console.log`. Podés redirigir a `/contacto`, abrir `mailto:`, o POST a Formspree (como en otras docs del sitio).

---

## Comando CLI (alternativa)

Si preferís regenerar desde la web de Shadcn Blocks:

```bash
export SHADCNBLOCKS_API_KEY=sk_live_...
cd /ruta/a/unclic
npx shadcn add @shadcnblocks/hero154
```

Luego fusioná el resultado con nuestro copy (objeto **`hero`** en `lib/copy.ts`) y no pises componentes globales sin revisar [INSTALAR-SHADCN-BLOCKS.md](./INSTALAR-SHADCN-BLOCKS.md).

---

## Subir a Gitea nucleic

Tras los cambios: [TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md](./TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md).

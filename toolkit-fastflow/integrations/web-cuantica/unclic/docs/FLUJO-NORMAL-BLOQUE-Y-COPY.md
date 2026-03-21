# Flujo normal: añadir un bloque de la librería (Shadcn / Shadcn Blocks) y pulir copy

Objetivo: traer un componente UI (bloque Pro o pieza shadcn) al sitio **sin romper** lo que ya tenemos, y **centralizar textos** en `lib/copy.ts` para ir puliendo cada sección a mano.

**Ejemplo ya hecho en el repo:** ruta **`/flow-demo`** + doc [EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md](./EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md) (comandos, commit sugerido, deploy nucleic/Jenkins).

---

## 1. Instalar el bloque (CLI)

Desde la carpeta **`unclic`** (donde está `package.json`):

```bash
export SHADCNBLOCKS_API_KEY=$(grep '^SHADCNBLOCKS_API_KEY=' .env.local | head -1 | cut -d= -f2- | tr -d '\r' | xargs)
npx shadcn add @shadcnblocks/<nombre>
```

- El **nombre exacto** (`hero125`, `pricing3`, …) lo copiás de la **toolbar** del bloque en [shadcnblocks.com](https://www.shadcnblocks.com).
- Cuando pregunte **sobrescribir** `button.tsx`, `utils.ts`, `input.tsx`, etc. → **No** (convención del repo). Ver [INSTALAR-SHADCN-BLOCKS.md](./INSTALAR-SHADCN-BLOCKS.md).

La CLI suele dejar el bloque en **`components/<nombre>.tsx`** (o ruta similar).

---

## 2. Ubicación y convención de archivos

| Qué | Dónde |
|-----|--------|
| Bloque recién bajado (plantilla) | A veces `components/hero125.tsx` — podés **dejarlo** o **mover** la lógica a `components/sections/<seccion>.tsx`. |
| Secciones de la web “de verdad” | Preferible **`components/sections/`** (hero, CTA, logos, etc.) para que la home y las páginas importen desde ahí. |
| Piezas UI reutilizables | **`components/ui/`** (Button, Input, Field…) — no mezclar copy de negocio ahí. |
| **Todo el copy** (titulares, CTAs, listas) | **`lib/copy.ts`** — un objeto por sección o por página. |

Flujo típico: **copiás/adaptás** el JSX del bloque dentro de `components/sections/mi-seccion.tsx` y **sacás los strings** hacia `lib/copy.ts`.

---

## 3. Conectar el copy (pulir cada sección a mano)

1. En **`lib/copy.ts`**, creá o ampliá un objeto, por ejemplo:

   ```ts
   export const miSeccion = {
     title: '…',
     lead: '…',
     cta: '…',
     items: [{ label: '…', description: '…' }],
   } as const;
   ```

2. En el componente de la sección:

   ```tsx
   import { miSeccion } from '@/lib/copy';
   // …
   <h2>{miSeccion.title}</h2>
   ```

3. **Pulís** solo editando `copy.ts` (o props que lean de ahí) — sin reordenar layout hasta que quieras.

Si el bloque ya usa el mismo mensaje que otra parte del sitio (como el hero), **reutilizá** el mismo objeto (`hero`, `cta`, etc.) en lugar de duplicar strings.

---

## 4. Enchufar la sección en una página

- **Home:** `app/page.tsx` → import y `<MiSeccion />` en el orden que quieras.
- Otra ruta: `app/<ruta>/page.tsx` igual.

Mantené **una sección = un componente** en `sections/` cuando pueda, para encontrar rápido qué archivo tocar.

---

## 5. Revisión rápida antes de subir

```bash
npm run dev      # ver en http://localhost:3002 (o el puerto del proyecto)
npm run typecheck
```

---

## 6. Publicar solo el sitio (Gitea **nucleic**)

No hace falta commitear todo el monorepo para que Jenkins construya el landing:

```bash
bash scripts/update-nucleic-from-monorepo.sh
# equivalente:
npm run deploy:nucleic
```

Detalle: [TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md](./TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md).  
Git del monorepo “ruidoso”: [GIT-MONOREPO-SOLO-UNClic.md](./GIT-MONOREPO-SOLO-UNClic.md) (`git status .`).

---

## Resumen en 5 líneas

1. **`npx shadcn add @shadcnblocks/…`** en `unclic` → **No** a sobrescribir UI base.  
2. Llevar el bloque a **`components/sections/…`** si es una sección de página.  
3. **Strings → `lib/copy.ts`**; el TSX solo referencia `copy`.  
4. Importar la sección en **`app/.../page.tsx`**.  
5. **`update-nucleic-from-monorepo.sh`** para subir solo UnClic a **nucleic**.

---

## Docs relacionadas

| Doc | Para qué |
|-----|----------|
| [INSTALAR-SHADCN-BLOCKS.md](./INSTALAR-SHADCN-BLOCKS.md) | API key, CLI 3.0, no pisar archivos |
| [CONSISTENCIA-UI.md](./CONSISTENCIA-UI.md) | Tema, tokens, coherencia visual |
| [SHADCN-BLOCKS-MAP.md](./SHADCN-BLOCKS-MAP.md) | Mapeo bloque → sección del sitio (referencia) |

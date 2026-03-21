# Ejemplo de commit: flujo bloque + copy + entrega rápida (nucleic / Jenkins)

Este archivo documenta **un cambio mínimo real** en el repo: instalar un componente shadcn, enlazarlo a **`lib/copy.ts`**, exponerlo en una **ruta de prueba** y **subir** el sitio con el script de sync a Gitea (y Jenkins si está configurado).

---

## Qué se añadió (solo lo necesario para el flujo)

| Pieza | Archivo |
|-------|---------|
| UI **Progress** (shadcn CLI) | `components/ui/progress.tsx` |
| Copy de la demo | `lib/copy.ts` → objeto **`flowDemo`** |
| Sección que compone Card + Badge + Progress | `components/sections/flow-demo-section.tsx` |
| Página de prueba | `app/flow-demo/page.tsx` → **https://tu-dominio/flow-demo** (export estático: `out/flow-demo/index.html`) |
| Dependencia | `@radix-ui/react-progress` en `package.json` |

**Nota:** La CLI shadcn 4.x generó un import inválido (`from "radix-ui"`). En el repo quedó corregido a `@radix-ui/react-progress` (mismo patrón que `separator.tsx`).

---

## Comandos ejecutados (reproducir)

Desde la carpeta **`unclic`**:

```bash
# 1) Instalar pieza UI desde el registry público shadcn (sin API key)
npx shadcn@latest add progress -y

# 2) Si TypeScript falla, revisar progress.tsx → import * as ProgressPrimitive from "@radix-ui/react-progress"

# 3) Verificar
npm run typecheck
npm run build
npm run dev
# Abrir http://localhost:3002/flow-demo
```

Para bloques **Pro** de Shadcn Blocks, el flujo es el mismo salvo el paso 1:

```bash
export SHADCNBLOCKS_API_KEY=…
npx shadcn add @shadcnblocks/<nombre>
```

Ver [FLUJO-NORMAL-BLOQUE-Y-COPY.md](./FLUJO-NORMAL-BLOQUE-Y-COPY.md) y [INSTALAR-SHADCN-BLOCKS.md](./INSTALAR-SHADCN-BLOCKS.md).

---

## Commit de ejemplo (mensaje sugerido)

```bash
cd /ruta/al/pipeline-as-code-with-jenkins-master

git add \
  toolkit-fastflow/integrations/web-cuantica/unclic/components/ui/progress.tsx \
  toolkit-fastflow/integrations/web-cuantica/unclic/components/sections/flow-demo-section.tsx \
  toolkit-fastflow/integrations/web-cuantica/unclic/app/flow-demo/page.tsx \
  toolkit-fastflow/integrations/web-cuantica/unclic/lib/copy.ts \
  toolkit-fastflow/integrations/web-cuantica/unclic/package.json \
  toolkit-fastflow/integrations/web-cuantica/unclic/package-lock.json \
  toolkit-fastflow/integrations/web-cuantica/unclic/docs/EJEMPLO-COMMIT-FLUJO-BLOQUE-Y-DEPLOY.md \
  toolkit-fastflow/integrations/web-cuantica/unclic/docs/FLUJO-NORMAL-BLOQUE-Y-COPY.md \
  toolkit-fastflow/integrations/web-cuantica/unclic/docs/README.md \
  toolkit-fastflow/integrations/web-cuantica/unclic/README.md

git commit -m "feat(unclic): ejemplo flujo shadcn + copy (/flow-demo) y doc de deploy rápido"
```

Ajustá la lista si movés archivos. El objetivo es **un commit coherente** con demo + doc.

---

## Entrega rápida (mismo commit → Gitea nucleic → Jenkins)

1. **Sync solo UnClic** al repo **nucleic** (no hace falta pushear todo el monorepo):

   ```bash
   cd toolkit-fastflow/integrations/web-cuantica/unclic
   bash scripts/update-nucleic-from-monorepo.sh
   ```

   O con npm (si está definido):

   ```bash
   npm run deploy:nucleic
   ```

2. **Jenkins:** job contra repo `nucleic`, rama `main` → **Build Now** (o webhook al push).

3. Con **`DEPLOY_HOST`** configurado, el pipeline hace **rsync** de `out/` al servidor Nginx.

Referencias: [TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md](./TUTORIAL-DIA-A-DIA-SYNC-NUCLEIC.md), [JENKINS-NUCLEIC-DEPLOY.md](./JENKINS-NUCLEIC-DEPLOY.md).

---

## Checklist desarrollador (fast delivery)

1. Cambiá **`flowDemo`** en `lib/copy.ts` o la sección en `flow-demo-section.tsx`.
2. `npm run build` local.
3. `bash scripts/update-nucleic-from-monorepo.sh`
4. Esperá build Jenkins o disparalo manualmente.
5. Abrí **`/flow-demo`** en producción para validar.

---

## Enlace desde la home (opcional)

La demo **no** está en la home para no mezclar tráfico; la URL directa es **`/flow-demo`**. Si querés un link en el footer o header, añadilo en el componente de navegación correspondiente.

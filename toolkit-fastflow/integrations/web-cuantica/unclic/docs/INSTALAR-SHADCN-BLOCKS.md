# Instalar bloques de Shadcn Blocks

**Referencia oficial:** [Shadcn Blocks — Getting Started](https://www.shadcnblocks.com/docs/getting-started). Esta guía sigue la documentación oficial (CLI, registro con API key, variables de entorno).

**Base de diseño:** El sitio toma como referencia [sequoia.com](https://www.sequoia.com) para jerarquía de contenido (eyebrow → H1 → lead → CTA) y navegación. Ver [REFERENCIA-DISENO-SEQUOIA.md](./REFERENCIA-DISENO-SEQUOIA.md).

---

## Regla importante: no sobrescribir nuestros componentes

Cuando instales un bloque, la CLI puede preguntar si quieres **sobrescribir** archivos que ya existen (`button.tsx`, `utils.ts`, `badge.tsx`, etc.).

- **Responde siempre No** a sobrescribir. Así no se rompen nuestros componentes UI ni se mezcla el contenido de los bloques con el nuestro.
- El bloque se instalará igual como **archivo nuevo** (ej. `components/hero2.tsx`). Nosotros usamos ese archivo solo como estructura/layout y **inyectamos nuestro contenido** desde `lib/copy.ts` y desde nuestras secciones en `components/sections/`.
- **No uses** `npx shadcn add ... -o` (el flag `-o` fuerza sobrescritura y pisa nuestros archivos).

Nuestro contenido y nuestra UI son los que ya tenemos; los bloques que pagamos son solo plantillas donde nosotros ponemos el copy.

---

## Configuración ya aplicada (como en los docs oficiales)

En este proyecto ya está aplicado lo que indican los docs oficiales:

- **components.json:** `style: "radix-vega"` (Vega = New York), `iconLibrary: "lucide"`, registro `@shadcnblocks` con URL y cabecera `Authorization: Bearer ${SHADCNBLOCKS_API_KEY}`.
- **Tailwind:** plugin `@tailwindcss/typography` instalado y configurado en `tailwind.config.ts`.
- **globals.css:** variables que usan los bloques (`--popover`, `--secondary`, `--destructive`, etc.) añadidas al tema Nord.
- **Tailwind theme:** colores `popover`, `secondary`, `destructive` en `tailwind.config.ts` para clases como `bg-secondary`, `bg-destructive`.

Solo falta que **tú ejecutes** en tu máquina los comandos de instalación (ver abajo). La CLI debe leer la API key del entorno.

---

## Según la documentación oficial

- **Comando para cada bloque:** se usa el **Shadcn CLI** (nombre **sin guión**: `hero2`, `pricing3`):
  ```bash
  npx shadcn add @shadcnblocks/hero2
  ```
  El comando exacto de cada bloque está en la **barra de herramientas** de la página del bloque en [shadcnblocks.com](https://www.shadcnblocks.com) (puedes copiarlo desde ahí).

- **Nombres de bloque:** según la [doc del CLI de Shadcnblocks](https://docs.shadcnblocks.com/blocks/shadcn-cli/), el formato es **categoría + número sin guión**: `hero125`, `pricing3`, `features8` (no `hero-1` ni `hero-125`). El comando exacto está en la toolbar de cada bloque.

- **Bloques Pro / Premium:** hace falta autenticación con API key:
  1. Dashboard → API Keys en [shadcnblocks.com](https://www.shadcnblocks.com).
  2. Añadir la key al entorno (ej. en `.env` o `.env.local`):
     ```bash
     SHADCNBLOCKS_API_KEY=sk_live_your_api_key_here
     ```
  3. En `components.json`, registrar el registry con cabecera de autorización:
     ```json
     "registries": {
       "@shadcnblocks": {
         "url": "https://shadcnblocks.com/r/{name}",
         "headers": {
           "Authorization": "Bearer ${SHADCNBLOCKS_API_KEY}"
         }
       }
     }
     ```
  Con eso puedes instalar cualquier bloque Pro:
  ```bash
  npx shadcn add @shadcnblocks/hero125
  ```

En este proyecto el registro con API key ya está configurado en `components.json`. Solo necesitas tener `SHADCNBLOCKS_API_KEY` en `.env.local`.

---

## Comandos en este proyecto (unclic)

**0. Carpeta correcta**

Los comandos deben ejecutarse **dentro de la carpeta del proyecto** (`unclic`).

- Si tu prompt es `unclic %` o la ruta termina en `.../unclic`, **ya estás en la carpeta correcta**: no ejecutes ningún `cd`.
- Si estás en la **raíz del repo** (p. ej. `pipeline-as-code-with-jenkins-master %`), entonces sí:
  ```bash
  cd toolkit-fastflow/integrations/web-cuantica/unclic
  ```

Si desde dentro de `unclic` ejecutas `cd toolkit-fastflow/...` dará "no such file or directory" porque esa ruta es relativa a la raíz del repo, no a unclic.

**Cargar la API key:** `npx dotenv -e .env.local -- npx shadcn add ...` a veces falla con "could not determine executable". Usa en su lugar:

```bash
export SHADCNBLOCKS_API_KEY=$(grep '^SHADCNBLOCKS_API_KEY=' .env.local | head -1 | cut -d= -f2- | tr -d '\r' | xargs)
npx shadcn add @shadcnblocks/hero2
```

**1. Crear `.env.local`** (si no existe) y poner tu API key:

```bash
cp .env.example .env.local
```

Edita `.env.local` y deja la línea:

```bash
SHADCNBLOCKS_API_KEY=sk_live_...
```

**2. Instalar un bloque** (la CLI usa la variable de entorno):

Desde la carpeta `unclic`, exporta la key y ejecuta el comando que aparece en la toolbar del bloque (o usa el formato oficial):

```bash
export SHADCNBLOCKS_API_KEY=$(grep '^SHADCNBLOCKS_API_KEY=' .env.local | head -1 | cut -d= -f2- | tr -d '\r' | xargs)
npx shadcn add @shadcnblocks/hero2
```

Cuando pregunte si quieres sobrescribir archivos existentes, responde **No** a todos (ver regla arriba). No uses `-o`. Si en el pasado aceptaste sobrescribir y el Button dejó de funcionar, en `components/ui/button.tsx` debe ser `import { Slot } from "@radix-ui/react-slot"` y `Comp = asChild ? Slot : "button"`.

(Como en la [guía del dashboard](https://www.shadcnblocks.com/dashboard/api): nombre **sin guión**, ej. `hero2`, `hero125`, `pricing3`.)

**3. Instalar varios bloques de una vez:**

```bash
npm run shadcn-blocks
```

o:

```bash
bash scripts/add-shadcn-blocks.sh
```

El script lee `SHADCNBLOCKS_API_KEY` de `.env.local` e instala la lista de bloques definida en `scripts/add-shadcn-blocks.sh`. Puedes **editar esa lista** con los nombres exactos de la toolbar (formato sin guión: `hero1`, `pricing3`, etc.).

---

## Nombres de bloque (formato oficial)

En la web, el comando de cada bloque está en la **toolbar** de su página. El nombre suele ser **kebab-case** con número:

| Categoría | Formato (sin guión) |
|-----------|---------------------|
| Hero | `hero2`, `hero125` (ej. del dashboard) |
| Pricing | `pricing3` |
| Features | `features8` |
| Login, Signup, CTA, Navbar, Features, Social, Footer, FAQ, etc. | `login7`, `signup9`, `signup10`, `cta1`, `ecommerce-navbar1`, `feature323`, `feature343`, `social-media-trending3`, `footer1`, `faq1`… |

**Importante:** En la doc oficial usan **categoría + número sin guión**. Siempre copia el comando de la **toolbar** del bloque en [shadcnblocks.com](https://www.shadcnblocks.com).

---

## Por qué usar la CLI (según docs oficiales)

- Respeta tu `components.json` (estilo, rutas, Tailwind, aliases).
- Instala dependencias npm y componentes shadcn que use el bloque.
- Evita copiar/pegar a mano y tener que instalar dependencias por tu cuenta.

---

## Componentes genéricos: qué bloques instalar (UI de pago)

Si quieres **sustituir** secciones o patrones genéricos por bloques de Shadcn Blocks (tu suscripción), instala los que necesites. **Responde No** a sobrescribir; el bloque se crea como archivo nuevo y tú adaptas el contenido con `lib/copy.ts`.

| Uso actual en el sitio | Bloque sugerido | Notas |
|------------------------|-----------------|--------|
| Hero (sections/hero) | `hero2`, `hero70` | Ya tenemos hero tipo Hero70; hero2 está en `components/hero2.tsx`. |
| CTA (sections/cta-section) | `cta1`, `cta2`, … | Sustituir solo el layout; copy desde `cta` en copy. |
| Header / Navbar | `navbar1`, `ecommerce-navbar1` | Nuestro header está en `layout/header.tsx`. Para mega menú: ecommerce-navbar1; logo → UnClicLogo, menú → nav/navDropdowns. |
| Footer | `footer1`, `footer2`, … | Nuestro footer en `layout/footer.tsx`; bloques dan otro layout, mismo copy. |
| Login / Signup | `signup9`, `signup10`, `login7` | Ya instalados signup10, ecommerce-navbar1 trae más UI. Copy: `signupPage`, `loginPage`. |
| Features (grid / lista) | `feature323`, `feature343` | features.items en copy; imágenes desde placeholders o public. |
| Logos (carrusel) | `logos12` | Ya tenemos LogosCarouselSection con `logosCarousel`. |
| FAQ | `faq1`, `faq2`, … | Copy: `faq.sectionTitle`, `pricingFaq`. |
| Pricing | `pricing34`, `pricing41` | Tenemos pricing-section y copy-pricing. |
| Galería | `gallery30`, … | Copy: `gallery`; imágenes: GALLERY_PLACEHOLDERS. |
| Social / trending | `social-media-trending3` | Copy: definir `socialMediaTrending`; imágenes locales. |
| Contacto | `contact1`, … | Página contacto y copy correspondiente. |

**Instalar más bloques** (cuando pregunte sobrescribir, responde **No**):

```bash
export SHADCNBLOCKS_API_KEY=$(grep '^SHADCNBLOCKS_API_KEY=' .env.local | head -1 | cut -d= -f2- | tr -d '\r' | xargs)
npx shadcn add @shadcnblocks/cta1
npx shadcn add @shadcnblocks/footer1
npx shadcn add @shadcnblocks/faq1
npx shadcn add @shadcnblocks/pricing34
npx shadcn add @shadcnblocks/contact1
```

Cada comando se ejecuta por separado. Nombres sin guión; el exacto está en la toolbar del bloque en shadcnblocks.com.

Luego integra el JSX del bloque en tu página o sección y alimenta todo desde `lib/copy.ts`. Ver [CONSISTENCIA-UI.md](./CONSISTENCIA-UI.md) para ejemplos de adaptación.

---

## Después de instalar

- Los bloques pueden instalarse en `components/` (ej. `hero2.tsx`) o en la ruta que indique el bloque.
- Integra o sustituye el JSX en tus páginas y mantén el contenido en `lib/copy.ts`.
- Ver [SHADCN-BLOCKS-MAP.md](./SHADCN-BLOCKS-MAP.md) para el mapeo bloque → sección del sitio.

### Si la CLI pide sobrescribir archivos

**Respuesta recomendada: No a todo.**

- No sobrescribas `button.tsx`, `utils.ts`, `badge.tsx` ni ningún archivo existente. Así no se rompen nuestros componentes ni se mete el contenido por defecto de los bloques en nuestra UI.
- El bloque se añade como archivo nuevo (ej. `hero2.tsx`). Nosotros usamos ese bloque pasándole nuestro copy desde `lib/copy.ts` o usando nuestras secciones en `components/sections/` (ej. el hero real está en `sections/hero.tsx`).
- Si en el pasado aceptaste sobrescribir y algo se rompió (p. ej. Button), revisa: import `@radix-ui/react-slot`, `Comp = asChild ? Slot : "button"` en `components/ui/button.tsx`.

---

## Si aparece «Block not found» o «item was not found»

- **Nombre exacto:** La doc oficial usa **sin guión**: `hero1`, `hero125`, `pricing3`, no `hero-1`. Si sigue fallando, copia el comando exacto de la toolbar del bloque en [shadcnblocks.com](https://www.shadcnblocks.com).
- **API key:** Comprueba que `SHADCNBLOCKS_API_KEY` está exportada en la misma terminal donde ejecutas `npx shadcn add`. Usa `export SHADCNBLOCKS_API_KEY=$(grep '^SHADCNBLOCKS_API_KEY=' .env.local | head -1 | cut -d= -f2- | tr -d '\r' | xargs)` (evita errores tipo «not valid in this context» que da `export $(grep ... | xargs)` con valores largos). Algunas versiones del CLI no expanden `${SHADCNBLOCKS_API_KEY}` dentro de `components.json`; en ese caso la petición iría sin autorización y el registry puede devolver «not found».
- **Pro vs gratis:** Algunos bloques son Pro/Premium y requieren API key; otros son públicos. Prueba primero un bloque que sepas que es gratuito para descartar problemas de red o de nombre.

---

## Seguridad

- No subas la API key al repo. Úsala solo en `.env.local` (o en variables de entorno en tu máquina/CI). `.env.local` está en `.gitignore`.

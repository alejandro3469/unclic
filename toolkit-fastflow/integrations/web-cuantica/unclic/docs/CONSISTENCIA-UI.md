# Consistencia UI — una sola fuente

Para que cada ítem, texto y componente del sitio sea mantenible y consistente (estilo Shadcn Blocks):

## Texto y copy

- **Todo el texto visible** sale de **`lib/copy.ts`** (o `lib/copy-pricing.ts` para precios/FAQ).
- No uses cadenas hardcodeadas en componentes. Añade la clave en el objeto correspondiente (`hero`, `nav`, `cta`, `faq`, `footer`, etc.) y úsala en el JSX.
- Ejemplo: el CTA del header usa `nav.ctaPrimary`; el título del FAQ usa `faq.sectionTitle`; títulos de iframe/alt usan `architectureLive.iframeTitle`, `architectureLive.imageAlt`.

## Componentes UI

- **Botones:** `@/components/ui/button` (variantes: default, outline, ghost, link).
- **Cards, inputs, acordeones, etc.:** `@/components/ui/*` (shadcn).
- **Secciones:** `BlockContainer`, `BlockSection`, `BlockSectionHeader` desde `@/components/blocks` para el mismo ancho y estructura que los bloques de Shadcn Blocks.
- **Logo:** `UnClicLogo` desde `@/components/ui/unclic-logo`.

## Tokens del tema

- Usa las clases del tema para colores y bordes: `bg-background`, `text-foreground`, `border-border`, `bg-primary`, `text-primary-foreground`, `text-muted-foreground`, `bg-muted`, etc.
- Evita colores fijos (hex o rgb) salvo en gradientes o casos muy puntuales; así el sitio respeta light/dark y el tema Nord.

## Resumen

| Qué | Dónde |
|-----|--------|
| Textos, títulos, CTAs, labels | `lib/copy.ts` o `lib/copy-pricing.ts` |
| Botones, cards, inputs, accordion | `components/ui/*` |
| Contenedores y cabeceras de sección | `components/blocks` (BlockContainer, BlockSection) |
| Colores y tipografía | Clases Tailwind del tema (`bg-background`, `text-foreground`, etc.) |

Así toda la UI queda alineada con un único sistema (Shadcn Blocks + nuestro copy).

---

## Ejemplos de bloques adaptados

Cuando instales un bloque (ej. `npx shadcn add @shadcnblocks/signup10`), no copies el JSX tal cual. Usa **nuestro copy** y **nuestros componentes** como en los ejemplos siguientes.

### Ejemplo 1: Signup10 / Signup9 (registro)

El bloque original trae texto en inglés y URLs externas (logo, imágenes). **Signup9** añade un carrusel de logos debajo del formulario (con los mismos fallos de gradiente: `bg-linear-to-*` → `bg-gradient-to-*`). Nosotros:

- **Logo:** sustituir `<img src="...cloudfront...">` por `<UnClicLogo size={56} />` y `alt={signupPage.logoAlt}`.
- **Título:** usar `signupPage.title` (ej. "Crear cuenta") en lugar de "Create your free account".
- **Botón Google:** texto `signupPage.signUpWithGoogle` ("Continuar con Google"); el ícono de Google puede quedarse en CDN o usar uno local.
- **Separador:** texto `signupPage.or` ("o").
- **Input:** `placeholder={signupPage.emailPlaceholder}` ("Tu correo").
- **Botón principal:** texto `signupPage.continue` ("Continuar"); clases `bg-foreground text-background` para respetar el tema.
- **Pie legal:** `signupPage.termsPrefix`, `signupPage.termsLink`, `signupPage.privacyLink`; enlaces a `/legal/...` o lo que tengas.
- **Enlace a login:** `signupPage.alreadyUser` + `signupPage.logIn`; el enlace a `/login`.
- **Imagen lateral:** si la necesitas, usar una de `lib/placeholders` o una ruta en `public/`; no dejar URLs de shadcnblocks en producción.
- **Signup9 solo — carrusel de logos bajo el formulario:** sustituir el array `logos` del bloque por **`logosCarousel.items`** de `lib/copy.ts`; los gradientes de borde deben ser `bg-gradient-to-r from-background to-transparent` y `bg-gradient-to-l from-background to-transparent` (no `bg-linear-to-r` / `bg-linear-to-l`).

Copy ya definido en `lib/copy.ts`: **`signupPage`** y **`logosCarousel`** (para el carrusel de Signup9).

### Ejemplo 2: Hero, CTA, FAQ

- **Hero:** título y subtítulo desde `hero.headline`, `hero.subtitle`, `hero.ctaPrimary`, `hero.ctaSecondary`; features desde `hero.heroFeatures`.
- **CTA:** `cta.sectionTitle`, `cta.sectionDescription`, `cta.ctaPrimary`, `cta.ctaSecondary`.
- **FAQ:** título de sección `faq.sectionTitle`; ítems desde `pricingFaq` (copy-pricing) o el objeto que uses para preguntas/respuestas.

### Ejemplo 3: Componentes UI

Siempre que el bloque use un botón, input, card o separador:

- Importar desde `@/components/ui/button`, `@/components/ui/input`, `@/components/ui/separator`, `@/components/ui/card`.
- No crear nuevos estilos inline; usar variantes del tema (`variant="outline"`, `className="bg-background text-foreground"`).

### Ejemplo 4: Logos12 (carrusel de logos)

El bloque original usa URLs de cloudfront y `bg-linear-to-r` / `bg-linear-to-l` (inválido en Tailwind). Nosotros:

- **Datos:** usar **`logosCarousel`** en `lib/copy.ts` (array `items` con `id`, `description`, `image`). Las imágenes pueden ser rutas en `public/` o URLs permitidas (ej. `cdn.simpleicons.org` en `next.config.js`).
- **Gradientes:** sustituir `bg-linear-to-r` por `bg-gradient-to-r from-background to-transparent` y `bg-linear-to-l` por `bg-gradient-to-l from-background to-transparent`.
- **Componente:** ya existe **`LogosCarouselSection`** en `components/sections/logos-carousel-section.tsx`; usa `logosCarousel`, `Carousel` + `AutoScroll`, y los gradientes correctos. Se muestra en la home entre Hero y CTA.

Comando para instalar el bloque de referencia: `npx shadcn add @shadcnblocks/logos12`. Responde **No** a sobrescribir; luego puedes comparar con nuestra implementación.

### Ejemplo 5: EcommerceNavbar1 (navbar con mega menú)

El bloque es para e-commerce (Store, Collections, Sale, carrito, selector de país). Si usas algo parecido en UnClic (p. ej. un mega menú para Soluciones / Capacidades / Insights):

- **Logo y home:** sustituir `Logo` / `LogoImage` de `@/components/shadcnblocks/logo` por **`UnClicLogo`** (`@/components/ui/unclic-logo`) y el enlace a `/`. No usar `home.logo.src` de cloudfront.
- **Menú:** los ítems (Store, Collections, Sale, Blog) deben salir de **`nav`** y **`navDropdowns`** en `lib/copy.ts` (Soluciones, Capacidades, Insights, Empresa, Contacto) con sus `href` a nuestras rutas (`/soluciones`, `/capacidades`, etc.).
- **Mega menu sections:** si tienes submenús con secciones, las `label` y `items[].label` / `href` desde copy (ej. mismo contenido que `navDropdowns` y `footerNav`); no usar imágenes de cloudfront en `imageSrc` — usar rutas en `public/` o eliminarlas.
- **Links útiles y contacto:** sustituir `HELPFULL_LINKS` y `CONTACT_INFO` por **`footerNav.connect`**, **`footer`** (builtWith, copyright) o un objeto en copy para “Enlaces útiles” y “Contacto” (teléfono, email).
- **Redes sociales:** usar **`footerNav`** y las URLs de entorno `NEXT_PUBLIC_LINKEDIN_URL`, `NEXT_PUBLIC_GITHUB_URL` como ya hace nuestro header; no usar `SOCIAL_ICONS` con URLs de cloudfront.
- **Selector de país:** si no lo necesitas, quitar `CountrySelector`; si lo necesitas, texto y opciones desde copy.
- **Dependencias del bloque:** el bloque usa `@/components/shadcnblocks/logo` y `@/hooks/usePreventScrollLock`. Si instalas el bloque, mantén el hook (o créalo si no existe) y sustituye el uso de `Logo`/`LogoImage` por `UnClicLogo` y nuestro copy.

En este proyecto el **Header** actual (`components/layout/header.tsx`) ya sigue nuestro patrón (nav, navDropdowns, UnClicLogo, Sheet móvil, sin mega menú). EcommerceNavbar1 sirve como referencia de estructura si en el futuro quieres un mega menú con el mismo estilo de copy y componentes.

Comando para instalar el bloque de referencia: `npx shadcn add @shadcnblocks/ecommerce-navbar1`. Responde **No** a sobrescribir.

### Ejemplo 6: Feature323 (sección features con animación)

El bloque muestra una lista de features con imagen grande animada (framer-motion), controles anterior/siguiente y cards expandibles. Viene con contenido e-commerce (Smart Watches, Fitness Tracking, etc.) e imágenes de cloudfront.

- **Título:** usar **`features.sectionTitle`** (ej. "Cómo trabajamos") en lugar de "Discover Our Products"; la descripción opcional desde **`features.sectionDescription`**.
- **Lista de features:** el bloque espera `FeatureItem[]` con `image`, `title`, `description`. En UnClic puedes mapear **`features.items`** de `lib/copy.ts` (cada ítem tiene `title`, `description`); para `image` usa una entrada de **`GALLERY_PLACEHOLDERS`** en `lib/placeholders.ts` o una ruta en `public/` (ej. por índice: `GALLERY_PLACEHOLDERS[index % GALLERY_PLACEHOLDERS.length]`). Si añades un objeto tipo `featureCarousel` en copy con `{ image, title, description }[]`, mejor.
- **Imágenes:** no dejar URLs de cloudfront; usar solo URLs de `next.config.js` (Unsplash, simpleicons, etc.) o rutas locales.
- **Animaciones:** el bloque usa **framer-motion** (`AnimatePresence`, `motion`). El proyecto usa la lib **motion** (ex-framer-motion); si el bloque instala `framer-motion`, puedes seguir usándolo o adaptar a `motion` si la API es compatible.
- **Hooks:** el bloque usa **`useMediaQuery("(max-width: 768px)")`**. Si no existe en el proyecto, usa **`useIsMobile`** de `@/hooks/use-mobile` o crea `useMediaQuery` según la implementación del bloque.

Comando para instalar el bloque de referencia: `npx shadcn add @shadcnblocks/feature323`. Responde **No** a sobrescribir.

### Ejemplo 7: Feature343 (features con líneas punteadas y grid)

El bloque muestra un título, líneas punteadas decorativas (`DashedLine`) y dos filas de features: dos ítems arriba y tres abajo. Cada ítem tiene `title`, `description` e `images[]` (con `src` y `alt`); algunos ítems usan `isIconGrid: true` para mostrar iconos en grid.

- **Título:** usar **`features.sectionTitle`** (ej. "Cómo trabajamos") en lugar de "Streamline your resource allocation..."; si quieres subtítulo, **`features.sectionDescription`**.
- **Top/bottom items:** el bloque espera `FeatureItem[]` con `title`, `description`, `images: { src, alt }[]`, y opcional `isIconGrid`. En UnClic puedes construir los arrays desde **`features.items`** en `lib/copy.ts` (cada ítem tiene `title`, `description`); para `images` usa una sola imagen por ítem con **`GALLERY_PLACEHOLDERS`** o rutas en `public/` (ej. `[{ src: GALLERY_PLACEHOLDERS[i], alt: item.title }]`). Para ítems tipo “stack” (iconos de herramientas) puedes usar **`logosCarousel.items`** o un array en copy con `images` de Simple Icons / `public/` y `isIconGrid: true`.
- **Imágenes:** no usar URLs de cloudfront (`placeholder-1.svg`, `jira.svg`, etc.); solo URLs permitidas en `next.config.js` o rutas locales.
- **DashedLine:** el componente `DashedLine` (horizontal/vertical con gradiente repetido y máscara) es reutilizable; puedes dejarlo en el bloque o extraerlo a `@/components/ui/dashed-line` si lo usas en más sitios (en el proyecto ya existía algo similar en el hero anterior).

Comando para instalar el bloque de referencia: `npx shadcn add @shadcnblocks/feature343`. Responde **No** a sobrescribir.

### Ejemplo 8: SocialMediaTrending3 (grid “social” + destacado)

El bloque muestra una sección destacada (título, perfil con avatar/username/followers, CTA) y un grid de “posts” (imagen + enlace). Usa `INSTAGRAM_ICON` y todas las imágenes desde cloudfront.

- **Título y perfil:** el bloque espera `featuredSection: { title, profile: { image, username, followers }, cta: { label, href } }`. Crear en **`lib/copy.ts`** un objeto (ej. **`socialMediaTrending`**) con ese formato: `title` tipo "Conéctate con nosotros", `profile.image` como ruta en `public/` o avatar por defecto (sin cloudfront), `profile.username` y `profile.followers` con nuestro copy, `cta.label` y `cta.href` (ej. **`nav.ctaPrimary`** y `/demo/access` o `/contacto`).
- **Posts:** `posts[]` con `image: { src, alt }` y `productLink`. Usar **`GALLERY_PLACEHOLDERS`** o imágenes en `public/` para `src`; `alt` descriptivo desde copy o por ítem; `productLink` a nuestras rutas (demo, insights, etc.) en lugar de `#`.
- **Icono de red social:** no usar `INSTAGRAM_ICON` de cloudfront. Usar un ícono de **lucide-react** (ej. `Share2`, `Instagram` si existe) o una imagen en `public/`; o quitar el overlay del hover si no aplica.
- **Componentes:** seguir usando **Avatar**, **Button** de `@/components/ui`; las clases del tema (`bg-background`, `text-muted-foreground`, etc.) ya están bien.

Comando para instalar el bloque de referencia: `npx shadcn add @shadcnblocks/social-media-trending3`. Responde **No** a sobrescribir.

### Comando para instalar bloques de ejemplo

```bash
export SHADCNBLOCKS_API_KEY=$(grep '^SHADCNBLOCKS_API_KEY=' .env.local | head -1 | cut -d= -f2- | tr -d '\r' | xargs)
npx shadcn add @shadcnblocks/signup10
npx shadcn add @shadcnblocks/logos12
npx shadcn add @shadcnblocks/ecommerce-navbar1
npx shadcn add @shadcnblocks/feature323
npx shadcn add @shadcnblocks/feature343
npx shadcn add @shadcnblocks/social-media-trending3
npx shadcn add @shadcnblocks/signup9
```

Al preguntar si quieres sobrescribir archivos existentes, responde **No**. Luego adapta el JSX del bloque usando nuestro copy y componentes como en los ejemplos.

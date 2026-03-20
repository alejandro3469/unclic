# Requisitos: Sitio UnClic

Qué necesitamos para tener el sitio dummy listo y luego rellenarlo con contenido. Basado en lo usado en **Vantive**.

**Propósito del sitio:** Albergar los **links de UnClic** a sus **demos** (automatización con Jenkins, registry, etc.), dejar el **flujo FastFlow aplicado al POS** como demo, y ofrecer un **usuario abierto** para que los visitantes **prueben desde la UI de UnClic** sin registrarse.

---

## 1. Entorno de desarrollo

| Requisito | Versión / detalle |
|-----------|-------------------|
| Node.js | 18+ (recomendado 20 LTS) |
| npm | Para `npm ci` / `npm install` |
| Editor | Cualquiera (VS Code, Cursor) con soporte TypeScript y ESLint |

---

## 2. Dependencias (package.json)

- **Next.js** 15, **React** 19.
- **Tailwind CSS** 3, **PostCSS**, **autoprefixer**.
- **TypeScript** 5.
- **Radix UI** (shadcn-style): `@radix-ui/react-slot`, `@radix-ui/react-dialog`, `@radix-ui/react-tabs`, `@radix-ui/react-slider`, `@radix-ui/react-progress`, `@radix-ui/react-separator`, etc., según componentes que copiemos.
- **motion** (framer-motion / motion): animaciones.
- **class-variance-authority**, **clsx**, **tailwind-merge**: estilos y `cn()`.
- **lucide-react**: iconos genéricos (estilo Liquid Glass / neutros).
- **Three.js**: `three`, `@react-three/fiber`, `@react-three/drei` (Orb).
- **Audio:** mismo patrón que Vantive: componente reproductor con play/pause, scrub, velocidad, lista de pistas (sin depender de @elevenlabs/react si no hace falta; podemos reutilizar la API del AudioPlayer de Vantive).

---

## 3. Estructura de la UI

- **Header:** logo placeholder, navegación dummy (Enlace 1, Enlace 2, Demos), CTA “Contacto”.
- **Hero:** título, subtítulo, CTA; opcional imagen o gradiente neutro.
- **Secciones de contenido:** títulos y párrafos placeholder (Lorem o “Contenido 1”, “Contenido 2”).
- **DemosSection (nueva):** sección con links a la **demo de automatización** (Jenkins, registry), al **FastFlow aplicado al POS** (demo), y CTA/texto para el **usuario abierto** (acceso para que visitantes prueben desde la UI de UnClic). Cards o lista de enlaces; opcionalmente credenciales de prueba o enlace directo al Jenkins/registry de demo.
- **AudioSection:** reproductor (controles ElevenLabs-style) + lista de 3–5 pistas con audios genéricos.
- **OrbSection:** contenedor con el Orb (Three.js), colores neutros.
- **CTA:** bloque final con título y botón “Comenzar” / “Contactar”.
- **Footer:** columnas con enlaces placeholder, copyright.

---

## 4. Audios genéricos

- **Formato:** MP3 (compatible con `<audio>` y reproductor actual).
- **Origen:** samples libres (ej. desde freesound, o archivos de prueba) o placeholders en `public/audio/` (sample-1.mp3, sample-2.mp3, …).
- **Lista:** definida en `lib/audio-samples.ts` (título + src por pista).
- **Política:** no autoplay; play solo tras gesto del usuario (igual que en Vantive).

---

## 5. Iconos

- **Set:** lucide-react (o el que se use en muestras) como “genéricos tipo Liquid Glass”.
- **Uso:** Header (menú, cerrar), Hero (flecha o ícono), Features (iconos por ítem), Footer (redes, correo). Sin marca específica.

---

## 6. Tema (out of the box)

- **CSS variables** en `app/globals.css`: --background, --foreground, --muted, --border, --primary (neutro), --card, etc., con valores slate/zinc.
- **tailwind.config.ts:** extend theme con esas variables; darkMode opcional (class).
- Sin paleta de marca; listo para sustituir después por colores de UnClic si se define.

---

## 7. Documentación a mantener

- **PLAN-GRANULAR-SITIO-WEB-CUANTICA.md:** fases, checklist, estructura.
- **REQUISITOS-SITIO-WEB-CUANTICA.md:** este archivo (requisitos técnicos y de contenido).
- **README.md (raíz del proyecto):** cómo instalar, `npm run dev`, `npm run build`, dónde poner audios y cómo sustituir placeholders.

---

## 8. Demos y usuario abierto (UnClic)

| Requisito | Descripción |
|-----------|-------------|
| **Links a demos** | URLs a la demo de automatización con **Jenkins** y **registry** (y las que UnClic defina). Incluir en una sección “Demos” o “Prueba la automatización”. |
| **FastFlow + POS** | Dejar el **flujo para UnClic** como **demo de FastFlow aplicado al POS**: enlazable desde el sitio y documentado. |
| **Usuario abierto** | Un **usuario de prueba** (credenciales o enlace de acceso) para que **cualquier visitante pueda probar desde la UI de UnClic** (Jenkins de demo, registry, etc.) sin registrarse. Definir y documentar en el sitio o en docs; opcionalmente mostrarlo en la sección Demos. |

---

## 9. Referencias a Vantive

| Concepto | Dónde en Vantive |
|----------|-------------------|
| Audio player (API y controles) | components/ui/audio-player.tsx, paciente-objetivo-drawer-content |
| Orb | components/ui/orb.tsx |
| Layout (header/footer) | components/site-layout-shell, footer-32, hero-section-01/header |
| Contenedores y secciones | components/content/site-section.tsx, lib/utils.ts (cn) |
| Estilos globales y tema | app/globals.css, tailwind.config.ts |

---

*Actualizar este documento cuando se añadan requisitos nuevos (ej. analytics, formularios, rutas adicionales).*

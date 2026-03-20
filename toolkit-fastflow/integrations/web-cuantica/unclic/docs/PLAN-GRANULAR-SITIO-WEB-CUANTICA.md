# Plan granular: Landing UnClic (estructura y dummy UI)

**Objetivo:** Tener un proyecto Next.js limpio dentro de `toolkit-fastflow/integrations/web-cuantica/unclic` que simule la estructura de un sitio web completo con componentes dummy, mismo stack que Vantive (Next.js, Tailwind, shadcn-style, audio ElevenLabs-style, Orb, iconos genéricos), **sin tema de colores de marca** (out of the box / neutro), listo para ordenar y rellenar con contenido real.

**Referencia:** Stack y patrones de **Vantive** (kings-joers/vantive): componentes UI, audio player, Orb, motion, estructura app/.

---

## 0. Propósito del sitio (UnClic)

El sitio será el **escaparate de UnClic** donde se pondrán:

| Objetivo | Descripción |
|----------|-------------|
| **Links a demos** | Enlaces a la **demo de automatización con Jenkins y registry** (y demás demos). Una sección dedicada con cards o lista de enlaces: Jenkins, registry, pipelines, etc. |
| **FastFlow aplicado al POS** | Conseguir y dejar un **flujo** que funcione como **demo de FastFlow aplicado al POS** (point of sale). El sitio debe contemplar esta demo como producto visible y enlazable. |
| **Usuario abierto para pruebas** | Dejar un **usuario abierto** (credenciales de prueba) para que **los usuarios puedan probar desde la UI**: acceso al Jenkins de demo, al registry o a la herramienta que corresponda, sin tener que registrarse. Documentar usuario/contraseña o enlace de acceso en el sitio o en docs. |

**Contemplar en el plan:** sección "Demos" o "Prueba la automatización" con links a Jenkins (demo), registry (demo), y CTA/instrucciones para usar el usuario de prueba desde la UI.

---

## 1. Alcance

| Ítem | Descripción |
|------|-------------|
| **Proyecto** | Next.js 15, React 19, `output: 'export'` (estático). |
| **Tema** | Neutro (slate/zinc), sin paleta de marca; variables CSS out of the box. |
| **UI** | Componentes dummy: Button, Card, Separator, Tabs, Dialog, Drawer, Progress, etc. (como en muestras shadcn). |
| **Audio** | Componentes tipo ElevenLabs (AudioPlayer, lista de pistas, controles) con **audios genéricos** (placeholders o samples públicos). |
| **Orb** | Componente Orb (Three.js / @react-three/fiber) igual que en Vantive, con colores neutros. |
| **Iconos** | Genéricos tipo Liquid Glass (lucide-react u otro set genérico), sin marca. |
| **Estructura** | Header, Hero, secciones (Features, **Demos** — links Jenkins/registry/automatización, Audio, Orb, CTA, FAQ), Footer. |
| **Demos UnClic** | Sección con links a demo de automatización (Jenkins, registry), FastFlow aplicado al POS, y usuario abierto para que visitantes prueben desde la UI. |

---

## 2. Fases del plan

### Fase 1 — Proyecto base (hecho en este arranque)
- [x] Crear carpeta `unclic` en `integrations/web-cuantica/`.
- [x] Documentar plan granular y requisitos.
- [x] Inicializar Next.js con Tailwind, tema neutro (CSS variables slate/zinc).
- [x] Añadir dependencias: motion, Radix (Button, Card, Dialog, Drawer, Tabs, Slider, Progress), @react-three/fiber + drei (Orb), @elevenlabs/react o mismo patrón audio-player que Vantive, lucide-react.
- [x] Estructura `app/`: layout, globals.css, page.tsx (una sola página que simula sitio completo).
- [x] Componentes UI dummy mínimos: Button, Card, Separator, etc.
- [x] Secciones dummy: Header, Hero, SectionBlock, AudioSection (reproductor + lista con audios genéricos), OrbSection, CTA, Footer.
- [x] Audios genéricos: referencias a samples públicos (ej. placeholder URLs o `/audio/sample-1.mp3`) o README para sustituir.

### Fase 2 — Completar componentes y contenido placeholder
- [ ] Revisar que todos los bloques de la página tengan el mismo patrón que las muestras (espaciado, contenedores, animaciones suaves).
- [ ] **Añadir sección Demos:** bloque con links a la demo de automatización (Jenkins, registry), FastFlow aplicado al POS, y texto/CTA para usuario abierto (probar desde la UI).
- [ ] Documentar usuario abierto (credenciales o enlace de acceso) en el sitio o en docs para que visitantes puedan probar sin registrarse.
- [ ] Añadir más componentes UI si hace falta (Badge, Alert, Tooltip) como dummy.
- [ ] Documentar en README cómo sustituir audios genéricos por los definitivos.
- [ ] Añadir iconos Liquid Glass (o lucide) en Header, Hero, Features, Footer.

### Fase 3 — Listo para ordenar y rellenar
- [ ] Ordenar secciones según maqueta o copy (mover bloques en page.tsx o extraer a rutas).
- [ ] Sustituir textos placeholder por copy real.
- [ ] **Definir y publicar URLs de demos:** Jenkins (demo), registry, y flujo FastFlow aplicado al POS.
- [ ] **Dejar usuario abierto** y enlazar/indicar en la UI cómo acceder para pruebas.
- [ ] Sustituir audios placeholder por archivos finales.
- [ ] Aplicar tema de colores de marca si se define (opcional; por ahora se deja neutro).

---

## 3. Estructura de archivos objetivo

```
unclic/
├── app/
│   ├── layout.tsx
│   ├── globals.css
│   └── page.tsx              # Una página con todas las secciones dummy
├── components/
│   ├── ui/                    # Button, Card, Separator, Tabs, Dialog, Drawer, Progress, Slider, etc.
│   ├── layout/
│   │   ├── header.tsx
│   │   └── footer.tsx
│   ├── sections/
│   │   ├── hero.tsx
│   │   ├── section-block.tsx
│   │   ├── demos-section.tsx  # Links a demos: Jenkins, registry, FastFlow+POS, usuario abierto
│   │   ├── audio-section.tsx  # Reproductor + lista (audios genéricos)
│   │   ├── orb-section.tsx
│   │   └── cta-section.tsx
│   └── audio/                 # Wrapper ElevenLabs-style (provider + controls)
├── lib/
│   ├── utils.ts
│   └── audio-samples.ts       # URLs o paths de audios genéricos
├── public/
│   └── audio/                 # (opcional) samples genéricos
├── docs/
│   ├── PLAN-GRANULAR-SITIO-WEB-CUANTICA.md
│   └── REQUISITOS-SITIO-WEB-CUANTICA.md
├── package.json
├── next.config.js
├── tailwind.config.ts
├── tsconfig.json
└── README.md
```

---

## 4. Stack técnico (alineado a Vantive)

| Dependencia | Uso |
|-------------|-----|
| next | 15.x, App Router, output: 'export' |
| react / react-dom | 19.x |
| tailwindcss | 3.x, tema neutro |
| motion | Animaciones (fade-in, etc.) |
| @radix-ui/* | Button, Card (slot), Dialog, Drawer, Tabs, Slider, Progress |
| @react-three/fiber, @react-three/drei, three | Orb |
| lucide-react | Iconos genéricos (Liquid Glass style) |
| Audio | Mismo patrón que Vantive: AudioPlayerProvider, useAudioPlayer, controles (play/pause, scrub, velocidad); audios genéricos |

---

## 5. Tema y diseño

- **Colores:** Variables CSS neutras (--background, --foreground, --muted, --border, etc.) con valores slate/zinc por defecto, sin primario de marca.
- **Tipografía:** Una fuente sans (ej. Inter o system-ui) para todo; sin segunda familia en esta fase.
- **Espaciado:** Contenedores `site-container` (max-width + padding), secciones con py-16 md:py-24.
- **Animaciones:** Entrada suave (opacity + translateY) al scroll; sin animaciones pesadas en dummy.

---

## 6. Audio genérico

- Lista de N pistas (ej. 3–5) con títulos placeholder: "Introducción", "Tema 1", "Cierre".
- Archivos: en `public/audio/` (sample-1.mp3, sample-2.mp3, …) o URLs a samples públicos; documentar en README.
- Comportamiento: mismo que Vantive (play/pause, barra de progreso, velocidad, sin autoplay hasta gesto del usuario).

---

## 7. Orb

- Mismo componente Orb que Vantive (o copia simplificada): colores neutros (grises/claros), sin vinculación a volumen de micrófono en dummy; opcionalmente `manualInput`/`manualOutput` para animación suave.

---

## 8. Criterios de “listo para ordenar y rellenar”

- Una sola página con: Header, Hero, 2–3 bloques de contenido, **DemosSection** (links Jenkins, registry, FastFlow+POS, usuario abierto), AudioSection, OrbSection, CTA, Footer.
- Todos los componentes son dummy (textos e imágenes placeholder) hasta sustituir por copy y URLs reales.
- **Demos:** URLs de la demo de automatización (Jenkins, registry) y del flujo FastFlow aplicado al POS definidas; usuario abierto documentado y accesible desde la UI para que visitantes prueben.
- Build `npm run build` genera `out/` sin errores.
- Documentación (este plan + REQUISITOS) describe qué hace falta para sustituir por contenido real.

---

*Documento vivo: actualizar fases y checkboxes según avance.*

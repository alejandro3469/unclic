# Secciones del sitio según nuestra copia

Resumen: **qué sección usa qué copia** y **orden en la página**. Toda la copia vive en `lib/copy.ts` y `lib/copy-pricing.ts`; las secciones solo leen de ahí.

---

## Orden de secciones (page.tsx)

| # | Id (ancla) | Sección | Copia (lib) |
|---|------------|---------|-------------|
| 1 | — | Hero | `hero` |
| 2 | `why` | Por qué Pipeline as Code | `why` |
| 3 | `features` | Qué ofrecemos | `features` |
| 4 | `how-it-works` | Cómo empezar | `howItWorks` |
| 5 | `demos` | Demos en vivo | `demos` |
| 6 | `pricing` | Precios | `copy-pricing` (pricingHero, pricingModels, etc.) |
| 7 | `gallery` | Galería | `gallery` |
| 8 | `video` | Vídeo | `video` |
| 9 | `audio` | Contenidos en audio | `audio` |
| 10 | `orb` | Visualización | `orb` |
| 11 | `cliente-ideal` | Trabajamos mejor con | `clienteIdeal` |
| 12 | `contacto` | CTA final | `cta` |

---

## Componentes por sección

| Sección | Componente | Archivo |
|---------|------------|---------|
| Hero | `Hero` | `components/sections/hero.tsx` |
| Por qué | `WhySection` | `components/sections/why-section.tsx` |
| Qué ofrecemos | `FeaturesSection` | `components/sections/features-section.tsx` |
| Cómo empezar | `HowItWorksSection` | `components/sections/how-it-works-section.tsx` |
| Demos | `DemosSection` | `components/sections/demos-section.tsx` |
| Precios | `PricingSection` | `components/sections/pricing-section.tsx` |
| Galería | `GallerySection` | `components/sections/gallery-section.tsx` |
| Vídeo | `VideoSection` | `components/sections/video-section.tsx` |
| Audio | `AudioSection` | `components/sections/audio-section.tsx` |
| Orb | `OrbSection` | `components/sections/orb-section.tsx` |
| Trabajamos mejor con | `ClienteIdealSection` | `components/sections/cliente-ideal-section.tsx` |
| CTA | `CtaSection` | `components/sections/cta-section.tsx` |

---

## Navegación (header)

Enlaces del nav = anclas de las secciones. Textos en `nav` (lib/copy.ts): `why`, `features`, `howItWorks`, `demos`, `pricing`, `clienteIdeal`, `contact`.

---

## Fuentes de la copia (docs)

- COPY-PRODUCTOS-SERVICIOS-PRICING, COPY-LANDING-ESTILO-CLERK, COPY-DEVOPS-AS-A-SERVICE-DAAS
- COPY-LANDING-OFERTA-SPENDBASE (Cómo funciona en 3 pasos)
- COPY-PRICING-PAGE, COPY-FRAGMENTOS
- PLAN-COPIA-WEB-CUANTICA, PLAN-GRANULAR-SITIO-WEB-CUANTICA

Para cambiar textos: editar `lib/copy.ts` o `lib/copy-pricing.ts`; no hardcodear en los componentes.
